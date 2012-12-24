package org.omaps.camspy;

import org.json.JSONException;
import org.json.JSONObject;
import org.omaps.config.SpyConfig;
import org.omaps.connection.HTTPCon;
import org.omaps.data.SpyData;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {

	public GCMIntentService() {
		super(SpyConfig.SPY_SENDER_ID);
	}

	@Override
	protected void onRegistered(Context arg0, String registrationId) {
		Log.i(SpyConfig.SPY_LOGGING, "Device registered: regId = " + registrationId);
		HTTPCon.getRequest("http://" + SpyData.getData().getBaseServer() + "/get_registerid_device.php?device_id=" + registrationId);
	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		Log.i(SpyConfig.SPY_LOGGING, "unregistered = " + arg1);
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		String message = intent.getExtras().getString("message");
		Log.i(SpyConfig.SPY_LOGGING, "new message= " + message);
		generateNotification(context, message);
	}

	@Override
	protected void onError(Context arg0, String errorId) {
		Log.i(SpyConfig.SPY_LOGGING, "Received error: " + errorId);
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		return super.onRecoverableError(context, errorId);
	}

	private static void generateNotification(Context context, String message) {
		JSONObject obj;
		try {
			obj = new JSONObject(message);
			String camera = obj.getString("camera");
			String notif = obj.getString("notif");

			String getMessage = "New Record(" + notif + ") from Camera " + camera;

			int icon = R.drawable.notif_logo;
			long when = System.currentTimeMillis();
			NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			Notification notification = new Notification(icon, getMessage, when);
			String title = context.getString(R.string.app_name);

			SpyData.getData().setCameraType(camera);
			SpyData.getData().setListVideo(camera);
			Intent notificationIntent = new Intent(context, ListGallery.class);
			// set intent so it does not start a new activity
			notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
			PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
			notification.setLatestEventInfo(context, title, getMessage, intent);
			notification.flags |= Notification.FLAG_AUTO_CANCEL;

			// Play default notification sound
			notification.defaults |= Notification.DEFAULT_SOUND;

			// Vibrate if vibrate is enabled
			notification.defaults |= Notification.DEFAULT_VIBRATE;
			notificationManager.notify(0, notification);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
