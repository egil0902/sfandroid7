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

import android.content.Context;
import android.database.Cursor;

/**
 * @author Yamel Senih
 *
 */
public class MBPartner extends MP {

	private final int AD_TABLE_ID = 291;
	
	public MBPartner(Context ctx, int ID) {
		super(ctx, ID);
		// TODO Auto-generated constructor stub
	}
	
	public MBPartner(Context ctx, Cursor rs) {
		super(ctx, rs);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.sg.model.MP#beforeUpdate()
	 */
	@Override
	public boolean beforeSave(boolean isNew) {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see org.sg.model.MP#beforeDelete()
	 */
	@Override
	public boolean beforeDelete() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	protected int getAD_Table_ID() {
		// TODO Auto-generated method stub
		return AD_TABLE_ID;
	}

	@Override
	protected boolean afterSave(boolean isNew) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean afterDelete() {
		// TODO Auto-generated method stub
		return true;
	}
}
