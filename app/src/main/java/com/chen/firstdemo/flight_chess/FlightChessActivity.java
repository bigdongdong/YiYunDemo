package com.chen.firstdemo.flight_chess;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;
import com.chen.firstdemo.flight_chess.ludo.CircleCountDownView;
import com.chen.firstdemo.flight_chess.ludo.DicePop;
import com.chen.firstdemo.flight_chess.ludo.LudoView;
import com.chen.firstdemo.flight_chess.ludo.Plane;
import com.chen.firstdemo.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FlightChessActivity extends BaseActivity {

    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.lv)
    LudoView lv;
    @BindView(R.id.resetButn)
    Button resetButn;
    @BindView(R.id.qifeiBtn1)
    Button qifeiBtn1;
    @BindView(R.id.qifeiBtn2)
    Button qifeiBtn2;
    @BindView(R.id.flyFrom55To16Btn)
    Button flyFrom55To16Btn;
    @BindView(R.id.setTo66Btn)
    Button setTo66Btn;
    @BindView(R.id.flyFrom66ToWinBtn)
    Button flyFrom66ToWinBtn;
    @BindView(R.id.crashBtn)
    Button crashBtn;
    @BindView(R.id.preheatBtn1)
    Button preheatBtn1;
    @BindView(R.id.countDownBtn)
    Button countDownBtn;
    @BindView(R.id.ccdv)
    CircleCountDownView ccdv;
    @BindView(R.id.foldAllBtn)
    Button foldAllBtn;
    @BindView(R.id.preheatBtn1All)
    Button preheatBtn1All;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_chess);
        ButterKnife.bind(this);

        final int w = ScreenUtil.getScreenWidth(context);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(w, w);
        rl.setLayoutParams(p);

//        lv.reset(new int[]{1, 2,3,4});

//        final int[] all = new int[]{56, 57, 58, 59, 60, 16, 13, 10, 7, 4, 1, 2, 3, 6, 9, 12, 15, 18, 19, 20, 21, 22, 23, 24, 30, 36, 35, 34, 33, 32, 31, 39, 42, 45, 48, 51, 54, 53, 52, 49, 46, 43, 40, 37, 72, 71, 70, 69, 68, 67, 61, 62, 63, 64, 65, 66};
        lv.setChessClickListener(new LudoView.ChessClickListener() {
            @Override
            public boolean onClick(Plane plane, View chess) {
                List<Integer> list = new ArrayList<>();
                list.add(6);
                list.add(6);
                list.add(2);

                new DicePop(context, list, new DicePop.OnDiceListener() {
                    @Override
                    public void onDice(int dice) {
                        toast(String.valueOf(dice));
                    }
                }).show(chess);
                return false ;
            }
        });

        countDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ccdv.countDown(5000, new CircleCountDownView.OnTimerListener() {
                    @Override
                    public void overTime() {
                        toast("5s倒计时完成");
                    }
                });

//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        ccdv.cancel();
//                    }
//                }, 3500);
            }
        });

        lv.setLudoLayoutListener(new LudoView.LudoLayoutListener() {
            @Override
            public void layoutEnd() {
                lv.reset(new int[]{1});
//                lv.set(new Plane(1,1),-1);
                lv.set(new Plane(1,2),-1);
                lv.set(new Plane(1,3),-1);
                lv.set(new Plane(1,4),53);
//                lv.preheat(new Plane[]{new Plane(1,1),new Plane(1,2),
//                        new Plane(1,3),new Plane(1,4)});
//                lv.preheat(new Plane[]{new Plane(4,1),new Plane(4,2),
//                        new Plane(4,3),new Plane(4,4)});
            }
        });
    }

    @OnClick({R.id.preheatBtn1All,R.id.foldAllBtn, R.id.preheatBtn1, R.id.crashBtn, R.id.resetButn, R.id.qifeiBtn1, R.id.qifeiBtn2, R.id.flyFrom55To16Btn, R.id.setTo66Btn, R.id.flyFrom66ToWinBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.resetButn:
                lv.reset(new int[]{1, 2, 3, 4});
                break;
            case R.id.qifeiBtn1:
                lv.flyOff(new Plane(1, 1), new LudoView.ChessActionListener() {
                    @Override
                    public void onFinish(Plane plane) {
                        toast("1-1起飞完成");
                    }
                });
                break;
            case R.id.qifeiBtn2:
                lv.flyOff(new Plane(1, 2), new LudoView.ChessActionListener() {
                    @Override
                    public void onFinish(Plane plane) {
                        toast("1-2起飞完成，并叠加");
                        lv.fold(new Plane[]{new Plane(1, 1), new Plane(1, 2)}, 56);
                    }
                });
                break;
            case R.id.flyFrom55To16Btn:
                lv.fly(new Plane(1, 1), new int[]{56, 57, 58, 59, 60, 16}, new LudoView.ChessActionListener() {
                    @Override
                    public void onFinish(Plane plane) {
                        toast("1-1从55-16完成");
                    }
                });
                break;
            case R.id.setTo66Btn:
                lv.set(new Plane(1, 1), 64);
                break;
            case R.id.flyFrom66ToWinBtn:
                lv.win(new Plane(1, 1), new int[]{64,65,66},new LudoView.ChessActionListener() {
                    @Override
                    public void onFinish(Plane plane) {
                        toast("1-1到达终点");
                    }
                });
                break;
            case R.id.crashBtn:
                lv.crash(new Plane[]{new Plane(1, 1), new Plane(1, 2)}, new LudoView.ChessActionListener() {
                    @Override
                    public void onFinish(Plane plane) {
                        toast("1-1&1-2坠机");
                    }
                });
                break;
            case R.id.preheatBtn1All:
                lv.preheat(new Plane[]{new Plane(1, 1),
                        new Plane(1, 2),
                        new Plane(1, 3),
                        new Plane(1, 4)});
                break;
            case R.id.preheatBtn1:
                lv.preheat(new Plane[]{new Plane(1, 1)});
                break;
            case R.id.foldAllBtn:
                lv.fold(new Plane[]{new Plane(4, 4),
                        new Plane(2, 2),
                        new Plane(4, 2),
                        new Plane(2, 1),
                        new Plane(3, 2)}, 56);
                break;
        }
    }
}