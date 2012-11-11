package com.servinow.android.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.servinow.android.domain.Categoria;
import com.servinow.android.domain.LineaPedido;
import com.servinow.android.domain.Pedido;
import com.servinow.android.domain.Place;
import com.servinow.android.domain.Producto;
import com.servinow.android.domain.Restaurant;

/*
 * Code based from example: http://ormlite.com/android/examples/
 * http://ormlite.com/sqlite_java_android_orm.shtml
 * */
public class ServinowDatabase extends OrmLiteSqliteOpenHelper {

	// name of the database file for your application
	private static final String DATABASE_NAME = "servinowDatabase.db";
	// any time you make changes to your database SCHEMA, you may have to increase the database version
	private static final int DATABASE_VERSION = 1;

	public ServinowDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION/*, R.raw.ormlite_config ¿?*/);
	}

	/**
	 * This is called when the database is first created. Usually you should call createTable statements here to create
	 * the tables that will store your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Restaurant.class);
			TableUtils.createTable(connectionSource, Place.class);
			TableUtils.createTable(connectionSource, Categoria.class);
			TableUtils.createTable(connectionSource, Producto.class);
			TableUtils.createTable(connectionSource, Pedido.class);
			TableUtils.createTable(connectionSource, LineaPedido.class);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
	 * the various data to match the new version number.
	 * 
	 * ...BUT in development you HAVE TO delete the database file manually, not increase the version number.
	 * To remove database db files go to DDMS.
	 * Go to the tab called "File Explorer" from the device you want to clean.
	 * Go to /data/data and in our case ./com.servinow.android/databases
	 * Then delete the databases files: select one file per delete and press the "remove" button from up-right (a red minus icon).
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
//		try {
//			/*TODO TableUtils.dropTable(connectionSource, Categoria.class, true);
//			TableUtils.dropTable(connectionSource, Pedido.class, true);
//			TableUtils.dropTable(connectionSource, LineaPedido.class, true);
//			
//			TableUtils.dropTable(connectionSource, Place.class, true);
//			TableUtils.dropTable(connectionSource, Restaurant.class, true);*/
//
//			//After drop old databases then we create the new ones.
//			onCreate(db, connectionSource);
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}
	}
}
