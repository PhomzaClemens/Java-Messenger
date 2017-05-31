package JMServer;

import java.io.*;
import java.net.*;

public class Server implements Runnable {

    public ServerThread clients[];
    public ServerSocket server = null;
    public Thread thread = null;
    public int clientCount = 0, port = 9000, MAX_THREAD = 50;
    public ServerWindow serverWindow;
    public Database db;

    // constructor
    public Server(ServerWindow _serverWindow) {

        clients = new ServerThread[MAX_THREAD];
        serverWindow = _serverWindow;
        db = new Database(serverWindow.dbFilePath);

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

    // start a new server thread
    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    // stop the server thread
    @SuppressWarnings("deprecation")
    public void stop() {
        if (thread != null) {
            thread.interrupt();  // terminate the thread
            thread = null;
        }
    }

    // this method gets called after this thread is started
    public void run() {
        while (thread != null) {  // continually listen for incoming connection requests until the server thread is no longer running
            try {
                serverWindow.consoleTextArea.append("\nWaiting for a client...");
                Socket connection = server.accept();  // listens for a connection to be made to this socket and accepts it
                addThread(connection);  // add a new connection thread
            } catch (Exception ioe) {
                serverWindow.consoleTextArea.append("\nServer accept error\n");
                serverWindow.RetryStart(0);
            }
        }
    }

    // find the index based on the client's ID
    private int findClient(int ID) {
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].getID() == ID) {
                return i;
            }
        }
        return -1;
    }

    // handle incoming messages
    public synchronized void handler(int ID, Message incomingMessage) {

        if (incomingMessage.content.equals(".disconnect")) {
            Announce("signout", "SERVER", incomingMessage.sender);
            remove(ID);
        } else {
            if (incomingMessage.type.equals("login")) {  // case: login message

                if (findUserThread(incomingMessage.sender) == null) {  // if the user hasn't been assigned a thread yet...
                    if (db.checkLogin(incomingMessage.sender, incomingMessage.content)) {  // ...and if the username and password is valid
                        clients[findClient(ID)].username = incomingMessage.sender;  // assign the username
                        clients[findClient(ID)].send(new Message("login", "SERVER", "TRUE", incomingMessage.sender));  // let the client know that login was successful
                        SendUserList(incomingMessage.sender);  // send the client the current userlist
                        Announce("newuser", "SERVER", incomingMessage.sender);  // let everyone know about the new client logging in
                    } else {  // ...and if the username and password is invalid
                        clients[findClient(ID)].send(new Message("login", "SERVER", "FALSE", incomingMessage.sender));  // let the client know that login was unsuccessful
                    }
                } else {  // the user has already been assigned a thread
                    clients[findClient(ID)].send(new Message("login", "SERVER", "FALSE", incomingMessage.sender));
                }

            } else if (incomingMessage.type.equals("message")) {  // case: regular message

                if (incomingMessage.recipient.equals("Everyone")) {  // the message is a public message
                    Announce("message", incomingMessage.sender, incomingMessage.content);
                } else {  // the message is a private message
                    Message outgoingMessage = new Message(incomingMessage.type, incomingMessage.sender, incomingMessage.content, incomingMessage.recipient);
                    findUserThread(incomingMessage.recipient).send(outgoingMessage);
                    clients[findClient(ID)].send(outgoingMessage);
                }

            } else if (incomingMessage.type.equals("connect")) {  // case: connect message

                clients[findClient(ID)].send(new Message("connect", "SERVER", "OK", incomingMessage.sender));

            } else if (incomingMessage.type.equals("register")) {  // case: register message

                if (findUserThread(incomingMessage.sender) == null) {  // if the user hasn't been assigned a thread yet...
                    if (db.userExists(incomingMessage.sender)) {  // ...and if the user already exists in the database
                        clients[findClient(ID)].send(new Message("register", "SERVER", "FALSE", incomingMessage.sender));
                    } else {  // the user doesn't exist in the database yet
                        db.addUser(incomingMessage.sender, incomingMessage.content);  // register the new user
                        clients[findClient(ID)].username = incomingMessage.sender;
                        clients[findClient(ID)].send(new Message("register", "SERVER", "TRUE", incomingMessage.sender));
                        clients[findClient(ID)].send(new Message("login", "SERVER", "TRUE", incomingMessage.sender));
                        Announce("newuser", "SERVER", incomingMessage.sender);
                        SendUserList(incomingMessage.sender);
                    }
                } else {  // the user has already been assigned a thread
                    clients[findClient(ID)].send(new Message("register", "SERVER", "FALSE", incomingMessage.sender));
                }
            }
        }
    }

    // send an announcement to everyone logged on
    public void Announce(String type, String sender, String content) {
        Message outgoingMessage = new Message(type, sender, content, "Everyone");
        for (int i = 0; i < clientCount; ++i) {
            clients[i].send(outgoingMessage);
        }
    }

    // send the userlist to the client
    public void SendUserList(String _username) {
        for (int i = 0; i < clientCount; ++i) {
            ServerThread recipient = findUserThread(_username);
            recipient.send(new Message("newuser", "SERVER", clients[i].username, _username));
        }
    }

    // find a client thread based on the username
    public ServerThread findUserThread(String _username) {

        for (int i = 0; i < clientCount; i++) {
            if (clients[i].username.equals(_username)) {
                return clients[i];
            }
        }
        return null;
    }

    // remove a client thread
    @SuppressWarnings("deprecation")
    public synchronized void remove(int ID) {

        int index = findClient(ID);  // find the index of the client thread
        if (index >= 0) {
            ServerThread toTerminate = clients[index];

            // shift all the clients on the right side of toTerminate to the left to close the gap
            if (index < clientCount - 1) {  // case: the index of the client thread to terminate is not the last thread in the array
                for (int i = index + 1; i < clientCount; ++i) {
                    clients[i - 1] = clients[i];
                }
            }
            clientCount--;  // decrement the client count
            try {
                toTerminate.close();  // close the socket and I/O streams
                serverWindow.consoleTextArea.append("\nRemoving client thread " + ID + " at " + index);
            } catch (IOException ioe) {
                serverWindow.consoleTextArea.append("\nError closing thread: " + ioe);
            }
            toTerminate.interrupt();  // finally, terminate the client thread
        }
    }

    // add a client thread
    private void addThread(Socket socket) {

        // if the number of clients logged on doesn't exceed MAX_THREAD, add the client thread
        if (clientCount < MAX_THREAD) {
            clients[clientCount] = new ServerThread(this, socket);  // create a new client thread
            try {
                clients[clientCount].openStreams();
                clients[clientCount].start();
                /*
                ServerThread.start()
                
                causes this thread to begin execution
                the Java Virtual Machine calls the run method of this thread
                the result is that two threads are running concurrently: the current thread (which returns from the call to the start method) and the other thread (which executes its run method).

                it is never legal to start a thread more than once
                in particular, a thread may not be restarted once it has completed execution
                 */
                clientCount++;
                serverWindow.consoleTextArea.append("\nClient accepted: " + socket);
            } catch (IOException ioe) {
                serverWindow.consoleTextArea.append("\nError opening thread: " + ioe);
            }
        } else {
            serverWindow.consoleTextArea.append("\nClient refused: maximum " + clients.length + " reached.");
        }
    }
}
