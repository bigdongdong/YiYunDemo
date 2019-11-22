package com.chen.firstdemo.floating_window_demo.floating_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.util.Log;
import android.util.Property;
import android.widget.RelativeLayout;

import com.chen.firstdemo.R;

public class MyFloatingView extends RelativeLayout {

    private final String TAG = "aaa";
    private FloatingShape shape ;
    private Drawable drawable ;
    private int offVal = 10 ;

    private StyleEnum styleEnum ;

    public enum StyleEnum{
        OLD,NEW
        ;
    }

    public MyFloatingView(Context context, StyleEnum styleEnum) {
        super(context);
        this.styleEnum = styleEnum;

        this.setBackgroundColor(Color.TRANSPARENT);

        shape = new FloatingShape(styleEnum);
        drawable = new ShapeDrawable(shape);
        this.setBackground(drawable);
        this.removeAllViews();
    }
    /**
     * 背景的高度完全取决于宽度
     */
    class FloatingShape extends Shape{
        private StyleEnum styleEnum ;

        public FloatingShape(StyleEnum styleEnum) {
            this.styleEnum = styleEnum;
        }

        private Path path = new Path();

        private float width,height;

        private  float bigCicleRadius;
        private  float smallCicleRadius;
        private float centerHeight ;

//        private float bigImageRadius ;
//        private float smallImageRadius ;

//        private float fontSize ;

        private void designPath(){
            path.reset();

            path.moveTo(0,0);

            /**
             * Path.Direction.CW  顺时针
             * Path.Direction.CCW   逆时针
             */


            //大圆
            path.addCircle(bigCicleRadius,bigCicleRadius,bigCicleRadius,Path.Direction.CW);


            //上面的直线
            if(styleEnum!=null && styleEnum==StyleEnum.NEW){
                path.moveTo(bigCicleRadius,0);
            }else{
                path.moveTo(bigCicleRadius,(height-centerHeight)/2);
            }
            path.lineTo(width-smallCicleRadius,(height-centerHeight)/2);

            //右侧小半圆
            RectF rectF = new RectF();
            rectF.set(width-smallCicleRadius*2,(height-centerHeight)/2,width,(height-centerHeight)/2+centerHeight);
            path.arcTo(rectF,270,180);

            //下面的直线，从右向左画
            if(styleEnum!=null && styleEnum==StyleEnum.NEW){
                path.lineTo(bigCicleRadius,height);
            }else{
                path.lineTo(bigCicleRadius,(height-centerHeight)/2+centerHeight);
            }

        }

        @Override
        protected void onResize(float width, float height) {
            super.onResize(width, height);

            this.width = width ;

            bigCicleRadius = (160f/426f)/2 * width ;
            smallCicleRadius = (117f/426f)/2 * width ;
            centerHeight = smallCicleRadius * 2 ;
//            viewHeight = bigCicleRadius * 2;
//            bigImageRadius = (126f/426f)/2 * width ;
//            smallImageRadius = (53f/426f)/2 * width ;
//            fontSize = (40f/426f)/2 * width ;

            this.height = bigCicleRadius*2;

            designPath(); //设计好path
        }

        @Override
        public void draw(Canvas canvas, Paint paint) {

//            canvas.save();
            //绘制path
            canvas.drawPath(path,getFillPaint(paint));

//            canvas.restore();
        }

        /**
         * 画笔，填充、白色、抗锯齿
         * @return
         */
        private Paint getFillPaint(Paint paint){
            paint.setShadowLayer(5,0,0,Color.parseColor("#20111111"));
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            return paint;
        }
    }
}
