package JMClient;

import java.io.*;
import java.net.*;
import JMServer.Message;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.DefaultListModel;

public class Client implements Runnable {

    public Socket socket = null;
    public ObjectInputStream streamIn = null;
    public ObjectOutputStream streamOut = null;

    public String serverAddress = "";
    public int port = 9000;

    public HistoryWindow historyWindow = null;
    public ClientWindow clientWindow = null;

    // constructor
    public Client(ClientWindow _clientWindow, HistoryWindow _historyWindow) throws IOException {

        clientWindow = _clientWindow;
        historyWindow = _historyWindow;

        serverAddress = clientWindow.serverAddress;
        port = clientWindow.port;

        // creates a stream socket and connect it to the specified port number at the specified IP address
        //socket = new Socket(InetAddress.getByName(serverAddress), port);
        socket = new Socket();
        socket.connect(new InetSocketAddress(InetAddress.getByName(serverAddress), port), 5000);  // 5000 millisecond timeout value

        // open I/O streams
        streamOut = new ObjectOutputStream(socket.getOutputStream());
        streamOut.flush();
        streamIn = new ObjectInputStream(socket.getInputStream());
    }

    // code executed in the thread
    @Override
    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            isRunning = handler();  // LISTEN FOR INCOMING SERVER MESSAGES
        }
    }

    // handles incoming messages from the server
    // updates the clientWindow
    public boolean handler() {

        String Me = clientWindow.username;
        DefaultListModel userlist = clientWindow.model;

        try {
            // get the incoming message
            Message message = (Message) streamIn.readObject();
            System.out.println("Incoming: " + message.toString());

            // message cases:
            switch (message.type) {
                case "connect":  // message type: connect

                    // either server says OK, or connection error
                    if (message.content.equals("OK")) {

                        // UPDATE THE UI ELEMENTS
                        clientWindow.loginButton.setEnabled(true);
                        clientWindow.registerButton.setEnabled(true);
                        clientWindow.usernameTextField.setEnabled(true);
                        clientWindow.passwordPasswordField.setEnabled(true);
                        clientWindow.serverAddressTextField.setEditable(false);
                        clientWindow.serverPortTextField.setEditable(false);
                        clientWindow.consoleTextArea.append("[" + timeStamp() + "] - [SERVER -> Me]    Connection Successful\n");
                    } else {
                        clientWindow.consoleTextArea.append("[" + timeStamp() + "] - [SERVER -> Me]    Connection Error\n");
                    }
                    break;

                case "login":  // message type: login

                    // LOGIN WAS SUCCESSFUL
                    if (message.content.equals("TRUE")) {

                        clientWindow.loginButton.setEnabled(false);
                        clientWindow.registerButton.setEnabled(false);
                        clientWindow.clearButton.setEnabled(true);
                        clientWindow.consoleTextArea.append("[" + timeStamp() + "] - [SERVER -> Me]    Login Successful\n");
                        clientWindow.usernameTextField.setEnabled(false);
                        clientWindow.passwordPasswordField.setEnabled(false);
                        clientWindow.messageTextField.setEditable(true);
                        clientWindow.historyButtonOn();
                        clientWindow.setTitle("JMessenger - " + message.recipient);

                        // LOGIN WAS UNSUCCESSFUL
                    } else if (message.content.equals("FALSE")) {
                        clientWindow.consoleTextArea.append("[" + timeStamp() + "] - [SERVER -> Me]    Login Failed\n");
                    }
                    break;

                case "register":  // message type: register

                    // REGISTRATION SUCCESSFUL
                    if (message.content.equals("TRUE")) {

                        clientWindow.loginButton.setEnabled(false);
                        clientWindow.registerButton.setEnabled(false);
                        clientWindow.clearButton.setEnabled(true);
                        clientWindow.consoleTextArea.append("[" + timeStamp() + "] - [SERVER -> Me]    Registration Successful\n");

                    } else if (message.content.equals("FALSE")) {
                        clientWindow.consoleTextArea.append("[" + timeStamp() + "] - [SERVER -> Me]    Registration Failed\n");
                    }
                    break;

                case "newuser":  // message type: newuser

                    // CHECK TO SEE IF THE USER IS ALREADY IN THE USERLIST
                    if (!message.content.equals(Me)) {
                        boolean userNotInList = true;
                        for (int i = 0; i < userlist.getSize(); ++i) {
                            if (userlist.getElementAt(i).equals(message.content)) {
                                userNotInList = false;
                                break;
                            }
                        }

                        // IF THE USER IS NOT IN THE USERLIST, ADD NEW USER TO THE USERLIST
                        if (userNotInList) {
                            userlist.addElement(message.content);
                        }
                    }
                    break;

                case "message":  // message type: message

                    // DISPLAY THE MESSAGE
                    if (message.recipient.equals(Me)) {
                        clientWindow.consoleTextArea.append("[" + timeStamp() + "] - [" + message.sender + " -> Me]    " + message.content + "\n");
                    } else {
                        clientWindow.consoleTextArea.append("[" + timeStamp() + "] - [" + message.sender + " -> " + message.recipient + "]    " + message.content + "\n");
                    }

                    // SEND THE SERVER A CHAT HISTORY REQUEST
                    clientWindow.requestHistory();
                    break;

                case "signout":  // message type: signout

                    // NOTE: message.content contains the username that signed out
                    
                    if (message.content.equals(Me)) {  // case: the client signed out

                        clientWindow.consoleTextArea.append("[" + timeStamp() + "] - [" + message.sender + " -> Me]    Bye\n");
                        clientWindow.clearButton.setEnabled(false);
                        clientWindow.serverAddressTextField.setEditable(true);
                        clientWindow.serverPortTextField.setEditable(true);
                        clientWindow.historyButtonOff();
                        clientWindow.setTitle("JMessenger - Client");

                        for (int i = userlist.size() - 1; i > 0; --i) {
                            userlist.removeElementAt(i);
                        }

                        clientWindow.clientThread.interrupt();
                        return false;

                    } else {  // some other client signed out
                        userlist.removeElement(message.content);
                        clientWindow.consoleTextArea.append("[" + timeStamp() + "] - [" + message.sender + " -> Everyone]    " + message.content + " has signed out\n");
                    }
                    break;

                case "history":  // message type: history

                    historyWindow.addTableEntry(message.sender, message.content, message.recipient, message.getTimeStamp());
                    break;

                default:  // case: unknown message

                    clientWindow.consoleTextArea.append("[" + timeStamp() + "] - [SERVER -> Me]    Unknown message type\n");
                    break;
            }
        } catch (IOException | ClassNotFoundException exception) {
            clientWindow.consoleTextArea.append("[" + timeStamp() + "] - [Application -> Me]    Connection Failure\n");
            clientWindow.serverAddressTextField.setEditable(true);
            clientWindow.serverPortTextField.setEditable(true);
            clientWindow.clearButton.setEnabled(false);

            // remove the user from the userlist
            for (int i = 1; i < userlist.size(); ++i) {
                userlist.removeElementAt(i);
            }

            clientWindow.clientThread.interrupt();

            System.out.println("Exception Client.handler()");
            exception.printStackTrace();

            return false;
        }
        return true;
    }

    // send a message
    public void send(Message message) {
        try {
            streamOut.writeObject(message);  // send the message
            streamOut.flush();
            System.out.println("Outgoing: " + message.toString());
        } catch (IOException exception) {
            System.out.println("Exception Client.send() ");
        }
    }

    // get the current time stamp
    public String timeStamp() {
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date());
    }
}
