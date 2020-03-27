package com.chen.firstdemo.chen_jin_pop_demo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;
import com.chen.firstdemo.utils.DensityUtil;
import com.chen.firstdemo.utils.StatusBarUtil;

import java.text.NumberFormat;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChenjinPopActivity extends BaseActivity {

    @BindView(R.id.popRL)
    RelativeLayout popRL;
    @BindView(R.id.show)
    Button show;
    @BindView(R.id.dismiss)
    Button dismiss;
    @BindView(R.id.popMainLL)
    LinearLayout popMainLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chenjin_pop);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            fullScreen(this);
            StatusBarUtil.StatusBarLightMode(this);
            this.getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        }


        TopPop pop = new TopPop(popMainLL,popRL);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.show();

            }
        });
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        });
    }

    class TopPop {
        View parentView ,popView;
        ValueAnimator tAnimator , bAnimator;
        AnimatorSet set ;

        public TopPop(View parentView , View popView) {
            this.parentView = parentView;
            this.popView = popView;


            /*初始化时pop隐藏*/
            this.parentView.setVisibility(View.GONE);

            parentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

        }

        public void show() {
            parentView.setVisibility(View.VISIBLE);
            set = new AnimatorSet();
            set.setDuration(400);
            tAnimator = ObjectAnimator.ofFloat(popView,"translationY",-DensityUtil.dip2px(context, 300), 0);
            tAnimator.setInterpolator(new OvershootInterpolator(0.7f));
            bAnimator = ObjectAnimator.ofInt(popMainLL,"backgroundColor",Color.TRANSPARENT,Color.parseColor("#77111111"));
            bAnimator.setEvaluator(new ArgbEvaluator());//如果要颜色渐变必须要ArgbEvaluator，来实现颜色之间的平滑变化，否则会出现颜色不规则跳动
            bAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            set.playTogether(tAnimator,bAnimator);
            set.start();
        }

        public void dismiss() {
            set = new AnimatorSet();
            set.setDuration(400);
            tAnimator = ObjectAnimator.ofFloat(popView,"translationY", 0,-DensityUtil.dip2px(context, 300));
            tAnimator.setInterpolator(new OvershootInterpolator(0.7f));
            bAnimator = ObjectAnimator.ofInt(popMainLL,"backgroundColor",Color.parseColor("#77111111"),Color.TRANSPARENT);
            bAnimator.setEvaluator(new ArgbEvaluator());//如果要颜色渐变必须要ArgbEvaluator，来实现颜色之间的平滑变化，否则会出现颜色不规则跳动
            bAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            set.playTogether(tAnimator,bAnimator);
            set.start();
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    parentView.setVisibility(View.GONE);
                }
            });

        }
    }


    /**
     * 通过设置全屏，设置状态栏透明
     *
     * @param activity
     */
    public static void fullScreen(Activity activity) {
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
//                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
//                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }
}
