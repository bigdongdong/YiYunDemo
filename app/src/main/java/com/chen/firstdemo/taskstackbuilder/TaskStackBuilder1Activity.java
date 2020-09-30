package com.chen.firstdemo.taskstackbuilder;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskStackBuilder1Activity extends BaseActivity {

    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_stack_builder1);
        ButterKnife.bind(this);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去2留在3

                //        获得TaskStackBuilder对象
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

                Intent targetIntent = new Intent(context, TaskStackBuilder2Activity.class);
                Intent stayIntent = new Intent(context,TaskStackBuilder3Activity.class);
//                ComponentName componentName = new ComponentName("com.ahei.myviewpager", "com.ahei.myviewpager.MainActivity");
//                resultIntent.setComponent(componentName);
//        addNextIntent()方法会添加Intent到任务的顶端，将当前app的Activity与另一app的Activity添加到一个由stackBuilder创建的新的任务中
                stackBuilder.addNextIntent(stayIntent);
                stackBuilder.addNextIntent(targetIntent);
//        获取一个PendingIntent去启动stackBuilder所创建的新任务
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                        PendingIntent.FLAG_UPDATE_CURRENT);
//        发送PendingIntent
                try {
                    resultPendingIntent.send();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}