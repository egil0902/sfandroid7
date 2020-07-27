/*************************************************************************************
 * Product: SFAndroid (Sales Force Mobile)                                           *
 * This program is free software; you can redistribute it and/or modify it           *
 * under the terms version 2 of the GNU General Public License as published          *
 * by the Free Software Foundation. This program is distributed in the hope          *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied        *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.                  *
 * See the GNU General Public License for more details.                              *
 * You should have received a copy of the GNU General Public License along           *
 * with this program; if not, write to the Free Software Foundation, Inc.,           *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                            *
 * For the text or an alternative of this public license, you may reach us           *
 * Copyright (C) 2012-2012 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.appd.util;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.appd.base.DB;
import org.appd.base.Env;
import org.appd.base.R;
import org.appd.util.contribution.ActionItem;
import org.appd.util.contribution.QuickAction;

import android.content.Context;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Yamel Senih
 *
 */
public class AdapterProductItem extends BaseAdapter {

	private ArrayList<DisplayProductItem> items = new ArrayList<DisplayProductItem>();
	private LayoutInflater inflater;
	private String listType = "CI";
	private Context ctx;
	private QuickAction mQAct = null;
	private int m_C_BPartner_Location_ID;
	
	public AdapterProductItem(Context ctx){
		inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		items = new ArrayList<DisplayProductItem>();
		this.ctx = ctx;
		notifyDataSetChanged();
		mQAct = new QuickAction(ctx);
		m_C_BPartner_Location_ID = Env.getContextAsInt(ctx, "C_BPartner_Location_ID");
	}
	
	public AdapterProductItem(Context ctx, String listType){
		inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		items = new ArrayList<DisplayProductItem>();
		notifyDataSetChanged();
		this.ctx = ctx;
		this.listType = listType;
		mQAct = new QuickAction(ctx);
		m_C_BPartner_Location_ID = Env.getContextAsInt(ctx, "C_BPartner_Location_ID");
	}
	
	public AdapterProductItem(Context ctx, String listType, OnClickListener event){
		inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		items = new ArrayList<DisplayProductItem>();
		notifyDataSetChanged();
		this.ctx = ctx;
		this.listType = listType;
		mQAct = new QuickAction(ctx);
		m_C_BPartner_Location_ID = Env.getContextAsInt(ctx, "C_BPartner_Location_ID");
	}
	
