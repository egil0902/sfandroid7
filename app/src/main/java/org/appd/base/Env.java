/*************************************************************************************
 * Product: SFAndroid (Sales Force Mobile)                       		             *
 * This program is free software; you can redistribute it and/or modify it    		 *
 * under the terms version 2 of the GNU General Public License as published   		 *
 * by the Free Software Foundation. This program is distributed in the hope   		 *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied 		 *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           		 *
 * See the GNU General Public License for more details.                       		 *
 * You should have received a copy of the GNU General Public License along    		 *
 * with this program; if not, write to the Free Software Foundation, Inc.,    		 *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     		 *
 * For the text or an alternative of this public license, you may reach us    		 *
 * Copyright (C) 2012-2013 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com				  		 *
 *************************************************************************************/
package org.appd.base;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * @author Yamel Senih
 *
 */
public final class Env {

	/**
	 * Check if already established preferences
	 * @author Yamel Senih 23/04/2012, 18:36:32
	 * @param ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isEnvLoad(Context ctx){
		return getContextAsBoolean(ctx, SET_ENV);
	}
	
	/**
	 * Obtiene si el formulario esta cargado
	 * @author Yamel Senih 09/08/2012, 19:58:14
	 * @param ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isLoadedActivity(Context ctx){
		return getContextAsBoolean(ctx, "#IsLoadedActivity");
	}
	
	/**
	 * Checks whether the user did login
	 * @author Yamel Senih 26/04/2012, 20:25:29
	 * @param ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isLogin(Context ctx){
		return getContextAsBoolean(ctx, "#IsLogin");
	}
	
	/**
	 * Sets a value for the initial preference
	 * @author Yamel Senih 23/04/2012, 18:38:35
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setIsEnvLoad(Context ctx, boolean value){
		setContext(ctx, SET_ENV, value);
	}
	
	/**
	 * Sets If the activity is loaded
	 * @author Yamel Senih 09/08/2012, 19:57:14
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setIsLoadedActivity(Context ctx, boolean value){
		setContext(ctx, "#IsLoadedActivity", value);
	}
	
	
	/**
	 * Establece un valor verdadero o falso para indicar si el usuario hizo login
	 * @author Yamel Senih 26/04/2012, 20:26:27
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setIsLogin(Context ctx, boolean value){
		setContext(ctx, "#IsLogin", value);
	}
	
	
	/**
	 * Obtiene el editor desde las preferencias compartidas
	 * @author Yamel Senih 23/04/2012, 18:03:15
	 * @param ctx
	 * @return
	 * @return Editor
	 */
	private static Editor getEditor(Context ctx){
		SharedPreferences pf = PreferenceManager.getDefaultSharedPreferences(ctx);
		return pf.edit();
	}
	
	/**
	 *	Set Global Context to Value
	 *  @param ctx context
	 *  @param context context key
	 *  @param value context value
	 */
	public static void setContext (Context ctx, String context, String value){
		if (ctx == null || context == null)
			return;
		//
		if (value == null)
			value = "";
		Editor ep = getEditor(ctx);
		ep.putString(context, value);
		ep.commit();
	}	//	setContext
	
	/**
	 * 
	 * @author Yamel Senih 26/03/2012, 12:09:23
	 * @param ctx
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext (Context ctx, String context, int value){
		if (ctx == null || context == null)
			return;
		Editor ep = getEditor(ctx);
		ep.putString(context, String.valueOf(value));
		ep.commit();
	}	//	setContext
	
	
	/**
	 *	Set Context for Window & Tab to Value
	 *  @param ctx context
	 *  @param WindowNo window no
	 *  @param TabNo tab no
	 *  @param context context key
	 *  @param value context value
	 *   */
	/*public static void setContext (Context ctx, int WindowNo, int TabNo, String context, String value)
	{
		if (ctx == null || context == null)
			return;
		//
		if (value == null)
			if (context.endsWith("_ID"))
				// TODO: Research potential problems with tables with Record_ID=0
				value = new String("0");
			else
				value = new String("");
		Editor ep = getEditor(ctx);
		ep.putString(WindowNo+"|"+TabNo+"|"+context, value);
		ep.commit();
	}	//	setContext*/
	
