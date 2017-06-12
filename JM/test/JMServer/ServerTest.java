/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JMServer;

import JMClient.Client;
import JMClient.ClientTest;
import JMClient.ClientWindow;
import java.io.IOException;
import static java.lang.Thread.sleep;
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
public class ServerTest {

    ServerWindow serverWindow;
    ClientWindow clientWindow;

    public ServerTest() throws InterruptedException {

        // SETUP THE TEST SERVER
        serverWindow = new ServerWindow();
        serverWindow.dbFilePath = System.getProperty("user.dir") + "src/JMServer/Database.xml";
        serverWindow.serverPortTextField.setText("9000");
        serverWindow.server = new Server(serverWindow);
        serverWindow.server.history.dirPath = System.getProperty("user.dir") + "src/JMServer/history/";
        
        // SETUP THE TEST CLIENT
        clientWindow = new ClientWindow();
        try {
            clientWindow.client = new Client(clientWindow, clientWindow.historyWindow);
            clientWindow.clientThread = new Thread(clientWindow.client);  // create a new client thread
            //clientWindow.clientThread.start();
        } catch (IOException ex) {
            Logger.getLogger(ClientTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of start method, of class Server.
     */
    @Test
    public void testStart() {
        System.out.println("Running testStart():");
        
        // start the server thread
        serverWindow.server.start();
        
        // assertions
        assertNotNull(serverWindow.server.thread);
        assertEquals(true, serverWindow.server.thread.isAlive());
    }

    /**
     * Test of stop method, of class Server.
     */
    @Test
    public void testStop() {
        System.out.println("Running testStop():");
        
        // stop the server thread
        serverWindow.server.stop();
        
        // assertions
        assertNull(serverWindow.server.thread);
        assertNull(serverWindow.server.clients);
        assertNull(serverWindow.server.serverSocket);
        assertNull(serverWindow.server.db);
        assertNull(serverWindow.server.history);

        assertEquals(0, serverWindow.server.clientCount);
        assertEquals(9000, serverWindow.server.port);
        assertEquals(50, serverWindow.server.MAX_THREAD);

        assertNull(serverWindow.server.serverWindow);
    }

    /**
     * Test of run method, of class Server.
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testRun() throws InterruptedException {
        System.out.println("Running testRun():");
        
        assertNotNull(serverWindow.server.thread);
    }

//    /**
//     * Test of handler method, of class Server.
//     */
//    @Test
//    public void testHandler() throws Exception {
//        System.out.println("handler");
//        int ID = 0;
//        Message inMsg = null;
//        Server instance = null;
//        instance.handler(ID, inMsg);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of sendAll method, of class Server.
//     */
//    @Test
//    public void testSendAll() {
//        System.out.println("sendAll");
//        String type = "";
//        String sender = "";
//        String content = "";
//        Server instance = null;
//        instance.sendAll(type, sender, content);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of SendUserList method, of class Server.
//     */
//    @Test
//    public void testSendUserList() {
//        System.out.println("SendUserList");
//        String user = "";
//        Server instance = null;
//        instance.SendUserList(user);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findUserThread method, of class Server.
//     */
//    @Test
//    public void testFindUserThread() {
//        System.out.println("findUserThread");
//        String _username = "";
//        Server instance = null;
//        ServerThread expResult = null;
//        ServerThread result = instance.findUserThread(_username);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of remove method, of class Server.
//     */
//    @Test
//    public void testRemove() {
//        System.out.println("remove");
//        int ID = 0;
//        Server instance = null;
//        instance.remove(ID);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of timeStamp method, of class Server.
//     */
//    @Test
//    public void testTimeStamp() {
//        System.out.println("timeStamp");
//        Server instance = null;
//        String expResult = "";
//        String result = instance.timeStamp();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
}
