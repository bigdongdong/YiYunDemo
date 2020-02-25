package com.chen.firstdemo.dialog_demo;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chen.firstdemo.R;
import com.chen.firstdemo.dialog_demo.shadow_popwindow.ShadowPopupWindow;

public class WatchPopWindow extends ShadowPopupWindow {
    public WatchPopWindow(Activity context) {
        super(context);

    }

    @Override
    protected void onCreateView(View view) {

        ImageView  dIV = view.findViewById(R.id.dismissIV);
        TextView confirmTV = view.findViewById(R.id.confirmTV);
        dIV.setOnClickListener(
                v -> dismiss()
        );

        confirmTV.setOnClickListener(
                v -> dismiss()
        );
    }
    public void show(int id){
       showAtLocation(LayoutInflater.from(context).inflate(id,null), Gravity.BOTTOM,0,0);
    }

    @Override
    protected Object getLayoutIdOrView() {
        return R.layout.dialog_goto_earn_gold_coin;
    }

}
