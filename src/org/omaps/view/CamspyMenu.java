package org.omaps.view;

import org.json.JSONArray;
import org.json.JSONException;
import org.omaps.config.SpyConfig;
import org.omaps.data.SpyData;
import org.omaps.model.ConfigModel;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CamspyMenu extends Activity {

	ConfigModel model = new ConfigModel(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camspaymenu);

		// Set Configurasi Apakah sudah ada didalam database
		String data = model.show().toString().replace("/", "_");
		System.out.println(data);
		try {
			JSONArray myData = new JSONArray(data);
			if (myData.length() != 0) {
				for (int i = 0; i < myData.length(); i++) {
					SpyData.getData().setBaseServer(myData.getJSONObject(i).getString("base_server").replace("_", "/"));
				}
			} else {
				SpyData.getData().setBaseServer(SpyConfig.SPY_SERVER_URL);
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		final GridView gridView = (GridView) findViewById(R.id.gridViewBody);
		gridView.setAdapter(new ImageAdapter(this));
		gridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				if (gridView.getItemAtPosition(position).toString().equals("0")) {
					Toast.makeText(CamspyMenu.this, "Konfigurasi", Toast.LENGTH_SHORT).show();
					Intent i = new Intent(getApplicationContext(), Configuration.class);
					startActivity(i);
				} else if (gridView.getItemAtPosition(position).toString().equals("1")) {
					Toast.makeText(CamspyMenu.this, "CCTV", Toast.LENGTH_SHORT).show();
					Intent i = new Intent(getApplicationContext(), OnlineCamera.class);
					startActivity(i);
				} else if (gridView.getItemAtPosition(position).toString().equals("2")) {
					Toast.makeText(CamspyMenu.this, "Bye...", Toast.LENGTH_SHORT).show();
					// TODO Auto-generated method stub
					Intent intent = new Intent(Intent.ACTION_MAIN);
					intent.addCategory(Intent.CATEGORY_HOME);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
					finish();
				}
			}
		});
	}

	public class ImageAdapter extends BaseAdapter {

		private Context context;
		private Integer[] imagesID = { R.drawable.config, R.drawable.camera, R.drawable.exit };

		public ImageAdapter(Context c) {
			context = c;
		}

		public int getCount() {
			return imagesID.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if (convertView == null) {
				imageView = new ImageView(context);
				imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setPadding(8, 8, 8, 8);

			} else {
				imageView = (ImageView) convertView;
			}
			imageView.setImageResource(imagesID[position]);
			return imageView;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i = new Intent(getApplicationContext(), CamspyMenu.class);
		startActivity(i);
	}
}