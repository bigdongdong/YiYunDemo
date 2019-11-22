package com.chen.firstdemo.diy_view_demo.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.shapes.Shape;


/**
 * 使用此shape的view要先关闭硬件加速
 * View.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
 */
public class ShadowShape extends Shape {
    private float shadowRadiation ;//阴影的辐射范围
    private int shadowColor ;
    private int conner ; //背景的圆角
    private int backgroundColor ;

    private float width , height ;


    public ShadowShape(float shadowRadiation, int shadowColor, int backgroundColor) {
        this.shadowRadiation = shadowRadiation;
        this.shadowColor = shadowColor;
        this.backgroundColor = backgroundColor;
        this.conner = 0;
    }

    public ShadowShape(float shadowRadiation, int shadowColor, int conner, int backgroundColor) {
        this.shadowRadiation = shadowRadiation;
        this.shadowColor = shadowColor;
        this.conner = conner;
        this.backgroundColor = backgroundColor;
    }

    private Path designPath(){
        Path path = new Path();
        RectF rectF = new RectF();

        path.reset();

        rectF.set(shadowRadiation,shadowRadiation,conner+shadowRadiation,conner+shadowRadiation);
        path.arcTo(rectF,180,90);

        path.lineTo(width-conner-shadowRadiation,shadowRadiation);
        rectF.set(width-conner-shadowRadiation,shadowRadiation,width-shadowRadiation,conner+shadowRadiation);
        path.arcTo(rectF,270,90);

        path.lineTo(width-shadowRadiation,height-conner-shadowRadiation);
        rectF.set(width-conner-shadowRadiation,height-conner-shadowRadiation,width-shadowRadiation,height-shadowRadiation);
        path.arcTo(rectF,0,90);

        path.lineTo(conner+shadowRadiation,height-shadowRadiation);
        rectF.set(shadowRadiation,height-conner-shadowRadiation,conner+shadowRadiation,height-shadowRadiation);
        path.arcTo(rectF,90,90);

        path.close();

        return path ;

    }

    @Override
    protected void onResize(float width, float height) {
        super.onResize(width, height);

        this.width = width ;
        this.height = height ;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.reset();
        paint.setColor(backgroundColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setShadowLayer(shadowRadiation,0,0,shadowColor);

//        paint.setShader()

        canvas.drawPath(designPath(),paint);
        canvas.restore();

    }
}
