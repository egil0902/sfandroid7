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
package org.appd.conn;

import java.io.IOException;
import java.net.Proxy;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author Carlos
 *
 */
public class Transport extends HttpTransportSE{

	
	/**
	 * *** Class Constructor ***
	 * @author Carlos Parada 18/04/2012
	 * @param proxy
	 * @param url
	 * @param timeout
	 */
	public Transport(Proxy proxy, String url, int timeout) {
		super(proxy, url, timeout);
		// TODO Auto-generated constructor stub
	}
	/**
	 * *** Class Constructor ***
	 * @author Carlos Parada 18/04/2012
	 * @param proxy
	 * @param url
	 */
	public Transport(Proxy proxy, String url) {
		super(proxy, url);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * *** Class Constructor ***
	 * @author Carlos Parada 18/04/2012
	 * @param url
	 */
	public Transport( String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}
	/**
	 * *** Class Constructor ***
	 * @author Carlos Parada 17/10/2012
	 * @param url
	 */
	public Transport( String url,int timeout) {
		super(url,timeout);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see org.ksoap2.transport.HttpTransportSE#call(java.lang.String, org.ksoap2.SoapEnvelope, java.util.List)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List call(String arg0, SoapEnvelope arg1, List arg2)
			throws IOException, XmlPullParserException {
		// TODO Auto-generated method stub
		return super.call(arg0, arg1, arg2);
	}
	/* (non-Javadoc)
	 * @see org.ksoap2.transport.HttpTransportSE#call(java.lang.String, org.ksoap2.SoapEnvelope)
	 */
	@Override
	public void call(String soapAction, SoapEnvelope envelope)
			throws IOException, XmlPullParserException {
		// TODO Auto-generated method stub
		super.call(soapAction, envelope);
	}
}
