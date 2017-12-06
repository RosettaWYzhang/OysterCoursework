package com.tfl.billing;

import com.oyster.OysterCard;
import com.oyster.OysterCardReader;
import com.tfl.external.Customer;
import com.tfl.underground.OysterReaderLocator;
import com.tfl.underground.Station;

import java.util.UUID;

public class Example {
    public static void main(String[] args){
        //1. get two external customer through adapter
        CustomerDatabaseAdapter adapter = new CustomerDatabaseAdapter();
        Customer customer = adapter.getCustomers().get(0);
        Customer customer2 = adapter.getCustomers().get(1);
        UUID cardId = customer.cardId();
        UUID cardId2 = customer2.cardId();


        //2. initialise card reader at start and end destination
        OysterCardReader paddingtonReader = OysterReaderLocator.atStation(Station.PADDINGTON);
        OysterCardReader bakerStreetReader = OysterReaderLocator.atStation(Station  .BAKER_STREET);
        OysterCardReader angelReader = OysterReaderLocator.atStation(Station.ANGEL);
        OysterCardReader aldgateEastReader = OysterReaderLocator.atStation(Station.ALDGATE_EAST);

        //3. use tracker to connect relative
        TravelTracker tracker1 = new TravelTracker(adapter);
        tracker1.connect(paddingtonReader,bakerStreetReader,angelReader);
        TravelTracker tracker2 = new TravelTracker(adapter);
        tracker2.connect(aldgateEastReader,paddingtonReader,bakerStreetReader,angelReader);

        //4. customer touches card at each stations
        OysterCard myCard1 = new OysterCard(cardId.toString());
        OysterCard myCard2 = new OysterCard(cardId2.toString());

        //4.1 customer 1 below cap
        paddingtonReader.touch(myCard1);
        bakerStreetReader.touch(myCard1);
        bakerStreetReader.touch(myCard1);
        angelReader.touch(myCard1);

        //4.2 customer 2 over cap
        paddingtonReader.touch(myCard2);
        bakerStreetReader.touch(myCard2);
        bakerStreetReader.touch(myCard2);
        aldgateEastReader.touch(myCard2);
        aldgateEastReader.touch(myCard2);
        angelReader.touch(myCard2);
        angelReader.touch(myCard2);
        paddingtonReader.touch(myCard2);
        paddingtonReader.touch(myCard2);
        bakerStreetReader.touch(myCard2);


        //5. summarise customer journey and print journey for each customer.
        CustomerSummary summary = new CustomerSummary(customer);
        summary.chargeCustomer(EventLogger.getInstance());
        CustomerSummary summary2 = new CustomerSummary(customer2);
        summary2.chargeCustomer(EventLogger.getInstance());


        //6. charge all customers and print the whole journey from database
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