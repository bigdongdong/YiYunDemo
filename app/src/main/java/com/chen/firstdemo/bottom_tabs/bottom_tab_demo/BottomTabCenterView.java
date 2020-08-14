package com.chen.firstdemo.bottom_tabs.bottom_tab_demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.Shape;
import android.widget.RelativeLayout;



public class BottomTabCenterView extends RelativeLayout {
    private StateListDrawable stalistDrawable ;
    private BottomTabCenterShape shape;

    public BottomTabCenterView(Context context) {
        super(context);

        /*不设置不绘制*/
        this.setBackgroundColor(Color.parseColor("#00000000"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //初始化一个空对象
        if(stalistDrawable==null){
            stalistDrawable = new StateListDrawable();
        }
        /*显示的背景*/
        shape = new BottomTabCenterShape();
        stalistDrawable.addState(new int []{}, new ShapeDrawable(shape));

        this.setBackground(stalistDrawable);
    }


    class BottomTabCenterShape extends Shape{

        float height ;
        Path path = new Path();  //填充path

        /*制定path的路线*/
        private void resizePath(){
            path.reset();
            path.moveTo(0,(60f/220f)*height);

            /*第一段圆弧*/
            path.cubicTo((20f/220f)*height,(60f/220f)*height,
                    (20f/220f)*height,(170f/220f)*height,
                    (110f/220f)*height,(170f/220f)*height);

            /*第二段圆弧*/
            path.cubicTo((200f/220f)*height,(170f/220f)*height,
                    (200f/220f)*height,(60f/220f)*height,
                    (220f/220f)*height,(60f/220f)*height);

            /*绘制第一段line*/
            path.lineTo(height,height);

            /*绘制第二段line*/
            path.lineTo(0,height);

            /*闭合路径*/
            path.close();

        }

        @Override
        protected void onResize(float width, float height) {
            super.onResize(width, height);

            this.height = height ;

            resizePath();
        }

        @Override
        public void draw(Canvas canvas, Paint paint) {
            /*记录画布*/
            canvas.save();
            /*绘制填充*/
            drawFill(canvas, paint);
            /*还原画布*/
            canvas.restore();
        }

        /*填充的paint*/
        private void drawFill(Canvas canvas, Paint paint) {
            paint.setColor(Color.parseColor("#FFFFFF"));
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);//抗锯齿
            paint.setDither(true);//抖动
            paint.setStrokeWidth(5);

            canvas.drawPath(path, paint);
        }
    }
}
