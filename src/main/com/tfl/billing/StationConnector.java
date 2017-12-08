package com.tfl.billing;

import com.oyster.OysterCardReader;

public class StationConnector {
    private CardTracker tracker;

    public StationConnector(CardTracker tracker){
        this.tracker = tracker;
    }

    public void connect(OysterCardReader... cardReaders) {
        for (OysterCardReader cardReader : cardReaders) {
            cardReader.register(tracker);
        }
    }

}
