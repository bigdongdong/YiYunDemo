package com.chen.firstdemo.floating_window_demo;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.firstdemo.R;
import com.chen.firstdemo.floating_window_demo.service.FloatingWindowService;

public class FloatingWindowActivity extends AppCompatActivity {

    private RelativeLayout.LayoutParams params ;
    private LinearLayout linearLayout ;
    private Context mContext ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this ;

        linearLayout = new LinearLayout(this);
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(Color.parseColor("#30111111"));
        linearLayout.setGravity(Gravity.CENTER);

        Button button = new Button(this);
        button.setText("点击显示悬浮窗！");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FloatingWindowService.isStarted){
                    return;
                }

                /**
                 * 低版本的android中没有.canDrawOverlays方法
                 * 23以后需要动态授权
                 */
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.canDrawOverlays(mContext)) {
                        Toast.makeText(mContext, "当前无权限，请授权", Toast.LENGTH_SHORT).show();
                        startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:" + getPackageName())), 0);
                    } else {
                        startService(new Intent(mContext, FloatingWindowService.class));
                    }
                }else{
                    startService(new Intent(mContext, FloatingWindowService.class));
                }
            }
        });

        linearLayout.addView(button);

        this.setContentView(linearLayout);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0){
            if(Settings.canDrawOverlays(mContext)){
                startService(new Intent(mContext,FloatingWindowService.class));
            }else{
                Toast.makeText(mContext,"授权失败",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
