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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pc
 */
public class Factura_Compras extends javax.swing.JInternalFrame {

    Connection con, connection;
    Statement stmt, st;
    /**
     * Creates new form Factura_Compras
     */
    public Factura_Compras() {
        initComponents();
        campos();
        llenarTabla();
    }

       public void campos() {
        tx_codigo.setText("0");
        tx_nombre.setText("-");
        tx_dirre.setText("-");
        tx_tele.setText("-");
        tx_email.setText("-");
    }

    public void FilaSeleccionada() {
        int indiceFilaSeleccionada = tb_proveedores.getSelectedRow();

        if (indiceFilaSeleccionada >= 0) {
            DefaultTableModel modeloTabla = (DefaultTableModel) tb_proveedores.getModel();

            // Obtener los valores de las celdas seleccionadas
            String codigo = modeloTabla.getValueAt(indiceFilaSeleccionada, 0).toString();
            String nombre = modeloTabla.getValueAt(indiceFilaSeleccionada, 1).toString();
            String descripcion = modeloTabla.getValueAt(indiceFilaSeleccionada, 2).toString();
            String precio = modeloTabla.getValueAt(indiceFilaSeleccionada, 3).toString();
            String cantidad = modeloTabla.getValueAt(indiceFilaSeleccionada, 4).toString();

            // Mostrar los valores en los campos de texto correspondientes
            tx_codigo.setText(codigo);
            tx_nombre.setText(nombre);
            tx_dirre.setText(descripcion);
            tx_tele.setText(precio);
            tx_email.setText(cantidad);
        }
    }

    public void quitarfila() {
        int indice = tb_compras.getSelectedRow();
        if (indice >= 0) {
            DefaultTableModel mymodelo = (DefaultTableModel) tb_compras.getModel();
            mymodelo.removeRow(indice);
        }
    }

    private void calcularSubtotal() {
        DefaultTableModel mymodelo = (DefaultTableModel) tb_compras.getModel();
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
        
    }

    public void llenarCombox() {
        
    }

    public void guardafactura() {
       
    }

    public void guardardetallefactura() {
        
    }

   public void llenarTabla() {
    try {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/sistemafactura", "root", "");
        Statement stmt = con.createStatement();

        // Ejecutar la consulta y obtener los resultados en un objeto ResultSet
        String query = "SELECT * FROM proveedores";
        ResultSet resultSet = stmt.executeQuery(query);

        // Obtener el modelo de tabla
        DefaultTableModel model = (DefaultTableModel) tb_proveedores.getModel();

        // Limpiar la tabla antes de llenarla con nuevos datos
        model.setRowCount(0);

        // Iterar sobre los resultados del ResultSet y agregar cada fila al modelo de la tabla
        while (resultSet.next()) {
            Object[] row = new Object[5];
            row[0] = resultSet.getInt("id_proveedor");
            row[1] = resultSet.getString("nombre");
            row[2] = resultSet.getString("direccion");
            row[3] = resultSet.getString("telefono");
            row[4] = resultSet.getString("email");
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tb_compras = new javax.swing.JTable();
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
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_proveedores = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tx_codigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tx_nombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tx_dirre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tx_tele = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tx_email = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
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

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Factura Compras");

        tb_compras.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tb_compras);

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

        tb_proveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre", "Dirrecion", "Telefeno", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_proveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_proveedoresMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tb_proveedores);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), " Proveedor"));

        jLabel1.setText("Codigo:");

        jLabel2.setText("Nombre:");

        jLabel3.setText("Dirreccion:");

        jLabel4.setText("Telefono:");

        jLabel5.setText("Email:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(4, 4, 4)
                        .addComponent(tx_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tx_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tx_dirre))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(4, 4, 4)
                        .addComponent(tx_tele, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tx_email, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                    .addComponent(tx_dirre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tx_tele, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(tx_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Seleccionar Productos"));

        jLabel6.setText("Codigo:");

        jLabel7.setText("Nombre:");

        tx_nombre1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_nombre1ActionPerformed(evt);
            }
        });

        jLabel8.setText("Descripticion:");

        tx_descrip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_descripActionPerformed(evt);
            }
        });

        jLabel9.setText("Precio:");

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
                        .addComponent(jLabel6)
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
                                .addComponent(jLabel6))
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tx_sbtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_sbtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_sbtActionPerformed

    private void txisvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txisvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txisvActionPerformed

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

    private void tb_proveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_proveedoresMouseClicked
        // TODO add your handling code here:
        FilaSeleccionada();
    }//GEN-LAST:event_tb_proveedoresMouseClicked

    private void tx_nombre1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_nombre1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_nombre1ActionPerformed

    private void tx_descripActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_descripActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_descripActionPerformed

    private void tx_cantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_cantActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_cantActionPerformed

    private void bt_insetar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_insetar1ActionPerformed
        // TODO add your handling code here:
        insertarDatos();
        calcularSubtotal();
    }//GEN-LAST:event_bt_insetar1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_cancelar;
    private javax.swing.JButton bt_guardar;
    private javax.swing.JButton bt_insetar1;
    private javax.swing.JButton bt_nuevo;
    private javax.swing.JButton bt_quitar;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel l_pro;
    private javax.swing.JTable tb_compras;
    private javax.swing.JTable tb_proveedores;
    private javax.swing.JTextField tx_cant;
    private javax.swing.JTextField tx_codigo;
    private javax.swing.JTextField tx_codigo1;
    private javax.swing.JTextField tx_descrip;
    private javax.swing.JTextField tx_dirre;
    private javax.swing.JTextField tx_email;
    private javax.swing.JTextField tx_nombre;
    private javax.swing.JTextField tx_nombre1;
    private javax.swing.JTextField tx_precio;
    private javax.swing.JTextField tx_sbt;
    private javax.swing.JTextField tx_tele;
    private javax.swing.JTextField txisv;
    private javax.swing.JTextField txtotal;
    // End of variables declaration//GEN-END:variables
}
