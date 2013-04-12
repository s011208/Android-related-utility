
package com.yenhsun.getresourcefromotherpackages;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnHoverListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {
    private PackageManager pm;

    private static final String LAUNCHER_PACKAGENAME = "com.android.launcher";

    private boolean DEBUG = true;

    private String TAG = "QQQQ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLauncherConfiguration();
    }

    private void getLauncherConfiguration() {
        int iconSize = 0;
        int widthGap = 0;
        int heightGap = 0;
        try {
            Resources appResource = pm.getResourcesForApplication(LAUNCHER_PACKAGENAME);
            if (appResource == null) {
                if (DEBUG)
                    Log.w(TAG, "null resources, package name : " + LAUNCHER_PACKAGENAME);
            }
            String iconSizeName = true ? "hotseat_cell_width"
                    : getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? "workspace_cell_width_land"
                            : "workspace_cell_width_port";
            String widthGapName = true ? "hotseat_width_gap"
                    : getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? "workspace_width_gap_land"
                            : "workspace_width_gap_port";
            String heightGapName = true ? "hotseat_height_gap"
                    : getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? "workspace_height_gap_land"
                            : "workspace_height_gap_port";
            int iconSizeID = appResource.getIdentifier(iconSizeName, "dimen", LAUNCHER_PACKAGENAME);
            int widthGapID = appResource.getIdentifier(widthGapName, "dimen", LAUNCHER_PACKAGENAME);
            int heightGapID = appResource.getIdentifier(heightGapName, "dimen",
                    LAUNCHER_PACKAGENAME);

            iconSize = (int)appResource.getDimension(iconSizeID);
            widthGap = (int)appResource.getDimension(widthGapID);
            heightGap = (int)appResource.getDimension(heightGapID);

            Log.i(TAG, "icon " + iconSize + ", wg " + widthGap + ", hg " + heightGap);

        } catch (NameNotFoundException e) {
            if (DEBUG) {
                Log.w(TAG, "null resource ID, package name : " + LAUNCHER_PACKAGENAME, e);
            }
        } catch (NullPointerException e) {
            if (DEBUG) {
                Log.w(TAG, "null pointer, package name : " + LAUNCHER_PACKAGENAME, e);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        Log.i(TAG, "icon " + iconSize + ", wg " + widthGap + ", hg " + heightGap);
    }
}
