package com.servinow.android.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import android.graphics.drawable.Drawable;

@DatabaseTable(tableName = "meal")
public class Plato extends Producto {
	
	//No getter (or setter) for this.
	@DatabaseField(foreign=true, foreignAutoCreate=true, foreignAutoRefresh=true)
	private Menu menu;

	public Plato() {
	}

	//TODO remove.
	public Plato(String nombre, Drawable imagen, String descripcion,
			double precio, int stock) {
		this.nombre = nombre;
		this.imagen = imagen;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
	}
}
