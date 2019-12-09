package com.chen.firstdemo.bottom_tab_demo_2;

import android.app.Activity;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chen.firstdemo.bottom_tab_demo.TabImageButton;
import com.chen.firstdemo.bottom_tab_demo.TabImageEnum;


public abstract class BottomTabView2 extends LinearLayout {

    private Activity context ;

    TabImageButton imageButton1 = null;
    TabImageButton imageButton2 = null;
    TabImageButton imageButton4 = null;
    TabImageButton imageButton5 = null;
    TabImageButton lastImageButton = null ;

    OnClickListener listener = new OnClickListener(){
        @Override
        public void onClick(View view) {
            TabImageButton imageButton = (TabImageButton) view;

            if(lastImageButton == null){
                lastImageButton = imageButton ;
            }

            if(lastImageButton == imageButton){
                return;
            }

            if(!imageButton.isSelected()){
                imageButton.setSelected(true);
                lastImageButton.setSelected(false);
                lastImageButton = imageButton;

                if(imageButton == imageButton1){
                    onTabSelectedListener(1);
                }
                if(imageButton == imageButton2){
                    onTabSelectedListener(2);
                }
                if(imageButton == imageButton4){
                    onTabSelectedListener(4);
                }
                if(imageButton == imageButton5){
                    onTabSelectedListener(5);
                }
            }

        }
    };

    protected abstract void onTabSelectedListener(int position);

    int height ;
    public BottomTabView2(Activity context , int height) {
        super(context);
        this.context = context;
        this.height = height;

        initializeView();
    }

    private void initializeView() {
        this.setOrientation(HORIZONTAL);

        imageButton1 = generateImageButton();
        imageButton2 = generateImageButton();
        imageButton4 = generateImageButton();
        imageButton5 = generateImageButton();

        imageButton1.setEnum(TabImageEnum.TAB_1);
        imageButton2.setEnum(TabImageEnum.TAB_2);
        imageButton4.setEnum(TabImageEnum.TAB_4);
        imageButton5.setEnum(TabImageEnum.TAB_5);

        imageButton1.setOnClickListener(listener);
        imageButton2.setOnClickListener(listener);
        imageButton4.setOnClickListener(listener);
        imageButton5.setOnClickListener(listener);

        /*初始设置，选中第二个*/
        imageButton2.setSelected(true);
        lastImageButton = imageButton2;

        this.removeAllViews();
        this.addView(imageButton1);
        this.addView(imageButton2);
        this.addView(generateImageButton(Color.parseColor("#00000000"))); //中心设置一个空白的
        this.addView(imageButton4);
        this.addView(imageButton5);
    }


    /**
     * 生成普通的tabView
     * @return
     */
    private TabImageButton generateImageButton(){
        return generateImageButton(Color.parseColor("#00000000"));
    }

    private TabImageButton generateImageButton(int color){
        TabImageButton imageButton = new TabImageButton(context,null);
        int imageButtonWidth = getScreenWidth()/5;
        imageButton.setLayoutParams(new LayoutParams(imageButtonWidth,height));
        imageButton.setBackgroundColor(color);
        imageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageButton.setPadding(75,35,75,35);
        return imageButton;
    }

    /**
     * 获取屏幕宽度 px
     * @return
     */
    public int getScreenWidth(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return  displaymetrics.widthPixels;
    }


}
