package com.servinow.android.startRestaurantSystem;

import java.io.IOException;

import com.servinow.android.synchronization.ServinowApi_GetRestaurant;

import android.os.AsyncTask;

public class CallForRestaurant extends AsyncTask<Void, Void, Boolean> {

	private final int restaurantID;
	private final int placeID;

	public CallForRestaurant(int restaurantID, int placeID) {
		this.restaurantID = restaurantID;
		this.placeID = placeID;
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		ServinowApi_GetRestaurant callToInternet = new ServinowApi_GetRestaurant(restaurantID, placeID);
		try {
			callToInternet.call();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
	}
}
