package com.chen.firstdemo.pager_demo;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.chen.firstdemo.R;

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
        for (int i = 0;i<20;i++){
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
    }


}
