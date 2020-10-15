package com.chen.firstdemo.scrolls.scroll_container;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.chen.firstdemo.R;

public class ScrollContainerView extends FrameLayout {
    private final String TAG = "ScrollContainerView";
    private final int DEFAULT_HEADER_AND_FOOTER_HEIGHT_PX = 500 ; //默认页眉页脚的高度 px
    private final int DEFAULT_SPRING_BACK_DURATION_MS = 300 ;//默认阻尼回弹时间 ms
    private boolean isFirstTimeOnMeasure ;
    private int w,h;
    private View mHeaderView ;
    private View mFooterView ;
    private View mContentView ;

    private boolean isSpringBacking ;
    private ValueAnimator mTopSpringBackAnimator;
    private ValueAnimator mBottomSpringBackAnimator;

    public ScrollContainerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        isFirstTimeOnMeasure = true ;
        isSpringBacking = false ;

        /*初始化mHeaderView & mFooterView*/
        mHeaderView = new View(context);
        LayoutParams lp = new LayoutParams(-1,DEFAULT_HEADER_AND_FOOTER_HEIGHT_PX);
        lp.gravity = Gravity.TOP ;
        mHeaderView.setLayoutParams(lp);
        mHeaderView.setBackgroundResource(R.mipmap.ic_launcher);

        mFooterView = new View(context);
        lp = new LayoutParams(-1,DEFAULT_HEADER_AND_FOOTER_HEIGHT_PX);
        lp.gravity = Gravity.BOTTOM ;
        mFooterView.setLayoutParams(lp);
        mFooterView.setBackgroundResource(R.mipmap.ic_launcher);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(isFirstTimeOnMeasure){
            if(getChildCount() != 1){
                throw new RuntimeException(getClass().getSimpleName()+"必须且只能包含一个子View");
            }
            mContentView = getChildAt(0);
            isFirstTimeOnMeasure = false;

            this.addView(mHeaderView,0);
            this.addView(mFooterView,2);
        }

        w = getMeasuredWidth() ;
        h = getMeasuredHeight() ;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mHeaderView.layout(0,0,w,0);
        mContentView.layout(0,0,w,h);
        mFooterView.layout(0,h,w,h);

    }

    private float mEventDownY ;
    private float mLastEventMoveY ;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                if(isSpringBacking){
                    return true ;
                }

                mEventDownY = ev.getY();
                mLastEventMoveY = mEventDownY ;

