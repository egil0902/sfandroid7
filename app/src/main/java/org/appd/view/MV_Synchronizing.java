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
 * Contributor(s): Carlos Parada www.erpconsultoresyasociados.com                    *
 *************************************************************************************/
package org.appd.view;

import java.util.LinkedList;


import org.appd.base.DB;
import org.appd.base.Env;
import org.appd.base.R;
import org.appd.sync.services.CallServices;
import org.appd.util.AdapterSyncItem;
import org.appd.util.DisplaySyncItem;
import org.appd.util.Msg;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.RemoteViews;

/**
 * @author Carlos Parada
 *
 */
@SuppressWarnings("unused")
public class MV_Synchronizing extends Activity implements OnItemLongClickListener,OnItemSelectedListener{
	
	private LinkedList<DisplaySyncItem> data = new LinkedList<DisplaySyncItem>();
	private Context ctx;
	private ListView syncList;
	private int m_listselected;
	private DisplaySyncItem item;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.v_sync);
		ctx = this;
		
    	syncList=		(ListView) findViewById(R.id.ls_Sync);
    	
    	Bundle extras = getIntent().getExtras();
    	if(extras != null){
    		item = (DisplaySyncItem) extras.getSerializable("ItemSync");
    		
    		setTitle((item!=null?item.getName():getResources().getString(R.string.msg_Synchronization)));
    	}
    	
    	load((item!=null?item.getRecord_ID():null));
    	
    	syncList.setOnItemLongClickListener(this);
		
    	syncList.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> padre, View v, int position,
					long id) {
				
				item = (DisplaySyncItem)padre.getItemAtPosition(position);
				if (item.getM_NumberChildren()!=0)
				{
					//	Param
					Bundle bundle = new Bundle();
					bundle.putSerializable("ItemSync", item);
					//	Intent Activity
					Intent intent = null;
					intent = new Intent(ctx.getApplicationContext(), MV_Synchronizing.class);
					intent.putExtras(bundle);
					ctx.startActivity(intent);
				}
			}
		});		
	}
	
	
	/**
     * Load Services from a query
     * @author Carlos Parada 31/05/2012
     * @return
     * @return boolean
     */
    private boolean load(Integer parentID){
		//	Modified by Yamel Senih by changes in the Constructor
   	 	//	19/08/2012 04:29
   	 	//	DB con = new DB(m_ctx, Env.getDB_Name(m_ctx), Env.getDB_Version(m_ctx));
		DB con = new DB(ctx);
   	 	//	Fin Yamel Senih
		con.openDB(DB.READ_ONLY);
		
		String sql = new String("select XX_MB_MenuList_ID," +
										"Name," +
										"Description," +
										"Case When (Select ST.EventType From XX_MB_SynchronizingTrace ST Where ST.XX_MB_MenuList_ID=ML.XX_MB_MenuList_ID Order By  ST.EndSync Desc Limit 1)='E' Then  ErrImgName " +
										"Else ImgName End as ImgName,"+
										"IsSummary, " +
										"(Select Count(1) From XX_MB_MenuList SM Where SM.XX_MB_ParentMenuList_ID=ML.XX_MB_MenuList_ID) as NumberChidren, " +
										"MenuType " +
										"From XX_MB_MenuList ML Where IsActive ='Y' And ML.MenuType!='M' And ML.XX_MB_ParentMenuList_ID " +(parentID==null?"Is Null ":"="+parentID) +
										" Order By SeqNo" );
		
		Cursor rs = con.querySQL(sql, null);
		data = new LinkedList<DisplaySyncItem>();
		if(rs.moveToFirst()){
			do {
				data.add(new DisplaySyncItem(rs.getInt(0), 
						rs.getString(1), 
						rs.getString(2), 
						rs.getString(3),
						rs.getString(4),
						rs.getInt(5),
						rs.getString(6)
						));
			} while(rs.moveToNext());
			AdapterSyncItem mi_adapter = new AdapterSyncItem(this, R.layout.il_sync, data);
			mi_adapter.setDropDownViewResource(R.layout.il_sync);
			syncList.setAdapter(mi_adapter);
		}
		con.closeDB(rs);
    	return true;
    }

	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemLongClickListener#onItemLongClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) { 
		
		
		// TODO Auto-generated method stub
		String l_NameService;
		Integer l_RecordID;
		String l_ServiceType;

		m_listselected=position;
		
		l_NameService = ((DisplaySyncItem) syncList.getItemAtPosition(m_listselected)).getName();
		l_RecordID =((DisplaySyncItem) syncList.getItemAtPosition(m_listselected)).getRecord_ID();
		l_ServiceType = ((DisplaySyncItem) syncList.getItemAtPosition(m_listselected)).getM_MenuType();
		
		
		Builder ask = Msg.confirmMsg(this, getResources().getString(R.string.SynchronzeButtom)+ " " +l_NameService+ "?");
    	String msg_Acept = this.getResources().getString(R.string.msg_Yes);
    	ask.setPositiveButton(msg_Acept, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				callService();
					
			}
		});
    	ask.show();
		return true;
	}
	/**
	 * synchronization method call
	 * @author Carlos Parada 06/08/2012, 04:10:04
	 * @return void
	 */
	@SuppressWarnings("unchecked")
	private void callService()
	{
		String l_NameService;
		Integer l_RecordID;
		String l_ServiceType;

		
		l_NameService = ((DisplaySyncItem) syncList.getItemAtPosition(m_listselected)).getName();
		l_RecordID =((DisplaySyncItem) syncList.getItemAtPosition(m_listselected)).getRecord_ID();
		l_ServiceType = ((DisplaySyncItem) syncList.getItemAtPosition(m_listselected)).getM_MenuType();
		
		RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.v_progressdialog);
        contentView.setImageViewResource(R.id.iV_Synchronizing, R.drawable.syncserver_m);
        contentView.setTextViewText(R.id.tV_CurrentSinchronizing, l_NameService);
        contentView.setTextViewText(R.id.tV_Percentaje, "0%");
        
        NotificationManager notify = Msg.notificationMsg(this, R.drawable.syncserver_h, l_NameService,1, this.getIntent(), contentView);
        CallServices serv = new CallServices(this, l_RecordID,l_ServiceType,notify);
        serv.execute();
	}
	
	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(android.widget.AdapterView)
	 */
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

}
