package org.omaps.view;

import org.json.JSONArray;
import org.json.JSONException;
import org.omaps.config.SpyConfig;
import org.omaps.data.SpyData;
import org.omaps.model.ConfigModel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Camspy extends Activity {
	Button b1, b2, b3, b4;
	private final Context context = this;
	ConfigModel model = new ConfigModel(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set Configurasi Apakah sudah ada didalam database
		String data = model.show().toString().replace("/", "_");
		System.out.println(data);
		try {
			JSONArray myData = new JSONArray(data);
			if (myData.length() != 0) {
				for (int i = 0; i < myData.length(); i++) {
					SpyData.getData().setBaseServer(myData.getJSONObject(i).getString("base_server").replace("_", "/"));
					SpyData.getData().setCameraPath(myData.getJSONObject(i).getString("path_camera"));
					SpyData.getData().setVideoPath(myData.getJSONObject(i).getString("path_video"));
				}
			} else {
				SpyData.getData().setBaseServer(SpyConfig.SPY_SERVER_URL);
				SpyData.getData().setCameraPath(SpyConfig.SPY_CAMERA_PATH);
				SpyData.getData().setVideoPath(SpyConfig.SPY_VIDEO_PATH);
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		b1 = (Button) findViewById(R.id.btnadd);
		b1.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), Configuration.class);
				startActivity(i);
			}
		});

		b2 = (Button) findViewById(R.id.btnext);
		b2.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), VideoSample.class);
				startActivity(i);
			}
		});

		b3 = (Button) findViewById(R.id.btngal);
		b3.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), OnlineCamera.class);
				startActivity(i);
			}
		});

		b4 = (Button) findViewById(R.id.btnexit);
		b4.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setMessage("are you sure?");
				builder.setCancelable(false);
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(Intent.ACTION_MAIN);
						intent.addCategory(Intent.CATEGORY_HOME);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
						finish();
					}
				});
				builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
				builder.show();
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i = new Intent(getApplicationContext(), Camspy.class);
		startActivity(i);
	}
}