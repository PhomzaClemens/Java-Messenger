import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Server extends JFrame {

    private ServerSocket server;
    private Socket connection;
    private Thread thread;
//    private ServerThread client;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private JTextField userText;
    private JTextArea chatWindow;
    private static int PORT = 6789;
    
    // constructor
    public Server(int port) {
        super("Java Messenger");
        PORT = port;
        userText = new JTextField();
        userText.setEditable(false);
        userText.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent event) {
                sendMessage(event.getActionCommand());  // send message after pressing the return key
                userText.setText("");
            }
        });
        add(userText, BorderLayout.SOUTH);
        
        chatWindow = new JTextArea();
        chatWindow.setEditable(false);
        add(new JScrollPane(chatWindow));
        setSize(600, 300); // Sets the window size
        setVisible(true);
    }

    public void startRunning() {
        try {
            System.out.println("Binding to port " + PORT + ", please wait...");
            server = new ServerSocket(PORT, 100); // 6789 is a dummy port for
                                                  // testing, this can be
                                                  // changed. The 100 is the
                                                  // maximum people waiting to
                                                  // connect.
            while (true) {
                try {
                    // Trying to connect and have conversation
                    waitForConnection();
                    setupStreams();
                    whileChatting();
                } catch (EOFException eofException) {
                    showMessage("\n Server ended the connection! ");
                } finally {
                    closeConnection(); // Changed the name to something more
                                       // appropriate
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    // wait for connection, then display connection information
    private void waitForConnection() throws IOException {
        showMessage(" Waiting for someone to connect... \n");
        connection = server.accept();
//        addThread(connection);  // start a new thread when a new connection is made
        showMessage(" Now connected to " + connection.getInetAddress().getHostName());
    }

//    // add a thread
//    public void addThread(Socket socket) {
//        System.out.println("Client accepted: " + socket);
//        client = new ChatServerThread(this, socket);
//        try {
//            client.open();
//            client.start();
//        } catch(IOException e) {
//            System.out.println("Error opening thread: " + e);
//        }
//    }
    
    // setup stream to send and receive data
    private void setupStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();

        input = new ObjectInputStream(connection.getInputStream());

        showMessage("\n Streams are now setup \n");
    }

    // during the chat conversation
    private void whileChatting() throws IOException {
        String message = " You are now connected! ";
        sendMessage(message);  // let the client know that they're connected
        ableToType(true);  // let the server be able to type
        do {
            try {
                message = (String) input.readObject();
                showMessage("\n" + message);
            } catch (ClassNotFoundException classNotFoundException) {
                showMessage("The user has sent an unknown object!");
            }
        } while (!message.equals("CLIENT - END"));  // 
    }

    // close the output and input streams, and close the socket connection
    public void closeConnection() {
        showMessage("\n Closing Connections... \n");
        ableToType(false);
        try {
            output.close(); // closes the output path to the client
            input.close(); // closes the input path to the server, from the client
            connection.close(); // closes the connection between you can the client
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    // send a message to the client
    private void sendMessage(String message) {
        try {
            output.writeObject("SERVER - " + message);  // write message to the ObjectOutputStream
            output.flush();
            showMessage("\nSERVER - " + message);  // update the chat window
        } catch (IOException ioException) {
            chatWindow.append("\n ERROR: CANNOT SEND MESSAGE, PLEASE RETRY");
        }
    }

    // update chatWindow
    private void showMessage(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                chatWindow.append(text);
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

