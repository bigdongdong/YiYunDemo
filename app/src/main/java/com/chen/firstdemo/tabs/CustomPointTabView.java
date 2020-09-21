package com.chen.firstdemo.tabs;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.chen.firstdemo.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create by chenxiaodong on 2019/12/16
 *
 * builder模式建造的tabview，与viewpager关系紧密
 * 不能在xml中直接引用，建议使用一个预留的LinearLayout占位置，之后addView添加
 *
 * 可以设置: tab的个数 , tab宽度 ,右上角point的颜色大小
 *         tab文字的颜色（选中与未选中）、文字的大小（选中与未选中）
 *
 * 注意：space优先级比tabWidth高，如果二者都不设置，则使用屏幕宽度票平分
 */
public class CustomPointTabView extends HorizontalScrollView {

    private final String TAG = "CustomPointTabView-->TAG";

    private LinearLayout parentLL ;
    private List<TabView> tabViews ;
    private OnTabSelectedListener listener ;
    private TabView lastTabView ;

    private Context context ;
    private Builder builder ;

    private CustomPointTabView(Context context,final Builder builder) {
        super(context);
        this.context = context ;
        this.builder = builder ;

        this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        this.setHorizontalScrollBarEnabled(false);

        parentLL = new LinearLayout(context);
        parentLL.setLayoutParams(new LinearLayout.LayoutParams(-1,-1));
        this.addView(parentLL);

        parentLL.setGravity(Gravity.CENTER_VERTICAL);
        parentLL.setOrientation(LinearLayout.HORIZONTAL);

        tabViews = new ArrayList<>();
        for(int i = 0 ; i<builder.tabs.size();i++){
            tabViews.add(new TabView(context,builder,i));
        }

        parentLL.removeAllViews();
        for(TabView tabView : tabViews){
            parentLL.addView(tabView);
        }

        /*tab点击监听*/
        listener = new OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                /*对viewpager做出操作*/
                if(builder.viewPager!=null){
                    builder.viewPager.setCurrentItem(position);
                }
            }
        };

        builder.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public ViewPager getViewPager(){
        return builder.viewPager ;
    }

    /**
     * 用于手动设置当前选中tab，不联动viewpager
     * 如果初始化时position不为0，需要在activity的onStart()中调用，
     * 避免CustomPointTabView.this尚未绘制完成
     * @param position
     */
    public void setSelected(int position){
        if(tabViews.get(position)!=null){
            TabView thisView = tabViews.get(position) ;
            if(lastTabView == null){
                thisView.setSelected(true);
                lastTabView = thisView ;
                return;
            }else if(lastTabView != thisView){
                thisView.setSelected(true);
                lastTabView.setSelected(false);
                lastTabView = thisView;
            }
        }else{
            throw new RuntimeException("tabViews.get(position)为空，请检查viewpager内fragment与tabs数量是否相等！！！");
        }

        //滑动到对应的position，并在第二个固定
        if(position > 0){
            CustomPointTabView.this.smoothScrollTo(tabViews.get(position -1 ).getLeft(),0);//除第一个外，固定在第二个
        }else{
            CustomPointTabView.this.smoothScrollTo(0,0);//第一个则滑到最左
        }
    }


    private int drawCount = 0;
    /*添加view绘制完成的监听*/
    public void setViewFinishedDrawListener(final OnViewDrawListener listener){
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //只有前三次是scrollView的初始化绘制
                //监听三次，因为单一次监听无效
                if(drawCount < 3){
                    listener.onViewFinishedDrawListener();
                    drawCount++;
                }else{
                    //三次之后，移除监听
                    CustomPointTabView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }

    public interface OnViewDrawListener{
        void onViewFinishedDrawListener();
    }

    /**
     * builder构造类
     */
    public static class Builder{
        private Context context ;
        private int textUnSelectedSize ,textSelectedSize;
        private int textUnSelectedColor , textSelectedColor ;
        private int pointColor;
        private int pointWidth;
        private ViewPager viewPager ;
        private List<String> tabs ;
        private int space ;
        private int tabWidth;
        private boolean isUnSelectedBold = false , isSelectedBold = true ;

//        private HashMap specialTabMap ;
//        public Builder addSpecialTabWidth(int position , int tabWidth){
//            if(specialTabMap == null){
//                specialTabMap = new HashMap() ;
//            }
//            specialTabMap.put("position",position);
//            specialTabMap.put("tabWidth",tabWidth);
//            return this ;
//        }

        public Builder isSelectedBold(boolean isSelectedBold){
            this.isSelectedBold = isSelectedBold ;
            return this;
        }
        public Builder isUnSelectedBold(boolean isUnSelectedBold){
            this.isUnSelectedBold = isUnSelectedBold ;
            return this;
        }
        public Builder pointWidth(int pointWidth){
            this.pointWidth = pointWidth ;
            return this;
        }
        public Builder pointColor(int pointColor){
            this.pointColor = pointColor ;
            return this;
        }
        public Builder space(int space){
            this.space = space ;
            return this;
        }
        public Builder context(Context context){
            this.context = context ;
            return this;
        }

        public Builder tabWidth(int tabWidth){
            this.tabWidth = tabWidth ;
            return this;
        }
        public Builder textUnSelectedSize(int textUnSelectedSize){
            this.textUnSelectedSize = textUnSelectedSize ;
            return this;
        }
        public Builder textSelectedSize(int textSelectedSize){
            this.textSelectedSize = textSelectedSize ;
            return this;
        }

        public Builder textUnSelectedColor(int textUnSelectedColor){
            this.textUnSelectedColor = textUnSelectedColor ;
            return this;
        }
        public Builder textSelectedColor(int textSelectedColor){
            this.textSelectedColor = textSelectedColor ;
            return this;
        }

       public Builder viewPager(ViewPager viewPager){
            this.viewPager = viewPager ;
            return this;
       }

       public Builder tabs(List<String> tabs){
            this.tabs = tabs ;
            return  this;
       }

       public CustomPointTabView build(){
           return new CustomPointTabView(context,this);
       }
    }

    /**
     * 每一个子TabView
     */
    class TabView extends RelativeLayout {
        private GradientDrawable gd ;
        private TextPaint paint ;//用来加粗的textpaint

        private Builder builder ;
        private RelativeLayout contentRL ;
        private TextView textView ;
        private View pointView ;
        private LayoutParams params ;
        private int position ;//当前位置

        public TabView(Context context, Builder builder ,final int position) {
            super(context);
            this.builder = builder ;
            this.position = position ;

            /*设置父布局的尺寸*/
            if(builder.space != 0){
                //设置了tab之间的边距，优先级比tabWidth高
                params = new LayoutParams(-2,-2); //当设置space时，父布局宽度与contentRL一样
                this.setPadding(builder.space/2 , 0 , builder.space/2,0);
            }else if(builder.tabWidth != 0 ){//当设置tabWidth时，父布局宽度固定为tabWidth，contentRL宽度为wrap_content
                params = new LayoutParams(builder.tabWidth, -2);
            }else{//都没设置，则为屏幕width均分
                params = new LayoutParams(ScreenUtil.getScreenWidth(context) /builder.tabs.size(), -2);
            }
            this.setLayoutParams(params);

            /*内容布局，宽度暂定wrap_content，之后动态更新宽度*/
            int textWidth = sp2px(builder.textSelectedSize) * builder.tabs.get(position).length();
            contentRL = new RelativeLayout(context);
            if(builder.space != 0 ){
                params = new LayoutParams(-2,-2);
            }else{
                params = new LayoutParams(textWidth , -2);
            }
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            contentRL.setLayoutParams(params);
            this.addView(contentRL);

            /*制造view*/
            generateTabView(context);

            /*初始化设置*/
            this.setSelected(false);
            this.setClickable(true);
            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        listener.onTabSelected(position);
                    }
                }
            });
        }

        private void generateTabView(Context context){
            /*添加pointView*/
            pointView = new View(context);
            params = new LayoutParams(builder.pointWidth,builder.pointWidth);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            pointView.setLayoutParams(params);
            gd = new GradientDrawable();
            gd.setColor(builder.pointColor);
            gd.setCornerRadius(builder.pointWidth/2);
            pointView.setBackground(gd);
            contentRL.addView(pointView);

            /*添加一个textView*/
            textView = new TextView(context);
            params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            textView.setLayoutParams(params);
            textView.setText(builder.tabs.get(position));
            textView.setMaxLines(1);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(builder.textUnSelectedSize);//默认单位就是sp
            paint = textView.getPaint();
            paint.setFakeBoldText(builder.isUnSelectedBold);
            contentRL.addView(textView);

            //更新contentRL宽度
            updateContentRLWidth();

        }

        //当设置space时生效，用于设置contentRL的宽度
        private void updateContentRLWidth(){
            if(builder.space != 0){
                int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                textView.measure(spec,spec);
                int tvWidth = textView.getMeasuredWidth();
                contentRL.getLayoutParams().width = tvWidth;
            }
        }

        public void setSelected(boolean isSelected){
            if(isSelected == true){
                pointView.setVisibility(VISIBLE);

                textView.setTextColor(builder.textSelectedColor);
                textView.setTextSize(builder.textSelectedSize);
                paint = textView.getPaint();
                paint.setFakeBoldText(builder.isSelectedBold);

            }else{
                pointView.setVisibility(GONE);

                textView.setTextColor(builder.textUnSelectedColor);
                textView.setTextSize(builder.textUnSelectedSize);
                paint = textView.getPaint();
                paint.setFakeBoldText(builder.isUnSelectedBold);
            }

            updateContentRLWidth();
        }

    }

    private interface OnTabSelectedListener{
        void onTabSelected(int position);
    }

    public  int sp2px(float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
