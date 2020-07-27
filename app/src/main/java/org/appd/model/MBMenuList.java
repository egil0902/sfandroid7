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
package org.appd.model;

import org.appd.base.DB;

import android.content.Context;

/**
 * @author Carlos Parada
 *
 */
public class MBMenuList extends MP{

	private final String TABLENAME = "XX_MB_MenuList";
	/**
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 20/08/2012, 00:01:58
	 * @param ctx
	 * @param con_tx
	 * @param ID
	 */
	public MBMenuList(Context ctx, DB con_tx, int ID) {
		super(ctx, con_tx, ID);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return TABLENAME;
	}
	
	public MBWebServiceType getWebServiceType()
	{
		int id = get_ValueAsInt("WS_WebServiceType_ID");
		if (id!=0)
			return new MBWebServiceType(m_ctx, con, id);
		else
			return null;
	}
	
}
