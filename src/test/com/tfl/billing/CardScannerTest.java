package com.tfl.billing;


import com.oyster.OysterCard;
import com.oyster.OysterCardReader;
import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;
import com.tfl.underground.OysterReaderLocator;
import com.tfl.underground.Station;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

public class CardScannerTest {
    // card scanner not complete
   // check date format, see if we want to use other time format?
   // test journey start, journey end individually
   // add more journey events for testing

    TravelTracker tracker = new TravelTracker();

    OysterCardReader reader = OysterReaderLocator.atStation(Station.PADDINGTON);

    // Registered card with valid id of an existing customer
    List<Customer> customers = CustomerDatabase.getInstance().getCustomers();
    String cardID = customers.get(0).cardId().toString();
    OysterCard registeredCard = new OysterCard(cardID);


    @Test(expected = UnknownOysterCardException.class)
    public void testUnknownOyster(){
        UUID fakeCardId = UUID.fromString("38411111-8cf0-11bd-b23e-10b96e4ef00d");
        UUID fakeReaderId = UUID.fromString("38422222-8cf0-11bd-b23e-10b96e4ef00d");
        tracker.cardScanned(fakeCardId,fakeReaderId);

    }

    @Test
    public void testKnownOysterEvokesTouchMethod(){
        CustomerDatabase customerDatabase = CustomerDatabase.getInstance();
        UUID cardID = customerDatabase.getCustomers().get(0).cardId();
        assertTrue(CustomerDatabase.getInstance().isRegisteredId(cardID));

    }

    @Test(expected = UnknownOysterCardException.class)
    public void trackerIdentifiesUnregisteredCard() {

        OysterCard unregisteredCard = new OysterCard("38000000-1234-1234-1234-123123123123");
        TravelTracker tracker = new TravelTracker();
        tracker.connect(reader);
        reader.touch(unregisteredCard);

    }

    @Test
    public void addedAndRemovedFromCurrentlyTravellingWhenTapped() {
        TravelTracker tracker = new TravelTracker();
        assertFalse(tracker.getCurrentlyTravelling().contains(registeredCard.id()));
        reader.register(tracker);
        reader.touch(registeredCard);
        assertTrue(tracker.getCurrentlyTravelling().contains(registeredCard.id()));
        reader.touch(registeredCard);
        assertFalse(tracker.getCurrentlyTravelling().contains(registeredCard.id()));

    }


}