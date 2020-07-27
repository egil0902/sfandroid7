/*************************************************************************************
 * Product: SFAndroid (Sales Force Mobile)                       		             *
 * This program is free software; you can redistribute it and/or modify it    		 *
 * under the terms version 2 of the GNU General Public License as published   		 *
 * by the Free Software Foundation. This program is distributed in the hope   		 *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied 		 *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           		 *
 * See the GNU General Public License for more details.                       		 *
 * You should have received a copy of the GNU General Public License along    		 *
 * with this program; if not, write to the Free Software Foundation, Inc.,    		 *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     		 *
 * For the text or an alternative of this public license, you may reach us    		 *
 * Copyright (C) 2012-2013 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com				  		 *
 *************************************************************************************/
package org.appd.base;


import java.io.File;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * 
 * @author Yamel Senih
 *
 */
public class DB extends SQLiteOpenHelper {

	/**
	 * *** Class Constructor ***
	 * @author Yamel Senih 26/03/2012, 02:43:48
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 * @param sqlCreate
	 */
	public DB(Context context, String name, CursorFactory factory,
			int version, String sqlCreate) {
		super(context, 	name, factory, version);
		this.sqlCreate = sqlCreate;
	}
	
	/**
	 * Principal Constructor 
	 * *** Class Constructor ***
	 * @author Yamel Senih 19/08/2012, 04:45:00
	 * @param ctx
	 */
	public DB(Context ctx){
		super(ctx, Env.getDB_PathName(ctx), null, DB_VERSION);
	}
	
