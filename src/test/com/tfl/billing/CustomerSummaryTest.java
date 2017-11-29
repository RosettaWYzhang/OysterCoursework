package com.tfl.billing;


import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static com.tfl.billing.CostCalculator.journeys;
import static org.junit.Assert.*;


public class CustomerSummaryTest {
    Customer customer = CustomerDatabase.getInstance().getCustomers().get(0);
    UUID cardID = customer.cardId();
    EventLogger eventLogger = EventLogger.getInstance();
    SystemClock clock = new SystemClock();

    UUID startReaderId = UUID.randomUUID();
    UUID endReaderId = UUID.randomUUID();
    JourneyEvent start = new JourneyStart(cardID, startReaderId, clock);
    JourneyEvent end = new JourneyEnd(cardID, endReaderId, clock);


    CustomerSummary customerSummary = new CustomerSummary(customer);

    private final ByteArrayOutputStream CustomerContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream JourneyContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream DateContent = new ByteArrayOutputStream();
    Journey journey = new Journey(start, end);





    @Test
    public void checkJourneyListNotEmpty(){
        eventLogger.add(start);
        eventLogger.add(end);
        customerSummary.summariseJourney();
        assertNotNull(journeys.size());
    }



    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(CustomerContent));
        System.setOut(new PrintStream(JourneyContent));
        System.setOut(new PrintStream(DateContent));
    }


    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

//    @Test
//    public void testCustomerInfoPrint() {
//        System.out.print("Customer: " + customer.fullName() + " - " + customer.cardId());
//        assertEquals("Customer: " + customer.fullName() + " - " + customer.cardId(), CustomerContent.toString());
//    }
//
//    @Test
//    public void testJourneyPrint() {
//
//        System.out.print(journey.formattedStartTime() + "\t" + journey.originId() + "\t" + " -- " + journey.formattedEndTime() + "\t" + journey.destinationId());
//        assertEquals(journey.formattedStartTime() + "\t" + journey.originId() + "\t" + " -- " + journey.formattedEndTime() + "\t" + journey.destinationId(), JourneyContent.toString());
//
//    }




    @Test
    public void testStartDate(){
        Date startDate = journey.startTime();
        long startTime1 = startDate.getTime();
        long startTime2 = start.time();
        assertTrue(startTime1 == startTime2);
    }
    @Test
    public void testEndDate(){
        Date EndDate = journey.endTime();
        long endTime1 = EndDate.getTime();
        long endTime2 = start.time();
        assertTrue(endTime1 == endTime2);
    }

    @Test
    public void testformattedStartTime(){
        String format = journey.formattedStartTime();
        String currentTime = SimpleDateFormat.getInstance().format(new Date());
        System.out.print(format);
        assertEquals(currentTime, DateContent.toString());
    }

    /*
    @Test
    public void testConnect(){
        OysterCardReader paddingtonReader = OysterReaderLocator.atStation(Station.PADDINGTON);
        OysterCardReader bakerStreetReader = OysterReaderLocator.atStation(Station.BAKER_STREET);
        TravelTracker travelTracker = new TravelTracker();
        travelTracker.connect(paddingtonReader, bakerStreetReader);
        OysterCard myCard = new OysterCard("38400000-8cf0-11bd-b23e-10b96e4ef00d");
        paddingtonReader.touch(myCard);
        bakerStreetReader.touch(myCard);
       }
       */

}