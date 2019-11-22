package com.chen.firstdemo.empty_recyclerview;

import android.content.Context;
import android.media.Image;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chen.firstdemo.R;
import com.chen.firstdemo.utils.ScreenUtil;

public class EmptyAdapterActivity extends AppCompatActivity {

    Context context ;
    RecyclerView recyclerView ;
    Button button ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_adapter);

        context = this ;
        recyclerView = findViewById(R.id.recyclerView);
        button = findViewById(R.id.button);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));

        MyAdapter adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        button.setOnClickListener(view -> {
            adapter.update(new String[]{"1","2","3","4"});
        });
    }


    class MyAdapter extends EmptyAdapter<MyAdapter.EmptyViewHolder, MyAdapter.GeneralViewHolder>{

        String[] words ;

        public void update(String[] words){
            this.words = words ;
            if(this.words == null || this.words.length==0){
                this.isOnlyEmptyView = true ;
            }
            notifyDataSetChanged();
        }


        @Override
        EmptyViewHolder onCreateEmptyHolder(@NonNull ViewGroup viewGroup, int type) {
            Log.i("aaa", "onCreateEmptyHolder: ");
            ImageView emptyIV = new ImageView(context);
            ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtil.getScreenWidth(context),
                    ScreenUtil.getScreenHeight(context));
            emptyIV.setLayoutParams(params);
            emptyIV.setImageResource(R.mipmap.empty);
            return new EmptyViewHolder(emptyIV);
        }

        @Override
        GeneralViewHolder onCreateGeneralHolder(@NonNull ViewGroup viewGroup, int type) {
            return new GeneralViewHolder(LayoutInflater.from(context).inflate(R.layout.item_general,viewGroup,false));
        }

        @Override
        void onBindEViewHolder(EmptyViewHolder holder, int position) {

        }

        @Override
        void onBindGeneralViewHolder(RecyclerView.ViewHolder holder, int position) {
            GeneralViewHolder viewHolder = (GeneralViewHolder)holder;
            viewHolder.textView.setText(words[position]);
        }

        @Override
        int getGeneralItemCount() {
            if(words == null){
                return 0;
            }
            return words.length;
        }

        class EmptyViewHolder extends EViewHolder{
            ImageView emptyIV ;
            public EmptyViewHolder(@NonNull View itemView) {
                super(itemView);

                this.emptyIV = (ImageView) itemView;
            }
        }

        class GeneralViewHolder extends RecyclerView.ViewHolder{

            TextView textView ;
            public GeneralViewHolder(@NonNull View itemView) {
                super(itemView);

                textView = itemView.findViewById(R.id.textView);
            }
        }
    }
}
