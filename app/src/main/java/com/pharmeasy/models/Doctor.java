package com.pharmeasy.models;

import org.parceler.Parcel;

/**
 * Created by vihaanverma on 03/03/18.
 */

@Parcel
public class Doctor {

    public Doctor() {

    }

    public Doctor(String name, String uid) {
        this.name = name;
        this.uid = uid;
    }

    public String name = "";
    public String uid = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
