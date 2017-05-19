import java.net.*;
import java.io.*;

public class ChatServerThread extends Thread {
    private ChatServer server = null;
    private Socket socket = null;
    private int ID = -1;
    private DataInputStream streamIn = null;
    private DataOutputStream streamOut = null;

	// constructor
    public ChatServerThread(ChatServer server, Socket socket) {
        super();
        this.server = server;
        this.socket = socket;
        ID = socket.getPort();
    }
	
	/*****
	
	send a message to streamOut
	
	*****/
    public void send(String msg) {
        try {
            streamOut.writeUTF(msg);
            streamOut.flush();
        } catch (IOException ioe) {
            System.out.println(ID + " ERROR sending: " + ioe.getMessage());
            server.remove(ID);
            interrupt();
        }
    }

	// get thread ID
    public int getID() {
        return ID;
    }

	
	// NOTE: When you invoke start() method a new thread is created and run() method is executed in this new thread.
    public void run() {
        System.out.println("Server Thread " + ID + " running.");
        while (true) {
            try {
                server.handle(ID, streamIn.readUTF());
            } catch (IOException ioe) {
                System.out.println(ID + " ERROR reading: " + ioe.getMessage());
                server.remove(ID);
                interrupt();
            }
        }
    }

	/*****
	
	setup stream to send and receive data
	
	*****/
	// NOTE: DataInputStream can only read/write primitive types and Strings (better performance)
	//		 ObjectInputStream can send complex data (slower performance)
    public void open() throws IOException {
        streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        streamOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

	/*****
	
	close socket, streamIn and streamOut
	
	*****/
    public void close() throws IOException {
        if (socket != null)
            socket.close();
        if (streamIn != null)
            streamIn.close();
        if (streamOut != null)
            streamOut.close();
    }
}