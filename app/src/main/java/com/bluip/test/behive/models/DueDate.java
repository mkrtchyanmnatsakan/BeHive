package com.bluip.test.behive.models;

import java.io.Serializable;

public class DueDate implements Serializable {

    private String time;
    private String pm;
    private String day;

    public DueDate(){}


    public DueDate(String time, String pm, String day) {
        this.time = time;
        this.pm = pm;
        this.day = day;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
