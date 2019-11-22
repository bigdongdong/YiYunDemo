package com.chen.firstdemo.dialog_demo;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chen.firstdemo.R;


/**
 * create by chenxiaodong on 2019/11/15
 *
 * 看视频赚金币dialog
 */
public class WatchVideoToEarnGoldCoinDialog extends Dialog {
    private View rootView ;
    private TextView confirmTV ;
    private LinearLayout dismissLL;
    private ImageView dismissIV ;
    public WatchVideoToEarnGoldCoinDialog(@NonNull Context context , View.OnClickListener confirmListener ) {
        super(context,R.style.dialog_tran);

        Window win = this.getWindow();
        win.setGravity(Gravity.CENTER);   // 这里控制弹出的位置
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        win.setAttributes(lp);

        rootView = LayoutInflater.from(context).inflate(R.layout.dialog_goto_earn_gold_coin,null);
        this.setContentView(rootView);
        this.setCancelable(true);

        confirmTV = rootView.findViewById(R.id.confirmTV);
        dismissLL = rootView.findViewById(R.id.dismissLL);
        dismissIV = rootView.findViewById(R.id.dismissIV);
        confirmTV.setOnClickListener(confirmListener);

        /*绘制 × 的背景*/
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#7C7C7C"));
        gd.setCornerRadius(60);
        dismissIV.setBackground(gd);

        dismissLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(WatchVideoToEarnGoldCoinDialog.this.isShowing()){
                    WatchVideoToEarnGoldCoinDialog.this.dismiss();
                }
            }
        });

    }
}
