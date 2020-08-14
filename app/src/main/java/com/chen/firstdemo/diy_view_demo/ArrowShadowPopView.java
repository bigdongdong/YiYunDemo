package com.chen.firstdemo.diy_view_demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.view.View;
import android.widget.LinearLayout;

import com.chen.firstdemo.utils.DensityUtil;

public class ArrowShadowPopView extends LinearLayout {
    private Context context ;
    public ArrowShadowPopView(Context context) {
        super(context);
        this.context = context ;
        this.setWillNotDraw(false);
        this.setClipToPadding(false);
        //关闭硬件加速
//        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        this.setBackground(new ShapeDrawable(new TopArrowWhiteShadowShape()));

    }

    class TopArrowWhiteShadowShape extends Shape {
        private final int aw ,ah ,r;
        private final int inner ;
        private int w,h;
        private Path path ;
        private Paint paint ;
        private RectF rect;

        public TopArrowWhiteShadowShape() {
            inner = DensityUtil.dip2px(context,5);
//        this.setPadding(padding,padding,padding,padding);

            aw = DensityUtil.dip2px(context,10);
            ah = DensityUtil.dip2px(context,5);
            r = DensityUtil.dip2px(context,15);

            path = new Path();
            rect = new RectF();

            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setShadowLayer(inner,0,0,Color.parseColor("#22111111"));
        }

        @Override
        protected void onResize(float width, float height) {
            super.onResize(width, height);
            w = getMeasuredWidth() - inner * 2;
            h = getMeasuredHeight() - inner * 2;

            final int space = (w - aw - r - r ) / 2;

            path.reset();
            path.moveTo(0,ah+r);
            rect.set(0,ah,r*2,ah+r*2);
            path.arcTo(rect,180,90);
            path.lineTo(r+space,ah);
            path.lineTo(w/2,0);
            path.lineTo(r+space+aw,ah);
            path.lineTo(w-r,ah);
            rect.set(w-r*2,ah,w,ah+r*2);
            path.arcTo(rect,270,90);
            path.lineTo(w,h-r);
            rect.set(w-r*2,h-r*2,w,h);
            path.arcTo(rect,0,90);
            path.lineTo(r,h);
            rect.set(0,h-r*2,r*2,h);
            path.arcTo(rect,90,90);
            path.close();

        }

        @Override
        public void draw(Canvas canvas, Paint p) {
            canvas.save();
            canvas.translate(inner,inner);
            canvas.drawPath(path,paint);
            canvas.restore();
        }
    }
}
