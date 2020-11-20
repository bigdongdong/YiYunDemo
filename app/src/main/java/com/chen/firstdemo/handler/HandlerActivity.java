package com.chen.firstdemo.handler;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.support.v4.app.ActivityManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chen.firstdemo.R;

import java.util.concurrent.locks.ReentrantLock;

public class HandlerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

//        ReentrantLock

//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//
//                Looper.prepare();//Looper初始化
//                //Handler初始化 需要注意, Handler初始化传入Looper对象是子线程中缓存的Looper对象
////                Handler mHandler = new Handler(Looper.myLooper());
//                Handler mHandler = Handler.createAsync(Looper.myLooper());
//                Looper.loop();//死循环
//
//            }
//        }.start();
//
////        Handler handler2 = Handler.createAsync(getMainLooper(), new Handler.Callback() {
////            @Override
////            public boolean handleMessage(Message msg) {
////                return false;
////            }
////        });
////
////        Handler handler3 = Handler.createAsync(Looper.myLooper());
////        Handler handler3 = Handler.createAsync(Looper.getMainLooper());
//
//        Handler handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//            }
//        };
//
//        Looper
//
//        Message message = Message.obtain();
//        handler.sendMessage(message);

    }
}