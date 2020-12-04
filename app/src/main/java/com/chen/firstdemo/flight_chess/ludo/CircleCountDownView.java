package com.chen.firstdemo.flight_chess.ludo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * create by chenxiaodong on 2020/11/24
 * 倒计时view
 */
public class CircleCountDownView extends View {
    private int W,H ;
    private RectF rect ;
    private CountDownTimer ctimer ;
    private float lastAngle = 360f;

    public CircleCountDownView(Context context) {
        super(context);
        this.setWillNotDraw(false);
    }

    public CircleCountDownView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setWillNotDraw(false);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(W != getMeasuredWidth()){
            W = H = getMeasuredWidth();
            rect = new RectF(0,0,W,H);
        }
    }

    public void countDown(long ms,final OnTimerListener listener){
        if(ms <= 0){
            return;
        }else{
            if(ctimer != null){
                ctimer.cancel();
            }

            ctimer = new CountDownTimer(ms,1) {
                @Override
                public void onTick(long millisUntilFinished) {
                    /*刷新界面*/
                    lastAngle = millisUntilFinished * 1.0f / ms * 360 ;
                    postInvalidate();
                }

                @Override
                public void onFinish() {
                    lastAngle = 0f ;
                    postInvalidate();

                    if(listener != null){
                        listener.overTime();
                    }
                }
            };
            lastAngle = 360f ;
            ctimer.start();
        }
    }

    public void cancel(){
        if(ctimer != null){
            ctimer.cancel();

            lastAngle = 0f ;
            postInvalidate();
        }
    }

    private Paint paint;
    private Paint getPaint(){
        if(paint == null){
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(0x55111111);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
        }
        return paint;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(lastAngle == 360f){
            canvas.drawCircle(W/2,H/2,W/2,getPaint());
        }else if(lastAngle > 0){
            Path path = new Path();
            path.moveTo(W/2,H/2);
            path.arcTo(rect,270,lastAngle);
            canvas.drawPath(path,getPaint());
        }
    }

    public interface OnTimerListener{
        void overTime();
    }
}
