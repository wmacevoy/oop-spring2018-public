/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package powerstrip;

import java.time.LocalDateTime;

/**
 *
 * @author wmacevoy
 */
public class Time implements Comparable<Time> {

    public static final long DAY_NS = 24L * 60L * 60L * 1_000_000_000L;
    public static final long HOUR_NS = 60L * 60L * 1_000_000_000L;
    public static final long MINUTE_NS = 60L * 1_000_000_000L;
    public static final long SECOND_NS = 1_000_000_000L;
    public static final long NANOSECOND_NS = 1L;
    public static final Time DAY = new Time(DAY_NS);

    public static final int MAX_DAYS = 106_751;
    private long _nanoseconds;

    public Time(long nanoseconds) {
        _nanoseconds = nanoseconds;
    }

    public Time(int day, int hour, int minute, int second, int nanosecond) {
        if (Math.abs(day) > MAX_DAYS
                || Math.abs(day + hour / 24 + minute / (24 * 60) + second / (24 * 60 * 60)) > MAX_DAYS) {
            throw new IllegalArgumentException("time must be be less than 106_751 days");
        }
        _nanoseconds = ((((day * 24L) + hour) * 60L + minute) * 60L + second) * 1_000_000_000L + nanosecond;
    }

    public Time(int hour, int minute, int second) {
        this(0, hour, minute, second, 0);
    }

    public int getDay() {
        return (int) (_nanoseconds / DAY_NS);
    }

    public int getHour() {
        return (int) ((_nanoseconds % DAY_NS) / HOUR_NS);
    }

    public int getMinute() {
        return (int) ((_nanoseconds % HOUR_NS) / MINUTE_NS);
    }

    public int getSecond() {
        return (int) ((_nanoseconds % MINUTE_NS) / SECOND_NS);
    }

    public int getNanosecond() {
        return (int) ((_nanoseconds % SECOND_NS) / NANOSECOND_NS);
    }

    @Override
    public int compareTo(Time to) {
        if (_nanoseconds < to._nanoseconds) {
            return -1;
        }
        if (_nanoseconds > to._nanoseconds) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object to) {
        return _nanoseconds == ((Time) to)._nanoseconds;
    }

    public static Time ofDay(LocalDateTime now) {
        return new Time(0, now.getHour(), now.getMinute(), now.getSecond(), now.getNano());
    }
    
    public static Time ofHMS(int hour, int minute, int second) {
        return new Time(0,hour,minute,second,0);
    }

    public long getTotalNanoseconds() {
        return _nanoseconds;
    }

}
