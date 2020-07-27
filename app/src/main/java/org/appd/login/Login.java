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
 * Copyright (C) 2012-2013 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.appd.login;

import org.appd.base.DB;
import org.appd.base.Env;
import org.appd.base.R;
import org.appd.interfaces.I_Login;
import org.appd.util.Msg;
import org.appd.view.MV_Menu;
import org.appd.view.VT_CancelOk;

import test.LoadInitData;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment; 
import android.view.Menu;
import android.view.MenuItem;

/**
 * This demonstrates how you can implement switching between the tabs of a
 * TabHost through fragments, using FragmentTabHost.
 */
public class Login extends VT_CancelOk {
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        addFagment(V_Login.class, "Conn", R.string.tt_Conn);
        addFagment(V_Role.class, "LoginRole", R.string.tt_LoginRole);
        
    	//*/
    	//CreatePDFTest.GenerarPDF(this);
    	setPagingEnabled(false);
    	// Validating SD card
    	if(Env.isEnvLoad(this)){
    		if(!Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
    			if(!Env.getDB_PathName(this).equals(DB.DB_NAME)){
    				Msg.alertMsg(this, getResources().getString(R.string.msg_SDNotFound), 
    						getResources().getString(R.string.msg_SDNotFoundDetail));
    				finish();
        		}	
    		}
    		//	Load Language
    		String language = Env.getAD_Language(this);
    		if(language != null) {
    			Env.changeLanguage(this, language);
    		}
    		else {
    			language = Env.getSOLanguage(this);
    			Env.setAD_Language(this, language);
    		}
    		
    	} else {
    		LoadInitData initData = new LoadInitData(this);
    		initData.initialLoad_copyDB();
    	}
    }  
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem item = menu.getItem(0);
        item.setVisible(true);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        	case R.id.action_config:
        		Intent intent = new Intent(this, V_Connection.class);
				startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    /**
     * Process OK Action
     * @author Yamel Senih 24/04/2012, 18:03:59
     * @return void
     */
    @Override
    protected void processActionOk(){
    	I_Login fr = (I_Login)getCurrentFragment();
    	boolean ret = fr.acceptAction();
		if(getCurrentFragment() instanceof V_Login){
			if(ret){
				if(!Env.isEnvLoad(this)){
					Intent intent = new Intent(this, V_Connection.class);
					startActivity(intent);
				} else {
					//	Is Logged
					if(Env.isLogin(this)){
						//pager.getChildAt(2).setEnabled(true);
						setCurrentFragment(2);
						fr = (I_Login)getCurrentFragment();
						ret = fr.loadData();	
					} else {
						//pager.getChildAt(2).setEnabled(true);
					}
				}
			}
		} else if(getCurrentFragment() instanceof V_Role){
			if(ret){
				Intent intent = new Intent(this, MV_Menu.class);
				startActivity(intent);
			//	Msg.toastMsg(getApplication(), "Hi");  -- red1 set these lines to previous version
			} else {
				
			}
		}
    }
    
    /**
     * Process Cancel Action
     * @author Yamel Senih 24/04/2012, 18:05:05
     * @return void
     */
    @Override
    protected void processActionCancel(){
    	/*Builder ask = Msg.confirmMsg(this, getResources().getString(R.string.msg_InIsOut));
    	String msg_Acept = this.getResources().getString(R.string.msg_Yes);
    	ask.setPositiveButton(msg_Acept, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				finish();
			}
		});
    	ask.show();*/
    	finish();
    }
    
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(!Env.isEnvLoad(this)){ //red1 ensure if no User data yet means initialLoad needed 
        	setCurrentFragment(0);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        /*int currentTab = mTabHost.getCurrentTab();
        Env.setContext(this, KEY_POS_TAB, currentTab);*/
    }
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int arg0) {
		
	}
}
