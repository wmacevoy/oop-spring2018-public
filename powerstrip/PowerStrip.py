class PowerStrip:
    MODE_OFF = 0
    MODE_REMOTE = 1
    MODE_ON = 2

    def reset(self):
        self._mode = PowerStrip.MODE_REMOTE
        self._outletStates = [ False for i in range(self._outlets) ]
        self._outletCurrents = [ 0 for i in range(self._outlets) ]        

    def __init__(self, outlets):
        self._outlets = outlets
        self.reset()

    def getOutlets(self):
        return self._outlets

    def outletOk(self, outlet):
        if not isinstance(outlet,int):
            raise ValueError("outlet must an integer")
        if outlet < 0 or self._outlets <= outlet:
            raise ValueError("outlet (" + str(outlet) + ") must be between 0 and " + str(self._outlets-1) + ".")

    def outletStateOk(self, state):
        if not isinstance(state,bool):
            raise ValueError("outlet state must a boolean")
        
    def modeOk(self, mode):
        if not isinstance(mode,int):
            raise ValueError("mode must be an integer")
        if mode != PowerStrip.MODE_OFF and mode != PowerStrip.MODE_ON and mode != PowerStrip.MODE_REMOTE:
            raise ValueError("mode must be MODE_OFF, MODE_REMOTE, or MODE_ON")
        
    def setMode(self, mode):
        self.modeOk(mode)
        self._mode = mode

    def getMode(self):
        return self._mode

    def setOutletState(self, outlet,state):
        self.outletOk(outlet)
        self.outletStateOk(state)
        self._outletStates[outlet]=state

    def getOutletState(self, outlet):
        self.outletOk(outlet)
        if self._mode == PowerStrip.MODE_OFF: return False
        if self._mode == PowerStrip.MODE_ON : return True
        return self._outletStates[outlet]

    def getOutletStates(self):
        return [ self.getOutletState(outlet) for outlet in range(self._outlets)]
