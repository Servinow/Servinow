package com.servinow.android.widget;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.zip.Inflater;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.servinow.android.R;
import com.servinow.android.Util.ImageAsyncHelper;
import com.servinow.android.Util.ImageAsyncHelper.ImageAsyncHelperCallBack;
import com.servinow.android.dao.LineaPedidoCache;
import com.servinow.android.dao.PedidoCache;
import com.servinow.android.domain.Estado;
import com.servinow.android.domain.LineaPedido;
import com.servinow.android.domain.OrdersState;
import com.servinow.android.restaurantCacheSyncSystem.CallForBorrar;
import com.servinow.android.restaurantCacheSyncSystem.CallForConsultar;
import com.servinow.android.widget.PurchasedItemAdapter.ViewHolder;

public class CheckStateArrayAdapter extends ArrayAdapter<OrdersState> {

	private final Context context;
	private final ArrayList<OrdersState> orders;
	// private final String[] platos;
	// public Boolean res = false;
	private OrdersState ord = null;
	public HashMap<Integer, Integer> lineasCant = new HashMap<Integer, Integer>();

	protected Handler taskHandler = new Handler();
	protected Boolean isComplete = false;
	public Boolean flagTimer = false;
	public int countOrders = 0;
	public int countChanges = 0;
	
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	static class ViewHolder {
		TextView name;
		TextView state;
		ImageView image;
		TextView ronda;
		public ImageButton deleteButtom;
	}

	public CheckStateArrayAdapter(Context context, ArrayList<OrdersState> orders) {
		super(context, -1, orders);
		this.context = context;
		this.orders = orders;
		// this.platos = platos;
		countOrders = orders.size();
		countChanges = countOrders * 3;
		setLineasCantidad();
		
		scheduler.scheduleAtFixedRate(new Runnable() {      
      @Override
      public void run() {
        runNextTask();        
      }
    }, 6, 5, TimeUnit.SECONDS);
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		if (orders.get(position).roundmark)
			return 1;
		else
			return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		ViewHolder holder = new ViewHolder();

		ord = orders.get(position);
		if (rowView == null) {
			LayoutInflater inflater = ((Activity) getContext())
					.getLayoutInflater();
			if (!ord.roundmark) {
				rowView = inflater.inflate(R.layout.list_check_state, parent,
						false);
				holder.name = (TextView) rowView
						.findViewById(R.id.TextViewCheckStateName);
				holder.state = (TextView) rowView
						.findViewById(R.id.TextViewCheckStateState);
				holder.image = (ImageView) rowView
						.findViewById(R.id.ImageViewCheckState);
				holder.deleteButtom = (ImageButton) rowView
						.findViewById(R.id.CheckState_row_Cancel);
			} else {
				rowView = inflater.inflate(R.layout.list_check_state_round,
						parent, false);
				holder.ronda = (TextView) rowView
						.findViewById(R.id.TextViewCheckStateRound);
			}
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}

		if (!ord.roundmark) {
			
			holder.name.setText(ord.name);
			if (ord.state == Estado.EN_COLA) {
				holder.state.setTextColor(Color.argb(255, 255, 0, 0));
				holder.state.setText(R.string.checkstateactivity_encola);
			} else if (ord.state == Estado.PREPARANDO) {
				holder.state.setTextColor(Color.argb(255, 187, 187, 0));
				holder.state.setText(R.string.checkstateactivity_encocina);
			} else if (ord.state == Estado.LISTO) {
				holder.state.setTextColor(Color.argb(255, 66, 204, 68));
				holder.state.setText(R.string.checkstateactivity_preparado);
			} else {
				holder.state.setTextColor(Color.argb(255, 0, 0, 0));
				holder.state.setText(R.string.checkstateactivity_servido);
			}

			ImageAsyncHelper imageAsyncHelper = new ImageAsyncHelper();
			holder.image.setImageBitmap(imageAsyncHelper.getBitmap(ord.imageName,
					new ImageAsyncHelperCallBack() {
				ImageView imgView;

				public ImageAsyncHelperCallBack setImageView(
						ImageView imgView) {
					this.imgView = imgView;
					return this;
				}

				@Override
				public void onImageSyn(Bitmap img) {
					imgView.setImageBitmap(img);
				}
			}.setImageView(holder.image), null));

			// holder.deleteButtom.setTag(position);

			if (ord.state != Estado.EN_COLA)
				holder.deleteButtom.setEnabled(false);
			else
				holder.deleteButtom.setEnabled(true);
			final int pos = position;
			holder.deleteButtom
					.setOnClickListener(new Button.OnClickListener() {
						@Override
						public void onClick(View v) {
							callDialog(context, pos);

						}
					});

		} else {
		  StringBuilder header = new StringBuilder(" - Ronda " + ord.round + " - ");
		  if (ord.pagado) {
		    header.append(context.getResources().getString(R.string.checkstate_pagado));
	    }
			holder.ronda.setText(header);
		}

		return rowView;
	}

