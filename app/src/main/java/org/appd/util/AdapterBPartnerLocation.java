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
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Yamel Senih
 *
 */
public class AdapterBPartnerLocation extends ArrayAdapter<DisplayBPLocationItem>{

	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 26/07/2012, 20:28:45
	 * @param ctx
	 * @param data
	 */
	public AdapterBPartnerLocation(Activity ctx, LinkedList<DisplayBPLocationItem> data) {
		super(ctx, R.layout.il_bpartner_location, data);
		this.ctx = ctx;
		this.data = data;
	}
	
	private Activity ctx;
	private LinkedList<DisplayBPLocationItem> data;
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		if(item == null){
			LayoutInflater inflater = ctx.getLayoutInflater();
			item = inflater.inflate(R.layout.il_bpartner_location, null);
		}
		
		DisplayBPLocationItem mi = data.get(position);
		
		LinearLayout il_Phone = (LinearLayout)item.findViewById(R.id.il_Phone);
		LinearLayout il_Phone2 = (LinearLayout)item.findViewById(R.id.il_Phone2);
		LinearLayout il_Fax = (LinearLayout)item.findViewById(R.id.il_Fax);
		LinearLayout il_Region = (LinearLayout)item.findViewById(R.id.il_Region);
		LinearLayout il_City = (LinearLayout)item.findViewById(R.id.il_City);
		LinearLayout il_Address1 = (LinearLayout)item.findViewById(R.id.il_Address1);
		LinearLayout il_Address2 = (LinearLayout)item.findViewById(R.id.il_Address2);
		LinearLayout il_Address3 = (LinearLayout)item.findViewById(R.id.il_Address3);
		LinearLayout il_Address4 = (LinearLayout)item.findViewById(R.id.il_Address4);
		LinearLayout il_Latitude = (LinearLayout)item.findViewById(R.id.il_Latitude);
		LinearLayout il_Longitude = (LinearLayout)item.findViewById(R.id.il_Longitude);
		//LinearLayout il_IsBillTo = (LinearLayout)item.findViewById(R.id.il_IsBillTo);
		//LinearLayout il_IsShipTo = (LinearLayout)item.findViewById(R.id.il_IsShipTo);
		
		//	Set Name
		TextView tv_Name = (TextView)item.findViewById(R.id.tv_Name);
		tv_Name.setText(mi.getName());
		//	Phone
		if(mi.getPhone() !=null && mi.getPhone().length() > 0){
			TextView tv_Phone = (TextView)item.findViewById(R.id.tv_v_Phone);
			tv_Phone.setText(mi.getPhone());
			il_Phone.setVisibility(View.VISIBLE);
		} else {
			il_Phone.setVisibility(View.GONE);
		}
		//	Phone 2
		if(mi.getPhone2() !=null && mi.getPhone2().length() > 0){
			TextView tv_Phone2 = (TextView)item.findViewById(R.id.tv_v_Phone2);
			tv_Phone2.setText(mi.getPhone2());
			il_Phone2.setVisibility(View.VISIBLE);
		} else {
			il_Phone2.setVisibility(View.GONE);
		}
		//	Fax
		if(mi.getFax() !=null && mi.getFax().length() > 0){
			TextView tv_Fax = (TextView)item.findViewById(R.id.tv_v_Fax);
			tv_Fax.setText(mi.getFax());
			il_Fax.setVisibility(View.VISIBLE);
		} else {
			il_Fax.setVisibility(View.GONE);
		}
		//	Region
		if(mi.getRegion() !=null && mi.getRegion().length() > 0){
			TextView tv_Region = (TextView)item.findViewById(R.id.tv_v_Region);
			tv_Region.setText(mi.getRegion());	
			il_Region.setVisibility(View.VISIBLE);
		} else {
			il_Region.setVisibility(View.GONE);
		}
		//	City
		if(mi.getCity() !=null && mi.getCity().length() > 0){
			TextView tv_City = (TextView)item.findViewById(R.id.tv_v_City);
			tv_City.setText(mi.getCity());	
			il_City.setVisibility(View.VISIBLE);
		} else {
			il_City.setVisibility(View.GONE);
		}
		//	Address 1
		if(mi.getAddress1() !=null && mi.getAddress1().length() > 0){
			TextView tv_Address1 = (TextView)item.findViewById(R.id.tv_v_Address1);
			tv_Address1.setText(mi.getAddress1());	
			il_Address1.setVisibility(View.VISIBLE);
		} else {
			il_Address1.setVisibility(View.GONE);
		}
		//	Address 2
		if(mi.getAddress2() !=null && mi.getAddress2().length() > 0){
			TextView tv_Address2 = (TextView)item.findViewById(R.id.tv_v_Address2);
			tv_Address2.setText(mi.getAddress2());	
			il_Address2.setVisibility(View.VISIBLE);
		} else {
			il_Address2.setVisibility(View.GONE);
		}
		//	Address 3
		if(mi.getAddress3() !=null && mi.getAddress3().length() > 0){
			TextView tv_Address3 = (TextView)item.findViewById(R.id.tv_v_Address3);
			tv_Address3.setText(mi.getAddress3());	
			il_Address3.setVisibility(View.VISIBLE);
		} else {
			il_Address3.setVisibility(View.GONE);
		}
		//	Address 4
		if(mi.getAddress4() !=null && mi.getAddress4().length() > 0){
			TextView tv_Address4 = (TextView)item.findViewById(R.id.tv_v_Address4);
			tv_Address4.setText(mi.getAddress4());	
			il_Address4.setVisibility(View.VISIBLE);
		} else {
			il_Address4.setVisibility(View.GONE);
		}
		//	Latitude
		if(mi.getLatitude() !=null && mi.getLatitude().length() > 0){
			TextView tv_Latitude = (TextView)item.findViewById(R.id.tv_v_Latitude);
			tv_Latitude.setText(mi.getLatitude());	
			il_Latitude.setVisibility(View.VISIBLE);
		} else {
			il_Latitude.setVisibility(View.GONE);
		}
		//	Longitude
		if(mi.getLongitude() !=null && mi.getLongitude().length() > 0){
			TextView tv_Longitude = (TextView)item.findViewById(R.id.tv_v_Longitude);
			tv_Longitude.setText(mi.getLongitude());	
			il_Longitude.setVisibility(View.VISIBLE);
		} else {
			il_Longitude.setVisibility(View.GONE);
		}
		//	Is Billding To
		TextView tv_IsBillTo = (TextView)item.findViewById(R.id.tv_v_IsBillTo);
		if(mi.isBillTo()){
			tv_IsBillTo.setText(ctx.getResources().getString(R.string.msg_Yes));
			//il_IsBillTo.setVisibility(View.VISIBLE);
		} else {
			//il_IsBillTo.setVisibility(View.GONE);
			tv_IsBillTo.setText(ctx.getResources().getString(R.string.msg_No));
		}
		//	Is Sihipment To
		TextView tv_IsShipTo = (TextView)item.findViewById(R.id.tv_v_IsShipTo);
		if(mi.isBillTo()){
			tv_IsShipTo.setText(ctx.getResources().getString(R.string.msg_Yes));
			//il_IsShipTo.setVisibility(View.VISIBLE);
		} else {
			tv_IsShipTo.setText(ctx.getResources().getString(R.string.msg_No));
			//il_IsShipTo.setVisibility(View.GONE);
		}
		
		//	Return
		return item;
	}
	
}
