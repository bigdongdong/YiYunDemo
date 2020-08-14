package com.chen.firstdemo.recyclers.hover_recycler_demo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;
import com.chen.firstdemo.recyclers.empty_recyclerview.adapters.QuickAdapter;
import com.chen.firstdemo.utils.DensityUtil;
import com.chen.firstdemo.utils.ScreenUtil;
import com.demon.yu.decoration.TopDecoration;
import com.demon.yu.decoration.VirtualFamily;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HoverRecyclerActivity extends BaseActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;

    private QuickAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hover_recycler);
        ButterKnife.bind(this);

        recycler.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recycler.addItemDecoration(new HoverItemDecoration(100){
            @Override
            protected List<Integer> getHoverPosis() {
                List<Integer> posis = new ArrayList<>();
                for (int i = 0; i < 30; i++) {
                    posis.add(i);
                }
                return posis;
            }

            @Override
            protected void drawHover(Canvas c, int position, Rect rect) {
                if(position % 2 == 0){
                    c.drawColor(Color.RED);
                }else{
                    c.drawColor(Color.BLUE);
                }

                Path path = new Path();
                path.moveTo(100,rect.centerY());
                path.rLineTo(ScreenUtil.getScreenWidth(context) - 200 ,0);
                path.close();

                Paint p = new Paint();
                p.setTextSize(DensityUtil.sp2px(context,14));
                p.setStyle(Paint.Style.FILL_AND_STROKE);
                p.setStrokeWidth(3);
                p.setColor(Color.WHITE);

                c.drawTextOnPath("posi == "+position,path,0,14/2,p);
//                c.drawPath(path,p);

                p.setStyle(Paint.Style.STROKE);

            }
        });

        adapter = new QuickAdapter(this) {
            @Override
            protected Object getEmptyIdOrView() {
                return null;
            }

            @Override
            protected Object getItemViewOrId() {
                RecyclerView recycler = new RecyclerView(context);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-1,-2);
                recycler.setLayoutParams(params);
                recycler.setNestedScrollingEnabled(false);
                recycler.setLayoutManager(new GridLayoutManager(context,3){
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                });

                final QuickAdapter adapter1 = new QuickAdapter(context) {
                    @Override
                    protected Object getEmptyIdOrView() {
                        return null;
                    }

                    @Override
                    protected Object getItemViewOrId() {
                        return R.layout.item_bitmap_mix;
                    }

                    @Override
                    protected void onBindViewHolder(ViewHolder holder, Object o, int position) {

                    }
                };
                recycler.setAdapter(adapter1);
                adapter1.doTest(new Random().nextInt(10));

                return recycler;
            }

            @Override
            protected void onBindViewHolder(ViewHolder holder, Object o, int position) {

            }
        };
        recycler.setAdapter(adapter);

        adapter.doTest(30);

    }
}
