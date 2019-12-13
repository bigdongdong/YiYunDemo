package com.chen.firstdemo.shape_demo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.shapes.Shape;
import android.view.Gravity;

/*viewpager左右角shape*/
public class HornShape extends Shape {

    int width ;
    int gravity ;
    public HornShape(int gravity) {
        this.gravity = gravity ;

    }

    @Override
    protected void onResize(float width, float height) {
        super.onResize(width, height);

        this.width = (int) width ;
    }

    /*获取紫色路径*/
    private Path designPurplePath(){
        Path path = new Path();
        RectF rect ;

        if(gravity == Gravity.LEFT){
            rect = new RectF(0,0,width*2,width*2);
            path.lineTo(0,width);
            path.addArc(rect,180 , 90 );
        }

        if(gravity == Gravity.RIGHT){
            rect = new RectF(-width,0,width,width * 2);
            path.addArc(rect,270 , 90 );
            path.lineTo(width,0);
        }

        path.lineTo(0,0);
        path.close();

        return path ;
    }

    /*获取白色路径*/
    private Path designWhitePath(){
        Path path = new Path();
        RectF rect ;
        if(gravity == Gravity.LEFT){
            rect = new RectF(0,0,width*2,width*2);
            path.addArc(rect,180 , 90 );
            path.lineTo(width,width);
        }

        if(gravity == Gravity.RIGHT){
            rect = new RectF(-width,0,width,width * 2);
            path.addArc(rect,270 , 90 );
            path.lineTo(0,width);
        }
        path.close();

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

        /*紫色伪圆角背景*/
        canvas.drawPath(designPurplePath(),getPaint(Color.parseColor("#9043FC")));
            canvas.drawPath(designWhitePath(),getPaint(Color.BLUE));
    }


}