package com.chen.firstdemo.soft_keyboard;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chen.firstdemo.R;
import com.chen.firstdemo.utils.DensityUtil;
import com.chen.firstdemo.utils.GradientDrawableBuilder;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class LotteryInputView extends RelativeLayout {
    private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7 ;
    private EditText et;
    private TextView[] tvs ;

    final GradientDrawable normalGd ;
    final GradientDrawable selectedGd ;
    private OnGlobalLayoutListener listener ;
    public LotteryInputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_lottery_input, this);
        this.setWillNotDraw(false);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv6 = findViewById(R.id.tv6);
        tv7 = findViewById(R.id.tv7);
        et = findViewById(R.id.et);

        tvs = new TextView[]{tv1, tv2, tv3, tv4, tv5, tv6, tv7};

        selectedGd = new GradientDrawableBuilder()
                .color(0x22111111)
                .conner(DensityUtil.dip2px(context,5))
                .storkeColor(Color.parseColor("#ff2e63"))
                .storkeWidth(DensityUtil.dip2px(context,1f))
                .build();

        normalGd = new GradientDrawableBuilder()
                .color(0x22111111)
                .conner(DensityUtil.dip2px(context,5))
                .build();

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                et.requestFocus();

                InputMethodManager imm = (InputMethodManager)context.getSystemService(INPUT_METHOD_SERVICE);
                imm.showSoftInput(et,0);
            }
        });

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                List<String> list = new ArrayList<>();
                String[] strings = s.toString().trim().split("");
                for (String str : strings){
                    if(str != null && !str.equals("") && !str.equals(" ")){
                        list.add(str);
                    }
                }
                for (int i = 0; i < tvs.length; i++) {
                    if(list.size() > i){
                        String num = list.get(i);
                        tvs[i].setText(num);
                    }else{
                        tvs[i].setText(null);
                    }
                }

                refreshBlocks() ;
            }
        });

        /*初始化时弹出软键盘*/
        refreshBlocks();

//        listener = new OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                LotteryInputView.this.callOnClick();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        LotteryInputView.this.getViewTreeObserver().removeOnGlobalLayoutListener(
//                                listener);
//                    }
//                },300);
//            }
//        };
//        this.getViewTreeObserver().addOnGlobalLayoutListener(listener);
    }

    /**
     * 更新
     */
    private void refreshBlocks(){
        String str = et.getText().toString().trim();
        for (int i = 0; i < tvs.length; i++) {
            if(i == str.length()){
                tvs[i].setBackground(selectedGd);
            }else{
                tvs[i].setBackground(normalGd);
            }
        }
    }
}
