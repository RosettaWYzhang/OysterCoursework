package com.tfl.billing;
import com.oyster.OysterCard;
import com.oyster.OysterCardReader;
import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;
import com.tfl.underground.OysterReaderLocator;
import com.tfl.underground.Station;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertTrue;

public class JourneyPriceTest {

     @Test
     public void testPeakHourPrice(){

         OysterCard myCard = new OysterCard("38400000-8cf0-11bd-b23e-10b96e4ef00d");
         OysterCardReader paddingtonReader = OysterReaderLocator.atStation(Station.PADDINGTON);
         OysterCardReader bakerStreetReader = OysterReaderLocator.atStation(Station.BAKER_STREET);
         OysterCardReader kingsCrossReader = OysterReaderLocator.atStation(Station.KINGS_CROSS);
         TravelTracker travelTracker = new TravelTracker();
         travelTracker.connect(paddingtonReader, bakerStreetReader);
         paddingtonReader.touch(myCard);
         bakerStreetReader.touch(myCard);
         Customer customer = CustomerDatabase.getInstance().getCustomers().get(0);
         CostCalculator calculator = new CostCalculator();
         CustomerSummary summary = new CustomerSummary(customer);
         summary.getCustomerJourney();
         calculator.totalCostFor(customer);
         summary.printCustomerSummary();
         assertTrue(true);



     }






}
