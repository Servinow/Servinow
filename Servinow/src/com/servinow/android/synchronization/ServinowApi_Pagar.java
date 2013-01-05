package com.servinow.android.synchronization;

import java.util.ArrayList;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServinowApi_Pagar extends ServinowApi {
  @SuppressWarnings("unused")
  private class PagarBag {
    public int mesa_id;
    public String metodo_pago;
    public ArrayList<Integer> pedidos;
  }

  public ServinowApi_Pagar(int restaurantID, int mesa_id, String metodo_pago, ArrayList<Integer> pedidos) {
    super("/"+restaurantID+"/api/pagar");
    PagarBag object = new PagarBag();
    object.mesa_id = mesa_id;
    object.metodo_pago = metodo_pago;
    object.pedidos = pedidos;
    
    //Debug
    Log.d("GSON", new GsonBuilder().setPrettyPrinting().create().toJson(object));
    payload = "DATA="+new Gson().toJson(object);
  }

}
