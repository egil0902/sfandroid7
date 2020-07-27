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
import org.appd.util.AdapterMenuItem;
import org.appd.util.DisplayMenuItem;
import org.appd.util.Msg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * @author Yamel Senih
 *
 */

public class MV_Menu extends Activity {
	
	private LinkedList<DisplayMenuItem> data = new LinkedList<DisplayMenuItem>();
	private Context ctx;
	private ListView menuList;
	private DisplayMenuItem item;
	private DB con = null;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	super.setContentView(R.layout.lv_menu);
    	
    	ctx = this;
    	Bundle extras = getIntent().getExtras();
    	if(extras != null){
    		item = (DisplayMenuItem) extras.getParcelable("Item");
    		setTitle(item.getName());
    	}
    	
    	menuList=(ListView) findViewById(R.id.ls_Menu);
    	
    	load();
    	
    	menuList.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> padre, View v, int position,
					long id) {
				
				//	Msg.toastMsg(ctx, ((DisplayMenuItem)padre.getItemAtPosition(position)).getName());
				
				item = (DisplayMenuItem)padre.getItemAtPosition(position);
				
				//	Param
				Bundle bundle = new Bundle();
				bundle.putParcelable("Item", item);
				//	Intent Activity
				Intent intent = null;
				if(item.getIsSummary()){
					intent = new Intent(ctx.getApplicationContext(), MV_Menu.class);
					intent.putExtras(bundle);
					ctx.startActivity(intent);
				} else if(item.getAction().equals("F")){			//	Action Form
					//	Valid If Current Date
					if(Env.getContext(ctx, "#IsCurrentDate").equals("N")){
						item.setIsReadWriteM("N");
						item.setInsertRecord("N");
					}
					if(item.getActivityType().equals("S") 			//	Search
							|| item.getActivityType().equals("M")	//	Menu
							|| item.getActivityType().equals("L")){	//	List
						intent = new Intent(ctx.getApplicationContext(), MV_ListRecord.class);
						intent.putExtras(bundle);
						ctx.startActivity(intent);
					} else if(item.getActivityType().equals("C")){	//	Custom Activity
						loadActivity(item);
					}
				} else if(item.getAction().equals("R")){
					intent = new Intent(ctx.getApplicationContext(), MV_ListRecord.class);
					intent.putExtras(bundle);
					ctx.startActivity(intent);
				}
			}
		});		
    }
    
    /**
     * Carga una Actividad a partir de el menú
     * @author Yamel Senih 20/05/2012, 21:24:33
     * @param item
     * @return
     * @return boolean
     */
    private boolean loadActivity(DisplayMenuItem item){
    	boolean ok = false;
    	Class<?> clazz;
		try {
			
			clazz = Class.forName(item.getClassName());
			/*ImpParamActivity param = new ImpParamActivity(item.getRecord_ID(), item.getAD_Table_ID(), 
					isReadWrite, item.getActivityType());
			param.setActivityName(item.getName());
			param.setIsInsertRecord(isInsertRecord);*/
			//	Param
			Bundle bundle = new Bundle();
			bundle.putParcelable("Param", item);
			//	Load Intent
			Intent intent = new Intent(getApplicationContext(), clazz);
			intent.putExtras(bundle);
			//	Start
			startActivity(intent);
			ok = true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			Msg.alertMsg(this, getResources().getString(R.string.msg_LoadError), 
					getResources().getString(R.string.msg_ClassNotFound));
		}
		return ok;
    }
    
    /**
     * Carga el Menu a partir de una consulta
     * @author Yamel Senih 28/04/2012, 16:40:50
     * @return
     * @return boolean
     */
    private boolean load(){
		loadConnection();
    	//DB con = new DB(ctx, Env.getDB_Name(ctx), Env.getDB_Version(ctx));
		con.openDB(DB.READ_ONLY);
		
		String sql = new String("SELECT f.AD_Form_ID, " +
				"CASE " +
				"	WHEN ml.Name IS NULL THEN " +
				"	CASE " +
				"		WHEN ml.Action = 'F' AND f.AD_Form_ID IS NOT NULL THEN f.Name " +
				"		WHEN ml.Action IN('R', 'P') AND p.AD_Process_ID IS NOT NULL THEN p.Name " +
				"	END " +
				"	ELSE ml.Name " +
				"END Name, " +
				"CASE " +
				"	WHEN ml.Description IS NULL THEN " +
				"	CASE " +
				"		WHEN ml.Action = 'F' AND f.AD_Form_ID IS NOT NULL THEN f.Name " +
				"		WHEN ml.Action IN('R', 'P') AND p.AD_Process_ID IS NOT NULL THEN p.Name " +
				"	END " +
				"	ELSE ml.Description " +
				"END Description, " +
				"ml.Action, " +
				"ml.ImgName, " +
				"CASE " +
				"	WHEN ml.Action = 'F' AND f.AD_Form_ID IS NOT NULL THEN f.ClassName " +
				"	WHEN ml.Action IN('R', 'P') AND p.AD_Process_ID IS NOT NULL THEN p.ClassName " +
				"END " +
				"ClassName, " +
				"IFNULL(CASE " +
				"	WHEN ml.Action = 'F' THEN fa.IsReadWrite " +
				"	WHEN ml.Action IN('R', 'P') THEN pa.IsReadWrite " +
				"	WHEN ml.IsSummary = 'Y' THEN 'Y'" +
				"	ELSE 'N' END, 'N') IsReadWrite, " +
				"ml.AD_Table_ID, " +
				"ta.TableName, " +
				"ml.WhereClause, " +
				"ml.GroupByClause, " +
				"ml.OrderByClause, " +
				"ml.XX_MB_MenuList_ID, " +
				"ml.IsSummary, " +
				"ml.ActivityType, " +
				"ml.AD_Process_ID, " +
				"p.ClassName, " +
				"ml.IsReadWrite, " +
				"ml.IsInsertRecord " +
				"FROM XX_MB_MenuList ml " +
				"LEFT JOIN AD_Form f ON(f.AD_Form_ID = ml.AD_Form_ID) " +
				"LEFT JOIN AD_Table ta ON(ta.AD_Table_ID = ml.AD_Table_ID) " +
				"LEFT JOIN AD_Process p ON(p.AD_Process_ID = ml.AD_Process_ID) " +
				"LEFT JOIN AD_Form_Access fa ON(fa.AD_Form_ID = f.AD_Form_ID) " +
				"LEFT JOIN AD_Process_Access pa ON(pa.AD_Process_ID = p.AD_Process_ID) " +
				"WHERE (fa.AD_Role_ID = " + Env.getAD_Role_ID(this) + " OR ml.Action IN('P', 'R') OR ml.IsSummary = 'Y') " +
				"AND (pa.AD_Role_ID = " + Env.getAD_Role_ID(this) + " OR ml.Action IN('F') OR ml.IsSummary = 'Y') ");
		if(item != null && item.getParentMenuList_ID() > 0){
			sql += " AND ml.XX_MB_ParentMenuList_ID = " + item.getParentMenuList_ID();
		} else {
			sql += " AND (ml.XX_MB_ParentMenuList_ID = 0 OR ml.XX_MB_ParentMenuList_ID IS NULL) ";
		}
		sql += " AND ml.Action IN('F', 'P', 'R', 'L') ";
		sql += " ORDER BY ml.SeqNo";
		
		//Log.i("SQL Menu", " " + sql);
		Cursor rs = con.querySQL(sql, null);
		data = new LinkedList<DisplayMenuItem>();
		if(rs.moveToFirst()){
			do {
				data.add(new DisplayMenuItem(rs.getInt(0), 
						rs.getString(1), 
						rs.getString(2), 
						rs.getString(3), 
						rs.getString(4), 
						rs.getString(5),
						rs.getString(6).equals("Y"),
						rs.getInt(7),
						rs.getString(8),
						rs.getString(9),
						rs.getString(10),
						rs.getString(11),
						rs.getInt(12),
						rs.getString(13).equals("Y"), 
						rs.getString(14),
						rs.getInt(15),
						rs.getString(16),
						rs.getString(17), 
						rs.getString(18)));
				//Log.i("-- ** --", " " + rs.getString(16) + " -- " + rs.getString(17));
			} while(rs.moveToNext());
		}
		closeConnection(rs);
		AdapterMenuItem mi_adapter = new AdapterMenuItem(this, R.layout.il_menu, data);
		mi_adapter.setDropDownViewResource(R.layout.il_menu);
		menuList.setAdapter(mi_adapter);
		//con.closeDB();
		
    	return true;
    }
    
    /**
     * Verifica y establece conexion
     * @author Yamel Senih 01/05/2012, 21:11:03
     * @return void
     */
    private void loadConnection(){
    	if(con == null)
			con = new DB(this);
		if(!con.isOpen())
			con.openDB(DB.READ_ONLY);
    }
    
    /**
     * Cierra la Conexion a la BD
     * @author Yamel Senih 04/05/2012, 21:29:05
     * @return void
     */
    private void closeConnection(Cursor rs){
    	if(con != null && con.isOpen())
			con.closeDB(rs);
    }
    
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
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
}
