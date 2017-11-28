package com.tfl.billing;

import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


public class JourneyEndTest {
    private final UUID cardId_test = UUID.randomUUID();
    private final UUID readerId_test = UUID.randomUUID();
    private Clock clock;
    private JourneyEvent newEvent = new JourneyEnd(cardId_test, readerId_test,clock);

    @Test
    public void CardIdTest() {
        assertThat(newEvent.cardId(), is(cardId_test));
    }

    @Test
    public void ReaderIdTest(){
        assertThat(newEvent.readerId(),is(readerId_test));
    }



}