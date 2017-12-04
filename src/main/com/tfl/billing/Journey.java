package com.tfl.billing;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class Journey{
    private final JourneyEvent start;
    private final JourneyEvent end;
    private final JourneyType journeyType;
    private final BigDecimal journeyPrice;
    private boolean isPeak;
    private boolean isLong;


    public Journey(JourneyEvent start, JourneyEvent end) {
        this.start = start;
        this.end = end;
        this.journeyType = determineType();
        this.journeyPrice = determinePrice();
    }

    public String formattedStartTime() {
        return new DateFormatter().format(start.time());
    }

    public String formattedEndTime() {
        return new DateFormatter().format(end.time());
    }

    public UUID originId() {
        return start.readerId();
    }

    public UUID destinationId() {
        return end.readerId();
    }

    public Date startTime() {
        return new Date(start.time());
    }

    public Date endTime() {
        return new Date(end.time());
    }

    private void checkDurationIsLong(){
        DurationChecker durationChecker = new DurationChecker();
        this.isLong = durationChecker.isLong(this);
    }

    private void checkTimeIsPeak(){
        TimeChecker timeChecker = new TimeChecker();
        this.isPeak = timeChecker.isPeak(this);
    }

    public boolean getTimeIsPeak(){
        return isPeak;
    }

    public boolean getDurationIsLong(){
        return isLong;
    }

    private JourneyType determineType(){
        checkDurationIsLong();
        checkTimeIsPeak();
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

    private BigDecimal determinePrice(){
        CostCalculator calculator = new CostCalculator();
        return calculator.calculateSingleJourney(journeyType);
    }

    public BigDecimal getJourneyPrice(){
        return journeyPrice;
    }
    public JourneyEvent getJourneyStart(){
        return start;
    }
    public JourneyEvent getJourneyEnd(){
        return end;
    }
    public JourneyType getJourneyType(){
        return journeyType;
    }

}
