package com.yenhsun.keyguardtest;

import android.os.Bundle;
import android.os.PowerManager;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.view.Menu;

public class MainActivity extends Activity {
	private PowerManager pm;
	private PowerManager.WakeLock wakeLock;
	KeyguardManager km;
	KeyguardLock Keylock;
	boolean isScreenLock = false;

	public void getUnlock() {
		// acquire wake lock
		pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
				| PowerManager.ACQUIRE_CAUSES_WAKEUP
				| PowerManager.ON_AFTER_RELEASE, "SimpleTimer");
		wakeLock.acquire();
		// unlock screen
		km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
		Keylock = km.newKeyguardLock("");
		if (km.inKeyguardRestrictedInputMode()) {
			Keylock.disableKeyguard();
			isScreenLock = true;
		} else {
			isScreenLock = false;
		}
	}

	public void releaseUnlock() {
		// release screen
		if (isScreenLock) {
			Keylock.reenableKeyguard();
			isScreenLock = false;
		}
		// release wake lock
		if (wakeLock.isHeld()) {
			wakeLock.release();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getUnlock();
	}

	public void onDestroy() {
		releaseUnlock();
	}
}
