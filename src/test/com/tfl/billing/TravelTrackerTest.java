package com.tfl.billing;

import com.oyster.OysterCard;
import com.oyster.OysterCardReader;
import com.tfl.underground.OysterReaderLocator;
import com.tfl.underground.Station;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import java.util.UUID;

import static org.junit.Assert.*;

public class TravelTrackerTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    CustomerDatabaseIF customerDatabaseIF = context.mock(CustomerDatabaseIF.class);

    OysterCardReader paddingtonReader = OysterReaderLocator.atStation(Station.PADDINGTON);
    MockCustomerDatabase mockCustomerDatabase = MockCustomerDatabase.getInstance();
    TravelTracker scanner = new TravelTracker(mockCustomerDatabase);
    UUID validCardId = mockCustomerDatabase.getCustomers().get(0).cardId();
    OysterCard validCard = new OysterCard(validCardId.toString());


    @Test
    public void testTravelTrackerChecksIfCardIsRegistered() {
        TravelTracker travelTracker = new TravelTracker(customerDatabaseIF);
        context.checking(new Expectations() {{
            exactly(1).of(customerDatabaseIF).isRegisteredId(validCardId);
        }});
        try {
            travelTracker.cardScanned(validCardId, paddingtonReader.id());
        }catch (Exception e){
        }
    }

    @Test(expected = UnknownOysterCardException.class)
    public void testUnknownOyster(){
        UUID fakeCardId = UUID.randomUUID();
        UUID fakeReaderId = UUID.randomUUID();
        scanner.cardScanned(fakeCardId,fakeReaderId);
    }

    @Test
    public void testKnownOysterIsRegistered(){
        scanner.cardScanned(validCardId,paddingtonReader.id());
    }

    @Test
    public void testAddToCurrentlyTravellingWhenTapped(){
        assertFalse(scanner.isTravelling(validCardId));
        paddingtonReader.register(scanner);
        paddingtonReader.touch(validCard);
        assertTrue(scanner.getCurrentlyTravelling().contains(validCardId));
    }

    @Test
    public void testRemovedFromCurrentlyTravellingWhenTappedTwice() {
        assertFalse(scanner.isTravelling(validCardId));
        paddingtonReader.register(scanner);
        paddingtonReader.touch(validCard);
        assertTrue(scanner.getCurrentlyTravelling().contains(validCardId));
        paddingtonReader.touch(validCard);
        assertFalse(scanner.getCurrentlyTravelling().contains(validCard.id()));
    }

}