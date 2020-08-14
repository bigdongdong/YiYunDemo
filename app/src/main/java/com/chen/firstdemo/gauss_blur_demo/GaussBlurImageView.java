package com.chen.firstdemo.gauss_blur_demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class GaussBlurImageView extends android.support.v7.widget.AppCompatImageView {

    private final String TAG = "GaussBlurImageView";
    private Matrix mMartix ;
    private int W , H ; //view's
//    private int dW , dH; //drawablw's
    private Canvas mCanvas ;
    private Bitmap mBitmap ;


    public GaussBlurImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setScaleType(ScaleType.MATRIX);

        mMartix = new Matrix();
        mMartix.set(this.getMatrix());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(this.getDrawable() != null){
            W = getMeasuredWidth();
            H = getMeasuredHeight();
//            dW = this.getDrawable().getIntrinsicWidth();
//            dH = this.getDrawable().getIntrinsicHeight();

//            mBitmap = Bitmap.createBitmap(W,H, Bitmap.Config.ARGB_4444);
//            mCanvas = new Canvas(mBitmap);

            BitmapDrawable bd = (BitmapDrawable) this.getDrawable();
            Bitmap bitmap = bd.getBitmap();
            compressBitmap(bitmap);


            /*压缩处理*/
//            mMartix.setScale(0.5f,0.5f);
//            this.setImageMatrix(mMartix);
            this.setImageBitmap(bitmap);        }
    }

    /**
     * 图片质量压缩
     * @param image
     * @return
     */
    public void compressBitmap(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 1, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
    }
}
