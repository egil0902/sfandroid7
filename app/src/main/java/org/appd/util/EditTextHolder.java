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

import android.widget.EditText;

/**
 * @author Yamel Senih
 *
 */
public class EditTextHolder {
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 14/05/2012, 19:26:46
	 * @param et_List
	 */
	public EditTextHolder(EditText et_List){
		this.et_List = et_List;
	}
	
	public EditTextHolder(){
	}
	
	private EditText et_List;
	
	/**
	 * Obtiene el EditText de la Clase
	 * @author Yamel Senih 14/05/2012, 19:27:39
	 * @return
	 * @return EditText
	 */
	public EditText getEditText(){
		return et_List;
	}
	
	/**
	 * Establece el EditText
	 * @author Yamel Senih 14/05/2012, 19:28:56
	 * @param et_List
	 * @return void
	 */
	public void setEditText(EditText et_List){
		this.et_List = et_List;
	}
	
	/**
	 * Obtiene el String del EditText
	 * @author Yamel Senih 14/05/2012, 19:48:41
	 * @return
	 * @return String
	 */
	public String getString(){
		return et_List.getText().toString();
	}
	
	/**
	 * Establece el valor del EditText
	 * @author Yamel Senih 14/05/2012, 19:52:22
	 * @param text
	 * @return void
	 */
	public void setString(String text){
		et_List.setText(text);
	}
	
}
