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
import android.widget.TextView;


import com.chen.firstdemo.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * create by chenxiaodong on 2019/11/12
 *
 * builder模式建造的tabview，与viewpager关系紧密
 * 不能在xml中直接引用，建议使用一个预留的LinearLayout占位置，之后addView添加
 *
 * 可以设置: tab的个数
 *         底部line的颜色、宽高、圆角、距离文字的marginTop
 *         tab文字的颜色（选中与未选中）、文字的大小（选中与未选中）
 *
 * 注意：目前tab默认加粗，初始化时需要手动调用setSelected来设置初始tab
 *      layout方向强制从左往右
 */
public class CustomLineTabView extends HorizontalScrollView {

    private Builder builder ;

    private LinearLayout parentLL ;
    private List<TabView> tabViews ;
    private OnTabSelectedListener listener ;
    private TabView lastTabView ;

    private CustomLineTabView(Context context,final Builder builder) {
        super(context);
        this.builder = builder ;


        this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        this.setHorizontalScrollBarEnabled(false);

        parentLL = new LinearLayout(context);
        parentLL.setLayoutParams(new LinearLayout.LayoutParams(-1,-1));
        parentLL.setLayoutDirection(View.LAYOUT_DIRECTION_LTR); //设置布局方向
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

    public List<TabView> getTabViews(){
        return tabViews ;
    }

    /**
     * 用于手动设置当前选中tab
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
            CustomLineTabView.this.smoothScrollTo(tabViews.get(position -1 ).getLeft(),0);//除第一个外，固定在第二个
        }else{
            CustomLineTabView.this.smoothScrollTo(0,0);//第一个则滑到最左
        }
    }

    /*添加view绘制完成的监听*/
    public void setViewFinishedDrawListener(final CustomPointTabView.OnViewDrawListener listener){
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                listener.onViewFinishedDrawListener();
                CustomLineTabView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
    /**
     * builder构造类
     */
    public static class Builder{
        private int textSize ,textSelectedSize;
        private int textUnSelectedColor , textSelectedColor ;
        private int lineColor;
        private int lineConnerRadius;
        private int lineMarginTop;
        private ViewPager viewPager ;
        private List<String> tabs ;
        private int count ;
        private int lineWidth , lineHeight;
        private Context context ;
        private int tabWidth;
        private int space ;
        private GradientDrawable lineBackground ;

        public CustomLineTabView.Builder space(int space){
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
        public Builder textSize(int textSize){
            this.textSize = textSize ;
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

       public Builder lineColor(int lineColor){
            this.lineColor = lineColor ;
            return this;
       }

       public Builder lineConnerRadius(int lineConnerRadius){
            this.lineConnerRadius = lineConnerRadius ;
            return this;
       }

       public Builder lineMarginTop(int lineMarginTop){
            this.lineMarginTop = lineMarginTop ;
            return  this ;
       }

       public Builder viewPager(ViewPager viewPager){
            this.viewPager = viewPager ;
            return this;
       }

       public Builder tabs(List<String> tabs){
            this.tabs = tabs ;
            return  this;
       }

       public Builder count(int count){
            this.count = count;
            return this;
       }
       public Builder lineWidth(int lineWidth){
            this.lineWidth = lineWidth;
            return this;
       }

       public Builder lineHeight(int lineHeight){
            this.lineHeight = lineHeight;
            return this;
       }
       public Builder lineBackground(GradientDrawable lineBackground){
            this.lineBackground = lineBackground;
            return this;
       }

       public CustomLineTabView build(){
           return new CustomLineTabView(context,this);
       }
    }

    /**
     * 每一个子TabView
     */
    public class TabView extends LinearLayout{
        private TextPaint paint ;//用来加粗的textpaint

        private Builder builder ;
        public TextView textView ;
        public View lineView ;
        private LayoutParams params ;
        private int position ;//当前位置

        private GradientDrawable gd ;
        public TabView(Context context, Builder builder ,final int position) {
            super(context);
            this.builder = builder ;
            this.position = position ;

            /*制造view*/
            generateTabView(context);

            this.setSelected(false);
            this.setClickable(true);
            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        listener.onTabSelected(position);
                    }

                    /*用于向外部通知*/
                    if(tabClickListener != null){
                        tabClickListener.onTabClick(position);
                    }
                }
            });
        }

        private void generateTabView(Context context){
            /*设置父布局的尺寸*/
            if(builder.space != 0){
                //设置了tab之间的边距，优先级比tabWidth高
                params = new LayoutParams(-2,-2); //当设置space时，父布局宽度与contentRL一样
                this.setPadding(builder.space/2 , 0 , builder.space/2,0);
            }else if(builder.tabWidth != 0 ){//当设置tabWidth时，父布局宽度固定为tabWidth，contentRL宽度为wrap_content
                params = new LayoutParams(builder.tabWidth, -2);
            }else{//都没设置，则为屏幕width均分
                params = new LayoutParams(ScreenUtil.getScreenWidth(context)/builder.tabs.size(), -2);
            }

            this.setLayoutParams(params);
            this.setGravity(Gravity.CENTER);
            this.setOrientation(LinearLayout.VERTICAL);
            /*添加一个textView*/
            textView = new TextView(context);
            params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(params);
            textView.setText(builder.tabs.get(position));
            textView.setMaxLines(1);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(builder.textSize);//默认单位就是sp
            paint = textView.getPaint();
            paint.setFakeBoldText(true);//默认都是加粗的
            this.addView(textView);

            /*添加一个lineView*/
            lineView = new View(context);
            params = new LayoutParams(builder.lineWidth,builder.lineHeight);
            params.setMargins(0,builder.lineMarginTop,0,0);
            lineView.setLayoutParams(params);
            if(builder.lineBackground != null){
                gd = builder.lineBackground ;
            }else{
                gd = new GradientDrawable();
                gd.setColor(builder.lineColor);
                gd.setCornerRadius(builder.lineConnerRadius);
            }
            lineView.setBackground(gd);
            this.addView(lineView);

        }

        public void setSelected(boolean isSelected){
            if(isSelected == true){
                lineView.setVisibility(VISIBLE);
                textView.setTextColor(builder.textSelectedColor);
                if(builder.textSelectedSize!=0){
                    //选中时的文字尺寸变化
                    textView.setTextSize(builder.textSelectedSize);
                }
            }else{
                lineView.setVisibility(INVISIBLE);
                textView.setTextColor(builder.textUnSelectedColor);
                if(builder.textSize!=0){
                   //未选中时，将文字尺寸变回来
                   textView.setTextSize(builder.textSize);
                }
            }
        }

    }

    private interface OnTabSelectedListener{
        void onTabSelected(int position);
    }

    private  OnTabClickListener tabClickListener ;
    public void setOnTabClickListener(OnTabClickListener l){
        this.tabClickListener = l ;

    }

    /*通知外部使用*/
    public interface OnTabClickListener{
        void onTabClick(int position);
    }
}
