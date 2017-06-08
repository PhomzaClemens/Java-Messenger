/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JMClient;

import JMServer.ServerWindow;
import JMServer.Message;
import JMServer.Server;
import java.io.IOException;
import static java.lang.Thread.sleep;
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
public class ClientTest {

    ServerWindow serverWindow;
    ClientWindow clientWindow;
    Client client;

    public ClientTest() {

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
     * Test of run method, of class Client.
     */
    @Test
    public void testRun() {
        System.out.println("Running testRun():");

        clientWindow.clientThread.start();
        if (!clientWindow.clientThread.isAlive()) {
            fail("FAIL");
        }
    }

    /**
     * Test of handler method, of class Client.
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testHandler() throws InterruptedException {
        System.out.println("Running testHandler():");

        // start the client thread and connect to the server
        clientWindow.clientThread.start();
        clientWindow.client.send(new Message("connect", "CLIENT", "connection request", "SERVER"));
        sleep(1000);
        System.out.println("CONNECTION SUCCESSFUL!!");

        // login to the server
        clientWindow.usernameTextField.setText("username");
        clientWindow.passwordPasswordField.setText("password");
        clientWindow.client.send(new Message("login", clientWindow.usernameTextField.getText(), clientWindow.passwordPasswordField.getText(), "SERVER"));
        sleep(1000);
        System.out.println("LOGIN SUCCESSFUL!!");
    }

    /**
     * Test of send method, of class Client.
     *
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testSend() throws InterruptedException {
        System.out.println("Running testSend():");

        // start the server and connect to the server
        clientWindow.clientThread.start();
        clientWindow.client.send(new Message("connect", "CLIENT", "connection request", "SERVER"));
        sleep(1000);
        System.out.println("CONNECTION SUCCESSFUL!!");

        // login to the server
        clientWindow.usernameTextField.setText("username");
        clientWindow.passwordPasswordField.setText("password");
        clientWindow.client.send(new Message("login", clientWindow.usernameTextField.getText(), clientWindow.passwordPasswordField.getText(), "SERVER"));
        sleep(1000);
        System.out.println("LOGIN SUCCESSFUL!!");

        // SENDING AN OUTGOING MESSAGE TO THE SERVER
        Message outMsg = new Message("message", "username", "test message", "Everyone");
        clientWindow.client.send(outMsg);
        sleep(1000);

        // trim out the beginning portion of what's displayed in the consoleTextArea
        String consoleText = clientWindow.consoleTextArea.getText();
        String[] parts = consoleText.split("\n");
        String bottomMsg = parts[parts.length - 1];
        String[] parts2 = bottomMsg.split("]");
        String trim = parts2[2].trim();

        String expectedStr = "test message";
        assertEquals(expectedStr, trim);
    }

    /**
     * Test of timeStamp method, of class Client.
     */
    @Test
    public void testTimeStamp() {
        System.out.println("Running testTimeStamp():");

        String expResult = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date());
        String result = clientWindow.client.timeStamp();
        assertEquals(expResult, result);
    }

}
