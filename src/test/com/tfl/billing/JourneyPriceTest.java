package com.tfl.billing;
import com.oyster.OysterCard;
import com.oyster.OysterCardReader;
import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;
import com.tfl.underground.OysterReaderLocator;
import com.tfl.underground.Station;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.UUID;

import static junit.framework.TestCase.assertTrue;

public class JourneyPriceTest {
    private static final BigDecimal OFF_PEAK_JOURNEY_PRICE = new BigDecimal(2.40);
    private static final BigDecimal PEAK_JOURNEY_PRICE = new BigDecimal(3.20);

    @Test
    public void testPriceForOffPeakHour(){
        //feels like I should pass time as an argument to determine price, instead of journey
        JourneyEvent start = new JourneyStart(UUID.randomUUID(),UUID.randomUUID());
        JourneyEvent end = new JourneyEnd(UUID.randomUUID(),UUID.randomUUID());
        Journey journey = new Journey(start, end);
        BigDecimal price = new CostCalculator(journey).determinePrice(journey);
        assertTrue(price.equals(PEAK_JOURNEY_PRICE));
    }

    @Test
    public void testPriceForPeakHour(){

    }

    @Test
    public void testPriceOfOneJourney(){

        //still feels too long
        Customer customer = CustomerDatabase.getInstance().getCustomers().get(0);

        UUID cardID = customer.cardId();
        OysterCard oysterCard = new OysterCard(cardID.toString());

        OysterCardReader paddingtonReader = OysterReaderLocator.atStation(Station.PADDINGTON);
        OysterCardReader bakerStreetReader = OysterReaderLocator.atStation(Station.BAKER_STREET);


        TravelTracker travelTracker = new TravelTracker();
        travelTracker.connect(paddingtonReader, bakerStreetReader);

        paddingtonReader.touch(oysterCard);
        bakerStreetReader.touch(oysterCard);

        //should pass a mock object of oyster card?
        CustomerSummary summary = new CustomerSummary(customer);
        summary.summariseJourney();
        BigDecimal totalBill = summary.getJourneyPrice();
        assertTrue(totalBill.compareTo(new BigDecimal(0)) == 1);

    }

}