package com.chen.firstdemo.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

    protected String TAG = "";
    protected Activity context ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName() + "TAG";
        context = this ;
    }

    protected void toast(String str){
        Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
    }
}