	/**
	 *	Set SO Trx
	 *  @param ctx context
	 *  @param isSOTrx SO Context
	 */
	/*public static void setISOTrx (Context ctx, boolean isSOTrx)
	{
		if (ctx == null)
			return;
		setContext(ctx, "IsSOTrx", isSOTrx);
	}	//	setSOTrx*/
	
	/**
	 *	Get global Value of Context
	 *  @param ctx context
	 *  @param context context key
	 *  @return value or ""
	 */
	public static String getContext (Context ctx, String context)
	{
		if (ctx == null || context == null)
			throw new IllegalArgumentException ("Require Context");
		SharedPreferences pf = PreferenceManager.getDefaultSharedPreferences(ctx);
		return pf.getString(context, "");
	}	//	getContext
	
	/**
	 * Obtiene la Fecha en un Formato específico
	 * @author Yamel Senih 16/07/2012, 19:03:06
	 * @param ctx
	 * @param context
	 * @param fromFormat
	 * @param toFormat
	 * @return String
	 */
	public static String getContextDateFormat(Context ctx, String context, String fromFormat, String toFormat)
	{
		String dateS = getContext(ctx, context);
		
		return getDateFormatString(dateS, fromFormat, toFormat);
	}	//	getContext
	
	/**
	 * Obtiene una fecha con un formato especifico
	 * @author Yamel Senih 18/07/2012, 01:51:15
	 * @param dateS
	 * @param fromFormat
	 * @param toFormat
	 * @return String
	 */
	public static String getDateFormatString(String dateS, String fromFormat, String toFormat)
	{
		
		/*Date date=new Date(dateS);
	    SimpleDateFormat fmt=new SimpleDateFormat(format);
	    return fmt.format(date);
		*/
	    SimpleDateFormat fmtFront=new SimpleDateFormat(fromFormat);
        SimpleDateFormat fmtBack=new SimpleDateFormat(toFormat);
        
        
        Date date;
		try {
			date = fmtFront.parse(dateS);
			return fmtBack.format(date);
		} catch (ParseException e) {
			Log.e("Cust_DateBox", "getDate(String)", e);
			e.printStackTrace();
		}       
        return null;
	    
	}	//	getContext
	
	
	public static Date getDateFormat(String dateS, String fromFormat, String toFormat)
	{
		
		/*Date date=new Date(dateS);
	    SimpleDateFormat fmt=new SimpleDateFormat(format);
	    return fmt.format(date);
		*/
	    SimpleDateFormat fmtFront=new SimpleDateFormat(fromFormat);
        //SimpleDateFormat fmtBack=new SimpleDateFormat(toFormat);
        
        
        Date date;
		try {
			date = fmtFront.parse(dateS);
			return date;
		} catch (ParseException e) {
			Log.e("Cust_DateBox", "getDate(String)", e);
			e.printStackTrace();
		}       
        return null;
	    
	}	//	getContext
	
	/**
	 * Obtiene la Fecha Actual en el Formato especificado
	 * @author Yamel Senih 16/07/2012, 19:17:45
	 * @param format
	 * @return
	 * @return String
	 */
	public static String getCurrentDateFormat(String format)
	{
		Date date=new Date();
	    SimpleDateFormat fmt=new SimpleDateFormat(format);
	    return fmt.format(date);
		
	}	//	getContext
	
	/**
	 * Get Context As Boolean Value
	 * @author Yamel Senih 23/04/2012, 18:35:37
	 * @param ctx
	 * @param context
	 * @return
	 * @return boolean
	 */
	public static boolean getContextAsBoolean (Context ctx, String context)
	{
		if (ctx == null || context == null)
			throw new IllegalArgumentException ("Require Context");
		SharedPreferences pf = PreferenceManager.getDefaultSharedPreferences(ctx);
		return pf.getBoolean(context, false);
	}	//	getContext
	
	/**
	 * Check if the activity is loaded
	 * @author Yamel Senih 13/05/2012, 12:56:55
	 * @param ctx
	 * @param context
	 * @return
	 * @return boolean
	 */
	@Deprecated
	public static boolean isLoadActivity(Context ctx, String context){
		return getContextAsBoolean(ctx, "|A|" + context);
	}
	
	/**
	 * Establece si se cargó o no una actividad
	 * @author Yamel Senih 13/05/2012, 12:58:29
	 * @param ctx
	 * @param activity
	 * @param load
	 * @return void
	 */
	@Deprecated
	public static void setLoadActivity(Context ctx, String activity, boolean load){
		setContext(ctx, "|A|" + activity, load);
	}
	
