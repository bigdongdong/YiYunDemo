package com.chen.firstdemo.flight_chess.ludo;

import java.util.Objects;

/**
 * crete by chenxiaodong on 2020/11/23
 * ludo游戏的棋子----飞机
 * 命令施加层，阵营和停机坪索引是1~4
 * 界面逻辑层，阵营和停机坪索引是0~3
 */
public class Plane {
    private int camp ; //阵营
    private int apron ; //停机坪

    public Plane(int camp, int apron) {
        this.camp = camp-1;
        this.apron = apron-1;
    }

    /**
     * for control
     * 用于命令施加层
     * @return 1~4
     */
    public int getCamp() {
        return camp+1;
    }

    public int getApron() {
        return apron+1;
    }

    /**
     * for {@link LudoView}
     * 用于界面逻辑层
     * @return  0~3
     */
    public int getCampIndex() {
        return camp;
    }

    public int getApronIndex() {
        return apron;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plane plane = (Plane) o;
        return camp == plane.camp &&
                apron == plane.apron;
    }
}
