package com.chen.firstdemo.scroller_demo;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.TextView;
import android.widget.Toast;

public class FlingView extends ViewGroup {
    private final String TAG = "FlingViewTAG";

    GestureDetector gestureDetector ;
    OverScroller scroller ;
    private Context context ;
    public FlingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context ;

        this.removeAllViews();
        for (int i = 0; i < 10; i++) {
            TextView tv = new TextView(context);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1,200);
            p.setMargins(50,10,50,10);
            tv.setLayoutParams(p);
            tv.setBackgroundColor(Color.GRAY);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(40);
            tv.setText(i+"");
            tv.setTextColor(Color.WHITE);
            final int finalI = i ;
//            tv.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context,""+finalI,Toast.LENGTH_SHORT).show();
//                }
//            });
            this.addView(tv);
        }

        scroller = new OverScroller(context , new DecelerateInterpolator(2f));
        gestureDetector = new GestureDetector(context,new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.i(TAG, "onSingleTapUp: "+e.getAction());
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.i(TAG, "onScroll: distanceY = "+distanceY);
                scrollBy(0, (int) distanceY);

                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.i(TAG, "onFling: velocityX = "+velocityX);
                Log.i(TAG, "onFling: velocityY = "+velocityY);



//                scroller.fling(0,(int)e2.getY(),0,
//                        (int)velocityY,0,0,10,30);
                invalidate();
                return false;
            }
        });
    }

    private int innerTop , innerBottom ;
    private int[] location = new int[2];
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            int top = 300 * i + 10 * (i<0?0:i-1);
            getChildAt(i).layout(0,top , r , top + 300);


        }

        this.getLocationInWindow(location);
        innerTop = location[1] + getPaddingTop() ;
        innerBottom = location[1] + getMeasuredHeight() - getPaddingBottom() ;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);


        switch(event.getAction()){
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                /*滑动到顶部和底部的时候 不允许滑动*/
                View view = getChildAt(0) ;
                view.getLocationInWindow(location);
                if(location[1] > innerTop){
//                    scroller.startScroll(0,location[1],0,location[1] - innerTop);
                    scrollBy(0, location[1] - innerTop);
//                    invalidate();
                }

                view = getChildAt(getChildCount() -1) ;
                view.getLocationInWindow(location);
                int vb = location[1] + view.getHeight();
                if(vb < innerBottom){
//                    scroller.startScroll(0,vb,0,vb - innerBottom);
                    scrollBy(0,  vb - innerBottom);
//                    invalidate();
                }
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        if(scroller.computeScrollOffset()){
//            scrollBy(0,  scroller.getStartY() - scroller.getCurrY());
//            Log.i(TAG, "computeScroll: " +scroller.getCurrY());
//            Log.i(TAG, "computeScroll: scroller.getCurrY() - scroller.getFinalY() =" + (scroller.getCurrY() - scroller.getFinalY()));
            postInvalidate();
        }
    }
}
