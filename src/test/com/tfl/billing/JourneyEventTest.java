package com.tfl.billing;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

public class JourneyEventTest {

    UUID CARD_ID = UUID.randomUUID();
    UUID START_READER_ID = UUID.randomUUID();
    UUID END_READER_ID = UUID.randomUUID();
    Clock clock = new SystemClock();
    JourneyEvent JOURNEY_START = new JourneyStart(CARD_ID, START_READER_ID, clock);
    JourneyEvent JOURNEY_END = new JourneyEnd(CARD_ID, END_READER_ID, clock);

    Journey testJourney = new Journey(JOURNEY_START,JOURNEY_END);


    @Test
    public void returnRightcardIdorigin() throws Exception {
        assertEquals(testJourney.originId(),(JOURNEY_START.readerId()));

    }

    @Test
    public void returnRightReaderDestinationId() throws Exception {
        assertEquals(testJourney.destinationId(),(JOURNEY_END.readerId()));

    }


    @Test
    public void checkFormattedStartTime() throws Exception{
        assertEquals(testJourney.startTime(),new Date(JOURNEY_START.time()));

    }


    @Test
    public void checkFormattedEndTime() {
        assertEquals(testJourney.formattedEndTime(),SimpleDateFormat.getInstance().format((new Date(JOURNEY_END.time()))));

    }

    @Test
    public void startTime() {
        assertEquals(testJourney.startTime(),new Date(JOURNEY_START.time()));

    }

    @Test
    public void endTime() {
        assertEquals(testJourney.endTime(),new Date(JOURNEY_END.time()));
    }


    @Test
    public void durationSeconds()  {
        assertEquals(testJourney.durationSeconds(), (int) ((JOURNEY_END.time() - JOURNEY_START.time()) / 1000));
    }

    @Test
    public void durationMinutes()  {
        assertEquals(testJourney.durationMinutes(), "" + testJourney.durationSeconds() / 60 + ":" + testJourney.durationSeconds() % 60);
    }

/*
    public String formattedStartTime() {
        return format(start.time());
    }

    public String formattedEndTime() {
        return format(end.time());
    }

    public Date startTime() {
        return new Date(start.time());
    }

    public Date endTime() {
        return new Date(end.time());
    }

    public int durationSeconds() {
        return (int) ((end.time() - start.time()) / 1000);
    }

    public String durationMinutes() {
        return "" + durationSeconds() / 60 + ":" + durationSeconds() % 60;
    }

    private String format(long time) {
        return SimpleDateFormat.getInstance().format(new Date(time));
    }*/







}