package com.moonbanggoo.ohgod.Model;

public class TempModel {
    String tmax = "";
    String tmin = "";

    public void setTemp(String x, String n) {
        this.tmax = x;
        this.tmin = n;
    }
    public String getMax() {
        return tmax;
    }
    public String getMin() {
        return tmin;
    }
}
