package com.servinow.android.synchronization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.servinow.android.domain.LineaPedido;
import com.servinow.android.domain.Pedido;

public class ServinowApi_Confirmar extends ServinowApi {
  //Ugly thing incoming...
  @SuppressWarnings("unused")
  private class LineaPedidoBag {
    public int linea_pedido_id;
    public int producto_id;
    public int cantidad;
    public LineaPedidoBag(LineaPedido lineaPedido) {
      linea_pedido_id = lineaPedido.getId();
      producto_id = lineaPedido.getProducto().getId();
      cantidad = lineaPedido.getCantidad();
    }
  }
  @SuppressWarnings("unused")
  private class PedidoBag {
    public int mesa_id;
    public ArrayList<LineaPedidoBag> lineas_pedido;
    public PedidoBag(Pedido pedido) {
      mesa_id = pedido.getPlace().getOnlineID();
      lineas_pedido = new ArrayList<LineaPedidoBag>();
      Collection<LineaPedido> lineas = pedido.getLineas();
      Iterator<LineaPedido> lineasIt = lineas.iterator();
      while(lineasIt.hasNext()) {
        lineas_pedido.add(new LineaPedidoBag(lineasIt.next()));
      }
    }
  }

  public ServinowApi_Confirmar(int restaurantID, Pedido pedido) {
    super("/"+ restaurantID + "/api/confirmar");
    PedidoBag object = new PedidoBag(pedido);
    //Debug
    Log.d("GSON", new GsonBuilder().setPrettyPrinting().create().toJson(object));
    payload = new Gson().toJson(object);
  }
}
