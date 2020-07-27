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
public class MBPayment extends MP {

	private final String TABLENAME = "C_Payment";
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 31/10/2012, 17:10:25
	 * @param ctx
	 * @param ID
	 */
	public MBPayment(Context ctx, int ID) {
		super(ctx, ID);
		
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 31/10/2012, 17:10:32
	 * @param ctx
	 * @param rs
	 */
	public MBPayment(Context ctx, Cursor rs) {
		super(ctx, rs);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 31/10/2012, 17:10:40
	 * @param ctx
	 * @param con_tx
	 * @param ID
	 */
	public MBPayment(Context ctx, DB con_tx, int ID) {
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
		con.deleteSQL(TABLENAME, "C_Payment_ID = " + getID(), null);
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
		return true;
	}

	@Override
	protected boolean afterDelete() {
		// TODO Auto-generated method stub
		return true;
	}
	
	/**
	 * Update Payment Header from Payment Allocation Lines
	 * @author Yamel Senih 01/12/2012, 00:28:02
	 * @return
	 * @return boolean
	 */
	public boolean updateHeader(){
		boolean ok = false;
		loadConnection(DB.READ_ONLY);
		String sql = new String("SELECT SUM(l.Amount) amt " +
				"FROM C_Payment p " +
				"INNER JOIN C_PaymentAllocate l ON(l.C_Payment_ID = p.C_Payment_ID) " +
				"WHERE p.C_Payment_ID = " + getID());
		Cursor rs = con.querySQL(sql, null);
		if(rs.moveToFirst()){
			
			String m_Amt = rs.getString(0);
			//Log.i("Values", "m_Amt = " + m_Amt);
			//	
			set_Value("PayAmt", Env.getNumber(m_Amt));
			ok = true;
		}
		closeConnection();
		return ok;
	}
	
	/**
	 * Obtiene el tipo de documento por defecto
	 * @author Yamel Senih 31/10/2012, 17:38:12
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
				"WHERE dt.DocBaseType IN('ARR') " +
				"LIMIT 1");
		//	Cursor
		Cursor rs = con_tx.querySQL(sql, null);
    	
		if(rs.moveToFirst()){
			m_C_DocType_ID = rs.getInt(0);
    	}
		
		return m_C_DocType_ID;
	}
	
	/**
	 * Verifica si el pago tiene lineas
	 * don de se pagan varias facturas
	 * @author Yamel Senih 31/10/2012, 17:33:47
	 * @return
	 * @return boolean
	 */
	public boolean isExistsLines(){
		boolean exists = false;
		loadConnection(DB.READ_ONLY);
		Cursor rs = con.querySQL("SELECT 1 FROM C_PaymentAllocate pa " +
				"WHERE pa.C_Payment_ID = " + 
				getID() + " LIMIT 1", null);
		exists = rs.moveToFirst();
		closeConnection();
		return exists;
	}
	
	/**
	 * Crea el pago y establece los valores por defecto
	 * @author Yamel Senih 07/11/2012, 11:59:22
	 * @param ctx
	 * @param con_tx
	 * @return
	 * @throws Exception
	 * @return MBPayment
	 */
	public static MBPayment create(Context ctx, DB con_tx) throws Exception {
		
		//	Instance Payment
		MBPayment payment = new MBPayment(ctx, con_tx, 0);
		int m_C_DocType_ID = MBDocType.getC_DocType_ID(ctx, con_tx, Env.getAD_User_ID(ctx), "'ARR'", null);
		payment.set_Value("DateTrx", Env.getContext(ctx, "#Date"));
		payment.set_Value("DateAcct", Env.getContext(ctx, "#Date"));
		payment.set_Value("C_BankAccount_ID", Env.getContext(ctx, "#C_BankAccount_ID"));
		payment.set_Value("C_DocType_ID", m_C_DocType_ID);
		payment.set_Value("AD_Org_ID", Env.getAD_Org_ID(ctx));
		payment.set_Value("AD_OrgTrx_ID", Env.getAD_Org_ID(ctx));
		payment.set_Value("C_Currency_ID", Env.getContext(ctx, "#C_Currency_ID"));
		
		String docNo = MSequence.getCurrentNext(con_tx, m_C_DocType_ID, Env.getAD_User_ID(ctx));
		payment.set_Value("DocumentNo", docNo);
		payment.set_Value("TenderType", "D");
		payment.set_Value("C_Charge_ID", null);
		//payment.set_Value("C_Invoice_ID");
		payment.set_Value("OverUnderAmt", 0);
		payment.set_Value("TrxType", "S");
		payment.set_Value("Processed", "N");
		payment.set_Value("Posted", "N");
		payment.set_Value("PayAmt", 0);
		payment.set_Value("IsSelfService", "N");
		payment.set_Value("IsReconciled", "N");
		payment.set_Value("IsReceipt", "Y");
		payment.set_Value("IsPrepayment", "N");
		payment.set_Value("IsOverUnderPayment", "N");
		payment.set_Value("IsOnline", "N");
		payment.set_Value("IsApproved", "N");
		payment.set_Value("IsAllocated", "N");
		payment.set_Value("DocAction", "DR");
		payment.set_Value("IsDelayedCapture", "N");
		//payment.set_Value("C_BPartner_ID");
		payment.set_Value("CreditCardExpMM", null);
	    payment.set_Value("CreditCardExpYY", null);
	    payment.set_Value("CreditCardNumber", null);
	    payment.set_Value("CreditCardType", null);
	    payment.set_Value("CreditCardVV", null);
	    payment.set_Value("A_Name", null);
	    payment.set_Value("RoutingNo", null);
	    payment.set_Value("AccountNo", null);
		
		return payment;
	}
	
}
