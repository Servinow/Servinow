package com.servinow.android;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.servinow.android.Util.ImageAsyncHelper;
import com.servinow.android.Util.ImageAsyncHelper.ImageAsyncHelperCallBack;
import com.servinow.android.dao.CategoryCache;
import com.servinow.android.dao.PedidoCache;
import com.servinow.android.dao.RestaurantCache;
import com.servinow.android.domain.Categoria;
import com.servinow.android.domain.Estado;
import com.servinow.android.domain.LineaPedido;
import com.servinow.android.domain.OrdersState;
import com.servinow.android.domain.Pedido;
import com.servinow.android.domain.Producto;
import com.servinow.android.domain.Restaurant;
import com.servinow.android.widget.CheckStateArrayAdapter;

public class CheckOrderStateActivity extends SherlockActivity {

	private ListView lv;
	public Boolean flagTimer=false;
	public int countOrders=0;
	public int countChanges=0;
	
	private int restaurantID;
	private int placeID;
	
	private List<Pedido> listaPedidos = null;

	private ArrayList<OrdersState> orders = new ArrayList<OrdersState>();
	public ArrayList<OrdersState> ordersToDisplay = new ArrayList<OrdersState>();
	private CheckStateArrayAdapter adapter = null;
	
	protected Handler taskHandler = new Handler();
	protected Boolean isComplete = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Bundle extras = getIntent().getExtras();
		if(extras != null)
		{
			restaurantID = extras.getInt(Param.RESTAURANT.toString());
			Log.d("restaurantID: ",""+restaurantID+"------");
			placeID = extras.getInt(Param.PLACE.toString());
			Restaurant restaurant = new RestaurantCache(this).
					getRestaurantFromCache(restaurantID);
			List<Categoria> listaCategorias = new CategoryCache(this).
					getCategories(restaurant);
			
			// TODO cambiar a List en el adapter

			setTitle(restaurant.getName());
    	}
        
        setContentView(R.layout.activity_check_state); 
        setTitle("Estado del pedido");
        
        createListView(this);
        
    //    setTimer(1000);
    }
    @Override
    protected void onDestroy() {
      super.onDestroy();
      adapter.stopScheduler();
    }

    public void createListView(Context ctx){
    	
    	populateOrders(); // populateOrders de la BD, populateOrdersB los crea
    	
    	prepareToDisplay();
    	
    	lv =  (ListView) findViewById(R.id.listViewCheckState);
    	adapter = new CheckStateArrayAdapter(this, ordersToDisplay);
    	lv.setAdapter(adapter);
    //	lv.setAdapter(new CheckStateArrayAdapter(this, ordersToDisplay));
    	
    }
    
    public void populateOrders(){
    	//TODO acceder a la BD para poblar la lista de �rdenes
    	
   
    	PedidoCache pd = new PedidoCache(this);
   // 	listaPedidos = pd.getAllPedidosConfirmados();
    	listaPedidos = pd.getAllPedidosConfirmed(placeID, restaurantID);
    }
    
    public void populateOrdersB(){
    	Pedido pd = new Pedido();
    	
    	pd.setId(1);
    	ArrayList<LineaPedido> arraylinea = new ArrayList<LineaPedido>();
    	LineaPedido lp = new LineaPedido();
    	lp.setCantidad(8);
    	lp.setEstado(Estado.EN_COLA);
    	Producto pr = new Producto();
    	lp.setProducto(pr);
    	arraylinea.add(lp);
    	pd.setLineas(arraylinea);
    	listaPedidos = new ArrayList<Pedido>();
    	listaPedidos.add(pd);
    	
    }
    
 
    
    public void prepareToDisplay(){
    	
    	Boolean pagado=false;
    	
    	for(int i=listaPedidos.size()-1; i>=0; i--){
    		if(!pagado){
    			if(listaPedidos.get(i).isPagado()){
    				OrdersState ordpd= new OrdersState();
    				ordpd.round=listaPedidos.get(i).getId();
    				ordpd.pagado=true;
    				ordersToDisplay.add(ordpd);
    				pagado=true;
    			}
    		}
    		OrdersState ord= new OrdersState();
    		ord.round=listaPedidos.get(i).getId();
    		ord.roundmark=true;
    		ordersToDisplay.add(ord);
    		countOrders++;
    		Iterator<LineaPedido> itr = listaPedidos.get(i).getLineas().iterator();
    		while (itr.hasNext()) {
    			LineaPedido lp = itr.next();
    			for(int j=0; j<lp.getCantidad(); j++){
    				OrdersState ordp = new OrdersState();
    				ordp.roundmark=false;
    				ordp.pagado=false;
    				ordp.pedidoId=listaPedidos.get(i).getId();
    				ordp.productoId=lp.getProducto().getId();
    				ordp.name = lp.getProducto().getNombre();
    				ordp.state = lp.getEstado();
    				ordp.lineaPedidoId=lp.getId();
    				ordp.cantidad=lp.getCantidad();
    				ordp.imageName = lp.getProducto().getImageName();
    				ordp.image = null;
    				ordp.lp = lp;
    				ordp.mesa_id = listaPedidos.get(i).getPlace().getOnlineID();
    				ordp.restaurantID = restaurantID;
    				
    				ordersToDisplay.add(ordp);
    				countOrders++;
    			}
    		}
    		countChanges=countOrders*3;
    	}
    }
    
    protected void setTimer( long time )
    {
        final long elapse = 1000+ (int)(Math.random()*2000);
        Runnable t = new Runnable() {
            public void run()
            {
                runNextTask();
                	if(!flagTimer)
                		taskHandler.postDelayed( this, elapse );
                	
                
            }
        };
    	taskHandler.postDelayed( t, elapse );
    }
     
    protected void runNextTask()
    {
        // run my task.
        // determine isComplete.
    	int nOrder = (int)(Math.random()*countOrders);
    	while(ordersToDisplay.get(nOrder).roundmark==true)
    		nOrder = (int)(Math.random()*countOrders);
    	
    	/*if(ordersToDisplay.get(nOrder).state == Estado.EN_COLA){
    		ordersToDisplay.get(nOrder).state=Estado.PREPARANDO;
    		countChanges--;
    	}else if(ordersToDisplay.get(nOrder).state == Estado.PREPARANDO){
    		ordersToDisplay.get(nOrder).state=Estado.LISTO;
    		countChanges--;
    	}else if(ordersToDisplay.get(nOrder).state == Estado.LISTO){
    		ordersToDisplay.get(nOrder).state=Estado.SERVIDO;
    		countChanges--;
    	}*/
    	adapter.notifyDataSetChanged();
    	if(countChanges<=0)
    		flagTimer=true;
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			Intent i = new Intent(CheckOrderStateActivity.this, CategoriasActivity.class);
			Bundle b = new Bundle();
			b.putInt(Param.RESTAURANT.toString(), restaurantID);
			b.putInt(Param.PLACE.toString(), placeID);
			i.putExtras(b);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		 MenuInflater inflater = getSupportMenuInflater();
	        inflater.inflate(R.menu.activity_checkstate, menu);
	        return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
        case R.id.checkstate_button_ticket:
			Intent i = new Intent(CheckOrderStateActivity.this, TicketActivity.class);
			Bundle b = new Bundle();
			b.putInt(Param.RESTAURANT.toString(), restaurantID);
			Log.d("restaurantID: ",""+restaurantID+"---2---");
			b.putInt(Param.PLACE.toString(), placeID);
			i.putExtras(b);

			startActivity(i);
         default:
        	 return true;
    }
	}
    
}
