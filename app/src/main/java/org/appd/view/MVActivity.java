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

import java.util.ArrayList;

import org.appd.base.DB;
import org.appd.base.Env;
import org.appd.base.R;
import org.appd.model.MP;
import org.appd.model.MPTableInfo;
import org.appd.util.DisplayMenuItem;
import org.appd.util.DisplayRecordItem;
import org.appd.util.Msg;
import org.appd.util.ViewIndex;
import org.appd.view.custom.Cust_ButtonDocAction;
import org.appd.view.custom.Cust_ButtonPaymentRule;
import org.appd.view.custom.Cust_DateBox;
import org.appd.view.custom.Cust_Search;
import org.appd.view.custom.Cust_Spinner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author Yamel Senih
 *
 */
public abstract class MVActivity extends Activity {
	
	protected DisplayMenuItem param;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fFrontEnd = new ArrayList<ViewIndex>();
		
		Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			param = (DisplayMenuItem)bundle.getParcelable("Param");
		}
		if(param == null)
			throw new IllegalArgumentException ("No Param");
		
		//	Instance of Object Connection
		con = new DB(this);
		
		setTitle(param.getName());
		Env.setIsLoadedActivity(this, false);
		load();
		
	}
	
	protected DB con;
	private MP mP = null;
	private boolean loadOk = false;
	protected MPTableInfo tabInfo = null;
	private ArrayList<ViewIndex> fFrontEnd;
	//private boolean manConn = false;
	private boolean isSummary = false;
	//protected boolean isLoaded = false;
	protected static final int NEW = 0;
	protected static final int MODIFY = 1;
	protected static final int SEE = 3;
	protected static final int DELETED = 4;
	
	/**
	 * Carga el Contexto y otros en la actividad
	 * @author Yamel Senih 13/04/2012, 11:16:27
	 * @return void
	 */
	public void load(){
		if(mP == null){
			mP = getCustomMP();
			if(mP == null)
				mP = new MP(this, 0, param.getAD_Table_ID());
			tabInfo = mP.getTableInfo();
		}
	}

	/**
	 * Refresca la Actividad
	 * @author Yamel Senih 13/05/2012, 13:05:43
	 * @return void
	 */
	public void loadView(){
		int record_ID = Env.getTabRecord_ID(this, getActivityName());
		if(record_ID > 0){
			setEnabled(SEE);
		} else {
			setEnabled(NEW);
		}
		refresh(record_ID);
	}
	
	/**
	 * Configuración de Visualizacion
	 * @author Yamel Senih 13/05/2012, 17:35:29
	 * @return
	 * @return boolean
	 */
	public boolean seeConfig(){
		setEnabled(SEE);
		return true;
	}
	
	/**
	 * Agrega un componente al arreglo
	 * @author Yamel Senih 28/05/2012, 10:12:04
	 * @param vName
	 * @param vField
	 * @param columnName
	 * @param isReadOnly
	 * @return void
	 */
	public void addView(View vName, View vField, String columnName, boolean isReadOnly){
		int index = tabInfo.getColumnIndex(columnName);
		if(vField instanceof Cust_Spinner)
			((Cust_Spinner)vField).setConnection(con);
		else if(vField instanceof CheckBox)
			((CheckBox) vField).setText(((TextView)vName).getText());
		else if(vField instanceof Cust_Search){
			((Cust_Search)vField).setActivity(this);
			((Cust_Search)vField).getItem().setFrom(fFrontEnd.size());
			((Cust_Search)vField).setConnection(con);
			//Msg.toastMsg(getCtx(), " Init " + fFrontEnd.size());
		}
		fFrontEnd.add(new ViewIndex(vName, vField, columnName, isReadOnly, index));
	}
	
	/**
	 * Agrega un componente al arreglo con un valor por defecto
	 * @author Yamel Senih 11/05/2012, 16:29:57
	 * @param vName
	 * @param vField
	 * @param columnName
	 * @param defaultValue
	 * @param isReadOnly
	 * @return void
	 */
	public void addView(View vName, View vField, String columnName, String defaultValue, boolean isReadOnly){
		int index = tabInfo.getColumnIndex(columnName);
		if(vField instanceof Cust_Spinner)
			((Cust_Spinner)vField).setConnection(con);
		else if(vField instanceof CheckBox)
			((CheckBox) vField).setText(((TextView)vName).getText());
		else if(vField instanceof Cust_Search){
			((Cust_Search)vField).setActivity(this);
			((Cust_Search)vField).getItem().setFrom(fFrontEnd.size());
			((Cust_Search)vField).setConnection(con);
		}
		fFrontEnd.add(new ViewIndex(vName, vField, defaultValue, columnName, isReadOnly, index));
	}
	
	/**
	 * Obtiene el Contexto
	 * @author Yamel Senih 13/04/2012, 11:18:56
	 * @return
	 * @return Context
	 */
	public Context getCtx(){
		return this;
	}

	/**
	 * Verifica si la pestaña es Padre de Otra
	 * @author Yamel Senih 12/05/2012, 06:07:14
	 * @return
	 * @return boolean
	 */
	public boolean isSummary(){
		return isSummary;
	}
	
	/**
	 * Estable si es una Pestaña Padre
	 * @author Yamel Senih 15/05/2012, 16:26:46
	 * @param isSummary
	 * @return void
	 */
	public void setIsSummary(boolean isSummary){
		this.isSummary = isSummary;
	}
	
	/**
	 * Obtiene el ID del Registro Actual
	 * @author Yamel Senih 12/05/2012, 06:08:45
	 * @return
	 * @return int
	 */
	public int getRecord_ID(){
		if(mP != null)
			return mP.getID();
		return 0;
	}
	
	/**
	 * Guarda en la base de Datos
	 * @author Yamel Senih 08/04/2012, 20:43:27
	 * @return boolean
	 */
	public boolean save(){
		try {
			for(ViewIndex v : fFrontEnd){
				String value = getValue(v);
				if(tabInfo.isMandatory(v.getIndex())){
					if(value == null || value.length() == 0){
						Msg.alertMsg(this, getResources().getString(R.string.msg_Error), 
								getResources().getString(R.string.MustFillField) + 
								" \"" + ((TextView)v.getViewLabel()).getText() + "\"");
						v.getViewValue().requestFocus();
						return false;
					}
				} 
				mP.set_Value(v.getIndex(), value);
				if(v.getViewValue() instanceof Cust_Spinner
						|| v.getViewValue() instanceof Cust_ButtonDocAction
						|| v.getViewValue() instanceof Cust_ButtonPaymentRule
						|| v.getViewValue() instanceof Cust_Search){
					Env.setContext(this, "#" + v.getColumnName(), value);
				}
			}
			mP.saveEx();
			setEnabled(SEE);
		} catch (Exception e) {
			Log.e("MVActivity.save", "Save Error", e);
			Msg.alertMsg(this, getResources().getString(R.string.msg_Error), e.getMessage());
			return false;
		}
		return true;
    }
	
	/**
	 * Habilita o deshablilita los campos de la actividad
	 * @author Yamel Senih 05/05/2012, 02:24:30
	 * @param enabled
	 * @return void
	 */
	public void setEnabled(int type){
		String docAction = Env.getContext(this, "#DocAction");
		if(docAction == null){
			docAction = Cust_ButtonDocAction.STATUS_Drafted;
		}
		if(type == NEW){
			for(ViewIndex v : fFrontEnd){
				if(!v.isReadOnly()
						&& !v.getColumnName().equals("DocAction")){
					v.setEnabled(true);
				} else {
					v.setEnabled(false);
				}
			}	
		} else if(type == MODIFY){
			for(ViewIndex v : fFrontEnd){
				if((tabInfo.isUpdateable(v.getIndex()) 
						&& !v.isReadOnly()
						&& !docAction.equals(Cust_ButtonDocAction.STATUS_Completed) 
						&& !docAction.equals(Cust_ButtonDocAction.STATUS_Voided)
						&& !v.getColumnName().equals("DocAction"))
						|| (tabInfo.isAlwaysUpdateable(v.getIndex())
								&& !v.isReadOnly()
								&& !v.getColumnName().equals("DocAction"))){
					v.setEnabled(true);
				} else {
					v.setEnabled(false);
				}
			}	
		} else if(type == DELETED){
			for(ViewIndex v : fFrontEnd){
				v.setEnabled(false);
			}
		} else if(type == SEE){
			for(ViewIndex v : fFrontEnd){
				if(param.isReadWrite()
						&& v.getColumnName().equals("DocAction")){
					v.setEnabled(true);
				} else {
					v.setEnabled(false);
				}
			}
		}
	}
	
	/**
	 * Elimina un registro de la base de datos y actualiza la actividad
	 * @author Yamel Senih 05/05/2012, 02:20:26
	 * @return
	 * @return boolean
	 */
	public boolean delete(){
		if(tabInfo.isDeleteable()){
			try {
				mP.deleteEx();
				setEnabled(DELETED);
		        refresh(0);
				return true;
			} catch (Exception e) {
				Log.e("MVActivity.delete", "Delete Error", e);
				Msg.alertMsg(this, getResources().getString(R.string.msg_Error), 
						e.getMessage());
			}
		} else {
			Msg.alertMsg(this, getResources().getString(R.string.msg_Error), 
					getResources().getString(R.string.msg_IsNoDeleteable));
		}
		return false;
    }
	
	/**
	 * Prepara un Nuevo Registro
	 * @author Yamel Senih 05/05/2012, 03:27:35
	 * @return
	 * @return boolean
	 */
	public boolean newConfig(){
		setEnabled(NEW);
		mP.copyValues(true);
		Env.setTabRecord_ID(this, getActivityName(), 0);
		//	Refresh
		refreshFrontEnd();
		return true;
	}
	
	/**
	 * Devuelve los cambios
	 * @author Yamel Senih 05/05/2012, 04:58:30
	 * @return
	 * @return boolean
	 */
	public boolean backCopy(){
		mP.backCopy();
		Env.setTabRecord_ID(this, getActivityName(), mP.getID());
		refreshFrontEnd();
		setEnabled(SEE);
		return true;
	}
	
	/**
	 * Se prepara para modificar un registro
	 * @author Yamel Senih 06/05/2012, 20:24:01
	 * @return
	 * @return boolean
	 */
	public boolean modifyRecord(){
		mP.copyValues(false);
		setEnabled(MODIFY);
		return true;
	}
	
	/**
	 * Obtiene el Modelo de Datos de la clase
	 * @author Yamel Senih 06/05/2012, 15:50:21
	 * @return
	 * @return MP
	 */
	public MP getMP(){
		return mP;
	}
	
	/**
	 * Establece el valor de la clase modelo
	 * @author Yamel Senih 09/11/2012, 01:54:10
	 * @param mP
	 * @return void
	 */
	public void setMP(MP mP){
		this.mP = mP;
	}
	
	/**
	 * Establece el valor en diferentes objetos del la actividad
	 * @author Yamel Senih 03/05/2012, 18:36:09
	 * @param vi
	 * @return void
	 */
	private void setValueFromMP(ViewIndex vi){
		int index = vi.getIndex();
		String defaultValue = vi.getDefaultValue();
		if(vi.getViewValue() instanceof EditText){
			if(mP.get_Value(vi.getIndex()) != null)
				((EditText)vi.getViewValue()).setText((String) mP.get_Value(index));
			else //if(defaultValue != null)
				((EditText)vi.getViewValue()).setText(defaultValue);
				
		} else if(vi.getViewValue() instanceof Cust_ButtonDocAction){
			if(mP.get_Value(vi.getIndex()) != null){
				((Cust_ButtonDocAction)vi.getViewValue()).setDocAction((String) mP.get_Value(index));
				Env.setContext(this, "#" + vi.getColumnName(), (String) mP.get_Value(index));
			} else {
				((Cust_ButtonDocAction)vi.getViewValue()).setDocAction(defaultValue);
				Env.setContext(this, "#" + vi.getColumnName(), defaultValue);
			}
		} else if(vi.getViewValue() instanceof Cust_ButtonPaymentRule){
			if(mP.get_Value(vi.getIndex()) != null){
				((Cust_ButtonPaymentRule)vi.getViewValue()).setPaymentRule((String) mP.get_Value(index));
				Env.setContext(this, "#" + vi.getColumnName(), (String) mP.get_Value(index));
			} else {
				((Cust_ButtonPaymentRule)vi.getViewValue()).setPaymentRule(defaultValue);
				Env.setContext(this, "#" + vi.getColumnName(), defaultValue);
			}
		} else if(vi.getViewValue() instanceof Cust_DateBox){
			if(mP.get_Value(index) != null)
				((Cust_DateBox)vi.getViewValue()).setDate((String) mP.get_Value(index));
			else
				((Cust_DateBox)vi.getViewValue()).setDate(defaultValue);
		} else if(vi.getViewValue() instanceof Cust_Spinner){
			//	Set Context Value
			Env.setContext(this, "#" + vi.getColumnName(), mP.get_ValueAsInt(index));
			((Cust_Spinner)vi.getViewValue()).setRecord_ID(mP.get_ValueAsInt(index));
		} else if(vi.getViewValue() instanceof CheckBox){
			if(mP.get_Value(vi.getIndex()) != null){
				boolean checked = ((String) mP.get_Value(index)).equals("Y");
				((CheckBox)vi.getViewValue()).setChecked(checked);
			}
			else if(defaultValue != null){
				boolean checked = defaultValue.equals("Y");
				((CheckBox)vi.getViewValue()).setChecked(checked);
			}				
		} else if(vi.getViewValue() instanceof Cust_Search){
			//	Set Context Value
			
			((Cust_Search)vi.getViewValue()).setRecord_ID(mP.get_ValueAsInt(index));
			
			if(mP.get_Value(index) != null){
				((Cust_Search)vi.getViewValue()).setRecord_ID(mP.get_ValueAsInt(index));
				Env.setContext(this, "#" + vi.getColumnName(), mP.get_ValueAsInt(index));
			} else {
				if(defaultValue != null){
					int defaultId = Integer.parseInt(defaultValue);
					((Cust_Search)vi.getViewValue()).setRecord_ID(defaultId);
					Env.setContext(this, "#" + vi.getColumnName(), defaultId);
				} else
					Env.setContext(this, "#" + vi.getColumnName(), 0);
			}
		}
	}
	
	/**
	 * Carga los datos a partir de un ID del Registro
	 * @author Yamel Senih 23/05/2012, 04:53:16
	 * @param record_ID
	 * @return
	 * @return boolean
	 */
	protected boolean refresh(int record_ID){
		load();
		loadOk = true;
		if(record_ID > 0){
			loadOk = mP.loadData(record_ID);
		} else {
			mP.clear();
		}
		
		Env.setTabRecord_ID(this, getActivityName(), record_ID);
		//Msg.toastMsg(this, "" + mP.getID());
		
		refreshFrontEnd();
		//Env.setIsLoadedActivity(getCtx(), true);
		return true;
	}
	
	/**
	 * Refresca la consulta con el mismo RecordID
	 * @author Yamel Senih 28/05/2012, 15:21:06
	 * @return
	 * @return boolean
	 */
	protected boolean refresh(){
		load();
		loadOk = true;
		int record_ID = Env.getTabRecord_ID(this, getActivityName());
		if(record_ID > 0){
			loadOk = mP.loadData(record_ID);
		} else {
			mP.clear();
		}
		
		refreshFrontEnd();
		//Env.setIsLoadedActivity(getCtx(), true);
		return true;
	}
	
	/**
	 * Establece si la Actividad Maneja la conexion o no
	 * @author Yamel Senih 11/05/2012, 00:43:02
	 * @param manConn
	 * @return void
	 */
	/*protected void setManConnection(boolean manConn){
		this.manConn = manConn;
	}*/
	
	/**
	 * Verifica si la actividad maneja la conexion para la carga de datos
	 * @author Yamel Senih 11/05/2012, 00:45:00
	 * @return
	 * @return boolean
	 */
	/*protected boolean isManConnection(){
		return manConn;
	}*/
	
	/**
	 * Refresca la Interfaz Grafica
	 * @author Yamel Senih 05/05/2012, 04:49:51
	 * @return void
	 */
	public void refreshFrontEnd(){
		//	Load Connection
		loadConnection();
		
		try {
			for(ViewIndex v : fFrontEnd){
				setValueFromMP(v);
			}
			
		} catch (Exception e) {
			Log.e("MVActivity.save", "Save Error", e);
			//Msg.alertMsg(this, "Error", e.getMessage());
		}
		//	Disconnect from DB
		closeConnection();
	}
	
	/**
	 * Retorna el estado de la carga
	 * @author Yamel Senih 07/05/2012, 14:10:49
	 * @return
	 * @return boolean
	 */
	public boolean getLoadOk(){
		return loadOk;
	}
	
	/**
	 * Obtiene el valor para validaciones
	 * @author Yamel Senih 13/04/2012, 15:18:15
	 * @param v
	 * @return
	 * @return String
	 */
	private String getValue(ViewIndex vi){
		View v = vi.getViewValue();
		if(v instanceof EditText){
			String value = ((EditText) v).getText().toString();
			//	Set Context Value
			//Env.setContext(this, vi.getColumnName(), value);
			return value;
		} else if(v instanceof Cust_Spinner){
			int id = ((Cust_Spinner) v).getID();
			//	Set Context Value
			Env.setContext(this, vi.getColumnName(), id);
			if(id > 0)
				return String.valueOf(id);
		} else if(v instanceof Cust_DateBox){
			String value = ((Cust_DateBox) v).getDate("yyyy-MM-dd hh:mm:ss");
			//	Set Context Value
			//Env.setContext(this, vi.getColumnName(), value);
			//Msg.toastMsg(getCtx(), "--" + value);
			return value;	
		} else if(v instanceof Cust_ButtonDocAction){
			String value = ((Cust_ButtonDocAction) v).getDocStatus();
			return value;
		} else if(v instanceof Cust_ButtonPaymentRule){
			String value = ((Cust_ButtonPaymentRule) v).getPaymentRule();
			return value;
		} else if(v instanceof CheckBox){
			String value = (((CheckBox) v).isChecked()? "Y": "N");
			return value;
		} else if(v instanceof Cust_Search){
			int id = ((Cust_Search) v).getID();
			//	Set Context Value
			Env.setContext(this, vi.getColumnName(), id);
			if(id > 0)
				return String.valueOf(id);
		}
		return null;
	}
	
	@Override
    protected void onStart() {
        super.onStart();
        //Msg.toastMsg(this, "onStart " + Env.getTabRecord_ID(getCtx(), getActivityName()));
    }
    @Override
    protected void onResume() {
        super.onResume();
        Env.setCurrentTab(this, getActivityName());
        if(!Env.isLoadedActivity(getCtx())){
        	//Msg.toastMsg(getCtx(), " - - ");
        	loadView();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        //Msg.toastMsg(this, "onPause");
        //Log.i("MVA", "onPause");
        //Env.setTabRecord_ID(this, getActivityName(), getRecord_ID());
    }
    @Override
    protected void onStop() {
        super.onStop();
        //Msg.toastMsg(this, "onStop");
        //Log.i("MVA", "onStop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Msg.toastMsg(this, "onDestroy");
        //Log.i("MVA", "onDestroy");
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	/*if ((keyCode == KeyEvent.KEYCODE_BACK)) {
    		//cancelOption();
    		return true;
    	}*/
    	return super.onKeyDown(keyCode, event);
    }
    
    /**
     * Establece la conexion con la Base de Datos
     * @author Yamel Senih 10/05/2012, 22:02:34
     * @return void
     */
    protected void loadConnection(){
    	//if(isManConnection()){
    		if(con == null)
    			con = new DB(this);
    		if(!con.isOpen())
    			con.openDB(DB.READ_ONLY);
    	//}
    }
    
    /**
     * Esstblece conexion
     * @author Yamel Senih 11/11/2012, 22:20:41
     * @param type
     * @return void
     */
    protected void loadConnection(int type){
    	//if(isManConnection()){
    		if(con == null)
    			con = new DB(this);
    		if(!con.isOpen())
    			con.openDB(type);
    	//}
    }
    
    /**
     * Desconecta la conexión con la Base de Datos
     * @author Yamel Senih 10/05/2012, 22:03:38
     * @return void
     */
    protected void closeConnection(){
    	if(con != null && con.isOpen())
			con.closeDB(null);
    	mP.closeConnection();
    }
    
    
    /**
     * Verifica y establece conexion
     * @author Yamel Senih 01/05/2012, 21:11:03
     * @return void
     */
    /*private void loadConnection(){
    	if(con == null)
			con = new DB(this, Env.getDB_Name(this), Env.getDB_Version(this));
		if(!con.isOpen())
			con.openDB(DB.READ_ONLY);
    }*/
    
    /**
     * Cierra la Conexion a la BD
     * @author Yamel Senih 04/05/2012, 21:29:05
     * @return void
     */
   /*private void closeConnection(){
    	if(con != null && con.isOpen())
			con.closeDB();
    }*/
    
    /**
     * Obtiene el Item o Parametro para Busqueda
     * @author Yamel Senih 23/05/2012, 04:27:33
     * @return
     * @return DisplayMenuItem
     */
    protected DisplayMenuItem getMenuItem(){
    	DisplayMenuItem item = new DisplayMenuItem(getResources().getString(R.string.msg_Search) + 
    			" \"" + getTitle() + "\"", 
    			null, tabInfo.getAD_Table_ID(), tabInfo.getTableName(), null, null, "F");
    	return item;
    }
    
    /**
     * Verifica si es Nuevo registro
     * @author Yamel Senih 24/07/2012, 23:58:38
     * @return
     * @return boolean
     */
    protected boolean isNew(){
    	return mP.isNew();
    }
    
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
	    if (resultCode == RESULT_OK) {
	    	if(data != null){
	    		DisplayRecordItem item = (DisplayRecordItem)data.getParcelableExtra("Record");
	    		int record_ID = item.getRecord_ID();
	    		//Msg.toastMsg(this, "** " + record_ID + " - From " + item.getFrom());
	    		if(record_ID > 0){
	    			ViewIndex vi = fFrontEnd.get(item.getFrom());
	    			if(vi.getViewValue() instanceof Cust_Search){
	    				((Cust_Search)vi.getViewValue()).setValue(record_ID, item.getCol_2());
	    				Env.setContext(this, "#" + vi.getColumnName(), record_ID);
	    			}
    			} 
	    	}
	    }
	    Env.setIsLoadedActivity(getCtx(), true);
	    //Msg.toastMsg(getCtx(), "Is Loaded true");
	}
    
    /*@Override
    public void onConfigurationChanged(Configuration newConfig) {
     super.onConfigurationChanged(newConfig);
     setContentView(getR.layout.myLayout);
    }*/

    
    
    protected abstract String getActivityName();
    
	protected abstract MP getCustomMP();
	
	protected abstract MP getCustomMP(int id);
	
	protected abstract MP getCustomMP(Cursor rs);
	
}
