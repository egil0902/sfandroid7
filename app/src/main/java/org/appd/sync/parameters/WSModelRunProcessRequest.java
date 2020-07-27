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
import org.ksoap2.serialization.SoapObject;

import android.content.Context;
import android.database.Cursor;

/**
 * @author Carlos Parada
 *
 */
public class WSModelRunProcessRequest extends SoapObject{

	
	/**
	 * *** Constructor de la Clase ***
	 * @author Carlos Parada 09/11/2012, 21:53:53
	 * @param ctx Contexto
	 * @param NameSpace Nombre del Espacio
	 * @param p_prop
	 * @param Name
	 */
	public WSModelRunProcessRequest(Context ctx, String NameSpace,Integer p_WS_WebServiceType_ID,DB con,Integer RecordID,Cursor rs) {
		super(NameSpace, "ModelRunProcessRequest");
		m_mc = new WSModelRunProcess(ctx, NameSpace, p_WS_WebServiceType_ID, con, RecordID, rs);
		m_al = new WSADLoginRequest(ctx, NameSpace);
		//l_para.put(m_mc.getM_Name(), m_mc);
		addProperty(m_mc.getName(), m_mc);
		addProperty(m_al.getName(), m_al);

	}

	

	private WSModelRunProcess m_mc;
	private WSADLoginRequest m_al;
	
}
