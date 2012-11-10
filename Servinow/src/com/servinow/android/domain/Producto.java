package com.servinow.android.domain;

import com.j256.ormlite.field.DatabaseField;

import android.graphics.drawable.Drawable;

/*
 * TODO ONLY getters.
 * */
public abstract class Producto {

	@DatabaseField(id = true)
	protected int id;
	
	@DatabaseField(canBeNull = false)
	protected String nombre;
	
	@DatabaseField(canBeNull = true)
	private String urlImage;
	
	//TODO remove.
	protected Drawable imagen;
	
	@DatabaseField(canBeNull = true)
	protected String descripcion;
	
	@DatabaseField(canBeNull = false)
	protected double precio;
	
	//TODO remove. The user can't know how many stock there are in the restaurant. Just know if "there are" (boolean).
	protected int stock;
	
	@DatabaseField(canBeNull = false)
	protected boolean isInStock;
	
	//No getter (or setter) for this.
	@DatabaseField(foreign=true, foreignAutoCreate=true, foreignAutoRefresh=true)
	protected Categoria category;
	
	//No getter (or setter) for this.
	@DatabaseField(foreign=true, foreignAutoCreate=true, foreignAutoRefresh=true)
	protected Restaurant restaurant;

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

	//TODO remove.
	public int getStock() {
		return stock;
	}

	//TODO remove.
	public void setStock(int stock) {
		this.stock = stock;
	}
}
