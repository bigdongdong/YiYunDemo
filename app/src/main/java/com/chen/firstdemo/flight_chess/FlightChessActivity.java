package com.chen.firstdemo.flight_chess;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;
import com.chen.firstdemo.utils.ScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FlightChessActivity extends BaseActivity {

    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.fcv)
    FlightChessView fcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_chess);
        ButterKnife.bind(this);

        final int w = ScreenUtil.getScreenWidth(context);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(w, w);
        rl.setLayoutParams(p);

        fcv.preheat(new Plane[]{new Plane(0,0),
                new Plane(0,1),
                new Plane(0,2),
                new Plane(0,3)});

        final int[] all = new int[]{56,
                57,
                58,
                59,
                60,
                16,
                13,
                10,
                7,
                4,
                1,
                2,
                3,
                6,
                9,
                12,
                15,
                18,
                19,
                20,
                21,
                22,
                23,
                24,
                30,
                36,
                35,
                34,
                33,
                32,
                31,
                39,
                42,
                45,
                48,
                51,
                54,
                53,
                52,
                49,
                46,
                43,
                40,
                37,
                72,
                71,
                70,
                69,
                68,
                67,
                61,
                62,
                63,
                64,
                65,
                66};
        fcv.setChessClickListener(new FlightChessView.ChessClickListener() {
            @Override
            public void onClick(Plane plane) {
                if(plane.getCamp() == 0 && plane.getApron() == 0){
                    fcv.flyOff(plane, new FlightChessView.ChessActionListener() {
                        @Override
                        public void onFinish(Plane plane) {
                            fcv.fly(new Plane(0,0),all, new FlightChessView.ChessActionListener() {
                                @Override
                                public void onFinish(Plane plane) {

                                }
                            });
                        }
                    });

                }
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}