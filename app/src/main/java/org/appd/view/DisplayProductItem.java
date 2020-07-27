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

import java.math.BigDecimal;

import org.appd.base.Env;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Yamel Senih
 *
 */
public class DisplayProductItem implements Parcelable{
	
	
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih 14/05/2012, 04:08:11
	 * @param m_M_Product_ID
	 * @param m_Name
	 * @param m_Description
	 */
	public DisplayProductItem(int m_M_Product_ID, String m_Name, String m_Description){
		this.m_M_Product_ID = m_M_Product_ID;
		this.m_Value = m_Name;
		this.m_Name = m_Description;
	}
	
	/**
	 * Utilizado para cargar datos desde la linea de la orden
	 * Modificado Por Yamel Senih 16/07/2012, 00:38:00
	 * Se agregó el parametro Foreign2
	 * Modificado Por Yamel Senih 16/08/2012, 11:38:00
	 * Se agregaron las columnas Foreign3 y QtyReturn
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 16/07/2012, 00:39:26
	 * @param m_M_Product_ID
	 * @param m_Foreign_ID
	 * @param m_Foreign2_ID
	 * @param m_QtySelected
	 * @param m_QtyOnStock
	 * @param m_QtyOnRack
	 */
	public DisplayProductItem(int m_M_Product_ID, 
			int m_Foreign_ID, int m_Foreign2_ID, int m_Foreign3_ID,  
			String m_QtySelected, String m_QtyOnStock, String m_QtyOnRack, String m_QtyReturn){
		this.m_M_Product_ID = m_M_Product_ID;
		this.m_Foreign_ID = m_Foreign_ID;
		this.m_Foreign2_ID = m_Foreign2_ID;
		this.m_Foreign3_ID = m_Foreign3_ID;
		this.m_QtySelected = m_QtySelected;
		this.m_QtyOld = m_QtySelected;
		this.m_QtyOnStock = m_QtyOnStock;
		this.m_QtyOnStockOld = m_QtyOnStock;
		this.m_QtyOnRack = m_QtyOnRack;
		this.m_QtyOnRackOld = m_QtyOnRack;
		this.m_QtyReturn = m_QtyReturn;
		this.m_QtyReturnOld = m_QtyReturn;
	}
	
	/**
	 * Utilizado en la Devolucion
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 16/08/2012, 20:39:48
	 * @param m_M_Product_ID
	 * @param m_Foreign_ID
	 * @param m_QtyMax
	 */
	public DisplayProductItem(int m_M_Product_ID, 
			int m_Foreign_ID, String m_QtyReturn){
		this.m_M_Product_ID = m_M_Product_ID;
		this.m_Foreign_ID = m_Foreign_ID;
		this.m_QtyReturn = m_QtyReturn;
		this.m_QtyReturnOld = m_QtyReturn;
	}
	
	
	/**
	 * Utilizado en el listado de productos
	 * *** Constructor de la Clase ***
	 * @author Yamel Senih 16/08/2012, 20:40:37
	 * @param m_M_Product_ID
	 * @param m_Foreign_ID
	 * @param m_Foreign2_ID
	 * @param m_Foreign3_ID
	 * @param m_Value
	 * @param m_Name
	 * @param m_PriceList
	 * @param m_QtySelected
	 * @param m_QtyOnStock
	 * @param m_QtyOnRack
	 * @param m_QtyReturn
	 * @param m_QtyMax
	 * @param m_M_Product_Category_ID
	 * @param m_C_TaxCategory_ID
	 * @param m_C_UOM_ID
	 */
	public DisplayProductItem(int m_M_Product_ID, int m_Foreign_ID, int m_Foreign2_ID, int m_Foreign3_ID, 
			String m_Value, String m_Name, String m_PriceList, String m_QtyMax,
			String m_QtySelected, String m_QtyOnStock, String m_QtyOnRack, String m_QtyReturn, 
			int m_M_Product_Category_ID, int m_C_TaxCategory_ID, 
			int m_C_UOM_ID){
		this.m_M_Product_ID = m_M_Product_ID;
		this.m_Value = m_Value;
		this.m_Name = m_Name;
		this.m_PriceList = m_PriceList;
		this.m_QtySelected = m_QtySelected;
		this.m_QtyOnStock = m_QtyOnStock;
		this.m_QtyOnRack = m_QtyOnRack;
		this.m_QtyReturn = m_QtyReturn;
		this.m_QtyMax = m_QtyMax;
		this.m_M_Product_Category_ID = m_M_Product_Category_ID;
		this.m_C_TaxCategory_ID = m_C_TaxCategory_ID;
		this.m_C_UOM_ID = m_C_UOM_ID;
		this.m_Foreign_ID = m_Foreign_ID;
		this.m_Foreign2_ID = m_Foreign2_ID;
		this.m_Foreign3_ID = m_Foreign3_ID;
		this.m_QtyOnStockOld = m_QtyOnStock;
		this.m_QtyOnRackOld = m_QtyOnRack;
		this.m_QtyReturnOld = m_QtyReturn;
	}
	
