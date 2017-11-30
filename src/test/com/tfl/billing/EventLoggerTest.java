package com.tfl.billing;

import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;


public class EventLoggerTest {

    @Test
    public void testEventsGetAdded(){

        EventLogger eventLogger = EventLogger.getInstance();
        UUID cardId = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
        UUID startReaderId = UUID.randomUUID();
        UUID endReaderId = UUID.randomUUID();
        SystemClock clock = new SystemClock();
        JourneyEvent start = new JourneyStart(cardId, startReaderId, clock);
        JourneyEvent end = new JourneyEnd(cardId, endReaderId, clock);
        List<JourneyEvent> eventListBefore = eventLogger.getEventLog();
        int before = eventListBefore.size();
        eventLogger.add(start);
        eventLogger.add(end);
        List<JourneyEvent> eventListAfter = eventLogger.getEventLog();
        int after = eventListAfter.size();
        assertEquals(after,before+2);

    }

}