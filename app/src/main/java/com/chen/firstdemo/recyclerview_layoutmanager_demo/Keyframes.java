package com.chen.firstdemo.recyclerview_layoutmanager_demo;

import android.animation.Keyframe;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PostProcessor;
import android.support.annotation.FloatRange;

/*关键帧*/
public class Keyframes  {
    private final int PRECISION = 1000 ;

    private float[] mX; //Path的所有x轴坐标点
    private float[] mY; //Path的所有y轴坐标点
    private float[] mAngle; //Path上每一个坐标所对应的角度
    private int mNumPoints = 0 ;//点的数量
    private Path path ;
    private float pathLength ;

    public Keyframes(Path path) {
        this.path = path;
        initPath();
    }

    private void initPath(){
        final PathMeasure pathMeasure = new PathMeasure(path,false);
        pathLength = pathMeasure.getLength();
        mNumPoints = (int) ((pathLength / PRECISION ) + 1); //precision 精度

        /*临时存放坐标点*/
        float[] position = new float[2];
        /*临时存放正切值*/
        float[] tangent = new float[2];
        /*当前距离*/
        float distance ;
        for (int i = 0; i < mNumPoints; i++) {
            /*更新当前距离*/
            distance = (i * pathLength) / (mNumPoints -1) ;
            /*根据当前距离获取对应的坐标点和正切值*/
            pathMeasure.getPosTan(distance,position,tangent);
            mX[i] = position[0];
            mY[i] = position[1];
            /*利用反正切函数得到角度*/
            mAngle[i] = fixAngle((float) (Math.atan2(tangent[1],tangent[0]) * 180F / Math.PI));
        }

    }


    /**
     * 根据传入的百分比来获取对应的坐标点和角度
     * @param fraction 当前百分比  0~1
     *                 @FloatRange(from = 0F,to =1F)
     * @return
     */
    public PosTan getValue(float fraction){
        /*超出范围直接返回空*/
        if(fraction >= 1F || fraction < 0){
            return null ;
        }else{
            PosTan posTan = new PosTan();
            int index = (int) (mNumPoints * fraction);
            /*更新temp的内部值*/
            posTan.set(mX[index],mY[index],mAngle[index]);
            return posTan;
        }
    }

    /**
     * 调整角度 使其在0~360之间
     *
     * @param rotation 当前角度
     * @return 调整之后的角度
     */
    private float fixAngle(float rotation) {
        float angle = 360F ;
        if(rotation < 0){
            rotation += angle;
        }

        if(rotation > angle){
            rotation %= angle;
        }

        return rotation;
    }

    /*获取path的总长度*/
    public int getPathLength() {
        return (int) this.pathLength;
    }
}
