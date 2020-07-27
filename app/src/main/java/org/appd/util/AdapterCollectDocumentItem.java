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
 * Copyright (C) 2012-2013 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.appd.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;

import org.appd.base.DB;
import org.appd.base.Env;
import org.appd.base.R;
import org.appd.view.MV_AddCollect;
import org.appd.view.custom.Cust_DateBox;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * @author Yamel Senih
 *
 */
public class AdapterCollectDocumentItem extends BaseAdapter {

	private ArrayList<DisplayCollectDocumentItem> items = new ArrayList<DisplayCollectDocumentItem>();
	private LayoutInflater inflater;
	private Context ctx;
	private TextView summary;
	private LinkedList<DisplaySpinner> banks;
	private LinearLayoutHolder llh_CashDirectDebit;
	private LinearLayoutHolder llh_Check;
	private LinearLayoutHolder llh_ACH;
	private LinearLayoutHolder llh_CreditCard;
	private TextView tv_ReferenceNo;
	
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih 22/10/2012, 15:36:08
	 * @param ctx
	 */
	public AdapterCollectDocumentItem(Context ctx){
		inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		items = new ArrayList<DisplayCollectDocumentItem>();
		this.ctx = ctx;
		banks = loadBank();
		llh_CashDirectDebit = new LinearLayoutHolder();
		llh_Check = new LinearLayoutHolder();
		llh_ACH = new LinearLayoutHolder();
		llh_CreditCard = new LinearLayoutHolder();
		notifyDataSetChanged();
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih 22/10/2012, 15:36:03
	 * @param ctx
	 * @param summary
	 */
	public AdapterCollectDocumentItem(Context ctx, TextView summary){
		inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		items = new ArrayList<DisplayCollectDocumentItem>();
		this.ctx = ctx;
		this.summary = summary;
		banks = loadBank();
		llh_CashDirectDebit = new LinearLayoutHolder();
		llh_Check = new LinearLayoutHolder();
		llh_ACH = new LinearLayoutHolder();
		llh_CreditCard = new LinearLayoutHolder();
		notifyDataSetChanged();
	}
	
	/**
	 * Add items
	 * @author Yamel Senih 19/10/2012, 16:43:16
	 * @param item
	 * @return void
	 */
	public void addItem(DisplayCollectDocumentItem item) {
		items.add(item);
		notifyDataSetChanged();
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return items.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	/**
	 * 
	 * @author Yamel Senih 19/10/2012, 16:43:07
	 * @return
	 * @return ArrayList<DisplayCollectDocumentItem>
	 */
	public ArrayList<DisplayCollectDocumentItem> getItems(){
		return items;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.il_collect_document, null);
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//loadLastSO(items.get(position).getM_Product_ID(), v);
			}
		});
		
		//	Get Current Item
		final DisplayCollectDocumentItem item = items.get(position);
		
		//	Tender Type
		final SpinnerHolder sph_CashType = new SpinnerHolder();
		sph_CashType.setSpinner((Spinner)convertView.findViewById(R.id.sp_TenderType));
		//	Load
		populateSpinnerTenderType(sph_CashType.getSpinner(), item.isPivot());
		
		//	Collect Amount
		final EditTextHolder eth_Amount = new EditTextHolder();
		eth_Amount.setEditText((EditText)convertView.findViewById(R.id.et_Amount));
		
		//	Add Collect
		final ButtonHolder bth_Add = new ButtonHolder();
		bth_Add.setButton((Button)convertView.findViewById(R.id.butt_Add));
		
		tv_ReferenceNo = (TextView)convertView.findViewById(R.id.tv_ReferenceNo);
		
		//	Reference No/Check No
		final EditTextHolder eth_ReferenceNo = new EditTextHolder();
		eth_ReferenceNo.setEditText((EditText)convertView.findViewById(R.id.et_ReferenceNo));
		
		//	Document Date
		final Cust_DateBoxHolder dbh_DateDoc = new Cust_DateBoxHolder();
		dbh_DateDoc.setCust_DateBox((Cust_DateBox)convertView.findViewById(R.id.db_DateDoc));
				
		//	Bank
		final SpinnerHolder sph_C_Bank_ID = new SpinnerHolder();
		sph_C_Bank_ID.setSpinner((Spinner)convertView.findViewById(R.id.sp_C_Bank_ID));
		//	
		populateSpinnerBank(sph_C_Bank_ID.getSpinner());
		
		//	Routing No
		final EditTextHolder eth_RoutingNo = new EditTextHolder();
		eth_RoutingNo.setEditText((EditText)convertView.findViewById(R.id.et_RoutingNo));
		
		//	Account No
		final EditTextHolder eth_AccountNo = new EditTextHolder();
		eth_AccountNo.setEditText((EditText)convertView.findViewById(R.id.et_AccountNo));
		
		//	Credit Card Type
		final SpinnerHolder sph_CreditCardType = new SpinnerHolder();
		sph_CreditCardType.setSpinner((Spinner)convertView.findViewById(R.id.sp_CreditCardType));
		//	Load
		populateSpinnerCreditCarType(sph_CreditCardType.getSpinner());
		
		//	CreditCardNumber
		final EditTextHolder eth_CreditCardNumber = new EditTextHolder();
		eth_CreditCardNumber.setEditText((EditText)convertView.findViewById(R.id.et_CreditCardNumber));
		
		//	Credit Card Exp Month
		final EditTextHolder eth_CreditCardExpMM = new EditTextHolder();
		eth_CreditCardExpMM.setEditText((EditText)convertView.findViewById(R.id.et_CreditCardExpMM));
		
		//	Credit Card Exp Year
		final EditTextHolder eth_CreditCardExpYY = new EditTextHolder();
		eth_CreditCardExpYY.setEditText((EditText)convertView.findViewById(R.id.et_CreditCardExpYY));
		
		//	Credit Card Verification Code
		final EditTextHolder eth_CreditCardVV = new EditTextHolder();
		eth_CreditCardVV.setEditText((EditText)convertView.findViewById(R.id.et_CreditCardVV));

		//	Name
		final EditTextHolder eth_A_Name = new EditTextHolder();
		eth_A_Name.setEditText((EditText)convertView.findViewById(R.id.et_A_Name));

		
		if(item.isPivot()){
			bth_Add.getButton().setCompoundDrawablesWithIntrinsicBounds(ctx.getResources().getDrawable(R.drawable.listadd_l),null, null, null);
		} else {
			bth_Add.getButton().setCompoundDrawablesWithIntrinsicBounds(ctx.getResources().getDrawable(R.drawable.remove_l),null, null, null);
		}
		
		llh_CashDirectDebit.setLinearLayout((LinearLayout)convertView.findViewById(R.id.ll_CashDirectDebit));
		
		llh_Check.setLinearLayout((LinearLayout)convertView.findViewById(R.id.ll_Check));
		
		llh_ACH.setLinearLayout((LinearLayout)convertView.findViewById(R.id.ll_ACH));
		
		llh_CreditCard.setLinearLayout((LinearLayout)convertView.findViewById(R.id.ll_CreditCard));
		
		//	Visible Reference
		loadReferenceView(((DisplaySpinner)sph_CashType.getSelectedItem()).getValue());
		
		eth_Amount.getEditText().addTextChangedListener(new TextWatcher() {
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				item.setAmount(eth_Amount.getString());
				calcalateSummary();
			}
		});
		
		//	ReferenceNo
		eth_ReferenceNo.getEditText().addTextChangedListener(new TextWatcher() {
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				item.setReferenceNo(eth_ReferenceNo.getString().trim());
			}
		});
		
		//	RoutingNo
		eth_RoutingNo.getEditText().addTextChangedListener(new TextWatcher() {
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				item.setRoutingNo(eth_RoutingNo.getString().trim());
			}
		});
		
		//	AccountNo
		eth_AccountNo.getEditText().addTextChangedListener(new TextWatcher() {
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				item.setAccountNo(eth_AccountNo.getString().trim());
			}
		});

		//	CreditCardNumber
		eth_CreditCardNumber.getEditText().addTextChangedListener(new TextWatcher() {
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				item.setCreditCardNumber(eth_CreditCardNumber.getString().trim());
			}
		});

		//	CreditCardExpMM
		eth_CreditCardExpMM.getEditText().addTextChangedListener(new TextWatcher() {
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				item.setCreditCardExpMM(eth_CreditCardExpMM.getString().trim());
			}
		});

		//	CreditCardExpYY
		eth_CreditCardExpYY.getEditText().addTextChangedListener(new TextWatcher() {
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				item.setCreditCardExpYY(eth_CreditCardExpYY.getString().trim());
			}
		});

		//	CreditCardVV
		eth_CreditCardVV.getEditText().addTextChangedListener(new TextWatcher() {
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				item.setCreditCardVV(eth_CreditCardVV.getString().trim());
			}
		});

		//	A_Name
		eth_A_Name.getEditText().addTextChangedListener(new TextWatcher() {
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				item.setA_Name(eth_A_Name.getString().trim());
			}
		});
		
		
		//	Add Collect Document
		bth_Add.getButton().setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(item.isPivot()){
					addCollectDocument(false);
				} else {
					removeCollectDocument(position);
				}
			}
		});
		
		sph_CashType.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> a, View v,
					int position, long i) {
				DisplaySpinner m_TenderType = ((DisplaySpinner) sph_CashType.getSpinner().getItemAtPosition(position));
				item.setTenderType(m_TenderType.getHiddenValue().toString());
				loadReferenceView(m_TenderType.getHiddenValue().toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> a) {
				
			}
    		
    	});
		
		sph_C_Bank_ID.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> a, View v,
					int position, long i) {
				DisplaySpinner m_Bank = ((DisplaySpinner) sph_C_Bank_ID.getSpinner().getItemAtPosition(position));
				item.setC_Bank_ID(m_Bank.getID());
			}

			@Override
			public void onNothingSelected(AdapterView<?> a) {
				
			}
    		
    	});
		
		//	Credit Card Type
		sph_CreditCardType.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> a, View v,
					int position, long i) {
				DisplaySpinner m_CreditCardType = ((DisplaySpinner) sph_CreditCardType.getSpinner().getItemAtPosition(position));
				item.setCreditCardType(m_CreditCardType.getHiddenValue().toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> a) {
				
			}
    		
    	});

		
		//	Set Date
		dbh_DateDoc.getCust_DateBox().setOnDateSetListener(new OnDateSetListener() {
    		public void onDateSet(DatePicker view, int year, 
    				int monthOfYear, int dayOfMonth) {
    			dbh_DateDoc.getCust_DateBox().onDateSet(view, year, monthOfYear, dayOfMonth);
    			item.setDateDoc(dbh_DateDoc.getCust_DateBox().getDate());
    		}
    	});
		
		//	Set Values
		sph_CashType.setSelectedValue(item.getTenderType());
		sph_CreditCardType.setSelectedValue(item.getCreditCardType());
		eth_Amount.getEditText().setText(item.getAmount());
		eth_ReferenceNo.getEditText().setText(item.getReferenceNo());
		eth_RoutingNo.getEditText().setText(item.getReferenceNo());
		eth_AccountNo.getEditText().setText(item.getAccountNo());
		eth_CreditCardNumber.getEditText().setText(item.getCreditCardNumber());
		eth_CreditCardExpMM.getEditText().setText(item.getCreditCardExpMM());
		eth_CreditCardExpYY.getEditText().setText(item.getCreditCardExpYY());
		eth_CreditCardVV.getEditText().setText(item.getCreditCardVV());
		eth_A_Name.getEditText().setText(item.getA_Name());
		
		if(item.getDateDoc() != null)
			dbh_DateDoc.setDate(item.getDateDoc());
		sph_C_Bank_ID.setSelected(item.getC_Bank_ID());
		loadReferenceView(item.getTenderType());
		
		return convertView;
	}
	
	/**
	 * Agrega una nueva forma de pago
	 * @author Yamel Senih 22/10/2012, 18:22:39
	 * @return void
	 */
	public void addCollectDocument(boolean pivot){
		DisplayCollectDocumentItem cdi = new DisplayCollectDocumentItem(0, null, null);
		cdi.setIsPivot(pivot);
		cdi.setDateDoc(Env.getCurrentDateFormat("yyyy-MM-dd hh:mm:ss"));
		items.add(cdi);
		notifyDataSetChanged();
	}
	
	/**
	 * Elimina el documento seleccionado
	 * @author Yamel Senih 23/10/2012, 02:11:42
	 * @param index
	 * @return void
	 */
	private void removeCollectDocument(int index){
		items.remove(index);
		notifyDataSetChanged();
		calcalateSummary();
	}
	
	/**
	 * Carga los datos del tipo de cobro en el Spinner
	 * @author Yamel Senih 23/10/2012, 00:20:09
	 * @param spinner
	 * @return void
	 */
	private void populateSpinnerTenderType(Spinner spinner, boolean isPivot){
		ArrayAdapter<DisplaySpinner> sp_adapter = 
				new ArrayAdapter<DisplaySpinner>(ctx, android.R.layout.simple_spinner_item);
		//	
		if(isPivot)
			sp_adapter.add(new DisplaySpinner(0, ctx.getString(R.string.tt_X_Cash), "X"));			//	Cash
		sp_adapter.add(new DisplaySpinner(0, ctx.getString(R.string.tt_K_Check), "K"));				//	Check
		sp_adapter.add(new DisplaySpinner(0, ctx.getString(R.string.tt_A_DirectDeposit), "A"));		//	ACH
		sp_adapter.add(new DisplaySpinner(0, ctx.getString(R.string.tt_C_CreditCard), "C"));		//	Credit Card
		sp_adapter.add(new DisplaySpinner(0, ctx.getString(R.string.tt_D_DirectDebit), "D"));		//	Direc Deposit
		//	
		sp_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(sp_adapter);
		
	}
	
	/**
	 * Carga un Spinner con los tipos de tarjeta de credito
	 * @author Yamel Senih 31/10/2012, 10:56:15
	 * @param spinner
	 * @return void
	 */
	private void populateSpinnerCreditCarType(Spinner spinner){
		ArrayAdapter<DisplaySpinner> sp_adapter = 
				new ArrayAdapter<DisplaySpinner>(ctx, android.R.layout.simple_spinner_item);
		sp_adapter.add(new DisplaySpinner(0, ctx.getString(R.string.cct_M_MasterCard), "M"));	//	MasterCard
		sp_adapter.add(new DisplaySpinner(0, ctx.getString(R.string.cct_V_Visa), "V"));			//	Visa
		sp_adapter.add(new DisplaySpinner(0, ctx.getString(R.string.cct_A_Amex), "A"));			//	Amex
		sp_adapter.add(new DisplaySpinner(0, ctx.getString(R.string.cct_C_Atm), "C"));			//	Atm
		sp_adapter.add(new DisplaySpinner(0, ctx.getString(R.string.cct_N_Discover), "N"));		//	Discover
		sp_adapter.add(new DisplaySpinner(0, ctx.getString(R.string.cct_D_Diners), "D"));		//	Diners Club
		sp_adapter.add(new DisplaySpinner(0, ctx.getString(R.string.cct_P_PCard), "P"));		//	Pruchase Card
		//	
		sp_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(sp_adapter);
		
	}
	
	/**
	 * Carga los datos de los bancos en el Spinner
	 * @author Yamel Senih 23/10/2012, 10:47:34
	 * @param spinner
	 * @return void
	 */
	private void populateSpinnerBank(Spinner spinner){
		ArrayAdapter<DisplaySpinner> sp_adapter = 
				new ArrayAdapter<DisplaySpinner>(ctx, android.R.layout.simple_spinner_item, banks);
		//	
		sp_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(sp_adapter);
	}
	
	
	/**
	 * Cambia el cmportamiento del layout dependiendo del tipo de cobro
	 * @author Yamel Senih 23/10/2012, 10:23:52
	 * @param tenderType
	 * @return void
	 */
	private void loadReferenceView(String tenderType){
		if(tenderType.equals("X")
				|| tenderType.equals("D")) { // Cash or Direct Debit	
			llh_Check.getLinearLayout().setVisibility(View.GONE);
			llh_ACH.getLinearLayout().setVisibility(View.GONE);
			llh_CreditCard.getLinearLayout().setVisibility(View.GONE);
		} else if(tenderType.equals("K")) {	//	Check
			llh_Check.getLinearLayout().setVisibility(View.VISIBLE);
			llh_ACH.getLinearLayout().setVisibility(View.GONE);
			llh_CreditCard.getLinearLayout().setVisibility(View.GONE);
			tv_ReferenceNo.setText(ctx.getResources().getString(R.string.CheckNo));
		} else if(tenderType.equals("A")){	//	ACH
			llh_Check.getLinearLayout().setVisibility(View.GONE);
			llh_ACH.getLinearLayout().setVisibility(View.VISIBLE);
			llh_CreditCard.getLinearLayout().setVisibility(View.GONE);
		} else if(tenderType.equals("C")){	//	Credit Card
			llh_Check.getLinearLayout().setVisibility(View.GONE);
			llh_ACH.getLinearLayout().setVisibility(View.GONE);
			llh_CreditCard.getLinearLayout().setVisibility(View.VISIBLE);
		} else {							//	Other
			llh_Check.getLinearLayout().setVisibility(View.GONE);
			llh_ACH.getLinearLayout().setVisibility(View.GONE);
			llh_CreditCard.getLinearLayout().setVisibility(View.GONE);
		}
	}
	
	/**
	 * Calcula el monto para actualizar el valor de Summary
	 * @author Yamel Senih 23/10/2012, 01:08:58
	 * @return void
	 */
	private void calcalateSummary(){
		BigDecimal amt = Env.ZERO;
		BigDecimal tmpAmt = Env.ZERO;
		for(DisplayCollectDocumentItem item : items){
			tmpAmt = Env.getNumber(item.getAmount());
			amt = amt.add(tmpAmt);
		}
		//
		if(summary != null){
			summary.setText(amt.toString());
		}
		MV_AddCollect cl = (MV_AddCollect)ctx;
		cl.updateDiff();
	}
	
	/**
	 * Carga los datos del banco
	 * @author Yamel Senih 23/10/2012, 10:45:03
	 * @return
	 * @return LinkedList<DisplaySpinner>
	 */
	private LinkedList<DisplaySpinner> loadBank(){
		String sql = new String("SELECT bn.C_Bank_ID, bn.Name FROM C_Bank bn");
		LinkedList<DisplaySpinner> data = new LinkedList<DisplaySpinner>();
		DB con = new DB(ctx);
		con.openDB(DB.READ_ONLY);
		
		Cursor rs = con.querySQL(sql, null);
		if(rs.moveToFirst()){
			//	Establecer los totales
			do {
				data.add(new DisplaySpinner(
						rs.getInt(0),		//	C_Bank_ID 
						rs.getString(1)));	//	Name
			} while(rs.moveToNext());
		}
		//
		con.closeDB(rs);
		return data;
	}
	
	/**
	 * Valida que el valor no esté vacio
	 * @author Yamel Senih 12/11/2012, 17:07:11
	 * @param value
	 * @return boolean
	 */
	private boolean validField(String value){
		if(value != null
				&& value.length() > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * Valida los datos de la lista
	 * @author Yamel Senih 12/11/2012, 17:17:56
	 * @return
	 * @return boolean
	 */
	public boolean validData(){
		for(DisplayCollectDocumentItem item : items){
			if(item.getTenderType().equals("K")
					&& !validField(item.getReferenceNo())){
				Msg.alertMustFillField(ctx, R.string.CheckNo, null);
				return false;
			}
			if(item.getTenderType().equals("A")){
				if(!validField(item.getRoutingNo())){
					Msg.alertMustFillField(ctx, R.string.RoutingNo, null);
					return false;
				} else if(!validField(item.getAccountNo())){
					Msg.alertMustFillField(ctx, R.string.AccountNo, null);
					return false;
				}
			}
			if(item.getTenderType().equals("C")){
				if(!validField(item.getCreditCardNumber())){
					Msg.alertMustFillField(ctx, R.string.CreditCardNumber, null);
					return false;
				} else if(!validField(item.getCreditCardExpMM())){
					Msg.alertMustFillField(ctx, R.string.CreditCardExpMM, null);
					return false;
				} else if(!validField(item.getCreditCardExpYY())){
					Msg.alertMustFillField(ctx, R.string.CreditCardExpYY, null);
					return false;
				} else if(!validField(item.getCreditCardVV())){
					Msg.alertMustFillField(ctx, R.string.CreditCardVV, null);
					return false;
				} else if(!validField(item.getA_Name())){
					Msg.alertMustFillField(ctx, R.string.A_Name, null);
					return false;
				}
			}
		}
		return true;
	}
	
}
