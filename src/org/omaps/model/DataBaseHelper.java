package org.omaps.model;

import org.omaps.config.SpyConfig;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

	private static final String MYDATABASE = SpyConfig.SPY_DB_NAME;
	private static final int VERSION = 1;

	public DataBaseHelper(Context connection) {
		super(connection, MYDATABASE, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + SpyConfig.SPY_CONFIG_TABLE + "(id_config INTEGER PRIMARY KEY AUTOINCREMENT,  base_server TEXT, path_camera TEXT, path_video TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXIST "+ SpyConfig.SPY_CONFIG_TABLE);
		onCreate(db);
	}

}