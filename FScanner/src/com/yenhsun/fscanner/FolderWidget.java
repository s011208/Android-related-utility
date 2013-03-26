
package com.yenhsun.fscanner;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

public class FolderWidget extends AppWidgetProvider {
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int widget : appWidgetIds)
            updateAppWidget(context, appWidgetManager, widget);
    }

    public void onDeleted(Context context, int[] appWidgetIds) {

    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        // TODO Auto-generated method stub
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_folder);
        LayoutInflater myInflater = (LayoutInflater)context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View myRootLayout = myInflater.inflate(R.layout.widget_folder, null);
        LinearLayout l = (LinearLayout)myRootLayout.findViewById(R.id.widget_folder);
        Button btn = new Button(context);
        btn.setText("QQQQQQQQQQQ");
        l.addView(btn);
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }
}
