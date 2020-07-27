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
import android.widget.TextView;

/**
 * @author Yamel Senih
 *
 */
public class AdapterInventoryLine extends ArrayAdapter<DisplayOrderItem>{

	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 05/07/2012, 17:19:50
	 * @param ctx
	 * @param data
	 */
	public AdapterInventoryLine(Activity ctx, LinkedList<DisplayOrderItem> data) {
		super(ctx, R.layout.il_customer_inventory_line, data);
		this.cxt = ctx;
		this.data = data;
	}
	
	private Activity cxt;
	private LinkedList<DisplayOrderItem> data;
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		if(item == null){
			LayoutInflater inflater = cxt.getLayoutInflater();
			item = inflater.inflate(R.layout.il_customer_inventory_line, null);
		}
		
		DisplayOrderItem mi = data.get(position);
		
		//	Set Name
		TextView tv_Name = (TextView)item.findViewById(R.id.tV_Name);
		tv_Name.setText(mi.getName());
		
		//	Set Description
		TextView tv_Description = (TextView)item.findViewById(R.id.tV_Description);
		tv_Description.setText(mi.getDescription());
		
		//	Qty On Stock
		TextView tv_XX_QtyOnStock = (TextView)item.findViewById(R.id.tv_XX_QtyOnStock);
		tv_XX_QtyOnStock.setText(mi.getQtyOnStock());
		
		//Log.i("--", "/ " + mi.getQtyOnStock());
		
		//	Set Qty On Rack
		TextView tv_XX_QtyOnRack = (TextView)item.findViewById(R.id.tv_XX_QtyOnRack);
		tv_XX_QtyOnRack.setText(mi.getQtyOnRack());

		//Log.i("--R ", "/ " + mi.getQtyOnRack());
		
		//	Return
		return item;
	}
	
}
