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

import java.util.LinkedList;

import org.appd.base.DB;
import org.appd.base.Env;
import org.appd.base.R;
import org.appd.interfaces.I_ClassLoaderListRecord;
import org.appd.model.MPTableInfo;
import org.appd.util.AdapterRecordItem;
import org.appd.util.Att_ColumnRecord;
import org.appd.util.DisplayFilterOption;
import org.appd.util.DisplayMenuItem;
import org.appd.util.DisplayRecordItem;
import org.appd.util.DisplayType;
import org.appd.util.LoadDataSpinner;
import org.appd.util.Msg;
import org.appd.view.custom.Cust_ButtonPaymentRule;
import org.appd.view.custom.Cust_DateBox;
import org.appd.view.custom.Cust_Spinner;

import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 
 * @author Yamel Senih
 *
 */
public class MV_ListRecord extends Activity {
	
	private LinkedList<DisplayRecordItem> data = new LinkedList<DisplayRecordItem>();
	private ListView recordList;
	/**	Search Fields		*/
	private Spinner sp_Filter;
	private EditText et_Search;
	private Cust_Spinner csp_Reference;
	private Cust_DateBox cdb_Date;
	private Cust_ButtonPaymentRule cpr_PaymentRule;
	private CheckBox ch_Boolean;
	/**	Record No			*/
	private long recordNo = 0;
	private TextView tv_RecordNo;
	private ImageButton iBut_Search;
	private DisplayFilterOption dfo = null;
	private DB con = null;
	private Att_ColumnRecord []identifierColumns;
	private StringBuffer joins = new StringBuffer();
	private String where = null;
	private String [] param = null;
	//private boolean loaded = false;
	
	/**	Info Table and Column	*/
	private MPTableInfo infoIdentifier = null;
	
	/**	Attribute Form			*/
	private DisplayMenuItem att_Form = null;
	
	private I_ClassLoaderListRecord classLoad;
	
	
	/** Called when the activity is first created. */
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	super.setContentView(R.layout.lv_records);
    	
    	Bundle extras = getIntent().getExtras();
    	att_Form = (DisplayMenuItem) extras.getParcelable("Item");
  
    	//	Set Name Activity
    	setTitle(att_Form.getName());
    	//Msg.toastMsg(this, " Att_Form P " + att_Form.getFrom());
    	//	Record No
    	tv_RecordNo = (TextView) findViewById(R.id.tv_RecordNo);
    	
    	//	Views
    	sp_Filter = (Spinner) findViewById(R.id.sp_Filter);
    	
    	et_Search = (EditText) findViewById(R.id.et_Search);
    	iBut_Search = (ImageButton) findViewById(R.id.iBut_Search);
    	csp_Reference = (Cust_Spinner) findViewById(R.id.csp_Reference);
    	csp_Reference.setConnection(con);
    	cdb_Date = (Cust_DateBox) findViewById(R.id.cdb_Date);
    	
    	cdb_Date.setDate(Env.getContext(this, "#Date"));
    	
    	cpr_PaymentRule = (Cust_ButtonPaymentRule) findViewById(R.id.cpr_PaymentRule);
    	ch_Boolean = (CheckBox) findViewById(R.id.ch_IsActive);
    	
    	recordList=(ListView) findViewById(R.id.ls_Records);
    	
    	loadConnection();
    	//	Load Filters
    	loadFilterOptions();
    	//	Load Identifier
    	loadIdentifierColumn();
    	
