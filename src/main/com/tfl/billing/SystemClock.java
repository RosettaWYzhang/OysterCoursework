package com.tfl.billing;

public class SystemClock implements Clock {
    @Override
    public long time(){
        return System.currentTimeMillis();
    }
}
