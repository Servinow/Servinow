package com.servinow.android.dao;

import android.content.Context;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.servinow.android.database.ServinowDatabase;

public abstract class ServinowDAOBase<MainClass, ID> {

	protected final Context context;
	protected final ServinowDatabase servinowDatabase;
	private RuntimeExceptionDao<MainClass, ID> dao;
	private Class<MainClass> clazzMainClass;

	ServinowDAOBase(Context context, Class<MainClass> clazzMainClass){
		this.context = context;
		this.servinowDatabase = new ServinowDatabase(this.context);
		this.clazzMainClass = clazzMainClass;
	}
	
	public boolean isOpen(){
		return servinowDatabase.isOpen();
	}

	public void close(){
		servinowDatabase.close();
		dao = null;
	}
	
	protected RuntimeExceptionDao<MainClass, ID> getDAO() {
		if(dao == null)
			dao = servinowDatabase.getRuntimeExceptionDao(clazzMainClass);
		
		return dao;
	}
}
