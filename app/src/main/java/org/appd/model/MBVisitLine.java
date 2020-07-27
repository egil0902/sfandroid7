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
public class MBVisitLine extends MP {

	private final String TABLENAME = "XX_MB_VisitLine";
	
	public MBVisitLine(Context ctx, int ID) {
		super(ctx, ID);
		// TODO Auto-generated constructor stub
	}
	
	public MBVisitLine(Context ctx, DB con_tx, int ID) {
		super(ctx, con_tx, ID);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Hace una consulta y crea una visita con el 
	 * Evento predeterminado base
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 10/07/2012, 17:14:19
	 * @param ctx
	 * @param con_tx
	 * @param XX_MB_Visit_ID
	 * @param XX_BaseEventType
	 * @throws Exception 
	 */
	public static MBVisitLine createFrom(Context ctx, DB con_tx, int XX_MB_Visit_ID, 
			String XX_BaseEventType, int C_Order_ID, 
			int XX_MB_CustomerInventory_ID, int C_Cash_ID, 
			int C_Payment_ID) throws Exception {
		
		StringBuffer sqlLoad = new StringBuffer("SELECT XX_MB_VisitLine_ID " +
				"FROM XX_MB_VisitLine " +
				//	Clause Where
				"WHERE ");
		
		boolean param = false;
		
		if(C_Order_ID !=0){
			param = true;
			sqlLoad.append("C_Order_ID = " + C_Order_ID);
		} else if(XX_MB_CustomerInventory_ID != 0){
			param = true;
			sqlLoad.append("XX_MB_CustomerInventory_ID = " + XX_MB_CustomerInventory_ID);
		} else if(C_Cash_ID != 0){
			param = true;
			sqlLoad.append("C_Cash_ID = " + C_Cash_ID);
		} else if(C_Payment_ID != 0){
			param = true;
			sqlLoad.append("C_Payment_ID = " + C_Payment_ID);
		}
		
		int m_XX_MB_VisitLine_ID = 0;
		
		Cursor rs = null;
		
		if(param){
			//	Limit
			sqlLoad.append(" LIMIT 1");
			
			//	Consulta 
			rs = con_tx.querySQL(sqlLoad.toString(), null);
			if(rs.moveToFirst()){
				m_XX_MB_VisitLine_ID = rs.getInt(0);
			}
		}
		
		//	Instance Visit Line
		MBVisitLine m_VisitLine = new MBVisitLine(ctx, con_tx, m_XX_MB_VisitLine_ID);
		//	Set Visit ID
		m_VisitLine.set_Value("XX_MB_Visit_ID", XX_MB_Visit_ID);
		
		if(m_XX_MB_VisitLine_ID == 0){
			//	Query find EventType ID
			String sql = new String("SELECT XX_MB_EventType_ID " +
					"FROM XX_MB_EventType " +
					"WHERE XX_BaseEventType = ? " +
					"ORDER BY IsDefault " +
					"LIMIT 1");
				
			int m_XX_MB_EventType_ID = 0;
			rs = con_tx.querySQL(sql.toString(), new String[]{XX_BaseEventType});
			if(rs.moveToFirst()){
				m_XX_MB_EventType_ID = rs.getInt(0);
			}
			//Msg.toastMsg(ctx, "m_XX_EventType_ID" + m_XX_MB_EventType_ID);
			m_VisitLine.set_Value("XX_MB_EventType_ID", m_XX_MB_EventType_ID);
			m_VisitLine.set_Value("DateDoc", Env.getCurrentDateFormat("yyyy-MM-dd hh:mm:ss"));
			m_VisitLine.saveEx();
		}
		
		return m_VisitLine;
	}
		
	public MBVisitLine(Context ctx, Cursor rs) {
		super(ctx, rs);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.sg.model.MP#beforeUpdate()
	 */
	@Override
	public boolean beforeSave(boolean isNew) {
		if(isNew){
			/*set_Value("SalesRep_ID", Env.getAD_User_ID(m_ctx));
			//set_Value("DocStatus", "CO");
			//set_Value("M_PriceList_ID", 1000013);//Env.getContextAsInt(m_ctx, "#M_Pricelist_ID"));
			set_Value("PaymentRule", Env.getContext(m_ctx, "#PaymentRule"));
			set_Value("C_PaymentTerm_ID", Env.getContextAsInt(m_ctx, "#C_PaymentTerm_ID"));
			MSequence.updateSequence(con, get_ValueAsInt("C_DocTypeTarget_ID"), Env.getAD_User_ID(m_ctx));*/
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
