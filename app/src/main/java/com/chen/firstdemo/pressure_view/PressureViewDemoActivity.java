package com.chen.firstdemo.pressure_view;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;
import com.chen.firstdemo.base.RoundImageView;
import com.chen.firstdemo.recyclers.empty_recyclerview.adapters.QuickAdapter;
import com.chen.firstdemo.utils.DensityUtil;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PressureViewDemoActivity extends BaseActivity {


    @BindView(R.id.recycler)
    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pressure_view_demo);
        ButterKnife.bind(this);


//        WhiteRoundShadowShape shape = new WhiteRoundShadowShape(DensityUtil.dip2px(context,20),
//                DensityUtil.dip2px(context,3));
//        pressureView.setBackground(new ShapeDrawable(shape));

        recycler.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));
        recycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = 10 ;
            }
        });
        QuickAdapter adapter = new QuickAdapter(context) {
            @Override
            protected Object getEmptyIdOrView() {
                return null;
            }

            @Override
            protected Object getItemViewOrId() {
                PressureView pv = new PressureView(context);
                pv.setPadding(20,20,20,20);
                ViewGroup.LayoutParams vp = new ViewGroup.LayoutParams(-1, DensityUtil.dip2px(context,150));
                pv.setLayoutParams(vp);


                RoundImageView iv = new RoundImageView(context);
                vp = new ViewGroup.LayoutParams(-1,-1);
                iv.setLayoutParams(vp);
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                pv.addView(iv);
                iv.setImageResource(R.mipmap.progress_iv_default);
                return pv;
            }

            @Override
            protected void onBindViewHolder(@NotNull ViewHolder holder, Object o, int position) {

            }
        };
        recycler.setAdapter(adapter);
        adapter.doTest(30);
        }
}