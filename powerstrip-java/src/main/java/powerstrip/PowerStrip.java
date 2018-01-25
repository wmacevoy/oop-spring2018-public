/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package powerstrip;

/**
 *
 * @author wmacevoy
 */
public class PowerStrip {
    
    public static final int MODE_ON = 0;
    
    private int outlets;
    private OutletState [] outletStates;
    private Mode mode;
    

    public PowerStrip(int _outlets) {
        if (_outlets <= 0) {
            throw new IllegalArgumentException("outlets (" + _outlets + ") must be positive.");
        }
        outlets=_outlets;
        outletStates = new OutletState[outlets];
        for (int outlet = 0; outlet < outlets; ++outlet) {
            outletStates[outlet] = OutletState.UNPOWERED;
        }
    }
    public int getOutlets() { return outlets; }   

    public void setMode(Mode _mode) {
        mode = _mode;
    }
    
    public Mode getMode() {
        return mode;
    }
    
    public void outletOk(int outlet) {
        if (outlet < 0 || outlet >= outlets) {
            throw new IllegalArgumentException("outlet (" + outlet + ") must be between 0 and " + (outlets-1) + ".");
        }
    }
    public void setOutletState(int outlet, OutletState state) {
        outletOk(outlet);
        outletStates[outlet]=state;
    }
    
    public OutletState getOutletState(int outlet) {
        outletOk(outlet);
        switch(mode) {
            case OFF: return OutletState.UNPOWERED;
            case ON: return OutletState.POWERED;
            case REMOTE: return outletStates[outlet];
        }
        throw new IllegalStateException();
    }
    
    public void setModeOff() { setMode(Mode.OFF); }
    public void setModeOn() { setMode(Mode.ON); }
    public void setModeRemote() { setMode(Mode.REMOTE); }

}
