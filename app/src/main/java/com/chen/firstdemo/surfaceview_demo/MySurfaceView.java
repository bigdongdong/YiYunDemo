package com.chen.firstdemo.surfaceview_demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

import com.chen.firstdemo.utils.DensityUtil;
import com.chen.firstdemo.utils.ScreenUtil;

public class MySurfaceView extends BaseSurfaceView {

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        path = new Path();
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

    }


    private int x , y ;
    private Path path ;
    private Paint paint ;
    @Override
    public void doDraw(Canvas c) {
        if(x < ScreenUtil.getScreenWidth(mContext)){
            c.drawColor(Color.WHITE);
            c.drawPath(path,paint);
//            path.moveTo(x,y);
            x+=1;
            y=(int)(100*Math.cos(x*2*Math.PI/180)+200);
            path.lineTo(x,y);
        }
    }
}
