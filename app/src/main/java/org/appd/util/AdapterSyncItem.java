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
 * Contributor(s): Carlos Parada www.erpconsultoresyasociados.com                    *
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
 * @author Carlos Parada
 *
 */
public class AdapterSyncItem extends ArrayAdapter<DisplaySyncItem>{

	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Carlos Parada 31/05/2012
	 * @param ctx
	 * @param textViewResourceId
	 * @param data
	 */
	public AdapterSyncItem(Activity ctx, int id_View, LinkedList<DisplaySyncItem> data) {
		super(ctx, id_View, data);
		this.cxt = ctx;
		this.data = data;
		this.id_View = id_View;
	}
	
	private Activity cxt;
	private LinkedList<DisplaySyncItem> data;
	private int id_View;
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		if(item == null){
			LayoutInflater inflater = cxt.getLayoutInflater();
			item = inflater.inflate(id_View, null);
		}
		
		DisplaySyncItem si = data.get(position);
		
		//	Set Name
		TextView tV_Name = (TextView)item.findViewById(R.id.tV_Name);
		tV_Name.setText(si.getName());
		
		//	Set Description
		TextView tV_Description = (TextView)item.findViewById(R.id.tV_Description);
		tV_Description.setText(si.getDescription());
		
		//	Set Imgage
		ImageView img_Menu = (ImageView)item.findViewById(R.id.img_Sync);
		
		Resources res = cxt.getResources();
		int resID=0;
		if (si.getM_ImgName()!=null)
			resID = res.getIdentifier((!si.getM_ImgName().equals("")?si.getM_ImgName():"download_h") , "drawable", cxt.getPackageName());
		else
			resID = res.getIdentifier("download_h", "drawable", cxt.getPackageName());
		
		if(resID != 0){
			Drawable drawable = res.getDrawable(resID);
			img_Menu.setImageDrawable(drawable);
		}
		 
			
		
		//	Return
		return item;
	}
	
}
