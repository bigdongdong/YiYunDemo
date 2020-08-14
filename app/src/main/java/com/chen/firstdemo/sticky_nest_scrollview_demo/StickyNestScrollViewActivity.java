package com.chen.firstdemo.sticky_nest_scrollview_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StickyNestScrollViewActivity extends BaseActivity {

    @BindView(R.id.headerView)
    Button headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_nest_scroll_view);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.headerView)
    public void onViewClicked() {
        Log.i(TAG, "onViewClicked: ");
    }
}
