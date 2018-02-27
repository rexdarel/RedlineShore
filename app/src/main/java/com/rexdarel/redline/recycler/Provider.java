package com.rexdarel.redline.recycler;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Admin on 2/27/2018.
 */
@IgnoreExtraProperties
public class Provider {
    public String name;
    public Long mobileNumber, telephoneNumber;
    public String address;
    public String email;

    public Provider(){

    }

    public Provider(String name, Long mobileNumber, Long telephoneNumber, String address, String email){
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.telephoneNumber = telephoneNumber;
        this.address = address;
        this.email = email;
    }

}
