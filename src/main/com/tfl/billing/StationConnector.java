package com.tfl.billing;

import com.oyster.OysterCardReader;

public class StationConnector {
    private cardTracker scanner;

    public StationConnector(cardTracker scanner){
        this.scanner = scanner;
    }

    public void connect(OysterCardReader... cardReaders) {
        for (OysterCardReader cardReader : cardReaders) {
            cardReader.register(scanner);
        }
    }

}
