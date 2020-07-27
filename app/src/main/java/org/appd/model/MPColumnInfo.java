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
package org.appd.model;


/**
 * @author Yamel Senih
 *
 */
public class MPColumnInfo {
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 13/02/2012, 04:32:22
	 * @param AD_Column_ID
	 */
	public MPColumnInfo(int AD_Column_ID){
		this.AD_Column_ID = AD_Column_ID;
	}

	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 13/02/2012, 05:26:16
	 * @param AD_Column_ID
	 * @param ColumnName
	 * @param ColumnSQL
	 * @param DisplayType
	 * @param IsMandatory
	 * @param DefaultLogic
	 * @param IsUpdateable
	 * @param ColumnLabel
	 * @param IsTranslated
	 * @param IsEncrypted
	 * @param FieldLength
	 * @param ValueMin
	 * @param ValueMax
	 */
	public MPColumnInfo(int AD_Column_ID, 
			String ColumnName, 
			String ColumnSQL, 
			int DisplayType, 
			boolean IsMandatory, 
			String DefaultLogic, 
			boolean IsUpdateable, 
			String ColumnLabel, 
			//boolean IsTranslated,
			boolean IsEncrypted,
			int FieldLength, 
			String ValueMin, 
			String ValueMax,
			String Callout,
			boolean IsSelectionColumn,
			boolean IsIdentifier, 
			boolean IsAlwaysUpdateable){
		this.ColumnName = ColumnName; 
		this.ColumnSQL = ColumnSQL;
		this.DisplayType = DisplayType;
		this.IsMandatory = IsMandatory;
		this.DefaultLogic = DefaultLogic;
		this.IsUpdateable = IsUpdateable;
		this.ColumnLabel = ColumnLabel;
		//this.IsTranslated = IsTranslated;
		this.IsEncrypted = IsEncrypted;
		this.FieldLength = FieldLength;
		this.ValueMin = ValueMin;
		this.ValueMax = ValueMax;
		this.Callout = Callout;
		this.IsSelectionColumn = IsSelectionColumn;
		this.IsIdentifier = IsIdentifier;
		this.IsAlwaysUpdateable = IsAlwaysUpdateable;
		//	Verifica si la columna es SQL o no
		isColumnSQL = (ColumnSQL != null && ColumnSQL.length() > 0);
		//Log.i("ColumnSQL", " - " + this.ColumnSQL);
	}
	/** Column ID			*/
	public int				AD_Column_ID;
	/** Column Name			*/
	public String			ColumnName;
	/** Virtual Column 		*/
	public String       	ColumnSQL;
	/** Display Type		*/
	public int				DisplayType;
	/**	Mandatory			*/
	public boolean      	IsMandatory;
	/**	Default Value		*/
	public String       	DefaultLogic;
	/**	Updateable			*/
	public boolean      	IsUpdateable;
	/**	Label				*/
	public String       	ColumnLabel;
	/**	Translated			*/
	//public boolean		IsTranslated;
	/**	Encrypted			*/
	public boolean			IsEncrypted;
	/** Field Length		*/
	public int				FieldLength;
	/**	Min Value			*/
	public String			ValueMin;
	/**	Max Value			*/
	public String			ValueMax;
	/**	Call Out			*/
	public String			Callout;
	/**	Is Selection Column */
	public boolean			IsSelectionColumn;
	/**	Is Identifier		*/
	public boolean			IsIdentifier;
	/**	Always Updateable	*/
	public boolean			IsAlwaysUpdateable;
	/**	Is Column SQL		*/
	public boolean			isColumnSQL = false;
	
	/**
	 * Establece el valor del atributo AD_Column_ID
	 * @author Yamel Senih 13/02/2012, 04:36:36
	 * @param AD_Column_ID
	 * @return void
	 */
	public void setAD_Column_ID(int AD_Column_ID){
		this.AD_Column_ID = AD_Column_ID;
	}
	
