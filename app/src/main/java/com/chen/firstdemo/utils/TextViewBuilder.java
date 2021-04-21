package com.chen.firstdemo.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;

public class TextViewBuilder {
    private Context context ;
    private ViewGroup.LayoutParams params ;
    private int width ;
    private int height ;
    private int textSize ;
    private int textColor ;
    private boolean isBold = false;
    private boolean isItalic = false;
    private int gravity ;
    private int maxLines ;
    private TextUtils.TruncateAt ellipsize;


    public TextViewBuilder ellipsize(TextUtils.TruncateAt ellipsize){
        this.ellipsize = ellipsize ;
        return this ;
    }
    public TextViewBuilder context(Context context){
        this.context = context ;
        return this ;
    }
    public TextViewBuilder params(ViewGroup.LayoutParams params){
        this.params = params ;
        return this ;
    }
    public TextViewBuilder width(int width){
        this.width = width ;
        return this ;
    }
    public TextViewBuilder height(int height){
        this.height = height ;
        return this ;
    }
    public TextViewBuilder textSize(int textSize){
        this.textSize = textSize ;
        return this ;
    }
    public TextViewBuilder textColor(int textColor){
        this.textColor = textColor ;
        return this ;
    }
    public TextViewBuilder isBold(boolean isBold){
        this.isBold = isBold ;
        return this ;
    }
    public TextViewBuilder isItalic(boolean isItalic){
        this.isItalic = isItalic ;
        return this ;
    }
    public TextViewBuilder gravity(int gravity){
        this.gravity = gravity ;
        return this ;
    }
    public TextViewBuilder maxLines(int maxLines){
        this.maxLines = maxLines ;
        return this ;
    }
    public TextView build(){
        TextView tv = new TextView(context);
        ViewGroup.LayoutParams vp ;
        if(params == null){
            vp = new ViewGroup.LayoutParams(width,height);
        }else {
            vp = params ;
        }
        tv.setLayoutParams(vp);
        tv.setTextSize(textSize);
        tv.setTextColor(textColor);
        if(isBold && isItalic){
            tv.getPaint().setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
        }else if(isBold){
            tv.getPaint().setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }else if(isItalic){
            tv.getPaint().setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        }
        if(gravity != 0){
            tv.setGravity(gravity);
        }

        if(maxLines > 0){
            tv.setMaxLines(maxLines);
        }

        if(ellipsize != null){
            tv.setEllipsize(ellipsize);
        }

        return tv ;
    }
}
