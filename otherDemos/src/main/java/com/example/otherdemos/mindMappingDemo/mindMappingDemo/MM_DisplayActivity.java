package com.example.otherdemos.mindMappingDemo.mindMappingDemo;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;


import com.example.otherdemos.R;
import com.example.otherdemos.mindMappingDemo.manager.ViewControl;
import com.example.otherdemos.mindMappingDemo.mindMappingDemo.SQL.MM_SQLiteManager;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.otherdemos.mindMappingDemo.mindMappingDemo.SQL.MM_SQLiteManager.helper;



/*这个activity只用作展示思维导图，当然添加水印也在这里
* 从前一个activity获取list
* */

public class MM_DisplayActivity extends AppCompatActivity {
    private SQLiteDatabase db ;
    ArrayList<HashMap<String,String>> level2List ;


    RelativeLayout mainLayout;
    ArrayList<HashMap<String,Object>> displayList ;
    String rootTitle ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*titlebar*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /*toolbar*/
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mm_activity_display);

        mainLayout = findViewById(R.id.mainLayout) ;
        level2List = new ArrayList<>();
        displayList  = new ArrayList<>();

        /*获取展示用的list*/
        helper = MM_SQLiteManager.getHelper(this);
        db = helper.getWritableDatabase();
        /*获取标题*/
        rootTitle = this.getIntent().getStringExtra("rootTitle");

        level2List = MM_SQLiteManager.selectLevel2(db);
        HashMap<String,String> map;
        for(int i =0;i<level2List.size();i++){
            map = level2List.get(i);
            String parent_id = map.get("mm_id");
            String content = map.get("mm_content");
            /*根据刚刚得到的 parent_id,来获取level3的数据*/
            String[] level3Array = MM_SQLiteManager.selectLevel3(db,parent_id);

            /*将 2的content 以及 对应的3的String[] 添加到最终的*/
            HashMap<String,Object> map2 = new HashMap<>();
            map2.put("title",content);
            map2.put("message",level3Array);

            displayList.add(map2);
        }
        Log.d("aaa", "最终的list: "+displayList);


        /*接着展示内容*/
        ViewControl control = new ViewControl(this);

        try{
            mainLayout.addView(control.getParentView(rootTitle,displayList));
        }catch (Exception e){}


//        String[] s;
//        s = new String[]{"选择内容","论文中需要的文献",
//                "选择题目的目的和意义","论文的研究重点","李很快就"};
//
//        ArrayList<HashMap<String,Object>> list = new ArrayList<>() ;
//        HashMap<String,Object>  map;
//
//        map = new HashMap<>();
//        map.put("title","科学复习");
//        map.put("message",new String[]{"自行百度"});
//        String title = "论文往后都和我一打五的" ;
//
//
//
////        mainLayout.addView(control.getChildView_3((String[]) list.get(2).get("message"),0));
////        mainLayout.addView(control.getChildView_2(list));
//        mainLayout.addView(control.getParentView(title,list));


    }



}
