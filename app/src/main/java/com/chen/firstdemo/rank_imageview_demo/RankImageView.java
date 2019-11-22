package com.chen.firstdemo.rank_imageview_demo;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chen.firstdemo.R;

/**
 * create by chenxiaodong on 2019/11/19
 *
 * 最新排行榜的前三名，携带头饰布局
 * 内置一个CircleImageView和一个View
 * CircleImageView是圆形头像控件
 * view是遮罩层，是透明png头饰，实例化时传入type：1=第一名 2=第二名 3=第三名
 */
public class RankImageView extends RelativeLayout {
    private final String TAG = "RankImageView-->TAG";
    private LayoutParams params ;

    private ImageView civ ;
    private View coverView ; //遮罩View，之所以用遮罩View 不用setForground是为了提高兼容性

    private Context context ;
    private int type ;

    public RankImageView(Context context , int type) {
        super(context);
        this.context = context ;
        this.type = type ;

        params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        this.setLayoutParams(params);

        civ = new ImageView(context);
        coverView = new ImageView(context);

        this.addView(civ);
        this.addView(coverView);

        params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        coverView.setLayoutParams(params);

        switch(type){
            case 1:
                coverView.setBackgroundResource(R.mipmap.rank_iv_cover_1);
                break;
            case 2:
                coverView.setBackgroundResource(R.mipmap.rank_iv_cover_2);
                break;
            case 3:
                coverView.setBackgroundResource(R.mipmap.rank_iv_cover_3);
                break;
            default:
                break;
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        int width = this.getMeasuredWidth();
        int civWidth = 0;

        switch(type){
            case 1:
                civWidth = (int) ((150f/235f) * width);
                params = new LayoutParams(civWidth , civWidth);
                params.setMargins(0, (int) ((60f/235f) * width),
                        0,0);
                break;
            case 2:
                civWidth = (int) ((164f/235f) * width);
                params = new LayoutParams(civWidth , civWidth);
                params.setMargins(0, (int) ((50f/235f) * width),
                        0,0);
                break;
            case 3:
                civWidth = (int) ((164f/235f) * width);
                params = new LayoutParams(civWidth , civWidth);
                params.setMargins(0, (int) ((50f/235f) * width),
                        0,0);
                float offX = ((5f/235f)*width) ;
                civ.setTranslationX(offX);
                break;
            default:
                break;
        }
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        civ.setLayoutParams(params);

        civ.setImageResource(R.mipmap.circle_image);
    }


    public View getCover(){
        if(coverView !=null){
            return coverView ;
        }else{
            throw new RuntimeException("咦！奇怪，view怎么会是null的呢？？？？");
        }
    }

    public ImageView getView(){
        if(civ !=null){
            return civ ;
        }else{
            throw new RuntimeException("咦！奇怪，civ怎么会是null的呢？？？？");
        }
    }



}