    	//	Load Records
    	//loadRecords();
    	//	Close Connection
    	//closeConnection();
    	iBut_Search.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(et_Search.getText().toString() != null 
    					&& et_Search.getText().toString().length() > 0){
    				//where = new String("WHERE " + att_Form.getTableName() + "." + dfo.getColumnName() + " LIKE ? ");
    				
    				where = new String("WHERE ");
	    			
	    			String column = covertWhere(dfo);
	    			
	    			where += column + " LIKE ? ";
    				
    				param = new String[]{"%" + et_Search.getText().toString().trim() + "%"};
    			} else {
    				where = null;
    				param = null;
    			}
				loadRecords();
			}
		});
    	
    	//	Event Click
    	sp_Filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> a, View v,
					int position, long i) {
				dfo = (DisplayFilterOption) sp_Filter.getItemAtPosition(position);
				where = null;
				param = null;
		    	// Dependiendo del Tipo de Dato muestra un filtro distinto
		    	loadDataType();
		    	loadRecords();
			}

			@Override
			public void onNothingSelected(AdapterView<?> a) {
				
			}
    		
    	});
    	
    	//	Event Click
    	csp_Reference.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> a, View v,
					int position, long i) {
				int id = csp_Reference.getID(position);
				if(id != 0){
					/*where = new String("WHERE " + 
								att_Form.getTableName() + 
								"." + 
								dfo.getColumnName() + 
								"=" + 
								id);*/
					where = new String("WHERE ");
	    			
	    			String column = covertWhere(dfo);
	    			
	    			where += column + "=" + id;
	    			
				} else
					where = null;
				loadRecords();
			}

			@Override
			public void onNothingSelected(AdapterView<?> a) {
				
			}
    		
    	});
    	
    	ch_Boolean.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				/*where = new String("WHERE " + 
							att_Form.getTableName() + 
							"." + 
							dfo.getColumnName() + 
							"=" + 
							(isChecked? "'Y'": "'N'"));
				*/
				where = new String("WHERE ");
    			
    			String column = covertWhere(dfo);
    			
    			where += column + "=" + (isChecked? "'Y'": "'N'");
    			
				loadRecords();
			}
		});
    	
    	cdb_Date.setOnDateSetListener(new OnDateSetListener() {
    		public void onDateSet(DatePicker view, int year, 
    				int monthOfYear, int dayOfMonth) {
    			cdb_Date.onDateSet(view, year, monthOfYear, dayOfMonth);
    			where = "WHERE ";
    			
    			String column = covertWhere(dfo);
    			where += "DATE(" + column + ") " +
    					"= DATE('" + cdb_Date.getDate("yyyy-MM-dd") + "')";
    			//where += "DATE(" + column + ") ='" + cdb_Date.getDate() + "'";
    			
    			loadRecords();
    		}
    	});

    	
    	recordList.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> padre, View v, int position,
					long id) {
				
				if(att_Form.getAction().equals("F")){
					if(att_Form.getActivityType().equals("S")
							|| att_Form.getActivityType().equals("L")){
						//	Standard
						loadActivity((DisplayRecordItem) padre.getItemAtPosition(position));
					} else if(att_Form.getActivityType().equals("M")){
						//	Menu
						loadClassMenu(v,(DisplayRecordItem) padre.getItemAtPosition(position));
					} else if(att_Form.getActivityType().equals("F")){
						//	Finder
						selectedRecord((DisplayRecordItem) padre.getItemAtPosition(position));
					}
				} else if(att_Form.getAction().equals("R")){
					//
				}
			}
		});    	
    }
    
	/**
	 * Si la Columna es SQL selecciona el sql para 
	 * hacer el filtro, en caso contrario coloca 
	 * el nmombre de la columna
	 * @author Yamel Senih 17/07/2012, 11:05:40
	 * @param dfo
	 * @return
	 * @return String
	 */
	private String covertWhere(DisplayFilterOption dfo){
		StringBuffer sql = new StringBuffer(" ");
		//	Verifica
		if(dfo.isColumnSQL()){
			//Log.i("dfo.getColumnSQL()", "" + dfo.getColumnSQL());
			String sqlContext = Env.parseContext(this, dfo.getColumnSQL(), false);
			sql.append(sqlContext);
			//Log.i("sqlContext", "" + sqlContext);
		} else {
			sql.append(att_Form.getTableName());
			sql.append(".");
			sql.append(dfo.getColumnName());
		}

		return sql.toString();
	}
	
	/**
	 * Establece la Clausula where
	 * @author Yamel Senih 17/07/2012, 23:18:35
	 * @return void
	 */
	private void setWhere(){
		int m_AD_Reference_ID = dfo.getAD_Reference_ID();
		if(DisplayType.isID(m_AD_Reference_ID)){
			if(!dfo.getColumnName().replaceAll("_ID", "").
					equals(csp_Reference.getTableName())){
				
				int id = csp_Reference.getID();
				if(id != 0){
					where = new String("WHERE ");
	    			
	    			String column = covertWhere(dfo);
	    			
	    			where += column + "=" + id;	
				}
			}
			
		} else if(DisplayType.isDate(m_AD_Reference_ID)){
			where = "WHERE ";
			
			String column = covertWhere(dfo);
			
			where += "DATE(" + column + ") " +
					"= DATE('" + cdb_Date.getDate("yyyy-MM-dd") + "')";
		} else if(DisplayType.isBoolean(m_AD_Reference_ID)){
			
			where = new String("WHERE ");
			
			String column = covertWhere(dfo);
			
			where += column + "=" + (ch_Boolean.isChecked()? "'Y'": "'N'");
			
		} else {
			if(et_Search.getText().toString() != null 
					&& et_Search.getText().toString().length() > 0){
				where = new String("WHERE " + att_Form.getTableName() + "." + dfo.getColumnName() + " LIKE ? ");
				param = new String[]{"%" + et_Search.getText().toString() + "%"};
			}
		}
	}
	
	/**
	 * Carga los tipos de foltros de busqueda dependiendo del tipo de referencia
	 * @author Yamel Senih 21/05/2012, 01:41:54
	 * @return
	 * @return boolean
	 */
	private boolean loadDataType(){
		int m_AD_Reference_ID = dfo.getAD_Reference_ID();
		if(DisplayType.isID(m_AD_Reference_ID)){
			et_Search.setVisibility(View.GONE);
			iBut_Search.setVisibility(View.GONE);
			cdb_Date.setVisibility(View.GONE);
			cpr_PaymentRule.setVisibility(View.GONE);
			ch_Boolean.setVisibility(View.GONE);
			csp_Reference.setVisibility(View.VISIBLE);
			
			if(!dfo.getColumnName().replaceAll("_ID", "").
					equals(csp_Reference.getTableName())){
				loadConnection();
				csp_Reference.setTableName(dfo.getColumnName().replaceAll("_ID", ""));
				String identifier = LoadDataSpinner.getIdentifierFromTable(
						con, dfo.getColumnName().replaceAll("_ID", ""));
				//Log.i("identifier", identifier);
		    	csp_Reference.setIdentifierName(identifier);
		    	csp_Reference.setFirstSpace(true);
		    	csp_Reference.load(true);
			}
	    	//csp_Reference.setWhereClause("C_DocType.DocBaseType IN('SOO')");
			
		} else if(DisplayType.isDate(m_AD_Reference_ID)){
			et_Search.setVisibility(View.GONE);
			iBut_Search.setVisibility(View.GONE);
			cpr_PaymentRule.setVisibility(View.GONE);
			csp_Reference.setVisibility(View.GONE);
			cdb_Date.setVisibility(View.VISIBLE);
			ch_Boolean.setVisibility(View.GONE);
			cdb_Date.requestFocus();
		} else if(DisplayType.isBoolean(m_AD_Reference_ID)){
			et_Search.setVisibility(View.GONE);
			iBut_Search.setVisibility(View.GONE);
			cpr_PaymentRule.setVisibility(View.GONE);
			csp_Reference.setVisibility(View.GONE);
			cdb_Date.setVisibility(View.GONE);
			ch_Boolean.setVisibility(View.VISIBLE);
			ch_Boolean.setText(dfo.getValue());
			//	Valor por Defecto
			String value = dfo.getDefaultValue();
			value = Env.parseContext(getApplicationContext(), value, false);
			ch_Boolean.setChecked((value != null && value.equals("Y")));
			ch_Boolean.requestFocus();
		} else if(DisplayType.isNumeric(m_AD_Reference_ID) 
				|| DisplayType.isText(m_AD_Reference_ID)){
			cdb_Date.setVisibility(View.GONE);
			cpr_PaymentRule.setVisibility(View.GONE);
			csp_Reference.setVisibility(View.GONE);
			ch_Boolean.setVisibility(View.GONE);
			et_Search.setVisibility(View.VISIBLE);
			iBut_Search.setVisibility(View.VISIBLE);
			et_Search.setInputType(DisplayType.getInputType(dfo.getAD_Reference_ID()));
			et_Search.requestFocus();
		} else {
			cdb_Date.setVisibility(View.GONE);
			cpr_PaymentRule.setVisibility(View.GONE);
			csp_Reference.setVisibility(View.GONE);
			ch_Boolean.setVisibility(View.GONE);
			et_Search.setVisibility(View.GONE);
			iBut_Search.setVisibility(View.GONE);
			sp_Filter.setVisibility(View.GONE);
			//et_Search.setInputType(DisplayType.getInputType(dfo.getAD_Reference_ID()));
			//et_Search.requestFocus();
		}
		//	Set Clause Where
		setWhere();
		return true;
	}
	
    /**
     * Se encarga de cargar y mostrar la actividad por medio de 
     * un parametro que es el nombre de la clase
     * @author Yamel Senih 01/05/2012, 04:17:14
     * @param myClass
     * @param item
     * @return
     * @return boolean
     */
    private boolean loadActivity(DisplayRecordItem item){
    	boolean ok = false;
    	Class<?> clazz;
		try {
			
			clazz = Class.forName(att_Form.getClassName());
			
			//	Set Record ID
			Env.setTabRecord_ID(this, att_Form.getTableName(), item.getRecord_ID());
			att_Form.setRecord_ID(item.getRecord_ID());
			//	Param
			Bundle bundle = new Bundle();
			bundle.putParcelable("Param", att_Form);
			//	Load Intent
			Intent intent = new Intent(getApplicationContext(), clazz);
			intent.putExtras(bundle);
			//	Start
			startActivity(intent);
			ok = true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			Msg.alertMsg(this, getResources().getString(R.string.msg_LoadError), 
					getResources().getString(R.string.msg_ClassNotFound));
		}
		
		return ok;
    }
    
    /**
     * Inicia una clase cualquiera
     * @author Yamel Senih 12/05/2012, 03:52:05
     * @param myClass
     * @param item
     * @return
     * @return boolean
     */
    private boolean loadClassMenu(View v, DisplayRecordItem item){
    	boolean ok = false;
    	Class<?> clazz;
		try {
			
			if(classLoad == null){
				clazz = Class.forName(att_Form.getClassName());
				classLoad = (I_ClassLoaderListRecord)clazz.newInstance();
			}
			
			att_Form.setRecord_ID(item.getRecord_ID());
			//	Start Load
			classLoad.startLoad(this, v, att_Form);
			//	Ok
			ok = true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			Msg.alertMsg(this, getResources().getString(R.string.msg_LoadError), 
					getResources().getString(R.string.msg_ClassNotFound));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			Msg.alertMsg(this, getResources().getString(R.string.msg_LoadError), 
					getResources().getString(R.string.msg_IllegalAccessException));
		} catch (InstantiationException e) {
			e.printStackTrace();
			Msg.alertMsg(this, getResources().getString(R.string.msg_LoadError), 
					getResources().getString(R.string.msg_InstantiationException));
		}
		
		return ok;
    }
    
    /**
     * Carga los registros de Cualquier tabla en forma de Listado
     * @author Yamel Senih 01/05/2012, 20:11:08
     * @return
     * @return boolean
     */
	private boolean loadRecords(){
		//	Falta colocar Dinamico las Columnas a mostrar
		StringBuffer sql = new StringBuffer("SELECT ");
				sql.append(att_Form.getTableName() + "_ID, ");
				
				//	Columnas Establecidas como Identificador
				sql.append(identifierColumns[0].getColumnName() + ", ");
				sql.append(identifierColumns[1].getColumnName() + ", ");
				sql.append(identifierColumns[2].getColumnName() + ", ");
				sql.append(identifierColumns[3].getColumnName() + ", ");
				sql.append(identifierColumns[4].getColumnName() + ", ");
				sql.append(identifierColumns[5].getColumnName() + " ");
				//sql.append(identifierColumns[6].getColumnName() + " ");
				
				//	From de la Consulta
    			sql.append("FROM " + att_Form.getTableName() + " ");
    			//	Joins References
    			if(joins != null)
    				sql.append(joins);
    			
    			
    			//	Optional Where
    			if(att_Form.getWhereClause() != null 
    					&& att_Form.getWhereClause().length() > 0){
    				//	Parse Valus Context
    				String whereContext = Env.parseContext(this, att_Form.getWhereClause(), false);
    				
    				if(where != null){
    					where += " AND " + whereContext;
    				} else {
    					where = new String("WHERE " + whereContext);
    				}
    			}
    			
    			//	Add Where Clause
    			if(where != null){
    				sql.append(where);
    			}
    			
    			//	Group By
    			if(att_Form.getGroupByClause() != null 
    					&& att_Form.getGroupByClause().length() > 0){
    				sql.append(" GROUP BY ");
    				sql.append(att_Form.getGroupByClause() + " ");
    			}
    			
    			
    			//	Order
    			sql.append(" ORDER BY ");
    			if(att_Form.getOrderByClause() != null 
    					&& att_Form.getOrderByClause().length() > 0){
    				sql.append(att_Form.getOrderByClause() + " ");
    			} else {
    				sql.append(identifierColumns[1].getColumnName() + " ");
    			}
    			//	Limit
    			int limit = Env.getContextAsInt(this, "#LimitQuery");
    			if(limit > 0)
    				sql.append(" LIMIT " + limit);
    	
    		
    	boolean poli = false;	
		data = new LinkedList<DisplayRecordItem>();
		
		//	Form
		if(att_Form.getAction().equals("F")){
			//	If Can Read Write 
			if(att_Form.isReadWrite() 
					&& att_Form.isInsertRecord()
					&& att_Form.getActivityType().equals("S")){
				data.add(new DisplayRecordItem(
						new Att_ColumnRecord(null, "", 0, null),
						DisplayRecordItem.RT_NEW, 
						getResources().getString(R.string.msg_New),
						getResources().getString(R.string.msg_NewRecord),
						getResources().getString(R.string.msg_New),
						null, null, 0));
			}
		//	Process
		} else if(att_Form.getAction().equals("R")){
			//	Header
			DisplayRecordItem header = new DisplayRecordItem(
					0, 
					identifierColumns,
					identifierColumns[0].getName(),
					identifierColumns[1].getName(),
					identifierColumns[2].getName(),
					identifierColumns[3].getName(),
					identifierColumns[4].getName(), 
					identifierColumns[5].getName());
			//	Set Preferences
			header.setBackground(getResources().getColor(android.R.color.white));
			header.setTextColor(getResources().getColor(android.R.color.black));
			header.setTypeFace(Typeface.DEFAULT_BOLD);
			
			data.add(header);
			
			if(DisplayType.isNumeric(identifierColumns[1].getType())
					|| DisplayType.isNumeric(identifierColumns[2].getType())
					|| DisplayType.isNumeric(identifierColumns[3].getType())
					|| DisplayType.isNumeric(identifierColumns[4].getType())
					|| DisplayType.isNumeric(identifierColumns[5].getType())){
				
				identifierColumns[0].setSummary(Env.ZERO);
				identifierColumns[1].setSummary(Env.ZERO);
				identifierColumns[2].setSummary(Env.ZERO);
				identifierColumns[3].setSummary(Env.ZERO);
				identifierColumns[4].setSummary(Env.ZERO);
				identifierColumns[5].setSummary(Env.ZERO);
				
				data.add(null);
				
				poli = true;
			}	
			
		}
		
		loadConnection();
    	//Log.i("sql.toString()", "-- " + sql.toString());
    	Cursor rs = con.querySQL(sql.toString(), param);
		//	Inicializa el Contador de Registros
		recordNo = 0;
		if(rs.moveToFirst()){
			do {
				data.add(new DisplayRecordItem(rs.getInt(0), 
						identifierColumns, 
						rs.getString(1), 
						rs.getString(2), 
						rs.getString(3), 
						rs.getString(4), 
						rs.getString(5), 
						rs.getString(6)));
				if(poli){
					identifierColumns[0].addSummary(rs.getString(1));
					identifierColumns[1].addSummary(rs.getString(2));
					identifierColumns[2].addSummary(rs.getString(3));
					identifierColumns[3].addSummary(rs.getString(4));
					identifierColumns[4].addSummary(rs.getString(5));
					identifierColumns[5].addSummary(rs.getString(6));	
				}
				
				//	Incrementa el Contador de Registros
				recordNo++;
				
			} while(rs.moveToNext());
		}
		//	Close
		rs.close();
		closeConnection();
		
		//	Load Adapter
		if(poli){
			DisplayRecordItem summary = new DisplayRecordItem(
					0, 
					identifierColumns,
					null,
					identifierColumns[1].getSummary(),
					identifierColumns[2].getSummary(),
					identifierColumns[3].getSummary(),
					identifierColumns[4].getSummary(), 
					identifierColumns[5].getSummary());
			//	Set Preferences				
			//summary.setBackground(R.style.TextSummary);
			//summary.setTextColor(R.style.TextSummary);
			summary.setTypeFace(Typeface.DEFAULT_BOLD);
			
			data.set(1, summary);
		}

		//	Se establece el valor en la etiqueta
		tv_RecordNo.setText(String.valueOf(recordNo));
		
		AdapterRecordItem mi_adapter = null;
		if(att_Form.getAction().equals("F") 
				|| att_Form.getAction().equals("L")){
			mi_adapter = new AdapterRecordItem(this, R.layout.il_record, data, att_Form.getAction());
			mi_adapter.setDropDownViewResource(R.layout.il_record);
		} else if(att_Form.getAction().equals("R")){
			mi_adapter = new AdapterRecordItem(this, R.layout.il_report, data, att_Form.getAction());
			mi_adapter.setDropDownViewResource(R.layout.il_report);
		}
		
		recordList.setAdapter(mi_adapter);
		
		//closeConnection();
		
    	return true;
    }
    
    /**
     * Verifica y establece conexion
     * @author Yamel Senih 01/05/2012, 21:11:03
     * @return void
     */
    private void loadConnection(){
    	if(con == null)
			con = new DB(this);
		if(!con.isOpen())
			con.openDB(DB.READ_ONLY);
    }
    
    /**
     * Cierra la Conexion a la BD
     * @author Yamel Senih 04/05/2012, 21:29:05
     * @return void
     */
    private void closeConnection(){
    	if(con != null && con.isOpen())
			con.closeDB(null);
    }
    
    /**
     * Carga las Opciones mostradas en el Spinner de Busqueda
     * @author Yamel Senih 01/05/2012, 04:20:40
     * @return
     * @return boolean
     */
    private boolean loadFilterOptions(){
    	boolean ok = false;
    	String sql = new String("SELECT c.AD_Column_ID, c.Name, c.AD_Reference_ID, " +
    			"c.ColumnName, c.ColumnSQL, c.DefaultValue " +
    			"FROM AD_Table t " +
    			"INNER JOIN AD_Column c ON(c.AD_Table_ID = t.AD_Table_ID) ");
    	//	Verifica si la busqueda sera por ID de la Tabla o por el Nombre
    	if(att_Form.getAD_Table_ID() != 0)
    		sql += "WHERE c.AD_Table_ID = " + att_Form.getAD_Table_ID()  + " ";
    	else
    		sql += "WHERE t.TableName = '" + att_Form.getTableName()  + "' ";
    	//
    	sql += "AND (c.ColumnName IN('DocumentNo', 'Name', 'Description') " +
    			"OR (c.IsSelectionColumn = 'Y' " +
    			"AND c.AD_Reference_ID IN(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 24, 29, 30, 42))) " +
    			"ORDER BY c.SelectionSeqNo ,c.Name";	//	New Column SelectionSeqNo only Mobile
    	
    	//Log.i("SQL", sql);
    	/**
    	 * Falta arreglar esto para usar la clase de InfoColumn Optimo
    	 */
    	LoadDataSpinner.loadFilterOption(this, con, sp_Filter, sql);
    	
    	dfo = (DisplayFilterOption) sp_Filter.getSelectedItem();
    	loadDataType();
    	
    	return ok;
    }
    
    private boolean loadIdentifierColumn(){
    	boolean ok = false;
    	
	    String sqlWhere = new String("(AD_Column.ColumnName IN('DocumentNo', 'Name', " +
	    		"'Description', 'DocAction', 'DocStatus', 'IsActive', 'ImgName') " +
	    	"OR AD_Column.IsIdentifier = 'Y') ");
	    
	    String sqlOrderBy = new String("AD_Column.SeqNo, AD_Column.Name");
	    
	    if(infoIdentifier == null)
	    	infoIdentifier = new MPTableInfo(
	    			con, att_Form.getAD_Table_ID(), 
	    			att_Form.getTableName(), 
	    			sqlWhere, 
	    			sqlOrderBy, 
	    			false);
    	
    	identifierColumns = new Att_ColumnRecord []{
    			new Att_ColumnRecord("''", null, 0, null), 
    			new Att_ColumnRecord("''", null, 0, null), 
    			new Att_ColumnRecord("''", null, 0, null), 
    			new Att_ColumnRecord("''", null, 0, null),
    			new Att_ColumnRecord("''", null, 0, null),
    			new Att_ColumnRecord("''", null, 0, null)
    			};
    	//labelColumns = new String []{"", "", "", "", "", "", ""};
    	
    	int j = 1;
    	for(int i = 0;i < infoIdentifier.getColumnLength(); i++){
    		String columnName = getColumnName(infoIdentifier, i);
    		String columnNameDB = infoIdentifier.getColumnName(i);
    		if(infoIdentifier.getColumnName(i).equals("ImgName")){
    			identifierColumns [0] = new Att_ColumnRecord(columnName, infoIdentifier.getName(i), 
    					infoIdentifier.getDisplayType(i), null, columnNameDB);
    		} else if(infoIdentifier.getColumnName(i).equals("IsActive")){
    			identifierColumns [0] = new Att_ColumnRecord(columnName, infoIdentifier.getName(i), 
    					infoIdentifier.getDisplayType(i), null, columnNameDB);
    		} else if(infoIdentifier.getColumnName(i).equals("DocAction")
    				|| infoIdentifier.getColumnName(i).equals("DocStatus")){
    			identifierColumns [0] = new Att_ColumnRecord(columnName, infoIdentifier.getName(i), 
    					infoIdentifier.getDisplayType(i), null, columnNameDB);
    		} else{
    			if(infoIdentifier.getColumnName(i).endsWith("_ID")){
					String newColumnName = getColumnNameFromID(infoIdentifier.getColumnName(i));
					if(newColumnName != null){
						joins.append(getTableJoin(infoIdentifier.getColumnName(i), infoIdentifier.isMandatory(i)));
						columnName = newColumnName;
					}
					identifierColumns[j] = new Att_ColumnRecord("IFNULL(" + columnName + ", '')", infoIdentifier.getName(i), 
	    					infoIdentifier.getDisplayType(i), null, columnNameDB);
				} else {
					identifierColumns[j] = new Att_ColumnRecord(columnName, infoIdentifier.getName(i), 
	    					infoIdentifier.getDisplayType(i), null, columnNameDB);
				}
    			j++;
    		}
    	}
    	
    	return ok;
    }
    
    /**
     * Obtiene la columna sql o el nombre de la columna con el ParseContext
     * @author Yamel Senih 30/07/2012, 11:38:09
     * @param infoColumn
     * @param index
     * @return
     * @return String
     */
    private String getColumnName(MPTableInfo infoColumn, int index){
    	String value = "''";
    	if(!infoColumn.isColumnSQL(index)){
    		value = "IFNULL(" + infoColumn.getTableName() + "." + infoColumn.getColumnName(index) + ", '')";
    	} else {
    		//	Parse SQL Column
    		String columnSQL = Env.parseContext(this, infoColumn.getColumnSQL(index), false);
    		value = "IFNULL(" + columnSQL + ", '')";
    	}
    	
    	return value;
    }
    
    /**
     * Obtiene el nombre de la columna para identificar un ID
     * @author Yamel Senih 03/05/2012, 20:55:46
     * @param columnID
     * @return
     * @return String
     */
    private String getColumnNameFromID(String columnID){
    	String tableName = columnID.replaceAll("_ID", "");
    	String columName = null;
    	String sql = new String("SELECT c.ColumnName " +
    			"FROM AD_Table t " +
    			"INNER JOIN AD_Column c ON(c.AD_Table_ID = t.AD_Table_ID) " +
    			"WHERE t.TableName = ? AND c.IsIdentifier = 'Y' " +
    			"ORDER BY c.SeqNo Limit 1");
    	Cursor rs = con.querySQL(sql, new String[]{tableName});
    	
    	//Log.i("MV_ListRecord.getColumnNameFromID", sql);
    	
    	//	
    	if(rs.moveToFirst()){
    		columName = rs.getString(0);
    		//Log.i("MV_ListRecord.getColumnNameFromID", columName);
    	}
    	
    	return (columName != null? tableName + "." + columName: columName);
    }
    
    /**
     * Obtiene el JOIN de la consulta
     * @author Yamel Senih 03/05/2012, 21:07:15
     * @param columnID
     * @param isNotNull
     * @return
     * @return String
     */
    private String getTableJoin(String columnID, boolean isNotNull){
    	String tableName = columnID.replaceAll("_ID", "");
    	StringBuffer join = new StringBuffer();
    	if(isNotNull)
    		join.append(" INNER JOIN ");
    	else
    		join.append(" LEFT JOIN ");
    	join.append(tableName);
    	join.append(" ON(" + tableName + "." + columnID + " = " + att_Form.getTableName() + "." + columnID + ") ");
    	return join.toString();
    }
    
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //loadConnection();
    	//	Load Filters
    	//loadFilterOptions();
        loadRecords();
    	//closeConnection();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }
    
    /**
	 * Se retorna el Record_ID y se finaliza la actividad
	 * @author Yamel Senih 23/05/2012, 04:35:18
	 * @param item
	 * @return void
	 */
	private void selectedRecord(DisplayRecordItem item){
		item.setFrom(att_Form.getFrom());
		//Msg.toastMsg(this, " Att_Form " + att_Form.getFrom());
		Intent intent = getIntent();
		intent.putExtra("Record", item);
		setResult(RESULT_OK, intent);
		finish();
	}
    
}
