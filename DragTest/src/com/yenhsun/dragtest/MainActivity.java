
package com.yenhsun.dragtest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    private ControlPanel mCp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPanel();
    }

    private void initPanel() {
        mCp = (ControlPanel)findViewById(R.id.control_panel);
    }
}
