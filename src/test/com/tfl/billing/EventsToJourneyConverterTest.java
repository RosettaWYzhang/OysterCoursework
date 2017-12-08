package com.tfl.billing;

import com.tfl.external.Customer;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class EventsToJourneyConverterTest {

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    EventLoggerIF eventLogger = context.mock(EventLoggerIF.class);
    Customer customer1 = MockCustomerDatabase.getInstance().getCustomers().get(0);
    Customer customer2 = MockCustomerDatabase.getInstance().getCustomers().get(1);
    Clock clock = new SystemClock();
    EventsToJourneyConverter converter = new EventsToJourneyConverter(customer1,eventLogger);


    @Test
    public void testRetrieveEventsFromEventLogger(){
        context.checking(new Expectations() {{
            exactly(1).of(eventLogger).getEventLog();
        }});
        converter.getCustomerJourneys();
    }


    @Test
    public void testOneJourneyIsAddedWithOneJourneyStartOneJourneyEnd(){
        JourneyEvent start = new JourneyStart(customer1.cardId(),UUID.randomUUID(), clock);
        JourneyEvent end = new JourneyEnd(customer1.cardId(),UUID.randomUUID(), clock);
        List<JourneyEvent> eventList = new ArrayList<>();
        eventList.add(start);
        eventList.add(end);
        List<Journey> journeys = converter.convertEventLogToJourneys(eventList);
        assertTrue(journeys.size() == 1);
    }

    @Test
    public void testOneJourneyIsAddedWithTwoJourneyStartOneJourneyEnd(){
        JourneyEvent start = new JourneyStart(customer1.cardId(),UUID.randomUUID(), clock);
        JourneyEvent end = new JourneyEnd(customer1.cardId(),UUID.randomUUID(), clock);
        JourneyEvent start2 = new JourneyStart(customer1.cardId(),UUID.randomUUID(), clock);
        List<JourneyEvent> eventList = new ArrayList<>();
        eventList.add(start);
        eventList.add(end);
        eventList.add(start2);
        List<Journey> journeys = converter.convertEventLogToJourneys(eventList);
        assertTrue(journeys.size() == 1);
    }

    @Test
    public void testTwoJourneysAreAddedWithTwoJourneyStartTwoJourneyEnd(){
        JourneyEvent start = new JourneyStart(customer1.cardId(),UUID.randomUUID(), clock);
        JourneyEvent end = new JourneyEnd(customer1.cardId(),UUID.randomUUID(), clock);
        JourneyEvent start2 = new JourneyStart(customer1.cardId(),UUID.randomUUID(), clock);
        JourneyEvent end2 = new JourneyEnd(customer1.cardId(),UUID.randomUUID(), clock);
        List<JourneyEvent> eventList = new ArrayList<>();
        eventList.add(start);
        eventList.add(end);
        eventList.add(start2);
        eventList.add(end2);
        List<Journey> journeys = converter.convertEventLogToJourneys(eventList);
        assertTrue(journeys.size() == 2);
    }

    @Test
    public void testNoJourneyIsAddedWithOneJourneyStart(){
        JourneyEvent start = new JourneyStart(customer1.cardId(),UUID.randomUUID(), clock);
        List<JourneyEvent> eventList = new ArrayList<>();
        eventList.add(start);
        List<Journey> journeys = converter.convertEventLogToJourneys(eventList);
        assertTrue(journeys.size() == 0);
    }

    @Test
    public void testNoJourneyIsAddedWithThreeJourneyStart(){
        JourneyEvent start = new JourneyStart(customer1.cardId(),UUID.randomUUID(), clock);
        JourneyEvent start2 = new JourneyStart(customer1.cardId(),UUID.randomUUID(), clock);
        JourneyEvent start3 = new JourneyStart(customer1.cardId(),UUID.randomUUID(), clock);
        List<JourneyEvent> eventList = new ArrayList<>();
        eventList.add(start);
        eventList.add(start2);
        eventList.add(start3);
        List<Journey> journeys = converter.convertEventLogToJourneys(eventList);
        assertTrue(journeys.size() == 0);
    }

    @Test
    public void testNoJourneyIsAddedWithThreeJourneyEnd(){
        JourneyEvent end = new JourneyStart(customer1.cardId(),UUID.randomUUID(), clock);
        JourneyEvent end2 = new JourneyStart(customer1.cardId(),UUID.randomUUID(), clock);
        JourneyEvent end3 = new JourneyStart(customer1.cardId(),UUID.randomUUID(), clock);
        List<JourneyEvent> eventList = new ArrayList<>();
        eventList.add(end);
        eventList.add(end2);
        eventList.add(end3);
        List<Journey> journeys = converter.convertEventLogToJourneys(eventList);
        assertTrue(journeys.size() == 0);
    }

    @Test
    public void testEventFiltering(){
        JourneyEvent customer1EventStart = new JourneyStart(customer1.cardId(),UUID.randomUUID(),clock);
        JourneyEvent customer1EventEnd = new JourneyEnd(customer1.cardId(),UUID.randomUUID(),clock);
        JourneyEvent customer2EventStart = new JourneyStart(customer2.cardId(),UUID.randomUUID(),clock);
        JourneyEvent customer2EventEnd = new JourneyEnd(customer2.cardId(),UUID.randomUUID(),clock);
        List<JourneyEvent> eventList = new ArrayList<>();
        eventList.add(customer1EventStart);
        eventList.add(customer1EventEnd);
        eventList.add(customer2EventStart);
        eventList.add(customer2EventEnd);
        List<JourneyEvent> journeyEventsForCustomer1 = converter.filterJourneyEvent(eventList);
        assertTrue(journeyEventsForCustomer1.size() == 2);
    }



}