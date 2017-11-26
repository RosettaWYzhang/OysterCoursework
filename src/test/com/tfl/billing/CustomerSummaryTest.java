package com.tfl.billing;

import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static com.tfl.billing.CostCalculator.journeys;
import static org.junit.Assert.*;

public class CustomerSummaryTest {

    @Test
    public void checkJourneyListNotEmpty(){
        Customer customer = CustomerDatabase.getInstance().getCustomers().get(0);
        UUID cardID = customer.cardId();
        EventLogger eventLogger = EventLogger.getInstance();
        SystemClock clock = new SystemClock();

        UUID startReaderId = UUID.randomUUID();
        UUID endReaderId = UUID.randomUUID();
        JourneyEvent start = new JourneyStart(cardID, startReaderId, clock);
        JourneyEvent end = new JourneyEnd(cardID, endReaderId, clock);
        eventLogger.add(start);
        eventLogger.add(end);

        System.out.println(customer.fullName()+" "+ customer.cardId());
        CustomerSummary customerSummary = new CustomerSummary(customer);
        customerSummary.summariseJourney();

        //not sure if it's a good decision to make journeys in costcalculator package private...
        //or should it return the arrayList of journeys?
        System.out.println(journeys.size());
        assertTrue(journeys.size() == 1);
    }

}