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
package org.appd.util;

/**
 * @author Yamel Senih
 *
 */
public class DisplayRMAItem extends DisplayItem {

	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 16/08/2012, 16:17:46
	 * @param record_ID
	 * @param name
	 * @param description
	 */
	public DisplayRMAItem(int record_ID, String name, String description) {
		super(record_ID, name, description);
		// TODO Auto-generated constructor stub
	}
	
	
	public DisplayRMAItem(int record_ID, int m_M_Product_ID, 
			String name, String description, 
			String qtyReturn, String amt, 
			String lineNetAmt) {
		this(record_ID, name, description);
		this.m_M_Product_ID = m_M_Product_ID;
		this.qtyReturn = qtyReturn;
		this.amt = amt;
		this.lineNetAmt = lineNetAmt;
	}
	
	private int m_M_Product_ID = 0;
	/**	Qty Ordered			*/
	private String qtyReturn = null;
	/**	Amt					*/
	private String amt = null;
	/**	Line Net Amount		*/
	private String lineNetAmt = null;
	
	/**
	 * Cantidad a Devolver
	 * @author Yamel Senih 16/08/2012, 16:20:28
	 * @return
	 * @return String
	 */
	public String getQtyReturn(){
		return qtyReturn;
	}

	/**
	 * Obtiene el monto
	 * @author Yamel Senih 16/08/2012, 16:20:50
	 * @return
	 * @return String
	 */
	public String getProductPrice(){
		return amt;
	}
	
	/**
	 * Obtiene el Neto de linea
	 * @author Yamel Senih 15/05/2012, 15:02:12
	 * @return
	 * @return double
	 */
	public String getLineNetAmt(){
		return lineNetAmt;
	}
	
	/**
	 * Obtiene el ID del Producto
	 * @author Yamel Senih 23/05/2012, 02:30:42
	 * @return
	 * @return int
	 */
	public int getM_Product_ID(){
		return m_M_Product_ID;
	}
}
