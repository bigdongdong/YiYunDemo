package com.chen.firstdemo.bottom_tab_demo_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chen.firstdemo.R;
import com.chen.firstdemo.bottom_tab_demo.BottomTabView;

public class BottomTab2Activity extends AppCompatActivity {

    private LinearLayout bottomTabLL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_tab2);

        bottomTabLL = findViewById(R.id.bottomTabLL);
        bottomTabLL.addView(new BottomNavigationView(this));
    }
}
