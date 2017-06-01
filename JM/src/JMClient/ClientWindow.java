package JMClient;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import JMServer.Message;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class ClientWindow extends javax.swing.JFrame {

    public Client client;
    public Thread clientThread;
    public String serverAddress;
    public int port;
    public String username;
    public String password;
    public DefaultListModel model;
    public String historyFile;
    public HistoryWindow historyWindow;

    // constructor
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ClientWindow() {
        initComponents();
        this.setTitle("JMessenger - Client");
        model.addElement("Everyone");
        userList.setSelectedIndex(0);
        historyWindow = new HistoryWindow();
        historyWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        historyWindow.setVisible(false);
        historyButton.setEnabled(false);
        messageTextField.setEditable(false);
        consoleTextArea.setEditable(false);

        this.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    client.send(new Message("message", username, ".disconnect", "SERVER"));
                    clientThread.interrupt();
                } catch (Exception exception) {
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
    }

    // checks to see if we're dealing with a Windows Operating System
    public boolean isWin32() {
        return System.getProperty("os.name").startsWith("Windows");
    }

    // initializes all of the Java swing components objects that your front-end GUI uses using the NetBeans GUI Builder
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        serverAddressLabel = new javax.swing.JLabel();
        serverAddressTextField = new javax.swing.JTextField();
        serverPortLabel = new javax.swing.JLabel();
        serverPortTextField = new javax.swing.JTextField();
        connectButton = new javax.swing.JButton();
        usernameTextField = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        registerButton = new javax.swing.JButton();
        passwordPasswordField = new javax.swing.JPasswordField();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        consoleTextArea = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        userList = new javax.swing.JList();
        msgLabel = new javax.swing.JLabel();
        messageTextField = new javax.swing.JTextField();
        clearButton = new javax.swing.JButton();
        loginButton = new javax.swing.JButton();
        historyButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(820, 470));

        serverAddressLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        serverAddressLabel.setText("Server Address:");

        serverAddressTextField.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        serverAddressTextField.setText("127.0.0.1");
        serverAddressTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serverAddressTextFieldActionPerformed(evt);
            }
        });
        serverAddressTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                serverAddressTextFieldKeyTyped(evt);
            }
        });

        serverPortLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        serverPortLabel.setText("Server Port:");

        serverPortTextField.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        serverPortTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        serverPortTextField.setText("9000");
        serverPortTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serverPortTextFieldActionPerformed(evt);
            }
        });
        serverPortTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                serverPortTextFieldKeyTyped(evt);
            }
        });

        connectButton.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        connectButton.setText("Connect");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

        usernameTextField.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        usernameTextField.setText("username");
        usernameTextField.setEnabled(false);
        usernameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameTextFieldActionPerformed(evt);
            }
        });
        usernameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                usernameTextFieldKeyTyped(evt);
            }
        });

        passwordLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        passwordLabel.setText("Password:");

        usernameLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        usernameLabel.setText("Username:");

        registerButton.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        registerButton.setText("Register");
        registerButton.setEnabled(false);
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });

        passwordPasswordField.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        passwordPasswordField.setText("password");
        passwordPasswordField.setEnabled(false);
        passwordPasswordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordPasswordFieldActionPerformed(evt);
            }
        });
        passwordPasswordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                passwordPasswordFieldKeyTyped(evt);
            }
        });

        consoleTextArea.setColumns(20);
        consoleTextArea.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        consoleTextArea.setLineWrap(true);
        consoleTextArea.setRows(5);
        jScrollPane1.setViewportView(consoleTextArea);

        userList.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        userList.setModel((model = new DefaultListModel()));
        jScrollPane2.setViewportView(userList);

        msgLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        msgLabel.setText("Message:");

        messageTextField.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        messageTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messageTextFieldActionPerformed(evt);
            }
        });

        clearButton.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        clearButton.setText("Clear");
        clearButton.setEnabled(false);
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        loginButton.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        loginButton.setText("Login");
        loginButton.setEnabled(false);
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        historyButton.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        historyButton.setText("History");
        historyButton.setEnabled(true);
        historyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historyButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        jLabel1.setText("JMessenger™");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 612, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(serverAddressLabel)
                            .addComponent(usernameLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(serverAddressTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                            .addComponent(usernameTextField))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(serverPortLabel)
                            .addComponent(passwordLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(serverPortTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                                .addComponent(connectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(passwordPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(registerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(historyButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel1)))
                        .addGap(4, 4, 4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(msgLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(messageTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(serverAddressLabel)
                    .addComponent(serverAddressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(serverPortLabel)
                    .addComponent(serverPortTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(connectButton)
                    .addComponent(historyButton)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordLabel)
                    .addComponent(usernameLabel)
                    .addComponent(registerButton)
                    .addComponent(passwordPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loginButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clearButton)
                    .addComponent(msgLabel)
                    .addComponent(messageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // when the connect button is pressed, connect to the server
    // when the disconnect button is pressed, disconnect from the server
    private void connectButtonActionPerformed(java.awt.event.ActionEvent event) {//GEN-FIRST:event_connectButtonActionPerformed

        // validate user input
        boolean validated = validateTextField();

        // two cases: connect button, disconnect button
        if (connectButton.getText().equals("Connect")) {
            if (validated) {
                serverAddress = serverAddressTextField.getText();
                port = Integer.parseInt(serverPortTextField.getText());

                try {
                    client = new Client(this, historyWindow);  // instantiate a new client attached to this clientWindow instance
                    clientThread = new Thread(client);  // create a new client thread
                    clientThread.start();

                    client.send(new Message("connect", "requester", "", "SERVER"));
                    connectButton.setText("Disconnect");
                    serverAddressTextField.setEditable(false);
                    serverPortTextField.setEditable(false);
                    usernameTextField.requestFocus();  // set the cursor to the username textbox after clicking on the connect button
                    usernameTextField.setEditable(true);
                    passwordPasswordField.setEditable(true);
                    messageTextField.setEditable(true);

                } catch (IOException exception) {
                    consoleTextArea.append("[Application -> Me]    Server not found\n");
                    serverAddressTextField.requestFocus();
                }

            } else {
                consoleTextArea.append("[Application -> Me]    User Input Error: Please Re-Enter the Server Address and/or Port Number\n");
                serverAddressTextField.requestFocus();
                return;
            }
        } else if (connectButton.getText().equals("Disconnect")) {

            loginButton.setEnabled(false);
            registerButton.setEnabled(false);
            usernameTextField.setEditable(false);
            passwordPasswordField.setEditable(false);
            serverAddressTextField.setEditable(true);
            serverPortTextField.setEditable(true);
            messageTextField.setEditable(false);

            usernameTextField.setText("");
            passwordPasswordField.setText("");
            connectButton.setText("Connect");
            client.send(new Message("signout", username, ".disconnect", "SERVER"));
        }
    }//GEN-LAST:event_connectButtonActionPerformed

    // validate server address text field and server port text field
    private boolean validateTextField() {

        try {
            Integer portNumber = Integer.parseInt(serverPortTextField.getText());
            if (0 <= portNumber && portNumber <= 65535) {
                return true;
            } else {
                return false;  // port number is out-of-bounds (ie. 0-65535)
            }
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    // login button
    private void loginButtonActionPerformed(java.awt.event.ActionEvent event) {//GEN-FIRST:event_loginButtonActionPerformed
        username = usernameTextField.getText();
        password = String.valueOf(passwordPasswordField.getPassword());

        if (!username.isEmpty() && !password.isEmpty()) {
            client.send(new Message("login", username, password, "SERVER"));
        }
        messageTextField.requestFocus();
    }//GEN-LAST:event_loginButtonActionPerformed

    // clear the console
    private void clearButtonActionPerformed(java.awt.event.ActionEvent event) {//GEN-FIRST:event_clearButtonActionPerformed
        consoleTextArea.setText("");
    }//GEN-LAST:event_clearButtonActionPerformed

    private void registerButtonActionPerformed(java.awt.event.ActionEvent event) {//GEN-FIRST:event_registerButtonActionPerformed
        username = usernameTextField.getText();
        password = String.valueOf(passwordPasswordField.getPassword());

        if (!username.isEmpty() && !password.isEmpty()) {
            client.send(new Message("register", username, password, "SERVER"));
        }
    }//GEN-LAST:event_registerButtonActionPerformed

    private void messageTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_messageTextFieldActionPerformed
        String message = messageTextField.getText();
        String target = userList.getSelectedValue().toString();

        if (!message.isEmpty() && !target.isEmpty()) {
            messageTextField.setText("");
            client.send(new Message("message", username, message, target));
        }
    }//GEN-LAST:event_messageTextFieldActionPerformed

    // click on the login button when the user presses the return key in the password field
    private void passwordPasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordPasswordFieldActionPerformed
        loginButton.doClick();
        messageTextField.requestFocus();
    }//GEN-LAST:event_passwordPasswordFieldActionPerformed

    // click on the connect button when the user presses the return key in the port field
    private void serverPortTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serverPortTextFieldActionPerformed
        connectButton.doClick();
    }//GEN-LAST:event_serverPortTextFieldActionPerformed

    private void historyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historyButtonActionPerformed
        historyWindow.setLocation(this.getLocation());
        historyWindow.setVisible(true);
        requestHistory();
    }//GEN-LAST:event_historyButtonActionPerformed

    private void usernameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameTextFieldActionPerformed
        passwordPasswordField.requestFocus();
    }//GEN-LAST:event_usernameTextFieldActionPerformed

    private void serverAddressTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serverAddressTextFieldActionPerformed
        serverPortTextField.requestFocus();
    }//GEN-LAST:event_serverAddressTextFieldActionPerformed

    private void serverPortTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_serverPortTextFieldKeyTyped
        if (serverPortTextField.getText().length() >= 5) {
            evt.consume();
        }
    }//GEN-LAST:event_serverPortTextFieldKeyTyped

    private void serverAddressTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_serverAddressTextFieldKeyTyped
        if (serverAddressTextField.getText().length() >= 32) {
            evt.consume();
        }
    }//GEN-LAST:event_serverAddressTextFieldKeyTyped

    private void usernameTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usernameTextFieldKeyTyped
        if (usernameTextField.getText().length() >= 32) {
            evt.consume();
        }
    }//GEN-LAST:event_usernameTextFieldKeyTyped

    private void passwordPasswordFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordPasswordFieldKeyTyped
        if (passwordPasswordField.getText().length() >= 20) {
            evt.consume();
        }
    }//GEN-LAST:event_passwordPasswordFieldKeyTyped

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        try {
            Desktop.getDesktop().browse(new URI("https://github.com/kimdj/Java-Messenger"));
        } catch (URISyntaxException ex) {
            Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel1MouseClicked

    public void historyButtonOn() {
        historyButton.setEnabled(true);
    }

    public void historyButtonOff() {
        historyButton.setEnabled(false);
    }

    public void requestHistory() {
        DefaultTableModel model = (DefaultTableModel) historyWindow.historyTable.getModel();
        model.setRowCount(0);
        Message outgoingMessage = new Message("history", username, "", "SERVER");
        client.send(outgoingMessage);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton clearButton;
    public javax.swing.JButton connectButton;
    public javax.swing.JTextArea consoleTextArea;
    private javax.swing.JButton historyButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    public javax.swing.JButton loginButton;
    public javax.swing.JTextField messageTextField;
    private javax.swing.JLabel msgLabel;
    private javax.swing.JLabel passwordLabel;
    public javax.swing.JPasswordField passwordPasswordField;
    public javax.swing.JButton registerButton;
    private javax.swing.JLabel serverAddressLabel;
    public javax.swing.JTextField serverAddressTextField;
    private javax.swing.JLabel serverPortLabel;
    public javax.swing.JTextField serverPortTextField;
    public javax.swing.JList userList;
    private javax.swing.JLabel usernameLabel;
    public javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables
}
