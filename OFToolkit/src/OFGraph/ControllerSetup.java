/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OFGraph;

import edu.uci.ics.jung.graph.Graph;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author 164776
 */
public class ControllerSetup extends javax.swing.JDialog {

    private static ControllerSetup instance = null;
    public static String cName, cType, protocol;
    public static int port;
    public static Inet4Address IP;
    public static DefaultTableModel model, baseModel;
    private static Graph g;
    private static ArrayList<GraphElements.MyVertex> switches;

    public static void setIP(Inet4Address IP) {
        ControllerSetup.IP = IP;
    }

    /**
     * Creates new form ControllerSetup
     *
     * @throws java.net.UnknownHostException
     */
    public ControllerSetup(java.awt.Frame parent, boolean modal, Graph g) throws UnknownHostException {
        super(parent, modal);

        ControllerSetup.g = g;
        ControllerSetup.cName = "c0";
        ControllerSetup.port = 6633;
        ControllerSetup.cType = "Remote Controller";
        ControllerSetup.protocol = "TCP";
        ControllerSetup.IP = (Inet4Address) InetAddress.getByName("127.0.0.1");

        Collection<GraphElements.MyVertex> vertices = g.getVertices();
        switches = new ArrayList<>();
        vertices.stream().map((v) -> {
            if (v.getType().equals("Switch"));
            return v;
        }).forEachOrdered((v) -> {
            switches.add(v);
        });

        initComponents();
    }

