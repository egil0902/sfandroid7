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

import org.appd.base.DB;
import org.appd.base.Env;
import org.appd.base.R;
import org.appd.util.DisplayType;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;


/**
 * @author Yamel Senih
 *
 */
public class MP {

	/** Context                 	*/
	protected Context				m_ctx;
	/** Model Info              	*/
	protected MPTableInfo			m_TableInfo = null;
	/** Original Values         	*/
	private String[]    			m_currentValues = null;
	/** New Values              	*/
	private String[]    			m_oldValues = null;

	protected DB 				con = null;  
	
	/** Record_IDs          		*/
	private Object[]       			m_IDs = new Object[] {0};
	/** Key Columns					*/
	private String[]         		m_KeyColumns = null;
	/** Create New for Multi Key 	*/
	private boolean					isNew = true;
	/**	Deleted ID					*/
	private int						m_currentId = 0;
	/**	Handle Connection			*/
	private boolean					handConnection = true;
	/**	Log Error					*/
	private String					error = null;
	
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 07/07/2012, 23:59:07
	 * @param ctx
	 * @param con_tx
	 * @param ID
	 * @param rs
	 * @param m_AD_Table_ID
	 * @param tableName
	 */
	private MP(Context ctx, DB con_tx, int ID, Cursor rs, int m_AD_Table_ID, String tableName)
	{
		
		if (ctx == null)
			throw new IllegalArgumentException ("No Context");
		m_ctx = ctx;
		if(con_tx == null)
			con = new DB(m_ctx);
		else {
			con = con_tx;
			handConnection = false;
		}
		
		if(m_AD_Table_ID == 0)
			m_AD_Table_ID = getAD_Table_ID();
		if(tableName == null)
			tableName = getTableName();
		if(m_AD_Table_ID != 0)
			m_TableInfo = new MPTableInfo(con, m_AD_Table_ID, handConnection);
		else
			m_TableInfo = new MPTableInfo(con, tableName, handConnection);
		
		if (m_TableInfo == null || m_TableInfo.getTableName() == null)
			throw new IllegalArgumentException ("Invalid MP Info - " + m_TableInfo);
		//
		int size = m_TableInfo.getColumnLength();
		m_currentValues = new String[size];
		m_oldValues = new String[size];

		if (rs != null){
			loadData(rs);
		}else{
			loadData(ID);
		}
	} 
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 28/03/2012, 00:10:43
	 * @param ctx
	 * @param ID
	 */
	public MP(Context ctx, int ID){
		this(ctx, null, ID, null, 0, null);
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 08/07/2012, 00:01:25
	 * @param ctx
	 * @param con_tx
	 * @param ID
	 */
	public MP(Context ctx, DB con_tx, int ID){
		this(ctx, con_tx, ID, null, 0, null);
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 08/07/2012, 00:02:06
	 * @param ctx
	 * @param con_tx
	 * @param tableName
	 * @param ID
	 */
	public MP(Context ctx, DB con_tx, String tableName, int ID){
		this(ctx, con_tx, ID, null, 0, tableName);
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 03/05/2012, 02:02:36
	 * @param ctx
	 * @param ID
	 * @param m_AD_Table_ID
	 */
	public MP(Context ctx, int ID, int m_AD_Table_ID){
		this(ctx, null, ID, null, m_AD_Table_ID, null);
	}
	
	public MP(Context ctx, int ID, String tableName){
		this(ctx, null, ID, null, 0, tableName);
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 28/03/2012, 00:11:30
	 * @param ctx
	 * @param rs
	 */
	public MP(Context ctx, Cursor rs){
		this(ctx, null, 0, rs, 0, null);
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 03/05/2012, 02:01:47
	 * @param ctx
	 * @param rs
	 * @param m_AD_Table_ID
	 */
	public MP(Context ctx, Cursor rs, int m_AD_Table_ID){
		this(ctx, null, 0, rs, m_AD_Table_ID, null);
	}
	
	/**
	 * Carga los datos a partir de un Cursor
	 * @author Yamel Senih 27/03/2012, 01:30:50
	 * @param ID
	 * @return void
	 */
	public void loadData(Cursor rs){
		/*if(ID > 0){
			m_IDs = new Object[] {new Integer(ID)};
			m_KeyColumns = new String[] {column_info.getTableName() + "_ID"};	
		} else {
			
		}*/
	}
	
	/**
	 * Carga los datos a partir de un ID
	 * @author Yamel Senih 27/03/2012, 01:32:45
	 * @param ID
	 * @return void
	 */
	public boolean loadData(int ID){
		boolean ok = false;
		//Log.i("MP.loadData", String.valueOf(ID));
		if(ID > 0){
			m_IDs = new Object[] {ID};
			m_currentId = ID;
			m_KeyColumns = new String[] {m_TableInfo.getTableName() + "_ID"};
			ok = loadDataQuery(ID);
		} else {
			isNew = true;
		}
		return ok;
	}
	
	/**
	 * Retorna el ID del Registro
	 * @author Yamel Senih 12/05/2012, 06:03:15
	 * @return
	 * @return int
	 */
	public int getID(){
		return m_currentId;
	}
	
	/**
	 * Copia los valores a un arreglo temporal,
	 * si el parametro es true elimina los valores viejos
	 * @author Yamel Senih 05/05/2012, 04:50:51
	 * @param deleteOld
	 * @return void
	 */
	public void copyValues(boolean deleteOld){
		m_oldValues = m_currentValues;
		if(deleteOld){
			isNew = true;
			m_currentValues = new String[m_TableInfo.getColumnLength()];
		}
	}
	
	/**
	 * Devuelve los cambios a la ultima copia
	 * @author Yamel Senih 05/05/2012, 04:46:14
	 * @return void
	 */
	public void backCopy(){
		this.isNew = false;
		m_currentValues = m_oldValues;
	}
	
	/**
	 * Obtiene el velor de Is New, indicando si 
	 * se esta insertando o actualizando un registro
	 * @author Yamel Senih 05/05/2012, 05:00:52
	 * @return
	 * @return boolean
	 */
	public boolean isNew(){
		return isNew;
	}
	
	/**
	 * Realiza una consulta del tipo SQL para cargar los datos del MP
	 * @author Yamel Senih 27/03/2012, 01:34:39
	 * @param ID
	 * @return boolean
	 */
	private boolean loadDataQuery(int ID){
		boolean ok = false;
		StringBuffer sql = new StringBuffer("SELECT ");
		for(int i = 0; i < m_TableInfo.getColumnLength(); i++){
			if (i != 0)
				sql.append(",");
			if(!m_TableInfo.isColumnSQL(i))
				sql.append(m_TableInfo.getColumnName(i));
			else
				sql.append(Env.parseContext(m_ctx, m_TableInfo.getColumnSQL(i), false));
		}
		sql.append(" FROM ");
		sql.append(m_TableInfo.getTableName());
		sql.append(" WHERE ");
		sql.append(get_WhereClause(true));
		
		//Log.i("MP.loadDataQuery", sql.toString());
		
		//	info
		//Log.i("MP.loadDataQuery(ID).sql", sql.toString());
		//
		Cursor rs = null;
		//	Load Connection
		loadConnection(DB.READ_ONLY);
		
		rs = con.querySQL(sql.toString(), null);
		if(rs.moveToFirst()){
			for(int i = 0; i < m_TableInfo.getColumnLength(); i++){
				m_currentValues[i] = rs.getString(i);
				//Log.i("MP.loadDataQuery", "*** " + m_oldValues[i]);
			}
			isNew = false;
			ok = true;
		} else
			ok = false;
		//	Close Connection
		closeConnection();
		return ok;
	}
	
	/**
	 * Establece el ID del Registro Principal,
	 * marca el objeto como modo actualizar para ejecutar el update
	 * @author Yamel Senih 23/05/2012, 03:38:12
	 * @param ID
	 * @return void
	 */
	public void setIDUpdate(int ID){
		if(ID > 0){
			m_IDs = new Object[] {ID};
			m_currentId = ID;
			m_KeyColumns = new String[] {m_TableInfo.getTableName() + "_ID"};
			isNew = false;
		}
	}
	
	/**
	 * 	Create Single/Multi Key Where Clause
	 * 	@param withValues if true uses actual values otherwise ?
	 * 	@return where clause
	 */
	protected String get_WhereClause (boolean withValues)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < m_IDs.length; i++)
		{
			if (i != 0)
				sb.append(" AND ");
			sb.append(m_KeyColumns[i]).append("=");
			if (withValues)
			{
				if (m_KeyColumns[i].endsWith("_ID"))
					sb.append(m_IDs[i]);
				else
					sb.append("'").append(m_IDs[i]).append("'");
			}
			else
				sb.append("?");
		}
		return sb.toString();
	}	//	getWhereClause
	
	/**
	 * Obtiene los Values de la clausula Where
	 * @author Yamel Senih 05/05/2012, 02:44:17
	 * @return
	 * @return String
	 */
	protected String [] get_WhereClauseValues() {
		String[] values = new String [m_IDs.length];
		for (int i = 0; i < m_IDs.length; i++) {
			values[i] = String.valueOf(m_IDs[i]);
		}
		return values;
	}	//	getWhereClauseValues
	
	/**
	 * Establece un valor
	 * @author Yamel Senih 28/03/2012, 00:22:52
	 * @param columnName
	 * @param value
	 * @return
	 * @return boolean
	 */
	public final boolean set_Value(String columnName, Object value){
		int index = m_TableInfo.getColumnIndex(columnName);
		if(index >= 0){
			if(value != null)
				m_currentValues[index] = String.valueOf(value);
			else
				m_currentValues[index] = null;
			return true;
		} else 
			Log.i("set_Value", "columnName = " + columnName + ", value = " + value + " Not Found");
		return false;
	}
	
	/**
	 * Establece un valor
	 * @author Yamel Senih 08/04/2012, 21:05:26
	 * @param index
	 * @param value
	 * @return
	 * @return boolean
	 */
	public final boolean set_Value(int index, Object value){
		//Log.i("set_Value", "index = " + index + ", value = " + value);
		if(index >= 0){
			if(value != null)
				m_currentValues[index] = String.valueOf(value);
			else
				m_currentValues[index] = null;
			return true;
		} else 
			Log.i("set_Value", "value = " + value + " Column Not Found");
		return false;
	}
	
	/**
	 * Obtiene los valores en Formato String
	 * @author Yamel Senih 30/03/2012, 19:03:40
	 * @return
	 * @return String[]
	 */
	public String [] getValuesAsString(){
		String []values = null;
		if(m_currentValues != null){
			values = new String[m_currentValues.length];
			for (int i = 0; i < m_currentValues.length; i++) {
				values[i] = m_currentValues[i];
			}
		}
		return values;
	}
	
	/**
	 * Obtiene un valor
	 * @author Yamel Senih 28/03/2012, 00:22:56
	 * @param columnName
	 * @return
	 * @return Object
	 */
	public final Object get_Value(String columnName){
		int index = m_TableInfo.getColumnIndex(columnName);
		//Log.i("get_Value", "columnName = " + columnName);
		if(index >= 0){
			//Log.i("get_Value", "Value = " + m_oldValues[index]);
			return m_currentValues[index];
		}
		return null;
	}
	
	/**
	 * Obtiene un valor con el indice
	 * @author Yamel Senih 03/05/2012, 18:32:38
	 * @param index
	 * @return
	 * @return Object
	 */
	public final Object get_Value(int index){
		if(index >= 0){
			return m_currentValues[index];
		}
		return null;
	}
	
	/**
	 * Obtiene un valor transformado a Entero
	 * @author Yamel Senih 30/03/2012, 00:26:31
	 * @param columnName
	 * @return
	 * @return int
	 */
	public final int get_ValueAsInt(String columnName){
		return get_ValueAsInt(columnName, m_currentValues);
	}
	
	/**
	 * Obtiene el valor anterior de un registro 
	 * miestras se esta modificando
	 * @author Yamel Senih 20/08/2012, 23:23:17
	 * @param columnName
	 * @return
	 * @return int
	 */
	public final int get_OldValueAsInt(String columnName){
		return get_ValueAsInt(columnName, m_oldValues);
	}
	
	/**
	 * Obtiene un valor entero de un arreglo
	 * @author Yamel Senih 20/08/2012, 23:22:35
	 * @param columnName
	 * @param m_arrayValues
	 * @return
	 * @return int
	 */
	private int get_ValueAsInt(String columnName, String [] m_arrayValues){
		int index = m_TableInfo.getColumnIndex(columnName);
		int displayType = m_TableInfo.getDisplayType(index);
		if(index >= 0){
			if(m_arrayValues[index] != null
					&& m_arrayValues[index].length() > 0){
				if(DisplayType.isNumeric(displayType) 
						|| DisplayType.isID(displayType)){
					return Integer.parseInt(m_arrayValues[index]);
				} else {
					return 0;
				}
			}
		}
		return 0;
	}
	
	/**
	 * Obtiene un valor en formato Entero
	 * @author Yamel Senih 20/05/2012, 02:13:50
	 * @param index
	 * @return
	 * @return int
	 */
	public final int get_ValueAsInt(int index){
		int displayType = m_TableInfo.getDisplayType(index);
		if(index >= 0){//Msg.toastMsg(m_ctx, "aja ");
			if(m_currentValues[index] != null
					&& m_currentValues[index].length() > 0){
				if(DisplayType.isNumeric(displayType) 
						|| DisplayType.isID(displayType)){
					String value = (String) m_currentValues[index];
					if(value != null && value.length() > 0){
						try{
							return Integer.parseInt(value);
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				} else {
					return 0;
				}	
			}
		}
		return 0;
	}
	
	/**
	 * Obtiene un valor transformado a Booleano
	 * @author Yamel Senih 30/03/2012, 00:28:37
	 * @param columnName
	 * @return
	 * @return boolean
	 */
	public final boolean get_ValueAsBoolean(String columnName){
		int index = m_TableInfo.getColumnIndex(columnName);
		//Log.i("get_Value", "columnName = " + columnName);
		if(index >= 0){
			//Log.i("get_ValueAsInt", "Value = " + m_oldValues[index]);
			if(DisplayType.isText(m_TableInfo.getDisplayType(columnName))){
				return "Y".equals(((String)m_currentValues[index]));
			} else {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Guarda un registro en la base de datos
	 * @author Yamel Senih 29/03/2012, 23:45:04
	 * @return
	 * @return boolean
	 */
	public boolean save(){
		try{
			saveEx();
			error = null;
			return true;
		}catch (Exception e) {
			error = e.getMessage();
			Log.e("MP.save", e.getMessage());
		}
		return false;
	}
	
	/**
	 * Guarda un registro en la base de datos
	 * @author Yamel Senih 08/04/2012, 20:22:37
	 * @throws Exception
	 * @return void
	 */
	public void saveEx() throws Exception{
		loadConnection(DB.READ_WRITE);
		boolean fine = beforeSave(isNew);
		if(!fine)
			throw new Exception("saveEx.beforeSave");
		if(isNew)
			saveNew();
		else
			saveUpdate();
		
		fine = afterSave(isNew);
		
		if(!fine)
			throw new Exception("saveEx.afterSave");
	}
	
	/**
	 * Elimina un registro y retorna false si existe un error
	 * @author Yamel Senih 05/05/2012, 02:06:03
	 * @return
	 * @return boolean
	 */
	public boolean delete(){
		try{
			deleteEx();
			error = null;
			return true;
		}catch (Exception e) {
			error = e.getMessage();
			Log.e("MP.delete", " Error ", e);
		}
		return false;
	}
	
	/**
	 * Elimina un registro retornando la excepcion
	 * @author Yamel Senih 05/05/2012, 02:04:13
	 * @throws Exception
	 * @return void
	 */
	public void deleteEx() throws Exception{
		
		try{
			loadConnection(DB.READ_WRITE);
			//	Before Delete
			boolean fine = beforeDelete();
			if(!fine)
				throw new Exception("delete.beforeDelete");
			
			con.deleteSQL(m_TableInfo.getTableName(), get_WhereClause(false), get_WhereClauseValues());
			if(handConnection)
				con.setTransactionSuccessful();
			//	
			clear();
			//Log.i("MP.deleteEx", (String)m_oldValues[0]);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			closeConnection();
		}
	}
	
	/**
	 * Limpia el arreglo de datos
	 * @author Yamel Senih 05/05/2012, 03:26:13
	 * @return void
	 */
	public void clear(){
		isNew = true;
		m_currentId = 0;
		int size = m_TableInfo.getColumnLength();
		m_currentValues = new String[size];
		m_oldValues = new String[size];
	}
	
	
	/**
	 * uarda un nuevo registro
	 * @author Yamel Senih 30/03/2012, 22:10:05
	 * @return
	 * @return boolean
	 * @throws Exception 
	 */
	private boolean saveNew() throws Exception{
		StringBuffer columns = new StringBuffer();
		StringBuffer sym = new StringBuffer();
		String [] values = new String[m_TableInfo.getColumnLength() - m_TableInfo.getCountColumnSQL()];
		
		try{
			for (int i = 0; i < m_TableInfo.getColumnLength(); i++) {
				//	Si la Columna no es SQL entonces la agrega al Query
				if(!m_TableInfo.isColumnSQL(i)){
					if(i > 0){
						columns.append(",");
						sym.append(",");
					}
					columns.append(m_TableInfo.getColumnName(i));
					sym.append("?");
					
					//Log.i("Index Test", " " + columns.toString());
					String value = get_ValueForInsert(i);
					if(m_TableInfo.isMandatory(i) 
							&& value == null)
						throw new Exception(m_ctx.getResources().getString(R.string.MustFillField) + 
								" \"" + m_TableInfo.getColumnName(i) + "\"");
					values[i] = value;
					//Log.i(m_TableInfo.getColumnName(i), "-- " + values[i] + " *Mandatory=" + m_TableInfo.isMandatory(i));
					//Prueba.append(values[i]);
				}
			}
			//Log.i("sss ", Prueba.toString());
			String sql = "INSERT INTO " + 
					m_TableInfo.getTableName() + 
					" (" + 
					columns.toString() + 
					") VALUES(" + 
					sym.toString() + 
					")";
			//Log.i("Values", sql.toString());
			con.executeSQL(sql, values);
			if(handConnection)
				con.setTransactionSuccessful();
			
			//	Load Values
			m_IDs = new Object[] {m_currentId};
			m_KeyColumns = new String[] {m_TableInfo.getTableName() + "_ID"};
			isNew = false;
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection();
		}
		return false;
	}
	
	/**
	 * Actualiza un registro
	 * @author Yamel Senih 04/05/2012, 21:11:58
	 * @return
	 * @throws Exception
	 * @return boolean
	 */
	private boolean saveUpdate() throws Exception{
		StringBuffer columns = new StringBuffer();
		//boolean maxZero = false;
		String [] values = new String[m_TableInfo.getColumnLength() - m_TableInfo.getCountColumnSQL() - 1];
		int indexArray = 0;
		try{
			for (int i = 0; i < m_TableInfo.getColumnLength(); i++) {
				String columnName = m_TableInfo.getColumnName(i);
				if(!columnName.equals(m_TableInfo.getTableName() + "_ID")){
					//	Si la Columna no es SQL entonces la agrega al Query
					if(!m_TableInfo.isColumnSQL(i)){
						if(indexArray > 0){
							columns.append(",");
						}
						columns.append(m_TableInfo.getColumnName(i) + "=" + "?");
						//sym.append("?");
						values[indexArray] = (String)get_ValueForUpdate(i);
						indexArray++;
						//Log.i("values", values[indexArray]);
					}
				}
			}
			//	
			String sql = "UPDATE " + 
					m_TableInfo.getTableName() + 
					" SET " + 
					columns.toString() +
					" WHERE "  + get_WhereClause(true);
			
			//Log.i("MP.SaveUpdate.SQLUpdate", sql.toString());
			
			con.executeSQL(sql, values);
			if(handConnection)
				con.setTransactionSuccessful();	
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection();
		}
		return false;
	}
	
	/**
	 * Obtiene un valor para el Insert
	 * @author Yamel Senih 04/05/2012, 21:13:26
	 * @param index
	 * @return
	 * @throws Exception
	 * @return Object
	 */
	/*
	 * 31/03/2012 Falta establecer el formato de valores por defetcto
	 */
	public final String get_ValueForInsert(int index) throws Exception{
		String columnName = m_TableInfo.getColumnName(index); 
		//int displayType = m_TableInfo.getDisplayType(index);
		String defaultValue = m_TableInfo.getDefaultValue(index);
		if(index >= 0){
			String value = m_currentValues[index]; 
			if(columnName.equals(m_TableInfo.getTableName() + "_ID")){
				m_currentId = nextSequence(m_TableInfo.getAD_Table_ID());
				//	Set ID
				set_Value(index, m_currentId);
				return String.valueOf(m_currentId);
			} else {
				if(value != null && value.length() > 0){
					//Log.i("value", "value " + value);
					return value;
				}
				else if(defaultValue != null && defaultValue.length() > 0){
					//Log.i("value", "value " + value);
					return defaultValue;
				}
				else
					return null;
			}
		}
		return null;
	}

	/**
	 * Obtiene los valores para un update
	 * @author Yamel Senih 04/05/2012, 21:10:48
	 * @param index
	 * @return
	 * @throws Exception
	 * @return String
	 */
	public final String get_ValueForUpdate(int index) throws Exception{
		int displayType = m_TableInfo.getDisplayType(index);
		if(index >= 0){
			String value = m_currentValues[index];
			//Log.i("MP.get_ValueForInssert", (String) value);
			if(displayType == DisplayType.YES_NO){
				return value;
			} else {
				if(value != null && value.length() > 0)
					return value;
				else
					return null;
			}
		}
		return null;
	}
	
	/**
	 * Obtiene la siguiente secuencia
	 * @author Yamel Senih 08/04/2012, 23:06:40
	 * @param name
	 * @return
	 * @return int
	 * @throws Exception 
	 */
	private int nextSequence(int m_AD_Table_ID) throws Exception {
		int m_XX_MB_Sequence_ID = 0;
		int cNext = 0;
		//	
		String sql = new String("SELECT s.XX_MB_Sequence_ID, s.CurrentNext " +
				"FROM XX_MB_Sequence s " +
				"WHERE s.AD_Table_ID = " + m_AD_Table_ID);
		//	Cursor
		Cursor rs = con.querySQL(sql, null);
		if(rs.moveToFirst()){
			m_XX_MB_Sequence_ID = rs.getInt(0);
			cNext = rs.getInt(1);
			//	Log
			//Log.i("ID", String.valueOf(m_XX_MB_Sequence_ID));
			//Log.i("cNext", String.valueOf(cNext));
			//	Query Update
			String sqlU = new String("UPDATE XX_MB_Sequence SET CurrentNext = " + 
					++cNext + 
					" WHERE XX_MB_Sequence_ID = " + m_XX_MB_Sequence_ID);
			//	Update Sequence
			con.executeSQL(sqlU);
			rs.close();
		} else
			throw new Exception("No Sequence");
		
		return cNext;
	}
	
	/**
	 * Obtiene el Modelo de la Tabla
	 * @author Yamel Senih 08/04/2012, 17:15:32
	 * @return
	 * @return MPTableInfo
	 */
	public MPTableInfo getTableInfo(){
		return m_TableInfo;
	}
	
	/**
	 * Disparador que se ejecuta antes de Guardar un Registro
	 * @author Yamel Senih 27/03/2012, 23:10:35
	 * @param isNew
	 * @return
	 * @return boolean
	 */
	protected boolean beforeSave(boolean isNew){
		return true;
	}
	
	/**
	 * Disparador que se ejecuta despues de Guardar un Registro
	 * @author Yamel Senih 29/03/2012, 23:40:55
	 * @param isNew
	 * @return
	 * @return boolean
	 */
	protected boolean afterSave(boolean isNew){
		return true;
	}
	
	/**
	 * Disparador que se ejecuta antes de Eliminar un Registro
	 * @author Yamel Senih 27/03/2012, 23:10:40
	 * @return
	 * @return boolean
	 */
	protected boolean beforeDelete(){
		return true;
	}
	
	/**
	 * Disparador que se ejecuta despues de Eliminar un Registro 
	 * @author Yamel Senih 29/03/2012, 23:42:51
	 * @return
	 * @return boolean
	 */
	protected boolean afterDelete(){
		return true;
	}
	
	/**
	 *  Initialize and return PO_Info
	 *  @param ctxInto context
	 *  @return int
	 */
	protected int getAD_Table_ID(){
		return 0;
	}
	
	/**
	 * Obtiene el Nombre de la Tabla
	 * @author Yamel Senih 15/06/2012, 18:01:39
	 * @return
	 * @return String
	 */
	protected String getTableName(){
		return null;
	}
	
	/**
	 * Crea una Conexion a la BD
	 * @author Yamel Senih 11/05/2012, 17:57:15
	 * @return void
	 */
	public void loadConnection(int type){
		if(!con.isOpen()){
			if(handConnection){
				//Log.i("loadConnection", "handConnection");
				con.openDB(type);
				if(type == DB.READ_WRITE)
					con.beginTransaction();
			}
		}
    }
	
	/**
	 * Cierra la conexion con la BD si el parámetro 
	 * maneja conexion esta en true
	 * @author Yamel Senih 10/07/2012, 18:13:44
	 * @return void
	 */
	public void closeConnection(){
		if(con.isOpen()){
			if(handConnection){
				//Log.i("closeConnection", "handConnection");
				if(con.getBd().inTransaction())
					con.endTransaction();
				con.close();
			}
		}
    }
	
	/**
	 * Obtiene el error generado al momento de guardar
	 * o actualizar
	 * @author Yamel Senih 16/07/2012, 20:35:15
	 * @return
	 * @return String
	 */
	public String getError(){
		return error;
	}
	
}
