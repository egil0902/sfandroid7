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
package org.appd.sync.services;

import java.io.IOException;
import org.appd.base.DB;
import org.appd.base.Env;
import org.appd.base.R;
import org.appd.model.MBSynchronizingTrace;
import org.appd.model.MBWebServiceType;
import org.appd.util.Msg;
import org.appd.view.MV_Synchronizing;
import org.ksoap2.serialization.SoapObject;
import org.xmlpull.v1.XmlPullParserException;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;

@SuppressLint({ "UseValueOf" })
@SuppressWarnings("rawtypes")
public class CallServices extends AsyncTask{
 
	
	/**
	 * *** Constructor for the Class ***
	 * @author Carlos Parada 06/06/2012, 23:46:23
	 */
	public CallServices(MV_Synchronizing ctx,int id,String SyncType,NotificationManager MNotification) {
		// TODO Auto-generated constructor stub
		this.m_ctx=ctx;
		this.m_serv_id=id;
		this.m_SyncType= SyncType;
		this.m_NotificationManager=MNotification;
	}
	
	/**
	 * Call adempiere services 
	 * @author Carlos Parada 10/06/2012, 23:38:27
	 * @return
	 * @return boolean
	 */
	public boolean CallListServices()
	{
		//creating Connection
		//	Modified by Yamel Senih by changes in the Constructor
   	 	//	19/08/2012 04:29
   	 	//	DB con = new DB(m_ctx, Env.getDB_Name(m_ctx), Env.getDB_Version(m_ctx));
		DB con = new DB(m_ctx);
   	 	//	Fin Yamel Senih
		con.openDB(DB.READ_WRITE);

		//Loading the list of services to call
		Cursor rsList = getListServices(con);
		
		if (rsList.moveToFirst())
		{
			do 
			{	
				callService(rsList.getInt(0), con,null,null,null,rsList.getInt(0));
			}
			while (rsList.moveToNext());
		}
		onPostExecute(true);
    	con.closeDB(rsList);
		return true;
	}
	