	/**
	 * Agrega un Item al listado
	 * @author Yamel Senih 14/05/2012, 19:15:15
	 * @param item
	 * @return void
	 */
	public void addItem(DisplayProductItem item) {
		items.add(item);
		notifyDataSetChanged();
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return items.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	/**
	 * Obtiene un ArrayList con los valores del ListView 
	 * @author Yamel Senih 15/05/2012, 04:35:47
	 * @return
	 * @return ArrayList<String>
	 */
	public ArrayList<DisplayProductItem> getItems(){
		return items;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.il_product, null);
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				loadLastSO(items.get(position).getM_Product_ID(), v);
			}
		});
		
		final EditTextHolder eth_QtySelected = new EditTextHolder();
		eth_QtySelected.setEditText((EditText)convertView.findViewById(R.id.et_QtySelected));
		
		final EditTextHolder eth_QtyOnStock = new EditTextHolder();
		eth_QtyOnStock.setEditText((EditText)convertView.findViewById(R.id.et_QtyInStock));
		
		final EditTextHolder eth_QtyOnRack = new EditTextHolder();
		eth_QtyOnRack.setEditText((EditText)convertView.findViewById(R.id.et_QtyOnRack));
		
		final EditTextHolder eth_QtyReturn = new EditTextHolder();
		eth_QtyReturn.setEditText((EditText)convertView.findViewById(R.id.et_QtyReturn));
		
		TextView tv_Name = (TextView)convertView.findViewById(R.id.tv_Name);
		TextView tv_Description = (TextView)convertView.findViewById(R.id.tv_Description);
		TextView tv_Price = (TextView)convertView.findViewById(R.id.tv_Price);
		TextView tv_QtyReturn = (TextView)convertView.findViewById(R.id.tv_QtyReturn);
		
		LinearLayout ll_Ordered = (LinearLayout)convertView.findViewById(R.id.ll_Ordered);
		LinearLayout ll_Returned = (LinearLayout)convertView.findViewById(R.id.ll_Returned);
		LinearLayout ll_Rack = (LinearLayout)convertView.findViewById(R.id.ll_Rack);
		LinearLayout ll_Stock = (LinearLayout)convertView.findViewById(R.id.ll_Stock);
		//	
		if(listType.equals("CI")){	//	Customer Inventory
			ll_Ordered.setVisibility(View.GONE);
			ll_Returned.setVisibility(View.GONE);
			ll_Rack.setVisibility(View.VISIBLE);
			ll_Stock.setVisibility(View.VISIBLE);
		} else if(listType.equals("SO")){	//	Sales Order
			ll_Ordered.setVisibility(View.VISIBLE);
			ll_Returned.setVisibility(View.GONE);
			ll_Rack.setVisibility(View.VISIBLE);
			ll_Stock.setVisibility(View.VISIBLE);
		} else if(listType.equals("RM")){	//	Return Material
			ll_Ordered.setVisibility(View.GONE);
			ll_Returned.setVisibility(View.VISIBLE);
			ll_Rack.setVisibility(View.GONE);
			ll_Stock.setVisibility(View.GONE);
		}
		
		eth_QtySelected.getEditText().addTextChangedListener(new TextWatcher() {
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				items.get(position).setQtySelected(eth_QtySelected.getString());
			}
		});
		
		eth_QtyOnStock.getEditText().addTextChangedListener(new TextWatcher() {
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				items.get(position).setQtyOnStock(eth_QtyOnStock.getString());
			}
		});
		
		eth_QtyOnRack.getEditText().addTextChangedListener(new TextWatcher() {
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				items.get(position).setQtyOnRack(eth_QtyOnRack.getString());
			}
		});
		
		eth_QtyReturn.getEditText().addTextChangedListener(new TextWatcher() {
						
			@Override
			public void afterTextChanged(Editable s) {
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String QtyReturn = eth_QtyReturn.getString();
				BigDecimal diff = items.get(position).validExced(QtyReturn);
				if(diff.compareTo(Env.ZERO) < 0){
					Msg.alertMsg(ctx, ctx.getResources().getString(R.string.msg_QtyOverMA), 
							ctx.getResources().getString(R.string.msg_QtyAllowed) + " = " + 
									items.get(position).getQtyMax() + "\n" + 
							ctx.getResources().getString(R.string.msg_Diff) + " = " + diff + "\n");
					items.get(position).setQtyReturn(null);
					eth_QtyReturn.setString(items.get(position).getQtyReturn());
				} else {
					items.get(position).setQtyReturn(eth_QtyReturn.getString());
					
				}
			}
		});
		
		DisplayProductItem item = (DisplayProductItem) getItem(position);
		
		eth_QtySelected.setString(items.get(position).getQtySelected());
		convertView.setTag(eth_QtySelected);
		
		eth_QtyOnStock.setString(items.get(position).getQtyOnStock());
		convertView.setTag(eth_QtyOnStock);
		
		eth_QtyOnRack.setString(items.get(position).getQtyOnRack());
		convertView.setTag(eth_QtyOnRack);
		
		eth_QtyReturn.setString(items.get(position).getQtyReturn());
		convertView.setTag(eth_QtyReturn);
		
		tv_Name.setText(item.getValue());
		tv_Description.setText(item.getName());
		tv_Price.setText(item.getPrice());
		tv_QtyReturn.setText(item.getQtyMax());
		return convertView;
	}

    /**
     * Muestra las ultimas tres Ventas
     * @author Yamel Senih 28/08/2012, 02:55:24
     * @return void
     */
    public void loadLastSO(int m_M_Product_ID, View v){
    	String m_QtyDelivered = ctx.getResources().getString(R.string.XX_QtyDelivered);
    	String m_QtyReturn = ctx.getResources().getString(R.string.XX_QtyReturn);
    	DB con = new DB(ctx);
		con.openDB(DB.READ_ONLY);
		
		String sql = new String("SELECT " +
				"io.M_InOut_ID, " +
				"io.DocumentNo, " +
				"strftime('%d/%m/%Y', io.MovementDate), " +
				"iol.MovementQty, " +
				"iol.QtyReturn " +
				"FROM M_InOut io " +
				"INNER JOIN M_InOutLine iol ON(iol.M_InOut_ID = io.M_InOut_ID) " +
				"WHERE iol.M_Product_ID = " + m_M_Product_ID + " " +
				"AND io.C_BPartner_Location_ID = " + m_C_BPartner_Location_ID + " " +  
				"ORDER BY io.MovementDate " +
				"LIMIT 3");
		Cursor rs = con.querySQL(sql, null);
		mQAct.clear();
		if(rs.moveToFirst()){
			do {
				mQAct.addActionItem(new ActionItem(
						rs.getInt(0), 
						"<" + rs.getString(1) + ">" + 
						"\n" + 
						"<" + rs.getString(2) + ">" + 
						"\n" + 
						m_QtyDelivered + "=<" + rs.getString(3) + ">" + 
						"\n" + 
						m_QtyReturn + "=<" + rs.getString(4) + ">"));
			} while(rs.moveToNext());
		}
		con.closeDB(rs);
    	mQAct.show(v);
    }

}
