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
 * Copyright (C) 2012-2013 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.appd.util;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.appd.base.Env;
import org.appd.base.R;
import org.appd.view.MV_AddCollect;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author Yamel Senih
 *
 */
public class AdapterOpenItem extends BaseAdapter {

	private ArrayList<DisplayOpenItem> items = new ArrayList<DisplayOpenItem>();
	private LayoutInflater inflater;
	private Context ctx;
	private TextView summary;
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 22/10/2012, 15:38:45
	 * @param ctx
	 */
	public AdapterOpenItem(Context ctx){
		inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		items = new ArrayList<DisplayOpenItem>();
		this.ctx = ctx;
		notifyDataSetChanged();
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 22/10/2012, 15:38:48
	 * @param ctx
	 * @param summary
	 */
	public AdapterOpenItem(Context ctx, TextView summary){
		inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		items = new ArrayList<DisplayOpenItem>();
		this.ctx = ctx;
		this.summary = summary;
		notifyDataSetChanged();
	}
	
	/**
	 * 
	 * @author Yamel Senih 19/10/2012, 16:43:16
	 * @param item
	 * @return void
	 */
	public void addItem(DisplayOpenItem item) {
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
	 * 
	 * @author Yamel Senih 19/10/2012, 16:43:07
	 * @return
	 * @return ArrayList<DisplayOpenItem>
	 */
	public ArrayList<DisplayOpenItem> getItems(){
		return items;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.il_openitem, null);
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//loadLastSO(items.get(position).getM_Product_ID(), v);
			}
		});
		
		final CheckBoxHolder chh_Selection = new CheckBoxHolder();
		chh_Selection.setCheckBox((CheckBox)convertView.findViewById(R.id.ch_Selection));
		
		final EditTextHolder eth_Amt = new EditTextHolder();
		eth_Amt.setEditText((EditText)convertView.findViewById(R.id.et_Amt));
		
		TextView tv_DocInvoice = (TextView)convertView.findViewById(R.id.tv_DocInvoice);
		TextView tv_GrandTotal = (TextView)convertView.findViewById(R.id.tv_GrandTotal);
		final TextView tv_OpenAmt = (TextView)convertView.findViewById(R.id.tv_OpenAmt);
		//LinearLayout ll_lb_Amt = (LinearLayout)convertView.findViewById(R.id.ll_lb_Amt);
		//ll_lb_Amt.setEnabled(false);
		
		eth_Amt.getEditText().setEnabled(false);
		eth_Amt.getEditText().setFocusable(false);
		eth_Amt.getEditText().setFocusableInTouchMode(false);
		eth_Amt.getEditText().setClickable(false);
		
		chh_Selection.getCheckBox().setOnCheckedChangeListener(new OnCheckedChangeListener() { 
		    @Override 
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 
		    	items.get(position).setSelected(buttonView.isChecked());
		    	eth_Amt.getEditText().setEnabled(buttonView.isChecked());
		    	eth_Amt.getEditText().setFocusable(buttonView.isChecked());
				eth_Amt.getEditText().setFocusableInTouchMode(buttonView.isChecked());
				eth_Amt.getEditText().setClickable(buttonView.isChecked());
				//	
				calcalateSummary();
		    } 
		});
		
		
		eth_Amt.getEditText().addTextChangedListener(new TextWatcher() {
			
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
				String m_OpenAmt = eth_Amt.getString();
				BigDecimal diff = items.get(position).validExced(m_OpenAmt);
				if(diff.compareTo(Env.ZERO) < 0){
					Msg.alertMsg(ctx, ctx.getResources().getString(R.string.msg_AmtOverMA), 
							ctx.getResources().getString(R.string.msg_AmtAllowed) + " = " + 
									items.get(position).getOpenAmt() + "\n" + 
							ctx.getResources().getString(R.string.msg_Diff) + " = " + diff + "\n");
					eth_Amt.setString(items.get(position).getOpenAmt());
				} else
					items.get(position).setAmount(eth_Amt.getString());
				//	
				calcalateSummary();
			}
		});
		
		chh_Selection.getCheckBox().setChecked(items.get(position).isSelected());
		tv_DocInvoice.setText(items.get(position).getDocInvoice());
		tv_GrandTotal.setText(items.get(position).getGrandTotal());
		tv_OpenAmt.setText(items.get(position).getOpenAmt());
		eth_Amt.setString(items.get(position).getOpenAmt());
		
		return convertView;
	}
	
	/**
	 * Calcula el total y actualiza el campo resumen
	 * @author Yamel Senih 22/10/2012, 15:40:59
	 * @return void
	 */
	private void calcalateSummary(){
		BigDecimal amt = Env.ZERO;
		BigDecimal tmpAmt = Env.ZERO;
		for(DisplayOpenItem item : items){
			if(item.isSelected()){
				if(item.getAmount() != null
						&& item.getAmount().length() > 0){
					tmpAmt = new BigDecimal(item.getAmount());
					amt = amt.add(tmpAmt);
				}
			}
		}
		//
		if(summary != null){
			summary.setText(amt.toString());
		}
		MV_AddCollect cl = (MV_AddCollect)ctx;
		cl.updateDiff();
	}

}
