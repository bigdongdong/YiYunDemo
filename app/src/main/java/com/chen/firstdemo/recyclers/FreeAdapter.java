package com.chen.firstdemo.recyclers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.firstdemo.R;
import com.chen.firstdemo.StartActivity;
import com.chen.firstdemo.recyclers.empty_recyclerview.adapters.HFEAdapter;

public class FreeAdapter extends HFEAdapter<String,FreeAdapter.MyViewHolder> {

    public FreeAdapter(Context context) {
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