	/**
	 *	Set Global Context to Y/N Value
	 *  @param ctx context
	 *  @param context context key
	 *  @param value context value
	 */
	public static void setContext (Context ctx, String context, boolean value)
	{
		Editor ep = getEditor(ctx);
		ep.putBoolean(context, value);
		ep.commit();
	}	//	setContext

	/**
	 *	Set Context for Window to Value
	 *  @param ctx context
	 *  @param WindowNo window no
	 *  @param context context key
	 *  @param value context value
	 */
	public static void setContext (Context ctx, int WindowNo, String context, String value)
	{
		if (ctx == null || context == null)
			return;
		Editor ed = getEditor(ctx);
		if (value == null)
			value = "";
		ed.putString(WindowNo+"|"+context, value);
		ed.commit();
	}	//	setContext

	
	/**
	 *	Get Value of Context for Window.
	 *	if not found global context if available and enabled
	 *  @param ctx context
	 *  @param WindowNo window
	 *  @param context context key
	 *  @param  onlyWindow  if true, no defaults are used unless explicitly asked for
	 *  @return value or ""
	 */
	public static String getContext (Context ctx, int WindowNo, String context, boolean onlyWindow)
	{
		if (ctx == null)
			throw new IllegalArgumentException ("No Ctx");
		if (context == null)
			throw new IllegalArgumentException ("Require Context");
		SharedPreferences pf = PreferenceManager.getDefaultSharedPreferences(ctx);
		String s = pf.getString(WindowNo+"|"+context, "");
		if (s == null)
		{
			//	Explicit Base Values
			if (context.startsWith("#") || context.startsWith("$"))
				return getContext(ctx, context);
			if (onlyWindow)			//	no Default values
				return "";
			return getContext(ctx, "#" + context);
		}
		return s;
	}	//	getContext
	
	/**2
	 *	Get Value of Context for Window.
	 *	if not found global context if available
	 *  @param ctx context
	 *  @param WindowNo window
	 *  @param context context key
	 *  @return value or ""
	 */
	public static String getContext (Context ctx, int WindowNo, String context)
	{
		return getContext(ctx, WindowNo, context, false);
	}	//	getContext

	/**
	 * Get Value of Context for Window & Tab,
	 * if not found global context if available.
	 * If TabNo is TAB_INFO only tab's context will be checked.
	 * @param ctx context
	 * @param WindowNo window no
	 * @param TabNo tab no
	 * @param context context key
	 * @return value or ""
	 */
	public static String getContext (Context ctx, int WindowNo, int TabNo, String context)
	{
		if (ctx == null || context == null)
			throw new IllegalArgumentException ("Require Context");
		SharedPreferences pf = PreferenceManager.getDefaultSharedPreferences(ctx);
		String s = pf.getString(WindowNo+"|"+TabNo+"|"+context, "");
		// If TAB_INFO, don't check Window and Global context - teo_sarca BF [ 2017987 ]
		if (TAB_INFO == TabNo)
			return s != null ? s : "";
		//
		if (s == null)
			return getContext(ctx, WindowNo, context, false);
		return s;
	}	//	getContext

	/**
	 * Get Value of Context for Window & Tab,
	 * if not found global context if available.
	 * If TabNo is TAB_INFO only tab's context will be checked.
	 * @param ctx context
	 * @param WindowNo window no
	 * @param TabNo tab no
	 * @param context context key
	 * @param onlyTab if true, no window value is searched
	 * @return value or ""
	 */
	public static String getContext (Context ctx, int WindowNo, int TabNo, String context, boolean onlyTab)
	{
		final boolean onlyWindow = onlyTab ? true : false;
		return getContext(ctx, WindowNo, TabNo, context, onlyTab, onlyWindow);
	}
	
