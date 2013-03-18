
package com.yenhsun.croptextview;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView mTxtCropSource;

    private ImageView mImgCropResult;

    private Button mBtnCrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxtCropSource = (TextView)findViewById(R.id.crop_txt);
        mImgCropResult = (ImageView)findViewById(R.id.crop_result_img);
        mBtnCrop = (Button)findViewById(R.id.crop_btn);
        mBtnCrop.setText("CROP");
        mBtnCrop.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                crop();
            }
        });
    }

    private int counter = 0;

    private static final int BADGE_HEIGHT = 20;

    private static final int BADGE_WIDTH = 30;

    private void crop() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(BADGE_WIDTH,
                BADGE_HEIGHT);
        mTxtCropSource.setLayoutParams(layoutParams);
        String showString = counter >= 99 ? "99+" : "" + ++counter;
        mTxtCropSource.setText(showString);
        mTxtCropSource.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        mTxtCropSource.setTextColor(Color.BLUE);
        mTxtCropSource.setBackgroundColor(Color.TRANSPARENT);

        Bitmap cropBitmap;

        cropBitmap = Bitmap.createBitmap(BADGE_WIDTH, BADGE_HEIGHT, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(cropBitmap);
        mTxtCropSource.layout(0, 0, BADGE_WIDTH, BADGE_HEIGHT);
        mTxtCropSource.draw(c);

        mImgCropResult.setLayoutParams(layoutParams);
        mImgCropResult.setBackgroundResource(R.drawable.bg);
        mImgCropResult.setImageBitmap(cropBitmap);
        mImgCropResult.setMaxHeight(BADGE_HEIGHT);
        mImgCropResult.setMaxWidth(BADGE_WIDTH);
    }
}
