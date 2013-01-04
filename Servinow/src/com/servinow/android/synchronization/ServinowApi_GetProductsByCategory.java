package com.servinow.android.synchronization;

public class ServinowApi_GetProductsByCategory extends ServinowApi {

	public ServinowApi_GetProductsByCategory(int restaurantID, int categoryID) {
		super("/" + restaurantID + "/api/categoria?categoryid="+categoryID);
	}

}
