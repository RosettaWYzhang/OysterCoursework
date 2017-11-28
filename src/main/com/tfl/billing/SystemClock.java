package com.tfl.billing;

/**
 * Created by wanyuezhang on 26/11/2017.
 */
public class SystemClock implements Clock {
    @Override
    public long time(){
        return System.currentTimeMillis();
    }
}
