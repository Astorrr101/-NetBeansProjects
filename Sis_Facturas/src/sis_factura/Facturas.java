package sis_factura;

import java.awt.Color;
import net.sf.jasperreports.engine.JRDataSource;


public class Facturas extends javax.swing.JFrame {

    public Facturas() {
        initComponents();
        close();
    }

    public void llamar() {
        Productos llamar = new Productos();
        view.add(llamar);
        llamar.setVisible(true);
    }

    public void close() {
        Color nuevoColor = Color.RED;
        mi_alma.enable(false);
        mi_fac.enable(false);
        mi_deta.enable(false);
        mi_reg.enable(false);
        mi_alma.setForeground(nuevoColor);
        mi_fac.setForeground(nuevoColor);
        mi_deta.setForeground(nuevoColor);
        mi_reg.setForeground(nuevoColor);
    }

    public void Administrador() {
        Color nuevoColor = new Color(0, 255, 0);
        mi_alma.enable(true);
        mi_alma.setForeground(nuevoColor);
        mi_fac.enable(true);
        mi_fac.setForeground(nuevoColor);
        mi_deta.enable(true);
        mi_deta.setForeground(nuevoColor);
        mi_reg.enable(true);
        mi_reg.setForeground(nuevoColor);
    }

    public void usuario() {
        Color nuevoColor = new Color(0, 255, 0);
        mi_fac.enable(true);
        mi_fac.setForeground(nuevoColor);
        mi_compras.show(false);

    }

    public void encargado() {
        Color nuevoColor = new Color(0, 255, 0);
        mi_alma.enable(true);
        mi_alma.setForeground(nuevoColor);
        mi_deta.enable(true);
        mi_deta.setForeground(nuevoColor);
        mi_dv.show(false);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        view = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mi_alma = new javax.swing.JMenu();
        mi_productos = new javax.swing.JMenuItem();
        mi_provee = new javax.swing.JMenuItem();
        mi_fac = new javax.swing.JMenu();
        mi_compras = new javax.swing.JMenuItem();
        mi_ventas = new javax.swing.JMenuItem();
        mi_deta = new javax.swing.JMenu();
        mi_dv = new javax.swing.JMenuItem();
        mi_dc = new javax.swing.JMenuItem();
        mi_reg = new javax.swing.JMenu();
        mi_clientes = new javax.swing.JMenuItem();
        mi_vendedores = new javax.swing.JMenuItem();
        mi_prove = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        salir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SISTEMA FACTURA");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton1.setText("Administrador");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Usuario final");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Encargado");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap(100, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addContainerGap(404, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout viewLayout = new javax.swing.GroupLayout(view);
        view.setLayout(viewLayout);
        viewLayout.setHorizontalGroup(
            viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 960, Short.MAX_VALUE)
        );
        viewLayout.setVerticalGroup(
            viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jMenuBar1.setBackground(new java.awt.Color(153, 153, 153));

        jMenu1.setText("Inicio");
        jMenu1.setFont(new java.awt.Font("Sylfaen", 0, 22)); // NOI18N
        jMenuBar1.add(jMenu1);

        mi_alma.setBackground(new java.awt.Color(204, 0, 0));
        mi_alma.setBorder(null);
        mi_alma.setText("Almacenes");
        mi_alma.setFont(new java.awt.Font("Sylfaen", 0, 22)); // NOI18N

        mi_productos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        mi_productos.setText("Productos");
        mi_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_productosActionPerformed(evt);
            }
        });
        mi_alma.add(mi_productos);

        mi_provee.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        mi_provee.setText("Proveedores");
        mi_alma.add(mi_provee);

        jMenuBar1.add(mi_alma);

        mi_fac.setText("Facturacion");
        mi_fac.setFont(new java.awt.Font("Sylfaen", 0, 22)); // NOI18N

        mi_compras.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        mi_compras.setText("Compras");
        mi_compras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_comprasActionPerformed(evt);
            }
        });
        mi_fac.add(mi_compras);

        mi_ventas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        mi_ventas.setText("Ventas");
        mi_ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_ventasActionPerformed(evt);
            }
        });
        mi_fac.add(mi_ventas);

        jMenuBar1.add(mi_fac);

        mi_deta.setText("Detalles");
        mi_deta.setFont(new java.awt.Font("Sylfaen", 0, 22)); // NOI18N

        mi_dv.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        mi_dv.setText("Detalles Ventas");
        mi_deta.add(mi_dv);

        mi_dc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        mi_dc.setText("Detalles Compras");
        mi_deta.add(mi_dc);

        jMenuBar1.add(mi_deta);

        mi_reg.setText("Registros");
        mi_reg.setFont(new java.awt.Font("Sylfaen", 0, 22)); // NOI18N

        mi_clientes.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        mi_clientes.setText("Clientes");
        mi_reg.add(mi_clientes);

        mi_vendedores.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        mi_vendedores.setText("Vendedores");
        mi_reg.add(mi_vendedores);

        mi_prove.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        mi_prove.setText("Proveedores");
        mi_reg.add(mi_prove);

        jMenuBar1.add(mi_reg);

        jMenu2.setText("Exit");
        jMenu2.setFont(new java.awt.Font("Sylfaen", 0, 22)); // NOI18N

        salir.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        salir.setText("Salir");
        jMenu2.add(salir);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(view))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(view, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void mi_ventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_ventasActionPerformed
        // TODO add your handling code here:
        Factura_Ventas llamar = new Factura_Ventas();
        view.add(llamar);
        llamar.setVisible(true);
    }//GEN-LAST:event_mi_ventasActionPerformed

    private void mi_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_productosActionPerformed
        // TODO add your handling code here:
        llamar();
    }//GEN-LAST:event_mi_productosActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        close();
        Administrador();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        close();
        usuario();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        close();
        encargado();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void mi_comprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_comprasActionPerformed
        // TODO add your handling code here:
        Factura_Compras llamar = new Factura_Compras();
        view.add(llamar);
        llamar.setVisible(true);
    }//GEN-LAST:event_mi_comprasActionPerformed

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
            java.util.logging.Logger.getLogger(Facturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Facturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Facturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Facturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Facturas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenu mi_alma;
    private javax.swing.JMenuItem mi_clientes;
    private javax.swing.JMenuItem mi_compras;
    private javax.swing.JMenuItem mi_dc;
    private javax.swing.JMenu mi_deta;
    private javax.swing.JMenuItem mi_dv;
    private javax.swing.JMenu mi_fac;
    private javax.swing.JMenuItem mi_productos;
    private javax.swing.JMenuItem mi_prove;
    private javax.swing.JMenuItem mi_provee;
    private javax.swing.JMenu mi_reg;
    private javax.swing.JMenuItem mi_vendedores;
    private javax.swing.JMenuItem mi_ventas;
    private javax.swing.JMenuItem salir;
    private javax.swing.JDesktopPane view;
    // End of variables declaration//GEN-END:variables
}
