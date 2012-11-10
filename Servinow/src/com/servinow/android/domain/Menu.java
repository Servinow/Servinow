package com.servinow.android.domain;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

public class Menu extends Producto {

	private ArrayList<Plato> platos;

	public Menu() {
		super();
	}

	public Menu(String nombre, Drawable imagen, String descripcion,
			double precio, int stock, ArrayList<Plato> platos) {
		super();
		this.nombre = nombre;
		this.imagen = imagen;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
		this.platos = platos;
	}

	public ArrayList<Plato> getPlatos() {
		return platos;
	}

	public void setPlatos(ArrayList<Plato> platos) {
		this.platos = platos;
	}
}
