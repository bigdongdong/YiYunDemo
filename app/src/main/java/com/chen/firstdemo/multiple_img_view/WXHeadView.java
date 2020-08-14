package com.chen.firstdemo.multiple_img_view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;


import com.bumptech.glide.Glide;
import com.chen.firstdemo.utils.DensityUtil;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

import javax.annotation.Nullable;

/**
 * create by chenxiaodong on 2020/6/1
 * 仿微信群组头像
 */
public class WXHeadView extends View {
    private final int mConner ;
    private int mSpace ;

    private Context mContext ;
    private int mWidth ;
    private int mHeight ;

    private Canvas mCanvas ;
    private Bitmap mBitmap ;

    public WXHeadView(Context context) {
        this(context,null);
    }

    public WXHeadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setWillNotDraw(false);
        mContext = context ;
        mConner = DensityUtil.dip2px(mContext,10);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        mSpace = (int) ((mWidth * 1.0f) / 25);
    }

    /**
     * 使用Glide作为加载方式
     * 没有做layout判断，不建议使用
     * @param datas
     * @param defaultDes
     */
    @Deprecated
    public void loadImgsWithGlide(Object[] datas , Integer defaultDes){
        loadImgs(datas,new WXHeadView.OnSyncLoadImgListener(){
            @Override
            public Bitmap onLoad(Object data, int bw ) throws ExecutionException, InterruptedException {
                return Glide.with(mContext)
                        .asBitmap()
                        .load(data)
                        .submit(bw,bw)
                        .get();
            }
        });
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        Path p = new Path();
        p.addRoundRect(new RectF(0,0,mWidth,mHeight),mConner,mConner, Path.Direction.CCW);
        canvas.clipPath(p);

        super.onDraw(canvas);

        if(mBitmap != null){
            canvas.drawBitmap(mBitmap,0,0,null);
        }

    }

    public synchronized void loadImgs(Object[] urls ,OnSyncLoadImgListener listener){
        if(listener == null || urls == null || urls.length == 0){
            return;
        }
        if(mWidth == 0 || mHeight == 0){
            Log.i("aaa", "loadImgs: 未绘制完");
            //未layout完
            this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    loadImgs(urls,listener);
                    WXHeadView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    return;
                }
            });
            return;
        }

        Log.i("aaa", "loadImgs: 已绘制完");

        //根据长度，获取每一个的位置信息
        final int len = urls.length ;
        ArgsBean[] abs = computeArgs(len);

        //同步下载所有小bitmap
        new Thread(){
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < len; i++) {
                    Bitmap b;
                    try {
                        b = listener.onLoad(urls[i],abs[i].bw);
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
                            RectF rectF = new RectF();

                            for (int i = 0; i < len; i++) {
                                Canvas c ;
                                Bitmap b ;
                                if(abs[i].bitmap == null){
                                    b = Bitmap.createBitmap(abs[i].bw,abs[i].bw, Bitmap.Config.ARGB_4444);
                                    c = new Canvas(b);
                                    c.drawColor(Color.parseColor("#AAAAAA"));
                                    abs[i].bitmap = b ;
                                }
                                rectF.set(abs[i].left,abs[i].top, abs[i].left+abs[i].bw ,abs[i].top+abs[i].bw);
                                mCanvas.drawBitmap(abs[i].bitmap,null,rectF,null);
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
        int bw ;

        if(len == 1){
            bw = mWidth - mSpace * 2 ;
            abs[0] = new ArgsBean(bw,mSpace,mSpace);
        }else if(len == 2){
            bw = (mWidth - mSpace * 3) / 2 ;
            for (int i = 0; i < len; i++) {
                abs[i] = new ArgsBean(bw,mSpace + bw * i + mSpace * i, (mHeight - bw) / 2);
            }
        }else if(len >= 3 && len <= 4){ // 3 ~ 4
            bw = (mWidth - mSpace*3) / 2 ;
            for (int i = 0; i < len; i++) {
                if(i < len - 2){
                    //第一排
                    if(len == 3){
                        abs[i] = new ArgsBean(bw,(mWidth -bw)/2, mSpace);
                    }else{
                        abs[i] = new ArgsBean(bw,mSpace * (i+1) + bw * i , mSpace);
                    }
                }else{
                    //第二排
                    abs[i] = new ArgsBean(bw,mSpace * (i - len + 3) + bw * (i - len + 2), mSpace * 2 + bw);
                }
            }
        }else if(len >= 5 && len <= 6){ // 5 ~ 6
            bw = (mWidth - mSpace*4)/3 ;
            for (int i = 0; i < len; i++) {
                if(i < len - 3){
                    //第一排
                    if(len == 5){
                        abs[i] = new ArgsBean(bw,(mWidth - bw * 2 - mSpace) / 2 + bw * i + mSpace * i,
                                (mHeight - bw * 2 - mSpace) / 2);
                    }else{
                        abs[i] = new ArgsBean(bw,mSpace * (i + 1) + bw * i , (mHeight - bw * 2 - mSpace)/2);
                    }
                }else{
                    //第二排
                    abs[i] = new ArgsBean(bw,mSpace * (i - len + 4) + bw * (i - len + 3),
                            (mHeight - mSpace)/2 + mSpace);
                }
            }
        }else if(len >= 7){             // 7 ~ 9
            bw = (mWidth - mSpace*4)/3 ;
            for (int i = 0; i < len; i++) {
                if(i < len - 6){
                    //第一排
                    if(len == 7){
                        abs[i] = new ArgsBean(bw,(mWidth - bw ) / 2 ,mSpace);
                    }else if(len == 8){
                        abs[i] = new ArgsBean(bw,(mWidth - bw * 2 - mSpace) / 2 + bw * i + mSpace * i, mSpace);
                    }else{
                        abs[i] = new ArgsBean(bw,mSpace * (i + 1) + bw * i, mSpace);
                    }
                }else {
                    //第二排 & 第三排
                    abs[i] = new ArgsBean(bw,mSpace * (-(len - i - 6)% 3 +1) + bw * (-(len - i - 6)% 3),
                            mSpace * (i / (len - 3) + 2) + bw * (i / (len - 3) + 1));
                }
            }
        }
        return abs ;
    }

    //包含小bitmap的大小、位置信息
    class ArgsBean implements Serializable{

        private int bw ;  //小bitmap的尺寸 1:1
        private int left ;
        private int top ;
        private Bitmap bitmap ;

        public ArgsBean(int bw, int left, int top) {
            this.bw = bw;
            this.left = left;
            this.top = top;
        }
    }


    public interface OnSyncLoadImgListener{
        Bitmap onLoad(Object url, int bw) throws ExecutionException,InterruptedException;
    }



}
