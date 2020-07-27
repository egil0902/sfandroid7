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

import org.appd.base.Env;
import org.appd.base.R;
import org.appd.interfaces.I_ClassLoaderListRecord;
import org.appd.model.MBPlanningVisit;
import org.appd.model.MBVisit;
import org.appd.util.ActionItemList;
import org.appd.util.DisplayMenuItem;
import org.appd.util.contribution.QuickAction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * @author Yamel Senih
 *
 */
public class M_MenuPlanningVisit implements I_ClassLoaderListRecord {

	
	private static final String START_Sales_Order = "SOO";
	private static final String START_AR_Receipt = "ARR";
	private static final String START_Making_Inventory = "MMI";
	private static final String START_Return_Material = "RMC";
	private static final String START_No_Sales_Reason = "NSR";
	
	
	private DisplayMenuItem param;
	private Context ctx;
	private QuickAction mQAct = null;
	/* (non-Javadoc)
	 * @see org.appd.interfaces.I_ClassLoaderListRecord#startLoad(android.content.Context, android.view.View, org.appd.util.DisplayRecordItem)
	 */
	@Override
	public boolean startLoad(Context ctx, View v, DisplayMenuItem param) {
		boolean ok = false;
		
		this.param = param;
		this.ctx = ctx;
		//	si no se ha instanciado
		if(mQAct == null){
			initMenuItem();
		}
		//	Show Menu Item
		mQAct.show(v);
		
		return ok;
	}
	
