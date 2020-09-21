package com.chen.firstdemo.scrolls;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;
import com.cxd.springscrolllinearlayout.SpringScrollLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpringScrollActivity extends BaseActivity {

    @BindView(R.id.fv)
    SpringScrollLinearLayout fv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group);
        ButterKnife.bind(this);

        final int count = 5;
        for (int i = 0; i < count; i++) {
            TextView tv = new TextView(context);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, 200);
            p.setMargins(50, 10, 50, 10);
            tv.setLayoutParams(p);
            tv.setBackgroundColor(Color.GRAY);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(40);
            tv.setText(i + "");
            tv.setTextColor(Color.WHITE);
            final int finalI = i ;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,""+finalI,Toast.LENGTH_SHORT).show();
                }
            });
//            tv.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    Toast.makeText(context,"long:"+finalI,Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//            });
            fv.addView(tv);
        }
    }
}
