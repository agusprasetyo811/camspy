package org.omaps.view;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.omaps.data.SpyData;
import org.omaps.media.SpyListGallery;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class OnlineCamera extends ListActivity {

	VideoPlay vp = new VideoPlay();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();

		if (!SpyListGallery.getSpyDataCameraOnline().equals("ERROR_CONN")) {
			try {
				JSONArray spyData = new JSONArray(SpyListGallery.getSpyDataCameraOnline());
				for (int j = 0; j < spyData.length(); j++) {
					String getData = spyData.getJSONObject(j).getString("file");

					HashMap<String, String> map = new HashMap<String, String>();
					map.put("File", getData);
					mylist.add(map);
				}

				// Generate List
				ListAdapter adapter = new SimpleAdapter(this, mylist, R.layout.listcamera, new String[] { "File" }, new int[] { R.id.filename });
				setListAdapter(adapter);

				final ListView lv = getListView();
				lv.setTextFilterEnabled(true);
				lv.setOnItemClickListener(new OnItemClickListener() {

					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						@SuppressWarnings("unchecked")
						HashMap<String, String> spyData = (HashMap<String, String>) lv.getItemAtPosition(position);
						Toast.makeText(OnlineCamera.this, "Camera " + spyData.get("File"), Toast.LENGTH_SHORT).show();
						SpyData.getData().setCameraType(spyData.get("File"));
						SpyData.getData().setListVideo(spyData.get("File"));
						Intent i = new Intent(getApplicationContext(), ListGallery.class);
						startActivity(i);
						finish();
					}
				});

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Toast.makeText(OnlineCamera.this, "Koneksi Internet Tidak Ada!!!", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i = new Intent(getApplicationContext(), CamspyMenu.class);
		startActivity(i);
	}
}