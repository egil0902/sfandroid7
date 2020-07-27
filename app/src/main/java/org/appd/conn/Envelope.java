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


import org.ksoap2.serialization.SoapSerializationEnvelope;



/**
 * @author Carlos Parada
 *
 */
public class Envelope extends SoapSerializationEnvelope{

	/**
	 * *** Class Constructor ***
	 * @author Carlos Parada 17/04/2012
	 * @param version
	 */
	
	public Envelope(int version) {
		super(version);
		// TODO Auto-generated constructor stub
		
	}
	
	/**
	 * @author Carlos Parada 17/04/2012
	 * @param dotNet the dotNet to set
	 * sets whether the service is .NET
	 */
	public void setDotNet(boolean dotNet) {
		this.dotNet = dotNet;
	}
	
	/**
	 * @author Carlos Parada 17/04/2012
	 * @return the dotNet
	 * indicates whether the service is .NET
	 */
	public boolean isDotNet() {
		return dotNet;
	}
	
	/* Wrap the request
	 * @see org.ksoap2.SoapEnvelope#setOutputSoapObject(java.lang.Object)
	 */
	@Override
	public void setOutputSoapObject(Object soapObject) {
		// TODO Auto-generated method stub
		super.setOutputSoapObject(soapObject);
	}
	


}
