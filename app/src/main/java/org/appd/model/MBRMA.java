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
import org.appd.base.R;
import org.appd.util.Msg;

import android.content.Context;
import android.database.Cursor;

/**
 * @author Yamel Senih
 *
 */
public class MBRMA extends MP {

	private final String TABLENAME = "M_RMA";
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 22/08/2012, 01:30:14
	 * @param ctx
	 * @param ID
	 */
	public MBRMA(Context ctx, int ID) {
		super(ctx, ID);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 22/08/2012, 01:30:09
	 * @param ctx
	 * @param rs
	 */
	public MBRMA(Context ctx, Cursor rs) {
		super(ctx, rs);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 22/08/2012, 01:31:04
	 * @param ctx
	 * @param con_tx
	 * @param ID
	 */
	public MBRMA(Context ctx, DB con_tx, int ID) {
		super(ctx, con_tx, ID);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.sg.model.MP#beforeUpdate()
	 */
	@Override
	public boolean beforeSave(boolean isNew) {
		if(isNew){
			set_Value("SalesRep_ID", Env.getAD_User_ID(m_ctx));
			//set_Value("DocAction", "DR");
			set_Value("IsSOTrx", "Y");
			MSequence.updateSequence(con, get_ValueAsInt("C_DocType_ID"), Env.getAD_User_ID(m_ctx));
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
						"RMS", 0, 0, 0, 0);
				
				Env.setContext(m_ctx, "#XX_MB_Visit_ID", m_visit.getID());
				Env.setContext(m_ctx, "#XX_MB_VisitLine_ID", m_visitLine.getID());
				
			} catch (Exception e) {
				Msg.alertMsg(m_ctx, m_ctx.getString(R.string.msg_Error), e.getMessage());
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
			m_visitLine.set_Value("M_RMA_ID", getID());
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
	 * Actualiza el monto total del encabezado
	 * @author Yamel Senih 22/08/2012, 02:34:22
	 * @return
	 * @return boolean
	 */
	public boolean updateAmt(){
		boolean ok = false;
		loadConnection(DB.READ_ONLY);
		String sql = new String("SELECT SUM(l.LineNetAmt) " +
				"FROM M_RMALine l " +
				"WHERE l.M_RMA_ID = " + getID());
		Cursor rs = con.querySQL(sql, null);
		if(rs.moveToFirst()){
			set_Value("Amt", Env.getNumber(rs.getString(0)));
			ok = true;
		}
		closeConnection();
		return ok;
	}
}
