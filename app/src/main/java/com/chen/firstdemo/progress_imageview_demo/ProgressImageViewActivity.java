package com.chen.firstdemo.progress_imageview_demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.chen.firstdemo.R;
import com.chen.firstdemo.utils.DensityUtil;
import com.chen.firstdemo.utils.ScreenUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProgressImageViewActivity extends AppCompatActivity {

    private ProgressImageViewActivity context ;
    @BindView(R.id.progressIV)
    ProgressImageView progressIV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_image_view);
        ButterKnife.bind(this);

        context = this ;

        progressIV.setOnProgressListener(new ProgressImageView.OnProgressListenr() {
            @Override
            public void start() {
                progressIV.setImageResource(R.mipmap.progress_cover_2);
            }

        });

        getImage("https://image.soudlink.net/pic/9998E598BA3CB17CF72BB3B6DB0C9392.jpg");

    }
    private void getImage(String url) {
        int begin = url.lastIndexOf("/");
        String path = context.getExternalCacheDir()+url.substring(begin) ;

        /*Okhttp3 获取图片*/
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                FileOutputStream fos = null;
                byte[] bytes = new byte[2048];

                final long total = response.body().contentLength();
                int len = 0;
                long sum = 0;


                try{
                    is = response.body().byteStream();
                    Log.i("aaa", "path: "+path);
                    fos = new FileOutputStream(path);
                    while ((len = is.read(bytes) ) != -1){
                        fos.write(bytes,0,len);
                        sum = sum + len ;
                        final int progress = (int) ((sum * 1.0f / total * 1.0f) * 100);
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("aaa", "run: progress"+progress);
                                progressIV.setProgress(progress);
                            }
                        });
                    }

                    /*下载完成 填充*/
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap bitmap = BitmapFactory.decodeFile(path);
                            progressIV.setImageBitmap(bitmap);
                        }
                    });


                }catch (Exception e){

                }

            }
        });
    }

    @OnClick(R.id.progressIV)
    public void onViewClicked() {
        getImage("https://image.soudlink.net/pic/9998E598BA3CB17CF72BB3B6DB0C9392.jpg");
    }
}
