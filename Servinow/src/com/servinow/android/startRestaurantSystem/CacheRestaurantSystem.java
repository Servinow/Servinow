package com.servinow.android.startRestaurantSystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.servinow.android.dao.ProductCache;
import com.servinow.android.dao.RestaurantCache;
import com.servinow.android.domain.Categoria;
import com.servinow.android.domain.Restaurant;
import com.servinow.android.synchronization.ServinowApi_GetProductsByCategory;
import com.servinow.android.synchronization.ServinowApi_GetRestaurant;

public class CacheRestaurantSystem {

	private final int restaurantID;
	private final int placeID;
	private final Context context;
	private CallForRestaurantToCache callForRestaurantToCache;

	public CacheRestaurantSystem(Context context, int restaurantID, int placeID) {
		this.restaurantID = restaurantID;
		this.placeID = placeID;
		this.context = context;
	}
	
	public void start(){
		callForRestaurantToCache = new CallForRestaurantToCache();
		callForRestaurantToCache.execute();
	}
	
	public void stop(){
		callForRestaurantToCache.cancel(true);
	}
	
	private class CallForRestaurantToCache extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				ServinowApi_GetRestaurant callToInternet = new ServinowApi_GetRestaurant(restaurantID, placeID);
				String restaurantJson = callToInternet.call();
				
				Restaurant restaurant = new Gson().fromJson(restaurantJson, Restaurant.class);
				
				//Save restaurant, categories and products.
				RestaurantCache restaurantCache = new RestaurantCache(context);
				restaurantCache.setRestaurantCache(restaurant);
				restaurantCache.close();
				
				//Get products of the each category.
				Collection<Categoria> categoriesList = restaurant.getCategories();
				for(Categoria cat: categoriesList){
					String productsByCategoryJson = new ServinowApi_GetProductsByCategory(restaurantID, cat.getId()).call();
					
					TypeToken<ArrayList<Integer>> productIDsListType = new TypeToken<ArrayList<Integer>>(){};
					ArrayList<Integer> productIDsList = new Gson().fromJson(productsByCategoryJson, productIDsListType.getType());
					
					//Rel. all this products with this category.
					ProductCache productCache = new ProductCache(context);
					productCache.setProductCacheByCategory(cat, productIDsList);
					productCache.close();
				}
				
			}catch(JsonSyntaxException e){
				return false;
			} catch (IOException e) {
				return false;
			}
			return true;
		}
	}
}
