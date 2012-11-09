package com.servinow.android.dao;

import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.servinow.android.database.ExampleDatabase;
import com.servinow.android.domain.ExampleEntity;

public class MyEntityDAO {

	private final Context context;
	private final ExampleDatabase exampleDatabase;

	public MyEntityDAO(Context context){
		this.context = context;
		this.exampleDatabase = new ExampleDatabase(this.context);
	}
	
	public boolean isOpen(){
		return exampleDatabase.isOpen();
	}

	public void close(){
		exampleDatabase.close();
	}

	/**
	 * Obtains the ExampleEntity by its ID.
	 * @param exampleEntityID ExampleEntity ID.
	 * */
	public ExampleEntity getExampleEntity(int exampleEntityID){
		/*Dao generated for this entity WIHOUT throw exeption.*/
		RuntimeExceptionDao<ExampleEntity, Integer> exampleEntityDao = exampleDatabase.getRuntimeExceptionDao(ExampleEntity.class);
		
		//Same DAO as before but with throw exception that have to be catched.
		//Dao<ExampleEntity, Integer> exampleEntityDao = exampleDatabase.getDao(ExampleEntity.class);
		
		ExampleEntity ee = exampleEntityDao.queryForId(exampleEntityID);

		return ee;
	}
	
	/**
	 * Return all ExampleEntities objects stored in the Database.
	 * */
	public List<ExampleEntity> getAllExampleEntities(){
		RuntimeExceptionDao<ExampleEntity, Integer> exampleEntityDao = exampleDatabase.getRuntimeExceptionDao(ExampleEntity.class);
		List<ExampleEntity> ee = exampleEntityDao.queryForAll();
		
		return ee;
	}
}
