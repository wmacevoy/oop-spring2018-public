/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package powerstrip;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author wmacevoy
 */
public class TimerPowerStripTest extends PowerStripTest {

    public TimerPowerStripTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    class MockClock extends Clock {

        LocalDateTime _now;

        MockClock(LocalDateTime now) {
            _now = now;
        }

        MockClock() {
            _now = LocalDateTime.now();
        }

        @Override
        public LocalDateTime now() {
            return _now;
        }
    }

    Clock getClock() {
        LocalDateTime clockNow = LocalDateTime.of(2013, Month.APRIL, 17, 18, 45, 5, 234_000_123);
        MockClock mockClock = new MockClock(clockNow);
        return mockClock;
    }

    @Override
    TimerPowerStrip create(int outlets) {
        TimerPowerStrip unit = new TimerPowerStrip(outlets, getClock());
        unit.setTimeOn(Time.ofHMS(0, 0, 0));
        unit.setTimeOff(Time.ofHMS(24, 0, 0));
        unit.setMode(Mode.REMOTE);
        for (int outlet = 0; outlet < outlets; ++outlet) {
            unit.setOutletState(outlet, OutletState.UNPOWERED);
        }
        return unit;
    }

    @Override
    TimerPowerStrip create() {
        return create(2);
    }

    @Test
    public void testUnit() {
        TimerPowerStrip unit = create();
        testUnit(unit);
    }

    void testUnit(TimerPowerStrip unit) {
        LocalDateTime now = LocalDateTime.of(2001, Month.FEBRUARY, 12, 15, 12, 16, 123_543_184);
        LocalDateTime today = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0, 0);
        for (Mode mode : Mode.values()) {
            unit.setMode(mode);
            assertEquals(mode, unit.getMode());
            for (int hOn = -1; hOn <= 25; ++hOn) {
                try {
                    unit.setTimeOn(Time.ofHMS(hOn, 0, 0));
                } catch (IllegalArgumentException ex) {
                    assertTrue(hOn < 0 || hOn > 24);
                    continue;
                }
                assertEquals(Time.ofHMS(hOn, 0, 0), unit.getTimeOn());
                for (int hOff = -1; hOff <= 25; ++hOff) {
                    try {
                        unit.setTimeOff(Time.ofHMS(hOff, 0, 0));
                    } catch (IllegalArgumentException ex) {
                        assertTrue(hOff < 0 || hOff > 24);
                        continue;
                    }
                    assertEquals(Time.ofHMS(hOff, 0, 0), unit.getTimeOff());

                    for (int h = -24; h <= 48; ++h) {
                        for (int m = 0; m < 60; m += 15) {
                            LocalDateTime t = today.plusHours(h).plusMinutes(m);
                            unit.setDateTime(t);
                            assertEquals(t, unit.getDateTime());

                            for (OutletState outletStateSet : OutletState.values()) {
                                for (int outlet = -1; outlet <= unit.getOutlets(); ++outlet) {
                                    try {
                                        unit.setOutletState(outlet, outletStateSet);
                                    } catch (IndexOutOfBoundsException ex) {
                                        assertTrue(outlet < 0 || outlet >= unit.getOutlets());
                                        continue;
                                    }
                                    OutletState outletStateGet = unit.getOutletState(outlet);

                                    switch (mode) {
                                        case OFF:
                                            assertEquals(OutletState.UNPOWERED, outletStateGet);
                                            break;
                                        case ON:
                                            assertEquals(OutletState.POWERED, outletStateGet);
                                            break;
                                        case REMOTE:
                                            if (outletStateSet == OutletState.UNPOWERED) {
                                                assertEquals(OutletState.UNPOWERED, outletStateGet);
                                            } else {
                                                boolean active;
                                                int mOn = hOn * 60;
                                                int mOff = hOff * 60;
                                                int mins = ((h + 24) % 24)*60 + m;
                                                if (mOn > mOff) {
                                                    active = mins < mOff || mins >= mOn;
                                                } else {
                                                    active = mins >= mOn && mins < mOff;
                                                }
                                                OutletState expect = active ? OutletState.POWERED : OutletState.UNPOWERED;
                                                assertEquals(expect, outletStateGet);

                                            }
                                    }
                                }

                            }
                        }
                    }
                }
            }

        }
    }
}
