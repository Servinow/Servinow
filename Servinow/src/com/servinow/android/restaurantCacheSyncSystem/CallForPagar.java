package com.servinow.android.restaurantCacheSyncSystem;

import java.io.IOException;
import java.util.ArrayList;

import com.servinow.android.synchronization.ServinowApi_Pagar;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class CallForPagar {

  private final Context context;
  private final int restaurantID;
  private final int mesa_id;
  private final String metodo_pago;
  private final ArrayList<Integer> pedidos;

  public CallForPagar(Context context, int restaurantID, int mesa_id, String metodo_pago, ArrayList<Integer> pedidos) {
    this.context = context;
    this.restaurantID = restaurantID;
    this.mesa_id = mesa_id;
    this.metodo_pago = metodo_pago;
    this.pedidos = pedidos;
  }

  public void start() {
    new task().execute();
  }
  
  private class task extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {
      ServinowApi_Pagar pagar = new ServinowApi_Pagar(restaurantID, mesa_id, metodo_pago, pedidos);
      try {
        pagar.call();
      }
      catch(IOException e) {
        Log.e("Error", "Bad call to ServinowApi_Pagar", e);
      }
      return null;
    }
  }
}
