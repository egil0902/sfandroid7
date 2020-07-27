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
package org.appd.view;

import java.math.BigDecimal;

import org.appd.base.DB;
import org.appd.base.Env;
import org.appd.base.R;
import org.appd.interfaces.I_CancelOk;
import org.appd.model.MBCash;
import org.appd.model.MBCashLine;
import org.appd.model.MBPayment;
import org.appd.model.MBPaymentAllocate;
import org.appd.model.MBVisit;
import org.appd.model.MBVisitLine;
import org.appd.model.MPTableInfo;
import org.appd.util.AdapterCollectDocumentItem;
import org.appd.util.AdapterOpenItem;
import org.appd.util.DisplayCollectDocumentItem;
import org.appd.util.DisplayOpenItem;
import org.appd.util.Msg;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Yamel Senih
 * Modelo
 *
 */
public class MV_AddCollect extends Activity implements I_CancelOk {
	/**	Intent						*/
	private Intent			intent;
	/**	Open Items					*/
	private ListView 		listOpenItems;
	/**	Document Collect			*/
	private ListView 		listCollectDocuments;
	/**	Summary Open Amt			*/
	private TextView 		tv_OpenAmt;
	/**	Summary Collect				*/
	private TextView 		tv_Amt;
	/**	Summary Difference			*/
	private TextView 		tv_Diff;
	private  ImageButton 	butt_Ok;
    private ImageButton 	butt_Cancel;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        super.setContentView(R.layout.lv_add_collect);
        
        listCollectDocuments = (ListView) findViewById(R.id.ls_CollectDocuments);
        listOpenItems = (ListView) findViewById(R.id.ls_OpenItems);
        tv_OpenAmt =  (TextView) findViewById(R.id.tv_OpenAmt);
        tv_Amt =  (TextView) findViewById(R.id.tv_Amt);
        tv_Diff =  (TextView) findViewById(R.id.tv_Diff);
        //	Temp
        //tv_Diff.setVisibility(View.GONE);
        
        butt_Ok = (ImageButton) findViewById(R.id.butt_Ok);
    	butt_Cancel = (ImageButton) findViewById(R.id.butt_Cancel);
    	