	public DisplayProductItem(Parcel parcel){
		this(0, null, null);
		readToParcel(parcel);
	}
	
	/**	Record ID			*/
	private int 			m_M_Product_ID = 0;
	/**	Name Item			*/
	private String  		m_Value = null;
	/**	Short Description	*/
	private String 			m_Name = null;
	/**	Image				*/
	private String 			img = null;
	/**	Product Price List	*/
	private String			m_PriceList = null;
	/**	Quantity Selected	*/
	private String			m_QtySelected = null;
	/**	Quantity On Stock	*/
	private String			m_QtyOnStock = null;
	/**	Quantity On Rack	*/
	private String			m_QtyOnRack = null;
	/**	Quantity Return		*/
	private String			m_QtyReturn = null;
	/**	Quantity Old		*/
	private String			m_QtyOld = null;
	/**	Quantity On Stock Old*/
	private String			m_QtyOnStockOld = null;
	/**	Quantity On Rack Old*/
	private String			m_QtyOnRackOld = null;
	/**	Quantity Return	Old	*/
	private String			m_QtyReturnOld = null;
	/**	Max Quantity		*/
	private String			m_QtyMax = null;
	/**	Product Category	*/
	private int			m_M_Product_Category_ID = 0;
	/**	Tax	Category		*/
	private int			m_C_TaxCategory_ID = 0;
	/**	Unit of Measure		*/
	private int			m_C_UOM_ID = 0;
	
	/**	Foreign ID			*/
	private int 			m_Foreign_ID = 0;
	/**	Foreign 2 ID		*/
	private int 			m_Foreign2_ID = 0;
	/**	Foreign 3 ID		*/
	private int 			m_Foreign3_ID = 0;
	
	/**
	 * Obtiene el ID del Producto
	 * @author Yamel Senih 28/04/2012, 00:23:13
	 * @return
	 * @return int
	 */
	public int getM_Product_ID(){
		return m_M_Product_ID;
	}
	
	/**
	 * Obtiene el Codigo del Producto
	 * @author Yamel Senih 28/04/2012, 00:24:01
	 * @return
	 * @return String
	 */
	public String getValue(){
		return m_Value;
	}
	
	/**
	 * Obtiene el nombre del Producto
	 * @author Yamel Senih 28/04/2012, 00:25:17
	 * @return
	 * @return String
	 */
	public String getName(){
		return m_Name;
	}
	
	/**
	 * Obtiene el nombre de la Imagen
	 * @author Yamel Senih 28/04/2012, 00:24:59
	 * @return
	 * @return String
	 */
	public String getImg(){
		return img;
	}
	
	/**
	 * Obtiene la cantidad seleccionada en formato String
	 * @author Yamel Senih 15/05/2012, 04:59:38
	 * @return
	 * @return String
	 */
	public String getQtySelected(){
		return m_QtySelected;
	}
	
	/**
	 * Establece la cantidad Seleccionada
	 * @author Yamel Senih 14/05/2012, 04:03:39
	 * @param m_QtySelected
	 * @return void
	 */
	public void setQtySelected(String m_QtySelected){
		this.m_QtySelected = m_QtySelected;
	}
	
