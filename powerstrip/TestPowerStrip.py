import unittest
from PowerStrip import PowerStrip

class TestPowerStrip(unittest.TestCase):
    def _testInit(self,unit):
        self.assertEqual(unit.getMode(),PowerStrip.MODE_REMOTE)
        for outlet in range(unit.getOutlets()):
            self.assertEqual(unit.getOutletState(outlet),False,
                             "outlet " + str(outlet) + " should be off")
    def _testModes(self,unit):
        unit.setMode(PowerStrip.MODE_ON)
        for outlet in range(unit.getOutlets()):
            unit.setOutletState(outlet,False)
            self.assertEqual(unit.getOutletState(outlet),True)
            unit.setOutletState(outlet,True)
            self.assertEqual(unit.getOutletState(outlet),True)

        unit.setMode(PowerStrip.MODE_OFF)
        for outlet in range(unit.getOutlets()):
            unit.setOutletState(outlet,False)
            self.assertEqual(unit.getOutletState(outlet),False)
            unit.setOutletState(outlet,True)
            self.assertEqual(unit.getOutletState(outlet),False)

        unit.setMode(PowerStrip.MODE_REMOTE)
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
            unit.setMode(PowerStrip.MODE_REMOTE)
            self.assertEqual(unit.getOutletState(outlet), state,
                             "outlet " + str(outlet) + " should be " + str(state))
            unit.setMode(PowerStrip.MODE_OFF)
            self.assertEqual(unit.getOutletState(outlet),False,
                             "outlet " + str(outlet) + " in off mode should be False.")
            unit.setMode(PowerStrip.MODE_ON)
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
            
    def testConstructPowerStrip(self):
        unit = PowerStrip(2)
        self._testInit(unit)

    def testModesPowerStrip(self):
        unit = PowerStrip(2)
        self._testModes(unit)
        
    def testModesInvalidPowerStrip(self):
        unit = PowerStrip(2)
        self._testModesInvalid(unit)

    def testOutletsPowerStrip(self):
        unit = PowerStrip(2)
        self._testOutlets(unit)
        
if __name__ == '__main__':
    unittest.main()
