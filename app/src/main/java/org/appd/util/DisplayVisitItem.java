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
public class DisplayVisitItem extends DisplayItem {

	/**
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 18/08/2012, 22:13:10
	 * @param record_ID
	 * @param name
	 * @param description
	 */
	public DisplayVisitItem(int record_ID, String name, String description) {
		super(record_ID, name, description);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 19/08/2012, 19:36:34
	 * @param record_ID
	 * @param time
	 * @param description
	 * @param m_XX_BaseEventType
	 * @param m_XX_MB_EventType_ID
	 * @param eventTypeName
	 * @param m_Foreign_ID
	 * @param m_TableName
	 */
	public DisplayVisitItem(int record_ID, String time, String description, 
			String m_XX_BaseEventType, int m_XX_MB_EventType_ID, String eventTypeName, 
			int m_Foreign_ID, String m_TableName) {
		super(record_ID, time, description);
		this.m_XX_BaseEventType = m_XX_BaseEventType;
		this.m_XX_MB_EventType_ID = m_XX_MB_EventType_ID;
		this.eventTypeValue = eventTypeName;
		this.m_Foreign_ID = m_Foreign_ID;
		this.m_TableName = m_TableName;
		
	}
	
	/**	Event Type ID			*/
	private int 	m_XX_MB_EventType_ID = 0;
	/**	Event Type Name			*/
	private String eventTypeValue = null;
	/**	Foreign Key				*/
	private int 	m_Foreign_ID = 0;
	/**	Base Event Type			*/
	private String m_XX_BaseEventType = null;
	/**	Table Name				*/
	private String m_TableName = null;
	
	/**
	 * Establece el valor de XX_MB_EventType_ID
	 * @author Yamel Senih 18/08/2012, 22:27:33
	 * @param m_XX_MB_EventType_ID
	 * @return void
	 */
	public void setXX_MB_EventType_ID(int m_XX_MB_EventType_ID){
		this.m_XX_MB_EventType_ID = m_XX_MB_EventType_ID;
	}
	
	/**
	 * Obtiene el valor de XX_MB_EventType_ID
	 * @author Yamel Senih 18/08/2012, 22:28:12
	 * @return
	 * @return int
	 */
	public int getXX_MB_EventType_ID(){
		return m_XX_MB_EventType_ID;
	}
	
	/**
	 * Establece el Nombre del Tipo de Evento
	 * @author Yamel Senih 18/08/2012, 22:48:52
	 * @param eventTypeValue
	 * @return void
	 */
	public void setEventTypeValue(String eventTypeValue){
		this.eventTypeValue = eventTypeValue;
	}
	
	/**
	 * Obtiene el valor del tipo de Evento
	 * @author Yamel Senih 18/08/2012, 22:49:38
	 * @return
	 * @return String
	 */
	public String getEventTypeValue(){
		return eventTypeValue;
	}
	
	/**
	 * Establece el valor de la clave Foranea
	 * @author Yamel Senih 18/08/2012, 22:35:38
	 * @param m_Foreign_ID
	 * @return void
	 */
	public void setForeign_ID(int m_Foreign_ID){
		this.m_Foreign_ID = m_Foreign_ID;
	}
	
	/**
	 * Obtiene el ID Foraneo
	 * @author Yamel Senih 18/08/2012, 22:47:33
	 * @return
	 * @return int
	 */
	public int getForeign_ID(){
		return m_Foreign_ID;
	}
	
	/**
	 * Establece el Tipo de Evento Base
	 * @author Yamel Senih 18/08/2012, 22:55:12
	 * @param m_XX_BaseEventType
	 * @return void
	 */
	public void setXX_BaseEventType(String m_XX_BaseEventType){
		this.m_XX_BaseEventType = m_XX_BaseEventType;
	}
	
	/**
	 * Obtiene el Tipo de Evento Base
	 * @author Yamel Senih 18/08/2012, 22:49:38
	 * @return
	 * @return String
	 */
	public String getXX_BaseEventType(){
		return m_XX_BaseEventType;
	}
	
	/**
	 * Establece el Nombre de la tabla
	 * @author Yamel Senih 19/08/2012, 19:37:19
	 * @param m_TableName
	 * @return void
	 */
	public void setTableName(String m_TableName){
		this.m_TableName = m_TableName;
	}
	
	/**
	 * Obtiene el Nombre de la Tabla
	 * @author Yamel Senih 19/08/2012, 19:37:32
	 * @return
	 * @return String
	 */
	public String getTableName(){
		return m_TableName;
	}
	
}
