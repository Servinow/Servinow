package com.servinow.android.widget;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.servinow.android.ProductosActivity;
import com.servinow.android.R;
import com.servinow.android.Util.ImageAsyncHelper;
import com.servinow.android.Util.ImageAsyncHelper.ImageAsyncHelperCallBack;
import com.servinow.android.dao.LineaPedidoCache;
import com.servinow.android.domain.LineaPedido;
import com.servinow.android.domain.Producto;

public class ProductoAdapter extends ArrayAdapter<Producto> {
	
	private int layoutResourceId;
	private List<LineaPedido> lineas;

	public ProductoAdapter(Context context, int layoutResourceId, Producto[] objects) {
		super(context, layoutResourceId, objects);
		this.layoutResourceId = layoutResourceId;
		
		LineaPedidoCache lineaPedidoCache = new LineaPedidoCache(getContext());
		int placeID = ((ProductosActivity) this.getContext()).placeID;
		int restaurantID =  ((ProductosActivity) this.getContext()).restaurantID;
		lineas = lineaPedidoCache.getLineasPedidoNotConfirmed(placeID, restaurantID);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		
		if (view == null) {
	        LayoutInflater inflater = ((Activity)this.getContext()).getLayoutInflater();
	        view = inflater.inflate(layoutResourceId, parent, false);
		}	

		Producto producto = this.getItem(position);

		if (producto != null) {
			final ImageView imageViewProducto = (ImageView)view.findViewById(R.id.imageViewProducto);
			
			if (imageViewProducto != null) {
				ImageAsyncHelper imageAsyncHelper = new ImageAsyncHelper();
				
				Bitmap img = imageAsyncHelper.getBitmap(producto.getImageName(),
						new ImageAsyncHelperCallBack() {
					
					@Override
					public void onImageSyn(Bitmap img) {
						imageViewProducto.setImageBitmap(img);
					}
				}, null);
				
				if (img != null)
					imageViewProducto.setImageBitmap(img);
			}
			
			TextView textViewNombre = (TextView)view.findViewById(R.id.textViewNombre);
			if (textViewNombre != null)
				textViewNombre.setText(producto.getNombre());

			TextView textViewPrecio = (TextView)view.findViewById(R.id.textViewPrecio);
			if (textViewPrecio != null)
				textViewPrecio.setText(Double.toString(producto.getPrecio()) + " €");
			
			TextView textViewCantidad = (TextView)view.findViewById(R.id.textViewCantidad);
				
			Iterator<LineaPedido> itr = this.lineas.iterator();
			boolean productoEncontrado = false;
			while (itr.hasNext() && !productoEncontrado) {
				LineaPedido lp = itr.next();
				if (lp.getProducto().equals(producto)) {
					textViewCantidad.setText("x" + lp.getCantidad());
					productoEncontrado = true;
				}
			}
			if (!productoEncontrado)
				textViewCantidad.setText("");
		}
		
		return view;
	}

}
