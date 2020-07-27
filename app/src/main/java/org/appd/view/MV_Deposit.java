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

import org.appd.base.DB;
import org.appd.base.Env;
import org.appd.base.R;
import org.appd.model.MBPayment;
import org.appd.model.MP;
import org.appd.util.DisplayMenuItem;
import org.appd.util.Msg;
import org.appd.util.contribution.QuickAction;
import org.appd.view.custom.Cust_ButtonDocAction;
import org.appd.view.custom.Cust_DateBox;
import org.appd.view.custom.Cust_Search;
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
public class MV_Deposit extends MVActivity {
	
	private EditText 				et_DocumentNo;
    private Cust_Spinner 			sp_C_DocType_ID;
    private Cust_DateBox 			cd_DateAcct;
    private Cust_Search 			sp_C_BankAccount_ID;
    private EditText 				et_Description;
    private Cust_ButtonDocAction 	cds_DocStatus;
    private EditText 				et_PayAmt;
    //private Button 					butt_ViewCollects;
    
	//private Context ctx;
	private DisplayMenuItem param;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        super.setContentView(R.layout.v_deposit);
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
		sp_C_DocType_ID = (Cust_Spinner) findViewById(R.id.sp_C_DocType_ID);
		sp_C_BankAccount_ID = (Cust_Search) findViewById(R.id.cs_C_BankAccount_ID);
		cd_DateAcct = (Cust_DateBox) findViewById(R.id.cd_DateAcct);
		et_Description = (EditText) findViewById(R.id.et_Description);
		
		
		/*butt_ViewCollects = (Button)findViewById(R.id.butt_ViewCollects);
		butt_ViewCollects.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//addCollect();
			}
		});*/
		
		sp_C_DocType_ID.setTableName("C_DocType");
		sp_C_DocType_ID.setIdentifierName("Name");
		sp_C_DocType_ID.setWhereClause("C_DocType.DocBaseType IN('ARR')");
		
		sp_C_DocType_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

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
		
		sp_C_BankAccount_ID.setItem(new DisplayMenuItem(getResources().getString(R.string.C_BankAccount_ID), 
				null, 0, "C_BankAccount", null, null, "F"));
		
		sp_C_BankAccount_ID.getItem().setIdentifier("AccountNo");
		sp_C_BankAccount_ID.setActivity(this);
		
