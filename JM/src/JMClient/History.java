package JMClient;

import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import javax.swing.table.DefaultTableModel;
import JMServer.Message;
import java.util.Date;
import javax.swing.DefaultListModel;
import org.xml.sax.SAXException;

public class History {

    public void updateHistoryWindow(String username) {
        
    }
    
    public void addTableEntry(Message msg) {

        
        //DefaultTableModel model = (DefaultTableModel) historyWindow.historyTable.getModel();

        //model.addRow(new Object[]{msg.sender, msg.content, msg.recipient, "SOME TIME"});
    }

}
