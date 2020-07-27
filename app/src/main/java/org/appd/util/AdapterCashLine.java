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
import java.util.LinkedList;

import org.appd.base.Env;
import org.appd.base.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * @author Yamel Senih
 *
 */
public class AdapterCashLine extends ArrayAdapter<DisplayCashLineItem>{

	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 18/10/2012, 09:40:31
	 * @param ctx
	 * @param id_View
	 * @param data
	 */
	public AdapterCashLine(Activity ctx, int id_View, LinkedList<DisplayCashLineItem> data) {
		super(ctx, id_View, data);
		this.cxt = ctx;
		this.data = data;
		this.id_View = id_View;
		type = "N";
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 10/11/2012, 23:50:02
	 * @param ctx
	 * @param id_View
	 * @param data
	 * @param type
	 * @param summary
	 */
	public AdapterCashLine(Activity ctx, int id_View, LinkedList<DisplayCashLineItem> data, String type, TextView summary) {
		super(ctx, id_View, data);
		this.cxt = ctx;
		this.data = data;
		this.id_View = id_View;
		this.type = type;
		this.summary = summary;
	}
	
	private Activity cxt;
	private LinkedList<DisplayCashLineItem> data;
	private int id_View;
	private String type;
	private TextView summary;
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		if(item == null){
			LayoutInflater inflater = cxt.getLayoutInflater();
			item = inflater.inflate(id_View, null);
		}
		
		final DisplayCashLineItem mi = data.get(position);
		//Log.i("DisplayCashLineItem", mi.toString());
		ImageView img_Record = (ImageView)item.findViewById(R.id.img_Record);
		
		ImageView img_Separator = (ImageView)item.findViewById(R.id.img_Separator);
		//	Set Columns
		
		final CheckBoxHolder chh_Selection = new CheckBoxHolder();
		chh_Selection.setCheckBox((CheckBox)item.findViewById(R.id.ch_Selection));
		
		chh_Selection.getCheckBox().setOnCheckedChangeListener(new OnCheckedChangeListener() { 
		    @Override 
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 
		    	mi.setSelected(buttonView.isChecked());
		    	//	
				calcalateSummary();
		    } 
		});
		
		TextView tv_DocInvoice = (TextView)item.findViewById(R.id.tv_DocInvoice);
		TextView tv_GrandTotal = (TextView)item.findViewById(R.id.tv_GrandTotal);
		TextView tv_Amt = (TextView)item.findViewById(R.id.tv_Amt);
		TextView tv_OpenAmt = (TextView)item.findViewById(R.id.tv_OpenAmt);
		TextView tv_ReferenceNo = (TextView)item.findViewById(R.id.tv_ReferenceNo);
		TextView tv_DateDoc = (TextView)item.findViewById(R.id.tv_DateDoc);
		TextView tv_Bank = (TextView)item.findViewById(R.id.tv_Bank);
		TextView tv_Description = (TextView)item.findViewById(R.id.tv_Description);
		TextView tv_RoutingNo = (TextView)item.findViewById(R.id.tv_RoutingNo);
		TextView tv_AccountNo = (TextView)item.findViewById(R.id.tv_AccountNo);
		TextView tv_CreditCardType = (TextView)item.findViewById(R.id.tv_CreditCardType);
		TextView tv_CreditCardNumber = (TextView)item.findViewById(R.id.tv_CreditCardNumber);
		TextView tv_CreditCardExpMM = (TextView)item.findViewById(R.id.tv_CreditCardExpMM);
		TextView tv_CreditCardExpYY = (TextView)item.findViewById(R.id.tv_CreditCardExpYY);
		TextView tv_CreditCardVV = (TextView)item.findViewById(R.id.tv_CreditCardVV);
		//
		LinearLayout ll_lb_Check = (LinearLayout)item.findViewById(R.id.ll_lb_Check);
		LinearLayout ll_lb_ACH = (LinearLayout)item.findViewById(R.id.ll_lb_ACH);
		LinearLayout ll_lb_CreditCard = (LinearLayout)item.findViewById(R.id.ll_lb_CreditCard);
		LinearLayout ll_lb_Description = (LinearLayout)item.findViewById(R.id.ll_lb_Description);
		
		if(type.equals("D")){
			chh_Selection.getCheckBox().setVisibility(View.VISIBLE);
		} else {
			chh_Selection.getCheckBox().setVisibility(View.GONE);
		}
		
