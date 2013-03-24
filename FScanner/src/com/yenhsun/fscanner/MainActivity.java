
package com.yenhsun.fscanner;

import java.io.File;
import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements Button.OnClickListener {

    private static final String TAG = "com.yenhsun.fscanner.MainActivity";

    private TextView mTxtShowReport;

    private Button mBtnSearch;

    private LinearLayout mResult_l;

    private EditText mEdtSearchBox;

    private String searchText;

    private IScannerService mService;

    private boolean isScanning = false;

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = IScannerService.Stub.asInterface(service);
            try {
                mService.registerCallback(mScannerCallBack);
            } catch (RemoteException e) {
            }

        }

        public void onServiceDisconnected(ComponentName className) {
            mService = null;
        }
    };

    private IScannerCallback mScannerCallBack = new IScannerCallback.Stub() {

        @Override
        public void done(String[] result) throws RemoteException {
            Log.e(TAG, "DONE");
            isScanning = false;
            mResults = result;
            createResults();
        }

        @Override
        public void report(final String report) throws RemoteException {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    mResult_l.addView(mTxtShowReport);
                    mTxtShowReport.setText(report);
                }
            });
        }
    };

    private ArrayList<TextView> mShowList;

    private String[] mResults;

    private static final String EMPTY_SEARCHING_TEXT = "warning : text cannot be empty string";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();

        Intent intent = new Intent(this, ScannerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private void initComponents() {
        mTxtShowReport = new TextView(getApplicationContext());
        mBtnSearch = (Button)findViewById(R.id.search_btn);
        mBtnSearch.setText("Search this file!");
        mBtnSearch.setOnClickListener(this);
        mEdtSearchBox = (EditText)findViewById(R.id.search_box);
        mEdtSearchBox.setSingleLine(true);
        mEdtSearchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onClick(null);
                }
                return false;
            }

        });
        mResult_l = (LinearLayout)findViewById(R.id.result_l);
    }

    private void createResults() {
        mShowList = new ArrayList<TextView>();
        for (final String result : mResults) {
            TextView tv = new TextView(getApplicationContext());
            final String name = result.substring(result.lastIndexOf("/") + 1);
            tv.setText(name);
            tv.setTextColor(Color.BLACK);
            tv.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        MimeTypeMap mime = MimeTypeMap.getSingleton();
                        String ext = name.substring(name.indexOf(".") + 1);
                        String type = mime.getMimeTypeFromExtension(ext);

                        intent.setDataAndType(Uri.fromFile(new File(result)), type);
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "open file failed",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
            mShowList.add(tv);
        }

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                for (TextView tv : mShowList)
                    mResult_l.addView(tv, 0);
            }
        });
    }

    private void resetComponents() {
        mResult_l.removeAllViews();
        mTxtShowReport.setText("");
        searchText = mEdtSearchBox.getText().toString().toLowerCase();
    }

    @Override
    public void onClick(final View v) {

        resetComponents();

        if (searchText.equals("") == false) {
            try {
                isScanning = true;
                createHintThread();
                mService.requestScanService(searchText);
                mResult_l.addView(mTxtShowReport);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(0,
                    InputMethodManager.HIDE_NOT_ALWAYS);

        } else {
            mResult_l.addView(mTxtShowReport);
            mTxtShowReport.setText(EMPTY_SEARCHING_TEXT);
        }
    }

    private int hintCounter = 0;

    private void createHintThread() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (isScanning) {
                    hintCounter++;
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if (hintCounter % 3 == 0)
                                mTxtShowReport.setText("Scanning .");
                            else if (hintCounter % 3 == 1)
                                mTxtShowReport.setText("Scanning . .");
                            else if (hintCounter % 3 == 2)
                                mTxtShowReport.setText("Scanning . . .");
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
