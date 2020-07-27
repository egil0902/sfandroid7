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
import java.util.LinkedList;

import org.appd.base.DB;
import org.appd.base.Env;
import org.appd.base.R;
import org.appd.model.MBCash;
import org.appd.model.MBCashLine;
import org.appd.model.MBPayment;
import org.appd.model.MP;
import org.appd.util.AdapterCashLine;
import org.appd.util.DisplayCashLineItem;
import org.appd.util.DisplayMenuItem;
import org.appd.util.Msg;
import org.appd.view.custom.Cust_ButtonDocAction;
import org.appd.view.custom.Cust_DateBox;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Yamel Senih
 * Modelo
 *
 */
public class MV_CustomerCollect extends MVActivity {
	
	private Cust_DateBox 					cd_StatementDate;
    private EditText 						et_GrandTotal;
    private BigDecimal 						grandTotal;
    private ListView 						cashLineList;
	private Button 							butt_AddCollect;
	private TextView 						tv_Amt;
	private LinkedList<DisplayCashLineItem> data;
	private MBCashLine 						m_cashLine = null;
	private MBCash 							m_cash = null;
	private String 							cash = "S";
	private int								m_C_BPartner_Location_ID = 0;
	private int								m_C_Cash_ID = 0;
	
	private final String 					TYPE_STANDARD = "S";
	private final String 					TYPE_CASH = "C";
	private final String 					TYPE_CASH_PAYMENT = "P";
	private final String 					TYPE_CASH_CUST_FILTER = "F";
    
