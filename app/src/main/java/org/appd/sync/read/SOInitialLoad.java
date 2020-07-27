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
 * Copyright (C) 2012-2013 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Carlos Parada www.erpconsultoresyasociados.com                    *
 *************************************************************************************/
package org.appd.sync.read;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/**
 * SO Initial Load - Sign On during Initial Load
 * @author Carlos Parada
 *
 */
public class SOInitialLoad implements KvmSerializable{

	
	/* (non-Javadoc)
	 * @see org.ksoap2.serialization.KvmSerializable#getProperty(int)
	 */
	@Override
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		Object m_return;
		m_return = (arg0==0?m_User:m_Pass);
		return m_return;
	}

	/* (non-Javadoc)
	 * @see org.ksoap2.serialization.KvmSerializable#getPropertyCount()
	 */
	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	/* (non-Javadoc)
	 * @see org.ksoap2.serialization.KvmSerializable#getPropertyInfo(int, java.util.Hashtable, org.ksoap2.serialization.PropertyInfo)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub
		if (arg0==0)
		{
			arg2.name="user";
			arg2.setType(PropertyInfo.STRING_CLASS);
		}
		else if(arg0==1)
		{
			arg2.name="pass";
			arg2.setType(PropertyInfo.STRING_CLASS);
		} 
	}

	/* (non-Javadoc)
	 * @see org.ksoap2.serialization.KvmSerializable#setProperty(int, java.lang.Object)
	 */
	@Override
	public void setProperty(int arg0, Object arg1) {
		// TODO Auto-generated method stub
		if(arg0==0)
			m_User=(arg1!=null?arg1.toString():"");
		else if(arg0==1)
			m_Pass=(arg1!=null?arg1.toString():"");
		
	}

	// User and Password
	private String m_User;
	private String m_Pass; 
}
