package com.chen.firstdemo.pager_demo;

import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.LazyFragment;
import com.chen.firstdemo.diy_view_demo.shape.ShadowShape;

import org.w3c.dom.Text;

public class PagerFrament extends LazyFragment {

    int position ;
    TextView textView ;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pager;
    }

    @Override
    protected void onCreateView(View view) {
        textView = view.findViewById(R.id.textView);
        //关闭硬件加速
        textView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        textView.setBackground(new ShapeDrawable(new ShadowShape(10,
                Color.GRAY,
                50,
                Color.WHITE)));
        textView.setText(position+"");
    }

    @Override
    protected void lazyLoad() {
        Log.i("aaa", "lazyLoad: "+position);

    }

    public void setPosition(int position){
        this.position = position ;
    }
}
