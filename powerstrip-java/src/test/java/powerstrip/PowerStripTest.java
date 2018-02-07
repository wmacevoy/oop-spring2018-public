/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package powerstrip;

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
public class PowerStripTest {

    public PowerStripTest() {
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

    
    PowerStrip create(int outlets) {
        return new PowerStrip(outlets);
    }

    PowerStrip create() {
        return create(2);
    }

    @Test
    public void testNullPointerExceptionRegression() {
        PowerStrip unit = new PowerStrip(2);
        int outlet = 0;
        unit.setModeRemote();
        unit.setOutletState(outlet, OutletState.POWERED);
    }

    @Test
    public void testConstruct() {
        for (int outlets = -1; outlets < 10; ++outlets) {
            try {
                PowerStrip unit = create(outlets);
                assertEquals(outlets, unit.getOutlets());
            } catch (IllegalArgumentException ex) {
                assertTrue(outlets <= 0);
            }
        }
    }

    @Test
    public void testAllOutlets() {
        PowerStrip unit = create();
        testAllOutlets(unit);
    }

    public void testAllOutlets(PowerStrip unit) {
        for (int outlet = -1; outlet <= unit.getOutlets(); ++outlet) {
            try {
                testOutletOff(unit, outlet);
                testOutletOn(unit, outlet);
                testOutletRemotePowered(unit, outlet);
                testOutletRemoteUnpowered(unit, outlet);
            } catch (IndexOutOfBoundsException ex) {
                assertTrue(outlet < 0 || outlet >= unit.getOutlets());
            }
        }
    }

    public void testOutletOff(PowerStrip unit, int outlet) {
        unit.setModeOff();
        OutletState expResult = OutletState.UNPOWERED;
        OutletState result = unit.getOutletState(outlet);
        assertEquals(expResult, result);

    }

    public void testOutletOn(PowerStrip unit, int outlet) {
        unit.setModeOn();
        OutletState expResult = OutletState.POWERED;
        OutletState result = unit.getOutletState(outlet);
        assertEquals(expResult, result);

    }
    public void testOutletRemotePowered(PowerStrip unit, int outlet) {
        unit.setModeRemote();
        unit.setOutletState(outlet, OutletState.POWERED);
        OutletState expResult = OutletState.POWERED;
        OutletState result = unit.getOutletState(outlet);
        if (expResult != result) {
            System.out.println("broke");
        }
        assertEquals(expResult, result);
    }

    public void testOutletRemoteUnpowered(PowerStrip unit, int outlet) {
        unit.setModeRemote();
        unit.setOutletState(outlet, OutletState.UNPOWERED);
        OutletState expResult = OutletState.UNPOWERED;
        OutletState result = unit.getOutletState(outlet);
        assertEquals(expResult, result);
    }
}
