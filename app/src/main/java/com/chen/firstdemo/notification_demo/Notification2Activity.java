package com.chen.firstdemo.notification_demo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;

import static android.app.Notification.VISIBILITY_SECRET;

public class Notification2Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendNotification();
            }
        },3000);
    }


    /*notification 相关*/
    private NotificationManager notificationManager ;
    private PendingIntent pendingIntent ;
    private Notification.Builder notificationBuilder ;
    private Notification notification ;
    private NotificationChannel channel ;
    private final String CHANNEL_ID = "闹钟提醒ID";
    private final int NOTIFICATION_ID = 111;

    private void sendNotification(){
        Vibrator vibrator = (Vibrator)this.getSystemService(this.VIBRATOR_SERVICE);
        vibrator.vibrate(3000);

        if(notificationManager == null){
            notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        if(pendingIntent == null){
            pendingIntent = PendingIntent.getActivity(context, 0, new Intent(this,Notification2Activity.class),
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }

        if(notificationBuilder == null){
            notificationBuilder = new Notification.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent)
                    .setWhen(System.currentTimeMillis());
        }

        /*Android 8.0 及以上需要channel*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && channel == null) {
            channel = new NotificationChannel(
                    CHANNEL_ID,
                    "闹钟提醒Name",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setSound(null, null);  //设置静音
            channel.enableLights(true);//闪光灯
            channel.setLightColor(Color.WHITE);//闪关灯的灯光颜色
            channel.setLockscreenVisibility(VISIBILITY_SECRET);//锁屏显示通知
            channel.enableVibration(true);//允许震动
            channel.setVibrationPattern(new long[]{1000, 500, 2000}); //振动模式
            notificationManager.createNotificationChannel(channel);//创建channel
            notificationBuilder.setChannelId(CHANNEL_ID);
        }

        /*RemoteViews 由于机制原因 不可复用 会卡死*/
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.notification_alarm_clock);
        /*notification显示数据*/
//        remoteViews.setImageViewResource(R.id.logoIV,R.mipmap.ic_launcher);
//        remoteViews.setProgressBar(R.id.processBar,100,process,false);
//        remoteViews.setTextViewText(R.id.processTV,"下载"+process+"%");
        /*创建一个新的notification*/
        notificationBuilder.setContent(remoteViews);
        notification = notificationBuilder.build();
        notificationManager.notify(NOTIFICATION_ID,notification);
    }

//    /*下载完成-关闭通知*/
//    private void clearNotification(){
//        if(notificationManager != null){
//            notificationManager.cancel(NOTIFICATION_ID);
//        }
//    }

}
