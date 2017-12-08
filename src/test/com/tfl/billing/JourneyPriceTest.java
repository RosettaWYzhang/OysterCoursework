package com.tfl.billing;

import org.junit.Test;
import java.math.BigDecimal;


import static junit.framework.TestCase.assertTrue;

public class JourneyPriceTest {
    private static final BigDecimal PEAK_LONG_PRICE = new BigDecimal(3.80);
    private static final BigDecimal PEAK_SHORT_PRICE = new BigDecimal(2.90);
    private static final BigDecimal OFF_PEAK_LONG_PRICE = new BigDecimal(2.70);
    private static final BigDecimal OFF_PEAK_SHORT_PRICE = new BigDecimal(1.60);


    @Test
    public void testPriceForOffPeakLongHour(){
        Journey journey = JourneyBuilder.aJourney().withStartTime(11,0).withEndTime(12,0).build();
        BigDecimal price = journey.getJourneyPrice();
        assertTrue(price.equals(OFF_PEAK_LONG_PRICE));
    }


    @Test
    public void testPriceForOffPeakShortHour(){
        Journey journey = JourneyBuilder.aJourney().withStartTime(13,23).withEndTime(13,25).build();
        BigDecimal price = journey.getJourneyPrice();
        assertTrue(price.equals(OFF_PEAK_SHORT_PRICE));
    }


    @Test
    public void testPriceForPeakShortHour(){
        Journey journey = JourneyBuilder.aJourney().withStartTime(8,0).withEndTime(8,1).build();
        BigDecimal price = journey.getJourneyPrice();
        assertTrue(price.equals(PEAK_SHORT_PRICE));
    }


    @Test
    public void testPriceForPeakLongHour(){
        Journey journey = JourneyBuilder.aJourney().withStartTime(17,0).withEndTime(18,1).build();
        BigDecimal price = journey.getJourneyPrice();
        assertTrue(price.equals(PEAK_LONG_PRICE));
    }


}