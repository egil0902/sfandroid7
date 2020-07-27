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
public class MT_VisitAndLine extends MTabActivity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	addActivity(MV_VisitAndLine.class, "XX_MB_Visit", R.string.XX_MB_Visit_ID, R.drawable.meet_m);
    	setVisibleButtons(View.GONE);
	}

	/* (non-Javadoc)
	 * @see org.appd.view.MTabActivity#getActivityName()
	 */
	@Override
	protected String getActivityName() {
		return getClass().getSimpleName();
	}
}
