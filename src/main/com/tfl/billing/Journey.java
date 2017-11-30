package com.tfl.billing;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class Journey {

    private final JourneyEvent start;
    private final JourneyEvent end;

    public Journey(JourneyEvent start, JourneyEvent end) {
        this.start = start;
        this.end = end;
    }

    public UUID originId() {
        return start.readerId();
    }

    public UUID destinationId() {
        return end.readerId();
    }

    public String formattedStartTime() {
        return new Formatter(start.time()).format();
    }

    public String formattedEndTime() {
        return new Formatter(end.time()).format();
    }

    public Date startTime() {
        return new Date(start.time());
    }

    public Date endTime() {
        return new Date(end.time());
    }

    //rationale: a journey should know its own type, so that we can eliminate some if/else in costCalculator
    //however, I dont think it's a good design as later we need to ask the journey for its own price,
    // break tell and dont ask principle
    // need to be deleted
    public JourneyType determineType(){
        DurationChecker durationChecker = new DurationChecker();
        TimeChecker timeChecker = new TimeChecker();
        boolean isLong = durationChecker.isLong(this);
        boolean isPeak = timeChecker.isPeak(this);
        if(isLong && isPeak){
            return JourneyType.PEAK_LONG;
        }
        else if(isPeak && !isLong){
            return JourneyType.PEAK_SHORT;
        }
        else if(!isPeak && isLong){
            return JourneyType.OFF_PEAK_LONG;
        }
        else
            return JourneyType.OFF_PEAK_SHORT;

    }


    // Shoud a journey should know its own price?

    private BigDecimal roundToNearestPenny(BigDecimal poundsAndPence) {
        return poundsAndPence.setScale(2, BigDecimal.ROUND_HALF_UP);
    }



}
