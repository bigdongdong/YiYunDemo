package com.chen.firstdemo.diy_view_demo;

import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.chen.firstdemo.base.HornShape;
import com.chen.firstdemo.shape_demo.WindowFlowerShape;
import com.chen.firstdemo.utils.DensityUtil;

public class DIYViewActivity extends AppCompatActivity {

    LinearLayout linearLayout ;
    LinearLayout.LayoutParams params ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView sv = new ScrollView(this);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        sv.setLayoutParams(params);

        linearLayout = new LinearLayout(this);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setClipChildren(false);
        sv.addView(linearLayout);

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
        iv.setBackground(new ShapeDrawable(new HornShape(HornShape.Gravity.TOP_LEFT, Color.BLUE)));
        linearLayout.addView(iv);

        iv = new ImageView(this);
        iv.setLayoutParams(new RelativeLayout.LayoutParams(200,200));
        iv.setBackground(new ShapeDrawable(new HornShape(HornShape.Gravity.TOP_RIGHT, Color.BLUE)));
        linearLayout.addView(iv);


        iv = new ImageView(this);
        iv.setLayoutParams(new RelativeLayout.LayoutParams(400,400));
        iv.setBackground(new ShapeDrawable(new WindowFlowerShape()));
        linearLayout.addView(iv);


        ArrowShadowPopView aspv = new ArrowShadowPopView(this);
        params = new LinearLayout.LayoutParams(DensityUtil.dip2px(this,100),
                DensityUtil.dip2px(this,105));
        params.topMargin = 100 ;
        aspv.setLayoutParams(params);
        linearLayout.addView(aspv);

        this.setContentView(sv);

    }
}
