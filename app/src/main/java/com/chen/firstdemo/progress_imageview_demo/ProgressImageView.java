package com.chen.firstdemo.progress_imageview_demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.chen.firstdemo.utils.DensityUtil;

public class ProgressImageView extends AppCompatImageView {

    private Integer angle = null;
    private int width , height ;
    private int radius ;
    private OnProgressListenr listenr ;

    private Context context ;

    public ProgressImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context ;
        paint = new Paint();

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        width = right - left ;
        height = bottom - top ;

//        radius = (int) ((Math.min(width,height)) * (0.07f));
        radius = DensityUtil.dip2px(context,20);
    }

    private Paint paint ;
    private RectF rectF ;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(angle == null){
            return;
        }

        if(angle !=null && angle >= 360){
            return;
        }

        /*1.在顶部加一个遮罩蒙板*/
        paint.reset();
        paint.setColor(Color.parseColor("#50111111"));
        rectF = new RectF();
        rectF.set(0,0,width,height);
        canvas.drawRect(rectF,paint);

        /*2.绘制底部的圆，圆形半径是高度的三分之一*/
        paint.reset();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#55FFFFFF"));
        canvas.drawCircle(width/2,height/2,radius,paint);

        /*3.在绘制上层扇形*/
        paint.reset();
        paint.setColor(Color.parseColor("#88FFFFFF"));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        rectF = new RectF();
        rectF.set(width/2-radius,height/2-radius,width/2+radius,height/2+radius);
        canvas.drawArc(rectF,270,angle,true,paint);
    }


    /**
     * 设置进度
     *
     * @param progress 已加载的进度
     */
    public void setProgress(int progress) {
        if(angle == null){
            if(listenr != null){
                listenr.start();
            }
            angle = 0 ;
        }
        this.angle = (int) (progress * 3.6f);
        postInvalidate();
    }

    /**
     * 设置完成监听
     * @param listener
     */
    public void setOnProgressListener(OnProgressListenr listener ){
        this.listenr = listener ;
    }

    interface OnProgressListenr{
        void start();
    }
}