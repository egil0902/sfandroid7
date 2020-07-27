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
public class Tax {

	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 24/05/2012, 01:45:07
	 * @param con
	 */
	public Tax(DB con){
		this.con = con;
	}
	
	/**	Connection			*/
	private DB con = null;
	/**	Tax ID				*/
	private int m_C_Tax_ID = 0;
	/**	Rate				*/
	private double rate = 0;
	
	/**
	 * Carga el impuesto por defecto de una Categoria de Impuesto
	 * @author Yamel Senih 24/05/2012, 02:36:14
	 * @param m_C_TaxCategory_ID
	 * @return void
	 */
	public void loadTax(int m_C_TaxCategory_ID){
		
		String sql = new String("SELECT " +
				"tx.C_Tax_ID, " +
				"tx.Rate " +
				"FROM C_TaxCategory tc " +
				"INNER JOIN C_Tax tx ON(tx.C_TaxCategory_ID = tc.C_TaxCategory_ID) " +
				"WHERE tc.C_TaxCategory_ID = " + m_C_TaxCategory_ID + " " + 
				"AND tx.IsDefault = 'Y'");
		
		Cursor rs = con.querySQL(sql, null);
		if(rs.moveToFirst()){
			m_C_Tax_ID = rs.getInt(0);
			rate = rs.getDouble(1);
		}
    }
	
	/**
	 * Obtiene el ID del Impuesto
	 * @author Yamel Senih 24/05/2012, 01:31:00
	 * @return
	 * @return int
	 */
	public int getC_Tax_ID(){
		return m_C_Tax_ID;
	}
	
	/**
	 * Obtiene la tasa de impuesto
	 * @author Yamel Senih 24/05/2012, 01:31:29
	 * @return
	 * @return double
	 */
	public double getRate(){
		return rate;
	}
}
