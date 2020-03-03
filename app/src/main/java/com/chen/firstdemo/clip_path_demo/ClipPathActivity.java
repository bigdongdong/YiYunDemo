package com.chen.firstdemo.clip_path_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.chen.firstdemo.R;
import com.chen.firstdemo.clip_path_demo.clip_path_view.RadiusImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClipPathActivity extends AppCompatActivity {

    @BindView(R.id.riv)
    RadiusImageView riv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_path);
        ButterKnife.bind(this);

        Glide.with(this)
//                .load("https://image.soudlink.net/web/qicaiyun_fang.webp")
                .load("http://yinyueka.moguupd.com/web/1579324731833.gif")
                .into(riv);
    }
}
