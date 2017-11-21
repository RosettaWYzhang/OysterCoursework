package com.tfl.billing;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;


public class JourneyTimeTest {
    TimeChecker timeChecker = new TimeChecker();
    @Test
    public void testPeakStartTime(){
        assertTrue(timeChecker.peak(new Date("Sun Nov 19 8:32:44 GMT 2017")));
    }
    @Test
    public void testPeakEndTime(){
        assertTrue(timeChecker.peak(new Date("Sun Nov 19 17:32:44 GMT 2017")));
    }
    @Test
    public void testNonPeakStartTime(){assertTrue(!timeChecker.peak(new Date("Sun Nov 19 3:32:44 GMT 2017")));}
    @Test
    public void testNonPeakEndTime(){
        assertTrue(!timeChecker.peak(new Date("Sun Nov 19 22:32:44 GMT 2017")));
    }
}
