package com.chen.firstdemo.multiple_img_view;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MultipleImgViewActivity extends BaseActivity {

    @BindView(R.id.miv1)
    MultipleImgView miv1;
    @BindView(R.id.miv2)
    MultipleImgView miv2;
    @BindView(R.id.miv3)
    MultipleImgView miv3;
    @BindView(R.id.miv4)
    MultipleImgView miv4;
    @BindView(R.id.miv5)
    MultipleImgView miv5;
    @BindView(R.id.miv6)
    MultipleImgView miv6;
    @BindView(R.id.miv7)
    MultipleImgView miv7;
    @BindView(R.id.miv8)
    MultipleImgView miv8;
    @BindView(R.id.miv9)
    MultipleImgView miv9;
    @BindView(R.id.uiButton)
    Button uiButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_img_view);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.uiButton)
    public void onViewClicked() {
        miv1.setImgs(R.mipmap.miv1);
        miv2.setImgs(R.mipmap.miv1, R.mipmap.miv2);
        miv3.setImgs(R.mipmap.miv1, R.mipmap.miv2, R.mipmap.miv3);
        miv4.setImgs(R.mipmap.miv1, R.mipmap.miv2, R.mipmap.miv3, R.mipmap.miv4);
        miv5.setImgs(R.mipmap.miv1, R.mipmap.miv2, R.mipmap.miv3, R.mipmap.miv4, R.mipmap.miv5);
        miv6.setImgs(R.mipmap.miv1, R.mipmap.miv2, R.mipmap.miv3, R.mipmap.miv4, R.mipmap.miv5, R.mipmap.miv6);
        miv7.setImgs(R.mipmap.miv1, R.mipmap.miv2, R.mipmap.miv3, R.mipmap.miv4, R.mipmap.miv5, R.mipmap.miv6, R.mipmap.miv7);
        miv8.setImgs(R.mipmap.miv1, R.mipmap.miv2, R.mipmap.miv3, R.mipmap.miv4, R.mipmap.miv5, R.mipmap.miv6, R.mipmap.miv7, R.mipmap.miv8);
        miv9.setImgs(R.mipmap.miv1, R.mipmap.miv2, R.mipmap.miv3, R.mipmap.miv4, R.mipmap.miv5, R.mipmap.miv6, R.mipmap.miv7, R.mipmap.miv8, R.mipmap.miv9);
    }
}
