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


import org.appd.base.Env;
import org.ksoap2.serialization.SoapObject;


import android.content.Context;

/**
 * @author Carlos Parada
 *
 */
public class WSADLoginRequest extends SoapObject{

	/**
	 * *** Constructor de la Clase ***
	 * @author Carlos Parada 21/05/2012, 22:31:37
	 */
	public WSADLoginRequest(Context ctx,String NameSpace) {
		// TODO Auto-generated constructor stub
		super(NameSpace,"ADLoginRequest");
		addProperty("user", Env.getContext(ctx, "#SUser"));
		addProperty("pass", Env.getContext(ctx, "#SPass"));
		addProperty("ClientID", Env.getAD_Client_ID(ctx));
		addProperty("RoleID", Env.getAD_Role_ID(ctx));
		addProperty("OrgID", Env.getAD_Org_ID(ctx));
		addProperty("WarehouseID", Env.getM_Warehouse_ID(ctx));
	}

}
