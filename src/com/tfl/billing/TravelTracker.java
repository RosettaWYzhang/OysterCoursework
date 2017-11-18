package com.tfl.billing;

import com.oyster.*;
import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;
import com.tfl.external.PaymentsSystem;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class TravelTracker implements ScanListener {

    static final BigDecimal OFF_PEAK_JOURNEY_PRICE = new BigDecimal(2.40);
    static final BigDecimal PEAK_JOURNEY_PRICE = new BigDecimal(3.20);
    static final BigDecimal OFF_PEAK_LONG_JOURNEY_PRICE = new BigDecimal(2.70);
    static final BigDecimal OFF_PEAK_SHORT_JOURNEY_PRICE = new BigDecimal(1.60);
    static final BigDecimal PEAK_LONG_JOURNEY_PRICE = new BigDecimal(3.80);
    static final BigDecimal PEAK_SHORT_JOURNEY_PRICE = new BigDecimal(2.90);
    static final BigDecimal PEAK_CAP = new BigDecimal(9.00);
    static final BigDecimal OFF_PEAK_CAP = new BigDecimal(7.00);



    private final List<JourneyEvent> eventLog = new ArrayList<JourneyEvent>();
    private final Set<UUID> currentlyTravelling = new HashSet<UUID>();

    public void chargeAccounts() {
        //get total cost of all customer
        CustomerDatabase customerDatabase = CustomerDatabase.getInstance(); //singleton

        List<Customer> customers = customerDatabase.getCustomers();
        for (Customer customer : customers) {
            totalJourneysFor(customer);
        }
    }

    private void totalJourneysFor(Customer customer) {
        //get all journeys of a customer

        //in eventlog, cardID == customer.cardID add into list
        List<JourneyEvent> customerJourneyEvents = new ArrayList<>();
        for (JourneyEvent journeyEvent : eventLog) {
            if (journeyEvent.cardId().equals(customer.cardId())) {
                customerJourneyEvents.add(journeyEvent);
            }
        }

        List<Journey> journeys = new ArrayList<Journey>();

        JourneyEvent start = null;
        // make a list of Journeys
        for (JourneyEvent event : customerJourneyEvents) {
            if (event instanceof JourneyStart) {
                start = event;
            }
            if (event instanceof JourneyEnd && start != null) {
                journeys.add(new Journey(start, event));
                start = null;
            }
        }
        //calculate customer's total cost
        BigDecimal customerTotal = new BigDecimal(0);
        for (Journey journey : journeys) {
            BigDecimal journeyPrice = OFF_PEAK_JOURNEY_PRICE;
            if (peak(journey)) {
                journeyPrice = PEAK_JOURNEY_PRICE;
            }
            customerTotal = customerTotal.add(journeyPrice);
        }


        PaymentsSystem.getInstance().charge(customer, journeys, roundToNearestPenny(customerTotal));
    }

    private void NewVersionTotalJourneysFor(Customer customer) {
        //get all journeys of a customer

        //in eventlog, cardID == customer.cardID add into list
        List<JourneyEvent> customerJourneyEvents = new ArrayList<>();
        for (JourneyEvent journeyEvent : eventLog) {
            if (journeyEvent.cardId().equals(customer.cardId())) {
                customerJourneyEvents.add(journeyEvent);
            }
        }

        List<Journey> journeys = new ArrayList<Journey>();

        JourneyEvent start = null;
        // make a list of Journeys
        for (JourneyEvent event : customerJourneyEvents) {
            if (event instanceof JourneyStart) {
                start = event;
            }
            if (event instanceof JourneyEnd && start != null) {
                journeys.add(new Journey(start, event));
                start = null;
            }
        }
        //calculate customer's total cost
        int flag = 0;
        BigDecimal customerTotal = new BigDecimal(0);
        for (Journey journey : journeys) {
            BigDecimal journeyPrice = OFF_PEAK_JOURNEY_PRICE;
            if (peak(journey)) {
                journeyPrice = PEAK_JOURNEY_PRICE;
                flag = 1;
            }
            customerTotal = customerTotal.add(journeyPrice);

        }

        BigDecimal customerTotalCost = roundToNearestPenny(customerTotal);
        if(customerTotal.compareTo(PEAK_CAP) == 1){
            if(flag == 1){
                customerTotalCost=PEAK_CAP;
            }
                else {
                  if (customerTotal.compareTo(OFF_PEAK_CAP) == 1) {
                       customerTotalCost=OFF_PEAK_CAP;
                  }
                  else{
                      customerTotalCost=roundToNearestPenny(customerTotal);
                  }


            }
        }


        PaymentsSystem.getInstance().charge(customer, journeys, customerTotalCost );
    }

    public BigDecimal journeysListCostOldVersion(List<Journey> journeys){
        //calculate cost of a list of Journeys
        BigDecimal customerTotal = new BigDecimal(0);
        for (Journey journey : journeys) {
            BigDecimal journeyPrice = OFF_PEAK_JOURNEY_PRICE;
            if (peak(journey)) {
                journeyPrice = PEAK_JOURNEY_PRICE;
            }
            customerTotal = customerTotal.add(journeyPrice);
        }
        return roundToNearestPenny(customerTotal);
    }
    public long getDateDiff(Date start, Date end, TimeUnit timeUnit) {
        long diffInMillies = end.getTime() - start.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

    public BigDecimal journeysListCostNewVersion(List<Journey> journeys){
        BigDecimal customerTotal = new BigDecimal(0);
        for(Journey journey : journeys){
            long timeInterval = getDateDiff(journey.endTime(), journey.startTime(),TimeUnit.MINUTES);
            if(peak(journey)){
                if(timeInterval <= 25){
                    customerTotal = customerTotal.add(PEAK_SHORT_JOURNEY_PRICE);
                }else{
                    customerTotal = customerTotal.add(PEAK_LONG_JOURNEY_PRICE);
                }
            }else{
                if(timeInterval <= 25){
                    customerTotal = customerTotal.add(OFF_PEAK_SHORT_JOURNEY_PRICE);
                }else{
                    customerTotal = customerTotal.add(OFF_PEAK_LONG_JOURNEY_PRICE);
                }
            }
        }
        return customerTotal;
    }

    private BigDecimal roundToNearestPenny(BigDecimal poundsAndPence) {

        //ex. 1.745 -> 1.75
        return poundsAndPence.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private boolean peak(Journey journey) {
        //check peak
        return peak(journey.startTime()) || peak(journey.endTime());
    }

    private boolean peak(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return (hour >= 6 && hour <= 9) || (hour >= 17 && hour <= 19);
    }

    public void connect(OysterCardReader... cardReaders) {
        for (OysterCardReader cardReader : cardReaders) {
            cardReader.register(this);
        }
    }

    @Override
    public void cardScanned(UUID cardId, UUID readerId) {
        //currentlyTravelling is a set
        //currentlyTravelling is a set
        // test whether cardId is in the set
        // yes: add a JourneyEnd into eventLog, and remove from the currentlyTravelling
        // No:add cardId into currentlyTravelling, eventLog add a JourneyStart

        if (currentlyTravelling.contains(cardId)) {
            eventLog.add(new JourneyEnd(cardId, readerId));
            currentlyTravelling.remove(cardId);
        } else {
            if (CustomerDatabase.getInstance().isRegisteredId(cardId)) {
                currentlyTravelling.add(cardId);
                eventLog.add(new JourneyStart(cardId, readerId));
            } else {
                throw new UnknownOysterCardException(cardId);
            }
        }
    }

}
