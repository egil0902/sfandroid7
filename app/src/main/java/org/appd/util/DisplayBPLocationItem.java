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
package org.appd.util;

/**
 * @author Yamel Senih
 *
 */
public class DisplayBPLocationItem extends DisplayItem {

	/**
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 26/07/2012, 17:54:35
	 * @param record_ID
	 * @param name
	 * @param description
	 */
	public DisplayBPLocationItem(int record_ID, String name, String description) {
		super(record_ID, name, description);
		// TODO Auto-generated constructor stub
	}
	
	private String		phone 		= null;
	private String		phone2 		= null;
	private String		fax 		= null;
	private String		region 		= null;
	private String		city 		= null;
	private String		address1 	= null;
	private String		address2 	= null;
	private String		address3 	= null;
	private String		address4 	= null;
	private String		latitude 	= null;
	private String		longitude 	= null;
	private boolean	isBillTo	= false;
	private boolean	isShipTo	= false;
	
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 26/07/2012, 20:27:19
	 * @param record_ID
	 * @param name
	 * @param description
	 * @param phone
	 * @param phone2
	 * @param fax
	 * @param region
	 * @param city
	 * @param address1
	 * @param address2
	 * @param address3
	 * @param address4
	 * @param latitude
	 * @param longitude
	 * @param isBillTo
	 * @param isShipTo
	 */
	public DisplayBPLocationItem(int record_ID, String name, String description, 
			String		phone, 
			String		phone2, 
			String		fax, 
			String		region, 
			String		city, 
			String		address1, 
			String		address2, 
			String		address3, 
			String		address4, 
			String		latitude, 
			String		longitude, 
			boolean		isBillTo, 
			boolean		isShipTo) {
		
		super(record_ID, name, description);
		
		this.phone		=	phone; 
		this.phone2		=	phone2; 
		this.fax		=	fax;
		this.region		=	region;
		this.city		=	city;
		this.address1	=	address1;
		this.address2	=	address2;
		this.address3	=	address3;
		this.address4	=	address4;
		this.latitude	=	latitude; 
		this.longitude	=	longitude;
		this.isBillTo	=	isBillTo;
		this.isShipTo	=	isShipTo;
	}

	/**
	 * Obtiene el Telefono Principal
	 * @author Yamel Senih 26/07/2012, 20:23:58
	 * @return
	 * @return String
	 */
	public String getPhone(){
		return phone;
	}
	
	/**
	 * Obtiene el segundo telefono 
	 * @author Yamel Senih 26/07/2012, 20:24:08
	 * @return
	 * @return String
	 */
	public String getPhone2(){
		return phone2;
	}
	
	/**
	 * Obtiene el Fax
	 * @author Yamel Senih 26/07/2012, 20:24:21
	 * @return
	 * @return String
	 */
	public String getFax(){
		return fax;
	}
	
	/**
	 * Obtiene la Region / Provincia o Estado
	 * @author Yamel Senih 26/07/2012, 20:24:29
	 * @return
	 * @return String
	 */
	public String getRegion(){
		return region;
	}
	
	/**
	 * Obtiene la Ciudad
	 * @author Yamel Senih 26/07/2012, 20:24:50
	 * @return
	 * @return String
	 */
	public String getCity(){
		return city;
	}
	
	/**
	 * Obtiene la referencia de direccion 1
	 * @author Yamel Senih 26/07/2012, 20:24:57
	 * @return
	 * @return String
	 */
	public String getAddress1(){
		return address1;
	}
	
	/**
	 * Obtiene la referencia de direccion 2
	 * @author Yamel Senih 26/07/2012, 20:25:14
	 * @return
	 * @return String
	 */
	public String getAddress2(){
		return address2;
	}
	
	/**
	 * Obtiene la referencia de direccion 3
	 * @author Yamel Senih 26/07/2012, 20:25:20
	 * @return
	 * @return String
	 */
	public String getAddress3(){
		return address3;
	}
	
	/**
	 * Obtiene la referencia de direccion 4
	 * @author Yamel Senih 26/07/2012, 20:25:27
	 * @return
	 * @return String
	 */
	public String getAddress4(){
		return address4;
	}
	
	/**
	 * Obtiene la Latitud
	 * @author Yamel Senih 26/07/2012, 20:25:33
	 * @return
	 * @return String
	 */
	public String getLatitude(){
		return latitude;
	}
	
	/**
	 * Obtiene la Longitud
	 * @author Yamel Senih 26/07/2012, 20:25:42
	 * @return
	 * @return String
	 */
	public String getLongitude(){
		return longitude;
	}
	
	/**
	 * Verifica si es direccion de facturacion
	 * @author Yamel Senih 26/07/2012, 20:25:52
	 * @return
	 * @return boolean
	 */
	public boolean isBillTo(){
		return isBillTo;
	}
	
	/**
	 * Verifica si es direccion de Entrega
	 * @author Yamel Senih 26/07/2012, 20:26:03
	 * @return
	 * @return boolean
	 */
	public boolean isShipTo(){
		return isShipTo;
	}

}
