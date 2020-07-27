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

import org.appd.base.Env;
import org.appd.base.R;
import org.appd.model.MBOrder;
import org.appd.model.MP;
import org.appd.model.MSequence;
import org.appd.util.DisplayMenuItem;
import org.appd.util.DisplaySpinner;
import org.appd.util.Msg;
import org.appd.util.contribution.QuickAction;
import org.appd.view.custom.Cust_ButtonDocAction;
import org.appd.view.custom.Cust_DateBox;
import org.appd.view.custom.Cust_Spinner;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author Yamel Senih
 * Modelo
 *
 */
public class MV_CustomerOrder extends MVActivity {
	
	private EditText et_DocumentNo;
    private Cust_Spinner sp_C_DocTypeTarget_ID;
    private Cust_DateBox cd_DateOrdered;
    private Cust_DateBox cd_DatePromised;
    private Cust_Spinner sp_C_BPartner_ID;
    private Cust_Spinner sp_M_PriceList_ID;
    private Cust_Spinner sp_Bill_Location_ID;
    private Cust_Spinner sp_C_BPartner_Location_ID;
    private EditText et_Description;
    private Cust_ButtonDocAction cds_DocStatus;
    private EditText et_TotalLines;
    private EditText et_GrandTotal;
    private EditText et_POReference;
    private boolean loaded = false;
    
    //	Default Values
    private int m_C_BPartner_ID = 0;
	private int m_C_BPartner_Location_ID = 0;
	private int m_Bill_Location_ID = 0;
	private int m_M_PriceList_ID = 0;
	private int m_C_PaymentTerm_ID = 0;
	private String m_PaymentRule = null;
    
