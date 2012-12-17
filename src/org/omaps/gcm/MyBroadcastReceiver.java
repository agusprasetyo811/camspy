package org.omaps.gcm;

import org.omaps.config.SpyConfig;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyBroadcastReceiver extends BroadcastReceiver {

	@Override
	public final void onReceive(Context context, Intent intent) {
		MyIntentService.runIntentInService(context, intent);
		setResult(Activity.RESULT_OK, null, null);
		Log.i(SpyConfig.SPY_LOGGING, "Result Register");
	}

}