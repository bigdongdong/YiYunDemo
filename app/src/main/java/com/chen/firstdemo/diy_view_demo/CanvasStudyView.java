package com.chen.firstdemo.diy_view_demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.view.View;
import android.widget.LinearLayout;

public class CanvasStudyView extends View {
    public CanvasStudyView(Context context) {
        super(context);

        LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        this.setLayoutParams(l);
        this.setBackground(new ShapeDrawable(new MyShape()));

    }

    class MyShape extends Shape {

        private Path designPath(float marginTop){
            Path path = new Path();

//            path.addRect(new RectF(100,100,500,200), Path.Direction.CW);

            path.moveTo(0,marginTop);
            path.lineTo(500f,marginTop);


            return path;

        }


        @Override
        public void draw(Canvas canvas, Paint paint) {
            paint.reset();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10f);
            paint.setAntiAlias(true);

            paint.setColor(Color.RED);
            canvas.drawPath(designPath(100f),paint);

            canvas.save();
            canvas.rotate(45f,300f,150f);
            paint.setColor(Color.BLACK);
            canvas.drawPath(designPath(150f),paint);
            canvas.restore();

            paint.setColor(Color.BLUE);
            canvas.drawPath(designPath(200f),paint);

        }
    }
}
