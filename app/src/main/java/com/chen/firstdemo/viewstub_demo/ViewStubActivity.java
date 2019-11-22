package com.chen.firstdemo.viewstub_demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

import com.chen.firstdemo.R;

public class ViewStubActivity extends AppCompatActivity {


    private Activity context ;
    private ViewStub viewStub ;
    private Button button ;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stub);
        context = this ;

        viewStub = findViewById(R.id.viewStub);
        button = findViewById(R.id.button);

        viewStub.setLayoutResource(R.layout.dialog_goto_earn_gold_coin);

        button.setOnClickListener(
                v -> {
                    if(context.findViewById(R.id.viewStub) == null
                            || viewStub.getVisibility() == View.GONE){
                        viewStub.inflate();
                    }else{
                        viewStub.setVisibility(View.GONE);
                    }
                }
        );
    }
}
