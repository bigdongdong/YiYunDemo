package com.chen.firstdemo;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.chen.firstdemo.bitmap_mix_demo.BitmapMixActivity;
import com.chen.firstdemo.bottom_tab_demo.BottomTabActivity;
import com.chen.firstdemo.dialog_demo.DialogActivity;
import com.chen.firstdemo.diy_view_demo.DIYViewActivity;
import com.chen.firstdemo.empty_recyclerview.EmptyAdapterActivity;
import com.chen.firstdemo.floating_window_demo.FloatingWindowActivity;
import com.chen.firstdemo.greendao_demo.GreendaoActivity;
import com.chen.firstdemo.lazy_fragment.LazyLoadActivity;
import com.chen.firstdemo.notification_demo.NotificationActivity;
import com.chen.firstdemo.pager_demo.PagerActivity;
import com.chen.firstdemo.rank_imageview_demo.RankImageViewActivity;
import com.chen.firstdemo.utils.ScreenUtil;
import com.chen.firstdemo.viewstub_demo.ViewStubActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout ;
    private Class[] classes ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.linearLayout);
        linearLayout.setMinimumHeight(ScreenUtil.getScreenHeight(this));

        classes = new Class[]{BitmapMixActivity.class,
                BottomTabActivity.class,
                DIYViewActivity.class,
                FloatingWindowActivity.class,
                GreendaoActivity.class,
                NotificationActivity.class,
                PagerActivity.class,
                RankImageViewActivity.class,
                LazyLoadActivity.class,
                DialogActivity.class,
                ViewStubActivity.class,
                EmptyAdapterActivity.class
        } ;

        for(Class c : classes){
            generateView(c.getSimpleName(),c);
        }
//        /**
//         * View 是 Android 中的视图的呈现方式，但是 View 不能单独存在，它必须附着在 Window 这个抽象的概念上面
//         * 有视图的地方就有 Window，因此 Activity、Dialog、Toast 等视图都对应着一个 Window
//         * 这也是面试中常问到的一个知识点：一个应用中有多少个 Window？下面分别分析 Activity、Dialog以及 Toast 的 Window 创建过程。
//         *
//         * View 是 Window 存在的实体
//         * 实际情况中，我们无法直接访问Window
//         * 对Window的访问必须通过WindowManager
//         *
//         */
//
//        /**
//         * WindowManagerService 是个位于 Framework 层的窗口管理服务
//         * WindowManager 和 WindowManagerService 的关系是C-S
//         * WindowManagerService运行在一个单独的进程中
//         * 所以二者通过IPC通讯
//         */
//        Button floatingButton = new Button(this);
//        floatingButton.setText("button");
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                0, 0,
//                PixelFormat.TRANSPARENT
//        );
//        // flag 设置 Window 属性
////        layoutParams.flags= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
//        // type 设置 Window 类别（层级）
//            layoutParams.type = WindowManager.LayoutParams.TYPE_DRAWN_APPLICATION;
//        layoutParams.gravity = Gravity.CENTER;
//
//        // 将设置好的View 添加到Window中，通过WindowManager添加
//        WindowManager windowManager = getWindowManager();
//        windowManager.addView(floatingButton, layoutParams);

    }

    /**
     * 根据类名 逐一生成button
     * @param s
     * @param c
     */
    private void generateView(String s,final Class c){
        Button button = new Button(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(100,0,100,0);
        button.setLayoutParams(params);
        button.setText(s+".class");
        button.setAllCaps(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,c);
                MainActivity.this.startActivity(intent);
            }
        });
        linearLayout.addView(button);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("aaa", "onDestroy: ");
    }
}
