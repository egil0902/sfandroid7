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
package org.appd.view;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Yamel Senih
 *
 */
public class DisplayMenuItem implements Parcelable {
		
	/**
	 * 
	 * *** Contructor ***
	 * @author Yamel Senih 04/05/2012, 22:02:31
	 * @param record_ID
	 * @param name
	 * @param description
	 * @param action
	 * @param img
	 * @param className
	 * @param isReadWrite
	 * @param m_AD_Table_ID
	 * @param tableName
	 * @param whereClause
	 * @param orderByClause
	 */
	public DisplayMenuItem(int record_ID, String name, String description, 
			String action, String img, String className, 
			boolean isReadWrite, int m_AD_Table_ID, String tableName, 
			String whereClause, String orderByClause){
		this.record_ID = record_ID;
		this.name = name;
		this.action = action;
		this.description = description;
		this.img = img;
		this.className = className;
		this.m_IsReadWrite = isReadWrite;
		this.m_AD_Table_ID = m_AD_Table_ID;
		this.tableName = tableName;
		this.whereClause = whereClause;
		this.orderByClause = orderByClause;
	}
	
	public DisplayMenuItem(){
		this.record_ID = 0;		
	}
	

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih 14/08/2012, 22:09:49
	 * @param m_SF_MenuList_ID
	 * @param name
	 * @param description
	 * @param action
	 * @param img
	 * @param className
	 * @param isReadWrite
	 * @param m_AD_Table_ID
	 * @param tableName
	 * @param whereClause
	 * @param groupByClause
	 * @param orderByClause
	 * @param m_SF_ParentMenuList_ID
	 * @param m_IsSummary
	 * @param activitType
	 * @param m_AD_Process_ID
	 * @param processClassName
	 * @param isReadWriteM
	 * @param isInsertRecord
	 */
	public DisplayMenuItem(int m_SF_MenuList_ID, String name, String description, 
			String action, String img, String className, 
			boolean isReadWrite, int m_AD_Table_ID, String tableName, 
			String whereClause, String groupByClause, String orderByClause, 
			int m_SF_ParentMenuList_ID, boolean m_IsSummary, 
			String activitType, int m_AD_Process_ID, String processClassName, 
			String isReadWriteM, String isInsertRecord){
		this.m_SF_MenuList_ID = m_SF_MenuList_ID;
		this.name = name;
		this.action = action;
		this.description = description;
		this.img = img;
		this.className = className;
		this.m_IsReadWrite = isReadWrite;
		this.m_AD_Table_ID = m_AD_Table_ID;
		this.tableName = tableName;
		this.whereClause = whereClause;
		this.groupByClause = groupByClause;
		this.orderByClause = orderByClause;
		this.m_SF_ParentMenuList_ID = m_SF_ParentMenuList_ID;
		this.m_IsSummary = m_IsSummary;
		this.activitType = activitType;
		this.originalActivityType = activitType;
		this.m_AD_Process_ID = m_AD_Process_ID;
		this.processClassName = processClassName;
		this.m_IsReadWriteM = isReadWriteM;
		if(isReadWriteM != null){
			this.m_IsReadWrite = isReadWriteM.equals("Y");
		}
		
		if(isInsertRecord != null){
			this.m_IsInsertRecord = isInsertRecord.equals("Y");
		}
		
	}
	
	/**
	 * Called in search
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 23/05/2012, 04:32:37
	 * @param name
	 * @param description
	 * @param m_AD_Table_ID
	 * @param tableName
	 * @param whereClause
	 * @param orderByClause
	 * @param orderByClause
	 * @param activitType
	 */
	public DisplayMenuItem(String name, String description, 
			int m_AD_Table_ID, String tableName, 
			String whereClause, String orderByClause, 
			String activitType){
		this.name = name;
		this.description = description;
		this.m_AD_Table_ID = m_AD_Table_ID;
		this.tableName = tableName;
		this.whereClause = whereClause;
		this.orderByClause = orderByClause;
		this.activitType = activitType;
		this.originalActivityType = activitType;
		this.action = "F";
	}
	
	
	
