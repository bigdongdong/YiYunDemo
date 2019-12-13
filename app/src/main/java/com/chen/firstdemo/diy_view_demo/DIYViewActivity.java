package com.chen.firstdemo.diy_view_demo;

import android.graphics.drawable.ShapeDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chen.firstdemo.shape_demo.HornShape;
import com.chen.firstdemo.shape_demo.WindowFlowerShape;

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
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);


        DiyView diyView = new DiyView(this);
        linearLayout.addView(diyView);

//        CanvasStudyView canvasStudyView = new CanvasStudyView(this);
//        linearLayout.addView(canvasStudyView);

        ImageView iv = new ImageView(this);
        iv.setLayoutParams(new RelativeLayout.LayoutParams(200,200));
        iv.setBackground(new ShapeDrawable(new HornShape(Gravity.LEFT)));
        linearLayout.addView(iv);

        iv = new ImageView(this);
        iv.setLayoutParams(new RelativeLayout.LayoutParams(200,200));
        iv.setBackground(new ShapeDrawable(new HornShape(Gravity.RIGHT)));
        linearLayout.addView(iv);


        iv = new ImageView(this);
        iv.setLayoutParams(new RelativeLayout.LayoutParams(400,400));
        iv.setBackground(new ShapeDrawable(new WindowFlowerShape()));
        linearLayout.addView(iv);

        this.setContentView(linearLayout);

    }
}
