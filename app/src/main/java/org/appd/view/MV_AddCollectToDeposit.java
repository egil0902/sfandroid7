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
import org.appd.interfaces.I_CancelOk;
import org.appd.util.AdapterCashLine;
import org.appd.util.DisplayCashLineItem;
import org.appd.util.Msg;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Yamel Senih
 * Modelo
 *
 */
public class MV_AddCollectToDeposit extends Activity implements I_CancelOk {
	
    private TextView tv_Amt;
    private BigDecimal amt;
    private ListView cashLineList;
	private LinkedList<DisplayCashLineItem> data;
    private CheckBox ch_SelectAll;
    private  ImageButton 	butt_Ok;
    private ImageButton 	butt_Cancel;
    private Intent intentResult = null;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        super.setContentView(R.layout.lv_add_collect_deposit);
        
		//	Instance of Fields
		tv_Amt = (TextView) findViewById(R.id.tv_Amt);
		ch_SelectAll = (CheckBox) findViewById(R.id.ch_SelectAll);
		ch_SelectAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				selectedAll((AdapterCashLine) cashLineList.getAdapter(), isChecked);
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
    	
    	tv_Amt.setEnabled(false);
    	
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
    }
	
	/**
	 * Carga los registros de de eventos de la visita
	 * @author Yamel Senih 19/08/2012, 18:05:04
	 * @return
	 * @return boolean
	 */
	private boolean loadCurrentRecords(){
		//	Permission
		DB con = new DB(this);
		con.openDB(DB.READ_ONLY);
		
		String sql = new String("SELECT " +
				"cl.C_CashLine_ID, " +
				"cl.Deposit_ID, " + 
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
				"WHERE (cl.Deposit_ID = " + Env.getTabRecord_ID(this, "C_Payment") + " OR cl.Deposit_ID = 0) " +
				"AND cl.TenderType IN('X', 'K') " +
				"AND cl.DocAction IN('CO', 'CL') " + 
				"ORDER BY cl.DocumentNo");
		Cursor rs = con.querySQL(sql, null);
		data = new LinkedList<DisplayCashLineItem>();
		//Log.i("SQL", " " + sql);
		amt = Env.ZERO;
		
		if(rs.moveToFirst()){
			do {
				
				String amount = rs.getString(7);
				
				BigDecimal tmpAmt = Env.getNumber(amount);
				boolean selected = rs.getInt(1) > 0;
				data.add(new DisplayCashLineItem(
						rs.getInt(0),		//	C_CashLine_ID
						selected,			//	Selected
						rs.getInt(2), 		//	C_Invoice_ID
						rs.getString(3), 	//	DocumentNo
						rs.getString(4), 	//	CashType
						rs.getString(5), 	//	TenderType
						rs.getString(6), 	//	OpenAmt
						amount, 			//	Amount
						rs.getString(8), 	//	IsOverUnderPayment
						rs.getString(9), 	//	WriteOffAmt
						rs.getString(10), 	//	ReferenceNo
						rs.getString(11), 	//	DateDoc
						rs.getString(12),	//	Bank
						rs.getInt(13), 		//	C_Payment_ID
						rs.getInt(14), 		//	C_PaymentAllocate_ID
						rs.getString(15),	//	RoutingNo
						rs.getString(16),	//	AccountNo
						rs.getString(17),	//	CreditCardType
						rs.getString(18),	//	CreditCardNumber
						rs.getString(19),	//	CreditCardExpMM
						rs.getString(20),	//	CreditCardExpYY
						rs.getString(21),	//	CreditCardVV
						rs.getString(22)));	//	A_Name
				if(selected)
					amt = amt.add(tmpAmt);
				
			} while(rs.moveToNext());
		}
		AdapterCashLine mi_adapter = new AdapterCashLine(this, R.layout.il_cash_line, data, "D", tv_Amt);
		cashLineList.setAdapter(mi_adapter);
		con.closeDB(rs);
		//tv_Amt.setText(amt.toString());
    	return true;    	
	}

	/* (non-Javadoc)
	 * @see org.appd.interfaces.I_CancelOk#aceptAction()
	 */
	@Override
	public boolean aceptAction() {
		AdapterCashLine data = (AdapterCashLine) cashLineList.getAdapter();
		StringBuffer whereIn = new StringBuffer("(");
		boolean afterFirst = false;
		for(DisplayCashLineItem item : data.getItems()){
			if(item.isSelected()){
				if(afterFirst)
					whereIn.append(",");
				else
					afterFirst = true;
				//	Add ID
				whereIn.append(item.getRecord_ID());					
			}
		}
		whereIn.append(")");
		//	
		intentResult = getIntent();
		if(afterFirst)
			intentResult.putExtra("void", true);
		intentResult.putExtra("Where", whereIn.toString());
		setResult(RESULT_OK, intentResult);
		finish();
		return true;
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
		return false;
	}

	/* (non-Javadoc)
	 * @see org.appd.interfaces.I_CancelOk#getParam()
	 */
	@Override
	public Intent getParam() {
		return intentResult;
	}
	
	/**
	 * Selecciona o no los items del ListView
	 * @author Yamel Senih 11/11/2012, 01:09:49
	 * @param mi_adapter
	 * @param selected
	 * @return void
	 */
	private void selectedAll(AdapterCashLine mi_adapter, boolean selected){
		amt = Env.ZERO;
		for(DisplayCashLineItem item : mi_adapter.getItems()){
			item.setSelected(selected);
			amt = amt.add(Env.getNumber(item.getAmount()));
		}
		mi_adapter.notifyDataSetChanged();
		tv_Amt.setText(amt.toString());
	}
	
}
