package com.chen.firstdemo.recyclers.better_adapters.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;
import com.chen.firstdemo.recyclers.better_adapters.activity.adapters.Item;
import com.chen.firstdemo.recyclers.better_adapters.activity.adapters.MultipleAdapter;
import com.chen.firstdemo.utils.TextViewBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MultipleAdapterActivity extends BaseActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.integerBtn)
    Button integerBtn;
    @BindView(R.id.stringBtn)
    Button stringBtn;
    @BindView(R.id.booleanBtn)
    Button booleanBtn;
    MultipleAdapter mAdapter;
    @BindView(R.id.emptyBtn)
    Button emptyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_adapter);
        ButterKnife.bind(this);

        recycler.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        mAdapter = new MultipleAdapter(context) {
            @Item(value = Integer.class)
            public int resId = R.layout.item_bucket ;

            @Item(String.class)
            public View getStringView() {
                TextView tv = new TextViewBuilder()
                        .context(context)
                        .width(1080)
                        .height(200)
                        .gravity(Gravity.CENTER)
                        .textColor(Color.BLACK)
                        .isBold(true)
                        .isItalic(true)
                        .textSize(18)
                        .build();
                tv.setBackgroundColor(0xFFCCCCCC);
                return tv;
            }

            @Item(Boolean.class)
            public View getBooleanView() {
                return new TextViewBuilder()
                        .context(context)
                        .width(1080)
                        .height(200)
                        .gravity(Gravity.CENTER)
                        .textColor(Color.RED)
                        .isBold(true)
                        .isItalic(true)
                        .textSize(18)
                        .build();
            }

            @Override
            protected Object getEmptyIdOrView() {
                return R.layout.item_empty;
            }

            @Override
            protected void onBindViewHolder(View item, Object element, int position) {
                if (element instanceof String) {
                    TextView tv = (TextView) item;
                    tv.setText("String:" + element + "  p:" + position);
                } else if (element instanceof Boolean) {
                    TextView tv = (TextView) item;
                    tv.setText("Boolean:" + element + "  p:" + position);
                }

            }
        };
        recycler.setAdapter(mAdapter);
    }

    @OnClick({R.id.emptyBtn,R.id.integerBtn, R.id.stringBtn, R.id.booleanBtn})
    public void onViewClicked(View view) {
        List<Object> list = new ArrayList<>();
        switch (view.getId()) {
            case R.id.emptyBtn:
                mAdapter.update(null);
                return;
            case R.id.integerBtn:
                list.add(new Integer(1));
                break;
            case R.id.stringBtn:
                list.add(new String("1"));
                break;
            case R.id.booleanBtn:
                list.add(new Boolean(true));
                break;
        }
        mAdapter.append(list);
    }
}