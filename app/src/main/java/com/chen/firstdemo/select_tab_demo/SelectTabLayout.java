package com.chen.firstdemo.select_tab_demo;

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
import android.util.Log;
import android.widget.LinearLayout;

import com.chen.firstdemo.utils.DensityUtil;

public class SelectTabLayout extends LinearLayout {
    int width , height ;
    int tabWidth ;
    int radius ; //14dp
    private Context context ;

    public SelectTabLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context ;
        radius = DensityUtil.dip2px(context,14);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        width = r - l; //计算控件宽度
        height = b - t; //计算控件高度
        tabWidth = (int) ((1f/3f)*width);
    }

    public void setRadius(int radius){
        this.radius = radius ;
    }

    /**
     *
     * @param tab 从1 开始
     */
    public void setSelectedTab(int tab){
        this.setBackground(new ShapeDrawable(new TabShape(tab)));
    }


    class TabShape extends Shape {
        int tab ;

        public TabShape(int tab) {
            this.tab = tab;
        }

        private Path designYellowPath(){
            int currentLeft = tabWidth * (tab -1 ) ;
            int currentRight = tabWidth * tab ;
            Path path = new Path();
            RectF rectF = new RectF() ;

            //设置起点
            path.moveTo(currentLeft -radius,height);
            //左下角
            rectF.set(currentLeft-radius*2,height - radius*2 ,currentLeft , height);
            path.arcTo(rectF,90,-90);
            //弧上方直线
            path.lineTo(currentLeft , radius);
            //左上角
            rectF.set(currentLeft ,0,currentLeft + radius*2 , radius*2);
            path.arcTo(rectF,180,90);
            //弧右侧直线
            path.lineTo(currentRight -radius,0);
            //右上角
            rectF.set(currentRight -radius*2,0,currentRight,radius*2);
            path.arcTo(rectF,270,90);
            //弧下方直线
            path.lineTo(currentRight,height-radius);
            //右下角
            rectF.set(currentRight,height-radius*2,currentRight+radius*2,height);
            path.arcTo(rectF,180,-90);
            path.close();
            return path;

        }

        private Path designPurplePath(){
            Path path = new Path();
            RectF rectF = new RectF(0,0,width,height);
            path.addRoundRect(rectF,new float[]{radius,radius,radius,radius,0,0,0,0}, Path.Direction.CCW);
            path.close();
            return path ;
        }

        @Override
        public void draw(Canvas canvas, Paint paint) {
            paint.setColor(Color.parseColor("#9043FC"));
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(designPurplePath(),paint);

            paint.setColor(Color.parseColor("#FFC531"));
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(5);
            canvas.drawPath(designYellowPath(),paint);

        }
    }

}
