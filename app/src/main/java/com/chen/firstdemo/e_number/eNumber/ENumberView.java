package com.chen.firstdemo.e_number.eNumber;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ENumberView extends View {

    private int W,H,nW,nS;
    private Eight mEight ;

    public ENumberView(Context context) {
        this(context,null);
    }

    public ENumberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if(W != getMeasuredWidth()){
            W = getMeasuredWidth();
            nW = (int) (W * 0.2f);
            H = W + (W-nW) ;
            nS = nW / 10 ;

            mEight = new Eight(W,H,nW, nS);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#33111111"));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        for (int i = 0; i < mEight.getRects().length; i++) {
            Rect rect = new Rect(mEight.getRects()[i]);
            rect.inset(3,3);
            canvas.drawRect(rect,paint);
        }
    }

    /**
     * 电子数字八
     * 内部存储7个菱形{@link Rhombus}
     */
    private class Eight{
        private Rhombus[] rhombuses ;
        private Rect[] rects ;

        /**
         *
         * @param W
         * @param H
         * @param nW  菱形块的厚度
         * @param space  两个菱形的间隔px
         */
        public Eight(int W , int H ,int nW , int space) {
            Rect rect ;
            rects = new Rect[7];
            final int wl = W-nW ; //横菱形的长
            final int hl = (H-nW)/2 ; //竖菱形的长


            /*0*/
            rect = new Rect(nW/2,0,nW/2+wl,nW);
            rects[0] = rect ;
            /*1*/
            rect = new Rect(0,nW/2,nW,nW/2+hl);
            rects[1] = rect ;
            /*2*/
            rect = new Rect(W-nW,nW/2,W,nW/2+hl);
            rects[2] = rect ;
            /*3*/
            rect = new Rect(nW/2,hl,W-nW/2,hl+nW);
            rects[3] = rect ;
            /*4*/
            rect = new Rect(0,H/2,nW,H-nW/2);
            rects[4] = rect ;
            /*5*/
            rect = new Rect(W-nW,H/2,W,H-nW/2);
            rects[5] = rect ;
            /*6*/
            rect = new Rect(nW/2,H-nW,W-nW/2,H);
            rects[6] = rect ;


            rhombuses = new Rhombus[7];
            for (int i = 0; i < 7; i++) {
                rhombuses[i] = generateRhombusByRect(rects[i],space);
            }
        }

        private Rhombus generateRhombusByRect(Rect rect , int space){
            Rect temp = new Rect(rect);
            temp.inset(space/2,space/2);
            return new Rhombus(temp);
        }

        public Rect[] getRects(){
            return rects ;
        }
    }

    public class Rhombus {
        private Point[] points ;

        public Rhombus(Rect rect) {
            int w = rect.width() ;
            int h = rect.height() ;

            points = w > h ? convertPointsByHorizontalRhombus(w,h) : convertPointsByVertitalRhombus(w,h);
        }

        /**
         * 计算水平菱形点集合
         * @return
         */
        private Point[] convertPointsByHorizontalRhombus(int w , int h){
            Point[] points = new Point[6];


        }

        /**
         * 计算垂直菱形点集合
         * @return
         */
        private Point[] convertPointsByVertitalRhombus(int w , int h){
            Point[] points = new Point[6];


        }
    }
}
