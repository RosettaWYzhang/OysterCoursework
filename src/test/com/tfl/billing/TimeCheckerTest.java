package com.tfl.billing;
import org.junit.Test;
import java.util.Date;
import static org.junit.Assert.*;

// PEAK: between 06:00 and 09:59 or between 17:00 and 19:59
// TODO: add tests using date objects?

public class TimeCheckerTest {
    TimeChecker timeChecker = new TimeChecker();

    @Test
    public void testMorningPeakTime(){
        assertTrue(timeChecker.isPeak(7));

    }
    @Test
    public void testAfternoonPeakTime(){
        assertTrue(timeChecker.isPeak(17));

    }

    @Test
    public void testOffPeakTime(){
        assertTrue(!timeChecker.isPeak(11));
    }
    @Test
    public void testPeakJourneyWithPeakStartOffPeakEnd(){
        assertTrue(timeChecker.isPeak(7) || timeChecker.isPeak(13));

    }
    @Test
    public void testPeakJourneyWithPeakStartPeakEnd(){
        assertTrue(timeChecker.isPeak(7) || timeChecker.isPeak(8));
    }
    @Test
    public void testPeakJourneyWithOffPeakStartPeakEnd(){
        assertTrue(timeChecker.isPeak(13) || timeChecker.isPeak(17));
    }

    @Test
    public void testOffPeakJourneyWithOffPeakStartOffEnd(){
        assertTrue(!timeChecker.isPeak(13) || !timeChecker.isPeak(14));
    }


    @Test
    public void testCurrentTime(){
        long currentTime = System.currentTimeMillis();
        assertTrue(timeChecker.isPeak(new Date(currentTime)) || !timeChecker.isPeak(new Date(currentTime)));
    }


}
