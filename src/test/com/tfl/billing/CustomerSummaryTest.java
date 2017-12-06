package com.tfl.billing;

import com.oyster.OysterCardReader;
import com.tfl.external.Customer;
import com.tfl.underground.OysterReaderLocator;
import com.tfl.underground.Station;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;


public class CustomerSummaryTest {
    private Customer customer = MockCustomerDatabase.getInstance().getCustomers().get(0);
    private UUID cardId = customer.cardId();
    private CustomerSummary customerSummary;
    private final ByteArrayOutputStream Content = new ByteArrayOutputStream();
    private MockEventLogger eventLogger;
    private static final BigDecimal PEAK_CAP = new BigDecimal(9.00);
    private static final BigDecimal OFF_PEAK_CAP = new BigDecimal(7.00);

    public CustomerSummaryTest(){
        this.customerSummary = new CustomerSummary(customer);
    }

    @Before
    public void before() throws Exception{
        this.eventLogger  = MockEventLogger.getInstance();
    }

    @After
    public void after() throws Exception{
        eventLogger.deleteAllEvents();
    }

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(Content));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void checkJourneyListNotEmpty(){
        OysterCardReader paddingtonReader = OysterReaderLocator.atStation(Station.PADDINGTON);
        UUID readerId = paddingtonReader.id();
        ControllableClock clock = new ControllableClock();
        JourneyStart start = new JourneyStart(cardId,readerId,clock);
        JourneyEnd end = new JourneyEnd(cardId,readerId,clock);
        eventLogger.add(start);
        eventLogger.add(end);
        customerSummary.printCustomerBill(eventLogger);
        System.out.println(customerSummary.getJourneys().size());
        assertTrue(customerSummary.getJourneys().size() == 1);
    }

    // PEAK: between 06:00 and 09:59 or between 17:00 and 19:59
    private List<Journey> journeyListSetUp(){
        List<Journey> journeys = new ArrayList<>();

        Journey morningPeakShortJourney = JourneyBuilder.aJourney().withStartTime(8,10).withEndTime(8,11).build();
        Journey morningPeakLongJourney = JourneyBuilder.aJourney().withStartTime(9,10).withEndTime(10,20).build();
        Journey eveningPeakShortJourney = JourneyBuilder.aJourney().withStartTime(17,50).withEndTime(17,55).build();
        Journey eveningPeakLongJourney = JourneyBuilder.aJourney().withStartTime(17,10).withEndTime(18,20).build();

        Journey noonOffPeakShortJourney = JourneyBuilder.aJourney().withStartTime(12,10).withEndTime(12,12).build();
        Journey noonOffPeakLongJourney = JourneyBuilder.aJourney().withStartTime(12,10).withEndTime(13,20).build();
        Journey eveningOffPeakShortJourney = JourneyBuilder.aJourney().withStartTime(22,10).withEndTime(22,12).build();
        Journey eveningOffPeakLongJourney = JourneyBuilder.aJourney().withStartTime(21,1).withEndTime(23,12).build();

        journeys.add(morningPeakShortJourney);
        journeys.add(morningPeakLongJourney);
        journeys.add(eveningPeakLongJourney);
        journeys.add(eveningPeakShortJourney);

        journeys.add(noonOffPeakLongJourney);
        journeys.add(noonOffPeakShortJourney);
        journeys.add(eveningOffPeakLongJourney);
        journeys.add(eveningOffPeakShortJourney);

        return journeys;
    }

    @Test
    public void testListOfJourneyDoesNotExceedPeakCap(){
        List<Journey> journeys = journeyListSetUp();
        List<Journey> journeysForTesting  = new ArrayList<>();
        journeysForTesting.add(journeys.get(0));
        journeysForTesting.add(journeys.get(4));
        CostCalculator calculator = new CostCalculator();
        assertTrue(calculator.calculateSum(journeysForTesting).compareTo(PEAK_CAP) < 0);
    }

    @Test
    public void testListOfJourneyExceedPeakCap(){
        List<Journey> journeysForTesting = journeyListSetUp();
        CostCalculator calculator = new CostCalculator();
        assertTrue(calculator.calculateSum(journeysForTesting).equals(PEAK_CAP));
    }

    @Test
    public void testListOfJourneyDoesNotExceedOffPeakCap(){
        List<Journey> journeys = journeyListSetUp();
        List<Journey> journeysForTesting = new ArrayList<>();
        journeysForTesting.add(journeys.get(4));
        journeysForTesting.add(journeys.get(5));
        CostCalculator calculator = new CostCalculator();
        assertTrue(calculator.calculateSum(journeysForTesting).compareTo(OFF_PEAK_CAP) < 0);
    }

    @Test
    public void testListOfJourneyExceedOffPeakCap(){
        List<Journey> journeys = journeyListSetUp();
        List<Journey> journeysForTesting = new ArrayList<>();
        journeysForTesting.add(journeys.get(4));
        journeysForTesting.add(journeys.get(5));
        journeysForTesting.add(journeys.get(6));
        journeysForTesting.add(journeys.get(7));
        CostCalculator calculator = new CostCalculator();
        assertTrue(calculator.calculateSum(journeysForTesting).equals(OFF_PEAK_CAP));
    }


    @Test
    public void testCustomerInfoPrint() {
        System.out.print("Customer: " + customer.fullName() + " - " + customer.cardId());
        assertEquals("Customer: " + customer.fullName() + " - " + customer.cardId(), Content.toString());
    }

}