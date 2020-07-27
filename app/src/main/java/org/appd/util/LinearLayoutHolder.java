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

import android.widget.LinearLayout;

/**
 * @author Yamel Senih
 *
 */
public class LinearLayoutHolder {
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 23/10/2012, 10:08:32
	 * @param ll_List
	 */
	public LinearLayoutHolder(LinearLayout ll_List){
		this.ch_List = ll_List;
	}
	
	public LinearLayoutHolder(){
	}
	
	private LinearLayout ch_List;
	
	/**
	 * Obtiene el LinearLayout
	 * @author Yamel Senih 22/10/2012, 17:56:09
	 * @return
	 * @return Button
	 */
	public LinearLayout getLinearLayout(){
		return ch_List;
	}
	
	/**
	 * Establece el LinearLayout
	 * @author Yamel Senih 22/10/2012, 17:56:28
	 * @param ch_List
	 * @return void
	 */
	public void setLinearLayout(LinearLayout ch_List){
		this.ch_List = ch_List;
	}
}
