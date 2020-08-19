package com.example.otherdemos.mindMappingDemo.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.example.otherdemos.mindMappingDemo.customView.MM_CustomNodeView;
import com.example.otherdemos.mindMappingDemo.customView.MM_CustomTitleView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.otherdemos.mindMappingDemo.utils.DensityUtils.dp2px;


/**
 * Created by CXD on 2018/11/23.
 */
public class ViewControl {
    public int nodeWidth;//一个子节点的宽度
    public int vLineHeight;//节点上面的小竖线高度
    public int spaceStart ; //横线的时候 左边起点需要多空出来的,更改的时候 设为0 即可
    Context context ;

    public ViewControl(Context context ) {
        this.context = context ;
        this.nodeWidth = dp2px(context,30);
        this.vLineHeight =  dp2px(context,10);
        this.spaceStart = dp2px(context,5);
    }

    /*获取第三级节点
    * 返回一个view  带天线
    * */
    public LinearLayout getChildView_3(String[] strings,int secondWidth){
        if(strings == null || strings.length ==0 ){
            return  null ;
        }
        /*****************创建layout********************/
        LinearLayout layout = new LinearLayout(context); //LinearLayout 的宽高是动态变化的
        layout.setOrientation(LinearLayout.HORIZONTAL);

        /***************添加子节点*****************/
        for(int i=0;i<strings.length;i++){
            /*添加第三级nodeView*/
            MM_CustomNodeView nodeView  = new MM_CustomNodeView(context,null);
            nodeView.setText(strings[i]);
            layout.addView(nodeView);
            setMargins(nodeView,0,vLineHeight*2,0,0);
        }
        /**************画小竖线******************/
        layout.measure(0,0); //此时的layout已经add完了第三极节点nodeView
        int bitmapHeight =  layout.getMeasuredHeight();
        int bitmapWidth = layout.getMeasuredWidth();
        if(secondWidth > layout.getMeasuredWidth()){  //帽子和第三级 谁大取谁
            bitmapWidth = secondWidth;
        }
        /*创建bitmap*/
        Bitmap layoutBitmap = Bitmap.createBitmap(bitmapWidth,bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(layoutBitmap);
        /*画竖线*/
        int startX = ((bitmapWidth - nodeWidth*strings.length)/2)+nodeWidth/2 ; //第一根竖线位置，无论二级宽于三级，还是三级宽于二级
        for(int i=0;i<strings.length;i++){
            /*画竖线*/
            /*画竖线，根据bitmap长度和几个nodeView可以算出每个nodeView的位置*/

            canvas.drawPath(getLine(startX,vLineHeight,startX,vLineHeight*2),getPaint(1));
            startX = startX + nodeWidth;
        }
        /*画横线：
        * 注意：单个nodeView宽30dp,左右margin各2.5dp,textview本身宽25dp
        * 而小竖线高vLineHeight = 10dp, 在左起15dp处
        * 所以画横线时左右要多留5dp ,也就是spaceStart
        * */
        if(strings.length > 1){
            /*画横线*/
            /*画横线，要先测量出出发点和结束点*/
            int hLineStartX = ((bitmapWidth - nodeWidth*strings.length)/2)+nodeWidth/2 ; //横线的起点X
            int hLineEndX = hLineStartX+(strings.length-1)*nodeWidth ;
            canvas.drawPath(getLine(hLineStartX,vLineHeight,hLineEndX,vLineHeight),getPaint(1));
        }
        /*画天线*/
        canvas.drawPath(getLine(bitmapWidth/2,0,bitmapWidth/2,vLineHeight),getPaint(1));
        /*将bitmap设置给linearLayout做背景*/
        layout.setBackground(new BitmapDrawable(context.getResources(),layoutBitmap));
        return  layout ;
    }

    /*获取第二级节点集*/
    public LinearLayout getChildView_2(ArrayList<HashMap<String, Object>> list) {
        int hLineStartX = 0;
        int hLineEndViewSpace = 0;
        int allWidth = 0 ;
        int maxHeight = 0;
        Bitmap mainBitmap ;
        Canvas canvas = new Canvas();
        LinearLayout resultLayout = new LinearLayout(context);
        resultLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout secondLayout = null; //单个二级及子节点
        for(int i=0;i<list.size();i++){
            /*第三级内容*/
            String[] string = (String[]) list.get(i).get("message");
            if(string == null || string.length==0){
                /*没有三级内容*/
                MM_CustomTitleView secNodeView = new MM_CustomTitleView(context,null);
                secNodeView.setText(list.get(i).get("title").toString());
                secondLayout = new LinearLayout(context);
                secondLayout.setOrientation(LinearLayout.VERTICAL);
                secondLayout.addView(secNodeView);
                /*空出天线的位置*/
                setMargins(secNodeView,0,vLineHeight*2,0,0);

            }else{
                /*帽子*/
                MM_CustomTitleView secondView = new MM_CustomTitleView(context,null); //帽子View
                secondView.setText(list.get(i).get("title").toString());
                secondView.measure(0,0);
                /*第三级layout*/
                LinearLayout thirdView = getChildView_3(string,secondView.getMeasuredWidth()); //第三级View
                thirdView.setGravity(Gravity.CENTER_HORIZONTAL);
                /*对第三级内容 加一个二级帽子*/
                secondLayout = new LinearLayout(context);
                secondLayout.setOrientation(LinearLayout.VERTICAL);
                secondLayout.addView(secondView);
                /*空出天线的位置*/
                setMargins(secondView,0,vLineHeight*2,0,0);
                secondLayout.addView(thirdView);
            }

            /*加二级的天线*/
            secondLayout.measure(0,0);
            Bitmap layoutBitmap = Bitmap.createBitmap(secondLayout.getMeasuredWidth(),secondLayout.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            canvas.setBitmap(layoutBitmap);
            canvas.drawPath(getLine(secondLayout.getMeasuredWidth()/2,vLineHeight,secondLayout.getMeasuredWidth()/2,vLineHeight*2),getPaint(1));
            secondLayout.setBackground(new BitmapDrawable(context.getResources(),layoutBitmap));
            /*将上述创建好的2/3布局添加进主的layout*/
            resultLayout.addView(secondLayout);

            allWidth = secondLayout.getMeasuredWidth()+allWidth ; //总宽度
            if(i == 0 ){
                hLineStartX = secondLayout.getMeasuredWidth()/2 ; //起手减掉多少
            }
            if(i == list.size()-1){
                hLineEndViewSpace = secondLayout.getMeasuredWidth()/2 ;  //最终减掉多少
            }
            if(secondLayout.getMeasuredHeight()>maxHeight){
                maxHeight = secondLayout.getMeasuredHeight(); //判断最大高度
            }

        }
        /*画一个横线*/
        mainBitmap = Bitmap.createBitmap(allWidth,maxHeight, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(mainBitmap);
        canvas.drawPath(getLine(hLineStartX,vLineHeight,allWidth-hLineEndViewSpace,vLineHeight),getPaint(1));
        /*最后加一个天线*/
//        int startX = (allWidth-hLineEndViewSpace)/2+hLineStartX ;
        int startX = allWidth/2 ;
        canvas.drawPath(getLine(startX,0,startX,vLineHeight),getPaint(1));

        resultLayout.setBackground(new BitmapDrawable(mainBitmap));
        return resultLayout;
    }

    public LinearLayout getParentView(String title , ArrayList<HashMap<String, Object>> list){
        /*只需要加一个帽子就可以了*/
        LinearLayout childView = getChildView_2(list);
        MM_CustomTitleView titleView = new MM_CustomTitleView(context,null);
        titleView.setText(title);
        LinearLayout resultLayout = new LinearLayout(context);
        resultLayout.setOrientation(LinearLayout.VERTICAL);

        resultLayout.addView(titleView);
        resultLayout.addView(childView);


        return resultLayout ;
    }


    /*获取一条线路径*/
    public static Path getLine(int startX ,int startY ,int endX ,int endY){
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

    /*设置margin*/
    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}