	//private Context ctx;
	private DisplayMenuItem param;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        super.setContentView(R.layout.lv_collect_invoices);
        
        Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			param = (DisplayMenuItem)bundle.getParcelable("Param");
			cash = bundle.getString("Type");
			if(cash == null)
				cash = TYPE_STANDARD;
			else if(cash.equals(TYPE_CASH_CUST_FILTER)){
				m_C_BPartner_Location_ID = bundle.getInt("C_BPartner_Location_ID");
				m_C_Cash_ID = bundle.getInt("C_Cash_ID");
			}
		} 
		if(param == null)
			throw new IllegalArgumentException ("No Param");
		
		setTitle(param.getName());
		
		//	Instance of Fields
		cd_StatementDate = (Cust_DateBox) findViewById(R.id.cd_StatementDate);
		et_GrandTotal = (EditText) findViewById(R.id.et_GrandTotal);
		tv_Amt = (TextView) findViewById(R.id.tv_Amt);
		
		butt_AddCollect = (Button)findViewById(R.id.butt_AddCollect);
		butt_AddCollect.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				addCollect();
			}
		});
		
    	cashLineList = (ListView)findViewById(R.id.ls_C_CashLine);
    	
    	cashLineList.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> padre, View v, int position,
					long id) {
				//	Open Document
				//openDocument((DisplayVisitItem) padre.getItemAtPosition(position));
				
			}
		});
    	
    	cd_StatementDate.setEnabled(false);
    	et_GrandTotal.setEnabled(false);
    	//	Visible
    	LinearLayout ll_lb_Amt = (LinearLayout)findViewById(R.id.ll_lb_Amt);
    	if(cash.equals(TYPE_CASH)
    			|| cash.equals(TYPE_CASH_PAYMENT)){
    		LinearLayout ll_HeaderCashLine = (LinearLayout)findViewById(R.id.ll_HeaderCashLine);
    		ll_HeaderCashLine.setVisibility(View.GONE);
    		butt_AddCollect.setVisibility(View.GONE);
    		ll_lb_Amt.setVisibility(View.VISIBLE);
    	} else {
    		ll_lb_Amt.setVisibility(View.GONE);
    	}
    	
    	registerForContextMenu(cashLineList);
    	
    	//	Load Records
        loadCurrentRecords();
    	
    }
    
    @Override
    protected void onStart() {
        super.onStart();
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

	@Override
    protected void onResume() {
        super.onResume();
        String DocAction = Env.getContext(this, "#DocAction");
        if(DocAction == null){
        	DocAction = Cust_ButtonDocAction.STATUS_Drafted;
        }
        
        if(DocAction.equals(Cust_ButtonDocAction.STATUS_Completed)
        		|| DocAction.equals(Cust_ButtonDocAction.STATUS_Voided)){
        	butt_AddCollect.setEnabled(cash.equals(TYPE_STANDARD));
        } else {
        	butt_AddCollect.setEnabled(param.isReadWrite());
        }
    }
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		loadCurrentRecords();
	}
	
	/**
	 * Carga los registros de de eventos de la visita
	 * @author Yamel Senih 19/08/2012, 18:05:04
	 * @return
	 * @return boolean
	 */
	private boolean loadCurrentRecords(){
		//	Permission
		butt_AddCollect.setEnabled(param.isReadWrite());
    	
		if(cash.equals(TYPE_STANDARD))
			m_C_BPartner_Location_ID = Env.getContextAsInt(this, "#C_BPartner_Location_ID");
			
		else if(cash.equals(TYPE_CASH)
				|| cash.equals(TYPE_CASH_PAYMENT))
			m_C_Cash_ID = Env.getTabRecord_ID(this, "C_Cash");
		
		DB con = new DB(this);
		con.openDB(DB.READ_ONLY);
		
		String sql = new String("SELECT " +
				"cl.C_Cash_ID, " +
				"cl.DocumentNo, " +
				"cl.StatementDate, " +
				"cl.C_CashLine_ID, " +
				"cl.C_Invoice_ID, " +
				"cl.DocumentNo, " +
				"cl.CashType, " +
				"cl.TenderType, " +
				"cl.InvoiceAmt, " +
				"cl.Amount, " +
				"cl.IsOverUnderPayment, " +
				"cl.OverUnderAmt, " +
				"cl.ReferenceNo, " +
				"strftime('%d/%m/%Y', COALESCE(cl.DateDoc, Current_Timestamp)), " + 
				"cl.Name, " +
				"cl.C_Payment_ID, " +
				"cl.C_PaymentAllocate_ID, " +
				"cl.RoutingNo, " +
				"cl.AccountNo, " +
				"cl.CreditCardType, " +
				"cl.CreditCardNumber, " +
				"cl.CreditCardExpMM, " +
				"cl.CreditCardExpYY, " +
				"cl.CreditCardVV, " +
				"cl.A_Name, " +
				"cl.DocAction " + 
				"FROM XX_MB_RV_CollectInvoice cl ");
				if(cash.equals(TYPE_STANDARD))
					sql += "WHERE cl.C_BPartner_Location_ID = " + m_C_BPartner_Location_ID + " " +
							"AND cl.DocAction IN('DR', 'CO') ";// AND DATE(cl.StatementDate) = DATE('" + Env.getContext(getCtx(), "#DateP") + "') ";
				if(cash.equals(TYPE_CASH_CUST_FILTER))
					sql += "WHERE cl.C_BPartner_Location_ID = " + m_C_BPartner_Location_ID + " " +
							"AND cl.C_Cash_ID = " + m_C_Cash_ID + " AND cl.DocAction IN('DR', 'CO') ";
				else if(cash.equals(TYPE_CASH))
					sql += "WHERE cl.C_Cash_ID = " + m_C_Cash_ID + " AND cl.TenderType IN('X', 'K') ";
				else if(cash.equals(TYPE_CASH_PAYMENT))
					sql += "WHERE cl.C_Cash_ID = " + m_C_Cash_ID + " AND cl.TenderType NOT IN('X', 'K') ";
				sql += "ORDER BY cl.DocumentNo";
		Cursor rs = con.querySQL(sql, null);
		data = new LinkedList<DisplayCashLineItem>();
		//Log.i("SQL", " " + sql);
		grandTotal = Env.ZERO;
		
		if(rs.moveToFirst()){
			//	Load Cash
			if(m_cash == null)
				m_cash = new MBCash(this, rs.getInt(0));
			
			Env.setContext(this, "#DocAction", rs.getInt(25));
			
			//cd_StatementDate.setDate(Env.getContext(this, "#Date"));
			
			do {
				
				String amount = rs.getString(9);
				
				BigDecimal tmpAmt = new BigDecimal(amount != null? amount: "0");
				
				data.add(new DisplayCashLineItem(
						rs.getInt(3),		//	C_CashLine_ID
						rs.getInt(4), 		//	C_Invoice_ID
						rs.getString(5), 	//	DocumentNo
						rs.getString(6), 	//	CashType
						rs.getString(7), 	//	TenderType
						rs.getString(8), 	//	OpenAmt
						amount, 			//	Amount
						rs.getString(10), 	//	IsOverUnderPayment
						rs.getString(11), 	//	OverUnderAmt
						rs.getString(12), 	//	ReferenceNo
						rs.getString(13), 	//	DateDoc
						rs.getString(14),	//	Bank
						rs.getInt(15), 		//	C_Payment_ID
						rs.getInt(16), 		//	C_PaymentAllocate_ID
						rs.getString(17),	//	RoutingNo
						rs.getString(18),	//	AccountNo
						rs.getString(19),	//	CreditCardType
						rs.getString(20),	//	CreditCardNumber
						rs.getString(21),	//	CreditCardExpMM
						rs.getString(22),	//	CreditCardExpYY
						rs.getString(23),	//	CreditCardVV
						rs.getString(24)));	//	A_Name
				grandTotal = grandTotal.add(tmpAmt);
				
			} while(rs.moveToNext());
		}
		con.closeDB(rs);
		AdapterCashLine mi_adapter = new AdapterCashLine(this, R.layout.il_cash_line, data);
		cashLineList.setAdapter(mi_adapter);
		et_GrandTotal.setText(grandTotal.toString());
		tv_Amt.setText(grandTotal.toString());
    	return true;    	
	}
	
	/**
	 * Muestra una actividad en forma de dialogo donde 
	 * se colocan los datos del evento
	 * @author Yamel Senih 20/08/2012, 01:30:40
	 * @return void
	 */
	private void addCollect(){
		//	Intent
		Intent intent = new Intent(this, MV_AddCollect.class);
		//	Start
		startActivityForResult(intent, 0);
		/*Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		intent.putExtra("SCAN_MODE","PRODUCT_MODE");
		startActivityForResult(intent, 1);*/
		
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.m_standar_line, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		//	Get Position
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		final DisplayCashLineItem dcl = (DisplayCashLineItem) cashLineList.getAdapter().getItem(info.position);
	    switch (item.getItemId()) {
	        case R.id.opt_Delete:
	        	String DocAction = Env.getContext(this, "#DocAction");
	            if(DocAction == null){
	            	DocAction = Cust_ButtonDocAction.STATUS_Drafted;
	            }
	        	if(param.isReadWrite()){
	        		if(DocAction.equals(Cust_ButtonDocAction.STATUS_Completed)){
		            	Msg.alertMsg(this, getResources().getString(R.string.msg_ProcessError), 
		            			getResources().getString(R.string.msg_STATUS_Completed));
		            } else if(DocAction.equals(Cust_ButtonDocAction.STATUS_Voided)) {
		            	Msg.alertMsg(this, getResources().getString(R.string.msg_ProcessError), 
		            			getResources().getString(R.string.msg_STATUS_Voided));
		            } else {
		            	Builder ask = Msg.confirmMsg(this, getResources().getString(R.string.msg_AskDelete) + 
			        			"\n\"" + dcl.getDocInvoice() + "\"");
			        	String msg_Acept = this.getResources().getString(R.string.msg_Yes);
			        	ask.setPositiveButton(msg_Acept, new DialogInterface.OnClickListener() {
			    			public void onClick(DialogInterface dialog, int which) {
			    				if(dcl.getRecord_ID() != 0){
			    					m_cashLine = new MBCashLine(cashLineList.getContext(), dcl.getRecord_ID());
			    					if(m_cashLine.get_ValueAsInt("Deposit_ID") == 0){
			    						m_cashLine.delete();
			    						m_cash.updateHeader();
			    						m_cash.save();
			    					} else {
			    						Msg.alertMsg(cashLineList.getContext(), 
			    								cashLineList.getContext().getResources().getString(R.string.msg_ProcessError), 
			    		            			getResources().getString(R.string.msg_ExistsDeposit));
			    					}
			    				} else if(dcl.getC_PaymentAllocate_ID() != 0
			    						|| dcl.getC_Payment_ID() != 0){
			    					MBPayment py = new MBPayment(cashLineList.getContext(), dcl.getC_Payment_ID());
			    					py.delete();
			    				} else if(dcl.getC_Payment_ID() != 0){
			    					MBPayment py = new MBPayment(cashLineList.getContext(), dcl.getC_Payment_ID());
			    					py.delete();
			    				}
			    	        	loadCurrentRecords();
			    			}
			    		});
			        	ask.show();	
		            }	
	        	} else {
	        		Msg.alertMsg(this, getResources().getString(R.string.msg_ProcessError), 
	            			getResources().getString(R.string.msg_ReadOnly));
	        	}
	            return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}

	/* (non-Javadoc)
	 * @see org.appd.view.MVActivity#getActivityName()
	 */
	@Override
	protected String getActivityName() {
		return "C_CashLine";
	}

	/* (non-Javadoc)
	 * @see org.appd.view.MVActivity#getCustomMP()
	 */
	@Override
	protected MP getCustomMP() {
		return new MBCashLine(getCtx(), 0);
	}

	/* (non-Javadoc)
	 * @see org.appd.view.MVActivity#getCustomMP(int)
	 */
	@Override
	protected MP getCustomMP(int id) {
		return new MBCashLine(getCtx(), id);
	}

	/* (non-Javadoc)
	 * @see org.appd.view.MVActivity#getCustomMP(android.database.Cursor)
	 */
	@Override
	protected MP getCustomMP(Cursor rs) {
		return new MBCashLine(getCtx(), rs);
	}
	
}
