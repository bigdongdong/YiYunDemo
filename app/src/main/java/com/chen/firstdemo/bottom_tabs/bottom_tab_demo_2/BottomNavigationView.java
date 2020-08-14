package com.chen.firstdemo.bottom_tabs.bottom_tab_demo_2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.RelativeLayout;

public class BottomNavigationView extends RelativeLayout {


    float bili ;
    int viewHeight ;
    int bgHeight ;
    int bgPositionY ;
    int screenWidth ;
    Activity context ;

    public BottomNavigationView(Context context) {
        super(context);
        this.context = (Activity)context ;

        screenWidth = getScreenWidth() ;
        bili =  (getScreenWidth()*1.0f) / 1500f;

        viewHeight = (int) (310 * bili);
        bgPositionY = (int) (105 * bili);
        bgHeight = (int) (205 * bili);

        Log.i("aaa", "viewHeight: "+viewHeight);
        Log.i("aaa", "bgPositionY: "+bgPositionY);
        Log.i("aaa", "bgHeight: "+bgHeight);

        this.setLayoutParams(new LayoutParams(-1,viewHeight));

        BottomTabView2 tabView2 = new BottomTabView2(this.context , bgHeight) {
            @Override
            protected void onTabSelectedListener(int position) {

            }
        };
        LayoutParams params = new LayoutParams(-1,bgHeight);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        tabView2.setLayoutParams(params);

        this.addView(tabView2);

        this.setBackground(new ShapeDrawable(new MyShape()));
    }


    class MyShape extends Shape {

        Path path = new Path();  //填充path

        /*制定path的路线*/
        private void resizePath(){
            path.reset();
            path.moveTo(0,bgPositionY);

            path.lineTo(575*bili,bgPositionY);

            /*第一段贝塞尔曲线*/
            path.cubicTo((575 + 60) * bili,bgPositionY,
                    (575 + 60) * bili,bgPositionY+ (180 * bili),
                    (575 + 180) * bili,bgPositionY+ (180* bili) );

            /*第二段贝塞尔曲线*/
            path.cubicTo((575 + 60 + 120 + 120) * bili,bgPositionY+ (180* bili),
                    (575 + 60 + 120 + 120) * bili,bgPositionY,
                    (575 +  60 + 120 + 120 + 60) * bili,bgPositionY);

            path.lineTo(screenWidth,bgPositionY);
            path.lineTo(screenWidth,viewHeight);
            path.lineTo(0,viewHeight);


            /*闭合路径*/
            path.close();

        }

        @Override
        protected void onResize(float width, float height) {
            super.onResize(width, height);

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

    /**
     * 获取屏幕宽度 px
     * @return
     */
    public int getScreenWidth(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return  displaymetrics.widthPixels;
    }
}
