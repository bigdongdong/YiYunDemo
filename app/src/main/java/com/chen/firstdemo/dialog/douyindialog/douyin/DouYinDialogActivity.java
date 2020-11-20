package com.chen.firstdemo.dialog.douyindialog.douyin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;
import com.chen.firstdemo.dialog.BaseDialog;
import com.chen.firstdemo.recyclers.empty_recyclerview.adapters.QuickAdapter;
import com.chen.firstdemo.utils.DensityUtil;
import com.chen.firstdemo.utils.GradientDrawableBuilder;
import com.chen.firstdemo.utils.ScreenUtil;
import com.cxd.likedouyinframelayout.LikeDouYinDialog;
import com.cxd.likedouyinframelayout.LikeDouYinFrameLayout;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DouYinDialogActivity extends BaseActivity {

//    @BindView(R.id.button)
//    Button button;
    @BindView(R.id.buttonDiy)
    Button buttonDiy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dou_yin_dialog);
        ButterKnife.bind(this);

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new MyDialog(DouYinDialogActivity.this).show();
//            }
//        });
        buttonDiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyDiyDialog(DouYinDialogActivity.this).show();
            }
        });
        buttonDiy.callOnClick();
    }


    @SuppressLint("ValidFragment")
//    public static class MyDialog extends LikeDouYinDialog {
//        public MyDialog(@NonNull AppCompatActivity context) {
//            super(context);
//        }
//
//        @Override
//        protected void onConfig(Config c) {
//            c.gravity = Gravity.BOTTOM;
//            c.width = -1;
//            c.height = (int) (ScreenUtil.getScreenHeight(context) * 0.75f);
//            c.retainShadow = true;
//        }
//
//        @Override
//        protected void onCreateView(View view) {
//        }
//
//        @Override
//        protected Object getLayoutIdOrView() {
//            return R.layout.dialog_dou_yin;
//        }
//    }

    public static class MyDiyDialog extends BaseDialog{

        public MyDiyDialog(@NonNull Activity context) {
            super(context);
        }

        @Override
        protected void onConfig(Config c) {
            c.gravity = Gravity.BOTTOM ;
            c.width = -1;
            c.height = DensityUtil.dip2px(context,600);
            c.style = R.style.pop_bottom_animation ;
        }

        @Override
        protected void onCreateView(View view) {
            final int c15 = DensityUtil.dip2px(context,15);
            new GradientDrawableBuilder()
                    .color(0xFFCCCCCC)
                    .conners(new float[]{c15,c15,c15,c15,0,0,0,0})
                    .into(view);

            ((LikeDouYinFrameLayout)view).setOnCloseListener(new LikeDouYinFrameLayout.OnCloseListener() {
                @Override
                public void onClose() {
                    dismiss();
                }
            });

            RecyclerView recycler = view.findViewById(R.id.recycler);
            recycler.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));
            QuickAdapter adapter = new QuickAdapter(context) {
                @Override
                protected Object getEmptyIdOrView() {
                    return null;
                }

                @Override
                protected Object getItemViewOrId() {
                    return R.layout.item_bucket;
                }

                @Override
                protected void onBindViewHolder(@NotNull QuickAdapter.ViewHolder holder, Object o, int position) {
                    holder.getItemView().setBackgroundColor(Color.WHITE);
                    holder.getItemView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i("aaa", "onClick: ");
                        }
                    });
                }
            };
            recycler.setAdapter(adapter);
            adapter.doTest(30);
        }

        @Override
        protected Object getLayoutOrView() {
            return R.layout.dialog_dou_yin_diy;
        }
    }
}