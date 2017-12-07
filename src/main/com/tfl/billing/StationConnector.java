package com.tfl.billing;

import com.oyster.OysterCardReader;

public class StationConnector {
    private CardTracker scanner;

    public StationConnector(CardTracker scanner){
        this.scanner = scanner;
    }

    public void connect(OysterCardReader... cardReaders) {
        for (OysterCardReader cardReader : cardReaders) {
            cardReader.register(scanner);
        }
    }

}
