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
package org.appd.sync.parameters;


import org.appd.base.DB;
import org.appd.base.Env;
import org.ksoap2.serialization.SoapObject;

import android.content.Context;
import android.database.Cursor;

/**
 * @author Carlos Parada
 *
 */
public class WSParamValues extends SoapObject{

	/**
	 * *** Constructor de la Clase ***
	 * @author Carlos Parada 22/05/2012, 01:43:31
	 * @param ctx
	 * @param NameSpace
	 * @param p_prop
	 * @param Name
	 */
	public WSParamValues(Context ctx, String NameSpace,Integer p_WS_WebServiceType_ID,DB p_con,Cursor rs) {
		super(NameSpace, "ParamValues");
		// TODO Auto-generated constructor stub
		//Cargando Contexto
		m_Ctx=ctx;
		//Id Tipo de servicio
		m_WS_WebServiceType_ID =p_WS_WebServiceType_ID;
		//conexion activa
		m_con = p_con;
		//Datos a enviar
		m_rsData = rs;
		//Creando Campos y Valores a Enviar
		setFields();
	}
	
	/***
	 * @author Carlos Parada 26/05/2012, 21:23:13
	 * Carga los Campos de Entrada del servicio
	 * @param rs
	 * @return void
	 */
	private void setFields()
	{
		
		Cursor rs = m_con.querySQL("select AC.ColumnName,SynchronizeType " +
									" from " + 	
									"WS_WebServiceType WST " + 
									"Inner Join WS_WebServiceFieldInput WSI On WST.WS_WebServiceType_ID = WSI.WS_WebServiceType_ID " +
									"Inner Join AD_Column AC On AC.AD_Column_ID = WSI.AD_Column_ID " +
									"Where WST.WS_WebServiceType_ID=?", new String[]{m_WS_WebServiceType_ID.toString()});
		int i;
		WSField l_field;
		String l_NameColumn;
		if (rs.moveToFirst())
		{
			do 
			{
				l_NameColumn=rs.getString(0);
				//Si La Columna es el ID del Representante de ventas se asigna directamente del contexto
				if(l_NameColumn.equals("SalesRep_ID"))
				{
					l_field = new WSField(getM_Ctx(), getNamespace(),l_NameColumn,Env.getAD_User_ID(m_Ctx));
					//Agregando Campo a Datarow
					addProperty(l_field.getName(), l_field);
				}
				else
				{
					i = m_rsData.getColumnIndex(rs.getString(0));
					if (i!=-1)
					{
						l_field = new WSField(getM_Ctx(), getNamespace(),l_NameColumn,m_rsData.getString(i));
						//Agregando Campo a Datarow
						addProperty(l_field.getName(), l_field);
					}
				}
				
			}while (rs.moveToNext());
		}
		
	}

	/**
	 * @return the m_Ctx
	 */
	public Context getM_Ctx() {
		return m_Ctx;
	}

	//id del servicio
	private Integer m_WS_WebServiceType_ID;
	//Conexion activa
	private DB m_con;
	//Cursor con datos a enviar
	private Cursor m_rsData;
	//Contexto
	private Context m_Ctx;
	
}

