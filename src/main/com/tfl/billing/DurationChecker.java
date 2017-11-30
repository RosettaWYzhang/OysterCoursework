package com.tfl.billing;


import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DurationChecker {
    private Journey journey;

    //confused, should I pass journey as constructor or pass to method as parameter?


    public boolean isLong(Journey journey){
        long millis = journey.endTime().getTime() - journey.startTime().getTime();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        if(minutes < 25)
            return false;
        return true;
    }

    public boolean isLong(Clock clockStart, Clock clockEnd){
        long diff = clockEnd.time() - clockStart.time();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        if(minutes < 25)
            return false;
        return true;
    }


}
