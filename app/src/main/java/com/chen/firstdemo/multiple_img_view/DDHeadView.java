package com.chen.firstdemo.multiple_img_view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import com.bumptech.glide.Glide;
import com.chen.firstdemo.R;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

/**
 * create by chenxiaodong on 2020/6/1
 * 仿钉钉群组头像，支持1~9个图片的组合展示，其强大远超乎你想象
 */
public class DDHeadView extends View {
    private int mSpace ;

    private Context mContext ;
    private int mWidth ;
    private int mHeight ;

    private Canvas mCanvas ;
    private Bitmap mBitmap ;
    private Bitmap mDefaultBitmap;

    public DDHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context ;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        mSpace = (int) ((mWidth * 1.0f) / 35);

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#DDDDDD"));
        gd.setCornerRadius(mWidth / 2);
        this.setBackground(gd);
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        Path p = new Path();
        p.addCircle(mWidth/2,mHeight/2,mWidth/2, Path.Direction.CCW);
        canvas.clipPath(p);
        super.onDraw(canvas);

        if(mBitmap != null){
            canvas.drawBitmap(mBitmap,0,0,null);
        }

    }

    /**
     *
     * @param datas 数据集合,url集合
     * @param listener 同步获得bitmap的监听
     * @param defalutDes 无图或者加载失败的默认占位图
     */
    public void loadImgs(Object[] datas ,OnSyncLoadImgListener listener , Integer defalutDes){
        if(listener == null || datas == null || datas.length == 0){
            return;
        }
        if(mWidth == 0 || mHeight == 0){
            //未layout完
            this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    loadImgs(datas,listener,defalutDes);
                    DDHeadView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    return;
                }
            });
            return;
        }
        //根据长度，获取每一个的位置信息
        final int len = datas.length ;
        ArgsBean[] abs = computeArgs(len);

        //同步下载所有小bitmap
        new Thread(){
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < len; i++) {
                    Bitmap b;
                    try {
                        b = listener.onLoad(datas[i],abs[i].bw);
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
                            if (mWidth <= 0 || mHeight <= 0) {
                                return;
                            }
                            mBitmap = Bitmap.createBitmap(mWidth,mHeight, Bitmap.Config.ARGB_4444);
                            mCanvas = new Canvas(mBitmap);
                            RectF rectF = new RectF();

                            for (int i = 0; i < len; i++) {
                                if(abs[i].bitmap == null){
                                    if(mDefaultBitmap == null){
                                        mDefaultBitmap = BitmapFactory.decodeResource(mContext.getResources(),
                                                defalutDes == null ? R.mipmap.miv0 : defalutDes);
                                    }
                                    abs[i].bitmap = mDefaultBitmap ;
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

    /**
     * 使用Glide作为加载方式
     * 没有做layout判断，不建议使用
     * @param datas
     * @param defaultDes
     */
    @Deprecated
    public void loadImgsWithGlide(Object[] datas , Integer defaultDes){
        loadImgs(datas,new OnSyncLoadImgListener() {
            @Override
            public Bitmap onLoad(Object data, int bw ) throws ExecutionException, InterruptedException {
                return Glide.with(mContext)
                        .asBitmap()
                        .load(data)
                        .submit(bw,bw)
                        .get();
            }
        },defaultDes);
    }

    /**
     * 计算每一个图片的坐标位置
     * @param len  图片集合长度
     * @return
     */
    private ArgsBean[] computeArgs(int len){
        if(len > 9){
            len = 9 ;
        }
        ArgsBean[] abs = new ArgsBean[len];
        int bw ;

        if(len == 1){
            bw = mWidth;
            abs[0] = new ArgsBean(bw,0,0);
        }else if(len == 2){
            bw = (mWidth - mSpace ) / 2 ;
            for (int i = 0; i < len; i++) {
                abs[i] = new ArgsBean(bw,bw * i + mSpace * i, (mHeight - bw) / 2);
            }
        }else if(len >= 3 && len <= 4){ // 3 ~ 4
            bw = (mWidth - mSpace) / 2 ;
            for (int i = 0; i < len; i++) {
                if(i < len - 2){
                    //第一排
                    if(len == 3){
                        abs[i] = new ArgsBean(bw,(mWidth -bw)/2, 0);
                    }else{
                        abs[i] = new ArgsBean(bw,mSpace * i + bw * i , 0);
                    }
                }else{
                    //第二排
                    abs[i] = new ArgsBean(bw,mSpace * (i - len + 2) + bw * (i - len + 2), mSpace + bw);
                }
            }
        }else if(len >= 5 && len <= 6){ // 5 ~ 6
            bw = (mWidth - mSpace*2)/3 ;
            for (int i = 0; i < len; i++) {
                if(i < len - 3){
                    //第一排
                    if(len == 5){
                        abs[i] = new ArgsBean(bw,(mWidth - bw * 2 - mSpace) / 2 + bw * i + mSpace * i,
                                (mHeight - bw * 2 - mSpace) / 2);
                    }else{
                        abs[i] = new ArgsBean(bw,mSpace * i + bw * i , (mHeight - bw * 2 - mSpace)/2);
                    }
                }else{
                    //第二排
                    abs[i] = new ArgsBean(bw,mSpace * (i - len + 3) + bw * (i - len + 3),
                            (mHeight - mSpace)/2 + mSpace);
                }
            }
        }else if(len >= 7){             // 7 ~ 9
            bw = (mWidth - mSpace*2)/3 ;
            for (int i = 0; i < len; i++) {
                if(i < len - 6){
                    //第一排
                    if(len == 7){
                        abs[i] = new ArgsBean(bw,(mWidth - bw ) / 2 ,0);
                    }else if(len == 8){
                        abs[i] = new ArgsBean(bw,(mWidth - bw * 2 - mSpace) / 2 + bw * i + mSpace * i, 0);
                    }else{
                        abs[i] = new ArgsBean(bw,mSpace * i + bw * i, 0);
                    }
                }else {
                    //第二排 & 第三排
                    abs[i] = new ArgsBean(bw,mSpace * (-(len - i - 6)% 3) + bw * (-(len - i - 6)% 3),
                            mSpace * (i / (len - 3) + 1) + bw * (i / (len - 3) + 1));
                }
            }
        }
        return abs ;
    }

    //包含小bitmap的大小、位置信息
    class ArgsBean implements Serializable{

        private int bw ;  //小bitmap的尺寸
        private int left ;
        private int top ;
        private Bitmap bitmap ;

        public ArgsBean(int bw, int left, int top) {
            this.bw = bw;
            this.left = left;
            this.top = top;
        }
    }


    /**
     * 同步加载图片bitmap的回调
     */
    public interface OnSyncLoadImgListener{
        Bitmap onLoad(Object data, int bw) throws ExecutionException,InterruptedException;
    }



}
