package com.servinow.android.dao;

import android.content.Context;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.servinow.android.domain.Place;

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
}