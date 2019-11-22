package com.chen.firstdemo.diy_view_demo;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class DIYViewActivity extends AppCompatActivity {

    LinearLayout linearLayout ;
    LinearLayout.LayoutParams params ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        linearLayout = new LinearLayout(this);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        ShadowView shadowView = new ShadowView(this) ;
        params = new LinearLayout.LayoutParams(400,
                400);
        shadowView.setLayoutParams(params);
        linearLayout.addView(shadowView);


        DiyView diyView = new DiyView(this);
        linearLayout.addView(diyView);

//        CanvasStudyView canvasStudyView = new CanvasStudyView(this);
//        linearLayout.addView(canvasStudyView);


        this.setContentView(linearLayout);

    }
}
