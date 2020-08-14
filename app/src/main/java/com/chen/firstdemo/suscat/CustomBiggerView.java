package com.chen.firstdemo.suscat;

import android.content.Context;
import android.util.AttributeSet;

import com.andexert.library.RippleView;

/**
 * 放大按钮
 */

public class CustomBiggerView extends RippleView {
    public CustomBiggerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRippleDuration(70);
        setRippleAlpha(0);
        setZooming(true);
        setZoomDuration(100);
        setZoomScale(1.05f);
    }

    public CustomBiggerView(Context context) {
        super(context);
    }

    public CustomBiggerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

}
