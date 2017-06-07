package JMServer;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.util.Date;

public class Server implements Runnable {

    public ServerThread clients[] = null;
    public ServerSocket serverSocket = null;
    public Thread thread = null;
    public Database db = null;
    public History history = null;

    public int clientCount = 0, port = 9000, MAX_THREAD = 50;

    public ServerWindow serverWindow = null;

    // constructor
    public Server(ServerWindow _serverWindow) {

        serverWindow = _serverWindow;
        clients = new ServerThread[MAX_THREAD];  // create an array of threads where each thread is a client connection (socket)
        db = new Database(serverWindow.dbFilePath);
        history = new History(this);

        try {
            // START THE SERVER SOCKET
            serverSocket = new ServerSocket(Integer.parseInt(serverWindow.serverPortTextField.getText()));
            port = serverSocket.getLocalPort();

            // START THE SERVER THREAD
            start();

            // UPDATE THE UI ELEMENT
            serverWindow.consoleTextArea.append("Server running...\nIP Address: " + InetAddress.getLocalHost() + ", Port: " + serverSocket.getLocalPort() + "\n");
        } catch (IOException ioexception) {
            serverWindow.consoleTextArea.append("Cannot bind to port " + port + ": " + "Retrying...\n");
            serverWindow.RetryStart(port);
        }
    }

    // constructor
    public Server(ServerWindow _serverWindow, int _port) {

        serverWindow = _serverWindow;
        clients = new ServerThread[MAX_THREAD];  // create an array of threads where each thread is a client connection (socket)
        db = new Database(serverWindow.dbFilePath);
        history = new History(this);
        int DEFAULT_PORT = _port;

        try {
            // START THE SERVER SOCKET
            serverSocket = new ServerSocket(DEFAULT_PORT);
            port = serverSocket.getLocalPort();

            // START THE SERVER THREAD
            start();

            // UPDATE THE UI ELEMENT
            serverWindow.consoleTextArea.append("Server running...\nIP Address: " + InetAddress.getLocalHost() + ", Port: " + serverSocket.getLocalPort() + "\n");
        } catch (IOException ioexception) {
            serverWindow.consoleTextArea.append("Cannot bind to port " + port + ": " + ioexception.getMessage() + "\n");
        }
    }

    // start the thread
    public void start() {
        if (thread == null) {
            thread = new Thread(this);

            // START THE THREAD
            thread.start();
        }
    }

    // stop the thread
    @SuppressWarnings("deprecation")
    public void stop() {
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }

        clients = null;
        serverSocket = null;
        db = null;
        history = null;

        clientCount = 0;
        port = 9000;
        MAX_THREAD = 50;

