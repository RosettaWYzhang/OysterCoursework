package com.tfl.billing;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeChecker {

    //need constructor?

    //checked
    public boolean isPeak(Journey journey) {
        return isPeak(journey.startTime()) || isPeak(journey.endTime());
    }

    //checked
    public boolean isPeak(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return (hour >= 6 && hour <= 9) || (hour >= 17 && hour <= 19);
    }

    //checked
    public boolean isPeak(Clock clock) {
        long millis = clock.time();
        long hour = TimeUnit.MILLISECONDS.toHours(millis);
        return (hour >= 6 && hour <= 9) || (hour >= 17 && hour <= 19);
    }

    public boolean isPeak(int hour){
        return (hour >= 6 && hour <= 9) || (hour >= 17 && hour <= 19);
    }


}
