package com.servinow.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.servinow.android.Util.Util;

public class MainActivity extends SherlockActivity {	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainactivity); /*A esta actividad/ventana le pones este layout*/

		ImageButton qrReadingButton = (ImageButton) findViewById(R.id.mainactivity_qrreading);
		qrReadingButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!Util.isOnline(MainActivity.this)) {
					Toast.makeText(MainActivity.this, "Se requiere conxión a internet.", Toast.LENGTH_LONG).show();
					return;
				}
				
				startActivity(new Intent(MainActivity.this, QRReading.class));
				
			}
		});

		findViewById(R.id.mainactivity_gotomap).setEnabled(false);
	}
}
