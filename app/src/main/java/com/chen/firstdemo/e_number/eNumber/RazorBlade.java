package com.chen.firstdemo.e_number.eNumber;

import android.graphics.Point;
import android.graphics.Rect;

/**
 * 剃须刀刀片[菱形块]
 */
public class RazorBlade {

    public RazorBlade(Rect rect) {
        int w = rect.width() ;
        int h = rect.height() ;

        EEight eEight = new EEight();
        points = w > h ? convertPointsByHorizontalRhombus(w,h) : convertPointsByVertitalRhombus(w,h);
    }

    /**
     * 计算水平菱形点集合
     * @return
     */
    private Point[] convertPointsByHorizontalRhombus(int w , int h){
        Point[] points = new Point[6];

        return null ;
    }

    /**
     * 计算垂直菱形点集合
     * @return
     */
    private Point[] convertPointsByVertitalRhombus(int w , int h){
        Point[] points = new Point[6];

        return null ;
    }


    /**
     * 电子数字八
     * 内部存储7个菱形{@link RazorBlade}
     */
    private class EEight {
        private Rect[] rects ;

        /**
         *
         * @param w
         * @param h
         * @param thickness  刀片厚度
         */
        public EEight(int w , int h , int thickness) {
            Rect rect ;
            rects = new Rect[7];
            final int wl = w-thickness ; //横菱形的长
            final int hl = (h-thickness)/2 ; //竖菱形的长


            /*0*/
            rect = new Rect(thickness/2,0,thickness/2+wl,thickness);
            rects[0] = rect ;
            /*1*/
            rect = new Rect(0,thickness/2,thickness,thickness/2+hl);
            rects[1] = rect ;
            /*2*/
            rect = new Rect(w-thickness,thickness/2,w,thickness/2+hl);
            rects[2] = rect ;
            /*3*/
            rect = new Rect(thickness/2,hl,w-thickness/2,hl+thickness);
            rects[3] = rect ;
            /*4*/
            rect = new Rect(0,h/2,thickness,h-thickness/2);
            rects[4] = rect ;
            /*5*/
            rect = new Rect(w-thickness,h/2,w,h-thickness/2);
            rects[5] = rect ;
            /*6*/
            rect = new Rect(thickness/2,h-thickness,w-thickness/2,h);
            rects[6] = rect ;


            rhombuses = new RazorBlade[7];
            for (int i = 0; i < 7; i++) {
                rhombuses[i] = generateRhombusByRect(rects[i],space);
            }
        }

        public
        private RazorBlade generateRhombusByRect(Rect rect , int space){
            Rect temp = new Rect(rect);
            temp.inset(space/2,space/2);
            return new RazorBlade(temp);
        }

        public Rect[] getRects(){
            return rects ;
        }
    }
}