	/**
	 * Get Value of Context for Window & Tab,
	 * if not found global context if available.
	 * If TabNo is TAB_INFO only tab's context will be checked.
	 * @param ctx context
	 * @param WindowNo window no
	 * @param TabNo tab no
	 * @param context context key
	 * @param onlyTab if true, no window value is searched
	 * @param onlyWindow if true, no global context will be searched
	 * @return value or ""
	 */
	public static String getContext (Context ctx, int WindowNo, int TabNo, String context, boolean onlyTab, boolean onlyWindow)
	{
		if (ctx == null || context == null)
			throw new IllegalArgumentException ("Require Context");
		SharedPreferences pf = PreferenceManager.getDefaultSharedPreferences(ctx);
		String s = pf.getString(WindowNo+"|"+TabNo+"|"+context, "");
		// If TAB_INFO, don't check Window and Global context - teo_sarca BF [ 2017987 ]
		if (TAB_INFO == TabNo)
			return s != null ? s : "";
		//
		if (s == null && ! onlyTab)
			return getContext(ctx, WindowNo, context, onlyWindow);
		return s;
	}	//	getContext

	/**
	 *	Get Context and convert it to an integer (0 if error)
	 *  @param ctx context
	 *  @param context context key
	 *  @return value
	 */
	public static int getContextAsInt(Context ctx, String context)
	{
		try{
			if (ctx == null || context == null)
				throw new IllegalArgumentException ("Require Context");
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
			String value = sp.getString(context, "0");
			if(value != null && value.length() > 0)
				return Integer.parseInt(value);
		} catch (Exception e) {
			
		}
		return 0;
	}	//	getContextAsInt

	/**
	 *	Get Context and convert it to an integer (0 if error)
	 *  @param ctx context
	 *  @param WindowNo window no
	 *  @param context context key
	 *  @return value or 0
	 */
	/*public static int getContextAsInt(Context ctx, int WindowNo, String context)
	{
		String s = getContext(ctx, WindowNo, context, false);
		if (s.length() == 0)
			return 0;
		//
		try
		{
			return Integer.parseInt(s);
		}
		catch (NumberFormatException e)
		{
			Log.w("Env_D.getContextAsInt()", "(" + context + ") = " + s, e);
		}
		return 0;
	}	//	getContextAsInt*/

	/**
	 *	Get Context and convert it to an integer (0 if error)
	 *  @param ctx context
	 *  @param WindowNo window no
	 *  @param context context key
	 *  @param onlyWindow  if true, no defaults are used unless explicitly asked for
	 *  @return value or 0
	 */
	/*public static int getContextAsInt(Context ctx, int WindowNo, String context, boolean onlyWindow)
	{
		String s = getContext(ctx, WindowNo, context, onlyWindow);
		if (s.length() == 0)
			return 0;
		//
		try{
			return Integer.parseInt(s);
		}
		catch (NumberFormatException e){
			Log.w("Env_D.getContextAsInt()", "(" + context + ") = " + s, e);
		}
		return 0;
	}	//	getContextAsInt*/

	/**
	 *	Get Context and convert it to an integer (0 if error)
	 *  @param ctx context
	 *  @param WindowNo window no
	 *  @param TabNo tab no
	 * 	@param context context key
	 *  @return value or 0
	 */
	/*public static int getContextAsInt (Context ctx, int WindowNo, int TabNo, String context)
	{
		String s = getContext(ctx, WindowNo, TabNo, context);
		if (s.length() == 0)
			return 0;
		//
		try{
			return Integer.parseInt(s);
		}
		catch (NumberFormatException e){
			Log.w("Env_D.getContextAsInt()", "(" + context + ") = " + s, e);
		}
		return 0;
	}	//	getContextAsInt*/
	
	/**
	 *	Is Sales Order Trx
	 *  @param ctx context
	 *  @return true if SO (default)
	 */
	public static boolean isSOTrx (Context ctx)
	{
		SharedPreferences pf = PreferenceManager.getDefaultSharedPreferences(ctx);
		return pf.getBoolean("IsSOTrx", false);
	}	//	isSOTrx

	/**
	 *	Is Sales Order Trx
	 *  @param ctx context
	 *  @param WindowNo window no
	 *  @return true if SO (default)
	 */
	/*public static boolean isSOTrx (Context ctx, int WindowNo)
	{
		String s = getContext(ctx, WindowNo, "IsSOTrx", true);
		if (s != null && s.equals("N"))
			return false;
		return true;
	}*/	//	isSOTrx
	
	/**
	 * 	Get Login AD_Client_ID
	 *	@param ctx context
	 *	@return login AD_Client_ID
	 */
	public static int getAD_Client_ID (Context ctx)
	{
		return Env.getContextAsInt(ctx, "#AD_Client_ID");
	}	//	getAD_Client_ID

