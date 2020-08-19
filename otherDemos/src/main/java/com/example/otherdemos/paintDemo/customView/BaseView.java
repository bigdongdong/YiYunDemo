package com.example.otherdemos.paintDemo.customView;


import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.otherdemos.R;

/*星期 和 节次数字*/
public class BaseView extends LinearLayout {

    private final TextView textView;

    public BaseView(Context context, int w, int h,int fontSize) {
        this(context,null,w,h,fontSize);

    }

    public BaseView(Context context, AttributeSet attrs, int w, int h,int fontSize) {
        super(context, attrs);


        View view= LayoutInflater.from(context).inflate(R.layout.paint_custom_view_base, null);
        textView=(TextView)view.findViewById(R.id.textView);
        textView.setWidth(w);
        textView.setHeight(h);
        textView.setTextSize(fontSize);

        addView(view);
    }
    public void setText(String className){
        textView.setText(className);
    }
    public void setRight(){
        textView.setGravity(Gravity.RIGHT);
    }

}