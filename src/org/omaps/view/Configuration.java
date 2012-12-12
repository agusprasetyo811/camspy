package org.omaps.view;

import org.json.JSONArray;
import org.json.JSONException;
import org.omaps.config.SpyConfig;
import org.omaps.model.ConfigModel;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Configuration extends Activity {

	private EditText mServer;
	private Button ConfirmButton;
	ConfigModel configModel = new ConfigModel(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configuration);

		mServer = (EditText) findViewById(R.id.title);
		ConfirmButton = (Button) findViewById(R.id.btnLogin);

		// Menampilkan data di edit text
		String fillData = configModel.show().toString().replace("/", "_");
		try {
			JSONArray myData = new JSONArray(fillData);
			if (myData.length() != 0) {
				for (int i = 0; i < myData.length(); i++) {
					mServer.setText(myData.getJSONObject(i).getString("base_server").replace("_", "/"));
				}
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		ConfirmButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String data = configModel.show().toString().replace("/", "_");
				Log.i(SpyConfig.SPY_LOGGING, data);

				if (mServer.getText().toString().trim().equals("")) {
					Toast.makeText(Configuration.this, "Server Harus Ditentukan!!", Toast.LENGTH_SHORT).show();
				} else if (mServer.getText().toString().startsWith("http://") || mServer.getText().toString().contains("//")) {
					Toast.makeText(Configuration.this, "Karakter Illegal!!", Toast.LENGTH_SHORT).show();
				} else {
					try {
						JSONArray configData = new JSONArray(data);
						if (configData.length() != 0) {
							// Update Config
							String fillData = configModel.show().toString().replace("/", "_");
							try {
								JSONArray myData = new JSONArray(fillData);
								if (myData.length() != 0) {
									for (int i = 0; i < myData.length(); i++) {
										String id_config = myData.getJSONObject(i).getString("id_config");
										configModel.update(id_config, mServer.getText().toString());
									}
								}
							} catch (JSONException e1) {
								e1.printStackTrace();
							}
						} else {
							// Insert Config
							configModel.add(mServer.getText().toString());
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// Jika berhasil Install maka akan kembali ke home
					Toast.makeText(Configuration.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
					Intent i = new Intent(getApplicationContext(), CamspyMenu.class);
					startActivity(i);
					finish();
				}
			}
		});

		ImageButton ib = (ImageButton) findViewById(R.id.btnhome);
		ib.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), Camspy.class);
				startActivity(i);
			}
		});
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i = new Intent(getApplicationContext(), CamspyMenu.class);
		startActivity(i);
	}
}
