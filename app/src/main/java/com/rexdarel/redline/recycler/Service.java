package com.rexdarel.redline.recycler;

/**
 * Created by Admin on 2/28/2018.
 */

public class Service {
    public String name, schedule, location, description, uid, key;
    public float price;

    public Service(String uid, String name, String schedule, String location, String description, float price, String key){
        this.uid = uid;
        this.name = name;
        this.schedule = schedule;
        this.location = location;
        this.description = description;
        this.price = price;
        this.key = key;
    }
}
