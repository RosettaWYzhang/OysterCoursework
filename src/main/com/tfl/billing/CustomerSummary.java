package com.tfl.billing;

import com.tfl.external.Customer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomerSummary {

    private final Customer customer;
    private List<JourneyEvent> eventLog;
    private final List<JourneyEvent> customerJourneyEvents = new ArrayList<JourneyEvent>();
    public final List<Journey> journeys = new ArrayList<Journey>();
    private BigDecimal totalBill = new BigDecimal("0");

    public CustomerSummary(Customer customer){

        this.customer = customer;
        this.eventLog = EventLogger.getInstance().getEventLog();
    }

    public void summariseJourney(){
        filterJourneyEvent();
        convertEventLogToJourneys();
        getCustomerJourneyPrice();
    }

    private void getCustomerJourneyPrice(){
        CostCalculator calculator = new CostCalculator(journeys);
        totalBill = calculator.calculateSum();
    }



    private void filterJourneyEvent(){
        for (JourneyEvent journeyEvent : eventLog) {
            if (journeyEvent.cardId().equals(customer.cardId())) {
                customerJourneyEvents.add(journeyEvent);
            }
        }
    }

    private void convertEventLogToJourneys(){
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

    public BigDecimal getJourneyPrice(){
        return totalBill;
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
        System.out.println("Total charge £: " + totalBill);
    }


}
