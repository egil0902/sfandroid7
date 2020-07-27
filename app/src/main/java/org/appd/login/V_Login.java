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


import java.util.ArrayList;
import java.util.Locale;

import org.appd.base.DB;
import org.appd.base.Env;
import org.appd.base.R;
import org.appd.interfaces.I_Login;
import org.appd.util.DisplaySpinner;
import org.appd.util.Msg;
import org.appd.view.custom.Cust_Spinner;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

public class V_Login extends Fragment implements I_Login {
	private EditText 		et_User;
	private EditText 		et_Pass;
	private CheckBox 		ch_SavePass;
	private CheckBox 		ch_AutomaticVisitClosing;
	private Cust_Spinner	sp_Language;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.v_login, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	et_User = (EditText) getActivity().findViewById(R.id.et_User);
    	et_Pass = (EditText) getActivity().findViewById(R.id.et_Pass);
    	ch_SavePass = (CheckBox) getActivity().findViewById(R.id.ch_SavePass);
    	ch_AutomaticVisitClosing = (CheckBox) getActivity().findViewById(R.id.ch_AutomaticVisitClosing);
    	sp_Language = (Cust_Spinner) getActivity().findViewById(R.id.sp_Language);
    	
    	ArrayList <DisplaySpinner> data = new ArrayList<DisplaySpinner>();
    	for(Locale loc : Locale.getAvailableLocales()){
    		data.add(new DisplaySpinner(0, loc.getDisplayName(), loc.toString()));
    	}
		sp_Language.load(data);
    	
    }
    
    /**
     * Reload Activity
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 07/02/2013, 18:56:16
     * @param language
     * @return void
     */
    private void reloadLanguage(String language){
    	Env.changeLanguage(getActivity(), language);
    	Intent refresh = new Intent(getActivity(), Login.class);
		startActivity(refresh);
		getActivity().finish();
    }
    
    /**
     * Valid User and Password
     * @author Yamel Senih 04/02/2013, 19:36:24
     * @return
     * @return boolean
     */
    private boolean validUser() {
    	String user = et_User.getText().toString().trim();
    	String pass = et_Pass.getText().toString().trim();
    	if(user != null && user.length() > 0){
    		if(pass != null && pass.length() > 0){
    			Env.setContext(this.getActivity(), "#SUser", user);
    			Env.setContext(this.getActivity(), "#SPass", pass);
    			Env.setSavePass(this.getActivity(), ch_SavePass.isChecked());
    			Env.setAutomaticVisitClosing(this.getActivity(), ch_AutomaticVisitClosing.isChecked());
    			String language = (String)((DisplaySpinner)sp_Language.getSelectedItem()).getHiddenValue();
    			if(!language.equals(Env.getAD_Language(getActivity()))){
    				Env.setAD_Language(getActivity(), language);
    				reloadLanguage(language);
    			}
    			if(!Env.isEnvLoad(this.getActivity()))
    				return true;
    			else if(findUser(user, pass)){
    				return true;
    			} else {
    				Msg.alertMsg(this.getActivity(), getResources().getString(R.string.msg_ValidError), 
    						getResources().getString(R.string.msg_UserPassDoNotMatch));
    			}
    		} else {
    			Msg.alertMustFillField(this.getActivity(), R.string.Pass, et_Pass);
    		}
    	} else {
    		Msg.alertMustFillField(this.getActivity(), R.string.User, et_User);
    	}
    	return false;
    }
    
    /**
     * Find User on database
     * @author Yamel Senih 04/02/2013, 19:38:06
     * @param user
     * @param pass
     * @return
     * @return boolean
     */
    private boolean findUser(String user, String pass){
    	boolean ok = false;
    	DB con = new DB(this.getActivity());
    	con.openDB(DB.READ_ONLY);
    	String sql = "SELECT u.AD_User_ID " +
    			"FROM AD_User u " +
    			"WHERE u.Name = ? AND u.PassWord = ?";
    	Cursor rs = con.querySQL(sql, new String[]{user, pass});
    	//red1 check for no records not wrong login
    	if (!rs.moveToNext()) return true;
    	if(rs.moveToFirst()){
    		Env.setAD_User_ID(this.getActivity(), rs.getInt(0));
    		Env.setIsLogin(this.getActivity(), true);
    		ok = true;
    	} else {
    		Env.setIsLogin(this.getActivity(), false);
    	}
    	con.closeDB(rs);
    	return ok;
    }
    
    /**
     * Valid Exit
     * @author Yamel Senih 04/02/2013, 19:39:16
     * @return
     * @return boolean
     */
    private boolean validExit() {
    	return true;
    }
    
    @Override
    public void onStart() {
    	super.onStart();
    	//red1 for fast testing purpose
    	et_User.setText("zona12");
    	et_Pass.setText("zona12");
        String user = et_User.getText().toString();
     	String pass = et_Pass.getText().toString();
     	//boolean isSavePass = ch_SavePass.isChecked();
     	if(user == null || user.length() == 0){
     		user = Env.getContext(this.getActivity(), "#SUser");
     		if(user != null)
     			et_User.setText(user);
     	}
     	
     	//	Remember Password check
     	ch_SavePass.setChecked(Env.isSavePass(this.getActivity()));
     	//	Automatic Closed Visits Remember
     	ch_AutomaticVisitClosing.setChecked(Env.isAutomaticVisitClosing(this.getActivity()));
     	
     	//	Remember the password
 		if(pass == null || pass.length() == 0){
 			if(Env.isSavePass(this.getActivity())){
 				pass = Env.getContext(this.getActivity(), "#SPass");
 				if(pass != null)
 					et_Pass.setText(pass);
 			}
 			
 		}
 		//	Select Language
 		String language = Env.getAD_Language(getActivity());
 		if(language != null
 				&& language.length() != 0){
 			sp_Language.setSelectedHiddenValue(language);
 		} else
 			sp_Language.setSelectedHiddenValue(Env.BASE_LANGUAGE);
 			
    }
    
	@Override
	public boolean acceptAction() {
		return validUser();
	}

	@Override
	public boolean cancelAction() {
		return validExit();
	}

	@Override
	public boolean loadData() {
		return false;
	}
    
}
