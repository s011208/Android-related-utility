
package com.yenhsun.fscanner;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

public class ScannerService extends Service implements Runnable {

    private static final String TAG = "com.yenhsun.fscanner.ScannerService";

    private static final boolean DEBUG = true;

    final RemoteCallbackList<IScannerCallback> mCallbacks = new RemoteCallbackList<IScannerCallback>();

    void sendResult(String[] results) {
        final int N = mCallbacks.beginBroadcast();
        for (int i = 0; i < N; i++) {
            try {
                mCallbacks.getBroadcastItem(i).done(results);
            } catch (RemoteException e) {
                // The RemoteCallbackList will take care of removing
                // the dead object for us.
            }
        }
        mCallbacks.finishBroadcast();
    }

    void sendReport(String report) {
        final int N = mCallbacks.beginBroadcast();
        for (int i = 0; i < N; i++) {
            try {
                mCallbacks.getBroadcastItem(i).report(report);
            } catch (RemoteException e) {
                // The RemoteCallbackList will take care of removing
                // the dead object for us.
            }
        }
        mCallbacks.finishBroadcast();
    }

    private Binder mBinder = new IScannerService.Stub() {

        @Override
        public boolean isTaskRunning() throws RemoteException {
            if (DEBUG)
                Log.e(TAG, "isTaskRunning");
            return mScanner.isScanning();
        }

        @Override
        public void stopRunningTask() throws RemoteException {
            if (DEBUG)
                Log.e(TAG, "stopRunningTask");
            mScanner.stopScan();
        }

        @Override
        public void registerCallback(IScannerCallback cb) throws RemoteException {
            if (DEBUG)
                Log.e(TAG, "registerCallback");
            if (cb != null)
                mCallbacks.register(cb);
        }

        @Override
        public void unregisterCallback(IScannerCallback cb) throws RemoteException {
            if (DEBUG)
                Log.e(TAG, "unregisterCallback");
            if (cb != null)
                mCallbacks.unregister(cb);
        }

        @Override
        public void requestScanService(String target) throws RemoteException {
            Intent intent = new Intent(ScannerService.this, ScannerService.class);
            intent.putExtra(EXTRA_TARGET_STRING, target);
            startService(intent);
        }
    };

    private Scanner mScanner = new Scanner();

    public static final String EXTRA_TARGET_STRING = "extra_target_string";

    private static final int MSG_SCAN = 1;

    private volatile Handler mServiceHandler;

    public void onCreate() {
        super.onCreate();
        if (DEBUG)
            Log.e(TAG, "START SCANNER SERVICE");
        Thread thr = new Thread(null, this, "ScannerService");
        thr.start();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        while (mServiceHandler == null) {
            synchronized (this) {
                try {
                    wait(100);
                } catch (InterruptedException e) {
                }
            }
        }
        Message m1 = mServiceHandler.obtainMessage();
        m1.obj = intent.getStringExtra(EXTRA_TARGET_STRING);
        m1.what = MSG_SCAN;
        mServiceHandler.sendMessage(m1);

        Log.e(TAG, "STRING : " + m1.obj);

        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void run() {
        Looper.prepare();
        mServiceHandler = new Handler() {
            public void handleMessage(final Message msg) {
                String scanPath = (String)msg.obj;
                Log.e(TAG, "GET MSG with path : " + scanPath);
                if (scanPath != null && "".equals(scanPath) == false) {
                    if (msg.what == MSG_SCAN) {

                        mScanner.setTargetString(scanPath);
                        String[] results = mScanner.scan();
                        sendResult(results);
                        sendReport(mScanner.getReport());
                    }
                }
            }
        };
        Looper.loop();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return mBinder;
    }

}
