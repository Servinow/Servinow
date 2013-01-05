package com.servinow.android.synchronization;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServinowApi_Borrar extends ServinowApi {
  @SuppressWarnings("unused")
  private class BorrarBag {
    public int pedido_id_online;
    public int mesa_id;
    public int linea_pedido_local;
    public int cantidad;
  }

  public ServinowApi_Borrar(int restaurantID, int mesa_id, int pedido_id_online, int linea_pedido_local, int cantidad) {
    super("/"+ restaurantID + "/api/borrar");
    BorrarBag object = new BorrarBag();
    object.mesa_id = mesa_id;
    object.pedido_id_online = pedido_id_online;
    object.linea_pedido_local = linea_pedido_local;
    object.cantidad = cantidad;
    //Debug
    Log.d("GSON", new GsonBuilder().setPrettyPrinting().create().toJson(object));
    payload = new Gson().toJson(object);
  }

}
