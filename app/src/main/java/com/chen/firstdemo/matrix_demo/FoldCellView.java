package com.chen.firstdemo.matrix_demo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chen.firstdemo.R;

import java.util.ArrayList;
import java.util.List;

public class FoldCellView extends RelativeLayout {

    private final String TAG = "aaa";
    private Context context ;
    private LinearLayout ll ;
    private View coverView ;
    public FoldCellView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context ;
        this.setBackgroundColor(Color.parseColor("#20111111"));

    }

    private List<View> llChilds ;
    private int llc0H ;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(getChildCount() == 0){
            return;
        }
        if(!(getChildAt(0) instanceof LinearLayout)){
            return;
        }

        /*获取第一子View : ll */
        ll = (LinearLayout) getChildAt(0);

        if( ll.getChildCount() == 0){
            return;
        }

        /*获取初始的高度*/
        int llc0W = ll.getChildAt(0).getMeasuredWidth();
        llc0H = ll.getChildAt(0).getMeasuredHeight();

        ll.getLayoutParams().height = llc0H ;  //让ll的高度跟第一个子View等高
        this.getLayoutParams().height = llc0H ; //让this的高度做自适应

        Log.i(TAG, "onLayout: llc0H = "+llc0H);
        Log.i(TAG, "onLayout: ll.getChildCount() = "+ll.getChildCount());

        llChilds = new ArrayList<>();
        for (int i = 0; i < ll.getChildCount(); i++) {
            View v = ll.getChildAt(i);
            llChilds.add(v);
            if(i == 0){
                v.setVisibility(VISIBLE);
            }else{
                v.setVisibility(GONE);
            }
        }

        /*创建coverView*/
        //让cover的宽度跟ll宽度等宽
        //让cover的高度跟ll的第一个子View等高
        coverView = new View(context);
        RelativeLayout.LayoutParams p = new LayoutParams(llc0W,llc0H);
        p.addRule(RelativeLayout.CENTER_HORIZONTAL);
        coverView.setLayoutParams(p);
        coverView.setBackgroundColor(Color.GRAY);
        this.addView(coverView);
        this.bringChildToFront(coverView);
        requestLayout();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

    }

    public void startFold(){
        AnimatorSet animatorSet = new AnimatorSet();
        List<Animator> animatorList = new ArrayList<>();


        for (int i = 0; i < llChilds.size(); i++) {
            final int finalI = i;
            coverView.setPivotY(llc0H * (finalI+1));
            ObjectAnimator animator = ObjectAnimator.ofFloat(coverView,"rotationX",
                    0,-180);
            animator.setDuration(2000);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float val = (float) animation.getAnimatedValue();
                    if(val == -180){
                        //结束动画
                        FoldCellView.this.getLayoutParams().height = llc0H * (finalI+1) ;
                        ll.getLayoutParams().height = llc0H * (finalI+1) ;
                        llChilds.get(finalI).setVisibility(VISIBLE);
                    }
                }
            });
            animatorList.add(animator);
        }
        animatorSet.playSequentially(animatorList);
        animatorSet.start();
    }
}
