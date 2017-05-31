/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JMClient;

import JMClient.History;
import JMServer.Message;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Element;

/**
 *
 * @author macbookpro
 */
public class HistoryTest {
    
    public HistoryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of addMessage method, of class History.
     */
    @Test
    public void testAddMessage() {
        System.out.println("addMessage");
        Message msg = null;
        String time = "";
        History instance = new History();
        instance.addMessage(msg, time);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of FillTable method, of class History.
     */
    @Test
    public void testFillTable() {
        System.out.println("FillTable");
        HistoryWindow historyFrame = null;
        History instance = new History();
        instance.FillTable(historyFrame);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeToFile method, of class History.
     */
    @Test
    public void testWriteToFile() {
        System.out.println("writeToFile");
        HistoryWindow historyFrame = null;
        History instance = new History();
        instance.writeToFile(historyFrame);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTagValue method, of class History.
     */
    @Test
    public void testGetTagValue() {
        System.out.println("getTagValue");
        String sTag = "";
        Element eElement = null;
        String expResult = "";
        String result = History.getTagValue(sTag, eElement);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
