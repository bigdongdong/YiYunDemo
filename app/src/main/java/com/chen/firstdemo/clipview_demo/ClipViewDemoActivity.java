package com.chen.firstdemo.clipview_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.chen.firstdemo.R;
import com.cxd.clipview.ClipImageView;
import com.cxd.photor.Photor;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClipViewDemoActivity extends AppCompatActivity {

    @BindView(R.id.civ)
    ClipImageView civ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_view_demo);
        ButterKnife.bind(this);


        civ = findViewById(R.id.civ);
        civ.setCropWindowSize(400,400);
        Glide.with(this).load(R.mipmap.beauty_1).into(civ);

        civ.getCropBitmap();
        civ.getCropBitmapWithZip();

//        Photor.getInstance().context(this).requestImgsFromAlbum(10);
//        Photor.getInstance().context(this).requestImgFromCamera();
        Photor.getInstance().context(this).requestImgsFromDirectory(10);

    }
}
