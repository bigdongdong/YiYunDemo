package com.chen.firstdemo.recycler_manager_demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;
import com.chen.firstdemo.empty_recyclerview.adapters.QuickAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerManagerActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    QuickAdapter<String> adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_manager);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL,false));

        adapter = new QuickAdapter<String>(context) {
            @Override
            protected Object getEmptyIdOrView() {
                return null;
            }

            @Override
            protected Object getItemViewOrId() {
                TextView tv = new TextView(context);
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1,200);
                p.setMargins(50,10,50,10);
                tv.setLayoutParams(p);
                tv.setBackgroundColor(Color.GRAY);
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(40);
                tv.setTextColor(Color.WHITE);
                return tv;
            }

            @Override
            protected void onBindViewHolder(ViewHolder holder, String s, int position) {
                TextView tv = holder.getItemView();
                tv.setText(s);
            }
        };
        recyclerView.setAdapter(adapter);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(""+i);
        }
        adapter.update(list);
    }
}
