package com.chen.firstdemo.shape_demo.radar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class RadarView extends View {
    public RadarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void showRadar(RadarOptions options){
        this.setBackground(new ShapeDrawable(new RadarShape(options)));
    }

    private class RadarShape extends Shape {
        private RadarOptions options ;

        private int viewWidth ;
        private int halfWidth ;
        private float radarAngle ; //角度 比如 60
        private float halfRadian ; //半弧度值

        public RadarShape(RadarOptions options) {
            this.options = options;
        }

        @Override
        protected void onResize(float width, float height) {
            super.onResize(width, height);

            this.viewWidth = (int) width ;
            this.halfWidth = (int) width / 2  ;
            radarAngle = 360f / options.count;
            halfRadian = (float) (Math.PI / options.count);
        }

        float bili ;
        private int getW(int val){
            bili = (val * 1.0f)/ (options.maxVal * 1.0f );
            int h = (int) (halfWidth * bili);
            return (int) (Math.tan(halfRadian) * h);
        }

        private int getH(int val){
            bili = (val * 1.0f)/ (options.maxVal * 1.0f );
            int h = (int) (halfWidth * bili);
            return halfWidth - h;
        }

        private Path designValPath(int p){
            Path path = new Path();
            int lastVal , nextVal ;

            lastVal = options.vals[p];
            if(p == options.vals.length -1){
                nextVal = options.vals[0];
            }else{
                nextVal = options.vals[p+1];
            }

            path.moveTo(halfWidth - getW(lastVal) , getH(lastVal));
            path.lineTo(halfWidth + getW(nextVal) , getH(nextVal));
            return path ;
        }

        /*画雷达蜘蛛网基线*/
        private Path designRadarPath(int l){
            Path path = new Path();
            int w , h;

            h = (int) (halfWidth * ((options.level - l + 1) * 1.0f/options.level*1.0f));
            w = (int) (Math.tan(halfRadian) * h);
            path.moveTo(halfWidth,halfWidth);
            path.lineTo(halfWidth-w,halfWidth - h);
            path.lineTo(halfWidth+w,halfWidth - h);
            path.close();

            return path ;
        }


        /*获取填充画笔*/
        private Paint getFillPaint(int color){
            Paint paint = new Paint();
            paint.setColor(color);
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            return paint ;
        }

        private Paint getStrokePaint(int color , int width){
            Paint paint = new Paint();
            paint.setColor(color);
            paint.setStyle(Paint.Style.STROKE);
            paint.setAntiAlias(true);
            paint.setStrokeWidth(width);
            paint.setStrokeCap(Paint.Cap.SQUARE);
            return paint ;
        }


        @Override
        public void draw(Canvas canvas, Paint paint) {
//            canvas.drawRect(new RectF(0,0,viewWidth,viewWidth), getFillPaint(Color.parseColor("#30111111")));
            canvas.scale(0.85f,0.85f,halfWidth,halfWidth); //将画布缩小

            canvas.rotate(radarAngle/2, halfWidth , halfWidth); //起始旋转，使尖叫朝正上方

            int[] colors = new int[]{Color.parseColor("#D3F0F6"),
                    Color.parseColor("#9BDAE1"),
                    Color.parseColor("#54C1C4"),
                    Color.parseColor("#2A8890")};

            for(int l = 1 ; l <= options.level ; l++){
                //p:当前到第几个点了，从0开始
                for(int p = 0 ; p < options.vals.length ; p++ ){
                    canvas.drawPath(designRadarPath(l) , getStrokePaint(Color.parseColor("#B7D9DA"),2));
                    
                    canvas.drawPath(designRadarPath(l) , getFillPaint(colors[l-1]));
                    canvas.drawPath(designValPath(p) , getStrokePaint(Color.RED,5));
                    canvas.rotate(radarAngle , halfWidth , halfWidth);
                }
            }

        }
    }
}
