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
import org.appd.util.AdapterBPartnerLocation;
import org.appd.util.DisplayBPLocationItem;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.ListView;

/**
 * @author Yamel Senih
 *
 */
public class MV_BPartnerLocation extends MVActivity {
	
	private LinkedList<DisplayBPLocationItem> data = new LinkedList<DisplayBPLocationItem>();
	private ListView bpartnerLocationList;
	//private Button butt_Add;
	
	//private BigDecimal totalLines;
	//private BigDecimal grandTotal;
	//private boolean updateHeap = false;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	super.setContentView(R.layout.lv_bpartner_location);
    	
    	/*butt_Add = (Button)findViewById(R.id.butt_AddModify);
    	butt_Add.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				loadProductList();
			}
		});*/
    	bpartnerLocationList = (ListView)findViewById(R.id.ls_bpartner_location);
    	
    	/*//load();
    	*/
    	/*bpartnerLocationList.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> padre, View v, int position,
					long id) {
				
				//	Star Activity
			}
		});
    	
    	registerForContextMenu(bpartnerLocationList);*/
    }
    
    
    private boolean loadRecords(){
		DB con = new DB(this);
		con.openDB(DB.READ_ONLY);
		/*String sql = new String("SELECT " +
				"cpl.C_BPartner_Location_ID, " +
				"cpl.Name, " +
				"cpl.Phone, " +
				"cpl.Phone2, " +
				"'' Fax, " +
				"'' Region, " +
				"'' City, " +
				"'' Address1, " +
				"'' Address2, " +
				"'' Address3, " +
				"'' Address4, " +
				"'' Latitude, " +
				"'' Longitude, " +
				"cpl.IsBillTo, " +
				"cpl.IsShipTo " +
				"FROM C_BPartner_Location cpl " +
				"WHERE cpl.C_BPartner_ID = " + 
				Env.getTabRecord_ID(this, "C_BPartner") + " " + 
				"ORDER BY cpl.Name");*/
		String sql = new String("SELECT " +
				"cpl.C_BPartner_Location_ID, " +
				"cpl.Name, " +
				"cpl.Phone, " +
				"cpl.Phone2, " +
				"cpl.Fax, " +
				"re.Name Region, " +
				"ci.Name City, " +
				"lo.Address1, " +
				"lo.Address2, " +
				"lo.Address3, " +
				"lo.Address4, " +
				"cpl.Latitude, " +
				"cpl.Longitude, " +
				"cpl.IsBillTo, " +
				"cpl.IsShipTo " +
				"FROM C_BPartner_Location cpl " +
				"INNER JOIN C_Location lo ON(lo.C_Location_ID = cpl.C_Location_ID) " +
				"LEFT JOIN C_Region re ON(re.C_Region_ID = lo.C_Region_ID) " +
				"LEFT JOIN C_City ci ON(ci.C_City_ID = lo.C_City_ID) " + 
				"WHERE cpl.C_BPartner_ID = " + 
				Env.getTabRecord_ID(this, "C_BPartner") + " " + 
				"ORDER BY cpl.Name");
		Cursor rs = con.querySQL(sql, null);
		data = new LinkedList<DisplayBPLocationItem>();
		if(rs.moveToFirst()){
			do {
				data.add(new DisplayBPLocationItem(
						rs.getInt(0), 
						rs.getString(1), 
						null, 
						rs.getString(2), 
						rs.getString(3), 
						rs.getString(4), 
						rs.getString(5), 
						rs.getString(6), 
						rs.getString(7), 
						rs.getString(8), 
						rs.getString(9), 
						rs.getString(10), 
						rs.getString(11), 
						rs.getString(12), 
						(rs.getString(13) != null && rs.getString(13).equals("Y")? true: false), 
						(rs.getString(14) != null && rs.getString(14).equals("Y")? true: false)));
			} while(rs.moveToNext());
		}
		AdapterBPartnerLocation mi_adapter = new AdapterBPartnerLocation(this, data);
		mi_adapter.setDropDownViewResource(R.layout.il_menu);
		bpartnerLocationList.setAdapter(mi_adapter);
		con.closeDB(rs);
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
		return "C_BPartner_Location";
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
