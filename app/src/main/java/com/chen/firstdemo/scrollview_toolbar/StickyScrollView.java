package com.chen.firstdemo.scrollview_toolbar;

import android.content.Context;
import android.util.AttributeSet;

/**
 * create by chenxiaodong on 2019/12/23
 *
 * 继承自粘性scrollView，同时暴露onScrollChanged方法
 */
public class StickyScrollView extends com.amar.library.ui.StickyScrollView {


    private Listener  listener ;
    public StickyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int mScrollX, int mScrollY, int oldX, int oldY) {
        super.onScrollChanged(mScrollX, mScrollY, oldX, oldY);

        if(this.listener != null){
            listener.onScrollChangeListener(mScrollX,mScrollY,oldX,oldY);
        }
    }

    public void setOnScrollChangeListener(Listener  listener ){
        this.listener = listener ;
    }


    interface Listener{
        void onScrollChangeListener(int mScrollX, int mScrollY, int oldX, int oldY);
    }
}
