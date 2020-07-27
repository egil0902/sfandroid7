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

import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Yamel Senih
 *
 */
public class DisplayRecordItem implements Parcelable{
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 30/07/2012, 15:34:58
	 */
	public DisplayRecordItem(){
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 23/05/2012, 04:44:21
	 * @param parcel
	 */
	public DisplayRecordItem(Parcel parcel){
		this();
		readToParcel(parcel);
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 30/07/2012, 22:18:09
	 * @param att_img_Record
	 * @param img_Record
	 * @param col_1
	 * @param col_2
	 * @param col_3
	 * @param col_4
	 * @param col_5
	 */
	public DisplayRecordItem(
			Att_ColumnRecord att_img_Record, String img_Record, 
			String col_1, String col_2, String col_3, 
			String col_4, String col_5, int xcale){
		this.record_ID = 0;
		this.att_img_Record = att_img_Record;
		this.img_Record = img_Record;
		this.col_1 = col_1;
		this.col_2 = col_2;
		this.col_3 = col_3;
		this.col_4 = col_4;
		this.col_5 = col_5;
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 30/07/2012, 21:38:21
	 * @param record_ID
	 * @param items
	 * @param img_Record
	 * @param col_1
	 * @param col_2
	 * @param col_3
	 * @param col_4
	 * @param col_5
	 */
	public DisplayRecordItem(int record_ID, 
			Att_ColumnRecord [] items,
			String img_Record,
			String col_1, String col_2, 
			String col_3, String col_4, 
			String col_5){
		this.record_ID = record_ID;
		this.att_img_Record = items[0];
		this.att_col_1 = items[1];
		this.att_col_2 = items[2];
		this.att_col_3 = items[3];
		this.att_col_4 = items[4];
		this.att_col_5 = items[5];
		
		this.img_Record = img_Record;
		this.col_1 = col_1;
		this.col_2 = col_2;
		this.col_3 = col_3;
		this.col_4 = col_4;
		this.col_5 = col_5;
	}
	
	
	/**
	 * Para colocar los Nuevos Registros
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 30/07/2012, 17:32:40
	 * @param record_ID
	 * @param img_Record
	 * @param col_1
	 * @param col_2
	 */
	public DisplayRecordItem(int record_ID, 
			Att_ColumnRecord img_Record, Att_ColumnRecord col_1, 
			Att_ColumnRecord col_2){
		
	}
	
	/**	Record ID			*/
	private int 				record_ID = 0;
	/**	Image				*/
	private Att_ColumnRecord 	att_img_Record = null;
	/**	Column 1			*/
	private Att_ColumnRecord	att_col_1 = null;
	/**	Column 2			*/
	private Att_ColumnRecord 	att_col_2 = null;
	/**	Optional Column		*/
	private Att_ColumnRecord 	att_col_3 = null;
	/**	Optional 1 Column	*/
	private Att_ColumnRecord 	att_col_4 = null;
	/**	Record Type			*/
	private Att_ColumnRecord 	att_col_5 = null;
	
	private String img_Record = null;
	private String col_1 = null;
	private String col_2 = null;
	private String col_3 = null;
	private String col_4 = null;
	private String col_5  = null;
	
	private int from = 0;
	
	private int background = 0;
	private int textColor = 0;
	/**	Type Face			*/
	private Typeface	tface = Typeface.DEFAULT;
	
	
	
	/**
	 * Establece la posicion de procedencia
	 * @author Yamel Senih 08/08/2012, 19:15:45
	 * @param from
	 * @return void
	 */
	public void setFrom(int from){
		this.from = from;
	}
	
	/**
	 * Obtiene la posicion de procedencia
	 * @author Yamel Senih 08/08/2012, 19:16:10
	 * @return
	 * @return int
	 */
	public int getFrom(){
		return from;
	}
	
	
	/**
	 * Establece el color de Fondo
	 * @author Yamel Senih 31/07/2012, 00:32:21
	 * @param background
	 * @return void
	 */
	public void setBackground(int background){
		this.background = background;
	}
	
	/**
	 * Establece el color del Texto
	 * @author Yamel Senih 31/07/2012, 00:33:49
	 * @param textColor
	 * @return void
	 */
	public void setTextColor(int textColor){
		this.textColor = textColor;
	}
	
	/**
	 * Obtiene el color de fondo
	 * @author Yamel Senih 31/07/2012, 00:34:34
	 * @return
	 * @return int
	 */
	public int getBackground(){
		return background;
	}
	
	/**
	 * Obtiene el color del texto
	 * @author Yamel Senih 31/07/2012, 00:35:04
	 * @return
	 * @return int
	 */
	public int getTextColor(){
		return textColor;
	}
	
	public void setTypeFace(Typeface tface){
		this.tface = tface;
	}
	
	/**
	 * Obtiene el tipo de texto
	 * @author Yamel Senih 06/08/2012, 01:07:56
	 * @return
	 * @return Typeface
	 */
	public Typeface getTypeface(){
		return tface;
	}
	
	/**
	 * Obtiene el ID del Item
	 * @author Yamel Senih 28/04/2012, 00:23:13
	 * @return
	 * @return int
	 */
	public int getRecord_ID(){
		return record_ID;
	}
	
	/**
	 * Obtiene el nombre de la Imagen
	 * @author Yamel Senih 28/04/2012, 00:24:59
	 * @return
	 * @return String
	 */
	public Att_ColumnRecord getAtt_Img_Record(){
		return att_img_Record;
	}
	
	/**
	 * Obtiene la Columna 1 del Reporte
	 * @author Yamel Senih 28/04/2012, 00:24:01
	 * @return
	 * @return String
	 */
	public Att_ColumnRecord getAtt_Col_1(){
		return att_col_1;
	}
	
	/**
	 * Obtiene la Columna 2 del Reporte
	 * @author Yamel Senih 30/07/2012, 15:04:31
	 * @return
	 * @return String
	 */
	public Att_ColumnRecord getAtt_Col_2(){
		return att_col_2;
	}
	
	/**
	 * Obtiene la Columna 3 del reporte
	 * @author Yamel Senih 30/07/2012, 15:04:01
	 * @return
	 * @return String
	 */
	public Att_ColumnRecord getAtt_Col_3(){
		return att_col_3;
	}
	
	/**
	 * Obtiene el valor de la Columna 4 del Reporte
	 * @author Yamel Senih 04/06/2012, 23:10:49
	 * @return
	 * @return String
	 */
	public Att_ColumnRecord getAtt_Col_4(){
		return att_col_4;
	}
	
	/**
	 * Obtiene la Columna 5 del reporte
	 * @author Yamel Senih 30/07/2012, 15:03:35
	 * @return
	 * @return String
	 */
	public Att_ColumnRecord getAtt_Col_5(){
		return att_col_5;
	}
	
	public String getImg_Record(){
		return img_Record;
	}
	
	public String getCol_1(){
		return col_1;
	}
	
	public String getCol_2(){
		return col_2;
	}
	
	public String getCol_3(){
		return col_3;
	}
	
	public String getCol_4(){
		return col_4;
	}
	
	public String getCol_5(){
		return col_5;
	}
	
	public static String RT_DOC_DRAFT 		= "DR";
	public static String RT_DOC_COMPLETE 	= "CO";
	public static String RT_DOC_INACTIVE 	= "IN";
	public static String RT_DOC_IN_PROCESS	= "IP";
	public static String RT_DOC_REVERSED 	= "RE";
	public static String RT_DOC_CANCELED	= "VO";
	public static String RT_ACTIVE 		= "Y";
	public static String RT_INACTIVE 		= "N";
	public static String RT_EDITABLE 		= "ED";
	public static String RT_NEW 			= "NW";

	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public DisplayRecordItem createFromParcel(Parcel parcel) {
			return new DisplayRecordItem(parcel);
		}
		public DisplayRecordItem[] newArray(int size) {
			return new DisplayRecordItem[size];
		}
	};
	
	@Override
	public int describeContents() {
		return hashCode();
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(record_ID);
		parcel.writeInt(from);
		parcel.writeString(img_Record);
		parcel.writeString(col_1);
		parcel.writeString(col_2);
		parcel.writeString(col_3);
		parcel.writeString(col_4);
		parcel.writeString(col_5);
	}
	
	public void readToParcel(Parcel parcel){
		record_ID = parcel.readInt();
		from = parcel.readInt();
		col_1 = parcel.readString();
		col_2 = parcel.readString();
		col_3 = parcel.readString();
		col_4 = parcel.readString();
		col_5 = parcel.readString();
	}
	
}
