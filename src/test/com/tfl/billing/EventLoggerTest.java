package com.tfl.billing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;


public class EventLoggerTest {
    private MockEventLogger eventLogger;

    @Before
    public void before() throws Exception{
       this.eventLogger  = MockEventLogger.getInstance();
    }

    @After
    public void after() throws Exception{
        eventLogger.deleteAllEvents();
    }

    @Test
    public void testEventsGetAdded(){
        MockEventLogger eventLogger = MockEventLogger.getInstance();
        UUID cardId = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
        UUID startReaderId = UUID.randomUUID();
        UUID endReaderId = UUID.randomUUID();
        SystemClock clock = new SystemClock();
        JourneyEvent start = new JourneyStart(cardId, startReaderId, clock);
        JourneyEvent end = new JourneyEnd(cardId, endReaderId, clock);
        eventLogger.add(start);
        eventLogger.add(end);
        assertTrue(eventLogger.getEventLog().size()==2);
    }



}