
package com.yenhsun.getresourcefromotherpackages;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class T extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView t = new TextView(this);
        t.setText("QQQQ");
        setContentView(t);
    }
}
