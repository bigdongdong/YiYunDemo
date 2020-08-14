package com.chen.firstdemo.base;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public abstract class DouYinShadowPopWindow extends ShadowPopupWindow{



    float startY = 0.0f;
    private boolean isAniming = false ;
    public DouYinShadowPopWindow(Activity context) {
        super(context);

        this.view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(isAniming){
                    return false;
                }
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float distance = event.getY() - startY ;
                        v.setTranslationY(distance);
                        Log.i(TAG, "onTouch: ");
                        if(distance > 0 ){ //下拉

                        }else if(distance < 0){ //上滑

                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        float transY = v.getTranslationY();
                        /*属性动画恢复*/
                        ObjectAnimator animator = ObjectAnimator.ofFloat(v,"translationY",transY);
                        animator.start();
                        isAniming = true ;
                        animator.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                isAniming = false;
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                                isAniming = false;
                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                        break;
                }


                return true;
            }
        });
    }
}
