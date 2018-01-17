import unittest
from PowerStrip import PowerStrip

class TestPowerStrip(unittest.TestCase):
    def test_construct(self):
        ps = PowerStrip(2)
        self.assertEqual(ps.getMode(),PowerStrip.MODE_REMOTE)
        self.assertEqual(ps.getOutletState(0),False)
        self.assertEqual(ps.getOutletState(1),False)

    def test_modes(self):
        ps = PowerStrip(2)
        ps.setMode(PowerStrip.MODE_REMOTE)
        ps.setOutletState(0, True)
        ps.setOutletState(1, False)
        self.assertEqual(ps.getOutletState(0), True)
        self.assertEqual(ps.getOutletState(1), False)
        ps.setMode(PowerStrip.MODE_OFF)
        self.assertEqual(ps.getOutletState(0), False)
        self.assertEqual(ps.getOutletState(1), False)
        ps.setMode(PowerStrip.MODE_ON)
        self.assertEqual(ps.getOutletState(0), True)
        self.assertEqual(ps.getOutletState(1), True)

    def test_mode_errors(self):
        ps = PowerStrip(2)
        ok = False
        try:
            ps.setMode("ON")
        except ValueError:
            ok = True
        self.assertTrue(ok)

    def test_output_errors(self):
        ps = PowerStrip(2)
        ok = False
        try:
            ps.setOutletState(0,0.5)
        except ValueError:
            ok = True
        self.assertTrue(ok)
        
if __name__ == '__main__':
    unittest.main()
