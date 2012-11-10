package com.servinow.android;

import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.servinow.android.dao.MyEntityDAO;
import com.servinow.android.domain.ExampleEntity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends SherlockActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); /*A esta actividad/ventana le pones este layout*/

        /*
         * You instantiate the session with the database.
         * Try to store as a property to avoid a lot of reconnection in each activity session.
         * */
    	MyEntityDAO mydao = new MyEntityDAO(this);
    	List<ExampleEntity> exampleEntitiesList = mydao.getAllExampleEntities();
    	
    	Log.i("Data", ""+exampleEntitiesList.size());
    	
    	/*
    	 * Close the database session if you have ended with your database work.
    	 * Anyway I suggest do it in onPause() activity method
    	 * and instantiate another session in onResume() activity method.
    	 * That means that you have the DAO session opened during all activity's life cycle.
    	 * */
    	mydao.close();
    }
    
/*
 * Use this if you want to maintain in "cache" the session of a DAO of that object.
 * */
//    private MyEntityDAO mydao;
//    @Override
//	protected void onResume() {
//		super.onResume();
//		if(mydao==null || !mydao.isOpen())
//			mydao = new MyEntityDAO(this);
//	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { /*Al pulsar la tecla menú o al generarse "el menú" de la app*/
        
    	/*Use of getSUPPORTMenuInflater() INSTEAD of the API call getMenuInflater()
    	 * The first is the one for the ActionBarSherlock library.
    	 * The second if used for the oficial Android API but the API are ""fully"" compatible.
    	 * */
    	//test
    	getSupportMenuInflater().inflate(R.menu.activity_main, menu);/*Al "menú" correspondiente le pones este menú (activity_main)*/
        
    	
    	return true;
    }


}
