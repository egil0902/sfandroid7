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

import org.appd.base.R;
import org.appd.interfaces.I_CancelOk;
import org.appd.util.DisplayMenuItem;
import org.appd.util.DisplayRecordItem;
import org.appd.util.Msg;
import org.appd.view.custom.Cust_Search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * @author Yamel Senih
 * Modelo
 *
 */
public class MV_AddVisitLine extends Fragment implements I_CancelOk {
	/**	Event Type					*/
	private Cust_Search 	cs_XX_MB_EventType_ID;
	/**	Description					*/
	private EditText 		et_Description;
	/**	Intent						*/
	private Intent			intent;
	
	private Activity 		act;
	
	/**
	 * Constructor
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 23/05/2013, 23:26:48
	 * @param content
	 * @return
	 * @return MV_AddVisitLine
	 */
	public static MV_AddVisitLine newInstance(String content) {
		MV_AddVisitLine fragment = new MV_AddVisitLine();
        return fragment;
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.v_add_visit_line, container, false);
    }
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	act = getActivity();
    	cs_XX_MB_EventType_ID = (Cust_Search) act.findViewById(R.id.cs_XX_MB_EventType_ID);
        et_Description = (EditText) act.findViewById(R.id.et_Description);
        
        cs_XX_MB_EventType_ID.setItem(new DisplayMenuItem(getResources().getString(R.string.XX_MB_VisitLine_ID), 
				null, 0, "XX_MB_EventType", "XX_MB_EventType.XX_BaseEventType NOT IN('SOO', 'MII', 'RMS', 'ARR', 'RDD')", null, "F"));
        cs_XX_MB_EventType_ID.getItem().setIdentifier("Name");
        cs_XX_MB_EventType_ID.setFragment(this);
	}
    
    @Override
    public void onStart() {
        super.onStart();
        // The activity is about to become visible.
    }
    @Override
    public void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
    }
    @Override
    public void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }
    @Override
    public void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }

	/* (non-Javadoc)
	 * @see org.appd.interfaces.I_CancelOk#aceptAction()
	 */
	@Override
	public boolean aceptAction() {
		if(cs_XX_MB_EventType_ID.getID() != 0){
			if(et_Description.getText() != null 
	    			&& et_Description.getText().toString().length() > 0){
				intent = getActivity().getIntent();
				intent.putExtra("XX_MB_EventType_ID", cs_XX_MB_EventType_ID.getID());
				intent.putExtra("Description", et_Description.getText().toString());
				return true;
			} else {
				Msg.alertMustFillField(getActivity(), R.string.Description, et_Description);
			}
		} else {
			Msg.alertMustFillField(getActivity(), R.string.XX_MB_EventType_ID, cs_XX_MB_EventType_ID);
		}
		intent = null;
		return false;
	}

	/* (non-Javadoc)
	 * @see org.appd.interfaces.I_CancelOk#cancelAction()
	 */
	@Override
	public boolean cancelAction() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.appd.interfaces.I_CancelOk#getParam()
	 */
	@Override
	public Intent getParam() {
		return intent;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
	    if (resultCode == Activity.RESULT_OK) {
	    	if(data != null){
	    		DisplayRecordItem item = (DisplayRecordItem)data.getParcelableExtra("Record");
	    		int record_ID = item.getRecord_ID();
	    		if(record_ID > 0){
	    			cs_XX_MB_EventType_ID.setValue(record_ID, item.getCol_2());
    			} 
	    	}
	    }
	}
    
}
