package com.chen.firstdemo.recyclers.recyclerview_layoutmanager_demo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;
import com.chen.firstdemo.recyclers.empty_recyclerview.adapters.QuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewLayoutManagerActivity extends BaseActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;

    QuickAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_layout_manager);
        ButterKnife.bind(this);

        recycler.setBackground(new Drawable() {
            @Override
            public void draw(@NonNull Canvas canvas) {
                Path path = new Path();
                path.moveTo(250,250);
                path.rLineTo(600,300);
                path.rLineTo(-600,300);
                path.rLineTo(600,300);
                path.rLineTo(-600,300);

                Paint paint = new Paint();
                paint.setStyle(Paint.Style.FILL);

                canvas.drawPath(path,paint);
            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(@Nullable ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return PixelFormat.TRANSLUCENT;
            }
        });

        Path path = new Path();
        path.moveTo(250,250);
        path.rLineTo(600,300);
        path.rLineTo(-600,300);
        path.rLineTo(600,300);
        path.rLineTo(-600,300);

        recycler.setLayoutManager(new PathLayoutManager(path, 150));

        adapter = new QuickAdapter(this) {
            @Override
            protected Object getEmptyIdOrView() {
                return null;
            }

            @Override
            protected Object getItemViewOrId() {
                TextView tv = new TextView(context);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(300,80);
                tv.setLayoutParams(params);
                tv.setGravity(Gravity.CENTER);
                tv.setBackgroundColor(Color.GRAY);
                return tv;
            }

            @Override
            protected void onBindViewHolder(ViewHolder holder, Object o, int position) {
                TextView tv = (TextView) holder.getItemView();
                tv.setText(""+position);
            }
        };
        recycler.setAdapter(adapter);

        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(null);
        }
        adapter.update(list);
    }

}
