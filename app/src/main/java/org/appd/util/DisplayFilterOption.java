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

/**
 * @author Yamel Senih
 *
 */
public class DisplayFilterOption extends DisplaySpinner {
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 01/05/2012, 03:46:08
	 * @param m_ID
	 * @param value
	 */
	public DisplayFilterOption(int m_ID, String value) {
		super(m_ID, value, null);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 23/07/2012, 18:15:26
	 * @param m_ID
	 * @param value
	 * @param m_AD_Reference_ID
	 * @param columnName
	 * @param columnSQL
	 * @param defaultValue
	 */
	public DisplayFilterOption(int m_ID, String value, int m_AD_Reference_ID, 
			String columnName, String columnSQL, String defaultValue) {
		super(m_ID, value, null);
		this.m_AD_Reference_ID = m_AD_Reference_ID;
		this.columnName = columnName;
		this.isColumnSQL = (columnSQL != null && columnSQL.length() > 0);
		this.columnSQL = columnSQL;
		this.defaultValue = defaultValue;
		// TODO Auto-generated constructor stub
	}
	
	/**	Reference Value			*/
	private int	 	m_AD_Reference_ID;
	/**	Column Name				*/
	private String 		columnName 	= null;
	/**	Is Column SQL			*/
	private boolean 	isColumnSQL = false;
	/**	Column SQL				*/
	private String 		columnSQL 	= null;
	/**	Default Value			*/
	private String defaultValue = null;
	/**
	 * Obtiene el ID de Referencia de la opcion seleccionada 
	 * para evaluar el tipo de entrada
	 * @author Yamel Senih 01/05/2012, 03:40:34
	 * @return
	 * @return int
	 */
	public int getAD_Reference_ID(){
		return m_AD_Reference_ID;
	}
	
	/**
	 * Obtiene el nombre de la columna en BD
	 * @author Yamel Senih 01/05/2012, 03:41:48
	 * @return
	 * @return String
	 */
	public String getColumnName(){
		return columnName;
	}
	
	/**
	 * Obtiene el valor true cuando la columna es del tipo SQL
	 * @author Yamel Senih 17/07/2012, 10:57:04
	 * @return
	 * @return boolean
	 */
	public boolean isColumnSQL(){
		return isColumnSQL;
	}
	
	/**
	 * Obtiene el Query de la Columna SQL
	 * @author Yamel Senih 17/07/2012, 11:02:41
	 * @return
	 * @return String
	 */
	public String getColumnSQL(){
		return columnSQL;
	}
	
	/**
	 * Obtiene el valor por Defecto de la columna
	 * @author Yamel Senih 23/07/2012, 18:08:25
	 * @return
	 * @return String
	 */
	public String getDefaultValue(){
		return defaultValue;
	}

}
