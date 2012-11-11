package com.servinow.android.dao;

import com.servinow.android.database.ServinowDatabase;

import android.content.Context;

public abstract class ServinowDAOBase {

	protected final Context context;
	protected final ServinowDatabase servinowDatabase;

	ServinowDAOBase(Context context){
		this.context = context;
		this.servinowDatabase = new ServinowDatabase(this.context);
	}
	
	public boolean isOpen(){
		return servinowDatabase.isOpen();
	}

	public void close(){
		servinowDatabase.close();
	}
}
