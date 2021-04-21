package com.chen.firstdemo.charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * create by cxd on 2021/4/20
 * 多层柱状图
 */
class MultiStoreyBarView extends View {
    private int w , h ;
    private Context context ;

    private Point[] points ; //值对应的点集合

    private int unitPx; //x轴和y轴坐标单位宽度
    private boolean isPreview = true ; //是否为预览

    /*轴线*/
    private Path axisPath;
    private Paint axisPaint;
    private Paint pointPaint ;
    private Paint textPaint ;


    /*api*/
    private String xAxisStr = "x" ;
    private String yAxisStr = "y" ;
    private float[] floats ; //值集合

    public MultiStoreyBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context ;

        axisPath = new Path();

        axisPaint = new Paint();
        axisPaint.setColor(0xFF333333);
        axisPaint.setStyle(Paint.Style.STROKE);
        axisPaint.setStrokeWidth(dip2px(1));
        axisPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(sp2px(12));
        textPaint.setTextAlign(Paint.Align.CENTER);

        pointPaint = new Paint();
        pointPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        pointPaint.setStrokeWidth(dip2px(5f));
        pointPaint.setColor(Color.BLACK);
    }

    public void setFloats(String xAxisStr , String yAxisStr , float[] floats){
        this.xAxisStr = xAxisStr ;
        this.yAxisStr = yAxisStr ;
        this.floats = floats ;
        isPreview = false ;
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        w = getMeasuredWidth() ;
        h = getMeasuredHeight() ;

        if(isPreview){
            floats = new float[]{0.5f,0.24f,0.1f,0.6f,0.8f,0.2f} ;
        }

        /*计算x和y轴的单位坐标*/
        final float xPieces = floats.length + 1.25f ;
        unitPx = (int) (w / xPieces);

        /*计算y轴值范围区间*/
        float minFloat = 0f, maxFloat = 0f;
        for (int i = 0; i < floats.length; i++) {
            final float f = floats[i];
            if(f < minFloat){
                minFloat = f ;
            }else if(f > maxFloat){
                maxFloat = f ;
            }
        }
        float yRange = maxFloat - minFloat ;

        /*计算点集合*/
        points = new Point[floats.length];
        final int yLength = (int) (h-unitPx*2.25f);
        for (int i = 0; i < floats.length; i++) {
            final int x = (int) (unitPx*(i + 1.25f));
            final int y = (int) ((yLength * ((maxFloat-floats[i])/yRange)) + unitPx);
            points[i] = new Point(x,y);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*绘制坐标轴*/

        /*x轴*/
        axisPath.reset();
        axisPath.moveTo(0,h-unitPx*0.25f);
        axisPath.rLineTo(w,0);
        canvas.drawPath(axisPath,axisPaint);
        /*x轴箭头*/
        final int dx = unitPx / 8 ;
        axisPath.reset();
        axisPath.moveTo(w-dx,h-unitPx*0.25f-dx);
        axisPath.rLineTo(dx,dx);
        axisPath.rLineTo(-dx,dx);
        canvas.drawPath(axisPath,axisPaint);
        /*y轴*/
        axisPath.reset();
        axisPath.moveTo(unitPx*0.25f,h);
        axisPath.rLineTo(0,-h);
        canvas.drawPath(axisPath,axisPaint);
        /*y轴箭头*/
        axisPath.reset();
        axisPath.moveTo(unitPx*0.25f-dx,dx);
        axisPath.rLineTo(dx,-dx);
        axisPath.rLineTo(dx,dx);
        canvas.drawPath(axisPath,axisPaint);
        /*绘制x轴和y轴提示文案*/
        textPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(yAxisStr,unitPx*0.4f,unitPx*0.5f,textPaint);
        textPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(xAxisStr,w-unitPx*0.1f,h-unitPx*0.4f,textPaint);
        /*恢复中心*/
        textPaint.setTextAlign(Paint.Align.CENTER);

        Path path = new Path();
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            if(i == 0){
                path.moveTo(p.x,p.y);
            }else{
                path.lineTo(p.x,p.y);
            }
            /*x*/
            axisPath.reset();
            axisPath.moveTo(p.x,h-unitPx*0.25f);
            axisPath.rLineTo(0,unitPx*0.1f);
            canvas.drawPath(axisPath,axisPaint);
            /*绘制点*/
            canvas.drawPoint(p.x,p.y,pointPaint);
            /*数值-下沉处理*/
            if(i > 0 && i < points.length - 1){
                Point p1 = points[i-1];
                Point p2 = points[i+1];
                if(p.y > p1.y && p.y > p2.y){
                    canvas.drawText(String.valueOf(floats[i]),p.x,p.y+unitPx*0.2f+sp2px(12),textPaint);
                }else{
                    canvas.drawText(String.valueOf(floats[i]),p.x,p.y-unitPx*0.3f,textPaint);
                }
            }else if(i == 0 && points.length > 1 && p.y > points[i+1].y){
                canvas.drawText(String.valueOf(floats[i]),p.x,p.y+unitPx*0.2f+sp2px(12),textPaint);
            }else if(i == points.length-1 && points.length > 1 && p.y > points[i-1].y){
                canvas.drawText(String.valueOf(floats[i]),p.x,p.y+unitPx*0.2f+sp2px(12),textPaint);
            }else{
                canvas.drawText(String.valueOf(floats[i]),p.x,p.y-unitPx*0.3f,textPaint);
            }

        }
        canvas.drawPath(path,axisPaint);
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * @return
     */
    private int sp2px(float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
