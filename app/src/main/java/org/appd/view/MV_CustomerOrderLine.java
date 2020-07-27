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
import java.util.ArrayList;
import java.util.LinkedList;

import org.appd.base.DB;
import org.appd.base.Env;
import org.appd.base.R;
import org.appd.model.MBCustomerInventory;
import org.appd.model.MBCustomerInventoryLine;
import org.appd.model.MBOrder;
import org.appd.model.MBOrderLine;
import org.appd.model.MBPartner;
import org.appd.model.MP;
import org.appd.util.AdapterOrderLine;
import org.appd.util.DisplayMenuItem;
import org.appd.util.DisplayOrderItem;
import org.appd.util.DisplayProductItem;
import org.appd.util.Msg;
import org.appd.util.contribution.ActionItem;
import org.appd.util.contribution.QuickAction;
import org.appd.view.custom.Cust_ButtonDocAction;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Yamel Senih
 *
 */
public class MV_CustomerOrderLine extends MVActivity {
	
	private LinkedList<DisplayOrderItem> data = new LinkedList<DisplayOrderItem>();
	private ListView orderLineList;
	private TextView tv_TotalLines;
	private TextView tv_GandTotal;
	private Button butt_Add;
	private Button butt_Inventory;
	private BigDecimal totalLines;
	private BigDecimal grandTotal;
	private boolean updateHeap = false;
	private MBOrder m_Order = null;
	private QuickAction mQAct = null;
	private int m_C_BPartner_Location_ID;
	private boolean suggested = false;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	super.setContentView(R.layout.lv_customer_order_line);
    	
    	Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			param = (DisplayMenuItem)bundle.getParcelable("Param");
		} 
		if(param == null)
			throw new IllegalArgumentException ("No Param");
    	
    	butt_Add = (Button)findViewById(R.id.butt_AddModify);
    	butt_Add.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				loadProductList();
			}
		});
    	
    	butt_Inventory = (Button)findViewById(R.id.butt_Inventory);
    	butt_Inventory.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				loadInventory();
			}
		});
    	
    	tv_TotalLines = (TextView)findViewById(R.id.tv_TotalLines);
    	tv_GandTotal = (TextView)findViewById(R.id.tv_GrandTotal);
    	orderLineList = (ListView)findViewById(R.id.ls_OrderLine);
    	
    	//load();
    	
    	orderLineList.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> padre, View v, int position,
					long id) {
				DisplayOrderItem product = (DisplayOrderItem) orderLineList.getItemAtPosition(position);
				loadLastSO(product.getM_Product_ID(), v);
			}
		});
    	
    	registerForContextMenu(orderLineList);
    	
    	butt_Inventory.setEnabled(false);
    	
    	mQAct = new QuickAction(this);
    }
    
    /**
     * Muestra las ultimas tres Ventas
     * @author Yamel Senih 28/08/2012, 02:55:24
     * @return void
     */
    public void loadLastSO(int m_M_Product_ID, View v){
    	String m_QtyDelivered = getResources().getString(R.string.XX_QtyDelivered);
    	String m_QtyReturn = getResources().getString(R.string.XX_QtyReturn);
    	DB con = new DB(this);
		con.openDB(DB.READ_ONLY);
		
		String sql = new String("SELECT " +
				"io.M_InOut_ID, " +
				"io.DocumentNo," +
				"strftime('%d/%m/%Y', io.MovementDate), " +
				"iol.MovementQty, " +
				"iol.QtyReturn " +
				"FROM M_InOut io " +
				"INNER JOIN M_InOutLine iol ON(iol.M_InOut_ID = io.M_InOut_ID) " +
				"WHERE iol.M_Product_ID = " + m_M_Product_ID + " " +
				"AND io.C_BPartner_Location_ID = " + m_C_BPartner_Location_ID + " " +  
				"ORDER BY io.MovementDate " +
				"LIMIT 3");
		Cursor rs = con.querySQL(sql, null);
		mQAct.clear();
		if(rs.moveToFirst()){
			do {
				mQAct.addActionItem(new ActionItem(
						rs.getInt(0), 
						"<" + rs.getString(1) + ">" + 
						"\n" + 
						"<" + rs.getString(2) + ">" +  
						"\n" + 
						m_QtyDelivered + "=<" + rs.getString(3) + ">" + 
						"\n" + 
						m_QtyReturn + "=<" + rs.getString(4) + ">"));
			} while(rs.moveToNext());
		}
		con.closeDB(rs);
    	mQAct.show(v);
    }
    
    
    /**
     * Obtiene un listado de los productos, Linea y sus cantidades
     * se utiliza para tomar como referencia las cantidades o 
     * cambiar el color de la linea al momento de pedir
     * @author Yamel Senih 23/05/2012, 02:48:44
     * @return
     * @return ArrayList<DisplayProductItem>
     */
    private ArrayList<DisplayProductItem> getCurrentProducts(){
    	ArrayList<DisplayProductItem> currentProducts = new ArrayList<DisplayProductItem>();
    	for (DisplayOrderItem line : data) {
			currentProducts.add(new DisplayProductItem(
					line.getM_Product_ID(), 
					line.getRecord_ID(),
					line.getXX_MB_CustomerInventoryLine_ID(), 
					0, 
					line.getQtyOrdered(), 
					line.getQtyOnStock(), 
					line.getQtyOnRack(), 
					null));
			//Log.i("nada", "line.getQtyOrdered " + line.getQtyOrdered() + " line.getProductPrice " + line.getProductPrice());
		}
    	return currentProducts;
    }
    
    /**
     * Carfga el Listado de Productos
     * @author Yamel Senih 14/05/2012, 03:29:50
     * @param product
     * @return void
     */
    private void loadProductList(ArrayList<DisplayProductItem> product){
    	Bundle bundle = new Bundle();
    	bundle.putParcelableArrayList("CurrentProducts", product);
    	bundle.putString("ListType", "SO");
    	//	
    	Intent intent = new Intent(this, MV_ProductList.class);
    	intent.putExtras(bundle);
    	//	
		startActivityForResult(intent, 0);
    }
    
    /**
     * Verifica si el pedido tiene lineas para 
     * preguntar si se puede cargar el pedido sugerido
     * @author Yamel Senih 30/08/2012, 03:07:25
     * @return void
     */
    private void loadProductList(){
    	if(!m_Order.isExistsLines()){
    		final int m_XX_MB_CustomerInventory_ID = Env.getContextAsInt(getCtx(), "#XX_MB_CustomerInventory_ID");
    		Builder ask = Msg.confirmMsg(this, getResources().getString(R.string.msg_AskLoadQtySuggested));
    		String msg_Yes = this.getResources().getString(R.string.msg_Yes);
    		String msg_No = this.getResources().getString(R.string.msg_No);
    		
    		//	Pedido Sugerido
    		ask.setPositiveButton(msg_Yes, new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int which) {
    				ArrayList<DisplayProductItem> product = MBCustomerInventoryLine
            				.getCurrentProductsSuggested(getCtx(), m_XX_MB_CustomerInventory_ID, m_C_BPartner_Location_ID);
    				loadProductList(product);
    				suggested = true;
    			}
    		});
    		//	No Sugerido
    		ask.setNegativeButton(msg_No, new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int which) {
    				ArrayList<DisplayProductItem> product = getCurrentProducts();
    	    		loadProductList(product);
    	    		suggested = false;
    			}
    		});
    	    	
    		ask.show();
    	} else {
    		ArrayList<DisplayProductItem> product = getCurrentProducts();
    		loadProductList(product);
    		suggested = false;
    	}
    }
    
    /**
     * Carga la Actividad de Inventario para mostrar el mismo
     * @author Yamel Senih 16/07/2012, 03:18:33
     * @return void
     */
    private void loadInventory(){
    	
    	int m_XX_MB_CustomerInventory_ID = Env.getContextAsInt(getCtx(), "#XX_MB_CustomerInventory_ID");
    	
    	if(m_XX_MB_CustomerInventory_ID == 0)
    		return;
    	
    	MBCustomerInventory m_cin = new MBCustomerInventory(getCtx(), m_XX_MB_CustomerInventory_ID);
    	
    	/*ImpParamActivity param = new ImpParamActivity(m_cin.getID(), m_cin.getTableInfo().getAD_Table_ID(), 
				true, "s");*/
		
    	int id = Env.getContextAsInt(this, "#C_BPartner_ID");
    	MBPartner m_bpartner = new MBPartner(this, id);
    	/*param.setName(getResources().getString(R.string.XX_MB_CustomerInventory_ID) + 
		" \"" + m_bpartner.get_Value("Name") + "\"");*/

    	DisplayMenuItem item = DisplayMenuItem.createFromMenu(param);

    	item.setName(getResources().getString(R.string.XX_MB_CustomerInventory_ID) + 
    			" \"" + m_bpartner.get_Value("Name") + "\"");
    	
    	item.setTableName("XX_MB_CustomerInventory");
    	
    	item.setAD_Table_ID(0);
    	
    	item.setWhereClause("XX_MB_CustomerInventory.C_Order_ID = " + 
    			Env.getTabRecord_ID(this, "C_Order"));
    	//Msg.toastMsg(getCtx(), item.getWhereClause());
		//	Set Record ID
		Env.setTabRecord_ID(this, m_cin.getTableInfo().getTableName(), m_cin.getID());
		
		//	Param
		Bundle bundle = new Bundle();
		bundle.putParcelable("Param", item);
		//	Load Intent
		Intent intent = new Intent(getApplicationContext(), MT_CustomerInventory.class);
		intent.putExtras(bundle);
		//	Start
		startActivity(intent);
		
    }
    
    /**
     * Carga los Registros Actuales
     * @author Yamel Senih 14/05/2012, 03:12:32
     * @return
     * @return boolean
     */
    private boolean loadCurrentRecords(){
		DB con = new DB(this);
		con.openDB(DB.READ_ONLY);
		BigDecimal temp = Env.ZERO;
		BigDecimal rate = Env.ZERO;
		totalLines = Env.ZERO;
		grandTotal = Env.ZERO;
		String sql = new String("SELECT " +
				"lo.C_OrderLine_ID, " +
				"lo.XX_MB_CustomerInventoryLine_ID, " +
				"lo.M_Product_ID, " +
				"lo.Value, " +
				"lo.Name, " +
				"lo.QtyOrdered, " +
				"lo.XX_QtyInStock, " +
				"lo.XX_QtyOnRack, " +
				"lo.PriceActual, " +
				"lo.LineNetAmt, " +
				"lo.Rate, " +
				"lo.XX_MB_CustomerInventory_ID " +
				"FROM XX_OrderInventory lo " +
				"WHERE lo.C_Order_ID = " + 
				Env.getTabRecord_ID(this, "C_Order") + " " + 
				"ORDER BY lo.Name");
		
		Cursor rs = con.querySQL(sql, null);
		
		data = new LinkedList<DisplayOrderItem>();
		int m_XX_MB_CustomerInventory_ID = 0;
		if(rs.moveToFirst()){
			do {
				data.add(new DisplayOrderItem(
						rs.getInt(0),		//	Record ID
						rs.getInt(1),		//	Customer Inventory Line ID
						rs.getInt(2), 		//	Product
						rs.getString(3),	//	Name
						rs.getString(4), 	//	Description
						rs.getString(5), 	//	Quantity Ordered
						rs.getString(6),	//	Quantity On Stock
						rs.getString(7),	//	Quantity On Rack
						rs.getString(8), 	//	Product Price
						rs.getString(9)));	//	Line Net Amount
				//	Line Net Amount
				temp = new BigDecimal(rs.getString(9));
				//	Rate Tax
				rate = new BigDecimal(rs.getString(10));
				//Log.i("", "" + rate);
				totalLines = totalLines.add(temp);
				grandTotal = totalLines.add(temp.multiply(rate));
				if(rs.getInt(11) != 0
						&& m_XX_MB_CustomerInventory_ID == 0){
					m_XX_MB_CustomerInventory_ID = rs.getInt(11);
				}
				
			} while(rs.moveToNext());
		}
		//	Set Customer Inventory ID
		Env.setContext(getCtx(), "#XX_MB_CustomerInventory_ID", m_XX_MB_CustomerInventory_ID);
		//	Enable Button Inventory
		butt_Inventory.setEnabled((m_XX_MB_CustomerInventory_ID != 0));
		
		AdapterOrderLine mi_adapter = new AdapterOrderLine(this, data);
		mi_adapter.setDropDownViewResource(R.layout.il_menu);
		orderLineList.setAdapter(mi_adapter);
		con.closeDB(rs);
		//	Update Header
		if(updateHeap){
        	m_Order.set_Value("TotalLines", totalLines);
        	m_Order.set_Value("GrandTotal", grandTotal);
        	m_Order.save();
        }
		
		tv_TotalLines.setText(String.valueOf(totalLines));
    	tv_GandTotal.setText(String.valueOf(grandTotal));
		
    	return true;
    }
    
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
        String DocAction = Env.getContext(this, "#DocAction");
        if(DocAction == null){
        	DocAction = Cust_ButtonDocAction.STATUS_Drafted;
        }
        
        if(DocAction.equals(Cust_ButtonDocAction.STATUS_Completed)
        		|| DocAction.equals(Cust_ButtonDocAction.STATUS_Voided)){
        	butt_Add.setEnabled(false);
        	//butt_Inventory.setEnabled(false);
        } else {
        	butt_Add.setEnabled(param.isReadWrite() && param.isInsertRecord());
        	//butt_Inventory.setEnabled(Env.getContextAsInt(getCtx(), "#XX_MB_CustomerInventory_ID") != 0);
        }
        
        //	Verfifica si no hay instancia o si es diferente
        int id = Env.getTabRecord_ID(this, "C_Order");
        if(m_Order == null 
        		|| m_Order.getID() != id){
			m_Order = new MBOrder(this, id);
			m_C_BPartner_Location_ID = m_Order.get_ValueAsInt("C_BPartner_Location_ID");
		}
        
        loadCurrentRecords();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }

	/* (non-Javadoc)
	 * @see org.appd.view.MVActivity#getCustomMP()
	 */
	@Override
	protected MP getCustomMP() {
		// TODO Auto-generated method stub
		return new MBOrderLine(getCtx(), 0);
	}

	/* (non-Javadoc)
	 * @see org.appd.view.MVActivity#getCustomMP(int)
	 */
	@Override
	protected MP getCustomMP(int id) {
		// TODO Auto-generated method stub
		return new MBOrderLine(getCtx(), id);
	}

	/* (non-Javadoc)
	 * @see org.appd.view.MVActivity#getCustomMP(android.database.Cursor)
	 */
	@Override
	protected MP getCustomMP(Cursor rs) {
		// TODO Auto-generated method stub
		return new MBOrderLine(getCtx(), rs);
	}
	
	/* (non-Javadoc)
	 * @see org.appd.view.MVActivity#getActivityName()
	 */
	@Override
	protected String getActivityName() {
		return "C_OrderLine";
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
	    if (resultCode == RESULT_OK) {
	    	if(data != null){
	    		//	Columna de C_OrderLine
	    		int m_C_Order_ID = Env.getTabRecord_ID(this, "C_Order");
	    		ArrayList<DisplayProductItem> products = data.getParcelableArrayListExtra("Products");
	    		loadConnection();
	    		MBOrderLine.createLinesAndInventoryFromProducts(this, con, products, m_C_Order_ID, m_Order, 
	    				Env.getContextAsInt(this, "#XX_MB_CustomerInventory_ID"), suggested);
				closeConnection();
	    	}
	    }
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.m_customer_order_line, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		//	Get Position
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		final DisplayOrderItem doi = (DisplayOrderItem) orderLineList.getAdapter().getItem(info.position);
	    switch (item.getItemId()) {
	        case R.id.opt_Delete:
	        	String DocAction = Env.getContext(this, "#DocAction");
	            if(DocAction == null){
	            	DocAction = Cust_ButtonDocAction.STATUS_Drafted;
	            }
	            
	            if(param.isReadWrite()){
	            	if(DocAction.equals(Cust_ButtonDocAction.STATUS_Completed)){
		            	Msg.alertMsg(this, getResources().getString(R.string.msg_ProcessError), 
		            			getResources().getString(R.string.msg_STATUS_Completed));
		            } else if(DocAction.equals(Cust_ButtonDocAction.STATUS_Voided)) {
		            	Msg.alertMsg(this, getResources().getString(R.string.msg_ProcessError), 
		            			getResources().getString(R.string.msg_STATUS_Voided));
		            } else {
		            	Builder ask = Msg.confirmMsg(this, getResources().getString(R.string.msg_AskDelete) + 
			        			"\n\"" + doi.getDescription() + "\"");
			        	String msg_Acept = this.getResources().getString(R.string.msg_Yes);
			        	ask.setPositiveButton(msg_Acept, new DialogInterface.OnClickListener() {
			    			public void onClick(DialogInterface dialog, int which) {
			    				if(doi.getRecord_ID() != 0){
			    					MBOrderLine m_OrderLine = new MBOrderLine(getCtx(), doi.getRecord_ID());
				    	        	m_OrderLine.delete();
			    				} else if(doi.getXX_MB_CustomerInventoryLine_ID() != 0){
			    					MBCustomerInventoryLine m_CInventory = new MBCustomerInventoryLine(getCtx(), doi.getXX_MB_CustomerInventoryLine_ID());
			    					m_CInventory.delete();
			    				}
			    				
			    				updateHeap = true;
			    	        	loadCurrentRecords();
			    			}
			    		});
			        	ask.show();
		            }	
	            } else {
	        		Msg.alertMsg(this, getResources().getString(R.string.msg_ProcessError), 
	            			getResources().getString(R.string.msg_ReadOnly));
	            }
	            return true;
	        case R.id.opt_ProductInfo:
	        	
	        	return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}

}
