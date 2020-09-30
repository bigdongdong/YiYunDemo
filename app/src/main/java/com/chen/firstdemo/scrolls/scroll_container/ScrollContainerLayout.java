package com.chen.firstdemo.scrolls.scroll_container;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import android.widget.ScrollView;

import com.chen.firstdemo.R;

public class ScrollContainerLayout extends FrameLayout {
    private final String TAG = "ScrollContainerLayout";
    private final int DEFAULT_HEADER_AND_FOOTER_HEIGHT_PX = 300 ; //默认页眉页脚的高度 px
    private boolean isFirstTimeOnMeasure ;

    private View mHeaderView ;
    private View mFooterView ;
    private View mContentView ;

    private int w,h ;

    private OverScroller scroller ;

    public ScrollContainerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setFocusable(true);
        isFirstTimeOnMeasure = true ;

        /*初始化mHeaderView & mFooterView*/
        mHeaderView = new View(context);
        LayoutParams lp = new LayoutParams(-1,DEFAULT_HEADER_AND_FOOTER_HEIGHT_PX);
        lp.gravity = Gravity.TOP ;
        mHeaderView.setAlpha(0.5f);
        mHeaderView.setLayoutParams(lp);
        mHeaderView.setBackgroundResource(R.mipmap.ic_launcher);

        mFooterView = new View(context);
        lp = new LayoutParams(-1,DEFAULT_HEADER_AND_FOOTER_HEIGHT_PX);
        lp.gravity = Gravity.BOTTOM ;
        mFooterView.setAlpha(0.5f);
        mFooterView.setLayoutParams(lp);
        mFooterView.setBackgroundResource(R.mipmap.ic_launcher);

        scroller = new OverScroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(isFirstTimeOnMeasure){
            if(getChildCount() != 1){
                throw new RuntimeException("ScrollContainerLayout必须且只能包含一个子View");
            }
            isFirstTimeOnMeasure = false;
            this.addView(mHeaderView,0);
            this.addView(mFooterView,2);
        }

        w = getMeasuredWidth() ;
        h = getMeasuredHeight() ;
        View contentView = getChildAt(1);
        final int contentViewWidthMeasureSpec = MeasureSpec.makeMeasureSpec(w,MeasureSpec.EXACTLY);
        final int contentViewHeightMeasureSpec = MeasureSpec.makeMeasureSpec(h,MeasureSpec.EXACTLY);
        contentView.measure(contentViewWidthMeasureSpec,contentViewHeightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        View view ;

        view = getChildAt(0);
        view.layout(0,0,w,DEFAULT_HEADER_AND_FOOTER_HEIGHT_PX);

        mContentView = getChildAt(1);
        mContentView.layout(0,DEFAULT_HEADER_AND_FOOTER_HEIGHT_PX,w,DEFAULT_HEADER_AND_FOOTER_HEIGHT_PX+h);

        view = getChildAt(2);
        view.layout(0,DEFAULT_HEADER_AND_FOOTER_HEIGHT_PX+h,w,DEFAULT_HEADER_AND_FOOTER_HEIGHT_PX*2+h);

        scrollTo(0,DEFAULT_HEADER_AND_FOOTER_HEIGHT_PX);

    }

