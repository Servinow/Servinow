package com.servinow.android.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "lineaPedido")
public class LineaPedido {

	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(canBeNull = false)
	private int cantidad;
	
	@DatabaseField(canBeNull = false)
	private Estado estado;
	
	@DatabaseField(canBeNull = false, foreign = true)
	private Producto producto;

	public LineaPedido() {
		super();
	}

	public LineaPedido(int cantidad, Estado estado, Producto producto) {
		super();
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
