package com.servinow.android.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "place")
public class Place {

	@DatabaseField(generatedId = true)
	private int onlineID;

	@DatabaseField(canBeNull = false)
	private long lastUpdate;

	@DatabaseField(foreign=true, foreignAutoCreate=true, foreignAutoRefresh=true)
	private Restaurant restaurant;

	public int getOnlineID() {
		return onlineID;
	}
	public long getLastUpdate() {
		return lastUpdate;
	}



	Place() {
	}
}
