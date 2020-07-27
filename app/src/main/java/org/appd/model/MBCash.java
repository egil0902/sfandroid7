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
public class MBCash extends MP {

	private final String TABLENAME = "C_Cash";
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 22/08/2012, 02:53:33
	 * @param ctx
	 * @param ID
	 */
	public MBCash(Context ctx, int ID) {
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
	public MBCash(Context ctx, Cursor rs) {
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
	public MBCash(Context ctx, DB con_tx, int ID) {
		super(ctx, con_tx, ID);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.sg.model.MP#beforeUpdate()
	 */
	@Override
	public boolean beforeSave(boolean isNew) {
		if(isNew){
			MSequence.updateSequence(con, get_ValueAsInt("C_DocType_ID"), Env.getAD_User_ID(m_ctx));
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
		/*int XX_MB_VisitLine_ID = Env.getContextAsInt(m_ctx, "#XX_MB_VisitLine_ID");
		if(XX_MB_VisitLine_ID != 0){
			MBVisitLine m_visitLine = new MBVisitLine(m_ctx, XX_MB_VisitLine_ID);
			m_visitLine.set_Value("C_Order_ID", getID());
			return m_visitLine.save();
		}*/
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
	public boolean updateHeader(){
		boolean ok = false;
		loadConnection(DB.READ_ONLY);
		String sql = new String("SELECT SUM(l.Amount) amt, " +
				"COALESCE(c.BeginningBalance, 0) + SUM(l.Amount) endB " +
				"FROM C_Cash c " +
				"INNER JOIN C_CashLine l ON(l.C_Cash_ID = c.C_Cash_ID) " +
				"WHERE c.C_Cash_ID = " + getID());
		Cursor rs = con.querySQL(sql, null);
		if(rs.moveToFirst()){
			
			String m_StatementDifference = rs.getString(0);
			String m_EndingBalance = rs.getString(1);
			//Log.i("Values", "m_StatementDifference = " + m_StatementDifference + "  " + m_EndingBalance);
			//	
			set_Value("StatementDifference", Env.getNumber(m_StatementDifference));
			set_Value("EndingBalance", Env.getNumber(m_EndingBalance));
			ok = true;
		}
		closeConnection();
		return ok;
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
				"WHERE dt.DocBaseType IN('CMC') " +
				"LIMIT 1");
		//	Cursor
		Cursor rs = con_tx.querySQL(sql, null);
    	
		if(rs.moveToFirst()){
			m_C_DocType_ID = rs.getInt(0);
    	}
		
		return m_C_DocType_ID;
	}
	
	/**
	 * Verifica si la Caja Tiene Lineas
	 * @author Yamel Senih 30/08/2012, 23:45:56
	 * @return
	 * @return boolean
	 */
	public boolean isExistsLines(){
		boolean exists = false;
		loadConnection(DB.READ_ONLY);
		Cursor rs = con.querySQL("SELECT 1 FROM C_CashLine cl " +
				"WHERE cl.C_Cash_ID = " + 
				getID() + " LIMIT 1", null);
		exists = rs.moveToFirst();
		closeConnection();
		return exists;
	}
	
	/**
	 * Verifica si la caja tiene depositos asignados
	 * @author Yamel Senih 15/11/2012, 01:55:53
	 * @return
	 * @return boolean
	 */
	public boolean isExistsDeposits(){
		boolean exists = false;
		loadConnection(DB.READ_ONLY);
		Cursor rs = con.querySQL("SELECT 1 FROM C_Payment py " +
				"WHERE py.C_Cash_ID = " + 
				getID() + " " +
				"AND py.DocAction NOT IN('VO', 'RE') " +
				"LIMIT 1", null);
		exists = rs.moveToFirst();
		closeConnection();
		return exists;
	}
	
	/**
	 * Procesa el documento y los hijos
	 * @author Yamel Senih 15/11/2012, 01:31:00
	 * @param docAction
	 * @return
	 * @return boolean
	 */
	public boolean processDocuments(String docAction){
		boolean ok = true;
		loadConnection(DB.READ_ONLY);
		con.executeSQL("UPDATE C_Payment SET DocAction = ? WHERE C_Cash_ID = ? ", 
				new String[]{docAction, String.valueOf(getID())});
		closeConnection();
		return ok;
	}
	
	/**
	 * Crea la caja
	 * @author Yamel Senih 01/11/2012, 10:14:47
	 * @param ctx
	 * @param con_tx
	 * @param force
	 * @return
	 * @throws Exception
	 * @return MBCash
	 */
	public static MBCash create(Context ctx, DB con_tx, boolean force) throws Exception {
		
		int m_C_Cash_ID = 0;
		//	Crea la Consulta
		if(!force){
			StringBuffer sqlLoad = new StringBuffer("SELECT ch.C_Cash_ID " +
					"FROM C_Cash ch " +
					//	Clause Where
					"WHERE ch.DocAction = 'DR' ");
					//	Filtro Adicional
					sqlLoad.append("LIMIT 1");
			
			Cursor rs = con_tx.querySQL(sqlLoad.toString(), null);
			if(rs.moveToFirst()){
				m_C_Cash_ID = rs.getInt(0);
			}
		}
		//	Instance Cash
		MBCash m_cash = new MBCash(ctx, con_tx, m_C_Cash_ID);
		
		//	Set New Values
		if(m_C_Cash_ID == 0){
			int m_C_DocType_ID = MBDocType.getC_DocType_ID(ctx, con_tx, Env.getAD_User_ID(ctx), "'CMC'", null);
			m_cash.set_Value("SalesRep_ID", Env.getAD_User_ID(ctx));
			m_cash.set_Value("AD_Org_ID", Env.getAD_Org_ID(ctx));
			m_cash.set_Value("StatementDate", Env.getContext(ctx, "#DateP"));
			m_cash.set_Value("C_CashBook_ID", Env.getContext(ctx, "#C_CashBook_ID"));
			m_cash.set_Value("C_DocTypeTarget_ID", m_C_DocType_ID);
			m_cash.set_Value("DocAction", "DR");
			m_cash.set_Value("Processed", "N");
			m_cash.set_Value("BeginningBalance", 0);
			m_cash.set_Value("EndingBalance", 0);
			String docNo = MSequence.getCurrentNext(con_tx, m_C_DocType_ID, Env.getAD_User_ID(ctx));
			m_cash.set_Value("DocumentNo", docNo);
			m_cash.saveEx();
			MSequence.updateSequence(con_tx, m_C_DocType_ID, Env.getAD_User_ID(ctx));
		}
		
		return m_cash;
	}
	
}
