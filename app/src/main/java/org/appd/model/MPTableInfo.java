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

import java.util.ArrayList;

import org.appd.base.DB;
import org.appd.base.Env;

import android.content.Context;
import android.database.Cursor;

/**
 * @author Yamel Senih
 *
 */
public class MPTableInfo {
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 27/03/2012, 02:10:28
	 * @param ctx
	 * @param con
	 * @param AD_Table_ID
	 */
	public MPTableInfo(DB con, int AD_Table_ID){
		//m_ctx = ctx;
		loadInfoColumn(con, AD_Table_ID, null, null, null, true);
	}
	
	/**
	 * Con Clausula Where y  Order By
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 30/07/2012, 12:20:50
	 * @param con
	 * @param AD_Table_ID
	 * @param tableName
	 * @param whereClause
	 * @param orderByClause
	 * @param handConnection
	 */
	public MPTableInfo(DB con, int AD_Table_ID, String tableName, String whereClause, String orderByClause, boolean handConnection){
		//m_ctx = ctx;
		loadInfoColumn(con, AD_Table_ID, tableName, whereClause, orderByClause, handConnection);
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 09/07/2012, 09:26:28
	 * @param con
	 * @param AD_Table_ID
	 * @param handConnection
	 */
	public MPTableInfo(DB con, int AD_Table_ID, boolean handConnection){
		//m_ctx = ctx;
		loadInfoColumn(con, AD_Table_ID, null, null, null, handConnection);
	}

	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 15/06/2012, 17:53:07
	 * @param con
	 * @param tableName
	 */
	public MPTableInfo(DB con, String tableName){
		//m_ctx = ctx;
		loadInfoColumn(con, 0, tableName, null, null, true);
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 09/07/2012, 09:27:14
	 * @param con
	 * @param tableName
	 * @param handConnection
	 */
	public MPTableInfo(DB con, String tableName, boolean handConnection){
		//m_ctx = ctx;
		loadInfoColumn(con, 0, tableName, null, null, handConnection);
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 27/03/2012, 02:11:11
	 * @param ctx
	 * @param AD_Table_ID
	 */
	public MPTableInfo(Context ctx, int AD_Table_ID){
		//m_ctx = ctx;
		loadInfoColumn(null, AD_Table_ID, null, null, null, true);
	}
	
	/** Context             	*/
	//private Context		m_ctx = null;
	/** Table_ID            	*/
	private int				m_AD_Table_ID = 0;
	/** Table Name          	*/
	private String				m_TableName = null;
	/**	Is Deleteable Record	*/
	private boolean 			m_IsDeleteable = false;
	/** Columns             	*/
	private MPColumnInfo[]		m_columns = null;
	/**	Count Column SQL		*/
	private int				m_CountColumnSQL = 0;
	
	/**
	 * Carga el Meta-Dato de las Columnas
	 * @author Yamel Senih 31/03/2012, 00:09:26
	 * @param con
	 * @param AD_Table_ID
	 * @return void
	 */
	/*
	 * 31/03/2012 Falta agregar las columnas creadas hoy
	 */
	private void loadInfoColumn(DB con, int AD_Table_ID, String tableName, String whereClause, String orderByClause, boolean handConnection){
		String m_AD_Table = new String("AD_Table");
		String m_AD_Column = new String("AD_Column");
		String sql = new String("SELECT " +
				m_AD_Table + ".AD_Table_ID, " +			//	0
				m_AD_Table + ".TableName," +			//	1
				m_AD_Table + ".IsDeleteable, " +		//	2
				m_AD_Column + ".AD_Column_ID, " +		//	3
				m_AD_Column + ".ColumnName, " +			//	4
				m_AD_Column + ".ColumnSQL, " +			//	5
				m_AD_Column + ".AD_Reference_ID, " +	//	6
				m_AD_Column + ".IsMandatory, " +		//	7
				m_AD_Column + ".DefaultValue, " +		//	8
				m_AD_Column + ".IsUpdateable, " +		//	9
				m_AD_Column + ".Name, " +				//	10
				m_AD_Column + ".IsEncrypted, " +		//	11
				m_AD_Column + ".FieldLength, " +		//	12
				m_AD_Column + ".ValueMin, " +			//	13
				m_AD_Column + ".ValueMax, " +			//	14
				m_AD_Column + ".Callout, " +			//	15
				m_AD_Column + ".IsSelectionColumn, " +	//	16
				m_AD_Column + ".IsIdentifier, " +		//	17
				m_AD_Column + ".IsAlwaysUpdateable " +	//	18
				"FROM AD_Table " +
				"INNER JOIN AD_Column ON(AD_Column.AD_Table_ID = AD_Table.AD_Table_ID) " +
				"WHERE AD_Column.IsActive = 'Y' ");
		if(AD_Table_ID != 0)
			sql += "AND AD_Table.AD_Table_ID = " + AD_Table_ID;
		else
			sql += "AND AD_Table.TableName = '" + tableName + "' ";
		
		//	Where Clause
		if(whereClause != null){
			sql += " AND " + whereClause;
		}
		
		//	Order By
		if(orderByClause != null){
			sql += " ORDER BY " + orderByClause;
		}/* else {
			sql += " ORDER BY " + m_AD_Column + ".Name";
		}*/
		
		//Log.i("SQL TableInfo", "SQL:" + sql);
		
		ArrayList<MPColumnInfo> columns = new ArrayList<MPColumnInfo>();
		if(handConnection && !con.isOpen())
			con.openDB(DB.READ_ONLY);
		Cursor rs = null;
		rs = con.querySQL(sql, null);
		if(rs.moveToFirst()){
			int 		m_AD_Column_ID;
			String 		m_ColumnName; 
			String 		m_ColumnSQL;
			int 		m_AD_Reference_ID;
			boolean 	m_IsMandatory; 
			String 		m_DefaultValue; 
			boolean 	m_IsUpdateable; 
			String 		m_Name;
			boolean 	m_IsEncrypted;
			int 		m_FieldLength;
			String 		m_ValueMin; 
			String 		m_ValueMax;
			String 		m_Callout;
			boolean 	m_IsSelectionColumn;
			boolean 	m_IsIdentifier;
			boolean 	m_IsAlwaysUpdateable;
			int i = 0;
			m_AD_Table_ID 	= rs.getInt(i++);
			m_TableName 	= rs.getString(i++);
			m_IsDeleteable	= "Y".equals(rs.getString(i++));
			do{
				m_AD_Column_ID 		= rs.getInt(i++);
				m_ColumnName		= rs.getString(i++);
				m_ColumnSQL			= rs.getString(i++);
				m_AD_Reference_ID	= rs.getInt(i++);
				m_IsMandatory		= "Y".equals(rs.getString(i++)); 
				m_DefaultValue		= rs.getString(i++);
				m_IsUpdateable		= "Y".equals(rs.getString(i++)); 
				m_Name				= rs.getString(i++);
				m_IsEncrypted		= "Y".equals(rs.getString(i++));
				m_FieldLength		= rs.getInt(i++);
				m_ValueMin			= rs.getString(i++);
				m_ValueMax			= rs.getString(i++);
				m_Callout			= rs.getString(i++);
				m_IsSelectionColumn	= "Y".equals(rs.getString(i++));
				m_IsIdentifier		= "Y".equals(rs.getString(i++));
				m_IsAlwaysUpdateable= "Y".equals(rs.getString(i++));
				
				//Log.i("m_IsAlwaysUpdateable", " - " + m_ColumnName + " = " + m_IsAlwaysUpdateable);
				
				MPColumnInfo iColumn = new MPColumnInfo(
						m_AD_Column_ID, m_ColumnName, m_ColumnSQL, m_AD_Reference_ID, 
						m_IsMandatory, m_DefaultValue, m_IsUpdateable, m_Name, 
						m_IsEncrypted, m_FieldLength, m_ValueMin, m_ValueMax,
						m_Callout, m_IsSelectionColumn, m_IsIdentifier, m_IsAlwaysUpdateable
						);
				
				columns.add(iColumn);
				i = 3;
				//	Incrementa la cantidad de Columnas Virtuales
				if(iColumn.isColumnSQL)
					m_CountColumnSQL ++;
			}while(rs.moveToNext());
		}
		if(handConnection && con.isOpen())
			con.closeDB(rs);
		//	Convertir a Arreglo
		
		//Log.i("Size ", "- " + columns.size());
		
		m_columns = new MPColumnInfo[columns.size()];
		columns.toArray(m_columns);
		//Log.i("Size ", "- " + m_columns.length);
	}
	
	/**
	 * Obtiene el nombre de la tabla
	 * @author Yamel Senih 26/02/2012, 23:42:37
	 * @return
	 * @return String
	 */
	public String getTableName(){
		return m_TableName;
	}
	
	/**
	 * Verifica si los registros son eliminables
	 * @author Yamel Senih 07/06/2012, 09:27:31
	 * @return
	 * @return boolean
	 */
	public boolean isDeleteable(){
		return m_IsDeleteable;
	}
	
	/**
	 * Obtiene el id de la tabla
	 * @author Yamel Senih 26/02/2012, 23:43:36
	 * @return
	 * @return int
	 */
	public int getAD_Table_ID(){
		return m_AD_Table_ID;
	}
	
	/**
	 * Obtiene la longitud del arreglo
	 * @author Yamel Senih 27/02/2012, 00:27:09
	 * @return
	 * @return int
	 */
	public int getColumnLength(){
		if(m_columns != null){
			return m_columns.length;
		}
		return 0;
	}
	
	/**
	 * Obtiene la cantidad de columnas SQL
	 * @author Yamel Senih 26/07/2012, 11:36:52
	 * @return
	 * @return int
	 */
	public int getCountColumnSQL(){
		return m_CountColumnSQL;
	}
	
	/**
	 * Obtiene el nombre de una Columna con el indice de la misma
	 * @author Yamel Senih 27/02/2012, 00:34:43
	 * @param index
	 * @return
	 * @return String
	 */
	public String getColumnName(int index){
		if(m_columns != null || index < m_columns.length){
			return m_columns[index].ColumnName;
		}
		return null;
	}
	
	/**
	 * Obtiene las columnas separadas por coma ","
	 * @author Yamel Senih 29/03/2012, 23:58:58
	 * @return
	 * @return String
	 */
	public String getColumnsInsert(){
		StringBuffer columns = new StringBuffer();
		if(m_columns != null){
			for (int i = 0; i < m_columns.length; i++) {
				if(i > 0)
					columns.append(",");
				columns.append(m_columns[i].ColumnName);
			}	
		}
		return columns.toString();
	}
	
	
	/**
	 * Obtiene el ID de la columna a traves del nombre de la misma
	 * @author Yamel Senih 27/02/2012, 00:47:51
	 * @param columnName
	 * @return
	 * @return int
	 */
	public int getAD_Column_ID(String columnName){
		if(m_columns != null){
			for (int i = 0; i < m_columns.length; i++) {
				if(m_columns[i].ColumnName.equals(columnName))
					return m_columns[i].AD_Column_ID;
			}	
		}
		return -1;
	}
	
	/**
	 * Obtiene el indice de la columna pasandole el nombre
	 * @author Yamel Senih 27/02/2012, 00:50:45
	 * @param columnName
	 * @return
	 * @return int
	 */
	public int getColumnIndex(String columnName){
		columnName = columnName.trim();
		if(m_columns != null){
			for (int i = 0; i < m_columns.length; i++) {
				if(m_columns[i].ColumnName != null 
						&& m_columns[i].ColumnName.equals(columnName)){
					return i;
				}
			}	
		}
		return -1;
	}
	
	/**
	 * Verifica si la columna tiene una llamada asociada
	 * @author Yamel Senih 08/04/2012, 18:44:09
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isCallout(int index){
		if(m_columns != null && index >= 0){
			if(m_columns[index].Callout != null 
					&& m_columns[index].Callout.length() != 0)
				return true;
		}
		return false;
	}
	
	/**
	 * Obtiene la columna SQL a partir del nombre de la columna
	 * @author Yamel Senih 27/02/2012, 00:55:03
	 * @param columnName
	 * @return
	 * @return String
	 */
	public String getColumnSQL(String columnName){
		int index = getColumnIndex(columnName);
		if(index >= 0){
			return m_columns[index].ColumnSQL;
		}
		return null;
	}
	
	/**
	 * Obtiene la columna SQL a partir del indice de la columna
	 * @author Yamel Senih 25/07/2012, 17:55:45
	 * @param index
	 * @return
	 * @return String
	 */
	public String getColumnSQL(int index){
		if(index >= 0){
			return m_columns[index].ColumnSQL;
		}
		return null;
	}
	
	/**
	 * Obtiene el tipo de Visualizacion a partir del nombre de la columna
	 * @author Yamel Senih 27/02/2012, 01:01:40
	 * @param columnName
	 * @return
	 * @return int
	 */
	public int getDisplayType(String columnName){
		int index = getColumnIndex(columnName);
		if(index >= 0){
			return m_columns[index].DisplayType;
		}
		return 0;
	}
	
	/**
	 * Obtiene el tipo de Visualizacion a partir del indice de la columna
	 * @author Yamel Senih 30/03/2012, 22:36:55
	 * @param index
	 * @return
	 * @return int
	 */
	public int getDisplayType(int index){
		if(index >= 0){
			return m_columns[index].DisplayType;
		}
		return 0;
	}
	
	/**
	 * Obtiene informacion sobre si la columna es obligatoria
	 * @author Yamel Senih 27/02/2012, 01:02:45
	 * @param columnName
	 * @return
	 * @return boolean
	 */
	public boolean isMandatory(String columnName){
		int index = getColumnIndex(columnName);
		if(index >= 0){
			return m_columns[index].IsMandatory;
		}
		return false;
	}
	
	/**
	 * Obtiene informacion sobre si la columna es obligatoria
	 * @author Yamel Senih 08/04/2012, 20:58:03
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isMandatory(int index){
		if(index >= 0){
			return m_columns[index].IsMandatory;
		}
		return false;
	}
	
	/**
	 * Obtiene la logica predeterminada de la columna
	 * @author Yamel Senih 27/02/2012, 01:04:19
	 * @param columnName
	 * @return
	 * @return String
	 */
	public String getDefaultValue(String columnName){
		int index = getColumnIndex(columnName);
		if(index >= 0){
			return m_columns[index].DefaultLogic;
		}
		return null;
	}
	
	/**
	 * Obtiene la logica predeterminada de la columna
	 * @author Yamel Senih 30/03/2012, 23:42:02
	 * @param index
	 * @return
	 * @return String
	 */
	public String getDefaultValue(int index){
		if(index >= 0){
			return m_columns[index].DefaultLogic;
		}
		return null;
	}
	
	/**
	 * Obtiene informacion sobre si es actualizable
	 * @author Yamel Senih 27/02/2012, 01:05:24
	 * @param columnName
	 * @return
	 * @return boolean
	 */
	public boolean isUpdateable(String columnName){
		int index = getColumnIndex(columnName);
		if(index >= 0){
			return m_columns[index].IsUpdateable;
		}
		return false;
	}
	
	public boolean isUpdateable(int index){
		if(index >= 0){
			return m_columns[index].IsUpdateable;
		}
		return false;
	}
	
	/**
	 * Obtiene informacion sobre el atributo IsAlwaysUpdateable
	 * @author Yamel Senih 28/05/2012, 09:24:10
	 * @param columnName
	 * @return
	 * @return boolean
	 */
	public boolean isAlwaysUpdateable(String columnName){
		int index = getColumnIndex(columnName);
		if(index >= 0){
			return m_columns[index].IsAlwaysUpdateable;
		}
		return false;
	}
	
	/**
	 * Obtiene informacion sobre el atributo IsAlwaysUpdateable
	 * @author Yamel Senih 28/05/2012, 09:25:03
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isAlwaysUpdateable(int index){
		if(index >= 0){
			return m_columns[index].IsAlwaysUpdateable;
		}
		return false;
	}
	
	/**
	 * Obtiene la etiqueta de la columna
	 * @author Yamel Senih 27/02/2012, 01:06:27
	 * @param columnName
	 * @return
	 * @return String
	 */
	public String getName(String columnName){
		int index = getColumnIndex(columnName);
		if(index >= 0){
			return m_columns[index].ColumnLabel;
		}
		return null;
	}
	
	/**
	 * Obtiene el nombre para mostrar de la columna
	 * @author Yamel Senih 08/04/2012, 18:22:05
	 * @param index
	 * @return
	 * @return String
	 */
	public String getName(int index){
		if(index >= 0){
			return m_columns[index].ColumnLabel;
		}
		return null;
	}

	/**
	 * Obtiene informacion sobre si es traducida o no
	 * @author Yamel Senih 27/02/2012, 01:07:24
	 * @param columnName
	 * @return
	 * @return boolean
	 */
	/*public boolean getIsTranslated(String columnName){
		int index = getColumnIndex(columnName);
		if(index >= 0){
			return m_columns[index].IsTranslated;
		}
		return false;
	}*/
	
	/**
	 * Obtiene informacion sobre si es encriptada
	 * @author Yamel Senih 27/02/2012, 01:08:04
	 * @param columnName
	 * @return
	 * @return boolean
	 */
	public boolean isEncrypted(String columnName){
		int index = getColumnIndex(columnName);
		if(index >= 0){
			return m_columns[index].IsEncrypted;
		}
		return false;
	}
	
	/**
	 * Obtiene la longitud de la columna
	 * @author Yamel Senih 27/02/2012, 01:08:52
	 * @param columnName
	 * @return
	 * @return int
	 */
	public int getFieldLength(String columnName){
		int index = getColumnIndex(columnName);
		if(index >= 0){
			return m_columns[index].FieldLength;
		}
		return -1;
	}
	
	/**
	 * Obtiene el valor minimo de la columna
	 * @author Yamel Senih 27/02/2012, 01:09:37
	 * @param columName
	 * @return
	 * @return String
	 */
	public String getValueMin(String columName){
		int index = getColumnIndex(columName);
		if(index >= 0){
			return m_columns[index].ValueMin;
		}
		return null;
	}
	
	/**
	 * Obtiene si la Columna es SQL
	 * a partir del Nombre de la Columna
	 * @author Yamel Senih 25/07/2012, 17:47:04
	 * @param columnName
	 * @return
	 * @return boolean
	 */
	public boolean isColumnSQL(String columnName){
		int index = getColumnIndex(columnName);
		if(index >= 0){
			return m_columns[index].isColumnSQL;
		}
		return false;
	}
	
	/**
	 * Obtiene si la Columna es SQL
	 * a partir del indice de la Columna
	 * @author Yamel Senih 25/07/2012, 17:47:53
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isColumnSQL(int index){
		if(index >= 0){
			return m_columns[index].isColumnSQL;
		}
		return false;
	}
	
	
	/**
	 * Obtiene el valor Maximo de la columna
	 * @author Yamel Senih 27/02/2012, 01:10:09
	 * @param columnName
	 * @return
	 * @return String
	 */
	public String getValueMax(String columnName){
		int index = getColumnIndex(columnName);
		if(index >= 0){
			return m_columns[index].ValueMax;
		}
		return null;
	}

	/**
	 * Convierte los valores de un parametro value 
	 * a su valor dependiendo del tipo de columna
	 * @author Yamel Senih 27/03/2012, 02:29:02
	 * @param index
	 * @param value
	 * @return
	 * @return Object
	 */
	public Object parseType(int index, Object value){
		if(index >= 0){
			
		}
		return null;
	}
	
	/**
	 * Convierte los valores de un parametro value 
	 * a su valor dependiendo del tipo de columna
	 * @author Yamel Senih 27/03/2012, 02:30:41
	 * @param columName
	 * @param value
	 * @return
	 * @return Object
	 */
	public Object parseType(String columName, Object value){
		int index = getColumnIndex(columName);
		if(index >= 0){
			//return m_columns[index].ValueMax;
		}
		return null;
	}
	
	/**
	 * Obtiene el valor de la columna si es Virtual o no
	 * @author Yamel Senih 10/08/2012, 11:19:00
	 * @param ctx
	 * @param infoColumn
	 * @param index
	 * @return
	 * @return String
	 */
	public static String getColumnNameForSelect(Context ctx, MPTableInfo infoColumn, int index){
    	String value = "''";
    	if(!infoColumn.isColumnSQL(index)){
    		value = infoColumn.getTableName() + "." + infoColumn.getColumnName(index);
    	} else {
    		//	Parse SQL Column
    		String columnSQL = Env.parseContext(ctx, infoColumn.getColumnSQL(index), false);
    		value = columnSQL;
    	}
    	
    	return value;
    }
}
