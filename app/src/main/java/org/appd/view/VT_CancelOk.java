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
package org.appd.view;

import org.appd.base.R;
import org.appd.login.Login;
import org.appd.util.AdapterFragmentTabs;
import org.appd.util.DisplayMenuItem;
import org.appd.view.custom.Cust_ViewPager;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

/**
 * 
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public abstract class VT_CancelOk extends FragmentActivity 
									implements ViewPager.OnPageChangeListener {
	
	/**	Adapter Fragment			*/
	private AdapterFragmentTabs		mAdapterList;
	/**	Pager						*/
	private Cust_ViewPager			mViewPager;
	/**	Resources					*/
	protected Resources 			mResources;
	/**	Params						*/
	protected DisplayMenuItem 		param;
	
	TabHost mTabHost;
    
    /** Called when the activity is first created. */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	super.setContentView(R.layout.t_cancel_ok);
    	Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			param = (DisplayMenuItem)bundle.getParcelable("Param");
		}
    	
		ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
		
    	mResources = getResources();
    	
    	mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();
    	
        
        
    	mViewPager = (Cust_ViewPager)findViewById(R.id.v_pager);
        mAdapterList = new AdapterFragmentTabs(this, mTabHost, mViewPager);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_cancel_ok, menu);

        // Add either a "next" or "finish" button to the action bar, depending on which page
        // is currently selected.
        /*MenuItem item = menu.add(Menu.NONE, R.id.action_next, Menu.NONE,
                (mPager.getCurrentItem() == mPagerAdapter.getCount() - 1)
                        ? R.string.action_finish
                        : R.string.action_next);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);*/
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Navigate "up" the demo structure to the launchpad activity.
                // See http://developer.android.com/design/patterns/navigation.html for more.
                NavUtils.navigateUpTo(this, new Intent(this, Login.class));
                return true;

            case R.id.action_ok:
                // Go to the previous step in the wizard. If there is no previous step,
                // setCurrentItem will do nothing.
                processActionOk();
                return true;

            case R.id.action_cancel:
                // Advance to the next step in the wizard. If there is no next step, setCurrentItem
                // will do nothing.
                processActionCancel();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    /**
     * Add Fragment Tab in FragmentActivity
     * @author Yamel Senih 03/05/2012, 19:08:05
     * @param clazz
     * @param bundle
     * @param title
     * @param bundle
     * @return void
     */
    protected void addFagment(Class<?> clazz, String tag, int title, Bundle bundle){
    	mAdapterList.addTab(clazz, tag, title, bundle);
    	//Env.setTabRecord_ID(this, mAdapterList.makeFragmentName(mViewPager, mViewPager.getCurrentItem()), 0);
    }
    
    /**
     * Add Fragment Tab in FragmentActivity
     * @author Yamel Senih 05/02/2013, 16:58:49
     * @param clazz
     * @param title
     * @return void
     */
    protected void addFagment(Class<?> clazz, String tag, int title){
    	mAdapterList.addTab(clazz, tag, title, null);
    	//Env.setTabRecord_ID(this, mAdapterList.makeFragmentName(mViewPager, mViewPager.getCurrentItem()), 0);
    }
    
    /**
     * Get Current Fragment from View Pager
     * @author Yamel Senih 05/02/2013, 17:11:23
     * @return
     * @return Fragment
     */
    protected Fragment getCurrentFragment() {
		return mAdapterList.getCurrentFragment(mViewPager);
	}
    
    /**
     * Get Fragment from index
     * @author Yamel Senih 05/02/2013, 17:12:46
     * @param index
     * @return
     * @return Fragment
     */
    protected Fragment getFragment(int index) {
		return mAdapterList.getActiveFragment(index, mViewPager);
	}
    
    protected void setCurrentFragment(int index) {
		mViewPager.setCurrentItem(index);
	}
    
    /**
     * Ok Action
     * @author Yamel Senih 24/04/2012, 18:03:59
     * @return void
     */
    protected abstract void processActionOk();
    
    /**
     * Cancel Action
     * @author Yamel Senih 24/04/2012, 18:05:05
     * @return void
     */
    protected abstract void processActionCancel();
    
    @Override
    protected void onResume() {
        super.onResume();
        //setCurrentFragment(Env.getContextAsInt(this, KEY_POS_TAB));
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        int currentTab = mViewPager.getCurrentItem();
        //Env.setContext(this, KEY_POS_TAB, currentTab);
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
    
    /**
     * Enabled ViewPager Navigation
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/06/2013, 17:29:58
     * @param enabled
     * @return void
     */
    public void setPagingEnabled(boolean enabled){
    	mViewPager.setPagingEnabled(enabled);
    }
    
}