    private float mEventDownY ;
    private float mLastEventMoveY ;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                mEventDownY = mLastEventMoveY = ev.getY();
                //如果子View正在处理顶部或者底部悬停事件，拦截并回弹
                if(isTriggeringBottomHoverEvent() || isTriggeringTopHoverEvent()){
                    springBackToTopIfNecessary();
                    springBackToBottomIfNecessary();
                }
                return false ;
            case MotionEvent.ACTION_MOVE:
                final float mCurEventMoveY = ev.getY();
                int dy = (int) (mCurEventMoveY - mLastEventMoveY);
                mLastEventMoveY = mCurEventMoveY;
                if(dy > 0){//下拉
                    //如果子View到达了顶部，拦截
//                    Log.i(TAG, "onInterceptTouchEvent: 下拉");
                    return isContentViewReachTheTop();
                }else if(dy < 0){//上滑
                    Log.i(TAG,  "onInterceptTouchEvent: isContentViewReachTheTop() "+isContentViewReachTheTop());
                    //如果子View到达了底部，拦截
                    if(isContentViewReachTheTop()){
                        return false;
                    }else{
                        return isContentViewReachTheBottom();
                    }
                }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if(isHeaderViewLeakOut() || isFooterViewLeakOut()){
                    //如果顶部或者底部漏出，拦截
                    return true ;
                }
                break;
        }

        return false ;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch(action){
            case MotionEvent.ACTION_MOVE:
                //这里只负责parent的滚动
                final float mCurEventMoveY = ev.getY();
                int dy = (int) (mCurEventMoveY - mLastEventMoveY);
                if(dy < 0){
                    dy = Math.max(dy,-DEFAULT_HEADER_AND_FOOTER_HEIGHT_PX);
                }else{
                    dy = Math.min(dy,DEFAULT_HEADER_AND_FOOTER_HEIGHT_PX);
                }
                scrollTo(0,DEFAULT_HEADER_AND_FOOTER_HEIGHT_PX - dy);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if(isHeaderViewLeakOut() && !isTriggeringTopHoverEvent()){
                    //顶部脱落却没有触发顶部悬停事件
                    springBackToTopIfNecessary(); //顶部回弹
                }

                if(isFooterViewLeakOut() && !isTriggeringBottomHoverEvent()){
                    //底部脱落缺没有触发底部悬停事件
                    springBackToBottomIfNecessary(); //底部回弹
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            postInvalidate();
        }
    }

    /*判断headerView 是否漏出来了*/
    private boolean isHeaderViewLeakOut(){
        return getScrollY() < DEFAULT_HEADER_AND_FOOTER_HEIGHT_PX ;
    }

    /*判断footerView 是否漏出来了*/
    private boolean isFooterViewLeakOut(){
        return getScrollY() > DEFAULT_HEADER_AND_FOOTER_HEIGHT_PX ;
    }

    /*是否正在触发顶部悬停事件*/
    private boolean isTriggeringTopHoverEvent(){
//        return getScrollY() == 0 ;
        return false;
    }

    /*是否正在触发顶部悬停事件*/
    private boolean isTriggeringBottomHoverEvent(){
//        return getScrollY() == DEFAULT_HEADER_AND_FOOTER_HEIGHT_PX * 2 ;
        return false;
    }

    /*判断contentView是否到达了顶端*/
    private boolean isContentViewReachTheTop(){
        if(mContentView instanceof ScrollView){
            ScrollView sv = (ScrollView) mContentView;
            return sv.getScrollY() == 0 ;
        }
        return true;
    }

    private boolean isContentViewReachTheBottom(){
        if(mContentView instanceof ScrollView){
            ScrollView sv = (ScrollView) mContentView;
            return sv.getScrollY() + h == sv.getChildAt(0).getMeasuredHeight() ;
        }
        return false;
    }


    /*顶部回弹*/
    private void springBackToTopIfNecessary(){
        final int curScrollY = getScrollY();
        if(curScrollY < DEFAULT_HEADER_AND_FOOTER_HEIGHT_PX){
            //顶部回弹
            scroller.startScroll(0,curScrollY,0,DEFAULT_HEADER_AND_FOOTER_HEIGHT_PX - curScrollY,500);
            postInvalidate();
        }
    }

    /*底部回弹*/
    private void springBackToBottomIfNecessary(){
        final int curScrollY = getScrollY();
        if(curScrollY > DEFAULT_HEADER_AND_FOOTER_HEIGHT_PX){
            //顶部回弹
            scroller.startScroll(0,curScrollY,0,DEFAULT_HEADER_AND_FOOTER_HEIGHT_PX - curScrollY,500);
            postInvalidate();
        }
    }
}
