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
package org.appd.util;

/**
 * @author Yamel Senih
 *
 */
public class DisplayOrderItem extends DisplayItem {

	/**
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 15/05/2012, 14:57:59
	 * @param record_ID
	 * @param name
	 * @param description
	 */
	public DisplayOrderItem(int record_ID, String name, String description) {
		super(record_ID, name, description);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 16/07/2012, 00:43:56
	 * @param record_ID
	 * @param m_XX_MB_CustomerInventoryLine_ID
	 * @param m_M_Product_ID
	 * @param name
	 * @param description
	 * @param qtyOrdered
	 * @param qtyOnStock
	 * @param qtyOnRack
	 * @param productPrice
	 * @param lineNetAmt
	 */
	public DisplayOrderItem(int record_ID, int m_XX_MB_CustomerInventoryLine_ID, int m_M_Product_ID, 
			String name, String description, 
			String qtyOrdered, String qtyOnStock, String qtyOnRack, String productPrice, 
			String lineNetAmt) {
		this(record_ID, name, description);
		this.m_XX_MB_CustomerInventoryLine_ID = m_XX_MB_CustomerInventoryLine_ID;
		this.m_M_Product_ID = m_M_Product_ID;
		this.qtyOrdered = qtyOrdered;
		this.qtyOnStock = qtyOnStock;
		this.qtyOnRack = qtyOnRack;
		this.productPrice = productPrice;
		this.lineNetAmt = lineNetAmt;
	}
	
	private int m_M_Product_ID = 0;
	/**	Qty Ordered			*/
	private String qtyOrdered = null;
	/**	Qty On Stock			*/
	private String qtyOnStock = null;
	/**	Qty On Rack			*/
	private String qtyOnRack = null;
	/**	Product Price		*/
	private String productPrice = null;
	/**	Line Net Amount		*/
	private String lineNetAmt = null;
	/**	Customer Inventory Line ID	*/
	private int m_XX_MB_CustomerInventoryLine_ID = 0;
	
	/**
	 * Obtiene la Cantidad Ordenada en la Orden
	 * @author Yamel Senih 15/05/2012, 14:58:37
	 * @return
	 * @return String
	 */
	public String getQtyOrdered(){
		return qtyOrdered;
	}
	
	/**
	 * Obtiene la Cantidad en Stock
	 * @author Yamel Senih 13/06/2012, 18:22:06
	 * @return
	 * @return String
	 */
	public String getQtyOnStock(){
		return qtyOnStock;
	}

	/**
	 * Obtiene la Cantidad en Rack
	 * @author Yamel Senih 25/06/2012, 20:22:39
	 * @return
	 * @return String
	 */
	public String getQtyOnRack(){
		return qtyOnRack;
	}
	
	/***
	 * Obtiene el Precio unitario Por Producto
	 * @author Yamel Senih 15/05/2012, 14:59:09
	 * @return
	 * @return double
	 */
	public String getProductPrice(){
		return productPrice;
	}
	
	/**
	 * Obtiene el Neto de linea
	 * @author Yamel Senih 15/05/2012, 15:02:12
	 * @return
	 * @return double
	 */
	public String getLineNetAmt(){
		return lineNetAmt;
	}
	
	/**
	 * Obtiene el ID del Producto
	 * @author Yamel Senih 23/05/2012, 02:30:42
	 * @return
	 * @return int
	 */
	public int getM_Product_ID(){
		return m_M_Product_ID;
	}
	
	/**
	 * Obtiene el ID de la Linea de Inventario Tomada
	 * @author Yamel Senih 16/07/2012, 00:42:32
	 * @return
	 * @return int
	 */
	public int getXX_MB_CustomerInventoryLine_ID(){
		return m_XX_MB_CustomerInventoryLine_ID;
	}
}
