package com.chen.firstdemo.rank_imageview_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.chen.firstdemo.R;

public class RankImageViewActivity extends AppCompatActivity {

    private LinearLayout mainLayout ;
    private RankImageView rankIV ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_image_view);


        mainLayout = findViewById(R.id.mainLayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(400,400);

        rankIV = new RankImageView(this,1);
        rankIV.setLayoutParams(params);
        mainLayout.addView(rankIV);
        rankIV = new RankImageView(this,2);
        rankIV.setLayoutParams(params);
        mainLayout.addView(rankIV);
        rankIV = new RankImageView(this,3);
        rankIV.setLayoutParams(params);
        mainLayout.addView(rankIV);
    }
}
