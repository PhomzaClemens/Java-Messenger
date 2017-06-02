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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dbFileTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(openButton, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(startServerButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dbFileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dbFileLabel)
                    .addComponent(openButton)
                    .addComponent(startServerButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startServerButtonActionPerformed(java.awt.event.ActionEvent event) {//GEN-FIRST:event_startServerButtonActionPerformed
        
        if (startServerButton.getText().equals("Start Server")) {
            server = new Server(this);
            openButton.setEnabled(false);
            startServerButton.setText("Stop Server");
        } else if (startServerButton.getText().equals("Stop Server")) {
            server.stop();
            openButton.setEnabled(true);
            startServerButton.setText("Start Server");
            consoleTextArea.append("Server stopped running.");
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextArea consoleTextArea;
    private javax.swing.JLabel dbFileLabel;
    private javax.swing.JTextField dbFileTextField;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton openButton;
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
