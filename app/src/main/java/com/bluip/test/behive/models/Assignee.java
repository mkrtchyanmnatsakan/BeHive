package com.bluip.test.behive.models;

import java.io.Serializable;

public class Assignee implements Serializable {

    public Assignee(){}
    private int drawable;


    public Assignee(int drawable) {
        this.drawable = drawable;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
