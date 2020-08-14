package com.chen.firstdemo.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;

public abstract class MyBottomSheetDialog extends BottomSheetDialog {

    protected View view ;
    protected Context context ;
    public MyBottomSheetDialog(@NonNull Context context) {
        super(context);

        this.context = context ;

        if(getLayoutIdOrView() instanceof View){
            view = (View) getLayoutIdOrView();
        }else if(getLayoutIdOrView() instanceof Integer){
            view = LayoutInflater.from(context).inflate((Integer) getLayoutIdOrView(),null);
        }else{
            throw new RuntimeException("type error");
        }

        setContentView(view);
        onCreateView(view);

    }


    protected abstract void onCreateView(View view);

    protected abstract Object getLayoutIdOrView();
}
