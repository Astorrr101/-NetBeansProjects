/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sis_factura;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pc
 */
public class Factura_Ventas extends javax.swing.JInternalFrame {

    Connection con, connection;
    Statement stmt, st;

    /**
     * Creates new form Factura_Ventas
     */
    public Factura_Ventas() {
        initComponents();
        llenarTabla();
        camposproductos();
        llenarCombox();
    }

    public void camposproductos() {
        tx_codigo1.setText("0");
        tx_nombre1.setText("-");
        tx_descrip.setText("-");
        tx_precio.setText("0");
        tx_cant.setText("0");
    }

    public void FilaSeleccionada() {
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
            tx_codigo1.setText(codigo);
            tx_nombre1.setText(nombre);
            tx_descrip.setText(descripcion);
            tx_precio.setText(precio);
            tx_cant.setText(cantidad);
        }
    }

    public void quitarfila() {
        int indice = tb_ventas.getSelectedRow();
        if (indice >= 0) {
            DefaultTableModel mymodelo = (DefaultTableModel) tb_ventas.getModel();
            mymodelo.removeRow(indice);
        }
    }

    private void calcularSubtotal() {
        DefaultTableModel mymodelo = (DefaultTableModel) tb_ventas.getModel();
        int columnaSubtotal = 4;
        double subtotal = 0.0;
        double isvPorcentaje = 0.15;

        for (int i = 0; i < mymodelo.getRowCount(); i++) {
            double valorSubtotal = Double.parseDouble(mymodelo.getValueAt(i, columnaSubtotal).toString());
            subtotal += valorSubtotal;
        }

        double isv = subtotal * isvPorcentaje;
        isv = Math.round(isv * 100.0) / 100.0; // Redondea a 2 decimales

        tx_sbt.setText(String.valueOf(subtotal));

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.'); // Establece el punto como separador decimal
        DecimalFormat df = new DecimalFormat("#0.00", symbols); // Formato personalizado

        txisv.setText(df.format(isv));

        double totalGeneral = subtotal + isv;
        txtotal.setText(String.valueOf(totalGeneral));
    }

    public void insertarDatos() {
        // Obtener los valores de los campos de texto
        String codigo = tx_codigo1.getText();
        String descripcion = tx_descrip.getText();
        double precio = Double.parseDouble(tx_precio.getText()); // Convertir a double
        int cantidad = Integer.parseInt(tx_cant.getText()); // Convertir a int
        double subtotal = precio * cantidad;

        // Formatear el subtotal con dos decimales y convertirlo a String
        String subtotalString = String.format("%.2f", subtotal);

        // Agregar los datos a la tabla
        String[] datos = {codigo, descripcion, String.valueOf(precio), String.valueOf(cantidad), subtotalString};
        DefaultTableModel mymodelo = (DefaultTableModel) tb_ventas.getModel();
        mymodelo.addRow(datos);

        // Limpiar los campos de texto
        tx_codigo1.setText("0");
        tx_nombre1.setText("-");
        tx_descrip.setText("-");
        tx_precio.setText("0");
        tx_cant.setText("0");
    }

    public void llenarCombox() {
        try {
            // Crear una conexión a la base de datos
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/sistemafactura", "root", "");
            Statement statement = con.createStatement();

            // Crear una consulta SQL para obtener los ID y nombres de los vendedores
            String consulta = "SELECT id_vendedor, nombre FROM vendedores";
            ResultSet resultSet = statement.executeQuery(consulta);

            // Limpiar el JComboBox
            cb_vendedores.removeAllItems();

            // Iterar sobre los resultados y agregar los ID y nombres al JComboBox
            while (resultSet.next()) {
                int idVendedor = resultSet.getInt("id_vendedor");
                String nombreVendedor = resultSet.getString("nombre");

                // Concatenar el ID y nombre en un solo String
                String item = idVendedor + " - " + nombreVendedor;

                // Agregar el item al JComboBox
                cb_vendedores.addItem(item);
            }

            // Cerrar la conexión y liberar los recursos
            resultSet.close();
            statement.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guardafactura() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/sistemafactura", "root", "");
            stmt = con.createStatement();
            System.out.println("Conexión exitosa a la base de datos");

            String idFacturaVenta = tx_codigo.getText();
            String fechaVenta = tx_fecha.getText();
            String clienteId = tx_codigo.getText();

            // Obtener el elemento seleccionado en el JComboBox
            String vendedorId = (String) cb_vendedores.getSelectedItem();
            String idvendedor = vendedorId.split(" - ")[0]; // Obtener solo el ID del proveedor
            String sbt = tx_sbt.getText();
            String isv = txisv.getText();
            String total = txtotal.getText();

            String consulta = "INSERT INTO facturaventas (id_factura_venta, fecha_venta, cliente_id, vendedor_id, SBT, ISV, total) VALUES ('"
                    + idFacturaVenta + "','" + fechaVenta + "','" + clienteId + "','" + idvendedor + "','" + sbt + "','" + isv + "','" + total + "')";

            stmt.executeUpdate(consulta);
            System.out.println("Datos insertados en la tabla facturaventas");

            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void guardardetallefactura() {
        try {
            String facturaID = tx_codigo.getText(); // Obtener el valor de FacturaID del JTextField
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/sistemafactura", "root", "");
            String sql = "INSERT INTO detallefacturaventas (id_detalle_venta, factura_venta_id, producto_id, cantidad, precio, SBT) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sql);

            DefaultTableModel model = (DefaultTableModel) tb_ventas.getModel();
            int rowCount = model.getRowCount();

            for (int i = 0; i < rowCount; i++) {
                String productoID = model.getValueAt(i, 0).toString();
                String descripcion = model.getValueAt(i, 1).toString();
                String precio = model.getValueAt(i, 2).toString();
                String cantidad = model.getValueAt(i, 3).toString();
                String subtotal = model.getValueAt(i, 4).toString();

                statement.setString(1, facturaID);
                statement.setString(2, facturaID);
                statement.setString(3, productoID);
                statement.setString(4, cantidad);
                statement.setString(5, precio);
                statement.setDouble(6, Double.parseDouble(subtotal)); // Asignar el valor de subtotal como un Double en la columna SBT

                statement.executeUpdate();
            }

            statement.close();
            con.close();

            System.out.println("Datos insertados correctamente en la tabla detallefacturaventas");
        } catch (SQLException e) {
            System.err.println("Error al insertar datos en la tabla detallefacturaventas: " + e.getMessage());
        }
    }

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

    public void insetarclientes() {
        try {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/sistemafactura", "root", "");
        String sql = "INSERT INTO clientes (id_cliente, nombre, direccion, telefono, email) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql);

        String idCliente = tx_codigo.getText();
        String nombreCliente = tx_nombre.getText();
        String direccionCliente = tx_dirre.getText();
        String telefonoCliente = tx_tele.getText();
        String emailCliente = tx_email.getText();

        statement.setString(1, idCliente);
        statement.setString(2, nombreCliente);
        statement.setString(3, direccionCliente);
        statement.setString(4, telefonoCliente);
        statement.setString(5, emailCliente);

        statement.executeUpdate();
        System.out.println("Datos insertados en la tabla clientes");

        statement.close();
        con.close();
    } catch (SQLException e) {
        System.err.println("Error al insertar datos en la tabla clientes: " + e.getMessage());
    }
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
        tx_dirre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tx_tele = new javax.swing.JTextField();
        tx_email = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        tx_fecha = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        cb_vendedores = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        tx_codigo1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        tx_nombre1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tx_descrip = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        tx_precio = new javax.swing.JTextField();
        tx_cant = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        l_pro = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        bt_insetar1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_ventas = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_productos = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        tx_sbt = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txisv = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtotal = new javax.swing.JTextField();
        bt_nuevo = new javax.swing.JButton();
        bt_cancelar = new javax.swing.JButton();
        bt_guardar = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        bt_quitar = new javax.swing.JButton();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        tx_codigo2 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        tx_nombre2 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        tx_dirre1 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        tx_tele1 = new javax.swing.JTextField();
        tx_email1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        tx_codigo3 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        tx_nombre3 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        tx_descrip1 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        tx_precio1 = new javax.swing.JTextField();
        tx_cant1 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        l_pro1 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        bt_insetar2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tb_ventas1 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tb_productos1 = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();

        setBackground(new java.awt.Color(204, 204, 204));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Facturas_Ventas");
        setEnabled(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Clientes"));

        jLabel1.setText("Codigo:");

        tx_codigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_codigoActionPerformed(evt);
            }
        });

        jLabel2.setText("Nombre:");

        jLabel3.setText("Direccion");

        tx_dirre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_dirreActionPerformed(evt);
            }
        });

        jLabel4.setText("Telefeno:");

        tx_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_emailActionPerformed(evt);
            }
        });

        jLabel6.setText("Email:");

        jLabel24.setText("ID Vendedor");

        tx_fecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_fechaActionPerformed(evt);
            }
        });

        jLabel25.setText("Fecha");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tx_tele, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tx_email))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tx_dirre))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tx_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tx_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tx_fecha)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25))
                        .addGap(0, 55, Short.MAX_VALUE))
                    .addComponent(cb_vendedores, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cb_vendedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel25)
                        .addGap(8, 8, 8)
                        .addComponent(tx_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(tx_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(tx_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(tx_dirre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(tx_tele, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(tx_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Seleccionar Productos"));

        jLabel5.setText("Codigo:");

        tx_codigo1.setEditable(false);

        jLabel7.setText("Nombre:");

        tx_nombre1.setEditable(false);
        tx_nombre1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_nombre1ActionPerformed(evt);
            }
        });

        jLabel8.setText("Descripticion:");

        tx_descrip.setEditable(false);
        tx_descrip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_descripActionPerformed(evt);
            }
        });

        jLabel9.setText("Precio:");

        tx_precio.setEditable(false);

        tx_cant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_cantActionPerformed(evt);
            }
        });

        jLabel11.setText("Cantidad:");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Opciones"));

        bt_insetar1.setText("Insetar");
        bt_insetar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_insetar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bt_insetar1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bt_insetar1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(4, 4, 4)
                        .addComponent(tx_precio, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tx_cant))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(4, 4, 4)
                        .addComponent(tx_codigo1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tx_nombre1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tx_descrip)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(l_pro)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel5))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tx_codigo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addComponent(tx_nombre1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(tx_descrip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(l_pro))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(tx_precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(tx_cant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tb_ventas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Descripcion", "Precio", "Cantidad", "SBT"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tb_ventas);

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
        jScrollPane2.setViewportView(tb_productos);

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("Sub-Total");

        tx_sbt.setText("0");
        tx_sbt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_sbtActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setText("I.S.V");

        txisv.setText("0");
        txisv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txisvActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setText("Total");

        txtotal.setText("0");

        bt_nuevo.setText("Nuevo");

        bt_cancelar.setText("Cancelar");

        bt_guardar.setText("Guardar");
        bt_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_guardarActionPerformed(evt);
            }
        });

        jButton4.setText("Salir");

        bt_quitar.setText("Quitar");
        bt_quitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_quitarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(10, 10, 10)
                        .addComponent(tx_sbt, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel22)
                        .addGap(10, 10, 10)
                        .addComponent(txisv, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel23)
                        .addGap(10, 10, 10)
                        .addComponent(txtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 30, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(bt_nuevo)
                        .addGap(18, 18, 18)
                        .addComponent(bt_cancelar)
                        .addGap(18, 18, 18)
                        .addComponent(bt_guardar)
                        .addGap(18, 18, 18)
                        .addComponent(bt_quitar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txisv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tx_sbt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel22)
                            .addComponent(jLabel21))))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bt_nuevo)
                        .addComponent(bt_cancelar)
                        .addComponent(bt_guardar))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton4)
                        .addComponent(bt_quitar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jInternalFrame1.setClosable(true);
        jInternalFrame1.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jInternalFrame1.setIconifiable(true);
        jInternalFrame1.setMaximizable(true);
        jInternalFrame1.setResizable(true);
        jInternalFrame1.setTitle("Facturas_Ventas");
        jInternalFrame1.setEnabled(false);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Clientes"));

        jLabel10.setText("Codigo:");

        tx_codigo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_codigo2ActionPerformed(evt);
            }
        });

        jLabel12.setText("Nombre:");

        jLabel13.setText("Direccion");

        jLabel14.setText("Telefeno:");

        jLabel15.setText("Email:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tx_codigo2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tx_nombre2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tx_dirre1))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tx_tele1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tx_email1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(tx_codigo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12))
                    .addComponent(tx_nombre2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(tx_dirre1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(tx_tele1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(tx_email1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Seleccionar Productos"));

        jLabel16.setText("Codigo:");

        tx_codigo3.setEditable(false);

        jLabel17.setText("Nombre:");

        tx_nombre3.setEditable(false);
        tx_nombre3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_nombre3ActionPerformed(evt);
            }
        });

        jLabel18.setText("Descripticion:");

        tx_descrip1.setEditable(false);
        tx_descrip1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_descrip1ActionPerformed(evt);
            }
        });

        jLabel19.setText("Precio:");

        tx_precio1.setEditable(false);

        tx_cant1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_cant1ActionPerformed(evt);
            }
        });

        jLabel20.setText("Cantidad:");

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Opciones"));

        bt_insetar2.setText("Insetar");
        bt_insetar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_insetar2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bt_insetar2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bt_insetar2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(4, 4, 4)
                        .addComponent(tx_precio1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tx_cant1, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(4, 4, 4)
                        .addComponent(tx_codigo3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tx_nombre3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tx_descrip1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(l_pro1)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel16))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tx_codigo3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17)
                                    .addComponent(tx_nombre3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(tx_descrip1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(l_pro1))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(tx_precio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addComponent(tx_cant1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tb_ventas1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo_Producto", "Descripcion", "Precio", "Cantidad", "SBT"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tb_ventas1);

        tb_productos1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));
        tb_productos1.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_productos1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_productos1MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tb_productos1);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 457, Short.MAX_VALUE)
                    .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 458, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 36, Short.MAX_VALUE))))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 193, Short.MAX_VALUE)
                    .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 194, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void tx_codigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_codigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_codigoActionPerformed

    private void bt_insetar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_insetar1ActionPerformed
        // TODO add your handling code here:
        insertarDatos();
        calcularSubtotal();
    }//GEN-LAST:event_bt_insetar1ActionPerformed

    private void tx_nombre1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_nombre1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_nombre1ActionPerformed

    private void tx_descripActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_descripActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_descripActionPerformed

    private void tx_cantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_cantActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_cantActionPerformed

    private void tb_productosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_productosMouseClicked
        // TODO add your handling code here:
        FilaSeleccionada();
    }//GEN-LAST:event_tb_productosMouseClicked

    private void tx_codigo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_codigo2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_codigo2ActionPerformed

    private void tx_nombre3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_nombre3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_nombre3ActionPerformed

    private void tx_descrip1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_descrip1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_descrip1ActionPerformed

    private void tx_cant1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_cant1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_cant1ActionPerformed

    private void bt_insetar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_insetar2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bt_insetar2ActionPerformed

    private void tb_productos1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_productos1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb_productos1MouseClicked

    private void tx_sbtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_sbtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_sbtActionPerformed

    private void bt_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_guardarActionPerformed
        // TODO add your handling code here:
        insetarclientes();
        guardafactura();
        guardardetallefactura();
    }//GEN-LAST:event_bt_guardarActionPerformed

    private void bt_quitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_quitarActionPerformed
        // TODO add your handling code here:
        quitarfila();
    }//GEN-LAST:event_bt_quitarActionPerformed

    private void txisvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txisvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txisvActionPerformed

    private void tx_dirreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_dirreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_dirreActionPerformed

    private void tx_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_emailActionPerformed

    private void tx_fechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_fechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_fechaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_cancelar;
    private javax.swing.JButton bt_guardar;
    private javax.swing.JButton bt_insetar;
    private javax.swing.JButton bt_insetar1;
    private javax.swing.JButton bt_insetar2;
    private javax.swing.JButton bt_nuevo;
    private javax.swing.JButton bt_quitar;
    private javax.swing.JButton bt_update;
    private javax.swing.JButton btsalir;
    private javax.swing.JComboBox<String> cb_vendedores;
    private javax.swing.JButton jButton4;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel l_pro;
    private javax.swing.JLabel l_pro1;
    private javax.swing.JTable tb_productos;
    private javax.swing.JTable tb_productos1;
    private javax.swing.JTable tb_ventas;
    private javax.swing.JTable tb_ventas1;
    private javax.swing.JTextField tx_cant;
    private javax.swing.JTextField tx_cant1;
    private javax.swing.JTextField tx_codigo;
    private javax.swing.JTextField tx_codigo1;
    private javax.swing.JTextField tx_codigo2;
    private javax.swing.JTextField tx_codigo3;
    private javax.swing.JTextField tx_descrip;
    private javax.swing.JTextField tx_descrip1;
    private javax.swing.JTextField tx_dirre;
    private javax.swing.JTextField tx_dirre1;
    private javax.swing.JTextField tx_email;
    private javax.swing.JTextField tx_email1;
    private javax.swing.JTextField tx_fecha;
    private javax.swing.JTextField tx_nombre;
    private javax.swing.JTextField tx_nombre1;
    private javax.swing.JTextField tx_nombre2;
    private javax.swing.JTextField tx_nombre3;
    private javax.swing.JTextField tx_precio;
    private javax.swing.JTextField tx_precio1;
    private javax.swing.JTextField tx_sbt;
    private javax.swing.JTextField tx_tele;
    private javax.swing.JTextField tx_tele1;
    private javax.swing.JTextField txisv;
    private javax.swing.JTextField txtotal;
    // End of variables declaration//GEN-END:variables
}
