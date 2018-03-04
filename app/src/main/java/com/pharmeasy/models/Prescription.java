package com.pharmeasy.models;

/**
 * Created by vihaanverma on 03/03/18.
 */

public class Prescription {
    String name, detail;

    public Prescription(){

    }
    public Prescription(String name, String detail) {
        this.name = name;
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
