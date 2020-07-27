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

import org.appd.base.Env;
import org.appd.base.R;
import org.appd.util.DisplayMenuItem;
import org.appd.util.DisplayRecordItem;
import org.appd.util.Msg;

import android.app.AlertDialog.Builder;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public abstract class MTabActivity extends TabActivity implements OnTabChangeListener, OnClickListener{
	
	protected TabHost mTabHost;
    private Resources mResources;
    private DisplayMenuItem param;
    protected boolean existsRecord = false;
    protected int record_ID = 0;
    //protected boolean loaded = false;
    
    /**	Buttons	*/
    protected ImageButton buttNew = null;
    protected ImageButton buttBack = null;
    protected ImageButton buttDelete = null;
    protected ImageButton buttSave = null;
    protected ImageButton buttSearch = null;
    protected ImageButton buttReport = null;
    protected ImageButton buttModify = null;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.t_tab);
        
        Bundle bundle = getIntent().getExtras();
		 if(bundle != null){
			 param = (DisplayMenuItem)bundle.getParcelable("Param");
		 }
		 if(param == null)
			 throw new IllegalArgumentException ("No Param");
        
		 //Log.i("-- ** --", " " + param.isReadWrite() + " -- " + param.isInsertRecord()); 
		
        mTabHost = getTabHost();       
        mResources = getResources();
        
        setTitle(param.getName());
        
        mTabHost.setCurrentTab(Env.getContextAsInt(this, getActivityName()));
        //	Change Activity
        /*mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                loadView();
            }
        });*/
        mTabHost.setOnTabChangedListener(this);
        mTabHost.setOnClickListener(this);
        
        buttNew = (ImageButton) findViewById(R.id.iBut_New);
        buttBack = (ImageButton) findViewById(R.id.iBut_Back);
        buttDelete = (ImageButton) findViewById(R.id.iBut_Delete);
        buttSave = (ImageButton) findViewById(R.id.iBut_Save);
        buttModify = (ImageButton) findViewById(R.id.iBut_Modify);
        buttSearch = (ImageButton) findViewById(R.id.iBut_Search);
        buttReport = (ImageButton) findViewById(R.id.iBut_Report);
        
        buttNew.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				e_ButtNew();
			}
		});
        
        buttBack.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				e_ButtBack();
			}
		});
        
        buttDelete.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				e_ButtDelete();
			}
		});
        
        buttSave.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				e_ButtSave();
			}
		});
        
        buttSearch.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				e_ButtSearch();
			}
		});
        
        buttReport.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				e_ButtReport();
			}
		});
        
        buttModify.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				e_ButtModify();
			}
		});
        
        Env.setIsLoadedActivity(this, false);
        Env.isLoadedActivity(this);
    }
    
    /**
     * Carga y establece el estado de los botones
     * @author Yamel Senih 13/05/2012, 12:29:42
     * @return void
     */
    protected void loadView(){
    	if(!lockReadOnly()){
    		MVActivity bp = (MVActivity) getCurrentActivity();
    		//Msg.toastMsg(this, bp.getActivityName() + " " + Env.getTabRecord_ID(this, bp.getActivityName()));
    		if(Env.getTabRecord_ID(this, mTabHost.getCurrentTabTag()) > 0){
    			seeConfig();
    			bp.seeConfig();
    			existsRecord = true;
    		} else {
    			newConfig();
    			bp.newConfig();
    			existsRecord = false;
    		}
		}
    	//isLoaded = true;
    }
    
    /**
     * Bloquea los botones si no tiene permisos de escritura
     * @author Yamel Senih 05/05/2012, 04:21:58
     * @return
     * @return boolean
     */
    protected boolean lockReadOnly(){
    	boolean enabled = false;
    	if(!param.isReadWrite()){
    		buttNew.setEnabled(enabled);
    		buttDelete.setEnabled(enabled);
    		buttSave.setEnabled(enabled);
    		buttBack.setEnabled(enabled);
    		buttModify.setEnabled(enabled);
    		return true;
    	}
    	return false;
    }
    
    /**
     * 
     * @author Yamel Senih 03/05/2012, 19:08:05
     * @param clazz
     * @param bundle
     * @param tabTag
     * @param label
     * @param img
     * @return void
     */
    protected void addActivity(Class<?> clazz, Bundle bundle, String tabTag, int label, int img){
    	/*if(!Env.getCurrentTab(this).equals(tabTag))
    		Env.setTabRecord_ID(this, tabTag, 0);
    	*/
    	Intent intent = new Intent(this, clazz);
    	//	Param
    	bundle.putParcelable("Param", param);
    	intent.putExtras(bundle);
    	
    	TabSpec tSpec = mTabHost.newTabSpec(tabTag);
    	
        tSpec.setIndicator(mResources.getString(label), mResources
                .getDrawable(img));
        
        tSpec.setContent(intent);
        
        mTabHost.addTab(tSpec);
    }
    
    /**
     * 
     * @author Yamel Senih 14/11/2012, 19:10:28
     * @param clazz
     * @param bundle
     * @param tabTag
     * @param label
     * @param img
     * @return void
     */
    protected void addActivity(Class<?> clazz, Bundle bundle, String tabTag, String label, int img){
    	/*if(!Env.getCurrentTab(this).equals(tabTag))
    		Env.setTabRecord_ID(this, tabTag, 0);
    	*/
    	Intent intent = new Intent(this, clazz);
    	//	Param
    	bundle.putParcelable("Param", param);
    	intent.putExtras(bundle);
    	
    	TabSpec tSpec = mTabHost.newTabSpec(tabTag);
    	
        tSpec.setIndicator(label, mResources
                .getDrawable(img));
        
        tSpec.setContent(intent);
        
        mTabHost.addTab(tSpec);
    }
    
    /**
     * Agrega una actividad al TabActivity
     * @author Yamel Senih 03/05/2012, 19:13:47
     * @param clazz
     * @param tabTag
     * @param label
     * @param img
     * @return void
     */
    protected void addActivity(Class<?> clazz, String tabTag, int label, int img){
    	//Msg.toastMsg(this, tabTag);
    	/*if(!Env.getCurrentTab(this).equals(tabTag))
    		Env.setTabRecord_ID(this, tabTag, 0);
    	*/
    	Bundle bundle = new Bundle();
    	bundle.putParcelable("Param", param);
    	Intent intent = new Intent(this, clazz);
		//	Param
    	intent.putExtras(bundle);
    	
    	TabSpec tSpec = mTabHost.newTabSpec(tabTag);
    	
        tSpec.setIndicator(mResources.getString(label), mResources
                .getDrawable(img));
        
        tSpec.setContent(intent);
        
        mTabHost.addTab(tSpec);
    }
    
    /**
     * Agrega una Actividad al TabActivity
     * @author Yamel Senih 03/05/2012, 22:25:28
     * @param clazz
     * @param tabTag
     * @param label
     * @param img
     * @return void
     */
    protected void addActivity(Class<?> clazz, String tabTag, String label, String img){
    	//Msg.toastMsg(this, tabTag);
    	/*if(!Env.getCurrentTab(this).equals(tabTag))
    		Env.setTabRecord_ID(this, tabTag, 0);
    	*/
    	Bundle bundle = new Bundle();
    	bundle.putParcelable("Param", param);
    	Intent intent = new Intent(this, clazz);
		//	Param
    	intent.putExtras(bundle);
    	
    	TabSpec tSpec = mTabHost.newTabSpec(tabTag);
    	
    	if(img != null 
				&& img.length() > 0){
			Resources res = getResources();
			int resID = res.getIdentifier(img , "drawable", getPackageName());
			if(resID != 0){
				tSpec.setIndicator(label, res.getDrawable(resID));
			}
		} else {
			tSpec.setIndicator(label, mResources
	                .getDrawable(R.drawable.contruction_h));
		}
    	
        
        
        tSpec.setContent(intent);
        
        mTabHost.addTab(tSpec);
    }
    
    
    /**
     * Agrega una//int index = m_TableInfo.getColumnIndex(columnName);
		//Log.i("get_ValueForInsert", "columnName = " + columnName);
		 Actividad al TabActivity
     * @author Yamel Senih 03/05/2012, 22:29:54
     * @param clazz
     * @param tabTag
     * @param img
     * @return void
     */
    protected void addActivity(Class<?> clazz, String tabTag, int img){
    	
    	/*if(!Env.getCurrentTab(this).equals(tabTag))
    		Env.setTabRecord_ID(this, tabTag, 0);
    	*/
    	Bundle bundle = new Bundle();
    	bundle.putParcelable("Param", param);
    	Intent intent = new Intent(this, clazz);
		//	Param
    	intent.putExtras(bundle);
    	
    	TabSpec tSpec = mTabHost.newTabSpec(tabTag);
    	
			tSpec.setIndicator(param.getName(), mResources
	                .getDrawable(img));
		
        tSpec.setContent(intent);
        
        mTabHost.addTab(tSpec);
    }
    
    /**
     * Metodo para registro Nuevo
     * @author Yamel Senih 13/04/2012, 11:42:25
     * @return void
     */
    protected void e_ButtNew(){
    	MVActivity bp = (MVActivity)getCurrentActivity();
		if(bp != null){
			bp.newConfig();
			newConfig();
			record_ID = 0;
			Env.setTabRecord_ID(this, bp.getActivityName(), 0);
		}
    }
    
    /**
     * Metodo para devolver los Cambios
     * @author Yamel Senih 13/04/2012, 11:43:45
     * @return void
     */
    protected void e_ButtBack(){
    	MVActivity bp = (MVActivity)getCurrentActivity();
		if(bp != null){
			bp.backCopy();
			if(existsRecord){
				seeConfig();
			} else {
				voidConfig();
			}
			record_ID = bp.getRecord_ID();
			Env.setTabRecord_ID(this, bp.getActivityName(), bp.getRecord_ID());
		}
    }
    
    /**
     * Metodo para eliminar un Registro
     * @author Yamel Senih 13/04/2012, 11:44:03
     * @return void
     */
    protected void e_ButtDelete(){
    	final MVActivity bp = (MVActivity)getCurrentActivity();
		if(bp != null){
			String msg_Acept = this.getResources().getString(R.string.msg_Acept);
			Builder ask = Msg.confirmMsg(this, this.getResources().getString(R.string.msg_AskDelete));
			ask.setPositiveButton(msg_Acept, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					if(bp.delete()){
						voidConfig();
						existsRecord = false;
					}
				}
			});
			ask.show();
			record_ID = bp.getRecord_ID();
			Env.setTabRecord_ID(this, bp.getActivityName(), bp.getRecord_ID());
		}
    }
    
    /**
     * Configura los botones en modo nuevo
     * @author Yamel Senih 05/05/2012, 04:18:49
     * @return void
     */
    protected void newConfig(){
		//	Enable or disable Buttons
    	buttSave.setEnabled(true);
		buttBack.setEnabled(true);
		buttModify.setEnabled(false);
		buttNew.setEnabled(false);
		buttDelete.setEnabled(false);
		buttReport.setEnabled(false);
		buttSearch.setEnabled(false);
    }
    
    /**
     * Configura los botones en modo ver
     * @author Yamel Senih 05/05/2012, 04:20:20
     * @return void
     */
    protected void seeConfig(){
		//	Enable or disable Buttons
		buttSave.setEnabled(false);
		buttBack.setEnabled(false);
		buttModify.setEnabled(param.isReadWrite());
		buttNew.setEnabled((param.isReadWrite()? param.isInsertRecord(): false));
		buttDelete.setEnabled(param.isReadWrite());
		buttReport.setEnabled(true);
		buttSearch.setEnabled(true);
    }
    
    /**
     * Estado en el que se modifica
     * @author Yamel Senih 06/05/2012, 20:08:33
     * @return void
     */
    protected void modifyConfig(){
		//	Enable or disable Buttons
		buttSave.setEnabled(true);
		buttBack.setEnabled(true);
		buttModify.setEnabled(false);
		buttNew.setEnabled(false);
		buttDelete.setEnabled(false);
		buttReport.setEnabled(false);
		buttSearch.setEnabled(false);
    }
    
    /**
     * Estado en el que no existen registros
     * @author Yamel Senih 06/05/2012, 20:10:22
     * @return void
     */
    protected void voidConfig(){
		//	Enable or disable Buttons
		buttSave.setEnabled(false);
		buttBack.setEnabled(false);
		buttModify.setEnabled(false);
		buttDelete.setEnabled(false);
		buttNew.setEnabled((param.isReadWrite()? param.isInsertRecord(): false));
		buttReport.setEnabled(true);
		buttSearch.setEnabled(true);
    }
    
    /**
     * Establece la visibilidad de los botones
     * @author Yamel Senih 23/05/2012, 00:17:49
     * @param mode
     * @return void
     */
    protected void setVisibleButtons(int mode){
    	buttSave.setVisibility(mode);
		buttBack.setVisibility(mode);
		buttModify.setVisibility(mode);
		buttDelete.setVisibility(mode);
		buttNew.setVisibility(mode);
		buttReport.setVisibility(mode);
		buttSearch.setVisibility(mode);
    }
    
    /**
     * Verifica si la carga de datos tuvo un error y se coloca en modo void
     * @author Yamel Senih 07/05/2012, 14:11:56
     * @return void
     */
    protected void veriload(){
    	MVActivity bp = (MVActivity)getCurrentActivity();
		if(bp != null){
			if(!bp.getLoadOk()){
				voidConfig();
			}
		}
    }
    
    /**
     * Metodo para guardar Registro
     * @author Yamel Senih 13/04/2012, 11:40:15
     * @return void
     */
    protected void e_ButtSave(){
    	MVActivity bp = (MVActivity)getCurrentActivity();
		if(bp != null){
			if(bp.save()){
				seeConfig();
				existsRecord = true;
				record_ID = bp.getRecord_ID();
				Env.setTabRecord_ID(this, bp.getActivityName(), bp.getRecord_ID());
			}
		}
    }
    
    /**
     * Metodo para Buscar un Registro
     * @author Yamel Senih 13/04/2012, 11:44:41
     * @return void
     */
    protected void e_ButtSearch(){
    	MVActivity bp = (MVActivity)getCurrentActivity();
    	DisplayMenuItem item = bp.getMenuItem();
    	Bundle bundle = new Bundle();
		bundle.putParcelable("Item", item);
		//	Intent
		Intent intent = new Intent(this, MV_ListRecord.class);
		intent.putExtras(bundle);
		//	Start
		startActivityForResult(intent, 0);
    }
    
    /**
     * Metodo para Imprimir un Reporte
     * @author Yamel Senih 13/04/2012, 11:44:55
     * @return void
     */
    protected void e_ButtReport(){
    	
    }
    
    /**
     * Metodo Para Salir al Menu Principal
     * @author Yamel Senih 13/04/2012, 11:45:12
     * @return void
     */
    protected void e_ButtModify(){
    	MVActivity bp = (MVActivity)getCurrentActivity();
    	bp.modifyRecord();
    	modifyConfig();
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        int currentTab = Env.getContextAsInt(this, getActivityName());
        //Env.getTabRecord_ID(this, mTabHost.getCurrentTabTag());
        mTabHost.setCurrentTab(currentTab);
        //loaded = Env.isLoadedActivity(this);
        if(!Env.isLoadedActivity(this)){
        	loadView();
        	Env.setIsLoadedActivity(this, true);
        }
        /*if(!lockReadOnly()){
        	if(Env.getTabRecord_ID(this, mTabHost.getCurrentTabTag()) > 0){
    			seeConfig();
    		} else {
    			newConfig();
    		}	
        }*/
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        int currentTab = mTabHost.getCurrentTab();
        //MVActivity bp = (MVActivity)getCurrentActivity();
        Env.setContext(this, getActivityName(), currentTab);
        //Env.setTabRecord_ID(this, getActivityName(), bp.getRecord_ID());
        //Msg.toastMsg(this, "onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        //Msg.toastMsg(this, "onStop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Env.setContext(this, getActivityName(), 0);
    }

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		//Msg.toastMsg(this, "Click");
	}

	/* (non-Javadoc)
	 * @see android.widget.TabHost.OnTabChangeListener#onTabChanged(java.lang.String)
	 */
	@Override
	public void onTabChanged(String tabId) {
		//MVActivity bp = (MVActivity)getCurrentActivity();
		//if(bp != null){
		
		//Msg.toastMsg(this, "*/*/ " + mTabHost.getCurrentTabTag());
		
		if(!Env.isLoadedActivity(this)){
			loadView();
		}
		if(Env.getTabRecord_ID(this, mTabHost.getCurrentTabTag()) > 0){
			seeConfig();
		} else {
			newConfig();
		}
		//}
		//Env.setCurrentTab(this, bp.getActivityName());
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
	    if (resultCode == RESULT_OK) {
	    	if(data != null){
	    		DisplayRecordItem item = (DisplayRecordItem)data.getParcelableExtra("Record");
	    		int record_ID = item.getRecord_ID();
	    		//Msg.toastMsg(this, "** " + record_ID);
	    		if(record_ID > 0){
	    			MVActivity bp = (MVActivity) getCurrentActivity();
	    			seeConfig();
	    			bp.refresh(record_ID);
    				bp.seeConfig();
    				existsRecord = true;
    				//Msg.toastMsg(getApplicationContext(), "Padre");
    			} 
	    	}
	    }
	}
	
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	/*if ((keyCode == KeyEvent.KEYCODE_BACK)) {
    		//cancelOption();
    		return true;
    	}*/
    	return super.onKeyDown(keyCode, event);
    }
	
	protected abstract String getActivityName();
	
}