	private String sqlCreate;
	private String sqlUpdate;
	private SQLiteDatabase bd;
	private SQLiteStatement stm;
	public static final int READ_ONLY = 0;
	public static final int READ_WRITE = 1;
	public static final String DB_NAME = "SFAndroid";
	public static final String APP_DIRECTORY = "ERP";
	public static final String DB_DIRECTORY = "data";
	public static final int DB_VERSION = 1;
	
	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase bd) {
		if(sqlCreate != null
				&& sqlCreate.length() > 0)
			bd.execSQL(sqlCreate);
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		if(sqlUpdate != null
				&& sqlUpdate.length() > 0)
		bd.execSQL(sqlUpdate);
	}
	
	/**
	 * Was responsible for opening the database for use,
	 * Aperture mode depends on the parameter "type", if
	 * Read-only or write
	 * @author Yamel Senih 22/03/2011, 13:51:38
	 * @param type
	 * @return
	 * @return SQLiteDatabase
	 */
	public SQLiteDatabase openDB(int type){
		if(type == READ_ONLY){
			bd = getReadableDatabase();
		}else if(type == READ_WRITE){
			bd = getWritableDatabase();
		}
		return bd;
	}
	
	/**
	 * Gets the current Database
	 * @author Yamel Senih 22/03/2011, 13:54:26
	 * @return
	 * @return SQLiteDatabase
	 */
	public SQLiteDatabase getBd() {
		return bd;
	}

	/**
	 * Close DB
	 * @author Yamel Senih 03/12/2012, 01:47:20
	 * @param rs
	 * @return void
	 */
	public void closeDB(Cursor rs){
		if(rs != null && !rs.isClosed())
			rs.close();
		bd.close();
		//Log.i("closeDB", "Closed");
	}
	
	/**
	 * Check if the SQLite Db is Open
	 * @author Yamel Senih 01/05/2012, 18:01:02
	 * @return
	 * @return boolean
	 */
	public boolean isOpen(){
		boolean ok = false;
		if(bd != null){
			ok = bd.isOpen();
		}
		return ok;
	}
	
	/**
	 * Completes transaction
	 * @author Yamel Senih 26/04/2012, 11:40:38
	 * @return void
	 */
	public void endTransaction(){
		bd.endTransaction();
	}
	
	/**
	 * Starts transaction
	 * @author Yamel Senih 26/04/2012, 11:39:57
	 * @return void
	 */
	public void beginTransaction(){
		bd.beginTransaction();
	}
	
	/**
	 * Sets Transaction Successful
	 * @author Yamel Senih 25/04/2012, 19:06:07
	 * @return void
	 */
	public void setTransactionSuccessful(){
		bd.setTransactionSuccessful();
	}
	
	/**
	 * Execeute SQL statement
	 * @author Yamel Senih 22/03/2011, 13:56:40
	 * @param sql
	 * @return void
	 */
	public void executeSQL(String sql){
		bd.execSQL(sql);
	}
	
	/**
	 * Executes a SQL statement with parameters
	 * @author Yamel Senih 22/03/2011, 14:12:31
	 * @param sql
	 * @param param
	 * @return void
	 */
	public void executeSQL(String sql, Object [] param){
		bd.execSQL(sql, param);
	} 
	
	/**
	 * Insert on a table by passing the values
	 * As parameters
	 * @author Yamel Senih 22/03/2011, 20:32:52
	 * @param table
	 * @param columnaNull
	 * @param values
	 * @return void
	 */
	public void insertSQL(String table, String columnaNull, ContentValues values){
		bd.insert(table, columnaNull, values);
	}
	
	/**
	 * Make Update on a table by passing the values
	 * As parameters and Where
	 * @author Yamel Senih 22/03/2011, 20:35:39
	 * @param table
	 * @param values
	 * @param where
	 * @param argmWhere
	 * @return void
	 */
	public void updateSQL(String table, ContentValues values, String where, String [] argmWhere){
		bd.update(table, values, where, argmWhere);
	}
	
	/**
	 * Makes a Delete on a table by passing the values
	 * As parameters and Where
	 * @author Yamel Senih 22/03/2011, 20:37:22
	 * @param table
	 * @param where
	 * @param argmWhere
	 * @return void
	 */
	public void deleteSQL(String table, String where, String [] argmWhere){
		bd.delete(table, where, argmWhere);
	}
	
	/**
	 * Makes a request
	 * @author Yamel Senih 22/03/2011, 20:45:52
	 * @param sql
	 * @param valores
	 * @return
	 * @return Cursor
	 */
	public Cursor querySQL(String sql, String [] valores){
		return bd.rawQuery(sql, valores);
	}
	
	/**
	 * Make a consultation with all the necessary parameters
	 * @author Yamel Senih 22/03/2011, 20:53:31
	 * @param table
	 * @param col
	 * @param where
	 * @param argsWhere
	 * @param group
	 * @param having
	 * @param order
	 * @param limit
	 * @return
	 * @return Cursor
	 */
	public Cursor querySQL(String table, String [] col, 
			String where, String [] argsWhere, String group, 
			String having, String order, String limit){
		return bd.query(table, col, where, argsWhere, group, having, order, limit);
	}
	
	/**
	 * Compiles an instruction for reuse
	 * @author Yamel Senih 22/03/2011, 21:03:12
	 * @param sql
	 * @return
	 * @return SQLiteStatement
	 */
	public SQLiteStatement compileSQL(String sql){
		stm = bd.compileStatement(sql);
		return stm;
	}
	
	/**
	 * Gets the Instruction
	 * @author Yamel Senih 01/05/2012, 17:55:20
	 * @return
	 * @return SQLiteStatement
	 */
	public SQLiteStatement getStatement(){
		return stm;
	}

	/**
	 * Gets the full directory Database
	 * @author Yamel Senih 19/08/2012, 05:56:58
	 * @return
	 * @return String
	 */
	public static String getDB_Path(){
		return File.separator + 
				APP_DIRECTORY + 
				File.separator + 
				DB_DIRECTORY;
	}
	
	/**
	 * Gets the full path and name of the database
	 * @author Yamel Senih 19/08/2012, 05:57:49
	 * @return
	 * @return String
	 */
	public static String getDB_PathName(){
		return File.separator + 
				APP_DIRECTORY + 
				File.separator + 
				DB_DIRECTORY + 
				File.separator + 
				DB_NAME;
	}
}
