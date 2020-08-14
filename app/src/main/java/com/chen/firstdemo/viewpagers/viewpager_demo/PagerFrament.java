package com.chen.firstdemo.viewpagers.viewpager_demo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.LazyFragment;

public class PagerFrament extends LazyFragment {

    int position ;
    TextView textView ;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pager;
    }

    @Override
    protected void onBundle(Bundle arguments) {

    }

    @Override
    protected void onCreateView(View view) {
        textView = view.findViewById(R.id.textView);
        //关闭硬件加速
//        textView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        textView.setBackground(new ShapeDrawable(new ShadowShape(10,
//                Color.GRAY,
//                50,
//                Color.WHITE)));
        textView.setText(position+"");
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void lazyLoad() {
        Log.i("aaa", "lazyLoad: "+position);

    }

    public void setPosition(int position){
        this.position = position ;
    }
}