    public void updateTable(Graph g) {
        ControllerSetup.g = g;
        Collection<GraphElements.MyVertex> vertices = g.getVertices();
        switches = new ArrayList<>();
        vertices.stream().map((v) -> {
            if (v.getType().equals("Switch"));
            return v;
        }).forEachOrdered((v) -> {
            switches.add(v);
        });
        model = (DefaultTableModel) jTable1.getModel();
        switches.forEach((GraphElements.MyVertex s) -> {
            outerloop:
            if (s.getType().equals("Switch")) {
                for (int i = 0; i < model.getRowCount(); i++) {
                    if (s.getName().equals(model.getValueAt(i, 0))) {
                        break outerloop;
                    }
                }
                Object[] rowData = new Object[2];
                rowData[0] = s.getName();
                rowData[1] = false;
                model.addRow(rowData);
            }
        });
        jTable1.setModel(model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField3 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jTextField3.setText("jTextField1");

        jTextField6.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Controller Setup");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setText("Name:");

        jLabel2.setText("Type:");

        jLabel3.setText("Port:");

        jLabel4.setText("Protocol:");

        jLabel5.setText("IP Address:");

        jTextField1.setText("c0");

        jTextField2.setText("6633");

        jTextField7.setText("127.0.0.1");

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"Remote Controller", "OVS Controller"}));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"TCP", "SSL"}));

        jLabel6.setText("<html>Connect<br>to Switch(es):</html>");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Connected:"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        baseModel = (DefaultTableModel) jTable1.getModel();
        model = (DefaultTableModel) jTable1.getModel();
        switches.forEach((GraphElements.MyVertex s) -> {
            if (s.getType().equals("Switch")) {
                Object[] rowData = new Object[2];
                rowData[0] = s.getName();
                rowData[1] = false;
                model.addRow(rowData);
            }
        });
        jTable1.setModel(model);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //OK button
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (!"".equals(jTextField1.getText())) {
            setcName(jTextField1.getText());
        } else {
            jTextField1.setText(getcName());
        }
        if (!"".equals(jTextField2.getText())) {
            setPort(Integer.parseInt(jTextField2.getText()));
        } else {
            jTextField2.setText(Integer.toString(getPort()));
        }
        setcType(jComboBox1.getSelectedItem().toString());
        setProtocol(jComboBox2.getSelectedItem().toString());
        try {
            if (!"".equals(jTextField7.getText())) {
                ControllerSetup.IP = (Inet4Address) InetAddress.getByName(jTextField7.getText());
            } else {
                jTextField7.setText(ControllerSetup.IP.getHostAddress());
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(ControllerSetup.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (model != null) {
            model = (DefaultTableModel) jTable1.getModel();
        }
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    //Cancel button
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (!getcName().equals(jTextField1.getText())) {
            jTextField1.setText(getcName());
        }
        if (!Integer.toString(getPort()).equals(jTextField2.getText())) {
            jTextField2.setText(Integer.toString(getPort()));
        }
        if (!getcType().equals(jComboBox1.getSelectedItem().toString())) {
            String item;
            for (int i = 0; i < jComboBox1.getItemCount(); i++) {
                item = jComboBox1.getItemAt(i);
                if (item.equals(getcType())) {
                    jComboBox1.setSelectedIndex(i);
                    break;
                }
            }
        }
        if (!getProtocol().equals(jComboBox2.getSelectedItem().toString())) {
            String item;
            for (int i = 0; i < jComboBox2.getItemCount(); i++) {
                item = jComboBox2.getItemAt(i);
                if (item.equals(getProtocol())) {
                    jComboBox2.setSelectedIndex(i);
                    break;
                }
            }
        }
        if (!getIP().getHostAddress().equals(jTextField7.getText())) {
            jTextField7.setText(getIP().getHostAddress());
        }
        try {
            if (!model.equals(jTable1.getModel())) {
                jTable1.setModel(model);
            }
        } catch (NullPointerException e) {

        }

        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (!getcName().equals(jTextField1.getText())) {
            jTextField1.setText(getcName());
        }
        if (!Integer.toString(getPort()).equals(jTextField2.getText())) {
            jTextField2.setText(Integer.toString(getPort()));
        }
        if (!getcType().equals(jComboBox1.getSelectedItem().toString())) {
            String item;
            for (int i = 0; i < jComboBox1.getItemCount(); i++) {
                item = jComboBox1.getItemAt(i);
                if (item.equals(getcType())) {
                    jComboBox1.setSelectedIndex(i);
                    break;
                }
            }
        }
        if (!getProtocol().equals(jComboBox2.getSelectedItem().toString())) {
            String item;
            for (int i = 0; i < jComboBox2.getItemCount(); i++) {
                item = jComboBox2.getItemAt(i);
                if (item.equals(getProtocol())) {
                    jComboBox2.setSelectedIndex(i);
                    break;
                }
            }
        }
        if (!getIP().getHostAddress().equals(jTextField7.getText())) {
            jTextField7.setText(getIP().getHostAddress());
        }
        try {
            if (!model.equals(jTable1.getModel())) {
                jTable1.setModel(model);
            }
        } catch (NullPointerException e) {

        }

        dispose();
    }//GEN-LAST:event_formWindowClosing

    public static ControllerSetup setup(Graph g) throws UnknownHostException {
        if (instance != null) {
            instance.updateTable(g);
        }
        if (instance == null) {
            instance = new ControllerSetup(new javax.swing.JFrame(), true, g);
        }
        instance.setVisible(true);
        return instance;
    }

    public static ControllerSetup getSetup() {
        try {
            return instance;
        } catch (NullPointerException e) {
            System.err.println("ControllerSetup has not yet been initialised.");
            return null;
        }
    }

    public static String getcName() {
        return cName;
    }

    public static void setcName(String cName) {
        ControllerSetup.cName = cName;
    }

    public static String getcType() {
        return cType.replaceAll("\\s+","");
    }

    public static void setcType(String cType) {
        ControllerSetup.cType = cType;
    }

    public static String getProtocol() {
        return protocol;
    }

    public static void setProtocol(String protocol) {
        ControllerSetup.protocol = protocol;
    }

    public static int getPort() {
        return port;
    }
    
    public static String getPortString() {
        return Integer.toString(port);
    }

    public static void setPort(int port) {
        ControllerSetup.port = port;
    }

    public static Inet4Address getIP() {
        return IP;
    }
    
    public static String getIPAddress() {
        return IP.getHostAddress();
    }

    public static DefaultTableModel getModel() {
        return model;
    }

    public static void setModel(DefaultTableModel model) {
        ControllerSetup.model = model;
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables
}
