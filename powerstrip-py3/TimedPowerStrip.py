import datetime
from PowerStrip import PowerStrip
from Timer import Timer

class TimedPowerStrip(PowerStrip):
    def __init__(self,outlets):
        PowerStrip.__init__(self,outlets)
        self._timer = Timer()
        self._timer.setTimeOn(datetime.time.min)
        self._timer.setTimeOff(datetime.time.min)
        for outlet in range(outlets):
            self.setOutletState(outlet,True)

    def getTimer(self):
        return self._timer

    def getOutletState(self,outlet):
        self.outletOk(outlet)
        if self._mode == PowerStrip.MODE_OFF: return False
        if self._mode == PowerStrip.MODE_ON : return True
        if self._timer.isActive(): return self._outletStates[outlet]
        return False
