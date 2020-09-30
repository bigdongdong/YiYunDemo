package com.chen.firstdemo.scrolls;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;
import com.chen.firstdemo.recyclers.empty_recyclerview.adapters.QuickAdapter;
import com.cxd.springscrolllinearlayout.SpringScrollView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpringScrollActivity extends BaseActivity {

    @BindView(R.id.ssll)
    SpringScrollView ssll;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spring_scroll);
        ButterKnife.bind(this);

        recycler.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        final QuickAdapter adapter1 = adapter();
        List list1 = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            list1.add(null);
        }
        recycler.setAdapter(adapter1);

        adapter1.update(list1);
    }

    private QuickAdapter adapter(){
        return new QuickAdapter(this) {
            @Override
            protected Object getEmptyIdOrView() {
                return null;
            }

            @Override
            protected Object getItemViewOrId() {
                TextView tv = new TextView(context);
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, 150);
                p.setMargins(50, 10, 50, 10);
                tv.setLayoutParams(p);
                tv.setBackgroundColor(Color.GRAY);
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(20);
                tv.setTextColor(Color.WHITE);
                return tv;
            }

            @Override
            protected void onBindViewHolder(@NotNull ViewHolder holder, Object o, int position) {
                TextView tv = (TextView) holder.itemView;
                tv.setText("recycler:"+position);

                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"recycler:"+position,Toast.LENGTH_SHORT).show();
                    }
                });

            }
        };
    }
}
