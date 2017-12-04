package com.tfl.billing;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;


import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class JourneyTest {
    private final ByteArrayOutputStream Content = new ByteArrayOutputStream();
    private final Journey journey = JourneyBuilder.aJourney().withStartTime(11,25).withEndTime(14,20).build();
    JourneyEvent journeyStart = journey.getJourneyStart();
    JourneyEvent journeyEnd = journey.getJourneyEnd();


    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(Content));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }


    @Test
    public void testJourneyPrint() {
        System.out.print(journey.formattedStartTime() + "\t" + journey.originId() + "\t" + " -- " + journey.formattedEndTime() + "\t" + journey.destinationId());
        assertEquals(journey.formattedStartTime() + "\t" + journey.originId() + "\t" + " -- " + journey.formattedEndTime() + "\t" + journey.destinationId(), Content.toString());

    }

    @Test
    public void testJourneyStartEqualsEventStartTime(){
        Date startDate = journey.startTime();
        long journeyStartTime = startDate.getTime();
        long eventStartTime = journeyStart.time();
        assertTrue(journeyStartTime == eventStartTime);
    }

    @Test
    public void testEndEqualsEventEndTime(){
        Date EndDate = journey.endTime();
        long eventEndTime = EndDate.getTime();
        long journeyEndTime = journeyEnd.time();
        assertTrue(eventEndTime == journeyEndTime);
    }

    @Test
    public void testFormattedTime() throws Exception{
        Journey journey = JourneyBuilder.aJourney().build();
        String formattedJourneyTime = journey.formattedStartTime();
        String formattedTime = SimpleDateFormat.getInstance().format(new Date());
        assertEquals(formattedJourneyTime, formattedTime);
    }

    // PEAK: between 06:00 and 09:59 or between 17:00 and 19:59

    @Test
    public void testPeakShortJourneyType(){
        Journey peakShortJourney = JourneyBuilder.aJourney().withStartTime(7,0).withEndTime(7,15).build();
        assertEquals(peakShortJourney.getJourneyType(),JourneyType.PEAK_SHORT);
    }
    @Test
    public void testPeakLongJourneyType(){
        Journey peakLongJourney = JourneyBuilder.aJourney().withStartTime(7,0).withEndTime(8,15).build();
        assertEquals(peakLongJourney.getJourneyType(),JourneyType.PEAK_LONG);
    }
    @Test
    public void testOffPeakShortJourneyType(){
        Journey offPeakShortJourney = JourneyBuilder.aJourney().withStartTime(12,0).withEndTime(12,15).build();
        assertEquals(offPeakShortJourney.getJourneyType(),JourneyType.OFF_PEAK_SHORT);
    }
    @Test
    public void testOffPeakLongJourneyType(){
        Journey offPeakLongJourney = JourneyBuilder.aJourney().withStartTime(12,0).withEndTime(14,15).build();
        assertEquals(offPeakLongJourney.getJourneyType(),JourneyType.OFF_PEAK_LONG);
    }

    @Test
    public void testDurationIsShort(){
        Journey shortJourney = JourneyBuilder.aJourney().withStartTime(12,0).withEndTime(12,15).build();
        assertTrue(!shortJourney.getDurationIsLong());
    }

    @Test
    public void testDurationIsLong(){
        Journey longJourney = JourneyBuilder.aJourney().withStartTime(12,0).withEndTime(14,15).build();
        assertTrue(longJourney.getDurationIsLong());
    }

}
