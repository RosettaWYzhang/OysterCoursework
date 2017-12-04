package com.tfl.billing;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MockEventLogger implements EventLoggerIF{
    private static MockEventLogger instance = new MockEventLogger();
    private final List<JourneyEvent> eventLog = new ArrayList<JourneyEvent>();

    private MockEventLogger(){}

    public static MockEventLogger getInstance(){
        return instance;
    }

    @Override
    public void add(JourneyEvent event){
        eventLog.add(event);
    }

    @Override
    public List<JourneyEvent> getEventLog(){
        return eventLog;
    }

    public void deleteAllEvents(){
        int eventNum = eventLog.size();
        for(int i = 0; i < eventNum; i++){
            eventLog.remove(0);
        }
    }
}
