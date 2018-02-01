#!/usr/bin/env python3
import unittest
import datetime
from TimedPowerStrip import TimedPowerStrip

class TestPowerStrip(unittest.TestCase):
    def _testInit(self,unit):
        self.assertEqual(unit.getMode(),TimedPowerStrip.MODE_REMOTE)
        for outlet in range(unit.getOutlets()):
            self.assertEqual(unit.getOutletState(outlet),False,
                             "outlet " + str(outlet) + " should be off")

    def testInvalidOutput(self):
        unit = TimedPowerStrip(2)
        state = False
        unit.setOutletState(0,state)
        
    def _testModes(self,unit):
        unit.setMode(TimedPowerStrip.MODE_ON)
        for outlet in range(unit.getOutlets()):
            unit.setOutletState(outlet,False)
            self.assertEqual(unit.getOutletState(outlet),True)
            unit.setOutletState(outlet,True)
            self.assertEqual(unit.getOutletState(outlet),True)

        unit.setMode(TimedPowerStrip.MODE_OFF)
        for outlet in range(unit.getOutlets()):
            unit.setOutletState(outlet,False)
            self.assertEqual(unit.getOutletState(outlet),False)
            unit.setOutletState(outlet,True)
            self.assertEqual(unit.getOutletState(outlet),False)

        unit.setMode(TimedPowerStrip.MODE_REMOTE)
        unit.getTimer().setTimeOn(datetime.time.min)
        unit.getTimer().setTimeOff(datetime.time.max)
        for outlet in range(unit.getOutlets()):
            unit.setOutletState(outlet,False)
            self.assertEqual(unit.getOutletState(outlet),False)
            unit.setOutletState(outlet,True)
            self.assertEqual(unit.getOutletState(outlet),True)

    def _testModesInvalid(self,unit):
        for mode in ("remote","on","off","auto"):
            ok = False
            try:
                unit.setMode(mode)
            except ValueError:
                ok = True
            self.assertTrue(ok)

    def _testOutlet(self,unit,outlet):
        for state in (False,True):
            unit.setOutletState(outlet, state)
            unit.getTimer().setTimeOn(datetime.time.min)
            unit.getTimer().setTimeOff(datetime.time.max)
            unit.setMode(TimedPowerStrip.MODE_REMOTE)
            self.assertEqual(unit.getOutletState(outlet), state,
                             "outlet " + str(outlet) + " should be " + str(state))
            unit.getTimer().setTimeOn(datetime.time.min)
            unit.getTimer().setTimeOff(datetime.time.min)
            self.assertEqual(unit.getOutletState(outlet), False,
                             "outlet " + str(outlet) + " should be " + str(False))            
            unit.setMode(TimedPowerStrip.MODE_OFF)
            self.assertEqual(unit.getOutletState(outlet),False,
                             "outlet " + str(outlet) + " in off mode should be False.")
            unit.setMode(TimedPowerStrip.MODE_ON)
            self.assertEqual(unit.getOutletState(outlet),True,
                             "outlet " + str(outlet) + " in off mode should be True.")

    def _testOutletInvalid(self,unit,outlet):
        for state in (0.5,"off","on"):
            ok = False
            try:
                unit.setOutletState(outlet,state)
            except ValueError:
                ok = True
            self.assertTrue(ok, "outlet " + str(outlet) + " should fail to set value " + str(state))

    def _testOutlets(self,unit):
        for outlet in range(unit.getOutlets()):
            self._testOutlet(unit,outlet)
            self._testOutletInvalid(unit,outlet)
            
    def testConstructTimedPowerStrip(self):
        unit = TimedPowerStrip(2)
        self._testInit(unit)

    def testConstructPowerStripInvalid(self):
        ok = False
        try:
            unit = TimedPowerStrip(-2)
        except ValueError:
            ok = True
        self.assertTrue(ok)
        
    def testModesPowerStrip(self):
        unit = TimedPowerStrip(2)
        self._testModes(unit)
        
    def testModesInvalidPowerStrip(self):
        unit = TimedPowerStrip(2)
        self._testModesInvalid(unit)

    def testOutletsPowerStrip(self):
        unit = TimedPowerStrip(2)
        self._testOutlets(unit)

    def _testOutletsOff(self,unit):
        for outlet in range(unit.getOutlets()):
            self.assertFalse(unit.getOutletState(outlet))

    def _testOutletsOn(self,unit):
        for outlet in range(unit.getOutlets()):
            self.assertTrue(unit.getOutletState(outlet))
            
            
    def testTimer(self):
        unit = TimedPowerStrip(2)
        unit.setMode(TimedPowerStrip.MODE_REMOTE)
        jan2010midnight = datetime.datetime(2010,1,1,0,0,0)
        timer = unit.getTimer()
        timer.setDatetime(jan2010midnight)
        tenPm = datetime.time(22,0,0,0)
        timer.setTimeOn(tenPm)
        oneAm = datetime.time(1,0,0,0)
        timer.setTimeOff(oneAm)
        self.assertTrue(timer.isActive())
        self._testOutletsOn(unit)
        
        jan2010_2am = datetime.datetime(2010,1,1,2,0,0)        
        timer.setDatetime(jan2010_2am)
        self.assertFalse(timer.isActive())
        self._testOutletsOff(unit)

        dec2009_9pm = datetime.datetime(2009,12,31,21,0,0)        
        timer.setDatetime(dec2009_9pm)        
        self.assertFalse(timer.isActive())
        self._testOutletsOff(unit)
        
if __name__ == '__main__':
    unittest.main()
