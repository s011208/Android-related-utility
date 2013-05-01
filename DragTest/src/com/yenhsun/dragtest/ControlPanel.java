
package com.yenhsun.dragtest;

import java.util.ArrayList;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.view.DragEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;

public class ControlPanel extends LinearLayout implements View.OnDragListener,
        View.OnLongClickListener {
    private static final int CONTROLLER_WIDTH = 100;

    private ArrayList<Controller> mControllers;

    public ControlPanel(Context context) {
        this(context, null);
    }

    public ControlPanel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ControlPanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setOrientation(LinearLayout.HORIZONTAL);
        createControllers();
        this.setBackgroundColor(Color.BLACK);
        this.setOnDragListener(this);
    }

    private void createControllers() {
        mControllers = new ArrayList<Controller>();
        Controller c1 = new Controller(getContext());
        c1.setBackgroundResource(R.drawable.c1);
        mControllers.add(c1);
        Controller c2 = new Controller(getContext());
        c2.setBackgroundResource(R.drawable.c2);
        mControllers.add(c2);
        Controller c3 = new Controller(getContext());
        c3.setBackgroundResource(R.drawable.c3);
        mControllers.add(c3);
        Controller c4 = new Controller(getContext());
        c4.setBackgroundResource(R.drawable.c4);
        mControllers.add(c4);
        Controller c5 = new Controller(getContext());
        c5.setBackgroundResource(R.drawable.c5);
        mControllers.add(c5);
        Controller c6 = new Controller(getContext());
        c6.setBackgroundResource(R.drawable.c6);
        mControllers.add(c6);
        Controller c7 = new Controller(getContext());
        c7.setBackgroundResource(R.drawable.c7);
        mControllers.add(c7);
        Controller c8 = new Controller(getContext());
        c8.setBackgroundResource(R.drawable.c8);
        mControllers.add(c8);
        Controller c9 = new Controller(getContext());
        c9.setBackgroundResource(R.drawable.c9);
        mControllers.add(c9);
        Controller c10 = new Controller(getContext());
        c10.setBackgroundResource(R.drawable.c10);
        mControllers.add(c10);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(CONTROLLER_WIDTH, CONTROLLER_WIDTH);
        for (Controller c : mControllers) {
            c.setOnLongClickListener(this);
            addView(c, lp);
        }
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        final int action = event.getAction();
        if (DragEvent.ACTION_DRAG_STARTED == action) {
            Log.e("QQ", this.getWidth() + "");
            Log.e("QQ", "start");
        } else if (DragEvent.ACTION_DRAG_ENTERED == action) {
            Log.e("QQ", "enter");
        } else if (DragEvent.ACTION_DRAG_EXITED == action) {
            Log.e("QQ", "exit");
        } else if (DragEvent.ACTION_DRAG_ENDED == action) {
            Log.e("QQ", "ended");
            View controller = (View)event.getLocalState();
            controller.setAlpha(1f);
        } else if (DragEvent.ACTION_DROP == action) {
            Log.e("QQ", "DROP");
            View controller = (View)event.getLocalState();
            removeView(controller);
            int position = getPosition();
            this.addView(controller, position);
            controller.setAlpha(1f);
        }
        return true;
    }

    private int getPosition() {
        int rtn = 0;
        return rtn;
    }

    @Override
    public boolean onLongClick(View v) {
        v.setAlpha(0.3f);
        ClipData data = ClipData.newPlainText("", "");
        DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
        v.startDrag(data, shadowBuilder, v, 0);
        return true;
    }
}
