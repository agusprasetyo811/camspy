package org.omaps.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.omaps.config.SpyConfig;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ConfigModel {

	private DataBaseHelper dbh;
	private String[] SELECT_CONFIG = { "id_config", "base_server" };

	public ConfigModel(Context context) {
		dbh = new DataBaseHelper(context);
	}

	public void add(String base_server) {
		SQLiteDatabase db = dbh.getWritableDatabase();
		ContentValues datas = new ContentValues();
		datas.put("base_server", base_server);
		db.insertOrThrow(SpyConfig.SPY_CONFIG_TABLE, null, datas);
		Log.i(SpyConfig.SPY_LOGGING, "INSERT DATA");
	}
	
	public void update(String id_config, String base_server) {
		SQLiteDatabase db = dbh.getWritableDatabase();
		ContentValues datas = new ContentValues();
		datas.put("base_server", base_server);
		db.update(SpyConfig.SPY_CONFIG_TABLE, datas, "id_config="+id_config, null);
		Log.i(SpyConfig.SPY_LOGGING, "UPDATE DATA");
	}

	public ArrayList<HashMap<String, String>> show() {
		ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
		SQLiteDatabase db = dbh.getReadableDatabase();
		Cursor cursor = db.query(SpyConfig.SPY_CONFIG_TABLE, SELECT_CONFIG, null, null, null, null, null);
		while (cursor.moveToNext()) {
			HashMap<String, String> map = new HashMap<String, String>();
			
			String id_config = cursor.getString((cursor.getColumnIndex("id_config")));
			String base_server = cursor.getString((cursor.getColumnIndex("base_server")));
			
			map.put("id_config", id_config);
			map.put("base_server", base_server);
			mylist.add(map);
		}
		return mylist;
	}
	
	public void delete(Integer id_config) {
		SQLiteDatabase db = dbh.getWritableDatabase();
		db.delete(SpyConfig.SPY_CONFIG_TABLE, "id_config="+id_config, null);
	}
}