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
public class MBRMALine extends MP {

	private final String TABLENAME = "M_RMALine";
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 22/08/2012, 00:40:05
	 * @param ctx
	 * @param ID
	 */
	public MBRMALine(Context ctx, int ID) {
		super(ctx, ID);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 22/08/2012, 00:39:58
	 * @param ctx
	 * @param con_tx
	 * @param ID
	 */
	public MBRMALine(Context ctx, DB con_tx, int ID) {
		super(ctx, con_tx, ID);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 22/08/2012, 00:40:01
	 * @param ctx
	 * @param rs
	 */
	public MBRMALine(Context ctx, Cursor rs) {
		super(ctx, rs);
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
	 * Crea las lineas de la Autorizacion de Devolucion
	 * a Partir de un ArrayList de productos
	 * @author Yamel Senih 22/08/2012, 00:43:33
	 * @param ctx
	 * @param con
	 * @param products
	 * @param m_M_RMA_ID
	 * @return
	 * @return String
	 */
	public static String createLinesFromProducts(Context ctx, DB con, ArrayList<DisplayProductItem> products, 
			int m_M_RMA_ID, MBRMA m_Rma){
		
		if(m_Rma != null){
			m_M_RMA_ID = m_Rma.getID();
		}
		
		String msg = null;
		//	RMA Line
		MBRMALine m_RMALine = new MBRMALine(ctx, con, 0);
		MPTableInfo tInfo = m_RMALine.getTableInfo();
		int pos_M_RMA_ID = tInfo.getColumnIndex("M_RMA_ID");
		int pos_M_InOutLine_ID = tInfo.getColumnIndex("M_InOutLine_ID");
		int pos_Amt = tInfo.getColumnIndex("Amt");
		int pos_QtyReturn = tInfo.getColumnIndex("Qty");		
		int pos_LineNetAmt = tInfo.getColumnIndex("LineNetAmt");
		
		//	Objetos de M_RMALine
		 
		BigDecimal qtyReturn = null;
		BigDecimal qtyReturnOld = null;
		BigDecimal amt = null;
		BigDecimal lineAmtNet = null;
		boolean updateHead = false;
		
		try {
			for(DisplayProductItem item : products){
    			if((item.getQtyReturn() != null 
    					&& item.getQtyReturn().length() > 0
    					&& (qtyReturn = new BigDecimal(item.getQtyReturn())).signum() != 0)){
    				
    				//	Establece los valores obtenidos de la actividad de seleccion de productos
    				qtyReturn = Env.getNumber(item.getQtyReturn());
    				
    				qtyReturnOld = Env.getNumber(item.getQtyReturnOld());
    				
    				
    				//	Se Calcula y Guarda la Linea de la 
    				//	Autorizacion de Devolucion
    				
    				if(!qtyReturn.equals(Env.ZERO)
    						&& !qtyReturn.equals(qtyReturnOld)){
    					//	Establece el Precio
    					amt = Env.getNumber(item.getPrice());
	    				
    					lineAmtNet = qtyReturn.multiply(amt).setScale(2, BigDecimal.ROUND_HALF_UP);
	    				//	Clear MP
	    				m_RMALine.clear();
	    				m_RMALine.set_Value(pos_M_RMA_ID, m_M_RMA_ID);
	    				m_RMALine.set_Value(pos_M_InOutLine_ID, item.getM_Product_Category_ID());
	    				m_RMALine.set_Value(pos_Amt, amt);
	    				m_RMALine.set_Value(pos_QtyReturn, qtyReturn);
	    				m_RMALine.set_Value(pos_LineNetAmt, lineAmtNet);
	    				
	    				//	Insert Or Update
	    				m_RMALine.setIDUpdate(item.getForeign_ID());
	    				m_RMALine.saveEx();
	    				
    				}
    				
    			} else{
    				//	Se elimina la linea de la Autorizacion
    				
    				if(item.getForeign_ID() > 0){
    					//	Clear RMA
	    				m_RMALine.clear();
	    				m_RMALine.setIDUpdate(item.getForeign_ID());
	    				m_RMALine.deleteEx();
    				}
    			}
    			updateHead = true;
    		}
			//	Update Header
			if(updateHead){
				if(m_Rma == null)
					m_Rma = new MBRMA(ctx, con, m_M_RMA_ID);
				m_Rma.updateAmt();
				m_Rma.saveEx();	
			}
			
		} catch (Exception e) {
			Log.e("Exception", "Exception", e);
			msg =  e.getMessage();
		}
		
		return msg;
	}
	
	/**
	 * Crea las lineas de la Autorizacion de Devolucion
	 * a Partir de un ArrayList de productos
	 * @author Yamel Senih 22/08/2012, 02:16:02
	 * @param ctx
	 * @param con
	 * @param products
	 * @param m_M_RMA_ID
	 * @return
	 * @return String
	 */
	public static String createLinesFromProducts(Context ctx, DB con, ArrayList<DisplayProductItem> products, 
			int m_M_RMA_ID){
		return createLinesFromProducts(ctx, con, products, m_M_RMA_ID, null);
	}
	
	/**
	 * Crea las lineas de la Autorizacion de Devolucion
	 * a Partir de un ArrayList de productos
	 * @author Yamel Senih 22/08/2012, 02:17:06
	 * @param ctx
	 * @param con
	 * @param products
	 * @param m_Rma
	 * @return
	 * @return String
	 */
	public static String createLinesFromProducts(Context ctx, DB con, ArrayList<DisplayProductItem> products, MBRMA m_Rma){
		return createLinesFromProducts(ctx, con, products, 0, m_Rma);
	}
	
}
