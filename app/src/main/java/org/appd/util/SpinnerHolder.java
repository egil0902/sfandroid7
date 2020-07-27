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

import android.widget.Spinner;

/**
 * @author Yamel Senih
 *
 */
public class SpinnerHolder {
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 22/10/2012, 17:48:30
	 * @param et_List
	 */
	public SpinnerHolder(Spinner et_List){
		this.sp_List = et_List;
	}
	
	public SpinnerHolder(){
	}
	
	private Spinner sp_List;
	
	/**
	 * Obtiene el Spinner
	 * @author Yamel Senih 22/10/2012, 17:49:16
	 * @return
	 * @return Spinner
	 */
	public Spinner getSpinner(){
		return sp_List;
	}
	
	/**
	 * Establece el Spinner
	 * @author Yamel Senih 22/10/2012, 17:49:37
	 * @param ch_List
	 * @return void
	 */
	public void setSpinner(Spinner ch_List){
		this.sp_List = ch_List;
	}
	
	/**
	 * Obtiene el objeto seleccionado
	 * @author Yamel Senih 23/10/2012, 00:24:29
	 * @return
	 * @return Object
	 */
	public Object getSelectedItem(){
		return sp_List.getSelectedItem();
	}
	
	/**
	 * Selecciona la posicion donde se encuentra un valor
	 * @author Yamel Senih 23/10/2012, 00:27:30
	 * @param sp
	 * @param value
	 * @return
	 * @return boolean
	 */
	public boolean setSelectedValue(String value){
		int pos = 0;
		for(int i = 0; i < sp_List.getCount(); i++){
			DisplaySpinner ds = (DisplaySpinner)sp_List.getItemAtPosition(i);
			if(ds.getHiddenValue() == value){
				pos = i;
				break;
			}
		}
		sp_List.setSelection(pos);
		return (pos != 0);
	}
	
	/**
	 * Selecciona la posicion donde se encuentra el id del registro
	 * @author Yamel Senih 23/10/2012, 10:59:27
	 * @param id
	 * @return
	 * @return boolean
	 */
	public boolean setSelected(int id){
		int pos = 0;
		for(int i = 0; i < sp_List.getCount(); i++){
			DisplaySpinner ds = (DisplaySpinner)sp_List.getItemAtPosition(i);
			if(ds.getID() == id){
				pos = i;
				break;
			}
		}
		sp_List.setSelection(pos);
		return (pos != 0);
	}
	
}
