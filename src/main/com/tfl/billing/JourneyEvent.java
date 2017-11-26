package com.tfl.billing;

import java.util.Date;
import java.util.UUID;

public abstract class JourneyEvent{

    private final UUID cardId;
    private final UUID readerId;
    private Clock clock;

    public JourneyEvent(UUID cardId, UUID readerId, Clock clock) {
        this.cardId = cardId;
        this.readerId = readerId;
        this.clock = clock;
    }

    public long time() {
        return clock.time();

    }


    public UUID cardId() {
        return cardId;
    }

    public UUID readerId() {
        return readerId;
    }

}
