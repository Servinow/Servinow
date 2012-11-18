package com.servinow.android;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.MenuItem;
import com.servinow.android.R;
import com.servinow.android.domain.LineaPedido;
import com.servinow.android.domain.SelectedItem;
import com.servinow.android.widget.ListaPedidoAdapter;

import com.servinow.android.dao.LineaPedidoCache;
import com.servinow.android.dao.PedidoCache;
import com.servinow.android.domain.Pedido;

import com.servinow.android.pedidosSystem.PedidosHandler;
import com.servinow.android.picker.NumberPicker;

public class ListaPedidoActivity extends SherlockListActivity {

	private ListaPedidoAdapter listaPedidoAdapter;
	private Pedido pedido;
	private int restaurantID;
	private int placeID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_pedido);
		
		// Leer los parámetros recibidos: RESTAURANT y PLACE
		Bundle extras = getIntent().getExtras();
		if(extras != null)
		{
			this.restaurantID = extras.getInt(Param.RESTAURANT.toString());
			this.placeID = extras.getInt(Param.PLACE.toString());
		}
		
		// Leer de la base de datos el pedido no confirmado
		
		this.pedido = new PedidoCache( this ).getPedidoNotConfirmed(placeID, restaurantID);
		
		SelectedItem[] selectedItems = new SelectedItem[0];
		if( pedido != null){
		
			int numSelectedItems = pedido.getLineas().size();
		
			selectedItems = new SelectedItem[numSelectedItems];
	
			List<LineaPedido> lineasPedido = new ArrayList<LineaPedido>(pedido.getLineas());
			
			for(int i=0; i < numSelectedItems; i++){
				selectedItems[i] = new SelectedItem(lineasPedido.get(i), this.restaurantID, this.placeID);
			}
		}
		 
		 // Crear los datos para el Adapter a partir de los datos de la base de datos
		 ArrayList<SelectedItem> arrayList = new ArrayList<SelectedItem>();
		 arrayList.addAll(Arrays.asList(selectedItems));
		
		 // Crea el ADAPTER
		this.listaPedidoAdapter = new ListaPedidoAdapter(this, R.layout.lista_pedido_row, arrayList);
		setListAdapter( listaPedidoAdapter );
		
		// Poner evento de ONCLICK al listView
		getListView().setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// IR A LA ACTIVIDAD DetalleProductoActivity
				Intent myIntent = new Intent(ListaPedidoActivity.this, DetalleProductoActivity.class );
	        	Bundle b = new Bundle();
	        	SelectedItem selectedItem = (SelectedItem) parent.getAdapter().getItem(position);
	        	b.putInt(Param.RESTAURANT.toString(), selectedItem.getRestaurantId());
	        	b.putInt(Param.PLACE.toString(), selectedItem.getPlaceId());
	        	//b.putInt(Param.CATEGORIA.toString(), selectedItem.getCategoriaId());
	        	b.putInt(Param.PRODUCTO.toString(), selectedItem.getProductoId());
	        	
	        	myIntent.putExtras(b);
	        	
	        	startActivity(myIntent);
            }
        });
		
		
		// Eventos de Botones
		if(pedido != null){
			((TextView) findViewById(R.id.lista_pedido_precio_total)).setText( Math.round(pedido.getTotal()*100.0)/100.0 + " €");
		}
		else{
			((TextView) findViewById(R.id.lista_pedido_precio_total)).setText("0 €");
		}
		if( this.pedido != null ){
			((Button) findViewById(R.id.lista_pedido_button_edit)).setOnClickListener(editButtonClick);
			((Button) findViewById(R.id.lista_pedido_button_delete_confirm)).setOnClickListener(deleteConfirmButtonClick);
			((Button) findViewById(R.id.lista_pedido_button_change_quantity)).setOnClickListener(changeQuantityButtonClick);
			((Button) findViewById(R.id.lista_pedido_button_delete_cancel)).setOnClickListener(deleteCancelButtonClick);
			
			((Button) findViewById(R.id.lista_pedido_button_cancel)).setOnClickListener(buttonCancelClick);
			((Button) findViewById(R.id.lista_pedido_button_confirm)).setOnClickListener(buttonConfirmClick);
		}
		else{
			((Button) findViewById(R.id.lista_pedido_button_edit)).setEnabled(false);
			
			((Button) findViewById(R.id.lista_pedido_button_cancel)).setEnabled(false);
			((Button) findViewById(R.id.lista_pedido_button_confirm)).setEnabled(false);
		}
		
		getSupportActionBar().setHomeButtonEnabled(true);
	}
	
    @Override
    protected void onRestart() {
        super.onRestart();
        
        // Borrar todos los elementos del adapter
        for(int i=0; i < listaPedidoAdapter.getCount(); i++){
        	listaPedidoAdapter.remove(listaPedidoAdapter.getItem(i));
        }
        
        // Insertar los nuevos elementos en el adapter
        
		this.pedido = new PedidoCache( this ).getPedidoNotConfirmed(placeID, restaurantID);
		
		SelectedItem[] selectedItems = new SelectedItem[0];
		if( pedido != null){
		
			int numSelectedItems = pedido.getLineas().size();
		
			selectedItems = new SelectedItem[numSelectedItems];
	
			List<LineaPedido> lineasPedido = new ArrayList<LineaPedido>(pedido.getLineas());
			
			for(int i=0; i < numSelectedItems; i++){
				selectedItems[i] = new SelectedItem(lineasPedido.get(i), this.restaurantID, this.placeID);
			}
		}
		 
		 // Crear los datos para el Adapter a partir de los datos de la base de datos
		 ArrayList<SelectedItem> arrayList = new ArrayList<SelectedItem>();
		 arrayList.addAll(Arrays.asList(selectedItems));
        
		 // Crea el ADAPTER
		 this.listaPedidoAdapter = new ListaPedidoAdapter(this, R.layout.lista_pedido_row, arrayList);
		 setListAdapter( listaPedidoAdapter );
		 
		 // Modificar el precio total
    	((TextView) findViewById(R.id.lista_pedido_precio_total)).setText(Math.round(pedido.getTotal()*100.0)/100.0 + " €");
        
    }
	
	// Create an anonymous implementation of OnClickListener
	private OnClickListener editButtonClick = new OnClickListener() {
	    public void onClick(View v) {
	    	findViewById(R.id.lista_pedido_bar_delete).setVisibility(View.INVISIBLE);
	    	findViewById(R.id.lista_pedido_bar_delete_2).setVisibility(View.VISIBLE);
	    	findViewById(R.id.lista_pedido_button_delete_confirm).setEnabled(false);
	    	findViewById(R.id.lista_pedido_button_change_quantity).setEnabled(false);
	    	for(int i=0; i < listaPedidoAdapter.getCount(); i++){
        		SelectedItem itemSelected = listaPedidoAdapter.getItem(i);
        		itemSelected.setCheckBoxVisibility(View.VISIBLE);
        		itemSelected.setImageVisibility(View.INVISIBLE);
        	}
        	listaPedidoAdapter.notifyDataSetChanged();
	    }
	};
	
	// Create an anonymous implementation of OnClickListener
	private OnClickListener deleteConfirmButtonClick = new OnClickListener() {
	    public void onClick(View v) {
	    	
	    	SelectedItem[] items = new SelectedItem[listaPedidoAdapter.getCount()];
        	for(int i=0; i < listaPedidoAdapter.getCount(); i++){
        		items[i] = listaPedidoAdapter.getItem(i);
        	}
        	
        	int numberOfItems = listaPedidoAdapter.getCount();
        	int itemsRemoved = 0;
        	List<Integer> lineaPedidoIdList = new ArrayList<Integer>();
        	
        	for(int i=0; i < numberOfItems; i++){
        		if(items[i].isChecked()){
        			listaPedidoAdapter.remove(items[i]);
        			// Insertar los elementos en la lista para borrar después
        			// la lista entera de la base de datos
        			lineaPedidoIdList.add(items[i].getId());
                	itemsRemoved++;
        		}
        	}
        	
        	// BORRAR DE LA BASE DE DATOS
        	new LineaPedidoCache(ListaPedidoActivity.this).deleteMultipleLineaPedidoById(lineaPedidoIdList);
        	
        	if(itemsRemoved > 0){
	        	// Modificar el valor del PrecioTotal
	        	pedido = new PedidoCache( ListaPedidoActivity.this ).
	        			getPedidoNotConfirmed(ListaPedidoActivity.this.placeID, ListaPedidoActivity.this.restaurantID);
	        	((TextView) findViewById(R.id.lista_pedido_precio_total)).setText(Math.round(pedido.getTotal()*100.0)/100.0 + " €");
        	}
        	
        	for(int i=0; i < listaPedidoAdapter.getCount(); i++){
        		SelectedItem itemSelected = listaPedidoAdapter.getItem(i);
        		itemSelected.setCheckBoxVisibility(View.INVISIBLE);
        		itemSelected.setImageVisibility(View.VISIBLE);
        	}
        	// Si ya no hay líneas de pedido en el pedido se desabilitan los botones
        	if(listaPedidoAdapter.getCount() == 0){
        		((Button) findViewById(R.id.lista_pedido_button_edit)).setEnabled(false);
    			
    			((Button) findViewById(R.id.lista_pedido_button_cancel)).setEnabled(false);
    			((Button) findViewById(R.id.lista_pedido_button_confirm)).setEnabled(false);
        	}
        	
        	findViewById(R.id.lista_pedido_bar_delete).setVisibility(View.VISIBLE);
	    	findViewById(R.id.lista_pedido_bar_delete_2).setVisibility(View.INVISIBLE);        	
        	listaPedidoAdapter.notifyDataSetChanged();
	    }
	};
	
	// Create an anonymous implementation of OnClickListener
	private OnClickListener deleteCancelButtonClick = new OnClickListener() {
	    public void onClick(View v) {
	    	findViewById(R.id.lista_pedido_bar_delete).setVisibility(View.VISIBLE);
	    	findViewById(R.id.lista_pedido_bar_delete_2).setVisibility(View.INVISIBLE);
	    	for(int i=0; i < listaPedidoAdapter.getCount(); i++){
        		SelectedItem itemSelected = listaPedidoAdapter.getItem(i);
        		itemSelected.setChecked(false);
        		itemSelected.setCheckBoxVisibility(View.INVISIBLE);
        		itemSelected.setImageVisibility(View.VISIBLE);
        	}
        	listaPedidoAdapter.notifyDataSetChanged();
	    }
	};

	private OnClickListener changeQuantityButtonClick = new OnClickListener() {
	    public void onClick(View v) {
	    	
	        SelectedItem selectedItem = null;
	        for(int i=0; i < ListaPedidoActivity.this.listaPedidoAdapter.getCount(); i++){
	        	if(ListaPedidoActivity.this.listaPedidoAdapter.getItem(i).isChecked()){
	        		selectedItem = ListaPedidoActivity.this.listaPedidoAdapter.getItem(i);
	        		break;
	        	}
	        }
	    	
	        AlertDialog.Builder builder = new AlertDialog.Builder(ListaPedidoActivity.this);
	        // Get the layout inflater
	        LayoutInflater inflater = ListaPedidoActivity.this.getLayoutInflater();

	        // Inflate and set the layout for the dialog
	        // Pass null as the parent view because its going in the dialog layout
	        View view = inflater.inflate(R.layout.picker_activity, null);
	        
	        builder.setView(view)
	        // Add action buttons
	               .setPositiveButton(R.string.lista_pedido_cancel_button_ok, new DialogInterface.OnClickListener() {
	                   @Override
	                   public void onClick(DialogInterface dialog, int id) {
		           	       SelectedItem selectedItem = null;
		        	       for(int i=0; i < ListaPedidoActivity.this.listaPedidoAdapter.getCount(); i++){
		        	    	   if(ListaPedidoActivity.this.listaPedidoAdapter.getItem(i).isChecked()){
		        	    		   selectedItem = ListaPedidoActivity.this.listaPedidoAdapter.getItem(i);
		        	        	   break;
		        	           }
		        	       }
	                	   NumberPicker numberPicker = (NumberPicker) ((AlertDialog) dialog).findViewById(R.id.lista_pedido_picker);
	                  	   int cantidad = numberPicker.getCurrent();
	                	   
	                	   // Modificar en la base de datos
	                	   new LineaPedidoCache(ListaPedidoActivity.this).updateQuantityLineaPedido(selectedItem.getId(), cantidad);
	                	   
	                	   pedido = new PedidoCache( ListaPedidoActivity.this ).
	       	        			getPedidoNotConfirmed(ListaPedidoActivity.this.placeID, ListaPedidoActivity.this.restaurantID);
	       	        		((TextView) findViewById(R.id.lista_pedido_precio_total)).setText(Math.round(pedido.getTotal()*100.0)/100.0 + " €");
	                	   
	                  	   // Modificar en el adapter
	                	   selectedItem.setQuantity(cantidad);
	                	   ListaPedidoActivity.this.listaPedidoAdapter.notifyDataSetChanged();
	                	   
	           	    	findViewById(R.id.lista_pedido_bar_delete).setVisibility(View.VISIBLE);
	        	    	findViewById(R.id.lista_pedido_bar_delete_2).setVisibility(View.INVISIBLE);
	        	    	for(int i=0; i < listaPedidoAdapter.getCount(); i++){
	                		SelectedItem itemSelected = listaPedidoAdapter.getItem(i);
	                		itemSelected.setChecked(false);
	                		itemSelected.setCheckBoxVisibility(View.INVISIBLE);
	                		itemSelected.setImageVisibility(View.VISIBLE);
	                	}
	                	listaPedidoAdapter.notifyDataSetChanged();
	                   }
	               })
	               .setNegativeButton(R.string.lista_pedido_cancel_button_cancel, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                      
	                   }
	               });
	        
	    	NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.lista_pedido_picker);
	    	
	    	numberPicker.setCurrent(selectedItem.getQuantity());
	        
	        // Create the AlertDialog
	    	AlertDialog alert = builder.create();
	    	alert.show();
	    }
	};
	
	View.OnClickListener buttonConfirmClick = new View.OnClickListener() {
	    public void onClick(View v) {
	    	// 1. Instantiate an AlertDialog.Builder with its constructor
	    	AlertDialog.Builder builder = new AlertDialog.Builder(ListaPedidoActivity.this);
	    	
	    	// 2. Chain together various setter methods to set the dialog characteristics
	    	builder.setMessage(R.string.lista_pedido_confirm_dialog_message).setTitle(R.string.lista_pedido_confirm_dialog_title);
	    	
	    	// Add the buttons
	    	builder.setPositiveButton(R.string.lista_pedido_confirm_button_ok, new DialogInterface.OnClickListener() {

	    	     

	    		public void onClick(DialogInterface dialog, int id) {
	    			// Marcar pedido como confirmado
	    			ListaPedidoActivity.this.pedido.setConfirmado(true);
	    			new PedidoCache(ListaPedidoActivity.this).updatePedido(pedido);

	    			// TODO INTENT A LA ACTIVIDAD DE CheckStateActivity.class
	    			// SOLO HAY QUE CAMBIAR MainActivity por CheckStateActivity
	    			Intent myIntent = new Intent(ListaPedidoActivity.this, CheckOrderStateActivity.class);
	    			Bundle b = new Bundle();
	    			b.putInt(Param.RESTAURANT.toString(), restaurantID);
	    			b.putInt(Param.PLACE.toString(), placeID);
	    			myIntent.putExtras(b);
	    			ListaPedidoActivity.this.startActivity(myIntent);
	    		//	startActivity(new Intent(ListaPedidoActivity.this, CheckOrderStateActivity.class));
	    			finish(); //Importante sino no se desapila la activdad.
	    		}
	    	});
	    	
	    	builder.setNegativeButton(R.string.lista_pedido_confirm_button_cancel, new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	               // User cancelled the dialog
	    	           }
	    	       });

	    	// Create the AlertDialog
	    	AlertDialog alert = builder.create();
	    	alert.show();
	    }
	};
	
	View.OnClickListener buttonCancelClick = new View.OnClickListener() {
	    public void onClick(View v) {
	    	// 1. Instantiate an AlertDialog.Builder with its constructor
	    	AlertDialog.Builder builder = new AlertDialog.Builder(ListaPedidoActivity.this);
	    	
	    	// 2. Chain together various setter methods to set the dialog characteristics
	    	builder.setMessage(R.string.lista_pedido_cancel_dialog_message).setTitle(R.string.lista_pedido_cancel_dialog_title);
	    	
	    	// Add the buttons
	    	builder.setPositiveButton(R.string.lista_pedido_cancel_button_ok, new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	               // User clicked OK button
	    	        	   // Borrar todos los datos de la base de datos

	    	        	   new PedidosHandler(ListaPedidoActivity.this).cancelarPedido(ListaPedidoActivity.this.pedido.getId());
	    	        	   
	    	        	   // Ir a la actividad de categorías CategoriasActivity
	    	        	  /* Intent myIntent = new Intent(ListaPedidoActivity.this, CategoriasActivity.class);
	    	       			Bundle b = new Bundle();
	    	       			b.putInt(Param.RESTAURANT.toString(), restaurantID);
	    	       			b.putInt(Param.PLACE.toString(), placeID);
	    	       			myIntent.putExtras(b);
	    	               ListaPedidoActivity.this.startActivity(myIntent);*/
	    	        	   
	    	        	   ListaPedidoActivity.this.finish();
	    	           }
	    	       });
	    	builder.setNegativeButton(R.string.lista_pedido_cancel_button_cancel, new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	               // User cancelled the dialog
	    	           }
	    	       });

	    	// Create the AlertDialog
	    	AlertDialog alert = builder.create();
	    	alert.show();
	    }
	};
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case android.R.id.home:
    		// app icon in action bar clicked; go home
    		Intent i = new Intent(this, CategoriasActivity.class);
    		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		
    		Bundle b = new Bundle();
    		b.putInt(Param.RESTAURANT.toString(), restaurantID);
    		b.putInt(Param.PLACE.toString(), placeID);
    		i.putExtras(b);
    		
    		startActivity(i);
    		return true;
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    }
	
}
