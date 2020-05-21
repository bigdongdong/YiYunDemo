package com.chen.firstdemo.glide_webp_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chen.firstdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GlideWebpActivity extends AppCompatActivity {

    @BindView(R.id.iv)
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_webp);
        ButterKnife.bind(this);


        Glide.with(this)
                .asGif()
                .load("https://image.soudlink.com/pictrue/202004101550.webp")
                .into(iv);
    }
}
