package com.tfl.billing;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertTrue;

public class JourneyPriceTest {
    private static final BigDecimal PEAK_LONG_PRICE = new BigDecimal(3.80);
    private static final BigDecimal PEAK_SHORT_PRICE = new BigDecimal(2.90);
    private static final BigDecimal OFF_PEAK_LONG_PRICE = new BigDecimal(2.70);
    private static final BigDecimal OFF_PEAK_SHORT_PRICE = new BigDecimal(1.60);
    private static final BigDecimal PEAK_CAP = new BigDecimal(9.00);
    private static final BigDecimal OFF_PEAK_CAP = new BigDecimal(7.00);
    private ControllableClock clockStart = new ControllableClock();
    private ControllableClock clockEnd = new ControllableClock();


    @Test
    public void testPriceForOffPeakLongHour(){
        clockStart.setTime(11, 0);
        clockEnd.setTime(12, 0);
        BigDecimal price = new CostCalculator().determinePrice(clockStart, clockEnd);
        assertTrue(price.equals(OFF_PEAK_LONG_PRICE));
    }

    @Test
    public void testPriceForOffPeakShortHour(){
        clockStart.setTime(13, 23);
        clockEnd.setTime(13, 25);
        BigDecimal price = new CostCalculator().determinePrice(clockStart, clockEnd);
        assertTrue(price.equals(OFF_PEAK_SHORT_PRICE));
    }


    @Test
    public void testPriceForPeakShortHour(){
        clockStart.setTime(8, 0);
        clockEnd.setTime(8, 1);
        BigDecimal price = new CostCalculator().determinePrice(clockStart, clockEnd);
        assertTrue(price.equals(PEAK_SHORT_PRICE));
    }

    @Test
    public void testPriceForPeakLongHour(){
        clockStart.setTime(17, 0);
        clockEnd.setTime(18, 1);
        BigDecimal price = new CostCalculator().determinePrice(clockStart, clockEnd);
        assertTrue(price.equals(PEAK_LONG_PRICE));
    }

    @Test
    public void testListOfJourneyExceedPeakCap(){
        List<Journey> journeys = new ArrayList<Journey>();

        UUID cardID = UUID.randomUUID();
        UUID readerID = UUID.randomUUID();

        clockStart.setTime(9, 10);
        clockEnd.setTime(10, 20);
        JourneyEvent start1 = new JourneyStart(cardID, readerID, clockStart);
        JourneyEvent end1 = new JourneyStart(cardID, readerID, clockEnd);
        Journey journey1 = new Journey(start1, end1);
        journeys.add(journey1);

        clockStart.setTime(12, 10);
        clockEnd.setTime(13, 20);
        JourneyEvent start2 = new JourneyStart(cardID, readerID, clockStart);
        JourneyEvent end2 = new JourneyStart(cardID, readerID, clockEnd);
        Journey journey2 = new Journey(start2, end2);
        journeys.add(journey2);

        clockStart.setTime(17, 10);
        clockEnd.setTime(18, 20);
        JourneyEvent start3 = new JourneyStart(cardID, readerID, clockStart);
        JourneyEvent end3 = new JourneyStart(cardID, readerID, clockEnd);
        Journey journey3 = new Journey(start3, end3);
        journeys.add(journey3);


        CostCalculator calculator = new CostCalculator(journeys);
        assertTrue(calculator.calculateSum().equals(PEAK_CAP));

    }



    @Test
    public void testListOfJourneyDoesNotExceedOffPeakCap(){
        List<Journey> journeys = new ArrayList<Journey>();

        UUID cardID = UUID.randomUUID();
        UUID readerID = UUID.randomUUID();

        clockStart.setTime(10, 10);
        clockEnd.setTime(10, 11);
        JourneyEvent start1 = new JourneyStart(cardID, readerID, clockStart);
        JourneyEvent end1 = new JourneyStart(cardID, readerID, clockEnd);
        Journey journey1 = new Journey(start1, end1);
        journeys.add(journey1);

        clockStart.setTime(12, 10);
        clockEnd.setTime(12, 12);
        JourneyEvent start2 = new JourneyStart(cardID, readerID, clockStart);
        JourneyEvent end2 = new JourneyStart(cardID, readerID, clockEnd);
        Journey journey2 = new Journey(start2, end2);
        journeys.add(journey2);

        CostCalculator calculator = new CostCalculator(journeys);
        assertTrue(!calculator.calculateSum().equals(OFF_PEAK_CAP));

    }

    @Test
    public void testListOfJourneyDoesNotExceedPeakCap(){
        List<Journey> journeys = new ArrayList<Journey>();

        UUID cardID = UUID.randomUUID();
        UUID readerID = UUID.randomUUID();

        clockStart.setTime(8, 10);
        clockEnd.setTime(8, 11);
        JourneyEvent start1 = new JourneyStart(cardID, readerID, clockStart);
        JourneyEvent end1 = new JourneyStart(cardID, readerID, clockEnd);
        Journey journey1 = new Journey(start1, end1);
        journeys.add(journey1);

        clockStart.setTime(12, 10);
        clockEnd.setTime(12, 12);
        JourneyEvent start2 = new JourneyStart(cardID, readerID, clockStart);
        JourneyEvent end2 = new JourneyStart(cardID, readerID, clockEnd);
        Journey journey2 = new Journey(start2, end2);
        journeys.add(journey2);

        CostCalculator calculator = new CostCalculator(journeys);
        assertTrue(!calculator.calculateSum().equals(PEAK_CAP));

    }

}