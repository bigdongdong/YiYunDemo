package com.chen.firstdemo.wheel_anim_demo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chen.firstdemo.R;

public class WheelRecyclerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel_recycler);
    }

    class WheelLayoutManager extends RecyclerView.LayoutManager{


        @Override
        public void measureChild(@NonNull View child, int widthUsed, int heightUsed) {
            super.measureChild(child, widthUsed, heightUsed);

            int childWidth = getDecoratedMeasuredWidth(child);
        }

        @Override
        public void layoutDecorated(@NonNull View child, int left, int top, int right, int bottom) {
            super.layoutDecorated(child, left, top, right, bottom);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            super.onLayoutChildren(recycler, state);
        }

        @Override
        public RecyclerView.LayoutParams generateDefaultLayoutParams() {
            return null;
        }
    }
}
