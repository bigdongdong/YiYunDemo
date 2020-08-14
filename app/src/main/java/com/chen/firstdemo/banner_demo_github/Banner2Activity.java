package com.chen.firstdemo.banner_demo_github;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chen.firstdemo.R;
import com.cxd.banner.Banner;
import com.cxd.banner.OnSelectedListener;
import com.cxd.banner.PointsOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用github添加框架实现
 *
 * 修改项目内容，去Banner项目中改，这里不做备份
 * 以免版本混淆
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
                .width(15)
                .build();


        Banner banner = new Banner.Builder()
                .isDisplayPoints(true)
                .pointsOptions(options)
                .animDuration(1000)
                .context(this)
                .layoutStyle(Banner.LAYOUT_STYLE_IMAGEVIEW)
                .banners(list)
                .playStyle(Banner.PLAY_STYLE_JUST_GO)
                .stayDuration(300)
                .onSelectedListener(new OnSelectedListener<ImageView,Integer>() {
                    @Override
                    public void onSelectedListener(ImageView view, Integer integer, int position) {
                        Glide.with(view.getContext()).load(integer).into(view);
                        view.setImageResource(integer);
                        Log.i("aaa", "onSelectedListener: position:"+position);
                    }

                })
                .build();

        bannerFL.addView(banner);

        banner.start();
    }
}
