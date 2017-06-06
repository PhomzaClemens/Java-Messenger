package JMServer;

<<<<<<< HEAD
import JMClient.HistoryWindow;
=======
>>>>>>> version-1.0
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
<<<<<<< HEAD
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
            //create document builder factory
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filePath);

            // 
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

=======
import org.xml.sax.SAXException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class History {

    public String dirPath = null;
    public Server server = null;

    // constructor
    public History(Server _server) {
        server = _server;
    }

    // set the file path
    public void setFilePath(String _filePath) {
        dirPath = _filePath;
    }

    // add a message to the history XML file
    public void addMessage(Message msg, String time, String username) {

        try {
            String filePath = dirPath + username + ".xml";
            File xmlFile = new File(filePath);

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc;

            // two cases: username.xml exists, and username.xml doesn't exist
            if (xmlFile.exists() && !xmlFile.isDirectory()) {
                //System.out.println("file exists, append to the file");
                doc = docBuilder.parse(filePath);

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
            } else {
                //System.out.println("file doesn't exist, create a new file");
                String defaultPath = dirPath + "DEFAULT.xml";
                File defaultFile = new File(defaultPath);
                xmlFile = new File(dirPath + username + ".xml");
                xmlFile.createNewFile();
                copyFile(defaultFile, xmlFile);
                doc = docBuilder.parse(filePath);

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
            }
>>>>>>> version-1.0
        } catch (IOException | IllegalArgumentException | ParserConfigurationException | TransformerException | DOMException | SAXException exception) {
            System.out.println("Exception History.addMessage()");
        }
    }

<<<<<<< HEAD
    // load the history into the table model
    public void FillTable(HistoryWindow historyWindow) {

        DefaultTableModel model = (DefaultTableModel) historyWindow.historyTable.getModel();

        try {
=======
    /*private static void copyFile(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }*/
    private static void copyFile(File source, File dest) throws IOException {

        InputStream input = null;
        OutputStream output = null;

        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);

            byte[] buf = new byte[1024];
            int bytesRead;

            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            input.close();
            output.close();
        }
    }

    // send to the client their chat history
    public void sendHistory(String username) throws IOException {

        try {
            String filePath = dirPath + username + ".xml";
>>>>>>> version-1.0
            File fXmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("message");

<<<<<<< HEAD
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
    public void writeToFile(HistoryWindow historyWindow) {

        DefaultTableModel model = (DefaultTableModel) historyWindow.historyTable.getModel();

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
=======
            // FOR EACH PAST MESSAGE, SEND IT TO THE CLIENT
            for (int i = 0; i < nList.getLength(); ++i) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    // CREATE THE MESSAGE CONTAINING A PAST MESSAGE
                    Object[] message = new Object[]{
                        getTagValue("sender", eElement),
                        getTagValue("content", eElement),
                        getTagValue("recipient", eElement),
                        getTagValue("time", eElement)
                    };

                    Message outMsg = new Message("history", message[0].toString(), message[1].toString(), message[2].toString());

                    // SET THE MESSAGE TIMESTAMP
                    outMsg.setTimeStamp(message[3].toString());
                    
                    // SEND THE MESSAGE
                    server.findUserThread(username).send(outMsg);
                }
            }
        } catch (SAXException | ParserConfigurationException ex) {
            Logger.getLogger(History.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // add a new XML history log for the new user
    public void addNewUser(String username) throws IOException {
        String defaultPath = dirPath + "DEFAULT.xml";
        File defaultFile = new File(defaultPath);
        File xmlFile = new File(dirPath + username + ".xml");
        xmlFile.createNewFile();
        copyFile(defaultFile, xmlFile);
>>>>>>> version-1.0
    }

    // get the XML tag value
    public static String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        return nValue.getNodeValue();
    }
}
