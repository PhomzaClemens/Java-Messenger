package JMServer;

import java.awt.Color;
import java.io.File;
import javax.swing.JFileChooser;

public class ServerWindow extends javax.swing.JFrame {

    public Server server = null;
    public Thread serverThread = null;
    public String dbFilePath = "";
    public JFileChooser fileChooser = null;

    public ServerWindow() {
        initComponents();

        dbFileTextField.setEditable(false);
        dbFileTextField.setBackground(Color.WHITE);

        fileChooser = new JFileChooser();
        consoleTextArea.setEditable(false);
    }

    // checks to see if we're dealing with a Windows Operating System
    public boolean isWin32() {
        return System.getProperty("os.name").startsWith("Windows");
    }

    // initializes all of the Java swing components objects that the front-end GUI uses (NetBeans GUI Builder)
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        startServerButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        consoleTextArea = new javax.swing.JTextArea();
        dbFileLabel = new javax.swing.JLabel();
        dbFileTextField = new javax.swing.JTextField();
        openButton = new javax.swing.JButton();
        serverPortTextField = new javax.swing.JTextField();
        serverPortLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java Messenger - Server");

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

        dbFileLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        dbFileLabel.setText("Database File:");

        dbFileTextField.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N

        openButton.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        openButton.setText("Open");
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
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
            }
        });

        serverPortLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 13)); // NOI18N
        serverPortLabel.setText("Server Port:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(dbFileLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dbFileTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(openButton, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(serverPortLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(serverPortTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(startServerButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dbFileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dbFileLabel)
                    .addComponent(startServerButton)
                    .addComponent(serverPortLabel)
                    .addComponent(serverPortTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(openButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startServerButtonActionPerformed(java.awt.event.ActionEvent event) {//GEN-FIRST:event_startServerButtonActionPerformed

        if (startServerButton.getText().equals("Start Server")) {
            if (validateServerPortTextField()) {
                server = new Server(this);
            openButton.setEnabled(false);
            startServerButton.setText("Stop Server");
            } else if (!validateServerPortTextField()) {
                consoleTextArea.append("Please enter a valid port number.\n");
            }
        } else if (startServerButton.getText().equals("Stop Server")) {
            server.stop();
            openButton.setEnabled(true);
            startServerButton.setText("Start Server");
            consoleTextArea.append("Server stopped running.\n");
        }

    }//GEN-LAST:event_startServerButtonActionPerformed

    public void RetryStart(int port) {
        if (server != null) {
            server.stop();
        }
        server = new Server(this, port);
    }

    private void openButtonActionPerformed(java.awt.event.ActionEvent event) {//GEN-FIRST:event_openButtonActionPerformed
        File workingDirectory = new File(System.getProperty("user.dir") + "/src");
        fileChooser.setCurrentDirectory(workingDirectory);
        fileChooser.showDialog(this, "Open");
        File file = fileChooser.getSelectedFile();

        if (file != null) {
            dbFilePath = file.getPath();

            if (this.isWin32()) {
                dbFilePath = dbFilePath.replace("\\", "/");
            }
            dbFileTextField.setText(dbFilePath);
            startServerButton.setEnabled(true);
        }
    }//GEN-LAST:event_openButtonActionPerformed

    private void serverPortTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serverPortTextFieldActionPerformed

    }//GEN-LAST:event_serverPortTextFieldActionPerformed

    private void serverPortTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_serverPortTextFieldKeyTyped

        // limit user input
        if (serverPortTextField.getText().length() >= 5) {
            evt.consume();
        }
    }//GEN-LAST:event_serverPortTextFieldKeyTyped

    // validate the server port text field
    private boolean validateServerPortTextField() {

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextArea consoleTextArea;
    private javax.swing.JLabel dbFileLabel;
    private javax.swing.JTextField dbFileTextField;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton openButton;
    private javax.swing.JLabel serverPortLabel;
    public javax.swing.JTextField serverPortTextField;
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
