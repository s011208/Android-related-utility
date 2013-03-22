
package com.yenhsun.addviewtest;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements OnClickListener {

    Button btn;

    LinearLayout out;

    RelativeLayout in;

    private WindowManager.LayoutParams wmParams;

    private WindowManager wm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wm = (WindowManager)getApplicationContext().getSystemService("window");
        btn = (Button)findViewById(R.id.btn);
        btn.setText("CLICK");
        btn.setOnClickListener(this);

        out = new LinearLayout(getApplicationContext());
        out.setBackgroundColor(Color.BLUE);
        in = new RelativeLayout(getApplicationContext());
        in.setBackgroundColor(Color.RED);

        LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(200, 200);
        in.setLayoutParams(l);
        wmParams = getDefaultWindowManagerParamsSettings();
    }

    @Override
    public void onClick(View v) {
        wmParams.width += 10;
        wmParams.height += 10;
        LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(200, 200);
        l.setMargins(5, 5, 5, 5);
        in.setLayoutParams(l);
        out.addView(in);
        wm.addView(out, wmParams);
    }

    public void onDestroy() {
        super.onDestroy();
        wm.removeView(out);
    }

    public WindowManager.LayoutParams getDefaultWindowManagerParamsSettings() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.x = 100;
        params.y = 100;
        params.height = 200;
        params.width = 200;
        params.type = LayoutParams.TYPE_PHONE;
        params.format = PixelFormat.RGBA_8888;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;

        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.horizontalWeight = 0;
        params.verticalWeight = 0;
        params.windowAnimations = android.R.style.Animation_Toast;
        params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN;

        return params;
    }

}
