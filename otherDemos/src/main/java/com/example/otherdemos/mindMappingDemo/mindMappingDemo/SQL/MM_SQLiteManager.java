package com.example.otherdemos.mindMappingDemo.mindMappingDemo.SQL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static com.example.otherdemos.mindMappingDemo.mindMappingDemo.SQL.MM_SQLStatics.TABLE_NAME;


/**
 * Created by CXD on 2018/11/20.
 * 处理所有sqlite的操作
 */
public class MM_SQLiteManager {
    public  static MM_SQLiteHelper helper ; //单例
    public static MM_SQLiteHelper getHelper(Context context){
        if(helper==null){
            helper= new MM_SQLiteHelper(context) ;
        }
        return helper ;
    }

    /*插入 数据
    * 如果level 是2  则 parent_id 是为null
    * 如果level 是3 则 parent_id 不为空
    * */
    public static String insert(SQLiteDatabase db, HashMap<String,String> map,String parent_id) {
        db = helper.getWritableDatabase();
        String uuid = "";
        if (db != null && map!=null ) {
            uuid = getUUid();
            String level = map.get("level");
            String content = map.get("content");
            String insertSQL = "insert into " + TABLE_NAME + "(mm_id,mm_level,mm_content,mm_parent_id) values('"+uuid+"','"+level+"','"+content+"','"+parent_id+"')";
            Log.i("aaa", "insert: " + insertSQL);
            /*执行插入*/
            db.execSQL(insertSQL);
            db.close();
        }
        return uuid;
    }

    /*查询所有level = 2 的数据*/
    public static ArrayList<HashMap<String,String>> selectLevel2(SQLiteDatabase db){
        db = helper.getWritableDatabase();
        HashMap<String,String> map;
        ArrayList<HashMap<String,String>> list = new ArrayList<>();

        Cursor cursor = null ;
        if(db!=null){
            String selectSQL = "select * from "+TABLE_NAME +" where mm_level = '2' ";
            /*执行搜寻*/
            cursor = db.rawQuery(selectSQL,null);

            int index; //某个字段对应的位置
            while(cursor.moveToNext()!=false){
                index = cursor.getColumnIndex("mm_id");
                String mm_id = cursor.getString(index);
                index = cursor.getColumnIndex("mm_content");
                String mm_content = cursor.getString(index);

                map = new HashMap<>();
                map.put("mm_id",mm_id);
                map.put("mm_content",mm_content);

                list.add(map);
            }
            db.close();
        }
        return list;
    }

    /*查询一条level = 3 的数据，根据level = 2 的mm_id来*/
    public static String[] selectLevel3(SQLiteDatabase db,String parent_id){
        db = helper.getWritableDatabase();
        HashMap<String,String> map;
        ArrayList<String> list = new ArrayList<>();

        Cursor cursor = null ;
        if(db!=null){
            String selectSQL = "select * from "+TABLE_NAME +" where mm_parent_id = '"+parent_id+"' ";
            /*执行搜寻*/
            cursor = db.rawQuery(selectSQL,null);

            int index; //某个字段对应的位置
            while(cursor.moveToNext()!=false){
                index = cursor.getColumnIndex("mm_content");
                String mm_content = cursor.getString(index);


                list.add(mm_content);
            }
            db.close();
        }
        return list.toArray(new String[list.size()]);
    }

    /*清空表*/
    public static void cleanupTable(SQLiteDatabase db){
        db = helper.getWritableDatabase();
        if(db!=null){
            String dropTable = "delete from "+TABLE_NAME+" where 1=1";
            /*执行删除表*/
            db.execSQL(dropTable);
        }
        db.close();
    }

    /*获取uuid*/
    private static String getUUid(){
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        Log.i("aaa", "getUUid: "+uuid);
        return uuid ;
    }

}
