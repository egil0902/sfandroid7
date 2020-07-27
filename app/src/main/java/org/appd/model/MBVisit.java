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
package org.appd.model;

import org.appd.base.DB;
import org.appd.base.Env;

import android.content.Context;
import android.database.Cursor;

/**
 * @author Yamel Senih
 *
 */
public class MBVisit extends MP {

	private final String TABLENAME = "XX_MB_Visit";
	
	public MBVisit(Context ctx, int ID) {
		super(ctx, ID);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Hace una consulta y crea una visita con el 
	 * Evento predeterminado base
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 08/07/2012, 00:03:36
	 * @param ctx
	 * @param con_tx
	 * @param ID
	 * @param XX_BaseEventType
	 */
	public MBVisit(Context ctx, DB con_tx, int ID, String XX_BaseEventType) {
		super(ctx, con_tx, ID);
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 10/07/2012, 17:47:04
	 * @param ctx
	 * @param con_tx
	 * @param ID
	 */
	public MBVisit(Context ctx, DB con_tx, int ID) {
		super(ctx, con_tx, ID);
	}
	
	public MBVisit(Context ctx, Cursor rs) {
		super(ctx, rs);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Consulta una Visita que dependa de una planificación de Visita y 
	 * la fecha Actual, sino existe crea una nueva
	 * @author Yamel Senih 12/07/2012, 00:17:01
	 * @author Yamel Senih 27/07/2012, Se agrega la columna Fuera de Ruta "OffCourse"
	 * @param ctx
	 * @param con_tx
	 * @param XX_MB_PlanningVisit_ID
	 * @param date
	 * @param dateFrom
	 * @return
	 * @throws Exception
	 * @return MBVisit
	 */
	public static MBVisit createFromPlanningVisit(Context ctx, DB con_tx, 
			int XX_MB_PlanningVisit_ID, String date, String dateFrom, 
			String offCourse, boolean force) throws Exception {
		
		int m_XX_MB_Visit_ID = 0;
		//	Crea la Consulta
		if(!force){
			StringBuffer sqlLoad = new StringBuffer("SELECT XX_MB_Visit_ID " +
					"FROM XX_MB_Visit " +
					//	Clause Where
					"WHERE XX_MB_PlanningVisit_ID = " + XX_MB_PlanningVisit_ID + " ");
					//	Filtro Adicional
					/*if(date != null){
						sqlLoad.append("AND DATE(DateVisit) = DATE('"+ date + "') ");
					}*/
					sqlLoad.append(" AND DateTo IS NULL ");
					sqlLoad.append("LIMIT 1");
			
			Cursor rs = con_tx.querySQL(sqlLoad.toString(), null);
			if(rs.moveToFirst()){
				m_XX_MB_Visit_ID = rs.getInt(0);
			}
		}
		//	Instance Visit
		MBVisit m_Visit = new MBVisit(ctx, con_tx, m_XX_MB_Visit_ID);
		
		//	Set New Values
		if(m_XX_MB_Visit_ID == 0){
			//Msg.toastMsg(ctx, "Visit ID " + "New");
			m_Visit.set_Value("XX_MB_PlanningVisit_ID", XX_MB_PlanningVisit_ID);
			m_Visit.set_Value("DateVisit", date);
			m_Visit.set_Value("DateFrom", dateFrom);
			m_Visit.set_Value("OffCourse", offCourse);
			m_Visit.saveEx();
		}
		
		return m_Visit;
	}
	
	/**
	 * Crea una Visita
	 * @author Yamel Senih 25/07/2012, 10:08:04
	 * @param ctx
	 * @param XX_MB_PlanningVisit_ID
	 * @param date
	 * @param dateFrom
	 * @param force
	 * @return
	 * @throws Exception
	 * @return MBVisit
	 */
	public static MBVisit createFromPlanningVisit(Context ctx, 
			int XX_MB_PlanningVisit_ID, String date, String dateFrom, String offCourse, boolean force) throws Exception {
		
		DB con_tx = new DB(ctx);
		con_tx.openDB(DB.READ_ONLY);
		MBVisit m_Visit = createFromPlanningVisit(ctx, con_tx, XX_MB_PlanningVisit_ID, date, dateFrom, offCourse, force);
		con_tx.closeDB(null);
		return m_Visit;
	}
	
	
	/**
	 * Verifica si existe una visita abierta
	 * @author Yamel Senih 25/07/2012, 00:24:21
	 * @param ctx
	 * @param XX_MB_PlanningVisit_ID
	 * @param date
	 * @param distOpen
	 * @return
	 * @throws Exception
	 * @return int
	 */
	public static int findVisit(Context ctx, int XX_MB_PlanningVisit_ID, String date, boolean distOpen) {
		
		int m_XX_MB_Visit_ID = 0;
		
		StringBuffer sqlLoad = new StringBuffer("SELECT XX_MB_Visit_ID " +
				"FROM XX_MB_Visit " +
				//	Clause Where
				"WHERE XX_MB_PlanningVisit_ID = " + XX_MB_PlanningVisit_ID + " ");
		
		if(distOpen){
			sqlLoad.append("AND DateTo IS NULL ");
		}
		
		if(date != null){
			sqlLoad.append("AND DATE(DateVisit) = DATE('"+ date + "') ");
		}
		
		sqlLoad.append("LIMIT 1");
		
		DB con = new DB(ctx);
		con.openDB(DB.READ_ONLY);
		Cursor rs = con.querySQL(sqlLoad.toString(), null);
		if(rs.moveToFirst()){
			m_XX_MB_Visit_ID = rs.getInt(0);
		}
		
		con.closeDB(rs);
		
		return m_XX_MB_Visit_ID;
	}
	
	/**
	 * Consulta si existe una visita abierta
	 * @author Yamel Senih 25/07/2012, 09:43:48
	 * @param ctx
	 * @param date
	 * @return
	 * @return int
	 */
	public static int findOpenVisit(Context ctx, String date, int m_XX_MB_PlanningVisit_ID) {
		
		int m_XX_MB_Visit_ID = 0;
		
		StringBuffer sqlLoad = new StringBuffer("SELECT XX_MB_Visit_ID " +
				"FROM XX_MB_Visit " +
				//	Clause Where
				//"WHERE XX_MB_PlanningVisit_ID = " + XX_MB_PlanningVisit_ID + " " + 
				"WHERE DateTo IS NULL ");
				if(date != null){
					sqlLoad.append("AND DATE(DateVisit) = DATE('"+ date + "') ");
				}
				//	Planning Visit
				if(m_XX_MB_PlanningVisit_ID != 0){
					sqlLoad.append("AND XX_MB_PlanningVisit_ID = " + m_XX_MB_PlanningVisit_ID + " ");
				}
				
				sqlLoad.append("LIMIT 1");
		
		DB con = new DB(ctx);
		con.openDB(DB.READ_ONLY);
		Cursor rs = con.querySQL(sqlLoad.toString(), null);
		if(rs.moveToFirst()){
			m_XX_MB_Visit_ID = rs.getInt(0);
		}
		
		con.closeDB(rs);
		
		return m_XX_MB_Visit_ID;
	}
	
	/**
	 * Verifica si existe una visita abierta
	 * @author Yamel Senih 20/08/2012, 03:49:43
	 * @param ctx
	 * @param date
	 * @return
	 * @return int
	 */
	public static int findOpenVisit(Context ctx, String date) {
		return findOpenVisit(ctx, date, 0);
	}
	
	
	/**
	 * Cierra ua visita, si el parametro "date" se encuentra 
	 * en null entonces se coloca la fecha del ultimo evento de la visita 
	 * @author Yamel Senih 25/07/2012, 10:31:25
	 * @param date
	 * @return void
	 * @throws Exception 
	 */
	public void closeVisit(String date) throws Exception{
		/**
		 * Si la Fecha es Null entonces se busca la fecha del ultimo evento de la visita
		 */
		if(date == null){
			
			StringBuffer sqlLoad = new StringBuffer("SELECT MAX(DateDoc) " +
					"FROM XX_MB_VisitLine " +
					//	Clause Where
					"WHERE XX_MB_Visit_ID = " + getID() + " ");
			
			loadConnection(DB.READ_ONLY);
			Cursor rs = con.querySQL(sqlLoad.toString(), null);
			if(rs.moveToFirst()){
				date = rs.getString(0);
			}
			
			closeConnection();
			
		}
		//	Establece la Fecha de Finalizacion de la visita
		set_Value("DateTo", (date == null? Env.getCurrentDateFormat("yyyy-MM-dd hh:mm:ss"): date));
		
		saveEx();
	}

	/* (non-Javadoc)
	 * @see org.sg.model.MP#beforeUpdate()
	 */
	@Override
	public boolean beforeSave(boolean isNew) {
		if(isNew){
			set_Value("SalesRep_ID", Env.getAD_User_ID(m_ctx));
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see org.sg.model.MP#beforeDelete()
	 */
	@Override
	public boolean beforeDelete() {
		// TODO Auto-generated method stub
		return true;
	}
	
	/*@Override
	protected int getAD_Table_ID() {
		// TODO Auto-generated method stub
		return AD_TABLE_ID;
	}*/
	
	@Override
	protected String getTableName(){
		return TABLENAME;
	}

	@Override
	protected boolean afterSave(boolean isNew) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean afterDelete() {
		// TODO Auto-generated method stub
		return true;
	}
}
