package com.chen.firstdemo.floating_window_demo.floating_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.Image;
import android.widget.ImageView;

import com.chen.firstdemo.R;

public class CicleImageView extends android.support.v7.widget.AppCompatImageView {

    public CicleImageView(Context context) {
        super(context);
        this.setScaleType(ScaleType.FIT_XY);
    }

    @Override
    public void setImageBitmap(Bitmap bitmap){
        /**
         * 这里对bitmap进行操作
         */
        Bitmap bottomBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(),Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bottomBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        //清屏
        canvas.drawARGB(0, 0, 0, 0);
        // 画圆角
        canvas.drawOval(new RectF(rect), paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//SRC_IN取上层
        canvas.drawBitmap(bitmap,rect,rect,paint);

        super.setImageBitmap(bottomBitmap);

    }

}
