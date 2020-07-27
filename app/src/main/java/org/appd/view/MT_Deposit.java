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

import org.appd.base.Env;
import org.appd.base.R;

import android.os.Bundle;
import android.view.View;

/**
 * @author Yamel Senih
 *
 */
public class MT_Deposit extends MTabActivity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        
    	addActivity(MV_Deposit.class, "C_Payment", 
    			R.string.t_Deposit,R.drawable.direct_deposit_m);
    	addActivity(MV_DepositLine.class, "C_PaymentLine", 
    			R.string.t_DepositLine,R.drawable.payment_mixed_m);
	}
	
	@Override
	protected void e_ButtSave(){
		super.e_ButtSave();
		int tabIndex = mTabHost.getCurrentTab();
		if(tabIndex == 0){
			if(record_ID != 0){
				mTabHost.getTabWidget().getChildAt(1).setEnabled(true);
			}
		}	
	}
	
	@Override
	protected void e_ButtNew(){
		super.e_ButtNew();
		int tabIndex = mTabHost.getCurrentTab();
		if(tabIndex == 0){
			if(record_ID == 0){
				mTabHost.getTabWidget().getChildAt(1).setEnabled(false);
			}
		}
	}
	
	@Override
	protected void e_ButtModify(){
		super.e_ButtModify();
		int tabIndex = mTabHost.getCurrentTab();
		if(tabIndex == 0)
			mTabHost.getTabWidget().getChildAt(1).setEnabled(false);
	}
	
	@Override
	protected void e_ButtBack(){
		super.e_ButtBack();
		int tabIndex = mTabHost.getCurrentTab();
		if(tabIndex == 0){
			if(record_ID != 0){
				mTabHost.getTabWidget().getChildAt(1).setEnabled(true);
			}
		}
	}
	
	@Override
	protected void voidConfig(){
		super.voidConfig();
		int tabIndex = mTabHost.getCurrentTab();
		if(tabIndex == 0){
			mTabHost.getTabWidget().getChildAt(1).setEnabled(false);
		}
	}
	
	@Override
    protected void onResume() {
        super.onResume();
        int tabIndex = mTabHost.getCurrentTab();
		if(tabIndex == 0){
			if(Env.getTabRecord_ID(this, "C_Payment") > 0){
				mTabHost.getTabWidget().getChildAt(1).setEnabled(true);
			} else {
				mTabHost.getTabWidget().getChildAt(1).setEnabled(false);
			}
		} else if(tabIndex == 1){
			voidConfig();
			buttNew.setEnabled(false);
			if(Env.getTabRecord_ID(this, "C_Payment") == 0)
				mTabHost.setCurrentTab(0);
		}
    }
	
	@Override
	public void onTabChanged(String tabId) {
		super.onTabChanged(tabId);
		int tabIndex = mTabHost.getCurrentTab();
		
		if(tabIndex == 1){
			setVisibleButtons(View.GONE);
		} else {
			setVisibleButtons(View.VISIBLE);
			MVActivity bp = (MVActivity)getCurrentActivity();
			bp.refresh();
		}
	}
	
	/* (non-Javadoc)
	 * @see org.appd.view.MTabActivity#getActivityName()
	 */
	@Override
	protected String getActivityName() {
		return getClass().getSimpleName();
	}
	
}
