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
package org.appd.util;

import java.io.Serializable;

/**
 * @author Carlos Parada
 *
 */
public class DisplaySyncItem implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7760154576590403365L;


	public DisplaySyncItem(int record_ID, String name, String description, 
			String ImgName,String IsSummary,int NumberChildren,String MenuType){
		this.record_ID=record_ID;
		this.name=name;
		this.description=(description==null?"":description);
		this.m_ImgName=ImgName;
		this.m_IsSummary=IsSummary;
		this.m_NumberChildren=NumberChildren;
		this.m_MenuType=MenuType;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the record_ID
	 */
	public int getRecord_ID() {
		return record_ID;
	}
	
	/**
	 * @return the m_ImgName
	 */
	public String getM_ImgName() {
		return m_ImgName;
	}
	
	
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/**
	 * @return the m_IsSummary
	 */
	public String getM_IsSummary() {
		return m_IsSummary;
	}
	
	/**
	 * @param m_IsSummary the m_IsSummary to set
	 */
	public void setM_IsSummary(String m_IsSummary) {
		this.m_IsSummary = m_IsSummary;
	}
	
	/**
	 * @return the m_NumberChildren
	 */
	public int getM_NumberChildren() {
		return m_NumberChildren;
	}
	
	/**
	 * @return the m_MenuType
	 */
	public String getM_MenuType() {
		return m_MenuType;
	}
	
	/**	Record ID			*/
	private int 		record_ID = 0;
	/**	Name Item			*/
	private String  	name = null;
	/**	Short Description	*/
	private String 		description = null;
	/** Img Name*/
	private String 		m_ImgName;
	/** IsSummary*/
	private String m_IsSummary;
	/** Number of Children */
	private int m_NumberChildren;
	/**Menu Type*/
	private String m_MenuType;
}
