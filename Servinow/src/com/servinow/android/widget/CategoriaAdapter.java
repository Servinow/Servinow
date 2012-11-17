package com.servinow.android.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.servinow.android.R;
import com.servinow.android.Util.ImageAsyncHelper;
import com.servinow.android.Util.ImageAsyncHelper.ImageAsyncHelperCallBack;
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
			final ImageView imageViewCategoria = (ImageView)view.findViewById(R.id.imageViewCategoria);
			
			if (imageViewCategoria != null) {			
				ImageAsyncHelper imageAsyncHelper = new ImageAsyncHelper();
				
				Bitmap img = imageAsyncHelper.getBitmap(categoria.getImageName(),
						new ImageAsyncHelperCallBack() {
					
					@Override
					public void onImageSyn(Bitmap img) {
						imageViewCategoria.setImageBitmap(img);
					}
				}, null);
				
				if (img != null)
					imageViewCategoria.setImageBitmap(img);
			}

			TextView textViewCategoria = (TextView)view.findViewById(R.id.textViewCategoria);
			if (textViewCategoria != null)
				textViewCategoria.setText(categoria.getNombre());
		}

		return view;
	}
}
