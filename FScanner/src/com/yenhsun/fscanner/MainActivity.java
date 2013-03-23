
package com.yenhsun.fscanner;

import java.io.File;
import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
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

    private TextView mTxtShowFiles;

    private Button mBtnSearch;

    private LinearLayout mResult_l;

    private EditText mEdtSearchBox;

    private long timer = 0;

    private int mFileCounter = 0;

    private int mDirectoryCounter = 0;

    private String searchText;

    private ArrayList<File> mResults;

    private ArrayList<TextView> mShowList;

    private static final String[] ROOTs = new String[] {
            Environment.getExternalStorageDirectory().getAbsolutePath(), "/storage/extSdCard" /*
                                                                                               * for
                                                                                               * samsung
                                                                                               * n7000
                                                                                               */
    };

    private static final String EMPTY_SEARCHING_TEXT = "warning : text cannot be empty string";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        mTxtShowFiles = new TextView(getApplicationContext());
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

        mResults = new ArrayList<File>();
    }

    private void scan(final String directory) {

        File root = new File(directory);

        File[] files = root.listFiles();
        if (files != null)
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    ++mDirectoryCounter;
                    scan(files[i].getAbsolutePath());
                } else {
                    ++mFileCounter;
                    if (files[i].getAbsolutePath().toLowerCase().contains(searchText))
                        mResults.add(files[i]);
                }
            }
    }

    private void createResults() {
        mShowList = new ArrayList<TextView>();
        for (final File result : mResults) {
            TextView tv = new TextView(getApplicationContext());
            tv.setText(result.getName());
            tv.setTextColor(Color.BLACK);
            tv.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        MimeTypeMap mime = MimeTypeMap.getSingleton();
                        String ext = result.getName().substring(result.getName().indexOf(".") + 1);
                        String type = mime.getMimeTypeFromExtension(ext);

                        intent.setDataAndType(Uri.fromFile(result), type);
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "open file failed",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
            mShowList.add(tv);
        }
    }

    private void resetComponents() {
        mResult_l.removeAllViews();
        mTxtShowFiles.setText("");
        mResults.clear();
        mDirectoryCounter = 0;
        mFileCounter = 0;
        searchText = mEdtSearchBox.getText().toString().toLowerCase();
    }

    @Override
    public void onClick(final View v) {
        resetComponents();

        if (searchText.equals("") == false) {
            mEdtSearchBox.setEnabled(false);
            mBtnSearch.setVisibility(View.INVISIBLE);
            new Thread(new Runnable() {

                @Override
                public void run() {
                    timer = System.currentTimeMillis();
                    for (String root : ROOTs)
                        if (new File(root).exists()) {
                            scan(root);
                        }
                    createResults();
                    timer = System.currentTimeMillis() - timer;
                    final String log = mTxtShowFiles.getText() + "\n\n\ntotal time : " + timer
                            + " ms\nTotal Directories : " + mDirectoryCounter + "\nTotal Files : "
                            + mFileCounter;
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            for (TextView tv : mShowList) {
                                mResult_l.addView(tv, 0);
                            }
                            mTxtShowFiles.setText(log);
                            mResult_l.addView(mTxtShowFiles);
                            mBtnSearch.setVisibility(View.VISIBLE);
                            mEdtSearchBox.setEnabled(true);
                            ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE))
                                    .toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    });
                }
            }).start();
        } else {
            mResult_l.addView(mTxtShowFiles);
            mTxtShowFiles.setText(EMPTY_SEARCHING_TEXT);
        }
    }
}
