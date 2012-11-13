package com.servinow.android.widget;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.servinow.android.R;
import com.servinow.android.domain.Producto;

public class ProductoAdapter extends ArrayAdapter<Producto> {
	
	private int layoutResourceId;

	public ProductoAdapter(Context context, int layoutResourceId, Producto[] objects) {
		super(context, layoutResourceId, objects);
		this.layoutResourceId = layoutResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		
		if (view == null) {
	        LayoutInflater inflater = ((Activity)this.getContext()).getLayoutInflater();
	        view = inflater.inflate(layoutResourceId, parent, false);
		}	

		Producto plato = this.getItem(position);

		if (plato != null) {
			ImageView imageViewPlato = (ImageView)view.findViewById(R.id.imageViewPlato);
			TextView textViewNombre = (TextView)view.findViewById(R.id.textViewNombre);
			TextView textViewPrecio = (TextView)view.findViewById(R.id.textViewPrecio);
			TextView textViewCantidad = (TextView)view.findViewById(R.id.textViewCantidad);

			if (imageViewPlato != null)
				imageViewPlato.setImageDrawable(plato.getImagen());
			
			if (textViewNombre != null)
				textViewNombre.setText(plato.getNombre());
			
			if (textViewPrecio != null)
				textViewPrecio.setText(Double.toString(plato.getPrecio()) + " €");
			
			/*
			 * Para mostrar la cantidad productos que hay de un mismo tipo (x4),
			 * hay que buscar si el producto se encuentra en alguna línea de
			 * pedido del pedido actual, y en tal caso obtener la cantidad.
			 * Pensar en cómo hacerlo.
			 */			
			/*if (textViewCantidad != null) {
				int cantidad = plato.getStock();

				if (cantidad > 0)
					textViewCantidad.setText("x " + plato.getStock());
				else
					textViewCantidad.setText("");
			}*/
		}
		
		return view;
	}

}
