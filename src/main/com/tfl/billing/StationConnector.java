package com.tfl.billing;

import com.oyster.OysterCardReader;

public class StationConnector {
    private TravelTracker scanner;

    public StationConnector(TravelTracker scanner){
        this.scanner = scanner;
    }

    public void connect(OysterCardReader... cardReaders) {
        for (OysterCardReader cardReader : cardReaders) {
            cardReader.register(scanner);
        }
    }

}
