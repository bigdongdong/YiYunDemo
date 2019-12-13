package com.chen.firstdemo.select_tab_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.chen.firstdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectTabActivity extends AppCompatActivity {

    @BindView(R.id.tab1TV)
    TextView tab1TV;
    @BindView(R.id.tab2TV)
    TextView tab2TV;
    @BindView(R.id.tab3TV)
    TextView tab3TV;

    SelectTabLayout selectTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_tab);
        ButterKnife.bind(this);


        selectTabLayout = findViewById(R.id.selectTabLayout);
        selectTabLayout.setSelectedTab(1);

    }

    @OnClick({R.id.tab1TV, R.id.tab2TV, R.id.tab3TV})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tab1TV:
                selectTabLayout.setSelectedTab(1);
                break;
            case R.id.tab2TV:
                selectTabLayout.setSelectedTab(2);
                break;
            case R.id.tab3TV:
                selectTabLayout.setSelectedTab(3);
                break;
        }
    }
}
