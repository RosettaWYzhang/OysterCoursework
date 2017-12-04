package com.tfl.billing;

import java.util.List;

public interface EventLoggerIF {
    void add(JourneyEvent event);
    List<JourneyEvent> getEventLog();
}