        serverWindow = null;
    }

    // code executed in the thread
    @Override
    public void run() {
        while (thread != null) {  // WHILE THREAD IS RUNNING..
            try {
                serverWindow.consoleTextArea.append("Waiting for a client...\n");

                // accept() METHOD BLOCKS UNTIL A CONNECTION IS MADE...THEN, ADD THE NEW CONNECTION AS A SERVER THREAD
                addThread(serverSocket.accept());
            } catch (IOException exception) {
                serverWindow.consoleTextArea.append("Server accept error...\n");
                serverWindow.RetryStart(10000);
            }
        }
    }

    // find the index of a client based on its ID
    private int findClient(int ID) {
        for (int i = 0; i < clientCount; ++i) {
            if (clients[i].getID() == ID) {
                return i;
            }
        }
        return -1;
    }

    // handle incoming messages
    public synchronized void handler(int ID, Message inMsg) throws IOException {

        // handle messages based on type
        switch (inMsg.type) {
            case "connect":  // message type: connect

                clients[findClient(ID)].send(new Message("connect", "SERVER", "OK", inMsg.sender));
                break;

            case "login":  // message type: login

                if (findUserThread(inMsg.sender) == null) {
                    if (db.checkLogin(inMsg.sender, inMsg.content)) {
                        clients[findClient(ID)].username = inMsg.sender;
                        clients[findClient(ID)].send(new Message("login", "SERVER", "TRUE", inMsg.sender));
                        sendAll("newuser", "SERVER", inMsg.sender);
                        SendUserList(inMsg.sender);
                    } else {
                        clients[findClient(ID)].send(new Message("login", "SERVER", "FALSE", inMsg.sender));
                    }
                } else {
                    clients[findClient(ID)].send(new Message("login", "SERVER", "FALSE", inMsg.sender));
                }
                break;

            case "register":  // message type: register

                if (findUserThread(inMsg.sender) == null) {
                    if (!db.userExists(inMsg.sender)) {
                        db.addUser(inMsg.sender, inMsg.content);
                        clients[findClient(ID)].username = inMsg.sender;
                        clients[findClient(ID)].send(new Message("register", "SERVER", "TRUE", inMsg.sender));
                        clients[findClient(ID)].send(new Message("login", "SERVER", "TRUE", inMsg.sender));
                        sendAll("newuser", "SERVER", inMsg.sender);
                        SendUserList(inMsg.sender);
                        history.addNewUser(inMsg.sender);
                    } else {
                        clients[findClient(ID)].send(new Message("register", "SERVER", "FALSE", inMsg.sender));
                    }
                } else {
                    clients[findClient(ID)].send(new Message("register", "SERVER", "FALSE", inMsg.sender));
                }
                break;

            case "history":  // message type: history

                // SEND TO THE CLIENT THEIR CHAT HISTORY
                history.sendHistory(inMsg.sender);
                break;

            case "message":  // message type: message

                // FIRST, ADD THE MESSAGE TO THE SENDER'S CHAT HISTORY
                history.addMessage(inMsg, timeStamp(), inMsg.sender);
                
                // THEN, ADD THE MESSAGE TO THE RECIPIENT
                if (inMsg.recipient.equals("Everyone")) {
                
                    // ADD THE MESSAGE TO EVERYONE'S CHAT HISTORY EXCEPT THE SENDER
                    for (int i = 0; i < clientCount; ++i) {
                        if (!clients[i].username.equals(inMsg.sender)) {
                            history.addMessage(inMsg, timeStamp(), clients[i].username);
                        }
                    }
                    
                    // SEND THE MESSAGE TO EVERYONE
                    sendAll("message", inMsg.sender, inMsg.content);
                } else {
                    
                    // ADD THE MESSAGE TO THE RECIPIENT'S CHAT HISTORY
                    history.addMessage(inMsg, timeStamp(), inMsg.recipient);
                    
                    // SEND THE PRIVATE MESSAGE TO THE RECIPIENT
                    findUserThread(inMsg.recipient).send(new Message(inMsg.type, inMsg.sender, inMsg.content, inMsg.recipient));
                    clients[findClient(ID)].send(new Message(inMsg.type, inMsg.sender, inMsg.content, inMsg.recipient));
                }
                break;

            case "signout":  // message type: signout

                // LET EVERYONE KNOW THAT A USER HAS SIGNED OUT
                sendAll("signout", "SERVER", inMsg.sender);
                
                // REMOVE THE USER FROM THE SERVER
                remove(ID);

            default:
                break;
        }
    }

    // make a public announcement
    public void sendAll(String type, String sender, String content) {
        Message outMsg = new Message(type, sender, content, "Everyone");
        for (int i = 0; i < clientCount; i++) {
            clients[i].send(outMsg);
        }
    }

    // send the user the current list of everyone logged on
    public void SendUserList(String user) {
        for (int i = 0; i < clientCount; i++) {
            findUserThread(user).send(new Message("newuser", "SERVER", clients[i].username, user));
        }
    }

    // get the user thread based on the username
    public ServerThread findUserThread(String _username) {
        for (int i = 0; i < clientCount; ++i) {
            if (clients[i].username.equals(_username)) {
                return clients[i];
            }
        }
        return null;
    }

    // remove a client
    @SuppressWarnings("deprecation")
    public synchronized void remove(int ID) {

        int index = findClient(ID);
        if (index >= 0) {
            ServerThread toTerminate = clients[index];
            
            // SHIFT THE ARRAY IF NECESSARY (i.e. not removing the last element in the array)
            if (index < clientCount - 1) {
                for (int i = index + 1; i < clientCount; ++i) {
                    clients[i - 1] = clients[i];
                }
            }
            clientCount--;
            serverWindow.consoleTextArea.append("Removing client thread " + ID + " at " + index + "\n");
            
            try {
                // CLOSE THE SERVER THREAD
                toTerminate.close();
                System.out.println("Closing thread ID: " + ID);
            } catch (IOException ioexception) {
                serverWindow.consoleTextArea.append("Error closing thread ID: " + ID + "\n" + ioexception + "\n");
            }
            toTerminate.interrupt();
        }
    }

    // add a server thread
    private void addThread(Socket socket) {

        if (clientCount < clients.length) {
            serverWindow.consoleTextArea.append("Client accepted: " + socket + "\n");
            clients[clientCount] = new ServerThread(this, socket);
            try {
                clients[clientCount].open();
                clients[clientCount].start();
                clientCount++;
            } catch (IOException ioe) {
                serverWindow.consoleTextArea.append("Error opening thread: " + ioe + "\n");
            }
        } else {
            serverWindow.consoleTextArea.append("Client refused: maximum " + clients.length + " reached.\n");
        }
    }

    // get the current time stamp
    public String timeStamp() {
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date());
    }
}
