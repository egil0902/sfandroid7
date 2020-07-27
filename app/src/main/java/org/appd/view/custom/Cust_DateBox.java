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
package org.appd.view.custom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.appd.base.Env;
import org.appd.base.R;
import org.appd.util.Msg;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;

/**
 * @author Yamel Senih
 *
 */
public class Cust_DateBox extends LinearLayout implements OnClickListener, OnDateSetListener {

	/**
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 07/05/2012, 15:57:26
	 * @param context
	 */
	public Cust_DateBox(Context context) {
		super(context);
		ctx = context;
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.cust_date, this);
        init();
	}
	
	/**
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 07/05/2012, 15:57:19
	 * @param context
	 * @param attrs
	 */
	public Cust_DateBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		ctx = context;
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.cust_date, this);
        init();
	}
	
	private ImageButton iButt_Date;
	private EditText et_Date;
    private Context ctx;
    private String formatFront = "dd/MM/yyyy";//"MM-dd-yyyy";
    private String formatBack = "yyyy-MM-dd hh:mm:ss";//"MM-dd-yyyy";
    private Calendar cal;
    
	private void init(){
		isInEditMode();
		//	Load Edit Text
		et_Date = (EditText) findViewById(R.id.et_Date);
		//	Load Button Date
		iButt_Date = (ImageButton) findViewById(R.id.iButt_Date);
		//	Set Values 
		//et_Date.setFocusable(false);
		//et_Date.setFocusableInTouchMode(false);
		//et_Date.setClickable(false);
		
		iButt_Date.setOnClickListener(this);

		// get the current date
        cal = Calendar.getInstance();
		
        // display the current date (this method is below)
        updateDisplay();

	}
	
	/**
	 * Acttualiza la caja de fecha
	 * @author Yamel Senih 07/05/2012, 18:05:34
	 * @return void
	 */
	private void updateDisplay() {
		et_Date.setText(formatDate(cal.getTime()));
    }
    
    private OnDateSetListener mDateSetListener =
    	new OnDateSetListener() {

    		public void onDateSet(DatePicker view, int year, 
    				int monthOfYear, int dayOfMonth) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDisplay();
    		}
    };
    
    /**
     * Crea un dialogo para establecer la fecha
     * @author Yamel Senih 07/05/2012, 17:57:34
     * @return
     * @return Dialog
     */
	private Dialog createDialog() {
		return new DatePickerDialog(ctx,
				mDateSetListener,
				cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH), 
				cal.get(Calendar.DAY_OF_MONTH));
	}
	
	/**
	 * Obtiene la fecha actual
	 * @author Yamel Senih 07/05/2012, 18:24:33
	 * @return
	 * @return String
	 */
	public String getDate(){
		SimpleDateFormat fmtFront=new SimpleDateFormat(formatFront);
        SimpleDateFormat fmtBack=new SimpleDateFormat(formatBack);
        
        String date0 = et_Date.getText().toString();
        Date date;
		try {
			date = fmtFront.parse(date0);
			return fmtBack.format(date);
		} catch (ParseException e) {
			//Log.e("Cust_DateBox", "getDate()", e);
			e.printStackTrace();
		}
		//Date date=new Date(et_Date.getText().toString());
        //SimpleDateFormat fmt=new SimpleDateFormat(formatBack);
        return null;
	}
	
	/**
	 * Obtiene la Fecha con un formato establecido
	 * @author Yamel Senih 20/05/2012, 03:43:40
	 * @param format
	 * @return
	 * @return String
	 */
	public String getDate(String format){
        SimpleDateFormat fmtFront=new SimpleDateFormat(formatFront);
        SimpleDateFormat fmtBack=new SimpleDateFormat(format);
        
        String date0 = et_Date.getText().toString();
        Date date;
		try {
			date = fmtFront.parse(date0);
			return fmtBack.format(date);
		} catch (ParseException e) {
			//Log.e("Cust_DateBox", "getDate(String)", e);
			e.printStackTrace();
		}       
        return null;
    }
	
	/**
	 * Da formato a una Fecha
	 * @author Yamel Senih 20/05/2012, 03:52:07
	 * @param date
	 * @return
	 * @return String
	 */
	private String formatDate(Date date){
		SimpleDateFormat fmt=new SimpleDateFormat(formatFront);
		return fmt.format(date);
	}
	
	/**
	 * Establece la fecha con formato predeterminado
	 * @author Yamel Senih 20/05/2012, 03:55:47
	 * @param date
	 * @return void
	 */
	public void setDate(String date){
		try{
			//SimpleDateFormat sdf = new SimpleDateFormat(formatFront);
			Date currentDate = Env.getDateFormat(date, formatBack, formatFront);//sdf.parse(date);
			cal.setTime(currentDate);
			updateDisplay();
		} catch(Exception e) {
			//Msg.alertMsg(getContext(), "Error", "-- " + e.getMessage());
			//Log.i("Cust_DateBox.setDate()", "", e);
			et_Date.setText("");
		}
	}
	
	/**
	 * Establece una fecha con un formato
	 * @author Yamel Senih 18/07/2012, 14:26:47
	 * @param date
	 * @param format
	 * @return void
	 */
	public void setDate(String date, String format){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date currentDate = /*new Date(date);*/sdf.parse(date);
			cal.setTime(currentDate);
			updateDisplay();
		} catch(Exception e) {
			Msg.alertMsg(getContext(), "Error", "-- " + e.getMessage());
			//Log.i("Cust_DateBox.setDate()", "", e);
			et_Date.setText("");
		}
	}
	
	/**
	 * Establece el formato de visualiazcion de la fecha
	 * @author Yamel Senih 20/05/2012, 04:02:34
	 * @param format
	 * @return void
	 */
	public void setFormat(String format){
		this.formatFront= format; 
	}
	
	/**
	 * Obtiene el formato de visualizacion de fecha
	 * @author Yamel Senih 20/05/2012, 04:03:10
	 * @return
	 * @return String
	 */
	public String getFormat(){
		return formatFront;
	}
	
	@Override
	public void setEnabled(boolean enabled){
		et_Date.setEnabled(enabled);
		iButt_Date.setEnabled(enabled);
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		createDialog().show();
	}

	/* (non-Javadoc)
	 * @see android.app.DatePickerDialog.OnDateSetListener#onDateSet(android.widget.DatePicker, int, int, int)
	 */
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateDisplay();
	}
	
	/**
	 * Establece el DateSetListener
	 * @author Yamel Senih 04/06/2012, 20:49:21
	 * @param mDateSetListener
	 * @return void
	 */
	public void setOnDateSetListener(OnDateSetListener mDateSetListener){
		this.mDateSetListener = mDateSetListener;
	}
}
