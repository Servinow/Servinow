package com.servinow.android;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.servinow.android.Util.ImageAsyncHelper;
import com.servinow.android.Util.ImageAsyncHelper.ImageAsyncHelperCallBack;
import com.servinow.android.dao.ProductCache;
import com.servinow.android.domain.Producto;
import com.servinow.android.pedidosSystem.PedidosHandler;
import com.servinow.android.picker.NumberPicker;

public class DetalleProductoActivity extends SherlockActivity {

	private int restaurantID;
	private int placeID;
	private Producto producto;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);
        
        Bundle extras = getIntent().getExtras();
		if(extras != null)
		{
			restaurantID = extras.getInt(Param.RESTAURANT.toString());
			placeID = extras.getInt(Param.PLACE.toString());
			
			int productoID = extras.getInt(Param.PRODUCTO.toString());			
			producto = new ProductCache(this).getProducto(productoID);

	        ImageView imageView = (ImageView) findViewById(R.id.imageView);
			imageView.setImageDrawable(producto.getImagen());
			
			ImageAsyncHelper imageAsyncHelper = new ImageAsyncHelper();
			
			Bitmap img = imageAsyncHelper.getBitmap(producto.getImageName(),
					new ImageAsyncHelperCallBack() {
				
				@Override
				public void onImageSyn(Bitmap img) {
					ImageView imageView = (ImageView) findViewById(R.id.imageView);
					imageView.setImageBitmap(img);
				}
			}, null);
			
			if (img != null)
				imageView.setImageBitmap(img);
			
			TextView textViewPrecio = (TextView) findViewById(R.id.textViewPrecio);
			textViewPrecio.setText(String.valueOf(producto.getPrecio()) + " €");
			
			TextView textViewDisponible = (TextView) findViewById(R.id.textViewDisponibilidad);
			Button buttonAdd = (Button) findViewById(R.id.buttonAdd);
			if (producto.isDisponible()) {
				textViewDisponible.setText("Disponible");
				buttonAdd.setEnabled(true);
			} else {
				textViewDisponible.setText("No disponible");
				textViewDisponible.setTextColor(Color.RED);
				buttonAdd.setEnabled(false);
			}
			
			TextView textViewDescripcion = (TextView) findViewById(R.id.textViewDescripcion);
			textViewDescripcion.setText(producto.getDescripcion());
			
			setTitle(producto.getNombre());
		}

		final Button button = (Button) findViewById(R.id.buttonAdd);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	AlertDialog.Builder builder = new AlertDialog.Builder(DetalleProductoActivity.this);
            	LayoutInflater inflater = DetalleProductoActivity.this.getLayoutInflater();
            	View view = inflater.inflate(R.layout.picker_activity, null);
            	
            	NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.lista_pedido_picker);
            	numberPicker.setCurrent(1); // Por defecto ponemos la cantidad a 1
            	
            	builder.setView(view)
            	.setPositiveButton(R.string.lista_pedido_cancel_button_ok, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						NumberPicker numberPicker = (NumberPicker) ((AlertDialog) dialog).findViewById(R.id.lista_pedido_picker);
						int cantidad = numberPicker.getCurrent();
						PedidosHandler pedidosHandler = new PedidosHandler(DetalleProductoActivity.this);
						pedidosHandler.insertarProducto(producto, cantidad, placeID, restaurantID);
		    	    	
						Toast.makeText(DetalleProductoActivity.this, "Se ha añadido " + cantidad +
								" de " + producto.getNombre() + " al pedido", Toast.LENGTH_LONG).show();
		    	    	finish();
					}
            		
            	})
            	.setNegativeButton(R.string.lista_pedido_cancel_button_cancel, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
            	});
            	
    	    	AlertDialog alert = builder.create();
    	    	alert.show();
            }
        });
        
        getSupportActionBar().setHomeButtonEnabled(true);
    }
    
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
