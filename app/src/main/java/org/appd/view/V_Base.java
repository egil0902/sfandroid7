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
package org.appd.view;

import org.appd.util.DisplayMenuItem;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * @author Yamel Senih
 *
 */
public class V_Base extends Fragment {

	/**	Parameters	*/
	protected DisplayMenuItem param;
	
	public static V_Base newInstance(String content) {
		V_Base fragment = new V_Base();
        return fragment;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	
    }
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	Bundle bundle = getActivity().getIntent().getExtras();		
    	if(bundle != null){
			param = (DisplayMenuItem)bundle.getParcelable("Param");
		}
		if(param == null)
			throw new IllegalArgumentException ("No Param");
    }
    
}
