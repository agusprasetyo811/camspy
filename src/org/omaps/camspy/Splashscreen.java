package org.omaps.camspy;

import org.omaps.camspy.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class Splashscreen extends Activity {

	protected boolean status = true;
	protected int timer = 2000;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		Thread splashthread = new Thread() {
			@Override
			public void run() {
				try {
					int wait = 0;
					while (status && (wait < timer)) {
						sleep(100);
						if (status) {
							wait += 100;
						}
					}
				} catch (InterruptedException e) {
					
				} finally {
					Intent newIntent = new Intent(Splashscreen.this, CamspyMenu.class);
					startActivityForResult(newIntent, 0);
					finish();
				}
			}
		};
		splashthread.start();
	}

	public boolean onTouchEvent(MotionEvent e) {
		if (e.getAction() == MotionEvent.ACTION_DOWN) {
			status = false;
		}
		return true;
	}
}
