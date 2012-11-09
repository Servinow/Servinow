package com.servinow.android.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "exampleEntity")
public class ExampleEntity {
	
	@DatabaseField(generatedId = true) //AUTOMINCREMENT
	private int offlineID; //offline ID for the local version of the object. You would use this "ever".
	
	@DatabaseField(canBeNull = true)
	private Integer onlineID; //onlineID for the synchronised version of the object (if it is)
	
	@DatabaseField(canBeNull = false)
	public String aProperty;
	
	/*
	 * This HAVE TO EXISTS for ORMLiteAndroid
	 * */
	protected ExampleEntity(){}

	public ExampleEntity(String aProperty) {
		this.aProperty = aProperty;
	}

	public int getOfflineID() {
		return offlineID;
	}

	public Integer getOnlineID() {
		return onlineID;
	}
}
