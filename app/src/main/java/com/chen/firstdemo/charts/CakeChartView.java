package com.chen.firstdemo.charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.netease.nimlib.sdk.auth.LoginInfo;

public class CakeChartView extends View {
    private Context context ;
    private int w,h ;
    private Path path ;
    private Paint paint ;
    private RectF rect ;

    /*模拟数据*/
    private final int len = 7 ;
    private final int offsetL = 50 ; //移动距离圆心50px
    private final float[] ratios = new float[]{0.1f,0.2f,0.1f,0.1f,0.1f,0.25f,0.15f};
    private final int[] colors = new int[]{Color.YELLOW,Color.BLUE,Color.GRAY,Color.GREEN,Color.YELLOW,Color.BLUE,Color.GRAY};
    private Value[] values ;

    public CakeChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        this.setWillNotDraw(false);
        this.setClickable(true);
        path = new Path();
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        rect = new RectF();

        /*组装数据*/
        values = new Value[len];
        float angle = 0 ;
        for (int i = 0; i < len; i++) {
            Value v = new Value();
            v.angle = ratios[i] *360;
            v.color = colors[i];
            v.centerAngle = angle + v.angle / 2;
            //计算位移方向和距离
            float tempAngle = 0 ;
            int directionX = 1 ; //位移x的方向
            int directionY = 1 ; //位移y的方向
            if(v.centerAngle >0 && v.centerAngle < 90){
                //第一象限
                tempAngle = v.centerAngle ;
                directionX = 1 ;
                directionY = -1 ;
            }else if(v.centerAngle > 90 && v.centerAngle < 180){
                //第二象限
                tempAngle = 180 - v.centerAngle ;
                directionX = 1 ;
                directionY = 1 ;
            }else if(v.centerAngle > 180 && v.centerAngle < 270){
                //第三象限
                tempAngle = v.centerAngle - 180;
                directionX = -1 ;
                directionY = 1 ;
            }else{
                //第四象限
                tempAngle = 360 - v.centerAngle ;
                directionX = -1 ;
                directionY = -1 ;
            }

            v.dx = (float) (Math.sin(tempAngle* Math.PI/180) * offsetL * directionX);
            v.dy = (float) (Math.cos(tempAngle* Math.PI/180) * offsetL * directionY);

            values[i] = v ;
            angle = (angle + v.angle) % 360;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        w = getMeasuredWidth();
        h = getMeasuredHeight();
    }

    private float curSelectedAngle = -10 ;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                float x = event.getX();
                float y = event.getY();
                int dinstance = (int) Math.sqrt(Math.pow(x-w/2,2)+Math.pow(y-h/2,2));
                int dx = (int) Math.abs(x - w/2);
                int dy = (int) Math.abs(y - h/2);
                if(dinstance <= w/2){
                    double a = Math.atan2(dx,dy) * 180 / Math.PI;
                    if(x >= w/2){
                        if(y <= h/2){
                            //第一象限
                            a = a ;
                        }else{
                            //第二象限
                            a = 180 - a;
                        }
                    }else{
                        if(y >= h/2){
                            //第三象限
                            a += 180 ;
                        }else{
                            //第四象限
                            a = 360 - a ;
                        }
                    }

                    curSelectedAngle = (float) a;
                    invalidate();
                }
                break;
        }

        return true;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float startAngle = 270 ; //画笔起画角度
        float posiAngle = 0 ;
        for (int i = 0; i < len; i++) {
            Value v = values[i];
            float curAngle = v.angle;
            canvas.save();
            rect.set(0,0,w,h);
            if(curSelectedAngle >= posiAngle && curSelectedAngle <= posiAngle+curAngle){
                /*位移*/
                rect.offset(v.dx,v.dy);
                v.isOut = true ;
//                if(v.isOut){
//                    v.isOut = false ;
//                }else{
//
//                }
            }

            path.reset();
            path.arcTo(rect,startAngle,curAngle);
            path.lineTo(rect.centerX(),rect.centerY());
            path.close();
            canvas.clipPath(path);
            canvas.drawColor(v.color);
            canvas.restore();
            startAngle = (startAngle + curAngle)%360;
            posiAngle = (posiAngle + curAngle)%360;
        }
    }

    private class Value{
        private float angle;
        private float centerAngle ;
        private float dx ;
        private float dy ;
        private int color ;
        private boolean isOut = false ;

        @NonNull
        @Override
        public String toString() {
            return ""+angle + '\n' + centerAngle +'\n' + dx +'\n'+dy;
        }
    }


//    /**
//     * 某个点旋转一定角度后，得到一个新的点
//     * @param p 初始点
//     * @param angle 旋转角度
//     * @return
//     */
//    private Point calcNewPoint(float angle) {
//        Point p = new Point(w/2,0);
//        Point pCenter = new Point(w/2,h/2) ;
//        // calc arc
//        float l = (float) ((angle * Math.PI) / 180);
//
//        //sin/cos value
//        float cosv = (float) Math.cos(l);
//        float sinv = (float) Math.sin(l);
//
//        // calc new point
//        float newX = (p.x - pCenter.x) * cosv - (p.y - pCenter.y) * sinv + pCenter.x;
//        float newY = (p.x - pCenter.x) * sinv + (p.y - pCenter.y) * cosv + pCenter.y;
//        return new Point((int) newX, (int) newY);
//    }
}
