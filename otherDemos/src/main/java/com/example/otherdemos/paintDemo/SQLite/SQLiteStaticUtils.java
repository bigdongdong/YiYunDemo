package com.example.otherdemos.paintDemo.SQLite;

/**
 * Created by CXD on 2018/11/20.
 */
public class SQLiteStaticUtils {
    public static final String DATABASE_NAME = "susCat.db" ; //数据库的名称  ' timetable.db'
    public static final String TABLE_NAME = "TimeTable" ; //就一张表，表名叫做 ' TimeTable'
    public static final int DATABASE_VERSION = 1 ; //强制使版本号为1

    /*字段名称*/
    public static final String UUID = "uuid" ;
    public static final String CLASS_NAME = "class_name" ;
    public static final String DATE = "date" ;
    public static final String TURN_1 = "turn_1" ;
    public static final String TURN_2 = "turn_2" ;
    public static final String CLASS_PLACE = "class_place" ;
}
