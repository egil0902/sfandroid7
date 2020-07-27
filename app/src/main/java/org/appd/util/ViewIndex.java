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

import android.view.View;

/**
 * @author Yamel Senih
 *
 */
public class ViewIndex {
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 28/05/2012, 10:10:25
	 * @param vFieldLabel
	 * @param vFieldValue
	 * @param columnName
	 * @param isReadOnly
	 * @param index
	 */
	public ViewIndex(View vFieldLabel, View vFieldValue, String columnName, boolean isReadOnly, int index){
		this.vFieldLabel = vFieldLabel;
		this.vFieldValue = vFieldValue;
		this.index = index;
		this.columnName = columnName;
		this.isReadOnly = isReadOnly;
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 28/05/2012, 10:10:51
	 * @param vFieldLabel
	 * @param vFieldValue
	 * @param defaultValue
	 * @param columnName
	 * @param isReadOnly
	 * @param index
	 */
	public ViewIndex(View vFieldLabel, View vFieldValue, String defaultValue, String columnName, boolean isReadOnly, int index){
		this.vFieldLabel = vFieldLabel;
		this.vFieldValue = vFieldValue;
		this.index = index;
		this.defaultValue = defaultValue;
		this.columnName = columnName;
		this.isReadOnly = isReadOnly;
	}
	
	private	int		index = 0;
	private	View	vFieldValue = null;
	private	View	vFieldLabel = null;
	private	String	defaultValue = null;
	private String	columnName = null;
	private boolean isReadOnly = false;
	
	/**
	 * Obtiene el indice
	 * @author Yamel Senih 08/04/2012, 17:40:07
	 * @return
	 * @return int
	 */
	public int getIndex(){
		return index;
	}
	
	/**
	 * Obriene el view que contiene el valor
	 * @author Yamel Senih 08/04/2012, 17:40:46
	 * @return
	 * @return View
	 */
	public View getViewValue(){
		return vFieldValue;
	}
	
	/**
	 * Obtiene la etiqueta que muestra el nombre del campo
	 * @author Yamel Senih 03/05/2012, 04:28:58
	 * @return
	 * @return View
	 */
	public View getViewLabel(){
		return vFieldLabel;
	}
	
	/**
	 * Obtiene el Nombre de la Columna
	 * @author Yamel Senih 20/05/2012, 00:41:05
	 * @return
	 * @return String
	 */
	public String getColumnName(){
		return columnName;
	}
	
	/**
	 * Habilita o desabilita el objeto que contiene
	 * @author Yamel Senih 04/05/2012, 19:29:44
	 * @param enabled
	 * @return void
	 */
	public void setEnabled(boolean enabled){
		vFieldValue.setEnabled(enabled);
	}
	
	/**
	 * Obtiene el valor por defecto a nivel de objeto
	 * @author Yamel Senih 11/05/2012, 16:24:44
	 * @return
	 * @return String
	 */
	public String getDefaultValue(){
		return defaultValue;
	}
	
	/**
	 * Verifica si la columna es solo lectura
	 * @author Yamel Senih 28/05/2012, 10:16:39
	 * @return
	 * @return boolean
	 */
	public boolean isReadOnly(){
		return isReadOnly;
	}
	
}
