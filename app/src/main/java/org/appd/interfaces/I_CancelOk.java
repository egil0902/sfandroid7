/*************************************************************************************
 * Product: SFAndroid (Sales Force Mobile)                       		             *
 * This program is free software; you can redistribute it and/or modify it    		 *
 * under the terms version 2 of the GNU General Public License as published   		 *
 * by the Free Software Foundation. This program is distributed in the hope   		 *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied 		 *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           		 *
 * See the GNU General Public License for more details.                       		 *
 * You should have received a copy of the GNU General Public License along    		 *
 * with this program; if not, write to the Free Software Foundation, Inc.,    		 *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     		 *
 * For the text or an alternative of this public license, you may reach us    		 *
 * Copyright (C) 2012-2012 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com				  		 *
 *************************************************************************************/
package org.appd.interfaces;

import android.content.Intent;

/**
 * @author Yamel Senih
 *
 */
public interface I_CancelOk {
	
	/**
	 * Accion Aceptar
	 * @author Yamel Senih 20/08/2012, 00:49:45
	 * @return
	 * @return boolean
	 */
	public boolean aceptAction();
	
	/**
	 * Accion Cancelar
	 * @author Yamel Senih 20/08/2012, 00:49:58
	 * @return
	 * @return boolean
	 */
	public boolean cancelAction();
	
	/**
	 * Obtiene los parametros de la Actividad
	 * @author Yamel Senih 20/08/2012, 01:00:25
	 * @return
	 * @return Intent
	 */
	public Intent getParam();
	
}