	/**
	 * 	Get Login AD_Org_ID
	 *	@param ctx context
	 *	@return login AD_Org_ID
	 */
	public static int getAD_Org_ID (Context ctx)
	{
		return Env.getContextAsInt(ctx, "#AD_Org_ID");
	}	//	getAD_Client_ID

	/**
	 * 	Get Login AD_User_ID
	 *	@param ctx context
	 *	@return login AD_User_ID
	 */
	public static int getAD_User_ID (Context ctx)
	{
		return Env.getContextAsInt(ctx, "#AD_User_ID");
	}	//	getAD_User_ID
	
	/**
	 * 	Get Login AD_Role_ID
	 *	@param ctx context
	 *	@return login AD_Role_ID
	 */
	public static int getAD_Role_ID (Context ctx)
	{
		return Env.getContextAsInt(ctx, "#AD_Role_ID");
	}	//	getAD_Role_ID

	/**
	 * Get Login M_Warehouse
	 * @author Yamel Senih 26/04/2012, 15:49:16
	 * @param ctx
	 * @return
	 * @return int
	 */
	public static int getM_Warehouse_ID (Context ctx)
	{
		return Env.getContextAsInt(ctx, "#M_Warehouse_ID");
	}	//	getAD_Role_ID
	
	
	/**
	 * Set User ID
	 * @author Yamel Senih 26/04/2012, 20:21:48
	 * @param ctx
	 * @param m_AD_User_ID
	 * @return void
	 */
	public static void setAD_User_ID(Context ctx, int m_AD_User_ID){
		setContext(ctx, "#AD_User_ID", m_AD_User_ID);
	}
	
	/**
	 * Set Client
	 * @author Yamel Senih 26/04/2012, 15:52:37
	 * @param ctx
	 * @param m_AD_Client_ID
	 * @return void
	 */
	public static void setAD_Client_ID(Context ctx, int m_AD_Client_ID){
		setContext(ctx, "#AD_Client_ID", m_AD_Client_ID);
	}
	
	/**
	 * Set Org
	 * @author Yamel Senih 26/04/2012, 15:53:56
	 * @param ctx
	 * @param m_AD_Org_ID
	 * @return void
	 */
	public static void setAD_Org_ID(Context ctx, int m_AD_Org_ID){
		setContext(ctx, "#AD_Org_ID", m_AD_Org_ID);
	}
	
	/**
	 * Set Role
	 * @author Yamel Senih 26/04/2012, 15:54:29
	 * @param ctx
	 * @param m_AD_Role_ID
	 * @return void
	 */
	public static void setAD_Role_ID(Context ctx, int m_AD_Role_ID){
		setContext(ctx, "#AD_Role_ID", m_AD_Role_ID);
	}
	
	/**
	 * Set Warehouse
	 * @author Yamel Senih 26/04/2012, 15:55:17
	 * @param ctx
	 * @param m_M_Warehouse_ID
	 * @return void
	 */
	public static void setM_Warehouse_ID(Context ctx, int m_M_Warehouse_ID){
		setContext(ctx, "#M_Warehouse_ID", m_M_Warehouse_ID);
	}
	
	/**
	 * Set Save Pass
	 * @author Yamel Senih 26/04/2012, 17:46:21
	 * @param ctx
	 * @param isSavePass
	 * @return void
	 */
	public static void setSavePass(Context ctx, boolean isSavePass){
		setContext(ctx, "#SavePass", isSavePass);
	}
	
	/**
	 * Set Automatic Visit Closing
	 * @author Yamel Senih 31/07/2012, 12:25:21
	 * @param ctx
	 * @param isSavePass
	 * @return void
	 */
	public static void setAutomaticVisitClosing(Context ctx, boolean isSavePass){
		setContext(ctx, "#AutomaticVisitClosing", isSavePass);
	}
	
	
	/**
	 * Get Save Pass
	 * @author Yamel Senih 26/04/2012, 17:47:21
	 * @param ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isSavePass(Context ctx){
		return getContextAsBoolean(ctx, "#SavePass");
	}
	
	/**
	 * get Automatic Visit Closing
	 * @author Yamel Senih 31/07/2012, 12:27:03
	 * @param ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isAutomaticVisitClosing(Context ctx){
		return getContextAsBoolean(ctx, "#AutomaticVisitClosing");
	}
	
	/**
	 * Establece el nombre de la Base de Datos
	 * @author Yamel Senih 25/04/2012, 12:04:31
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setDB_Path(Context ctx, String value){
		setContext(ctx, DB_NAME, value);
	}
	
	/**
	 * Obtiene el nombre de la base de datos
	 * @author Yamel Senih 25/04/2012, 12:05:27
	 * @param ctx
	 * @return
	 * @return String
	 */
	public static String getDB_PathName(Context ctx){
		return getContext(ctx, DB_NAME);
	}
	
