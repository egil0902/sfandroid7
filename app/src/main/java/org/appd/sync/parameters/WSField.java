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
 * Contributor(s): Carlos Parada www.erpconsultoresyasociados.com                    *
 *************************************************************************************/
package org.appd.sync.parameters;


import org.ksoap2.serialization.SoapObject;
import android.content.Context;

/**
 * @author Carlos Parada
 *
 */
public class WSField extends SoapObject{

	/**
	 * *** Constructor de la Clase ***
	 * @author Carlos Parada 22/05/2012, 02:15:36
	 * @param ctx
	 * @param NameSpace
	 * @param Name
	 */
	public WSField(Context ctx, String NameSpace,String p_ColumnName,Object p_Value) {
		super(NameSpace, "field");
		
		m_ColumnName = p_ColumnName;
		m_Value = p_Value;
		//Atributo Nombre de columna
		addAttribute("column",m_ColumnName);
		//Propiedad Valor de Columna 
		addProperty("val", m_Value);
		// TODO Auto-generated constructor stub
	}
	
	String m_ColumnName;
	Object m_Value;
	

}
