package org.omaps.media;

import org.json.JSONException;
import org.json.JSONObject;
import org.omaps.config.SpyConfig;
import org.omaps.connection.HTTPCon;
import org.omaps.data.SpyData;

import android.util.Log;

public class SpyListGallery {

	public static String getSpyDataCameraOnline() {
		String data = HTTPCon.getRequest("http://" + SpyData.getData().getBaseServer() + "/list_camera.php");
		if (data.equals("ERROR_CONN")) {
			return "ERROR_CONN";
		} else if (data.contains("Object not found!")) {
			return "ERROR_ADDR";
		} else {
			try {
				JSONObject spyData = new JSONObject(data);
				return spyData.getString("data");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String getSpyDataListVideo(String camera) {
		String data = HTTPCon.getRequest("http://" + SpyData.getData().getBaseServer() + "/data.php?camera=" + camera);
		if (data.equals("ERROR_CONN")) {
			return "ERROR_CONN";
		} else if (data.contains("Object not found!")) {
			return "ERROR_ADDR";
		} else {
			try {
				JSONObject spyData = new JSONObject(data);
				String spyFiles = spyData.getString("Files");
				JSONObject spyFilesData = new JSONObject(spyFiles);
				String spyFile = spyFilesData.getString("File");
				Log.i(SpyConfig.SPY_LOGGING, spyFile);
				if (!spyFile.equals("")) {
					JSONObject spyFilesFileData = new JSONObject(spyFile);
					return spyFilesFileData.getString("FilesFile");
				} else {
					return "ERROR_PARSING";
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
