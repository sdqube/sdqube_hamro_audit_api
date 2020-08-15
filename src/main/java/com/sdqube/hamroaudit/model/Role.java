package com.sdqube.hamroaudit.model;


/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 7/3/20 1:30 AM
 */

public class Role {

    public final static Role USER = new Role("USER");
    public final static Role SERVICE = new Role("SERVICE");

    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}