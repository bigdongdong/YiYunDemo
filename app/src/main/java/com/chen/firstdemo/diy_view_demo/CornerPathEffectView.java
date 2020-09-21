package com.chen.firstdemo.diy_view_demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("DrawAllocation")
public class CornerPathEffectView extends View {

    private int w,h;

    public CornerPathEffectView(Context context) {
        super(context);
        this.setWillNotDraw(false);
        this.setClickable(true);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        w = getMeasuredWidth();
        h = getMeasuredHeight() - 30;
    }


    private List<PointF> points = new ArrayList<>();
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP){
            float x = event.getX();
            float y = event.getY();
            points.add(new PointF(x,y));
            invalidate();
        }
        return super.onTouchEvent(event);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path = new Path();
//        path.moveTo(w/2,0);
//        path.rLineTo(-w/2,h);
//        path.rLineTo(w,0);
//        path.close();
        for (int i = 0; i < points.size(); i++) {
            PointF p = points.get(i);
            Log.i("aaa", "onDraw: "+p.toString());
            if(i == 0){
                path.moveTo(p.x,p.y);
            }else{
                path.lineTo(p.x,p.y);
            }
        }



        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
//        CornerPathEffect cornerPathEffect = new CornerPathEffect(100);
//        paint.setPathEffect(cornerPathEffect);

        canvas.drawPath(path,paint);

//        canvas.clipPath(path);
//        canvas.drawColor(Color.WHITE);
    }
}
