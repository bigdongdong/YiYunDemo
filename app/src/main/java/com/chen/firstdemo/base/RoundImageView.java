package com.chen.firstdemo.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 圆角ImageView
 */
public class RoundImageView extends android.support.v7.widget.AppCompatImageView {
    //圆角弧度
    private final float[] rids = {dip2px(20), dip2px(20), dip2px(20), dip2px(20),
            dip2px(20), dip2px(20), dip2px(20), dip2px(20)};

    public RoundImageView(Context context) {
        super(context);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        int w = this.getWidth();
        int h = this.getHeight();
        //绘制圆角imageview
        path.addRoundRect(new RectF(0, 0, w, h), rids, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }

    private int dip2px(int dipVal) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dipVal * scale + 0.5f);
    }
}
