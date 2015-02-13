package com.vimeo.activities;

import vimeo.sdk.access.InitializationException;
import vimeo.sdk.access.YTSDK;
import android.app.Activity;
import android.util.Log;

public class VimeoSDKUtils {
	private static YTSDK ytsdk = null;

	public static void initilizeYTSDK(Activity activity) {
		try {
			if (ytsdk == null) {
				ytsdk = YTSDK.getInstance(activity);
			}
			ytsdk.setDownloadFolderPath("download");
		} catch (InitializationException e) {
			Log.d("YTSDK", "exception " + e.toString());
		} catch (Exception e) {
			Log.d("YTSDK", "exception " + e.toString());
		}
	}

	public static YTSDK getYTSDK() {		
		return ytsdk;
	}
}