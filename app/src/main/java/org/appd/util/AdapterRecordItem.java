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

import org.appd.base.Env;
import org.appd.base.R;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
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
public class AdapterRecordItem extends ArrayAdapter<DisplayRecordItem>{

	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 30/07/2012, 01:47:41
	 * @param ctx
	 * @param id_View
	 * @param data
	 * @param action
	 */
	public AdapterRecordItem(Activity ctx, int id_View, LinkedList<DisplayRecordItem> data, String action) {
		super(ctx, id_View, data);
		this.ctx = ctx;
		this.data = data;
		this.id_View = id_View;
		this.action = action;
	}
	
	private Activity ctx;
	private LinkedList<DisplayRecordItem> data;
	private int id_View;
	private String action = "F";
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		if(item == null || action.equals("R")){
			LayoutInflater inflater = ctx.getLayoutInflater();
			item = inflater.inflate(id_View, null);
		}
		
		DisplayRecordItem mi = data.get(position);
		
		if(action.equals("F")
				|| action.equals("L")){
			//	Set Column 1
			TextView tv_col1 = (TextView)item.findViewById(R.id.tv_col1);
			setPreferences(tv_col1, mi.getAtt_Col_1(), mi.getCol_1(), mi.getBackground(), mi.getTextColor());
			//	Set Column 2
			TextView tv_col2 = (TextView)item.findViewById(R.id.tv_col2);
			setPreferences(tv_col2, mi.getAtt_Col_2(), mi.getCol_2(), mi.getBackground(), mi.getTextColor());
			//	Set Column 3
			TextView tv_col3 = (TextView)item.findViewById(R.id.tv_col3);
			setPreferences(tv_col3, mi.getAtt_Col_3(), mi.getCol_3(), mi.getBackground(), mi.getTextColor());
			//	Set Column 4
			TextView tv_col4 = (TextView)item.findViewById(R.id.tv_col4);
			setPreferences(tv_col4, mi.getAtt_Col_4(), mi.getCol_4(), mi.getBackground(), mi.getTextColor());
			//	Set Column 5
			TextView tv_col5 = (TextView)item.findViewById(R.id.tv_col5);
			setPreferences(tv_col5, mi.getAtt_Col_5(), mi.getCol_5(), mi.getBackground(), mi.getTextColor());
			
			ImageView img_Record = (ImageView)item.findViewById(R.id.img_Record);
			
			if(mi.getImg_Record() != null 
					&& mi.getImg_Record().length() != 0){
				if(mi.getAtt_Img_Record().getColumnNameDB() != null && 
						mi.getAtt_Img_Record().getColumnNameDB().equals("ImgName")){
					Resources res = ctx.getResources();
					int resID = res.getIdentifier(mi.getImg_Record() , "drawable", ctx.getPackageName());
					if(resID != 0){
						Drawable drawable = res.getDrawable(resID);
						img_Record.setImageDrawable(drawable);
					}	
				} else if(mi.getAtt_Img_Record().getColumnNameDB() != null && 
						mi.getAtt_Img_Record().getColumnNameDB().equals("IsActive")){
					if(mi.getImg_Record().equals("Y")){
						img_Record.setImageResource(R.drawable.ok_h);
					} else {
						img_Record.setImageResource(R.drawable.cancel_h);
					}
					
				} else if(mi.getAtt_Img_Record().getColumnNameDB() != null && 
						(mi.getAtt_Img_Record().getColumnNameDB().equals("DocAction") 
						|| mi.getAtt_Img_Record().getColumnNameDB().equals("DocStatus"))){
					if(mi.getAtt_Img_Record().getColumnNameDB() != null && 
							mi.getImg_Record().equals(DisplayRecordItem.RT_DOC_CANCELED)){
						img_Record.setImageResource(R.drawable.remove_h);
					} else if(mi.getAtt_Img_Record().getColumnNameDB() != null && 
							mi.getImg_Record().equals(DisplayRecordItem.RT_DOC_DRAFT)){
						img_Record.setImageResource(R.drawable.edit_h);
					} else if(mi.getAtt_Img_Record().getColumnNameDB() != null && 
							mi.getImg_Record().equals(DisplayRecordItem.RT_DOC_COMPLETE)){
						img_Record.setImageResource(R.drawable.doc_completed_h);
					} else if(mi.getAtt_Img_Record().getColumnNameDB() != null && 
							mi.getImg_Record().equals(DisplayRecordItem.RT_DOC_IN_PROCESS)){
						img_Record.setImageResource(R.drawable.doc_progress_h);
					} else if(mi.getAtt_Img_Record().getColumnNameDB() != null && 
							mi.getImg_Record().equals(DisplayRecordItem.RT_DOC_INACTIVE)){
						img_Record.setImageResource(R.drawable.remove_h);
					} else if(mi.getAtt_Img_Record().getColumnNameDB() != null && 
							mi.getImg_Record().equals(DisplayRecordItem.RT_DOC_REVERSED)){
						img_Record.setImageResource(R.drawable.remove_h);
					}
				} else if(mi.getImg_Record().equals(DisplayRecordItem.RT_NEW)){
					img_Record.setImageResource(R.drawable.add_h);
				}
			}
			//Log.i("--", "*** " + mi.getImg_Record() + "//////");
		} else if(action.equals("R")) {
			
			//	Set Imgage
			ImageView img_Record = (ImageView)item.findViewById(R.id.img_Record);
			//	Set Columns
			TextView tv_col1 = (TextView)item.findViewById(R.id.tv_col1);
			TextView tv_col2 = (TextView)item.findViewById(R.id.tv_col2);
			TextView tv_col3 = (TextView)item.findViewById(R.id.tv_col3);
			TextView tv_col4 = (TextView)item.findViewById(R.id.tv_col4);
			TextView tv_col5 = (TextView)item.findViewById(R.id.tv_col5);
			
			//	Image
			if(mi.getImg_Record() != null
					&& mi.getImg_Record().length() > 0){
				img_Record.setVisibility(View.VISIBLE);
			} else {
				img_Record.setVisibility(View.GONE);
			};
			
			//	Col 1
			if(mi.getAtt_Col_1().getName() != null
					&& mi.getAtt_Col_1().getName().length() > 0){
				
				setReportPreferences(tv_col1, mi.getAtt_Col_1(), mi.getCol_1(), mi.getBackground(), mi.getTextColor(), mi.getTypeface());
				
			} else {
				tv_col1.setVisibility(View.GONE);
			}
			//	Col 2
			if(mi.getAtt_Col_2().getName() != null
					&& mi.getAtt_Col_2().getName().length() > 0){
				tv_col2.setText(mi.getCol_2());
				
				setReportPreferences(tv_col2, mi.getAtt_Col_2(), mi.getCol_2(), mi.getBackground(), mi.getTextColor(), mi.getTypeface());
			} else {
				tv_col2.setVisibility(View.GONE);
			}
			//	Col 3
			if(mi.getAtt_Col_3().getName() != null
					&& mi.getAtt_Col_3().getName().length() > 0){
				
				setReportPreferences(tv_col3, mi.getAtt_Col_3(), mi.getCol_3(), mi.getBackground(), mi.getTextColor(), mi.getTypeface());
				
			} else {
				tv_col3.setVisibility(View.GONE);
			}
			//	Col 4
			if(mi.getAtt_Col_4().getName() != null
					&& mi.getAtt_Col_4().getName().length() > 0){
				tv_col4.setVisibility(View.VISIBLE);

				setReportPreferences(tv_col4, mi.getAtt_Col_4(), mi.getCol_4(), mi.getBackground(), mi.getTextColor(), mi.getTypeface());
				
			} else {
				tv_col4.setVisibility(View.GONE);
			}
			//	Col 5
			if(mi.getAtt_Col_5().getName() != null
					&& mi.getAtt_Col_5().getName().length() > 0){
				
				setReportPreferences(tv_col5, mi.getAtt_Col_5(), mi.getCol_5(), mi.getBackground(), mi.getTextColor(), mi.getTypeface());
				
			} else {
				tv_col5.setVisibility(View.GONE);
			}
		}
		
