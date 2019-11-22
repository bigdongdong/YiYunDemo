package com.chen.firstdemo.empty_recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.firstdemo.R;

import java.util.ArrayList;
import java.util.List;

public class EmptyAdapterActivity extends AppCompatActivity {

    Context context ;
    RecyclerView recyclerView ;
    Button button1,button2,button3,button4 ;

    List<String> list = new ArrayList<>();
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

        MyAdapter adapter = new MyAdapter(context) {
            @Override
            void deal(MyViewHolder holder, String data) {
                holder.textView.setText(data);
            }
        };
        recyclerView.setAdapter(adapter);

        for (int i = 0; i < 1; i++) {
            list.add("this is the "+i+" item");
        }

        button1.setOnClickListener(view -> {
            adapter.update(list);
        });
        button2.setOnClickListener(view -> {
            adapter.update(null);
        });
        button3.setOnClickListener(view -> {
            adapter.update(new ArrayList<>());
        });
        button4.setOnClickListener(view -> {
            adapter.append(list);
        });
    }


    abstract class MyAdapter extends EmptyAdapter<String,MyAdapter.MyViewHolder> {

        protected MyAdapter(Context context) {
            super(context);
        }

        @Override
        protected Object getEmptyIdOrView() {
            ImageView imageView = new ImageView(context);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,200,0,0);
            imageView.setLayoutParams(params);
            imageView.setImageResource(R.mipmap.empty);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"我被点击了！",Toast.LENGTH_SHORT).show();
                }
            });
            return imageView;
        }

        @Override
        protected MyViewHolder onCreateViewHolder(int itemType, ViewGroup viewGroup) {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_general,viewGroup,false));
        }

        @Override
        protected void onBindViewHolder(MyViewHolder holder, String s, int position) {
            deal(holder,s);
        }

        abstract void deal(MyViewHolder holder , String data);

        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView textView ;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.textView);
            }
        }
    }
}
