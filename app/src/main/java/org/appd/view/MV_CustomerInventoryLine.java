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

import java.util.ArrayList;
import java.util.LinkedList;

import org.appd.base.DB;
import org.appd.base.Env;
import org.appd.base.R;
import org.appd.model.MBCustomerInventoryLine;
import org.appd.model.MP;
import org.appd.util.AdapterInventoryLine;
import org.appd.util.DisplayMenuItem;
import org.appd.util.DisplayOrderItem;
import org.appd.util.DisplayProductItem;
import org.appd.util.Msg;
import org.appd.util.contribution.ActionItem;
import org.appd.util.contribution.QuickAction;

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

/**
 * @author Yamel Senih
 *
 */
public class MV_CustomerInventoryLine extends MVActivity{
	
	private LinkedList<DisplayOrderItem> data = new LinkedList<DisplayOrderItem>();
	private ListView inventoryLineList;
	private Button butt_Add;
	private QuickAction mQAct = null;
	private int m_C_BPartner_Location_ID;
	//private BigDecimal totalLines;
	//private BigDecimal grandTotal;
	//private boolean updateHeap = false;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	super.setContentView(R.layout.lv_customer_inventory_line);
    	
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
    	inventoryLineList = (ListView)findViewById(R.id.ls_InventoryLine);
    	
    	/*//load();
    	*/
    	inventoryLineList.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> padre, View v, int position,
					long id) {
				DisplayOrderItem product = (DisplayOrderItem) inventoryLineList.getItemAtPosition(position);
				loadLastSO(product.getM_Product_ID(), v);
				//	Star Activity
			}
		});
    	
    	registerForContextMenu(inventoryLineList);
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
				"io.DocumentNo, " +
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
					0,
					line.getXX_MB_CustomerInventoryLine_ID(),
					0,
					null, 
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
     * @return void
     */
    private void loadProductList(){
    	ArrayList<DisplayProductItem> product = getCurrentProducts();
    	Bundle bundle = new Bundle();
    	bundle.putParcelableArrayList("CurrentProducts", product);
    	bundle.putString("ListType", "CI");
    	//	
    	Intent intent = new Intent(this, MV_ProductList.class);
    	intent.putExtras(bundle);
    	//	
		startActivityForResult(intent, 0);
    }
    
    /**
     * Carga los Registros Actuales
     * @author Yamel Senih 14/05/2012, 03:12:32
     * @return
     * @return boolean
     */
    private boolean loadCurrentRecords(){
    	//	Permission
    	butt_Add.setEnabled(param.isReadWrite());
    	if(m_C_BPartner_Location_ID == 0){
    		m_C_BPartner_Location_ID = Env.getContextAsInt(getCtx(), "#C_BPartner_Location_ID");
    	}
		DB con = new DB(this);
		con.openDB(DB.READ_ONLY);
		
		String sql = new String("SELECT " +
				"lo.XX_MB_CustomerInventoryLine_ID, " +
				"lo.M_Product_ID, " +
				"p.Value, " +
				"p.Name, " +
				"lo.XX_QtyInStock, " +
				"lo.XX_QtyOnRack " +
				"FROM XX_MB_CustomerInventoryLine lo " +
				"INNER JOIN M_Product p ON(p.M_Product_ID = lo.M_Product_ID) " +
				"WHERE lo.XX_MB_CustomerInventory_ID = " + 
				Env.getTabRecord_ID(this, "XX_MB_CustomerInventory") + " " + 
				"ORDER BY p.Name");
		Cursor rs = con.querySQL(sql, null);
		data = new LinkedList<DisplayOrderItem>();
		if(rs.moveToFirst()){
			do {
				data.add(new DisplayOrderItem(
						0,
						rs.getInt(0),
						rs.getInt(1), 
						rs.getString(2), 
						rs.getString(3),
						null,
						rs.getString(4), 
						rs.getString(5),
						null, 
						null));
				//Log.i("rs.getString(4) ", " " + rs.getString(4));
				//Log.i("rs.getString(5) ", " " + rs.getString(5));
			} while(rs.moveToNext());
		}
		AdapterInventoryLine mi_adapter = new AdapterInventoryLine(this, data);
		mi_adapter.setDropDownViewResource(R.layout.il_menu);
		inventoryLineList.setAdapter(mi_adapter);
		con.closeDB(rs);
    	return true;
    }
    
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
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
	public MP getCustomMP() {
		return new MBCustomerInventoryLine(getCtx(), 0);
	}

	@Override
	public MP getCustomMP(int id) {
		return new MBCustomerInventoryLine(getCtx(), id);
	}

	@Override
	public MP getCustomMP(Cursor rs) {
		return new MBCustomerInventoryLine(getCtx(), rs);
	}
	
	/* (non-Javadoc)
	 * @see org.appd.view.MVActivity#getActivityName()
	 */
	@Override
	protected String getActivityName() {
		return "XX_MB_CustomerInventoryLine";
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
	    if (resultCode == RESULT_OK) {
	    	if(data != null){
	    		int m_XX_MB_CustomerInventory_ID = Env.getTabRecord_ID(this, "XX_MB_CustomerInventory");
	    		ArrayList<DisplayProductItem> products = data.getParcelableArrayListExtra("Products");
	    		
	    		String msg = MBCustomerInventoryLine.createLinesFromProduts(getCtx(), null,  
	    				products, 
	    				m_XX_MB_CustomerInventory_ID);
	    		if(msg != null){
	    			Msg.alertMsg(getCtx(), getResources().getString(R.string.msg_Error), msg);
	    		}
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
		final DisplayOrderItem doi = (DisplayOrderItem) inventoryLineList.getAdapter().getItem(info.position);
	    switch (item.getItemId()) {
	        case R.id.opt_Delete:
	        	if(param.isReadWrite()){
	        		Builder ask = Msg.confirmMsg(this, getResources().getString(R.string.msg_AskDelete) + 
		        			"\n\"" + doi.getDescription() + "\"");
		        	String msg_Acept = this.getResources().getString(R.string.msg_Yes);
		        	ask.setPositiveButton(msg_Acept, new DialogInterface.OnClickListener() {
		    			public void onClick(DialogInterface dialog, int which) {
		    				MBCustomerInventoryLine m_InventoryLine = new MBCustomerInventoryLine(getCtx(), doi.getXX_MB_CustomerInventoryLine_ID());
		    	        	m_InventoryLine.delete();
		    	        	loadCurrentRecords();
		    			}
		    		});
		        	ask.show();	
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