		cds_DocStatus = (Cust_ButtonDocAction) findViewById(R.id.cds_DocStatus);
		//cds_DocStatus.setValRuleAction(new String[]{Cust_ButtonDocAction.ACTION_Prepare});
		cds_DocStatus.getQuickAction().setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
			@Override
			public void onItemClick(QuickAction source, int pos, int actionId) {
				final String oldDocStatus = cds_DocStatus.getDocStatus();
				cds_DocStatus.onItemClick(source, pos, actionId);
				if(!cds_DocStatus.getDocStatus().equals(Cust_ButtonDocAction.STATUS_Voided)){
					if(cds_DocStatus.getDocStatus().equals(Cust_ButtonDocAction.STATUS_Completed)){
						//
						loadConnection();
						
						Cursor rs = con.querySQL("SELECT 1 FROM C_CashLine cl " +
								"WHERE cl.Deposit_ID = " + 
								Env.getTabRecord_ID(getCtx(), "C_Payment") + " LIMIT 1", null);
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
		    				loadConnection(DB.READ_WRITE);
		    				String sqlUpdate = new String("UPDATE C_CashLine SET Deposit_ID = null " + 
			    					"WHERE Deposit_ID = " + Env.getTabRecord_ID(getCtx(), "C_Payment"));
			    			//	
			    			con.executeSQL(sqlUpdate);
			    			closeConnection();
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
		
		
		et_PayAmt = (EditText) findViewById(R.id.et_PayAmt);
		
		//	Add Views to Super Model
		addView((TextView) findViewById(R.id.tv_DocumentNo), et_DocumentNo, "DocumentNo", false);
		addView((TextView) findViewById(R.id.tv_C_DocType_ID), sp_C_DocType_ID, "C_DocType_ID", false);
		addView((TextView) findViewById(R.id.tv_C_BankAccount_ID), sp_C_BankAccount_ID, "C_BankAccount_ID", false);
		addView((TextView) findViewById(R.id.tv_DateDoc), cd_DateAcct, "DateAcct", Env.getContext(this, "#Date"), true);
		addView((TextView) findViewById(R.id.tv_PayAmt), et_PayAmt, "PayAmt", "0", false);
		addView((TextView) findViewById(R.id.cds_DocStatus), cds_DocStatus, "DocAction", "DR", false);
		addView((TextView) findViewById(R.id.et_Description), et_Description, "Description", false);
		cds_DocStatus.setValRuleAction(null, this, Env.getContextAsInt(getCtx(), "#C_DocType_ID"), false);
    }
    
    @Override
    public boolean newConfig(){
    	boolean ok = super.newConfig();
    	setDefault((MBPayment) getMP());
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
		return setDefault(new MBPayment(getCtx(), 0));
	}

	@Override
	public MP getCustomMP(int id) {
		return setDefault(new MBPayment(getCtx(), id));
	}

	@Override
	public MP getCustomMP(Cursor rs) {
		return setDefault(new MBPayment(getCtx(), rs));
	}

	/* (non-Javadoc)
	 * @see org.appd.view.MVActivity#getActivityName()
	 */
	@Override
	protected String getActivityName() {
		return "C_Payment";
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
	
	/**
	 * Establece los valores por defecto para el pago/cobro
	 * @author Yamel Senih 09/11/2012, 01:46:33
	 * @param payment
	 * @return
	 * @return MBPayment
	 */
	public MBPayment setDefault(MBPayment payment) {
		
		//	Instance Payment
		if(payment == null)
			return payment;
		payment.set_Value("DateTrx", Env.getContext(this, "#Date"));
		payment.set_Value("DateAcct", Env.getContext(this, "#Date"));
		payment.set_Value("C_BankAccount_ID", Env.getContext(this, "#C_BankAccount_ID"));
		payment.set_Value("AD_Org_ID", Env.getAD_Org_ID(this));
		payment.set_Value("AD_OrgTrx_ID", Env.getAD_Org_ID(this));
		payment.set_Value("C_Currency_ID", Env.getContext(this, "#C_Currency_ID"));		
		payment.set_Value("TenderType", "D");
		payment.set_Value("C_Charge_ID", Env.getContext(this, "#C_Charge_ID"));
		payment.set_Value("C_BPartner_ID", Env.getContext(this, "#DepositBPartner_ID"));
		payment.set_Value("OverUnderAmt", 0);
		payment.set_Value("TrxType", "S");
		payment.set_Value("Processed", "N");
		payment.set_Value("Posted", "N");
		payment.set_Value("PayAmt", 0);
		payment.set_Value("IsSelfService", "N");
		payment.set_Value("IsReconciled", "N");
		payment.set_Value("IsReceipt", "Y");
		payment.set_Value("IsPrepayment", "N");
		payment.set_Value("IsOverUnderPayment", "N");
		payment.set_Value("IsOnline", "N");
		payment.set_Value("IsApproved", "N");
		payment.set_Value("IsAllocated", "N");
		payment.set_Value("DocAction", "DR");
		payment.set_Value("IsDelayedCapture", "N");
		payment.set_Value("CreditCardExpMM", null);
	    payment.set_Value("CreditCardExpYY", null);
	    payment.set_Value("CreditCardNumber", null);
	    payment.set_Value("CreditCardType", null);
	    payment.set_Value("CreditCardVV", null);
	    payment.set_Value("A_Name", null);
	    payment.set_Value("RoutingNo", null);
	    payment.set_Value("AccountNo", null);
		
		return payment;
	}
	
}
