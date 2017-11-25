package com.tfl.billing;
import com.tfl.external.CustomerDatabase;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.UUID;

public class CardScannerTest {

    TravelTracker tracker = new TravelTracker();
    @Test(expected = UnknownOysterCardException.class)
    public void testUnknownOyster(){
        UUID fakeCardId = UUID.fromString("38411111-8cf0-11bd-b23e-10b96e4ef00d");
        UUID fakeReaderId = UUID.fromString("38422222-8cf0-11bd-b23e-10b96e4ef00d");
        tracker.cardScanned(fakeCardId,fakeReaderId);
    }

    @Test
    public void testKnownOysterEvokesTouchMethod(){
        UUID cardId = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"); // Fred Blog
        assertTrue(CustomerDatabase.getInstance().isRegisteredId(cardId));
    }

}