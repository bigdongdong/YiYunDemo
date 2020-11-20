package com.chen.firstdemo.flight_chess;


public class Plane {
    private int camp ; //阵营0~3
    private int apron ; //停机坪0~3

    public Plane(int camp, int apron) {
        this.camp = camp;
        this.apron = apron;
    }

    /*0~3*/
    public int getCamp() {
        return camp;
    }

    /*0~3*/
    public int getApron() {
        return apron;
    }
}
