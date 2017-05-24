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
import org.xml.sax.SAXException;

public class History {

    public String filePath;

    // constructor
    public History() {
        filePath = null;
    }
    
    // constructor
    public History(String _filePath) {
        filePath = _filePath;
    }

    // add a message to the history XML file
    public void addMessage(Message msg, String time) {

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filePath);

            Node data = doc.getFirstChild();

            Element message = doc.createElement("message");
            Element _sender = doc.createElement("sender");
            _sender.setTextContent(msg.sender);
            Element _content = doc.createElement("content");
            _content.setTextContent(msg.content);
            Element _recipient = doc.createElement("recipient");
            _recipient.setTextContent(msg.recipient);
            Element _time = doc.createElement("time");
            _time.setTextContent(time);

            message.appendChild(_sender);
            message.appendChild(_content);
            message.appendChild(_recipient);
            message.appendChild(_time);
            data.appendChild(message);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

        } catch (IOException | IllegalArgumentException | ParserConfigurationException | TransformerException | DOMException | SAXException exception) {
            System.out.println("Exception History.addMessage()");
        }
    }

    // load the history into the table model
    public void FillTable(HistoryWindow historyFrame) {

        DefaultTableModel model = (DefaultTableModel) historyFrame.historyTable.getModel();

        try {
            File fXmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("message");

            for (int temp = 0; temp < nList.getLength(); ++temp) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    model.addRow(new Object[]{getTagValue("sender", eElement), getTagValue("content", eElement), getTagValue("recipient", eElement), getTagValue("time", eElement)});
                }
            }
        } catch (Exception exception) {
            System.out.println("Exception History.FillTable()");
        }
    }

    // write table model data to history XML file
    public void writeToFile(HistoryWindow historyFrame) {

        DefaultTableModel model = (DefaultTableModel) historyFrame.historyTable.getModel();

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filePath);
            
            Node data = doc.getFirstChild();

            for (int i = 0; i < model.getRowCount(); i++) {
                
                // package the message in XML format
                Element message = doc.createElement("message");
                Element sender = doc.createElement("sender");
                sender.setTextContent(model.getValueAt(i, 0).toString());
                Element content = doc.createElement("content");
                content.setTextContent(model.getValueAt(i, 1).toString());
                Element recipient = doc.createElement("recipient");
                recipient.setTextContent(model.getValueAt(i, 2).toString());
                Element timeStamp = doc.createElement("timeStamp");
                timeStamp.setTextContent(model.getValueAt(i, 3).toString());
                
                message.appendChild(sender);
                message.appendChild(content);
                message.appendChild(recipient);
                message.appendChild(timeStamp);
                data.appendChild(message);
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            //StreamResult result = new StreamResult(new File("C:\\file.xml"));
            //StreamResult result = new StreamResult(new File(System.getProperty("user.dir") + "/src/" + "History.xml"));
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (Exception exception) {
            System.out.println("Exception History.writeToFile()");
        }
    }

    // get the XML tag value
    public static String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        return nValue.getNodeValue();
    }
}
