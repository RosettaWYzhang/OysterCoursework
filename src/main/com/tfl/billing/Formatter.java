package com.tfl.billing;

import java.text.SimpleDateFormat;
import java.util.Date;

//takes in millis and return Date
public class Formatter {
    private long time;

    public Formatter(long time){
        this.time = time;
    }

    public String format() {
        return SimpleDateFormat.getInstance().format(new Date(time));
    }
}
