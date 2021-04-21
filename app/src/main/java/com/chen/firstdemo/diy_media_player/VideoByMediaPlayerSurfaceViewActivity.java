package com.chen.firstdemo.diy_media_player;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * MediaPlayer+SurfaceView
 */
public class VideoByMediaPlayerSurfaceViewActivity extends BaseActivity {

    @BindView(R.id.sfv)
    SurfaceView sfv;
    @BindView(R.id.playBtn)
    Button playBtn;
    @BindView(R.id.pauseBtn)
    Button pauseBtn;
    @BindView(R.id.endBtn)
    Button endBtn;
    @BindView(R.id.rePlayBtn)
    Button rePlayBtn;
    @BindView(R.id.sb)
    SeekBar seekBar;

    private MediaPlayer mMediaPlayer;
    private SurfaceHolder mSurfaceHolder;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diy_media_player_demo);
        ButterKnife.bind(this);


        mSurfaceHolder = sfv.getHolder();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //当进度条停止拖动的时候，把媒体播放器的进度跳转到进度条对应的进度
                if(mMediaPlayer != null){
                    mMediaPlayer.seekTo(seekBar.getProgress());
                }
            }
        });

        playBtn.setEnabled(false);
        seekBar.setEnabled(false);
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.i(TAG, "surfaceCreated: ");

                //为了避免图像控件还没有创建成功，用户就开始播放视频，造成程序异常，所以在创建成功后才使播放按钮可点
                playBtn.setEnabled(true);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.i(TAG, "surfaceChanged: ");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.i(TAG, "surfaceDestroyed: ");

                //当程序没有退出，但不在前台运行时，因为surfaceview很耗费空间，所以会自动销毁，
                // 这样就会出现当你再次点击进程序的时候点击播放按钮，声音继续播放，却没有图像
                //为了避免这种不友好的问题，简单的解决方式就是只要surfaceview销毁，我就把媒体播放器等
                //都销毁掉，这样每次进来都会重新播放，当然更好的做法是在这里再记录一下当前的播放位置，
                //每次点击进来的时候把位置赋给媒体播放器，很简单加个全局变量就行了。
                if(mMediaPlayer != null){
//                    mMediaPlayer.getCurrentPosition();
                    stop();
                }

            }
        });

    }

    protected void initMediaPlayerIfNotExists(){
        if(mMediaPlayer == null){
            /*初始化媒体播放器*/
            try {
                //第一种
//                mMediaPlayer = new MediaPlayer();
//                AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.media_test);
//                mMediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());

                //第二种
//                mMediaPlayer = MediaPlayer.create(this,R.raw.media_test);

                //第三种
                mMediaPlayer = new MediaPlayer();
                mMediaPlayer.setDataSource(context,AssetUtil.copyAssetFileToLocal(context,"media_test.mp4"));

                mMediaPlayer.setDisplay(mSurfaceHolder);//将影像播放控件与媒体播放控件关联起来
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        //视频播放完成后，释放资源
                        playBtn.setEnabled(true);
                        stop();

                    }
                });
                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        //媒体播放器就绪后，设置进度条总长度
                        //开启计时器不断更新进度条，播放视频
                        seekBar.setMax(mMediaPlayer.getDuration());
                        TimerTask tt = new TimerTask() {
                            @Override
                            public void run() {
                                if(mMediaPlayer != null){
                                    seekBar.setProgress(mMediaPlayer.getCurrentPosition());
                                }
                            }
                        };
                        timer = new Timer();
                        timer.schedule(tt,0,500); //立即启动
                    }
                });
                mMediaPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void play(){
        initMediaPlayerIfNotExists();
        seekBar.setEnabled(true);
        pauseBtn.setEnabled(true);
        playBtn.setEnabled(false);
        mMediaPlayer.start();
    }

    private void pause(){
        if(mMediaPlayer.isPlaying()){
            mMediaPlayer.pause();
            playBtn.setEnabled(true);
            pauseBtn.setEnabled(false);
        }
    }

    private void replay(){
        stop();
        play();
    }

    private void stop(){
        seekBar.setEnabled(false);
        if (timer != null) {
            timer.cancel();
        }

        if(mMediaPlayer != null){
            seekBar.setProgress(0);
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null ;
            playBtn.setEnabled(true);

            //拿到canvas 画成黑的
//            Canvas canvas = mSurfaceHolder.lockCanvas();
//            canvas.drawColor(Color.BLACK);
//            mSurfaceHolder.getSurface().unlockCanvasAndPost(canvas);
        }

    }

    @OnClick({R.id.playBtn, R.id.pauseBtn, R.id.endBtn, R.id.rePlayBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.playBtn:
                play();
                break;
            case R.id.pauseBtn:
                pause();
                break;
            case R.id.endBtn:
                stop();
                break;
            case R.id.rePlayBtn:
                replay();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop();
    }
}