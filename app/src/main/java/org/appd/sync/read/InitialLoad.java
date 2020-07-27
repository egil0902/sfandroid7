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
 * Contributor(s): Carlos Parada www.erpconsultoresyasociados.com                    *
 *************************************************************************************/
package org.appd.sync.read;
import java.io.IOException;
import java.util.ArrayList;

import org.appd.base.DB;
import org.appd.base.Env;
import org.appd.base.R;
import org.appd.conn.CommunicationSoap;
import org.appd.login.V_Connection;
import org.appd.util.Msg;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.xmlpull.v1.XmlPullParserException;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * @author Carlos Parada
 *
 */
@SuppressWarnings("rawtypes")
@SuppressLint({ "UseValueOf", "UseValueOf", "UseValueOf" })
public class InitialLoad extends AsyncTask{
	private String m_URL;
	private String m_Method;
	private CommunicationSoap m_soap;
	private String m_NameSpace;
	private boolean success=false;
	private boolean finish =false;
	private Context m_ctx;
	private RemoteViews contentView;
	private int qty_serv,progress_serv;
	private String current_service;
	private String before_service;
	private NotificationManager m_NotificationManager;
	private V_Connection connF;
	/**
	 * *** Constructor ***
	 * @author Yamel Senih 06/08/2012, 11:06:43
	 */
	public InitialLoad(V_Connection connF) {
		this.connF = connF;
	}
	/**
	 * *** Constructor ***
	 * @author Carlos Parada 12/05/2012, 12:29:13
	 */
	public InitialLoad(Context m_ctx) {
		// TODO Auto-generated constructor stub
		this.m_ctx=m_ctx;
	}
	/**
	 * *** Constructor ***
	 * @author Carlos Parada 23/04/2012, 00:52:10
	 */
	public InitialLoad(String NameSpace,String URL,String Method,Context m_ctx) {
		// TODO Auto-generated constructor stub
		this.m_ctx = m_ctx;
		m_URL = URL;
		m_Method = Method;
		m_NameSpace = NameSpace;
		m_soap = new CommunicationSoap(m_URL,m_NameSpace, m_Method, true);
	}
	 /**
     * Load Values ​​Connection With Soap from Context
     * @author Carlos Parada 13/05/2012 
     * @return void
     */
	public void LoadSoapFromContext(Context m_ctx)
	{
		this.m_ctx = m_ctx;
		m_soap = null;
		m_URL = Env.getContext(m_ctx, "#SUrlSoap");
		m_Method = Env.getContext(m_ctx, "#SMethod");
		m_NameSpace = Env.getContext(m_ctx, "#SNameSpace");;
		m_soap = new CommunicationSoap(m_URL,m_NameSpace, m_Method, true);
	}
	
	
	
	/**
     * Prepare to send to the server Parameters
     * @author Carlos Parada 13/05/2012 
     * @return void
     */
	private void getAuth(SOInitialLoad m_init)
	{
		String m_user ="";
		String m_pass= "";
		PropertyInfo m_pi = new PropertyInfo();
		
		m_user = Env.getContext(m_ctx, "#SUser");
		m_pass = Env.getContext(m_ctx, "#SPass");
		
		m_init.setProperty(0, m_user);
		m_init.setProperty(1, m_pass);
		
		m_pi.setName(m_Method);
		m_pi.setNamespace(m_NameSpace);
		m_pi.setValue(m_init);
		m_pi.setType(m_init.getClass());
		
		m_soap.addProperty(m_pi);
	}
	
	/**
     * Load the Web service in the local Soap Object
     * @author Carlos Parada 13/05/2012 
     * @return void
     */
	public SoapObject loadFromService()
	{
		int timeout =0;
		timeout=Env.getContextAsInt(m_ctx, "#Timeout");
		//Initializes object Soap
		LoadSoapFromContext(m_ctx);
		
		//Object Parameters Soap
		SOInitialLoad m_init = new SOInitialLoad();
		//Adding Parameter Object Soap
		getAuth(m_init);
		//Initializing object wrapping
		m_soap.init_envelope();
		//mapping the parameters
		m_soap.getM_Envelope().addMapping(m_NameSpace, m_Method, m_init.getClass());
		//Booting Transportation
		if (timeout==0)
			m_soap.initTransport();
		else
			m_soap.initTransport(timeout);
		
		try {
			m_soap.call();
			return (SoapObject) m_soap.getM_Envelope().getResponse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//current_service =m_ctx.getResources().getString(R.string.msg_CouldNotFindServer)+ " " + m_URL;
			current_service =e.getMessage();
			onCancelled();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			current_service =e.getMessage();
			onCancelled();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//Msg.alertMsg(m_ctx, m_ctx.getResources().getString(R.string.msg_UrlInvalid), e.getMessage());
			current_service =e.getMessage();
			onCancelled();
		}
		return null;
	}
	
	/**
     * Load and mount soap method response mobile database
     * @author Carlos Parada 13/05/2012 
     * @return boolean
     */
	public void initialLoad ()
	{
		ArrayList<SORespInitialLoad> m_list = new ArrayList<SORespInitialLoad>();
		SoapObject m_soap = loadFromService();
		if(m_soap!=null)
		{
			m_list=convertSoapToArraylist(m_soap);
			if (loadInBD(m_list))
			{
				success=true;
				finish=true;
				onPostExecute(true);
			}
		}
		else
			onPostExecute(true);
		
	}
	