		if(mi.getTenderType() != null 
				&& mi.getTenderType().length() != 0){
			if(mi.getTenderType().equals("X")){
				img_Record.setImageResource(R.drawable.cash_h);	
				img_Separator.setVisibility(View.GONE);
				ll_lb_Check.setVisibility(View.GONE);
				ll_lb_ACH.setVisibility(View.GONE);
				ll_lb_CreditCard.setVisibility(View.GONE);
			} else if(mi.getTenderType().equals("K")){
				img_Record.setImageResource(R.drawable.check_h);
				ll_lb_Check.setVisibility(View.VISIBLE);
				ll_lb_ACH.setVisibility(View.GONE);
				ll_lb_CreditCard.setVisibility(View.GONE);
				img_Separator.setVisibility(View.VISIBLE);
				tv_ReferenceNo.setText(mi.getReferenceNo());
				tv_DateDoc.setText(mi.getDateDoc());
				tv_Bank.setText(mi.getBank());
				if(mi.getDescription() != null 
						&& mi.getDescription().length() != 0){
					ll_lb_Description.setVisibility(View.GONE);
					tv_Description.setText(mi.getDescription());
				} else {
					ll_lb_Description.setVisibility(View.GONE);
				}
			} else if(mi.getTenderType().equals("A")){
				img_Record.setImageResource(R.drawable.wire_transfer_h);
				ll_lb_ACH.setVisibility(View.VISIBLE);
				ll_lb_Check.setVisibility(View.GONE);
				ll_lb_CreditCard.setVisibility(View.GONE);
				img_Separator.setVisibility(View.VISIBLE);
			} else if(mi.getTenderType().equals("C")){
				img_Record.setImageResource(R.drawable.direct_credit_h);
				ll_lb_CreditCard.setVisibility(View.VISIBLE);
				ll_lb_Check.setVisibility(View.GONE);
				ll_lb_ACH.setVisibility(View.GONE);
				img_Separator.setVisibility(View.VISIBLE);
			} else if(mi.getTenderType().equals("D")){
				img_Record.setImageResource(R.drawable.direct_debit_h);	
				ll_lb_Check.setVisibility(View.GONE);
				ll_lb_ACH.setVisibility(View.GONE);
				ll_lb_CreditCard.setVisibility(View.GONE);
				img_Separator.setVisibility(View.GONE);
			} 
		}
		
		chh_Selection.getCheckBox().setChecked(mi.isSelected());
		tv_DocInvoice.setText(mi.getDocInvoice());
		tv_GrandTotal.setText(mi.getGrandTotal());
		tv_Amt.setText(mi.getAmount());
		tv_OpenAmt.setText(mi.getWriteOffAmt());
		tv_RoutingNo.setText(mi.getRoutingNo());
		tv_AccountNo.setText(mi.getAccountNo());
		tv_CreditCardType.setText(mi.getCreditCardType());
		tv_CreditCardNumber.setText(mi.getCreditCardNumber());
		tv_CreditCardExpMM.setText(mi.getCreditCardExpMM());
		tv_CreditCardExpYY.setText(mi.getCreditCardExpYY());
		tv_CreditCardVV.setText(mi.getCreditCardVV());
		//tv_A_Name.setText(mi.getName());
		
		//	Return
		return item;
	}
	
	/**
	 * Calcula el monto y lo guarda en el textView
	 * @author Yamel Senih 10/11/2012, 23:50:19
	 * @return void
	 */
	private void calcalateSummary(){
		BigDecimal amt = Env.ZERO;
		BigDecimal tmpAmt = Env.ZERO;
		for(DisplayCashLineItem item : data){
			if(item.isSelected()){
				if(item.getAmount() != null
						&& item.getAmount().length() > 0){
					tmpAmt = new BigDecimal(item.getAmount());
					amt = amt.add(tmpAmt);
				}
			}
		}
		//
		if(summary != null){
			summary.setText(amt.toString());
		}
	}
	
	/**
	 * Obtiene los items
	 * @author Yamel Senih 11/11/2012, 01:06:42
	 * @return
	 * @return LinkedList<DisplayCashLineItem>
	 */
	public LinkedList<DisplayCashLineItem> getItems(){
		return data;
	}
	
}
