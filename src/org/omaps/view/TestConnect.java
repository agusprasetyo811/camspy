package org.omaps.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class TestConnect extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String video_uri = "http://project.cmlocator.com/camspay/data/testvid.mp4";
		Intent intent = new Intent(this, VideoSample.class);
		intent.putExtra("video_path", video_uri);
		startActivity(intent);
	}
}
