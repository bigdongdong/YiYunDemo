package com.chen.firstdemo.matrix_demo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.chen.firstdemo.R;

public class MatrixSetPolyToPolyTest extends View {

    private Bitmap mBitmap;             // 要绘制的图片
    private Matrix mPolyMatrix;         // 测试setPolyToPoly用的Matrix

    public MatrixSetPolyToPolyTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.beauty_3);

    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);


        vw = r - l ;
        vh = b - t ;

    }

    int vw , vh ;


    public void initBitmapAndMatrix() {
        isInitialize = false ;
        mPolyMatrix = new Matrix();
        float[] src = {0,0,
                vw,0,
                vw,vh,
                0,vh};
        /*属性动画*/
        ValueAnimator animator = new ValueAnimator();
        animator.setIntValues(0,100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                float[] dst ;
                if(value <= 50){
                    dst = new float[]{0,0, vw,value, vw,vh, 0,vh};
                }else{
                    dst = new float[]{0,0, vw,50, vw,vh-(value - 50), 0,vh};
                }

                mPolyMatrix.setPolyToPoly(src, 0, dst, 0, 4);
                invalidate();
            }
        });
        animator.setDuration(3000);
        animator.start();
    }

    private boolean isInitialize = true ;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(isInitialize){
            // 根据Matrix绘制一个变换后的图片
            canvas.drawBitmap(mBitmap,0,0,null);
        }else{
            // 根据Matrix绘制一个变换后的图片
            canvas.drawBitmap(mBitmap, mPolyMatrix, null);
        }

    }
}