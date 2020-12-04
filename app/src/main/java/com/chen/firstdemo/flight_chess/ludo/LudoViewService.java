package com.chen.firstdemo.flight_chess.ludo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * create by chenxiaodong on 2020/11/23
 * ludo游戏中棋子（飞机）的操作Service
 */
public interface LudoViewService {
    /**
     * 全部重置
     * 所有飞机回到停机坪
     * @camps 1~4 开启的阵营
     */
    void reset(@NotNull int[] camps);

    /**
     * 设置飞机的当前位置
     * @param plane
     * @param position 当前位置
     */
    void set(Plane plane, int position);

    /**
     * 飞机预热
     * 如果当前回合没有棋子可预热，也需要传null
     * 内部通过维护一个数组来确保飞机的点击事件准确
     * @param planes
     */
    void preheat(@Nullable Plane[] planes);


    /**
     * 起飞，从停机坪飞到cell
     * @param plane
     */
    void flyOff(Plane plane, LudoView.ChessActionListener listener);

    /**
     * 飞行
     * @param plane
     * @param positions 目的地
     */
    void fly(Plane plane, @NotNull int[] positions, LudoView.ChessActionListener listener);


    /**
     * 坠机
     * @param planes
     */
    void crash(@NotNull Plane[] planes, LudoView.ChessActionListener listener);

    /**
     * 叠机
     * @param planes
     * @param position 当前位置
     */
    void fold(@NotNull Plane[] planes, int position);

    /**
     * 飞机胜利
     * @param plane
     * @param positions 飞机到达终点前的剩余路径坐标集合
     */
    void win(Plane plane, @NotNull int[] positions, LudoView.ChessActionListener listener);

}