	/**
	 * Soap Service Call
	 * @author Carlos Parada 11/06/2012, 01:17:08
	 * @param service_id
	 * @param con
	 * @param fieldParent
	 * @param dataParentID
	 * @return
	 * @return boolean
	 */
	
	
	private boolean callService(int service_id,DB con,String fieldParent,Integer dataParentID,Integer SyncParent_ID,int RootSync_ID)
	{
		//Load the Service WS_WebServiceType
		Cursor rsService = loadService(service_id, con,RootSync_ID);
		Cursor rsData;
		Integer l_Sync_ID;
		
		//Trace synchronization
		MBSynchronizingTrace st;
		
		if (rsService.moveToFirst())
		{
			do  
			{
				//Create Trace is the start date and time
				st = new MBSynchronizingTrace(m_ctx, con, 0, rsService.getInt(0));

				// Driver Exception Web Service
				try 
				{
					current_service = rsService.getString(4);

					if (!(rsService.getInt(13)!=0 && service_id!=rsService.getInt(0)))
						progress_serv ++;
					
					//Service Type
					m_TypeServ=rsService.getString(11);
					
					//Call Synchronization Method
					if (rsService.getString(2)!=null)
					{
						//Download
						if (m_TypeServ.equals("D"))
						{
							//Script Before
							if (rsService.getString(16)!=null)
								execScript(con,Env.parseContext(m_ctx,rsService.getString(16),false,"0"));
						
							//Call Service
							WSModelADService call = new WSModelADService(m_ctx
									, rsService.getString(2) //Namespace
									, rsService.getString(1) //Url
									, rsService.getString(3) //Method
									, rsService.getInt(10) //Web_ServiceType_ID
									, con //Connection
									, null
									, null
									,Env.parseContext(m_ctx, rsService.getString(14), false, defaultDateSync)
									,m_TypeServ
									);
							
							//Get Web Service Response
							SoapObject so=call.getM_Resp();
							
							//Write Response in Local DB
							if (so!=null)
								readSoapDownload(so,rsService.getString(9),rsService.getInt(10),con,st);
							
							//Call Child Services if Has Child And Isn't Root Sync
							if (rsService.getInt(13)!=0 && rsService.getInt(0)!=RootSync_ID)
							{
								if (!callService(rsService.getInt(0), con,null,null,null,RootSync_ID))
								{
									st.setEvent(MBSynchronizingTrace.SyncTrace_Error, current_service);
									st.endTrace();
									st.save();
									return false;
								}
							}
							//Script After
							if (rsService.getString(15)!=null)
								execScript(con,Env.parseContext(m_ctx,rsService.getString(15),false,"0"));
						}

						//Upload Or Process in Adempiere
						if (m_TypeServ.equals("U") || m_TypeServ.equals("P"))
						{
							// Get data for process or upload
							rsData = getData(rsService.getString(9), con,Env.parseContext(m_ctx, rsService.getString(14),false,"0"));
							if (rsData.moveToFirst())
							{
								do
								{
									setContextID(rsData,rsService.getString(9));
									//Script Before
									if (rsService.getString(16)!=null)
										execScript(con,Env.parseContext(m_ctx,rsService.getString(16),false,"0"));
	
									// Call Web Service
									WSModelADService call;
									call = new WSModelADService(m_ctx
										, rsService.getString(2) //Namespace
										, rsService.getString(1) //Url
										, rsService.getString(3) //Method
										, rsService.getInt(10) //Web_ServiceType_ID
										, con //Conexion
										, rsData //Datos
										,rsService.getString(9)
										,Env.parseContext(m_ctx, rsService.getString(14), false, defaultDateSync)
										,m_TypeServ
										);
									//Get Web Service Response
									SoapObject so=call.getM_Resp();
									if (so!=null)
									{
										l_Sync_ID=(m_TypeServ.equals(MBWebServiceType.SynchronizingType_Process)?readSoapRunProcess(so,rsService.getString(9),rsService.getInt(10),con,rsData,st):readSoapUpload(so,rsService.getString(9),rsService.getInt(10),con,rsData,st));
										if (l_Sync_ID!=null)
										{
											Env.setContext(m_ctx, "#S_"+rsService.getString(9)+"_ID", l_Sync_ID);
											
											//Script After
											if (rsService.getString(15)!=null)
												execScript(con,Env.parseContext(m_ctx,rsService.getString(15),false,"0"));
											
											//Call Child Services if Has Child And Isn't Root Sync
											if (rsService.getInt(13)!=0 && rsService.getInt(0)!=RootSync_ID)
											{
												if (!callService(rsService.getInt(0), con,rsService.getString(9)+"_ID",rsData.getInt(rsData.getColumnIndex(rsService.getString(9)+"_ID")),l_Sync_ID,RootSync_ID))
												{
													st.setEvent(MBSynchronizingTrace.SyncTrace_Error, current_service);
													st.endTrace();
													st.save();
													return false;
												}
											}
										}
									}
								}while (rsData.moveToNext());
							}
							else
							{
								//Calling Web Service Without Nothing of Data 
								//Call Child Services if Has Child And Isn't Root Sync
								if (rsService.getInt(13)!=0 && rsService.getInt(0)!=RootSync_ID)
								{
									//Script Before
									if (rsService.getString(16)!=null)
										execScript(con,Env.parseContext(m_ctx,rsService.getString(16),false,"0"));
									
									if (!callService(rsService.getInt(0), con,rsService.getString(9)+"_ID",-1,-1,RootSync_ID))
									{
										st.setEvent(MBSynchronizingTrace.SyncTrace_Error, current_service);
										st.endTrace();
										st.save();
										return false;
									}
									
									//Script After
									if (rsService.getString(15)!=null)
										execScript(con,Env.parseContext(m_ctx,rsService.getString(15),false,"0"));
								}
							}
						}
						
					}
					//No Synchronization 
					else
					{
						//Script Before
						if (rsService.getString(16)!=null)
							execScript(con,Env.parseContext(m_ctx,rsService.getString(16),false,"0"));

						//Call Child Services if Has Child And Isn't Root Sync
						if (rsService.getInt(13)!=0 && rsService.getInt(0)!=RootSync_ID)
						{
							if (!callService(rsService.getInt(0), con,null,null,null,RootSync_ID))
							{
								st.setEvent(MBSynchronizingTrace.SyncTrace_Error, current_service);
								st.endTrace();
								st.save();
								return false;
							}
						}

						//Script After
						if (rsService.getString(15)!=null)
							execScript(con,Env.parseContext(m_ctx,rsService.getString(15),false,"0"));
					}
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					tittleError =m_ctx.getResources().getString(R.string.msg_Error);
					current_service=e.getMessage();
					st.setEvent(MBSynchronizingTrace.SyncTrace_Error, e.getMessage());
					st.endTrace();
					st.save();
					onCancelled();
					return false;
				} 
				catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					tittleError =m_ctx.getResources().getString(R.string.msg_Error);
					current_service=e.getMessage();
					st.setEvent(MBSynchronizingTrace.SyncTrace_Error, e.getMessage());
					st.endTrace();
					st.save();
					onCancelled();
					return false;
				}
				catch (SQLiteException e) {
					// TODO Auto-generated catch block
					tittleError =m_ctx.getResources().getString(R.string.msg_Error);
					current_service=e.getMessage();
					st.setEvent(MBSynchronizingTrace.SyncTrace_Error, e.getMessage());
					st.endTrace();
					st.save();
					onCancelled();
					return false;
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					tittleError =m_ctx.getResources().getString(R.string.msg_Error);
					current_service=e.getMessage();
					st.setEvent(MBSynchronizingTrace.SyncTrace_Error, e.getMessage());
					st.endTrace();
					st.save();
					onCancelled();
					return false;
				}
	
				st.endTrace();
				st.save();
				onProgressUpdate();
				//clearContextID(rsService.getString(9));
				}
				while (rsService.moveToNext());
			}
		
