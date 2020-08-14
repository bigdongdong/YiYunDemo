package com.chen.firstdemo.new_beiyu;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewBeiyuActivity extends BaseActivity {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.nbpv1)
    NewBeiyuProgressView nbpv1;
    @BindView(R.id.nbpv2)
    NewBeiyuProgressView nbpv2;
    @BindView(R.id.pv)
    ProgressView pv;
    @BindView(R.id.ipv)
    IntegralProgressView ipv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_beiyu);
        ButterKnife.bind(this);

//        an();
        pv.setProcess(76, 100);
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        an();
    }

    private void an() {
        final int total = 100;

        AnimatorSet as = new AnimatorSet();
        as.setInterpolator(new DecelerateInterpolator(2));
        as.setDuration(1000);

        ValueAnimator va = new ValueAnimator();
        va.setIntValues(0, 80);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final int value = (int) animation.getAnimatedValue();
                nbpv1.value(value, total);
            }
        });

        ValueAnimator va1 = new ValueAnimator();
        va1.setIntValues(0, 60);
        va1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final int value = (int) animation.getAnimatedValue();
                nbpv2.value(value, total);
            }
        });

        ValueAnimator va2 = new ValueAnimator();
        va2.setIntValues(0, 75);
        va2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final int value = (int) animation.getAnimatedValue();
                pv.setProcess(value, total);
            }
        });

        as.setStartDelay(100);
        as.playTogether(va, va1, va2);
        as.start();
    }
}
