import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class tarea extends javax.swing.JFrame {
    /**
     * Creates new form tarea
     */
    public tarea() {
        initComponents();
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bt_login = new javax.swing.JButton();
        bt_cancelar = new javax.swing.JButton();
        bt_sigin = new javax.swing.JButton();
        tx_passw = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        tx_username = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bt_login.setBackground(new java.awt.Color(0, 255, 0));
        bt_login.setText("Login");
        bt_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_loginActionPerformed(evt);
            }
        });

        bt_cancelar.setText("cancelar");

        bt_sigin.setBackground(new java.awt.Color(255, 153, 0));
        bt_sigin.setText("sigin");

        jLabel2.setFont(new java.awt.Font("Sitka Display", 1, 18)); // NOI18N
        jLabel2.setText("Ingrese Contraseña");

        jLabel1.setFont(new java.awt.Font("Sitka Display", 1, 18)); // NOI18N
        jLabel1.setText("Ingrese Usuario");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(bt_login)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bt_sigin))
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addComponent(tx_username)
                        .addComponent(tx_passw, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(bt_cancelar)))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tx_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(7, 7, 7)
                .addComponent(tx_passw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_login)
                    .addComponent(bt_sigin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bt_cancelar)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


public void login() {
   String userm = tx_username.getText();
   String passw = tx_passw.getText();

   try {
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost/matricula", "root", "");
      Statement stmt = con.createStatement();
      System.out.println("Conexión exitosa a la base de datos");

      // Verificar si el usuario existe en la base de datos
      String checkUserQuery = "SELECT * FROM usuarios WHERE Username = '" + userm + "'";
      ResultSet userResult = stmt.executeQuery(checkUserQuery);
      if (userResult.next()) {
         int estatus = userResult.getInt("Estatus");
         if (estatus == 1) {
            // Realizar la autenticación
            String storedHashedPassword = userResult.getString("UserPassw");
            String hashedPassword = hashPassword(passw); // Encriptar la contraseña ingresada por el usuario
            String hashedStoredPassword = hashPassword(storedHashedPassword); // Encriptar la contraseña almacenada en la base de datos

            if (hashedPassword.equals(hashedStoredPassword)) {
               JOptionPane.showMessageDialog(null, "Bienvenido");
            } else {
               JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
            }
         } else {
            JOptionPane.showMessageDialog(null, "Contacte con la administración");
         }
      } else {
         JOptionPane.showMessageDialog(null, "Usuario incorrecto");
      }

      userResult.close();
      stmt.close();
      con.close();
   } catch (Exception e) {
      // Handle any exceptions that occur during the process
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "Error de conexión");
   }
}

private String hashPassword(String password) {
   try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] hashedBytes = md.digest(password.getBytes("UTF-8"));

      StringBuilder sb = new StringBuilder();
      for (byte b : hashedBytes) {
         sb.append(String.format("%02x", b));
      }
      return sb.toString();
   } catch (Exception e) {
      e.printStackTrace();
   }
   return null;
}

    private void bt_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_loginActionPerformed
        // TODO add your handling code here:
        login();
    }//GEN-LAST:event_bt_loginActionPerformed

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
            java.util.logging.Logger.getLogger(tarea.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(tarea.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(tarea.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(tarea.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new tarea().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_cancelar;
    private javax.swing.JButton bt_login;
    private javax.swing.JButton bt_sigin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPasswordField tx_passw;
    private javax.swing.JTextField tx_username;
    // End of variables declaration//GEN-END:variables
}
