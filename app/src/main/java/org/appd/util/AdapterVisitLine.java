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
package org.appd.util;

import java.util.LinkedList;

import org.appd.base.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Yamel Senih
 *
 */
public class AdapterVisitLine extends ArrayAdapter<DisplayVisitItem>{

	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 19/08/2012, 17:18:54
	 * @param ctx
	 * @param id_View
	 * @param data
	 * @param action
	 */
	public AdapterVisitLine(Activity ctx, int id_View, LinkedList<DisplayVisitItem> data) {
		super(ctx, id_View, data);
		this.cxt = ctx;
		this.data = data;
		this.id_View = id_View;
	}
	
	private Activity cxt;
	private LinkedList<DisplayVisitItem> data;
	private int id_View;
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		if(item == null){
			LayoutInflater inflater = cxt.getLayoutInflater();
			item = inflater.inflate(id_View, null);
		}
		
		DisplayVisitItem mi = data.get(position);
		
		ImageView img_Record = (ImageView)item.findViewById(R.id.img_Record);
		//	Set Columns
		TextView tv_XX_MB_EventTypeValue = (TextView)item.findViewById(R.id.tv_XX_MB_EventTypeValue);
		TextView tv_Description = (TextView)item.findViewById(R.id.tv_Description);
		TextView tv_TimeOfEvent = (TextView)item.findViewById(R.id.tv_TimeOfEvent);
		
		if(mi.getXX_BaseEventType() != null 
				&& mi.getXX_BaseEventType().length() != 0){
			if(mi.getXX_BaseEventType().equals("SOO")){
				img_Record.setImageResource(R.drawable.sales_order_h);	
			} else if(mi.getXX_BaseEventType().equals("MII")){
				img_Record.setImageResource(R.drawable.package_h);	
			} else if(mi.getXX_BaseEventType().equals("RMS")){
				img_Record.setImageResource(R.drawable.return_h);	
			} else if(mi.getXX_BaseEventType().equals("NSO")){
				img_Record.setImageResource(R.drawable.nosales_h);
			} else if(mi.getXX_BaseEventType().equals("ARR")){
				img_Record.setImageResource(R.drawable.collect_money_h);
			} 
		}
		
		tv_XX_MB_EventTypeValue.setText(mi.getEventTypeValue());
		tv_Description.setText(mi.getDescription());
		tv_TimeOfEvent.setText(mi.getName());
		//	Return
		return item;
	}	
}