	//private Context ctx;
	private DisplayMenuItem param;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        super.setContentView(R.layout.v_customer_order);
        //	Set Summary Tab
        setIsSummary(true);
        
        
        Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			param = (DisplayMenuItem)bundle.getParcelable("Param");
		} 
		if(param == null)
			throw new IllegalArgumentException ("No Param");
		
		//setManConnection(true);
		
		loadDefaultValues();
		//	Instance of Fields
		et_DocumentNo = (EditText) findViewById(R.id.et_DocumentNo);
		sp_C_DocTypeTarget_ID = (Cust_Spinner) findViewById(R.id.sp_C_DocTypeTarget_ID);
		sp_C_DocTypeTarget_ID.setTableName("C_DocType");
		sp_C_DocTypeTarget_ID.setIdentifierName("Name");
		sp_C_DocTypeTarget_ID.setWhereClause("C_DocType.DocBaseType IN('SOO') AND C_DocType.DocSubTypeSO IN('SO')");
		
		sp_C_DocTypeTarget_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> a, View v,
					int position, long i) {
				DisplaySpinner m_C_DocType = ((DisplaySpinner) sp_C_DocTypeTarget_ID.getItemAtPosition(position));
				loadSequence(m_C_DocType);
			}

			@Override
			public void onNothingSelected(AdapterView<?> a) {
				
			}
    		
    	});
		
		//Log.i("C_BPartner", "" + m_C_BPartner_ID);
		
		cd_DateOrdered = (Cust_DateBox) findViewById(R.id.cd_DateOrdered);
		cd_DatePromised = (Cust_DateBox) findViewById(R.id.cd_DatePromised);
		sp_C_BPartner_ID = (Cust_Spinner) findViewById(R.id.sp_C_BPartner_ID);
		sp_C_BPartner_ID.setTableName("C_BPartner");
		sp_C_BPartner_ID.setIdentifierName("TaxID || ' - ' || Name");
		//sp_C_BPartner_ID.setIdentifierName2("Name");
		
		
		sp_M_PriceList_ID = (Cust_Spinner) findViewById(R.id.sp_M_PriceList_ID);
		sp_M_PriceList_ID.setTableName("M_PriceList");
		sp_M_PriceList_ID.setIdentifierName("Name");
		
		//Msg.toastMsg(this, "M_PriceList.M_PriceList_ID = " + m_M_PriceList_ID);
		sp_Bill_Location_ID = (Cust_Spinner) findViewById(R.id.sp_Bill_Location_ID);
		sp_Bill_Location_ID.setTableName("C_BPartner_Location");
		sp_Bill_Location_ID.setIdentifierName("Name");
		
		sp_C_BPartner_Location_ID = (Cust_Spinner) findViewById(R.id.sp_C_BPartner_Location_ID);
		sp_C_BPartner_Location_ID.setTableName("C_BPartner_Location");
		sp_C_BPartner_Location_ID.setIdentifierName("Name");
		
		
		if(loaded){
			sp_C_BPartner_ID.setWhereClause("C_BPartner.C_BPartner_ID = " + m_C_BPartner_ID);
			sp_Bill_Location_ID.setWhereClause("C_BPartner_Location.C_BPartner_Location_ID = " + m_Bill_Location_ID);
			sp_C_BPartner_Location_ID.setWhereClause("C_BPartner_Location.C_BPartner_Location_ID = " + m_C_BPartner_Location_ID);
			sp_M_PriceList_ID.setWhereClause("M_PriceList.M_PriceList_ID = " + m_M_PriceList_ID);
		}
		
		et_Description = (EditText) findViewById(R.id.et_Description);
		
		cds_DocStatus = (Cust_ButtonDocAction) findViewById(R.id.cds_DocStatus);
		//cds_DocStatus.setActionOption(new String[]{Cust_ButtonDocAction.ACTION_Prepare});
		cds_DocStatus.getQuickAction().setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
			@Override
			public void onItemClick(QuickAction source, int pos, int actionId) {
				final String oldDocStatus = cds_DocStatus.getDocStatus();
				cds_DocStatus.onItemClick(source, pos, actionId);
				if(!cds_DocStatus.getDocStatus().equals(Cust_ButtonDocAction.STATUS_Voided)){
					if(cds_DocStatus.getDocStatus().equals(Cust_ButtonDocAction.STATUS_Completed)){
						//
						loadConnection();
						
						Cursor rs = con.querySQL("SELECT 1 FROM C_OrderLine ol " +
								"WHERE ol.C_Order_ID = " + 
								Env.getTabRecord_ID(getCtx(), "C_Order") + " LIMIT 1", null);
						boolean exists = rs.moveToFirst();
						closeConnection();
						if(exists){
							//setDocAction();
							save();	
						} else {
							Msg.alertMsg(getCtx(), getResources().getString(R.string.msg_Error), 
									getResources().getString(R.string.msg_DocNoLinesFound));
							cds_DocStatus.setDocAction(oldDocStatus);
		    				//setDocAction();
						}

					} else {
						//setDocAction();
						save();
					}
				} else {
					Builder ask = Msg.confirmMsg(getCtx(), getResources().getString(R.string.msg_AskVoid) + 
		        			"\n\"" + et_DocumentNo.getText() + "\"");
		        	String msg_Yes = getCtx().getResources().getString(R.string.msg_Yes);
		        	ask.setPositiveButton(msg_Yes, new DialogInterface.OnClickListener() {
		    			public void onClick(DialogInterface dialog, int which) {
		    				//setDocAction();
							save();
		    			}
		    		});
		        	String msg_No = getCtx().getResources().getString(R.string.msg_No);
		        	ask.setNegativeButton(msg_No, new DialogInterface.OnClickListener() {
		    			public void onClick(DialogInterface dialog, int which) {
		    				cds_DocStatus.setDocAction(oldDocStatus);
		    				//setDocAction();
		    			}
		    		});
		        	
		        	ask.show();
				}
			}
		});
		
		
		et_TotalLines = (EditText) findViewById(R.id.et_TotalLines);
		et_GrandTotal = (EditText) findViewById(R.id.et_GrandTotal);
		
		et_POReference = (EditText) findViewById(R.id.et_POReference);
		
		//	Add Views to Super Model
		addView((TextView) findViewById(R.id.tv_DocumentNo), et_DocumentNo, "DocumentNo", true);
		addView((TextView) findViewById(R.id.tv_C_DocTypeTarget_ID), sp_C_DocTypeTarget_ID, "C_DocTypeTarget_ID", false);
		addView((TextView) findViewById(R.id.tv_DateOrdered), cd_DateOrdered, "DateOrdered", Env.getContext(this, "#Date"), true);
		addView((TextView) findViewById(R.id.tv_DatePromised), cd_DatePromised, "DatePromised", Env.getContext(this, "#Date"), false);
		addView((TextView) findViewById(R.id.tv_POReference), et_POReference, "POReference", false);
		addView((TextView) findViewById(R.id.tv_C_BPartner_ID), sp_C_BPartner_ID, "C_BPartner_ID", true);
		addView((TextView) findViewById(R.id.tv_M_PriceList_ID), sp_M_PriceList_ID, "M_PriceList_ID", true);
		addView((TextView) findViewById(R.id.tv_Bill_Location_ID), sp_Bill_Location_ID, "Bill_Location_ID", true);
		addView((TextView) findViewById(R.id.tv_C_BPartner_Location_ID), sp_C_BPartner_Location_ID, "C_BPartner_Location_ID", true);
		addView((TextView) findViewById(R.id.et_Description), et_Description, "Description", false);
		
		addView((TextView) findViewById(R.id.cds_DocStatus), cds_DocStatus, "DocAction", "DR", false);
		
		addView((TextView) findViewById(R.id.tv_TotalLines), et_TotalLines, "TotalLines", "0", true);
		addView((TextView) findViewById(R.id.tv_GrandTotal), et_GrandTotal, "GrandTotal", "0", true);
		cds_DocStatus.setValRuleAction(null, this, Env.getContextAsInt(getCtx(), "#C_DocTypeTarget_ID"), false);
    }
    
    /**
     * Carga loas Filtros
     * @author Yamel Senih 12/05/2012, 01:41:14
     * @return void
     */
    private void loadDefaultValues(){
    	/*Msg.toastMsg(getCtx(), "" + param.getActivityType());
    	Msg.toastMsg(getCtx(), "**" + param.getOriginalActivityType());
    	Msg.toastMsg(getCtx(), "--" + param.getAction());*/
    	if(param.getOriginalActivityType().equals("M")){
    		String sql = new String("SELECT cp.C_BPartner_ID, pv.C_BPartner_Location_ID, pv.Bill_Location_ID, " +
        			"cp.M_PriceList_ID, cp.PaymentRule, cp.C_PaymentTerm_ID " +
        			"FROM XX_MB_PlanningVisit pv " +
        			"INNER JOIN C_BPartner cp ON(cp.C_BPartner_ID = pv.C_BPartner_ID) " +
        			"WHERE pv.XX_MB_PlanningVisit_ID = " + Env.getContextAsInt(this, "#XX_MB_PlanningVisit_ID"));
    		loadConnection();
    		Cursor rs = con.querySQL(sql, null);
    		if(rs.moveToFirst()){
    			m_C_BPartner_ID = rs.getInt(0);
    			m_C_BPartner_Location_ID = rs.getInt(1);
    			m_Bill_Location_ID = rs.getInt(2);
    			m_M_PriceList_ID = rs.getInt(3);
    			m_PaymentRule = rs.getString(4);
    			m_C_PaymentTerm_ID = rs.getInt(5);
    			Env.setContext(this, "#M_PriceList_ID", m_M_PriceList_ID);
    			Env.setContext(this, "#PaymentRule", m_PaymentRule);
    			Env.setContext(this, "#C_PaymentTerm_ID", m_C_PaymentTerm_ID);
    			Env.setContext(this, "#C_BPartner_ID", m_C_BPartner_ID);
    			Env.setContext(this, "#C_BPartner_Location_ID", m_C_BPartner_Location_ID);
    			Env.setContext(this, "#Bill_Location_ID", m_Bill_Location_ID);
    			loaded = true;
    		}
    	}    	
    }
    
    
    /**
     * Carga la proxima secuencia
     * @author Yamel Senih 11/05/2012, 15:12:28
     * @param m_C_DocType
     * @return void
     */
    private void loadSequence(DisplaySpinner m_C_DocType){
    	if(getMP().isNew()){
    		loadConnection();
    		String seq = MSequence.getCurrentNext(con, m_C_DocType.getID(), Env.getAD_User_ID(this));
        	if(seq != null){
        		et_DocumentNo.setText(seq);
        	} else {
        		et_DocumentNo.setText("");
        	}
        	closeConnection();
    	}
    }
    
    @Override
    public boolean newConfig(){
    	boolean ok = super.newConfig();
    	DisplaySpinner m_C_DocType = (DisplaySpinner)sp_C_DocTypeTarget_ID.getSelectedItem();
    	loadSequence(m_C_DocType);
    	Env.setContext(getCtx(), "#XX_MB_CustomerInventory_ID", 0);
    	return ok;
    }
    
    @Override
    public boolean modifyRecord(){
		super.modifyRecord();
		return true;
	}
    
    @Override
    public boolean seeConfig(){
    	boolean ok = super.seeConfig();
    	//setDocAction();
		return ok;
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
    }
    @Override
    protected void onResume() {
        super.onResume();
        //loadDefaultValues();
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
		return new MBOrder(getCtx(), 0);
	}

	@Override
	public MP getCustomMP(int id) {
		return new MBOrder(getCtx(), id);
	}

	@Override
	public MP getCustomMP(Cursor rs) {
		return new MBOrder(getCtx(), rs);
	}

	/* (non-Javadoc)
	 * @see org.appd.view.MVActivity#getActivityName()
	 */
	@Override
	protected String getActivityName() {
		return "C_Order";
	}
	
	protected DisplayMenuItem getMenuItem(){
		DisplayMenuItem item = super.getMenuItem();
    	item.setWhereClause(param.getWhereClause());
    	return item;
    }
	
}
