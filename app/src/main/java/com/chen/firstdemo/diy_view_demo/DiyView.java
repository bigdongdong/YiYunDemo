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

public class DiyView extends View {
    public DiyView(Context context) {
        super(context);

        LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(
                700,300
        );
        this.setLayoutParams(l);
        this.setBackground(new ShapeDrawable(new DiyShape()));

        //关闭硬件加速
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

    }


    class DiyShape extends Shape{

        private Path designPath(){
            Path path = new Path();

            path.addCircle(150f,150f, 100,Path.Direction.CW);

            path.addCircle(550f,150f, 100,Path.Direction.CW);

            path.moveTo(150f,100f);
            path.lineTo(550f,100f);
            path.lineTo(550f,200f);
            path.lineTo(150f,200f);
            path.lineTo(150f,100f);

            path.close();

//            path.addRect(new RectF(150f,100f,550f,200f), Path.Direction.CW);

            return path;

        }


        @Override
        public void draw(Canvas canvas, Paint paint) {
            paint.reset();
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setAntiAlias(true);
            paint.setShadowLayer(15,0,0,Color.GRAY);

            canvas.drawPath(designPath(),paint);
        }
    }
}
