package com.tfl.billing;

import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class JourneyStartTest {
    private final UUID cardId_test = UUID.randomUUID();
    private final UUID readerId_test = UUID.randomUUID();
    Clock clock = new SystemClock();
    private JourneyEvent newEvent = new JourneyStart(cardId_test, readerId_test,clock);



    @Test
    public void returnCardIdTest() {
        assertThat(newEvent.cardId(), is(cardId_test));
    }

    @Test
    public void returnReaderIdTest(){
        assertThat(newEvent.readerId(),is(readerId_test));
    }




}