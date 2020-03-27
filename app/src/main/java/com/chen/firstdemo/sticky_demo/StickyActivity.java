package com.chen.firstdemo.sticky_demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chen.firstdemo.R;
import com.chen.firstdemo.empty_recyclerview.adapters.QuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StickyActivity extends AppCompatActivity {

    @BindView(R.id.recycler1)
    RecyclerView recycler1;
    @BindView(R.id.stickyBar)
    RecyclerView stickyBar;
    @BindView(R.id.recycler2)
    RecyclerView recycler2;

    QuickAdapter adapter1 , adapter2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky);
        ButterKnife.bind(this);


        recycler1.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL,false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        recycler2.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL,false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        adapter1 = new QuickAdapter(this) {
            @Override
            protected Object getEmptyIdOrView() {
                return null;
            }

            @Override
            protected Object getItemViewOrId() {
                TextView v = new TextView(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100,300);
                v.setLayoutParams(params);
                return v;
            }

            @Override
            protected void onBindViewHolder(ViewHolder holder, Object o, int position) {
                TextView v = (TextView) holder.getItemView();
                v.setText(position+"");
            }
        };
        adapter2 = new QuickAdapter(this) {
            @Override
            protected Object getEmptyIdOrView() {
                return null;
            }

            @Override
            protected Object getItemViewOrId() {
                TextView v = new TextView(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1,300);
                v.setLayoutParams(params);
                return v;
            }

            @Override
            protected void onBindViewHolder(ViewHolder holder, Object o, int position) {
                TextView v = (TextView) holder.getItemView();
                v.setText(position+"");
            }
        };
        recycler1.setAdapter(adapter1);
        recycler2.setAdapter(adapter2);

        List l = new ArrayList();
        l.add(null);
        l.add(null);
        l.add(null);
        l.add(null);
        l.add(null);
        l.add(null);
        l.add(null);
        l.add(null);
        l.add(null);
        l.add(null);
        l.add(null);
        l.add(null);
        l.add(null);
        l.add(null);
        l.add(null);
        l.add(null);
        l.add(null);
        adapter1.update(l);
        adapter2.update(l);
    }
}
