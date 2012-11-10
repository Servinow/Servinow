package com.servinow.android.domain;

import android.graphics.drawable.Drawable;

public class Plato extends Producto {

	public Plato() {
		super();
	}

	public Plato(String nombre, Drawable imagen, String descripcion,
			double precio, int stock) {
		super();
		this.nombre = nombre;
		this.imagen = imagen;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
	}
}
