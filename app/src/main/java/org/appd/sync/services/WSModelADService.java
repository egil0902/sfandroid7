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
package org.appd.sync.services;

import java.io.IOException;


import org.appd.base.DB;
import org.appd.base.Env;
import org.appd.conn.CommunicationSoap;
import org.appd.model.MBWebServiceType;
import org.appd.sync.parameters.WSModelCRUDRequest;
import org.appd.sync.parameters.WSModelRunProcessRequest;
import org.ksoap2.serialization.SoapObject;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.database.Cursor;

/**
 * @author Carlos Parada
 *
 */
public class WSModelADService{

	/**
	 * *** Constructor de la Clase ***
	 * @author Carlos Parada 22/05/2012, 22:24:31
	 * @throws XmlPullParserException 
	 * @throws IOException 
	 */
	
	public WSModelADService(Context ctx,String p_NameSpace,String p_Url,String p_Method,int p_Web_ServiceType_ID,DB con,Cursor rs,String TableName,String Filter,String SynchronizingType) throws IOException, XmlPullParserException {
		int timeout=0;
		SoapObject l_para;
		m_NameSpace = p_NameSpace;
		m_Url = p_Url;
		m_Method = p_Method;
		timeout=Env.getContextAsInt(ctx, "#Timeout");
		//Creando Objeto Soap
		m_soap = new CommunicationSoap(m_Url, m_NameSpace, m_Method, true);
		
		//m_soap.setM_SoapAction("{"+m_NameSpace+"}"+m_Method);
		
		//Creando Parametros de servicios Web (Se Evalua si se va a ejecutar un proceso o se va a ejecutar otra accion)
		if (SynchronizingType.equals(MBWebServiceType.SynchronizingType_Process))
			l_para = new WSModelRunProcessRequest(ctx, m_NameSpace, p_Web_ServiceType_ID, con, (rs!=null?rs.getInt(rs.getColumnIndex(TableName+"_ID")):null), rs);
		else
			l_para = new WSModelCRUDRequest(ctx, m_NameSpace, p_Web_ServiceType_ID, con, (rs!=null?rs.getInt(rs.getColumnIndex(TableName+"_ID")):null), rs,Filter);

		System.out.println(p_Web_ServiceType_ID);
		m_soap.addSoapObject(l_para);
		
		//Inicializando objeto de envoltura
		m_soap.init_envelope();
		//mapeando los parametros
		
		
		//Inicializando Transporte
		if (timeout==0)
			m_soap.initTransport();
		else
			m_soap.initTransport(timeout);
		
		
			m_soap.call();
			m_Resp =(SoapObject) m_soap.getM_Envelope().getResponse();

		
	}
	
	/**
	 * Retorna Respuesta de llamada Soap
	 * @return the m_Resp
	 */
	public SoapObject getM_Resp() {
		return m_Resp;
	}
	
	private CommunicationSoap m_soap ;
	private String m_NameSpace;
	private String m_Url;
	private String m_Method;
	private SoapObject m_Resp;
}
