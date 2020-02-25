package com.chen.firstdemo.chen_jin_pop_demo;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.chen.firstdemo.R;
import com.chen.firstdemo.utils.StatusBarUtil;

public class ChenjinInputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setContentView(R.layout.activity_chenjin_input);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ChenjinPopActivity.fullScreen(this);
            StatusBarUtil.StatusBarLightMode(this);
//            this.getWindow().getDecorView().setBackgroundColor(Color.BLUE);
//            this.getWindow().getDecorView().setPadding(
//                    0,StatusBarUtil.getStatusBarHeight(this),0,0);
        }

    }
}
