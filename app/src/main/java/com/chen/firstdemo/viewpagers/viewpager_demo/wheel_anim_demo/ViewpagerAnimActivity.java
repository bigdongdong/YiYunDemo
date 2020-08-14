package com.chen.firstdemo.viewpagers.viewpager_demo.wheel_anim_demo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.bumptech.glide.Glide;
import com.chen.firstdemo.R;
import com.chen.firstdemo.utils.DensityUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewpagerAnimActivity extends AppCompatActivity {

    @BindView(R.id.viewPagerRL)
    RelativeLayout viewPagerRL;
    private Context context;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_anim);
        ButterKnife.bind(this);

        context = this;

        viewPagerRL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        /* 设置viewpager切换动画速率 */
        try {
            Class aClass = ViewPager.class;
            Field sInterpolator = aClass.getDeclaredField("sInterpolator");
            sInterpolator.setAccessible(true);
            Scroller scroller = new Scroller(this, (Interpolator) sInterpolator.get(viewPager)) {
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                    //最后一个参数即viewpager自动滑动的时间
                    super.startScroll(startX, startY, dx, dy, 500);
                }
            };
            Field mScroller = aClass.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(viewPager, scroller);
        } catch (Exception e) {
            new RuntimeException("给viewpager设置动画时间时出错：" + e.getMessage());
        }

    }

    void init() {

        List<String> urls = new ArrayList<>();
        urls.add("https://image.soudlink.net/props/gift_shuijingxie.png");
        urls.add("https://image.soudlink.net/props/ssvoicedan_tree.png");
        urls.add("https://image.soudlink.net/props/gift_xiaostar.png");
        urls.add("https://image.soudlink.net/props/gift_ssvipmhyyc1.png");
        urls.add("https://image.soudlink.net/props/gift_sssheshou.png");
        WheelAdapter wheelAdapter = new WheelAdapter(urls);

        viewPager.setAdapter(wheelAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            //Y方向最小缩放值
            private static final float MIN_SCALE = 0.7f;

            /**
             *
             * @param page
             * @param position  基准点，左负右正  -5 -4 -3 -2 -1.5 -1 0 1 1.5 2 3 4 5
             */
            @Override
            public void transformPage(@NonNull View page, float position) {
                Log.i("aaa", "transformPage: " + position);
                float scale;

                if (position <= 0 || position >= 2) { //界外
                    page.setScaleX(MIN_SCALE);
                    page.setScaleY(MIN_SCALE);
                } else if (position < 1) { //左1f
                    scale = MIN_SCALE + 0.2f * (position - 0);
                    page.setScaleX(scale);
                    page.setScaleY(scale);
                } else {//右1
                    scale = MIN_SCALE + 0.2f * (2 - position);
                    page.setScaleX(scale);
                    page.setScaleY(scale);
                }
            }
        });

        viewPager.setPadding(DensityUtil.dip2px(context, 100), 0, DensityUtil.dip2px(context, 100), 0);
        viewPager.setClipToPadding(false);
        viewPager.setCurrentItem(0);
    }


    @OnClick(R.id.button)
    public void onViewClicked() {
        /*属性动画*/

        init();

        ValueAnimator animator = new ValueAnimator();

        animator.setIntValues(0, 30);
        animator.setDuration(3000);
        animator.setInterpolator(new DecelerateInterpolator()); //先加速后减速
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int posi = (int) animation.getAnimatedValue();
                viewPager.setCurrentItem(posi);
            }
        });
        animator.start();
    }

        /**
         * 轮播轮子的adapter
         */
        private class WheelAdapter extends PagerAdapter {
            private List<String> urls;

            public WheelAdapter(List<String> urls) {
                this.urls = urls;
            }

            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                RelativeLayout.LayoutParams rparams =
                        new RelativeLayout.LayoutParams(DensityUtil.dip2px(context, 100), DensityUtil.dip2px(context, 100));

                //根据style返回对应view类型
                ImageView imageView = new ImageView(context);
    //            rparams.setMargins(DensityUtil.dip2px(context,10), DensityUtil.dip2px(context,10),
    //                    DensityUtil.dip2px(context,10),DensityUtil.dip2px(context,10));
                imageView.setLayoutParams(rparams);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                int posi = position % urls.size();
                Glide.with(context).load(urls.get(posi)).into(imageView);
                container.addView(imageView);
                GradientDrawable gd = new GradientDrawable();
                gd.setColor(Color.WHITE);
                gd.setCornerRadius(DensityUtil.dip2px(context, 10));
                imageView.setBackground(gd);
    //            container.setTag(posi);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(position);
                    }
                });
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        }
}
