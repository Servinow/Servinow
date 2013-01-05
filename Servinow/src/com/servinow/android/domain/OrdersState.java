package com.servinow.android.domain;

import android.graphics.Bitmap;

public class OrdersState {
	public String name = "name";
	public Estado state = Estado.EN_COLA;
	public Bitmap image;
	public int round = 1;
	public Boolean roundmark=false;
	public int lineaPedidoId;
	public int productoId;
	public int pedidoId;
	public int mesa_id;
	public int cantidad=0;
	public String imageName;
	public Boolean pagado=false;
	public LineaPedido lp;
  public int restaurantID;
}
