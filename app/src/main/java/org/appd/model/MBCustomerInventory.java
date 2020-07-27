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
import org.appd.util.Msg;

import android.content.Context;
import android.database.Cursor;

/**
 * @author Yamel Senih
 *
 */
public class MBCustomerInventory extends MP {

	//private final int AD_TABLE_ID = 0;//259;
	private final String TABLENAME = "XX_MB_CustomerInventory";
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 22/08/2012, 03:07:24
	 * @param ctx
	 * @param ID
	 */
	public MBCustomerInventory(Context ctx, int ID) {
		super(ctx, ID);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 22/08/2012, 03:07:31
	 * @param ctx
	 * @param rs
	 */
	public MBCustomerInventory(Context ctx, Cursor rs) {
		super(ctx, rs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 22/08/2012, 03:07:56
	 * @param ctx
	 * @param con_tx
	 * @param ID
	 */
	public MBCustomerInventory(Context ctx, DB con_tx, int ID) {
		super(ctx, con_tx, ID);
		// TODO Auto-generated constructor stub
	}
	/* (non-Javadoc)
	 * @see org.sg.model.MP#beforeUpdate()
	 */
	@Override
	public boolean beforeSave(boolean isNew) {
		if(isNew){
			//	Visit
			try {
				int m_XX_MB_Visit_ID = Env.getContextAsInt(m_ctx, "#XX_MB_Visit_ID");
				
				MBVisit m_visit = null;
				
				if(m_XX_MB_Visit_ID != 0) {
					m_visit = new MBVisit(m_ctx, con, m_XX_MB_Visit_ID);
				}
				else{
					//	Visit
					m_visit = MBVisit.createFromPlanningVisit(m_ctx, con, 
							Env.getContextAsInt(m_ctx, "#XX_MB_PlanningVisit_ID"), 
							Env.getContext(m_ctx, "#Date"), 
							Env.getCurrentDateFormat("yyyy-MM-dd hh:mm:ss"), 
							Env.getContext(m_ctx, "#OffCourse"),
							false);
				}
				//	Visit Line
				MBVisitLine m_visitLine = MBVisitLine.createFrom(m_ctx, con, 
						m_visit.getID(), 
						"MII", 0, 0, 0, 0);
				
				Env.setContext(m_ctx, "#XX_MB_Visit_ID", m_visit.getID());
				Env.setContext(m_ctx, "#XX_MB_VisitLine_ID", m_visitLine.getID());
				
			} catch (Exception e) {
				Msg.alertMsg(m_ctx, "Error", e.getMessage());
				Env.setContext(m_ctx, "#XX_MB_Visit_ID", 0);
				Env.setContext(m_ctx, "#XX_MB_VisitLine_ID", 0);
				return false;
			}

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
		int XX_MB_VisitLine_ID = Env.getContextAsInt(m_ctx, "#XX_MB_VisitLine_ID");
		if(XX_MB_VisitLine_ID != 0){
			MBVisitLine m_visitLine = new MBVisitLine(m_ctx, XX_MB_VisitLine_ID);
			m_visitLine.set_Value("XX_MB_CustomerInventory_ID", getID());
			return m_visitLine.save();
		}
		return true;
	}

	@Override
	protected boolean afterDelete() {
		// TODO Auto-generated method stub
		return true;
	}
	
	/**
	 * Obtiene el ID de un Inventario que dependa de una Orden de Venta
	 * @author Yamel Senih 13/07/2012, 09:36:24
	 * @param ctx
	 * @param con
	 * @param m_C_Order_ID
	 * @param hanConnection
	 * @return
	 * @return int
	 */
	public static int getCustomerInventoryFromOrder(Context ctx, DB con, int m_C_Order_ID, boolean hanConnection){
		int m_XX_MB_CustomerInventory_ID = 0;
		String sql = new String("SELECT ci.XX_MB_CustomerInventory_ID " +
				"FROM XX_MB_CustomerInventory ci " +
				"WHERE ci.C_Order_ID = " + m_C_Order_ID);
		if(hanConnection)
			con.openDB(DB.READ_ONLY);
		Cursor rs = con.querySQL(sql, null);
		if(rs.moveToFirst()){
			m_XX_MB_CustomerInventory_ID = rs.getInt(0);
		}
		if(hanConnection)
			con.closeDB(rs);
		return m_XX_MB_CustomerInventory_ID;
	}
	
	/**
	 * Verifica si existe inventario generado a 
	 * partir de la orden de venta
	 * @author Yamel Senih 16/07/2012, 03:53:45
	 * @param ctx
	 * @param con
	 * @param m_C_Order_ID
	 * @param hanConnection
	 * @return
	 * @return boolean
	 */
	public static boolean existsInventoryFromOrder(Context ctx, DB con, int m_C_Order_ID, boolean hanConnection){
		boolean fine = false;
		String sql = new String("SELECT ci.XX_MB_CustomerInventory_ID " +
				"FROM XX_MB_CustomerInventory ci " +
				"INNER JOIN XX_MB_CustomerInventoryLine cil ON(cil.XX_MB_CustomerInventory_ID = ci.XX_MB_CustomerInventory_ID) " +
				"WHERE ci.C_Order_ID = " + m_C_Order_ID);
		if(hanConnection)
			con.openDB(DB.READ_ONLY);
		Cursor rs = con.querySQL(sql, null);
		if(rs.moveToFirst()){
			fine = true;
		}
		if(hanConnection)
			con.closeDB(rs);
		return fine;
	}
	
	
}
