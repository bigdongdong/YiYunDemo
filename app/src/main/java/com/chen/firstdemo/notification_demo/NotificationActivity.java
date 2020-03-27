package com.chen.firstdemo.notification_demo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RemoteViews;


import com.chen.firstdemo.R;

import static android.app.Notification.VISIBILITY_SECRET;


public class NotificationActivity extends AppCompatActivity {

    LinearLayout linearLayout ;
    LinearLayout.LayoutParams params ;

    private final String TAG = "aaa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);

        Button button = new Button(this);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,10,10,10);
        button.setLayoutParams(params);
        button.setAllCaps(false);
        button.setText("发送channel1");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*发送notification通知*/
                sendNotification((int) Math.random(),"channel1");
            }
        });
        linearLayout.addView(button);

        button = new Button(this);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,10,10,10);
        button.setLayoutParams(params);
        button.setAllCaps(false);
        button.setText("发送channel2");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*发送notification通知*/
                sendNotification((int) Math.random(),"channel2");
            }
        });
        linearLayout.addView(button);


        button = new Button(this);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,10,10,10);
        button.setLayoutParams(params);
        button.setAllCaps(false);
        button.setText("发送进度条");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*发送notification通知*/
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 101; i += 5) {
                            senProcessNotification(i);
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
        linearLayout.addView(button);
        this.setContentView(linearLayout);
    }

    private void sendNotification(int id , String channelId){
        /**
         * 1、首先需要一个NotificationManager来进行管理，
         * 可以调用Context的getSystemService方法获取，
         * 这里传入一个Context.NOTIFICAATION_SERVICE即可。
         */
        NotificationManager notificationManager =
                (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);


        /**
         * 需要使用一个Builder构造器来创建Notification对象，
         * 由于API不同会造成不同版本的通知出现不稳定的问题，
         * 所以这里使用NotificationCompat类来兼容各个版本。
         *
         * intent 和 PendingIntent 是必须的
         * intent 是 PendingIntent的组成部件
         */
        Notification notification ;

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,
                new Intent(this,NotificationDisplayActivity.class),
                PendingIntent.FLAG_ONE_SHOT);


//        /**
//         * 低于API Level 11版本
//         * setLatestEventInfo()函数是唯一的实现方法
//         *
//         * 在API Level 11中，该函数已经被替代，不推荐使用了。
//         */
//        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB){
//            Log.i(TAG, "API Level < 11 ");
//
////             notification.setLatestEventInfo(this, title, message, pendingIntent);
////                    manager.notify(id, notification);
//        }

//        /**
//         * 高于等于API Level 11 但是低于API Level 16
//         * 可使用Notification.Builder来构造函数。但要使用getNotification()来使notification实现。
//         */
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
//                &&Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
//
//            Log.i(TAG, "API Level >= 11 && API Level < 16");
//
//            Notification.Builder builder = new Notification.Builder(this)
//                    .setAutoCancel(true)
//                    .setContentTitle("title")
//                    .setContentText("describe")
//                    .setContentIntent(pendingIntent)
//                    .setSmallIcon(R.mipmap.main_tab_center_view)
//                    .setWhen(System.currentTimeMillis())
//                    .setOngoing(true);
//            notification = builder.getNotification();
//            notificationManager.notify(id,notification);
//        }

        /**
         * 高于等于API Level 16 但是低于API Level 26
         * 就可以用Builder和build()函数来配套的方便使用notification了。
         *
         * API 16开始，对于notification的创建没有变化，只是提供了builder的简便方式
         */
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O){

            Log.i(TAG, "API Level >= 16 && API Level < 26");
            notification = new Notification.Builder(this)
                    .setAutoCancel(true)
                    .setContentTitle("title")
                    .setContentText("describe")
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.main_tab_center_view)
                    .setWhen(System.currentTimeMillis())
                    .build();
            notificationManager.notify(id,notification);
        }

        /**
         * 高于等于API Level 26
         * 从Android 8.0开始,使用系统通知Notification需要再设置下Channel
         */

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Log.i(TAG, "API Level > 26");

            //第一个参数：channel_id
            //第二个参数：channel_name
            //第三个参数：设置通知重要性级别
            //注意：该级别必须要在 NotificationChannel 的构造函数中指定，总共要五个级别；
            //范围是从 NotificationManager.IMPORTANCE_NONE(0) ~ NotificationManager.IMPORTANCE_HIGH(4)
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);//闪光灯
            channel.setLockscreenVisibility(VISIBILITY_SECRET);//锁屏显示通知
            channel.setLightColor(Color.RED);//闪关灯的灯光颜色
            channel.enableVibration(true);//是否允许震动
            channel.setVibrationPattern(new long[]{100, 100, 200});//设置震动模式
            channel.setBypassDnd(true);//设置可绕过 请勿打扰模式

            notificationManager.createNotificationChannel(channel);//创建channel
//            notificationManager.cancelAll();//清空所有消息

            Notification.Builder builder = new Notification.Builder(this, channelId)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.main_tab_center_view))
                .setSmallIcon(R.mipmap.main_tab_center_view)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setAutoCancel(true) //自动消失
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setContentTitle(channelId)
                .setContentText("ContentText:describe")
                .setSubText("SubText");
            notification = builder.build();

            notificationManager.notify(id,notification);

        }

    }


    private final int notification_id = 111 ;
    private void senProcessNotification(int process){
        /**
         * 1、首先需要一个NotificationManager来进行管理，
         */
        NotificationManager notificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);


        /**
         * 一下是android 8.0需要的
         */
        NotificationChannel channel = null;
        String channelId = "音淘版本更新下载CHANNEL";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(
                    channelId,
                    "CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);//闪光灯
            channel.setLockscreenVisibility(VISIBILITY_SECRET);//锁屏显示通知
            channel.setLightColor(Color.BLUE);//闪关灯的灯光颜色
            channel.enableVibration(false);//是否允许震动
//            channel.setVibrationPattern(new long[]{100, 100, 200});//设置震动模式
//            channel.setBypassDnd(true);//设置可绕过 请勿打扰模式
            notificationManager.createNotificationChannel(channel);//创建channel
        }

        if(process == 100){
            notificationManager.cancel(notification_id);
            return;
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(),
                PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.notification_process);
        remoteViews.setProgressBar(R.id.processBar,100,process,false);
        remoteViews.setTextViewText(R.id.processTV,"下载..."+process+"%");

        Notification.Builder builder = new Notification.Builder(this)
                .setContent(remoteViews)
                .setSmallIcon(R.mipmap.main_tab_center_view)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(channelId);
        }

        Notification notification = builder.build();


        notificationManager.notify(notification_id,notification);
    }
}
