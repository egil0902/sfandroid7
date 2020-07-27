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
package org.appd.view;

import org.appd.base.DB;
import org.appd.base.Env;
import org.appd.base.R;
import org.appd.model.MBRMA;
import org.appd.model.MBVisit;
import org.appd.util.Msg;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * @author Yamel Senih
 *
 */
public class MT_CustomerRMA extends MTabActivity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        
    	addActivity(MV_CustomerRMA.class, "M_RMA", 
    			R.string.M_RMA_ID,R.drawable.return_m);
    	addActivity(MV_CustomerRMALine.class, "M_RMALine", 
    			R.string.M_RMALine_ID, R.drawable.inventory_m);
    	
	}
	
	@Override
	protected void e_ButtSave(){
		MVActivity bp = (MVActivity)getCurrentActivity();
		if(bp != null){
			if(bp.isNew()){
				//final int m_XX_MB_Visit_ID = Env.getContextAsInt(this, "#XX_MB_Visit_ID");
				//	Verifica si la visita actual es la que esta abierta
				if(Env.getContextAsInt(this, "#XX_MB_Visit_ID") == 0){
					final int m_XX_MB_Visit_ID = MBVisit.findOpenVisit(this, null);
					//	Verifica si ya hay una visita abierta
					if(m_XX_MB_Visit_ID != 0){
						final MBVisit visit = new MBVisit(getApplicationContext(), m_XX_MB_Visit_ID);
						//	Get Date from Visit
						if(!Env.isAutomaticVisitClosing(this)){
							String dateS = (String)visit.get_Value("DateVisit");
							
							Builder ask = Msg.confirmMsg(this, Env.getDateFormatString(dateS, "yyyy-MM-dd hh:mm:ss", "dd/MM/yyyy hh:mm:ss") 
									+ "\n" + visit.get_Value("NameBPartner") + "\""
									+ "\n" + getResources().getString(R.string.msg_AskCloseVisit));
					    	
							String msg_Acept = this.getResources().getString(R.string.msg_Yes);
					    	ask.setPositiveButton(msg_Acept, new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									
									dialog.cancel();
									//	Cierra la Visita anterior
									try {	
										visit.closeVisit(null);
										saveRMA();
									} catch(Exception e) {
										Msg.alertMsg(getCurrentActivity(), 
												getCurrentActivity().getString(R.string.msg_Error), e.getMessage());
										Log.e("Error", "Exception", e);
									}
								}
							});
					    	ask.show();
						} else {
							
							//	Cierra la Visita anterior
							try {	
								visit.closeVisit(null);
								saveRMA();
							} catch(Exception e) {
								Msg.alertMsg(getCurrentActivity(), 
										getCurrentActivity().getString(R.string.msg_Error), e.getMessage());
								Log.e("Error", "Exception", e);
							}
						}
						
					} else
						saveRMA();    	
				} else
					saveRMA();
			} else {
				final MBRMA m_rma = (MBRMA) bp.getMP();
				int currentInOut_ID = Env.getContextAsInt(this, "#InOut_ID");
				int oldInOut_ID = m_rma.get_OldValueAsInt("InOut_ID");
				//	Valida si se modifico la Entrega
				if(currentInOut_ID != oldInOut_ID){
					//	Si existen lineas muestra un mensaje de confirmacion
					//	Notificando al usuario que se eliminaran las lineas si continua con la operacion
					if(findLines()){
						Builder ask = Msg.confirmMsg(this, 
								getResources().getString(R.string.msg_AskRMA_InOutCh), 
								getResources().getString(R.string.msg_AskRMA_InOutChDetail));
				    	
						String msg_Acept = getResources().getString(R.string.msg_Yes);
						String msg_Cancel = getResources().getString(R.string.msg_Cancel);
						
						ask.setNegativeButton(msg_Cancel, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								e_ButtBack();
								dialog.cancel();
							}
						});
						
				    	ask.setPositiveButton(msg_Acept, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								deleteLines();
								m_rma.set_Value("Amt", Env.ZERO);
								saveRMA();
							}
						});
				    	
				    	ask.show();
					} else {
						saveRMA();
					}
				} else {
					saveRMA();
				}
			}
				
		}
	}
	
	/**
	 * Verifica si existen lineas en la ADM
	 * @author Yamel Senih 21/08/2012, 00:46:43
	 * @return
	 * @return boolean
	 */
	private boolean findLines(){
		DB con = new DB(this);
		con.openDB(DB.READ_ONLY);
		Cursor rs = con.querySQL("SELECT 1 FROM M_RMALine ml " +
				"WHERE ml.M_RMA_ID = " + 
				Env.getTabRecord_ID(this, "M_RMA") + " LIMIT 1", null);
		boolean exists = rs.moveToFirst();
		con.closeDB(rs);
		return exists;
	}
	
	
	/**
	 * Elimina las Lineas de la ADM
	 * @author Yamel Senih 21/08/2012, 00:29:19
	 * @return void
	 */
	private void deleteLines(){
		DB con = new DB(this);
		con.openDB(DB.READ_WRITE);
		con.deleteSQL("M_RMALine", "M_RMA_ID = " + Env.getTabRecord_ID(this, "M_RMA"), null);
		con.closeDB(null);
	}
	
	/**
	 * Guarda la Autorizacion de Devolucion
	 * @author Yamel Senih 24/07/2012, 23:30:29
	 * @return void
	 */
	private void saveRMA(){
		super.e_ButtSave();
		int tabIndex = mTabHost.getCurrentTab();
		if(tabIndex == 0){
			if(record_ID != 0){
				mTabHost.getTabWidget().getChildAt(1).setEnabled(true);
			}
		}
	}
	
	@Override
	protected void e_ButtNew(){
		super.e_ButtNew();
		int tabIndex = mTabHost.getCurrentTab();
		if(tabIndex == 0){
			if(record_ID == 0){
				mTabHost.getTabWidget().getChildAt(1).setEnabled(false);
			}
		}
	}
	
	@Override
	protected void e_ButtModify(){
		super.e_ButtModify();
		int tabIndex = mTabHost.getCurrentTab();
		if(tabIndex == 0)
			mTabHost.getTabWidget().getChildAt(1).setEnabled(false);
	}
	
	@Override
	protected void e_ButtBack(){
		super.e_ButtBack();
		int tabIndex = mTabHost.getCurrentTab();
		if(tabIndex == 0){
			if(record_ID != 0){
				mTabHost.getTabWidget().getChildAt(1).setEnabled(true);
			}
		}
	}
	
	@Override
	protected void voidConfig(){
		super.voidConfig();
		int tabIndex = mTabHost.getCurrentTab();
		if(tabIndex == 0){
			mTabHost.getTabWidget().getChildAt(1).setEnabled(false);
		}
	}
	
	@Override
    protected void onResume() {
        super.onResume();
        int tabIndex = mTabHost.getCurrentTab();
		if(tabIndex == 0){
			if(Env.getTabRecord_ID(this, "M_RMA") > 0){
				mTabHost.getTabWidget().getChildAt(1).setEnabled(true);
			} else {
				mTabHost.getTabWidget().getChildAt(1).setEnabled(false);
			}
		} else if(tabIndex == 1){
			voidConfig();
			buttNew.setEnabled(false);
		}
    }
	
	@Override
	public void onTabChanged(String tabId) {
		super.onTabChanged(tabId);
		int tabIndex = mTabHost.getCurrentTab();
		
		if(tabIndex == 1){
			setVisibleButtons(View.GONE);
		} else {
			setVisibleButtons(View.VISIBLE);
			MVActivity bp = (MVActivity)getCurrentActivity();
			bp.refresh();
		}
	}
	
	/* (non-Javadoc)
	 * @see org.appd.view.MTabActivity#getActivityName()
	 */
	@Override
	protected String getActivityName() {
		return getClass().getSimpleName();
	}
	
}
