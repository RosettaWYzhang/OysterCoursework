package com.tfl.billing;

import com.tfl.external.Customer;
import com.tfl.external.PaymentsSystem;
import com.tfl.underground.OysterReaderLocator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerSummary{

    private final Customer customer;
    private List<Journey> journeys;
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
        EventsToJourneyConverter converter = new EventsToJourneyConverter(customer, eventLogger);
        this.journeys = converter.getCustomerJourneys();
        calculateJourneyCost();
    }

    private void calculateJourneyCost(){
        CostCalculator calculator = new CostCalculator();
        totalBill = calculator.calculateSum(journeys);
    }

    public void printCustomerBill(EventLoggerIF eventLogger){
        summariseJourney(eventLogger);
        PaymentsSystemAdapter adapter = PaymentsSystemAdapter.getInstance();
        adapter.charge(customer,journeys,totalBill);
    }

}
