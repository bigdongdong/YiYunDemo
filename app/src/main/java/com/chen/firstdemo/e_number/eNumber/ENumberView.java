package com.chen.firstdemo.e_number.eNumber;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ENumberView extends View {

    private int w ;
    private RazorBlade[] razorBlades ; //刀片集


    public ENumberView(Context context) {
        this(context,null);
    }

    public ENumberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if(w != getMeasuredWidth()){
            ENumberShape ens = new ENumberShape(w);
            razorBlades = ens.getRazorBlades();
            this.setBackground(new ShapeDrawable(ens));

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

}
