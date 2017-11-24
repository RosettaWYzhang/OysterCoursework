package com.tfl.billing;


import com.tfl.external.Customer;
import com.tfl.external.PaymentsSystem;
import sun.jvm.hotspot.gc_implementation.parallelScavenge.PSYoungGen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomerSummary {

    private final Customer customer;
    private List<JourneyEvent> eventLog = new ArrayList<JourneyEvent>();
    private final List<JourneyEvent> customerJourneyEvents = new ArrayList<JourneyEvent>();
    private final List<Journey> journeys = new ArrayList<Journey>();

    public CustomerSummary(Customer customer){
        this.customer = customer;
    }

    public void getEventLog(){
        eventLog = EventLogger.getInstance().getEventLog();

    }

    public void filterJourneyEvent(){
        for (JourneyEvent journeyEvent : eventLog) {
            if (journeyEvent.cardId().equals(customer.cardId())) {
                customerJourneyEvents.add(journeyEvent);
            }
        }
    }

    public void convertEventLogToJourneys(){
        JourneyEvent start = null;
        for (JourneyEvent event : customerJourneyEvents) {
            if (event instanceof JourneyStart) {
                start = event;
            }
            if (event instanceof JourneyEnd && start != null) {
                journeys.add(new Journey(start, event));
                start = null;
            }
        }
    }

    public List<Journey> getCustomerJourney(){
        getEventLog();
        filterJourneyEvent();
        convertEventLogToJourneys();
        return journeys;
    }

    public void printCustomerSummary(){
        System.out.println("\n\n*****************\n\n");
        System.out.println("Customer: " + customer.fullName() + " - " + customer.cardId());
        System.out.println("Journey Summary:");
        Iterator i$ = journeys.iterator();
        while(i$.hasNext()) {
            Journey journey = (Journey)i$.next();
            System.out.println(journey.formattedStartTime() + "\t" + journey.originId() + "\t" + " -- " + journey.formattedEndTime() + "\t" + journey.destinationId());
        }
    }


}
