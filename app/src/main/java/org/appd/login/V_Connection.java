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
 * Copyright (C) 2012-2013 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.appd.login;

import java.io.File;

import org.appd.base.DB;
import org.appd.base.Env;
import org.appd.base.R;
import org.appd.interfaces.I_Login;
import org.appd.sync.read.InitialLoad;
import org.appd.util.Msg;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RemoteViews;

/**
 * 
 * @author Yamel Senih
 *
 */
public class V_Connection extends Activity implements I_Login {

	/**	URL SOAP Comunication	*/
	private EditText et_UrlSoap;
	/**	Synchronization Method	*/
	private EditText et_Method;
	/**	Limit Query				*/
	private EditText et_LimitQuery;
	/**	Timeout					*/
	private EditText et_Timeout;
	/** NameSpace*/
	private EditText et_NameSpace;
	/** Soap Object InitialLoad	*/
	private InitialLoad m_load ;
	/**	Sync					*/
	private Button butt_InitSync;
	/**	Save data SD			*/
	private CheckBox ch_SaveSD;
	
    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        super.setContentView(R.layout.v_connection);
        
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        et_UrlSoap = (EditText) findViewById(R.id.et_UrlSoap);
    	et_Method = (EditText) findViewById(R.id.et_Method);
    	et_LimitQuery = (EditText) findViewById(R.id.et_LimitQuery);
    	et_Timeout = (EditText) findViewById(R.id.et_Timeout);
    	et_NameSpace = (EditText) findViewById(R.id.et_NameSpace);
    	ch_SaveSD = (CheckBox) findViewById(R.id.ch_SaveSD);
    	//red1 using et_User.getText().toString().trim();
    	// Carlos Parada Initializing for not writing the same thing all the time
    	//red1 allow user setting
    	et_UrlSoap.setText("http://prim.ghintech.com:8091/ADInterface/services/AppDroidServices?wsdl");
      	et_UrlSoap.getText().toString().trim();
      	//DO NOT CHANGE NameSpace value
    	et_NameSpace.setText("http://www.erpconsultoresasociados.com/");
    	et_Method.setText("InitialLoad");
    	//Fin Carlos Parada
    	//butt_Test = (Button) findViewById(R.id.butt_Test);
    	butt_InitSync = (Button) findViewById(R.id.butt_InitSync);
    	//butt_InitSyncManual = (Button) findViewById(R.id.butt_InitSyncManual);
    	
    	//lockFront();
    	