	/**	Record ID			*/
	private int 			record_ID = 0;
	/**	Name Item			*/
	private String  		name = null;
	/**	Short Description	*/
	private String 			description = null;
	/**	Action Menu			*/
	private String  		action = null;
	/**	Image				*/
	private String 			img = null;
	/**	Class				*/
	private String 			className = null;
	/**	Table				*/
	private int 			m_AD_Table_ID = 0;
	/**	Is ReadOnly			*/
	private boolean	 		m_IsReadWrite = false;
	/**	Table Name			*/
	private String 			tableName = null;
	/**	Where				*/
	private String			whereClause = null;
	/**	Order By			*/
	private String			orderByClause = null;
	/**	Menu ID				*/
	private int				m_SF_ParentMenuList_ID = 0;
	/**	Is Summary Menu		*/
	private boolean			m_IsSummary = false;
	/**	Activity Type		*/
	private String			activitType = "S";
	/**	Process ID			*/
	private int 			m_AD_Process_ID = 0;
	/**	Process Class Name	*/
	private String 			processClassName = null;
	/**	Menu is Read and Write*/
	private String		 	m_IsReadWriteM = null;
	/**	Menu is Insert Record*/
	private boolean			m_IsInsertRecord = true;
	/**	ID del Menu			*/
	private int				m_SF_MenuList_ID = 0;
	/**	From				*/
	private int		 		from = 0;
	/**	Identifier			*/
	private String			identifier = null;
	/**	Order By			*/
	private String			groupByClause = null;
	/**	Original Menu		*/
	private String			originalActivityType = "S";
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public DisplayMenuItem createFromParcel(Parcel parcel) {
			return new DisplayMenuItem(parcel);
		}
		public DisplayMenuItem[] newArray(int size) {
			return new DisplayMenuItem[size];
		}
	};
	
	public DisplayMenuItem(Parcel parcel){
		this();
		readToParcel(parcel);
	}
	
	@Override
	public int describeContents() {
		return hashCode();
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(record_ID);
		parcel.writeString(name);
		parcel.writeString(description);
		parcel.writeString(action);
		parcel.writeString(img);
		parcel.writeString(className);
		parcel.writeInt(m_AD_Table_ID);
		parcel.writeString(tableName);
		parcel.writeString(whereClause);
		parcel.writeString(orderByClause);
		parcel.writeInt(m_SF_ParentMenuList_ID);
		parcel.writeString(activitType);
		parcel.writeInt(m_AD_Process_ID);
		parcel.writeString(processClassName);
		parcel.writeString(m_IsReadWriteM);
		parcel.writeInt(m_SF_MenuList_ID);
		parcel.writeInt(from);
		parcel.writeString(identifier);
		parcel.writeString(groupByClause);
		parcel.writeString(originalActivityType);
		parcel.writeBooleanArray(new boolean []{
				m_IsReadWrite, 
				m_IsSummary, 
				m_IsInsertRecord});
	}
	
	public void readToParcel(Parcel parcel){
		record_ID = parcel.readInt();
		name = parcel.readString();
		description = parcel.readString();
		action = parcel.readString();
		img = parcel.readString();
		className = parcel.readString();
		m_AD_Table_ID = parcel.readInt();
		tableName = parcel.readString();
		whereClause = parcel.readString();
		orderByClause = parcel.readString();
		m_SF_ParentMenuList_ID = parcel.readInt();
		activitType = parcel.readString();
		m_AD_Process_ID = parcel.readInt();
		processClassName = parcel.readString();
		m_IsReadWriteM = parcel.readString();
		m_SF_MenuList_ID = parcel.readInt();
		from = parcel.readInt();
		identifier = parcel.readString();
		groupByClause = parcel.readString();
		originalActivityType = parcel.readString();
		//	Read Boolean
		boolean valBool[] = {false, false, false};
		parcel.readBooleanArray(valBool);
		m_IsReadWrite = valBool[0];
		m_IsSummary = valBool[1];
		m_IsInsertRecord = valBool[2];
		
	}

	
	/**
	 * Create Menu from other
	 * @author Yamel Senih 01/08/2012, 12:10:41
	 * @param fromMenu
	 * @return
	 * @return DisplayMenuItem
	 */
	public static DisplayMenuItem createFromMenu(DisplayMenuItem fromMenu){
		DisplayMenuItem item = new DisplayMenuItem();
		
		item.setSF_MenuList_ID(item.getSF_MenuList_ID());
		item.setRecord_ID(fromMenu.getRecord_ID());
		item.setName(fromMenu.getName());
		item.setAction(fromMenu.getAction());
		item.setDescription(fromMenu.getDescription());
		item.setImg(fromMenu.getImg());
		item.setClassName(fromMenu.getClassName());
		item.setIsReadWrite(fromMenu.isReadWrite());
		item.setAD_Table_ID(fromMenu.getAD_Table_ID());
		item.setTableName(fromMenu.getTableName());
		item.setWhereClause(fromMenu.getWhereClause());
		item.setOrderByClause(fromMenu.getOrderByClause());
		item.setParentMenuList_ID(fromMenu.getParentMenuList_ID());
		item.setIsSummary(fromMenu.getIsSummary());
		item.setActivityType(fromMenu.getActivityType());
		item.setAD_Table_ID(fromMenu.getAD_Process_ID());
		item.setProcessClassName(fromMenu.getProcessClassName());
		item.setIsReadWriteM(fromMenu.isReadWriteM());
		item.setOriginalActivityType(fromMenu.getActivityType());
		
		return item;
	}
	
	/**
	 * Set Identifier
	 * @author Yamel Senih 08/08/2012, 22:42:31
	 * @param identifier
	 * @return void
	 */
	public void setIdentifier(String identifier){
		this.identifier = identifier;
	}
	
	/**
	 * Get Identifier
	 * @author Yamel Senih 08/08/2012, 22:43:17
	 * @return
	 * @return String
	 */
	public String getIdentifier(){
		return identifier;
	}
	
	/**
	 * Set polsition from
	 * @author Yamel Senih 08/08/2012, 19:15:45
	 * @param from
	 * @return void
	 */
	public void setFrom(int from){
		this.from = from;
	}
	
	/**
	 * Get position from
	 * @author Yamel Senih 08/08/2012, 19:16:10
	 * @return
	 * @return int
	 */
	public int getFrom(){
		return from;
	}
	
	/**
	 * Get Record Identifier
	 * @author Yamel Senih 28/04/2012, 00:23:13
	 * @return
	 * @return int
	 */
	public int getRecord_ID(){
		return record_ID;
	}
	
	/**
	 * Get Menu ID
	 * @author Yamel Senih 01/08/2012, 12:35:24
	 * @return
	 * @return int
	 */
	public int getSF_MenuList_ID(){
		return m_SF_MenuList_ID;
	}
	
	/**
	 * Set Menu ID
	 * @author Yamel Senih 01/08/2012, 12:38:45
	 * @param m_SF_MenuList_ID
	 * @return void
	 */
	public void setSF_MenuList_ID(int m_SF_MenuList_ID){
		this.m_SF_MenuList_ID = m_SF_MenuList_ID;
	}
	
	/**
	 * Set Record ID
	 * @author Yamel Senih 01/08/2012, 09:54:52
	 * @param record_ID
	 * @return void
	 */
	public void setRecord_ID(int record_ID){
		this.record_ID = record_ID;
	}
	
	/**
	 * Get Item Name
	 * @author Yamel Senih 28/04/2012, 00:24:01
	 * @return
	 * @return String
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Set Item Name 
	 * @author Yamel Senih 01/08/2012, 09:55:37
	 * @param name
	 * @return void
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Set Image Name
	 * @author Yamel Senih 28/04/2012, 00:25:17
	 * @return
	 * @return String
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * Set Activity Description
	 * @author Yamel Senih 01/08/2012, 10:04:18
	 * @param description
	 * @return void
	 */
	public void setDescription(String description){
		this.description = description;
	}
	
	/**
	 * Get Image Name
	 * @author Yamel Senih 28/04/2012, 00:24:59
	 * @return
	 * @return String
	 */
	public String getImg(){
		return img;
	}
	
	/**
	 * Set Image Name
	 * @author Yamel Senih 01/08/2012, 10:05:32
	 * @param img
	 * @return void
	 */
	public void setImg(String img){
		this.img = img;
	}
	
	/**
	 * Get Class Name
	 * @author Yamel Senih 28/04/2012, 03:24:38
	 * @return
	 * @return String
	 */
	public String getClassName(){
		return className;
	}
	
	/**
	 * Set Class Name
	 * @author Yamel Senih 01/08/2012, 10:06:19
	 * @param className
	 * @return void
	 */
	public void setClassName(String className){
		this.className = className;
	}
	
	/**
	 * Get Action Menu
	 * @author Yamel Senih 28/04/2012, 16:09:28
	 * @return
	 * @return String
	 */
	public String getAction(){
		return action;
	}
	
	/**
	 * Set Action Menu
	 * @author Yamel Senih 01/08/2012, 10:10:58
	 * @param action
	 * @return void
	 */
	public void setAction(String action){
		this.action = action;
	}
	
	/**
	 * Get Table ID
	 * @author Yamel Senih 30/04/2012, 19:25:23
	 * @return
	 * @return int
	 */
	public int getAD_Table_ID(){
		return m_AD_Table_ID;
	}
	
	/**
	 * Set Table ID
	 * @author Yamel Senih 01/08/2012, 10:11:49
	 * @param m_AD_Table_ID
	 * @return void
	 */
	public void setAD_Table_ID(int m_AD_Table_ID){
		this.m_AD_Table_ID = m_AD_Table_ID;
	}
	
	/**
	 * Is Read and Write
	 * @author Yamel Senih 01/05/2012, 01:33:43
	 * @return
	 * @return boolean
	 */
	public boolean isReadWrite(){
		return m_IsReadWrite;
	}
	
	/**
	 * Set Read and Write
	 * @author Yamel Senih 01/08/2012, 10:12:35
	 * @param m_IsReadWrite
	 * @return void
	 */
	public void setIsReadWrite(boolean m_IsReadWrite){
		this.m_IsReadWrite = m_IsReadWrite;
	}
	
	/**
	 * Get Table Name
	 * @author Yamel Senih 01/05/2012, 17:09:44
	 * @return
	 * @return String
	 */
	public String getTableName(){
		return tableName;
	}
	
	/**
	 * Set Table Name
	 * @author Yamel Senih 01/08/2012, 10:13:21
	 * @param tableName
	 * @return void
	 */
	public void setTableName(String tableName){
		this.tableName = tableName;
	}
	
	/**
	 * Get Where Clause
	 * @author Yamel Senih 04/05/2012, 17:34:31
	 * @return
	 * @return String
	 */
	public String getWhereClause(){
		return whereClause;
	}
	
	/**
	 * Set Where Clause
	 * @author Yamel Senih 28/05/2012, 08:39:04
	 * @param whereClause
	 * @return void
	 */
	public void setWhereClause(String whereClause){
		this.whereClause = whereClause;
	}
	
	
	/**
	 * Get Group by clause
	 * @author Yamel Senih 14/08/2012, 22:07:49
	 * @return
	 * @return String
	 */
	public String getGroupByClause(){
		return groupByClause;
	}
	
	/**
	 * Set Group by clause
	 * @author Yamel Senih 14/08/2012, 22:08:25
	 * @param groupByClause
	 * @return void
	 */
	public void setGroupByClause(String groupByClause){
		this.groupByClause = groupByClause;
	}
	
	/**
	 * Set Order By clause
	 * @author Yamel Senih 28/05/2012, 08:50:40
	 * @param orderByClause
	 * @return void
	 */
	public void setOrderByClause(String orderByClause){
		this.orderByClause = orderByClause;
	}
	
	/**
	 * Get Order By Clause
	 * @author Yamel Senih 04/05/2012, 22:01:04
	 * @return
	 * @return String
	 */
	public String getOrderByClause(){
		return orderByClause;
	}
	
	/**
	 * Set Parent Menu ID
	 * @author Yamel Senih 07/05/2012, 11:53:55
	 * @param m_SF_ParentMenuList_ID
	 * @return void
	 */
	public void setParentMenuList_ID(int m_SF_ParentMenuList_ID){
		this.m_SF_ParentMenuList_ID = m_SF_ParentMenuList_ID;
	}
	
	/**
	 * Get PArent Menu ID
	 * @author Yamel Senih 07/05/2012, 11:52:15
	 * @return
	 * @return int
	 */
	public int getParentMenuList_ID(){
		return m_SF_ParentMenuList_ID;
	}
	
	/**
	 * Set Summary
	 * @author Yamel Senih 07/05/2012, 12:12:56
	 * @param m_IsSummary
	 * @return void
	 */
	public void setIsSummary(boolean m_IsSummary){
		this.m_IsSummary = m_IsSummary;
	}
	
	/**
	 * Is Summary
	 * @author Yamel Senih 07/05/2012, 12:13:35
	 * @return
	 * @return boolean
	 */
	public boolean getIsSummary(){
		return m_IsSummary;
	}

	/**
	 * Get Activity Type
	 * @author Yamel Senih 12/05/2012, 03:41:19
	 * @return
	 * @return String
	 */
	public String getActivityType(){
		return activitType;
	}
	
	/**
	 * Set Activity Type
	 * @author Yamel Senih 01/08/2012, 10:14:20
	 * @param activitType
	 * @return void
	 */
	public void setActivityType(String activitType){
		this.activitType = activitType;
	}
	
	/**
	 * Get Process ID
	 * @author Yamel Senih 29/07/2012, 16:09:14
	 * @return
	 * @return int
	 */
	public int getAD_Process_ID(){
		return m_AD_Process_ID;
	}
	
	/**
	 * Set Process ID
	 * @author Yamel Senih 01/08/2012, 12:09:15
	 * @param m_AD_Process_ID
	 * @return void
	 */
	public void setAD_Process_ID(int m_AD_Process_ID){
		this.m_AD_Process_ID = m_AD_Process_ID;
	}
	
	/**
	 * Get Process Class Name
	 * @author Yamel Senih 29/07/2012, 16:09:51
	 * @return
	 * @return String
	 */
	public String getProcessClassName(){
		return processClassName;
	}
	
	/**
	 * Set Process Class Name
	 * @author Yamel Senih 01/08/2012, 10:15:05
	 * @param processClassName
	 * @return void
	 */
	public void setProcessClassName(String processClassName){
		this.processClassName = processClassName;
	}
	
	/**
	 * Is Read Write Menu Level
	 * @author Yamel Senih 31/07/2012, 18:46:44
	 * @return
	 * @return String
	 */
	public String isReadWriteM(){
		return m_IsReadWriteM;
	}
	
	/**
	 * Set Read Write Menu Level
	 * @author Yamel Senih 01/08/2012, 10:16:10
	 * @param m_IsReadWriteM
	 * @return void
	 */
	public void setIsReadWriteM(String m_IsReadWriteM){
		if(m_IsReadWriteM != null){
			this.m_IsReadWrite = m_IsReadWriteM.equals("Y");
		}
		this.m_IsReadWriteM = m_IsReadWriteM;
	}
	
	/**
	 * Is Insert Record
	 * @author Yamel Senih 31/07/2012, 18:47:24
	 * @return
	 * @return boolean
	 */
	public boolean isInsertRecord(){
		return m_IsInsertRecord;
	}
	
	/**
	 * Set Insert Record
	 * @author Yamel Senih 01/08/2012, 10:17:02
	 * @param m_IsInsertRecord
	 * @return void
	 */
	public void setInsertRecord(boolean m_IsInsertRecord){
		this.m_IsInsertRecord = m_IsInsertRecord;
	}
	
	/**
	 * Set Insert Record From String Value
	 * @author Yamel Senih 06/08/2012, 02:01:22
	 * @param m_IsInsertRecord
	 * @return void
	 */
	public void setInsertRecord(String m_IsInsertRecord){
		if(m_IsInsertRecord != null){
			this.m_IsInsertRecord = m_IsInsertRecord.equals("Y");
		}
	}	
	
	/**
	 * Set Original Activity Type
	 * @author Yamel Senih 26/11/2012, 22:18:01
	 * @param originalActivityType
	 * @return void
	 */
	public void setOriginalActivityType(String originalActivityType){
		this.originalActivityType = originalActivityType;
	}
	
	/**
	 * get Original Activity Type
	 * @author Yamel Senih 26/11/2012, 22:18:32
	 * @return
	 * @return String
	 */
	public String getOriginalActivityType(){
		return originalActivityType;
	}
}