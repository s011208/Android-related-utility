
package com.yenhsun.fscanner;

import java.io.File;
import java.util.ArrayList;

import android.os.Environment;
import android.util.Log;

public class Scanner {

    private static final String TAG = "com.yenhsun.fscanner.Scanner";

    private static final boolean DEBUG = true;

    private static final String[] ROOTs = new String[] {
            Environment.getExternalStorageDirectory().getAbsolutePath(), "/storage/extSdCard" /*
                                                                                               * for
                                                                                               * samsung
                                                                                               * n7000
                                                                                               */
    };

    private long timer = 0;

    private int mFileCounter = 0;

    private int mDirectoryCounter = 0;

    private String targetString;

    private ArrayList<String> mResults;

    private boolean keepScan = true;

    private String report;

    public String getReport() {
        return report;
    }

    public Scanner() {
        mResults = new ArrayList<String>();
    }

    public void setTargetString(String targetString) {
        this.targetString = targetString;
    }

    public String[] scan() {
        mFileCounter = 0;
        mDirectoryCounter = 0;
        mResults.clear();
        keepScan = true;
        timer = System.currentTimeMillis();
        for (String root : ROOTs)
            if (new File(root).exists()) {
                scan(root);
            }
        timer = System.currentTimeMillis() - timer;
        if (DEBUG) {
            Log.e(TAG, "time: " + timer + ", dirs : " + mDirectoryCounter + ", files : "
                    + mFileCounter);
            Log.i(TAG, "Find " + mResults.size() + " files.");
        }
        report = " time: " + timer + "\n total parsing dirs : " + mDirectoryCounter
                + "\n total parsing files : " + mFileCounter + "\n get files : " + mResults.size();

        return mResults.toArray(new String[mResults.size()]);
    }

    public boolean isScanning() {
        return keepScan;
    }

    public void stopScan() {
        keepScan = false;
    }

    private void scan(final String directory) {
        if (keepScan == false)
            return;
        File root = new File(directory);

        File[] files = root.listFiles();
        if (files != null)
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    ++mDirectoryCounter;
                    scan(files[i].getAbsolutePath());
                } else {
                    ++mFileCounter;
                    if (files[i].getName().toLowerCase().contains(targetString))
                        mResults.add(files[i].getAbsolutePath());
                }
            }
    }
}
