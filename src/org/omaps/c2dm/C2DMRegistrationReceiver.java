package org.omaps.c2dm;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.omaps.connection.HTTPCon;
import org.omaps.data.SpyData;
import org.omaps.view.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.util.Log;

public class C2DMRegistrationReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		Log.w("C2DM", "Registration Receiver called. Action : " + action);
		if ("com.google.android.c2dm.intent.REGISTRATION".equals(action)) {
			Log.w("C2DM", "Received registration ID");
			final String registrationId = intent.getStringExtra("registration_id");
			String error = intent.getStringExtra("error");

			Log.d("C2DM", "dmControl: registrationId = " + registrationId + ", error = " + error);
			String deviceId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
			createNotification(context, registrationId);
			sendRegistrationIdToServer(deviceId, registrationId);
		}
	}

	public void createNotification(Context context, String registrationId) {
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.icon, "Registration successful", System.currentTimeMillis());
		// Hide the notification after its selected
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		// Intent intent = new Intent(context,
		// RegistrationResultActivity.class);
		// intent.putExtra("registration_id", registrationId);
		// PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
		// intent, 0);
		// notification.setLatestEventInfo(context, "Registration",
		// "Successfully registered", pendingIntent);
		// notificationManager.notify(0, notification);
	}

	// private void saveRegistrationId(Context context, String registrationId) {
	// SharedPreferences prefs = PreferenceManager
	// .getDefaultSharedPreferences(context);
	// Editor edit = prefs.edit();
	// edit.putString(C2DMClientActivity.AUTH, registrationId);
	// edit.commit();
	// }

	public void sendRegistrationIdToServer(String deviceId, String registrationId) {
		Log.d("C2DM", "Sending registration ID to my application server");
		HTTPCon.getRequest("http://" + SpyData.getData().getBaseServer() + "/get_registerid_device.php?device_id=" + deviceId + "&register_id=" + registrationId);
	}
}
