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

        serverWindow = _serverWindow;
<<<<<<< HEAD
        db = new Database(serverWindow.dbFilePath);
=======
        clients = new ServerThread[MAX_THREAD];  // create an array of threads where each thread is a client connection (socket)
        db = new Database(serverWindow.dbFilePath);
        history = new History(this);
>>>>>>> version-1.0

        try {
            // START THE SERVER SOCKET
            server = new ServerSocket(Integer.parseInt(serverWindow.serverPortTextField.getText()));
            port = server.getLocalPort();
<<<<<<< HEAD
            serverWindow.consoleTextArea.append("Server running... IP Address: " + InetAddress.getLocalHost() + ", Port: " + server.getLocalPort());
            start();
        } catch (IOException ioe) {
            serverWindow.consoleTextArea.append("Cannot bind to port: " + port + "\nRetrying");
            serverWindow.RetryStart(0);
=======

            // START THE SERVER THREAD
            start();

            // UPDATE THE UI ELEMENT
            serverWindow.consoleTextArea.append("Server running...\nIP Address: " + InetAddress.getLocalHost() + ", Port: " + server.getLocalPort() + "\n");
        } catch (IOException ioexception) {
            serverWindow.consoleTextArea.append("Cannot bind to port " + port + ": " + "Retrying...\n");
            serverWindow.RetryStart(port);
>>>>>>> version-1.0
        }
    }

    // constructor
    public Server(ServerWindow _serverWindow, int _port) {

        serverWindow = _serverWindow;
<<<<<<< HEAD
        port = _port;
        db = new Database(serverWindow.dbFilePath);
=======
        clients = new ServerThread[MAX_THREAD];  // create an array of threads where each thread is a client connection (socket)
        db = new Database(serverWindow.dbFilePath);
        history = new History(this);
        int DEFAULT_PORT = _port;
>>>>>>> version-1.0

        try {
            // START THE SERVER SOCKET
            server = new ServerSocket(DEFAULT_PORT);
            port = server.getLocalPort();
<<<<<<< HEAD
            serverWindow.consoleTextArea.append("Server running... IP Address: " + InetAddress.getLocalHost() + ", Port: " + server.getLocalPort());
            start();
        } catch (IOException ioe) {
            serverWindow.consoleTextArea.append("\nCannot bind to port " + port + ": " + ioe.getMessage());
        }
    }

    // start a new server thread
=======

            // START THE SERVER THREAD
            start();

            // UPDATE THE UI ELEMENT
            serverWindow.consoleTextArea.append("Server running...\nIP Address: " + InetAddress.getLocalHost() + ", Port: " + server.getLocalPort() + "\n");
        } catch (IOException ioexception) {
            serverWindow.consoleTextArea.append("Cannot bind to port " + port + ": " + ioexception.getMessage() + "\n");
        }
    }

    // start the thread
>>>>>>> version-1.0
    public void start() {
        if (thread == null) {
            thread = new Thread(this);

            // START THE THREAD
            thread.start();
        }
    }

<<<<<<< HEAD
    // stop the server thread
=======
    // stop the thread
>>>>>>> version-1.0
    @SuppressWarnings("deprecation")
    public void stop() {
        if (thread != null) {
            thread.interrupt();  // terminate the thread
            thread = null;
        }

        clients = null;
        server = null;
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
                addThread(server.accept());
            } catch (IOException exception) {
                serverWindow.consoleTextArea.append("Server accept error...\n");
                serverWindow.RetryStart(10000);
            }
        }
    }

<<<<<<< HEAD
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
=======
    // find the index of a client based on its ID
>>>>>>> version-1.0
    private int findClient(int ID) {
        for (int i = 0; i < clientCount; ++i) {
            if (clients[i].getID() == ID) {
                return i;
            }
        }
        return -1;
    }

    // handle incoming messages
<<<<<<< HEAD
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
=======
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
>>>>>>> version-1.0
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

<<<<<<< HEAD
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
=======
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
>>>>>>> version-1.0
            if (clients[i].username.equals(_username)) {
                return clients[i];
            }
        }
        return null;
    }

<<<<<<< HEAD
    // remove a client thread
    @SuppressWarnings("deprecation")
    public synchronized void remove(int ID) {

        int index = findClient(ID);  // find the index of the client thread
        if (index >= 0) {
            ServerThread toTerminate = clients[index];

            // shift all the clients on the right side of toTerminate to the left to close the gap
            if (index < clientCount - 1) {  // case: the index of the client thread to terminate is not the last thread in the array
=======
    // remove a client
    @SuppressWarnings("deprecation")
    public synchronized void remove(int ID) {

        int index = findClient(ID);
        if (index >= 0) {
            ServerThread toTerminate = clients[index];
            
            // SHIFT THE ARRAY IF NECESSARY (i.e. not removing the last element in the array)
            if (index < clientCount - 1) {
>>>>>>> version-1.0
                for (int i = index + 1; i < clientCount; ++i) {
                    clients[i - 1] = clients[i];
                }
            }
<<<<<<< HEAD
            clientCount--;  // decrement the client count
            try {
                toTerminate.close();  // close the socket and I/O streams
                serverWindow.consoleTextArea.append("\nRemoving client thread " + ID + " at " + index);
            } catch (IOException ioe) {
                serverWindow.consoleTextArea.append("\nError closing thread: " + ioe);
=======
            clientCount--;
            serverWindow.consoleTextArea.append("Removing client thread " + ID + " at " + index + "\n");
            
            try {
                // CLOSE THE SERVER THREAD
                toTerminate.close();
                System.out.println("Closing thread ID: " + ID);
            } catch (IOException ioexception) {
                serverWindow.consoleTextArea.append("Error closing thread ID: " + ID + "\n" + ioexception + "\n");
>>>>>>> version-1.0
            }
            toTerminate.interrupt();  // finally, terminate the client thread
        }
    }

<<<<<<< HEAD
    // add a client thread
    private void addThread(Socket socket) {

        // if the number of clients logged on doesn't exceed MAX_THREAD, add the client thread
        if (clientCount < MAX_THREAD) {
            clients[clientCount] = new ServerThread(this, socket);  // create a new client thread
=======
    // add a server thread
    private void addThread(Socket socket) {

        if (clientCount < clients.length) {
            serverWindow.consoleTextArea.append("Client accepted: " + socket + "\n");
            clients[clientCount] = new ServerThread(this, socket);
>>>>>>> version-1.0
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
<<<<<<< HEAD
                serverWindow.consoleTextArea.append("\nError opening thread: " + ioe);
            }
        } else {
            serverWindow.consoleTextArea.append("\nClient refused: maximum " + clients.length + " reached.");
=======
                serverWindow.consoleTextArea.append("Error opening thread: " + ioe + "\n");
            }
        } else {
            serverWindow.consoleTextArea.append("Client refused: maximum " + clients.length + " reached.\n");
>>>>>>> version-1.0
        }
    }

    // get the current time stamp
    public String timeStamp() {
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date());
    }
}
