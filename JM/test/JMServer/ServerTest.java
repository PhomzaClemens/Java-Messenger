/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JMServer;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author macbookpro
 */
public class ServerTest {
    
    public ServerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of run method, of class Server.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        Server instance = null;
        instance.run();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of start method, of class Server.
     */
    @Test
    public void testStart() {
        System.out.println("start");
        Server instance = null;
        instance.start();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stop method, of class Server.
     */
    @Test
    public void testStop() {
        System.out.println("stop");
        Server instance = null;
        instance.stop();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of handler method, of class Server.
     */
    @Test
    public void testHandler() {
        System.out.println("handler");
        int ID = 0;
        Message msg = null;
        Server instance = null;
        instance.handler(ID, msg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Announce method, of class Server.
     */
    @Test
    public void testAnnounce() {
        System.out.println("Announce");
        String type = "";
        String sender = "";
        String content = "";
        Server instance = null;
        instance.Announce(type, sender, content);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of SendUserList method, of class Server.
     */
    @Test
    public void testSendUserList() {
        System.out.println("SendUserList");
        String user = "";
        Server instance = null;
        instance.SendUserList(user);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findUserThread method, of class Server.
     */
    @Test
    public void testFindUserThread() {
        System.out.println("findUserThread");
        String user = "";
        Server instance = null;
        ServerThread expResult = null;
        ServerThread result = instance.findUserThread(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of remove method, of class Server.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        int ID = 0;
        Server instance = null;
        instance.remove(ID);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
