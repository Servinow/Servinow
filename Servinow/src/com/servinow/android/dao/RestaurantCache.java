package com.servinow.android.dao;

import android.content.Context;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.servinow.android.domain.Restaurant;

public class RestaurantCache extends ServinowDAOBase<Restaurant, Integer> {

	public RestaurantCache(Context context){
		super(context, Restaurant.class);
	}
	
	public Restaurant getRestaurantFromCache(int restaurantID){
		RuntimeExceptionDao<Restaurant, Integer> restaurantDAO = getDAO();
		Restaurant restaurant = restaurantDAO.queryForId(restaurantID);
		return restaurant;
	}

	public void setRestaurantCache(Restaurant restaurant){
		RuntimeExceptionDao<Restaurant, Integer> restaurantDAO = getDAO();
		restaurantDAO.createOrUpdate(restaurant);
		
		new PlaceCache(context).setPlacesCache(restaurant, restaurant.getPlaces());
		new CategoryCache(context).setCategoriesCache(restaurant, restaurant.getCategories());
		new ProductCache(context).setProductCache(restaurant, restaurant.getProducts());
	}
}
