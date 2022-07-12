/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import MyClient.MyClient;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Linh Tran Bao;
 */ 

public class Server extends javax.swing.JFrame {  
public final static int SERVER_PORT = 2089;
String LinkedString = "####################";
boolean ísConnected = false;
    /**
     * Creates new form MyServer
     */ 
    ServerSocket ss ;  
    ServerSocket ss1; 
    HashMap clientColumn = new HashMap(); 
    Socket s; 
    public Server() {  
        try {
            initComponents();   
            this.setLocationRelativeTo(null);
            ss = new ServerSocket(SERVER_PORT);  
            sStatus.setText("Server Started");
            new ClientAccept().start();
        } catch (Exception e) { 
            e.printStackTrace();
        }  
    }  
    class ClientAccept extends Thread { 
        public void run() { 
            while(true) { 
                try { 
                    s = ss.accept();
                    String nameUser = new DataInputStream(s.getInputStream()).readUTF();  
                    if(clientColumn.containsKey(nameUser)) { 
                        DataOutputStream dout = new DataOutputStream(s.getOutputStream()); 
                        dout.writeUTF("You Are Already Accessed Please Check Error ... !!!");
                    } else  {
                        clientColumn.put(nameUser, s); 
                        msgBox.append(nameUser + " Joined !!!\n"); 
                        DataOutputStream dout = new DataOutputStream(s.getOutputStream()); 
                        dout.writeUTF(""); 
                        new MsgRead(s, nameUser).start(); 
                        new PrepareClientList().start();
                    }
                } catch (Exception e) { 
                    e.printStackTrace();
                }
            }
        }
    } 
    class MsgRead extends Thread{ 
        Socket s; 
        String ID; 
        
        private MsgRead(Socket s, String ID) {
            this.ID = ID; 
            this.s = s;
        }    
        public void run() { 
            while(!clientColumn.isEmpty()){ 
                try {
                    String i = new DataInputStream(s.getInputStream()).readUTF();   
                    if(i.equals(LinkedString)){ 
                        clientColumn.remove(ID); 
                        msgBox.append(ID + ": removed: \n");   
                        Set<String> k = clientColumn.keySet();  
                        
                        Iterator itr = k.iterator();  
                        while(itr.hasNext()){ 
                            String key = (String)itr.next(); 
                            if(!key.equalsIgnoreCase(ID)){ 
                                new DataOutputStream(((Socket)clientColumn.get(key)).getOutputStream()).writeUTF("<Notification:  " + ID  + " offlined>");
                            }
                        }
                    } else if(i.contains(LinkedString)){ 
                        i = i.substring(20); // 
                        StringTokenizer st = new StringTokenizer(i, ":"); 
                        String id = st.nextToken(); 
                        i = st.nextToken(); 
                        new DataOutputStream(((Socket)clientColumn.get(id)).getOutputStream()).writeUTF("< " + ID + " to "  + "me" + " > " + i);
                    } else { 
                        Set k = clientColumn.keySet();
                        Iterator itr = k.iterator();  
                        while(itr.hasNext()){ 
                            String key = (String)itr.next(); 
                            if(!key.equalsIgnoreCase(ID)){ 
                            new DataOutputStream(((Socket)clientColumn.get(key)).getOutputStream()).writeUTF("< " + ID + " to All > " + i);
                            }
                        }
                    }
                } catch (Exception e) { 
                    e.printStackTrace();
                }
            }
        } 
    }
    class PrepareClientList extends Thread{ 
        public void run() { 
            try {
                String ids = ""; 
                Set k = clientColumn.keySet(); 
                System.out.println(k.size());
                Iterator itr = k.iterator();  
                while(itr.hasNext()){
                    String key =(String)itr.next(); 
                    ids += key+",";
                }  
                
                if(ids.length()!= 0) 
                    ids = ids.substring(0, ids.length()-1 );
                    itr = k.iterator(); 
                while(itr.hasNext()){ 
                    String key = (String)itr.next();  
                    new DataOutputStream(((Socket)clientColumn.get(key)).getOutputStream()).writeUTF("@@@@@@" + ids);
                }
            } catch (Exception e) { 
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        msgBox = new javax.swing.JTextArea();
        sStatus = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("My Server");
        setBackground(new java.awt.Color(0, 153, 153));
        setLocation(new java.awt.Point(100, 100));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        msgBox.setBackground(new java.awt.Color(0, 0, 0));
        msgBox.setColumns(20);
        msgBox.setForeground(new java.awt.Color(255, 255, 255));
        msgBox.setRows(5);
        jScrollPane1.setViewportView(msgBox);

        sStatus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        sStatus.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Clients Status:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel2.setText("SERVER");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Server Status:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(sStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(sStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 330, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() { 
            public void run() {
                new Server().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea msgBox;
    private javax.swing.JLabel sStatus;
    // End of variables declaration//GEN-END:variables
}
