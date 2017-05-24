package JMClient;

import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Font;

public class HistoryWindow extends javax.swing.JFrame {

    public History hist;
    public File file;
    public String historyFile;
    public HistoryWindow historyFrame;

    // constructor
    public HistoryWindow() {
        initComponents();
    }

    // constructor
    public HistoryWindow(History hist) {
        initComponents();
        this.hist = hist;
        hist.FillTable(this);
    }

    // initializes all of the Java swing components objects that your front-end GUI uses using the NetBeans GUI Builder
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        historyTable = new javax.swing.JTable();
        saveButton = new javax.swing.JButton();
        historyFileTextField = new javax.swing.JTextField();
        historyFileLabel = new javax.swing.JLabel();
        openButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat History");

        historyTable.setFont(new java.awt.Font("Helvetica Neue", 0, 12)); // NOI18N
        historyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sender", "Message", "To", "Time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(historyTable);
        if (historyTable.getColumnModel().getColumnCount() > 0) {
            historyTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        }

        saveButton.setFont(new java.awt.Font("Helvetica Neue", 0, 12)); // NOI18N
        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        historyFileTextField.setFont(new java.awt.Font("Helvetica Neue", 0, 12)); // NOI18N
        historyFileTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historyFileTextFieldActionPerformed(evt);
            }
        });

        historyFileLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 12)); // NOI18N
        historyFileLabel.setText("History File:");

        openButton.setFont(new java.awt.Font("Helvetica Neue", 0, 12)); // NOI18N
        openButton.setText("Open");
        openButton.setEnabled(true);
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 873, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(historyFileLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(historyFileTextField)
                        .addGap(18, 18, 18)
                        .addComponent(openButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(historyFileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(historyFileLabel)
                    .addComponent(saveButton)
                    .addComponent(openButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // save chat history
        hist.writeToFile(this);
        
        // pop-up notification
        JLabel label = new JLabel("File saved!");
        label.setFont(new Font("Helvetica", Font.PLAIN, 13));
        JOptionPane.showMessageDialog(null, label, "", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_saveButtonActionPerformed

    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
        JFileChooser jfc = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir") + "/src");
        jfc.setCurrentDirectory(workingDirectory);
        jfc.showDialog(this, "Open");

        if (!jfc.getSelectedFile().getPath().isEmpty()) {
            historyFile = jfc.getSelectedFile().getPath();
            if (this.isWin32()) {
                historyFile = historyFile.replace("/", "\\");
            }
            historyFileTextField.setText(historyFile);
            historyFileTextField.setEditable(false);
            hist = new History(historyFile);
        }
    }//GEN-LAST:event_openButtonActionPerformed

    private void historyFileTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historyFileTextFieldActionPerformed
        openButton.doClick();
    }//GEN-LAST:event_historyFileTextFieldActionPerformed

    public boolean isWin32() {
        return System.getProperty("os.name").startsWith("Windows");
    }

    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HistoryWindow().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel historyFileLabel;
    public javax.swing.JTextField historyFileTextField;
    public javax.swing.JTable historyTable;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JButton openButton;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
}