    	//	Init Sync
    	butt_InitSync.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				if(valid()){
					synchronize();
					lockFront();
				}
			}
		});
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, Login.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    /**
     * loock the field when sync is done
     * @author Yamel Senih 03/05/2012, 15:47:26
     * @return void
     */
    public void lockFront(){
    	if(!Env.isEnvLoad(this)){
    		et_UrlSoap.setEnabled(true);
    		et_Method.setEnabled(true);
    		et_NameSpace.setEnabled(true);
    		ch_SaveSD.setEnabled(true);
    		//ch_SaveSD.setChecked(true);
    	} else {
    		et_UrlSoap.setEnabled(false);
    		et_Method.setEnabled(false);
    		et_NameSpace.setEnabled(false);
    		ch_SaveSD.setEnabled(false);
    		//Sets the timeout in the Window
    		//Carlos Parada 04/11/2012
    		setTimeOut();
    		//Fin Carlos Parada
    	}
    }
    
    
    /**
     * Set Timeout in Activity
     * @author Carlos Parada 04/11/2012, 19:59:01
     * @return void
     */
    public void setTimeOut()
    {
    	//
		int timeoutInt = Env.getContextAsInt(this, "#Timeout");
		String timeout = String.valueOf(timeoutInt);
		et_Timeout.setText(timeout);
    }
    
    
    /**
     * Action Initial Synchronization
     * @author Yamel Senih 24/04/2012, 00:14:42
     * 		   Carlos Parada 17/05/2012 I put the initial charge is automatic from adempiere
     * @return void
     */

	private void synchronize(){
    	
    	 m_load = new InitialLoad(this){
    		
    		@Override
    		protected void onPostExecute(Object result) {
    			super.onPostExecute(result);
    			//	Load Context
    			loadContext();
    		}
    	};
    	
    	m_load.LoadSoapFromContext(this);
    		
		if(!Env.isEnvLoad(this)){
			RemoteViews contentView = new RemoteViews(this.getPackageName(), R.layout.v_progressdialog);
	        contentView.setImageViewResource(R.id.iV_Synchronizing, R.drawable.syncserver_m);
	        contentView.setTextViewText(R.id.tV_CurrentSinchronizing, this.getResources().getString(R.string.msg_CallingWebService));
	        contentView.setTextViewText(R.id.tV_Percentaje, "0%");
	      
	        //NotificationManager notify = Msg.notificationMsg(this, R.drawable.syncserver_h, "",0, this.getParentActivityIntent(), contentView);
			NotificationManager notify = Msg.notificationMsg(this,R.drawable.syncserver_h,"",0,this.getIntent(),contentView);
	        m_load.setContentView(contentView);
	        m_load.setM_NotificationManager(notify);
			m_load.execute();
			
		} else {
			loadContext();
		}
    
    }
    
    /**
     * Load Context Data
     * @author Yamel Senih 17/10/2012, 16:46:40
     * @return void
     */
    private void loadContext(){
    	/**
		 * Carlos Parada, Load var in comntext
		 */
    	
		if (Env.isEnvLoad(this))
		{	
			String sql = new String("SELECT sc.Name, sc.Value FROM AD_SysConfig sc");
	    	DB con = new DB(this);
	    	con.openDB(DB.READ_ONLY);
	    	Cursor rs = con.querySQL(sql, null);
	    	if(rs.moveToFirst()){
				do {
					Env.setContext(this, "#" + rs.getString(0), rs.getInt(1));
				} while(rs.moveToNext());
			}
	    	con.closeDB(rs);
	    		
		}
    }
    
    /**
     * Valid fields on Activity
     * @author Yamel Senih 24/04/2012, 12:33:57
     * @return
     * @return boolean
     */
    private boolean valid(){
    	if(et_UrlSoap.getText() != null 
    			&& et_UrlSoap.getText().toString().length() > 0){
    		if(et_Method.getText() != null 
    				&& et_Method.getText().toString().length() > 0){
    			if(et_NameSpace.getText() != null 
        				&& et_NameSpace.getText().toString().length() > 0){
    				Env.setContext(this, "#SUrlSoap", et_UrlSoap.getText().toString());
    				Env.setContext(this, "#SMethod", et_Method.getText().toString());
    				Env.setContext(this, "#SNameSpace", et_NameSpace.getText().toString());
    				Env.setContext(this, "#SaveSD", ch_SaveSD.isChecked());
    				createDirectory();
    				if(et_LimitQuery.getText() != null 
    	    				&& et_LimitQuery.getText().toString().length() > 0){
    					String limit = et_LimitQuery.getText().toString();
    					Env.setContext(this, "#LimitQuery", Integer.parseInt(limit));
    				}
    				
    				if(et_Timeout.getText() != null 
    	    				&& et_Timeout.getText().toString().length() > 0){
    					String limit = et_Timeout.getText().toString();
    					Env.setContext(this, "#Timeout", Integer.parseInt(limit));
    				}
    				
    	    		return true;
    			} else {
            		Msg.alertMustFillField(this, R.string.NameSpace, et_NameSpace);
            	}
        	} else {
        		Msg.alertMustFillField(this, R.string.MethodSync, et_Method);
        	}
    	} else {
    		Msg.alertMustFillField(this, R.string.Url_Soap, et_UrlSoap);
    	}
    	return false;
    }
    
    /**
	 * Create the directory where data will be stored
	 * From the database, if there is SD only places the name
	 * @author Yamel Senih 19/08/2012, 05:45:05
	 * @return void
	 */
	private void createDirectory(){
		if(!Env.isEnvLoad(this)){
			if(Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)
					&& ch_SaveSD.isChecked()){
				String dbpath = Environment.getExternalStorageDirectory() + DB.getDB_Path();
				String dbPathName = Environment.getExternalStorageDirectory() + DB.getDB_PathName();
				File f = new File(dbpath);
				if(!f.exists()) {
					if(f.mkdirs()){
						Env.setDB_Path(this, dbPathName);
					} else {
						Env.setDB_Path(this, DB.DB_NAME);
					}
				} else if(f.isDirectory()) {
					File fDB = new File(dbPathName);
					fDB.delete();
					Env.setDB_Path(this, dbPathName);
				} else if(f.isFile()){
					if(f.mkdirs()){
						Env.setDB_Path(this, dbPathName);
					} else {
						Env.setDB_Path(this, DB.DB_NAME);
					}
				}
			} else {
				Env.setDB_Path(this, DB.DB_NAME);
			}	
    	}
	}
    
	
    @Override
    public void onStart() {
        super.onStart();
        String url = et_UrlSoap.getText().toString();
    	String method = et_Method.getText().toString();
    	String limit = et_LimitQuery.getText().toString();
    	String timeout = et_Timeout.getText().toString();
    	String nameSpace = et_NameSpace.getText().toString();
    	//	Load URL SOAP
    	if(url == null || url.length() == 0){
    		url = Env.getContext(this, "#SUrlSoap");
    		if(url != null)
    			et_UrlSoap.setText(url);
    	}
    	//	Load Method
    	if(method == null || method.length() == 0){
    		method = Env.getContext(this, "#SMethod");
    		if(method != null)
    			et_Method.setText(method);
    	}
    	//	Name Space
    	if(nameSpace == null || nameSpace.length() == 0){
    		nameSpace = Env.getContext(this, "#SNameSpace");
    		if(nameSpace != null)
    			et_NameSpace.setText(nameSpace);
    	}
    	//	Limit Query
    	if(limit == null || limit.length() == 0){
    		int limitInt = Env.getContextAsInt(this, "#LimitQuery");
    		limit = String.valueOf(limitInt);
    		et_LimitQuery.setText(limit);
    	}
    	//	Timeout
    	if(timeout == null || timeout.length() == 0){
    		int timeoutInt = Env.getContextAsInt(this, "#Timeout");
    		timeout = String.valueOf(timeoutInt);
    		et_Timeout.setText(timeout);
    	}
    	
    	//	Save SD
    	ch_SaveSD.setChecked(Env.getContextAsBoolean(this, "#SaveSD"));
    	
    	lockFront();
    	
    }
	
    @Override
    public void onResume() {
        super.onResume();
        lockFront();
    }
	
    @Override
	public boolean acceptAction() {
		if(valid())
			return true;
		return false;
	}

	@Override
	public boolean cancelAction() {
		return false;
	}

	@Override
	public boolean loadData() {
		return false;
	}
    
}
