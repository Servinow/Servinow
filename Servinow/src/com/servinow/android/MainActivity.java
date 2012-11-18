package com.servinow.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.actionbarsherlock.app.SherlockActivity;

public class MainActivity extends SherlockActivity {	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainactivity); /*A esta actividad/ventana le pones este layout*/

		ImageButton qrReadingButton = (ImageButton) findViewById(R.id.mainactivity_qrreading);
		qrReadingButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, QRReading.class));
				
			}
		});

		findViewById(R.id.mainactivity_gotomap).setEnabled(false);


		Button directToRestaurantButton = (Button) findViewById(R.id.mainactivity_directtorestaurant);
		directToRestaurantButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, QRReading.class);
				Bundle b = new Bundle();
				b.putBoolean(QRReading.PARAM.GOTORESTAURANT.toString(), true);
				i.putExtras(b);

				startActivity(i);
			}
		});
	}
}