	/**
	 * Obtiene la Verssion de BD
	 * @author Yamel Senih 26/03/2012, 12:05:52
	 * @param ctx
	 * @return
	 * @return int
	 */
	public static int getDB_Version(Context ctx){
		return Env.getContextAsInt(ctx, DB_VERSION);
	}	//	getAD_Role_ID
	
	/**
	 * Obtiene el ID del Registro Principal de un Tab
	 * @author Yamel Senih 13/05/2012, 17:18:22
	 * @param ctx
	 * @param tab
	 * @return
	 * @return int
	 */
	public static int getTabRecord_ID(Context ctx, String tab){
		//Msg.toastMsg(ctx, ID_TAB + tab + " " + getContextAsInt(ctx, ID_TAB + tab));
		return getContextAsInt(ctx, ID_TAB + tab);
	}
	
	/**
	 * Esstablece el ID del Registro guardado en un tab
	 * @author Yamel Senih 13/05/2012, 17:19:37
	 * @param ctx
	 * @param tab
	 * @param record_ID
	 * @return void
	 */
	public static void setTabRecord_ID(Context ctx, String tab, int record_ID){
		//Msg.toastMsg(ctx, ID_TAB + tab + " " + record_ID);
		setContext(ctx, ID_TAB + tab, record_ID);
	}
	
	/**
	 * Establece el Valor del Tab Actual
	 * @author Yamel Senih 13/05/2012, 19:38:54
	 * @param ctx
	 * @param tab
	 * @return void
	 */
	public static void setCurrentTab(Context ctx, String tab){
		setContext(ctx, CURRENT_TAB, tab);
	}
	
	/**
	 * Obtiene el valor del Tab Actual
	 * @author Yamel Senih 13/05/2012, 19:39:39
	 * @param ctx
	 * @return String
	 */
	public static String getCurrentTab(Context ctx){
		return getContext(ctx, CURRENT_TAB);
	}
	
	/**
	 * Establece el ID del Registro Padre
	 * @author Yamel Senih 15/05/2012, 16:05:19
	 * @param ctx
	 * @param record_ID
	 * @return void
	 */
	/*public static void setSummaryRecord_ID(Context ctx, String tab, int record_ID){
		setContext(ctx, tab, record_ID);
	}*/
	
	/**
	 * Obtiene el ID del Registro Padre
	 * @author Yamel Senih 15/05/2012, 16:06:29
	 * @param ctx
	 * @return
	 * @return int
	 */
	/*public static int getSummaryRecord_ID(Context ctx, String tab){
		return getContextAsInt(ctx, tab);
	}*/
	
	/**
	 * Establece la version de la base de Datos
	 * @author Yamel Senih 26/03/2012, 12:08:42
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setDB_Version(Context ctx, int value){
		if (ctx == null)
			return;
		//
		Env.setContext(ctx, DB_VERSION, value);
	}
	
	/**
	 * Obtiene el nombre formateado de un tab
	 * @author Yamel Senih 27/05/2012, 03:24:47
	 * @param activityName
	 * @return
	 * @return String
	 */
	public static String getActivityName(String activityName){
		return ID_TAB + activityName;
	}
	
	/**
	 * Obtiene el valor en formato BigDecimal
	 * @author Yamel Senih 22/08/2012, 01:43:48
	 * @param value
	 * @return
	 * @return BigDecimal
	 */
	public static BigDecimal getNumber(String value){
		if(value != null 
				&& value.length() > 0){
			return new BigDecimal(value);
		}
		return Env.ZERO;
	}
	
