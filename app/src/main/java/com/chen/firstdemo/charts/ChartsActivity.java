package com.chen.firstdemo.charts;

import android.os.Bundle;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;
import com.chen.firstdemo.utils.DensityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChartsActivity extends BaseActivity {

    @BindView(R.id.barChartView)
    BarChartView barChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        ButterKnife.bind(this);

        final int ys = 10 ;
        String[] yVals = new String[ys];
        for (int i = 0; i < ys; i++) {
            yVals[i] = String.valueOf(i*5);
        }

        final int xs = 31 ;
        String[] xVals = new String[xs];
        String[] barVals = new String[xs];
        float[] ratios = new float[xs];
        for (int i = 0; i < xs; i++) {
//            xVals[i] = "8."+(i+1);
            xVals[i] = ""+(i+1);
            float random = (float) Math.random();
            ratios[i] = random;
            barVals[i] = String.valueOf((int)(random*(5*ys)));
        }

        barChartView.setAdapter(new BarChartView.Adapter() {
            @Override
            public int getLeftMargin() {
                return DensityUtil.dip2px(context,30);
            }

            @Override
            public int getRightMargin() {
                return DensityUtil.dip2px(context,30);
            }

            @Override
            public String getYTailText() {
                return "Y轴（单位）";
            }

            @Override
            public String getXTailText() {
                return "X轴";
            }

            @Override
            public String[] getYVals() {
                return yVals;
            }

            @Override
            public String[] getXVals() {
                return xVals;
            }

            @Override
            public float[] getBarRatios() {
                return ratios;
            }

            @Override
            public String[] getBarVals() {
                return barVals;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}
