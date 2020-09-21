package com.chen.firstdemo.diy_view_demo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;

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
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.setPadding(0,0,0,300);
        sv.addView(linearLayout);

//        ShadowView shadowView = new ShadowView(this) ;
//        params = new LinearLayout.LayoutParams(400,
//                400);
//        shadowView.setLayoutParams(params);
//        linearLayout.addView(shadowView);


//        DiyView diyView = new DiyView(this);
//        linearLayout.addView(diyView);

//        CanvasStudyView canvasStudyView = new CanvasStudyView(this);
//        linearLayout.addView(canvasStudyView);

//        ImageView iv = new ImageView(this);
//        iv.setLayoutParams(new RelativeLayout.LayoutParams(200,200));
//        iv.setBackground(new ShapeDrawable(new HornShape(HornShape.Gravity.TOP_LEFT, Color.BLUE)));
//        linearLayout.addView(iv);
//
//        iv = new ImageView(this);
//        iv.setLayoutParams(new RelativeLayout.LayoutParams(200,200));
//        iv.setBackground(new ShapeDrawable(new HornShape(HornShape.Gravity.TOP_RIGHT, Color.BLUE)));
//        linearLayout.addView(iv);
//
//
//        iv = new ImageView(this);
//        iv.setLayoutParams(new RelativeLayout.LayoutParams(400,400));
//        iv.setBackground(new ShapeDrawable(new WindowFlowerShape()));
//        linearLayout.addView(iv);


        ArrowShadowPopView aspv = new ArrowShadowPopView(this);
        params = new LinearLayout.LayoutParams(DensityUtil.dip2px(this,100),
                DensityUtil.dip2px(this,105));
        params.topMargin = 100 ;
        aspv.setLayoutParams(params);
        linearLayout.addView(aspv);

        CornerPathEffectView cpev= new CornerPathEffectView(this);
        cpev.setBackgroundColor(Color.WHITE);
        params = new LinearLayout.LayoutParams(-1,
                DensityUtil.dip2px(this,300));
        params.topMargin = 100 ;
        cpev.setLayoutParams(params);
        linearLayout.addView(cpev);

        this.setContentView(sv);

    }
}
