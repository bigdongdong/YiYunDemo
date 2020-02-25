package com.chen.firstdemo.anim_demo;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;
import com.chen.firstdemo.pager_demo.MyFragmentPagerAdapter;
import com.chen.firstdemo.scrollview_toolbar.CustomLineTabView;
import com.chen.firstdemo.scrollview_toolbar.MyViewPager;
import com.chen.firstdemo.scrollview_toolbar.TestFragment;
import com.chen.firstdemo.utils.DensityUtil;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimActivity extends BaseActivity {

    @BindView(R.id.tvTitle2)
    TextView tvTitle2;
    @BindView(R.id.chuantouView)
    View chuantouView;
    @BindView(R.id.tabFL)
    FrameLayout tabFL;
    @BindView(R.id.stickyView)
    RelativeLayout stickyView;
    @BindView(R.id.viewPager)
    MyViewPager viewPager;
    @BindView(R.id.ll)
    LinearLayout ll;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        ButterKnife.bind(this);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new TestFragment());
        fragments.add(new TestFragment());
        fragments.add(new TestFragment());
        viewPager.setAdapter(new MyFragmentPagerAdapter(this.getSupportFragmentManager(), fragments));

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.WHITE);
        gd.setCornerRadii(new float[]{50,50,50,50,0,0,0,0});
        tvTitle2.setBackground(gd);

        //初始化导航条
        List<String> list = new ArrayList<>();
        list.add("资料");
        list.add("技能");
        list.add("动态");
        CustomLineTabView tabView = new CustomLineTabView.Builder()
                .viewPager(viewPager)
                .context(this)
                .count(3)
                .lineColor(Color.parseColor("#FFEC00"))
                .lineConnerRadius(DensityUtil.dip2px(this, 3))
                .lineWidth(DensityUtil.dip2px(this, 9))
                .lineHeight(DensityUtil.dip2px(this, 4))
                .lineMarginTop(DensityUtil.dip2px(this, 9))
                .tabs(list)
                .textSize(14)
                .textSelectedSize(21)
                .textUnSelectedColor(Color.parseColor("#999999"))
                .textSelectedColor(Color.parseColor("#333333"))
                .tabWidth(DensityUtil.dip2px(this, 57))
                .build();
        tabView.setSelected(0);//默认选中第一个
        tabFL.addView(tabView);


        ll.setFocusable(true);
        ll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.i(TAG, "onTouch: ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int y = (int) event.getY();
                        Log.i(TAG, "onTouch: "+y);
                        RelativeLayout.LayoutParams rp = (RelativeLayout.LayoutParams) ll.getLayoutParams();
                        rp.topMargin = y ;
                        break;
                }

                return false ;
            }
        });
    }
}
