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

import java.util.LinkedList;

import org.appd.base.DB;
import org.appd.base.Env;
import org.appd.base.R;
import org.appd.model.MBVisit;
import org.appd.model.MBVisitLine;
import org.appd.model.MP;
import org.appd.util.AdapterVisitLine;
import org.appd.util.DisplayMenuItem;
import org.appd.util.DisplayVisitItem;
import org.appd.util.Msg;
import org.appd.view.custom.Cust_DateBox;
import org.appd.view.custom.Cust_Search;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Yamel Senih
 * Modelo
 *
 */
public class MV_VisitAndLine extends MVActivity {
	
	private Cust_DateBox 					cd_DateVisit;
    private Cust_Search 					cs_XX_MB_PlanningVisit_ID;
    private CheckBox 						ch_OffCourse;
    private ListView 						visitLineList;
	private Button 							butt_Add;
	private LinkedList<DisplayVisitItem> 	data;
	private MBVisitLine 					m_visitLine = null;
	private int								m_C_BPartner_Location_ID = 0;
    
	//private Context ctx;
	private DisplayMenuItem param;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        super.setContentView(R.layout.v_customer_visit);
        //	Set Summary Tab
        setIsSummary(true);
        
        
        Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			param = (DisplayMenuItem)bundle.getParcelable("Param");
		} 
		if(param == null)
			throw new IllegalArgumentException ("No Param");
		
		//setManConnection(true);
		
		//loadDefaultValues();
		
		int m_XX_MB_PlanningVisit_ID = Env.getContextAsInt(this, "#XX_MB_PlanningVisit_ID");
		//Env.setContext(this, "#XX_MB_Temp_PlanningVisit_ID", m_XX_MB_PlanningVisit_ID);
		
		//	Instance of Fields
		cd_DateVisit = (Cust_DateBox) findViewById(R.id.cd_DateVisit);
		cs_XX_MB_PlanningVisit_ID = (Cust_Search) findViewById(R.id.cs_XX_MB_PlanningVisit_ID);
		ch_OffCourse = (CheckBox) findViewById(R.id.ch_OffCourse);
				
		String where = new String();
		
		if(m_XX_MB_PlanningVisit_ID != 0){
			where = "XX_MB_PlanningVisit.XX_MB_PlanningVisit_ID = " + m_XX_MB_PlanningVisit_ID;		
		}
		
		cs_XX_MB_PlanningVisit_ID.setItem(new DisplayMenuItem(getResources().getString(R.string.XX_MB_PlanningVisit_ID), 
				null, 0, "XX_MB_PlanningVisit", where , null, "F"));
		cs_XX_MB_PlanningVisit_ID.getItem().setIdentifier("Name");
		cs_XX_MB_PlanningVisit_ID.setActivity(this);	

		//	Add Views to Super Model
		addView((TextView) findViewById(R.id.tv_XX_MB_PlanningVisit_ID), cs_XX_MB_PlanningVisit_ID, "XX_MB_PlanningVisit_ID", 
				String.valueOf(m_XX_MB_PlanningVisit_ID), true);
		addView((TextView) findViewById(R.id.tv_DateVisit), cd_DateVisit, "DateVisit", Env.getContext(this, "#Date"), true);
		addView((TextView) findViewById(R.id.ch_OffCourse), ch_OffCourse, "OffCourse", true);
		
		butt_Add = (Button)findViewById(R.id.butt_Add);
		butt_Add.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				addVisitLine();
			}
		});
		
    	visitLineList = (ListView)findViewById(R.id.ls_VisitLine);
    	
    	visitLineList.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> padre, View v, int position,
					long id) {
				//	Open Document
				openDocument((DisplayVisitItem) padre.getItemAtPosition(position));
				
			}
		});
    	
    	registerForContextMenu(visitLineList);
    }
    
    @Override
    public boolean newConfig(){
    	boolean ok = super.newConfig();
    	//DisplaySpinner m_C_DocType = (DisplaySpinner)sp_C_DocType_ID.getSelectedItem();
    	//loadSequence(m_C_DocType);
    	//cds_DocStatus.setValRuleAction(new String[]{Cust_ButtonDocAction.STATUS_Drafted});
    	//Env.setContext(getCtx(), "#M_RMA_ID", 0);
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
		return new MBVisit(getCtx(), 0);
	}

	@Override
	public MP getCustomMP(int id) {
		return new MBVisit(getCtx(), id);
	}

	@Override
	public MP getCustomMP(Cursor rs) {
		return new MBVisit(getCtx(), rs);
	}

	/* (non-Javadoc)
	 * @see org.appd.view.MVActivity#getActivityName()
	 */
	@Override
	protected String getActivityName() {
		return "XX_MB_Visit";
	}
	
	protected DisplayMenuItem getMenuItem(){
		DisplayMenuItem item = super.getMenuItem();
		item.setWhereClause(param.getWhereClause());
    	return item;
    }
	
	@Override
    protected void onResume() {
        super.onResume();
        if(getMP().get_Value("DateTo") == null)
        	butt_Add.setEnabled(param.isReadWrite());
        else
        	butt_Add.setEnabled(false);
        loadCurrentRecords();
    }
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
	    if (resultCode == RESULT_OK) {
	    	if(data != null){
	    		final Bundle extras = data.getExtras();
    			
    			if(Env.getTabRecord_ID(this, "XX_MB_Visit") == 0){
    				final int m_XX_MB_Visit_ID = MBVisit.findOpenVisit(this, null);
					//	Verifica si ya hay una visita abierta
					if(m_XX_MB_Visit_ID != 0){
						final MBVisit visit = new MBVisit(getApplicationContext(), m_XX_MB_Visit_ID);
						//	Get Date from Visit
						if(!Env.isAutomaticVisitClosing(this)){
							String dateS = (String)visit.get_Value("DateVisit");
							
							Builder ask = Msg.confirmMsg(this, Env.getDateFormatString(dateS, "yyyy-MM-dd hh:mm:ss", "dd/MM/yyyy hh:mm:ss") 
									+ "\n" + visit.get_Value("NameBPartner") + "\""
									+ "\n" + getResources().getString(R.string.msg_AskCloseVisit));
					    	
							String msg_Acept = this.getResources().getString(R.string.msg_Yes);
					    	ask.setPositiveButton(msg_Acept, new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									
									dialog.cancel();
									//	Cierra la Visita anterior
									try {	
										visit.closeVisit(null);
										//	Crea la Linea
										createVisitAndLine(extras);
									} catch(Exception e) {
										Msg.alertMsg(getCtx(), 
												getCtx().getString(R.string.msg_Error), e.getMessage());
										Log.e("Error", "Exception", e);
									}
								}
							});
					    	ask.show();
						} else {
							
							//	Cierra la Visita anterior
							try {	
								visit.closeVisit(null);
								//	Crea la Linea
								createVisitAndLine(extras);
							} catch(Exception e) {
								Msg.alertMsg(getCtx(), 
										getCtx().getString(R.string.msg_Error), e.getMessage());
								Log.e("Error", "Exception", e);
							}
						}	
					} else 
						createVisitAndLine(extras);
    			} else
    				createVisitAndLine(extras);
	    	}
	    }
	}
	
	/**
	 * Crea la linea de la visita
	 * @author Yamel Senih 20/08/2012, 03:21:57
	 * @return void
	 */
	private void createVisitAndLine(Bundle extras){
		//	Visit
		try {
			//int m_XX_MB_Visit_ID = Env.getTabRecord_ID(this, "XX_MB_Visit");
			
			MBVisit m_visit = (MBVisit) getMP();
			
			if(m_visit.getID() == 0) {
				//	Visit
				m_visit.set_Value("XX_MB_PlanningVisit_ID", Env.getContextAsInt(this, "#XX_MB_PlanningVisit_ID"));
				m_visit.set_Value("DateVisit", Env.getContext(this, "#Date"));
				m_visit.set_Value("DateFrom", Env.getCurrentDateFormat("yyyy-MM-dd hh:mm:ss"));
				m_visit.set_Value("OffCourse", Env.getContext(this, "#OffCourse"));
				m_visit.saveEx();
				
				Env.setContext(this, "#XX_MB_Visit_ID", m_visit.getID());
				Env.setTabRecord_ID(this, "XX_MB_Visit", m_visit.getID());
			}
			
			if(m_visitLine == null){
				m_visitLine = new MBVisitLine(this, 0);
			}
			
			m_visitLine.clear();
			m_visitLine.set_Value("XX_MB_Visit_ID", m_visit.getID());
			m_visitLine.set_Value("XX_MB_EventType_ID", extras.getInt("XX_MB_EventType_ID"));
			m_visitLine.set_Value("Description", extras.getString("Description"));
			m_visitLine.set_Value("DateDoc", Env.getCurrentDateFormat("yyyy-MM-dd hh:mm:ss"));
			
			m_visitLine.saveEx();
			
			refresh();
			loadCurrentRecords();
			
			Env.setContext(this, "#XX_MB_VisitLine_ID", m_visitLine.getID());
			
		} catch (Exception e) {
			Msg.alertMsg(this, getString(R.string.msg_Error), e.getMessage());
			Env.setContext(this, "#XX_MB_Visit_ID", 0);
			Env.setContext(this, "#XX_MB_VisitLine_ID", 0);
		}
	}
	
	/**
	 * Carga los registros de de eventos de la visita
	 * @author Yamel Senih 19/08/2012, 18:05:04
	 * @return
	 * @return boolean
	 */
	private boolean loadCurrentRecords(){
		//	Permission
    	//butt_Add.setEnabled(param.isReadWrite());
    	
		DB con = new DB(this);
		con.openDB(DB.READ_ONLY);
		
		String sql = new String("SELECT " +
				"vl.XX_MB_VisitLine_ID, " +
				"strftime('%H:%M:%S', vl.DateDoc), " +
				"CASE " +
				"	WHEN et.XX_BaseEventType = 'SOO' THEN ord.DocumentNo " +
				"	WHEN et.XX_BaseEventType = 'MII' THEN ci.Description " +
				"	WHEN et.XX_BaseEventType = 'RMS' THEN rm.DocumentNo " +
				"	WHEN et.XX_BaseEventType = 'NSO' THEN vl.Description " +
				"	WHEN et.XX_BaseEventType = 'ARR' THEN ch.DocumentNo " +
				"END Description, " +
				"et.XX_BaseEventType, " +
				"et.XX_MB_EventType_ID, " +
				"et.Name, " +
				"CASE " +
				"	WHEN et.XX_BaseEventType = 'SOO' THEN vl.C_Order_ID " +
				"	WHEN et.XX_BaseEventType = 'MII' THEN vl.XX_MB_CustomerInventory_ID " +
				"	WHEN et.XX_BaseEventType = 'RMS' THEN vl.M_RMA_ID " +
				"	WHEN et.XX_BaseEventType = 'NSO' THEN vl.XX_MB_VisitLine_ID " +
				"	WHEN et.XX_BaseEventType = 'ARR' THEN vl.C_Cash_ID " +
				"END Foreign_ID, " +
				"CASE " +
				"	WHEN et.XX_BaseEventType = 'SOO' THEN 'C_Order' " +
				"	WHEN et.XX_BaseEventType = 'MII' THEN 'XX_MB_CustomerInventory' " +
				"	WHEN et.XX_BaseEventType = 'RMS' THEN 'M_RMA' " +
				"	WHEN et.XX_BaseEventType = 'NSO' THEN '' " +
				"	WHEN et.XX_BaseEventType = 'ARR' THEN 'C_Cash' " +
				"END TableName, " +
				"pv.C_BPartner_Location_ID, " +
				"vl.C_Cash_ID " +
				"FROM XX_MB_Visit v " +
				"INNER JOIN XX_MB_PlanningVisit pv ON(pv.XX_MB_PlanningVisit_ID = v.XX_MB_PlanningVisit_ID) " +
				"INNER JOIN XX_MB_VisitLine vl ON(vl.XX_MB_Visit_ID = v.XX_MB_Visit_ID) " +
				"INNER JOIN XX_MB_EventType et ON(et.XX_MB_EventType_ID = vl.XX_MB_EventType_ID) " +
				"LEFT JOIN C_Order ord ON(ord.C_Order_ID = vl.C_Order_ID) " +
				"LEFT JOIN XX_MB_CustomerInventory ci ON(ci.XX_MB_CustomerInventory_ID = vl.XX_MB_CustomerInventory_ID) " +
				"LEFT JOIN M_RMA rm ON(rm.M_RMA_ID = vl.M_RMA_ID) " +
				"LEFT JOIN C_Cash ch ON(ch.C_Cash_ID = vl.C_Cash_ID) " +
				"WHERE v.XX_MB_Visit_ID = " + Env.getTabRecord_ID(this, "XX_MB_Visit") + " " + 
				"ORDER BY vl.DateDoc");
		Cursor rs = con.querySQL(sql, null);
		data = new LinkedList<DisplayVisitItem>();
		if(rs.moveToFirst()){
			m_C_BPartner_Location_ID = rs.getInt(8);
			do {
				data.add(new DisplayVisitItem(
						rs.getInt(0), 		//	Visit Line ID
						rs.getString(1), 	//	Time of Event
						rs.getString(2), 	//	Description
						rs.getString(3), 	//	Base Event Type
						rs.getInt(4), 		//	Event Type ID
						rs.getString(5), 	//	Value Event Type
						rs.getInt(6),		//	Foreign Key
						rs.getString(7)));	//	Table Name
			} while(rs.moveToNext());
		}
		AdapterVisitLine mi_adapter = new AdapterVisitLine(this, R.layout.il_visit_line, data);
		visitLineList.setAdapter(mi_adapter);
		con.closeDB(rs);
    	return true;    	
	}
	
	/**
	 * Muestra una actividad en forma de dialogo donde 
	 * se colocan los datos del evento
	 * @author Yamel Senih 20/08/2012, 01:30:40
	 * @return void
	 */
	private void addVisitLine(){
		//	Intent
		Intent intent = new Intent(this, MT_AddVisitLine.class);
		//	Start
		startActivityForResult(intent, 0);
	}
	
	/**
	 * Acerca el documento seleccionado
	 * @author Yamel Senih 19/08/2012, 19:27:10
	 * @param item
	 * @return void
	 */
	private void openDocument(DisplayVisitItem item){
		DisplayMenuItem att_Form = DisplayMenuItem.createFromMenu(param);
		
		att_Form.setAD_Table_ID(0);
		//att_Form.setTableName(tableName)
		Env.setTabRecord_ID(this, item.getTableName(), item.getForeign_ID());
		//Msg.toastMsg(getCtx(), item.getTableName());
		att_Form.setRecord_ID(item.getForeign_ID());
		att_Form.setWhereClause(null);
		//	Param
		Bundle bundle = new Bundle();
		bundle.putParcelable("Param", att_Form);
		//	Load Intent
		Intent intent = null;
		
		if(item.getXX_BaseEventType().equals("SOO")){
			intent = new Intent(getApplicationContext(), MT_CustomerOrder.class);
		} else if(item.getXX_BaseEventType().equals("MII")){
			intent = new Intent(getApplicationContext(), MT_CustomerInventory.class);
		} else if(item.getXX_BaseEventType().equals("RMS")){
			intent = new Intent(getApplicationContext(), MT_CustomerRMA.class);
		} else if(item.getXX_BaseEventType().equals("ARR")){
			bundle.putInt("C_BPartner_Location_ID", m_C_BPartner_Location_ID);
			bundle.putInt("C_Cash_ID", item.getForeign_ID());
			bundle.putString("Type", "F");
			intent = new Intent(getApplicationContext(), MV_CustomerCollect.class);
		}
		
		//	Start
		if(intent != null){
			intent.putExtras(bundle);
			//	Start
			startActivity(intent);			
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.m_visit_line, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		//	Get Position
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		final DisplayVisitItem dvi = (DisplayVisitItem) visitLineList.getAdapter().getItem(info.position);
	    switch (item.getItemId()) {
	        case R.id.opt_Delete:
	        	if(param.isReadWrite()){
	        		if(getMP().get_Value("DateTo") == null){
	        			if(!dvi.getXX_BaseEventType().equals("SOO")
	        					&& !dvi.getXX_BaseEventType().equals("MII")
	        					&& !dvi.getXX_BaseEventType().equals("RMS")){
	        				Builder ask = Msg.confirmMsg(this, getResources().getString(R.string.msg_AskDelete) + 
				        			"\n\"" + dvi.getDescription() + "\"");
				        	String msg_Acept = this.getResources().getString(R.string.msg_Yes);
				        	ask.setPositiveButton(msg_Acept, new DialogInterface.OnClickListener() {
				    			public void onClick(DialogInterface dialog, int which) {
				    				m_visitLine = new MBVisitLine(getCtx(), dvi.getRecord_ID());
				    				m_visitLine.delete();
				    	        	loadCurrentRecords();
				    			}
				    		});
				        	ask.show();	
	        			} else {
			        		Msg.alertMsg(this, getResources().getString(R.string.msg_ProcessError), 
			            			getResources().getString(R.string.msg_CantDEDocument));
			        	}
	        		} else {
		        		Msg.alertMsg(this, getResources().getString(R.string.msg_ProcessError), 
		            			getResources().getString(R.string.msg_VisitClosed));
		        	}
	        	} else {
	        		Msg.alertMsg(this, getResources().getString(R.string.msg_ProcessError), 
	            			getResources().getString(R.string.msg_ReadOnly));
	        	}
	            return true;
	        case R.id.opt_ProductInfo:
	        	
	        	return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}

	
}
