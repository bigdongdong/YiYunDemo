package com.chen.firstdemo.new_beiyu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.jar.Attributes;

public class ProgressView extends View {
    private boolean isArab = false ;
    private boolean isLayoutOver = false ;

    private int w , h ;
    private RectF pr ;
    private RectF br ;
    private Paint pp ;
    private Paint bp;
    private int curX ;

    public ProgressView(Context context , AttributeSet atts) {
        super(context,atts);
        this.setWillNotDraw(false);

        pr = new RectF();
        br = new RectF();
        pp = new Paint();

        pp = new Paint();
        pp.setColor(Color.parseColor("#3AF6E7"));
        pp.setAntiAlias(true);
        pp.setStyle(Paint.Style.FILL);

        bp = new Paint();
        bp.setColor(Color.parseColor("#C3FCF7"));
        bp.setAntiAlias(true);
        bp.setStyle(Paint.Style.FILL);

//        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                if(isLayoutOver){
//                    ProgressView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                    return;
//                }
//                setProcess(0,100);
//                isLayoutOver = true;
//                ProgressView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//            }
//        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        isArab = this.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
        w = getMeasuredWidth();
        h = getMeasuredHeight();

        br.set(0,0,w,h);
    }

    public void setProcess(final float cur ,final float total){
        if(!isLayoutOver){
            this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    isLayoutOver = true;
                    final int rateX = (int) (cur/total * w); //当前进度的长度
                    curX = rateX;
                    invalidate();
                    ProgressView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });

        }else{
            final int rateX = (int) (cur/total * w); //当前进度的长度
            curX = rateX;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRoundRect(br,h/2,h/2,bp);
        if(curX > 0 && curX <= w){
            if(isArab){
                pr.set(w-curX,0,w,h);
            }else{
                pr.set(0,0,curX,h);
            }
            canvas.drawRoundRect(pr,h/2,h/2,pp);
        }else if(curX > w){
            canvas.drawRoundRect(br,h/2,h/2,pp);
        }

    }
}