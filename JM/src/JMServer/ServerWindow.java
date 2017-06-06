package JMServer;

import java.awt.Color;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.text.DefaultCaret;

public class ServerWindow extends javax.swing.JFrame {

<<<<<<< HEAD
    public Server server;
    public Thread serverThread;
    public String dbFilePath;
    public JFileChooser fileChooser;

    public ServerWindow() {
        initComponents();
        dbFileTextField.setEditable(false);
        dbFileTextField.setBackground(Color.WHITE);

        fileChooser = new JFileChooser();
        consoleTextArea.setEditable(false);
    }

    // checking to see if the current OS is Windows
    public boolean isWin32() {
        return System.getProperty("os.name").startsWith("Windows");
=======
    public Server server = null;
    public Thread serverThread = null;
    public String dbFilePath = "";
    public JFileChooser fileChooser = null;

    public ServerWindow() {
        initComponents();

        databaseFileTextField.setEditable(false);
        databaseFileTextField.setBackground(Color.WHITE);
        historyFileTextField.setEditable(false);

        fileChooser = new JFileChooser();
        consoleTextArea.setEditable(false);
        
        // make the console window scroll to the bottom as its default behavior
        DefaultCaret caret = (DefaultCaret) consoleTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
>>>>>>> version-1.0
    }

    // initializes all of the Java swing components objects that the front-end GUI uses (NetBeans GUI Builder)
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        startServerButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        consoleTextArea = new javax.swing.JTextArea();
<<<<<<< HEAD
        dbFileLabel = new javax.swing.JLabel();
        dbFileTextField = new javax.swing.JTextField();
        openButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java Messenger - Server");
=======
        databaseFileLabel = new javax.swing.JLabel();
        databaseFileTextField = new javax.swing.JTextField();
        openDatabaseFileButton = new javax.swing.JButton();
        serverPortTextField = new javax.swing.JTextField();
        serverPortLabel = new javax.swing.JLabel();
        historyFileLabel = new javax.swing.JLabel();
        openHistoryFileButton = new javax.swing.JButton();
        historyFileTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java Messenger - Server");
        setMinimumSize(new java.awt.Dimension(770, 420));
>>>>>>> version-1.0

        startServerButton.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        startServerButton.setText("Start Server");
        startServerButton.setEnabled(false);
        startServerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startServerButtonActionPerformed(evt);
            }
        });

        consoleTextArea.setColumns(20);
        consoleTextArea.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        consoleTextArea.setRows(5);
        jScrollPane1.setViewportView(consoleTextArea);
<<<<<<< HEAD

        dbFileLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        dbFileLabel.setText("Database File:");

        dbFileTextField.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N

        openButton.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        openButton.setText("Open");
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
=======

        databaseFileLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        databaseFileLabel.setText("Database File:");

        databaseFileTextField.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N

        openDatabaseFileButton.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        openDatabaseFileButton.setText("Open");
        openDatabaseFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openDatabaseFileButtonActionPerformed(evt);
            }
        });

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
>>>>>>> version-1.0
            }
        });

        serverPortLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        serverPortLabel.setText("Server Port:");

        historyFileLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        historyFileLabel.setText("History File:");

        openHistoryFileButton.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        openHistoryFileButton.setText("Open");
        openHistoryFileButton.setEnabled(false);
        openHistoryFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openHistoryFileButtonActionPerformed(evt);
            }
        });

        historyFileTextField.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        historyFileTextField.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
