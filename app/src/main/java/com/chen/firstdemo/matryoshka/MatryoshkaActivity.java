package com.chen.firstdemo.matryoshka;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MatryoshkaActivity extends BaseActivity {

    @BindView(R.id.mv)
    MatryoshkaView mv;
    @BindView(R.id.shrinkBtn)
    Button shrinkBtn;
    @BindView(R.id.zoomBtn)
    Button zoomBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matryoshka);
        ButterKnife.bind(this);


        shrinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mv.shrink();
            }
        });
        zoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mv.zoom();
            }
        });
    }
}