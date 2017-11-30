package com.tfl.billing;

import java.math.BigDecimal;
import java.util.*;

public class CostCalculator {
    //problem with too many ifs, feels too procedural

    private static final BigDecimal PEAK_LONG_PRICE = new BigDecimal(3.80);
    private static final BigDecimal PEAK_SHORT_PRICE = new BigDecimal(2.90);
    private static final BigDecimal OFF_PEAK_LONG_PRICE = new BigDecimal(2.70);
    private static final BigDecimal OFF_PEAK_SHORT_PRICE = new BigDecimal(1.60);
    private static final BigDecimal PEAK_CAP = new BigDecimal(9.00);
    private static final BigDecimal OFF_PEAK_CAP = new BigDecimal(7.00);
    static List<Journey> journeys = new ArrayList<Journey>();
    private boolean peakFlag = false;


    public CostCalculator(List<Journey> journeys){
        this.journeys = journeys;
    }

    public CostCalculator(Journey journey){
        journeys.add(journey);
    }

    public CostCalculator(){

    }

    //seems useless at this stage
    public BigDecimal calculateSingleJourney(Journey journey){
        JourneyType type = journey.determineType();
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

    // I think it's not very
    public BigDecimal calculateSum(){
        BigDecimal totalPrice = new BigDecimal(0);
        for(Journey journey : journeys) {
            totalPrice = totalPrice.add(determinePrice(journey));
        }
        return cap(totalPrice);
    }

    //needed by customer summary, but should this return void?
    /*public BigDecimal calculateSum(){
        BigDecimal totalPrice = new BigDecimal(0);
        for(Journey journey : journeys) {
            totalPrice = totalPrice.add(determinePrice(journey));
        }
        return cap(totalPrice);
    }*/

    private BigDecimal cap(BigDecimal totalPrice){
        if(!peakFlag){
            return totalPrice.compareTo(OFF_PEAK_CAP) < 1 ? totalPrice:OFF_PEAK_CAP;
        }
        else{
            return totalPrice.compareTo(PEAK_CAP) < 1 ? totalPrice:PEAK_CAP;
        }

    }

    //should delete the method
    //pass time directly to the argument to minimise dependency
    public BigDecimal determinePrice(Journey journey){
        TimeChecker timeChecker = new TimeChecker(); // is timechecker necessary? consider a utility function?
        DurationChecker durationChecker = new DurationChecker();
        boolean isPeak = timeChecker.isPeak(journey);
        boolean isLong = durationChecker.isLong(journey);

        BigDecimal journeyPrice = OFF_PEAK_SHORT_PRICE;
        if (isPeak && isLong) {
            journeyPrice = PEAK_LONG_PRICE;
            peakFlag = true;
        }
        else if(isPeak && !isLong){
            journeyPrice = PEAK_SHORT_PRICE;
            peakFlag = true;
        }
        else if(!isPeak && isLong){
            journeyPrice = OFF_PEAK_LONG_PRICE;
        }
        else{
            journeyPrice = OFF_PEAK_SHORT_PRICE;
        }
        return journeyPrice;
    }

    //should this method accept time as argument?
    public BigDecimal determinePrice(Clock clockStart, Clock clockEnd){
        TimeChecker timeChecker = new TimeChecker(); // is timechecker necessary? consider a utility function?
        DurationChecker durationChecker = new DurationChecker();
        boolean isPeak = timeChecker.isPeak(clockStart) || timeChecker.isPeak(clockEnd) ;
        boolean isLong = durationChecker.isLong(clockStart, clockEnd);

        BigDecimal journeyPrice = OFF_PEAK_SHORT_PRICE;
        if (isPeak && isLong) {
            journeyPrice = PEAK_LONG_PRICE;
            peakFlag = true;
        }
        else if(isPeak && !isLong){
            journeyPrice = PEAK_SHORT_PRICE;
            peakFlag = true;
        }
        else if(!isPeak && isLong){
            journeyPrice = OFF_PEAK_LONG_PRICE;
        }
        else{
            journeyPrice = OFF_PEAK_SHORT_PRICE;
        }
        return journeyPrice;
    }


}
