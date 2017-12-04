package com.tfl.billing;

import java.util.UUID;

public abstract class JourneyEvent {

    private final UUID cardId;
    private final UUID readerId;
    private final long clock;

    public JourneyEvent(UUID cardId, UUID readerId, Clock clock) {
        this.cardId = cardId;
        this.readerId = readerId;
        this.clock = clock.time();
    }


    public long time() { return clock; }

    public UUID cardId() {
        return cardId;
    }

    public UUID readerId() {
        return readerId;
    }

}
