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

    /**
     * Test of getOutlets method, of class PowerStrip.
     */
    @Test
    public void testGetOutlets() {
        System.out.println("getOutlets");
        PowerStrip instance = new PowerStrip(2);
        int expResult = 2;
        int result = instance.getOutlets();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testOutletSafety() {
        PowerStrip instance = new PowerStrip(2);
        instance.setModeOff();
        for (int outlet = 0; outlet < instance.getOutlets(); ++outlet) {
            boolean expResult = false;
            boolean result = instance.getOutletState(outlet);
            assertEquals(expResult, result);
        }
    }
    
}