	public void callDialog(Context ctx, int pos) {
		final int position = pos;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setMessage(ctx
				.getString(R.string.checkstateactivity_cancelar_elem));
		builder.setPositiveButton(
				(ctx.getString(R.string.checkstateactivity_si)),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface arg0, int arg1) {
					//	Integer index = (Integer) vv.getTag();
						if(deleteInDB(orders.get(position))) {
						  orders.remove(position);
						  notifyDataSetChanged();
						}
					}
				});
		builder.setNegativeButton(
				ctx.getString(R.string.checkstateactivity_no),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface arg0, int arg1) {

					}
				});

		AlertDialog alert = builder.create();
		alert.show();

	}

	public boolean deleteInDB(OrdersState ord) {
		// TODO BORRAR EN LA BASE DE DATOS
		//LineaPedidoCache lpCach = new LineaPedidoCache(context);
		// LineaPedido lp = new LineaPedido();
		// lp = lpCach.getLineaPedido(ord.productoId);
		/*
		 * int cant = lineasCant.get(ord.lineaPedidoId); if(cant<=1)
		 * lpCach.deleteLineaPedido(ord.lineaPedidoId); else
		 * lpCach.updateQuantityLineaPedido(ord.lineaPedidoId, ord.cantidad-1);
		 * lineasCant.remove(ord.lineaPedidoId);
		 * lineasCant.put(ord.lineaPedidoId, cant-1);
		 */

		/*
		 * LineaPedido lp = lpCach.getLineaPedido(ord.productoId, ord.pedidoId);
		 * int cantidad = lp.getCantidad(); Log.d("ord.lineaPedidoId: ","");
		 * Log.d("--casa---","---"); Log.d("     lp:"+lp.getId(),"");
		 * lpCach.updateQuantityLineaPedido(ord.lineaPedidoId, cantidad-1);
		 */
	  
	  int cantidad = ord.lp.getCantidad();
//		if(cantidad-1<=0)
//			lpCach.deleteLineaPedido(ord.lp.getId());
//		else
//			lpCach.updateQuantityLineaPedido(ord.lp.getId(), cantidad - 1);
	  
	  new CallForBorrar(context, ord.restaurantID, ord.mesa_id, ord.pedidoId, ord.lineaPedidoId, cantidad-1).start();
			
		return true;
	}

	protected void runNextTask() {
		// run my task.
	  ArrayList<Integer> pedidos = new ArrayList<Integer>();
	  PedidoCache pedidoCache = new PedidoCache(context);
	  for(Iterator<OrdersState> it = orders.iterator(); it.hasNext();) {
	    OrdersState o = it.next();
      if(!o.roundmark) {
        int pedidoId = pedidoCache.getPedidoById(o.pedidoId).getOnlineID();
        pedidos.add(pedidoId);
      }
	  }
	  new CallForConsultar(context, orders.get(1).restaurantID, orders.get(1).mesa_id, pedidos, this).start();
	}

	public void setLineasCantidad() {
		int prevLn = -1;
		for (int i = 0; i < orders.size(); i++) {
			if (prevLn != orders.get(i).lineaPedidoId) {
				prevLn = orders.get(i).lineaPedidoId;
				lineasCant.put(prevLn, orders.get(i).cantidad);
			}
		}
	}

  public void updateOrder(int id, Estado estado) {
    for(int i=0;i<orders.size();i++) {
      OrdersState order = orders.get(i);
      if(order.lineaPedidoId==id) {
        order.state = estado;
      }
    }
    ((Activity) context).runOnUiThread(new Runnable() {
      
      @Override
      public void run() {
        notifyDataSetChanged();
      }
    });
  }

  public void stopScheduler() {
    scheduler.shutdown();
  }

}
