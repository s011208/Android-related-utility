
package com.yenhsun.fscanner;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements Button.OnClickListener {

    private TextView mTxtShowFiles;

    private Button mBtnSearch;

    private EditText mEdtSearchBox;

    private long timer = 0;

    private StringBuilder mStrBuilder;

    private int mFileCounter = 0;

    private static final int START_SCAN = 1;

    private int mDirectoryCounter = 0;

    private String searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        mTxtShowFiles = (TextView)findViewById(R.id.show);
        mBtnSearch = (Button)findViewById(R.id.search_btn);
        mBtnSearch.setText("Search this file!");
        mBtnSearch.setOnClickListener(this);
        mEdtSearchBox = (EditText)findViewById(R.id.search_box);
        mEdtSearchBox.setSingleLine(true);

        mStrBuilder = new StringBuilder();
    }

    private void scan(final String directory) {

        File root = new File(directory);

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                // mTxtShowFiles.setText("searching : " + directory);
            }
        });
        File[] files = root.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                ++mDirectoryCounter;
                scan(files[i].getAbsolutePath());
            } else {
                ++mFileCounter;
                // mHandler.post(updateBox);
                if (files[i].getAbsolutePath().contains(searchText))
                    mStrBuilder.append(mTxtShowFiles.getText() + files[i].getAbsolutePath() + "\n");
            }
        }
    }

    // private Runnable updateBox = new Runnable() {
    //
    // @Override
    // public void run() {
    // updateBox();
    // }
    // };
    //
    // private void updateBox() {
    // mEdtSearchBox.setText(mCounter + "");
    // }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        mTxtShowFiles.setText("");
        mStrBuilder = new StringBuilder();
        mDirectoryCounter = 0;
        mFileCounter = 0;
        searchText = mEdtSearchBox.getText().toString();
        if (searchText.equals("") == false)
            new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    timer = System.currentTimeMillis();

                    mFileCounter = 0;
                    scan(Environment.getExternalStorageDirectory().getAbsolutePath());
                    timer = System.currentTimeMillis() - timer;
                    mStrBuilder.append(mTxtShowFiles.getText() + "\n\n\ntotal time : " + timer
                            + " ms\nTotal Directories : " + mDirectoryCounter + "\nTotal Files : "
                            + mFileCounter);
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            mTxtShowFiles.setText(mStrBuilder.toString());
                        }
                    });
                }
            }).start();
    }
}
