package com.example.otherdemos.freePaintDemo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;


import com.example.otherdemos.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.otherdemos.Utils.ScreenUtils.getScreenHeight;
import static com.example.otherdemos.Utils.ScreenUtils.getScreenWidth;


public class FreePaint_MainActivity extends AppCompatActivity {

    ImageView imageView ;
    Bitmap bitmap ;
    int lastX =0,lastY=0;
    Canvas canvas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*toolbar*/
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        /*titlebar*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.free_paint_activity_main);

        imageView = findViewById(R.id.imageView) ;
        bitmap = Bitmap.createBitmap(getScreenWidth(this),getScreenHeight(this), Bitmap.Config.ARGB_8888);
        canvas= new Canvas();
        canvas.setBitmap(bitmap);

        lastX =(int)((Math.random())*(getScreenWidth(FreePaint_MainActivity.this)));
        lastY =(int)((Math.random())*(getScreenWidth(FreePaint_MainActivity.this)));

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                int newX,newY;
                newX = (int)((Math.random())*(getScreenWidth(FreePaint_MainActivity.this)));
                newY = (int)((Math.random())*(getScreenHeight(FreePaint_MainActivity.this)));
                String color = getRandColorCode();
                canvas.drawPath(getPath(lastX,lastY,newX,newY),getPaint(color));

                lastX = newX ;
                lastY = newY ;
                imageView.setImageBitmap(bitmap);
//                bitmap = Bitmap.createBitmap(getScreenWidth(MainActivity.this),getScreenHeight(MainActivity.this),Bitmap.Config.ARGB_8888);
            }
        };

        timer.schedule(timerTask,0,200);


    }

    /*设置画笔*/
    Paint getPaint(String color){
        Paint paint ;
        paint= new Paint();
        paint.setColor(Color.parseColor(color));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND); //直角连接
        paint.setStrokeWidth(200);
        paint.setAntiAlias(true);
        return paint;
    }

    /*获取线条路径*/
    Path getPath(int startX, int startY , int endX, int endY){
        Path path = new Path();
        path.moveTo(startX,startY);
        path.lineTo(endX,endY);
        return path;
    }

    public static String getRandColorCode(){
        String r = "11",g = "11",b = "11";  //使初始化 为 111111
        Random random = new Random();
        while((r+g+b).equals("111111") ){  //避免随机出黑色的颜色
            r = Integer.toHexString(random.nextInt(256)).toUpperCase();
            g = Integer.toHexString(random.nextInt(256)).toUpperCase();
            b = Integer.toHexString(random.nextInt(256)).toUpperCase();

            r = r.length()==1 ? "0" + r : r ;
            g = g.length()==1 ? "0" + g : g ;
            b = b.length()==1 ? "0" + b : b ;
        }

        return "#"+r+g+b;
    }
}
