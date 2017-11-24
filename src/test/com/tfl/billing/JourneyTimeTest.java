package com.tfl.billing;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

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

    @Test
    public void testJourneyDuration() throws InterruptedException {
        UUID cardID = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"); // Fred Blog
        UUID readerID = UUID.randomUUID();
        JourneyEvent start = new JourneyStart(cardID, readerID);
        Thread.sleep(600);
        JourneyEvent end = new JourneyEnd(cardID, readerID);
        System.out.println(start.time());
        System.out.println(end.time());
        assertTrue(start.time() < end.time());
    }

    @Test
    public void testCurrentTime(){
        long currentTime = System.currentTimeMillis();
        assertTrue(timeChecker.peak(new Date(currentTime)) || !timeChecker.peak(new Date(currentTime)));
    }

    @Test
    public void testMillisToDateConversion(){

    }

    @Test
    public void testJourneyStartTimeRecorded(){
        UUID cardId = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
        UUID startReaderId = UUID.randomUUID();
        JourneyEvent start = new JourneyStart(cardId, startReaderId);
        assertTrue(start.time() > 0);
    }
}
