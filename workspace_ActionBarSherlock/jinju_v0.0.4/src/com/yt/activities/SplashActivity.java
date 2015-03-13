package com.yt.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;

import com.startapp.android.publish.StartAppSDK;
import com.wecandoit.jinju_0_0_4.*;
import com.wecandoit.jinju_mech_lib.ClientThread;
import com.wecandoit.jinju_mech_lib.jG;
import com.yt.common.utils.Utils;

public class SplashActivity extends Activity {

	private final String TAG = "jinju_v0.0.4::SplashActivity";
	private boolean networkCheck = false;
	private static SplashTimer timer;
	private final static int TIMER_INTERVAL = 2000; // 2 sec

	private boolean activityStarted;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		/*
		String ip = "logio.test.clipeo.com", port = "28177";
		jG.Log = new ClientThread("jinju", ip, port);
		new Thread(jG.Log).start();
		Log.d(TAG, "jG.Log is start!!");
*/
		

		setContentView(R.layout.yt_layout_splash);
		YTSDKUtils.initilizeYTSDK(this);
		networkCheck = Utils.isNetworkAvailable(this);

		// Initilize StartApp
		StartAppSDK.init(this, getString(R.string.startapp_developer_id),
				getString(R.string.startapp_app_id), true);

		if (!networkCheck) {
			Utils.showConnectivityErrorDialog(this);
		} else {
			jG.Log.d(TAG, "startHomePageActivity");
			
			 startHomePageActivity();
			//timer = new SplashTimer();
			//timer.sendEmptyMessageDelayed(1, TIMER_INTERVAL);
		}
		Utils.loadFullScreenAd(this);
	}

	private void startHomePageActivity() {

		jG.Log.d("startHomePageActivity");
		if (activityStarted) {
			return;
		}
		activityStarted = true;

		SplashActivity.this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				startActivity(new Intent(SplashActivity.this,
						HomePageActivity.class));
				finish();
			}
		});

	}

	final class SplashTimer extends Handler {
		@Override
		public void handleMessage(Message msg) {
			post(new Runnable() {

				public void run() {
					timer = null;
					startHomePageActivity();
				}
			});
		}
	}

}