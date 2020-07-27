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
public class MBCustomerInventoryLine extends MP {

	private final String TABLENAME = "XX_MB_CustomerInventoryLine";
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 22/08/2012, 02:42:05
	 * @param ctx
	 * @param ID
	 */
	public MBCustomerInventoryLine(Context ctx, int ID) {
		super(ctx, ID);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 22/08/2012, 02:42:02
	 * @param ctx
	 * @param rs
	 */
	public MBCustomerInventoryLine(Context ctx, Cursor rs) {
		super(ctx, rs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 22/08/2012, 02:41:58
	 * @param ctx
	 * @param con_tx
	 * @param ID
	 */
	public MBCustomerInventoryLine(Context ctx, DB con_tx, int ID) {
		super(ctx, con_tx, ID);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see org.sg.model.MP#beforeUpdate()
	 */
	@Override
	public boolean beforeSave(boolean isNew) {
		if(isNew){
			/*set_Value("SalesRep_ID", Env.getAD_User_ID(m_ctx));
			//set_Value("DocStatus", "CO");
			//set_Value("M_PriceList_ID", 1000013);//Env.getContextAsInt(m_ctx, "#M_Pricelist_ID"));
			set_Value("PaymentRule", Env.getContext(m_ctx, "#PaymentRule"));
			set_Value("C_PaymentTerm_ID", Env.getContextAsInt(m_ctx, "#C_PaymentTerm_ID"));
			MSequence.updateSequence(con, get_ValueAsInt("C_DocTypeTarget_ID"), Env.getAD_User_ID(m_ctx));*/
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
	 * Crea una Linea de Inventario del Cliente a partir de 
	 * un Item de Producto seleccionado en la actividad de seleccion
	 * de producto
	 * @author Yamel Senih 12/07/2012, 19:20:17
	 * @param products
	 * @return
	 * @return String
	 */
	public static String createLinesFromProduts(Context ctx, DB con, ArrayList<DisplayProductItem> products, 
			int m_XX_MB_CustomerInventory_ID){
		
		MBCustomerInventoryLine m_CustomerInventoryLine = new MBCustomerInventoryLine(ctx, con, 0);
		
		MPTableInfo tInfo = m_CustomerInventoryLine.getTableInfo();
		
		int pos_XX_MB_CustomerInventory_ID = tInfo.getColumnIndex("XX_MB_CustomerInventory_ID");
		
		int pos_M_Product_ID = tInfo.getColumnIndex("M_Product_ID");
		
		int pos_XX_QtyInStock = tInfo.getColumnIndex("XX_QtyInStock");
		int pos_XX_QtyOnRack = tInfo.getColumnIndex("XX_QtyOnRack");
		
		int pos_IsActive = tInfo.getColumnIndex("IsActive");
		int pos_Line = tInfo.getColumnIndex("Line");
		
		BigDecimal qtyOnRack = null;
		BigDecimal qtyOnStock = null;
		
		BigDecimal qtyOnRackOld = null;
		BigDecimal qtyOnStockOld = null;
		
		String msg = null;
		try {
			for(DisplayProductItem item : products){
				if((item.getQtyOnRack() != null 
    					&& item.getQtyOnRack().length() > 0
    					&& (qtyOnRack = new BigDecimal(item.getQtyOnRack())).signum() >= 0)
    					|| (item.getQtyOnStock() != null 
		    					&& item.getQtyOnStock().length() > 0
		    					&& (qtyOnStock = new BigDecimal(item.getQtyOnStock())).signum() >= 0)){
    			
					//	Establece Valores obtenidos de la Actividad
    				
					qtyOnStock = Env.getNumber(item.getQtyOnStock());
    				qtyOnRack = Env.getNumber(item.getQtyOnRack());
    				
    				qtyOnStockOld = Env.getNumber(item.getQtyOnStockOld());
    				qtyOnRackOld = Env.getNumber(item.getQtyOnRackOld());
    				
    				/*Log.i("qtyOnStock", " -- " + qtyOnStock);
    				Log.i("qtyOnRack", " -- " + qtyOnRack);
    				*/
    				
    				if((/*!qtyOnRack.equals(Env.ZERO) 
    						&& */!qtyOnRack.equals(qtyOnRackOld) || qtyOnRack.equals(Env.ZERO))
    						|| (/*!qtyOnStock.equals(Env.ZERO) 
		    						&& */!qtyOnStock.equals(qtyOnStockOld) || qtyOnStock.equals(Env.ZERO))){
    					
    					//	Clear MP
    					
	    				m_CustomerInventoryLine.clear();
	    				m_CustomerInventoryLine.set_Value(pos_XX_MB_CustomerInventory_ID, m_XX_MB_CustomerInventory_ID);
	    				m_CustomerInventoryLine.set_Value(pos_M_Product_ID, item.getM_Product_ID());
	    				m_CustomerInventoryLine.set_Value(pos_XX_QtyInStock, qtyOnStock);
	    				m_CustomerInventoryLine.set_Value(pos_XX_QtyOnRack, qtyOnRack);
	    				m_CustomerInventoryLine.set_Value(pos_IsActive, "Y");
	    				//	Line
	    				m_CustomerInventoryLine.set_Value(pos_Line, Env.ZERO);
	    				
	    				//	Insert Or Update
	    				m_CustomerInventoryLine.setIDUpdate(item.getForeign2_ID());
	    				m_CustomerInventoryLine.saveEx();
	    				
    				}		    				
    			} else if(item.getForeign2_ID() > 0){
    				//	Clear
    				m_CustomerInventoryLine.clear();
    				m_CustomerInventoryLine.setIDUpdate(item.getForeign2_ID());
    				m_CustomerInventoryLine.deleteEx();
    				
    				//updateHeap = true;
    			}
    		}
		} catch (Exception e) {
			Log.e("Exception", "Exception", e);
			msg = e.getMessage();
		}
		
		return msg;
	}
	
	/**
	 * Obtiene las lineas del inventario
	 * @author Yamel Senih 22/08/2012, 17:04:31
	 * @param ctx
	 * @param m_XX_MB_CustomerInventory_ID
	 * @return
	 * @return ArrayList<DisplayProductItem>
	 */
	public static ArrayList<DisplayProductItem> getCurrentProducts(Context ctx, int m_XX_MB_CustomerInventory_ID){
    	ArrayList<DisplayProductItem> currentProducts = null;
    	//	Query
    	String sql = new String("SELECT " +
    			"il.M_Product_ID, " +
    			"il.XX_MB_CustomerInventoryLine_ID, " +
    			"il.XX_QtyInStock, " +
    			"il.XX_QtyOnRack " +
    			"FROM XX_MB_CustomerInventoryLine il " +
				"WHERE il.XX_MB_CustomerInventory_ID = " + m_XX_MB_CustomerInventory_ID);
    	//	Open DB
    	DB con = new DB(ctx);
    	con.openDB(DB.READ_ONLY);
    	//	get Result Set
    	Cursor rs = con.querySQL(sql, null);
		//	Load Data
		if(rs.moveToFirst()){
			currentProducts = new ArrayList<DisplayProductItem>();
			do{
				currentProducts.add(new DisplayProductItem(
						rs.getInt(0), 		//	Product ID
						0,					//	Order
						rs.getInt(1), 		//	Record ID
						0, 					//	
						null, 				//	
						rs.getString(2), 	//	Qty Stock
						rs.getString(3),	//	Qty Rack
						null));
			}while(rs.moveToNext());
		}
		//	Close DB
		con.closeDB(rs);
    	return currentProducts;
    }
	
	/**
	 * Obtiene un ArrayList con las Cantidades sugeridas 
	 * para la venta
	 * @author Yamel Senih 28/08/2012, 01:11:39
	 * @param ctx
	 * @param m_CustomerInventory_ID
	 * @param m_C_BPartner_Location_ID
	 * @return
	 * @return ArrayList<DisplayProductItem>
	 */
	public static ArrayList<DisplayProductItem> getCurrentProductsSuggested(Context ctx, 
			int m_CustomerInventory_ID, int m_C_BPartner_Location_ID){
		
		//Msg.toastMsg(ctx, " -- " + m_CustomerInventory_ID);
		
		ArrayList<DisplayProductItem> currentProducts = null;
    	//	Query
    	String sql = new String("SELECT " +
    			"M_Product_ID, " +
    			"XX_MB_CustomerInventoryLine_ID, " +
    			"CASE " +
    			"	WHEN SUM(QtyAvailable) - (SUM(XX_QtyInStock) + SUM(XX_QtyOnRack)) > 0 " +
    			"		THEN ROUND(SUM(QtyAvailable) - (SUM(XX_QtyInStock) + SUM(XX_QtyOnRack))) " +
    			"	ELSE 0 " +
    			"END QtyOrdered, " +
    			"SUM(XX_QtyInStock) XX_QtyInStock, " +
    			"SUM(XX_QtyOnRack) XX_QtyOnRack " +
    			"FROM (SELECT 0 XX_MB_CustomerInventoryLine_ID, " +
    			"		iol.M_Product_ID M_Product_ID, " +
    			"		AVG(iol.QtyAvailable) QtyAvailable, " +
    			"		0 XX_QtyInStock, " +
    			"		0 XX_QtyOnRack, io.MovementDate DateDoc " +
    			"		FROM M_InOut io " +
    			"		INNER JOIN M_InOutLine iol ON(iol.M_InOut_ID = io.M_InOut_ID) " +
    			"		WHERE io.C_BPartner_Location_ID = " + m_C_BPartner_Location_ID + " " +
    			"		GROUP BY io.C_BPartner_Location_ID , iol.M_Product_ID " +  
    			"		HAVING COUNT(io.M_InOut_ID) <= 3 " +
    			"		UNION ALL " +
    			"		SELECT cil.XX_MB_CustomerInventoryLine_ID, " +
    			"		cil.M_Product_ID M_Product_ID, " +
    			"		0 QtyAvailable, " +
    			"		cil.XX_QtyInStock, " +
    			"		cil.XX_QtyOnRack, " +
    			"		ci.DateDoc " +
    			"		FROM XX_MB_CustomerInventory ci " +
    			"		INNER JOIN XX_MB_CustomerInventoryLine cil ON(cil.XX_MB_CustomerInventory_ID = ci.XX_MB_CustomerInventory_ID) " +
    			"		WHERE ci.XX_MB_CustomerInventory_ID = " + m_CustomerInventory_ID + " " +
    			"		ORDER BY DateDoc DESC ) ioci " +
    			"	GROUP BY M_Product_ID");
    	
    	//Log.i("sql ", "-- " + sql);
    	
    	//	Open DB
    	DB con = new DB(ctx);
    	con.openDB(DB.READ_ONLY);
    	//	get Result Set
    	Cursor rs = con.querySQL(sql, null);
		//	Load Data
		if(rs.moveToFirst()){
			currentProducts = new ArrayList<DisplayProductItem>();
			do{
				currentProducts.add(new DisplayProductItem(
						rs.getInt(0), 		//	Product ID
						0,					//	Order
						rs.getInt(1), 		//	Record ID
						0, 					//	
						rs.getString(2), 	//	Qty Suggested
						rs.getString(3), 	//	Qty Stock
						rs.getString(4),	//	Qty Rack
						null));
				//Log.i(" -- ", " " + rs.getInt(0) + "  " + rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
			}while(rs.moveToNext());
		}
		//	Close DB
		con.closeDB(rs);
    	return currentProducts;
	}
	
}
