package com.tfl.billing;

import java.util.concurrent.TimeUnit;

public class ControllableClock implements Clock{

    private long now = System.currentTimeMillis();

    @Override
    public long time() {
        return now;
    }

    public void setTime(int hour, int minute){
        long hourMillis = TimeUnit.HOURS.toMillis(hour);
        long minuteMillis = TimeUnit.MINUTES.toMillis(minute);
        now = hourMillis + minuteMillis;
    }
}
