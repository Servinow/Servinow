package com.servinow.android;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.servinow.android.dao.CategoryCache;
import com.servinow.android.domain.Categoria;
import com.servinow.android.domain.Producto;
import com.servinow.android.widget.ProductoAdapter;

public class ProductosActivity extends SherlockListActivity {
	
	private Producto [] productos = new Producto[0];
	private int restaurantID;
	private int placeID;

    @Override
	protected void onResume() {
		super.onResume();
		
        Bundle extras = getIntent().getExtras();
		if(extras != null)
		{
			restaurantID = extras.getInt(Param.RESTAURANT.toString());
			placeID = extras.getInt(Param.PLACE.toString());
			
			int categoriaID = extras.getInt(Param.CATEGORIA.toString());
			Categoria categoria = new CategoryCache(this).getCategoria(categoriaID);
			List<Producto> listaProductos = new ArrayList<Producto>(categoria.getProducts());
			
			productos = new Producto [listaProductos.size()];
			listaProductos.toArray(productos);
			setTitle(categoria.getNombre());
		}

        ProductoAdapter adapter = new ProductoAdapter(this, R.layout.item_producto, productos);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(itemClickListener);
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.activity_categorias, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.categorias_button_lista_pedidos:
    			Intent i = new Intent(ProductosActivity.this, ListaPedidoActivity.class);
    			Bundle b = new Bundle();
    			b.putInt(Param.RESTAURANT.toString(), restaurantID);
    			b.putInt(Param.PLACE.toString(), placeID);
    			i.putExtras(b);

    			startActivity(i);
             default:
            	 return true;
        }
    }

	private OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			int productoID = productos[arg2].getId();

			Intent i = new Intent(ProductosActivity.this, DetalleProductoActivity.class);
			Bundle b = new Bundle();
			b.putInt(Param.RESTAURANT.toString(), restaurantID);
			b.putInt(Param.PLACE.toString(), placeID);
			b.putInt(Param.PRODUCTO.toString(), productoID);
			i.putExtras(b);

			startActivity(i);
		}
    };
}
