package com.chen.firstdemo.flight_chess;

public interface ChessService{
    /**
     * 全部重置
     */
    void reset();

    /**
     * 设置飞机的当前位置
     * @param plane
     * @param position 当前位置，（如果停在停机坪，传-1）
     */
    void set(Plane plane ,int position);

    /**
     * 飞机预热
     * @param planes
     */
    void preheat(Plane[] planes);

    /**
     * 起飞，从停机坪飞到cell
     * @param plane
     */
    void flyOff(Plane plane ,FlightChessView.ChessActionListener listener);

    /**
     * 飞行
     * @param plane
     * @param positions 目的地
     */
    void fly(Plane plane ,int[] positions ,FlightChessView.ChessActionListener listener);


    /**
     * 坠机
     * @param plane
     */
    void crash(Plane plane ,FlightChessView.ChessActionListener listener );

    /**
     * 叠机
     * @param planes int[camp][apron]
     */
    void fold(Plane[] planes);

    /**
     * 飞机胜利
     * @param plane
     */
    void win(Plane plane , FlightChessView.ChessActionListener listener);

}