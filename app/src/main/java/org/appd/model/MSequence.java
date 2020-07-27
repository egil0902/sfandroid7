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

import android.database.Cursor;

/**
 * @author Yamel Senih
 *
 */
public class MSequence {

	/**
	 * Obtiene la siguiente secuencia para un tipo de documento,
	 * retorna null sino se encuentra coincidencia con los parametros
	 * en la busqueda
	 * @author Yamel Senih 11/05/2012, 14:57:19
	 * @param con
	 * @param m_C_DocType_ID
	 * @param m_AD_User_ID
	 * @return
	 * @return String
	 */
	public static String getCurrentNext(DB con, int m_C_DocType_ID, int m_AD_User_ID){
		String m_Sequence = null;
		
		String sql = new String("SELECT s.PrefixValue, s.CurrentNext, s.SuffixValue " +
				"FROM XX_MB_Sequence s " +
				"WHERE s.C_DocType_ID = ? " +
				"AND AD_User_ID = ?");
		//	
		Cursor rs = con.querySQL(sql, new String[]{String.valueOf(m_C_DocType_ID), String.valueOf(m_AD_User_ID)});
    	
		if(rs.moveToFirst()){
    		String m_PrefixValue = rs.getString(0);
    		int m_CurrentNext = rs.getInt(1);
    		String m_SuffixValue = rs.getString(2);
    		m_Sequence = new String();
    		if(m_PrefixValue != null)
    			m_Sequence += m_PrefixValue;
    		m_Sequence += m_CurrentNext;
    		if(m_SuffixValue != null)
    			m_Sequence += m_SuffixValue;
    	}
		
		return m_Sequence;
	}
	
	/**
	 * Actualiza la Secuencia del Documento
	 * @author Yamel Senih 11/05/2012, 17:51:04
	 * @param con
	 * @param m_C_DocType_ID
	 * @param m_AD_User_ID
	 * @return void
	 */
	public static void updateSequence(DB con, int m_C_DocType_ID, int m_AD_User_ID){
		String sqlU = new String("UPDATE XX_MB_Sequence " +
				"SET CurrentNext = CurrentNext + 1, " +
				"S_XX_MB_Sequence_ID = NULL " +
				"WHERE C_DocType_ID = " + m_C_DocType_ID + 
				" AND AD_User_ID = " + m_AD_User_ID);
		//	Update Sequence
		con.executeSQL(sqlU);
	}
}
