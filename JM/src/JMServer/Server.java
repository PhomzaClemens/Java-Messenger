package JMServer;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable {

    public ServerThread clients[];
    public ServerSocket server = null;
    public Thread thread = null;
    public int clientCount = 0, port = 9000, MAX_THREAD = 50;
    public ServerWindow serverWindow;
    public Database db;
    public History history;

    // constructor
    public Server(ServerWindow _serverWindow) {

        clients = new ServerThread[MAX_THREAD];
        serverWindow = _serverWindow;
        db = new Database(serverWindow.dbFilePath);
        history = new History(this);

        try {
            server = new ServerSocket(port);
            port = server.getLocalPort();
            serverWindow.consoleTextArea.append("Server running... IP Address: " + InetAddress.getLocalHost() + ", Port: " + server.getLocalPort());
            start();
        } catch (IOException ioe) {
            serverWindow.consoleTextArea.append("Cannot bind to port: " + port + "\nRetrying");
            serverWindow.RetryStart(0);
        }
    }

    // constructor
    public Server(ServerWindow _serverWindow, int _port) {

        clients = new ServerThread[MAX_THREAD];
        serverWindow = _serverWindow;
        port = _port;
        db = new Database(serverWindow.dbFilePath);

        try {
            server = new ServerSocket(port);
            port = server.getLocalPort();
            serverWindow.consoleTextArea.append("Server running... IP Address: " + InetAddress.getLocalHost() + ", Port: " + server.getLocalPort());
            start();
        } catch (IOException ioe) {
            serverWindow.consoleTextArea.append("\nCannot bind to port " + port + ": " + ioe.getMessage());
        }
    }

    public void run() {
        while (thread != null) {
            try {
                serverWindow.consoleTextArea.append("\nWaiting for a client...");
                addThread(server.accept());
            } catch (Exception ioe) {
                serverWindow.consoleTextArea.append("\nServer accept error: \n");
                serverWindow.RetryStart(0);
            }
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
            thread.interrupt();
            thread = null;
        }
    }

    private int findClient(int ID) {
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].getID() == ID) {
                return i;
            }
        }
        return -1;
    }

    // handle incoming messages
    public synchronized void handler(int ID, Message msg) throws IOException {
        if (msg.content.equals(".disconnect")) {
            Announce("signout", "SERVER", msg.sender);
            remove(ID);
        } else {
            if (msg.type.equals("login")) {
                if (findUserThread(msg.sender) == null) {
                    if (db.checkLogin(msg.sender, msg.content)) {
                        clients[findClient(ID)].username = msg.sender;
                        clients[findClient(ID)].send(new Message("login", "SERVER", "TRUE", msg.sender));
                        Announce("newuser", "SERVER", msg.sender);
                        SendUserList(msg.sender);
                    } else {
                        clients[findClient(ID)].send(new Message("login", "SERVER", "FALSE", msg.sender));
                    }
                } else {
                    clients[findClient(ID)].send(new Message("login", "SERVER", "FALSE", msg.sender));
                }
            } else if (msg.type.equals("message")) {
                history.addMessage(msg, timeStamp(), msg.sender);
                if (msg.recipient.equals("Everyone")) {
                    Announce("message", msg.sender, msg.content);
                } else {
                    findUserThread(msg.recipient).send(new Message(msg.type, msg.sender, msg.content, msg.recipient));
                    clients[findClient(ID)].send(new Message(msg.type, msg.sender, msg.content, msg.recipient));
                }
            } else if (msg.type.equals("connect")) {
                clients[findClient(ID)].send(new Message("connect", "SERVER", "OK", msg.sender));
            } else if (msg.type.equals("register")) {
                if (findUserThread(msg.sender) == null) {
                    if (!db.userExists(msg.sender)) {
                        db.addUser(msg.sender, msg.content);
                        clients[findClient(ID)].username = msg.sender;
                        clients[findClient(ID)].send(new Message("register", "SERVER", "TRUE", msg.sender));
                        clients[findClient(ID)].send(new Message("login", "SERVER", "TRUE", msg.sender));
                        Announce("newuser", "SERVER", msg.sender);
                        SendUserList(msg.sender);
                        history.addNewUser(msg.sender);
                    } else {
                        clients[findClient(ID)].send(new Message("register", "SERVER", "FALSE", msg.sender));
                    }
                } else {
                    clients[findClient(ID)].send(new Message("register", "SERVER", "FALSE", msg.sender));
                }
            } else if (msg.type.equals("history")) {
                history.sendHistory(msg.sender);
            }
        }
    }

    public void Announce(String type, String sender, String content) {
        Message msg = new Message(type, sender, content, "Everyone");
        for (int i = 0; i < clientCount; i++) {
            clients[i].send(msg);
        }
    }

    public void SendUserList(String user) {
        for (int i = 0; i < clientCount; i++) {
            findUserThread(user).send(new Message("newuser", "SERVER", clients[i].username, user));
        }
    }

    public ServerThread findUserThread(String user) {
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].username.equals(user)) {
                return clients[i];
            }
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    public synchronized void remove(int ID) {
        int pos = findClient(ID);
        if (pos >= 0) {
            ServerThread toTerminate = clients[pos];
            serverWindow.consoleTextArea.append("\nRemoving client thread " + ID + " at " + pos);
            if (pos < clientCount - 1) {
                for (int i = pos + 1; i < clientCount; i++) {
                    clients[i - 1] = clients[i];
                }
            }
            clientCount--;
            try {
                toTerminate.close();
            } catch (IOException ioe) {
                serverWindow.consoleTextArea.append("\nError closing thread: " + ioe);
            }
            toTerminate.interrupt();
        }
    }

    private void addThread(Socket socket) {
        if (clientCount < clients.length) {
            serverWindow.consoleTextArea.append("\nClient accepted: " + socket);
            clients[clientCount] = new ServerThread(this, socket);
            try {
                clients[clientCount].open();
                clients[clientCount].start();
                clientCount++;
            } catch (IOException ioe) {
                serverWindow.consoleTextArea.append("\nError opening thread: " + ioe);
            }
        } else {
            serverWindow.consoleTextArea.append("\nClient refused: maximum " + clients.length + " reached.");
        }
    }

    // get the current time stamp
    public String timeStamp() {
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date());
    }
}
