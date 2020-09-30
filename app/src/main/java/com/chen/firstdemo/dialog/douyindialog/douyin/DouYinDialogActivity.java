package com.chen.firstdemo.dialog.douyindialog.douyin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;
import com.chen.firstdemo.dialog.BaseDialog;
import com.chen.firstdemo.dialog.LikeDouYinDialog;
import com.chen.firstdemo.recyclers.empty_recyclerview.adapters.QuickAdapter;
import com.chen.firstdemo.utils.DensityUtil;
import com.chen.firstdemo.utils.ScreenUtil;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DouYinDialogActivity extends BaseActivity {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.buttonDiy)
    Button buttonDiy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dou_yin_dialog);
        ButterKnife.bind(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyDialog(DouYinDialogActivity.this).show();
            }
        });
        buttonDiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyDiyDialog(DouYinDialogActivity.this).show();
            }
        });
        buttonDiy.callOnClick();
    }


    @SuppressLint("ValidFragment")
    public static class MyDialog extends LikeDouYinDialog {
        public MyDialog(@NonNull AppCompatActivity context) {
            super(context);
        }

        @Override
        protected void onConfig(Config c) {
            c.gravity = Gravity.BOTTOM;
            c.width = -1;
            c.height = (int) (ScreenUtil.getScreenHeight(context) * 0.75f);
            c.retainShadow = true;
        }

        @Override
        protected void onCreateView(View view) {
        }

        @Override
        protected Object getLayoutOrView() {
            return R.layout.dialog_dou_yin;
        }
    }

    public static class MyDiyDialog extends BaseDialog{

        public MyDiyDialog(@NonNull Activity context) {
            super(context);
        }

        @Override
        protected void onConfig(Config c) {
            c.gravity = Gravity.BOTTOM ;
            c.width = -1;
            c.height = DensityUtil.dip2px(context,600);
        }

        @Override
        protected void onCreateView(View view) {
            ((LikeDouYinFrameLayout)view).setOnCloseListener(new LikeDouYinFrameLayout.OnCloseListener() {
                @Override
                public void onClose() {
                    dismiss();
                }
            });
            RecyclerView recycler = (RecyclerView) ((LikeDouYinFrameLayout) view).getChildAt(0);
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