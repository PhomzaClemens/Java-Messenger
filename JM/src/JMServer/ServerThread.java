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
    public int ID = -1;
    public String username = "";
    public ObjectInputStream streamIn = null;
    public ObjectOutputStream streamOut = null;
    public ServerWindow ui;

    // constructor
    public ServerThread(Server _server, Socket _socket) {
        super();
        server = _server;
        socket = _socket;
        ID = socket.getPort();
        ui = _server.ui;
    }

    // send a message
    public void send(Message msg) {
        try {
            streamOut.writeObject(msg);
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
    public void run() {
        ui.jTextArea1.append("\nServer Thread " + ID + " running.");
        while (true) {
            try {
                Message msg = (Message) streamIn.readObject();
                server.handler(ID, msg);
            } catch (Exception ioe) {
                System.out.println(ID + " ERROR reading: " + ioe.getMessage());
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
