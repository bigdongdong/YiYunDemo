package com.chen.firstdemo.scrollview_toolbar;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chen.firstdemo.R;
import com.chen.firstdemo.viewpager_demo.MyFragmentPagerAdapter;
import com.chen.firstdemo.utils.DensityUtil;
import com.chen.firstdemo.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScrollViewToolbarActivity extends AppCompatActivity implements StickyScrollView.Listener {

    final String TAG = "aaa";
    Context context;
    @BindView(R.id.firstView)
    View firstView;
    @BindView(R.id.chuantouView)
    View chuantouView;
    @BindView(R.id.stickyView)
    RelativeLayout stickyView;
    @BindView(R.id.scrollView)
    StickyScrollView scrollView;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.tvTitle2)
    TextView tvTitle2;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tabFL)
    FrameLayout tabFL;
    @BindView(R.id.viewPager)
    MyViewPager viewPager;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view_toolbar);
        ButterKnife.bind(this);
        fullScreen(this);

        scrollView.setOnScrollChangeListener(this);
        tvTitle.setAlpha(0);

        context = this;

        chuantouView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ");
            }
        });


        int[] location = new int[2];
        tvTitle2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tabFL.getLocationInWindow(location);
                title2StratY = location[1];
                Log.i(TAG, "title2StratY = " + title2StratY);
                if (title2StratY > 0) {
                    tvTitle2.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });

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


        scrollView.setOnTouchListener(new View.OnTouchListener() {
            final float[] y = {0};
            boolean isUp = false ;
            boolean isDown = false ;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        if(y[0] == 0){
                            y[0] = event.getY() ;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if(y[0] != 0 && (y[0] - event.getY()) > 100){ //上划
                            isUp = true ;
                            isDown = false;
                        }else if(y[0] != 0 && (event.getY() - y[0]) > 100){ //下划
                            isUp = false ;
                            isDown = true;
                        }else{ //初次
                            isUp = false ;
                            isDown = false ;
                        }

                        int[] location = new int[2];

                        stickyView.getLocationInWindow(location);

//                        float dy = Math.abs(event.getY() - y[0]) ; //滑动距离

                        if(isUp && scrollView.isHeaderSticky() == false){
                            toUp(location[1]);
                        }else if(isDown && scrollView.isHeaderSticky() == false){
                            toBottom(location[1]);
                        }

                        y[0] = 0 ;
                        break;
                }
////
//                if(scrollView.isHeaderSticky()){
//                    TestFragment f = (TestFragment) fragments.get(viewPager.getCurrentItem());
//
//                    return viewPager.onTouchEvent(event);
//                }



                return false ;
            }
        });
    }

    ValueAnimator valueAnimator;
    /*要动态自己计算*/
    private int title2StratY;

    private void toUp(final int y){
        //上方悬停
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollBy(0, y);
                Log.i(TAG, "toUp: ");
            }
        });
    }
    private void toBottom(final int y){
        //下方悬停
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollBy(0, y - title2StratY);
                Log.i(TAG, "toBottom: ");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewPager.getLayoutParams().height = 1800 ;
        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) toolbar.getLayoutParams();
        p.topMargin = StatusBarUtil.getStatusBarHeight(this);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) stickyView.getLayoutParams();
        lp.height = lp.height + StatusBarUtil.getStatusBarHeight(this);
        lp.topMargin = lp.topMargin - StatusBarUtil.getStatusBarHeight(this);
    }



    final int[] title2Location = new int[2];
    final int[] tabLocation = new int[2];

    @Override
    public void onScrollChangeListener(int mScrollX, int mScrollY, int oldX, int oldY) {
        tvTitle2.getLocationInWindow(title2Location);
        tabFL.getLocationInWindow(tabLocation);

        if (title2Location[1] > 102 && title2Location[1] < title2StratY) {
            /* 500 px 为 100% */
            float alpha = (float) (((title2Location[1] - 102) * 1.0) / (title2StratY - 102));
//            Log.i(TAG, "alpha = " + alpha);

            if (alpha > 0.8f) {
                tvTitle.setAlpha(0);
            } else {
                tvTitle.setAlpha(1 - alpha);
            }
        }

        if (title2Location[1] <= 102) {
            ivBack.setImageResource(R.mipmap.back_icon);
            tvTitle2.setText(null);
            stickyView.setBackgroundColor(Color.WHITE);
        } else {
            ivBack.setImageResource(R.mipmap.back_white_icon);
            tvTitle2.setText("我是你的小海胆");
            stickyView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    /**
     * 通过设置全屏，设置状态栏透明
     *
     * @param activity
     */
    public void fullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }
}
