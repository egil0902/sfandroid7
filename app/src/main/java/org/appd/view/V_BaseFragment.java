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

import java.util.ArrayList;

import org.appd.base.DB;
import org.appd.model.MP;
import org.appd.model.MPTableInfo;
import org.appd.util.DisplayMenuItem;
import org.appd.util.ViewIndex;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * 
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public abstract class V_BaseFragment extends Fragment {

	/**	Parameters	*/
	protected DisplayMenuItem param;
	
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
    
    protected 		MPTableInfo 			tabInfo 	= null;
    protected 		DB 					con 		= null;
	private 		MP 						mP 			= null;
	private 		ArrayList<ViewIndex> 	fFrontEnd;
	private 		boolean 				loadOk 		= false;
	private 		boolean 				isSummary	= false;
	/**	Current Status				*/
	protected static final int NEW = 0;
	protected static final int MODIFY = 1;
	protected static final int SEE = 3;
	protected static final int DELETED = 4;
	
	/**
	 * Get Activity Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 11/03/2013, 17:45:05
	 * @return
	 * @return String
	 */
	protected abstract String getActivityName();
    
	/**
	 * Get Custom MP
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 11/03/2013, 17:45:28
	 * @return
	 * @return MP
	 */
	protected abstract MP getCustomMP();
	
	/**
	 * Get Custom MP
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 11/03/2013, 17:45:41
	 * @param id
	 * @return
	 * @return MP
	 */
	protected abstract MP getCustomMP(int id);
    
}