//                if(isTriggeringTopHoverEvent() || isTriggeringBottomHoverEvent()){
//                    springBackToTopIfNecessary();
//                    springBackToBottomIfNecessary();
//                }
                return false ;
            case MotionEvent.ACTION_MOVE:
                final float mCurEventMoveY = ev.getY();
                int dy = (int) (mCurEventMoveY - mLastEventMoveY);
                if(dy < 0 && isTriggeringTopHoverEvent()){
                    springBackToTopIfNecessary();
                }else if(dy > 0 && isTriggeringBottomHoverEvent()){
                    springBackToBottomIfNecessary();
                }
                mLastEventMoveY = mCurEventMoveY;
                return (dy > 0 && isContentViewReachTheTop()) || (dy < 0 && isContentViewReachTheBottom()) ;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if(mHeaderView.getHeight() > 0 || mFooterView.getHeight() > 0){
                    //如果顶部或者底部漏出，拦截
                    return true ;
                }
                break;
        }

        return super.onInterceptTouchEvent(ev) ;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch(action){
            case MotionEvent.ACTION_MOVE:
                final float mCurEventMoveY = ev.getY();
                int dy = (int) (mCurEventMoveY - mEventDownY);
                if(dy > 0 && isContentViewReachTheTop()){
                    //下拉
                    dy = Math.min(dy,DEFAULT_HEADER_AND_FOOTER_HEIGHT_PX);
                    mHeaderView.layout(0,0,w,dy);
                    mContentView.layout(0,dy,w,h+dy);
                }else if(dy < 0 && isContentViewReachTheBottom()){
                    //上提
                    dy = Math.max(dy,-DEFAULT_HEADER_AND_FOOTER_HEIGHT_PX);
                    mContentView.layout(0,dy,w,h+dy);
                    mFooterView.layout(0,h+dy,w,h);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if(mHeaderView.getHeight() > 0 && !isTriggeringTopHoverEvent()){
                    //顶部脱落却没有触发顶部悬停事件
                    springBackToTopIfNecessary(); //顶部回弹
                }

                if(mFooterView.getHeight() > 0 && !isTriggeringBottomHoverEvent()){
                    //底部脱落缺没有触发底部悬停事件
                    springBackToBottomIfNecessary(); //底部回弹
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /*是否正在触发顶部悬停事件*/
    private boolean isTriggeringTopHoverEvent(){
        return mHeaderView.getHeight() == DEFAULT_HEADER_AND_FOOTER_HEIGHT_PX ;
//        return false ;
    }

    /*是否正在触发底部悬停事件*/
    private boolean isTriggeringBottomHoverEvent(){
        return mFooterView.getHeight() == DEFAULT_HEADER_AND_FOOTER_HEIGHT_PX ;
//        return false;
    }

    /*判断contentView是否到达了顶端*/
    private boolean isContentViewReachTheTop(){
        if(mContentView instanceof ScrollView){
            ScrollView sv = (ScrollView) mContentView;
            return sv.getScrollY() == 0 ;
        }
        return true;
    }

//    private boolean isContentViewReachTheTop() {
//        View contentView = this.getChildAt(0);
//        return contentView != null && !contentView.canScrollVertically(-1);
//    }

    /*判断contentView是否到达了底部*/
    private boolean isContentViewReachTheBottom(){
        if(mContentView instanceof ScrollView){
            ScrollView sv = (ScrollView) mContentView;
            return sv.getScrollY() + h == sv.getChildAt(0).getMeasuredHeight() ;
        }
        return false;
    }

    /*顶部回弹*/
    private void springBackToTopIfNecessary(){
        /*属性动画实现回弹*/
        if(mHeaderView.getHeight() > 0){
            if(mTopSpringBackAnimator == null){
                mTopSpringBackAnimator = new ValueAnimator();
                mTopSpringBackAnimator.setInterpolator(new DecelerateInterpolator());
                mTopSpringBackAnimator.setDuration(DEFAULT_SPRING_BACK_DURATION_MS);
                mTopSpringBackAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        final int value = (Integer) animation.getAnimatedValue();
                        mHeaderView.layout(0,0,w,value);
                        mContentView.layout(0,value,w,h+value);
                    }
                });
                mTopSpringBackAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        isSpringBacking = true ;
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isSpringBacking = false ;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        isSpringBacking = false ;
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
            mTopSpringBackAnimator.setIntValues(mHeaderView.getHeight(),0);
            mTopSpringBackAnimator.start();

        }
    }

    /*底部回弹*/
    private void springBackToBottomIfNecessary(){
        /*属性动画实现回弹*/
        if(mFooterView.getHeight() > 0){
            if(mBottomSpringBackAnimator == null){
                mBottomSpringBackAnimator = new ValueAnimator();
                mBottomSpringBackAnimator.setInterpolator(new DecelerateInterpolator());
                mBottomSpringBackAnimator.setDuration(DEFAULT_SPRING_BACK_DURATION_MS);
                mBottomSpringBackAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        final int value = (Integer) animation.getAnimatedValue();
                        mContentView.layout(0,-value,w,h-value);
                        mFooterView.layout(0,h-value,w,h);
                    }
                });
                mBottomSpringBackAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        isSpringBacking = true ;
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isSpringBacking = false ;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        isSpringBacking = false ;
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
            mBottomSpringBackAnimator.setIntValues(mFooterView.getHeight(),0);
            mBottomSpringBackAnimator.start();

        }
    }

}
