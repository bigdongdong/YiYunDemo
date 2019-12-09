package com.chen.firstdemo.empty_recyclerview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.firstdemo.StartActivity;
import com.chen.firstdemo.R;

import java.util.ArrayList;
import java.util.List;

public class EmptyAdapterActivity extends AppCompatActivity {

    Context context ;
    RecyclerView recyclerView ;
    Button button1,button2,button3,button4 ;

    List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_adapter);

        context = this ;
        recyclerView = findViewById(R.id.recyclerView);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));

//        MyAdapter adapter = new MyAdapter(context);
        QuickAdapter adapter = new QuickAdapter<String>(context) {
            @Override
            protected Object getEmptyIdOrView() {
                View view = LayoutInflater.from(context).inflate(R.layout.item_empty,null);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                TextView goToCreateTV = view.findViewById(R.id.goToCreateTV);
                goToCreateTV.setTextColor(Color.WHITE);
                GradientDrawable gd = new GradientDrawable();
                gd.setColor(Color.parseColor("#70FF0000"));
                gd.setCornerRadius(20);
                gd.setStroke(2,Color.parseColor("#40111111"));
                goToCreateTV.setBackground(gd);
                goToCreateTV.setOnClickListener(v -> {
                    Intent intent = new Intent(context, StartActivity.class);
                    context.startActivity(intent);
                });
                return view;
            }

            @Override
            protected Object getItemViewOrId() {
                return R.layout.item_general;
            }

            @Override
            protected void onBindViewHolder(QuickAdapter.ViewHolder holder, String s, int i) {
                TextView textView = (TextView) holder.getView(R.id.textView);

                textView.setText(s+"  position:"+i);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"position:"+i,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);


        button1.setOnClickListener(view -> {
            adapter.update(getList());
        });
        button2.setOnClickListener(view -> {
            adapter.update(null);
        });
        button3.setOnClickListener(view -> {
            adapter.update(new ArrayList<>());
        });
        button4.setOnClickListener(view -> {
            adapter.append(getList());
        });

    }

    List<String> getList(){
        list = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            list.add("this is the "+i+" item");
        }
        return list ;
    }


    class MyAdapter extends HFEAdapter<String,MyAdapter.MyViewHolder> {

        protected MyAdapter(Context context) {
            super(context);
        }

        @Override
        protected Object getEmptyIdOrView() {
            View view = LayoutInflater.from(context).inflate(R.layout.item_empty,null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            TextView goToCreateTV = view.findViewById(R.id.goToCreateTV);
            goToCreateTV.setTextColor(Color.WHITE);
            GradientDrawable gd = new GradientDrawable();
            gd.setColor(Color.parseColor("#70FF0000"));
            gd.setCornerRadius(20);
            gd.setStroke(2,Color.parseColor("#40111111"));
            goToCreateTV.setBackground(gd);
            goToCreateTV.setOnClickListener(v -> {
                Intent intent = new Intent(context, StartActivity.class);
                context.startActivity(intent);
            });
            return view;
        }

        @Override
        protected Object getHeaderIdOrView() {
            return R.layout.item_header;
        }

        @Override
        protected Object getFooterIdOrView() {
            return R.layout.item_footer;
        }

        @Override
        protected MyViewHolder onCreateViewHolder(int itemType, ViewGroup parent) {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_general,parent,false));
        }

        @Override
        protected void onBindViewHolder(MyViewHolder holder, String s, final int position) {
            holder.textView.setText(s+"  position:"+position);
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"position:"+position,Toast.LENGTH_SHORT).show();
                }
            });
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView textView ;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.textView);
            }
        }
    }

}