	public static String parseContext (Context ctx, String whereClause, boolean ignoreUnparsable)
	{
		return parseContext(ctx, whereClause, ignoreUnparsable,null);
	}
	/**
	 *	Parse Context replaces global or Window context @tag@ with actual value.
	 *
	 *  @tag@ are ignored otherwise "" is returned
	 *  @param ctx context
	 *
	 *	@param whereClause Message to be parsed
	 * 	@param ignoreUnparsable if true, unsuccessful @return parsed String or "" if not successful and ignoreUnparsable
	 *  @param defaultUnparseable
	 *	@return parsed context 
	 */
	public static String parseContext (Context ctx, String whereClause, boolean ignoreUnparsable,String defaultUnparseable)
	{
		if (whereClause == null || whereClause.length() == 0)
			return "";

		String token;
		String inStr = new String(whereClause);
		StringBuffer outStr = new StringBuffer();

		int i = inStr.indexOf('@');
		while (i != -1)
		{
			outStr.append(inStr.substring(0, i));			// up to @
			inStr = inStr.substring(i+1, inStr.length());	// from first @

			int j = inStr.indexOf('@');						// next @
			if (j < 0)
			{
				Log.d("No second tag: ", inStr);
				return "";						//	no second tag
			}

			token = inStr.substring(0, j);
			//Msg.alertMsg(ctx, "Epale", token);
			String ctxInfo = getContext(ctx, token);	// get context
			if (ctxInfo.length() == 0 && (token.startsWith("#") || token.startsWith("$")) )
				ctxInfo = getContext(ctx, token);	// get global context
			if (ctxInfo.length() == 0)
			{
				Log.d("No Context for: ", token);
				if (!ignoreUnparsable && defaultUnparseable==null)
					return "";						//	token not found
				else if (!ignoreUnparsable && defaultUnparseable!=null)
					outStr.append(defaultUnparseable);
					//ctxInfo=defaultUnparseable;
			}
			else
				outStr.append(ctxInfo);				// replace context with Context

			inStr = inStr.substring(j+1, inStr.length());	// from second @
			i = inStr.indexOf('@');
		}
		outStr.append(inStr);						// add the rest of the string

		return outStr.toString();
	}	//	parseContext

	/**
	 *	Parse Context replaces global or Window context @tag@ with actual value.
	 *
	 *  @param ctx context
	 *	@param	WindowNo	Number of Window
	 *	@param	value		Message to be parsed
	 *  @param  onlyWindow  if true, no defaults are used
	 *  @return parsed String or "" if not successful
	 */
	/*public static String parseContext (Properties ctx, int WindowNo, String value,
		boolean onlyWindow)
	{
		return parseContext(ctx, WindowNo, value, onlyWindow, false);
	}	//	parseContext*/
	
	/**
	 * Parse expression, replaces global or PO properties @tag@ with actual value. 
	 * @param expression
	 * @param po
	 * @param trxName
	 * @return String
	 */
	/*public static String parseVariable(String expression, PO po, String trxName, boolean keepUnparseable) {
		if (expression == null || expression.length() == 0)
			return "";

		String token;
		String inStr = new String(expression);
		StringBuffer outStr = new StringBuffer();

		int i = inStr.indexOf('@');
		while (i != -1)
		{
			outStr.append(inStr.substring(0, i));			// up to @
			inStr = inStr.substring(i+1, inStr.length());	// from first @

			int j = inStr.indexOf('@');						// next @
			if (j < 0)
			{
				Log.d("No second tag: ", inStr);
				return "";						//	no second tag
			}

			token = inStr.substring(0, j);
			
			//format string
			String format = "";
			int f = token.indexOf('<');
			if (f > 0 && token.endsWith(">")) {
				format = token.substring(f+1, token.length()-1);
				token = token.substring(0, f);
			}
			
			if (token.startsWith("#") || token.startsWith("$")) {
				//take from context
				Properties ctx = po != null ? po.getCtx() : Env.getCtx();
				String v = Env.getContext(ctx, token);
				if (v != null && v.length() > 0)
					outStr.append(v);
				else if (keepUnparseable)
					outStr.append("@"+token+"@");
			} else if (po != null) {
				//take from po
				Object v = po.get_Value(token);
				if (v != null) {
					if (format != null && format.length() > 0) {
						if (v instanceof Integer && token.endsWith("_ID")) {
							int tblIndex = format.indexOf(".");
							String table = tblIndex > 0 ? format.substring(0, tblIndex) : token.substring(0, token.length() - 3);
							String column = tblIndex > 0 ? format.substring(tblIndex + 1) : format;
							outStr.append(DB.getSQLValueString(trxName, 
									"select " + column + " from  " + table + " where " + table + "_id = ?", (Integer)v));
						} else if (v instanceof Date) {
							SimpleDateFormat df = new SimpleDateFormat(format);
							outStr.append(df.format((Date)v));
						} else if (v instanceof Number) {
							DecimalFormat df = new DecimalFormat(format);
							outStr.append(df.format(((Number)v).doubleValue()));
						} else {
							MessageFormat mf = new MessageFormat(format);
							outStr.append(mf.format(v));
						}
					} else {
						outStr.append(v.toString());
					}
				}
				else if (keepUnparseable) {
					outStr.append("@"+token+"@");
				}
			}

			inStr = inStr.substring(j+1, inStr.length());	// from second @
			i = inStr.indexOf('@');
		}
		outStr.append(inStr);						// add the rest of the string

		return outStr.toString();
	}*/
	
