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
public class DisplayCashLineItem extends DisplayItem {

	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 18/10/2012, 09:42:31
	 * @param record_ID
	 * @param name
	 * @param description
	 */
	public DisplayCashLineItem(int record_ID, String name, String description) {
		super(record_ID, name, description);
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 10/11/2012, 12:08:46
	 * @param record_ID
	 * @param m_Selected
	 * @param m_C_Invoice_ID
	 * @param m_DocInvoice
	 * @param m_CashType
	 * @param m_TenderType
	 * @param m_GrandTotal
	 * @param m_Amount
	 * @param isOverUnderPayment
	 * @param m_WriteOffAmt
	 * @param m_ReferenceNo
	 * @param m_DateDoc
	 * @param m_Bank
	 * @param m_C_Payment_ID
	 * @param m_C_PaymentAllocate_ID
	 * @param m_RoutingNo
	 * @param m_AccountNo
	 * @param m_CreditCardType
	 * @param m_CreditCardNumber
	 * @param m_CreditCardExpMM
	 * @param m_CreditCardExpYY
	 * @param m_CreditCardVV
	 * @param m_A_Name
	 */
	public DisplayCashLineItem(int record_ID, boolean m_Selected, int m_C_Invoice_ID, String m_DocInvoice, 
			String m_CashType, String m_TenderType, String m_GrandTotal,
			String m_Amount, String isOverUnderPayment, String m_WriteOffAmt, 
			String m_ReferenceNo, String m_DateDoc, String m_Bank, 
			int m_C_Payment_ID, int m_C_PaymentAllocate_ID, String m_RoutingNo, 
			String m_AccountNo, String m_CreditCardType, String m_CreditCardNumber, 
			String m_CreditCardExpMM, String m_CreditCardExpYY, String m_CreditCardVV, String m_A_Name){
		super(record_ID, null, null);
		//	Set Values
		this.m_Selected= m_Selected; 
		this.m_DocInvoice = m_DocInvoice;
		this.m_CashType = m_CashType;
		this.m_TenderType = m_TenderType;
		this.m_GrandTotal = m_GrandTotal;
		this.m_Amount = m_Amount;
		this.isOverUnderPayment = isOverUnderPayment;
		this.m_WriteOffAmt = m_WriteOffAmt;
		this.m_ReferenceNo = m_ReferenceNo;
		this.m_DateDoc = m_DateDoc;
		this.m_Bank = m_Bank;
		this.m_C_Payment_ID = m_C_Payment_ID;
		this.m_C_PaymentAllocate_ID = m_C_PaymentAllocate_ID;
		this.m_RoutingNo = m_RoutingNo;
		this.m_AccountNo = m_AccountNo;
		this.m_CreditCardType = m_CreditCardType;
		this.m_CreditCardNumber = m_CreditCardNumber;
		this.m_CreditCardExpMM = m_CreditCardExpMM;
		this.m_CreditCardExpYY = m_CreditCardExpYY;
		this.m_CreditCardVV = m_CreditCardVV;
		this.m_A_Name = m_A_Name;
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 10/11/2012, 12:14:02
	 * @param record_ID
	 * @param m_C_Invoice_ID
	 * @param m_DocInvoice
	 * @param m_CashType
	 * @param m_TenderType
	 * @param m_GrandTotal
	 * @param m_Amount
	 * @param isOverUnderPayment
	 * @param m_WriteOffAmt
	 * @param m_ReferenceNo
	 * @param m_DateDoc
	 * @param m_Bank
	 * @param m_C_Payment_ID
	 * @param m_C_PaymentAllocate_ID
	 * @param m_RoutingNo
	 * @param m_AccountNo
	 * @param m_CreditCardType
	 * @param m_CreditCardNumber
	 * @param m_CreditCardExpMM
	 * @param m_CreditCardExpYY
	 * @param m_CreditCardVV
	 * @param m_A_Name
	 */
	public DisplayCashLineItem(int record_ID, int m_C_Invoice_ID, String m_DocInvoice, 
			String m_CashType, String m_TenderType, String m_GrandTotal,
			String m_Amount, String isOverUnderPayment, String m_WriteOffAmt, 
			String m_ReferenceNo, String m_DateDoc, String m_Bank, 
			int m_C_Payment_ID, int m_C_PaymentAllocate_ID, String m_RoutingNo, 
			String m_AccountNo, String m_CreditCardType, String m_CreditCardNumber, 
			String m_CreditCardExpMM, String m_CreditCardExpYY, String m_CreditCardVV, String m_A_Name){
		super(record_ID, null, null);
		//	Set Values
		this.m_DocInvoice = m_DocInvoice;
		this.m_CashType = m_CashType;
		this.m_TenderType = m_TenderType;
		this.m_GrandTotal = m_GrandTotal;
		this.m_Amount = m_Amount;
		this.isOverUnderPayment = isOverUnderPayment;
		this.m_WriteOffAmt = m_WriteOffAmt;
		this.m_ReferenceNo = m_ReferenceNo;
		this.m_DateDoc = m_DateDoc;
		this.m_Bank = m_Bank;
		this.m_C_Payment_ID = m_C_Payment_ID;
		this.m_C_PaymentAllocate_ID = m_C_PaymentAllocate_ID;
		this.m_RoutingNo = m_RoutingNo;
		this.m_AccountNo = m_AccountNo;
		this.m_CreditCardType = m_CreditCardType;
		this.m_CreditCardNumber = m_CreditCardNumber;
		this.m_CreditCardExpMM = m_CreditCardExpMM;
		this.m_CreditCardExpYY = m_CreditCardExpYY;
		this.m_CreditCardVV = m_CreditCardVV;
		this.m_A_Name = m_A_Name;
	}
	
	
	/**	Selected				*/
	private boolean m_Selected;
	/**	Invoice					*/
	private int 	m_C_Invoice_ID = 0;
	/**	DocInvoice				*/
	private String m_DocInvoice = null;
	/**	Cash Type				*/
	private String m_CashType = null;
	/**	Table Name				*/
	private String m_TenderType = null;
	/**	Grand Total				*/
	private String m_GrandTotal = null;
	/**	Amount					*/
	private String m_Amount = null;
	/**	Over Under Payment		*/
	private String isOverUnderPayment = null;
	/**	Write Off Amt			*/
	private String m_WriteOffAmt = null;
	/**	Reference				*/
	private String m_ReferenceNo = null;
	/**	Document Date			*/
	private String m_DateDoc = null;
	/**	Bank					*/
	private String m_Bank = null;
	/**	Payment					*/
	private int m_C_Payment_ID = 0;
	/**	PaymentAllocate			*/
	private int m_C_PaymentAllocate_ID = 0;
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
	
	/**
	 * Obtiene el Valor de la Seleccion
	 * @author Yamel Senih 10/11/2012, 12:09:22
	 * @return
	 * @return boolean
	 */
	public boolean isSelected(){
		return m_Selected;
	}
	
	/**
	 * Establece el valor de la seleccion
	 * @author Yamel Senih 10/11/2012, 12:09:46
	 * @param m_Selected
	 * @return void
	 */
	public void setSelected(boolean m_Selected){
		this.m_Selected = m_Selected;
	}
	
	
	/**
	 * Set Payment Allocate
	 * @author Yamel Senih 31/10/2012, 17:32:41
	 * @param m_C_PaymentAllocate_ID
	 * @return void
	 */
	public void setC_PaymentAllocate_ID(int m_C_PaymentAllocate_ID){
		this.m_C_PaymentAllocate_ID = m_C_PaymentAllocate_ID;
	}
	
	/**
	 * Get Payment Allocate
	 * @author Yamel Senih 31/10/2012, 17:32:31
	 * @return
	 * @return int
	 */
	public int getC_PaymentAllocate_ID(){
		return m_C_PaymentAllocate_ID;
	}
	
	/**
	 * Set Payment
	 * @author Yamel Senih 31/10/2012, 17:32:25
	 * @param m_C_Payment_ID
	 * @return void
	 */
	public void setC_Payment_ID(int m_C_Payment_ID){
		this.m_C_Payment_ID = m_C_Payment_ID;
	}
	
	/**
	 * Get Payment
	 * @author Yamel Senih 31/10/2012, 17:32:16
	 * @return
	 * @return int
	 */
	public int getC_Payment_ID(){
		return m_C_Payment_ID;
	}
	
	
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
	public String getWriteOffAmt(){
		return m_WriteOffAmt;
	}
	
	/**
	 * Establece el monto pendiente
	 * @author Yamel Senih 18/10/2012, 10:21:41
	 * @param m_WriteOffAmt
	 * @return void
	 */
	public void setWriteOffAmt(String m_WriteOffAmt){
		this.m_WriteOffAmt = m_WriteOffAmt;
	}
	
	/**
	 * Establece el valor del Documento de Factura
	 * @author Yamel Senih 18/10/2012, 10:19:00
	 * @param m_DocInvoice
	 * @return void
	 */
	public void setDocInvoice(String m_DocInvoice){
		this.m_DocInvoice = m_DocInvoice;
	}
	
	/**
	 * Obtiene el valor del documento de factura
	 * @author Yamel Senih 18/10/2012, 10:19:48
	 * @return
	 * @return String
	 */
	public String getDocInvoice(){
		return m_DocInvoice;
	}
	
	/**
	 * Establece si es Abono
	 * @author Yamel Senih 18/10/2012, 10:13:56
	 * @param eventTypeValue
	 * @return void
	 */
	public void setIsOverUnderPayment(String eventTypeValue){
		this.isOverUnderPayment = eventTypeValue;
	}
	
	/**
	 * Obtiene el valor de Abono
	 * @author Yamel Senih 18/10/2012, 10:14:36
	 * @return
	 * @return String
	 */
	public String isOverUnderPayment(){
		return isOverUnderPayment;
	}
	
	/**
	 * Establece el ID de la Factura
	 * @author Yamel Senih 18/10/2012, 10:15:10
	 * @param m__C_Invoice_ID
	 * @return void
	 */
	public void set_C_Invoice_ID(int m__C_Invoice_ID){
		this.m_C_Invoice_ID = m__C_Invoice_ID;
	}
	
	/**
	 * Obtiene el ID de la Factura
	 * @author Yamel Senih 18/10/2012, 10:15:34
	 * @return
	 * @return int
	 */
	public int get_C_Invoice_ID(){
		return m_C_Invoice_ID;
	}
	
	/**
	 * Establece el tipo de Cobro
	 * @author Yamel Senih 18/10/2012, 10:16:23
	 * @param m_CashType
	 * @return void
	 */
	public void setCashType(String m_CashType){
		this.m_CashType = m_CashType;
	}
	
	/**
	 * Obtiene el tipo de Cobro
	 * @author Yamel Senih 18/10/2012, 10:16:48
	 * @return
	 * @return String
	 */
	public String getCashType(){
		return m_CashType;
	}
	
	/**
	 * Establece el tipo de documento de cobro
	 * @author Yamel Senih 18/10/2012, 10:17:16
	 * @param m_TenderType
	 * @return void
	 */
	public void setTenderType(String m_TenderType){
		this.m_TenderType = m_TenderType;
	}
	
	/**
	 * Obtiene el tipo de cocumento de cobro
	 * @author Yamel Senih 18/10/2012, 10:17:41
	 * @return
	 * @return String
	 */
	public String getTenderType(){
		return m_TenderType;
	}
	
	public String toString(){
		return "m_DocInvoice = " + m_DocInvoice + "\n" + 
		"m_CashType = " + m_CashType + "\n" +
		"m_TenderType = " + m_TenderType + "\n" +
		"m_GrandTotal = " + m_GrandTotal + "\n" +
		"m_Amount = " + m_Amount + "\n" +
		"isOverUnderPayment = " + isOverUnderPayment + "\n" +
		"m_WriteOffAmt = " + m_WriteOffAmt + "\n" +
		"m_ReferenceNo = " + m_ReferenceNo + "\n" +
		"m_DateDoc = " + m_DateDoc + "\n" +
		"m_Bank = " + m_Bank; 
	}
}
