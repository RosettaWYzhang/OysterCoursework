package com.tfl.billing;


import com.oyster.OysterCard;
import com.oyster.OysterCardReader;
import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;
import com.tfl.underground.OysterReaderLocator;
import com.tfl.underground.Station;

import java.util.UUID;

public class EntryPoint {
    public static void main(String[] args){
        //get external customer database through adapter
        CustomerDatabaseAdapter adapter = new CustomerDatabaseAdapter();
        CustomerDatabase customerDatabase = adapter.getInstance();

        //get the first customer from the real database
        Customer customer = customerDatabase.getCustomers().get(0);
        UUID cardId = customer.cardId();

        //initialise card reader at start and end destination
        OysterCardReader paddingtonReader = OysterReaderLocator.atStation(Station.PADDINGTON);
        OysterCardReader bakerStreetReader = OysterReaderLocator.atStation(Station.BAKER_STREET);

        //use tracker to connect the two stations
        TravelTracker tracker = new TravelTracker(adapter);
        tracker.connect(paddingtonReader,bakerStreetReader);

        //customer touches card at the two stations
        OysterCard myCard = new OysterCard(cardId.toString());
        paddingtonReader.touch(myCard);
        bakerStreetReader.touch(myCard);

        //summarise journey and print journey
        CustomerSummary summary = new CustomerSummary(customer);
        summary.chargeCustomer(EventLogger.getInstance());
    }
}

