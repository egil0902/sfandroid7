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

/**
 * @author Carlos Parada
 *
 */
@SuppressWarnings("rawtypes")
public class PField {

	/**
	 * *** Constructor de la Clase ***
	 * @author Carlos Parada 21/05/2012, 21:47:29
	 */
	
	public PField(String field, Object value,Class type) {
		// TODO Auto-generated constructor stub
		m_field = field;
		m_value = value;
		m_type = type;
	}
	/**
	 * @param m_field the m_field to set
	 */
	public void setM_field(String m_field) {
		this.m_field = m_field;
	}
	/**
	 * @param m_type the m_type to set
	 */
	public void setM_type(Class m_type) {
		this.m_type = m_type;
	}
	/**
	 * @param m_value the m_value to set
	 */
	public void setM_value(Object m_value) {
		this.m_value = m_value;
	}
	/**
	 * @return the m_field
	 */
	public String getM_field() {
		return m_field;
	}
	/**
	 * @return the m_value
	 */
	public Object getM_value() {
		return m_value;
	}
	/**
	 * @return the m_type
	 */
	public Class getM_type() {
		return m_type;
	}
	private String m_field;
	private Object m_value;
	private Class m_type;
}
