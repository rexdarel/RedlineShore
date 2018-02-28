package com.rexdarel.redline.recycler;

/**
 * Created by Admin on 2/28/2018.
 */

public class Service {
    public String name, requirements, schedule, location, description, uid;
    public float price;

    public Service(String uid, String name, String requirements, String schedule, String location, String description, float price){
        this.uid = uid;
        this.name = name;
        this.requirements = requirements;
        this.schedule = schedule;
        this.location = location;
        this.description = description;
        this.price = price;
    }
}
