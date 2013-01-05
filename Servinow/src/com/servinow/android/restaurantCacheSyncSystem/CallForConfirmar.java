package com.servinow.android.restaurantCacheSyncSystem;

import java.io.IOException;

import com.servinow.android.dao.PedidoCache;
import com.servinow.android.domain.Pedido;
import com.servinow.android.synchronization.ServinowApi_Confirmar;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class CallForConfirmar {

  private final Context context;
  private final int     restaurantID;
  private final Pedido pedido;

  public CallForConfirmar(Context context, Pedido pedido, int restaurantID) {
    this.context = context;
    this.pedido = pedido;
    this.restaurantID = restaurantID;
  }

  public void start() {
    new task().execute();
  }

  private class task extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {
      ServinowApi_Confirmar confirmar = new ServinowApi_Confirmar(restaurantID, pedido);
      try {
        String result = confirmar.call();
        pedido.setOnlineID(Integer.parseInt(result));
        Log.d("ServinowApi", "confirmar pedido, respuesta:"+result);
        new PedidoCache(context).updatePedido(pedido);
      }
      catch(IOException e) {
        Log.e("Error", "Bad call to ServinowApi_Confirmar", e);
      }
      catch (NumberFormatException e) {
        Log.e("Error", "Bad return from ServinowApi_Confirmar", e);
      }
      return null;
    }
  }

}
