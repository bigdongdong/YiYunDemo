package com.chen.firstdemo.sticky_nest_scrollview_demo;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.chen.firstdemo.R;

/**
 * 可以悬停子view的scrollView 继承自NestedScrollView
 * 但是有一个致命缺点，只支持API 21 及以上
 */
public class StickyScrollView extends NestedScrollView {
    private final String TAG = "StickyScrollView" ;
    private int mOffsetY = 0;
    private int mHeaderViewId ;
    private View mHeaderView ;

    public StickyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ssv);
        mHeaderViewId = ta.getResourceId(R.styleable.ssv_header,0);

        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mHeaderView = StickyScrollView.this.findViewById(mHeaderViewId);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mHeaderView.setTranslationZ(1);
                }
                StickyScrollView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /*计算高度*/
        if(getChildCount() > 0 && mOffsetY == 0){
            ViewGroup vp = (ViewGroup) getChildAt(0);
            for (int i = 0; i < vp.getChildCount(); i++) {
                View v = vp.getChildAt(i);

                if(v == null){
                    continue;
                }

                //此时mHeaderView还没绘制完成，
                // 所以mHeaderView是null，只能用view的id判断
                if(v.getId() == mHeaderViewId){
                    break;
                }

                mOffsetY += v.getMeasuredHeight();
            }
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && t > mOffsetY && mHeaderView != null){
            mHeaderView.setTranslationY(t - mOffsetY);
        }
    }
}
