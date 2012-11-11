package com.servinow.android.dao;

import android.content.Context;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.servinow.android.domain.Restaurant;

public class RestaurantCache extends ServinowDAOBase {

	public RestaurantCache(Context context){
		super(context);
	}
	
	public Restaurant getRestaurantFromCache(int restaurantID){
		RuntimeExceptionDao<Restaurant, Integer> restaurantDAO = servinowDatabase.getRuntimeExceptionDao(Restaurant.class);
		Restaurant restaurant = restaurantDAO.queryForId(restaurantID);
		return restaurant;
	}

	public void setRestaurantCache(Restaurant restaurant){
		RuntimeExceptionDao<Restaurant, Integer> restaurantDAO = servinowDatabase.getRuntimeExceptionDao(Restaurant.class);
		restaurantDAO.createOrUpdate(restaurant);
		
		new CategoryCache(context).setCategoriesCache(restaurant.getCategories(), restaurant);
	}
}
