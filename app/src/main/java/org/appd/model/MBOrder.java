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
public class MBOrder extends MP {

	//private final int AD_TABLE_ID = 0;//259;
	private final String TABLENAME = "C_Order";
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 22/08/2012, 02:53:33
	 * @param ctx
	 * @param ID
	 */
	public MBOrder(Context ctx, int ID) {
		super(ctx, ID);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 22/08/2012, 02:53:31
	 * @param ctx
	 * @param rs
	 */
	public MBOrder(Context ctx, Cursor rs) {
		super(ctx, rs);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 22/08/2012, 02:53:27
	 * @param ctx
	 * @param con_tx
	 * @param ID
	 */
	public MBOrder(Context ctx, DB con_tx, int ID) {
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
			set_Value("M_Warehouse_ID", Env.getContextAsInt(m_ctx, "#M_Warehouse_ID"));
			set_Value("IsSOTrx", "Y");
			set_Value("PaymentRule", "P");
			set_Value("DeliveryViaRule", "D");
			set_Value("DeliveryRule", "F");
			set_Value("C_PaymentTerm_ID", Env.getContextAsInt(m_ctx, "#C_PaymentTerm_ID"));
			set_Value("C_Activity_ID", Env.getContext(m_ctx, "#C_Activity_ID"));	//	Cable, Listo
			//set_Value("DocStatus", "DR");
			MSequence.updateSequence(con, get_ValueAsInt("C_DocTypeTarget_ID"), Env.getAD_User_ID(m_ctx));
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
						"SOO", 0, 0, 0, 0);
				
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
			m_visitLine.set_Value("C_Order_ID", getID());
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
	 * Actualiza el Total de lineas y el Total General en el encabezado
	 * @author Yamel Senih 22/08/2012, 02:34:22
	 * @return
	 * @return boolean
	 */
	public boolean updateAmt(){
		boolean ok = false;
		loadConnection(DB.READ_ONLY);
		String sql = new String("SELECT SUM(l.LineNetAmt), SUM(l.LineNetAmt-(l.LineNetAmt*t.Rate)) " +
				"FROM C_OrderLine l " +
				"INNER JOIN C_Tax t ON(t.C_Tax_ID = l.C_Tax_ID) " +
				"WHERE l.C_Order_ID = " + getID());
		Cursor rs = con.querySQL(sql, null);
		if(rs.moveToFirst()){
			set_Value("TotalLines", Env.getNumber(rs.getString(0)));
			set_Value("GrandTotal", Env.getNumber(rs.getString(1)));
			ok = true;
		}
		closeConnection();
		return ok;
	}
	
	/**
	 * Crea un pedido a partir de un inventario
	 * @author Yamel Senih 22/08/2012, 18:32:46
	 * @param ctx
	 * @param con_tx
	 * @param m_CustomerInventory
	 * @return
	 * @return MBOrder
	 */
	public static MBOrder createFromInventory(Context ctx, DB con_tx, MBCustomerInventory m_CustomerInventory, MBPlanningVisit pv) {
		MBOrder m_Order = new MBOrder(ctx, con_tx, 0);
		int m_C_DocType_ID = getC_DocType_ID(ctx, con_tx, Env.getAD_User_ID(ctx));
		String seq = MSequence.getCurrentNext(con_tx, m_C_DocType_ID, Env.getAD_User_ID(ctx));
    	if(seq != null){
    		m_Order.set_Value("DocumentNo", seq);
    	}

    	int m_C_BPartner_ID = m_CustomerInventory.get_ValueAsInt("C_BPartner_ID");
    	
    	MBPartner cp = new MBPartner(ctx, m_C_BPartner_ID);
    	
    	m_Order.set_Value("C_DocTypeTarget_ID", m_C_DocType_ID);
    	m_Order.set_Value("C_PaymentTerm_ID", cp.get_ValueAsInt("C_PaymentTerm_ID"));
    	m_Order.set_Value("DateOrdered", Env.getContext(ctx, "#Date"));
		m_Order.set_Value("DatePromised", Env.getContext(ctx, "#Date"));
		m_Order.set_Value("C_BPartner_ID", m_CustomerInventory.get_ValueAsInt("C_BPartner_ID"));
		m_Order.set_Value("C_BPartner_Location_ID", m_CustomerInventory.get_ValueAsInt("C_BPartner_Location_ID"));
		m_Order.set_Value("Bill_Location_ID", pv.get_ValueAsInt("Bill_Location_ID"));
		m_Order.set_Value("M_PriceList_ID", Env.getContextAsInt(ctx, "#M_PriceList_ID"));
		m_Order.set_Value("DocStatus", "DR");
		m_Order.set_Value("DocAction", "DR");
		m_Order.updateAmt();
		
		return m_Order;
		
	}
	
	/**
	 * Obtiene el Tipo de Documento por defecto
	 * @author Yamel Senih 22/08/2012, 19:23:05
	 * @param ctx
	 * @param con_tx
	 * @param m_AD_User_ID
	 * @return
	 * @return int
	 */
	public static int getC_DocType_ID(Context ctx, DB con_tx, int m_AD_User_ID){
		int m_C_DocType_ID = 0;
		//	Sql
		String sql = new String("SELECT " +
				"dt.C_DocType_ID " +
				"FROM C_DocType dt " +
				"WHERE dt.DocBaseType IN('SOO') " +
				"AND dt.DocSubTypeSO IN('SO')");
		//	Cursor
		Cursor rs = con_tx.querySQL(sql, null);
    	
		if(rs.moveToFirst()){
			m_C_DocType_ID = rs.getInt(0);
    	}
		
		return m_C_DocType_ID;
	}
	
	/**
	 * Verifica si el Pedido Tiene Lineas
	 * @author Yamel Senih 30/08/2012, 03:06:37
	 * @return
	 * @return boolean
	 */
	public boolean isExistsLines(){
		boolean exists = false;
		loadConnection(DB.READ_ONLY);
		Cursor rs = con.querySQL("SELECT 1 FROM C_OrderLine ol " +
				"WHERE ol.C_Order_ID = " + 
				getID() + " LIMIT 1", null);
		exists = rs.moveToFirst();
		closeConnection();
		return exists;
	}
	
}
