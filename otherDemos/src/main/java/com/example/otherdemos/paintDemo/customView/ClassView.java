package com.example.otherdemos.paintDemo.customView;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.otherdemos.R;


/*一个课程*/
public class ClassView extends LinearLayout {

    private final TextView textView;
    private final TextView textRoom;
    private String uuid;

    public ClassView(Context context, int w, int h,int fontSize) {
        this(context,null,w,h,fontSize);

    }

    public ClassView(Context context, AttributeSet attrs, int w, int h,int fontSize) {
        super(context, attrs);


        View view= LayoutInflater.from(context).inflate(R.layout.paint_custom_view_class, null);
        textView=(TextView)view.findViewById(R.id.textView);
        textRoom = (TextView)view.findViewById(R.id.textRoom);

        double bili23 = (double)2/(double)3;
        double bili13 = (double)1/(double)3;
        textView.setWidth(w);
        textRoom.setWidth(w);
//        textView.setHeight((int)(h*bili23));
//        textRoom.setHeight((int)(h*bili13));
        textView.setHeight((int)(h*0.5));
        textRoom.setHeight((int)(h*0.5));

        textView.setTextSize(fontSize);
        if(fontSize <= 0){
            textRoom.setTextSize(fontSize);
        }else{
            textRoom.setTextSize(fontSize-1);
        }
        addView(view);
    }
    public void setText(String className,String classRoom){
        textView.setText(className);
        textRoom.setText(classRoom);
    }
    public void setUUid(String uuid){
        this.uuid=uuid;
    }

}