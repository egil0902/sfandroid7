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
import org.appd.model.MBCashLine;
import org.appd.model.MBCustomerInventoryLine;
import org.appd.model.MBPayment;
import org.appd.model.MP;
import org.appd.util.AdapterCashLine;
import org.appd.util.DisplayCashLineItem;
import org.appd.util.DisplayMenuItem;
import org.appd.util.Msg;
import org.appd.view.custom.Cust_ButtonDocAction;

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
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Yamel Senih
 *
 */
public class MV_DepositLine extends MVActivity{
	
	private LinkedList<DisplayCashLineItem> data = new LinkedList<DisplayCashLineItem>();
	private ListView cashLineList;
	private Button butt_AddCollect;
	private TextView tv_PayAmt;
    private BigDecimal amt;
    private MBPayment m_Payment = null;
    private boolean updateHeap = false;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	super.setContentView(R.layout.lv_deposit_line);
    	
    	Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			param = (DisplayMenuItem)bundle.getParcelable("Param");
		} 
		if(param == null)
			throw new IllegalArgumentException ("No Param");
		
		tv_PayAmt = (TextView)findViewById(R.id.tv_PayAmt);
		
    	butt_AddCollect = (Button)findViewById(R.id.butt_AddCollect);
    	butt_AddCollect.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				loadCashList();
			}
		});
    	cashLineList = (ListView)findViewById(R.id.ls_DepositLine);
    	
    	registerForContextMenu(cashLineList);
    }
    
    /**
     * Carga las cobranzas realizadas
     * @author Yamel Senih 10/11/2012, 23:16:25
     * @return void
     */
    private void loadCashList(){
    	Intent intent = new Intent(this, MV_AddCollectToDeposit.class);
    	//	
		startActivityForResult(intent, 0);
    }
    
    /**
     * Carga los registros de las lineas de caja que están 
     * relacionadas al depósito
     * @author Yamel Senih 10/11/2012, 12:02:03
     * @return
     * @return boolean
     */
    private boolean loadCurrentRecords(){
		DB con = new DB(this);
		con.openDB(DB.READ_ONLY);
		
		String sql = new String("SELECT " +
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
				"cl.A_Name " + 
				"FROM XX_MB_RV_CollectInvoice cl " +
				"WHERE cl.Deposit_ID = " + Env.getTabRecord_ID(this, "C_Payment") + " " + 
				"ORDER BY cl.DocumentNo");
		Cursor rs = con.querySQL(sql, null);
		data = new LinkedList<DisplayCashLineItem>();
		//Log.i("SQL", " " + sql);
		amt = Env.ZERO;
		
		if(rs.moveToFirst()){
			//	Load Cash
			do {
				
				String amount = rs.getString(6);
				
				BigDecimal tmpAmt = Env.getNumber(amount);
				
				data.add(new DisplayCashLineItem(
						rs.getInt(0),		//	C_CashLine_ID
						rs.getInt(1), 		//	C_Invoice_ID
						rs.getString(2), 	//	DocumentNo
						rs.getString(3), 	//	CashType
						rs.getString(4), 	//	TenderType
						rs.getString(5), 	//	OpenAmt
						amount, 			//	Amount
						rs.getString(7), 	//	IsOverUnderPayment
						rs.getString(8), 	//	WriteOffAmt
						rs.getString(9), 	//	ReferenceNo
						rs.getString(10), 	//	DateDoc
						rs.getString(11),	//	Bank
						rs.getInt(12), 		//	C_Payment_ID
						rs.getInt(13), 		//	C_PaymentAllocate_ID
						rs.getString(14),	//	RoutingNo
						rs.getString(15),	//	AccountNo
						rs.getString(16),	//	CreditCardType
						rs.getString(17),	//	CreditCardNumber
						rs.getString(18),	//	CreditCardExpMM
						rs.getString(19),	//	CreditCardExpYY
						rs.getString(20),	//	CreditCardVV
						rs.getString(21)));	//	A_Name
				amt = amt.add(tmpAmt);
				
			} while(rs.moveToNext());
		}
		AdapterCashLine mi_adapter = new AdapterCashLine(this, R.layout.il_cash_line, data);
		cashLineList.setAdapter(mi_adapter);
		con.closeDB(rs);
		tv_PayAmt.setText(amt.toString());
		
		if(updateHeap){
			m_Payment.set_Value("PayAmt", amt);
			m_Payment.save();	
		}
        
    	return true;    	
	}
    
    @Override
    protected void onStart() {
        super.onStart();
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
        	butt_AddCollect.setEnabled(false);
        } else {
        	butt_AddCollect.setEnabled(param.isReadWrite());
        }
        //	Verfifica si no hay instancia o si es diferente
        int id = Env.getTabRecord_ID(this, "C_Payment");
        if(m_Payment == null 
        		|| m_Payment.getID() != id){
        	m_Payment = new MBPayment(this, id);
		}
        updateHeap = false;
        loadCurrentRecords();
    }
    @Override
    protected void onPause() {
        super.onPause();
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
	 * @see org.appd.view.MVActivity#getCustomMP()
	 */
    @Override
	public MP getCustomMP() {
		return new MBCustomerInventoryLine(getCtx(), 0);
	}

	@Override
	public MP getCustomMP(int id) {
		return new MBCustomerInventoryLine(getCtx(), id);
	}

	@Override
	public MP getCustomMP(Cursor rs) {
		return new MBCustomerInventoryLine(getCtx(), rs);
	}
	
	/* (non-Javadoc)
	 * @see org.appd.view.MVActivity#getActivityName()
	 */
	@Override
	protected String getActivityName() {
		return "C_PaymentLine";
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
	    if (resultCode == RESULT_OK) {
	    	if(data != null){
	    		boolean arrayVoid = data.getBooleanExtra("Void", false);
	    		String where = data.getStringExtra("Where");
	    		//	
	    		DB con = new DB(this);
	    		con.openDB(DB.READ_WRITE);
	    		if(!arrayVoid){
	    			String sqlUpdate = new String("UPDATE C_CashLine SET Deposit_ID = " + Env.getTabRecord_ID(this, "C_Payment") + " " + 
	    					"WHERE C_CashLine_ID IN" + where);
	    			//	
	    			con.executeSQL(sqlUpdate);
	    			sqlUpdate = new String("UPDATE C_CashLine SET Deposit_ID = null " + 
	    					"WHERE Deposit_ID = " + Env.getTabRecord_ID(this, "C_Payment") + " " + 
	    					"AND C_CashLine_ID NOT IN" + where);
	    			//	
	    			con.executeSQL(sqlUpdate);
	    		} else {
	    			String sqlUpdate = new String("UPDATE C_CashLine SET Deposit_ID = null " + 
	    					"WHERE Deposit_ID = " + Env.getTabRecord_ID(this, "C_Payment"));
	    			con.executeSQL(sqlUpdate);
	    		}
	    		//	
	    		con.closeDB(null);
	    		updateHeap = true;
	    		loadCurrentRecords();
	    	}
	    }
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
		final DisplayCashLineItem dci = (DisplayCashLineItem) cashLineList.getAdapter().getItem(info.position);
	    switch (item.getItemId()) {
	        case R.id.opt_Delete:
	        	if(param.isReadWrite()){
	        		String DocAction = Env.getContext(this, "#DocAction");
		            if(DocAction == null){
		            	DocAction = Cust_ButtonDocAction.STATUS_Drafted;
		            }
	        		if(DocAction.equals(Cust_ButtonDocAction.STATUS_Completed)){
		            	Msg.alertMsg(this, getResources().getString(R.string.msg_ProcessError), 
		            			getResources().getString(R.string.msg_STATUS_Completed));
		            } else if(DocAction.equals(Cust_ButtonDocAction.STATUS_Voided)) {
		            	Msg.alertMsg(this, getResources().getString(R.string.msg_ProcessError), 
		            			getResources().getString(R.string.msg_STATUS_Voided));
		            } else {
		            	Builder ask = Msg.confirmMsg(this, getResources().getString(R.string.msg_AskDelete) + 
			        			"\n\"" + dci.getDocInvoice() + "\"");
			        	String msg_Acept = this.getResources().getString(R.string.msg_Yes);
			        	ask.setPositiveButton(msg_Acept, new DialogInterface.OnClickListener() {
			    			public void onClick(DialogInterface dialog, int which) {
			    				MBCashLine m_CashLine = new MBCashLine(getCtx(), dci.getRecord_ID());
			    	        	m_CashLine.set_Value("Deposit_ID", null);
			    	        	m_CashLine.save();
			    	        	updateHeap = true;
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
}
