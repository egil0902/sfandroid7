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
import org.appd.interfaces.I_CancelOk;

import android.os.Bundle;

/**
 * @author Yamel Senih
 *
 */
public class MT_AddVisitLine extends VT_CancelOk {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	addFagment(MV_AddVisitLine.class, null, R.string.START_No_Sales_Reason);
    	setTitle(getResources().getString(R.string.msg_AddEvent));
    }

	/* (non-Javadoc)
	 * @see org.appd.view.MT_CancelOk#processActionOk()
	 */
	@Override
	protected void processActionOk() {
		I_CancelOk fr = (I_CancelOk) getCurrentFragment();
		if(fr.aceptAction()){
			setResult(RESULT_OK, fr.getParam());
			finish();
		}

	}

	/* (non-Javadoc)
	 * @see org.appd.view.MT_CancelOk#processActionCancel()
	 */
	@Override
	protected void processActionCancel() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}

}