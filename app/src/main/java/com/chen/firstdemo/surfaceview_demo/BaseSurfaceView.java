package com.chen.firstdemo.surfaceview_demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class BaseSurfaceView extends SurfaceView  implements SurfaceHolder.Callback,Runnable{

    private final String TAG = "MySurfaceView";
    protected Context mContext ;
    private SurfaceHolder mHolder ;
    private Canvas mCanvas ;
    private boolean isDrawing = false ;

    public BaseSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context ;
        mHolder=getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDrawing=true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDrawing = false;
    }

    @Override
    public void run() {
        while (isDrawing){
            draw();
        }
    }

    public abstract void doDraw(Canvas c);

    private void draw(){
        try{
            mCanvas=mHolder.lockCanvas();
            //这里写要绘制的内容
            doDraw(mCanvas);
        }catch (Exception e){

        }finally {
            if(mCanvas!=null){
                mHolder.unlockCanvasAndPost(mCanvas);//提交画布内容
            }
        }
    }
}
