package com.chen.firstdemo.charts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * 轻量折线图
 */
@SuppressLint("DrawAllocation")
public class LightLineChartView extends ViewGroup {
    //文字大小 15sp
    private final int FONT_SIZE ;
    //小数点后留余
    private final DecimalFormat df2 = new DecimalFormat("###.0");

    private Context context ;
    private int w,h ;
    private int pleft , pright , ptop , pbottom ;
    private CPoint[] points ;


    //模拟数据
    private float[] ratios;
    private int count ;

    public LightLineChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context ;
        FONT_SIZE = sp2px(10);

        count = 6 ;
        ratios = new float[count];
        for (int i = 0; i < count; i++) {
            float f = new Random().nextFloat();
            if(f < 0.75f){
                f = 0.75f ;
            }
            if(f > 0.81f){
                f = 0.81f ;
            }
            ratios[i] = f;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        w = getMeasuredWidth();
        h = getMeasuredHeight();
        ptop = getPaddingTop() ;
        pbottom = getPaddingBottom() ;
        pleft = getPaddingLeft() ;
        pright = getPaddingRight() ;
        final int space = (w - pleft - pright)/(count-1) ;

        points = new CPoint[count];
        for (int i = 0; i < count; i++) {
            float ratio = ratios[i];
            int x = pleft + space * i ;
            int y = (int) ((h - ptop - pbottom) * (1-ratio) + ptop);
            points[i] = new CPoint(x,y,df2.format(ratio*100),"2"+i);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //points's paint
        Paint pp = new Paint();
        pp.setStyle(Paint.Style.FILL_AND_STROKE);
        pp.setStrokeCap(Paint.Cap.ROUND);
        pp.setStrokeWidth(20);
        pp.setAntiAlias(true);
        pp.setColor(0xFF1AB0F0);
        pp.setPathEffect(new CornerPathEffect(30));

        //path's paint
        Paint pt = new Paint();
        pt.setAntiAlias(true);
        pt.setStyle(Paint.Style.STROKE);
        pt.setColor(0xFF1AB0F0);
        pt.setStrokeWidth(6);
        pt.setAntiAlias(true);

        //block's paint
        Paint pb = new Paint();
        pb.setAntiAlias(true);
        pb.setStyle(Paint.Style.FILL);
        pb.setShader(new LinearGradient(0,ptop,0,h-pbottom,
                new int[]{0x441AB0F0,0x001AB0F0},null, Shader.TileMode.MIRROR));

        //font's paint
        Paint pf = new Paint();
        pf.setAntiAlias(true);
        pf.setStyle(Paint.Style.FILL);
        pf.setColor(0xFF111111);
        pf.setTextSize(FONT_SIZE);
        pf.setTextAlign(Paint.Align.CENTER);

        //绘制线
        Path path = new Path();
        for (int i = 0; i < count; i++) {
            Point p = points[i];
            if(i == 0){
                path.reset();
                path.moveTo(p.x,p.y);
            }else{
                path.lineTo(p.x,p.y);
            }
        }
        canvas.drawPath(path,pt);


        //绘制点
        for (int i = 0; i < count; i++) {
            Point p = points[i];
            canvas.drawPoint(p.x,p.y,pp);
        }

        //绘制渐变块
        path = new Path();
        path.reset();
        path.moveTo(pleft,h-pbottom);
        for (int i = 0; i < count; i++) {
            Point p = points[i];
            path.lineTo(p.x,p.y);
        }
        path.lineTo(w-pright,h-pbottom);
        path.close();
        canvas.drawPath(path,pb);

        //绘制文字
        pf.setColor(0xFF111111);
        for (int i = 0; i < count; i++) {
            CPoint p = points[i];
            canvas.drawText(p.str,p.x,p.y - FONT_SIZE,pf);
        }

        //底部坐标轴
        pf.setColor(0xFF666666);
        for (int i = 0; i < count; i++) {
            CPoint p = points[i];
            canvas.drawText(p.xStr,p.x,h-pbottom+FONT_SIZE*2,pf);
        }
    }

    class CPoint extends Point{
        private String str ;
        private String xStr ;

        public CPoint(int x, int y, String str, String xStr) {
            super(x, y);
            this.str = str;
            this.xStr = xStr;
        }
    }


    /**
     * sp转px
     * @return
     */
    public int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }
}
