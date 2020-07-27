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
package org.appd.model;

import org.appd.base.DB;
import org.appd.base.Env;
import org.appd.base.R;

import android.content.Context;
import android.database.Cursor;

/**
 * @author Carlos Parada
 *
 */
public class MBSynchronizingTrace extends MP {

	private final String TABLENAME = "XX_MB_SynchronizingTrace";
	
	public static final String SyncTrace_Error="E";
	public static final String SyncTrace_Warning="W";
	public static final String SyncTrace_Success="S";
	
	
	/**
	 * *** Constructor de la Clase ***
	 * @author Carlos Parada 26/07/2012
	 * @param ctx
	 * @param con_tx
	 * @param ID
	 */
	public MBSynchronizingTrace(Context ctx, DB con_tx, int ID, int XX_MB_MenuList_ID) {
		super(ctx, con_tx, ID);
		set_Value("XX_MB_MenuList_ID", XX_MB_MenuList_ID);
		startTrace();
	}
	
	/**
	 * *** Constructor de la Clase ***
	 * @author Carlos Parada 26/07/2012
	 * @param ctx
	 * @param con_tx
	 * @param ID
	 */
	public MBSynchronizingTrace(Context ctx, int ID) {
		super(ctx, ID);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * *** Constructor de la Clase ***
	 * @author Carlos Parada 26/07/2012
	 * @param ctx
	 * @param con_tx
	 * @param ID
	 */
	public MBSynchronizingTrace(Context ctx, DB con_tx, int ID) {
		super(ctx, con_tx, ID);
	}
	
	/** 
	 * *** Constructor de la Clase ***
	 * @author Carlos Parada 10/26/2012
	 * @param ctx
	 * @param con_tx
	 * @param ID
	 */
	public MBSynchronizingTrace(Context ctx, Cursor rs) {
		super(ctx, rs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected String getTableName(){
		return TABLENAME;
	}

	/**
	 * Inicia la traza de sincronizacion
	 * @author Carlos Parada 20/08/2012, 00:10:42
	 * @return void
	 */
	@SuppressWarnings("static-access")
	public void startTrace()
	{
		set_Value("StartSync", Env.getCurrentDateFormat("yyyy-MM-dd hh:mm:ss"));
		setEvent(this.SyncTrace_Success, m_ctx.getResources().getString(R.string.msg_SyncSucess));		
	}
	/**
	 * 
	 * @author Carlos Parada 20/08/2012, 00:25:04
	 * @return void
	 */
	public void endTrace()
	{
		int id_ml=get_ValueAsInt("XX_MB_MenuList_ID");
		String NameService;
		MBMenuList ml ;
		MBWebServiceType mw;

		set_Value("EndSync", Env.getCurrentDateFormat("yyyy-MM-dd hh:mm:ss"));
		
		if (id_ml!=0)
		{
			ml = new MBMenuList(m_ctx, con, id_ml);
			mw=ml.getWebServiceType();
			if (mw!=null)
			{
				NameService  = "#" + ml.getWebServiceType().get_Value("Value").toString();
				NameService += "_LastSyncDate";
				Env.setContext(m_ctx, NameService, (get_Value("EventType").equals(SyncTrace_Error)?null:get_Value("StartSync").toString()));
			}
		}
	}

	/**
	 * 
	 * @author Carlos Parada 20/08/2012, 00:25:04
	 * @return void
	 */
	public void setEvent(String EventType,String Description)
	{
		set_Value("EventType",EventType);
		set_Value("Description", Description);

	}
	/**
	 * Establece un error
	 * @author Carlos Parada 21/08/2012, 22:56:19
	 * @return void
	 */
	public void setError(String Description)
	{
		int id_ml=get_ValueAsInt("XX_MB_MenuList_ID");
		String NameService;
		MBMenuList ml ;
		MBWebServiceType mw;

		set_Value("EventType",MBSynchronizingTrace.SyncTrace_Error);
		set_Value("Description", Description);
		
		if (id_ml!=0)
		{
			ml = new MBMenuList(m_ctx, con, id_ml);
			mw=ml.getWebServiceType();
			if (mw!=null)
			{
				NameService  = "#" + ml.getWebServiceType().get_Value("Value").toString();
				NameService += "_LastSyncDate";
				Env.setContext(m_ctx, NameService, null);
			}
		}
	}


}
