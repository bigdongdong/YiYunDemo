package com.chen.firstdemo.shape_demo;

import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.ImageView;

import com.chen.firstdemo.R;
import com.chen.firstdemo.shape_demo.radar.RadarOptions;
import com.chen.firstdemo.shape_demo.radar.RadarView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShapeActivity extends AppCompatActivity {

    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.iv2)
    ImageView iv2;
    @BindView(R.id.iv3)
    ImageView iv3;
    @BindView(R.id.iv4)
    RadarView iv4;
    @BindView(R.id.iv5)
    ImageView iv5;
    @BindView(R.id.iv6)
    ImageView iv6;
    @BindView(R.id.iv7)
    ImageView iv7;
    @BindView(R.id.iv8)
    ImageView iv8;
    @BindView(R.id.iv9)
    ImageView iv9;
    @BindView(R.id.iv10)
    ImageView iv10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape);
        ButterKnife.bind(this);

        iv1.setBackground(new ShapeDrawable(new HornShape(Gravity.RIGHT)));
        iv2.setBackground(new ShapeDrawable(new HornShape(Gravity.LEFT)));
        iv3.setBackground(new ShapeDrawable(new WindowFlowerShape()));

        RadarOptions options = new RadarOptions.Builder()
                .count(7)
                .level(4)
                .maxVal(100)
                .vals(new int[]{51,53,49,48,56,33,63})
                .build();
        iv4.showRadar(options);
    }
}
