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
import org.appd.model.MBRMA;
import org.appd.model.MBRMALine;
import org.appd.model.MP;
import org.appd.util.AdapterRMALine;
import org.appd.util.DisplayMenuItem;
import org.appd.util.DisplayProductItem;
import org.appd.util.DisplayRMAItem;
import org.appd.util.Msg;
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
public class MV_CustomerRMALine extends MVActivity {
	
	private LinkedList<DisplayRMAItem> data = new LinkedList<DisplayRMAItem>();
	private ListView rmaLineList;
	private TextView tv_Amt;
	private Button butt_Add;
	private BigDecimal m_Amt;
	private boolean updateHeap = false;
	private MBRMA m_Rma = null;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	super.setContentView(R.layout.lv_customer_rma_line);
    	
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
    	
    	tv_Amt = (TextView)findViewById(R.id.tv_Amt);
    	
    	rmaLineList = (ListView)findViewById(R.id.ls_RMALine);
    	
    	//load();
    	
    	rmaLineList.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> padre, View v, int position,
					long id) {
				
				//	Star Activity
			}
		});
    	
    	registerForContextMenu(rmaLineList);
    	
    }
    
	/**
	 * Carga los productos actuales y sus cantidades para 
	 * tomerse como referencia al momento de cargar el 
	 * listado de seleccion
	 * @author Yamel Senih 16/08/2012, 16:33:01
	 * @return
	 * @return ArrayList<DisplayProductItem>
	 */
    private ArrayList<DisplayProductItem> getCurrentProducts(){
    	ArrayList<DisplayProductItem> currentProducts = new ArrayList<DisplayProductItem>();
    	for (DisplayRMAItem line : data) {
			currentProducts.add(new DisplayProductItem(
					line.getM_Product_ID(), 
					line.getRecord_ID(),
					line.getQtyReturn()));
			//Log.i("nada", "line.getQtyOrdered " + line.getQtyOrdered() + " line.getProductPrice " + line.getProductPrice());
		}
    	return currentProducts;
    }
    
    /**
     * Carga los productos en un listado de seleccion
     * @author Yamel Senih 16/08/2012, 16:31:59
     * @return void
     */
    private void loadProductList(){
    	ArrayList<DisplayProductItem> product = getCurrentProducts();
    	Bundle bundle = new Bundle();
    	bundle.putParcelableArrayList("CurrentProducts", product);
    	bundle.putString("ListType", "RM");
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
		DB con = new DB(this);
		con.openDB(DB.READ_ONLY);
		BigDecimal temp = Env.ZERO;
		m_Amt = Env.ZERO;
		String sql = new String("SELECT " +
				"rl.M_RMALine_ID, " +
				"pr.M_Product_ID, " +
				"pr.Value, " +
				"pr.Name, " +
				"rl.Qty, " +
				"rl.Amt, " +
				"rl.LineNetAmt " +
				"FROM M_RMALine rl " +
				"INNER JOIN M_InOutLine iol ON(iol.M_InOutLine_ID = rl.M_InOutLine_ID) " +
				"INNER JOIN M_Product pr ON(pr.M_Product_ID = iol.M_Product_ID) " +
				"WHERE rl.M_RMA_ID = " + 
				Env.getTabRecord_ID(this, "M_RMA") + " " + 
				"ORDER BY pr.Name");
		
		Cursor rs = con.querySQL(sql, null);
		
		data = new LinkedList<DisplayRMAItem>();
		if(rs.moveToFirst()){
			do {
				data.add(new DisplayRMAItem(
						rs.getInt(0),		//	Record ID
						rs.getInt(1), 		//	Product
						rs.getString(2),	//	Name
						rs.getString(3), 	//	Description
						rs.getString(4), 	//	Quantity Return
						rs.getString(5), 	//	Amt
						rs.getString(6)));	//	Line Net Amount
				//	Line Net Amount
				temp = new BigDecimal(rs.getString(6));
				//Log.i("", "" + rate);
				m_Amt = m_Amt.add(temp);
				
			} while(rs.moveToNext());
		}
		
		AdapterRMALine mi_adapter = new AdapterRMALine(this, data);
		mi_adapter.setDropDownViewResource(R.layout.il_menu);
		rmaLineList.setAdapter(mi_adapter);
		con.closeDB(rs);
		//	Update Header
		if(updateHeap){
        	m_Rma.set_Value("Amt", m_Amt);
        	m_Rma.save();
        }
		
		tv_Amt.setText(String.valueOf(m_Amt));
		
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
        } else {
        	butt_Add.setEnabled(param.isReadWrite());
        }
        
        //	Verfifica si no hay instancia o si es diferente
        int id = Env.getTabRecord_ID(this, "M_RMA");
        if(m_Rma == null 
        		|| m_Rma.getID() != id){
        	m_Rma = new MBRMA(this, id);
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
		return new MBRMALine(getCtx(), 0);
	}

	/* (non-Javadoc)
	 * @see org.appd.view.MVActivity#getCustomMP(int)
	 */
	@Override
	protected MP getCustomMP(int id) {
		// TODO Auto-generated method stub
		return new MBRMALine(getCtx(), id);
	}

	/* (non-Javadoc)
	 * @see org.appd.view.MVActivity#getCustomMP(android.database.Cursor)
	 */
	@Override
	protected MP getCustomMP(Cursor rs) {
		// TODO Auto-generated method stub
		return new MBRMALine(getCtx(), rs);
	}
	
	/* (non-Javadoc)
	 * @see org.appd.view.MVActivity#getActivityName()
	 */
	@Override
	protected String getActivityName() {
		return "M_RMALine";
	}
	
	/*@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
	    if (resultCode == RESULT_OK) {
	    	if(data != null){
	    		
	    		//	Columna de M_RMALine
	    		
	    		int m_M_RMA_ID = Env.getTabRecord_ID(this, "M_RMA");
	    		
	    		MBRMALine m_RMALine = new MBRMALine(this, 0);
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
	    		
	    		ArrayList<DisplayProductItem> products = data.getParcelableArrayListExtra("Products");
	    		loadConnection();
	    		try {
	    			for(DisplayProductItem item : products){
		    			if((item.getQtyReturn() != null 
		    					&& item.getQtyReturn().length() > 0
		    					&& (qtyReturn = new BigDecimal(item.getQtyReturn())).signum() != 0)){
		    				
		    				//	Establece los valores obtenidos de la actividad de seleccion de productos
		    				if(item.getQtyReturn() != null 
			    					&& item.getQtyReturn().length() > 0){
		    					qtyReturn = new BigDecimal(item.getQtyReturn());
		    				} else
		    					qtyReturn = Env.ZERO;
		    				
		    				if(item.getQtyReturnOld() != null 
			    					&& item.getQtyReturnOld().length() > 0){
		    					qtyReturnOld = new BigDecimal(item.getQtyReturnOld());
		    				} else
		    					qtyReturnOld = Env.ZERO;
		    				
		    				
		    				//	Se Calcula y Guarda la Linea de la 
		    				//	Autorizacion de Devolucion
		    				
		    				if(!qtyReturn.equals(Env.ZERO)
		    						&& !qtyReturn.equals(qtyReturnOld)){
		    					//	Establece el Precio
		    					if(item.getPrice() != null 
				    					&& item.getPrice().length() > 0){
		    						amt = new BigDecimal(item.getPrice());
			    				} else
			    					amt = Env.ZERO;
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
			    				
			    				updateHeap = true;
			    				
		    				}
		    				
		    			} else{
		    				//	Se elimina la linea de la Autorizacion
		    				
		    				if(item.getForeign_ID() > 0){
		    					//	Clear Order
			    				m_RMALine.clear();
			    				m_RMALine.setIDUpdate(item.getForeign_ID());
			    				m_RMALine.deleteEx();
			    					
			    				updateHeap = true;
		    				}
		    			}
		    		}
	    			
	    		} catch (Exception e) {
					Log.e("Exception", "Exception", e);
					Msg.alertMsg(this, getResources().getString(R.string.msg_Error), e.getMessage());
				} finally {
					closeConnection();
				}
	    	}
	    }
	}*/
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
	    if (resultCode == RESULT_OK) {
	    	if(data != null){
	    		/**
	    		 * Columna de M_RMALine
	    		 */
	    		int m_M_RMA_ID = Env.getTabRecord_ID(this, "M_RMA");
	    		
	    		ArrayList<DisplayProductItem> products = data.getParcelableArrayListExtra("Products");
	    		String msg = MBRMALine.createLinesFromProducts(getCtx(), null, products, m_M_RMA_ID);
	    		if(msg !=null){
	    			Msg.alertMsg(this, getResources().getString(R.string.msg_Error), msg);
	    		}
	    		
	    		//updateHeap = true;
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
		final DisplayRMAItem dri = (DisplayRMAItem) rmaLineList.getAdapter().getItem(info.position);
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
			        			"\n\"" + dri.getDescription() + "\"");
			        	String msg_Acept = this.getResources().getString(R.string.msg_Yes);
			        	ask.setPositiveButton(msg_Acept, new DialogInterface.OnClickListener() {
			    			public void onClick(DialogInterface dialog, int which) {
			    				if(dri.getRecord_ID() != 0){
			    					MBRMALine m_RmaLine = new MBRMALine(getCtx(), dri.getRecord_ID());
				    	        	m_RmaLine.delete();
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
