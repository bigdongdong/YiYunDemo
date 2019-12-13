package com.chen.firstdemo.rank_imageview_demo;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chen.firstdemo.R;

/**
 * create by chenxiaodong on 2019/11/19
 *
 */
public class WeekStarRankImageView extends RelativeLayout {
    private final String TAG = "RankImageView-->TAG";
    private LayoutParams params ;

    private ImageView civ ;
    private View coverView ; //遮罩View，之所以用遮罩View 不用setForground是为了提高兼容性

    private Context context ;
    private int type ;

    public WeekStarRankImageView(Context context , int type) {
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
                coverView.setBackgroundResource(R.mipmap.week_star_rank_headwear_1);
                break;
            case 2:
                coverView.setBackgroundResource(R.mipmap.week_star_rank_headwear_2);
                break;
            case 3:
                coverView.setBackgroundResource(R.mipmap.week_star_rank_headwear_3);
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
        civWidth = (int) ((114f/152f) * width);
        params = new LayoutParams(civWidth , civWidth);

        params.addRule(RelativeLayout.CENTER_IN_PARENT);
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
