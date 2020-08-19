package com.example.otherdemos.paintDemo.SQLite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.otherdemos.paintDemo.SQLite.SQLiteStaticUtils.*;

/**
 * Created by CXD on 2018/11/20.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
private String  TAG ="aaa" ;

    public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySQLiteHelper(Context context) {
        this(context,"paintDemo"+DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        /*创建表*/
        String createTableSQL = "create table "+SQLiteStaticUtils.TABLE_NAME+"("+UUID+" varchar(35) primary key not null,"
                +CLASS_NAME+" varchar(30),"
                +DATE+" varchar(10),"
                +TURN_1+" varchar(4) not null,"
                +TURN_2+" varchar(4) not null,"
                +CLASS_PLACE+" varchar(10) );";
        Log.i(TAG, "onCreate: "+createTableSQL);

        db.execSQL(createTableSQL );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
