package com.tfl.billing;


import com.tfl.external.Customer;

import java.util.ArrayList;
import java.util.List;


public class EventsToJourneyConverter {
    private final Customer customer;
    private final EventLoggerIF eventLogger;

    public EventsToJourneyConverter(Customer customer, EventLoggerIF eventLogger){
        this.customer = customer;
        this.eventLogger = eventLogger;
    }

    public List<Journey> getCustomerJourneys(){
        List<JourneyEvent> eventLog = eventLogger.getEventLog();
        List<JourneyEvent> customerJourneyEvent = filterJourneyEvent(eventLog);
        List<Journey> customerJourneys = convertEventLogToJourneys(customerJourneyEvent);
        return customerJourneys;
    }


    public List<JourneyEvent> filterJourneyEvent(List<JourneyEvent> eventLog) {
        List<JourneyEvent> customerJourneyEvents = new ArrayList<>();
        for (JourneyEvent journeyEvent : eventLog) {
            if (journeyEvent.cardId().equals(customer.cardId())) {
                customerJourneyEvents.add(journeyEvent);
            }
        }
        return customerJourneyEvents;
    }

    public List<Journey> convertEventLogToJourneys(List<JourneyEvent> customerJourneyEvents) {
        List<Journey> customerJourneys = new ArrayList<Journey>();
        JourneyEvent start = null;
        for (JourneyEvent event : customerJourneyEvents) {
            if (event instanceof JourneyStart) {
                start = event;
            }
            if (event instanceof JourneyEnd && start != null) {
                customerJourneys.add(new Journey(start, event));
                start = null;
            }
        }
        return customerJourneys;
    }
}
