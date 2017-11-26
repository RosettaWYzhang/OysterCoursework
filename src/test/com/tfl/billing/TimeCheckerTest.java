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
        assertTrue(timeChecker.peak(7));

    }
    @Test
    public void testAfternoonPeakTime(){
        assertTrue(timeChecker.peak(17));

    }

    @Test
    public void testOffPeakTime(){
        assertTrue(!timeChecker.peak(11));
    }
    @Test
    public void testPeakJourneyWithPeakStartOffPeakEnd(){
        assertTrue(timeChecker.peak(7) || timeChecker.peak(13));

    }
    @Test
    public void testPeakJourneyWithPeakStartPeakEnd(){
        assertTrue(timeChecker.peak(7) || timeChecker.peak(8));
    }
    @Test
    public void testPeakJourneyWithOffPeakStartPeakEnd(){
        assertTrue(timeChecker.peak(13) || timeChecker.peak(17));
    }

    @Test
    public void testOffPeakJourneyWithOffPeakStartOffEnd(){
        assertTrue(!timeChecker.peak(13) || !timeChecker.peak(14));
    }


    @Test
    public void testCurrentTime(){
        long currentTime = System.currentTimeMillis();
        assertTrue(timeChecker.peak(new Date(currentTime)) || !timeChecker.peak(new Date(currentTime)));
    }

}
