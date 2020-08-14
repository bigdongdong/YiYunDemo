package com.chen.firstdemo.suscat;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.andexert.library.RippleView;
import com.chen.firstdemo.R;
import com.example.otherdemos.freePaintDemo.FreePaint_MainActivity;
import com.example.otherdemos.mindMappingDemo.mindMappingDemo.MM_DemoActivity;
import com.example.otherdemos.paintDemo.myActivity.PaintDemoMainActivity;


public class OtherApplicationsActivity extends AppCompatActivity {
    CustomBiggerView timetableCBV,mindMappingCBV,freePaintCBV ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_applications);

        RippleView.OnRippleCompleteListener listener = new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                switch (rippleView.getId()){
                    case R.id.timetableCBV :
                        OtherApplicationsActivity.this.startActivity(new Intent(OtherApplicationsActivity.this, PaintDemoMainActivity.class));
                        break ;
                    case R.id.mindMappingCBV :
                        OtherApplicationsActivity.this.startActivity(new Intent(OtherApplicationsActivity.this, MM_DemoActivity.class));
                        break ;
                    case R.id.freePaintCBV :
                        OtherApplicationsActivity.this.startActivity(new Intent(OtherApplicationsActivity.this, FreePaint_MainActivity.class));
                        break ;
                }
            }
        };

        timetableCBV = findViewById(R.id.timetableCBV);
        mindMappingCBV = findViewById(R.id.mindMappingCBV);
        freePaintCBV = findViewById(R.id.freePaintCBV);


        timetableCBV.setOnRippleCompleteListener(listener);
        mindMappingCBV.setOnRippleCompleteListener(listener);
        freePaintCBV.setOnRippleCompleteListener(listener);


        /*toolbar 返回*/
        findViewById(R.id.toolbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OtherApplicationsActivity.this.finish();
            }
        });

    }

}
