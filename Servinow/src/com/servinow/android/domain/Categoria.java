package com.servinow.android.domain;

import java.util.ArrayList;
import java.util.Collection;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import android.graphics.drawable.Drawable;


/*
 * TODO ONLY getters.
 * */

@DatabaseTable(tableName = "category")
public class Categoria {

	@DatabaseField(id = true)
	private int id;
	
	@DatabaseField(canBeNull = false)
	private String nombre;
	
	@DatabaseField(canBeNull = true)
	private String urlImage;
	
	//TODO remove.
	private Drawable imagen;
	
	@ForeignCollectionField(eager = true)
	private Collection<Producto> products;
	
	//TODO remove.
	private ArrayList<Producto> productos;
	
	//No getter (or setter) for this.
	@DatabaseField(foreign=true, foreignAutoCreate=true, foreignAutoRefresh=true)
	protected Restaurant restaurant;
	
	public Categoria() {
	}

	//TODO remove
	public Categoria(String nombre, Drawable imagen, ArrayList<Producto> productos) {
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

	//TODO remove.
	public ArrayList<Producto> getProductos() {
		return productos;
	}

	//TODO remove.
	public void setProductos(ArrayList<Producto> productos) {
		this.productos = productos;
	}
}
