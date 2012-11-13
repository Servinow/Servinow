package com.servinow.android;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.servinow.android.dao.ProductCache;
import com.servinow.android.domain.Producto;

public class DetalleProductoActivity extends SherlockActivity {

	private int restaurantID;
	private int placeID;
	private int categoriaID;
	private int productoID;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);
        
        Bundle extras = getIntent().getExtras();
		if(extras != null)
		{
			restaurantID = extras.getInt(Param.RESTAURANT.toString());
			placeID = extras.getInt(Param.PLACE.toString());
			categoriaID = extras.getInt(Param.CATEGORIA.toString());
			productoID = extras.getInt(Param.PRODUCTO.toString());
			
			Producto producto = new ProductCache(this).getProducto(productoID);
	        Resources res = getResources();
			//TODO obtener las imágenes a partir de su URL
			producto.setImagen(res.getDrawable(R.drawable.meal));

	        ImageView imageView = (ImageView) findViewById(R.id.imageView);
			imageView.setImageDrawable(producto.getImagen());
			
			TextView textViewPrecio = (TextView) findViewById(R.id.textViewPrecio);
			textViewPrecio.setText(String.valueOf(producto.getPrecio()) + " €");
			
			TextView textViewDisponible = (TextView) findViewById(R.id.textViewDisponibilidad);
			String disponibilidad = producto.isStock()?"Disponible":"No disponible";
			textViewDisponible.setText(disponibilidad);
			
			TextView textViewDescripcion = (TextView) findViewById(R.id.textViewDescripcion);
			textViewDescripcion.setText(producto.getDescripcion());
			
			setTitle(producto.getNombre());
		}

		final Button button = (Button) findViewById(R.id.buttonAdd);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
				Bundle b = new Bundle();
				b.putInt(Param.RESTAURANT.toString(), restaurantID);
				b.putInt(Param.PLACE.toString(), placeID);
				b.putInt(Param.CATEGORIA.toString(), categoriaID);
				b.putInt(Param.PRODUCTO.toString(), productoID);
				// TODO se envía cantidad=1 para probar. Implementar diálogo con NumberPicker
				b.putInt(Param.CANTIDAD.toString(), 1);
				
				// TODO conectar con la parte de Carlos.
				/*Intent i = new Intent(DetalleProductoActivity.this, ?.class);
				i.putExtras(b);
				startActivity(i);*/
				// TODO para depuración, eliminar
            	printToast("Se ha hecho click en el botón de añadir");            	
            }
        });
    }
	
    // TODO para depuración, eliminar
	private void printToast(String mensaje) {
		Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
	}
}
