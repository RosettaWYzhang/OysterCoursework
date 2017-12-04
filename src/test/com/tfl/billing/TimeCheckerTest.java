package com.tfl.billing;
import org.junit.Test;
import java.util.Date;
import static org.junit.Assert.*;

// PEAK: between 06:00 and 09:59 or between 17:00 and 19:59

public class TimeCheckerTest {
    TimeChecker timeChecker = new TimeChecker();

    @Test
    public void testMorningPeakTime(){
        Journey morningPeakJourney = JourneyBuilder.aJourney().withStartTime(8,0).withEndTime(9,11).build();
        assertTrue(timeChecker.isPeak(morningPeakJourney));
    }

    @Test
    public void testAfternoonPeakTime(){
        Journey afternoonPeakJourney = JourneyBuilder.aJourney().withStartTime(17,0).withEndTime(17,11).build();
        assertTrue(timeChecker.isPeak(afternoonPeakJourney));
    }

    //TODO: fix error
    //the time checked by time checker is 1 hour ahead of the time passed in
    //possible because of error in time conversion
    @Test
    public void testPeakJourneyWithPeakStartOffPeakEnd(){
        Journey peakStartOffPeakEndJourney = JourneyBuilder.aJourney().withStartTime(9,20).withEndTime(10,11).build();
        assertTrue(timeChecker.isPeak(peakStartOffPeakEndJourney));
    }

    @Test
    public void testPeakJourneyWithPeakStartPeakEnd(){
        Journey peakStartPeakEndJourney = JourneyBuilder.aJourney().withStartTime(8,50).withEndTime(9,11).build();
        assertTrue(timeChecker.isPeak(peakStartPeakEndJourney));
    }


    @Test
    public void testPeakJourneyWithOffPeakStartPeakEnd(){
        Journey offPeakStartPeakEndJourney = JourneyBuilder.aJourney().withStartTime(16,30).withEndTime(17,20).build();
        assertTrue(timeChecker.isPeak(offPeakStartPeakEndJourney));
    }


    @Test
    public void testOffPeakJourneyWithOffPeakStartOffEnd(){
        Journey offPeakStartOffPeakEndJourney = JourneyBuilder.aJourney().withStartTime(13,30).withEndTime(14,20).build();
        assertTrue(!timeChecker.isPeak(offPeakStartOffPeakEndJourney));
    }


}
