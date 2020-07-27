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
package org.appd.interfaces;

//import org.appd.util.ImpParamActivity;

import org.appd.util.DisplayMenuItem;

import android.content.Context;
import android.view.View;

/**
 * @author Yamel Senih
 *
 */
public interface I_ClassLoaderListRecord {
	/**
	 * Inicia una clase, ejemplo Menu entre otros, 
	 * pasando los parametros del registro que inicia la clase
	 * @author Yamel Senih 12/05/2012, 03:54:08
	 * @author Yamel Senih 12/05/2012, 04:28:44
	 * @param ctx
	 * @param v
	 * @param item
	 * @return
	 * @return boolean
	 */
	public boolean startLoad(Context ctx, View v, DisplayMenuItem item);
	
	/**
	 * Inicia una clase, ejemplo Menu
	 * @author Yamel Senih 12/05/2012, 03:54:58
	 * @return
	 * @return boolean
	 */
	public boolean startLoad();
}
