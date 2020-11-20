package com.chen.firstdemo.matryoshka;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.Image;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.RoundImageView;

import java.io.Serializable;

public class MatryoshkaView extends ViewGroup {
    private final String TAG = "MatryoshkaView";
//    private Bitmap mBitmap ;
    private RetractViewBean[] mViews ;

    //数量
    private final int count = 20 ;

    //最大最小边长
    private final float MH = 400f ;
    private final float mH = 50f ;
    private final float IV = (MH - mH)/(count - 1)/2;
    private final float IR = 0.9f ;

    public MatryoshkaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

//        mBitmap = BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher);
        mViews = new RetractViewBean[count];

        ImageView view ;
        LayoutParams p ;
        RetractViewBean rvb ;
        for (int i = 0; i < count; i++) {
            int w = (int) nW(i);

            view = new ImageView(context);
            p = new LayoutParams(w,w);
            view.setLayoutParams(p);
            view.setImageResource(R.mipmap.rank_iv_cover_1);

            rvb = new RetractViewBean(view,i,w,(int)nx(i),(int)ny(i),(int)nD(i));

            mViews[i] = rvb ;
            this.addView(view);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int newWidthMeasureSpec = MeasureSpec.makeMeasureSpec((int)nW(0),MeasureSpec.getMode(widthMeasureSpec));
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec((int)sumnH(count-1),MeasureSpec.getMode(heightMeasureSpec));
        super.onMeasure(newWidthMeasureSpec, newHeightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        RetractViewBean rvb ;
        for (int i = 0; i < count; i++) {
            rvb = mViews[i];
            rvb.view.layout(rvb.x,rvb.y,rvb.x+rvb.width,rvb.y+rvb.width);
        }

        for (int i = count-1; i >= 0; i--) {
            rvb = mViews[i];
            rvb.view.bringToFront();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    private float nW(int n){
        return MH-n*IV*2;
    }

    private float sumnH(int n){
        return MH+n*(1f-IR)*(MH-(IV*(n+1)));
    }

    private float nCy(int n){
        return sumnH(n) - nW(n)/2 ;
    }

    private float nD(int n){
        return nCy(n)-MH/2;
    }

    private float nx(int n){
        return n*IV;
    }

    private float ny(int n){
        return nCy(n) - nW(n)/2 ;
    }

    private class RetractViewBean implements Serializable {
        private ImageView view ;
        private int position ;
        private int width ;
        private int x ;
        private int y ;
        private int distance ; //全部收起或全部展开所需要的距离

        public RetractViewBean(ImageView view, int position, int width, int x, int y, int distance) {
            this.view = view;
            this.position = position;
            this.width = width;
            this.x = x;
            this.y = y;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return " position=" + position +
                    ", width=" + width +
                    ", x=" + x +
                    ", y=" + y +
                    ", distance=" + distance +
                    '}';
        }
    }


    public void shrink(){
        RetractViewBean rvb ;
        AnimatorSet as = new AnimatorSet();
        Animator[] animators = new Animator[count];
        for (int i = 0; i < count; i++) {
            rvb = mViews[i];
            animators[i] = ObjectAnimator.ofFloat(rvb.view,"translationY",0,-rvb.distance);
        }
        as.playTogether(animators);
        as.setInterpolator(new DecelerateInterpolator());
        as.setDuration(1500);
        as.start();
    }

    public void zoom(){
        RetractViewBean rvb ;
        AnimatorSet as = new AnimatorSet();
        Animator[] animators = new Animator[count];
        for (int i = 0; i < count; i++) {
            rvb = mViews[i];
            animators[i] = ObjectAnimator.ofFloat(rvb.view,"translationY",-rvb.distance,0);
        }
        as.playTogether(animators);
        as.setInterpolator(new DecelerateInterpolator());
        as.setDuration(1500);
        as.start();
    }
}
