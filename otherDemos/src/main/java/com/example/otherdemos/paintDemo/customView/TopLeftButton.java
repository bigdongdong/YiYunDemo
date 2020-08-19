package com.example.otherdemos.paintDemo.customView;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.otherdemos.R;


/*上午和下午*/
public class TopLeftButton extends LinearLayout {

    private final TextView button;

    public TopLeftButton(Context context,int fontSize) {
        this(context,null,fontSize);

    }

    public TopLeftButton(Context context, AttributeSet attrs,int fontSize) {
        super(context, attrs);


        View view= LayoutInflater.from(context).inflate(R.layout.paint_custom_view_topleft, null);
        button=view.findViewById(R.id.button);
        button.setTextSize(fontSize+6);
        addView(view);
    }
    public void setSize(int w,int h){
        ViewGroup.LayoutParams layoutParams=button.getLayoutParams();
        layoutParams.width=w;
        layoutParams.height=h;
        button.setLayoutParams(layoutParams);
    }

}