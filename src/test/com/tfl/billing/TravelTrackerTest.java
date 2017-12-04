package com.tfl.billing;

import com.oyster.OysterCard;
import com.oyster.OysterCardReader;
import com.tfl.underground.OysterReaderLocator;
import com.tfl.underground.Station;
import org.junit.Test;
import java.util.UUID;
import static org.junit.Assert.*;

public class TravelTrackerTest {
    OysterCardReader paddingtonReader = OysterReaderLocator.atStation(Station.PADDINGTON);
    MockCustomerDatabase mockCustomerDatabase = MockCustomerDatabase.getInstance();
    TravelTracker tracker = new TravelTracker(mockCustomerDatabase);
    UUID validCardId = mockCustomerDatabase.getCustomers().get(0).cardId();
    OysterCard validCard = new OysterCard(validCardId.toString());


    @Test(expected = UnknownOysterCardException.class)
    public void testUnknownOyster(){
        UUID fakeCardId = UUID.randomUUID();
        UUID fakeReaderId = UUID.randomUUID();
        tracker.cardScanned(fakeCardId,fakeReaderId);
    }

    @Test
    public void testKnownOysterIsRegistered(){
        tracker.cardScanned(validCardId,paddingtonReader.id());
    }

    @Test
    public void testAddToCurrentlyTravellingWhenTapped(){
        assertFalse(tracker.isTravelling(validCardId));
        paddingtonReader.register(tracker);
        paddingtonReader.touch(validCard);
        assertTrue(tracker.getCurrentlyTravelling().contains(validCardId));
    }

    @Test
    public void testRemovedFromCurrentlyTravellingWhenTappedTwice() {
        assertFalse(tracker.isTravelling(validCardId));
        paddingtonReader.register(tracker);
        paddingtonReader.touch(validCard);
        assertTrue(tracker.getCurrentlyTravelling().contains(validCardId));
        paddingtonReader.touch(validCard);
        assertFalse(tracker.getCurrentlyTravelling().contains(validCard.id()));
    }

}