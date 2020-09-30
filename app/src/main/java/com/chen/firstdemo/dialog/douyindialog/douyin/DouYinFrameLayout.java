package com.chen.firstdemo.dialog.douyindialog.douyin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.OverScroller;

/**
 * 2020/9/27
 * 实现抖音评论弹窗的parent
 * 半成品，惯性滑动传递不到子View
 * 另一种实现方式已100%实现：
 * {@link com.chen.firstdemo.dialog.LikeDouYinDialog}
 * 只是上面做这种方式阻尼和回收比较慢
 */
@SuppressLint("LongLogTag")
public class DouYinFrameLayout extends FrameLayout {
    private final String TAG = "DouYinFrameLayout";
    private final int DEFAULT_SPRING_TIME_MS = 500; //默认阻尼时间ms
    private final float DEFAULT_CLOSE_RATIO = 0.5f ; //默认关闭比例
    private View child ;
    private OverScroller scroller ;
    private OnCloseListener listener ;

    public DouYinFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        scroller = new OverScroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        child = getChildAt(0);

        for (int i = 0; i < getChildCount(); i++) {
            final View v = getChildAt(i);
            v.measure(MeasureSpec.makeMeasureSpec(v.getMeasuredWidth(),MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(v.getMeasuredHeight(),MeasureSpec.UNSPECIFIED));
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if(listener != null && top >= getMeasuredHeight() * 0.99f){
            listener.onClose();
        }
    }

    private float mEventDownY ;
    private float mEventLastMoveY ;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action){
            //down只采集不拦截
            case MotionEvent.ACTION_DOWN:
                mEventLastMoveY = mEventDownY = ev.getY();
                return false ;
            case MotionEvent.ACTION_MOVE:
                final float curEventMoveY = ev.getY();
                final boolean isDown = curEventMoveY > mEventLastMoveY ;
                mEventLastMoveY = curEventMoveY;
                if(isDown){
                    //下拉
                    if(child != null){
                        if(child.getScrollY() == 0){
                            return true ;
                        }else{
                            return false ;
                        }
                    }else{
                        return true ;
                    }
                }else{
                    //上滑
                    if(getTop() != 0){
                        //不在顶部
                        return true ;
                    }else{
                        //在顶部给到子View
                        if(child != null){
                            return child.onTouchEvent(ev);
                        }
                        return false ;
                    }
                }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if(getTop() > 0){
                    return true ;
                }else {
                    return false;
                }
            default:
                return false ;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_MOVE:
                final float curEventMoveY = ev.getY() ;
                final float dy = curEventMoveY - mEventDownY ;
                layout(getLeft(), (int) (getTop()+dy),getRight(),getBottom());
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                final int top = getTop() ;
                if(top > 0){
                    //顶部脱离
                    if(top > getMeasuredHeight() * DEFAULT_CLOSE_RATIO){
                        //消失
                        scroller.startScroll(0,top,0,getMeasuredHeight()-top, DEFAULT_SPRING_TIME_MS);
                    }else{
                        //回弹
                        scroller.startScroll(0,top,0,-top, DEFAULT_SPRING_TIME_MS);
                    }
                    postInvalidate();
                }else{
                    return false;
                }
                break;
        }
        return false;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        //动画未结束 ， 计算还在进行中
        if(scroller.computeScrollOffset()){
            // view 滚动到对应位置
            layout(getLeft(), scroller.getCurrY(),getRight(),getBottom());
        }
    }

    /****************api****************/

    public void setOnCloseListener(OnCloseListener listener){
        this.listener = listener ;
    }

    public interface OnCloseListener{
        void onClose();
    }
}
