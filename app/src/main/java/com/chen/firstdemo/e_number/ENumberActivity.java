package com.chen.firstdemo.e_number;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;
import com.cxd.enumberview.ENumberView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ENumberActivity extends BaseActivity {


    @BindView(R.id.eNumberView)
    ENumberView eNumberView;
    @BindView(R.id.eNumberView2)
    ENumberView eNumberView2;
    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_number);
        ButterKnife.bind(this);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < 6; i++) {
                    sb.append(new Random().nextInt(10));
                }
                long l = Long.parseLong(sb.toString()) ;
                eNumberView.set(l);
                eNumberView2.set(l,true);
            }
        });

    }

}