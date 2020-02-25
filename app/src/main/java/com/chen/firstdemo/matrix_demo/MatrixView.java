package com.chen.firstdemo.matrix_demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.chen.firstdemo.R;

public class MatrixView extends android.support.v7.widget.AppCompatImageView {
    private int method = -1 ;
    private Matrix matrix ;
    private Bitmap bitmap ;
    private int height ;

    public MatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);

        BitmapDrawable bd  = (BitmapDrawable)getDrawable();
        bitmap = bd.getBitmap();
        setScaleType(ScaleType.FIT_XY);
//        matrix = getImageMatrix() ;

        matrix = new Matrix();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        height = getMeasuredHeight() ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        matrix.reset();
        switch(method){
            case 0:
                /*移动*/
                matrix.setTranslate(200,200);
                break;
            case 1:
                /*旋转*/
                /*参数依次是:旋转角度，轴心(x,y)*/
                matrix.setRotate(45,500,height/2);
                break;
            case 2:
                /*缩放*/
                /*参数依次是：X，Y轴上的缩放比例；缩放的轴心。*/
                matrix.setScale(0.5f,0.7f,500,height/2);
                break;
            case 3:
                /*错切*/
                /*参数依次是：X，Y轴上的缩放比例*/
                matrix.setSkew(0.5f,0.7f);
                break;
            default:
                break;
        }
        canvas.drawBitmap(bitmap,matrix,null);
    }


    public void setMethod(int method){
        this.method = method ;
        postInvalidate();
    }
}
