package com.tfl.billing;

import com.oyster.*;

import java.util.*;

public class TravelTracker implements ScanListener{
    private final Set<UUID> currentlyTravelling = new HashSet<UUID>();
    private final CustomerDatabaseIF database;

    public TravelTracker(CustomerDatabaseIF database) {
        this.database = database;
    }

    public void connect(OysterCardReader... cardReaders) {
        for (OysterCardReader cardReader : cardReaders) {
            cardReader.register(this);
        }
    }

    // called by touch method in OysterCardReader
    // TODO: use polymorphism
    // TODO: card state should have 1 method
    // TODO: in different implementation of the interface, some return exception, some add to eventlogger add call remove, some add to both eventLogger and currently travelling
    @Override
    public void cardScanned(UUID cardId, UUID readerId) {
        EventLogger eventLogger = EventLogger.getInstance();
        SystemClock clock = new SystemClock();

        if (isTravelling(cardId)) {
            eventLogger.add(new JourneyEnd(cardId, readerId, clock));
            currentlyTravelling.remove(cardId);
        } else {
            if (database.isRegisteredId(cardId)) {
                currentlyTravelling.add(cardId);
                eventLogger.add(new JourneyStart(cardId, readerId, clock));
            } else {
                throw new UnknownOysterCardException(cardId);
            }
        }
    }

    public boolean isTravelling(UUID cardId) {
        return currentlyTravelling.contains(cardId);
    }

    public Set<UUID> getCurrentlyTravelling() {
        return currentlyTravelling;
    }



}
