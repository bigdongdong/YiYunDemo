package com.chen.firstdemo.new_beiyu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

public class NewBeiyuProgressView extends LinearLayout {
    private int w , h ;
    private PopView mPopView ;
    private ProgressView mProgressView ;
    private LayoutParams params ;
    private Context context;
    private boolean isLayoutOver = false ;
    private boolean isArab = false ;

    public NewBeiyuProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        this.setWillNotDraw(false);
        this.setClipChildren(false);
        this.setOrientation(LinearLayout.VERTICAL);

        mPopView = new PopView(context);
        params = new LayoutParams(-2,dp2px(22));
        params.bottomMargin = dp2px(5);
        mPopView.setLayoutParams(params);
        this.addView(mPopView);

        mProgressView = new ProgressView(context);
        params = new LayoutParams(-1,dp2px(8));
        mProgressView.setLayoutParams(params);
        this.addView(mProgressView);

        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(isLayoutOver){
                    NewBeiyuProgressView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    return;
                }
                mPopView.setTranslationX(isArab ? mPopView.getWidth()/2 : -mPopView.getWidth()/2);
                mPopView.setText("0");
                isLayoutOver = true;
                NewBeiyuProgressView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        isArab = this.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
        w = getMeasuredWidth();
        h = getMeasuredHeight();

    }

    public void value(final float cur ,final float total){
        if(!isLayoutOver){
            this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    final int rateX = (int) (cur/total * w); //当前进度的长度

                    mPopView.translate(Math.min(rateX,w)*(isArab ? -1:1));
                    mPopView.setText(String.valueOf((int)cur));

                    mProgressView.setProcess(rateX);
                    NewBeiyuProgressView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    isLayoutOver = true;
                }
            });

        }else{
            final int rateX = (int) (cur/total * w); //当前进度的长度

            mPopView.translate(Math.min(rateX,w)*(isArab ? -1:1));
            mPopView.setText(String.valueOf((int)cur));

            mProgressView.setProcess(rateX);
        }
    }

    class PopView extends android.support.v7.widget.AppCompatTextView {
        private int w , h ,ph;
        private Path mPath ;
        private Paint mPaint ;
        private RectF rect ;
        private final int r ;
        private final int aw;
        private final int ah;

        public PopView(Context context) {
            super(context);
            r = dp2px(6);
            aw = dp2px(8.7f);
            ah = dp2px(4.4f);

            this.setWillNotDraw(false);
            this.setGravity(Gravity.CENTER);
            this.setTextSize(12);
            this.setTextColor(Color.WHITE);
            TextPaint tp = this.getPaint();
            tp.setTypeface(Typeface.DEFAULT_BOLD);
            this.setMinWidth(dp2px(20));
            this.setPadding(dp2px(6),0,dp2px(6),ah);

            mPaint = new Paint();
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.parseColor("#34EBDD"));
            mPaint.setAntiAlias(true);

            mPath = new Path();
            rect = new RectF();

        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);

            w = getMeasuredWidth() ;
            h = getMeasuredHeight();
            ph = h - ah ;
            final int rtaw = (w-aw-2*r)/2;

            mPath.reset();
            mPath.moveTo(0,r);
            rect.set(0,0,r,r);
            mPath.arcTo(rect,180,90);
            mPath.lineTo(w-r*2,0);
            rect.set(w-r,0,w,r);
            mPath.arcTo(rect,270,90);
            mPath.lineTo(w,ph-r);
            rect.set(w-r,ph-r,w,ph);
            mPath.arcTo(rect,0,90);
            mPath.lineTo(w-r-rtaw,ph);
            mPath.lineTo(w/2,h);
            mPath.lineTo(r+rtaw,ph);
            mPath.lineTo(r,ph);
            rect.set(0,ph-r,r,ph);
            mPath.arcTo(rect,90,90);
            mPath.close();

        }

        public void translate(final int rateX){
//            PopView.this.setTranslationX(rateX - w/2 * (isArab?-1:1));
            //w是变动的
            this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    PopView.this.setTranslationX(rateX - w/2 * (isArab?-1:1));
                    PopView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        }

        @Override
        protected void onDraw(Canvas canvas) {
            /*填充背景色*/
            canvas.drawPath(mPath,mPaint);

            super.onDraw(canvas);
        }
    }

    class ProgressView extends View {
        private int w , h ;
        private RectF pr ;
        private RectF br ;
        private Paint pp ;
        private Paint bp;
        private int curX ;

        public ProgressView(Context context) {
            super(context);
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
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);

            w = getMeasuredWidth();
            h = getMeasuredHeight();

            br.set(0,0,w,h);
        }

        public void setProcess(int rateX){
            curX = rateX;
            invalidate();
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

    public int dp2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
