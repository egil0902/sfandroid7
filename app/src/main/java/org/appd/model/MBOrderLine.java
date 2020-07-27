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

import java.math.BigDecimal;
import java.util.ArrayList;

import org.appd.base.DB;
import org.appd.base.Env;
import org.appd.util.DisplayProductItem;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * @author Yamel Senih
 *
 */
public class MBOrderLine extends MP {

	/*
	 * Antes la tabla estaba en 260????
	 * No se ni que hora era cuando lo cambié????
	 */
	//private final int AD_TABLE_ID = 260;
	private final String TABLENAME = "C_OrderLine";
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 22/08/2012, 03:03:23
	 * @param ctx
	 * @param ID
	 */
	public MBOrderLine(Context ctx, int ID) {
		super(ctx, ID);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 22/08/2012, 03:03:26
	 * @param ctx
	 * @param rs
	 */
	public MBOrderLine(Context ctx, Cursor rs) {
		super(ctx, rs);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 22/08/2012, 03:03:49
	 * @param ctx
	 * @param con_tx
	 * @param ID
	 */
	public MBOrderLine(Context ctx, DB con_tx, int ID) {
		super(ctx, con_tx, ID);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.sg.model.MP#beforeUpdate()
	 */
	@Override
	public boolean beforeSave(boolean isNew) {
		if(isNew){
			//set_Value("SalesRep_ID", Env.getAD_User_ID(m_ctx));
			//set_Value("DocStatus", "DR");
			//Log.i("MP.beforeSave", "C_DocTypeTarget_ID = " + get_ValueAsInt("C_DocTypeTarget_ID") + " AD_User_ID = " + Env.getAD_User_ID(m_ctx));
			//MSequence.updateSequence(con, get_ValueAsInt("C_DocTypeTarget_ID"), Env.getAD_User_ID(m_ctx));	
		}
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
	
	/*@Override
	protected int getAD_Table_ID() {
		// TODO Auto-generated method stub
		return AD_TABLE_ID;
	}*/
	
	@Override
	protected String getTableName(){
		return TABLENAME;
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
	
	/**
	 * rea las lineas a partir de un ArrayList de Productos
	 * @author Yamel Senih 22/08/2012, 03:10:48
	 * @param ctx
	 * @param con
	 * @param products
	 * @param m_C_Order_ID
	 * @param m_Order
	 * @param m_XX_MB_CustomerInventory_ID
	 * @param suggested
	 * @return
	 * @return String
	 */
	public static String createLinesAndInventoryFromProducts(Context ctx, DB con, ArrayList<DisplayProductItem> products, 
			int m_C_Order_ID, MBOrder m_Order, int m_XX_MB_CustomerInventory_ID, boolean suggested){
		
		if(m_Order != null){
			m_C_Order_ID = m_Order.getID();
		} else {
			m_Order = new MBOrder(ctx, con, m_C_Order_ID);
		}
		
		int m_C_BPartner_ID = m_Order.get_ValueAsInt("C_BPartner_ID");
		int m_C_BPartner_Location_ID = m_Order.get_ValueAsInt("C_BPartner_Location_ID");
		
		String msg = null;
		boolean updateHead = false;
		
		String DocumentNo = (String) m_Order.get_Value("DocumentNo");
		
		MBOrderLine m_OrderLine = new MBOrderLine(ctx, con, 0);
		MPTableInfo tInfo = m_OrderLine.getTableInfo();
		int pos_C_Order_ID = tInfo.getColumnIndex("C_Order_ID");
		int pos_M_Warehouse_ID = tInfo.getColumnIndex("M_Warehouse_ID");
		int pos_M_Product_ID = tInfo.getColumnIndex("M_Product_ID");
		int pos_PriceList = tInfo.getColumnIndex("PriceList");
		int pos_PriceActual = tInfo.getColumnIndex("PriceActual");
		int pos_PriceEntered = tInfo.getColumnIndex("PriceEntered");
		int pos_Qty = tInfo.getColumnIndex("Qty");
		int pos_QtyOrdered = tInfo.getColumnIndex("QtyOrdered");
		int pos_QtyEntered = tInfo.getColumnIndex("QtyEntered");
		int pos_QtyDelivered = tInfo.getColumnIndex("QtyDelivered");
		int pos_QtyInvoiced = tInfo.getColumnIndex("QtyInvoiced");
		int pos_QtyReserved = tInfo.getColumnIndex("QtyReserved");
		int pos_PriceLimit = tInfo.getColumnIndex("PriceLimit");
		int pos_C_BPartner_Location_ID = tInfo.getColumnIndex("C_BPartner_Location_ID");   		
		int pos_LineNetAmt = tInfo.getColumnIndex("LineNetAmt");
		int pos_C_UOM_ID = tInfo.getColumnIndex("C_UOM_ID");
		int pos_C_Tax_ID = tInfo.getColumnIndex("C_Tax_ID");
		int pos_C_Activity_ID = tInfo.getColumnIndex("C_Activity_ID");
		String v_C_Activity_ID = Env.getContext(ctx, "#C_Activity_ID");
		
		boolean created = (m_XX_MB_CustomerInventory_ID != 0);
		MBCustomerInventoryLine m_CustomerInventoryLine = new MBCustomerInventoryLine(ctx, con, 0);
		tInfo = m_CustomerInventoryLine.getTableInfo();
		int pos_XX_MB_CustomerInventory_ID = tInfo.getColumnIndex("XX_MB_CustomerInventory_ID");
		int pos_M_ProductCI_ID = tInfo.getColumnIndex("M_Product_ID");
		int pos_XX_QtyInStock = tInfo.getColumnIndex("XX_QtyInStock");
		int pos_XX_QtyOnRack = tInfo.getColumnIndex("XX_QtyOnRack");
		int pos_IsActive = tInfo.getColumnIndex("IsActive");
		//	int pos_C_OrderInv_ID = tInfo.getColumnIndex("C_Order_ID");
		int pos_LineInventory = tInfo.getColumnIndex("Line");
		//	Cantidades de XX_MB_CustomerInventoryLine
		BigDecimal qtyOnRack = null;
		BigDecimal qtyOnStock = null;
		
		BigDecimal qtyOnRackOld = null;
		BigDecimal qtyOnStockOld = null;
		//	Objetos de C_OrderLine
		BigDecimal qty = null;
		BigDecimal qtyOld = null;
		BigDecimal price = null;
		BigDecimal lineNet = null;
		
		try {
			Tax tax = new Tax(con);
			for(DisplayProductItem item : products){
    			if((item.getQtySelected() != null 
    					&& item.getQtySelected().length() > 0
    					&& (qty = new BigDecimal(item.getQtySelected())).signum() != 0)
    					|| (item.getQtyOnStock() != null
		    					&& item.getQtyOnStock().length() > 0
		    					&& (qtyOnStock = new BigDecimal(item.getQtyOnStock())).signum() >= 0)
		    			|| (item.getQtyOnRack() != null
		    					&& item.getQtyOnRack().length() > 0
		    					&& (qtyOnRack = new BigDecimal(item.getQtyOnRack())).signum() >= 0)){
    				
    				//	Establece los valores obtenidos de la actividad de seleccion de productos
    				qty = Env.getNumber(item.getQtySelected());
    				
    				qtyOld = Env.getNumber(item.getQtyOld());
    				
    				//	Se Calcula y Guarda la Linea de la Orden de Venta
    				if(!qty.equals(Env.ZERO)
    						&& !qty.equals(qtyOld)){
    					
    					price = new BigDecimal(item.getPrice());
	    				lineNet = qty.multiply(price).setScale(2, BigDecimal.ROUND_HALF_UP);
	    				//	Clear MP
	    				m_OrderLine.clear();
	    				m_OrderLine.set_Value(pos_C_Order_ID, m_Order.getID());
	    				m_OrderLine.set_Value(pos_M_Warehouse_ID, Env.getM_Warehouse_ID(ctx));
	    				m_OrderLine.set_Value(pos_M_Product_ID, item.getM_Product_ID());
	    				m_OrderLine.set_Value(pos_C_UOM_ID, item.getC_UOM_ID());
	    				m_OrderLine.set_Value(pos_PriceList, price);
	    				m_OrderLine.set_Value(pos_PriceActual, price);
	    				m_OrderLine.set_Value(pos_PriceEntered, price);
	    				m_OrderLine.set_Value(pos_Qty, qty);
	    				m_OrderLine.set_Value(pos_QtyOrdered, qty);
	    				m_OrderLine.set_Value(pos_QtyEntered, qty);
	    				m_OrderLine.set_Value(pos_LineNetAmt, lineNet);
	    				
	    				m_OrderLine.set_Value(pos_C_Activity_ID, v_C_Activity_ID);	//	Cable, Listo
	    				
	    				m_OrderLine.set_Value(pos_QtyDelivered, Env.ZERO);
	    				m_OrderLine.set_Value(pos_QtyInvoiced, Env.ZERO);
	    				m_OrderLine.set_Value(pos_QtyReserved, Env.ZERO);
	    				m_OrderLine.set_Value(pos_PriceLimit, Env.ZERO);
	    				m_OrderLine.set_Value(pos_C_BPartner_Location_ID, m_C_BPartner_Location_ID);
	
	    				//	Load Default Tax
	    				tax.loadTax(item.getC_TaxCategory_ID());
	    				if(tax.getC_Tax_ID() == 0)
	    					throw new Exception("No Tax");
	    				//	Set Tax
	    				m_OrderLine.set_Value(pos_C_Tax_ID, tax.getC_Tax_ID());
	    				
	    				//	Insert Or Update
	    				m_OrderLine.setIDUpdate(item.getForeign_ID());
	    				m_OrderLine.saveEx();
	    				
    				}
    				
    				//	Handle Inventory
    				if((item.getQtyOnStock() != null 
    						&& item.getQtyOnStock().length() > 0 
    						&& (((qtyOnStock = new BigDecimal(item.getQtyOnStock())).signum() >= 0 && !suggested) 
    								|| (qtyOnStock = new BigDecimal(item.getQtyOnStock())).signum() > 0 && suggested))
    		    			|| (item.getQtyOnRack() != null 
    		    				&& item.getQtyOnRack().length() > 0
    		    				&& (((qtyOnRack = new BigDecimal(item.getQtyOnRack())).signum() >= 0 && !suggested)
    		    					|| (qtyOnRack = new BigDecimal(item.getQtyOnRack())).signum() > 0 && !suggested))){
    				
    					qtyOnStock = Env.getNumber(item.getQtyOnStock());
        				
        				qtyOnRack = Env.getNumber(item.getQtyOnRack());
        				
        				//	Establece los valores de las cantidades 
        				//	anteriormete seleccionadas
        				qtyOnStockOld = Env.getNumber(item.getQtyOnStockOld());
        				
        				qtyOnRackOld = Env.getNumber(item.getQtyOnRackOld());
    				
        				//	Se Inserta la linea de Inventario
        				if((!qtyOnRack.equals(qtyOnRackOld) || qtyOnRack.equals(Env.ZERO))
        						|| (!qtyOnStock.equals(qtyOnStockOld) || qtyOnStock.equals(Env.ZERO))){
        					//	Verifica si esta creado un inventario, de no ser asi lo crea
        					if(!created){
        						//	Consulta o Crea un Nuevo Inventario
        			    		if(m_XX_MB_CustomerInventory_ID == 0){
        			    			m_XX_MB_CustomerInventory_ID = MBCustomerInventory.getCustomerInventoryFromOrder(
        			    					ctx, con, m_Order.getID(), false);
        			    			if(m_XX_MB_CustomerInventory_ID == 0){
        			    				MBCustomerInventory m_CustomerInventory = new MBCustomerInventory(ctx, con, 0);
        			    				m_CustomerInventory.set_Value("C_Order_ID", m_Order.getID());
        			    				m_CustomerInventory.set_Value("C_BPartner_ID", m_C_BPartner_ID);
        			    				m_CustomerInventory.set_Value("C_BPartner_Location_ID", m_C_BPartner_Location_ID);
        			    				m_CustomerInventory.set_Value("DateDoc", Env.getContext(ctx, "#Date"));
        			    				m_CustomerInventory.set_Value("Description", Env.getContextDateFormat(ctx, "#Date", "yyyy-MM-dd hh:mm:ss", "dd/MM/yyyy") + " - " + DocumentNo);
        			    				m_CustomerInventory.set_Value("IsActive", "Y");
        			    				m_CustomerInventory.saveEx();
        			    				m_XX_MB_CustomerInventory_ID = m_CustomerInventory.getID();
        			    				Env.setContext(ctx, "#XX_MB_CustomerInventory_ID", m_XX_MB_CustomerInventory_ID);
        			    				
        			    				//Msg.toastMsg(getCtx(), "C_Order_ID " + m_C_Order_ID);
        			    				
        			    			}
        			    			created = true;
        			    		}
        					}
        					
        					//	Clear MP
    	    				m_CustomerInventoryLine.clear();
    	    				m_CustomerInventoryLine.set_Value(pos_XX_MB_CustomerInventory_ID, m_XX_MB_CustomerInventory_ID);
    	    				m_CustomerInventoryLine.set_Value(pos_M_ProductCI_ID, item.getM_Product_ID());
    	    				m_CustomerInventoryLine.set_Value(pos_XX_QtyInStock, qtyOnStock);
    	    				m_CustomerInventoryLine.set_Value(pos_XX_QtyOnRack, qtyOnRack);
    	    				m_CustomerInventoryLine.set_Value(pos_IsActive, "Y");
    	    				//	Inventory Line
    	    				m_CustomerInventoryLine.set_Value(pos_LineInventory, Env.ZERO);
    	    				
    	    				//	Insert Or Update
    	    				m_CustomerInventoryLine.setIDUpdate(item.getForeign2_ID());
    	    				m_CustomerInventoryLine.saveEx();
    	    				
        				}
    				}
    				
    			} else{
    				//	Se elimina la linea del Pedidio
    				if(item.getForeign_ID() > 0){
    					//	Clear Order
	    				m_OrderLine.clear();
	    				m_OrderLine.setIDUpdate(item.getForeign_ID());
	    				m_OrderLine.deleteEx();
	    				
    				}
    				//	Se elimina la linea del Inventario
    				if(item.getForeign2_ID() > 0){
        				//	Clear Inventory
        				m_CustomerInventoryLine.clear();
        				m_CustomerInventoryLine.setIDUpdate(item.getForeign2_ID());
        				m_CustomerInventoryLine.deleteEx();
        				//updateHeap = true;
        			}
    			}
    			updateHead = true;
    		}
			//	Update Header
			if(updateHead){
				m_Order.updateAmt();
				m_Order.saveEx();	
			}
			
		} catch (Exception e) {
			Log.e("Exception", "Exception", e);
			msg =  e.getMessage();
		}
		
		return msg;
	}
	
	/**
	 * Crea las lineas del pedido desde un ArrayList de Productos
	 * @author Yamel Senih 22/08/2012, 03:45:52
	 * @param ctx
	 * @param con
	 * @param products
	 * @param m_Order
	 * @return
	 * @return String
	 */
	public static String createLinesFromProducts(Context ctx, DB con, ArrayList<DisplayProductItem> products, MBOrder m_Order){
		
		int m_C_BPartner_Location_ID = m_Order.get_ValueAsInt("C_BPartner_Location_ID");
		
		String msg = null;
		boolean updateHead = false;
		
		MBOrderLine m_OrderLine = new MBOrderLine(ctx, con, 0);
		MPTableInfo tInfo = m_OrderLine.getTableInfo();
		int pos_C_Order_ID = tInfo.getColumnIndex("C_Order_ID");
		int pos_M_Warehouse_ID = tInfo.getColumnIndex("M_Warehouse_ID");
		int pos_M_Product_ID = tInfo.getColumnIndex("M_Product_ID");
		int pos_PriceList = tInfo.getColumnIndex("PriceList");
		int pos_PriceActual = tInfo.getColumnIndex("PriceActual");
		int pos_PriceEntered = tInfo.getColumnIndex("PriceEntered");
		int pos_Qty = tInfo.getColumnIndex("Qty");
		int pos_QtyOrdered = tInfo.getColumnIndex("QtyOrdered");
		int pos_QtyEntered = tInfo.getColumnIndex("QtyEntered");
		int pos_QtyDelivered = tInfo.getColumnIndex("QtyDelivered");
		int pos_QtyInvoiced = tInfo.getColumnIndex("QtyInvoiced");
		int pos_QtyReserved = tInfo.getColumnIndex("QtyReserved");
		int pos_PriceLimit = tInfo.getColumnIndex("PriceLimit");
		int pos_C_BPartner_Location_ID = tInfo.getColumnIndex("C_BPartner_Location_ID");   		
		int pos_LineNetAmt = tInfo.getColumnIndex("LineNetAmt");
		int pos_C_UOM_ID = tInfo.getColumnIndex("C_UOM_ID");
		int pos_C_Tax_ID = tInfo.getColumnIndex("C_Tax_ID");
		int pos_C_Activity_ID = tInfo.getColumnIndex("C_Activity_ID");
		String v_C_Activity_ID = Env.getContext(ctx, "#C_Activity_ID");
		
		//	Objetos de C_OrderLine
		BigDecimal qty = null;
		BigDecimal qtyOld = null;
		BigDecimal price = null;
		BigDecimal lineNet = null;
		
		try {
			Tax tax = new Tax(con);
			for(DisplayProductItem item : products){
    			if(item.getQtySelected() != null 
    					&& item.getQtySelected().length() > 0
    					&& (qty = new BigDecimal(item.getQtySelected())).signum() != 0){
    				
    				//	Establece los valores obtenidos de la actividad de seleccion de productos
    				qty = Env.getNumber(item.getQtySelected());
    				
    				qtyOld = Env.getNumber(item.getQtyOld());
    				
    				//	Se Calcula y Guarda la Linea de la Orden de Venta
    				if(!qty.equals(Env.ZERO)
    						&& !qty.equals(qtyOld)){
    					Log.i("QtySselected", " " + item.getQtySelected());
    					price = new BigDecimal(item.getPrice());
	    				lineNet = qty.multiply(price).setScale(2, BigDecimal.ROUND_HALF_UP);
	    				//	Clear MP
	    				m_OrderLine.clear();
	    				m_OrderLine.set_Value(pos_C_Order_ID, m_Order.getID());
	    				m_OrderLine.set_Value(pos_M_Warehouse_ID, Env.getM_Warehouse_ID(ctx));
	    				m_OrderLine.set_Value(pos_M_Product_ID, item.getM_Product_ID());
	    				m_OrderLine.set_Value(pos_C_UOM_ID, item.getC_UOM_ID());
	    				m_OrderLine.set_Value(pos_PriceList, price);
	    				m_OrderLine.set_Value(pos_PriceActual, price);
	    				m_OrderLine.set_Value(pos_PriceEntered, price);
	    				m_OrderLine.set_Value(pos_Qty, qty);
	    				m_OrderLine.set_Value(pos_QtyOrdered, qty);
	    				m_OrderLine.set_Value(pos_QtyEntered, qty);
	    				m_OrderLine.set_Value(pos_LineNetAmt, lineNet);
	    				
	    				m_OrderLine.set_Value(pos_C_Activity_ID, v_C_Activity_ID);	//	Cable, Listo
	    				
	    				m_OrderLine.set_Value(pos_QtyDelivered, Env.ZERO);
	    				m_OrderLine.set_Value(pos_QtyInvoiced, Env.ZERO);
	    				m_OrderLine.set_Value(pos_QtyReserved, Env.ZERO);
	    				m_OrderLine.set_Value(pos_PriceLimit, Env.ZERO);
	    				m_OrderLine.set_Value(pos_C_BPartner_Location_ID, m_C_BPartner_Location_ID);
	
	    				//	Load Default Tax
	    				tax.loadTax(item.getC_TaxCategory_ID());
	    				if(tax.getC_Tax_ID() == 0)
	    					throw new Exception("No Tax");
	    				//	Set Tax
	    				m_OrderLine.set_Value(pos_C_Tax_ID, tax.getC_Tax_ID());
	    				
	    				//	Insert Or Update
	    				m_OrderLine.setIDUpdate(item.getForeign_ID());
	    				m_OrderLine.saveEx();
	    				
    				}
    				
    			} else{
    				//	Se elimina la linea del Pedidio
    				if(item.getForeign_ID() > 0){Log.i("DELETE QtySselected", " " + item.getQtySelected());
    					//	Clear Order
	    				m_OrderLine.clear();
	    				m_OrderLine.setIDUpdate(item.getForeign_ID());
	    				m_OrderLine.deleteEx();
	    				
    				}
    			}
    			updateHead = true;
    		}
			//	Update Header
			if(updateHead){
				m_Order.updateAmt();
				m_Order.saveEx();	
			}
			
		} catch (Exception e) {
			Log.e("Exception", "Exception", e);
			msg =  e.getMessage();
		}
		
		return msg;
	}
	
}
