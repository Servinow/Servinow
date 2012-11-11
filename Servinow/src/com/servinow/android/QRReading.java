package com.servinow.android;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.servinow.android.QRSystem.QRReadingSystem;
import com.servinow.android.QRSystem.QRResultCallback;
import com.servinow.android.startRestaurantSystem.CacheRestaurantSystem;

public class QRReading extends SherlockActivity implements QRResultCallback{

	private QRReadingSystem qrReadingSystem;
	private CacheRestaurantSystem startRestDEBUG;
	
	//Remove me in the final product START
	public static enum PARAM {
		GOTORESTAURANT
	}
	//Remove me ENDS.

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Remove me in the final product START
		Bundle extras = getIntent().getExtras();
		if(extras != null && extras.getBoolean(PARAM.GOTORESTAURANT.toString(), false)) {
			onAnswer(1, 1);
			startRestDEBUG = new CacheRestaurantSystem(this, 1, 1, this);
			return;
		}//Remove me ENDS.
		
		setContentView(R.layout.qrreading);

		/**
		 * The activity have to be in portrait mode all time.
		 * */
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		FrameLayout cameraContainer = (FrameLayout) findViewById(R.id.QRReading_cameraPreview);
		qrReadingSystem = new QRReadingSystem(cameraContainer, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if(qrReadingSystem != null)
			qrReadingSystem.releaseCamera();
		
		if(startRestDEBUG != null)
			startRestDEBUG.stop();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(qrReadingSystem != null)
			qrReadingSystem.start();
		
		if(startRestDEBUG != null)
			startRestDEBUG.start();
	}

	//http://stackoverflow.com/questions/10407159/android-how-to-manage-start-activity-for-result
	@Override
	public void onAnswer(int restaurantID, int placeID) {
		Toast.makeText(this, "Aquí se debe abrir la actividad de listado de categorías", Toast.LENGTH_LONG).show();
		//Pasar por parámetro el restaurante y la mesa.
		//finish(); Uncomment me after execute the category activity.
	}

	@Override
	public void onBadCode() {
		Toast.makeText(this, "QR erróneo", Toast.LENGTH_LONG).show();
		
		qrReadingSystem.releaseCamera();
		qrReadingSystem.start();
	}
}
