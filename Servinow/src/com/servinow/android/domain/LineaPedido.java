package com.servinow.android.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "orderLine")
public class LineaPedido {

	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(canBeNull = true)
	private Integer onlineID;
	
	@DatabaseField(canBeNull = false)
	private int cantidad;
	
	@DatabaseField(canBeNull = false)
	private Estado estado; //It is stored as string automatically in the DB by ORMLite. Great!
	
	@DatabaseField(foreign=true, foreignAutoCreate=true, foreignAutoRefresh=true)
	private Producto producto;
	
	@DatabaseField(foreign=true, foreignAutoCreate=true, foreignAutoRefresh=true)
	private Pedido order;

	public LineaPedido() {
	}

	public LineaPedido(int cantidad, Estado estado, Producto producto) {
		this.cantidad = cantidad;
		this.estado = estado;
		this.producto = producto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	public double getTotal() {
		return producto.getPrecio() * cantidad;
	}
}
