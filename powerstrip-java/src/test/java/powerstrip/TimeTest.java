/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package powerstrip;

import java.time.LocalDateTime;
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
public class TimeTest {
    
    public TimeTest() {
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

    @Test
    public void testBuilder() {
        assertEquals(Time.build().time(),new Time(0,0,0,0,0));
        assertEquals(Time.build().day(1).time(),new Time(1,0,0,0,0));
        assertEquals(Time.build().hour(12).time(),new Time(12,0,0));
        assertEquals(Time.build().minute(30).time(),new Time(0,30,0));
        assertEquals(Time.build().second(15).time(),new Time(0,0,15));
        assertEquals(Time.build().nanosecond(150_450_850).time(),new Time(0,0,0,0,150_450_850));
        assertEquals(Time.build().day(1).hour(12).minute(30).second(15).nanosecond(150_450_850).time(),new Time(1,12,30,15,150_450_850));
    }
    /**
     * Test of getDay method, of class Time.
     */
    @Test
    public void testGetFields() {
        int day = 2;
        int hour = 15;
        int minute = 30;
        int second = 27;
        int nanosecond = 345_673;
        Time time = new Time(day,hour,minute,second,nanosecond);
        assertEquals(day,time.getDay());
        assertEquals(hour,time.getHour());
        assertEquals(minute,time.getMinute());
        assertEquals(second,time.getSecond());
        assertEquals(nanosecond,time.getNanosecond());
    }

    @Test
    public void testGetTotalNanoseconds() {
        int day = 2;
        int hour = 15;
        int minute = 30;
        int second = 27;
        int nanosecond = 345_673_876;
        Time time = new Time(day,hour,minute,second,nanosecond);
        long total = day*24L*60L*60L*1_000_000_000L
                + hour*60L*60L*1_000_000_000L
                + minute*60L*1_000_000_000L
                + second*1_000_000_000L
                + nanosecond;
        assertEquals(total,time.getTotalNanoseconds());
    }

    /**
     * Test of compareTo method, of class Time.
     */
    @Test
    public void testCompareTo() {
        Time[] a = new Time[] { new Time(0,0,0), new Time(1,30,15), new Time(22,18,05), new Time(24,0,0) };
        for (int i=0; i<a.length; ++i) {
            for (int j=0; j<a.length; ++j) {
                int expect = (i < j) ? -1 : (i == j) ? 0 : 1;
                assertEquals(expect,a[i].compareTo(a[j]));
                assertEquals(i==j, a[i].equals(a[j]));
            }
        }
    }
    /**
     * Test of ofDay method, of class Time.
     */
    @Test
    public void testOfDay() {
        System.out.println("ofDay");
        LocalDateTime date = LocalDateTime.of(2010,Month.APRIL,13,9,30,22,484_848_222);
        Time expect = new Time(0,date.getHour(),date.getMinute(),date.getSecond(),date.getNano());
        
        Time result = Time.ofDateTime(date);
        assertEquals(expect, result);
    }
}
