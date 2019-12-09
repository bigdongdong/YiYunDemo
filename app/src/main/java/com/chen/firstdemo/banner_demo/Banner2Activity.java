package com.chen.firstdemo.banner_demo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chen.firstdemo.R;
import com.cxd.moudle.Banner;
import com.cxd.moudle.OnSelectedListener;
import com.cxd.moudle.PointsOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用github添加框架实现
 */
public class Banner2Activity extends AppCompatActivity {

    private FrameLayout bannerFL ;
    List<Integer> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner2);

        bannerFL = findViewById(R.id.bannerFL);

        list.add(R.mipmap.beauty);
        list.add(R.mipmap.beauty_1);
        list.add(R.mipmap.beauty_2);
        list.add(R.mipmap.beauty_3);
        list.add(R.mipmap.beauty_4);
        list.add(R.mipmap.beauty_5);
        list.add(R.mipmap.beauty_6);

        PointsOptions options = new PointsOptions.Builder()
                .space(5)
                .unSelectedColor(Color.BLACK)
                .selectedColor(Color.WHITE)
                .count(list.size())
                .marginBottom(10)
                .width(5)
                .build();


        Banner banner = new Banner.Builder()
                .isDisplayPoints(true)
                .pointsOptions(options)
                .animDuration(1000)
                .context(this)
                .layoutStyle(Banner.LAYOUT_STYLE_IMAGEVIEW)
                .banners(list)
                .playStyle(Banner.PLAY_STYLE_JUST_GO)
                .stayDuration(1000)
                .onSelectedListener(new OnSelectedListener<ImageView,Integer>() {
                    @Override
                    public void onSelectedListener(ImageView view, Integer integer, int position) {
                        Glide.with(view.getContext()).load(integer).into(view);
                        view.setImageResource(integer);
                    }

                })
                .build();

        bannerFL.addView(banner);
    }
}
