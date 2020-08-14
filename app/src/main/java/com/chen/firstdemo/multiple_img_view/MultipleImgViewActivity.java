package com.chen.firstdemo.multiple_img_view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MultipleImgViewActivity extends BaseActivity {

    @BindView(R.id.miv1)
    WXHeadView miv1;
    @BindView(R.id.miv2)
    WXHeadView miv2;
    @BindView(R.id.miv3)
    WXHeadView miv3;
    @BindView(R.id.miv4)
    WXHeadView miv4;
    @BindView(R.id.miv5)
    WXHeadView miv5;
    @BindView(R.id.miv6)
    WXHeadView miv6;
    @BindView(R.id.miv7)
    WXHeadView miv7;
    @BindView(R.id.miv8)
    WXHeadView miv8;
    @BindView(R.id.miv9)
    WXHeadView miv9;
    @BindView(R.id.uiButton)
    Button uiButton;

    List<String> heads;
    @BindView(R.id.ddhv1)
    DDHeadView ddhv1;
    @BindView(R.id.ddhv2)
    DDHeadView ddhv2;
    @BindView(R.id.ddhv3)
    DDHeadView ddhv3;
    @BindView(R.id.ddhv4)
    DDHeadView ddhv4;
    @BindView(R.id.ddhv5)
    DDHeadView ddhv5;
    @BindView(R.id.ddhv6)
    DDHeadView ddhv6;
    @BindView(R.id.ddhv7)
    DDHeadView ddhv7;
    @BindView(R.id.ddhv8)
    DDHeadView ddhv8;
    @BindView(R.id.ddhv9)
    DDHeadView ddhv9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_img_view);
        ButterKnife.bind(this);

        heads = new ArrayList<>();
        heads.add("https://image.soudlink.net/pic/26c22ac8b2002432c9f600acb40a7cd3.png");
        heads.add("https://image.soudlink.net/pic/78C078E5DE6595552BC3AB5B226A05CC.png");
        heads.add("https://image.soudlink.net/pic/e88136b39029d61e440e3e268b7207e6.png");
        heads.add("https://image.soudlink.net/pic/A86E5C602181BA0D551BA4AABB3EEF04.png");
        heads.add("https://image.soudlink.net/pic/6A5F93297790EB8185358158AD1DB40A.png");
        heads.add("http://thirdwx.qlogo.cn/mmopen/vi_32/TXneJicINLuPH0sl23QPhq3p7cmrUUHkSKxsLlbicD9A2Px2TIVN2ic07kONlVhBicsTgCpLCJ7RXmH93ibUjibQAXcg/132");
        heads.add("https://image.soudlink.net/pic/a597142856c8f18bb50e39d5b2d01d3d.png");
        heads.add("http://thirdqq.qlogo.cn/qqapp/101418471/A042BB9EB0BD96F07AED0DA05D0ADC34/100");
        heads.add("https://image.soudlink.net/pic/47819B2D0E31C2EAE6DE51C013130E8A.png");
        heads.add("https://image.soudlink.net/pic/B639A3C047DD8984D1260983751C6EB2.png");


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                uiButton.callOnClick();
            }
        },1000);
    }


    @OnClick(R.id.uiButton)
    public void onViewClicked() {
        miv1.loadImgsWithGlide(heads.subList(0, 1).toArray(),0);
        miv2.loadImgsWithGlide(heads.subList(0, 2).toArray(),0);
        miv3.loadImgsWithGlide(heads.subList(0, 3).toArray(),0);
        miv4.loadImgsWithGlide(heads.subList(0, 4).toArray(),0);
        miv5.loadImgsWithGlide(heads.subList(0, 5).toArray(),0);
        miv6.loadImgsWithGlide(heads.subList(0, 6).toArray(),0);
        miv7.loadImgsWithGlide(heads.subList(0, 7).toArray(),0);
        miv8.loadImgsWithGlide(heads.subList(0, 8).toArray(),0);
        miv9.loadImgsWithGlide(heads.subList(0, 9).toArray(),0);

        ddhv1.loadImgsWithGlide(heads.subList(0, 1).toArray(),0);
        ddhv2.loadImgsWithGlide(heads.subList(0, 2).toArray(),0);
        ddhv3.loadImgsWithGlide(heads.subList(0, 3).toArray(),0);
        ddhv4.loadImgsWithGlide(heads.subList(0, 4).toArray(),0);
        ddhv5.loadImgsWithGlide(heads.subList(0, 5).toArray(),0);
        ddhv6.loadImgsWithGlide(heads.subList(0, 6).toArray(),0);
        ddhv7.loadImgsWithGlide(heads.subList(0, 7).toArray(),0);
        ddhv8.loadImgsWithGlide(heads.subList(0, 8).toArray(),0);
        ddhv9.loadImgsWithGlide(heads.subList(0, 9).toArray(),0);

    }
}
