package org.omaps.camspy;

import org.omaps.config.SpyConfig;
import org.omaps.data.SpyData;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class VideoPlay extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String fileNameVideo = SpyData.getData().getFileNameVideo();
		Log.i(SpyConfig.SPY_LOGGING, "File Video = " + fileNameVideo);
		String video_uri = "http://" + SpyData.getData().getBaseServer() + "/data/video/" + SpyData.getData().getCameraType() + "/" + fileNameVideo;
		Intent intent = new Intent(this, VideoSample.class);
		intent.putExtra("video_path", video_uri);
		startActivity(intent);
	}
}
