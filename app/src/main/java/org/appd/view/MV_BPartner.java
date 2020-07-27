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

import org.appd.base.R;
import org.appd.model.MP;
import org.appd.util.DisplayMenuItem;
import org.appd.view.custom.Cust_DateBox;
import org.appd.view.custom.Cust_Spinner;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author Yamel Senih
 * Modelo
 *
 */
public class MV_BPartner extends MVActivity {
	
	private DisplayMenuItem param;
	/** Called when the activity is first created. */
	private Cust_Spinner sp_C_PaymentTerm_ID;
	private Cust_Spinner sp_M_PriceList_ID;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        super.setContentView(R.layout.v_bpartner);
        
        Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			param = (DisplayMenuItem)bundle.getParcelable("Param");
		}
		if(param == null)
			throw new IllegalArgumentException ("No Param");
		
        addView((TextView) findViewById(R.id.tV_Value), (EditText) findViewById(R.id.eT_Value), "Value", false);
        addView((TextView) findViewById(R.id.tV_Name), (EditText) findViewById(R.id.eT_Name), "Name", false);
        addView((TextView) findViewById(R.id.tV_Name2), (EditText) findViewById(R.id.eT_Name2) , "Name2", false);
        
        sp_M_PriceList_ID = (Cust_Spinner) findViewById(R.id.sp_M_PriceList_ID);
        sp_M_PriceList_ID.setTableName("M_PriceList");
        sp_M_PriceList_ID.setIdentifierName("Name");
        
        sp_C_PaymentTerm_ID = (Cust_Spinner) findViewById(R.id.sp_C_PaymentTerm_ID);
        sp_C_PaymentTerm_ID.setTableName("C_PaymentTerm");
        sp_C_PaymentTerm_ID.setIdentifierName("Name");
        
        
        addView((TextView) findViewById(R.id.tv_M_PriceList_ID), sp_M_PriceList_ID, "M_PriceList_ID", false);
        addView((TextView) findViewById(R.id.tv_C_PaymentTerm_ID), sp_C_PaymentTerm_ID, "C_PaymentTerm_ID", false);
        addView((TextView) findViewById(R.id.tv_SO_CreditLimit), (EditText) findViewById(R.id.et_SO_CreditLimit), "SO_CreditLimit", false);
        addView((TextView) findViewById(R.id.tv_SO_CreditUsed), (EditText) findViewById(R.id.et_SO_CreditUsed), "SO_CreditUsed", false);
        addView((TextView) findViewById(R.id.tv_SO_CreditAvailable), (EditText) findViewById(R.id.et_SO_CreditAvailable), "SO_CreditAvailable", false);
        addView((TextView) findViewById(R.id.tv_FlatDiscount), (EditText) findViewById(R.id.et_FlatDiscount), "FlatDiscount", false);
        addView((TextView) findViewById(R.id.tv_FirstSale), (Cust_DateBox) findViewById(R.id.cd_FirstSale), "FirstSale", false);
        
        /*
        sp_C_DocTypeTarget_ID = (Cust_Spinner) findViewById(R.id.sp_C_DocTypeTarget_ID);
		sp_C_DocTypeTarget_ID.setTableName("C_DocType");
		sp_C_DocTypeTarget_ID.setIdentifierName("Name");
		sp_C_DocTypeTarget_ID.setWhereClause("C_DocType.DocBaseType IN('SOO')");
        */
        
        /*final Spinner spBPGroup = (Spinner) findViewById(R.id.sp_C_BP_Group_ID);
        LinkedList<DisplaySpinner> data = new LinkedList<DisplaySpinner>();
        data.add(new DisplaySpinner(0, "10/07/1987"));
        //= ArrayAdapter.createFromResource(this, R.array.C_BP_Group_ID, android.R.layout.simple_spinner_item);
        ArrayAdapter<DisplaySpinner> spAdapter = new ArrayAdapter<DisplaySpinner>(this, android.R.layout.simple_spinner_item, data);
        spAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        spBPGroup.setAdapter(spAdapter);*/
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
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

	@Override
	public MP getCustomMP() {
		return null;//new MBPartner(this, 0);
	}

	@Override
	public MP getCustomMP(int id) {
		return null;//new MBPartner(ctx, id);
	}

	@Override
	public MP getCustomMP(Cursor rs) {
		return null;//new MBPartner(ctx, rs);
	}

	/* (non-Javadoc)
	 * @see org.appd.view.MVActivity#getActivityName()
	 */
	@Override
	protected String getActivityName() {
		return "C_BPartner";
	}
}
