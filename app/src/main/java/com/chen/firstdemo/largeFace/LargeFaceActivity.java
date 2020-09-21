package com.chen.firstdemo.largeFace;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LargeFaceActivity extends BaseActivity {

    @BindView(R.id.perTV)
    TextView perTV;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.lfv)
    BaromterView lfv;
    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_face);
        ButterKnife.bind(this);


        final float[] progress = {0f};
        final DecimalFormat df2 = new DecimalFormat("###.0");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress[0] += 0.05f;
                progress[0] = Math.min(progress[0], 1);
                lfv.setProgressAndInvalidate(progress[0]);
                perTV.setText(df2.format(progress[0] * 100) + "%");
            }
        });


        /*给tv设置drawableLeft*/
        tv.setCompoundDrawablePadding(20);

        Drawable drawable1 = new ColorDrawable(0xFFb83b5e);
        drawable1.setBounds(10,-20,30,0);

        Drawable drawable2 = new ColorDrawable(0xFFb83b5e);
        drawable2.setBounds(-10,-20,10,0);
        tv.setCompoundDrawables(drawable1,null,drawable2,null);
    }
}
