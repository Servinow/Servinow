package com.servinow.android.synchronization;

import java.util.ArrayList;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServinowApi_Consultar extends ServinowApi {
  @SuppressWarnings("unused")
  private class ConsultaBag {
    public int mesa_id;
    public ArrayList<Integer> pedidos;
  }

  public ServinowApi_Consultar(int restaurantID, int mesa_id, ArrayList<Integer> pedidos) {
    super("/"+restaurantID+"/api/consultar");
    ConsultaBag object = new ConsultaBag();
    object.mesa_id = mesa_id;
    object.pedidos = pedidos;
    
    //Debug
    Log.d("GSON", new GsonBuilder().setPrettyPrinting().create().toJson(object));
    payload = "DATA="+new Gson().toJson(object);
  }

}
