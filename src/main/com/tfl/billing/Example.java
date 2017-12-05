package com.tfl.billing;

import com.oyster.OysterCard;
import com.oyster.OysterCardReader;
import com.tfl.external.Customer;
import com.tfl.underground.OysterReaderLocator;
import com.tfl.underground.Station;

import java.util.UUID;

public class Example {
    public static void main(String[] args){
        //1. get an external customer through adapter
        CustomerDatabaseAdapter adapter = new CustomerDatabaseAdapter();
        Customer customer = adapter.getCustomers().get(0);
        UUID cardId = customer.cardId();

        //2. initialise card reader at start and end destination
        OysterCardReader paddingtonReader = OysterReaderLocator.atStation(Station.PADDINGTON);
        OysterCardReader bakerStreetReader = OysterReaderLocator.atStation(Station.BAKER_STREET);

        //3. use tracker to connect the two stations
        TravelTracker tracker = new TravelTracker(adapter);
        tracker.connect(paddingtonReader,bakerStreetReader);

        //4. customer touches card at the two stations
        OysterCard myCard = new OysterCard(cardId.toString());
        paddingtonReader.touch(myCard);
        bakerStreetReader.touch(myCard);

        //5. summarise customer journey and print journey
        CustomerSummary summary = new CustomerSummary(customer);
        summary.chargeCustomer(EventLogger.getInstance());

        //6. charge all customers
        PaymentsSystemAdapter.getInstance().chargeAllAccounts(adapter.getCustomers());
    }
}

/* original example

public static void main(String[] args) throws Exception {
    //1. create oyster card
    OysterCard myCard = new OysterCard("38400000-8cf0-11bd-b23e-10b96e4ef00d");

    //2. create card reader at different stations
    OysterCardReader paddingtonReader = OysterReaderLocator.atStation(Station.PADDINGTON);
    OysterCardReader bakerStreetReader = OysterReaderLocator.atStation(Station.BAKER_STREET);
    OysterCardReader kingsCrossReader = OysterReaderLocator.atStation(Station.KINGS_CROSS);

    //use tracker to connect different stations
    TravelTracker travelTracker = new TravelTracker();
    travelTracker.connect(paddingtonReader, bakerStreetReader, kingsCrossReader);

    //3. touch at different stations
    paddingtonReader.touch(myCard);
    minutesPass(5);
    bakerStreetReader.touch(myCard);
    minutesPass(15);
    bakerStreetReader.touch(myCard);
    minutesPass(10);
    kingsCrossReader.touch(myCard);

    //4. charge
    travelTracker.chargeAccounts();
}

private static void minutesPass(int n) throws InterruptedException {
    Thread.sleep(n * 60 * 1000);
}
*/