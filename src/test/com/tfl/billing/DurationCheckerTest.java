package com.tfl.billing;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;


public class DurationCheckerTest {
    private ControllableClock clockStart = new ControllableClock();
    private ControllableClock clockEnd = new ControllableClock();
    private DurationChecker durationChecker = new DurationChecker();

    @Test
    public void testDurationIsShort(){
        clockStart.setTime(11,25);
        clockEnd.setTime(11,35);
        assertTrue(!durationChecker.isLong(clockStart, clockEnd));
    }

    @Test
    public void testDurationIsLong(){
        clockStart.setTime(11,25);
        clockEnd.setTime(11,55);
        assertTrue(durationChecker.isLong(clockStart, clockEnd));
    }


}