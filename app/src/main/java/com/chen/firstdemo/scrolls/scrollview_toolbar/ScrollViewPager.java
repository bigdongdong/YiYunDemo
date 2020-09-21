package com.chen.firstdemo.scrolls.scrollview_toolbar;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.chen.firstdemo.utils.DensityUtil;
import com.chen.firstdemo.utils.ScreenUtil;


/**
 * Created by chenxiaodong on 2019/12/10
 *
 * 可以动态控制高度的viewpager，用于解决scrollview嵌套不显示问题，
 * 高度为viewpager内部最高的子view + 60dp
 */
public class ScrollViewPager extends ViewPager {

    Context context ;
    public ScrollViewPager(Context context) {
        super(context);
    }

    public ScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context ;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height)height = h;
        }
        height = height + DensityUtil.dip2px(context , 60) ;//底部留余60dp

        height = Math.min(height, ScreenUtil.getScreenHeight(context));

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
