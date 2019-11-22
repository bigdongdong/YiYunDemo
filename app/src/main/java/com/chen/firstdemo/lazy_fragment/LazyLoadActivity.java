package com.chen.firstdemo.lazy_fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.chen.firstdemo.R;
import com.chen.firstdemo.bottom_tab_demo.BottomTabView;

import java.util.ArrayList;
import java.util.List;

public class LazyLoadActivity extends AppCompatActivity {

    CustomTabView customTabView ;
    LinearLayout bottomTabLL ;
    ViewPager viewPager ;
    List<String> tabs ;
    LazyLoadTestFragment fragment ;
    List<Fragment> fragments ;
    final int count = 8 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazy_load);

        viewPager = findViewById(R.id.viewPager);
        bottomTabLL = findViewById(R.id.bottomTabLL);

        tabs=new ArrayList<>();
        for(int i = 0;i <count ; i++){
            tabs.add((i+1)+"");
        }


        customTabView = new CustomTabView.Builder()
                .context(this)
                .tabs(tabs)
                .count(count)
                .lineMarginTop(30)
                .viewPager(viewPager)
                .lineConnerRadius(7)
                .lineWidth(120)
                .lineHeight(12)
                .lineColor(Color.parseColor("#FFFFEC00"))
                .textSize(15)
                .textUnSelectedColor(Color.parseColor("#FF999999"))
                .textSelectedColor(Color.parseColor("#FF333333"))
                .build();

        bottomTabLL.addView(customTabView);

        fragments = new ArrayList<>();
        for(int i =0;i<count;i++){
            fragment = new LazyLoadTestFragment(i+1);
            fragments.add(fragment);
        }

        MyViewpagerAdapter adapter = new MyViewpagerAdapter(this.getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(0);
    }

    public class MyViewpagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> fragments;

        public MyViewpagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments!=null?fragments.size():0;
        }
    }

}
