package JMServer;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.util.Date;

public class Server implements Runnable {

    public ServerThread clients[] = null;
    public ServerSocket server = null;
    public Thread thread = null;
    public Database db = null;
    public History history = null;

    public int clientCount = 0, port = 9000, MAX_THREAD = 50;

    public ServerWindow serverWindow = null;

    // constructor
    public Server(ServerWindow _serverWindow) {

        clients = new ServerThread[MAX_THREAD];
        serverWindow = _serverWindow;
        db = new Database(serverWindow.dbFilePath);
        history = new History(this);

        try {
            port = Integer.parseInt(serverWindow.serverPortTextField.getText());
            server = new ServerSocket(port);
            port = server.getLocalPort();
            start();

            serverWindow.consoleTextArea.append("Server running...\nIP Address: " + InetAddress.getLocalHost() + ", Port: " + server.getLocalPort() + "\n");
        } catch (IOException ioexception) {
            serverWindow.consoleTextArea.append("Cannot bind to port " + port + ": " + "Retrying...\n");
            serverWindow.RetryStart(port);
        }
    }

    // constructor
    public Server(ServerWindow _serverWindow, int _port) {

        clients = new ServerThread[MAX_THREAD];
        serverWindow = _serverWindow;
        port = _port;
        db = new Database(serverWindow.dbFilePath);

        try {
            port = Integer.parseInt(serverWindow.serverPortTextField.getText());
            server = new ServerSocket(port);
            port = server.getLocalPort();
            serverWindow.consoleTextArea.append("Server running...\nIP Address: " + InetAddress.getLocalHost() + ", Port: " + server.getLocalPort() + "\n");
            start();
        } catch (IOException ioe) {
            serverWindow.consoleTextArea.append("Cannot bind to port " + port + ": " + ioe.getMessage() + "\n");
        }
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    @SuppressWarnings("deprecation")
    public void stop() {
        if (thread != null) {
            thread.stop();
            thread = null;
        }

        ServerThread clients[] = null;
        ServerSocket server = null;
        Thread thread = null;
        Database db = null;
        History history = null;

        clientCount = 0;
        port = 9000;
        MAX_THREAD = 50;

        serverWindow = null;
    }

// code executed in the thread
    @Override
    public void run() {
        while (thread != null) {
            try {
                serverWindow.consoleTextArea.append("Waiting for a client...\n");
                addThread(server.accept());
            } catch (IOException exception) {
                serverWindow.consoleTextArea.append("Server accept error: \n");
                serverWindow.RetryStart(0);
            }
        }
    }

    // find the index of a client based on its ID
    private int findClient(int ID) {
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].getID() == ID) {
                return i;
            }
        }
        return -1;
    }

    // handle incoming messages
    public synchronized void handler(int ID, Message incomingMessage) throws IOException {

        // two cases: incoming message is a disconnection request, or not
        if (incomingMessage.content.equals(".disconnect")) {

            Announce("signout", "SERVER", incomingMessage.sender);
            remove(ID);

        } else {

            if (incomingMessage.type.equals("login")) {  // message type: login

                if (findUserThread(incomingMessage.sender) == null) {
                    if (db.checkLogin(incomingMessage.sender, incomingMessage.content)) {
                        clients[findClient(ID)].username = incomingMessage.sender;
                        clients[findClient(ID)].send(new Message("login", "SERVER", "TRUE", incomingMessage.sender));
                        Announce("newuser", "SERVER", incomingMessage.sender);
                        SendUserList(incomingMessage.sender);
                    } else {
                        clients[findClient(ID)].send(new Message("login", "SERVER", "FALSE", incomingMessage.sender));
                    }
                } else {
                    clients[findClient(ID)].send(new Message("login", "SERVER", "FALSE", incomingMessage.sender));
                }

            } else if (incomingMessage.type.equals("message")) {  // message type: message

                history.addMessage(incomingMessage, timeStamp(), incomingMessage.sender);
                if (incomingMessage.recipient.equals("Everyone")) {
                    Announce("message", incomingMessage.sender, incomingMessage.content);
                } else {
                    findUserThread(incomingMessage.recipient).send(new Message(incomingMessage.type, incomingMessage.sender, incomingMessage.content, incomingMessage.recipient));
                    clients[findClient(ID)].send(new Message(incomingMessage.type, incomingMessage.sender, incomingMessage.content, incomingMessage.recipient));
                }

            } else if (incomingMessage.type.equals("connect")) {  // message type: connect

                clients[findClient(ID)].send(new Message("connect", "SERVER", "OK", incomingMessage.sender));

            } else if (incomingMessage.type.equals("register")) {  // message type: register

                if (findUserThread(incomingMessage.sender) == null) {
                    if (!db.userExists(incomingMessage.sender)) {
                        db.addUser(incomingMessage.sender, incomingMessage.content);
                        clients[findClient(ID)].username = incomingMessage.sender;
                        clients[findClient(ID)].send(new Message("register", "SERVER", "TRUE", incomingMessage.sender));
                        clients[findClient(ID)].send(new Message("login", "SERVER", "TRUE", incomingMessage.sender));
                        Announce("newuser", "SERVER", incomingMessage.sender);
                        SendUserList(incomingMessage.sender);
                        history.addNewUser(incomingMessage.sender);
                    } else {
                        clients[findClient(ID)].send(new Message("register", "SERVER", "FALSE", incomingMessage.sender));
                    }
                } else {
                    clients[findClient(ID)].send(new Message("register", "SERVER", "FALSE", incomingMessage.sender));
                }

            } else if (incomingMessage.type.equals("history")) {  // message type: history

                history.sendHistory(incomingMessage.sender);
            }
        }
    }

    // make a public announcement
    public void Announce(String type, String sender, String content) {
        Message outgoingMessage = new Message(type, sender, content, "Everyone");
        for (int i = 0; i < clientCount; i++) {
            clients[i].send(outgoingMessage);
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

        int pos = findClient(ID);
        if (pos >= 0) {
            ServerThread toTerminate = clients[pos];
            serverWindow.consoleTextArea.append("Removing client thread " + ID + " at " + pos + "\n");
            if (pos < clientCount - 1) {
                for (int i = pos + 1; i < clientCount; i++) {
                    clients[i - 1] = clients[i];
                }
            }
            clientCount--;
            try {
                toTerminate.close();
                System.out.println("CLOSING THREAD");
            } catch (IOException ioexception) {
                serverWindow.consoleTextArea.append("Error closing thread: " + ioexception + "\n");
            }
            toTerminate.stop();
        }
    }

    // add a client thread
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
