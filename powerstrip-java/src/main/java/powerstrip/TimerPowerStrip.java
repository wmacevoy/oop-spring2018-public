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
public class TimerPowerStrip extends PowerStrip {
    private Timer timer;
    public void setTimeOn(Time on) { timer.setTimeOn(on); }
    public Time getTimeOn() { return timer.getTimeOn(); }
    public void setTimeOff(Time off) { timer.setTimeOff(off); }
    public Time getTimeOff() { return timer.getTimeOff(); }
    public void setDateTime(LocalDateTime now) { timer.setDateTime(now); }
    public LocalDateTime getDateTime() { return timer.getDateTime(); }
    
    public TimerPowerStrip(int _outlets) {
        this(_outlets,Clock.DEFAULT_CLOCK);
    }
    
    public TimerPowerStrip(int _outlets, Clock clock) {
        super(_outlets);
        timer = new Timer(clock);
        timer.setTimeOff(Time.ofHMS(0,0,0));
        timer.setTimeOn(Time.ofHMS(24,0,0));
        mode = Mode.REMOTE;
        for (int outlet=0; outlet < outlets; ++outlet) {
            outletStates[outlet]=OutletState.POWERED;
        }
    }
    
    public OutletState getOutletState(int outlet) {
        outletOk(outlet);
        switch(getMode()) {
            case ON: return OutletState.POWERED;
            case OFF: return OutletState.UNPOWERED;
            case REMOTE: return timer.isActive() ? outletStates[outlet] : OutletState.UNPOWERED;
        }
        throw new IllegalStateException();
    }
}
