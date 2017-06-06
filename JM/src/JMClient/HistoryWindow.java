package JMClient;

<<<<<<< HEAD
import JMClient.History;
import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Font;
=======
import javax.swing.table.DefaultTableModel;
>>>>>>> version-1.0

public class HistoryWindow extends javax.swing.JFrame {

    public String username = "";
    public ClientWindow clientWindow = null;
    
    // constructor
    public HistoryWindow(ClientWindow _clientWindow) {
        initComponents();
        clientWindow = _clientWindow;
    }

    // add a message to the table
    public void addTableEntry(String sender, String content, String recipient, String timeStamp) {
        DefaultTableModel model = (DefaultTableModel) this.historyTable.getModel();
        model.insertRow(0, new Object[]{sender, content, recipient, timeStamp});
    }

    // initializes all of the Java swing components objects that the front-end GUI uses (NetBeans GUI Builder)
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        historyTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat History");

        historyTable.setAutoCreateRowSorter(true);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 873, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

<<<<<<< HEAD
=======
    // checks to see if we're dealing with a Windows Operating System
>>>>>>> version-1.0
    public boolean isWin32() {
        return System.getProperty("os.name").startsWith("Windows");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTable historyTable;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

//    public static void main(String args[]) {
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new HistoryWindow().setVisible(true);
//            }
//        });
//    }
}
