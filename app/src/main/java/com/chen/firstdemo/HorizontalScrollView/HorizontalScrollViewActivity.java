package com.chen.firstdemo.HorizontalScrollView;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chen.firstdemo.R;

public class HorizontalScrollViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_scroll_view);
        LinearLayout layout = findViewById(R.id.layout);

        for(int i = 0 ; i<5 ; i++){
            android.util.Log.i("aaa", "getQuickRelayWords: "+i);
            layout.addView(generateQuickRelayView("这是快捷语："+i));
        }
    }


    /**
     * 生成一个快捷回复语View
     * @param s
     * @return
     */
    private View generateQuickRelayView(String s){
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(30,0,0,0);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.parseColor("#333333"));
        textView.setText(s);
        textView.setTextSize(12);
        textView.setMaxLines(1);
        textView.setPadding(30,0,30,0);
        textView.setClickable(true);

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.WHITE);
        gd.setCornerRadius((float) (17.5*3));
        textView.setBackground(gd);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO  chenxiaodong 快捷语点击事件，是放到输入框还是直接发送
            }
        });
        return textView ;
    }
}
