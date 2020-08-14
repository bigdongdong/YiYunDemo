package com.chen.firstdemo.new_beiyu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chen.firstdemo.utils.DensityUtil;

public class IntegralProgressView extends RelativeLayout {
    private Context mContext ;
    private int w , h ;
    private int inLeft , inRight ;

    private boolean isArab = false ;
    private final int bgColor = Color.parseColor("#E8E5E9");

    private ItemView[] ivs ;


    public IntegralProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context ;

        ivs = new ItemView[5];
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                initViews();
                IntegralProgressView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        isArab = this.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
        w = getMeasuredWidth();
        h = getMeasuredHeight();
    }

    public void initViews(){
        /*生成五个iv*/
        int[] status = new int[]{1,0,0,1,0};
        for (int i = 0; i < 5; i++) {
            final int w = status[i] == 1 ? DensityUtil.dip2px(mContext,35) : DensityUtil.dip2px(mContext,25) ;
            ItemView iv = new ItemView(mContext,w);
            ivs[i] = iv ;
        }

        inLeft = ivs[0].w / 2 ;
        inRight = ivs[4].w / 2 ;

        /*进度条*/
        ProgressView pv = new ProgressView(mContext,0.75f);
        LayoutParams params = new LayoutParams(-1,DensityUtil.dip2px(mContext,10));
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.setMarginStart(inLeft);
        params.setMarginEnd(inRight);
        pv.setLayoutParams(params);
        this.addView(pv);

        /*添加ivs*/
        final int space = (w - ivs[0].w/2 - ivs[4].w/2 )/ 4 ;
        for (int i = 0; i < 5; i++) {
            int transX = (-ivs[i].w/2 + space*i+inLeft) * (isArab ? -1: 1);
            ivs[i].setTranslationX(transX);
            ivs[i].setBackgroundColor(Color.parseColor("#33111111"));
            this.addView(ivs[i]);
        }
    }

    class ItemView extends AppCompatImageView {

        public int w ;

        public ItemView(Context context , int width) {
            super(context);
            this.setWillNotDraw(false);

            LayoutParams params = new LayoutParams(width,width);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            this.setLayoutParams(params);

            w = width ;
        }

    }


    public class ProgressView extends View {
        /*进度条渐变颜色*/
        private final int[] grantColors = new int[]{Color.parseColor("#31F4ED"),Color.parseColor("#1DB8FF")};

        private int w , h ;
        private RectF pr ;
        private RectF br ;
        private Paint pp ;
        private Paint bp;
        private int curX ;
        private float rate ;

        /**
         * @param context
         * @param rate 0.0 ~ 1.0 进度比例
         */
        public ProgressView(Context context , float rate) {
            super(context);
            this.setWillNotDraw(false);

            pr = new RectF();
            br = new RectF();
            pp = new Paint();

            pp = new Paint();
            pp.setAntiAlias(true);
            pp.setStyle(Paint.Style.FILL);

            bp = new Paint();
            bp.setColor(bgColor);
            bp.setAntiAlias(true);
            bp.setStyle(Paint.Style.FILL);

            this.rate = rate ;
        }

        @SuppressLint("DrawAllocation")
        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
            w = getMeasuredWidth();
            h = getMeasuredHeight();

            br.set(0, 0, w, h);
            curX = (int) (rate * w);

            if(isArab){
                pr.set(w-curX,0,w,h);
            }else{
                pr.set(0,0,curX,h);
            }

            Shader shader ;
            if(isArab){
                shader = new LinearGradient(pr.right,pr.centerY(),pr.left,pr.centerY(),grantColors,null, Shader.TileMode.CLAMP);
            }else{
                shader = new LinearGradient(pr.left,pr.centerY(),pr.right,pr.centerY(),grantColors,null, Shader.TileMode.CLAMP);
            }
            pp.setShader(shader);
        }

        @SuppressLint("DrawAllocation")
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawRect(br,bp);
            if(curX > 0 && curX <= w){
                canvas.drawRect(pr,pp);
            }else if(curX > w){
                canvas.drawRect(br,pp);
            }

        }
    }
}
