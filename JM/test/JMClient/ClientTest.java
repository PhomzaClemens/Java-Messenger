/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JMClient;

import JMServer.Message;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author macbookpro
 */
public class ClientTest {
    
    public ClientTest() {
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
     * Test of run method, of class Client.
     */
    @Test
    public void testRun() throws IOException {
        System.out.println("run");
        Client instance = new Client(new ClientWindow());
        instance.run();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clientWindowHandler method, of class Client.
     */
    @Test
    public void testClientWindowHandler() {
        System.out.println("clientWindowHandler");
        Client instance = null;
        boolean expResult = false;
        boolean result = instance.clientWindowHandler();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of send method, of class Client.
     */
    @Test
    public void testSend() {
        System.out.println("send");
        Message message = null;
        Client instance = null;
        instance.send(message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of timeStamp method, of class Client.
     */
    @Test
    public void testTimeStamp() {
        System.out.println("timeStamp");
        Client instance = null;
        String expResult = "";
        String result = instance.timeStamp();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