	/**
	 * Obtiene el valor del atributo AD_Column_ID
	 * @author Yamel Senih 13/02/2012, 04:37:20
	 * @return
	 * @return int
	 */
	public int getAD_Column_ID(){
		return this.AD_Column_ID;
	}
	
	/**
	 * Establece el valor del atributo ColumnName
	 * @author Yamel Senih 13/02/2012, 04:38:08
	 * @param ColumnName
	 * @return void
	 */
	public void setColumnName(String ColumnName){
		this.ColumnName = ColumnName;
	}
	
	/**
	 * Obtiene el valor del atributo ColumnName
	 * @author Yamel Senih 13/02/2012, 04:38:41
	 * @return
	 * @return String
	 */
	public String getColumnName(){
		return this.ColumnName;
	}
	
	/**
	 * Establece el valor del atributo ColumnSQL
	 * @author Yamel Senih 13/02/2012, 04:39:41
	 * @param ColumnSQL
	 * @return void
	 */
	public void setColumnSQL(String ColumnSQL){
		this.ColumnSQL = ColumnSQL;
	}
	
	/**
	 * Obtiene el valor del atributo ColumnSQL
	 * @author Yamel Senih 13/02/2012, 04:40:17
	 * @return
	 * @return String
	 */
	public String getColumnSQL(){
		return this.ColumnSQL;
	}
	
	/**
	 * Obtiene el valor del atributo DisplayType
	 * @author Yamel Senih 13/02/2012, 04:41:12
	 * @param DisplayType
	 * @return void
	 */
	public void setDisplayType(int DisplayType){
		this.DisplayType = DisplayType;
	}
	
	/**
	 * Obtiene el valor del atributo DisplayType
	 * @author Yamel Senih 13/02/2012, 04:41:49
	 * @return
	 * @return int
	 */
	public int getDisplayType(){
		return this.DisplayType;
	}
	
	/**
	 * Esyablece el valor del atributo IsMandatory
	 * @author Yamel Senih 13/02/2012, 04:42:44
	 * @param IsMandatory
	 * @return void
	 */
	public void setIsMandatory(boolean IsMandatory){
		this.IsMandatory = IsMandatory;
	}
	
	/**
	 * Obtiene el valor del atributo IsMandatory
	 * @author Yamel Senih 13/02/2012, 04:43:47
	 * @return
	 * @return boolean
	 */
	public boolean getIsMandatory(){
		return this.IsMandatory;
	}
	
	/**
	 * Establece el valor del atributo DefaultLogic
	 * @author Yamel Senih 13/02/2012, 04:44:46
	 * @param DefaultLogic
	 * @return void
	 */
	public void setDefaultLogic(String DefaultLogic){
		this.DefaultLogic = DefaultLogic;
	}
	
	/**
	 * Obtiene el valor del atributo DefaultLogic
	 * @author Yamel Senih 13/02/2012, 04:45:09
	 * @return
	 * @return String
	 */
	public String getDefaultLogic(){
		return this.DefaultLogic;
	}
	
	/**
	 * Establece el valor del atributo IsUpdateable
	 * @author Yamel Senih 13/02/2012, 04:45:44
	 * @param IsUpdateable
	 * @return void
	 */
	public void setIsUpdateable(boolean IsUpdateable){
		this.IsUpdateable = IsUpdateable;
	}
	
	/**
	 * Obtiene el valor del atributo IsUpdateable
	 * @author Yamel Senih 13/02/2012, 04:46:14
	 * @return
	 * @return boolean
	 */
	public boolean getIsUpdateable(){
		return this.IsUpdateable;
	}
	
	/**
	 * Establece el valor del atributo ColumnLabel
	 * @author Yamel Senih 13/02/2012, 04:47:06
	 * @param ColumnLabel
	 * @return void
	 */
	public void setColumnLabel(String ColumnLabel){
		this.ColumnLabel = ColumnLabel;
	}
	
