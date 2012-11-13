package com.servinow.android.widget;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.servinow.android.R;
import com.servinow.android.domain.Categoria;

public class CategoriaAdapter extends ArrayAdapter<Categoria> {

	private int layoutResourceId;

	public CategoriaAdapter(Context context, int layoutResourceId,
			Categoria[] objects) {
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

		Categoria categoria = this.getItem(position);

		if (categoria != null) {
			ImageView imageViewCategoria = (ImageView)view.findViewById(R.id.imageViewCategoria);
			TextView textViewCategoria = (TextView)view.findViewById(R.id.textViewCategoria);
			
			if (imageViewCategoria != null)
				imageViewCategoria.setImageDrawable(categoria.getImagen());
			
			if (textViewCategoria != null)
				textViewCategoria.setText(categoria.getNombre());
		}

		return view;
	}
}
