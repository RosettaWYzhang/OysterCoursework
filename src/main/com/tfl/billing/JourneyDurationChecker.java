package com.tfl.billing;


import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JourneyDurationChecker {

    public boolean isLong(Journey journey){
        long millis = journey.startTime().getTime() - journey.endTime().getTime();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        if(minutes > 25)
            return false;
        return true;
    }

}
