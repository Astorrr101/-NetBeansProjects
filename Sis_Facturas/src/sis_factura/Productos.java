package sis_factura;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Productos extends javax.swing.JInternalFrame {

    public Productos() {
        initComponents();
        llenarTabla();
        llenarCombox();
        selecCombox();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tx_codigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tx_nombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tx_descrip = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tx_precio = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        CB_provee = new javax.swing.JComboBox<>();
        tx_cant = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        bt_insetar = new javax.swing.JButton();
        bt_update = new javax.swing.JButton();
        btsalir = new javax.swing.JButton();
        l_pro = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_productos = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Productos");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Agregar/Actualizar Productos"));

        jLabel1.setText("Codigo:");

        jLabel2.setText("Nombre:");

        jLabel3.setText("Descripticion:");

        jLabel4.setText("Precio:");

        jLabel5.setText("Proveedor:");

        jLabel6.setText("Cantidad:");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Opciones"));

        bt_insetar.setText("Insetar");
        bt_insetar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_insetarActionPerformed(evt);
            }
        });

        bt_update.setText("Actualizar");
        bt_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_updateActionPerformed(evt);
            }
        });

        btsalir.setText("Salir");
        btsalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btsalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bt_insetar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btsalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bt_update, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bt_insetar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bt_update)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btsalir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(4, 4, 4)
                        .addComponent(tx_precio, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGap(4, 4, 4)
                        .addComponent(tx_cant, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CB_provee, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(4, 4, 4)
                                .addComponent(tx_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tx_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tx_descrip, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(l_pro)
                        .addGap(53, 53, 53)))
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tx_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)
                                    .addComponent(tx_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(tx_descrip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(l_pro))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(tx_precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(tx_cant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(CB_provee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tb_productos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));
        tb_productos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre", "Descripcion", "Precio", "Cantidad", "Cod_Proveedor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_productos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_productosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_productos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
public void llenarTabla() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/sistemafactura", "root", "");
            Statement stmt = con.createStatement();

            // Ejecutar la consulta y obtener los resultados en un objeto ResultSet
            String query = "SELECT * FROM productos";
            ResultSet resultSet = stmt.executeQuery(query);

            // Obtener el modelo de tabla
            DefaultTableModel model = (DefaultTableModel) tb_productos.getModel();

            // Limpiar la tabla antes de llenarla con nuevos datos
            model.setRowCount(0);

            // Iterar sobre los resultados del ResultSet y agregar cada fila al modelo de la tabla
            while (resultSet.next()) {
                Object[] row = new Object[6];
                row[0] = resultSet.getInt("id_producto");
                row[1] = resultSet.getString("nombre");
                row[2] = resultSet.getString("descripcion");
                row[3] = resultSet.getString("precio");
                row[4] = resultSet.getString("cantidad");
                row[5] = resultSet.getString("proveedor_id");
                model.addRow(row);
            }
         // Cerrar la conexión y el Statement
   
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

 public void mostrarDatosFilaSeleccionada() {
    int indiceFilaSeleccionada = tb_productos.getSelectedRow();

    if (indiceFilaSeleccionada >= 0) {
        DefaultTableModel modeloTabla = (DefaultTableModel) tb_productos.getModel();

        // Obtener los valores de las celdas seleccionadas
        String codigo = modeloTabla.getValueAt(indiceFilaSeleccionada, 0).toString();
        String nombre = modeloTabla.getValueAt(indiceFilaSeleccionada, 1).toString();
        String descripcion = modeloTabla.getValueAt(indiceFilaSeleccionada, 2).toString();
        String precio = modeloTabla.getValueAt(indiceFilaSeleccionada, 3).toString();
        String cantidad = modeloTabla.getValueAt(indiceFilaSeleccionada, 4).toString();

        // Mostrar los valores en los campos de texto correspondientes
        tx_codigo.setText(codigo);
        tx_nombre.setText(nombre);
        tx_descrip.setText(descripcion);
        tx_precio.setText(precio);
        tx_cant.setText(cantidad);
    }
}


    public void llenarCombox() {
        try {
            // Crear una conexión a la base de datos
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/sistemafactura", "root", "");
            Statement statement = con.createStatement();

            // Crear una consulta SQL para obtener los ID y nombres de los proveedores
            String consulta = "SELECT id_proveedor, nombre FROM Proveedores";
            ResultSet resultSet = statement.executeQuery(consulta);

            // Limpiar el JComboBox
            CB_provee.removeAllItems();

            // Iterar sobre los resultados y agregar los ID y nombres al JComboBox
            while (resultSet.next()) {
                int idProveedor = resultSet.getInt("id_proveedor");
                String nombreProveedor = resultSet.getString("nombre");

                // Concatenar el ID y nombre en un solo String
                String item = idProveedor + " - " + nombreProveedor;

                // Agregar el item al JComboBox
                CB_provee.addItem(item);
            }
         

            // Cerrar la conexión y liberar los recursos
            resultSet.close();
            statement.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
public void selecCombox(){
     // Obtener el elemento seleccionado en el JComboBox
            String vendedorId = (String) CB_provee.getSelectedItem();
            String idp = vendedorId.split(" - ")[0]; // Obtener solo el ID del proveedor

            // Asignar el ID del proveedor al JLabel
            l_pro.setText(idp + "");
}
    private void bt_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_updateActionPerformed
        // TODO add your handling code here:
         selecCombox();
    }//GEN-LAST:event_bt_updateActionPerformed

    private void btsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btsalirActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btsalirActionPerformed

    private void bt_insetarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_insetarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bt_insetarActionPerformed

    private void tb_productosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_productosMouseClicked
        // TODO add your handling code here:
        mostrarDatosFilaSeleccionada();
    }//GEN-LAST:event_tb_productosMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CB_provee;
    private javax.swing.JButton bt_insetar;
    private javax.swing.JButton bt_update;
    private javax.swing.JButton btsalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel l_pro;
    private javax.swing.JTable tb_productos;
    private javax.swing.JTextField tx_cant;
    private javax.swing.JTextField tx_codigo;
    private javax.swing.JTextField tx_descrip;
    private javax.swing.JTextField tx_nombre;
    private javax.swing.JTextField tx_precio;
    // End of variables declaration//GEN-END:variables
}
