
package com.yenhsun.ndktest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        test = new TextView(this);
        test.setText(getMagicString());
        setContentView(test);

        Toast.makeText(this, getSecString(), Toast.LENGTH_LONG).show();
    }

    public native String getSecString();

    public native String getMagicString();

    static {
        System.loadLibrary("NDKTest");
    }
}
