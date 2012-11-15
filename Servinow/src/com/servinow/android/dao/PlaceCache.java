package com.servinow.android.dao;

import java.util.Collection;

import android.content.Context;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.servinow.android.domain.Categoria;
import com.servinow.android.domain.Place;
import com.servinow.android.domain.Restaurant;

public class PlaceCache extends ServinowDAOBase<Place, Integer> {

	public PlaceCache(Context context){
		super(context, Place.class);
	}
	
	public Place getPlaceFromCache(int placeID){
		RuntimeExceptionDao<Place, Integer> placeDAO = getDAO();
		Place place = placeDAO.queryForId(placeID);
		return place;
	}

	public void setPlaceCache(Place place){
		RuntimeExceptionDao<Place, Integer> placeDAO = getDAO();
		placeDAO.createOrUpdate(place);
	}
	
	void setPlacesCache(Restaurant restaurant , Collection<Place> placeList){
		RuntimeExceptionDao<Place, Integer> placeDAO = getDAO();
		
		for(Place place: placeList){
			place.restaurant = restaurant;
			placeDAO.createOrUpdate(place);
		}
	}

}