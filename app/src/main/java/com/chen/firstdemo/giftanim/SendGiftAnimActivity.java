package com.chen.firstdemo.giftanim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;
import com.chen.firstdemo.utils.DensityUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SendGiftAnimActivity extends BaseActivity {

    @BindView(R.id.viewsRL)
    RelativeLayout viewsRL;
    @BindView(R.id.button3)
    Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_gift_anim);
        ButterKnife.bind(this);

        final int upperTransY = -DensityUtil.dip2px(context, 200);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int count = 5;
                List<View> views = new ArrayList<>();
                RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(-1, -1);
                for (int i = 0; i < count; i++) {
                    View v = new View(context);
                    v.setLayoutParams(rl);
                    v.setBackgroundResource(R.mipmap.ic_launcher);
                    viewsRL.addView(v);
                    v.setVisibility(View.GONE);
                    views.add(v);
                }
                /*最小缩放比例*/
                final float MIN_SCALE = 0.5F;
                /*最大透明度*/
                final float MIN_ALPHA = 0.5F;

                /*第一阶段*/
                Collections.reverse(views);
                AnimatorSet as1 = new AnimatorSet();
                Animator[] ass1 = new Animator[count];
                final float space = (0.8f - 0.1f) / count;
                for (int i = 0; i < views.size(); i++) {
                    View v = views.get(i);
                    AnimatorSet as = new AnimatorSet();
                    final float scale = 0.8f - i * space;
                    ObjectAnimator translate = ObjectAnimator.ofFloat(v, "translationY", 0, upperTransY);
                    ObjectAnimator scaleX = ObjectAnimator.ofFloat(v, "scaleX", scale, 1.0f);
                    ObjectAnimator scaleY = ObjectAnimator.ofFloat(v, "scaleY", scale, 1.0f);
                    ObjectAnimator alpha = ObjectAnimator.ofFloat(v, "alpha", MIN_ALPHA, 1f);
                    translate.setInterpolator(new DecelerateInterpolator(0.5f));
                    as.playTogether(translate, scaleX, scaleY, alpha);
                    as.setStartDelay(200 * i);
                    ass1[i] = as;
                }
                as1.playTogether(ass1);
                as1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        for (int i = 0; i < viewsRL.getChildCount(); i++) {
                            View v = viewsRL.getChildAt(i);
                            v.setScaleX(0f);
                            v.setScaleY(0f);
                            v.setAlpha(0f);
                            v.setVisibility(View.VISIBLE);
                        }
                    }
                });

                /*第二阶段*/
                /*随机生成移动坐标*/
                final int transX = (int) (Math.random() * DensityUtil.dip2px(context, 200) * (((Math.random() * 100) > 50) ? 1 : -1));
                final int transY = (int) (Math.random() * DensityUtil.dip2px(context, 200) * (((Math.random() * 100) > 50) ? 1 : -1));
                Collections.reverse(views);
                AnimatorSet as2 = new AnimatorSet();
                Animator[] ass2 = new Animator[count];
                for (int i = 0; i < views.size(); i++) {
                    final View v = views.get(i);
                    AnimatorSet as = new AnimatorSet();
                    ObjectAnimator translateX = ObjectAnimator.ofFloat(v, "translationX", 0, transX);
                    ObjectAnimator translateY = ObjectAnimator.ofFloat(v, "translationY", upperTransY, upperTransY + transY);
                    ObjectAnimator scaleX = ObjectAnimator.ofFloat(v, "scaleX", 1.0f, MIN_SCALE);
                    ObjectAnimator scaleY = ObjectAnimator.ofFloat(v, "scaleY", 1.0f, MIN_SCALE);
                    ObjectAnimator alpha = ObjectAnimator.ofFloat(v, "alpha", 1f, MIN_ALPHA);
                    as.playTogether(translateX, translateY, scaleX, scaleY, alpha);
                    as.setStartDelay(200 * i);
                    as.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            v.setVisibility(View.GONE);
                        }
                    });
                    ass2[i] = as;
                }
                as2.playTogether(ass2);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playSequentially(as1, as2);
                animatorSet.setDuration(1000);
                animatorSet.start();
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        for (View v : views) {
                            viewsRL.removeView(v);
                        }
                    }
                });
            }
        });

        //        TimerTask tt = new TimerTask() {
//            @Override
//            public void run() {
//                context.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        button3.callOnClick();
//                    }
//                });
//            }
//        };
//        new Timer().schedule(tt,100,300);
    }
}