	/**
	 * Obtiene el valor del atributo ColumnLabel
	 * @author Yamel Senih 13/02/2012, 04:47:24
	 * @return
	 * @return String
	 */
	public String getColumnLabel(){
		return this.ColumnLabel;
	}
	
	/**
	 * Establece el valor del atributo IsTranslated
	 * @author Yamel Senih 13/02/2012, 04:48:03
	 * @param IsTranslated
	 * @return void
	 */
	/*public void setIsTranslated(boolean IsTranslated){
		this.IsTranslated = IsTranslated;
	}*/
	
	/**
	 * Obtiene el valor del atributo IsTranslated
	 * @author Yamel Senih 13/02/2012, 04:48:35
	 * @return
	 * @return boolean
	 */
	/*public boolean getIsTranslated(){
		return this.IsTranslated;
	}*/
	
	/**
	 * Establece el valor del atributo FieldLength
	 * @author Yamel Senih 13/02/2012, 04:49:09
	 * @param FieldLength
	 * @return void
	 */
	public void setFieldLength(int FieldLength){
		this.FieldLength = FieldLength;
	}
	
	/**
	 * Obtiene el valor del atributo FieldLength
	 * @author Yamel Senih 13/02/2012, 04:49:47
	 * @return
	 * @return int
	 */
	public int getFieldLength(){
		return this.FieldLength;
	}
	
	/**
	 * Establece el valor del atributo ValueMin
	 * @author Yamel Senih 13/02/2012, 04:50:53
	 * @param ValueMin
	 * @return void
	 */
	public void setValueMin(String ValueMin){
		this.ValueMin = ValueMin;
	}
	
	/**
	 * Obtiene el valor del atributo ValueMin
	 * @author Yamel Senih 13/02/2012, 04:51:22
	 * @return
	 * @return String
	 */
	public String getValueMin(){
		return this.ValueMin;
	}
	
	/**
	 * Establece el valor del atributo ValueMax
	 * @author Yamel Senih 13/02/2012, 04:52:20
	 * @param ValueMax
	 * @return void
	 */
	public void setValueMax(String ValueMax){
		this.ValueMax = ValueMax;
	}
	
	/**
	 * Obtiene el valor del atributo ValueMax
	 * @author Yamel Senih 13/02/2012, 04:52:40
	 * @return
	 * @return String
	 */
	public String getValueMax(){
		return this.ValueMax;
	}
	
	/**
	 * Establece el valor del atributo IsEncrypted
	 * @author Yamel Senih 13/02/2012, 05:27:13
	 * @param IsEncrypted
	 * @return void
	 */
	public void setIsEncrypted(boolean IsEncrypted){
		this.IsEncrypted = IsEncrypted;
	}
	
	/**
	 * Obtiene el valor del atributo IsEncrypted
	 * @author Yamel Senih 13/02/2012, 05:27:54
	 * @return
	 * @return boolean
	 */
	public boolean getIsEncrypted(){
		return this.IsEncrypted;
	}
	
	/**
	 * Establece el valor del atributo IsAlwaysUpdateable
	 * @author Yamel Senih 28/05/2012, 09:21:40
	 * @param IsAlwaysUpdateable
	 * @return void
	 */
	public void setIsAlwaysUpdateable(boolean IsAlwaysUpdateable){
		this.IsAlwaysUpdateable = IsAlwaysUpdateable;
	}
	
	/**
	 * Retorna el valor del atributo IsAlwaysUpdateable
	 * @author Yamel Senih 28/05/2012, 09:21:00
	 * @return
	 * @return boolean
	 */
	public boolean getIsAlwaysUpdateable(){
		return this.IsAlwaysUpdateable;
	}
	
	/**
	 * Obtiene si la columna es SQL o no
	 * @author Yamel Senih 25/07/2012, 17:34:17
	 * @return
	 * @return boolean
	 */
	public boolean isColumnSQL(){
		return isColumnSQL;
	}
	
}
