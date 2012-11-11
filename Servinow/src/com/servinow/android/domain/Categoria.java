package com.servinow.android.domain;

import java.util.Collection;

import android.graphics.drawable.Drawable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;


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

	// Requerido por ORMLite
	@SuppressWarnings("unused")
	@DatabaseField(foreign=true, foreignAutoCreate=true, foreignAutoRefresh=true)
	public Restaurant restaurant;
	
	public Categoria() {
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

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public Drawable getImagen() {
		return imagen;
	}

	public void setImagen(Drawable imagen) {
		this.imagen = imagen;
	}

	public Collection<Producto> getProducts() {
		return products;
	}

	public void setProducts(Collection<Producto> products) {
		this.products = products;
	}
}
