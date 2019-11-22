package com.chen.firstdemo.dialog_demo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;

import com.chen.firstdemo.R;

public class DialogActivity extends AppCompatActivity {

    Activity context ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        context = this;

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new WatchPopWindow(context).showCenteral(R.layout.activity_dialog);
            }
        });
    }
}
