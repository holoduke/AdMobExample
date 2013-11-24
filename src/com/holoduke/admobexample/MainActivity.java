package com.holoduke.admobexample;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Display display = getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);

		float density = getResources().getDisplayMetrics().density;
		float dpWidth = outMetrics.widthPixels / density;

		AdView adView = new AdView(this);

		// here we take the users screen size. Simply counting pixels isn't a good idea since
		// the pixel density changes for different devices. 
		// for a full overview of the sizes look at
		// https://developers.google.com/mobile-ads-sdk/docs/admob/intermediate
		if (dpWidth >= 728) {
			adView.setAdSize(AdSize.LEADERBOARD);
		} else if (dpWidth >= 468) {
			adView.setAdSize(AdSize.FULL_BANNER);
		} else {
			adView.setAdSize(AdSize.BANNER);
		}
		//put here your ad id
		adView.setAdUnitId(getResources().getString(R.string.adid));
		adView.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		LinearLayout adContainer = (LinearLayout) findViewById(R.id.adViewContainer);
		adContainer.addView(adView);

		// on android 2.2 and lower devices we won't show ads. The new google play services is not supported there 
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (ConnectionResult.SUCCESS != resultCode) {
			Log.i("app", "no google play services available");
			return;
		}

		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				.addTestDevice("xxxxxx").build(); ///ad here the device id. Look at logcat to receive your id
		adView.loadAd(adRequest);
	}
}
