package com.tfl.billing;

import java.util.concurrent.TimeUnit;

public class DurationChecker {

    public boolean isLong(Journey journey){
        long millis = journey.endTime().getTime() - journey.startTime().getTime();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        if(minutes < 25)
            return false;
        return true;
    }

}
