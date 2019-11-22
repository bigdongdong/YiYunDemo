package com.chen.firstdemo.diy_view_demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.view.View;


@SuppressLint("NewApi")
public class ShadowView extends View {
    public ShadowView(Context context) {
        super(context);

        this.setBackgroundColor(Color.TRANSPARENT);
        ShapeDrawable shapeDrawable = new ShapeDrawable(new CicleShape());
        this.setBackground(shapeDrawable);
        //关闭硬件加速
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    class CicleShape extends Shape{

        @Override
        public void draw(Canvas canvas, Paint paint) {
            paint.setColor(Color.WHITE);
            paint.setShadowLayer(10,0,0,
                    Color.parseColor("#30111111"));

            canvas.drawCircle(this.getWidth()/2,this.getWidth()/2,
                    0.6f*(this.getWidth()/2),paint);
        }
    }
}
