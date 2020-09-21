package com.chen.firstdemo.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogActivity extends BaseActivity {

    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new MyDialog(context).show();
            }
        },3000);

    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        new MyDialog(context).show();
    }


    class MyDialog extends BaseDialog{
        public MyDialog(@NonNull Activity context) {
            super(context);
        }

        @Override
        protected void onConfig(Config c) {
            c.width = 300 ;
            c.height = 400 ;
            c.retainShadow = true ;
            c.gravity = Gravity.CENTER ;
        }

        @Override
        protected void onCreateView(View view) {

        }

        @Override
        protected Object getLayoutOrView() {
            return R.layout.dialog_my;
        }
    }

}
