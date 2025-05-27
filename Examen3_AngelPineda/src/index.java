
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author pc
 */
public class index extends javax.swing.JFrame {

    Connection con, connection;
    Statement stmt, st;
    Properties properties = new Properties();
    Session session;

    /**
     * Creates new form index
     */
    public index() {
        initComponents();
        conexion();
        llenarTabla();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.mail.sender", "jmailexamen58@gmail.com");
        properties.put("mail.smtp.user", "jmailexamen58@gmail.com");
        properties.put("mail.smtp.auth", "true");
        session = Session.getDefaultInstance(properties);
    }

    public static void conexion() {
        String url = "jdbc:mysql://localhost/gestion_contactos";
        String username = "root";
        String password = "";

        try {
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Conexión exitosa a la base de datos");
            // Aquí puedes realizar operaciones con la conexión establecida
            con.close();
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    public void llenarTabla() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/gestion_contactos", "root", "");
            Statement stmt = con.createStatement();

            // Ejecutar la consulta y obtener los resultados en un objeto ResultSet
            String query = "SELECT * FROM contactos";
            ResultSet resultSet = stmt.executeQuery(query);

            // Obtener el modelo de tabla
            DefaultTableModel model = (DefaultTableModel) tcontactos.getModel();

            // Limpiar la tabla antes de llenarla con nuevos datos
            model.setRowCount(0);

            // Iterar sobre los resultados del ResultSet y agregar cada fila al modelo de la tabla
            while (resultSet.next()) {
                Object[] row = new Object[4];
                row[0] = resultSet.getInt("id");
                row[1] = resultSet.getString("nombre");
                row[2] = resultSet.getString("correo");
                row[3] = resultSet.getString("categoria");
                model.addRow(row);
            }
            // Cerrar la conexión y el Statement

            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    public void enviar() {
    String host = "smtp.gmail.com";
    int port = 587;
    String username = txde.getText();
    String password = new String(txpass.getPassword()); // 
    String saludo = txsaludo.getText(); 
    String asunto = txasunto.getText();
    String categoriaSeleccionada = (String) CBcate.getSelectedItem();

    Properties props = new Properties();
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.port", port);
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");

    try {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/gestion_contactos", "root", "");
        Statement stmt = con.createStatement();

        String query = "SELECT nombre, correo FROM contactos WHERE categoria = ?";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, categoriaSeleccionada);

        ResultSet resultSet = preparedStatement.executeQuery();

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        while (resultSet.next()) {
            String recipient = resultSet.getString("correo");
            String nombre = resultSet.getString("nombre");

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(asunto); // Cambio a asunto
            message.setText(saludo + " - " + "Hola " + nombre + ",\n\nEste es un mensaje personalizado para ti."); // Cambio a saludo

            Transport.send(message);

            System.out.println("Correo enviado a: " + recipient);
        }

        preparedStatement.close();
        con.close();
    } catch (SQLException | MessagingException e) {
        e.printStackTrace();
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

        jScrollPane2 = new javax.swing.JScrollPane();
        tcontactos = new javax.swing.JTable();
        CBcate = new javax.swing.JComboBox<>();
        btenviar = new javax.swing.JButton();
        txsaludo = new javax.swing.JTextField();
        txde = new javax.swing.JTextField();
        txpass = new javax.swing.JPasswordField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txasunto = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tcontactos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Correos", "Categorias"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tcontactos);

        CBcate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gold", "Sliver", "Bronze", "Platinum" }));

        btenviar.setText("Enviar");
        btenviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btenviarActionPerformed(evt);
            }
        });

        txsaludo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txsaludoActionPerformed(evt);
            }
        });

        txasunto.setColumns(20);
        txasunto.setRows(5);
        jScrollPane3.setViewportView(txasunto);

        jLabel3.setText("Asunto:");

        jLabel2.setText("De:");

        jLabel1.setText("Titulo / Saludo:");

        jLabel4.setText("Angel Pineda");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txsaludo)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txde, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txpass))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(CBcate, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)
                                .addComponent(btenviar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CBcate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btenviar))
                        .addGap(63, 63, 63))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txsaludo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txpass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(169, 169, 169)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btenviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btenviarActionPerformed
        // TODO add your handling code here:
enviar();
    }//GEN-LAST:event_btenviarActionPerformed

    private void txsaludoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txsaludoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txsaludoActionPerformed

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
            java.util.logging.Logger.getLogger(index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new index().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CBcate;
    private javax.swing.JButton btenviar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tcontactos;
    private javax.swing.JTextArea txasunto;
    private javax.swing.JTextField txde;
    private javax.swing.JPasswordField txpass;
    private javax.swing.JTextField txsaludo;
    // End of variables declaration//GEN-END:variables
}
