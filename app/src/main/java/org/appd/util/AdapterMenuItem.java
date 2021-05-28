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
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
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
public class AdapterMenuItem extends ArrayAdapter<DisplayMenuItem>{

	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 28/04/2012, 00:45:59
	 * @param ctx
	 * @param id_View
	 * @param data
	 */
	public AdapterMenuItem(Activity ctx, int id_View, LinkedList<DisplayMenuItem> data) {
		super(ctx, id_View, data);
		this.cxt = ctx;
		this.data = data;
		this.id_View = id_View;
	}
	
	private Activity cxt;
	private LinkedList<DisplayMenuItem> data;
	private int id_View;
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		if(item == null){
			LayoutInflater inflater = cxt.getLayoutInflater();
			item = inflater.inflate(id_View, null);
		}
		
		DisplayMenuItem mi = data.get(position);
		
		//	Set Name
		TextView tV_Name = (TextView)item.findViewById(R.id.tV_Name);
		tV_Name.setText(mi.getName());
		
		//	Set Description
		TextView tV_Description = (TextView)item.findViewById(R.id.tV_Description);
		tV_Description.setText(mi.getDescription());
		
		//	Set Imgage
		ImageView img_Menu = (ImageView)item.findViewById(R.id.img_Menu);
		
		if(mi.getImg() != null 
				&& mi.getImg().length() > 0){
			Resources res = cxt.getResources();
			int resID = res.getIdentifier(mi.getImg() , "drawable", cxt.getPackageName());
			if(resID != 0){
				Drawable drawable = res.getDrawable(resID);
				img_Menu.setImageDrawable(drawable);
			}
		} else {
			if(mi.getAction().equals("F")){
				img_Menu.setImageResource(R.drawable.contruction_h);
			}
		}
			
		
		//	Return
		return item;
	}
	
}
