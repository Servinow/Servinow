package com.servinow.android.restaurantCacheSyncSystem;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.servinow.android.dao.LineaPedidoCache;
import com.servinow.android.domain.Estado;
import com.servinow.android.domain.LineaPedido;
import com.servinow.android.synchronization.ServinowApi_Consultar;
import com.servinow.android.widget.CheckStateArrayAdapter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class CallForConsultar {
  
  private final Context context;
  private final int restaurantID;
  private final int mesa_id;
  private final ArrayList<Integer> pedidos;
  private CheckStateArrayAdapter checkStateArrayAdapter;
  
  @SuppressWarnings("unused")
  private class LineaBag {
    public int linea_pedido_id;
    public String estado;
  }
  
  @SuppressWarnings("unused")
  private class ConsultaBag {
    public int pedido_id_online;
    public LineaBag[] lineas_pedido;
  }

  /**
   * @param context
   * @param restaurantID
   * @param mesa_id
   * @param pedidos
   */
  public CallForConsultar(Context context, int restaurantID, int mesa_id, ArrayList<Integer> pedidos, CheckStateArrayAdapter checkStateArrayAdapter) {
    this.context = context;
    this.restaurantID = restaurantID;
    this.mesa_id = mesa_id;
    this.pedidos = pedidos;
    //Yeah I know and I don't care
    this.checkStateArrayAdapter = checkStateArrayAdapter;
  }
  
  public void start() {
    new task().execute();
  }
  
  private class task extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {
      ServinowApi_Consultar consultar = new ServinowApi_Consultar(restaurantID, mesa_id, pedidos);
      try {
        String result = consultar.call();
        Log.d("GSON", "Debug: Consultar call return:");
        Log.d("GSON", result);
        ConsultaBag[] consulta = new Gson().fromJson(result, ConsultaBag[].class);
        LineaPedidoCache lpCache = new LineaPedidoCache(context);
        for(int i = 0; i<consulta.length; i++) {
          for(int j = 0; j<consulta[i].lineas_pedido.length; j++) {
            LineaPedido lp = lpCache.getLineaPedidoById(consulta[i].lineas_pedido[j].linea_pedido_id);
            String estado = consulta[i].lineas_pedido[j].estado;
            Estado prevEstado = lp.getEstado();
            if(estado.equalsIgnoreCase("COLA"))
              lp.setEstado(Estado.EN_COLA);
            else if(estado.equalsIgnoreCase("PREPARANDOSE"))
              lp.setEstado(Estado.PREPARANDO);
            else if(estado.equalsIgnoreCase("PREPARADO") || estado.equalsIgnoreCase("TRANSITO")) 
              lp.setEstado(Estado.LISTO);
            else if(estado.equalsIgnoreCase("SERVIDO"))
              lp.setEstado(Estado.SERVIDO);
            lpCache.update(lp);
            if(prevEstado!=lp.getEstado()) {
              checkStateArrayAdapter.updateOrder(lp.getId(), lp.getEstado());
            }
          }
        }
      }
      catch(IOException e) {
        Log.e("Error", "Bad call to ServinowApi_Consultar", e);
      }
      return null;
    }
    
    @Override
    protected void onPostExecute(Void result) {
      super.onPostExecute(result);
      
    }
  }

}
