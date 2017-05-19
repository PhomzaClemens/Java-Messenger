import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Server extends JFrame implements Runnable {

    private JTextField userText;
    private JTextArea chatWindow;

    private int clientCount = 0;
    private int PORT = 6789;
    private int MAX_THREAD = 100;
    private int MAX_WAITING = 100;
    private ServerThread clients[] = new ServerThread[MAX_THREAD];
    private ServerSocket server = null;
    private Thread thread = null;

    // constructor
    public Server() {
        /*****
         * 
         * setup the chatWindow
         * 
         *****/
        super("Java Messenger - SERVER");
        userText = new JTextField();
        // userText.setEditable(false);
        userText.setEditable(true);
        userText.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                // sendMessage(event.getActionCommand()); // send message after
                // pressing the return
                // key
                userText.setText("");
            }
        });
        add(userText, BorderLayout.SOUTH);

        chatWindow = new JTextArea();
        // chatWindow.setEditable(false);
        chatWindow.setEditable(true);
        add(new JScrollPane(chatWindow));
        setSize(600, 300); // Sets the window size
        setVisible(true);

        /*****
         * 
         * create a ServerSocket that listens on port 6789 for incoming
         * connections from clients via TCP/IP
         * 
         *****/
        try {
            showMessage("Binding to port " + PORT + ", please wait...");
            server = new ServerSocket(PORT, MAX_WAITING); // 6789 is a dummy
                                                          // port for
            // testing, this can be
            // changed. The 100 is the
            // maximum people waiting to
            // connect.
            showMessage("Server started: " + server);
            start();
        } catch (IOException ioe) {
            showMessage("ERROR: Cannot bind to port " + PORT + ": " + ioe.getMessage());
        }
    }

    /*****
     * 
     * create a server thread
     * 
     *****/
    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start(); // The run() method is just an ordinary method
                            // (overridden by you).
                            // As with any other ordinary method and calling it
                            // directly will
                            // cause the current thread to execute run().
                            // All magic happens inside start(). The start()
                            // method will cause
                            // the JVM to spawn a new thread and make the newly
                            // spawned thread execute run().
        }
    }

    /*****
     * 
     * the start() method will spawn a new thread, the new thread will execute
     * run()
     *
     *****/
    public void run() {
        /*****
         * 
         * trying to connect and have conversations
         * 
         *****/
        while (thread != null) {
            try {
                showMessage("Waiting for someone to connect... \n");
                addThread(server.accept()); // waits for a connection
                                            // once a connection is made,
                                            // execution proceeds
            } catch (IOException ioe) {
                showMessage("Server connection error: " + ioe);
                stop();
            }
        }
        // try {
        // waitForConnection();
        // setupStreams();
        // whileChatting();
        // } catch (EOFException eofException) {
        // showMessage("\n Server ended the connection! ");
        // } finally {
        // closeConnection(); // Changed the name to something more
        // // appropriate
        // }
    }

    // // wait for connection, then display connection information
    // private void waitForConnection() throws IOException {
    // while (thread != null) {
    // try {
    // showMessage(" Waiting for someone to connect... \n");
    // socket = server.accept();
    // addThread(socket); // start a new thread when a new connection
    // // is made
    // showMessage(" Now connected to " +
    // socket.getInetAddress().getHostName());
    // } catch (IOException ioe) {
    // showMessage("Server accept error: " + ioe);
    // stop();
    // }
    // }
    // }

    /*****
     * 
     * stop the current thread by sending an interrupt signal
     *
     *****/
    public void stop() {
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
    }

    /*****
     * 
     * return the index of a client
     * 
     *****/
    private int findClient(int ID) {
        for (int i = 0; i < clientCount; i++)
            if (clients[i].getID() == ID)
                return i;
        return -1;
    }

    /*****
     * 
     * send all clients a message
     * 
     *****/
    public synchronized void handle(int ID, String input) {
        if (input.equals("END CONNECTION")) {
            clients[findClient(ID)].send("END CONNECTION");
            showMessage("END CONNECTION - ID: " + ID);
            remove(ID);
        } else
            showMessage(ID + ": " + input);
        for (int i = 0; i < clientCount; ++i)
            clients[i].send(ID + ": " + input);
    }

    /*****
     * 
     * remove a client
     * 
     *****/
    public synchronized void remove(int ID) {
        int pos = findClient(ID);
        if (pos >= 0) {
            ServerThread toTerminate = clients[pos];
            showMessage("Removing client thread " + ID + " at " + pos);
            if (pos < clientCount - 1)
                for (int i = pos + 1; i < clientCount; i++)
                    clients[i - 1] = clients[i];
            clientCount--;
            try {
                toTerminate.close();
            } catch (IOException ioe) {
                showMessage("Error closing thread: " + ioe);
            }
            toTerminate.interrupt();
        }
    }

    /*****
     * 
     * add a thread
     * 
     *****/
    private void addThread(Socket socket) {
        if (clientCount < clients.length) {
            showMessage("Client accepted: " + socket);

            clients[clientCount] = new ServerThread(this, socket);
            try {
                clients[clientCount].open();
                clients[clientCount].start();
                clientCount++;
            } catch (IOException ioe) {
                showMessage("Error opening thread: " + ioe);
            }
        } else {
            showMessage("Client refused: maximum " + clients.length + " reached.");
        }
    }

    // // setup stream to send and receive data
    // private void setupStreams() throws IOException {
    // output = new ObjectOutputStream(connection.getOutputStream());
    // output.flush();
    //
    // input = new ObjectInputStream(connection.getInputStream());
    //
    // showMessage("\n Streams are now setup \n");
    // }

    // // during the chat conversation
    // private void whileChatting() throws IOException {
    // String message = " You are now connected! ";
    // sendMessage(message); // let the client know that they're connected
    // ableToType(true); // let the server be able to type
    // do {
    // try {
    // message = (String) input.readObject();
    // showMessage("\n" + message);
    // } catch (ClassNotFoundException classNotFoundException) {
    // showMessage("The user has sent an unknown object!");
    // }
    // } while (!message.equals("CLIENT - END")); //
    // }

    // // close the output and input streams, and close the socket connection
    // public void closeConnection() {
    // showMessage("\n Closing Connections... \n");
    // ableToType(false);
    // try {
    // output.close(); // closes the output path to the client
    // input.close(); // closes the input path to the server, from the
    // // client
    // connection.close(); // closes the connection between you can the
    // // client
    // } catch (IOException ioException) {
    // ioException.printStackTrace();
    // }
    // }

    // // send a message to the client
    // private void sendMessage(String message) {
    // try {
    // output.writeObject("SERVER - " + message); // write message to the
    // // ObjectOutputStream
    // output.flush();
    // showMessage("\nSERVER - " + message); // update the chat window
    // } catch (IOException ioException) {
    // chatWindow.append("\n ERROR: CANNOT SEND MESSAGE, PLEASE RETRY");
    // }
    // }

    // update chatWindow
    private void showMessage(final String msg) {
        System.out.println(msg);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                chatWindow.append(" " + msg + "\n");
            }
        });
    }

    // set editable to on or off
    private void ableToType(final boolean trueOrFalse) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                userText.setEditable(trueOrFalse);
            }
        });
    }
}
