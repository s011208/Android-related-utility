
package com.yenhsun.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class TestService extends Service implements Runnable {
    private static final String TAG = "com.yenhsun.service.TestService";

    private volatile Handler mServiceHandler;

    public void onCreate() {
        super.onCreate();
        Thread thr = new Thread(null, this, "TestService");
        thr.start();
    }

    int counter = 0;

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, counter++ + "");

        while (mServiceHandler == null) {
            synchronized (this) {
                try {
                    wait(100);
                } catch (InterruptedException e) {
                }
            }
        }
        Message m1 = mServiceHandler.obtainMessage();
        m1.what = counter;
        mServiceHandler.sendMessage(m1);

        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        Log.e(TAG, "GO " + counter);
        Looper.prepare();
        mServiceHandler = new Handler() {
            public void handleMessage(final Message msg) {
                int sleep = (int)(Math.random() * 5000);
                Log.e(TAG, "what : " + msg.what + ", sleep time : " + sleep);
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "done!");
            }
        };
        Looper.loop();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}
