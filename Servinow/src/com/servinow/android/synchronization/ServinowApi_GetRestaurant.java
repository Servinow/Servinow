package com.servinow.android.synchronization;

public class ServinowApi_GetRestaurant extends ServinowApi {

	public ServinowApi_GetRestaurant(int restaurantID, int placeID) {
		super("?restaurantid="+restaurantID+"&placeid="+placeID);
	}
}
