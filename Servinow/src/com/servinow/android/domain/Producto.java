package com.servinow.android.domain;

import android.graphics.drawable.Drawable;

public abstract class Producto {

	private int id;
	protected String nombre;
	protected Drawable imagen;
	protected String descripcion;
	protected double precio;
	protected int stock;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Drawable getImagen() {
		return imagen;
	}

	public void setImagen(Drawable imagen) {
		this.imagen = imagen;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
}
