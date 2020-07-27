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
import org.appd.model.MBCustomerInventoryLine;
import org.appd.model.MP;
import org.appd.util.AdapterRecordItem;
import org.appd.util.Att_ColumnRecord;
import org.appd.util.DisplayMenuItem;
import org.appd.util.DisplayRecordItem;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * @author Yamel Senih
 *
 */
public class MV_BPartnerPlanningVisit extends MVActivity {
	
	private LinkedList<DisplayRecordItem> data = new LinkedList<DisplayRecordItem>();
	private ListView lv_planning_visit;
	private Att_ColumnRecord []identifierColumns;
	private M_MenuPlanningVisit loadDetail;
	
	//private BigDecimal totalLines;
	//private BigDecimal grandTotal;
	//private boolean updateHeap = false;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	super.setContentView(R.layout.lv_bp_planning_visit);
    	
    	Bundle bundle = getIntent().getExtras();
    	if(bundle != null){
			param = (DisplayMenuItem)bundle.getParcelable("Param");
		} 
		if(param == null)
			throw new IllegalArgumentException ("No Param");
    	
    	lv_planning_visit = (ListView)findViewById(R.id.ls_bp_planning_visit);
		
		lv_planning_visit.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> padre, View v, int position,
					long id) {
				//	Zoom a Detail
				lookupVisit(v,((DisplayRecordItem) padre.getItemAtPosition(position)).getRecord_ID());
			}
		}); 
		//	instance Quick Action
		loadDetail = new M_MenuPlanningVisit();
		
    }
    
    /**
     * Va a la Visita y muestra los eventos
     * @author Yamel Senih 03/08/2012, 19:14:20
     * @return void
     */
    private void lookupVisit(View v, int id){
    	param.setRecord_ID(id);
    	param.setIsReadWrite(false);
    	param.setInsertRecord(false);
    	param.setIsReadWriteM("N");
		loadDetail.startLoad(this, v, param);
    }
    
    /**
     * Carga un listado con las diferentes 
     * planificaciones del socio de negocio
     * @author Yamel Senih 03/08/2012, 18:08:20
     * @return
     * @return boolean
     */
    private boolean loadRecords(){    	
    	
    	identifierColumns = new Att_ColumnRecord []{
    			new Att_ColumnRecord("ImgName", null, 0, null), 
    			new Att_ColumnRecord("''", null, 0, null), 
    			new Att_ColumnRecord("''", null, 0, null), 
    			new Att_ColumnRecord("''", null, 0, null),
    			new Att_ColumnRecord("''", null, 0, null),
    			new Att_ColumnRecord("''", null, 0, null)
    			};
    	
		String sql = new String("SELECT " +
				"pv.XX_MB_PlanningVisit_ID, " +	//	Record_ID
				"IFNULL((" +
				"	IFNULL((" +
				"		SELECT " +
				"			CASE " +
				"				WHEN SUM(v.XX_MB_Visit_ID) > 0 AND v.DateTo IS NOT NULL THEN 'pship_h' WHEN  SUM(v.XX_MB_Visit_ID) > 0 AND v.DateTo IS NULL " +
				"				THEN 'meet_h' " +
				"				ELSE 'klipper_h' " +
				"			END " +
				"		FROM XX_MB_Visit v " +
				"		WHERE v.XX_MB_PlanningVisit_ID = pv.XX_MB_PlanningVisit_ID LIMIT 1), 'klipper_h')), ''), " +	//	ImgName
				"dy.Name MBDay, " +
				"lo.Name Location, " +
				"fr.Name Frequency " +
				"FROM XX_MB_PlanningVisit  pv " +
				"INNER  JOIN C_BPartner cp ON(cp.C_BPartner_ID = pv.C_BPartner_ID) " +
				"INNER JOIN C_BPartner_Location lo ON(lo.C_BPartner_Location_ID = pv.C_BPartner_Location_ID) " +
				"INNER JOIN XX_MB_Frequency fr ON(fr.XX_MB_Frequency_ID = pv.XX_MB_Frequency_ID) " +
				"INNER JOIN XX_MB_Day dy ON(dy.XX_MB_Day_ID = pv.XX_MB_Day_ID) " +
				"WHERE cp.C_BPartner_ID = " + Env.getTabRecord_ID(this, "C_BPartner") + " " + 
				"ORDER BY pv.SeqNo");
		
		Log.i("MV_BPartnerPlanningVisit", "SQL: " + sql);
		
		DB con = new DB(this);
		con.openDB(DB.READ_ONLY);
		
		Cursor rs = con.querySQL(sql, null);
		data = new LinkedList<DisplayRecordItem>();
		if(rs.moveToFirst()){
			do {
				data.add(new DisplayRecordItem(
						rs.getInt(0), 
						identifierColumns, 
						rs.getString(1), 
						rs.getString(2), 
						rs.getString(3), 
						rs.getString(4), 
						null, 
						null));
			} while(rs.moveToNext());
		}
		
		con.closeDB(rs);
		
		AdapterRecordItem mi_adapter = null;
		mi_adapter = new AdapterRecordItem(this, R.layout.il_record, data, "F");
		mi_adapter.setDropDownViewResource(R.layout.il_record);
		lv_planning_visit.setAdapter(mi_adapter);
		
    	return true;
    }
    
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadRecords();
    }
    @Override
    protected void onPause() {
        super.onPause();
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

	/* (non-Javadoc)
	 * @see org.appd.view.MVActivity#getCustomMP()
	 */
    @Override
	public MP getCustomMP() {
		return new MBCustomerInventoryLine(getCtx(), 0);
	}

	@Override
	public MP getCustomMP(int id) {
		return new MBCustomerInventoryLine(getCtx(), id);
	}

	@Override
	public MP getCustomMP(Cursor rs) {
		return new MBCustomerInventoryLine(getCtx(), rs);
	}
	
	/* (non-Javadoc)
	 * @see org.appd.view.MVActivity#getActivityName()
	 */
	@Override
	protected String getActivityName() {
		return "XX_MB_VPartnerPlanningVisit";
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
	    if (resultCode == RESULT_OK) {
	    	//
	    }
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
	    super.onCreateContextMenu(menu, v, menuInfo);
	}

}
