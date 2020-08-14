package com.chen.firstdemo.recyclers.drag_recycler_demo;

import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chen.firstdemo.base.BaseActivity;
import com.chen.firstdemo.recyclers.empty_recyclerview.adapters.QuickAdapter;

import java.util.ArrayList;
import java.util.List;

public class DragRecyclerDeniActivity extends BaseActivity {

    QuickAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView recycler = new RecyclerView(context);
        recycler.setLayoutParams(new LinearLayout.LayoutParams(-1,-1));
        recycler.setLayoutManager(new LinearLayoutManager(context));
        recycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 20 ;
            }
        });
        setContentView(recycler);

        adapter = new QuickAdapter(context) {
            @Override
            protected Object getEmptyIdOrView() {
                return null ;
            }

            @Override
            protected Object getItemViewOrId() {
                TextView tv = new TextView(context);
                tv.setLayoutParams(new LinearLayout.LayoutParams(-1,200));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(18);
                tv.setBackgroundColor(Color.GRAY);
                return tv;
            }

            @Override
            protected void onBindViewHolder(ViewHolder holder, Object o, int position) {
                TextView tv = (TextView) holder.getItemView();
                tv.setText("这是第"+position+"项");

            }
        };
        recycler.setAdapter(adapter);

        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(null);
        }
        adapter.update(list);



        ItemTouchHelper helper = new ItemTouchHelper(new DragCallback(adapter) {
            @Override
            void onDrag(RecyclerView.ViewHolder holder) {

            }

            @Override
            void dropDownView(RecyclerView recycler, RecyclerView.ViewHolder holder) {

            }
        });
        helper.attachToRecyclerView(recycler);
    }
}
