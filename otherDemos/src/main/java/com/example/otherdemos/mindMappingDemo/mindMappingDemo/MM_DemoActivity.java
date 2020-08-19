package com.example.otherdemos.mindMappingDemo.mindMappingDemo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


import com.example.otherdemos.R;
import com.example.otherdemos.mindMappingDemo.mindMappingDemo.SQL.MM_SQLiteManager;

import java.util.HashMap;

import static com.example.otherdemos.mindMappingDemo.mindMappingDemo.SQL.MM_SQLiteManager.helper;


public class MM_DemoActivity extends AppCompatActivity {
    final String spitE = "#";

    final String text = "论文\n" +
            "#科学复习\n" +
            "##自行百度\n" +
            "#回忆原文\n" +
            "##关键词回忆\n" +
            "##横向联结";
    private EditText editText;
    private SQLiteDatabase db ;
    private String lastParentId;
    private String title;

    private ImageButton spitButton,huicheButton;
    private Button button,clearupButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mm_activity_demo);

        helper = MM_SQLiteManager.getHelper(this);
        db = helper.getWritableDatabase();

        spitButton = findViewById(R.id.spitButton);
        huicheButton = findViewById(R.id.huicheButton);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        clearupButton = findViewById(R.id.clearupButton);

        /*一键生成*/
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*第一件事 清空表*/
                MM_SQLiteManager.cleanupTable(db);

                String[] sArray = editText.getText().toString().split("\n");

                for(int i = 0;i<sArray.length;i++){
                    String s = sArray[i];
                    Log.i("aaa", "sArray:["+i+"]:"+s+"               s.length="+s.length());


                    /*判断是否为标题*/
                    if(s.length()>0 && !s.substring(0,1).equals(spitE)){
                        /*插入标题*/
                        title = s;
                    }

                    if(s.length()>1 ){
                        if(s.substring(0,1).equals(spitE)){//保证第一个字符是#
//                            /*这里判断是2级还是3级*/
//                            if(s.length()>2){ //如果 length() ==1,但是
                                if(!s.substring(1,2).equals(spitE)){
                                    //是2级的
                                    s = s.substring(1,s.length());
                                    HashMap<String,String> map = new HashMap<>();
                                    map.put("level","2");
                                    map.put("content",s);
                                    String parent_id = MM_SQLiteManager.insert(db,map,null);
                                    lastParentId = parent_id ;

                                }else{
                                    //是3级的
                                    s = s.substring(2,s.length());
                                    HashMap<String,String> map = new HashMap<>();
                                    map.put("level","3");
                                    map.put("content",s);
                                    MM_SQLiteManager.insert(db,map,lastParentId);
                                }
                            }
//                        }
                    }
                }
                /*跳转到下一界面，携带着title*/
                Intent intent = new Intent(MM_DemoActivity.this,MM_DisplayActivity.class);
                intent.putExtra("rootTitle",title);
                MM_DemoActivity.this.startActivity(intent);
            }
        });
        /*添加井号*/
        spitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = editText.getSelectionStart();
                Editable editable = editText.getText();
                editable.insert(index, "#");
            }
        });
        /*添加回车*/
        huicheButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = editText.getSelectionStart();
                Editable editable = editText.getText();
                editable.insert(index, "\n");
            }
        });

        /*清空*/
        clearupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
    }
}
