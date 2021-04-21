package com.chen.firstdemo.diy_media_player;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.AndroidMediaPlayerFactory;
import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.player.VideoViewConfig;
import com.dueeeke.videoplayer.player.VideoViewManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoByDKPlayerActivity extends BaseActivity {

    @BindView(R.id.player)
    VideoView player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_by_dkplayer);
        ButterKnife.bind(this);

        player.setUrl(AssetUtil.copyAssetFileToLocal(context,"media_test_2.mp4").getPath()); //设置视频地址
        StandardVideoController controller = new StandardVideoController(this);
        controller.addDefaultControlComponent("标题", false);
        player.setVideoController(controller); //设置控制器



    }

    @Override
    protected void onStart() {
        super.onStart();
        player.start(); //开始播放，不调用则不自动播放
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }


    @Override
    public void onBackPressed() {
        if (!player.onBackPressed()) {
            super.onBackPressed();
        }
    }
}