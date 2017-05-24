package JMClient;

import java.io.*;
import java.net.*;
import javax.swing.table.DefaultTableModel;
import JMServer.Message;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.DefaultListModel;

public class Client implements Runnable {

    public ClientWindow clientWindow;
    public String serverAddress;
    public int port;
    public Socket socket;
    public ObjectInputStream streamIn;
    public ObjectOutputStream streamOut;
    public History history;

    // constructor
    public Client(ClientWindow _clientWindow) throws IOException {

        clientWindow = _clientWindow;
        serverAddress = clientWindow.serverAddress;
        port = clientWindow.port;

        // creates a stream socket and connect it to the specified port number at the specified IP address
        socket = new Socket(InetAddress.getByName(serverAddress), port);

        // open I/O streams
        streamOut = new ObjectOutputStream(socket.getOutputStream());
        streamOut.flush();
        streamIn = new ObjectInputStream(socket.getInputStream());

        // get a copy of the history object
        history = clientWindow.history;
    }

    // code executed in the thread
    @Override
    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            isRunning = clientWindowHandler();
        }
    }

    // handles incoming messages from the server
    // updates the clientWindow
    public boolean clientWindowHandler() {

        String Me = clientWindow.username;
        DefaultListModel userlistUI = clientWindow.model;

        try {
            // get the message
            Message message = (Message) streamIn.readObject();
            System.out.println("Incoming: " + message.toString());

            // message cases:
            if (message.type.equals("message")) {
                // recipient is Me, or not Me
                if (message.recipient.equals(Me)) {
                    clientWindow.consoleTextArea.append("[" + timeStamp() + "] - [" + message.sender + " -> Me]    " + message.content + "\n");
                } else {
                    clientWindow.consoleTextArea.append("[" + timeStamp() + "] - [" + message.sender + " -> " + message.recipient + "]    " + message.content + "\n");
                }

                if (!message.sender.equals(Me) && !message.content.equals(".disconnect")) {
                    String messageTime = new Date().toString();

                    try {
                        history.addMessage(message, messageTime);
                        DefaultTableModel table = (DefaultTableModel) clientWindow.historyWindow.historyTable.getModel();
                        table.addRow(new Object[]{message.sender, message.content, "Me", messageTime});
                    } catch (Exception exception) {
                    }
                }
            } else if (message.type.equals("login")) {  // login message
                if (message.content.equals("TRUE")) {
                    clientWindow.loginButton.setEnabled(false);
                    clientWindow.registerButton.setEnabled(false);
                    clientWindow.clearButton.setEnabled(true);
                    clientWindow.consoleTextArea.append("[" + timeStamp() + "] - [SERVER -> Me]    Login Successful\n");
                    clientWindow.usernameTextField.setEnabled(false);
                    clientWindow.passwordPasswordField.setEnabled(false);
                } else {
                    clientWindow.consoleTextArea.append("[" + timeStamp() + "] - [SERVER -> Me]    Login Failed\n");
                }
            } else if (message.type.equals("test")) {  // test message
                clientWindow.loginButton.setEnabled(true);
                clientWindow.registerButton.setEnabled(true);
                clientWindow.usernameTextField.setEnabled(true);
                clientWindow.passwordPasswordField.setEnabled(true);
                clientWindow.serverAddressTextField.setEditable(false);
                clientWindow.serverPortTextField.setEditable(false);
            } else if (message.type.equals("newuser")) {  // new user message
                if (!message.content.equals(Me)) {
                    boolean exists = false;
                    for (int i = 0; i < userlistUI.getSize(); i++) {
                        if (userlistUI.getElementAt(i).equals(message.content)) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        userlistUI.addElement(message.content);
                    }
                }
            } else if (message.type.equals("register")) {  // register message
                if (message.content.equals("TRUE")) {
                    clientWindow.loginButton.setEnabled(false);
                    clientWindow.registerButton.setEnabled(false);
                    clientWindow.clearButton.setEnabled(true);
                    clientWindow.consoleTextArea.append("[" + timeStamp() + "] - [SERVER -> Me]    Registration Successful\n");
                } else {
                    clientWindow.consoleTextArea.append("[" + timeStamp() + "] - [SERVER -> Me]    Registration Failed\n");
                }
            } else if (message.type.equals("signout")) {  // signout message
                if (message.content.equals(Me)) {
                    clientWindow.consoleTextArea.append("[" + timeStamp() + "] - [" + message.sender + " -> Me]    Bye\n");
                    clientWindow.clearButton.setEnabled(false);
                    clientWindow.serverAddressTextField.setEditable(true);
                    clientWindow.serverPortTextField.setEditable(true);

                    for (int i = 1; i < userlistUI.size(); i++) {
                        userlistUI.removeElementAt(i);
                    }

                    clientWindow.clientThread.interrupt();
                } else {
                    userlistUI.removeElement(message.content);
                    clientWindow.consoleTextArea.append("[" + timeStamp() + "] - [" + message.sender + " -> Everyone]    " + message.content + " has signed out\n");
                }
            } else {  // unknown message
                clientWindow.consoleTextArea.append("[" + timeStamp() + "] - [SERVER -> Me]    Unknown message type\n");
            }
        } catch (IOException | ClassNotFoundException exception) {
            clientWindow.consoleTextArea.append("[" + timeStamp() + "] - [Application -> Me]    Connection Failure\n");
            clientWindow.serverAddressTextField.setEditable(true);
            clientWindow.serverPortTextField.setEditable(true);
            clientWindow.clearButton.setEnabled(false);

            for (int i = 1; i < userlistUI.size(); i++) {
                userlistUI.removeElementAt(i);
            }

            clientWindow.clientThread.interrupt();

            System.out.println("Exception Client.run()");
            exception.printStackTrace();

            return false;
        }
        return true;
    }

    // send a message
    public void send(Message message) {
        try {
            streamOut.writeObject(message);
            streamOut.flush();
            System.out.println("Outgoing    " + message.toString());

            if (message.type.equals("message") && !message.content.equals(".disconnect")) {
                String messageTime = (new Date()).toString();
                try {
                    history.addMessage(message, messageTime);
                    DefaultTableModel table = (DefaultTableModel) clientWindow.historyWindow.historyTable.getModel();
                    table.addRow(new Object[]{"Me", message.content, message.recipient, messageTime});
                } catch (Exception exception) {
                }
            }
        } catch (IOException exception) {
            System.out.println("Exception Client.send() ");
        }
    }

    // get the current time stamp
    public String timeStamp() {
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date());
    }
}
