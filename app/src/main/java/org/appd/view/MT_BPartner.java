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

import android.os.Bundle;
import android.view.View;

/**
 * @author Yamel Senih
 *
 */
public class MT_BPartner extends MTabActivity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        
    	addActivity(MV_BPartner.class, "C_BPartner", R.string.C_BPartner_ID, R.drawable.bpartner_m);
    	addActivity(MV_BPartnerLocation.class, "C_BPartner_Location", R.string.C_BP_Location_ID, R.drawable.location_m);
    	addActivity(MV_BPartnerPlanningVisit.class, "XX_MB_VPartnerPlanningVisit", R.string.XX_MB_PlanningVisit_ID, R.drawable.klipper_m);
	}
	
	@Override
	public void onTabChanged(String tabId) {
		super.onTabChanged(tabId);
		int tabIndex = mTabHost.getCurrentTab();
		
		if(tabIndex == 1
				|| tabIndex == 2){
			setVisibleButtons(View.GONE);
		} else {
			setVisibleButtons(View.VISIBLE);
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
