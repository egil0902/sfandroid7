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
import org.appd.model.MBCustomerInventory;
import org.appd.model.MBCustomerInventoryLine;
import org.appd.model.MBOrder;
import org.appd.model.MBOrderLine;
import org.appd.model.MBPartner;
import org.appd.model.MBPlanningVisit;
import org.appd.model.MP;
import org.appd.util.DisplayMenuItem;
import org.appd.util.DisplayProductItem;
import org.appd.util.Msg;
import org.appd.view.custom.Cust_DateBox;
import org.appd.view.custom.Cust_Spinner;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author Yamel Senih
 * Modelo
 *
 */
public class MV_CustomerInventory extends MVActivity {
	
	private Cust_DateBox cd_DateDoc;
    private Cust_Spinner sp_C_BPartner_ID;
    private Cust_Spinner sp_C_BPartner_Location_ID;
    private EditText et_Description;
    private CheckBox ch_IsActive;
    private Button butt_OrderGenerate;
    //private boolean suggested = false;
    
    //	Default Values
    private int m_C_BPartner_ID = 0;
    private int m_C_BPartner_Location_ID = 0;
	
    private DisplayMenuItem param;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        super.setContentView(R.layout.v_customer_inventory);
        //	Set Summary Tab
        setIsSummary(true);
        
        Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			param = (DisplayMenuItem)bundle.getParcelable("Param");
		} 
		if(param == null)
			throw new IllegalArgumentException ("No Param");
		
		//	Carga los valor por Defecto
		loadDefaultValues();
		
		sp_C_BPartner_ID = (Cust_Spinner) findViewById(R.id.sp_C_BPartner_ID);
		
		sp_C_BPartner_ID.setTableName("C_BPartner");
		sp_C_BPartner_ID.setIdentifierName("TaxID || ' - ' || Name");
		//sp_C_BPartner_ID.setIdentifierName2("Name");
		sp_C_BPartner_ID.setWhereClause("C_BPartner.C_BPartner_ID = " + m_C_BPartner_ID);		
		
		sp_C_BPartner_Location_ID = (Cust_Spinner) findViewById(R.id.sp_C_BPartner_Location_ID);
		
		sp_C_BPartner_Location_ID.setTableName("C_BPartner_Location");
		sp_C_BPartner_Location_ID.setIdentifierName("Name");
		sp_C_BPartner_Location_ID.setWhereClause("C_BPartner_Location.C_BPartner_Location_ID = " + m_C_BPartner_Location_ID);
		
		cd_DateDoc = (Cust_DateBox) findViewById(R.id.cd_DateDoc);
		et_Description = (EditText) findViewById(R.id.et_Description);
		ch_IsActive = (CheckBox) findViewById(R.id.ch_IsActive);
		butt_OrderGenerate = (Button) findViewById(R.id.butt_OrderGenerate);
		butt_OrderGenerate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				generateOrder();
			}
		});
		addView((TextView) findViewById(R.id.tv_DateDoc), cd_DateDoc, "DateDoc", Env.getContext(this, "#Date"), false);
		addView((TextView) findViewById(R.id.tv_C_BPartner_ID), sp_C_BPartner_ID, "C_BPartner_ID", false);
		addView((TextView) findViewById(R.id.tv_C_BPartner_Location_ID), sp_C_BPartner_Location_ID, "C_BPartner_Location_ID", false);
		addView((TextView) findViewById(R.id.tv_Description), et_Description, "Description", false);
		addView((TextView) findViewById(R.id.tv_IsActive), ch_IsActive, "IsActive", "Y", false);
    }
    
    /**
     * Carga loas Filtros
     * @author Yamel Senih 12/05/2012, 01:41:14
     * @return void
     */
    private void loadDefaultValues(){
    	if(getMP().isNew()){
    		String sql = new String("SELECT cp.C_BPartner_ID, pv.C_BPartner_Location_ID, cp.M_PriceList_ID " +
    				"FROM XX_MB_PlanningVisit pv " +
    				"INNER JOIN C_BPartner cp ON(cp.C_BPartner_ID = pv.C_BPartner_ID) " +
        			"WHERE pv.XX_MB_PlanningVisit_ID = " + Env.getContextAsInt(this, "#XX_MB_PlanningVisit_ID"));
    		loadConnection();
    		Cursor rs = con.querySQL(sql, null);
    		if(rs.moveToFirst()){
    			m_C_BPartner_ID = rs.getInt(0);
    			m_C_BPartner_Location_ID = rs.getInt(1);
    			Env.setContext(this, "#M_PriceList_ID", rs.getInt(2));
    		}
    	}
    }
    
    /**
     * Carga la Orden de Venta
     * @author Yamel Senih 23/08/2012, 01:38:27
     * @param m_cin
     * @return void
     */
    private void loadOrder(MBCustomerInventory m_cin){
    	
    	int id = Env.getContextAsInt(this, "#C_BPartner_ID");
    	MBPartner m_bpartner = new MBPartner(this, id);

    	DisplayMenuItem item = DisplayMenuItem.createFromMenu(param);

    	item.setName(getResources().getString(R.string.C_Order_ID) + 
    			" \"" + m_bpartner.get_Value("Name") + "\"");
    	
    	item.setTableName("C_Order");
    	
    	item.setAD_Table_ID(0);
    	
    	item.setWhereClause("C_Order.C_BPartner_Location_ID = " + m_cin.get_ValueAsInt("C_BPartner_Location_ID"));
    	//	Set Record ID
		Env.setTabRecord_ID(this, "C_Order", m_cin.get_ValueAsInt("C_Order_ID"));
		
		//	Param
		Bundle bundle = new Bundle();
		bundle.putParcelable("Param", item);
		//	Load Intent
		Intent intent = new Intent(getApplicationContext(), MT_CustomerOrder.class);
		intent.putExtras(bundle);
		//	Start
		startActivity(intent);
		
    }
    
    /**
     * Genera un pedido a partir de la toma de inventario
     * @author Yamel Senih 22/08/2012, 16:45:41
     * @return
     */
    private void generateOrder(){
    	final MBCustomerInventory m_CustomerInventory = (MBCustomerInventory) getMP();
    	final int m_XX_MB_CustomerInventory_ID = m_CustomerInventory.getID();
		final int m_C_BPartner_Location_ID = m_CustomerInventory.get_ValueAsInt("C_BPartner_Location_ID");
    	if(m_XX_MB_CustomerInventory_ID != 0){
    		if(m_CustomerInventory.get_ValueAsInt("C_Order_ID") != 0) {
    			loadOrder(m_CustomerInventory);
    		} else {
        		
    			Builder ask = Msg.confirmMsg(this, getResources().getString(R.string.msg_AskLoadQtySuggested));
    			String msg_Yes = this.getResources().getString(R.string.msg_Yes);
    			String msg_No = this.getResources().getString(R.string.msg_No);
    			
    			//	Pedido Sugerido
    			ask.setPositiveButton(msg_Yes, new DialogInterface.OnClickListener() {
    				public void onClick(DialogInterface dialog, int which) {
    					ArrayList<DisplayProductItem> product = MBCustomerInventoryLine
    	        				.getCurrentProductsSuggested(getCtx(), m_XX_MB_CustomerInventory_ID, m_C_BPartner_Location_ID);
    					loadProductList(product);
    					//suggested = true;
    				}
    			});
    			//	No Sugerido
    			ask.setNegativeButton(msg_No, new DialogInterface.OnClickListener() {
    				public void onClick(DialogInterface dialog, int which) {
    					ArrayList<DisplayProductItem> product = MBCustomerInventoryLine
    	        				.getCurrentProducts(getCtx(), m_XX_MB_CustomerInventory_ID);
    					loadProductList(product);
    					//suggested = false;
    				}
    			});
    		    	
    			ask.show();
    		}
    	} else {
    		Msg.alertMsg(getCtx(), getResources().getString(R.string.msg_Error), 
    				getResources().getString(R.string.msg_DocNoSaved));
    	}
    }
    
    /**
     * Carga el Listado de Productos
     * @author Yamel Senih 28/08/2012, 01:13:38
     * @param product
     * @return void
     */
    private void loadProductList(ArrayList<DisplayProductItem> product){
    	if(product != null){
    		Bundle bundle = new Bundle();
        	bundle.putParcelableArrayList("CurrentProducts", product);
        	bundle.putString("ListType", "SO");
        	//	
        	Intent intent = new Intent(this, MV_ProductList.class);
        	intent.putExtras(bundle);
        	//	
    		startActivityForResult(intent, 0);
    	} else {
			Msg.alertMsg(getCtx(), getResources().getString(R.string.msg_ValidError), 
					getResources().getString(R.string.msg_DocNoLinesFound));
		}
    }
    
    /**
     * Configura el boton de generar pedido
     * @author Yamel Senih 23/08/2012, 01:43:10
     * @return void
     */
    private void configButton(){
    	MBCustomerInventory m_CustomerInventory = (MBCustomerInventory) getMP();
        int m_C_Order_ID = m_CustomerInventory.get_ValueAsInt("C_Order_ID");
        Env.setContext(this, "#C_Order_ID", m_C_Order_ID);
        if(m_C_Order_ID != 0){
        	butt_OrderGenerate.setText(getResources().getString(R.string.C_Order_ID));
        	butt_OrderGenerate.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.sales_order_m),null, null, null);
        } else {
        	butt_OrderGenerate.setText(getResources().getString(R.string.msg_OrderGenerateFromInventory));
        	butt_OrderGenerate.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.process_m),null, null, null);
        	butt_OrderGenerate.setEnabled(param.isReadWrite());
        }
    }
    
    @Override
    public boolean save(){
    	boolean ok = super.save();
    	configButton();
    	return ok;
    }
    
    @Override
    public boolean modifyRecord(){
		super.modifyRecord();
		return true;
	}
    
    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
    }
    @Override
    public void refreshFrontEnd(){
    	super.refreshFrontEnd();
    	configButton();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }

	@Override
	public MP getCustomMP() {
		return new MBCustomerInventory(getCtx(), 0);
	}

	@Override
	public MP getCustomMP(int id) {
		return new MBCustomerInventory(getCtx(), id);
	}

	@Override
	public MP getCustomMP(Cursor rs) {
		return new MBCustomerInventory(getCtx(), rs);
	}

	/* (non-Javadoc)
	 * @see org.appd.view.MVActivity#getActivityName()
	 */
	@Override
	protected String getActivityName() {
		return "XX_MB_CustomerInventory";
	}
	
	protected DisplayMenuItem getMenuItem(){
		DisplayMenuItem item = super.getMenuItem();
    	item.setWhereClause(param.getWhereClause());
    	return item;
    }
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, final Intent data){
	    if (resultCode == RESULT_OK) {
	    	if(data != null){
	    		Builder ask = Msg.confirmMsg(this, getResources().getString(R.string.msg_AskOrderGenerateFromInventory));
				String msg_Acept = this.getResources().getString(R.string.msg_Yes);
		    	ask.setPositiveButton(msg_Acept, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						orderGenerate(data);
					}
				});
		    	ask.show();
	    	}
	    }
	}
	
	/**
	 * Genera el pedido desde el Inventario
	 * @author Yamel Senih 23/08/2012, 11:23:15
	 * @param data
	 * @return void
	 */
	private void orderGenerate(Intent data){
		DB con = new DB(this);
		con.openDB(DB.READ_WRITE);
		//con.beginTransaction();
		
		try {
			MBCustomerInventory m_CustomerInventory = (MBCustomerInventory) getMP();
    		ArrayList<DisplayProductItem> products = data.getParcelableArrayListExtra("Products");
    		
    		MBPlanningVisit pv = new MBPlanningVisit(this, con, Env.getContextAsInt(this, "#XX_MB_PlanningVisit_ID"));
    		
    		MBOrder m_Order = MBOrder.createFromInventory(getCtx(), con, m_CustomerInventory, pv);
    		
    		m_Order.saveEx();
    		
    		m_CustomerInventory.set_Value("C_Order_ID", m_Order.getID());
    		String description = (String) m_CustomerInventory.get_Value("Description");
    		if(description != null
    				&& description.length() != 0){
    			description += " " + Env.getContextDateFormat(this, "#Date", "yyyy-MM-dd hh:mm:ss", "dd/MM/yyyy") + 
	    				" - " + m_Order.get_Value("DocumentNo"); 
    		} else {
    			description = Env.getContextDateFormat(this, "#Date", "yyyy-MM-dd hh:mm:ss", "dd/MM/yyyy") + 
				" - " + m_Order.get_Value("DocumentNo");
    		}
    		m_CustomerInventory.set_Value("Description", description);
    		m_CustomerInventory.saveEx();
    		
    		String msg = MBOrderLine.createLinesFromProducts(this, con, products, m_Order);
    		
    		if(msg != null)
    			throw new Exception(msg);
    		//	SuccessFul
    		refreshFrontEnd();
    		
    		Msg.toastMsg(this, getResources().getString(R.string.msg_OrderGeneratedSuccessFul));
    		
		} catch(Exception e) {
			Msg.alertMsg(this, getResources().getString(R.string.msg_ProcessError), e.getMessage());
			e.printStackTrace();
		} finally {
			con.closeDB(null);
		}
	}
	
}
