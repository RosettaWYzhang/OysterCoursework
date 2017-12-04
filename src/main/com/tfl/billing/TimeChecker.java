package com.tfl.billing;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

// PEAK: between 06:00 and 09:59 or between 17:00 and 19:59

public class TimeChecker {

    public boolean isPeak(Journey journey) {
        return isJourneyEventPeak(journey.startTime()) || isJourneyEventPeak(journey.endTime());
    }

    public boolean isJourneyEventPeak(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return (hour >= 6 && hour <= 9) || (hour >= 17 && hour <= 19);
    }


}
