package org.omaps.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.UnknownHostException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class HTTPCon {

	private static final String DEB_TAG = "Nevz_Android";

	public HTTPCon() {
		// TODO Auto-generated constructor stub
	}

	// Method untuk mengirim data ke server dengan method get
	public static String getRequest(String url) {
		Log.i(DEB_TAG, url);
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		String getResponse = null;
		try {
			HttpResponse response = client.execute(request);
			getResponse = request(response).toString();
			Log.i(DEB_TAG, "Conneced");
		} catch (UnknownHostException e) {
			System.out.println("Url Not Found");
		} catch (ConnectException e) {
			System.out.println("Time Out or Denied");
		} catch (Exception e) {
			getResponse = "ERROR_CONN";
			System.out.println("Failed Connect to Server");
		}
		return getResponse;
	}

	// Method untuk menerima data dari server
	public static String request(HttpResponse response) {
		String result = "";
		try {
			InputStream is = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder str = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				str.append(line + "\n");
				Log.i(DEB_TAG, line);
			}
			is.close();
			result = str.toString();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
