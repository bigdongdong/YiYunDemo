package com.chen.firstdemo.douyin_popwindow_demo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;
import com.chen.firstdemo.base.DouYinShadowPopWindow;
import com.chen.firstdemo.utils.DensityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DouyinPopActivity extends BaseActivity {

    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_douyin_pop);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.button)
    public void onViewClicked() {
//        new DouyinPop(context).show(R.layout.activity_douyin_pop);
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        dialog.setContentView(R.layout.pop_douyin);
        dialog.show();
    }


    class DouyinPop extends DouYinShadowPopWindow {

        public DouyinPop(Activity context) {
            super(context);
            this.setWidth(-1);
            this.setHeight(DensityUtil.dip2px(context, 600));
        }

        @Override
        protected void onCreateView(View view) {
            GradientDrawable gd = new GradientDrawable();
            gd.setColor(Color.WHITE);
            final float radius = DensityUtil.dip2px(context, 30);
            gd.setCornerRadii(new float[]{radius, radius, radius, radius, 0, 0, 0, 0});
            view.setBackground(gd);


        }


        @Override
        protected Object getLayoutIdOrView() {
            return R.layout.pop_douyin;
        }

        public void show(int layout) {
            View v = LayoutInflater.from(context).inflate(layout, null);
            showAtLocation(v, Gravity.BOTTOM, 0, 0);
        }
    }

    class DouyinDialog extends BottomSheetDialog {

        public DouyinDialog(@NonNull Context context) {
            super(context);


        }
    }
}
