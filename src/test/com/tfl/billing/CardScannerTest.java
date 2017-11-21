package com.tfl.billing;


import org.junit.Test;

import java.util.UUID;

public class CardScannerTest {

    TravelTracker tracker = new TravelTracker();

    @Test(expected = UnknownOysterCardException.class)
    public void testUnknownOyster(){
        UUID fakeCardID = UUID.fromString("38411111-8cf0-11bd-b23e-10b96e4ef00d");
        UUID fakeReaderID = UUID.fromString("38422222-8cf0-11bd-b23e-10b96e4ef00d");
        tracker.cardScanned(fakeCardID,fakeReaderID);
    }




}