	/**
	 * Instance Menu Item
	 * @author Yamel Senih 13/08/2012, 15:27:29
	 * @return void
	 */
	private void initMenuItem(){
		mQAct = new QuickAction(ctx);
		
		mQAct.addActionItem(new ActionItemList(START_Making_Inventory, 
				ctx.getResources().getString(R.string.START_Making_Inventory), 
				ctx.getResources().getDrawable(R.drawable.package_m)));
		
		mQAct.addActionItem(new ActionItemList(START_Sales_Order, 
				ctx.getResources().getString(R.string.START_Sales_Order), 
				ctx.getResources().getDrawable(R.drawable.sales_order_m)));
		
		mQAct.addActionItem(new ActionItemList(START_AR_Receipt, 
				ctx.getResources().getString(R.string.START_AR_Receipt), 
				ctx.getResources().getDrawable(R.drawable.collect_money_m), ""));
		
		mQAct.addActionItem(new ActionItemList(START_Return_Material, 
				ctx.getResources().getString(R.string.START_Return_Material), 
				ctx.getResources().getDrawable(R.drawable.return_m)));
		
		mQAct.addActionItem(new ActionItemList(START_No_Sales_Reason, 
				ctx.getResources().getString(R.string.START_No_Sales_Reason), 
				ctx.getResources().getDrawable(R.drawable.nosales_m), ""));
		
		//	Action Event
		mQAct.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
			@Override
			public void onItemClick(QuickAction quickAction, int pos, int actionId) {
				ActionItemList actionItem = (ActionItemList) quickAction.getActionItem(pos);
				
				MBPlanningVisit pv = new MBPlanningVisit(ctx, param.getRecord_ID());
				
				String offCourse = (String)pv.get_Value("OffCourse");				
				int m_XX_MB_Visit_ID = MBVisit.findVisit(ctx, param.getRecord_ID(), null, true);
				
				String nameBPartner = (String) pv.get_Value("NameBP");
				
				int m_C_BPartner_ID = pv.get_ValueAsInt("C_BPartner_ID");
				
				int m_C_BPartner_Location_ID = pv.get_ValueAsInt("C_BPartner_Location_ID");
				
				Env.setContext(ctx, "#OffCourse", (offCourse != null && offCourse.equals("Y")? "N": "Y"));
				Env.setContext(ctx, "#XX_MB_PlanningVisit_ID", param.getRecord_ID());
				Env.setContext(ctx, "#XX_MB_Visit_ID", m_XX_MB_Visit_ID);
				Env.setContext(ctx, "#XX_MB_VisitLine_ID", 0);
				Env.setContext(ctx, "#C_BPartner_ID", m_C_BPartner_ID);
				Env.setContext(ctx, "#C_BPartner_Location_ID", m_C_BPartner_Location_ID);
				
				//	Sales Order Action
				if(actionItem.getValue().equals(START_Sales_Order)){
					
					DisplayMenuItem item = DisplayMenuItem.createFromMenu(param);
					
					item.setClassName("org.appd.view.MT_CustomerOrder");
					item.setWhereClause("C_Order.C_BPartner_Location_ID = " + m_C_BPartner_Location_ID + " " +
							"AND DATE(C_Order.DateOrdered) = DATE('" + Env.getContext(ctx, "#DateP") + "')");
					item.setOrderByClause("C_Order.DocumentNo");
					item.setTableName("C_Order");
					item.setAD_Table_ID(0);
					item.setActivityType("S");
					item.setAction("F");
					item.setName(ctx.getResources().getString(R.string.C_Order_ID) + 
							" \"" + nameBPartner + "\"");
					
					
					Env.setTabRecord_ID(ctx, "MV_CustomerOrder", 0);
					Env.setTabRecord_ID(ctx, "MV_CustomerOrderLine", 0);
					Env.setTabRecord_ID(ctx, "C_Order", 0);
					Env.setTabRecord_ID(ctx, "C_OrderLine", 0);
					
					Env.setContext(ctx, "MT_CustomerOrder", 0);
					
					Bundle bundle = new Bundle();
					bundle.putParcelable("Item", item);
					//	Intent
					Intent intent = new Intent(ctx, MV_ListRecord.class);
					intent.putExtras(bundle);
					//	Start
					ctx.startActivity(intent);
				} else if(actionItem.getValue().equals(START_Making_Inventory)){
					
					DisplayMenuItem item = DisplayMenuItem.createFromMenu(param);
					
					item.setClassName("org.appd.view.MT_CustomerInventory");
					item.setWhereClause("XX_MB_CustomerInventory.C_BPartner_Location_ID = " + m_C_BPartner_Location_ID + " " +
							"AND DATE(XX_MB_CustomerInventory.DateDoc) = DATE('" + Env.getContext(ctx, "#DateP") + "')");
					item.setOrderByClause("");
					item.setTableName("XX_MB_CustomerInventory");
					item.setAD_Table_ID(0);
					item.setActivityType("S");
					item.setAction("F");
					item.setName(ctx.getResources().getString(R.string.XX_MB_CustomerInventory_ID) + 
							" \"" + nameBPartner + "\"");
					
					Env.setTabRecord_ID(ctx, "XX_MB_CustomerInventory", 0);
					Env.setTabRecord_ID(ctx, "XX_MB_CustomerInventoryLine", 0);
					Env.setContext(ctx, "MT_CustomerInventory", 0);
					
					Bundle bundle = new Bundle();
					bundle.putParcelable("Item", item);
					//	Intent
					Intent intent = new Intent(ctx, MV_ListRecord.class);
					intent.putExtras(bundle);
					//	Start
					ctx.startActivity(intent);
				} else if(actionItem.getValue().equals(START_AR_Receipt)){
					
					DisplayMenuItem item = DisplayMenuItem.createFromMenu(param);
					
					item.setClassName("org.appd.view.MV_CustomerCollect");
					item.setWhereClause("C_CashLine.C_BPartner_ID = " + m_C_BPartner_ID + " " +
							"AND DATE(C_CashLine.DateDoc) = DATE('" + Env.getContext(ctx, "#DateP") + "')");
					item.setOrderByClause("C_CashLine.C_CashLine_ID");
					item.setTableName("C_CashLine");
					item.setAD_Table_ID(0);
					item.setActivityType("S");
					item.setAction("F");
					item.setName(ctx.getResources().getString(R.string.C_Cash_ID) + 
							" \"" + nameBPartner + "\"");
					
					
					Env.setTabRecord_ID(ctx, "MV_CustomerCollect", 0);
					//Env.setTabRecord_ID(ctx, "MV_CustomerOrderLine", 0);
					//Env.setContext(ctx, "MT_CustomerOrder", 0);
					
					Env.setContext(ctx, "#C_BPartner_ID", m_C_BPartner_ID);
					Env.setContext(ctx, "#C_BPartner_Location_ID", m_C_BPartner_Location_ID);
					
					Bundle bundle = new Bundle();
					bundle.putParcelable("Param", item);
					//	Intent
					Intent intent = new Intent(ctx, MV_CustomerCollect.class);
					intent.putExtras(bundle);
					//	Start
					ctx.startActivity(intent);
				} else if(actionItem.getValue().equals(START_Return_Material)){
					
					DisplayMenuItem item = DisplayMenuItem.createFromMenu(param);
					
					item.setClassName("org.appd.view.MT_CustomerRMA");
					
					item.setWhereClause("EXISTS(SELECT 1 FROM M_InOut io " +
			    			"WHERE io.M_InOut_ID = M_RMA.InOut_ID " +
			    			"AND io.C_BPartner_Location_ID = " + m_C_BPartner_Location_ID + ") " + 
			    			"AND DATE(M_RMA.DateDoc) = DATE('" + Env.getContext(ctx, "#DateP") + "')");
					
					item.setOrderByClause("");
					item.setTableName("M_RMA");
					item.setAD_Table_ID(0);
					item.setActivityType("S");
					item.setAction("F");
					item.setName(ctx.getResources().getString(R.string.M_RMA_ID) + 
							" \"" + nameBPartner + "\"");
					
					Env.setTabRecord_ID(ctx, "M_RMA", 0);
					Env.setTabRecord_ID(ctx, "M_RMALine", 0);
					Env.setContext(ctx, "MT_CustomerRMA", 0);
					
					Bundle bundle = new Bundle();
					bundle.putParcelable("Item", item);
					//	Intent
					Intent intent = new Intent(ctx, MV_ListRecord.class);
					intent.putExtras(bundle);
					//	Start
					ctx.startActivity(intent);
				} else if(actionItem.getValue().equals(START_No_Sales_Reason)){
					
					DisplayMenuItem item = DisplayMenuItem.createFromMenu(param);
					
					item.setClassName("org.appd.view.MT_VisitAndLine");
					
					item.setWhereClause("");
					
					item.setOrderByClause("");
					item.setTableName("XX_MB_Visit");
					item.setAD_Table_ID(0);
					item.setActivityType("S");
					item.setAction("F");
					item.setName(ctx.getResources().getString(R.string.START_No_Sales_Reason) + 
							" \"" + nameBPartner + "\"");
					
					m_XX_MB_Visit_ID = MBVisit.findVisit(ctx, param.getRecord_ID(), null, false);
					
					Env.setTabRecord_ID(ctx, "XX_MB_Visit", m_XX_MB_Visit_ID);
					Bundle bundle = new Bundle();
					bundle.putParcelable("Param", item);
					//	Intent
					Intent intent = new Intent(ctx, MT_VisitAndLine.class);
					intent.putExtras(bundle);
					//	Start
					ctx.startActivity(intent);
				}
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.appd.interfaces.I_ClassLoaderListRecord#startLoad()
	 */
	@Override
	public boolean startLoad() {
		// TODO Auto-generated method stub
		return false;
	}

}
