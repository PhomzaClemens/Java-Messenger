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
 * @author macbookpro
 */
class ServerThread extends Thread {

    public Server server = null;
    public Socket socket = null;
    public ObjectInputStream streamIn = null;
    public ObjectOutputStream streamOut = null;
    
    public int ID = -1;
    public String username = "";
    
    public ServerWindow serverWindow = null;

    // constructor
    public ServerThread(Server _server, Socket _socket) {
        super();
        server = _server;
        socket = _socket;
        ID = socket.getPort();
        serverWindow = _server.serverWindow;
    }

    // send a message
    public void send(Message message) {
        try {
            streamOut.writeObject(message);
            streamOut.flush();
        } catch (IOException exception) {
            System.out.println("Exception [SocketClient    send(...)]");
        }
    }

    // get thread ID
    public int getID() {
        return ID;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void run() {
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
            }
        }
    }

    // open I/O streams
    public void open() throws IOException {
        streamOut = new ObjectOutputStream(socket.getOutputStream());
        streamOut.flush();
        streamIn = new ObjectInputStream(socket.getInputStream());
    }

    // socket and I/O streams
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
