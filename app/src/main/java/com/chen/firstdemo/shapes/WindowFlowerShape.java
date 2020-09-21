package com.chen.firstdemo.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.shapes.Shape;

/*窗花shape*/
public class WindowFlowerShape extends Shape {

    int viewWidth ;

    int width ;
    @Override
    protected void onResize(float width, float height) {
        super.onResize(width, height);

        this.viewWidth = (int) width ;
        this.width = (int) width /2  ;
    }

    private Path designOutPath(){
        Path path = new Path();
        RectF rect = new RectF(width,0,viewWidth,width);
        path.moveTo(width,0);
        path.moveTo(width + width/2,0);
        path.arcTo(rect,270 , 90 );
        path.lineTo(viewWidth,0);
        path.close();

        return path ;
    }
    private Path designInPath(){
        Path path = new Path();
        RectF rect = new RectF(width,0,viewWidth,width);

        path.moveTo(width,width);
        path.lineTo(width+width/2,0);
        path.arcTo(rect,270 , 90 );
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
        canvas.drawRect(new RectF(0,0,viewWidth,viewWidth),
                getPaint(Color.parseColor("#30111111")));

        canvas.scale(0.7f,0.7f,width,width);

        float angle = 0 ;
        float a = 36.87f ;

        paint.setStrokeWidth(1);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);

        while (angle < 360f){

            canvas.drawPath(designOutPath(),getPaint(Color.parseColor("#9043FC")));
            canvas.drawPath(designInPath(),getPaint(Color.BLUE));
            canvas.drawPath(designInPath(),paint);
            canvas.rotate(a,width,width);
            angle += a;
        }

    }


}