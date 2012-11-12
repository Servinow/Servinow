package com.servinow.android;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.servinow.android.R;
import com.servinow.android.domain.LineaPedido;
import com.servinow.android.domain.SelectedItem;
import com.servinow.android.widget.ListaPedidoAdapter;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import com.servinow.android.dao.LineaPedidoCache;
import com.servinow.android.dao.PedidoCache;
import com.servinow.android.domain.Pedido;

public class ListaPedidoActivity extends SherlockListActivity {

	private ListaPedidoAdapter listaPedidoAdapter;
	private Pedido pedido;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_pedido);
		
		Log.i("Database", "hola");
		
		// Leer de la base de datos
		
		PedidoCache pedidoDAO = new PedidoCache( ListaPedidoActivity.this );
		pedido = pedidoDAO.getPedidoNotConfirmed();
		Log.i("Database1", pedido.getLineas().size() + "");
		
		int numSelectedItems = pedido.getLineas().size();
		
		SelectedItem[] selectedItems = new SelectedItem[numSelectedItems];
		
		List<LineaPedido> lineasPedido = new ArrayList<LineaPedido>(pedido.getLineas());
		
		for(int i=0; i < numSelectedItems; i++){
			selectedItems[i] = new SelectedItem(lineasPedido.get(i));
		}
		 
		 // Use ArrayList instead of vector
		 ArrayList<SelectedItem> arrayList = new ArrayList<SelectedItem>();
		 arrayList.addAll(Arrays.asList(selectedItems));
		
		listaPedidoAdapter = new ListaPedidoAdapter(this, R.layout.lista_pedido_row, arrayList);
		setListAdapter( listaPedidoAdapter );
		
		((TextView) findViewById(R.id.lista_pedido_precio_total)).setText(pedido.getTotal()+ " Û");
		
		((Button) findViewById(R.id.lista_pedido_button_edit)).setOnClickListener(editButtonClick);
		((Button) findViewById(R.id.lista_pedido_button_delete_confirm)).setOnClickListener(deleteConfirmButtonClick);
		((Button) findViewById(R.id.lista_pedido_button_delete_all)).setOnClickListener(deleteAllButtonClick);
		((Button)findViewById(R.id.lista_pedido_button_delete_cancel)).setOnClickListener(deleteCancelButtonClick);
		
		((Button) findViewById(R.id.lista_pedido_button_cancel)).setOnClickListener(buttonCancelClick);
		((Button) findViewById(R.id.lista_pedido_button_confirm)).setOnClickListener(buttonConfirmClick);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getSupportMenuInflater().inflate(R.menu.activity_lista_pedido, menu);
	    return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    return super.onOptionsItemSelected(item);
	}
	
	// Create an anonymous implementation of OnClickListener
	private OnClickListener editButtonClick = new OnClickListener() {
	    public void onClick(View v) {
	    	findViewById(R.id.lista_pedido_bar_delete).setVisibility(View.INVISIBLE);
	    	findViewById(R.id.lista_pedido_bar_delete_2).setVisibility(View.VISIBLE);
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
	    	LineaPedidoCache listaPedidoDAO = new LineaPedidoCache( ListaPedidoActivity.this );
	    	PedidoCache pedidoDAO = new PedidoCache( ListaPedidoActivity.this );
        	for(int i=0; i < listaPedidoAdapter.getCount(); i++){
        		items[i] = listaPedidoAdapter.getItem(i);
        	}
        	int numberOfItems = listaPedidoAdapter.getCount();
        	int itemsRemoved = 0;
        	for(int i=0; i < numberOfItems; i++){
        		if(items[i].isChecked()){
        			listaPedidoAdapter.remove(items[i]);
        			// BORRAR ELEMENTOS DE LA BASE DE DATOS
                	listaPedidoDAO.deleteLineaPedido(items[i].getId());
                	itemsRemoved++;
        		}
        	}
        	if(itemsRemoved > 0){
	        	// Modificar el valor del PrecioTotal
	        	pedido = pedidoDAO.getPedidoNotConfirmed();
	        	((TextView) findViewById(R.id.lista_pedido_precio_total)).setText(pedido.getTotal()+"" + "");
        	}
        	
        	for(int i=0; i < listaPedidoAdapter.getCount(); i++){
        		SelectedItem itemSelected = listaPedidoAdapter.getItem(i);
        		itemSelected.setCheckBoxVisibility(View.INVISIBLE);
        		itemSelected.setImageVisibility(View.VISIBLE);
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
	
	// Create an anonymous implementation of OnClickListener
	private OnClickListener deleteAllButtonClick = new OnClickListener() {
	    public void onClick(View v) {
	    	for(int i=0; i < listaPedidoAdapter.getCount(); i++){
        		SelectedItem itemSelected = listaPedidoAdapter.getItem(i);
        		itemSelected.setChecked(true);
        	}
        	listaPedidoAdapter.notifyDataSetChanged();
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
	    	        	   Intent myIntent = new Intent(ListaPedidoActivity.this, MainActivity.class);
	    	               ListaPedidoActivity.this.startActivity(myIntent);
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
	    	        	   
	    	        	   // Ir a la actividad de categor’as
	    	        	   Intent myIntent = new Intent(ListaPedidoActivity.this, MainActivity.class);
	    	               ListaPedidoActivity.this.startActivity(myIntent);
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
	
}
