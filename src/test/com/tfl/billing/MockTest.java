package com.tfl.billing;

import com.oyster.OysterCardReader;
import com.tfl.external.Customer;
import com.tfl.underground.OysterReaderLocator;
import com.tfl.underground.Station;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();


    private Customer customer = MockCustomerDatabase.getInstance().getCustomers().get(0);
    private UUID cardId = customer.cardId();
    OysterCardReader paddingtonReader = OysterReaderLocator.atStation(Station.PADDINGTON);
    UUID readerId = paddingtonReader.id();

    Clock clock = context.mock(Clock.class);



    CustomerDatabaseIF customerDatabaseIF = context.mock(CustomerDatabaseIF.class);
    EventLoggerIF eventLoggerIF = context.mock(EventLoggerIF.class);

    @Test
    public void testJourneyEventGetTimeInLong(){

        context.checking(new Expectations() {{
            exactly(1).of(clock).time();
        }});
        JourneyEvent journeyEvent = new JourneyStart(cardId, readerId, clock);
    }



    @Test
    public void testTravelTrackerCheckingIfCardIsRegister() {
        TravelTracker travelTracker = new TravelTracker(customerDatabaseIF);
        context.checking(new Expectations() {{
            exactly(1).of(customerDatabaseIF).isRegisteredId(cardId);
        }});
        try {
            travelTracker.cardScanned(cardId, readerId);
        }catch (Exception e){

        }
    }


}
