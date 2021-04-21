package com.chen.firstdemo.soft_keyboard;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;
import com.chen.firstdemo.dialog.BaseDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SoftKeyBoardActivity extends BaseActivity {

    @BindView(R.id.btn)
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_key_board);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        new MyDialog(context).show();
    }

    class MyDialog extends BaseDialog {

        public MyDialog(@NonNull Activity context) {
            super(context);
        }

        @Override
        protected void onConfig(Config c) {
            c.cancelAble = true;
            c.canceledOnTouchOutside = false ;
            c.gravity = Gravity.CENTER ;
        }

        @Override
        protected void onCreateView(View view) {

        }

        @Override
        protected Object getLayoutOrView() {
            return R.layout.dialog_custom;
        }
    }
}