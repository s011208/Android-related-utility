
package com.yenhsun.dragtest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class Controller extends ImageView {
    public Controller(Context context) {
        this(context, null);
    }

    public Controller(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Controller(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

}
