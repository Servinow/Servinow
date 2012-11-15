package com.servinow.android.domain;

import java.util.Collection;

import android.graphics.drawable.Drawable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/*
 * TODO ONLY getters.
 * TODO Remove what is deprecated. I maintain it for compatibility with "old" code.
 * Everyone has to use the data stored in the DB and not create your own examples entities.
 **/
@DatabaseTable(tableName = "product")
public class Producto {

	@DatabaseField(id = true)
	private int id;
	
	@DatabaseField(canBeNull = false)
	private String nombre;
	
	@Deprecated
	@DatabaseField(canBeNull = true)
	private String urlImage;

	@DatabaseField(canBeNull = false)
	private String imageName;
	
	//TODO remove.
	@Deprecated
	private Drawable imagen;
	
	@DatabaseField(canBeNull = true)
	private String descripcion;
	
	@DatabaseField(canBeNull = false)
	private double precio;
	
	@DatabaseField(canBeNull = false)
	private boolean disponible;
	
	@DatabaseField(canBeNull = false)
	private TipoProducto tipo;
	
	@ForeignCollectionField(eager = true)
	private Collection<Producto> meals;
	
	// Requerido por ORMLite - No getter or setter for this.
	@DatabaseField(foreign=true, foreignAutoCreate=true, foreignAutoRefresh=true)
	public Producto product;
	
	// Requerido por ORMLite - No getter or setter for this.
	@DatabaseField(foreign=true, foreignAutoCreate=true, foreignAutoRefresh=true)
	public Categoria category;
	
	// Requerido por ORMLite - No getter or setter for this.
	@DatabaseField(foreign=true, foreignAutoCreate=true, foreignAutoRefresh=true)
	public Restaurant restaurant;
	
	public Producto() {
	}

	//TODO remove.
	@Deprecated
	public Producto(String nombre, Drawable imagen, String descripcion,
			double precio, boolean disponible) {
		super();
		this.nombre = nombre;
		this.imagen = imagen;
		this.descripcion = descripcion;
		this.precio = precio;
		this.disponible = disponible;
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
	
	@Deprecated
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

	public Drawable getImagen() {
		return imagen;
	}

	@Deprecated
	public void setImagen(Drawable imagen) {
		this.imagen = imagen;
	}

	public double getPrecio() {
		return precio;
	}

	@Deprecated
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	@Deprecated
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isDisponible() {
		return disponible;
	}

	@Deprecated
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public TipoProducto getTipo() {
		return tipo;
	}

	@Deprecated
	public void setTipo(TipoProducto tipo) {
		this.tipo = tipo;
	}

	public Collection<Producto> getMeals() {
		return meals;
	}

	@Deprecated
	public void setMeals(Collection<Producto> meals) {
		this.meals = meals;
	}
	
	public Categoria getCategoria(){
		return category;
	}
}