        butt_Ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				aceptAction();
			}
		});
    	//	
    	butt_Cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				cancelAction();
			}
		});

    	//	Load Records
    	loadData();
        AdapterCollectDocumentItem adapter = new AdapterCollectDocumentItem(this, tv_Amt);
        adapter.addCollectDocument(true);
        listCollectDocuments.setAdapter(adapter);
    }
    
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }

	/* (non-Javadoc)
	 * @see org.appd.interfaces.I_CancelOk#aceptAction()
	 */
	@Override
	public boolean aceptAction() {
		BigDecimal summaryOpenAmt = Env.ZERO;
		BigDecimal summaryAmt = Env.ZERO;
		if(tv_OpenAmt.getText() != null
				&& tv_OpenAmt.getText().length() > 0){
			summaryOpenAmt = new BigDecimal(tv_OpenAmt.getText().toString());
		}
		
		if(tv_Amt.getText() != null
				&& tv_Amt.getText().length() > 0){
			summaryAmt = new BigDecimal(tv_Amt.getText().toString());
		}
		//
		if(!summaryOpenAmt.equals(Env.ZERO)){
			if(!summaryAmt.equals(Env.ZERO)){
				if(summaryAmt.compareTo(summaryOpenAmt) <= 0){
					//
					processCollect(this);
				} else {
					Msg.alertMsg(this, getResources().getString(R.string.msg_Error), 
							getResources().getString(R.string.msg_AmtReceiptOverOpen));
				}
			} else {
				Msg.alertMsg(this, getResources().getString(R.string.msg_Error), 
						getResources().getString(R.string.msg_AmtReceiptZero));
			}
		} else {
			Msg.alertMsg(this, getResources().getString(R.string.msg_Error), 
					getResources().getString(R.string.msg_OpenAmtZero));
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see org.appd.interfaces.I_CancelOk#cancelAction()
	 */
	@Override
	public boolean cancelAction() {
		Builder ask = Msg.confirmMsg(this, getResources().getString(R.string.msg_UndoSelection));
    	String msg_Acept = this.getResources().getString(R.string.msg_Yes);
    	ask.setPositiveButton(msg_Acept, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				finish();
			}
		});
    	ask.show();
    	return true;
	}

	/* (non-Javadoc)
	 * @see org.appd.interfaces.I_CancelOk#getParam()
	 */
	@Override
	public Intent getParam() {
		return intent;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
	    if (resultCode == RESULT_OK) {
	    	if(data != null){
	    		/*DisplayRecordItem item = (DisplayRecordItem)data.getParcelableExtra("Record");
	    		int record_ID = item.getRecord_ID();
	    		if(record_ID > 0){
	    			cs_XX_MB_EventType_ID.setValue(record_ID, item.getCol_2());
    			} */
	    	}
	    }
	}
    
	/**
	 * Carga los datos de facturas pendientes
	 * @author Yamel Senih 22/10/2012, 11:41:24
	 * @return void
	 */
	private void loadData(){
		String sql = new String("SELECT " +
				"inv.C_Invoice_ID, " +
				"inv.DocumentNo, " +
				"inv.GrandTotal, " +
				"(inv.OpenAmt - COALESCE(SUM(CASE WHEN cl.DocAction IN('DR', 'CO') THEN cl.Amount ELSE 0 END), 0)) Amt, " +
				"(inv.OpenAmt - COALESCE(SUM(CASE WHEN cl.DocAction IN('DR', 'CO') THEN cl.Amount ELSE 0 END), 0)) OpenAmt, " +
				"strftime('%d/%m/%Y', inv.DateInvoiced) " +
				"FROM C_Invoice inv " +
				"LEFT JOIN XX_MB_RV_CollectInvoice cl ON(cl.C_Invoice_ID = inv.C_Invoice_ID) " +
				"WHERE inv.C_BPartner_Location_ID = " + Env.getContextAsInt(this, "#C_BPartner_Location_ID") + " " + 
				"GROUP BY inv.C_Invoice_ID " +
				"HAVING (inv.OpenAmt - COALESCE(SUM(CASE WHEN cl.DocAction IN('DR', 'CO') THEN cl.Amount ELSE 0 END), 0)) != 0 " +
				"ORDER BY inv.DateInvoiced ASC");
		
		DB con = new DB(this);
		con.openDB(DB.READ_ONLY);
		
		Cursor rs = con.querySQL(sql, null);
		AdapterOpenItem adapter = new AdapterOpenItem(this, tv_OpenAmt);
		if(rs.moveToFirst()){
			//	Establecer los totales
			do {
				adapter.addItem(new DisplayOpenItem(
						rs.getInt(0), 		//	C_Invoice_ID
						rs.getString(1), 	//	DocumentNo
						rs.getString(2), 	//	GrandTotal
						rs.getString(3), 	//	Amt
						rs.getString(4),	//	OpenAmt
						rs.getString(5)));	//	DateDoc
			} while(rs.moveToNext());
		}
		//
		listOpenItems.setAdapter(adapter);
		rs.close();
		con.closeDB(rs);
	}
	
	/**
	 * Crea las lineas de la caja
	 * @author Yamel Senih 28/10/2012, 22:47:18
	 * @return void
	 */
	private void createLines(){
		DB con = new DB(this);
		con.openDB(DB.READ_WRITE);
		con.beginTransaction();
		try{
			MBCash m_cash = MBCash.create(this, con, false);
			int m_C_Cash_ID = m_cash.getID();
			int m_C_Org_ID = Env.getAD_Org_ID(this);
			int m_C_BPartner_ID = Env.getContextAsInt(this, "#C_BPartner_ID");
			MBCashLine m_cashLine = new MBCashLine(this, con, 0);
			MPTableInfo tInfo = m_cashLine.getTableInfo();
			int pos_Cash_C_Cash_ID = tInfo.getColumnIndex("C_Cash_ID");
			int pos_Cash_C_Org_ID = tInfo.getColumnIndex("C_Org_ID");
			int pos_Cash_C_BPartner_ID = tInfo.getColumnIndex("C_BPartner_ID");
			int pos_Cash_IsOverUnderPayment = tInfo.getColumnIndex("IsOverUnderPayment");
			int pos_Cash_CashType = tInfo.getColumnIndex("CashType");
			int pos_Cash_Amount = tInfo.getColumnIndex("Amount");
			int pos_Cash_OverUnderAmt = tInfo.getColumnIndex("OverUnderAmt");
			int pos_Cash_TenderType = tInfo.getColumnIndex("TenderType");
			int pos_Cash_ReferenceNo = tInfo.getColumnIndex("ReferenceNo");
			int pos_Cash_DateDoc = tInfo.getColumnIndex("DateDoc");
			int pos_Cash_C_Bank_ID = tInfo.getColumnIndex("C_Bank_ID");
			int pos_Cash_C_Invoice_ID = tInfo.getColumnIndex("C_Invoice_ID");
			
			AdapterOpenItem adapterOI = (AdapterOpenItem) listOpenItems.getAdapter();
			AdapterCollectDocumentItem adapterCDI = (AdapterCollectDocumentItem) listCollectDocuments.getAdapter();
			
			BigDecimal invoiceEnteredAmt = Env.ZERO;
			BigDecimal diffiOpenAmtSetAmt = Env.ZERO;
			BigDecimal collectAmt = Env.ZERO;
			int max = adapterCDI.getCount();
			int current = 0;
			boolean fine = false;
			
			MBPayment payment = null;
			for(DisplayOpenItem openItem : adapterOI.getItems()){
				if(openItem.isSelected()){
					boolean repeat = true;
					if(current == max)
						break;
					diffiOpenAmtSetAmt = Env.getNumber(openItem.getOpenAmt()).subtract(Env.getNumber(openItem.getAmount()));
					do{
						boolean isOverUnderPayment = false;
						boolean isPaymentAllocate = false;
						BigDecimal amt = Env.ZERO;
						BigDecimal overUnderAmt = Env.ZERO;
						DisplayCollectDocumentItem cdItem = (DisplayCollectDocumentItem) adapterCDI.getItem(current);
						invoiceEnteredAmt = Env.getNumber(openItem.getAmount());
						collectAmt = Env.getNumber(cdItem.getAmount());
						//Log.i("-- ", cdItem.toString());
						if(invoiceEnteredAmt.compareTo(collectAmt) >= 0){
							overUnderAmt = invoiceEnteredAmt.subtract(collectAmt);
							amt = collectAmt;
							openItem.setAmount(overUnderAmt.toString());
							cdItem.setAmount(null);
							isOverUnderPayment = !overUnderAmt.add(diffiOpenAmtSetAmt).equals(Env.ZERO);
							isPaymentAllocate = false;
							//Log.i("I > C Creada ",  openItem.getDocInvoice() + " - " + invoiceAmt + " - " + amt + " - " + writeOfAmt + " P = " + current + " - " + cdItem.getBigDecimal(cdItem.getAmount()));
							current++;
						} else if(collectAmt.compareTo(invoiceEnteredAmt) > 0){
							amt = invoiceEnteredAmt;
							overUnderAmt = Env.ZERO;
							openItem.setAmount(overUnderAmt.toString());
							cdItem.setAmount(collectAmt.subtract(invoiceEnteredAmt).toString());
							isPaymentAllocate = true;
							isOverUnderPayment = !diffiOpenAmtSetAmt.equals(Env.ZERO);
							//Log.i("C > I Creada ",  openItem.getDocInvoice() + " - " + invoiceAmt + " - " + amt + " - " + writeOfAmt + " P = " + current + " - " + cdItem.getBigDecimal(cdItem.getAmount()));
						}
						
						//	Distribution
						if(cdItem.getTenderType().equals("X")
								|| cdItem.getTenderType().equals("K")){	//	Cash & Check
							//	Set CashLine
							m_cashLine.clear();
							m_cashLine.set_Value(pos_Cash_C_Cash_ID, m_C_Cash_ID);
							m_cashLine.set_Value(pos_Cash_C_Org_ID, m_C_Org_ID);
							m_cashLine.set_Value(pos_Cash_C_BPartner_ID, m_C_BPartner_ID);
							m_cashLine.set_Value(pos_Cash_IsOverUnderPayment, isOverUnderPayment? "Y": "N");
							m_cashLine.set_Value(pos_Cash_CashType, "I");
							m_cashLine.set_Value(pos_Cash_Amount, amt);
							//m_cashLine.set_Value(pos_Cash_WriteOffAmt, writeOfAmt);
							m_cashLine.set_Value(pos_Cash_OverUnderAmt, overUnderAmt.add(diffiOpenAmtSetAmt));
							m_cashLine.set_Value(pos_Cash_TenderType, cdItem.getTenderType());
							m_cashLine.set_Value(pos_Cash_ReferenceNo, cdItem.getReferenceNo());
							m_cashLine.set_Value(pos_Cash_DateDoc, cdItem.getDateDoc());
							m_cashLine.set_Value(pos_Cash_C_Bank_ID, cdItem.getC_Bank_ID());
							m_cashLine.set_Value(pos_Cash_C_Invoice_ID, openItem.getC_Invoice_ID());
							m_cashLine.saveEx();	
						} else {
							//	Method for Deposit
							if(payment == null){
								payment = MBPayment.create(this, con);
								payment.set_Value("TenderType", cdItem.getTenderType());
								payment.set_Value("C_BPartner_ID", m_C_BPartner_ID);
								payment.set_Value("C_Cash_ID", m_C_Cash_ID);
								if(cdItem.getTenderType().equals("A")){	//	ACH
									payment.set_Value("RoutingNo", cdItem.getRoutingNo());
								    payment.set_Value("AccountNo", cdItem.getAccountNo());
								} else if(cdItem.getTenderType().equals("C")){	//	Credit Card
									payment.set_Value("CreditCardExpMM", cdItem.getCreditCardExpMM());
								    payment.set_Value("CreditCardExpYY", cdItem.getCreditCardExpYY());
								    payment.set_Value("CreditCardNumber", cdItem.getCreditCardNumber());
								    payment.set_Value("CreditCardType", cdItem.getCreditCardType());
								    payment.set_Value("CreditCardVV", cdItem.getCreditCardVV());
								    payment.set_Value("A_Name", cdItem.getA_Name());
								}
								//	Si el el pago se cruza completamente de la factura
								if(!isPaymentAllocate){
									payment.set_Value("C_Invoice_ID", openItem.getC_Invoice_ID());
									payment.set_Value("PayAmt", amt);
									payment.set_Value("OverUnderAmt", overUnderAmt.add(diffiOpenAmtSetAmt));
									payment.set_Value("IsOverUnderPayment", isOverUnderPayment? "Y": "N");
									payment.saveEx();
									payment = null;
								} else {
									payment.saveEx();
								}
							} 
							//	
							if(payment != null) {
								//Log.i("--", "Payment Allocation");
								MBPaymentAllocate payment_allocate = new MBPaymentAllocate(this, con, 0);
								payment_allocate.set_Value("C_Payment_ID", payment.getID());
								payment_allocate.set_Value("C_Invoice_ID", openItem.getC_Invoice_ID());
								payment_allocate.set_Value("InvoiceAmt", invoiceEnteredAmt.add(diffiOpenAmtSetAmt));
								payment_allocate.set_Value("Amount", amt);
								payment_allocate.set_Value("OverUnderAmt", overUnderAmt.add(diffiOpenAmtSetAmt));
								payment_allocate.saveEx();
								payment.updateHeader();
								payment.saveEx();
								//payment
							}
						}
						
						//	
						invoiceEnteredAmt = Env.getNumber(openItem.getAmount());
						//	Verifica si se debe salir del pago
						if(current == max || invoiceEnteredAmt.equals(Env.ZERO)){
							repeat = false;
						}
						//	Set Fine
						if(!fine)
							fine = true;
						
					}while(repeat);
				}
			}
			//	Transaction Ok
			if(fine){
				//	Update Header
				m_cash.updateHeader();
				m_cash.saveEx();
				
				createVisit(con, m_C_Cash_ID);
			}
			
			con.setTransactionSuccessful();
			con.endTransaction();
			con.closeDB(null);
			finish();
		} catch (Exception e){
			if(con != null 
					&& con.isOpen())
				con.closeDB(null);
			Env.setContext(this, "#XX_MB_Visit_ID", 0);
			Env.setContext(this, "#XX_MB_VisitLine_ID", 0);
			Log.e("Exception", "Exception", e);
			Msg.alertMsg(this, getResources().getString(R.string.msg_Error), e.getMessage());
		}
	}
	
	/**
	 * Cierra y crea una nueva visita
	 * @author Yamel Senih 29/10/2012, 01:52:17
	 * @param con
	 * @param m_C_Cash_ID
	 * @throws Exception
	 * @return void
	 */
	private void createVisit(DB con, int m_C_Cash_ID) throws Exception{
		// Visit
		int m_XX_MB_Visit_ID = Env.getContextAsInt(this, "#XX_MB_Visit_ID");
		
		MBVisit m_visit = null;
		
		if(m_XX_MB_Visit_ID != 0) {
			m_visit = new MBVisit(this, con, m_XX_MB_Visit_ID);
		}
		else{
			//	Visit
			m_visit = MBVisit.createFromPlanningVisit(this, con, 
					Env.getContextAsInt(this, "#XX_MB_PlanningVisit_ID"), 
					Env.getContext(this, "#Date"), 
					Env.getCurrentDateFormat("yyyy-MM-dd hh:mm:ss"), 
					Env.getContext(this, "#OffCourse"), 
					false);
			
		}
		//	Visit Line
		MBVisitLine m_visitLine = MBVisitLine.createFrom(this, con, 
				m_visit.getID(), 
				"ARR", 0, 0, m_C_Cash_ID, 0);
		
		m_visitLine.set_Value("C_Cash_ID", m_C_Cash_ID);
		m_visitLine.saveEx();
		//	
		Env.setContext(this, "#XX_MB_Visit_ID", m_visit.getID());
		Env.setContext(this, "#XX_MB_VisitLine_ID", m_visitLine.getID());
	}
	
	/**
	 * Procesa la cobranza
	 * @author Yamel Senih 29/10/2012, 01:58:59
	 * @param ctx
	 * @return void
	 */
	private void processCollect(final Context ctx){
		AdapterCollectDocumentItem adapterCDI = (AdapterCollectDocumentItem) listCollectDocuments.getAdapter();
		if(!adapterCDI.validData())
			return;
			
		//	Verifica si la visita actual es la que esta abierta
		if(Env.getContextAsInt(this, "#XX_MB_Visit_ID") == 0){
			final int m_XX_MB_Visit_ID = MBVisit.findOpenVisit(this, null);
			//	Verifica si ya hay una visita abierta
			if(m_XX_MB_Visit_ID != 0){
				final MBVisit visit = new MBVisit(getApplicationContext(), m_XX_MB_Visit_ID);
				//	Get Date from Visit
				if(!Env.isAutomaticVisitClosing(this)){
					String dateS = (String)visit.get_Value("DateVisit");
					
					Builder ask = Msg.confirmMsg(this, Env.getDateFormatString(dateS, "yyyy-MM-dd hh:mm:ss", "dd/MM/yyyy hh:mm:ss") 
							+ "\n" + visit.get_Value("NameBPartner") + "\""
							+ "\n" + getResources().getString(R.string.msg_AskCloseVisit));
			    	
					String msg_Acept = this.getResources().getString(R.string.msg_Yes);
			    	ask.setPositiveButton(msg_Acept, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							
							dialog.cancel();
							/**
							 * Cierra la Visita anterior
							 */
							try {	
								visit.closeVisit(null);
								createLines();
							} catch(Exception e) {
								Msg.alertMsg(ctx, 
										ctx.getString(R.string.msg_Error), e.getMessage());
								Log.e("Error", "Exception", e);
							}
						}
					});
			    	ask.show();
				} else {
					/**
					 * Cierra la Visita anterior
					 */
					try {	
						visit.closeVisit(null);
						createLines();
					} catch(Exception e) {
						Msg.alertMsg(ctx, 
								ctx.getString(R.string.msg_Error), e.getMessage());
						Log.e("Error", "Exception", e);
					}
				}
				
			} else
				createLines();    	
		} else
			createLines();
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if ((keyCode == KeyEvent.KEYCODE_BACK)) {
    		cancelAction();
    		return true;
    	}
    	return super.onKeyDown(keyCode, event);
    }
	
	/**
	 * Difference Update
	 * @author Yamel Senih 01/12/2012, 14:48:12
	 * @return void
	 */
	public void updateDiff(){
		BigDecimal openAmt = Env.getNumber((String)tv_OpenAmt.getText());
		BigDecimal amt = Env.getNumber((String)tv_Amt.getText());
		tv_Diff.setText(openAmt.subtract(amt).toString());
	}
	
	
}
