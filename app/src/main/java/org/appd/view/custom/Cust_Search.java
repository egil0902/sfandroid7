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

import org.appd.base.DB;
import org.appd.base.R;
import org.appd.model.MPTableInfo;
import org.appd.util.DisplayMenuItem;
import org.appd.view.MV_ListRecord;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * @author Yamel Senih
 *
 */
public class Cust_Search extends LinearLayout implements OnClickListener {

	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 07/08/2012, 19:44:23
	 * @param context
	 */
	public Cust_Search(Context context) {
		super(context);
		ctx = context;
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.cust_search, this);
        init();
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 07/08/2012, 19:44:18
	 * @param context
	 * @param attrs
	 */
	public Cust_Search(Context context, AttributeSet attrs) {
		super(context, attrs);
		ctx = context;
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.cust_search, this);
        init();
	}
	
	private ImageButton iButt_Search;
	private EditText et_Search;
    private Context ctx;
    private DisplayMenuItem item;
    private Activity act;
    private Fragment fr;
    /**	ID		*/
    private int id;
    /**	Value	*/
    private String value;
    /**	Table Info	*/
    private MPTableInfo tInfo = null;
    private DB con;
    private StringBuffer sql = null;
    
    
	private void init(){
		isInEditMode();
		//	Load Edit Text
		et_Search = (EditText) findViewById(R.id.et_Seach);
		//	Load Button Search
		iButt_Search = (ImageButton) findViewById(R.id.iButt_Search);
		
		iButt_Search.setOnClickListener(this);

		// display the current date (this method is below)
        updateDisplay();

	}
	
	/**
	 * Actualiza el Edit Text
	 * @author Yamel Senih 07/08/2012, 19:49:52
	 * @return void
	 */
	private void updateDisplay() {
		et_Search.setText(value);
    }
	
	/**
	 * Establece el parámetro donde se define la tabla
	 * de busqueda
	 * @author Yamel Senih 07/08/2012, 20:07:26
	 * @param item
	 * @return void
	 */
	public void setItem(DisplayMenuItem item){
		this.item = item;
	}
	
	/**
	 * Establece la actividad desde donde es llamado
	 * @author Yamel Senih 07/08/2012, 20:14:29
	 * @param act
	 * @return void
	 */
	public void setActivity(Activity act){
		this.act = act;
	}
	
	/**
	 * Establece la actividad desde donde es llamado
	 * @author Yamel Senih 24/05/2013, 00:25:50
	 * @param act
	 * @return void
	 */
	public void setFragment(Fragment fr){
		this.fr = fr;
	}
	
	/**
	 * Obtiene el Objeto Item
	 * @author Yamel Senih 08/08/2012, 19:31:14
	 * @return
	 * @return DisplayMenuItem
	 */
	public DisplayMenuItem getItem(){
		return item;
	}
	
	/**
	 * Metodo para Buscar Registros
	 * @author Yamel Senih 07/08/2012, 20:06:20
	 * @return void
	 */
	protected void e_ButtSearch(){
    	if(item != null){
    		Bundle bundle = new Bundle();
    		bundle.putParcelable("Item", item);
    		//	Intent
    		Intent intent = new Intent(ctx, MV_ListRecord.class);
    		intent.putExtras(bundle);
    		//	Start
    		if(act != null)
    			act.startActivityForResult(intent, 0);
    		else if(fr != null)
    			fr.startActivityForResult(intent, 0);
    	}
    }
	
	@Override
	public void setEnabled(boolean enabled){
		et_Search.setEnabled(enabled);
		iButt_Search.setEnabled(enabled);
	}
	
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		e_ButtSearch();
	}
	
	/**
	 * Obtiene el ID del Registro
	 * @author Yamel Senih 10/05/2012, 17:57:52
	 * @return
	 * @return int
	 */
	public int getID(){
		return id;
	}
	
	/**
	 * Obtiene el valor del Spinner
	 * @author Yamel Senih 10/05/2012, 17:59:10
	 * @return
	 * @return String
	 */
	public String getValue(){
		return value;
	}
	
	/**
	 * Establece el valor del registro incluyendo su ID
	 * @author Yamel Senih 07/08/2012, 20:28:35
	 * @param id
	 * @param value
	 * @return void
	 */
	public void setValue(int id, String value){
		this.id = id;
		this.value = value;
		updateDisplay();
	}
	
	/**
	 * Hace una Consulta con el filtro de busqueda ID
	 * @author Yamel Senih 07/08/2012, 20:30:20
	 * @param con
	 * @param id
	 * @return void
	 */
	public void setID(DB con, int id){
		
	}
	
	/**
	 * Hace una Consulta con el filtro de busqueda ID
	 * manejando la conexion
	 * @author Yamel Senih 07/08/2012, 20:31:08
	 * @param id
	 * @return void
	 */
	public void setRecord_ID(int id){
		if(id != 0){
			String sql = getSQL(id);
			if(con == null){
				con = new DB(ctx);
			}
			Cursor rs = con.querySQL(sql + " LIMIT 1", null);
			if(rs.moveToFirst()){
				setValue(id, rs.getString(0));
			}
		} else {
			setValue(0, "");
		}
	}
	
	/**
	 * Establece la Conexion
	 * @author Yamel Senih 08/08/2012, 23:42:52
	 * @param con
	 * @return void
	 */
	public void setConnection(DB con){
		this.con = con;
	}
	/**
	 * Genera el SQL ustilizado para la Consulta
	 * @author Yamel Senih 08/08/2012, 23:32:14
	 * @return
	 * @return String
	 */
	private String getSQL(int id){
		if(item != null){
			if(sql == null){
				sql = new StringBuffer();
				String sqlWhere = new String("(AD_Column.ColumnName IN('DocumentNo', 'Name', 'Description', 'DocAction', 'IsActive', 'ImgName') " +
				    	"OR AD_Column.IsIdentifier = 'Y') ");
				    String sqlOrderBy = new String("AD_Column.SeqNo, AD_Column.Name");
				sql.append("SELECT ");
				if(item.getIdentifier() != null){
					sql.append(item.getIdentifier() + " ");
				} else {
					if(tInfo == null){
						if(con != null){
							tInfo = new MPTableInfo(con, item.getAD_Table_ID(), item.getTableName(), sqlWhere, sqlOrderBy, false);
						} else {
							if(con == null){
								con = new DB(ctx);
							}
							tInfo = new MPTableInfo(con, item.getAD_Table_ID(), item.getTableName(), sqlWhere, sqlOrderBy, true);
						}	
					}
					
					for(int i = 0; i < tInfo.getColumnLength(); i++){
						if(i > 0)
							sql.append(" || ");
						sql.append("IFNULL(" + MPTableInfo.getColumnNameForSelect(ctx, tInfo, i) + ", '')");
					}
				}
				
				sql.append(" FROM " + item.getTableName());
				sql.append(" WHERE " + item.getTableName() + "_ID = " + id);
			}
		}
		//Msg.toastMsg(ctx, " -- " + sql.toString());
		return sql.toString();
	}
	
	/**
	 * Obtiene el boton que muestra el listado de la busqueda
	 * @author Yamel Senih 09/08/2012, 17:36:47
	 * @return
	 * @return ImageButton
	 */
	public ImageButton getButton(){
		return iButt_Search;
	}
	
}
