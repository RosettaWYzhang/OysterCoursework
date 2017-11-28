package com.tfl.billing;

import com.oyster.OysterCard;
import org.junit.Test;

import java.util.UUID;

import static junit.framework.TestCase.assertEquals;

public class OysterCardTest {
    @Test
    public void TestOysterCardID(){
        OysterCard oysterCard = new OysterCard("38400000-8cf0-11bd-b23e-10b96e4ef00d");
        UUID cardId = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
        assertEquals(oysterCard.id(),cardId);
    }
}
