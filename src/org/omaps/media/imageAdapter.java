package org.omaps.media;

import org.omaps.view.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class imageAdapter extends BaseAdapter {

	private Activity activity;
	private String[] data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;

	public imageAdapter(Activity a, String[] d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	public int getCount() {
		return data.length;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (convertView == null) {
			v = inflater.inflate(R.layout.listgallery, null);
		}
		ImageView image = (ImageView) v.findViewById(R.id.imageTumbl);
		imageLoader.DisplayImage(data[position], image);
		return v;
	}
}
