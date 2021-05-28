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

import java.util.ArrayList;
import java.util.Hashtable;
import org.ksoap2.serialization.AttributeContainer;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import android.content.Context;
import android.database.Cursor;

/**
 * @author Carlos Parada
 *
 */
@SuppressWarnings("rawtypes")
public class WSListParameters extends AttributeContainer implements KvmSerializable{

	
	/**
	 * *** Constructor de la Clase ***
	 * @author Carlos Parada 20/05/2012, 17:20:13
	 */
	public WSListParameters(Context ctx,String NameSpace,String Name) {
		// TODO Auto-generated constructor stub
		m_ctx=ctx;
		m_namespace = NameSpace;
		m_Name = Name;
	}
	
	/**
	 * *** Constructor de la Clase ***
	 * @author Carlos Parada 20/05/2012, 17:20:13
	 * Crea Objeto a Partir de Cursor
	 */
	public WSListParameters(Context ctx,String NameSpace,Cursor p_prop,String Name) {
		// TODO Auto-generated constructor stub
		m_ctx=ctx;
		m_namespace = NameSpace;
		m_Name = Name;
		setProperties(p_prop);
	}

	/**
	 * *** Constructor de la Clase ***
	 * @author Carlos Parada 20/05/2012, 17:20:13
	 * Crea Objeto a partir de hashmap
	 */
	public WSListParameters(Context ctx,String NameSpace,ArrayList<PField> p_prop,String Name) {
		// TODO Auto-generated constructor stub
		m_ctx=ctx;
		m_namespace = NameSpace;
		m_Name = Name;
		setProperties(p_prop);
	}
	
	/**
     * Establece las variables en hashmap de propiedades
     * @author Carlos Parada 20/05/2012
     * @param p_prop propiedades
     * @return void
     */
	public void setProperties(ArrayList<PField> p_prop)
	{		
		m_properties = p_prop;
	}
	
	/**
     * Establece las propiedades del objetos
     * @author Carlos Parada 20/05/2012
     * @param p_prop propiedades
     * @return void
     */
	public void setProperties(Cursor p_prop)
	{
		int l_count_col = p_prop.getColumnCount();
		
		String l_campo;
		Object l_valor;
		
		for (int i=0;i<l_count_col;i++)
		{
			l_campo = p_prop.getColumnName(i);
			l_valor = p_prop.getString(i);
			m_properties.add(new PField(l_campo, l_valor, PropertyInfo.STRING_CLASS));
		}
		
	}
    /**
     * Devuelve el valor de una propiedad del parametro
     * @author Carlos Parada 20/05/2012
     * @param item indice del item
     * @return object
     */
	@Override
	public Object getProperty(int item) {
		// TODO Auto-generated method stub
		return m_properties.get(item).getM_value();
	}

	/**
     * Devuelve la cantidad de propiedades de un parametro
     * @author Carlos Parada 20/05/2012
     * @return int
     */
	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return m_properties.size();
	}

    /**
     * Establece el nombre y tipo de datos de la columna
     * @author Carlos Parada 20/05/2012
     * @param arg0 indice del item,hashtable hasmap item, propertiinfo arg
     * @return void
     */
	
	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub
		arg2.setName(m_properties.get(arg0).getM_field());
		arg2.setType(m_properties.get(arg0).getM_type());
		
	}

	/* (non-Javadoc)
	 * @see org.ksoap2.serialization.KvmSerializable#setProperty(int, java.lang.Object)
	 */
	@Override
	public void setProperty(int arg0, Object arg1) {
		// TODO Auto-generated method stub
		m_properties.get(arg0).setM_value(arg1);
	}

	/**
	 * @param m_Name the m_Name to set
	 */
	public void setM_Name(String m_Name) {
		this.m_Name = m_Name;
	}
	/**
	 * @return the m_Name
	 */
	public String getM_Name() {
		return m_Name;
	}
	
	/**
	 * @param m_ctx the m_ctx to set
	 */
	public void setM_ctx(Context m_ctx) {
		this.m_ctx = m_ctx;
	}
	/**
	 * @return the m_ctx
	 */
	public Context getM_ctx() {
		return m_ctx;
	}
	/**
	 * @return the m_namespace
	 */
	public String getM_namespace() {
		return m_namespace;
	}
	//Contexto
	private Context m_ctx;
	//Nombre del Espacio
	protected String m_namespace;
	//Listados de Parametros
	private ArrayList<PField> m_properties = new ArrayList<PField>();
	//Nombre del Parametro
	protected String m_Name;
}
