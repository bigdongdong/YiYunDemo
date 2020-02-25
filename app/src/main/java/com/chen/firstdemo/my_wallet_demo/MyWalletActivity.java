package com.chen.firstdemo.my_wallet_demo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;
import com.chen.firstdemo.empty_recyclerview.adapters.QuickAdapter;
import com.chen.firstdemo.utils.DensityUtil;
import com.chen.firstdemo.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

public class MyWalletActivity extends BaseActivity {

    private RelativeLayout myAssetsRL ;//我的资产layout
    private RelativeLayout wxPayRL , aliPayRL , musicalNotePayRL;
    private ImageView wxSelectIV , aliSelectIV , musicalNoteSelectIV ;  //微信、支付宝、音符支付右侧选中状态的imageView
    private TextView confirmTV ;

    private RecyclerView recycler ;
    private QuickAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);


        myAssetsRL = findViewById(R.id.myAssetsRL);

        wxPayRL = findViewById(R.id.wxPayRL);
        aliPayRL = findViewById(R.id.aliPayRL);
        musicalNotePayRL = findViewById(R.id.musicalNotePayRL);

        wxSelectIV = findViewById(R.id.wxSelectIV);
        aliSelectIV = findViewById(R.id.aliSelectIV);
        musicalNoteSelectIV = findViewById(R.id.musicalNoteSelectIV);
        confirmTV = findViewById(R.id.confirmTV);

        recycler = findViewById(R.id.recycler);

        GradientDrawable gd ;
        gd = new GradientDrawable();
        gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        gd.setColors(new int[]{Color.parseColor("#FF892F"),Color.parseColor("#FFD674")});
        gd.setCornerRadius(DensityUtil.dip2px(context,15));
        myAssetsRL.setBackground(gd);

        gd = new GradientDrawable();
        gd.setStroke(DensityUtil.dip2px(context,1), Color.parseColor("#CCCCCC"));
        gd.setCornerRadius(DensityUtil.dip2px(context,8));
        wxSelectIV.setBackground(gd);
        aliSelectIV.setBackground(gd);
        musicalNoteSelectIV.setBackground(gd);

        gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#FFD514"));
        gd.setCornerRadius(DensityUtil.dip2px(context,22.5f));
        confirmTV.setBackground(gd);


        /*initialize*/
        recycler.setLayoutManager(new GridLayoutManager(context,2){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        recycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                outRect.left = ((parent.getChildAdapterPosition(view) + 1) % 2 == 1 ) ? DensityUtil.dip2px(context,15): DensityUtil.dip2px(context,5);
                outRect.right = ((parent.getChildAdapterPosition(view) + 1) % 2 == 1 ) ? DensityUtil.dip2px(context,5): DensityUtil.dip2px(context,15);
                outRect.bottom =  DensityUtil.dip2px(context,10);
            }
        });
        adapter = new QuickAdapter(context) {
            @Override
            protected Object getEmptyIdOrView() {
                return null;
            }

            @Override
            protected Object getItemViewOrId() {
                return new ItemView(context);
            }

            @Override
            protected void onBindViewHolder(ViewHolder holder, Object o, int position) {
                ItemView v = (ItemView) holder.getItemView();

//                StringBuffer sb = new StringBuffer("1");
//                for (int i = 0; i < position; i++) {
//                    sb.append("0");
//                }
                v.diaTV.setText((position+1) *10 +"00");
                v.rmbTV.setText("¥"+(position+1) *10);

                if(position == 0){
                    v.callOnClick();
                }
            }
        };
        recycler.setAdapter(adapter);
        List list = new ArrayList();
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(null);
        adapter.update(list);



        /*微信支付*/
        wxPayRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wxSelectIV.setImageResource(R.mipmap.pay_mothod_select_icon);
                aliSelectIV.setImageBitmap(null);
                musicalNoteSelectIV.setImageBitmap(null);
            }
        });

        /*支付宝支付*/
        aliPayRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wxSelectIV.setImageBitmap(null);
                aliSelectIV.setImageResource(R.mipmap.pay_mothod_select_icon);
                musicalNoteSelectIV.setImageBitmap(null);
            }
        });

        /*音符支付*/
        musicalNotePayRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wxSelectIV.setImageBitmap(null);
                aliSelectIV.setImageBitmap(null);
                musicalNoteSelectIV.setImageResource(R.mipmap.pay_mothod_select_icon);
            }
        });

        confirmTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private ItemView lastItemView ;
    class ItemView extends RelativeLayout{
        private Context context ;
        private final int vw ;
        private final int vh ;
        private LayoutParams p ;
        private GradientDrawable gd ;

        public TextView diaTV ;
        public TextView rmbTV ;

        public ItemView(Context context) {
            super(context);
            this.context = context ;

            vw = ( ScreenUtil.getScreenWidth(context) - DensityUtil.dip2px(context,40) ) / 2 ;
            vh = DensityUtil.dip2px(context,50);
            p = new LayoutParams(vw,vh);
            this.setLayoutParams(p);
            this.setBackground(getParentGD(false));

            generateViews();


            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(lastItemView == null){
                        ItemView.this.setBackground(getParentGD(true));
                        lastItemView = ItemView.this ;
                    }else if(lastItemView != ItemView.this){
                        lastItemView.setBackground(getParentGD(false));
                        ItemView.this.setBackground(getParentGD(true));
                        lastItemView = ItemView.this ;
                    }
                    confirmTV.setText("支付"+rmbTV.getText());
                }
            });
        }

        private void generateViews() {
            ImageView iv = new ImageView(context);
            p = new LayoutParams(DensityUtil.dip2px(context,20),DensityUtil.dip2px(context,15));
            p.addRule(RelativeLayout.CENTER_VERTICAL);
            p.leftMargin = DensityUtil.dip2px(context,15);
            iv.setLayoutParams(p);
            iv.setImageResource(R.mipmap.wallet_diamond_icon);
            this.addView(iv);

            diaTV = new TextView(context);
            p = new LayoutParams(-2,-2);
            p.addRule(RelativeLayout.CENTER_VERTICAL);
            p.leftMargin = DensityUtil.dip2px(context,45);
            diaTV.setLayoutParams(p);
            TextPaint paint = diaTV.getPaint();
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            diaTV.setTextColor(Color.parseColor("#333333"));
            diaTV.setTextSize(15);
            this.addView(diaTV);

            rmbTV = new TextView(context);
            p = new LayoutParams(-2,-2);
            p.addRule(RelativeLayout.CENTER_VERTICAL);
            p.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            p.rightMargin = DensityUtil.dip2px(context,15);
            rmbTV.setLayoutParams(p);
            rmbTV.setTextSize(12);
            rmbTV.setTextColor(Color.parseColor("#999999"));
            this.addView(rmbTV);
        }

        /**
         * 获取整个布局的背景
         * @param isSelected
         * @return
         */
        private GradientDrawable getParentGD(boolean isSelected) {
            gd = new GradientDrawable();
            gd.setColor(Color.parseColor(isSelected ? "#FFF6CD" : "#F7F7F7"));
            gd.setCornerRadius(DensityUtil.dip2px(context,25));
            if(isSelected){
                gd.setStroke(DensityUtil.dip2px(context,1.5f),Color.parseColor("#FFD514"));
            }else{
                gd.setStroke(2,Color.parseColor("#88CCCCCC"));
            }
            return gd ;
        }

    }
}
