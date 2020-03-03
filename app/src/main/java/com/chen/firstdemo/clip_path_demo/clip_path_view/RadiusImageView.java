package com.chen.firstdemo.clip_path_demo.clip_path_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * void addRoundRect (RectF rect, float[] radii, Path.Direction dir)
 * 第一个构造函数：可以定制每个角的圆角大小：
 * float[] radii：必须传入8个数值，分四组，分别对应每个角所使用的椭圆的横轴半径和纵轴半径，
 * 如｛x1,y1,x2,y2,x3,y3,x4,y4｝，
 * 其中，x1,y1对应第一个角的（左上角）用来产生圆角的椭圆的横轴半径和纵轴半径，其它类推……
 *
 *
 * void addRoundRect (RectF rect, float rx, float ry, Path.Direction dir)
 * 第二个构造函数：只能构建统一圆角大小
 * float rx：所产生圆角的椭圆的横轴半径；
 * float ry：所产生圆角的椭圆的纵轴半径
 */
public class RadiusImageView extends ImageView {
    //圆角弧度
    private float[] rids = {dip2px(20), dip2px(20), dip2px(20), dip2px(20),
            dip2px(20), dip2px(20), dip2px(20), dip2px(20)};

    public RadiusImageView(Context context) {
        super(context);
    }

    public RadiusImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadiusImageView(Context context, AttributeSet attrs, int defStyleAttr) {
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
