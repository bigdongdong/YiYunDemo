package com.chen.firstdemo.e_number.eNumber;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.shapes.Shape;

public class ENumberShape extends Shape {
    private int w,h;
    private int thickness; //刀片的厚度[默认:W*0.2f]
    private int interval; //刀片间距离[默认:thickness/10]
    private RazorBlade[] razorBlades ;

    public ENumberShape(int w) {
        this.w = w;
        thickness = (int) (w * 0.2f);
        h = w + (w-thickness) ;
        interval = thickness / 10 ;
    }

    /**
     * 返回刀片集
     * @return
     */
    public RazorBlade[] getRazorBlades(){
        if(razorBlades == null){
            computeRazorBlades();
        }
        return razorBlades ;
    }

    /**
     * 计算刀片集
     */
    private void computeRazorBlades(){
        if(razorBlades == null){
            /*TODO 计算刀片集*/

        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {

    }

}
