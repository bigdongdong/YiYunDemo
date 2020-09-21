package com.chen.firstdemo.largeFace;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * create by cxd on 2020/9/9
 * 晴雨表view
 */
@SuppressLint("DrawAllocation")
public class BaromterView extends View {
    private final String TAG = "LargeFaceView" ;
    private int w , h ;
    private Context context;
    private Paint paint;
    private RectF rect ;

    private final float pa = 360f/25f ; //每份的角度值15
    private final int[] lightColors = {0xFF49DD49,0xFFEAB020,0xFFF06E60};
    private int lightColor = lightColors[0]; //五官+头发+嘴巴颜色

    /*数据集*/
    private List<Hair> greyHairs; //灰色头发
    private List<Hair> lightHairs; //亮色头发

    private float progress ;

    public BaromterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context ;
        this.setWillNotDraw(false);

        /*头发的画笔*/
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
//        paint.setPathEffect(new CornerPathEffect(dp2px(10)));

        /*整体的矩阵*/
        rect = new RectF();

        //灰色头发
        greyHairs = new ArrayList<>();
        greyHairs.add(new Hair(90+3*pa,3*pa));
        greyHairs.add(new Hair(90+7*pa,3*pa));
        greyHairs.add(new Hair(90+11*pa,3*pa));
        greyHairs.add(new Hair(90+15*pa,3*pa));
        greyHairs.add(new Hair(90+19*pa,3*pa));

    }

    /**
     * 设置进度并刷新控件
     * @param progress
     */
    public void setProgressAndInvalidate(@Size(max = 1, min = 0) float progress){
        this.progress = Math.min(1,progress) ;

        /*计算头发*/
        lightHairs = new ArrayList<>();
        int paVal = 3 ;
        float hairAmount = this.progress ; //发量
        while (hairAmount > 0){
            lightHairs.add(new Hair(90+paVal*pa,
                    3*pa*(hairAmount >= 0.2f ? 1:(hairAmount/0.2f))));
            hairAmount -= Math.min(0.2f,hairAmount) ; //一次最多减少0.2
            paVal += 4 ;
        }

        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        w = getMeasuredWidth();
        h = getMeasuredHeight() ;

        /*设置画笔描边宽度*/
        paint.setStrokeWidth(w/15);

        /*偏移，避免裁剪头发*/
        final float offset = paint.getStrokeWidth()/2;
        rect.set(offset,offset,w-offset,h-offset);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawColor(Color.WHITE);

        /*设置画笔颜色*/
        if(progress <= 0.8f){
            lightColor = lightColors[0];
        }else if(progress <= 0.99f){
            lightColor = lightColors[1];
        }else if(progress == 1){
            lightColor = lightColors[2];
        }

        Path path = new Path();

        /*画头发*/
        paint.setStyle(Paint.Style.STROKE);

        /*画灰色头发*/
        if(greyHairs != null){
            paint.setColor(0xFFCCCCCC); //头盔底色
            for(Hair ab : greyHairs){
                path.addArc(rect,ab.getStartAngle(),ab.getAngle());
            }
            canvas.drawPath(path, paint);
        }


        /*画亮色头发*/
        if(lightHairs != null){
            paint.setColor(lightColor);
            path.reset();
            for(Hair ab : lightHairs){
                path.addArc(rect,ab.getStartAngle(),ab.getAngle());
            }
            canvas.drawPath(path, paint);
        }


        /*画眼睛*/
        paint.setStyle(Paint.Style.FILL);
        final int i4 = w / 4 ;
        final int offsetX = w / 25 ;
        RectF leftRectf = new RectF(i4-offsetX,i4,i4*2-offsetX,i4*2);
        canvas.drawCircle(leftRectf.centerX(),leftRectf.centerY(),i4/3, paint);
        RectF rightRectf = new RectF(i4*2+offsetX,i4,i4*3+offsetX,i4*2);
        canvas.drawCircle(rightRectf.centerX(),rightRectf.centerY(),i4/3, paint);

        /*画嘴巴*/
        paint.setStyle(Paint.Style.STROKE);
        final int offsetY = w / 20 ;
        final int offsetX2 = w / 20 ;
        path.reset();
        path.moveTo(leftRectf.centerX()+offsetX2,leftRectf.centerY()+i4+offsetY);
        if(progress <= 0.8f){
            path.quadTo(i4*2,i4*3+offsetY,rightRectf.centerX()-offsetX2,rightRectf.centerY()+i4+offsetY);
        }else if(progress <= 0.99f){
            path.lineTo(rightRectf.centerX()-offsetX2,rightRectf.centerY()+i4+offsetY);
        }else if(progress >= 1){
            path.quadTo(i4*2,i4*2+offsetY,rightRectf.centerX()-offsetX2,rightRectf.centerY()+i4+offsetY);
        }
        canvas.drawPath(path, paint);

    }

    /*头发bean*/
    private class Hair {
        private float startAngle ;
        private float angle ;

        public Hair(float startAngle, float angle) {
            this.startAngle = startAngle;
            this.angle = angle;
        }

        public float getStartAngle() {
            return startAngle;
        }

        public void setStartAngle(float startAngle) {
            this.startAngle = startAngle;
        }

        public float getAngle() {
            return angle;
        }

        public void setAngle(float angle) {
            this.angle = angle;
        }
    }

    public int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

}
