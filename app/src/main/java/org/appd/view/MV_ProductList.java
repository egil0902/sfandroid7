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
package org.appd.view;

import java.util.ArrayList;

import org.appd.base.DB;
import org.appd.base.Env;
import org.appd.base.R;
import org.appd.interfaces.I_CancelOk;
import org.appd.util.AdapterProductItem;
import org.appd.util.DisplayProductItem;
import org.appd.util.DisplaySpinner;
import org.appd.util.Msg;
import org.appd.view.custom.Cust_Spinner;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

/**
 * @author Yamel Senih
 *
 */
public class MV_ProductList extends Activity implements I_CancelOk {
	
	private AdapterProductItem adapter;
	private ListView productList;
	private Cust_Spinner sp_M_Product_Category_ID;
	private EditText et_Name;
	private ImageButton iBut_Search;
	private ImageButton iBut_Cancel;
	private ImageButton iBut_Ok;
	private ArrayList<DisplayProductItem> currentProducts;
	private String listType = null;
	private Intent intentResult = null;
	private OnClickListener event;
	
    /** Called when the activity is first created. */
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	super.setContentView(R.layout.lv_products);
    	
    	Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			currentProducts = bundle.getParcelableArrayList("CurrentProducts");
			listType = bundle.getString("ListType");
		}
    	
    	productList = (ListView) findViewById(R.id.ls_Products);
    	sp_M_Product_Category_ID = (Cust_Spinner) findViewById(R.id.sp_M_Product_Category_ID);
    	sp_M_Product_Category_ID.setTableName("M_Product_Category");
    	sp_M_Product_Category_ID.setIdentifierName("Value || ' - ' || Name");
    	sp_M_Product_Category_ID.setFirstSpace(true);
    	sp_M_Product_Category_ID.load(true);
    	
    	et_Name = (EditText) findViewById(R.id.et_Name);
    	iBut_Search = (ImageButton) findViewById(R.id.iBut_Search);
    	iBut_Cancel = (ImageButton) findViewById(R.id.butt_Cancel);
    	iBut_Ok = (ImageButton) findViewById(R.id.butt_Ok);
    	
    	iBut_Search.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				loadProducts();
			}
		});
    	
    	iBut_Cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				cancelOption();
			}
		});
    	
    	
    	iBut_Ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				okOption();
			}
		});
    	
    	
    	
    	//	Event Click
    	sp_M_Product_Category_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> a, View v,
					int position, long i) {
				loadProducts();
			}
				

			@Override
			public void onNothingSelected(AdapterView<?> a) {
				
			}
    		
    	});
    	
    	//	Load Records
    	loadProducts();
    	
	}
	
	/**
	 * Se finaliza la actividad
	 * @author Yamel Senih 15/05/2012, 04:27:55
	 * @return void
	 */
	private void cancelOption(){
		Builder ask = Msg.confirmMsg(this, getResources().getString(R.string.msg_UndoSelection));
    	String msg_Acept = this.getResources().getString(R.string.msg_Yes);
    	ask.setPositiveButton(msg_Acept, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				finish();
			}
		});
    	ask.show();
	}
	
	/**
	 * Se retorna el arreglo con los productos y se finaliza la actividad
	 * @author Yamel Senih 15/05/2012, 04:28:26
	 * @return void
	 */
	private void okOption(){
		ArrayList<DisplayProductItem> data = adapter.getItems();
		data.toArray();
		intentResult = getIntent();
		intentResult.putParcelableArrayListExtra("Products", data);
		setResult(RESULT_OK, intentResult);
		finish();
	}
	
	
	
	
	/**
	 * Carga loa Productos
	 * @author Yamel Senih 14/05/2012, 03:51:30
	 * @return
	 * @return boolean
	 */
	private boolean loadProducts(){
		DB con = new DB(this);
		con.openDB(DB.READ_ONLY);
		
		String whereClause = null;
		String whereClauseFilter = new String();
		DisplaySpinner ds = (DisplaySpinner)sp_M_Product_Category_ID.getSelectedItem();
		String productName = et_Name.getText().toString();
		
		whereClause = new String("WHERE plv.M_PriceList_ID = " + 
					Env.getContextAsInt(this, "#M_PriceList_ID") + 
					" AND DATE(plv.ValidFrom) <= DATE('" + Env.getContext(this, "#DateP") + "')");
		//Msg.toastMsg(this, Env.getContext(this, "#M_PriceList_ID"));
		String [] param = null;
		
		if(ds.getID() != 0)
			whereClauseFilter += " AND p.M_Product_Category_ID = " + ds.getID();
		
		if(productName != null 
				&& productName.length() > 0){
			whereClauseFilter += " AND p.Name LIKE ? ";
			param = new String[]{"%" + productName.trim() + "%"};
		}
		
		whereClause += whereClauseFilter;
		
		String sql = new String(); 
		if(listType.equals("CI")
				|| listType.equals("SO")){	//	CustomerInventory Or Sales Order
			sql = new String("SELECT " +
				"p.M_Product_ID, " +			//	Product ID
				"p.Value, " +					//	Value
				"p.Name, " +					//	Name
				"pp.PriceList, " +				//	Price List
				"999999999999, " +				//	Qty Max
				"p.M_Product_Category_ID, " +	//	Product Category
				"p.C_TaxCategory_ID, " +		//	Tax Category
				"p.C_UOM_ID, " + 				//	Unit of Measure
				"MAX(plv.ValidFrom) " + 		// 	Date
				"FROM M_PriceList_Version plv " +
				"INNER JOIN M_ProductPrice pp ON(pp.M_PriceList_Version_ID = plv.M_PriceList_Version_ID) " +
				"INNER JOIN M_Product p ON(p.M_Product_ID = pp.M_Product_ID) "); 
			if(whereClause != null)
				sql += whereClause;
			//	Group By
			sql += " GROUP BY p.M_Product_ID, p.Value, p.Name ";
			//	Order By
			sql += " ORDER BY 2 ";	
		} else if(listType.equals("RM")){	//	Return Material
			sql = new String("SELECT " +
				"p.M_Product_ID, " +			//	Product ID
				"p.Value, " +					//	Value
				"p.Name, " +					//	Name
				"iol.Amt, " +					//	Amt
				"iol.QtyAvailable, " + 			//	QtyAvailable
				"iol.M_InOutLine_ID, " +		//	Product Category
				"0, " +							//	Tax Category
				"0, " + 						//	Unit of Measure
				"io.MovementDate " + 			// 	Date
				"FROM M_InOut io " +
				"INNER JOIN M_InOutLine iol ON(iol.M_InOut_ID = io.M_InOut_ID)" +
				"INNER JOIN M_Product p ON(p.M_Product_ID = iol.M_Product_ID) " +
				"WHERE io.M_InOut_ID = " + Env.getContextAsInt(this, "#InOut_ID") + " "); 
			if(whereClauseFilter != null
					&& whereClauseFilter.length() > 0)
				sql += whereClauseFilter;
			//	Order By
			sql += " ORDER BY 2 ";
		}
		
		//Limit
		int limit = Env.getContextAsInt(this, "#LimitQuery");
		if(limit > 0)
			sql += "LIMIT " + limit;
		
		Cursor rs = con.querySQL(sql, param);
		
		//Log.i("Sql Product", sql);
		
		adapter = new AdapterProductItem(this, listType, event);
		//	Se Notifica el Cambio al GUI
		//adapter.notifyDataSetChanged();
		
		if(rs.moveToFirst()){
			String validFrom = rs.getString(8);
			setTitle(getResources().getString(R.string.ValidFrom) + 
					": " +
					Env.getDateFormatString(validFrom, "yyyy-MM-dd hh:mm:ss", "dd/MM/yyyy"));
			do {
				adapter.addItem(loadCurrentProductQty(
						rs.getInt(0),		//	Product ID
						rs.getString(1),	//	Value
						rs.getString(2),	//	Name
						rs.getString(3),	//	PriceList
						rs.getString(4),	//	Qty Max
						rs.getInt(5),		//	Product Category
						rs.getInt(6),		//	Tax Category
						rs.getInt(7)));		//	Unit of Measure
			} while(rs.moveToNext());
		}
		productList.setAdapter(adapter);
		con.closeDB(rs);
    	return true;
    }
	
	/**
	 * Obtiene una Instancia del Item del Producto,
	 * si el producto ya existe en la Linea de la Orden 
	 * le coloca automaticamente la cantidad
	 * @author Yamel Senih 23/05/2012, 03:06:24
	 * @param m_M_Product_ID
	 * @param value
	 * @param name
	 * @param priceList
	 * @param m_M_Product_Category_ID
	 * @param m_C_TaxCategory_ID
	 * @param m_C_UOM_ID
	 * @param m_QtyMax
	 * @return
	 * @return DisplayProductItem
	 */
	private DisplayProductItem loadCurrentProductQty(int m_M_Product_ID, String value, String name, 
			String priceList, String m_QtyMax, int m_M_Product_Category_ID, 
			int m_C_TaxCategory_ID, int m_C_UOM_ID){
		
		if(currentProducts != null){
			for (DisplayProductItem pItem : currentProducts) {
				if(pItem.getM_Product_ID() == m_M_Product_ID){
					return new DisplayProductItem(m_M_Product_ID, pItem.getForeign_ID(), pItem.getForeign2_ID(), pItem.getForeign3_ID(), 
							value, name, priceList, m_QtyMax, pItem.getQtySelected(), pItem.getQtyOnStock(), pItem.getQtyOnRack(), pItem.getQtyReturn(),
							m_M_Product_Category_ID, m_C_TaxCategory_ID, m_C_UOM_ID);
				}
			}
		}
		
		return new DisplayProductItem(m_M_Product_ID, 0, 0, 0, 
				value, name, priceList, m_QtyMax, null, null, null, null, 
				m_M_Product_Category_ID, m_C_TaxCategory_ID, m_C_UOM_ID);
	}
	
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if ((keyCode == KeyEvent.KEYCODE_BACK)) {
    		cancelOption();
    		return true;
    	}
    	return super.onKeyDown(keyCode, event);
    }

	/* (non-Javadoc)
	 * @see org.appd.interfaces.I_CancelOk#aceptAction()
	 */
	@Override
	public boolean aceptAction() {
		okOption();
		return true;
	}

	/* (non-Javadoc)
	 * @see org.appd.interfaces.I_CancelOk#cancelAction()
	 */
	@Override
	public boolean cancelAction() {
		cancelOption();
		return false;
	}

	/* (non-Javadoc)
	 * @see org.appd.interfaces.I_CancelOk#getParam()
	 */
	@Override
	public Intent getParam() {
		return intentResult;
	}
}
