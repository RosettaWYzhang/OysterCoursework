package com.tfl.billing;

import java.util.Calendar;
import java.util.Date;


import java.util.Calendar;
import java.util.Date;

public class TimeChecker {

    //checked
    public boolean peak(Journey journey) {
        return peak(journey.startTime()) || peak(journey.endTime());
    }

    //checked
    public boolean peak(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return (hour >= 6 && hour <= 9) || (hour >= 17 && hour <= 19);
    }


}
