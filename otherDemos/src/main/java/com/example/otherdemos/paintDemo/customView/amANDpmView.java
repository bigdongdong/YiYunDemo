package com.example.otherdemos.paintDemo.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.otherdemos.R;


/*上午和下午*/
public class amANDpmView extends LinearLayout {

    private final TextView textView;

    public amANDpmView(Context context, int w, int h,int fontSize) {
        this(context,null,w,h,fontSize);

    }

    public amANDpmView(Context context, AttributeSet attrs, int w, int h,int fontSize) {
        super(context, attrs);


        View view= LayoutInflater.from(context).inflate(R.layout.paint_custom_view_am_and_pm, null);
        textView=(TextView)view.findViewById(R.id.textView);
        textView.setWidth(w);
        textView.setHeight(h);
        textView.setTextSize(fontSize);

        addView(view);
    }
    public void setText(String className){
        textView.setText(className);
    }

}