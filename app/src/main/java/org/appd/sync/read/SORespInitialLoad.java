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
package org.appd.sync.read;

/**
 * @author Carlos Parada
 *
 */
public class SORespInitialLoad {

	String m_Name ;
	String m_Sql ;
	
	/**
	 * *** Constructor Class ***
	 * @author Carlos Parada 13/05/2012, 18:13:30
	 */
	public SORespInitialLoad(String p_name,String p_Sql) {
		// TODO Auto-generated constructor stub
		m_Name = p_name;
		m_Sql = p_Sql;
	}
	
	/**
	 * @param m_Name the m_Name to set
	 */
	public void setM_Name(String m_Name) {
		this.m_Name = m_Name;
	}
	/**
	 * @param m_Sql the m_Sql to set
	 */
	public void setM_Sql(String m_Sql) {
		this.m_Sql = m_Sql;
	}
	/**
	 * @return the m_Name
	 */
	public String getM_Name() {
		return m_Name;
	}
	/**
	 * @return the m_Sql
	 */
	public String getM_Sql() {
		return m_Sql;
	}
}
