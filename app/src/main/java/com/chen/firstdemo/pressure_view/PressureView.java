package com.chen.firstdemo.pressure_view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

/**
 * 压力View 手指按压会有阻尼缩放以及回弹动画
 */
public class PressureView extends FrameLayout {
    private float mScale = 1.0f; //0.75 ~ 0.9 ~ 1.0
    private boolean isFingerOut = true;
    private ValueAnimator autoAnimator ;
    private ValueAnimator manualAnimator ;
    private OnClickListener mOnClickListener ;

    private final float MIN_RATIO = 0.9f ;  //最小比例
    private final float CENTER_RATIO = 0.95f ; //中间比例

    public PressureView(@NonNull Context context) {
        this(context,null);
    }

    public PressureView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.setWillNotDraw(false);
        this.setClickable(true);
    }

    private long lastDownTime ;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(isAnimating()){
            return true ;
        }

        final int action = ev.getAction();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                isFingerOut = false ;
                lastDownTime = System.currentTimeMillis();
                autoAnimate(0);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isFingerOut = true ;
                if(manualAnimator != null && manualAnimator.isRunning()){
                    manualAnimator.cancel();
                }

                final long nowTime = System.currentTimeMillis();
                if(nowTime - lastDownTime < 200){
                    autoAnimate(-1);
                }else{
                    autoAnimate(1);
                }

                break;
        }
        return true;
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        this.mOnClickListener = l ;
    }

    private boolean isAnimating(){
        return (autoAnimator != null && autoAnimator.isRunning())
                && (manualAnimator != null && manualAnimator.isRunning()) ;
    }

    /**
     *
     * @param action 0-down  1-up&cancel -1-quick
     */
    private void autoAnimate(int action){
        if(autoAnimator == null){
            autoAnimator = new ValueAnimator();
            autoAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mScale = (float) animation.getAnimatedValue();
                    PressureView.this.setScaleX(mScale);
                    PressureView.this.setScaleY(mScale);
                }
            });
        }

        switch(action){
            case 0:
                autoAnimator.setFloatValues(1.0f,CENTER_RATIO);
                autoAnimator.setDuration(100);
                break;
            case 1:
                autoAnimator.setFloatValues(mScale,1.0f);
                autoAnimator.setDuration(100);
                break;
            case -1:
                autoAnimator.setFloatValues(mScale,CENTER_RATIO,1.0f);
                autoAnimator.setDuration(200);
                break;
        }

        autoAnimator.removeAllListeners();
        switch(action){
            case 0:
                autoAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //判断手指还在不在上面
                        if(!isFingerOut){
                            //继续手动动画
                            manualAnimate();
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                break;
            case 1:
            case -1:
                if(mOnClickListener != null){
                    autoAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mOnClickListener.onClick(PressureView.this);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                }
                break;
        }

        autoAnimator.start();

    }

    private void manualAnimate(){
        if(manualAnimator == null){
            manualAnimator = new ValueAnimator();
            manualAnimator.setDuration(1500);
            manualAnimator.setInterpolator(new DecelerateInterpolator(2)); //先快后慢，阻尼效果
            manualAnimator.setFloatValues(CENTER_RATIO,MIN_RATIO);
            manualAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mScale = (float) animation.getAnimatedValue();
                    PressureView.this.setScaleX(mScale);
                    PressureView.this.setScaleY(mScale);
                }
            });
        }
        manualAnimator.start();
    }
}
