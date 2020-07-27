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

import android.content.Context;
import android.database.Cursor;

/**
 * @author Yamel Senih
 *
 */
public class MBDocType extends MP {

	private final String TABLENAME = "C_CashLine";
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 22/08/2012, 03:03:23
	 * @param ctx
	 * @param ID
	 */
	public MBDocType(Context ctx, int ID) {
		super(ctx, ID);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 22/08/2012, 03:03:26
	 * @param ctx
	 * @param rs
	 */
	public MBDocType(Context ctx, Cursor rs) {
		super(ctx, rs);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 22/08/2012, 03:03:49
	 * @param ctx
	 * @param con_tx
	 * @param ID
	 */
	public MBDocType(Context ctx, DB con_tx, int ID) {
		super(ctx, con_tx, ID);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.sg.model.MP#beforeUpdate()
	 */
	@Override
	public boolean beforeSave(boolean isNew) {
		if(isNew){
			//
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
	
	/**
	 * Obtiene el tipo de documento por defecto,
	 * se le debe pasar como parámetro el tipo de diocumento base y
	 * el sub-Tipo de OV en caso de las 
	 * Ordenes de Venta y Autorizaciones de Devolución
	 * Si son varios tipos de documento base se separan por "," 
	 * tomando en cuenta que es un valor String
	 * @author Yamel Senih 31/10/2012, 17:48:03
	 * @param ctx
	 * @param con_tx
	 * @param m_AD_User_ID
	 * @param m_DocBaseType
	 * @param m_DocSubTypeSO
	 * @return
	 * @return int
	 */
	public static int getC_DocType_ID(Context ctx, DB con_tx, int m_AD_User_ID, 
			String m_DocBaseType, String m_DocSubTypeSO){
		int m_C_DocType_ID = 0;
		//	Sql
		String sql = new String("SELECT " +
				"dt.C_DocType_ID " +
				"FROM C_DocType dt " +
				"WHERE dt.DocBaseType IN(" + m_DocBaseType + ") ");
				if(m_DocSubTypeSO != null)
					sql += "dt.DocSubTypeSO IN(" + m_DocSubTypeSO + ") ";
				sql += "LIMIT 1";
		//	Cursor
		Cursor rs = con_tx.querySQL(sql, null);
    	
		if(rs.moveToFirst()){
			m_C_DocType_ID = rs.getInt(0);
    	}
		
		return m_C_DocType_ID;
	}
	
}
