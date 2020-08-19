package com.example.otherdemos.mindMappingDemo.mindMappingDemo.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.otherdemos.paintDemo.SQLite.SQLiteStaticUtils;

import static com.example.otherdemos.mindMappingDemo.mindMappingDemo.SQL.MM_SQLStatics.DATABASE_NAME;
import static com.example.otherdemos.mindMappingDemo.mindMappingDemo.SQL.MM_SQLStatics.DATABASE_VERSION;
import static com.example.otherdemos.mindMappingDemo.mindMappingDemo.SQL.MM_SQLStatics.TABLE_NAME;


/**
 * Created by CXD on 2018/11/20.
 */
public class MM_SQLiteHelper extends SQLiteOpenHelper {
private String  TAG ="aaa" ;


    public MM_SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MM_SQLiteHelper(Context context) {
        this(context,"mm_"+DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        /*创建表*/
        String createTableSQL = "create table "+TABLE_NAME+"(mm_id varchar(35)," +
                "mm_level varchar(2)," +
                "mm_content varchar(50)," +
                "mm_parent_id varchar(35)" +
                ");";
        Log.i(TAG, "onCreate: "+createTableSQL);

        db.execSQL(createTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
