
package com.yenhsun.tocheckserviceisforeground;

import java.util.Iterator;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(), "" + isServiceRunning("com.asus.floating"), Toast.LENGTH_SHORT).show();
    }

    private boolean isServiceRunning(String serviceName) {
        boolean serviceRunning = false;
        ActivityManager am = (ActivityManager)this.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> l = am.getRunningServices(50);
        Iterator<ActivityManager.RunningServiceInfo> i = l.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningServiceInfo runningServiceInfo = (ActivityManager.RunningServiceInfo)i
                    .next();

            if (runningServiceInfo.service.getClassName().contains(serviceName)
                    && runningServiceInfo.foreground) {
                serviceRunning = true;
            }
        }
        return serviceRunning;
    }
}
