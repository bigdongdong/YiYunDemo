package com.chen.firstdemo.recyclers.recyclerview_layoutmanager_demo;

import android.graphics.Point;
import android.graphics.PointF;

/**
 * {@link Point} -> 内部持有两个点int x,y
 * {@link PointF} -> 内部持有两个点float x,y
 */
public class PosTan extends PointF {
    /**
     * 在路径上的位置 (百分比)
     */
    public float fraction;

    /**
     * Item所对应的索引
     */
    public int index;

    /**
     * Item的旋转角度
     */
    private float angle;

    public PosTan() {
    }

    public PosTan(PosTan posTan, int i, float fraction) {
        this.x = posTan.x ;
        this.y = posTan.y ;
        this.angle = posTan.angle ;
        this.index = i;
        this.fraction = fraction ;
    }

    public void set(float x, float y, float angle) {
        this.x = x ;
        this.y = y ;
        this.angle = angle;
    }

    public float getChildAngle() {
        return this.angle;
    }
}
