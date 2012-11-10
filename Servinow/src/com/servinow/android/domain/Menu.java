package com.servinow.android.domain;

import java.util.ArrayList;
import java.util.Collection;

import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import android.graphics.drawable.Drawable;

@DatabaseTable(tableName = "menu")
public class Menu extends Producto {

	//TODO remove.
	private ArrayList<Plato> platos;
	
	@ForeignCollectionField(eager = true)
	private Collection<Plato> meals;

	public Menu() {
	}

	//TODO remove.
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

	//TODO remove
	public ArrayList<Plato> getPlatos() {
		return platos;
	}

	//TODO remove
	public void setPlatos(ArrayList<Plato> platos) {
		this.platos = platos;
	}
}
