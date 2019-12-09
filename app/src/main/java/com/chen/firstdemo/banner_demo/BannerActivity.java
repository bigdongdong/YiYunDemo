package com.chen.firstdemo.banner_demo;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chen.firstdemo.R;
import com.chen.firstdemo.banner_demo.pops.ImagePop;
import com.chen.firstdemo.dialog_demo.WatchPopWindow;

import java.util.ArrayList;
import java.util.List;

public class BannerActivity extends AppCompatActivity {

    Activity context ;
    Banner banner1,banner2;
    List<Integer> list = new ArrayList<>();
    LinearLayout.LayoutParams lparams ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this ;

        LinearLayout linearLayout = new LinearLayout(context);
        lparams = new LinearLayout.LayoutParams(-1,-1);
        linearLayout.setLayoutParams(lparams);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        lparams = new LinearLayout.LayoutParams(-1,1500);

        list.add(R.mipmap.beauty);
        list.add(R.mipmap.beauty_1);
        list.add(R.mipmap.beauty_2);
        list.add(R.mipmap.beauty_3);
        list.add(R.mipmap.beauty_4);
        list.add(R.mipmap.beauty_5);
        list.add(R.mipmap.beauty_6);

        PointsOptions options = new PointsOptions.Builder()
                .count(list.size())
                .marginBottom(30)
                .selectedColor(Color.WHITE)
                .unSelectedColor(Color.GRAY)
                .space(10)
                .width(15)
                .build();

                banner1 = new Banner.Builder()
                .context(this)
                .banners(list)
                .layoutStyle(Banner.LAYOUT_STYLE_IMAGEVIEW)
                .stayDuration(1500)
                .animDuration(500)
                .pointsOptions(options)
                .onSelectedListener(new OnSelectedListener<ImageView,Integer>() {
                    @Override
                    public void onSelectedListener(ImageView view, Integer integer, int position) {
                        Log.i("aaa", " position: "+position);
//                        Glide.with(BannerActivity.this).load(integer).into(view);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ImagePop pop =  new ImagePop(context);
                                pop.showCenteral(linearLayout);
                                pop.getIV().setImageResource(integer);
                            }
                        });
                    }
                })
                .build();
        banner1.setBackgroundColor(Color.GRAY);
        linearLayout.addView(banner1);


//        banner2 = new Banner.Builder()
//                .context(this)
//                .banners(list)
//                .layoutStyle(Banner.LAYOUT_STYLE_RELATIVELAYOUT)
//                .stayDuration(1500)
//                .animDuration(800)
//                .playStyle(Banner.PLAY_STYLE_JUST_ONCE)
//                .pointsOptions(options)
//                .onSelectedListener(new OnSelectedListener<RelativeLayout,Integer>() {
//                    @Override
//                    public void onSelectedListener(RelativeLayout view, Integer integer, int position) {
//                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-1,-1);
//                        view.setLayoutParams(params);
//
//                        if(position == list.size()-1){
//                            TextView tv = new TextView(context);
//                            tv.setText("点击这里查看更多！");
//                            params = new RelativeLayout.LayoutParams(500,150);
//                            params.addRule(RelativeLayout.CENTER_IN_PARENT);
//                            tv.setLayoutParams(params);
//                            tv.setGravity(Gravity.CENTER);
//                            tv.setTextColor(Color.WHITE);
//                            GradientDrawable gd = new GradientDrawable();
//                            gd.setColor(Color.BLUE);
//                            gd.setCornerRadius(150);
//                            tv.setBackground(gd);
//                            view.removeAllViews();
//                            view.addView(tv);
//                        }else{
//                            ImageView iv = new ImageView(context);
//                            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                            iv.setLayoutParams(new LinearLayout.LayoutParams(-1,-1));
//                            Glide.with(BannerActivity.this).load(integer).into(iv);
//                            view.removeAllViews();
//                            view.addView(iv);
//                        }
//                    }
//                })
//                .build();
//        banner2.setLayoutParams(lparams);
//        banner2.setBackgroundColor(Color.GRAY);
//        linearLayout.addView(banner2);


        this.setContentView(linearLayout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(banner1 != null){
            banner1.start();
        }
//        if(banner2 != null){
//            banner2.start();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(banner1 != null){
            banner1.stop();
        }
        if(banner2 != null){
            banner2.stop();
        }
    }
}
