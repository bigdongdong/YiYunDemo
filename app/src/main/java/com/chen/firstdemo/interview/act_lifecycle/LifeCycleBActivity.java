package com.chen.firstdemo.interview.act_lifecycle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.chen.firstdemo.R;

public class LifeCycleBActivity extends AppCompatActivity {

    private final String TAG = "LifeCycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle_b);

        Log.i(TAG, "onCreate: B");


        Button toABtn = findViewById(R.id.toABtn);
        toABtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(LifeCycleBActivity.this,LifeCycleAActivity.class);
                it.putExtra("data","data");
                LifeCycleBActivity.this.startActivity(it);
            }
        });
        Button toBBtn = findViewById(R.id.toBBtn);
        toBBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(LifeCycleBActivity.this,LifeCycleBActivity.class);
                it.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                it.putExtra("data","data");
                LifeCycleBActivity.this.startActivity(it);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG, "onNewIntent: B "+intent.getStringExtra("data"));
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: B");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: B");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: B");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: B");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: B");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: B");
    }
}
