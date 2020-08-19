package com.example.otherdemos.paintDemo.myActivity;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.otherdemos.R;
import com.example.otherdemos.paintDemo.SQLite.MySQLiteHelper;
import com.example.otherdemos.paintDemo.SQLite.SQLiteManager;
import com.example.otherdemos.Utils.ScreenUtils;
import com.example.otherdemos.paintDemo.bean.ClassBean;
import com.example.otherdemos.paintDemo.customView.BaseView;
import com.example.otherdemos.paintDemo.customView.ClassView;
import com.example.otherdemos.paintDemo.customView.CustomPopItemView;
import com.example.otherdemos.paintDemo.customView.TopLeftButton;
import com.example.otherdemos.paintDemo.customView.amANDpmView;

import java.util.HashMap;
import java.util.List;

import static com.example.otherdemos.paintDemo.SQLite.SQLiteManager.helper;
import static com.example.otherdemos.paintDemo.SQLite.SQLiteManager.selectOneClasses;


/*
* 课表功能
* 取消了上下午的显示
* */

public class PaintDemoMainActivity extends AppCompatActivity {
    final int ADD_POP = 0;
    final int EDIT_POP = 1;

    Canvas canvas ;
    Bitmap bitmap;
    int screenWidth,screenHeight ;  //屏幕的宽高，titleBar也在内
    int statueHeight ;  //titleBar高度
    int bitmapW,bitmapH;  //画布中填充的bitmap的宽高
    RelativeLayout layout ;  //整张界面的baseLayout,所有的控件都是在这里addView来实现
    int gridWidth,gridHeight; //格子的宽高
//    View menuView,addView ; //popwindow的parentView
//    ArrayList list;  //课程的list，之后从服务器或者本地sharePref中获取
    PopupWindow menuPopupWindow ; //点击左上角 ···  弹出的popwindow
    PopupWindow addPopupWindow ; //
    private amANDpmView am,pm;//上午和下午view
    private int fontSize;
    private AlertDialog dialogCleanup ;
    private MySQLiteHelper sqLiteHelper;
    private SQLiteDatabase db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*隐藏titlebar*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.paint_demo_activity_main_paint);

        helper = SQLiteManager.getHelper(this); //helper 实例
        db = helper.getWritableDatabase();

        initialize();

    }

    private void initialize() {
        layout=findViewById(R.id.layout) ;

        screenWidth= ScreenUtils.getScreenWidth(this);
        screenHeight =  ScreenUtils.getScreenHeight(this);
        statueHeight=ScreenUtils.getStatusHeight(this);

        bitmapW = screenWidth ;
        bitmapH = screenHeight; //因为我已经将titlebar隐藏了 所以不用 -statueHeight

        bitmap = Bitmap.createBitmap(bitmapW,bitmapH,Bitmap.Config.ARGB_8888);

        gridHeight=bitmapH/13; //也用来限制字体大小
        gridWidth=bitmapW/6;
        fontSize =  (int)(gridHeight*0.2)-1;
        if(fontSize<0){
            fontSize=0;
        }
        Log.i("aaa", "fontSize: "+fontSize);

        /*以下都是canvas 和 paint 的操作*/
        canvas = new Canvas();
        canvas.setBitmap(bitmap);
        drawHorizontalLines();/*划横线*/
        drawVerticalLines();/*划竖线*/
        layout.setBackground(new BitmapDrawable(bitmap));

//        menuView = this.getLayoutInflater().inflate(R.layout.paint_custom_view_class,null);
//        addView = this.getLayoutInflater().inflate(R.layout.paint_custom_view_class,null);

        /*先初始化界面，然后添加上下午，上下午采取单例模式，方便控制*/
        setAdapter(); //添加所有内容
//        addTime(); //上午下午
    }

    /*所有课程的监听*/
    View.OnClickListener getListener(final String uuid){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("aaa", "该课程的uuid: "+uuid);
                /*根据uuid去数据库进行操作*/
                showEditPopwindow(EDIT_POP,uuid,v);
            }
        };
        return listener;
    }

    /*从数据库获取list数据，然后配适在layout中 __addClasses*/
    private void  setAdapter(){
        /*先清空界面layout
        * 然后添加基础信息
        * 最后从数据库获取并填充课程信息*/
        layout.removeAllViews();
        addDays(); //星期
        addNum();  //节次
        addButton(); //左上角button
        List<ClassBean> list = SQLiteManager.selectAllClasses(db);
        addClasses(list);
    }

    /****************canvas&paint*****************************/
    /*得到一个画笔实例*/
    Paint getPaint(String color){
        Paint paint ;
        paint= new Paint();
        paint.setColor(Color.parseColor(color));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setStrokeJoin(Paint.Join.MITER); //直角连接
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
        return paint;
    }
    /*获取一条线条路径
    * 定义好起点和终点*/
    Path getPath(int startX,int startY ,int endX,int endY){
       Path path = new Path();
       path.moveTo(startX,startY);
       path.lineTo(endX,endY);
       return path;
    }
    /*绘制所有的横线*/
    void drawHorizontalLines(){
        int xx = 13 ;
        for(int i=1;i<xx;i++){
            if(i==1){
                canvas.drawPath(getPath(0,(bitmapH/xx)*i,bitmapW,(bitmapH/xx)*i),getPaint("#40111111"));
            }else{
                canvas.drawPath(getPath(0,(bitmapH/xx)*i,bitmapW,(bitmapH/xx)*i),getPaint("#20111111"));
            }

        }
    }
    /*绘制所有的竖线*/
    void drawVerticalLines(){
        int xx = 6;
        for(int i=1;i<xx;i++){
            if(i==1){
                canvas.drawPath(getPath((bitmapW/xx)*i,0,(bitmapW/xx)*i,bitmapH),getPaint("#40111111"));
            }else{
                canvas.drawPath(getPath((bitmapW/xx)*i,0,(bitmapW/xx)*i,bitmapH),getPaint("#20111111"));
            }
        }
    }
    /**********************************************************/


    /*添加所有课程信息*/
    void addClasses(List<ClassBean> list){
        for(int i=0;i<list.size();i++){
            int[] positionArray = list.get(i).getPosition();
            String className =  list.get(i).getClassName();
            String classPlace =  list.get(i).getClassPlace();
            String uuid = list.get(i).getUuid();
            int marginTop = positionArray[1]*gridHeight;
            int marginLeft = positionArray[0]*gridWidth ;
            int gridCount = (positionArray[2]-positionArray[1])+1; //跨越格子的数量

            ClassView classView = new ClassView(this,gridWidth,gridHeight*gridCount,fontSize);
            classView.setOnClickListener(getListener(uuid));

            layout.addView(classView,gridWidth,gridHeight*gridCount);

            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) classView.getLayoutParams();
            lp.leftMargin = marginLeft;
            lp.topMargin = marginTop  ;
            classView.setLayoutParams(lp);
            classView.setText(className,classPlace);
            /*classView携带uuid唯一标识*/
            classView.setUUid(uuid);
        }
    }
    /*添加星期几*/
    void addDays(){
        String[] days = new String[]{"星期一","星期二","星期三","星期四","星期五"};
        for(int i=0;i<days.length;i++){
            int marginTop = 0;
            int marginLeft =(i+1)*gridWidth ;
            BaseView textView = new BaseView(this,gridWidth,gridHeight,fontSize);

            layout.addView(textView,gridWidth,gridHeight);

            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) textView.getLayoutParams();
            lp.leftMargin = marginLeft;
            lp.topMargin = marginTop  ;
            textView.setText(days[i]);

        }
    }
    /*添加数字*/
    void addNum(){
        int[] ints =new int[]{1,2,3,4,5,6,7,8,9,10,11,12};
        for(int i=0;i<ints.length;i++){
            int marginTop = (i+1)*gridHeight;
            int marginLeft = 0;
            BaseView textView = new BaseView(this,gridWidth,gridHeight,fontSize);

            layout.addView(textView,gridWidth,gridHeight);

            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) textView.getLayoutParams();
            lp.leftMargin = marginLeft;
            lp.topMargin = marginTop  ;
            textView.setText(""+ints[i]);
            textView.setRight();
        }
    }
    /*添加上下午*/
