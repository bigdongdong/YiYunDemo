package com.chen.firstdemo.bottom_tab_demo;

import com.chen.firstdemo.R;

public enum TabImageEnum{
    TAB_1(R.mipmap.tab_main_hot,R.mipmap.tab_main_hot_on),
    TAB_2(R.mipmap.tab_main_find,R.mipmap.tab_main_find_on),
    TAB_4(R.mipmap.tab_main_message,R.mipmap.tab_main_message_on),
    TAB_5(R.mipmap.tab_main_my,R.mipmap.tab_main_my_on);

    public int offId ,onId;

    TabImageEnum(int offId , int onId){
        this.offId = offId ;
        this.onId = onId;
    }

}
