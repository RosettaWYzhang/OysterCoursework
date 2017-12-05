package com.tfl.billing;

import com.tfl.external.Customer;
import com.tfl.external.PaymentsSystem;
import com.tfl.underground.OysterReaderLocator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// try observer pattern
public class CustomerSummary{

    private final Customer customer;
    private final List<JourneyEvent> customerJourneyEvents = new ArrayList<JourneyEvent>();
    private final List<Journey> journeys = new ArrayList<Journey>();
    private BigDecimal totalBill = new BigDecimal("0");


    public List<Journey> getJourneys() {
        return journeys;
    }
    public Customer getCustomer() {
        return customer;
    }

    public CustomerSummary(Customer customer){
        this.customer = customer;
    }

    private void summariseJourney(EventLoggerIF eventLogger){
        filterJourneyEvent(eventLogger);
        convertEventLogToJourneys();
        calculateJourneyCost();
    }

    private void calculateJourneyCost(){
        CostCalculator calculator = new CostCalculator();
        totalBill = calculator.calculateSum(journeys);
    }


    private void filterJourneyEvent(EventLoggerIF eventLogger) {
        List<JourneyEvent> eventLog = eventLogger.getEventLog();
        for (JourneyEvent journeyEvent : eventLog) {
            if (journeyEvent.cardId().equals(customer.cardId())) {
                customerJourneyEvents.add(journeyEvent);
            }
        }
    }

    private void convertEventLogToJourneys() {
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

    public void chargeCustomer(EventLoggerIF eventLogger){
        summariseJourney(eventLogger);
        PaymentsSystemAdapter adapter = PaymentsSystemAdapter.getInstance();
        adapter.charge(customer,journeys,totalBill);
    }

    private String stationWithReader(UUID originId) {
        return OysterReaderLocator.lookup(originId).name();
    }




}
