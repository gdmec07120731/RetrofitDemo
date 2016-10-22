package com.ppjun.retrofitdemo.bean;

/**
 * Package :com.ppjun.retrofitdemo.bean
 * Description :
 * Author :Rc3
 * Created at :2016/10/21 14:46.
 */
public class Repos {
    int id;
    String name;
    String full_name;

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
