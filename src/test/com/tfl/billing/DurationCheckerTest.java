package com.tfl.billing;


import org.junit.Test;
import static com.tfl.billing.JourneyBuilder.*;


import static org.junit.Assert.*;


public class DurationCheckerTest{
    private DurationChecker durationChecker = new DurationChecker();

    @Test
    public void testDurationIsShort(){
        int startHour = 11;
        int startMinutes = 25;
        int endHour = 11;
        int endMinutes = 35;
        Journey journey = aJourney().withStartTime(startHour, startMinutes).withEndTime(endHour, endMinutes).build();
        assertFalse(durationChecker.isLong(journey));
    }

    @Test
    public void testDurationIsLong(){
        int startHour = 11;
        int startMinutes = 25;
        int endHour = 12;
        int endMinutes = 55;
        Journey journey = aJourney().withStartTime(startHour, startMinutes).withEndTime(endHour, endMinutes).build();
        assertTrue(durationChecker.isLong(journey));
    }


}