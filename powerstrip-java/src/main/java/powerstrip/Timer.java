/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package powerstrip;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 * @author wmacevoy
 */
public class Timer {
    private Clock _clock;
    
    Timer(Clock clock) { _clock = clock; }
    Timer() { _clock = Clock.DEFAULT_CLOCK; }
    public Clock getClock() { return _clock; }
    public void setClock(Clock clock) { _clock=clock; }
    
    private Time _on;
    
    public static final Time TIME_0H = Time.build().time();
    public static final Time TIME_24H = Time.build().hour(24).time();
    public void setTimeOn(Time value) {
        boolean tooSmall = value.compareTo(TIME_0H) < 0;
        boolean tooLarge = value.compareTo(TIME_24H) > 0;
        if (tooSmall || tooLarge) {
            throw new IllegalArgumentException();
        }
        _on = value;
    };
    public Time getTimeOn() { 
        return _on;
    }
    
    private Time _off;
    public void setTimeOff(Time value) {
        if (value.compareTo(TIME_0H) < 0 || value.compareTo(TIME_24H) > 0) {
            throw new IllegalArgumentException();
        }
        _off = value;
    };
    public Time getTimeOff() { 
        return _off;
    }
    
    private Duration _delta = Duration.ZERO;
    public void setDateTime(LocalDateTime now) {
        _delta = Duration.between(_clock.now(),now);
    }; 
    
    public LocalDateTime getDateTime() {
        return _clock.now().plusSeconds(_delta.getSeconds()).plusNanos(_delta.getNano());
    }
    
    public boolean isActive() {
        LocalDateTime now = getDateTime();
        Time time = Time.ofDateTime(now);
        
        if (_on.compareTo(_off) <= 0) return (_on.compareTo(time) <= 0) && (time.compareTo(_off) < 0);
        else return (time.compareTo(_off) < 0) || (time.compareTo(_on) >= 0);
    }
}
