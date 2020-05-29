package com.chen.firstdemo.multiple_img_view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chen.firstdemo.utils.DensityUtil;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

public class MultipleImgView extends View {
    private final int mConner ;
    private int mSpace ;

    private Context mContext ;
    private int mWidth ;
    private int mHeight ;

    private Canvas mCanvas ;
    private Bitmap mBitmap ;

    public MultipleImgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mContext = context ;
        mConner = DensityUtil.dip2px(mContext,5);


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        mSpace = (int) ((mWidth * 1.0f) / 40);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Path p = new Path();
        p.addRoundRect(new RectF(0,0,mWidth,mHeight),mConner,mConner, Path.Direction.CCW);
        canvas.clipPath(p);
        canvas.drawColor(Color.parseColor("#DDDDDD"));

        super.onDraw(canvas);

        if(mBitmap != null){
            canvas.drawBitmap(mBitmap,0,0,null);
        }

    }

    public void setImgs(Object... urls){
        if(urls == null || urls.length == 0){
            return;
        }
        //根据长度，获取每一个的位置信息
        final int len = urls.length ;
        ArgsBean[] abs = computeArgs(len);

        //同步下载所有小bitmap
        new Thread(){
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < len; i++) {
                    try {
                        Bitmap b = Glide.with(mContext)
                                .asBitmap()
                                .load(urls[i])
                                .submit(abs[i].bw,abs[i].bw)
                                .get();
                        abs[i].bitmap = b;
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //全部下载完毕，进行draw操作
                if(mContext instanceof Activity){
                    ((Activity)mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //清空重置mBitmap
                            mBitmap = Bitmap.createBitmap(mWidth,mHeight, Bitmap.Config.ARGB_4444);
                            mCanvas = new Canvas(mBitmap);

                            for (int i = 0; i < len; i++) {
                                //new Rect(0,0,abs[i].bw,abs[i].bw)
                                Canvas c ;
                                Bitmap b ;
                                if(abs[i].bitmap == null){
                                    b = Bitmap.createBitmap(abs[i].bw,abs[i].bw, Bitmap.Config.ARGB_4444);
                                    c = new Canvas(b);
                                    c.drawColor(Color.parseColor("#AAAAAA"));
                                    abs[i].bitmap = b ;
                                }
                                mCanvas.drawBitmap(abs[i].bitmap,null,abs[i].rect,null);
                            }
                            invalidate();

                        }
                    });
                }

            }
        }.start();

    }

    private ArgsBean[] computeArgs(int len){
        if(len > 9){
            len = 9 ;
        }

        ArgsBean[] abs = new ArgsBean[len];
        int bw = 0;
        RectF rect = null;
        switch(len){
            case 1:
                bw = mWidth - mSpace * 2 ;
                rect = new RectF(mSpace , mSpace , mSpace + bw , mSpace + bw);
                abs[0] = new ArgsBean(bw,rect);

                break;
            case 2:
                bw = (mWidth - mSpace * 3) / 2 ;
                rect = new RectF(mSpace , (mHeight - bw) / 2 , mSpace + bw , (mHeight - bw) / 2 + bw );
                abs[0] = new ArgsBean(bw,rect) ;

                rect = new RectF(mSpace * 2 + bw ,(mHeight - bw) / 2 , mSpace * 2 + bw + bw ,(mHeight - bw) / 2 + bw);
                abs[1] = new ArgsBean(bw,rect);

                break;
            case 3:
                bw = (mWidth - mSpace*3) / 2 ;
                rect = new RectF( (mWidth - bw)/2 ,mSpace ,(mWidth - bw)/2 + bw ,mSpace + bw);
                abs[0] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace , mSpace * 2 +bw ,mSpace + bw , mSpace * 2 +bw +bw);
                abs[1] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace * 2 + bw, mSpace * 2 +bw ,mSpace * 2 + bw + bw , mSpace * 2 +bw +bw);
                abs[2] = new ArgsBean(bw,rect);

                break;
            case 4:
                bw = (mWidth - mSpace*3) / 2 ;
                rect = new RectF( mSpace,mSpace ,mSpace + bw ,mSpace + bw);
                abs[0] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace * 2 +bw ,mSpace ,mSpace * 2 +bw +bw ,mSpace + bw);
                abs[1] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace , mSpace * 2 +bw ,mSpace + bw , mSpace * 2 +bw +bw);
                abs[2] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace * 2 + bw, mSpace * 2 +bw ,mSpace * 2 + bw + bw , mSpace * 2 +bw +bw);
                abs[3] = new ArgsBean(bw,rect);

                break;
            case 5:
                bw = (mWidth - mSpace*4)/3 ;
                rect = new RectF((mWidth - bw * 2 - mSpace) / 2 ,(mWidth - bw * 2 - mSpace) / 2 ,
                        (mWidth - bw * 2 - mSpace) / 2 +bw , (mWidth - bw * 2 - mSpace) / 2+bw);
                abs[0] = new ArgsBean(bw,rect);

                rect = new RectF((mWidth - bw * 2 - mSpace) / 2 + bw + mSpace,(mWidth - bw * 2 - mSpace) / 2 ,
                        (mWidth - bw * 2 - mSpace) / 2 + bw + mSpace +bw , (mWidth - bw * 2 - mSpace) / 2+bw);
                abs[1] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace,(mWidth - bw * 2 - mSpace) / 2 + bw + mSpace ,
                        mSpace + bw ,(mWidth - bw * 2 - mSpace) / 2 + bw + mSpace + bw);
                abs[2] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace * 2 + bw, (mWidth - bw * 2 - mSpace) / 2 + bw + mSpace ,
                        mSpace * 2  + bw + bw ,(mWidth - bw * 2 - mSpace) / 2 + bw + mSpace + bw);
                abs[3] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace * 3 + bw * 2,(mWidth - bw * 2 - mSpace) / 2 + bw + mSpace ,
                        mSpace * 3 + bw * 2 + bw ,(mWidth - bw * 2 - mSpace) / 2 + bw + mSpace+bw );
                abs[4] = new ArgsBean(bw,rect);
                break;
            case 6:
                bw = (mWidth - mSpace*4)/3 ;
                rect = new RectF(mSpace ,(mWidth - bw * 2 - mSpace) / 2 ,
                        mSpace + bw , (mWidth - bw * 2 - mSpace) / 2+bw);
                abs[0] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace * 2 + bw,(mWidth - bw * 2 - mSpace) / 2 ,
                        mSpace * 2 + bw +bw , (mWidth - bw * 2 - mSpace) / 2+bw);
                abs[1] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace * 3 + bw * 2,(mWidth - bw * 2 - mSpace) / 2 ,
                        mSpace * 3 + bw * 2 +bw , (mWidth - bw * 2 - mSpace) / 2+bw);
                abs[2] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace,(mWidth - bw * 2 - mSpace) / 2 + bw + mSpace ,
                        mSpace + bw ,(mWidth - bw * 2 - mSpace) / 2 + bw + mSpace + bw);
                abs[3] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace * 2 + bw, (mWidth - bw * 2 - mSpace) / 2 + bw + mSpace ,
                        mSpace * 2  + bw + bw ,(mWidth - bw * 2 - mSpace) / 2 + bw + mSpace + bw);
                abs[4] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace * 3 + bw * 2,(mWidth - bw * 2 - mSpace) / 2 + bw + mSpace ,
                        mSpace * 3 + bw * 2 + bw ,(mWidth - bw * 2 - mSpace) / 2 + bw + mSpace+bw );
                abs[5] = new ArgsBean(bw,rect);
                break;
            case 7:
                bw = (mWidth - mSpace*4)/3 ;
                rect = new RectF((mWidth - bw)/2 , mSpace ,
                        (mWidth - bw)/2 + bw ,mSpace + bw );
                abs[0] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace , mSpace * 2 + bw ,
                        mSpace + bw ,mSpace * 2 + bw + bw);
                abs[1] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace * 2 + bw, mSpace * 2 + bw ,
                        mSpace * 2 + bw+ bw ,mSpace * 2 + bw + bw);
                abs[2] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace * 3 + bw * 2, mSpace * 2 + bw ,
                        mSpace * 3 + bw * 2 + bw ,mSpace * 2 + bw + bw);
                abs[3] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace , mSpace * 3 + bw * 2,
                        mSpace + bw ,mSpace * 3 + bw * 2 + bw);
                abs[4] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace * 2 + bw , mSpace * 3 + bw * 2,
                        mSpace * 2 + bw + bw ,mSpace * 3 + bw * 2 + bw);
                abs[5] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace * 3 + bw * 2 , mSpace * 3 + bw * 2,
                        mSpace * 3 + bw * 2 + bw ,mSpace * 3 + bw * 2 + bw);
                abs[6] = new ArgsBean(bw,rect);
                break;
            case 8:
                bw = (mWidth - mSpace*4)/3 ;
                rect = new RectF((mWidth - bw * 2 - mSpace)/2 , mSpace ,
                        (mWidth - bw * 2 - mSpace)/2 + bw ,mSpace + bw );
                abs[0] = new ArgsBean(bw,rect);

                rect = new RectF((mWidth - bw * 2 - mSpace)/2 + bw + mSpace, mSpace ,
                        (mWidth - bw * 2 - mSpace)/2 + bw + mSpace + bw ,mSpace + bw );
                abs[1] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace , mSpace * 2 + bw ,
                        mSpace + bw ,mSpace * 2 + bw + bw);
                abs[2] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace * 2 + bw, mSpace * 2 + bw ,
                        mSpace * 2 + bw+ bw ,mSpace * 2 + bw + bw);
                abs[3] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace * 3 + bw * 2, mSpace * 2 + bw ,
                        mSpace * 3 + bw * 2 + bw ,mSpace * 2 + bw + bw);
                abs[4] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace , mSpace * 3 + bw * 2,
                        mSpace + bw ,mSpace * 3 + bw * 2 + bw);
                abs[5] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace * 2 + bw , mSpace * 3 + bw * 2,
                        mSpace * 2 + bw + bw ,mSpace * 3 + bw * 2 + bw);
                abs[6] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace * 3 + bw * 2 , mSpace * 3 + bw * 2,
                        mSpace * 3 + bw * 2 + bw ,mSpace * 3 + bw * 2 + bw);
                abs[7] = new ArgsBean(bw,rect);
                break;
            case 9:
                bw = (mWidth - mSpace*4)/3 ;
                rect = new RectF(mSpace , mSpace ,
                        mSpace + bw ,mSpace + bw );
                abs[0] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace * 2 + bw, mSpace ,
                        mSpace * 2 + bw + bw ,mSpace + bw );
                abs[1] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace * 3 + bw * 2, mSpace ,
                        mSpace * 3 + bw * 2 + bw ,mSpace + bw );
                abs[2] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace , mSpace * 2 + bw ,
                        mSpace + bw ,mSpace * 2 + bw + bw);
                abs[3] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace * 2 + bw, mSpace * 2 + bw ,
                        mSpace * 2 + bw+ bw ,mSpace * 2 + bw + bw);
                abs[4] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace * 3 + bw * 2, mSpace * 2 + bw ,
                        mSpace * 3 + bw * 2 + bw ,mSpace * 2 + bw + bw);
                abs[5] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace , mSpace * 3 + bw * 2,
                        mSpace + bw ,mSpace * 3 + bw * 2 + bw);
                abs[6] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace * 2 + bw , mSpace * 3 + bw * 2,
                        mSpace * 2 + bw + bw ,mSpace * 3 + bw * 2 + bw);
                abs[7] = new ArgsBean(bw,rect);

                rect = new RectF(mSpace * 3 + bw * 2 , mSpace * 3 + bw * 2,
                        mSpace * 3 + bw * 2 + bw ,mSpace * 3 + bw * 2 + bw);
                abs[8] = new ArgsBean(bw,rect);
                break;
            default:
                break;
        }
        return abs ;
    }


    //包含小bitmap的大小、位置信息
    class ArgsBean implements Serializable{

        private int bw ;  //小bitmap的尺寸 1:1
        private RectF rect ; //小bitmap的位置矩形
        private Bitmap bitmap ;

        public ArgsBean(int bw, RectF rect) {
            this.bw = bw;
            this.rect = rect;
        }
    }






}