<<<<<<< HEAD
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(dbFileLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dbFileTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(openButton, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(startServerButton)))
=======
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(databaseFileLabel)
                            .addComponent(historyFileLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(historyFileTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(openHistoryFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(252, 252, 252))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(databaseFileTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(openDatabaseFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(serverPortLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(serverPortTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(startServerButton)))))
>>>>>>> version-1.0
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
<<<<<<< HEAD
                    .addComponent(dbFileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dbFileLabel)
                    .addComponent(openButton)
                    .addComponent(startServerButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
=======
                    .addComponent(databaseFileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(databaseFileLabel)
                    .addComponent(startServerButton)
                    .addComponent(serverPortLabel)
                    .addComponent(serverPortTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(openDatabaseFileButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(historyFileLabel)
                    .addComponent(openHistoryFileButton)
                    .addComponent(historyFileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
>>>>>>> version-1.0
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

<<<<<<< HEAD
    // this method is called whenever the startServerButton is clicked
    private void startServerButtonActionPerformed(java.awt.event.ActionEvent event) {//GEN-FIRST:event_startServerButtonActionPerformed
        server = new Server(this);
        startServerButton.setEnabled(false);
        openButton.setEnabled(false);
=======
    // start the server
    private void startServerButtonActionPerformed(java.awt.event.ActionEvent event) {//GEN-FIRST:event_startServerButtonActionPerformed

        if (startServerButton.getText().equals("Start Server")) {
            switch (isUserInputValid()) {
                case 0:  // 0 means yes

                    // START THE SERVER
                    server = new Server(this);
                    
                    // UPDATE THE UI ELEMENTS
                    openDatabaseFileButton.setEnabled(false);
                    serverPortTextField.setEnabled(false);
                    openHistoryFileButton.setEnabled(true);
                    startServerButton.setText("Stop Server");
                    break;
                case 1:  // 1 means invalid port number
                    consoleTextArea.append("Please enter a valid port number.\n");
                    break;
                case 2:  // 2 means invalid database file
                    consoleTextArea.append("Choose a valid database file.\n");
                    break;
                default:  // 3 means invalid database file and port number
                    consoleTextArea.append("Please enter a valid port number and database file.\n");
                    break;
            }

        } else if (startServerButton.getText().equals("Stop Server")) {
            
            // STOP THE SERVER
            server.stop();
            
            // UPDATE THE UI ELEMENTS
            openDatabaseFileButton.setEnabled(true);
            startServerButton.setText("Start Server");
            historyFileTextField.setEnabled(false);
            openHistoryFileButton.setEnabled(false);
            consoleTextArea.append("Server stopped running.\n");
        }

>>>>>>> version-1.0
    }//GEN-LAST:event_startServerButtonActionPerformed

    // retry creating a server
    public void RetryStart(int port) {
        if (server != null) {
            server.stop();
        }
        server = new Server(this, port);
    }

<<<<<<< HEAD
    // this method is called whenever the openButton is clicked
    private void openButtonActionPerformed(java.awt.event.ActionEvent event) {//GEN-FIRST:event_openButtonActionPerformed
        
=======
    private void openDatabaseFileButtonActionPerformed(java.awt.event.ActionEvent event) {//GEN-FIRST:event_openDatabaseFileButtonActionPerformed

>>>>>>> version-1.0
        File workingDirectory = new File(System.getProperty("user.dir") + "/src");
        fileChooser.setCurrentDirectory(workingDirectory);
        fileChooser.showDialog(this, "Open");
        File file = fileChooser.getSelectedFile();

        if (file != null) {
            dbFilePath = file.getPath();
<<<<<<< HEAD
            
            if (this.isWin32()) {  // in case of a Windows platform, reformat the file path
                dbFilePath = dbFilePath.replace("\\", "/");
            }
            dbFileTextField.setText(dbFilePath);
            startServerButton.setEnabled(true);
        }
    }//GEN-LAST:event_openButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextArea consoleTextArea;
    private javax.swing.JLabel dbFileLabel;
    private javax.swing.JTextField dbFileTextField;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton openButton;
=======

            if (this.isWin32()) {
                dbFilePath = dbFilePath.replace("\\", "/");
            }
            databaseFileTextField.setText(dbFilePath);

            startServerButton.setEnabled(true);
        }

        System.out.println("Database File Opened");
    }//GEN-LAST:event_openDatabaseFileButtonActionPerformed

    private void serverPortTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serverPortTextFieldActionPerformed
        startServerButton.requestFocus();
    }//GEN-LAST:event_serverPortTextFieldActionPerformed

    private void serverPortTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_serverPortTextFieldKeyTyped

        // limit user input
        if (serverPortTextField.getText().length() >= 5) {
            evt.consume();
        }
    }//GEN-LAST:event_serverPortTextFieldKeyTyped

    private void openHistoryFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openHistoryFileButtonActionPerformed

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Open History File");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            server.history.setFilePath(file.getPath() + "/");
            historyFileTextField.setText(file.getPath() + "/");
            
            openHistoryFileButton.setEnabled(false);
            System.out.println("History File Opened");
        }
    }//GEN-LAST:event_openHistoryFileButtonActionPerformed

    // validate the server port text field
    // returns 0 if both the port number and database file are valid
    // returns 1 if only the port number is valid
    // returns 2 if only the database file is valid
    // returns 3 if both the port number and database file are invalid
    private int isUserInputValid() {

        boolean isPortNumberValid = false;
        boolean isDatabaseFileValid = false;

        try {
            Integer portNumber = Integer.parseInt(serverPortTextField.getText());
            if (0 <= portNumber && portNumber <= 65535) {
                isPortNumberValid = true;
            }
        } catch (NumberFormatException numberFormatException) {
            return 1;
        }

        isDatabaseFileValid = true;  // NEEDS A VALIDATE METHOD
        if (isPortNumberValid && isDatabaseFileValid) {
            return 0;
        } else if (isPortNumberValid == true && isDatabaseFileValid == false) {
            return 1;
        } else if (isPortNumberValid == false && isDatabaseFileValid == true) {
            return 2;
        }
        return 3;
    }

    // checks to see if we're dealing with a Windows Operating System
    public boolean isWin32() {
        return System.getProperty("os.name").startsWith("Windows");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextArea consoleTextArea;
    private javax.swing.JLabel databaseFileLabel;
    private javax.swing.JTextField databaseFileTextField;
    private javax.swing.JLabel historyFileLabel;
    private javax.swing.JTextField historyFileTextField;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton openDatabaseFileButton;
    private javax.swing.JButton openHistoryFileButton;
    private javax.swing.JLabel serverPortLabel;
    public javax.swing.JTextField serverPortTextField;
>>>>>>> version-1.0
    private javax.swing.JButton startServerButton;
    // End of variables declaration//GEN-END:variables

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServerWindow().setVisible(true);
            }
        });
    }

}