	/**
	 * Obtiene la Cantidad a devolver
	 * @author Yamel Senih 16/08/2012, 11:04:57
	 * @return
	 * @return String
	 */
	public String getQtyReturn(){
		return m_QtyReturn;
	}
	
	/**
	 * Obtiene la cantidad maxima
	 * @author Yamel Senih 16/08/2012, 20:37:26
	 * @return
	 * @return String
	 */
	public String getQtyMax(){
		return m_QtyMax;
	}
	
	/**
	 * Obtiene la Cantidad Temporal a Devolver
	 * @author Yamel Senih 16/08/2012, 20:17:18
	 * @return
	 * @return String
	 */
	public String getQtyReturnOld(){
		return m_QtyReturnOld;
	}
	
	/**
	 * Establece la cantidad a devolver
	 * @author Yamel Senih 16/08/2012, 11:05:16
	 * @param m_QtyReturn
	 * @return void
	 */
	public void setQtyReturn(String m_QtyReturn){
		this.m_QtyReturn = m_QtyReturn;
	}
	
	/**
	 * Obtiene la cantidad contada en la toma de inventario de Stock
	 * @author Yamel Senih 13/06/2012, 01:00:50
	 * @return
	 * @return String
	 */
	public String getQtyOnStock(){
		return m_QtyOnStock;
	}
	
	/**
	 * Obtiene la cantidad contada en el Rack
	 * @author Yamel Senih 25/06/2012, 19:53:45
	 * @return
	 * @return String
	 */
	public String getQtyOnRack(){
		return m_QtyOnRack;
	}
		
	/**
	 * Establece la Cantidad Contada a partir del inventario en Stock
	 * @author Yamel Senih 13/06/2012, 01:01:16
	 * @param m_QtyOnStock
	 * @return void
	 */
	public void setQtyOnStock(String m_QtyOnStock){
		this.m_QtyOnStock = m_QtyOnStock;
	}
	
	/**
	 * Obtiene la Resta del valor maximo 
	 * menos el valor de prueba
	 * @author Yamel Senih 16/08/2012, 21:42:33
	 * @param qtyTest
	 * @return
	 * @return BigDecimal
	 */
	public BigDecimal validExced(String qtyTest){
		BigDecimal qtyTestConv = Env.ZERO;
		BigDecimal qtyMaxConv = Env.ZERO;
		if(qtyTest != null
				&& qtyTest.length() > 0)
			qtyTestConv = new BigDecimal(qtyTest);
		
		if(getQtyMax() != null
				&& getQtyMax().length() > 0)
			qtyMaxConv = new BigDecimal(getQtyMax());
		
		return qtyMaxConv.subtract(qtyTestConv);
	}
	
	/**
	 * Establece la cantidad contada a partir del inventario en Rack
	 * @author Yamel Senih 25/06/2012, 19:54:21
	 * @param m_QtyOnRack
	 * @return void
	 */
	public void setQtyOnRack(String m_QtyOnRack){
		this.m_QtyOnRack = m_QtyOnRack;
	}
	
	/**
	 * Obtiene el Precio del Producto
	 * @author Yamel Senih 19/05/2012, 19:08:39
	 * @return
	 * @return String
	 */
	public String getPrice(){
		return m_PriceList;
	}
	
	/**
	 * Obtiene la categoria de impuesto a la que pertenece el producto
	 * @author Yamel Senih 19/05/2012, 19:17:09
	 * @return
	 * @return int
	 */
	public int getC_TaxCategory_ID(){
		return m_C_TaxCategory_ID;
	}

	/**
	 * Obtiene ID de la Categoria de Producto
	 * @author Yamel Senih 19/05/2012, 19:33:19
	 * @return
	 * @return int
	 */
	public int getM_Product_Category_ID(){
		return m_M_Product_Category_ID;
	}
	
	/**
	 * Obtiene la Unidad de Medida base del Producto
	 * @author Yamel Senih 20/05/2012, 02:42:22
	 * @return
	 * @return int
	 */
	public int getC_UOM_ID(){
		return m_C_UOM_ID;
	}
	
