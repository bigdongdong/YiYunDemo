package com.chen.firstdemo.floating_window_demo.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chen.firstdemo.R;
import com.chen.firstdemo.floating_window_demo.ChatroomActivity;
import com.chen.firstdemo.floating_window_demo.floating_view.CicleImageView;
import com.chen.firstdemo.floating_window_demo.floating_view.MyFloatingView;

import java.util.List;

/**
 * TODO 当应用在后台时，如何进行唤醒，类似于微信登录界面那样，不会==
 */
public class FloatingWindowService extends Service {

    public static boolean isStarted = false; //service是单例的，所以可以用public static 变量

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;

    private int floatingViewWidth = 420 ;
    private int floatingViewHeight = (int) ((160f/426f)*floatingViewWidth);
    private int bigCicleImageWidth = (int) (floatingViewHeight*0.80);
    private int bigCicleTopMargin = (floatingViewHeight - bigCicleImageWidth) / 2;

    private int smallCicleImageWidth = (int) ((53f/426f)*floatingViewWidth);

    private MyFloatingView myFloatingView ;


    private Context mContext ;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this ;
        isStarted = true;
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();

        /**
         * 8.0及8.0以上 与 8.0以下所需要设置的不一样
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.width = floatingViewWidth;
        layoutParams.height = floatingViewHeight;
        layoutParams.x = 300;
        layoutParams.y = 300;


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showFloatingWindow();
        return super.onStartCommand(intent, flags, startId);
    }

    private void showFloatingWindow() {
        myFloatingView = assembleFloatingView(MyFloatingView.StyleEnum.NEW, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 先判断应用在前台还是后台
                 * 如果在后台就用ActivitManager唤醒到前台
                 *
                 * 判断前后台的方式：
                 *      可以在application中用ActivityLifecycleCallbacks来进行监听
                 */
                Intent intent = new Intent(
                        FloatingWindowService.this, ChatroomActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );

                if(isBackground(mContext)){
                    Log.i("aaa", "在后台: ");
                    /*TODO 如何换新后台进程，不会做了   ----2019-11-9*/

                }else{
                    Log.i("aaa", "在前台: ");
                    mContext.startActivity(intent);
                }

            }
        });
        windowManager.addView(myFloatingView, layoutParams);

        myFloatingView.setOnTouchListener(new FloatingOnTouchListener());
    }
    /*组装出一个floatingView*/
    private MyFloatingView assembleFloatingView(
            MyFloatingView.StyleEnum styleEnum,View.OnClickListener listener){
        MyFloatingView mFloatingView = new MyFloatingView(this,styleEnum);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(floatingViewWidth,floatingViewHeight);
        mFloatingView.setLayoutParams(params);

        /**
         * 向悬浮view中添加内容
         */

        //大圆
        CicleImageView cicleImageView = new CicleImageView(this);
        cicleImageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.main_tab_center_view));
        params = new RelativeLayout.LayoutParams(bigCicleImageWidth,bigCicleImageWidth);
        params.setMargins(bigCicleTopMargin,bigCicleTopMargin,0,0);
        cicleImageView.setLayoutParams(params);
        //以圆心位置自转，圆心坐标是相对于view自身，而不是相对于父控件
        Animation animation = new RotateAnimation(0f,360f,bigCicleImageWidth/2,bigCicleImageWidth/2);
        animation.setRepeatCount(-1);//设置无限循环
        animation.setDuration(4000);//设置动画持续时间
        animation.setInterpolator(new LinearInterpolator());//设置匀速
        animation.setRepeatMode(RotateAnimation.RESTART);//重复模式：从上次结束点继续
        cicleImageView.setOnClickListener(listener);
        cicleImageView.setAnimation(animation);
        mFloatingView.addView(cicleImageView);
        animation.start();

        //中间文字
        TextView textView = new TextView(this);
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_VERTICAL);//设置在父布局垂直居中
        params.setMargins(floatingViewHeight,0,0,10);
        textView.setLayoutParams(params);
        textView.setMaxWidth(floatingViewWidth - floatingViewHeight - smallCicleImageWidth);
        textView.setTextSize((40F/426F)/3*floatingViewWidth);
        textView.setMaxLines(1);
        textView.setEllipsize(TextUtils.TruncateAt.END); //设置超出的用...
        textView.setTextColor(Color.BLACK);
        mFloatingView.addView(textView);
        textView.setText("新5004");

        //小圆
        cicleImageView = new CicleImageView(this);
        cicleImageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.view_flow_window_close));
        params = new RelativeLayout.LayoutParams(smallCicleImageWidth,smallCicleImageWidth);
        params.addRule(RelativeLayout.ALIGN_PARENT_END);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.setMargins(0,0, (int) ((28f/426f)*floatingViewWidth),0);
        cicleImageView.setLayoutParams(params);
        cicleImageView.setClickable(true);
        cicleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("aaa", "onClick: ");
            }
        });
        mFloatingView.addView(cicleImageView);


        /**
         * 21以上才可以设置Elevation
         */
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mFloatingView.setElevation(50);
//        }

        return mFloatingView ;
    }

    private class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    layoutParams.x = layoutParams.x + movedX;
                    layoutParams.y = layoutParams.y + movedY;
                    windowManager.updateViewLayout(view, layoutParams);
                    break;
                default:
                    break;
            }
            return false;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /** 判断是否处于后台
     * @param context
     * @return true：处于后台, false：不处于后台
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if(appProcesses == null){
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}
