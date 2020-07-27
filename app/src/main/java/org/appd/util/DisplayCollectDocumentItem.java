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
public class DisplayCollectDocumentItem extends DisplayItem {

	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 19/10/2012, 17:50:07
	 * @param record_ID
	 * @param name
	 * @param description
	 */
	public DisplayCollectDocumentItem(int record_ID, String name, String description) {
		super(record_ID, name, description);
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 19/10/2012, 17:53:47
	 * @param m_CashType
	 * @param m_Amount
	 * @param m_ReferenceNo
	 * @param m_DateDoc
	 * @param m_C_Bank_ID
	 * @param m_Bank
	 */
	public DisplayCollectDocumentItem(String m_CashType, 
			String m_Amount, String m_ReferenceNo, 
			String m_DateDoc, int m_C_Bank_ID, String m_Bank){
		super(0, null, null);
		//	Set Values
		this.m_TenderType = m_CashType;
		this.m_Amount = m_Amount;
		this.m_ReferenceNo = m_ReferenceNo;
		this.m_DateDoc = m_DateDoc;
		this.m_C_Bank_ID = m_C_Bank_ID;
		this.m_Bank = m_Bank;
	}
	
	/**	Cash Type				*/
	private String m_TenderType = "X";
	/**	Amount					*/
	private String m_Amount = null;
	/**	Reference				*/
	private String m_ReferenceNo = null;
	/**	Document Date			*/
	private String m_DateDoc = null;
	/**	Bank ID					*/
	private int 	m_C_Bank_ID = 0;
	/**	Bank					*/
	private String m_Bank = null;
	/**	Routing No				*/
	private String m_RoutingNo = null;
	/**	Account No				*/
	private String m_AccountNo = null;
	/**	Credit Card Type		*/
	private String m_CreditCardType = null;
	/**	Credit Card Number		*/
	private String m_CreditCardNumber = null;
	/**	Exp. Month				*/
	private String m_CreditCardExpMM = null;
	/**	Exp. Year				*/
	private String m_CreditCardExpYY = null;
	/**	Verification Code		*/
	private String m_CreditCardVV = null;
	/**	Name					*/
	private String m_A_Name = null;
	/**	Is Pivot				*/
	private boolean m_IsPivot = false;
	
	
	/**
	 * Set A_Name
	 * @author Yamel Senih 31/10/2012, 12:03:13
	 * @param m_A_Name
	 * @return void
	 */
	public void setA_Name(String m_A_Name){
		this.m_A_Name = m_A_Name;
	}
	
	/**
	 * Get A_Name
	 * @author Yamel Senih 31/10/2012, 12:03:08
	 * @return
	 * @return String
	 */
	public String getA_Name(){
		return m_A_Name;
	}
	
	/**
	 * Set CreditCardVV
	 * @author Yamel Senih 31/10/2012, 12:02:44
	 * @param m_CreditCardVV
	 * @return void
	 */
	public void setCreditCardVV(String m_CreditCardVV){
		this.m_CreditCardVV = m_CreditCardVV;
	}
	
	/**
	 * Get CreditCardVV
	 * @author Yamel Senih 31/10/2012, 12:02:40
	 * @return
	 * @return String
	 */
	public String getCreditCardVV(){
		return m_CreditCardVV;
	}
	
	/**
	 * Set CreditCardExpYY
	 * @author Yamel Senih 31/10/2012, 12:02:05
	 * @param m_CreditCardExpYY
	 * @return void
	 */
	public void setCreditCardExpYY(String m_CreditCardExpYY){
		this.m_CreditCardExpYY = m_CreditCardExpYY;
	}
	
	/**
	 * Get CreditCardExpYY
	 * @author Yamel Senih 31/10/2012, 12:02:10
	 * @return
	 * @return String
	 */
	public String getCreditCardExpYY(){
		return m_CreditCardExpYY;
	}
	
	/**
	 * Set CreditCardExpMM
	 * @author Yamel Senih 31/10/2012, 12:01:33
	 * @param m_CreditCardExpMM
	 * @return void
	 */
	public void setCreditCardExpMM(String m_CreditCardExpMM){
		this.m_CreditCardExpMM = m_CreditCardExpMM;
	}
	
	/**
	 * Get CreditCardExpMM
	 * @author Yamel Senih 31/10/2012, 12:01:28
	 * @return
	 * @return String
	 */
	public String getCreditCardExpMM(){
		return m_CreditCardExpMM;
	}
	
	/**
	 * Set CreditCardNumber
	 * @author Yamel Senih 31/10/2012, 12:00:44
	 * @param m_CreditCardNumber
	 * @return void
	 */
	public void setCreditCardNumber(String m_CreditCardNumber){
		this.m_CreditCardNumber = m_CreditCardNumber;
	}
	
	/**
	 * Get CreditCardNumber
	 * @author Yamel Senih 31/10/2012, 12:00:36
	 * @return
	 * @return String
	 */
	public String getCreditCardNumber(){
		return m_CreditCardNumber;
	}
	
	/**
	 * Set CreditCardType
	 * @author Yamel Senih 31/10/2012, 11:59:56
	 * @param m_CreditCardType
	 * @return void
	 */
	public void setCreditCardType(String m_CreditCardType){
		this.m_CreditCardType = m_CreditCardType;
	}
	
	/**
	 * Get CreditCardType
	 * @author Yamel Senih 31/10/2012, 12:00:03
	 * @return
	 * @return String
	 */
	public String getCreditCardType(){
		return m_CreditCardType;
	}
	
	
	/**
	 * Set AccountNo
	 * @author Yamel Senih 31/10/2012, 11:58:37
	 * @param m_AccountNo
	 * @return void
	 */
	public void setAccountNo(String m_AccountNo){
		this.m_AccountNo = m_AccountNo;
	}
	
	/**
	 * Get AccountNo
	 * @author Yamel Senih 31/10/2012, 11:58:00
	 * @return
	 * @return String
	 */
	public String getAccountNo(){
		return m_AccountNo;
	}
	
	
	/**
	 * Set RoutingNo
	 * @author Yamel Senih 31/10/2012, 11:48:55
	 * @param m_RoutingNo
	 * @return void
	 */
	public void setRoutingNo(String m_RoutingNo){
		this.m_RoutingNo = m_RoutingNo;
	}
	
	/**
	 * Get RoutingNo
	 * @author Yamel Senih 31/10/2012, 11:49:07
	 * @return
	 * @return String
	 */
	public String getRoutingNo(){
		return m_RoutingNo;
	}
	
	/**
	 * Obtiene el ID del Banco
	 * @author Yamel Senih 19/10/2012, 17:55:51
	 * @return
	 * @return int
	 */
	public int getC_Bank_ID(){
		return m_C_Bank_ID;
	}
	
	/**
	 * Establece el ID del Banco
	 * @author Yamel Senih 19/10/2012, 17:55:29
	 * @param m_C_Bank_ID
	 * @return void
	 */
	public void setC_Bank_ID(int m_C_Bank_ID){
		this.m_C_Bank_ID = m_C_Bank_ID;
	}
	
	
	/**
	 * Obtiene el nombre del banco
	 * @author Yamel Senih 18/10/2012, 10:27:31
	 * @return
	 * @return String
	 */
	public String getBank(){
		return m_Bank;
	}
	
	/**
	 * Establece el nombre del Banco
	 * @author Yamel Senih 18/10/2012, 10:27:22
	 * @param m_Bank
	 * @return void
	 */
	public void setBank(String m_Bank){
		this.m_Bank = m_Bank;
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
	 * Obtiene la referencia del documento de cobro
	 * @author Yamel Senih 18/10/2012, 10:25:17
	 * @return
	 * @return String
	 */
	public String getReferenceNo(){
		return m_ReferenceNo;
	}
	
	/**
	 * Establece la referencia del documento de cobro
	 * @author Yamel Senih 18/10/2012, 10:24:56
	 * @param m_ReferenceNo
	 * @return void
	 */
	public void setReferenceNo(String m_ReferenceNo){
		this.m_ReferenceNo = m_ReferenceNo;
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
	 * Establece el tipo de Cobro
	 * @author Yamel Senih 18/10/2012, 10:16:23
	 * @param m_TenderType
	 * @return void
	 */
	public void setTenderType(String m_TenderType){
		this.m_TenderType = m_TenderType;
	}
	
	/**
	 * Obtiene el tipo de Cobro
	 * @author Yamel Senih 18/10/2012, 10:16:48
	 * @return
	 * @return String
	 */
	public String getTenderType(){
		return m_TenderType;
	}
	
	/**
	 * Obtiene el valor en BigDecimal
	 * @author Yamel Senih 23/10/2012, 01:05:43
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
	
	/**
	 * Establece si el registro es pivote
	 * @author Yamel Senih 23/10/2012, 01:53:16
	 * @param pivot
	 * @return void
	 */
	public void setIsPivot(boolean pivot){
		this.m_IsPivot = pivot;
	}
	
	/**
	 * Verifica si el registro es pivote
	 * @author Yamel Senih 23/10/2012, 01:53:54
	 * @return
	 * @return boolean
	 */
	public boolean isPivot(){
		return m_IsPivot;
	}
	
	public String toString(){
		return "TenderType=" + m_TenderType + 
				"\nAmount=" + m_Amount + 
				"\nReferenceNo=" + m_ReferenceNo + 
				"\nDateDoc=" + m_DateDoc + 
				"\nC_Bank_ID=" + m_C_Bank_ID + 
				"\nBank=" + m_Bank + 
				"\nRoutingNo=" + m_RoutingNo + 
				"\nAccountNo=" + m_AccountNo + 
				"\nCreditCardType=" + m_CreditCardType + 
				"\nCreditCardNumber=" + m_CreditCardNumber + 
				"\nCreditCardExpMM=" + m_CreditCardExpMM + 
				"\nCreditCardExpYY=" + m_CreditCardExpYY + 
				"\nCreditCardVV=" + m_CreditCardVV + 
				"\nA_Name=" + m_A_Name + 
				"\nIsPivot=" + m_IsPivot + 
				"\n---------------";
	}
}
