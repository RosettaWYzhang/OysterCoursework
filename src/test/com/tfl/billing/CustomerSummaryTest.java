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
        EventLogger eventLogger = EventLogger.getInstance();
        UUID cardId = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
        UUID startReaderId = UUID.randomUUID();
        UUID endReaderId = UUID.randomUUID();
        JourneyEvent start = new JourneyStart(cardId, startReaderId);
        JourneyEvent end = new JourneyEnd(cardId, endReaderId);
        eventLogger.add(start);
        eventLogger.add(end);
        Customer customer = CustomerDatabase.getInstance().getCustomers().get(0);
        CustomerSummary customerSummary = new CustomerSummary(customer);
        customerSummary.summariseJourney();
        //not sure if it's a good decision to make journeys in costcalculator package private...
        assertTrue(journeys.size() == 1);
    }

}