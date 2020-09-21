package com.chen.firstdemo.recyclers.kotlin;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.shapes.Shape;


/**
 * create by chenxiaodong on 2020/8/10
 * 圆角的带阴影背景shape
 * 使用需要view关闭硬件加速：
 * view.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
 *
 */
public class WhiteRoundShadowShape extends Shape {

    private int conner ; //圆角半径
    private int inner ; //留给阴影的半径

    private int w,h;
    private Path path ;
    private Paint paint ;
    private RectF rect;

    public WhiteRoundShadowShape(int conner, int inner) {
        this.conner = conner;
        this.inner = inner;

        path = new Path();
        rect = new RectF();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setShadowLayer(inner,0,0,Color.parseColor("#33111111"));
    }

    @Override
    protected void onResize(float width, float height) {
        super.onResize(width, height);
        w = (int) width;
        h = (int) height;

        rect.set(inner,inner,w-inner,h-inner);
        path.reset();
        path.addRoundRect(rect,conner,conner, Path.Direction.CW);

    }

    @Override
    public void draw(Canvas canvas, Paint p) {
        canvas.save();
        canvas.drawPath(path,paint);
        canvas.restore();
    }
}
