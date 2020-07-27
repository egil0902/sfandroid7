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
import org.appd.model.MBCash;
import org.appd.model.MP;
import org.appd.util.DisplayMenuItem;
import org.appd.util.Msg;
import org.appd.util.contribution.QuickAction;
import org.appd.view.custom.Cust_ButtonDocAction;
import org.appd.view.custom.Cust_DateBox;
import org.appd.view.custom.Cust_Spinner;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
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
public class MV_Cash extends MVActivity {
	
	private EditText 				et_DocumentNo;
    private Cust_Spinner 			sp_C_DocTypeTarget_ID;
    private Cust_DateBox 			cd_StatementDate;
    private EditText 				et_BeginningBalance;
    private EditText				et_V_CashAmt;
    private EditText				et_V_OtherAmt;
    private EditText				et_V_DepositCashAmt;
    private EditText				et_V_DepositOtherAmt;
    private EditText				et_V_EndingBalanceAmt;
    private EditText				et_V_DifferenceAmt;
    private EditText				et_V_ForDepositAmt;
    private Cust_ButtonDocAction 	cds_DocAction;
    
	//private Context ctx;
	private DisplayMenuItem param;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        super.setContentView(R.layout.v_cash);
        //	Set Summary Tab
        setIsSummary(true);
        
        
        Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			param = (DisplayMenuItem)bundle.getParcelable("Param");
		} 
		if(param == null)
			throw new IllegalArgumentException ("No Param");
		
		//	Instance of Fields
		et_DocumentNo = (EditText) findViewById(R.id.et_DocumentNo);
		sp_C_DocTypeTarget_ID = (Cust_Spinner) findViewById(R.id.sp_C_DocTypeTarget_ID);
		cd_StatementDate = (Cust_DateBox) findViewById(R.id.cd_StatementDate);
		et_BeginningBalance = (EditText) findViewById(R.id.et_BeginningBalance);
		et_V_CashAmt = (EditText) findViewById(R.id.et_V_CashAmt);
		et_V_OtherAmt = (EditText) findViewById(R.id.et_V_OtherAmt);
		et_V_DepositCashAmt = (EditText) findViewById(R.id.et_V_DepositCashAmt);
		et_V_DepositOtherAmt = (EditText) findViewById(R.id.et_V_DepositOtherAmt);
		et_V_EndingBalanceAmt = (EditText) findViewById(R.id.et_V_EndingBalanceAmt);
		et_V_DifferenceAmt = (EditText) findViewById(R.id.et_V_DifferenceAmt);
		et_V_ForDepositAmt = (EditText) findViewById(R.id.et_V_ForDepositAmt);
		
		
		sp_C_DocTypeTarget_ID.setTableName("C_DocType");
		sp_C_DocTypeTarget_ID.setIdentifierName("Name");
		sp_C_DocTypeTarget_ID.setWhereClause("C_DocType.DocBaseType IN('CMC')");
		
		sp_C_DocTypeTarget_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> a, View v,
					int position, long i) {
				//DisplaySpinner m_C_DocType = ((DisplaySpinner) sp_C_DocType_ID.getItemAtPosition(position));
				//loadSequence(m_C_DocType);
			}

			@Override
			public void onNothingSelected(AdapterView<?> a) {
				
			}
    		
    	});
		
		cds_DocAction = (Cust_ButtonDocAction) findViewById(R.id.cds_DocAction);
		//cds_DocStatus.setValRuleAction(new String[]{Cust_ButtonDocAction.ACTION_Prepare});
		cds_DocAction.getQuickAction().setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
			@Override
			public void onItemClick(QuickAction source, int pos, int actionId) {
				final String oldDocStatus = cds_DocAction.getDocStatus();
				cds_DocAction.onItemClick(source, pos, actionId);
				final MBCash cs = (MBCash) getMP();
				if(!cds_DocAction.getDocStatus().equals(Cust_ButtonDocAction.STATUS_Voided)){
					if(cds_DocAction.getDocStatus().equals(Cust_ButtonDocAction.STATUS_Completed)){
						//	
						//MBCash cs = (MBCash) getMP();
						if(cs.isExistsLines()){
							cs.processDocuments(cds_DocAction.getDocStatus());
							save();	
						} else {
							Msg.alertMsg(getCtx(), getResources().getString(R.string.msg_Error), 
									getResources().getString(R.string.msg_DocNoLinesFound));
							cds_DocAction.setDocAction(oldDocStatus);
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
		    				try{
		    					if(!cs.isExistsDeposits()){
		    						cs.processDocuments(cds_DocAction.getDocStatus());
				    				save();
		    					} else {
		    						Msg.alertMsg(getCtx(), getResources().getString(R.string.msg_ProcessError),
			    							getResources().getString(R.string.msg_ExistsDeposit));
		    						cds_DocAction.setDocAction(oldDocStatus);
		    					}
		    					
		    				} catch (Exception e){
		    					Msg.alertMsg(getCtx(), getResources().getString(R.string.msg_Error),
		    							"" + e.getMessage());
		    				}
		    			}
		    		});
		        	String msg_No = getCtx().getResources().getString(R.string.msg_No);
		        	ask.setNegativeButton(msg_No, new DialogInterface.OnClickListener() {
		    			public void onClick(DialogInterface dialog, int which) {
		    				cds_DocAction.setDocAction(oldDocStatus);
		    				//setDocAction();
		    			}
		    		});
		        	
		        	ask.show();
				}
			}
		});
		
		//	Add Views to Super Model
		addView((TextView) findViewById(R.id.tv_DocumentNo), et_DocumentNo, "DocumentNo", false);
		addView((TextView) findViewById(R.id.tv_C_DocType_ID), sp_C_DocTypeTarget_ID, "C_DocTypeTarget_ID", false);
		addView((TextView) findViewById(R.id.tv_DateDoc), cd_StatementDate, "StatementDate", Env.getContext(this, "#Date"), true);
		addView((TextView) findViewById(R.id.cds_DocStatus), cds_DocAction, "DocAction", "DR", false);
		
		et_V_CashAmt = (EditText) findViewById(R.id.et_V_CashAmt);
		et_V_OtherAmt = (EditText) findViewById(R.id.et_V_OtherAmt);
		et_V_DepositCashAmt = (EditText) findViewById(R.id.et_V_DepositCashAmt);
		et_V_DepositOtherAmt = (EditText) findViewById(R.id.et_V_DepositOtherAmt);
		et_V_EndingBalanceAmt = (EditText) findViewById(R.id.et_V_EndingBalanceAmt);
		et_V_DifferenceAmt = (EditText) findViewById(R.id.et_V_DifferenceAmt);
		et_V_ForDepositAmt = (EditText) findViewById(R.id.et_V_ForDepositAmt);
		
		addView((TextView) findViewById(R.id.et_BeginningBalance), et_BeginningBalance, "BeginningBalance", false);
		addView((TextView) findViewById(R.id.et_V_CashAmt), et_V_CashAmt, "V_CashAmt", false);
		addView((TextView) findViewById(R.id.et_V_OtherAmt), et_V_OtherAmt, "V_OtherAmt", false);
		addView((TextView) findViewById(R.id.et_V_DepositCashAmt), et_V_DepositCashAmt, "V_DepositCashAmt", false);
		addView((TextView) findViewById(R.id.et_V_DepositOtherAmt), et_V_DepositOtherAmt, "V_DepositOtherAmt", false);
		addView((TextView) findViewById(R.id.et_V_EndingBalanceAmt), et_V_EndingBalanceAmt, "V_EndingBalanceAmt", false);
		addView((TextView) findViewById(R.id.et_V_DifferenceAmt), et_V_DifferenceAmt, "V_DifferenceAmt", false);
		addView((TextView) findViewById(R.id.et_V_ForDepositAmt), et_V_ForDepositAmt, "V_ForDepositAmt", false);
		
		
		cds_DocAction.setValRuleAction(null, this, Env.getContextAsInt(getCtx(), "#C_DocTypeTarget_ID"), false);
    }
    
    @Override
    public boolean newConfig(){
    	boolean ok = super.newConfig();
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
        //cds_DocAction.setValRuleAction(null, this, Env.getContextAsInt(getCtx(), "#C_DocType_ID"), false);
        // The activity is about to become visible.
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
		return new MBCash(getCtx(), 0);
	}

	@Override
	public MP getCustomMP(int id) {
		return new MBCash(getCtx(), id);
	}

	@Override
	public MP getCustomMP(Cursor rs) {
		return new MBCash(getCtx(), rs);
	}

	/* (non-Javadoc)
	 * @see org.appd.view.MVActivity#getActivityName()
	 */
	@Override
	protected String getActivityName() {
		return "C_Cash";
	}
	
	protected DisplayMenuItem getMenuItem(){
		DisplayMenuItem item = super.getMenuItem();
		item.setWhereClause(param.getWhereClause());
    	return item;
    }	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
	    super.onActivityResult(requestCode, resultCode, data);
	    //	CallOut
	    if (resultCode == RESULT_OK) {
	    	if(data != null){
	    		/*int bpartner = findBPartner(Env.getContextAsInt(getCtx(), "#InOut_ID"));
	    	    sp_C_BPartner_ID.setRecord_ID(bpartner);*/
	    	}
	    }
	}	
}
