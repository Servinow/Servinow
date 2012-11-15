package com.servinow.android.domain;

import java.util.Collection;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "place")
public class Place {

	@DatabaseField(generatedId = true)
	private int onlineID;

	@DatabaseField(canBeNull = false)
	private long lastUpdate;
	
	@DatabaseField(foreign=true, foreignAutoCreate=true, foreignAutoRefresh=true)
	private Restaurant restaurant;
	
	@ForeignCollectionField(eager = true)
	private Collection<Pedido> orders;

	public int getOnlineID() {
		return onlineID;
	}
	public long getLastUpdate() {
		return lastUpdate;
	}
	
	public void setLastUpdate(long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public void setOnlineId(int onlineId) {
		this.onlineID = onlineId;
	}
	
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
	public Place() {
	}
}
