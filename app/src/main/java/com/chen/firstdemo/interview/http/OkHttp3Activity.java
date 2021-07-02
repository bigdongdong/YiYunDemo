package com.chen.firstdemo.interview.http;

import android.os.Bundle;
import android.util.Log;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttp3Activity extends BaseActivity {

    private final String url = "https://api.apiopen.top/getJoke?page=1&count=2&type=video";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http3);


        /*同步请求基本写法*/
        final OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url(url)
                .build();

        /*必须在子线程中请求*/
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                Response response = null;
//                try {
//                    response = client.newCall(request).execute();
//                    Log.i(TAG, "onCreate: "+response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();



        /*异步请求写法*/
//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url(url)
//                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                Log.i(TAG, "onCreate: "+response.body().string());
            }
        });

    }
}
