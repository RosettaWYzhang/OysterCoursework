package com.tfl.billing;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class JourneyStartTest {

    private final UUID cardId_test = UUID.randomUUID();
    private final UUID readerId_test = UUID.randomUUID();
    Clock clock = new SystemClock();

    private JourneyEvent newEvent = new JourneyStart(cardId_test, readerId_test,clock);

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    Clock mockClock = context.mock(Clock.class);


    @Test
    public void testCardIdIsAddedInJourneyStart() {
        assertThat(newEvent.cardId(), is(cardId_test));
    }

    @Test
    public void testReaderIdIsAddedInJourneyStart(){
        assertThat(newEvent.readerId(),is(readerId_test));
    }

    @Test
    public void testJourneyStartSetsClockTime(){
        context.checking(new Expectations() {{
            exactly(1).of(mockClock).time();
        }});
        new JourneyStart(newEvent.cardId(), newEvent.readerId(), mockClock);
    }




}