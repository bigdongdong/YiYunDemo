package com.chen.firstdemo.sticky_nest_scrollview_demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.chen.firstdemo.R;

public class StickyScrollView extends NestedScrollView {

    private final String TAG = "StickyScrollView" ;
    private int mHeaderId ;
    private int mOffsetY = 0;
    private View mHeadView ;

    @SuppressLint("ResourceType")
    public StickyScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ssv);
        mHeaderId = ta.getResourceId(R.styleable.ssv_header,0);
        Log.i(TAG, "StickyScrollView: "+R.styleable.ssv_header);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /*计算高度*/
        if(getChildCount() > 0 && mOffsetY == 0){
            ViewGroup vp = (ViewGroup) getChildAt(0);
            View v ;
            for (int i = 0; i < vp.getChildCount(); i++) {
                v = getChildAt(i);
                if(v == null){
                    continue;
                }

                mOffsetY += v.getMeasuredHeight();

                if(v.getId() == mHeaderId){
                    break;
                }
            }
        }
        Log.i(TAG, "onLayout: " + mHeaderId);

        Log.i(TAG, "onMeasure: "+ mOffsetY);
    }
}
