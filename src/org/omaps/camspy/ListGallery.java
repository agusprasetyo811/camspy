package org.omaps.camspy;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.omaps.config.SpyConfig;
import org.omaps.data.SpyData;
import org.omaps.media.SpyListGallery;
import org.omaps.camspy.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListGallery extends ListActivity {

	VideoPlay vp = new VideoPlay();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
		String camera = SpyData.getData().getListVideo();
		if (SpyListGallery.getSpyDataListVideo(camera).equals("ERROR_CONN")) {
			Toast.makeText(ListGallery.this, "Koneksi Internet Tidak Ada!!!", Toast.LENGTH_SHORT).show();
		} else if (SpyListGallery.getSpyDataListVideo(camera).equals("ERROR_PARSING")) {
			Toast.makeText(ListGallery.this, "Data Masih Kosong!!", Toast.LENGTH_SHORT).show();
		} else if (SpyListGallery.getSpyDataListVideo(camera).equals("ERROR_ADDR")) {
			Toast.makeText(ListGallery.this, "Server Salah. Coba Cek Konfigurasi kembali!!!", Toast.LENGTH_SHORT).show();
		} else {
			try {
				JSONArray spyData = new JSONArray(SpyListGallery.getSpyDataListVideo(camera));
				for (int j = 0; j < spyData.length(); j++) {
					String getData = spyData.getJSONObject(j).getString("@attributes");
					JSONObject data = new JSONObject(getData);

					HashMap<String, String> map = new HashMap<String, String>();
					map.put("Filename", data.getString("Filename"));
					map.put("SizeBytes", data.getString("SizeBytes"));
					map.put("DurationSeconds", "Durasi " + data.getString("DurationSeconds") + " Detik");

					Log.i(SpyConfig.SPY_LOGGING, "File Name = " + data.getString("Filename"));
					Log.i(SpyConfig.SPY_LOGGING, "File Size = " + data.getString("SizeBytes"));
					Log.i(SpyConfig.SPY_LOGGING, "File Time = " + data.getString("DurationSeconds"));

					mylist.add(map);
				}

				// Generate List
				ListAdapter adapter = new SimpleAdapter(this, mylist, R.layout.listgallery, new String[] { "Filename", "DurationSeconds" }, new int[] { R.id.filename, R.id.duration });
				setListAdapter(adapter);

				final ListView lv = getListView();
				lv.setTextFilterEnabled(true);
				lv.setOnItemClickListener(new OnItemClickListener() {

					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						@SuppressWarnings("unchecked")
						HashMap<String, String> spyData = (HashMap<String, String>) lv.getItemAtPosition(position);
						Toast.makeText(ListGallery.this, "Lihat Rekamanan " + spyData.get("Filename"), Toast.LENGTH_SHORT).show();
						SpyData.getData().setFileNameVideo(spyData.get("Filename"));
						Intent i = new Intent(getApplicationContext(), VideoPlay.class);
						startActivity(i);
					}
				});
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i = new Intent(getApplicationContext(), OnlineCamera.class);
		startActivity(i);
	}
}
