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
	
	@Deprecated
	@DatabaseField(canBeNull = true)
	private String urlImage;
	
	@DatabaseField(canBeNull = false)
	private String imageName;
	
	@Deprecated
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

	@Deprecated
	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Deprecated
	public String getUrlImage() {
		return urlImage;
	}

	@Deprecated
	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	
	public String getImageName(){
		return this.imageName;
	}

	@Deprecated
	public Drawable getImagen() {
		return imagen;
	}

	@Deprecated
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
