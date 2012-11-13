package com.servinow.android.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.servinow.android.domain.Categoria;
import com.servinow.android.domain.Restaurant;

public class CategoryCache extends ServinowDAOBase<Categoria, Integer> {

	public CategoryCache(Context context) {
		super(context, Categoria.class);
	}
	
	public List<Categoria> getCategories(Restaurant restaurant){
		RuntimeExceptionDao<Categoria, Integer> categoryDAO = getDAO();
		
		HashMap<String, Object> sqlFieldsToMatch = new HashMap<String, Object>();
		sqlFieldsToMatch.put("restaurant_id", restaurant);
		List<Categoria> categoryList = categoryDAO.queryForFieldValues(sqlFieldsToMatch);
		
		return categoryList;
	}

	public Categoria getCategoria(int categoria_id) {
		RuntimeExceptionDao<Categoria, Integer> categoryDAO = getDAO();
		Categoria categoria = categoryDAO.queryForId(categoria_id);
		
		return categoria;
	}
	
	void setCategoriesCache(Restaurant restaurant , Collection<Categoria> categoryList){
		RuntimeExceptionDao<Categoria, Integer> categoryDAO = getDAO();
		
		for(Categoria categoria: categoryList){
			categoria.restaurant = restaurant;
			categoryDAO.createOrUpdate(categoria);
		}
	}

}
