/**
 * @finalidad hacer un paquete comun para tres atributos necesarios
 * de la columna a ser mostrada en los reportes o listados previos a actividades
 * @author Yamel Senih
 * @date 30/07/2012
 */
package org.appd.util;

import java.math.BigDecimal;

import org.appd.base.Env;

import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Yamel Senih
 *
 */
public class Att_ColumnRecord  implements Parcelable {

	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 30/07/2012, 21:51:05
	 * @param columnName
	 * @param name
	 * @param type
	 * @param value
	 * @param columnNameDB
	 */
	public Att_ColumnRecord(String columnName, String name, 
			int type, String value, String columnNameDB) {
		this.columnName = columnName;
		this.name = name;
		this.type = type;
		this.columnNameDB = columnNameDB;
		this.summary = Env.ZERO;
		this.tface = Typeface.DEFAULT;
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 30/07/2012, 21:52:45
	 * @param columnName
	 * @param name
	 * @param type
	 * @param value
	 */
	public Att_ColumnRecord(String columnName, String name, 
			int type, String value) {
		this.columnName = columnName;
		this.name = name;
		this.type = type;
		this.columnNameDB = columnName;
		this.summary = Env.ZERO;
		this.tface = Typeface.DEFAULT;
	}

	/**	DB Nanme			*/
	private String		columnName;
	/**	Name				*/
	private String		name;
	/**	Type				*/
	private int		type;
	/**	ColumnName DB		*/
	private String 		columnNameDB;
	/**	Summary				*/
	private BigDecimal	summary = Env.ZERO;
	/**	Type Face			*/
	private Typeface	tface = Typeface.DEFAULT;

	/**
	 * Obtiene el Nombre de la Columna en BD
	 * @author Yamel Senih 30/07/2012, 15:13:34
	 * @return
	 * @return String
	 */
	public String getColumnName(){
		return columnName;
	}
	
	/**
	 * Obtiene la Etiqueta de la Columna
	 * @author Yamel Senih 30/07/2012, 15:14:04
	 * @return
	 * @return String
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Obtiene el Tipo de dato de la columna
	 * @author Yamel Senih 30/07/2012, 15:14:49
	 * @return
	 * @return int
	 */
	public int getType(){
		return type;
	}
	
	/**
	 * Obtiene el Nombre de la cOlumna Real en la BD
	 * @author Yamel Senih 30/07/2012, 21:49:04
	 * @return
	 * @return String
	 */
	public String getColumnNameDB(){
		return columnNameDB;
	}
	
	/**
	 * Agrega un valor al sumario
	 * @author Yamel Senih 04/08/2012, 14:35:58
	 * @param value
	 * @return void
	 */
	public void addSummary(String value){
		try {
			if(value != null 
					&& DisplayType.isNumeric(type)){
				BigDecimal temp = new BigDecimal(value);
				//Log.i("", "tem " + temp);
				summary = summary.add(temp);
				//Log.i("", "summary " + summary);
			}
		} catch (Exception e){
			
		}
	}
	
	/**
	 * Obtiene la suma de los valores
	 * @author Yamel Senih 05/08/2012, 23:41:04
	 * @return
	 * @return String
	 */
	public String getSummary(){
		if(DisplayType.isNumeric(type)){
			//Log.i("", "summary " + summary);
			return summary.toString();
		} else if(DisplayType.isText(type)){
			return "" + (char)931;
		}
		return null;
	}
	
	/**
	 * Establece el valor del objeto que almacena los totales.
	 * @author Yamel Senih 06/08/2012, 11:36:54
	 * @param summary
	 * @return void
	 */
	public void setSummary(BigDecimal summary){
		this.summary = summary;
	}
	
	/**
	 * Establece el tipo de Texto
	 * @author Yamel Senih 06/08/2012, 01:03:09
	 * @param tface
	 * @return void
	 */
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
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public Att_ColumnRecord createFromParcel(Parcel parcel) {
			return new Att_ColumnRecord(parcel);
		}
		public Att_ColumnRecord[] newArray(int size) {
			return new Att_ColumnRecord[size];
		}
	};
	
	@Override
	public int describeContents() {
		return hashCode();
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(columnName);
		parcel.writeString(name);
		parcel.writeInt(type);
		parcel.writeString(columnNameDB);
		//parcel.writeDouble(summary.doubleValue());
		//parcel.writeSerializable(tface);
	}
	
	public void readToParcel(Parcel parcel){
		columnName = parcel.readString();
		name = parcel.readString();
		columnNameDB = parcel.readString();
		type = parcel.readInt();
		//summary = new BigDecimal(parcel.readDouble());
	}

	
	public Att_ColumnRecord(Parcel parcel){
		this(null, null, 0, null);
		readToParcel(parcel);
	}
	
}
