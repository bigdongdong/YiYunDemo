package com.chen.firstdemo.base;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.shapes.Shape;
import android.view.Gravity;

/*viewpager左右角shape*/
public class HornShape extends Shape {

    private int width ;
    private int color ;
    private Gravity gravity ;

    public enum Gravity{
        TOP_LEFT ,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    }

    public HornShape(Gravity gravity, int color) {
        this.gravity = gravity;
        this.color = color;
    }

    @Override
    protected void onResize(float width, float height) {
        super.onResize(width, height);

        this.width = (int) width ;
    }

    /*获取颜色路径*/
    private Path designPurplePath(){
        Path path = new Path();
        RectF rect ;

        if(gravity == Gravity.TOP_LEFT){
            rect = new RectF(0,0,width*2,width*2);
            path.lineTo(0,width);
            path.addArc(rect,180 , 90 );
            path.lineTo(0,0);
            path.close();
        }

        if(gravity == Gravity.TOP_RIGHT){
            rect = new RectF(-width,0,width,width * 2);
            path.addArc(rect,270 , 90 );
            path.lineTo(width,0);
            path.lineTo(0,0);
            path.close();
        }

        if(gravity == Gravity.BOTTOM_LEFT){
            rect = new RectF(0,-width,width * 2,width);
            path.addArc(rect,90 , 90 );
            path.lineTo(0,width);
            path.lineTo(width,width);
            path.close();
        }

        if(gravity == Gravity.BOTTOM_RIGHT){
            rect = new RectF(-width,-width,width,width);
            path.addArc(rect,0 , 90 );
            path.lineTo(width,width);
            path.lineTo(width,0);
            path.close();
        }

        return path ;
    }

    /*获取填充画笔*/
    private Paint getPaint(int color){
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        return paint ;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawPath(designPurplePath(),getPaint(color));
    }
}