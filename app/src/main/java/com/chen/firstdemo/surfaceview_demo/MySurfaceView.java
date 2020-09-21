package com.chen.firstdemo.surfaceview_demo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.animation.DecelerateInterpolator;

import com.chen.firstdemo.R;
import com.chen.firstdemo.utils.DensityUtil;
import com.chen.firstdemo.utils.ScreenUtil;

public class MySurfaceView extends BaseSurfaceView {

    private Bitmap bitmap ;
//    private Matrix[] matrices = new Matrix[6] ;
    private Matrix matrix ;
    AnimatorSet as = new AnimatorSet();
    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        matrix = new Matrix();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        super.surfaceCreated(holder);
        /*开启路径数据线程*/

        ValueAnimator scale = ValueAnimator.ofFloat(0.5f,1f);
        scale.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                matrix.postScale(1.5f,1.5f);
            }
        });

        ValueAnimator translation = ValueAnimator.ofFloat(0f,300f);
        translation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                matrix.setTranslate((Float) animation.getAnimatedValue(),0);
            }
        });

        as.playTogether(scale);
        as.setDuration(2000);
        as.setInterpolator(new DecelerateInterpolator());
        as.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);

            }
        });
//        as.start();
    }

    @Override
    public void doDraw(Canvas c) {

//        if(x < ScreenUtil.getScreenWidth(mContext)){
//            c.drawColor(Color.WHITE);
//            c.drawPath(path,paint);
////            path.moveTo(x,y);
//            x+=1;
//            y=(int)(100*Math.cos(x*2*Math.PI/180)+200);
//            path.lineTo(x,y);
//        }
        if(!as.isStarted()){
            as.start();
        }

        c.drawBitmap(bitmap,matrix,null);
    }
}
