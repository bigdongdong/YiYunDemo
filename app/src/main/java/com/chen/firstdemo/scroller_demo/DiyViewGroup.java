package com.chen.firstdemo.scroller_demo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Scroller;

public class DiyViewGroup extends ViewGroup {
    private final String TAG = "DiyViewGroupTAG";

    GestureDetector gestureDetector ;
    int[] location = new int[2];
    int[] location2 = new int[2];
    Scroller scroller ;

    public DiyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);

        gestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                getChildAt(0).getLocationInWindow(location);
                getChildAt(2).getLocationInWindow(location2);

                /*手势滑动*/
                if((distanceX < 0 && location[0] <= getLeft()) ||
                        (distanceX > 0 && location2[0] >= getLeft())){
                    scrollBy((int) distanceX,0);
                }

                getChildAt(0).getLocationInWindow(location);
                getChildAt(2).getLocationInWindow(location2);

                /*左侧差值滑动*/
                if(location[0] > getLeft()){
                    scrollBy(location[0] - getLeft(),0);
                }

                /*右侧 差值滑动*/
                if(location2[0] < getLeft()){
                    scrollBy(location2[0] - getLeft(),0);
                }

                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            /*手指抬起的时候才会调用*/
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.i(TAG, "onFling: e1"+e1.getAction());
                Log.i(TAG, "onFling: e2"+e2.getAction());

                return false;
            }
        });

        scroller = new Scroller(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i(TAG, "onLayout: l = "+l +"  t = "+t +"   r = "+r +"   b = "+b);

        this.getLayoutParams().width = (r-l)*3;

        for (int i = 0; i < this.getChildCount(); i++) {
            this.getChildAt(i).layout((i) * getMeasuredWidth()  , 0, (i+1) * getMeasuredWidth()  ,getMeasuredHeight());
        }
    }

    int scrollX , position;
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        gestureDetector.onTouchEvent(e);

        switch(e.getAction()){
            case MotionEvent.ACTION_MOVE:
                scrollX = getScrollX();//相对于初始位置滑动的距离

                //你滑动的距离加上屏幕的一半，除以屏幕宽度，就是当前图片显示的pos.如果你滑动距离超过了屏幕的一半，这个pos就加1
                position = (getScrollX() + getWidth() / 2) / getWidth();

                break;
            case MotionEvent.ACTION_UP:
//                scrollTo(position * getWidth() , 0);
                //滚动，startX, startY为开始滚动的位置，dx,dy为滚动的偏移量
                scroller.startScroll(scrollX, 0, -(scrollX - position * getWidth()), 0);
                invalidate();//使用invalidate这个方法会有执行一个回调方法computeScroll，我们来重写这个方法
                break;
        }
        return true ;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), 0);
            Log.e(TAG, "scroller.getCurrX()=" + scroller.getCurrX());
            postInvalidate();
        }else{
//            scroller.fling();
        }
    }
}
