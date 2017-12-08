package com.tfl.billing;

import java.math.BigDecimal;
import java.util.*;

public class CostCalculator {
    private static final BigDecimal PEAK_LONG_PRICE = new BigDecimal(3.80);
    private static final BigDecimal PEAK_SHORT_PRICE = new BigDecimal(2.90);
    private static final BigDecimal OFF_PEAK_LONG_PRICE = new BigDecimal(2.70);
    private static final BigDecimal OFF_PEAK_SHORT_PRICE = new BigDecimal(1.60);
    private static final BigDecimal PEAK_CAP = new BigDecimal(9.00);
    private static final BigDecimal OFF_PEAK_CAP = new BigDecimal(7.00);
    private boolean peakFlag = false;

    public BigDecimal calculateSingleJourney(JourneyType type){
        switch(type){
            case PEAK_LONG:
                return PEAK_LONG_PRICE;
            case PEAK_SHORT:
                return PEAK_SHORT_PRICE;
            case OFF_PEAK_LONG:
                return OFF_PEAK_LONG_PRICE;
            case OFF_PEAK_SHORT:
                return OFF_PEAK_SHORT_PRICE;
        }
        return PEAK_LONG_PRICE;
    }

    public BigDecimal calculateSum(List<Journey> journeys){
        BigDecimal totalBill = new BigDecimal(0);
        for(Journey journey : journeys){
            if(journey.getTimeIsPeak()){
                peakFlag = true;
            }
            totalBill = totalBill.add(journey.getJourneyPrice());
        }
        return cap(roundToNearestPenny(totalBill));
    }

    private BigDecimal cap(BigDecimal totalBill){
        if(!peakFlag){
            return totalBill.compareTo(OFF_PEAK_CAP) < 1 ? totalBill:OFF_PEAK_CAP;
        }
        else{
            return totalBill.compareTo(PEAK_CAP) < 1 ? totalBill:PEAK_CAP;
        }

    }

    private BigDecimal roundToNearestPenny(BigDecimal poundsAndPence) {
        return poundsAndPence.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

}