//    void addTime(){
//
//        /*上午*/
//        int marginTop = gridHeight;
//        int marginLeft = gridWidth/3;
//        int height = 5*gridHeight ;
//        am = new amANDpmView(this,gridWidth/3,height,fontSize);
//        layout.addView(am,gridWidth/3,height);
//        am.setText("上\n午");
//        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) am.getLayoutParams();
//        lp.leftMargin = marginLeft;
//        lp.topMargin = marginTop  ;
//        /*下午*/
//        marginTop = gridHeight*7;
//        marginLeft = gridWidth/3;
//        height = 5*gridHeight ;
//        pm = new amANDpmView(this,gridWidth/3,height,fontSize);
//        layout.addView(pm,gridWidth/3,height);
//        pm.setText("下\n午");
//        lp = (RelativeLayout.LayoutParams) pm.getLayoutParams();
//        lp.leftMargin = marginLeft;
//        lp.topMargin = marginTop  ;
//
//    }
    /*取消上下午*/
    void hideTime(){
        layout.removeView(am);
        layout.removeView(pm);
    }
    /*添加左上角按钮*/
    void addButton(){
        TopLeftButton button = new TopLeftButton(this,fontSize);

        layout.addView(button,gridWidth,gridHeight);
        button.setSize(gridWidth,gridHeight);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*显示弹窗*/

                showMenuPopwindow();
            }
        });

    }

    /*菜单操作都在这个弹窗里*/
    void showMenuPopwindow(){
        View parentView = this.getLayoutInflater().inflate(R.layout.paint_popwindow_menu,null);
        menuPopupWindow = new PopupWindow(parentView,gridWidth,gridHeight*12);
        CustomPopItemView item_add = parentView.findViewById(R.id.item_add);
        CustomPopItemView item_save = parentView.findViewById(R.id.item_save);
        CustomPopItemView item_cleanup = parentView.findViewById(R.id.item_cleanup);
        CustomPopItemView item_exit = parentView.findViewById(R.id.item_exit);
        item_add.setStyle(R.mipmap.paint_add,"添加");
        item_save.setStyle(R.mipmap.paint_save,"保存");
        item_cleanup.setStyle(R.mipmap.paint_cleanup,"清空");
        item_exit.setStyle(R.mipmap.paint_exit,"离开");

        View rootView = this.getLayoutInflater().inflate(R.layout.paint_demo_activity_main_paint,null);
        /*设置点击外部消失*/
        menuPopupWindow.setFocusable(true);
        menuPopupWindow.setTouchable(true);
        menuPopupWindow.setOutsideTouchable(true);
        /*设置进出动画*/
        menuPopupWindow.setAnimationStyle(R.style.paint_anim_menu_popupwindow);
        menuPopupWindow.showAtLocation(rootView, Gravity.LEFT,0,gridHeight);
        //每次打开popwindow 先隐藏上午/下午
        hideTime();
        /*popwindow 消失监听*/
        menuPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                addTime(); //消失时候，显示上午和下午
            }
        });
        /*离开按钮*/
        item_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*离开*/
                if(addPopupWindow!=null &&addPopupWindow.isShowing()){
                    addPopupWindow.dismiss();
                    return ;
                }
                PaintDemoMainActivity.this.finish();
            }
        });
        /*清除按钮*/
        item_cleanup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*弹窗确认*/
                if(addPopupWindow!=null &&addPopupWindow.isShowing()){
                    addPopupWindow.dismiss();
                    return ;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(PaintDemoMainActivity.this);
                builder.setTitle("温馨提示");
                builder.setMessage("是否清空当前课表？");
                builder.setNegativeButton("取消",null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /*清空表*/
                        SQLiteManager.cleanupTable(db);
                        setAdapter();
                    }
                });
                dialogCleanup = builder.create();
                dialogCleanup.show();
            }
        });
        /*添加监听*/
        item_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*先弹出弹窗给用户进行添加课程操作*/
                if(addPopupWindow!=null &&addPopupWindow.isShowing()){
                    addPopupWindow.dismiss();
                    return ;
                }
                showEditPopwindow(ADD_POP,null,null);
            }
        });
    }

    /*增加和编辑弹窗
    * 0表示添加弹窗 ， 1表示编辑弹窗
    * */
    void showEditPopwindow(int flag, final String uuid, final View classView){
        Log.i("aaa", "传来的uuid: "+uuid);
        View parentView = this.getLayoutInflater().inflate(R.layout.paint_popwindow_edit,null);
        View rootView = this.getLayoutInflater().inflate(R.layout.paint_demo_activity_main_paint,null);
        addPopupWindow = new PopupWindow(parentView,gridWidth*5, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout addLayout = parentView.findViewById(R.id.addLayout);
        LinearLayout editLayout = parentView.findViewById(R.id.editLayout);
        ImageView closeIV = parentView.findViewById(R.id.closeIV);
        final EditText classNameET = parentView.findViewById(R.id.classNameET);
        final EditText classPlaceET = parentView.findViewById(R.id.classPlaceET);
        final Spinner dateSpinner = parentView.findViewById(R.id.dateSpinner);
        final Spinner turn_1_spinner = parentView.findViewById(R.id.turn_1_spinner);
        final Spinner turn_2_spinner = parentView.findViewById(R.id.turn_2_spinner);
        Button moveBt = parentView.findViewById(R.id.cleanupButton);
        Button confirmEdit = parentView.findViewById(R.id.confirmEdit);
        Button addBt = parentView.findViewById(R.id.addButton);
        if(flag == ADD_POP) {  //添加按钮
            addLayout.setVisibility(View.VISIBLE);
            editLayout.setVisibility(View.GONE);
            /*将所有初始化*/
            /*重置popwindow*/
            reSetPopwindow(classNameET,classPlaceET,dateSpinner,turn_1_spinner,turn_2_spinner);
        }else if(flag == EDIT_POP){ //移除和编辑按钮
            addLayout.setVisibility(View.GONE);
            editLayout.setVisibility(View.VISIBLE);
            /*根据传来的uuid，从数据库获取当条内容并填充到布局*/
            HashMap<String,String> map = selectOneClasses(db,uuid);
            classNameET.setText(map.get("className"));
            classPlaceET.setText(map.get("classPlace"));
            /*date*/
            Adapter spinnerAdapter = dateSpinner.getAdapter();
            for(int i=0;i<spinnerAdapter.getCount();i++){
                if(map.get("date").equals(spinnerAdapter.getItem(i).toString())){
                    dateSpinner.setSelection(i,true);
                    break ;
                }
            }
            /*turn_1*/
            spinnerAdapter = turn_1_spinner.getAdapter();
            for(int i=0;i<spinnerAdapter.getCount();i++){
                if(map.get("turn_1").equals(spinnerAdapter.getItem(i).toString())){
                    turn_1_spinner.setSelection(i,true);
                    break ;
                }
            }
            /*turn_2*/
            spinnerAdapter = turn_2_spinner.getAdapter();
            for(int i=0;i<spinnerAdapter.getCount();i++){
                if(map.get("turn_2").equals(spinnerAdapter.getItem(i).toString())){
                    turn_2_spinner.setSelection(i,true);
                    break ;
                }
            }
        }
        /*设置点击外部消失*/
        addPopupWindow.setFocusable(true);
        addPopupWindow.setTouchable(true);
        addPopupWindow.setOutsideTouchable(true);
        /*设置动画*/
        addPopupWindow.setAnimationStyle(R.style.paint_anim_add_popupwindow);
        addPopupWindow.showAtLocation(rootView,Gravity.CENTER,gridWidth,0);

        /*添加课程*/
        addBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,String> map = new HashMap<>();
                String className = classNameET.getText().toString();
                String classPlace = classPlaceET.getText().toString();
                String date = dateSpinner.getSelectedItem().toString();
                String turn_1 = turn_1_spinner.getSelectedItem().toString();
                String turn_2 = turn_2_spinner.getSelectedItem().toString();
                map.put("className",className);
                map.put("date",date);
                map.put("turn_1",turn_1);
                map.put("turn_2",turn_2);
                map.put("classPlace",classPlace);
                SQLiteManager.insert(db,map);
                /*更新*/
                setAdapter();
                /*重置popwindow*/
                reSetPopwindow(classNameET,classPlaceET,dateSpinner,turn_1_spinner,turn_2_spinner);
             }
        });
        /*确认修改*/
        confirmEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,String> map = new HashMap<>();
                String className = classNameET.getText().toString();
                String classPlace = classPlaceET.getText().toString();
                String date = dateSpinner.getSelectedItem().toString();
                String turn_1 = turn_1_spinner.getSelectedItem().toString();
                String turn_2 = turn_2_spinner.getSelectedItem().toString();
                map.put("className",className);
                map.put("date",date);
                map.put("turn_1",turn_1);
                map.put("turn_2",turn_2);
                map.put("classPlace",classPlace);
                SQLiteManager.update(db,map,uuid);
                /*更新*/
                setAdapter();
            }
        });
        /*移除课程*/
        moveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteManager.delete(db,uuid);
                /*重置*/
                setAdapter();
            }
        });
        /*closeIV 关闭弹窗*/
        closeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addPopupWindow!=null &&addPopupWindow.isShowing()){
                    addPopupWindow.dismiss();
                    return ;
                }
            }
        });
    }

    /*重置popwindow*/
    void reSetPopwindow(EditText classNameET,EditText classPlaceET,Spinner dateSpinner,Spinner turn_1_spinner,Spinner turn_2_spinner){
        classNameET.setText(null);
        classPlaceET.setText(null);
        dateSpinner.setSelection(0);
        turn_1_spinner.setSelection(0);
        turn_2_spinner.setSelection(0);
    }

}
