package com.servinow.android.QRSystem;

public interface QRResultCallback {
	public void onAnswer(int restaurantID, int placeID );
	public void onBadCode();
	public void onStartSync();
}
