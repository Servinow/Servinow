package com.servinow.android.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.StatementBuilder.StatementType;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseResults;
import com.servinow.android.domain.Categoria;
import com.servinow.android.domain.Restaurant;

import android.content.Context;

public class CategoryCache extends ServinowDAOBase {

	public CategoryCache(Context context) {
		super(context);
	}
	
	public List<Categoria> getCategories(Restaurant restaurant){
		RuntimeExceptionDao<Categoria, Integer> categoryDAO = servinowDatabase.getRuntimeExceptionDao(Categoria.class);
		
		HashMap<String, Object> sqlFieldsToMatch = new HashMap<String, Object>();
		sqlFieldsToMatch.put("restaurant_id", restaurant);
		List<Categoria> categoryList = categoryDAO.queryForFieldValues(sqlFieldsToMatch);
		
		return categoryList;
	}
	
	void setCategoriesCache(Collection<Categoria> categoryList, Restaurant restaurant){
		RuntimeExceptionDao<Categoria, Integer> categoryDAO = servinowDatabase.getRuntimeExceptionDao(Categoria.class);
		
		for(Categoria categoria: categoryList){
			categoria.restaurant = restaurant;
			categoryDAO.createOrUpdate(categoria);
		}
		
		List<Categoria> catli = categoryDAO.queryForAll();
	}

}
