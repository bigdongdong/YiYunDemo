package com.chen.firstdemo.interview.act_lifecycle;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.chen.firstdemo.R;
import com.chen.firstdemo.dialog.BaseDialog;

public class LifeCycleAActivity extends AppCompatActivity {

//    private final String TAG = "LifeCycle";
    private final String TAG = "LifeCycleAActivityTAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle_a);

        if(savedInstanceState == null){
            Log.i(TAG, "onCreate: A first");
        }else{
            Log.i(TAG, "onCreate: A second");
        }

        new Handler();
        Looper.myLooper();

        getLifecycle().addObserver(new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            public void ready(){
                Log.e("--LifecycleObserver","ON_START");
            }
            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            public void connectListener() {
                Log.e("--LifecycleObserver","ON_RESUME");
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            public void disconnectListener() {
                Log.e("--LifecycleObserver","ON_PAUSE");
            }
        });

        Button toABtn = findViewById(R.id.toABtn);
        toABtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(LifeCycleAActivity.this,LifeCycleAActivity.class);
                it.putExtra("data","data");
                LifeCycleAActivity.this.startActivity(it);
            }
        });


        Button toBBtn = findViewById(R.id.toBBtn);
        toBBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(LifeCycleAActivity.this,LifeCycleBActivity.class);
                it.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                LifeCycleAActivity.this.startActivity(it);
            }
        });
        Button dialogBtn = findViewById(R.id.dialogBtn);
        dialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyDialog(LifeCycleAActivity.this).show();
            }
        });

        Button recreateBtn = findViewById(R.id.recreateBtn);
        recreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
                Log.i(TAG, "onClick: recreate()");
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG, "onNewIntent: A "+intent.getStringExtra("data"));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState: A");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState: A");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: A");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: A");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: A");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: A");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: A");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: A");
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
