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

import org.appd.view.custom.Cust_DateBox;

/**
 * @author Yamel Senih
 *
 */
public class Cust_DateBoxHolder {
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 14/05/2012, 19:26:46
	 * @param et_List
	 */
	public Cust_DateBoxHolder(Cust_DateBox et_List){
		this.et_List = et_List;
	}
	
	public Cust_DateBoxHolder(){
	}
	
	private Cust_DateBox et_List;
	
	/**
	 * Obtiene el EditText de la Clase
	 * @author Yamel Senih 14/05/2012, 19:27:39
	 * @return
	 * @return EditText
	 */
	public Cust_DateBox getCust_DateBox(){
		return et_List;
	}
	
	/**
	 * Establece el EditText
	 * @author Yamel Senih 14/05/2012, 19:28:56
	 * @param et_List
	 * @return void
	 */
	public void setCust_DateBox(Cust_DateBox et_List){
		this.et_List = et_List;
	}
	
	/**
	 * Obtiene la fecha
	 * @author Yamel Senih 22/10/2012, 18:11:10
	 * @return
	 * @return String
	 */
	public String getDate(){
		return et_List.getDate();
	}
	
	/**
	 * Establece la Fecha
	 * @author Yamel Senih 22/10/2012, 18:10:56
	 * @param date
	 * @return void
	 */
	public void setDate(String date){
		et_List.setDate(date);
	}
	
}
