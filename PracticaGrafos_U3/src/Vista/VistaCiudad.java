/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

import Controlador.Exceptions.VerticeOfSizeException;
import Controlador.Grafos.GrafoDirigidoEtiquetado;
import Controlador.Grafos.GrafoNoDirijidoEtiquetado;
import Controlador.Listas.ListaEnlazada;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SingleSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author johnny
 */
public class VistaCiudad extends javax.swing.JFrame {

    private GrafoDirigidoEtiquetado GrafoDi = new GrafoDirigidoEtiquetado(0, String.class);
    private ModeloTablaGrafo tablaModel = new ModeloTablaGrafo();
    private boolean vr = true;

    /**
     * Creates new form VistaCiudad
     */
    public VistaCiudad() {
        initComponents();
    }

    private void generarGrafo() throws Exception {
        if (!String.valueOf(this.nombreVertice.getText()).equalsIgnoreCase("")
                && -1 == GrafoDi.obtenerCodigo(String.valueOf(this.nombreVertice.getText()))) {
            GrafoDirigidoEtiquetado aux = GrafoDi;
            if (cbxTipoGrafo.getSelectedItem().toString().equals("Dirigido")) {
                if (!vr) {
                    aux = new GrafoDirigidoEtiquetado(0, String.class);
                    JOptionPane.showMessageDialog(null, "    Grafo borrado");
                }
                vr = true;
                this.GrafoDi = new GrafoDirigidoEtiquetado(aux.numVertices() + 1, String.class);
                for (int i = 1; i <= aux.numVertices(); i++) {
                    GrafoDi.etiquetarVertice(i, aux.obtenerEtiqueta(i));
                }
                for (int i = 1; i <= aux.numVertices(); i++) {
                    for (int j = 0; j < aux.adyacente(i).getSize(); j++) {
                        GrafoDi.insertarAristaE(aux.obtenerEtiqueta(i),
                                aux.obtenerEtiqueta(aux.adyacente(i).obtenerDato(j).getDestino()),
                                (Double) aux.existeAristaE(aux.obtenerEtiqueta(i),
                                        aux.obtenerEtiqueta(aux.adyacente(i).obtenerDato(j).getDestino()))[1]);
                    }
                }
                GrafoDi.etiquetarVertice(GrafoDi.numVertices(), String.valueOf(this.nombreVertice.getText()));

            } else {
                if (cbxTipoGrafo.getSelectedItem().toString().equals("NO Dirigido")) {
                    if (vr) {
                        aux = new GrafoDirigidoEtiquetado(0, String.class);
                        JOptionPane.showMessageDialog(null, "Grafo borrado");
                    }
                    vr = false;
                    this.GrafoDi = new GrafoNoDirijidoEtiquetado(aux.numVertices() + 1, String.class);
                    for (int i = 1; i <= aux.numVertices(); i++) {
                        GrafoDi.etiquetarVertice(i, aux.obtenerEtiqueta(i));

                    }
                    for (int i = 1; i <= aux.numVertices(); i++) {
                        for (int j = 0; j < aux.adyacente(i).getSize(); j++) {
                            GrafoDi.insertarAristaE(aux.obtenerEtiqueta(i),
                                    aux.obtenerEtiqueta(aux.adyacente(i).obtenerDato(j).getDestino()),
                                    (Double) aux.existeAristaE(aux.obtenerEtiqueta(i),
                                            aux.obtenerEtiqueta(aux.adyacente(i).obtenerDato(j).getDestino()))[1]);
                        }
                    }
                    GrafoDi.etiquetarVertice(GrafoDi.numVertices(), String.valueOf(this.nombreVertice.getText()));

                } else {
                    JOptionPane.showMessageDialog(null, "Seleccionar un tipo de grafo");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error con nombre");
        }
        cbxFin.removeAllItems();
        cbxOrigen1.removeAllItems();
        cbxDestino.removeAllItems();
        cbxinicio.removeAllItems();
        for (int i = 1; i <= GrafoDi.numVertices(); i++) {
            cbxFin.addItem(String.valueOf(GrafoDi.obtenerEtiqueta(i)));
            cbxOrigen1.addItem(String.valueOf(GrafoDi.obtenerEtiqueta(i)));
            cbxDestino.addItem(String.valueOf(GrafoDi.obtenerEtiqueta(i)));
            cbxinicio.addItem(String.valueOf(GrafoDi.obtenerEtiqueta(i)));
            cargarTabla();
            this.nombreVertice.setText(null);
        }
        System.out.println(GrafoDi.numAristas() + "  " + GrafoDi.numVertices());
    }

    public DefaultTableModel getTableListaDis(Boolean todosDatos) throws Exception {
        ListaEnlazada listaDis[] = GrafoDi.dijkStra(String.valueOf(cbxinicio.getSelectedItem().toString()), String.valueOf(cbxFin.getSelectedItem().toString()), todosDatos);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Vertice llegada");
        model.addColumn("Peso");
        model.addColumn("Recorrido");
        String datos[] = new String[3];
        for (int i = 0; i < listaDis.length; i++) {
            datos[0] = String.valueOf(listaDis[i].obtenerDato(0));
            datos[1] = String.valueOf(listaDis[i].obtenerDato(1));
            datos[2] = String.valueOf(listaDis[i].obtenerDato(2));
            model.addRow(datos);
        }
        return model;
    }

    private void cargarTabla() throws Exception {
        tablaModel.setGrafoED(GrafoDi);
        tblTabla.setModel((TableModel) (SingleSelectionModel) tablaModel);
        tablaModel.fireTableStructureChanged();
        tblTabla.updateUI();
    }

    private void adyacencias() throws Exception {
        String i = String.valueOf(cbxOrigen1.getSelectedItem().toString());
        String j = String.valueOf(cbxDestino.getSelectedItem().toString());

        if (i.equalsIgnoreCase(j)) {
            JOptionPane.showMessageDialog(null, "Vertices iguales");
        } else {
            try {
                GrafoDi.insertarAristaE(i, j, Double.parseDouble(this.peso.getText()));
                cargarTabla();
            } catch (VerticeOfSizeException ex) {
                System.out.println("ERROR EN INSERTAR ADYACENCIA: " + ex);
                JOptionPane.showMessageDialog(null, "No se puede insertar");
            }
        }
        this.peso.setText(null);
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
        tablaDis = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTabla = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        panelRound1 = new org.edisoncor.gui.panel.PanelRound();
        textBPA = new javax.swing.JLabel();
        cbxinicio = new org.edisoncor.gui.comboBox.ComboBoxRect();
        cbxFin = new org.edisoncor.gui.comboBox.ComboBoxRect();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnOk2 = new org.edisoncor.gui.button.ButtonCircle();
        btnOk = new org.edisoncor.gui.button.ButtonCircle();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton1 = new org.edisoncor.gui.button.ButtonAeroRound();
        panelNice1 = new org.edisoncor.gui.panel.PanelNice();
        nombreVertice = new org.edisoncor.gui.textField.TextFieldRectBackground();
        cbxTipoGrafo = new org.edisoncor.gui.comboBox.ComboBoxRectIcon();
        jLabel2 = new javax.swing.JLabel();
        btnModificar = new org.edisoncor.gui.button.ButtonIcon();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnOk1 = new org.edisoncor.gui.button.ButtonIcon();
        panel1 = new org.edisoncor.gui.panel.Panel();
        jLabel5 = new javax.swing.JLabel();
        btnMostrar = new org.edisoncor.gui.button.ButtonIcon();
        jLabel7 = new javax.swing.JLabel();
        peso = new javax.swing.JTextField();
        cbxDestino = new javax.swing.JComboBox<>();
        cbxOrigen1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tablaDis.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tablaDis);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 540, 760, 180));

        tblTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblTabla);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, 780, 200));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 3, 48)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 0, 204));
        jLabel6.setText("Grafos de ciudades");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 390, 40));

        panelRound1.setColorPrimario(new java.awt.Color(204, 204, 255));
        panelRound1.setColorSecundario(new java.awt.Color(51, 204, 255));
        panelRound1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        textBPA.setFont(new java.awt.Font("Yu Gothic UI Light", 3, 14)); // NOI18N
        textBPA.setForeground(new java.awt.Color(0, 0, 0));
        panelRound1.add(textBPA, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 40, 170, 20));

        cbxinicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxinicioActionPerformed(evt);
            }
        });
        panelRound1.add(cbxinicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, -1, -1));
        panelRound1.add(cbxFin, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, -1, -1));

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI Light", 3, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Ciudad Final");
        panelRound1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 110, -1));

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI Light", 3, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Ciudad Inicial");
        panelRound1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 110, -1));

        btnOk2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/imagenes/subir.png"))); // NOI18N
        btnOk2.setText("buttonCircle1");
        btnOk2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOk2ActionPerformed(evt);
            }
        });
        panelRound1.add(btnOk2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 40, 40));

        btnOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/imagenes/rec.png"))); // NOI18N
        btnOk.setText("buttonCircle2");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });
        panelRound1.add(btnOk, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 40, 40));

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI Light", 3, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("DijkStrac");
        panelRound1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI Light", 3, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("BBP ");
        panelRound1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, -1, -1));

        jButton1.setText("Obtener");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        panelRound1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 60, -1, -1));

        getContentPane().add(panelRound1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, 820, 100));

        panelNice1.setBackground(new java.awt.Color(204, 204, 255));
        panelNice1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelNice1.add(nombreVertice, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 220, -1));

        cbxTipoGrafo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione el estilo del Grafo", "Dirigido", "No Dirigido" }));
        panelNice1.add(cbxTipoGrafo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 230, 20));

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Light", 3, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Ciudad B");
        panelNice1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 80, 70, -1));

        btnModificar.setBackground(new java.awt.Color(204, 255, 255));
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/imagenes/salvar.png"))); // NOI18N
        btnModificar.setText("buttonIcon1");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        panelNice1.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, 50, 40));

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Light", 3, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Nombre de Ciudad");
        panelNice1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 170, -1));

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Light", 3, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Estilo De grafo");
        panelNice1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 110, 20));

        btnOk1.setBackground(new java.awt.Color(204, 204, 255));
        btnOk1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/imagenes/subir.png"))); // NOI18N
        btnOk1.setText("butUnir");
        btnOk1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOk1ActionPerformed(evt);
            }
        });
        panelNice1.add(btnOk1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 80, 40, 30));

        panel1.setColorPrimario(new java.awt.Color(204, 204, 255));
        panel1.setColorSecundario(new java.awt.Color(102, 0, 153));
        panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI Light", 3, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Mostrar Grafo");
        panel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 100, -1));

        btnMostrar.setBackground(new java.awt.Color(204, 153, 255));
        btnMostrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/imagenes/ojo.png"))); // NOI18N
        btnMostrar.setText("buttonIcon2");
        btnMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarActionPerformed(evt);
            }
        });
        panel1.add(btnMostrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 50, 40));

        panelNice1.add(panel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 10, 160, 90));

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI Light", 3, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Ciudad A");
        panelNice1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, 70, -1));
        panelNice1.add(peso, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 140, 110, 20));

        cbxDestino.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", " ", " ", " ", " ", " ", " " }));
        panelNice1.add(cbxDestino, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 110, 150, -1));

        cbxOrigen1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", " ", " ", " ", " ", " ", " ", " " }));
        panelNice1.add(cbxOrigen1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 40, 150, -1));

        getContentPane().add(panelNice1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 790, 170));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Vista/imagenes/FondoCity.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 850, 740));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        try {
            this.generarGrafo();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error en grafos");
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarActionPerformed
        FrmGrafoVista frm = new FrmGrafoVista(GrafoDi);
        frm.setSize(400, 400);
        frm.setVisible(true);
    }//GEN-LAST:event_btnMostrarActionPerformed

    private void cbxinicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxinicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxinicioActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            String c = "", d = "";

            Integer[] a = GrafoDi.toArrayDFS();

            System.out.println("sdsadsa");
            System.out.println(Arrays.toString(a));

            for (int i = 0; i < a.length - 1; i++) {
                c = c + " " + GrafoDi.obtenerEtiqueta(a[i]);

            }
            this.textBPA.setText(c);
        } catch (Exception ex) {
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnOk1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOk1ActionPerformed
        try {
            adyacencias();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error datos incorrectos", "ERROR", JOptionPane.ERROR_MESSAGE);
            this.peso.setText(null);
        }

    }//GEN-LAST:event_btnOk1ActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed

        try {
            this.tablaDis.setModel((TableModel) (SingleSelectionModel) this.getTableListaDis(true));
        } catch (Exception ex) {
            Logger.getLogger(VistaCiudad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnOk2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOk2ActionPerformed

        try {
            // TODO add your handling code here:
            this.tablaDis.setModel((TableModel) (SingleSelectionModel) this.getTableListaDis(false));
        } catch (Exception ex) {

        }
    }//GEN-LAST:event_btnOk2ActionPerformed

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
            java.util.logging.Logger.getLogger(VistaCiudad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaCiudad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaCiudad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaCiudad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaCiudad().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonIcon btnModificar;
    private org.edisoncor.gui.button.ButtonIcon btnMostrar;
    private org.edisoncor.gui.button.ButtonCircle btnOk;
    private org.edisoncor.gui.button.ButtonIcon btnOk1;
    private org.edisoncor.gui.button.ButtonCircle btnOk2;
    private javax.swing.JComboBox<String> cbxDestino;
    private org.edisoncor.gui.comboBox.ComboBoxRect cbxFin;
    private javax.swing.JComboBox<String> cbxOrigen1;
    private org.edisoncor.gui.comboBox.ComboBoxRectIcon cbxTipoGrafo;
    private org.edisoncor.gui.comboBox.ComboBoxRect cbxinicio;
    private org.edisoncor.gui.button.ButtonAeroRound jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private org.edisoncor.gui.textField.TextFieldRectBackground nombreVertice;
    private org.edisoncor.gui.panel.Panel panel1;
    private org.edisoncor.gui.panel.PanelNice panelNice1;
    private org.edisoncor.gui.panel.PanelRound panelRound1;
    private javax.swing.JTextField peso;
    private javax.swing.JTable tablaDis;
    private javax.swing.JTable tblTabla;
    private javax.swing.JLabel textBPA;
    // End of variables declaration//GEN-END:variables
}
