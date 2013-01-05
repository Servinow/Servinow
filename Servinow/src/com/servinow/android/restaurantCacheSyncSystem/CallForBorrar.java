package com.servinow.android.restaurantCacheSyncSystem;

import java.io.IOException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.servinow.android.dao.LineaPedidoCache;
import com.servinow.android.synchronization.ServinowApi_Borrar;

public class CallForBorrar {

  private final Context context;
  private final int restaurantID;
  private final int mesa_id;
  private final int pedido_id_online;
  private final int linea_pedido_local;
  private final int cantidad;

  public CallForBorrar(Context context, int restaurantID, int mesa_id, int pedido_id_online, int linea_pedido_local, int cantidad) {
    this.context = context;
    this.restaurantID = restaurantID;
    this.mesa_id = mesa_id;
    this.pedido_id_online = pedido_id_online;
    this.linea_pedido_local = linea_pedido_local;
    this.cantidad = cantidad;
  }
  
  public void start() {
    new task().execute();
  }

  private class task extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {
      ServinowApi_Borrar borrar = new ServinowApi_Borrar(restaurantID, mesa_id, pedido_id_online, linea_pedido_local, 1);
      try {
        String result = borrar.call();
        if(Integer.parseInt(result)!=0) {
          if(cantidad <= 0)
            new LineaPedidoCache(context).deleteLineaPedido(linea_pedido_local);
          else
            new LineaPedidoCache(context).updateQuantityLineaPedido(linea_pedido_local, cantidad);
        }
      }
      catch(IOException e) {
        Log.e("Error", "Bad call to ServinowApi_Borrar", e);
      }
      catch (NumberFormatException e) {
        Log.e("Error", "Bad return from ServinowApi_Borrar", e);
      }
      return null;
    }
  }

}
