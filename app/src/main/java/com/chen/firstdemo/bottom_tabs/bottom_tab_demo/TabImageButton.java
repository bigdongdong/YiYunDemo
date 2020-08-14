package com.chen.firstdemo.bottom_tabs.bottom_tab_demo;

import android.content.Context;
import android.util.AttributeSet;

public class TabImageButton extends android.support.v7.widget.AppCompatImageButton {
    private TabImageEnum mEnum ;
    private boolean sSelected = false;

    public TabImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setEnum(TabImageEnum mEnum){
        this.mEnum = mEnum ;
        setSelected(false);
    }

    public void setSelected(boolean isSelected){
        if(isSelected){
            this.setImageResource(mEnum.onId);
            sSelected = true;
        }else{
            this.setImageResource(mEnum.offId);
            sSelected = false;
        }
    }

    public boolean isSelected(){
        return sSelected;
    }


}