		//	Return
		return item;
	}
	
	/**
	 * Establece las Preferencias del TextView
	 * @author Yamel Senih 31/07/2012, 00:46:29
	 * @param tv
	 * @param att
	 * @param value
	 * @param background
	 * @param textColor
	 * @param tfase
	 * @return void
	 */
	private void setReportPreferences(TextView tv, Att_ColumnRecord att, String value, int background, int textColor, Typeface tfase){
		tv.setVisibility(View.VISIBLE);
		if(DisplayType.isNumeric(att.getType())){
			tv.setGravity(Gravity.CENTER | Gravity.RIGHT);
			tv.setInputType(DisplayType.getInputType(att.getType()));
		}
		if(DisplayType.isDate(att.getType())){
			value = Env.getDateFormatString(value, "yyyy-MM-dd hh:mm:ss", "dd/MM/yyyy");
		}/* else {
			tv.setInputType(DisplayType.getInputType(att.getType()));	
		}*/
		tv.setText(value);
		if(background != 0)
			tv.setBackgroundColor(background);
		if(textColor != 0)
			tv.setTextColor(textColor);
		tv.setTypeface(tfase);
	}
	
	/**
	 * Establece las preferencias de los registros de TextView
	 * @author Yamel Senih 31/07/2012, 01:02:33
	 * @param tv
	 * @param att
	 * @param value
	 * @param background
	 * @param textColor
	 * @return void
	 */
	private void setPreferences(TextView tv, Att_ColumnRecord att, String value, int background, int textColor){
		//tv.setVisibility(View.VISIBLE);
		if(att != null){
			if(DisplayType.isNumeric(att.getType())){
				tv.setGravity(Gravity.CENTER | Gravity.RIGHT);
				tv.setInputType(DisplayType.getInputType(att.getType()));
			}
			if(DisplayType.isDate(att.getType())){
				value = Env.getDateFormatString(value, "yyyy-MM-dd hh:mm:ss", "dd/MM/yyyy");
			} else {
				//tv.setInputType(DisplayType.getInputType(att.getType()));	
			}
		}
		
		tv.setText(value);
		
		if(background != 0)
			tv.setBackgroundColor(background);
		if(textColor != 0)
			tv.setTextColor(textColor);
	}
	
}
