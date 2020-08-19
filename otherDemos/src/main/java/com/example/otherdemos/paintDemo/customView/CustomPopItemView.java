package com.example.otherdemos.paintDemo.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.otherdemos.R;
/**
 * Created by CXD on 2018/11/16.
 */
public class CustomPopItemView extends RelativeLayout {

    private final ImageView imageView;
    private final TextView textView;

    public CustomPopItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.paint_item_menu, this);
        textView = findViewById(R.id.text);
        imageView = findViewById(R.id.icon);

//        textView.setTextSize(fontSize);
    }

    /*设置图片和文字*/
    public void setStyle(int id,String text){
        imageView.setImageResource(id);
        textView.setText(text);
    }

    public void setDrawable(Context context,int id){
        imageView.setBackgroundDrawable(context.getResources().getDrawable(id));
    }
}
