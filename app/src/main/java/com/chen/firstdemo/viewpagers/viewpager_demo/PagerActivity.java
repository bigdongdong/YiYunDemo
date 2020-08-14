package com.chen.firstdemo.viewpagers.viewpager_demo;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class PagerActivity extends AppCompatActivity {
    MyFragmentPagerAdapter myFragmentPagerAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout mainLayout = new LinearLayout(this,null);
        mainLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setBackgroundColor(Color.WHITE);
        mainLayout.setGravity(Gravity.CENTER);

        ViewPager viewPager = new ViewPager(this);
        viewPager.setId(View.generateViewId());
        viewPager.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        ArrayList<Fragment> fragments = new ArrayList<>();
        PagerFrament pagerFrament ;
        for (int i = 0;i<200;i++){
            pagerFrament = new PagerFrament();
            pagerFrament.setPosition(i);
            fragments.add(pagerFrament);
        }
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(this.getSupportFragmentManager(),fragments);
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            //Y方向最小缩放值
            private static final float MIN_SCALE_Y = 0.75f;

            @Override
            public void transformPage(@NonNull View page, float position) {
                if (position >= 1 || position <= -1) {
                    page.setScaleY(MIN_SCALE_Y);
                } else if (position < 0) {
                    //  -1 < position < 0
                    //View 在再从中间往左边移动，或者从左边往中间移动
                    float scaleY = MIN_SCALE_Y + (1 + position) * (1 - MIN_SCALE_Y);
                    page.setScaleY(scaleY);
                } else {
                    // 0 <= positin < 1
                    //View 在从中间往右边移动 或者从右边往中间移动
                    float scaleY = (1 - MIN_SCALE_Y) * (1 - position) + MIN_SCALE_Y;
                    page.setScaleY(scaleY);
                }
            }
        });

        viewPager.setPadding(100,400,100,400);
        viewPager.setClipToPadding(false);
        viewPager.setCurrentItem(0);
//        viewPager.setBackgroundColor(Color.parseColor("#00000000"));
        mainLayout.addView(viewPager);
        this.setContentView(mainLayout);


        /* 设置viewpager切换动画速率 */
        try {
            Class aClass = ViewPager.class;
            Field sInterpolator = aClass.getDeclaredField("sInterpolator");
            sInterpolator.setAccessible(true);
            Scroller scroller = new Scroller(this, (Interpolator) sInterpolator.get(viewPager)) {
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                    //最后一个参数即viewpager自动滑动的时间
                    super.startScroll(startX, startY, dx, dy, 200);
                }
            };
            Field mScroller = aClass.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(viewPager, scroller);
        } catch (Exception e) {
            new RuntimeException("给viewpager设置动画时间时出错："+e.getMessage());
        }

        /*计时器
        * 500ms 一个 一共一百个
        * */
//        CountDownTimer countDownTimer = new CountDownTimer(100*100,500) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                int i = (int) ((100*100 - millisUntilFinished ) / 1000);//当前第几个
//                viewPager.setCurrentItem(i);
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        };
//        countDownTimer.start();
    }


}
