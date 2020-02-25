package com.chen.firstdemo.matrix_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.chen.firstdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MatrixActivity extends AppCompatActivity {

    @BindView(R.id.b1)
    Button b1;
    @BindView(R.id.b2)
    Button b2;
    @BindView(R.id.b3)
    Button b3;
    @BindView(R.id.b4)
    Button b4;
    @BindView(R.id.mv)
    MatrixView mv;
    @BindView(R.id.b5)
    Button b5;
    @BindView(R.id.fv)
    FoldCellView fv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.b1:
                /*移动*/
                mv.setMethod(0);
                break;
            case R.id.b2:
                /*旋转*/
                /*参数依次是:旋转角度，轴心(x,y)*/
                mv.setMethod(1);
                break;
            case R.id.b3:
                /*缩放*/
                /*参数依次是：X，Y轴上的缩放比例；缩放的轴心。*/
                mv.setMethod(2);
                break;
            case R.id.b4:
                /*错切*/
                /*参数依次是：X，Y轴上的缩放比例*/
                mv.setMethod(3);
                break;
            case R.id.b5:
                /*重置*/
                mv.setMethod(-1);
                break;
        }
    }

    @OnClick(R.id.fv)
    public void onViewClicked() {
        fv.startFold();
    }
}
