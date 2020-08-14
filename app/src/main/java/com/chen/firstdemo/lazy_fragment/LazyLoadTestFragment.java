package com.chen.firstdemo.lazy_fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.LazyFragment;

@SuppressLint("ValidFragment")
public class LazyLoadTestFragment extends LazyFragment{
    private final String TAG = "LazyLoadTestFragment-->TAG";

    int position ;
    TextView tv ;

    public LazyLoadTestFragment(int position) {
        this.position = position;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test_lazyload;
    }

    @Override
    protected void onBundle(Bundle arguments) {

    }

    @Override
    protected void onCreateView(View view) {
        tv = view.findViewById(R.id.tv);
        tv.setText(""+position);
        for(int i = 0 ;i<1000;i++){
            System.out.println(i);
        }
    }

    @Override
    protected void initialize() {

    }

    @SuppressLint("LongLogTag")
    @Override
    protected void lazyLoad() {
        Log.i(TAG, "lazyBaseLoad: "+position);
    }
}


