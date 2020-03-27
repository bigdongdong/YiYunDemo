package com.chen.firstdemo.bottom_tab_demo;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chen.firstdemo.R;
import com.chen.firstdemo.viewpager_demo.MyFragmentPagerAdapter;
import com.chen.firstdemo.viewpager_demo.PagerFrament;

import java.util.ArrayList;

public class BottomTabActivity extends AppCompatActivity {

    RelativeLayout mainLayout ;
    RelativeLayout.LayoutParams layoutParams;
    MyFragmentPagerAdapter myFragmentPagerAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainLayout = new RelativeLayout(this);
        mainLayout.setLayoutParams(new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));
        mainLayout.setGravity(Gravity.BOTTOM);
        mainLayout.setBackgroundColor(Color.parseColor("#CCCCCC"));


        /*设置底部tab*/
        BottomTabView bottomTabView = new BottomTabView(this) {
            @Override
            protected void onTabSelectedListener(int position) {
                Log.i("aaa", "onTabSelectedListener: "+position);
            }
        };

        /*每一个tab宽度*/
        int tabWidth = bottomTabView.getScreenWidth()/5;

        int nomalHeight = (int) ((160f/220f)*tabWidth);
        layoutParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                nomalHeight);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        bottomTabView.setLayoutParams(layoutParams);


        /*设置底部tab中心的view*/

        BottomTabCenterView bottomTabCenterView = new BottomTabCenterView(this);
        layoutParams = new RelativeLayout.LayoutParams(tabWidth,tabWidth);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        bottomTabCenterView.setLayoutParams(layoutParams);

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.main_tab_center_view);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        int imageWidth = (int) ((160f/220f)*tabWidth);
        layoutParams = new RelativeLayout.LayoutParams(imageWidth,imageWidth);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        imageView.setLayoutParams(layoutParams);
        bottomTabCenterView.addView(imageView); //添加中心的图标


        /*添加画廊效果*/
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


        mainLayout.addView(bottomTabView);
        mainLayout.addView(bottomTabCenterView);
        mainLayout.addView(viewPager);

        this.setContentView(mainLayout);

    }
}
