package com.example.otherdemos.mindMappingDemo.customView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.otherdemos.R;


/**
 * Created by CXD on 2018/11/23.
 */
public class MM_CustomNodeView extends RelativeLayout {
    View view ;
    Context context;
    TextView  nodeText ;
//    public static final int nodeWidth = 60; //node总宽度
    public MM_CustomNodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context ;

        initView();
    }

    void initView(){
        LayoutInflater.from(context).inflate(R.layout.mm_custom_node,this);
        nodeText= this.findViewById(R.id.node);
//        nodeText.setTextSize(nodeWidth/3);
//        setMargins(nodeText,nodeWidth/4,nodeWidth/2,nodeWidth/4,0);
    }

    public void setText(String text){
        nodeText.setText(text);
    }


}
