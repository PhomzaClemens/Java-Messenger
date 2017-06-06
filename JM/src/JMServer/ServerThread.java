/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JMServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author David Kim
 */
class ServerThread extends Thread {

<<<<<<< HEAD
    public Server server;
    public Socket socket;
    public int ID = -1;
    public String username;
    public ObjectInputStream streamIn;
    public ObjectOutputStream streamOut;
    public ServerWindow serverWindow;
=======
    public Server server = null;
    public Socket socket = null;
    public ObjectInputStream streamIn = null;
    public ObjectOutputStream streamOut = null;
    
    public int ID = -1;
    public String username = "";
    
    public ServerWindow serverWindow = null;
>>>>>>> version-1.0

    // constructor
    public ServerThread(Server _server, Socket _socket) {
        
        super();  // calls Thread's constructor
        server = _server;
        socket = _socket;
        ID = _socket.getPort();
        serverWindow = _server.serverWindow;
    }

<<<<<<< HEAD
    // send a message to the client
    public void send(Message outgoingMessage) {
        
        try {
            streamOut.writeObject(outgoingMessage);  // the method writeObject is used to send an object to the stream
=======
    // send a message
    public void send(Message message) {
        try {
            streamOut.writeObject(message);
>>>>>>> version-1.0
            streamOut.flush();
        } catch (IOException exception) {
            System.out.println("Exception ServerThread.send()");
        }
    }

    // get thread ID
    public int getID() {
        return ID;
    }

    // this method gets called after this thread is started
    @SuppressWarnings("deprecation")
    @Override
    public void run() {
<<<<<<< HEAD
        
        serverWindow.consoleTextArea.append("\nServer Thread " + ID + " running.");
        while (true) {  // continually listen for incoming messages from the client
            try {
                Message incomingMessage = (Message) streamIn.readObject();  // the method readObject is used to read an object from the stream
                server.handler(ID, incomingMessage);  // pass the incoming message object to server's handler
            } catch (Exception ioe) {
                System.out.println(ID + " ERROR reading: " + ioe.getMessage());
                server.remove(ID);  // remove the client from the server
                interrupt();  // terminate this thread
=======
        serverWindow.consoleTextArea.append("Server Thread " + ID + " running.\n");
        while (true) {
            try {
                
                // READ IN MESSAGES FROM THE CLIENT
                Message message = (Message) streamIn.readObject();
                
                // SEND THE MESSAGES TO THE SERVER HANDLER
                server.handler(ID, message);
                
            } catch (IOException | ClassNotFoundException exception) {
                System.out.println(ID + " ERROR reading: " + exception.getMessage());
                server.remove(ID);
                stop();
>>>>>>> version-1.0
            }
        }
    }

    // open I/O streams
    public void openStreams() throws IOException {
        streamOut = new ObjectOutputStream(socket.getOutputStream());
        streamOut.flush();
        streamIn = new ObjectInputStream(socket.getInputStream());
    }

    // close the socket and I/O streams
    public void close() throws IOException {
        if (socket != null) {
            socket.close();
        }
        if (streamIn != null) {
            streamIn.close();
        }
        if (streamOut != null) {
            streamOut.close();
        }
    }
}
