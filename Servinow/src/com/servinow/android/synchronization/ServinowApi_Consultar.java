package com.servinow.android.synchronization;

import java.util.ArrayList;
import java.util.Iterator;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServinowApi_Consultar extends ServinowApi {
  
  @SuppressWarnings("unused")
  private class PedidoBag{
    public int pedido_id_online;
    public PedidoBag(int intValue) {
      pedido_id_online = intValue;
    }
  }
  
  @SuppressWarnings("unused")
  private class ConsultaBag {
    public int mesa_id;
    public ArrayList<PedidoBag> pedidos;
    public ConsultaBag(int mesa_id, ArrayList<Integer> pedidos) {
      this.mesa_id = mesa_id;
      this.pedidos = new ArrayList<PedidoBag>();
      for(Iterator<Integer> it=pedidos.iterator();it.hasNext();) {
        this.pedidos.add(new PedidoBag(it.next().intValue()));
      }
    }
  }

  public ServinowApi_Consultar(int restaurantID, int mesa_id, ArrayList<Integer> pedidos) {
    super("/"+restaurantID+"/api/consultar");
    ConsultaBag object = new ConsultaBag(mesa_id, pedidos);
    
    //Debug
    Log.d("GSON", new GsonBuilder().setPrettyPrinting().create().toJson(object));
    payload = new Gson().toJson(object);
  }

}
