package org.omaps.media;

import java.io.InputStream;
import java.io.OutputStream;

public class Utils {

	public static String durationInSecondsToString(int sec) {
		int hours = sec / 3600;
		int minutes = (sec / 60) - (hours * 60);
		int seconds = sec - (hours * 3600) - (minutes * 60);
		String formatted = String.format("%d:%02d:%02d", hours, minutes, seconds);
		return formatted;
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}
}
