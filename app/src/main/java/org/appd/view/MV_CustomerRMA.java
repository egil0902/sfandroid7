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
import org.appd.model.MBInOut;
import org.appd.model.MBPlanningVisit;
import org.appd.model.MBRMA;
import org.appd.model.MP;
import org.appd.model.MSequence;
import org.appd.util.DisplayMenuItem;
import org.appd.util.DisplaySpinner;
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
public class MV_CustomerRMA extends MVActivity {
	
	private EditText et_DocumentNo;
    private Cust_Spinner sp_C_DocType_ID;
    private Cust_DateBox cd_DateDoc;
    private Cust_Spinner sp_M_RMAType_ID;
    private Cust_Search cs_M_InOut_ID;
    private Cust_Spinner sp_C_BPartner_ID;
    private EditText et_Name;
    private EditText et_Description;
    private EditText et_Help;
    private Cust_ButtonDocAction cds_DocStatus;
    private EditText et_Amt;
	private MBInOut m_InOut = null;
	private int m_C_BPartner_ID = 0;
    
	//private Context ctx;
	private DisplayMenuItem param;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        super.setContentView(R.layout.v_customer_rma);
        //	Set Summary Tab
        setIsSummary(true);
        
        
        Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			param = (DisplayMenuItem)bundle.getParcelable("Param");
		} 
		if(param == null)
			throw new IllegalArgumentException ("No Param");
		
		MBPlanningVisit pv = new MBPlanningVisit(this, Env.getContextAsInt(this, "#XX_MB_PlanningVisit_ID"));
		m_C_BPartner_ID = pv.get_ValueAsInt("C_BPartner_ID");
		
		//	Instance of Fields
		et_DocumentNo = (EditText) findViewById(R.id.et_DocumentNo);
		sp_C_DocType_ID = (Cust_Spinner) findViewById(R.id.sp_C_DocType_ID);
		sp_M_RMAType_ID = (Cust_Spinner) findViewById(R.id.cs_M_RMAType_ID);
		cd_DateDoc = (Cust_DateBox) findViewById(R.id.cd_DateDoc);
		cs_M_InOut_ID = (Cust_Search) findViewById(R.id.cs_M_InOut_ID);
		sp_C_BPartner_ID = (Cust_Spinner) findViewById(R.id.sp_C_BPartner_ID);
		et_Name = (EditText) findViewById(R.id.et_Name);
		et_Description = (EditText) findViewById(R.id.et_Description);
		et_Help = (EditText) findViewById(R.id.et_Help);
		
		
		
		sp_C_DocType_ID.setTableName("C_DocType");
		sp_C_DocType_ID.setIdentifierName("Name");
		sp_C_DocType_ID.setWhereClause("C_DocType.DocBaseType IN('SOO') AND C_DocType.DocSubTypeSO IN('RM')");
		
		sp_C_DocType_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> a, View v,
					int position, long i) {
				DisplaySpinner m_C_DocType = ((DisplaySpinner) sp_C_DocType_ID.getItemAtPosition(position));
				loadSequence(m_C_DocType);
			}

			@Override
			public void onNothingSelected(AdapterView<?> a) {
				
			}
    		
    	});
		
		sp_M_RMAType_ID.setTableName("M_RMAType");
		sp_M_RMAType_ID.setIdentifierName("Name");
		sp_M_RMAType_ID.setFirstSpace(true);
		//sp_M_RMAType_ID.setWhereClause("C_DocType.DocBaseType IN('SOO') AND C_DocType.DocSubTypeSO IN('RM')");
		
		cs_M_InOut_ID.setItem(new DisplayMenuItem(getResources().getString(R.string.M_InOut_ID), 
				null, 0, "M_InOut", "M_InOut.C_BPartner_Location_ID = " + 
										Env.getContextAsInt(this, "#C_BPartner_Location_ID") + 
										" AND M_InOut.M_InOut_ID NOT IN(SELECT rm.InOut_ID FROM M_RMA rm WHERE rm.C_BPartner_ID = " +
										Env.getContextAsInt(this, "#C_BPartner_ID") + ")", null, "F"));
		cs_M_InOut_ID.getItem().setIdentifier("DocumentNo");
		cs_M_InOut_ID.setActivity(this);	

		sp_C_BPartner_ID.setTableName("C_BPartner");
		sp_C_BPartner_ID.setIdentifierName("Name");
		//Log.i(" - ", " - / " + Env.getContextAsInt(this, "#C_BPartner_ID"));
		if(Env.getTabRecord_ID(getCtx(), getActivityName()) != 0){
			sp_C_BPartner_ID.setFirstSpace(false);
		} else {
			sp_C_BPartner_ID.setWhereClause("C_BPartner.C_BPartner_ID = " + m_C_BPartner_ID);
			sp_C_BPartner_ID.setFirstSpace(true);
		}
		
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
						
						Cursor rs = con.querySQL("SELECT 1 FROM M_RMALine ml " +
								"WHERE ml.M_RMA_ID = " + 
								Env.getTabRecord_ID(getCtx(), "M_RMA") + " LIMIT 1", null);
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
		
		
		et_Amt = (EditText) findViewById(R.id.et_Amt);
		
		//	Add Views to Super Model
		addView((TextView) findViewById(R.id.tv_DocumentNo), et_DocumentNo, "DocumentNo", true);
		addView((TextView) findViewById(R.id.tv_C_DocType_ID), sp_C_DocType_ID, "C_DocType_ID", false);
		addView((TextView) findViewById(R.id.tv_M_RMAType_ID), sp_M_RMAType_ID, "M_RMAType_ID", false);
		addView((TextView) findViewById(R.id.tv_DateDoc), cd_DateDoc, "DateDoc", Env.getContext(this, "#Date"), true);
		addView((TextView) findViewById(R.id.tv_M_InOut_ID), cs_M_InOut_ID, "InOut_ID", false);
		addView((TextView) findViewById(R.id.tv_C_BPartner_ID), sp_C_BPartner_ID, "C_BPartner_ID", true);
		addView((TextView) findViewById(R.id.tv_Name), et_Name, "Name", false);
		addView((TextView) findViewById(R.id.tv_Amt), et_Amt, "Amt", "0", false);
		addView((TextView) findViewById(R.id.cds_DocStatus), cds_DocStatus, "DocAction", "DR", false);
		addView((TextView) findViewById(R.id.et_Description), et_Description, "Description", false);
		addView((TextView) findViewById(R.id.tv_Help), et_Help, "Help", false);
		cds_DocStatus.setValRuleAction(null, this, Env.getContextAsInt(getCtx(), "#C_DocType_ID"), false);
    }
    
    
    /**
     * Busca el Socio de Negocio que se encuentra en la entrega
     * seleccionada
     * @author Yamel Senih 09/08/2012, 18:47:53
     * @param id
     * @return
     * @return int
     */
    private int findBPartner(int id){
    	if(m_InOut == null){
    		m_InOut = new MBInOut(getCtx(), id);
    	} else {
    		m_InOut.loadData(id);
    	}
		return m_InOut.get_ValueAsInt("C_BPartner_ID");
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
    	DisplaySpinner m_C_DocType = (DisplaySpinner)sp_C_DocType_ID.getSelectedItem();
    	loadSequence(m_C_DocType);
    	Env.setContext(getCtx(), "#M_RMA_ID", 0);
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
		return new MBRMA(getCtx(), 0);
	}

	@Override
	public MP getCustomMP(int id) {
		return new MBRMA(getCtx(), id);
	}

	@Override
	public MP getCustomMP(Cursor rs) {
		return new MBRMA(getCtx(), rs);
	}

	/* (non-Javadoc)
	 * @see org.appd.view.MVActivity#getActivityName()
	 */
	@Override
	protected String getActivityName() {
		return "M_RMA";
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
	    		int bpartner = findBPartner(Env.getContextAsInt(getCtx(), "#InOut_ID"));
	    	    sp_C_BPartner_ID.setRecord_ID(bpartner);
	    	}
	    }
	}
}
