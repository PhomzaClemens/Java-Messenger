import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class Client extends JFrame implements Runnable {

    private JTextField userText;
    private JTextArea chatWindow;

    private ClientThread client;
    private Socket socket;
    private Thread thread;
    private DataInputStream streamIn = null;
    private DataOutputStream streamOut = null;

    // constructor
    public Client(String serverName, int serverPort) {
        /*****
         * 
         * setup the chatWindow
         * 
         *****/
        super("Java Messenger - CLIENT");
        userText = new JTextField();
        // userText.setEditable(false);
        userText.setEditable(true);
        userText.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                // sendMessage(event.getActionCommand());
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
         * initialize data members
         * 
         *****/
        client = null;
        socket = null;
        thread = null;
        streamIn = null;
        streamOut = null;

        /*****
         * 
         * create a stream socket and connects it to the specified port number
         * at the specified IP address
         * 
         *****/
        showMessage("Establishing connection. Please wait...");
        try {
            socket = new Socket(serverName, serverPort);
            showMessage("Connected: " + socket);
            start();
        } catch (UnknownHostException uhe) {
            showMessage("Host unknown: " + uhe.getMessage());
        } catch (IOException ioe) {
            showMessage("Unexpected exception: " + ioe.getMessage());
        }
    }

    public void start() throws IOException {
        streamIn = new DataInputStream(System.in);
        streamOut = new DataOutputStream(socket.getOutputStream());
        if (thread == null) {
            client = new ClientThread(this, socket);
            thread = new Thread(this);
            thread.start();
        }
    }

    /*****
     * 
     * the start() method will spawn a new thread, the new thread will execute
     * run()
     *
     *****/
    @SuppressWarnings("deprecation")
    public void run() {
        while (thread != null) {
            try {
                streamOut.writeUTF(streamIn.readLine());
                streamOut.flush();
            } catch (IOException ioe) {
                System.out.println("Sending error: " + ioe.getMessage());
                stop();
            }
        }
    }

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
        try {
            if (streamIn != null)
                streamIn.close();
            if (streamOut != null)
                streamOut.close();
            if (socket != null)
                socket.close();
        } catch (IOException ioe) {
            System.out.println("Error closing ...");
        }
        client.close();
        client.interrupt();
    }

    /*****
     * 
     * send all clients a message
     * 
     *****/
    public void handle(String msg) {
        if (msg.equals("END")) {
            showMessage("Good bye. Press RETURN to exit ...");
            stop();
        } else
            showMessage(msg);
    }

    // // connect to server
    // public void startRunning() {
    // try {
    // connectToServer();
    // setupStreams();
    // whileChatting();
    // } catch (EOFException eofException) {
    // showMessage("\n Client terminated the connection");
    // } catch (IOException ioException) {
    // ioException.printStackTrace();
    // } finally {
    // closeConnection();
    // }
    // }
    //
    // // connect to server
    // private void connectToServer() throws IOException {
    // showMessage("Attempting connection... \n");
    // connection = new Socket(InetAddress.getByName(serverIP), 6789);
    // showMessage("Connection Established! Connected to: " +
    // connection.getInetAddress().getHostName());
    // }

    // // set up streams
    // private void setupStreams() throws IOException {
    // output = new ObjectOutputStream(connection.getOutputStream());
    // output.flush();
    // input = new ObjectInputStream(connection.getInputStream());
    // showMessage("\n The streams are now set up! \n");
    // }

    // // while chatting with server
    // private void whileChatting() throws IOException {
    // ableToType(true);
    // do {
    // try {
    // message = (String) input.readObject();
    // showMessage("\n" + message);
    // } catch (ClassNotFoundException classNotFoundException) {
    // showMessage("Unknown data received!");
    // }
    // } while (!message.equals("SERVER - END"));
    // }

    // // Close connection
    // private void closeConnection() {
    // showMessage("\n Closing the connection!");
    // ableToType(false);
    // try {
    // output.close();
    // input.close();
    // connection.close();
    // } catch (IOException ioException) {
    // ioException.printStackTrace();
    // }
    // }

    // // send message to server
    // private void sendMessage(String message) {
    // try {
    // output.writeObject("CLIENT - " + message);
    // output.flush();
    // showMessage("\nCLIENT - " + message);
    // } catch (IOException ioException) {
    // chatWindow.append("\n Oops! Something went wrong!");
    // }
    // }

    // update chat window
    private void showMessage(final String msg) {
        System.out.println(msg);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                chatWindow.append(" " + msg + "\n");
            }
        });
    }

    // allows user to type
    private void ableToType(final boolean tof) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                userText.setEditable(tof);
            }
        });
    }
}