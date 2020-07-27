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

import java.math.BigDecimal;

import org.appd.base.Env;

/**
 * @author Yamel Senih
 *
 */
public class DisplayOpenItem extends DisplayItem {

	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 19/10/2012, 17:43:55
	 * @param record_ID
	 * @param name
	 * @param description
	 */
	public DisplayOpenItem(int record_ID, String name, String description) {
		super(record_ID, name, description);
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 19/10/2012, 17:49:48
	 * @param record_ID
	 * @param m_DocumentNo
	 * @param m_GrandTotal
	 * @param m_Amount
	 * @param m_OpenAmt
	 * @param m_DateDoc
	 */
	public DisplayOpenItem(int record_ID, String m_DocumentNo, String m_GrandTotal,
			String m_Amount, String m_OpenAmt, String m_DateDoc){
		super(record_ID, null, null);
		//	Set Values
		this.m_DocumentNo = m_DocumentNo;
		this.m_GrandTotal = m_GrandTotal;
		this.m_Amount = m_Amount;
		this.m_OpenAmt = m_OpenAmt;
		this.m_DateDoc = m_DateDoc;
	}
	
	/**	Selected				*/
	private boolean m_Selected;
	/**	DocumentNo				*/
	private String m_DocumentNo = null;
	/**	Grand Total				*/
	private String m_GrandTotal = null;
	/**	Amount					*/
	private String m_Amount = null;
	/**	Open Amt				*/
	private String m_OpenAmt = null;
	/**	Document Date			*/
	private String m_DateDoc = null;
	
	/**
	 * Obtiene el valor de la seleccion
	 * @author Yamel Senih 22/10/2012, 09:58:49
	 * @return
	 * @return boolean
	 */
	public boolean isSelected(){
		return m_Selected;
	}
	
	/**
	 * Establece el valor de la seleccion
	 * @author Yamel Senih 22/10/2012, 09:58:29
	 * @param m_Selected
	 * @return void
	 */
	public void setSelected(boolean m_Selected){
		this.m_Selected = m_Selected;
	}
	
	
	/**
	 * Obtiene la fecha del documento de cobro
	 * @author Yamel Senih 18/10/2012, 10:26:25
	 * @return
	 * @return String
	 */
	public String getDateDoc(){
		return m_DateDoc;
	}
	
	/**
	 * Establece la fecha del documento de cobro
	 * @author Yamel Senih 18/10/2012, 10:26:10
	 * @param m_DateDoc
	 * @return void
	 */
	public void setDateDoc(String m_DateDoc){
		this.m_DateDoc = m_DateDoc;
	}
	
	
	/**
	 * Obtiene el monto Cobrado
	 * @author Yamel Senih 18/10/2012, 10:23:34
	 * @return
	 * @return String
	 */
	public String getAmount(){
		return m_Amount;
	}
	
	/**
	 * Establece el monto cobrado
	 * @author Yamel Senih 18/10/2012, 10:23:16
	 * @param m_Amount
	 * @return void
	 */
	public void setAmount(String m_Amount){
		this.m_Amount = m_Amount;
	}
	
	/**
	 * Obtiene el monto de la factura
	 * @author Yamel Senih 18/10/2012, 16:51:09
	 * @return
	 * @return String
	 */
	public String getGrandTotal(){
		return m_GrandTotal;
	}
	
	/**
	 * Establece el monto de la factura
	 * @author Yamel Senih 18/10/2012, 16:51:21
	 * @param m_Amount
	 * @return void
	 */
	public void setGrandTotal(String m_Amount){
		this.m_GrandTotal = m_Amount;
	}
	
	/**
	 * Obtiene el monto pendiente
	 * @author Yamel Senih 18/10/2012, 10:22:14
	 * @return
	 * @return String
	 */
	public String getOpenAmt(){
		return m_OpenAmt;
	}
	
	/**
	 * Establece el monto pendiente
	 * @author Yamel Senih 18/10/2012, 10:21:41
	 * @param m_WriteOffAmt
	 * @return void
	 */
	public void setOpenAmt(String m_WriteOffAmt){
		this.m_OpenAmt = m_WriteOffAmt;
	}
	
	/**
	 * Establece el valor del Documento de Factura
	 * @author Yamel Senih 18/10/2012, 10:19:00
	 * @param m_DocInvoice
	 * @return void
	 */
	public void setDocInvoice(String m_DocInvoice){
		this.m_DocumentNo = m_DocInvoice;
	}
	
	/**
	 * Obtiene el valor del documento de factura
	 * @author Yamel Senih 18/10/2012, 10:19:48
	 * @return
	 * @return String
	 */
	public String getDocInvoice(){
		return m_DocumentNo;
	}
	
	/**
	 * Obtiene el ID de la Factura
	 * @author Yamel Senih 18/10/2012, 10:15:34
	 * @return
	 * @return int
	 */
	public int getC_Invoice_ID(){
		return getRecord_ID();
	}
	
	/**
	 * Valida que el monto introducido no exceda al de la factura
	 * @author Yamel Senih 22/10/2012, 12:42:42
	 * @param amt
	 * @return
	 * @return BigDecimal
	 */
	public BigDecimal validExced(String amt){
		BigDecimal amtTestConv = Env.ZERO;
		BigDecimal amtMaxConv = Env.ZERO;
		if(amt != null
				&& amt.length() > 0)
			amtTestConv = new BigDecimal(amt);
			
		if(getOpenAmt() != null
				&& getOpenAmt().length() > 0)
			amtMaxConv = new BigDecimal(getOpenAmt());
			
		return amtMaxConv.subtract(amtTestConv);
	}
	
	/**
	 * Obtiene el monto en formato BigDecimal
	 * @author Yamel Senih 23/10/2012, 17:06:58
	 * @param amt
	 * @return
	 * @return BigDecimal
	 */
	/*public BigDecimal getBigDecimal(String amt){
		BigDecimal amtTestConv = Env.ZERO;
		if(amt != null
				&& amt.length() > 0)
			amtTestConv = new BigDecimal(amt);
				
		return amtTestConv;
	}*/
	
}
