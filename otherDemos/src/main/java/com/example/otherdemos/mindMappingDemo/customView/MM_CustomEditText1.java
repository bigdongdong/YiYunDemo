package com.example.otherdemos.mindMappingDemo.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.otherdemos.R;
import com.example.otherdemos.mindMappingDemo.utils.DensityUtils;

import static com.example.otherdemos.mindMappingDemo.utils.DensityUtils.dp2px;


public class MM_CustomEditText1 extends LinearLayout {
    final int dp = 35 ;
    Context context ;
    EditText editText ;
    ImageView canvasIV,openIV ;
    Bitmap bitmap ;
    int bitmapH,bitmapW ;
    Canvas canvas ;
    Path vPath,hPath;

    public MM_CustomEditText1(Context context) {
        this(context,null);
        this.context= context;

        initView();

    }
    void initView(){
        LayoutInflater.from(context).inflate(R.layout.mm_custom_edittext1,this);
        editText= this.findViewById(R.id.edittext);
        canvasIV= this.findViewById(R.id.canvasIV);
        openIV= this.findViewById(R.id.openIV);

        bitmapW = bitmapH = dp2px(context,dp);
        bitmap = Bitmap.createBitmap(bitmapW,bitmapH,Bitmap.Config.ALPHA_8);
        canvas = new Canvas();
        canvas.setBitmap(bitmap);
        int end = (int)((double)(bitmapH*(0.7)));
        vPath = getLine(0,0,0,bitmapH);
        hPath = getLine(0,end,bitmapW,end);
        canvas.drawPath(vPath,getPaint(2));
        canvas.drawPath(hPath,getPaint(2));

        canvasIV.setBackground(new BitmapDrawable(bitmap));

    }

    public MM_CustomEditText1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /*获取一条线路径*/
    public static Path getLine(int startX , int startY , int endX , int endY){
        Path path = new Path();
        path.moveTo(startX,startY);
        path.lineTo(endX,endY);
        return path;
    }

    /*设置画笔*/
    public Paint getPaint(int width){
        Paint paint ;
        paint= new Paint();
        paint.setColor(Color.parseColor("#111111"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setStrokeJoin(Paint.Join.ROUND); //直角连接
        paint.setStrokeWidth(dp2px(context,width));
        paint.setAntiAlias(true);
        return paint;
    }

    public void setOpenButtonOnClickListener(OnClickListener listener){
        openIV.setOnClickListener(listener);
    }
}
