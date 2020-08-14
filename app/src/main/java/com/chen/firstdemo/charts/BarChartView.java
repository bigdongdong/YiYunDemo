package com.chen.firstdemo.charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class BarChartView extends View {
    private Context context ;
    private int W , H ; //View's width & height
    private final int topMargin1 ; //Y's text
    private final int textHeight ;
    private final int topMargin2 ; //bar's text
    private int leftMargin ; //Y's value
    private int rightMargin ; //X's text
    private final int bottomMargin ; //X's value
    private final int arrow ;

    private Paint textPaint ;
    private Paint barTextPaint ;
    private Paint axisPaint ; //X & Y's paint
    private Paint barPaint ;
    private Paint standardPaint ; //standard line's paint

    private Path tempPath ;
    private Adapter adapter ;

    public BarChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        tempPath = new Path();

        arrow = dp2px(5);
        topMargin1 = sp2px(20);
        topMargin2 = sp2px(5);
        textHeight = dp2px(13);
        bottomMargin = dp2px(30);
        leftMargin = rightMargin = dp2px(30);

        axisPaint = new Paint();
        axisPaint.setStyle(Paint.Style.STROKE);
        axisPaint.setColor(Color.parseColor("#444444"));
        axisPaint.setAntiAlias(true);
        axisPaint.setStrokeWidth(dp2px(1));

        standardPaint = new Paint();
        standardPaint.setStyle(Paint.Style.STROKE);
        standardPaint.setColor(Color.parseColor("#33333333"));
        standardPaint.setAntiAlias(true);
        standardPaint.setStrokeWidth(dp2px(0.5f));

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.parseColor("#555555"));
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(sp2px(13));
        textPaint.setStyle(Paint.Style.FILL);

        barTextPaint = new Paint();
        barTextPaint.setAntiAlias(true);
        barTextPaint.setColor(Color.parseColor("#444444"));
        barTextPaint.setTextAlign(Paint.Align.CENTER);
        barTextPaint.setTextSize(sp2px(12));
        barTextPaint.setStyle(Paint.Style.FILL);

        /*#1fab89*/
        barPaint = new Paint();
        barPaint.setColor(Color.parseColor("#5EC26A"));
        barPaint.setStyle(Paint.Style.FILL_AND_STROKE);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        W = getMeasuredWidth();
        H = getMeasuredHeight();
    }

    /**
     * 设置数据
     * @param adapter
     */
    public void setAdapter(Adapter adapter){
        this.adapter = adapter ;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(adapter != null){
            leftMargin = adapter.getLeftMargin();
            rightMargin = adapter.getRightMargin();
            final String[] yVals = adapter.getYVals();
            final String[] xVals = adapter.getXVals();
            final float[] barRatios = adapter.getBarRatios();
            final String[] barVals = adapter.getBarVals();
            final int yspace = (H - topMargin1 - topMargin2 - bottomMargin )/yVals.length;
            final int xspace = (W - leftMargin - rightMargin)/(xVals.length+1);
            /*绘制xy轴*/
            //y
            tempPath.reset();
            tempPath.moveTo(leftMargin,H-bottomMargin);
            tempPath.lineTo(leftMargin,topMargin1+topMargin2);
            canvas.drawPath(tempPath,axisPaint);
                //y's arrow
                tempPath.reset();
                tempPath.moveTo(leftMargin-arrow,topMargin1+topMargin2+arrow);
                tempPath.rLineTo(arrow,-arrow);
                tempPath.rLineTo(arrow,arrow);
                canvas.drawPath(tempPath,axisPaint);

                //y's values & text
                textPaint.setTextAlign(Paint.Align.RIGHT);
                for (int i = 0; i < yVals.length; i++) {
                    tempPath.reset();
                    tempPath.moveTo(leftMargin,H-bottomMargin-i*yspace);
                    tempPath.rLineTo(-arrow,0);
                    canvas.drawPath(tempPath,axisPaint);
                    canvas.drawText(String.valueOf(yVals[i]),leftMargin-dp2px(7),
                            H-bottomMargin-i*yspace,textPaint); //+textHeight/3
                }
            //x
            tempPath.reset();
            tempPath.moveTo(leftMargin,H-bottomMargin);
            tempPath.lineTo(W-rightMargin,H-bottomMargin);
            canvas.drawPath(tempPath,axisPaint);
                //x's arrow
                tempPath.reset();
                tempPath.moveTo(W-rightMargin-arrow,H-bottomMargin-arrow);
                tempPath.rLineTo(arrow,arrow);
                tempPath.rLineTo(-arrow,arrow);
                canvas.drawPath(tempPath,axisPaint);

                //x's values
                textPaint.setTextAlign(Paint.Align.CENTER);
                for (int i = 0; i < xVals.length; i++) {
                    tempPath.reset();
                    tempPath.moveTo(leftMargin+(i+1)*xspace,H-bottomMargin);
                    tempPath.rLineTo(0,arrow);
                    canvas.drawPath(tempPath,axisPaint);
                    canvas.drawText(String.valueOf(xVals[i]),leftMargin+xspace*(i+1),
                            H-bottomMargin+dp2px(5)+textHeight,textPaint);
                }
            /*绘制xy轴尾部的说明文字*/
            textPaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(adapter.getYTailText(),leftMargin/2,textHeight+topMargin2,textPaint);
            canvas.drawText(adapter.getXTailText(),W-rightMargin+textHeight/4,H-bottomMargin+textHeight/4,textPaint);

            /*标准线*/
            for (int i = 1; i < yVals.length; i++) {
                tempPath.reset();
                tempPath.moveTo(leftMargin,H-bottomMargin-i*yspace);
                tempPath.rLineTo(W-leftMargin-rightMargin,0);
                canvas.drawPath(tempPath,standardPaint);
            }

            /*bars*/
            barPaint.setStrokeWidth(xspace/2);
            final int totalH = H - topMargin1 - topMargin2 - bottomMargin ;
            for (int i = 0; i < barRatios.length; i++) {
                tempPath.reset();
                tempPath.moveTo(leftMargin+(i+1)*xspace,H-bottomMargin-axisPaint.getStrokeWidth()/2);
                final int h = (int) (barRatios[i]*totalH);
                tempPath.rLineTo(0,-h);
                canvas.drawPath(tempPath,barPaint);
                //bar's text
                canvas.drawText(barVals[i],leftMargin+(i+1)*xspace,H-bottomMargin-h-topMargin2,barTextPaint);
            }
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


    /**
     * dp转px
     * @return
     */
    public int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    public interface Adapter{
        int getLeftMargin();
        int getRightMargin();
        String getYTailText();
        String getXTailText();
        String[] getYVals();
        String[] getXVals();
        float[] getBarRatios();
        String[] getBarVals();
    }
}
