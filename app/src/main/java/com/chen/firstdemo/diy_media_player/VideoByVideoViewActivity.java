package com.chen.firstdemo.diy_media_player;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoByVideoViewActivity extends BaseActivity {

    @BindView(R.id.vv)
    VideoView vv;
    @BindView(R.id.playBtn)
    Button playBtn;
    @BindView(R.id.pauseBtn)
    Button pauseBtn;
    @BindView(R.id.endBtn)
    Button endBtn;
    @BindView(R.id.rePlayBtn)
    Button rePlayBtn;
    @BindView(R.id.seekBar)
    SeekBar seekBar;

    private Timer timer ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_by_video_view);
        ButterKnife.bind(this);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                vv.seekTo(seekBar.getProgress());
            }
        });

        initVideo();

    }

    private void initVideo() {
        isEnd = false;

        vv.setVideoURI(AssetUtil.copyAssetFileToLocal(this, "media_test.mp4"));
//        vv.setMediaController(new MediaController(this));
        playBtn.setEnabled(false);
        pauseBtn.setEnabled(false);
        endBtn.setEnabled(false);
        rePlayBtn.setEnabled(false);
        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                playBtn.setEnabled(true);
                pauseBtn.setEnabled(true);
                endBtn.setEnabled(true);
                rePlayBtn.setEnabled(true);

                seekBar.setMax(mp.getDuration());
            }
        });
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                if(vv.isPlaying()){
                    final int posi = vv.getCurrentPosition();
                    seekBar.setProgress(posi);
                }
            }
        };
        timer = new Timer();
        timer.schedule(tt,0,100);
    }

    private boolean isEnd;

    @OnClick({R.id.playBtn, R.id.pauseBtn, R.id.endBtn, R.id.rePlayBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.playBtn:
                if (isEnd) {
                    initVideo();
                }
                vv.start();
                break;
            case R.id.pauseBtn:
                vv.pause();
                break;
            case R.id.endBtn:
                isEnd = true;
                vv.stopPlayback();
                if(timer != null){
                    timer.cancel();
                    seekBar.setProgress(0);
                }
                break;
            case R.id.rePlayBtn:
                vv.resume();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer != null){
            timer.cancel();
        }
    }
}