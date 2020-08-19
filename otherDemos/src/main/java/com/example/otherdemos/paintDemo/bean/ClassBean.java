package com.example.otherdemos.paintDemo.bean;

import android.util.Log;

/**
 * Created by CXD on 2018/11/21.
 */
public class ClassBean {
    private int[] position;
    private String className;
    private String classPlace;
    private String uuid;

    public String toString(){
        return "position="+this.position+"  className="+this.className+"  classPlace="+this.classPlace+"  uuid="+this.uuid;
    }

    public ClassBean(int[] position, String className, String classPlace, String uuid) {
        this.position = position;
        this.className = className;
        this.classPlace = classPlace;
        this.uuid = uuid;
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassPlace() {
        return classPlace;
    }

    public void setClassPlace(String classPlace) {
        this.classPlace = classPlace;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