	/**
	 * Get Default Language
	 * @author Yamel Senih 06/02/2013, 21:58:27
	 * @param ctx
	 * @return
	 * @return String
	 */
	public static String getSOLanguage(Context ctx){
		return ctx.getResources().getConfiguration().locale.getDisplayName();
	}
	
	/**
	 * Set Current Language
	 * @author Yamel Senih 06/02/2013, 22:02:13
	 * @param ctx
	 * @param language
	 * @return void
	 */
	public static void setAD_Language(Context ctx, String language){
		setContext(ctx, LANGUAGE, language);
	}
	
	/**
	 * Get System AD_Language
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 07/02/2013, 19:53:16
	 * @param ctx
	 * @return
	 * @return String
	 */
	public static String getAD_Language(Context ctx){
		return getContext(ctx, LANGUAGE);
	}
	
	/**
	 * Change Language
	 * @author Yamel Senih 06/02/2013, 22:04:21
	 * @param ctx
	 * @param language
	 * @param metrics
	 * @return void
	 */
	public static void changeLanguage(Context ctx, String language, DisplayMetrics metrics){
		Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        ctx.getApplicationContext().getResources().updateConfiguration(config, metrics);
	}
	
	/**
	 * Change Language
	 * @author Yamel Senih 06/02/2013, 22:04:56
	 * @param ctx
	 * @param language
	 * @return void
	 */
	public static void changeLanguage(Context ctx, String language){
		changeLanguage(ctx, language, null);
	}
	
	/**************************************************************************
	 *  Application Context
	 */
	
	private static final String	SET_ENV = "#SET_ENV#";
	
	/** WindowNo for Main           */
	public static final int     WINDOW_MAIN = 0;
	/** WindowNo for Find           */
	public static final int     WINDOW_FIND = 1110;
	/** WinowNo for MLookup         */
	public static final int	   WINDOW_MLOOKUP = 1111;
	/** WindowNo for PrintCustomize */
	public static final int     WINDOW_CUSTOMIZE = 1112;
	/** WindowNo for PrintCustomize */
	public static final int     WINDOW_INFO = 1113;

	/** Tab for Info                */
	public static final int     TAB_INFO = 1113;
	
	/**************************************************************************
	 *  Language issues
	 */

	/** Context Language identifier */
	static public final String      	LANGUAGE = "#AD_Language";
	static public final String      	BASE_LANGUAGE = "en_US";
	
	/************************************Env***************************************
	 * Database Context
	 */
	public static final String		DB_VERSION = "#DB_Version";
	public static final String		DB_NAME = "#DB_Name";
	
	/***************************************************************************
	 * Prefix
	 */
	
	public static final String		ID_TAB = "|T|";
	public static final String		CURRENT_TAB = "|CT|";
	public static final String		SUMMARY_RECORD_ID = "#SummRID";
	
	/**	Big Decimal 0	 */
	static final public BigDecimal ZERO = new BigDecimal(0.0);
	/**	Big Decimal 1	 */
	static final public BigDecimal ONE = new BigDecimal(1.0);
	/**	Big Decimal 100	 */
	static final public BigDecimal ONEHUNDRED = new BigDecimal(100.0);
	
}
