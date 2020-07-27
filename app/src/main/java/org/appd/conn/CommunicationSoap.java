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

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author Carlos Parada
 * @date 17/04/2012
 * @description Establishes a connection to a web service using SOAP
 * */
public class CommunicationSoap extends SoapObject {
	
	/**
	 * *** Class Constructor ***
	 * @author Carlos Parada 17/04/2012
	 */
	public CommunicationSoap(String p_Url,String p_NameSpace, String p_Method_Name,boolean isNetService) {
		// TODO Auto-generated constructor stub
		super(p_NameSpace, p_Method_Name);
		setM_SoapMethodName(p_Method_Name);
		setM_NameSpace(p_NameSpace);
		setM_isNetService(isNetService);
		//setM_SoapAction(p_NameSpace+p_Method_Name);
		setM_Url(p_Url);
	}
	
	/**
	 * @author Carlos Parada 17/04/2012
	 * Create serializer
	 * returns void
	 */
	public void init_envelope(){
		//creating Wrapper
		m_Envelope = new Envelope(getM_Soap_Version());
		//Defining the Type of Service
		m_Envelope.setDotNet(isM_isNetService());
		//wrapping object
		m_Envelope.setOutputSoapObject(this);
	}
	
	/**
	 * @author Carlos Parada 17/04/2012
	 * Serialize the object and create the means of transport
	 * returns void
	 */
	public void initTransport(){
		//Creating conveyance object
		m_Transport = new Transport(m_Url);
	}
	
	/**
	 * @author Carlos Parada 17/04/2012
	 * Serialize the object and create the means of transport
	 * returns void
	 */
	public void initTransport(int timeout){
		//Creating conveyance object
		m_Transport = new Transport(m_Url,timeout);
	}
	/**
	 * @author Carlos Parada 17/04/2012
	 * soap service call
	 * returns void
	 * @throws XmlPullParserException 
	 * @throws IOException 
	 */
	public void call() throws IOException, XmlPullParserException{
		m_Transport.call(getM_SoapAction(), m_Envelope);
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	/** Set and gets*/
	
	/**
	 * @param m_isNetService the m_isNetService to set
	 */
	public void setM_isNetService(boolean m_isNetService) {
		this.m_isNetService = m_isNetService;
	}
	
	/**
	 * @param m_Soap_Version the m_Soap_Version to set
	 */
	public void setM_Soap_Version(int m_Soap_Version) {
		this.m_Soap_Version = m_Soap_Version;
	}
	
	/**
	 * @return the m_isNetService
	 */
	public boolean isM_isNetService() {
		return m_isNetService;
	}
	
	/**
	 * @return the m_Soap_Version
	 */
	public int getM_Soap_Version() {
		return m_Soap_Version;
	}
	
	/**
	 * @param m_Url the m_Url to set
	 */
	public void setM_Url(String m_Url) {
		this.m_Url = m_Url;
	}
	
	/**
	 * @return the m_Url
	 */
	public String getM_Url() {
		return m_Url;
	}
	
	/**
	 * @return the m_SoapAction
	 */
	public String getM_SoapAction() {
		return m_SoapAction;
	}
	/**
	 * @param m_SoapAction the m_SoapAction to set
	 */
	public void setM_SoapAction(String m_SoapAction) {
		this.m_SoapAction = m_SoapAction;
	}
	
	/**
	 * @return the m_SoapMethodName
	 */
	public String getM_SoapMethodName() {
		return m_SoapMethodName;
	}
	
	/**
	 * @param m_SoapMethodName the m_SoapMethodName to set
	 */
	public void setM_SoapMethodName(String m_SoapMethodName) {
		this.m_SoapMethodName = m_SoapMethodName;
	}
	
	/**
	 * @return the m_NameSpace
	 */
	public String getM_NameSpace() {
		return m_NameSpace;
	}
	
	/**
	 * @param m_NameSpace the m_NameSpace to set
	 */
	public void setM_NameSpace(String m_NameSpace) {
		this.m_NameSpace = m_NameSpace;
	}

	/**
	 * @return the m_Envelope
	 */
	public Envelope getM_Envelope() {
		return m_Envelope;
	}
	
	/**
	 * @return the m_Transport
	 */
	public Transport getM_Transport() {
		return m_Transport;
	}
	/**
	 * @param m_Envelope the m_Envelope to set
	 */
	public void setM_Envelope(Envelope m_Envelope) {
		this.m_Envelope = m_Envelope;
	}
	/**
	 * @param m_Transport the m_Transport to set
	 */
	public void setM_Transport(Transport m_Transport) {
		this.m_Transport = m_Transport;
	}

	
	/** properties*/
	//indicates whether the service is .Net
	private boolean m_isNetService = true;
	//Version no soup
	private int m_Soap_Version = SoapEnvelope.VER11;
	//Object serializer soap
	private Envelope m_Envelope;
	//Transport
	private Transport m_Transport;
	//Url of service
	private String m_Url;
	//Action soap call
	private String m_SoapAction; 
	//Call Soap Method
	private String m_SoapMethodName;
	//NameSpace 
	private String m_NameSpace; 
}