	/**
	 * Obtiene la cantidad seleccionada en la linea 
	 * de la orden de venta como temporal
	 * @author Yamel Senih 23/05/2012, 03:57:40
	 * @return
	 * @return String
	 */
	public String getQtyOld(){
		return m_QtyOld;
	}
	
	/**
	 * Obtiene la cantidad temporal contada en Stock
	 * @author Yamel Senih 13/06/2012, 18:31:36
	 * @return
	 * @return String
	 */
	public String getQtyOnStockOld(){
		return m_QtyOnStockOld;
	}
	
	/**
	 * Obtiene la cantidad temporal contada en Rack
	 * @author Yamel Senih 07/07/2012, 12:05:01
	 * @return
	 * @return String
	 */
	public String getQtyOnRackOld(){
		return m_QtyOnRackOld;
	}
	
	/***
	 * Obtiene el ID Foraneo utilizado en la linea de la orden
	 * @author Yamel Senih 23/05/2012, 02:43:44
	 * @return
	 * @return int
	 */
	public int getForeign_ID(){
		return m_Foreign_ID;
	}
	
	/**
	 * Obtiene el 2 ID Foraneo utilizado en el inventario cuando
	 * se toma junto con la orden de Venta
	 * @author Yamel Senih 07/07/2012, 11:28:42
	 * @return
	 * @return int
	 */
	public int getForeign2_ID(){
		return m_Foreign2_ID;
	}
	
	/**
	 * Obtiene el 3 ID Foraneo utilizado en el inventario cuando
	 * se toma junto con la orden de Venta
	 * @author Yamel Senih 16/08/2012, 11:34:19
	 * @return
	 * @return int
	 */
	public int getForeign3_ID(){
		return m_Foreign3_ID;
	}
	
	/* (non-Javadoc)
	 * @see android.os.Parcelable#describeContents()
	 */
	@Override
	public int describeContents() {
		return hashCode();
	}

	/* (non-Javadoc)
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel parcel, int arg1) {
		parcel.writeInt(m_M_Product_ID);
		parcel.writeString(m_Value);
		parcel.writeString(m_Name);
		parcel.writeString(img);
		parcel.writeString(m_QtySelected);
		parcel.writeString(m_QtyOnStock);
		parcel.writeString(m_QtyOnRack);
		parcel.writeString(m_QtyReturn);
		parcel.writeString(m_QtyMax);
		parcel.writeString(m_PriceList);
		parcel.writeInt(m_M_Product_Category_ID);
		parcel.writeInt(m_C_TaxCategory_ID);
		parcel.writeInt(m_C_UOM_ID);
		parcel.writeInt(m_Foreign_ID);
		parcel.writeInt(m_Foreign2_ID);
		parcel.writeInt(m_Foreign3_ID);
		parcel.writeString(m_QtyOld);
		parcel.writeString(m_QtyOnStockOld);
		parcel.writeString(m_QtyOnRackOld);
		parcel.writeString(m_QtyReturnOld);
	}
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public DisplayProductItem createFromParcel(Parcel parcel) {
			return new DisplayProductItem(parcel);
		}
		public DisplayProductItem[] newArray(int size) {
			return new DisplayProductItem[size];
		}
	};
	
	public void readToParcel(Parcel parcel){
		m_M_Product_ID = parcel.readInt();
		m_Value = parcel.readString();
		m_Name = parcel.readString();
		img = parcel.readString();
		m_QtySelected = parcel.readString();
		m_QtyOnStock = parcel.readString();
		m_QtyOnRack = parcel.readString();
		m_QtyReturn = parcel.readString();
		m_QtyMax = parcel.readString();
		m_PriceList = parcel.readString();
		m_M_Product_Category_ID = parcel.readInt();
		m_C_TaxCategory_ID = parcel.readInt();
		m_C_UOM_ID = parcel.readInt();
		m_Foreign_ID = parcel.readInt();
		m_Foreign2_ID = parcel.readInt();
		m_Foreign3_ID = parcel.readInt();
		m_QtyOld = parcel.readString();
		m_QtyOnStockOld = parcel.readString();
		m_QtyOnRackOld = parcel.readString();
		m_QtyReturnOld = parcel.readString();
	}
	
}
