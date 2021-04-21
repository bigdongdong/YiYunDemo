package com.chen.firstdemo.recyclers.nest_recycle;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;
import com.chen.firstdemo.recyclers.FreeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NestRecycleActivity extends BaseActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nest_recycle);
        ButterKnife.bind(this);

        recycler.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));
        FreeAdapter fAdapter = new FreeAdapter(context);
        recycler.setAdapter(fAdapter);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(null);
        }
        fAdapter.update(list);
    }
}