package com.servinow.android.domain;

import java.util.Collection;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "restaurant")
public class Restaurant {
	
	@DatabaseField(id = true)
	private int onlineID;

	@DatabaseField(canBeNull = false)
	private long lastUpdate;
	
	@DatabaseField(canBeNull = false)
	private String name;
	
	@ForeignCollectionField(eager = true) //lazy = false
	private Collection<Place> places;
	
	@ForeignCollectionField(eager = true)
	private Collection<Producto> products;
	
	@ForeignCollectionField(eager = true)
	private Collection<Pedido> orders;
	
	@ForeignCollectionField(eager = true)
	private Collection<Categoria> categories;


	public int getOnlineID() {
		return onlineID;
	}
	public long getLastUpdate() {
		return lastUpdate;
	}
	public String getName() {
		return name;
	}
	public Collection<Place> getPlaces() {
		return places;
	}
	public Collection<Producto> getProducts() {
		return products;
	}
	public Collection<Pedido> getOrders() {
		return orders;
	}
	public Collection<Categoria> getCategories() {
		return categories;
	}
	Restaurant() {
	}
}
