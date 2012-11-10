package com.servinow.android.domain;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

public class Categoria {

	private int id;
	private String nombre;
	private Drawable imagen;
	private ArrayList<Producto> productos;
	
	public Categoria() {
		super();
	}

	public Categoria(String nombre, Drawable imagen,
			ArrayList<Producto> productos) {
		super();
		this.nombre = nombre;
		this.imagen = imagen;
		this.productos = productos;
	}

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

	public ArrayList<Producto> getProductos() {
		return productos;
	}

	public void setProductos(ArrayList<Producto> productos) {
		this.productos = productos;
	}
}