		return true;
	}

	/**
	 * Returns a cursor with the data to send to Adempiere (Only TableName, Connection to Condition and bd)
	 * @author Carlos Parada 06/11/2012, 00:04:25
	 * @param TableName
	 * @param con
	 * @param where
	 * @return
	 * @return Cursor
	 */
	private Cursor getData(String TableName,DB con,String where)
	{
		String sql ;
			sql = new String("Select * "
					+ "From " 
					+ TableName 
					+" "+(where!=null && !where.equals("")?"Where "+where :""));

		return con.querySQL(sql,null);		
	}
	/**
	 * Executes the sql statement stored in the rule
	 * @author Carlos Parada 07/11/2012, 22:28:55
	 * @param con
	 * @param Script
	 * @return void
	 */
	private void execScript(DB con, String Script )throws Exception
	{
		String sql= "";
		
		do 
		{
			sql = Script.substring(0, (Script.indexOf(";")==-1?Script.length():Script.indexOf(";")+1));
			Script=Script.replaceAll(sql, "");
			con.executeSQL(sql);
		}
		while (!Script.trim().equals(""));
	}
	/**
	 * Sets the id of the Record in the context
	 * @author Carlos Parada 07/11/2012, 22:27:44
	 * @param rsData
	 * @param TableName
	 * @return void
	 */
	private void setContextID(Cursor rsData,String TableName)
	{
		int id;
		id = rsData.getInt(rsData.getColumnIndex(TableName+"_ID"));
		Env.setContext(m_ctx, "#"+TableName+"_ID", id);
	}
	
	/**
	 * Returns the list of services to call
	 * @author Carlos Parada 10/06/2012, 23:47:55
	 * @return
	 * @return Cursor
	 */
	private Cursor getListServices(DB con)
	{
		
		String sql = new String("Select "
					+ "ML.XX_MB_MenuList_ID,"
					+ "ML.Name "
					+ "From " 
					+ "XX_MB_MenuList ML " 
					+(m_serv_id==0?"Where MenuType='"+m_SyncType+"' And IsActive = 'Y' And IsSummary='Y'":"Where ML.XX_MB_Menulist_ID="+m_serv_id+" And MenuType='"+m_SyncType+"' And IsActive = 'Y'")
					+" Order By SeqNo");
		Cursor rs = con.querySQL(sql,null);
		return rs;
		
	}
	
	/**
	 * Load Services to be called
	 * @author Carlos Parada 14/06/2012, 01:42:29
	 * @param parent_ID
	 * @param con
	 * @return
	 * @return Cursor
	 */
	private Cursor loadService(int parent_ID,DB con,int RootSync_ID)
	{
		
		String sql = new String("Select "
					+ "ML.XX_MB_MenuList_ID,"
					+ "WS.Url,"
					+ "WS.NameSpace,"
					+ "WSM.Value as Method,"
					+ "ML.Name,"
					+ "WST.Value as Type,"
					+ "WST.AD_Table_ID,"
					+ "ATV.TableName as ViewName,"
					+ "WST.XX_MB_Table_ID,"
					+ "ATT.TableName as TableName, "
					+ "WST.WS_WebServiceType_ID, "
					+ "ML.MenuType As SynchronizeType," 
					+ "ML.IsSummary, "
					+ "Case When CML.QtySync Is Null Then 0 Else CML.QtySync End As QtySync, "
					+ "ML.WhereClause, "
					+ "rula.Script as ScriptAfter, "
					+ "rulb.Script as ScriptBefore "
					+ "From " 
					+ "XX_MB_MenuList ML "
					+ "Left Join AD_Rule rula On ML.AD_RuleAfter_ID = rula.AD_Rule_ID "
					+ "Left Join AD_Rule rulb On ML.AD_RuleBefore_ID = rulb.AD_Rule_ID "
					+ "Left Join WS_WebServiceType WST On ML.WS_WebServiceType_ID=WST.WS_WebServiceType_ID "
					+ "Left Join WS_WebService WS On WS.WS_WebService_ID=WST.WS_WebService_ID "
					+ "Left Join WS_WebServiceMethod WSM On WST.WS_WebServiceMethod_ID=WSM.WS_WebServiceMethod_ID " 
					+ "Left Join AD_Table ATV On ATV.AD_Table_ID=WST.AD_Table_ID "
					+ "Left Join AD_Table ATT On ATT.AD_Table_ID=WST.XX_MB_Table_ID "
					+ "Left Join (Select XX_MB_ParentMenuList_ID,Count(1) QtySync From XX_MB_MenuList Group By XX_MB_ParentMenuList_ID) CML On CML.XX_MB_ParentMenuList_ID = ML.XX_MB_Menulist_ID "
					+ "Where ("+ ((RootSync_ID==parent_ID?true:false)?"ML.XX_MB_Menulist_ID="+parent_ID+" Or ":"")+" ML.XX_MB_ParentMenulist_ID=?) And ML.MenuType!='M' And ML.IsActive ='Y' Order By ML.SeqNo ");
		Cursor rs = con.querySQL(sql, new String[]{((Integer) parent_ID).toString()});
	
		return rs;
		
	}
	
	/**
	 * read Soap Returned Id Upload
	 * @author Carlos Parada 13/07/2012, 17:59:14
	 * @param so
	 * @param TableName
	 * @param m_WS_WebServiceType
	 * @param con
	 * @return
	 * @return boolean
	 */
	private Integer readSoapUpload(SoapObject so,String TableName,int m_WS_WebServiceType,DB con,Cursor p_Data,MBSynchronizingTrace st)
	{
		Integer l_Sync_ID=null;
		try
		{
			
			//Counting The Number Of Records returned by the call to service
			int l_IDRow = Integer.parseInt(so.getAttributeAsString("RecordID"));
			l_Sync_ID = l_IDRow;
			//Assigning recordset object soap
			if (l_IDRow>0)
				if(!writeSoapRespUpload(l_IDRow,TableName,m_WS_WebServiceType,con,p_Data,st))
					return null;
		
		}
		catch (Exception e)
		{
			String Error ;
				if (so.getPropertyCount()>0)
				{
					Error =so.getPropertyAsString("Error");
					current_service=Error;
				}
				else
					current_service=e.getMessage();
		
				tittleError =m_ctx.getResources().getString(R.string.msg_Error);
				st.setEvent(MBSynchronizingTrace.SyncTrace_Error, current_service);
				onCancelled();
			return null;
		}
			
				
		return l_Sync_ID;
	}
	
	/**
	 * Read the response sent from the ERP readSoapRunProcess
	 * @author Carlos Parada 13/07/2012, 17:59:14
	 * @param so
	 * @param TableName
	 * @param m_WS_WebServiceType
	 * @param con
	 * @return
	 * @return boolean
	 */
	private Integer readSoapRunProcess(SoapObject so,String TableName,int m_WS_WebServiceType,DB con,Cursor p_Data,MBSynchronizingTrace st)
	{
		int l_Sync_ID=0;
		String Error ;
		try
		{
	
			if (so.getAttributeAsString("IsError").equals("false"))
			{
				l_Sync_ID =p_Data.getInt(p_Data.getColumnIndex(TableName+"_ID"));
			}
			else
			{
				if (so.getPropertyCount()>0)
				{

					Error =so.getPropertyAsString("Error");
					current_service=Error;
					tittleError =m_ctx.getResources().getString(R.string.msg_Error);
					st.setEvent(MBSynchronizingTrace.SyncTrace_Error, current_service);
					onCancelled();
					
				}
			}
		}
		catch (Exception e)
		{
			
				if (so.getPropertyCount()>0)
				{
					Error =so.getPropertyAsString("Error");
					current_service=Error;
				}
				else
					current_service=e.getMessage();
		
				tittleError =m_ctx.getResources().getString(R.string.msg_Error);
				st.setEvent(MBSynchronizingTrace.SyncTrace_Error, current_service);
				onCancelled();
			return null;
		}
			
				
		return l_Sync_ID;
	}
	/**
	 * Read Object Data Reading Soap
	 * @author Carlos Parada 14/06/2012, 00:14:19
	 * @param so
	 * @return
	 * @return boolean
	 */
	private boolean readSoapDownload(SoapObject so,String TableName,int m_WS_WebServiceType,DB con,MBSynchronizingTrace st)
	{
		SoapObject l_rows=null;
		try
		{
			
			//Counting the number of records returned by the call to service
			int l_totalRows = Integer.parseInt(so.getAttributeAsString("TotalRows"));
			//Asignando conjunto de registros a objeto soap
			if (l_totalRows>0)
				l_rows =(SoapObject) so.getProperty(0);
			
			for (int i=0;i<l_totalRows;i++)
			{
				if (m_TypeServ.equals("D"))
				{
					if(!writeSoapRespDownload((SoapObject)l_rows.getProperty(i),TableName,m_WS_WebServiceType,con,st))
						return false;
				}
				else
				{
					if(!writeSoapRespAlter((SoapObject)l_rows.getProperty(i),TableName,m_WS_WebServiceType,con,st))
						return false;
				}
			}			
		
		}
		catch (Exception e)
		{
			String Error ;
				if (so.getPropertyCount()>0)
				{
					Error =so.getPropertyAsString("Error");
					current_service=Error;
				}
				else
					current_service=e.getMessage();
				
				tittleError =m_ctx.getResources().getString(R.string.msg_Error);
				st.setEvent(MBSynchronizingTrace.SyncTrace_Error, current_service);
				onCancelled();
			return false;
		}
			
				
		return true;
	}
	
	/**
	 * Escribe Registro en BD Local
	 * @author Carlos Parada 14/06/2012, 01:12:39
	 * @param so
	 * @param TableName
	 * @param m_WS_WebServiceType
	 * @return
	 * @return boolean
	 */
	private boolean writeSoapRespDownload(SoapObject so,String TableName,int m_WS_WebServiceType,DB con,MBSynchronizingTrace st)
	{
		int l_properties = so.getPropertyCount();
		StringBuffer sql = new StringBuffer();
		StringBuffer fields =new StringBuffer();
		StringBuffer values =new StringBuffer();
		String [] data = new String[l_properties];
		SoapObject field = null;
		Object value =null;
		//inicializando sql 
		sql.append("Insert or Replace InTo "+
					TableName
					);
		//cargando Campos y valores a insertar 
		for (int i=0;i<l_properties;i++)
		{
			field = (SoapObject)so.getProperty(i);
			fields.append((fields.length()>0?","+field.getAttributeAsString("column"):field.getAttributeAsString("column")));
			values.append((values.length()>0?",?":"?"));
			value=field.getProperty(0);
			data[i]=(value!=null?value.toString():null);
			
			
		}
		//Armando sql
		sql.append("("+fields.toString()+") values("+values+");");
		
		//Ejecutando Query
		try
		{
			con.executeSQL(sql.toString(),data);
		}
		catch(Exception e)
		{
			tittleError =m_ctx.getResources().getString(R.string.msg_SQLFailed);
			current_service=e.getMessage();
			st.setEvent(MBSynchronizingTrace.SyncTrace_Error, current_service);
			onCancelled();
			return false;
		}
		
		return true;
	}
	
	/**
	 * writeSoapRespUpload Escribe ID Generado por Adempiere 
	 * @author Carlos Parada 13/07/2012, 18:01:29
	 * @param p_IDRow
	 * @param TableName
	 * @param m_WS_WebServiceType
	 * @param con
	 * @param p_Data
	 * @param st
	 * @return boolean
	 */
	private boolean writeSoapRespUpload(int p_IDRow,String TableName,int m_WS_WebServiceType,DB con,Cursor p_Data,MBSynchronizingTrace st)
	{
		
		StringBuffer sql = new StringBuffer();
		
		//inicializando sql 
		sql.append("Update "+
					TableName
					+
					" Set S_" +TableName+ "_ID = " + p_IDRow + "," +TableName+ "_ID"+"=" + p_IDRow +" Where " +TableName+ "_ID"+"="+ p_Data.getString(p_Data.getColumnIndex(TableName+ "_ID"))
					);
		
		//Ejecutando Query
		try
		{
			con.executeSQL(sql.toString());
		}
		catch(Exception e)
		{
			tittleError =m_ctx.getResources().getString(R.string.msg_SQLFailed);
			current_service=e.getMessage();
			onCancelled();
			st.setEvent(MBSynchronizingTrace.SyncTrace_Error, current_service);
			return false;
			
		}
		
		return true;
	}
	
	/**
	 * writeSoapRespAlter Ejecuta una sentencia sql previamente elaborada
	 * @author Carlos Parada 24/07/2012, 23:59:39
	 * @param so
	 * @param TableName
	 * @param m_WS_WebServiceType
	 * @param con
	 * @return
	 * @return boolean
	 */
	private boolean writeSoapRespAlter(SoapObject so,String TableName,int m_WS_WebServiceType,DB con,MBSynchronizingTrace st)
	{
		int l_properties = so.getPropertyCount();
		StringBuffer sql = new StringBuffer();
		SoapObject field = null;
		Object value =null;
 
		for (int i=0;i<l_properties;i++)
		{
			field = (SoapObject)so.getProperty(i);
			value=field.getProperty(0);
			if((value!=null?value.toString():null)!=null)
			{
				sql.append(value);
				try
				{
					con.executeSQL(sql.toString());
					sql.delete(0, sql.length());
				}
				catch(Exception e)
				{
					tittleError =m_ctx.getResources().getString(R.string.msg_SQLFailed);
					current_service=e.getMessage();
					onCancelled();
					st.setEvent(MBSynchronizingTrace.SyncTrace_Error, current_service);
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Cuenta la cantidad de servicios a llamar
	 * @author Carlos Parada 06/08/2012, 01:48:01
	 * @param parent_ID
	 * @param total
	 * @param con
	 * @return
	 * @return int
	 */
	private int getCountServices(int parent_ID,int total,DB con)
	{
		int id,qtysync,l_total;
		String sql = new String("Select "
				+ "ML.XX_MB_MenuList_ID,"
				+ "Case When CML.QtySync Is Null Then 0 Else CML.QtySync End As QtySync "
				+ "From " 
				+ "XX_MB_MenuList ML Left Join WS_WebServiceType WST On ML.WS_WebServiceType_ID=WST.WS_WebServiceType_ID "
				+ "Left Join WS_WebService WS On WS.WS_WebService_ID=WST.WS_WebService_ID "
				+ "Left Join WS_WebServiceMethod WSM On WST.WS_WebServiceMethod_ID=WSM.WS_WebServiceMethod_ID " 
				+ "Left Join AD_Table ATV On ATV.AD_Table_ID=WST.AD_Table_ID "
				+ "Left Join AD_Table ATT On ATT.AD_Table_ID=WST.XX_MB_Table_ID "
				+ "Left Join (Select XX_MB_ParentMenuList_ID,Count(1) QtySync From XX_MB_MenuList Group By XX_MB_ParentMenuList_ID) CML On CML.XX_MB_ParentMenuList_ID = ML.XX_MB_Menulist_ID "
				+ "Where (ML.XX_MB_Menulist_ID=? Or ML.XX_MB_ParentMenulist_ID=?) And ML.MenuType!='M' And ML.IsActive='Y' Order By ML.SeqNo ");
		Cursor rs = con.querySQL(sql, new String[]{((Integer) parent_ID).toString(),((Integer) parent_ID).toString()});
		l_total = total;
		if (rs.moveToFirst())
		{
			do
			{
				id= rs.getInt(0);
				qtysync = rs.getInt(1);
				
				if (qtysync!=0 && parent_ID!=id)
					l_total=getCountServices(id, l_total, con);
				else
					l_total++;
					
			}while (rs.moveToNext());
		}
		rs.close();
		return l_total;
	}
	
	
	/**
	 * Call the Update process
	 * @author carlos Parada 06/08/2012, 01:48:56
	 * @param params
	 * @return void
	 */
	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		return CallListServices();
	}
	/**
	 * Updates the view
	 * @author carlos Parada 06/08/2012, 01:48:26
	 * @param values
	 * @return void
	 */
	protected void onProgressUpdate(Integer... values) {
		updateProgress(progress_serv,qty_serv,current_service);
		
	}
	
	/**
	 * se ejecuta antes de comenzar el proceso
	 * @author Carlos Parada 06/08/2012, 01:48:26
	 * @return void
	 * 
	 */
	@Override
    protected void onPreExecute() {

		//	Modificado por Yamel Senih por cambios en el Constructor
   	 	//	19/08/2012 04:29
   	 	//	DB con = new DB(m_ctx, Env.getDB_Name(m_ctx), Env.getDB_Version(m_ctx));
		DB con = new DB(m_ctx);
   	 	//	Fin Yamel Senih
		con.openDB(DB.READ_ONLY);
		
		qty_serv=getCountServices(m_serv_id,0,con);
		progress_serv=0;
		con.closeDB(null);

		//Inicializando Interfaz Gafica
		updateProgress(progress_serv, qty_serv, "");
        
       
    }

    @SuppressWarnings("static-access")
	protected void onPostExecute(Boolean result) {
    	Msg.notification.flags|=Msg.notification.FLAG_AUTO_CANCEL;
    	updateProgress(1, 1, m_ctx.getResources().getString(R.string.msg_SyncFinish));
    }

    @Override
    protected void onCancelled() {
    	//Arreglar
    	Msg.notificationMsg(m_ctx,R.drawable.delete_m,  m_ctx.getResources().getString(R.string.msg_SyncFailed), tittleError,current_service, Msg.notification_ID, m_ctx.getIntent());
    	this.cancel(true);
    }
    
    private void updateProgress(int current_serv,int len_serv,String textNotify)
	{
		int current;
		current = (current_serv>len_serv?len_serv:current_serv);
		Double percentaje  = new Double(new Double(current)/new Double(len_serv))*100;
		Msg.contentView.setTextViewText(R.id.tV_CurrentSinchronizing, textNotify);
		Msg.contentView.setTextViewText(R.id.tV_Percentaje, percentaje.intValue()+"%");
		Msg.contentView.setProgressBar(R.id.pB_Sinchronizing, len_serv, current, false);
	
		m_NotificationManager.notify(Msg.notification_ID, Msg.notification);
	}

  
	
	private MV_Synchronizing m_ctx;
	private int m_serv_id;
	private String m_SyncType;
	private int qty_serv;
	private int progress_serv;
	private String m_TypeServ;
	private String current_service;
	private NotificationManager m_NotificationManager;
	private String tittleError;
	private String defaultDateSync="1900-01-01 00:00:00";
}