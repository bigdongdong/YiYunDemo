package com.chen.firstdemo.banner_demo.pops;

import android.app.Activity;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chen.firstdemo.R;
import com.chen.firstdemo.dialog_demo.shadow_popwindow.ShadowPopupWindow;

public class ImagePop extends ShadowPopupWindow {

    ImageView iv ;
    public ImagePop(Activity context) {
        super(context);
    }

    @Override
    protected void onCreateView(View view) {
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        iv = view.findViewById(R.id.imageView) ;
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public ImageView getIV(){
        return iv ;
    }

    @Override
    protected Object getLayoutIdOrView() {
        return R.layout.pop_image;
    }

}
