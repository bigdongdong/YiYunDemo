package com.chen.firstdemo.charts;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;
import com.chen.firstdemo.utils.DensityUtil;
import com.chen.firstdemo.utils.ScreenUtil;
import com.cxd.cakechartview.CakeAdapter;
import com.cxd.cakechartview.CakeChartView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChartsActivity extends BaseActivity {

    @BindView(R.id.barChartView)
    BarChartView barChartView;
    @BindView(R.id.uiButton)
    Button uiButton;
    @BindView(R.id.ccv)
    CakeChartView ccv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        ButterKnife.bind(this);

        final int ys = 10;
        String[] yVals = new String[ys];
        for (int i = 0; i < ys; i++) {
            yVals[i] = String.valueOf(i * 5);
        }

        final int xs = 10;
        String[] xVals = new String[xs];
        String[] barVals = new String[xs];
        float[] ratios = new float[xs];
        for (int i = 0; i < xs; i++) {
            xVals[i] = "" + (i + 1);
            float random = (float) Math.random();
            ratios[i] = random;
            barVals[i] = String.valueOf((int) (random * (5 * ys)));
        }

        barChartView.setAdapter(new BarChartView.Adapter() {
            @Override
            public int getLeftMargin() {
                return DensityUtil.dip2px(context, 30);
            }

            @Override
            public int getRightMargin() {
                return DensityUtil.dip2px(context, 30);
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

        final int margin = DensityUtil.dip2px(context, 0);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ccv.getLayoutParams();
        lp.width = ScreenUtil.getScreenWidth(context) - margin * 2;
        lp.height = ScreenUtil.getScreenWidth(context) - margin * 2;
        lp.setMargins(margin, margin, margin, margin);


        uiCake();
    }

    @OnClick(R.id.uiButton)
    public void onViewClicked() {
        uiCake();
    }

    private void uiCake() {
        final String[] texts = new String[]{"语文", "数学", "英语", "体育", "美术", "思修", "选修"};
        final float[] ratios = new float[]{0.1f, 0.2f, 0.1f, 0.1f, 0.1f, 0.25f, 0.15f};
        final int[] colors = new int[]{
                0xffffffd2,
                0xffc6fce5,
                0xffaa96da,
                0xfffcbad3,
                0xffdbe2ef,
                0xffe0f9b5,
                0xffff7e67,
                0xffa56cc1
        };
        List<Bean> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Bean bean = new Bean();
            bean.setColor(colors[i]);
            bean.setText(texts[i]);
            bean.setRatio(ratios[i]);
            list.add(bean);
        }

        ccv.setAdapter(new CakeAdapter<Bean>(list) {
            @Override
            public boolean isStatic() {
                return false;
            }

            @Override
            public BaseConfig base() {
                return new BaseConfig(
                        Color.parseColor("#11111111"),
//                        Color.TRANSPARENT,
                        10,
                        Color.BLACK,
                        0.5f,
                        0.65f
                );
            }

            @Override
            public ItemConfig item(Bean bean, int position) {
                return new ItemConfig(
                        bean.getColor(),
                        bean.getText(),
                        bean.getRatio(),
                        position == 5 || position == 1
                );
            }
        });
    }

    class Bean {
        int color;
        String text;
        float ratio;

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public float getRatio() {
            return ratio;
        }

        public void setRatio(float ratio) {
            this.ratio = ratio;
        }
    }


    /*获取随机颜色：#F7FF77*/
    public String getRandColorCode() {
        String r, g, b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;

        return "#99" + r + g + b;
    }
}
