package com.tfl.billing;

import com.oyster.*;

import java.util.*;

public class cardTracker implements ScanListener{
    private final Set<UUID> currentlyTravelling = new HashSet<UUID>();
    private final CustomerDatabaseIF database;

    public cardTracker(CustomerDatabaseIF database) {
        this.database = database;
    }

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
