package com.chen.firstdemo.hexagon_view_demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class FangleView extends View {

    private int W , H ;

    public FangleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        W =  getMeasuredWidth();
        H = getMeasuredHeight();

    }

    private Point[] getPoints(){
        Point start = new Point(W/2 , 0 + getPaddingTop());

        final float angle = 6 ;
        Point[] points = new Point[(int) (360 / angle)];
        int r = W /2 - getPaddingTop();

        for (int i = 0; i < (360 / angle); i++) {
            float a = (float) (angle * i * Math.PI / 180);
            float sina = (float) Math.sin(a);
            float cosa = (float) Math.cos(a);
            Point p = new Point();
            p.x = (int) (start.x - r * sina);
            p.y = (int) (start.y + r * (1-cosa));
            points[i] = p ;
        }
        return points ;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);

        for(Point p : getPoints()){
            canvas.drawPoint(p.x,p.y,paint);
        }
    }
}
