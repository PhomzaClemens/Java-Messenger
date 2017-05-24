package JMServer;

import java.io.*;
import java.net.*;

public class Server implements Runnable {

    public ServerThread clients[];
    public ServerSocket server = null;
    public Thread thread = null;
    public int clientCount = 0, port = 9000;
    public ServerWindow ui;
    public Database db;

    // constructor
    public Server(ServerWindow serverWindow) {

        clients = new ServerThread[50];
        ui = serverWindow;
        db = new Database(ui.filePath);

        try {
            server = new ServerSocket(port);
            port = server.getLocalPort();
            ui.jTextArea1.append("Server running... IP Address: " + InetAddress.getLocalHost() + ", Port: " + server.getLocalPort());
            start();
        } catch (IOException ioe) {
            ui.jTextArea1.append("Cannot bind to port: " + port + "\nRetrying");
            ui.RetryStart(0);
        }
    }

    // constructor
    public Server(ServerWindow serverWindow, int Port) {

        clients = new ServerThread[50];
        ui = serverWindow;
        port = Port;
        db = new Database(ui.filePath);

        try {
            server = new ServerSocket(port);
            port = server.getLocalPort();
            ui.jTextArea1.append("Server running... IP Address: " + InetAddress.getLocalHost() + ", Port: " + server.getLocalPort());
            start();
        } catch (IOException ioe) {
            ui.jTextArea1.append("\nCannot bind to port " + port + ": " + ioe.getMessage());
        }
    }

    public void run() {
        while (thread != null) {
            try {
                ui.jTextArea1.append("\nWaiting for a client...");
                addThread(server.accept());
            } catch (Exception ioe) {
                ui.jTextArea1.append("\nServer accept error: \n");
                ui.RetryStart(0);
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
    public synchronized void handle(int ID, Message msg) {
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
                if (msg.recipient.equals("Everyone")) {
                    Announce("message", msg.sender, msg.content);
                } else {
                    findUserThread(msg.recipient).send(new Message(msg.type, msg.sender, msg.content, msg.recipient));
                    clients[findClient(ID)].send(new Message(msg.type, msg.sender, msg.content, msg.recipient));
                }
            } else if (msg.type.equals("test")) {
                clients[findClient(ID)].send(new Message("test", "SERVER", "OK", msg.sender));
            } else if (msg.type.equals("register")) {
                if (findUserThread(msg.sender) == null) {
                    if (!db.userExists(msg.sender)) {
                        db.addUser(msg.sender, msg.content);
                        clients[findClient(ID)].username = msg.sender;
                        clients[findClient(ID)].send(new Message("register", "SERVER", "TRUE", msg.sender));
                        clients[findClient(ID)].send(new Message("login", "SERVER", "TRUE", msg.sender));
                        Announce("newuser", "SERVER", msg.sender);
                        SendUserList(msg.sender);
                    } else {
                        clients[findClient(ID)].send(new Message("register", "SERVER", "FALSE", msg.sender));
                    }
                } else {
                    clients[findClient(ID)].send(new Message("register", "SERVER", "FALSE", msg.sender));
                }
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
            ui.jTextArea1.append("\nRemoving client thread " + ID + " at " + pos);
            if (pos < clientCount - 1) {
                for (int i = pos + 1; i < clientCount; i++) {
                    clients[i - 1] = clients[i];
                }
            }
            clientCount--;
            try {
                toTerminate.close();
            } catch (IOException ioe) {
                ui.jTextArea1.append("\nError closing thread: " + ioe);
            }
            toTerminate.interrupt();
        }
    }

    private void addThread(Socket socket) {
        if (clientCount < clients.length) {
            ui.jTextArea1.append("\nClient accepted: " + socket);
            clients[clientCount] = new ServerThread(this, socket);
            try {
                clients[clientCount].open();
                clients[clientCount].start();
                clientCount++;
            } catch (IOException ioe) {
                ui.jTextArea1.append("\nError opening thread: " + ioe);
            }
        } else {
            ui.jTextArea1.append("\nClient refused: maximum " + clients.length + " reached.");
        }
    }
}