	/**
     * Load Arraylist in BD
     * @author Carlos Parada 13/05/2012 
     * @return boolean
     */
	private boolean loadInBD(ArrayList<SORespInitialLoad> p_list)
	{
		Env.setDB_Version(m_ctx, 3);
    	int show_percentage;
    	
		//	Modified by Yamel Senih by changes in the Constructor
   	 	//	19/08/2012 04:29
   	 	//	DB con = new DB(m_ctx, Env.getDB_Name(m_ctx), Env.getDB_Version(m_ctx));
		DB con = new DB(m_ctx);
   	 	//	Fin Yamel Senih

    	con.openDB(DB.READ_WRITE);
    	con.beginTransaction();
    	qty_serv=p_list.size();
    	progress_serv=0;
    	show_percentage = new Double(qty_serv*0.05).intValue();
    	for(SORespInitialLoad resp : p_list)
    	{
	    	try { 
	    			current_service=resp.getM_Name();
	    			progress_serv++;
	    			Log.i("SQL:", " " + resp.getM_Sql());
	    			con.executeSQL(resp.getM_Sql());
	    			
	    			//red1 avoid Divide by zero error
	    			if (show_percentage>0)
	    			{
	    				if ((progress_serv % show_percentage)==0 )//|| !current_service.equals(before_service))
	    					onProgressUpdate();
	    			}
	    			if (!current_service.equals(before_service))
	    				before_service = current_service;
	    			
				} catch (Exception e) {
					// TODO: handle exception
					//con.closeDB(null);
					Msg.alertMsg(m_ctx, m_ctx.getResources().getString(R.string.msg_SQLFailed), e.getMessage());
					current_service =e.getMessage(); 
					onCancelled();
					//return false;
				}
    	}
    	
    	con.setTransactionSuccessful();
    	con.endTransaction();
    	con.closeDB(null);
    	return true;
	}
	
	/**
     * Converts soap response in Arraylist
     * @author Carlos Parada 13/05/2012 
     * @return void
     */
	public ArrayList<SORespInitialLoad> convertSoapToArraylist(SoapObject m_soap)
	{
		ArrayList<SORespInitialLoad> m_list = new ArrayList<SORespInitialLoad>();		
		int m_tam = m_soap.getPropertyCount();		
		for(int i=0;i<m_tam;i++)
		{
			SoapObject m_resp = (SoapObject) m_soap.getProperty(i);

			m_list.add(new SORespInitialLoad(m_resp.getPropertyAsString("name"), m_resp.getPropertyAsString("sql")));
		}	
		return m_list;
	}
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		initialLoad ();
		return null;
	}
	
	/**
	 * @param contentView the contentView to set
	 */
	public void setContentView(RemoteViews contentView) {
		this.contentView = contentView;
	}
	
	/**
	 * @param m_NotificationManager the m_NotificationManager to set
	 */
	public void setM_NotificationManager(
			NotificationManager m_NotificationManager) {
		this.m_NotificationManager = m_NotificationManager;
	}
	/**
	 * @return the m_NotificationManager
	 */
	public NotificationManager getM_NotificationManager() {
		return m_NotificationManager;
	}
	/**
	 * @return the contentView
	 */
	public RemoteViews getContentView() {
		return contentView;
	}
	
	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
	
	@Override
    protected void onPreExecute() {
		success=false;
		updateProgress(0, 100, m_ctx.getResources().getString(R.string.msg_CallingWebService));
	}
	
	/**
	 * Actual visits
	 * @author carlos Parada 06/08/2012, 01:48:26
	 * @param values
	 * @return void
	 */
	protected void onProgressUpdate(Integer... values) {
		updateProgress(progress_serv,qty_serv,current_service);
	}
	
   @SuppressWarnings("static-access")
   protected void onPostExecute(Object result) {
	    Msg.notification.flags|=Msg.notification.FLAG_AUTO_CANCEL;
    	updateProgress(progress_serv, qty_serv, m_ctx.getResources().getString(R.string.msg_SyncFinish));
    	if (success && finish)
    	{
			connF.lockFront();
			
			Env.setIsEnvLoad(m_ctx, true);
    	}
    	else
    		Env.setIsEnvLoad(m_ctx, false);
    }
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onCancelled()
	 */
	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		//Msg.notificationMsg(m_ctx,R.drawable.delete_m,  m_ctx.getResources().getString(R.string.msg_SyncFailed), m_ctx.getResources().getString(R.string.msg_SyncFailed),current_service, Msg.notification_ID, connF.getActivity().getParent().getIntent());
		this.cancel(true);
		
	}
	/**
	 * Refreshes the view of the notification bar
	 * @author Carlos Parada 13/08/2012, 00:15:50
	 * @param current_serv
	 * @param len_serv
	 * @param textNotify
	 * @return void
	 */
	private void updateProgress(int current_serv,int len_serv,String textNotify)
	{
		int current;
		current = (current_serv>len_serv?len_serv:current_serv);
		
		
		Double percentage  = new Double(new Double(current)/new Double(len_serv))*100;
		Msg.contentView.setTextViewText(R.id.tV_CurrentSinchronizing, textNotify);
		Msg.contentView.setTextViewText(R.id.tV_Percentaje, percentage.intValue()+"%");
		Msg.contentView.setProgressBar(R.id.pB_Sinchronizing, len_serv, current, false);
	
		m_NotificationManager.notify(Msg.notification_ID, Msg.notification);
	}
}
