package com.tfl.billing;


import java.util.UUID;

public class JourneyBuilder {
    private UUID cardID = UUID.randomUUID();
    private UUID readerID = UUID.randomUUID();
    ControllableClock clockStart = new ControllableClock();
    ControllableClock clockEnd = new ControllableClock();
    private JourneyEvent start = new JourneyStart(cardID, readerID, clockStart);
    private JourneyEvent end = new JourneyEnd(cardID, readerID, clockEnd);

    private JourneyBuilder(){}

    public static JourneyBuilder aJourney(){
        return new JourneyBuilder();
    }

    public Journey build(){
        Journey journey = new Journey(start, end);
        return journey;
    }

    public JourneyBuilder withStartTime(int hours, int minutes){
        clockStart.setTime(hours, minutes);
        this.start = new JourneyStart(cardID, readerID, clockStart);
        return this;
    }

    public JourneyBuilder withEndTime(int hours, int minutes){
        clockEnd.setTime(hours, minutes);
        this.end = new JourneyEnd(cardID, readerID, clockEnd);
        return this;
    }


}



