package com.servinow.android.QRSystem;

class QRResult {

	private Integer placeID;
	private Integer restaurantID;
	
	public QRResult(int restaurantID , int placeID) {
		this.restaurantID = restaurantID;
		this.placeID = placeID;
	}

	public Integer getPlaceID() {
		return placeID;
	}

	public Integer getRestaurantID() {
		return restaurantID;
	}

}
