package com.chen.firstdemo.chen_jin_pop_demo;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.chen.firstdemo.R;
import com.chen.firstdemo.dialog_demo.shadow_popwindow.ShadowPopupWindow;

public class TopPop extends ShadowPopupWindow {
    public TopPop(Activity context) {
        super(context);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setHeight(500);
    }

    @Override
    protected long getAnimatorDuration() {
        return 500;
    }

    @Override
    protected void onCreateView(View view) {

    }

    public void show(int layoutId){
        View view = LayoutInflater.from(context).inflate(layoutId,null);
        showAtLocation(view, Gravity.TOP,0,0);
    }
    public void show(View view){
        showAtLocation(view, Gravity.TOP,0,0);
    }

    @Override
    protected Object getLayoutIdOrView() {
        return R.layout.pop_top;
    }
}
