package com.tfl.billing;

import com.oyster.OysterCard;
import com.oyster.OysterCardReader;
import com.tfl.external.Customer;
import com.tfl.underground.OysterReaderLocator;
import com.tfl.underground.Station;

import java.util.UUID;

public class Example {

    CustomerDatabaseAdapter database = new CustomerDatabaseAdapter();
    OysterCardReader paddingtonReader = OysterReaderLocator.atStation(Station.PADDINGTON);
    OysterCardReader bakerStreetReader = OysterReaderLocator.atStation(Station  .BAKER_STREET);
    OysterCardReader angelReader = OysterReaderLocator.atStation(Station.ANGEL);
    OysterCardReader aldgateEastReader = OysterReaderLocator.atStation(Station.ALDGATE_EAST);

    // example run
    public static void main(String[] args) {
        new Example().run();
    }

    public Customer getCustomer(int index){
        Customer customer = database.getCustomers().get(index);
        return customer;
    }


    public void run(){

        //1. get two customers
        Customer customer1 = getCustomer(0);
        Customer customer2 = getCustomer(1);
        UUID cardId = customer1.cardId();
        UUID cardId2 = customer2.cardId();

        //2. use station connector to connect stations
        TravelTracker scanner1 = new TravelTracker(database);
        StationConnector connector1 = new StationConnector(scanner1);
        connector1.connect(paddingtonReader,bakerStreetReader,angelReader);
        TravelTracker tracker2 = new TravelTracker(database);
        StationConnector connector2 = new StationConnector(tracker2);
        connector2.connect(aldgateEastReader,paddingtonReader,bakerStreetReader,angelReader);

        //3. customer touches card at each stations
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
        CustomerSummary summary = new CustomerSummary(customer1);
        summary.printCustomerBill(EventLogger.getInstance());
        CustomerSummary summary2 = new CustomerSummary(customer2);
        summary2.printCustomerBill(EventLogger.getInstance());


        //6. charge all customers and print the whole journey from database
        PaymentsSystemAdapter.getInstance().chargeAllAccounts(database.getCustomers());
    }

}

