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
public class DisplayItem {

	public DisplayItem(int record_ID, String name, String description){
		this.record_ID = record_ID;
		this.name = name;
		this.description = description;
	}
	
	/**	Record ID			*/
	private int record_ID = 0;
	/**	Name Item			*/
	private String  name = null;
	/**	Short Description	*/
	private String description = null;
	
	/**
	 * Obtiene el ID del Item del Menu
	 * @author Yamel Senih 28/04/2012, 00:23:13
	 * @return
	 * @return int
	 */
	public int getRecord_ID(){
		return record_ID;
	}
	
	/**
	 * Obtiene el nombre del Item
	 * @author Yamel Senih 28/04/2012, 00:24:01
	 * @return
	 * @return String
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Obtiene el nombre de la imagen
	 * @author Yamel Senih 28/04/2012, 00:25:17
	 * @return
	 * @return String
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * Establece el Valor del Nombre
	 * @author Yamel Senih 18/08/2012, 22:57:49
	 * @param name
	 * @return void
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 * Establece el Valor de la Descripcion
	 * @author Yamel Senih 18/08/2012, 22:57:21
	 * @param description
	 * @return void
	 */
	public void setDescription(String description){
		this.description = description;
	}
	
}
