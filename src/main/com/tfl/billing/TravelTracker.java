package com.tfl.billing;

import com.oyster.*;
import com.tfl.external.CustomerDatabase;

import java.util.*;

public class TravelTracker implements ScanListener {

    private final Set<UUID> currentlyTravelling = new HashSet<UUID>();


    public void connect(OysterCardReader... cardReaders) {
        for (OysterCardReader cardReader : cardReaders) {
            cardReader.register(this);
        }
    }

    @Override
    public void cardScanned(UUID cardId, UUID readerId) {
        EventLogger eventLog = EventLogger.getInstance();

        if (currentlyTravelling.contains(cardId)) {
            eventLog.add(new JourneyEnd(cardId, readerId));
            currentlyTravelling.remove(cardId);
        } else {
            //checked
            if (CustomerDatabase.getInstance().isRegisteredId(cardId)) {
                currentlyTravelling.add(cardId);
                eventLog.add(new JourneyStart(cardId, readerId));
            } else {
                //checked
                throw new UnknownOysterCardException(cardId);
            }
        }
    }



}
