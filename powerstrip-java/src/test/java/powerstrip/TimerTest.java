/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package powerstrip;

import java.time.Duration;
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
public class TimerTest {

    public TimerTest() {
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

    /**
     * Test of setTimeOn method, of class Timer.
     */
    LocalDateTime now() {
        return LocalDateTime.now();
    }

    TimeBuilder mkTime() {
        return new TimeBuilder();
    }

    @Test
    public void testSetTimeOn() {
        Timer timer = new Timer();
        Time t = new Time(19, 23, 43);
        timer.setTimeOn(t);
        Time s = timer.getTimeOn();
        assertEquals(s, t);
    }

    /**
     * Test of setTimeOff method, of class Timer.
     */
    @Test
    public void testSetTimeOff() {
        Timer timer = new Timer();
        Time t = new Time(24, 0, 0);
        timer.setTimeOff(t);
        Time s = timer.getTimeOff();
        assertEquals(s, t);
    }

    /**
     * Test of setDatetime method, of class Timer.
     */
    class MockClock extends Clock {

        LocalDateTime _now;

        MockClock(LocalDateTime now) {
            _now = now;
        }

        @Override
        public LocalDateTime now() {
            return _now;
        }
    }

    Timer getTimer() {
        LocalDateTime clockNow = LocalDateTime.of(2013, Month.APRIL, 17, 18, 45, 5, 234_000_123);
        MockClock clock = new MockClock(clockNow);
        Timer timer = new Timer(clock);
        assertEquals(clockNow, timer.getDatetime());
        return timer;
    }

    @Test
    public void testSetDatetime() {
        Timer timer = getTimer();
        LocalDateTime dateTime = LocalDateTime.of(2013, Month.APRIL, 17, 18, 45, 5, 234_000_123);
        timer.setDatetime(dateTime);
        LocalDateTime current = timer.getDatetime();
        assertEquals(current, dateTime);
    }

    private void timerAlways(Timer timer, boolean value) {
        LocalDateTime now = LocalDateTime.of(2023, Month.APRIL, 17, 18, 45, 5, 234_000_123);
        LocalDateTime midnight = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0);

        for (int hour : new int[]{-48, -25, -24, -23, -12, -1, 0, 1, 3, 12, 20, 23, 24, 25, 48}) {
            for (int min : new int[]{-120, -60, -1, 0, 1, 30, 59, 60, 61, 120}) {
                for (int sec : new int[]{-120, -60, -1, 0, 1, 30, 59, 60, 61, 120}) {
                    for (int nan : new int[]{-2_000_000_000, -1_000_000_000, -1, 0, 1, 500_000_000, 999_999_999, 1_000_000_000, 1_000_000_001, 2_000_000_000}) {
                        LocalDateTime at = midnight.plusHours(hour).plusMinutes(min).plusSeconds(sec).plusNanos(nan);
                        timer.setDatetime(at);
                        assertEquals(at, timer.getDatetime());
                        assertEquals(value, timer.isActive());
                    }
                }
            }
        }
    }

    @Test
    public void testTimerAlwaysOn() {
        Timer timer = getTimer();
        timer.setTimeOn(Time.ofHMS(0, 0, 0));
        timer.setTimeOff(Time.ofHMS(24, 0, 0));
        timerAlways(timer, true);
    }

    @Test
    public void testTimerAlwaysOff() {
        Timer timer = getTimer();
        for (int h = 0; h <= 24; ++h) {
            timer.setTimeOn(Time.ofHMS(h, 0, 0));
            timer.setTimeOff(Time.ofHMS(h, 0, 0));
            timerAlways(timer, false);
        }
    }

    @Test
    public void testIsActiveRegression1() {
        Timer timer = getTimer();
        LocalDateTime now = LocalDateTime.of(2013, Month.JUNE, 10, 18, 45, 5, 234_000_123);
        LocalDateTime midnight = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0);

        Time[] times = new Time[]{new Time(0, 0, 0), new Time(1, 30, 15), new Time(22, 18, 05), new Time(24, 0, 0)};

        int i = 0;
        int j = 1;
        timer.setTimeOn(times[i]);
        timer.setTimeOff(times[j]);
        LocalDateTime on = midnight.plusNanos(times[i].getTotalNanoseconds());
        LocalDateTime off = midnight.plusNanos(times[j].getTotalNanoseconds());

        timer.setDatetime(on.minusNanos(1));
        assertFalse(timer.isActive());
        timer.setDatetime(on);

        assertTrue("i=" + i + ",j=" + j, timer.isActive());
        timer.setDatetime(on.plusNanos(1));
        assertTrue("i=" + i + ",j=" + j, timer.isActive());
        timer.setDatetime(off.minusNanos(1));
        assertTrue(timer.isActive());
        timer.setDatetime(off);
        assertFalse(timer.isActive());
        timer.setDatetime(off.plusNanos(1));
        assertFalse(timer.isActive());

    }

    @Test
    public void testIsActive() {
        Timer timer = getTimer();
        LocalDateTime now = LocalDateTime.of(2013, Month.JUNE, 10, 18, 45, 5, 234_000_123);
        LocalDateTime midnight = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0);

        Time[] times = new Time[]{new Time(0, 0, 0), new Time(1, 30, 15), new Time(22, 18, 05), new Time(24, 0, 0)};

        for (int i = 0; i < times.length; ++i) {
            for (int j = 0; j < times.length; ++j) {
                timer.setTimeOn(times[i]);
                timer.setTimeOff(times[j]);
                LocalDateTime on = midnight.plusNanos(times[i].getTotalNanoseconds());
                LocalDateTime off = midnight.plusNanos(times[j].getTotalNanoseconds());
                if (i == j) { // no time
                    timerAlways(timer, false);
                } else if (i == 0 && j == times.length - 1) { // all time
                    timerAlways(timer, true);
                } else {
                    timer.setDatetime(on.minusNanos(1));
                    assertFalse(timer.isActive());
                    timer.setDatetime(on);

                    assertTrue("i=" + i + ",j=" + j, timer.isActive());
                    timer.setDatetime(on.plusNanos(1));
                    assertTrue("i=" + i + ",j=" + j, timer.isActive());
                    timer.setDatetime(off.minusNanos(1));
                    assertTrue(timer.isActive());
                    timer.setDatetime(off);
                    assertFalse(timer.isActive());
                    timer.setDatetime(off.plusNanos(1));
                    assertFalse(timer.isActive());
                }
            }
        }
    }
}
