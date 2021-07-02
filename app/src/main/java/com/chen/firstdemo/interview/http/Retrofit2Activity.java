package com.chen.firstdemo.interview.http;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit2Activity extends BaseActivity {
    private final String url = "https://api.apiopen.top/getJoke?page=1&count=2&type=video";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http3);

//        /*创建Retrofit对象*/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.apiopen.top/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//
//        RetrofitService service = retrofit.create(RetrofitService.class);
//        Call<Bean> call =  service.getJoke(1,2,"video");
//
//        /*同步调用*/
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    Bean bean = call.execute().body();
//                    Log.i(TAG, "onCreate: "+bean.toString());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();


        /*异步调用*/
//        call.enqueue(new Callback<Bean>() {
//            @Override
//            public void onResponse(Call<Bean> call, Response<Bean> response) {
//                Bean bean = response.body();
//                Log.i(TAG, "onCreate: "+bean.toString());
//            }
//
//            @Override
//            public void onFailure(Call<Bean> call, Throwable t) {
//            }
//        });

        // 具体使用
        RetrofitService service = retrofit.create(RetrofitService.class);
        // @FormUrlEncoded
        Call<ResponseBody> call1 = service.testFormUrlEncoded1("Carson", 24);

        //  @Multipart
        MediaType textType = MediaType.parse("text/plain");
        RequestBody name = RequestBody.create(textType, "Carson");
        RequestBody age = RequestBody.create(textType, "24");
        RequestBody file = RequestBody.create(MediaType.parse("application/octet-stream"), "这里是模拟文件的内容");

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "test.txt", file);
        Call<ResponseBody> call3 = service.testFileUpload1(name, age, filePart);


        FormBody.Builder builder = new FormBody.Builder();
        builder.add("key","value");



        // @FieldMap 实现的效果与上面@Field相同，但要传入Map
        Map<String, Object> map = new HashMap<>();
        map.put("username", "Carson");
        map.put("age", 24);
        Call<ResponseBody> call2 = service.testFormUrlEncoded2(map);
    }
}
