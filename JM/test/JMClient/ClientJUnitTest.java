/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JMClient;

import JMServer.Message;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author macbookpro
 */
public class ClientJUnitTest {

    public ClientJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of run method, of class Client.
     */
    @Test
    public void testRun() {
//        System.out.println("run");
//        Client instance = null;
//        instance.run();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of handler method, of class Client.
     */
    @Test
    public void testHandler() {
//        System.out.println("handler");
//        Client instance = null;
//        boolean expResult = false;
//        boolean result = instance.handler();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of send method, of class Client.
     */
    @Test
    public void testSend() {
//        System.out.println("send");
//        Message message = null;
//        Client instance = null;
//        instance.send(message);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of timeStamp method, of class Client.
     */
    @Test
    public void testTimeStamp() {
        System.out.println("** JMClient.ClientJUnitTest: testTimeStamp()");
        
        ClientWindow clientWindow = new ClientWindow();
        try {
            Client instance = new Client(clientWindow, clientWindow.historyWindow);
            String expResult = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date());
            String result = instance.timeStamp();
            assertEquals(expResult, result);
            // TODO review the generated test code and remove the default call to fail.
            fail("The test case is a prototype.");
        } catch (IOException ex) {
            Logger.getLogger(ClientJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
