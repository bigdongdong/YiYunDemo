package com.example.otherdemos.paintDemo.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.otherdemos.paintDemo.bean.ClassBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.example.otherdemos.paintDemo.SQLite.SQLiteStaticUtils.TABLE_NAME;

/**
 * Created by CXD on 2018/11/20.
 * 处理所有sqlite的操作
 */
public class SQLiteManager {
    public  static MySQLiteHelper helper ; //单例
    public static MySQLiteHelper getHelper(Context context){
        if(helper==null){
            helper= new MySQLiteHelper(context) ;
        }
        return helper ;
    }

    /*插入数据*/
    public static void insert(SQLiteDatabase db, HashMap<String,String> map){
        db = helper.getWritableDatabase();
        if(db!=null && map!=null){
            String uuid = getUUid();
            String className = (String)map.get("className");
            String date = (String)map.get("date");
            String turn_1 = (String)map.get("turn_1");
            String turn_2 = (String)map.get("turn_2");
            String classPlace = (String)map.get("classPlace");
            String insertSQL = "insert into "+TABLE_NAME+" values('"+uuid+"','"+className+"','"+date+"','"+turn_1+"','"+turn_2+"','"+classPlace+"');";
            Log.i("aaa", "insert: "+insertSQL);
            /*执行插入*/
            db.execSQL(insertSQL);
            db.close();
        }
    }

    /*更新修改*/
    public static void update(SQLiteDatabase db, HashMap<String,String> map,String uuid){
        db = helper.getWritableDatabase();
        if(db!=null && map!=null && !uuid.equals("")){
            String className = (String)map.get("className");
            String date = (String)map.get("date");
            String turn_1 = (String)map.get("turn_1");
            String turn_2 = (String)map.get("turn_2");
            String classPlace = (String)map.get("classPlace");
            String updateSQL = "update "+TABLE_NAME+" set "+SQLiteStaticUtils.CLASS_NAME+" = '"+className+"'," +
                    ""+SQLiteStaticUtils.CLASS_PLACE+" = '"+classPlace+"',"+
                    ""+SQLiteStaticUtils.DATE+" = '"+date+"',"+
                    ""+SQLiteStaticUtils.TURN_1+" = '"+turn_1+"',"+
                    ""+SQLiteStaticUtils.TURN_2+" = '"+turn_2+"' where "+SQLiteStaticUtils.UUID+" = '"+uuid+"'";
            Log.i("aaa", "insert: "+updateSQL);
            /*执行更新*/
            db.execSQL(updateSQL);
            db.close();
        }
    }

    /*移除一条*/
    public static void delete(SQLiteDatabase db,String uuid){
        db = helper.getWritableDatabase();
        if(db!=null && !uuid.equals("")){
            String deleteSQL = "delete from "+TABLE_NAME+" where "+SQLiteStaticUtils.UUID+" = '"+uuid+"'";
            /*执行删除*/
            db.execSQL(deleteSQL);
            db.close();
        }
    }

    /**
     * 查询一条数据
     * @param db
     * @param UUID 传来的uuid
     * @return
     */
    public static HashMap<String,String> selectOneClasses(SQLiteDatabase db,String UUID){
        db = helper.getWritableDatabase();
        HashMap<String,String> map = new HashMap<>();
        ClassBean bean = null ;
        Cursor cursor = null ;
        if(db!=null){
            String selectSQL = "select * from "+TABLE_NAME+" where "+SQLiteStaticUtils.UUID+"= '"+UUID+"'";
            /*执行搜寻*/
            cursor = db.rawQuery(selectSQL,null);

            int index; //某个字段对应的位置
            if(cursor.moveToNext()!=false){
                index = cursor.getColumnIndex(SQLiteStaticUtils.DATE);
                String  date = cursor.getString(index);
                index = cursor.getColumnIndex(SQLiteStaticUtils.TURN_1);
                String turn_1 = cursor.getString(index);
                index = cursor.getColumnIndex(SQLiteStaticUtils.TURN_2);
                String turn_2 = cursor.getString(index);
                index = cursor.getColumnIndex(SQLiteStaticUtils.UUID);
                String uuid = cursor.getString(index);
                index = cursor.getColumnIndex(SQLiteStaticUtils.CLASS_NAME);
                String className = cursor.getString(index);
                index = cursor.getColumnIndex(SQLiteStaticUtils.CLASS_PLACE);
                String classPlace = cursor.getString(index);

                map.put("date",date);
                map.put("turn_1",turn_1);
                map.put("turn_2",turn_2);
                map.put("uuid",uuid);
                map.put("className",className);
                map.put("classPlace",classPlace);
                Log.i("aaa", "单个课程信息: "+map.toString());
            }
            db.close();
        }
        return map;
    }

    /*查询所有数据*/
    public static List<ClassBean> selectAllClasses(SQLiteDatabase db){
        db = helper.getWritableDatabase();
        List<ClassBean> list = new ArrayList<ClassBean>();
        ClassBean bean ;
//        ArrayList<HashMap<String,Object>>  resultList = new ArrayList<>();
//        HashMap<String,Object> map ;
        Cursor cursor = null ;
        if(db!=null){
            String selectSQL = "select * from "+TABLE_NAME;
            /*执行搜寻*/
            cursor = db.rawQuery(selectSQL,null);

            int index; //某个字段对应的位置
            while(cursor.moveToNext()!=false){
                index = cursor.getColumnIndex(SQLiteStaticUtils.DATE);
                int  date = switchDate(cursor.getString(index));
                index = cursor.getColumnIndex(SQLiteStaticUtils.TURN_1);
                int turn_1 = Integer.parseInt(cursor.getString(index));
                index = cursor.getColumnIndex(SQLiteStaticUtils.TURN_2);
                int turn_2 = Integer.parseInt(cursor.getString(index));
                index = cursor.getColumnIndex(SQLiteStaticUtils.UUID);
                String uuid = cursor.getString(index);
                index = cursor.getColumnIndex(SQLiteStaticUtils.CLASS_NAME);
                String className = cursor.getString(index);
                index = cursor.getColumnIndex(SQLiteStaticUtils.CLASS_PLACE);
                String classPlace = cursor.getString(index);

                bean = new ClassBean(new int[]{date,turn_1,turn_2},className,classPlace,uuid);
                Log.i("aaa", "单个课程信息: "+bean.toString());
                list.add(bean) ;
            }
            db.close();
        }
        return list;
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

    /*将date转成int*/
    private static int switchDate(String date){
        switch (date){
            case "星期一":
                return 1;
            case "星期二":
                return 2;
            case "星期三":
                return 3;
            case "星期四":
                return 4;
            case "星期五":
                return 5;
        }
        return 0 ;
    }

}
