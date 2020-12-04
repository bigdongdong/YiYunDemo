package com.chen.firstdemo.flight_chess.ludo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.chen.firstdemo.R;
import com.chen.firstdemo.recyclers.kotlin.WhiteRoundShadowShape;
import com.chen.firstdemo.utils.DensityUtil;
import com.chen.firstdemo.utils.ScreenUtil;

import java.util.List;

/**
 * create by chenxiaodong on 2020/11/29
 * ludo 骰子选择弹窗
 */
public class DicePop extends PopupWindow {

    private final int d5 ,d75f,d15 , d30 ,d45 , d50;
    private final int p10 ;
    private final int W ;
    final int[] resIds = new int[]{R.drawable.dice_1,R.drawable.dice_2,R.drawable.dice_3,R.drawable.dice_4,R.drawable.dice_5,R.drawable.dice_6};

    public DicePop(Context context, List<Integer> dices , OnDiceListener listener) {
        super(context);
        d5 = DensityUtil.dip2px(context,5);
        d75f = DensityUtil.dip2px(context,7.5f);
        d15 = DensityUtil.dip2px(context,15);
        d30 = DensityUtil.dip2px(context,30);
        d45 = DensityUtil.dip2px(context,45);
        d50 = DensityUtil.dip2px(context,50);
        W = d30 * dices.size() + d15 * (dices.size() + 1);
        p10 = DensityUtil.dip2px(context,10);


        LinearLayout ll = new LinearLayout(context);
        ViewGroup.LayoutParams vp = new ViewGroup.LayoutParams(W,d50);
        ll.setLayoutParams(vp);
        ll.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        ll.setBackground(new ShapeDrawable(new WhiteRoundShadowShape(DensityUtil.dip2px(context,8),
                DensityUtil.dip2px(context,3))));
        ll.setPadding(d75f,0,d75f,0);

        ll.setGravity(Gravity.CENTER_VERTICAL);
        ll.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams lp ;
        for (int i = 0; i < dices.size(); i++) {
            ImageView iv = new ImageView(context);
            lp = new LinearLayout.LayoutParams(d45,d50);
            iv.setLayoutParams(lp);
            iv.setImageResource(resIds[dices.get(i)-1]);
            iv.setPadding(d75f,p10,d75f,p10);
            ll.addView(iv);
            final int finalI = i ;
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onDice(dices.get(finalI));
                    }
                    dismiss();
                }
            });
        }

        this.setContentView(ll);
        this.setWidth(-2);
        this.setHeight(d50);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //设置null会造成5.0及以下点击外部不消失问题.
    }


    public void show(View v){
        /*计算v到边界的距离*/
        final int dy = -d5-d50-v.getHeight() ;

        final int cw = v.getWidth();
        int dx =-(W/2-cw/2) ;

        this.showAsDropDown(v,dx,dy,Gravity.BOTTOM);
    }

    public interface OnDiceListener{
        void onDice(int dice);
    }
}
