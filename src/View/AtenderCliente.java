/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Model.Cliente;
import Model.ListaDobleClientes;
import Model.NodoCliente;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Franco Coward
 */
public class AtenderCliente extends javax.swing.JFrame {

    private ListaDobleClientes listaPreferencial;
    private ListaDobleClientes listaRapida;
    private ListaDobleClientes listaGeneral;


    public AtenderCliente(ListaDobleClientes listaPreferencial, ListaDobleClientes listaRapida, ListaDobleClientes listaGeneral) {

        initComponents();
        this.setLocationRelativeTo(null);
        this.listaPreferencial = listaPreferencial;
        this.listaRapida = listaRapida;
        this.listaGeneral = listaGeneral;
        String[] configuracion = leerConfiguracion();
        lblNombreDeBanco.setText(configuracion[0]);
        cargarClientesEnTabla(listaPreferencial);
        cargarClientesEnTabla(listaRapida);
        cargarClientesEnTabla(listaGeneral);
        if (listaPreferencial.getCabeza() != null) {
            mostrarClienteEnAtencion(listaPreferencial.getCabeza().getCliente(), "preferencial");
        }
    }

  


    private void actualizarFormularioPreferencial() {
        NodoCliente nodoPreferencial = listaPreferencial.getCabeza();
        if (nodoPreferencial != null) {
            mostrarClienteEnAtencion(nodoPreferencial.getCliente(), "preferencial");
        } else {
            limpiarCampos();
        }
    }

    private void mostrarClienteEnAtencion(Cliente cliente, String tipoCaja) {
        txtNombreCliente.setText("Nombre: " + cliente.getNombre());
        txtCedulaCliente.setText("Cédula: " + cliente.getId());
        txtTramiteCliente.setText("Trámite: " + cliente.getTramite());
        txtTiqueteCliente.setText("Tiquete: #" + cliente.getTiquete());
    }

    private void limpiarCampos() {
        txtNombreCliente.setText("");
        txtCedulaCliente.setText("");
        txtTramiteCliente.setText("");
        txtTiqueteCliente.setText("");
    }

    public String[] leerConfiguracion() {
        String[] configuracion = new String[2];
        try (BufferedReader reader = new BufferedReader(new FileReader("prod.txt"))) {
            configuracion[0] = reader.readLine();
            configuracion[1] = reader.readLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al leer la configuración del archivo", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return configuracion;
    }

    private void cargarClientesEnTabla(ListaDobleClientes listaClientes) {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaEspera.getModel();
        modeloTabla.setRowCount(0);

        NodoCliente actual = listaClientes.getCabeza();
        while (actual != null) {
            Cliente cliente = actual.getCliente();
            modeloTabla.addRow(new Object[]{
                cliente.getTiquete(),
                cliente.getNombre(),
                cliente.getId(),
                cliente.getTramite(),
                cliente.getTipo()
            });
            actual = actual.getSiguiente();
        }
    }

    private void atenderClientePreferencial() {
        Cliente cliente = listaPreferencial.atenderCliente(); 
        if (cliente != null) {
            mostrarClienteEnAtencion(cliente, "preferencial");
            guardarReporteCliente(cliente);
            cargarClientesEnTabla(listaPreferencial); 
            JOptionPane.showMessageDialog(this, "Cliente preferencial atendido con éxito: " + cliente.getNombre());
        } else {
            JOptionPane.showMessageDialog(this, "No hay clientes en la fila preferencial.");
        }
    }

    private void guardarReporteCliente(Cliente cliente) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("reportes.txt", true))) {
            writer.write(cliente.toString());
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el reporte del cliente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarClientesDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("prod.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 6) {
                    String nombre = datos[0];
                    int id = Integer.parseInt(datos[1]);
                    int edad = Integer.parseInt(datos[2]);
                    String tramite = datos[3];
                    String tipo = datos[4];
                    int tiquete = Integer.parseInt(datos[5]);

                    Cliente cliente = new Cliente(nombre, id, edad, tramite, tipo, tiquete);

                    if (tipo.equals("Preferencial")) {
                        listaPreferencial.agregarCliente(cliente);
                    } else if (tipo.equals("Rápido")) {
                        listaRapida.agregarCliente(cliente);
                    } else {
                        listaGeneral.agregarCliente(cliente);
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los clientes desde el archivo", "Error", JOptionPane.ERROR_MESSAGE);
        }
    
    }

    private String tipoFilaSeleccionada = "";

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNombreDeBanco1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblNombreDeBanco = new javax.swing.JLabel();
        btnIngresarCliente = new javax.swing.JButton();
        btnAtenderCliente = new javax.swing.JButton();
        btnRoportes = new javax.swing.JButton();
        btnConfiguracionSistema = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaEspera = new javax.swing.JTable();
        lblSubTitulo = new javax.swing.JLabel();
        btnAtenderPreferencial = new javax.swing.JButton();
        lblSubTitulo1 = new javax.swing.JLabel();
        btnAtenderNormal = new javax.swing.JButton();
        btnAtenderRapido = new javax.swing.JButton();
        btnAtender = new javax.swing.JButton();
        txtCedulaCliente = new javax.swing.JTextField();
        txtNombreCliente = new javax.swing.JTextField();
        txtTramiteCliente = new javax.swing.JTextField();
        txtTiqueteCliente = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();

        lblNombreDeBanco1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(46, 156, 94));

        lblNombreDeBanco.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        btnIngresarCliente.setBackground(new java.awt.Color(46, 156, 94));
        btnIngresarCliente.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnIngresarCliente.setForeground(new java.awt.Color(255, 255, 255));
        btnIngresarCliente.setText("Ingresar Cliente");
        btnIngresarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarClienteActionPerformed(evt);
            }
        });

        btnAtenderCliente.setBackground(new java.awt.Color(46, 156, 94));
        btnAtenderCliente.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAtenderCliente.setForeground(new java.awt.Color(255, 255, 255));
        btnAtenderCliente.setText("Atender Cliente");

        btnRoportes.setBackground(new java.awt.Color(46, 156, 94));
        btnRoportes.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRoportes.setForeground(new java.awt.Color(255, 255, 255));
        btnRoportes.setText("Reportes");
        btnRoportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRoportesActionPerformed(evt);
            }
        });

        btnConfiguracionSistema.setBackground(new java.awt.Color(46, 156, 94));
        btnConfiguracionSistema.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnConfiguracionSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnConfiguracionSistema.setText("Configuracion");
        btnConfiguracionSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfiguracionSistemaActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Logo Cajas para bancos.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addComponent(lblNombreDeBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel1)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnConfiguracionSistema, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRoportes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAtenderCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnIngresarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(lblNombreDeBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79)
                .addComponent(btnIngresarCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAtenderCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRoportes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnConfiguracionSistema)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        lblTitulo.setBackground(new java.awt.Color(255, 255, 255));
        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setText("Atencion al cliente");

        tablaEspera.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Tiquete", "Nombre", "Cedula", "Tramite", "Tipo de Cliente"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaEspera);

        lblSubTitulo.setText("Cliente en Atencion");

        btnAtenderPreferencial.setText("Preferencial");
        btnAtenderPreferencial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtenderPreferencialActionPerformed(evt);
            }
        });

        lblSubTitulo1.setText("Seleccione la fila que desea atender");

        btnAtenderNormal.setText("Normal");
        btnAtenderNormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtenderNormalActionPerformed(evt);
            }
        });

        btnAtenderRapido.setText("Rapido");
        btnAtenderRapido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtenderRapidoActionPerformed(evt);
            }
        });

        btnAtender.setText("Atender");
        btnAtender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtenderActionPerformed(evt);
            }
        });

        txtCedulaCliente.setEnabled(false);

        txtNombreCliente.setEnabled(false);

        txtTramiteCliente.setEnabled(false);

        txtTiqueteCliente.setEnabled(false);

        btnCancelar.setBackground(new java.awt.Color(102, 102, 102));
        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(null);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtTramiteCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtCedulaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtTiqueteCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnAtender)
                                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 241, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btnAtenderRapido)
                                        .addComponent(btnAtenderPreferencial)
                                        .addComponent(btnAtenderNormal))
                                    .addGap(156, 156, 156))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(lblSubTitulo)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblSubTitulo1)
                                    .addGap(105, 105, 105)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(94, 94, 94))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(266, 266, 266)
                        .addComponent(lblTitulo)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(lblTitulo)
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSubTitulo)
                    .addComponent(lblSubTitulo1))
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAtenderPreferencial)
                    .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCedulaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAtenderNormal))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTramiteCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAtenderRapido))
                .addGap(18, 18, 18)
                .addComponent(txtTiqueteCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btnAtender)
                .addGap(26, 26, 26)
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAtenderPreferencialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtenderPreferencialActionPerformed
        // TODO add your handling code here:
        Cliente cliente = listaPreferencial.atenderCliente();
        if (cliente != null) {
            mostrarClienteEnAtencion(cliente, "preferencial");
            JOptionPane.showMessageDialog(this, "Cliente preferencial atendido con éxito: " + cliente.getNombre());
            actualizarFormularioPreferencial(); // Actualiza el formulario con el siguiente cliente
        } else {
            JOptionPane.showMessageDialog(this, "No hay clientes en la fila preferencial.");
        }
    }//GEN-LAST:event_btnAtenderPreferencialActionPerformed

    private void btnAtenderNormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtenderNormalActionPerformed
        // TODO add your handling code here:
        tipoFilaSeleccionada = "normal";
        NodoCliente nodo = listaGeneral.getCabeza();
        if (nodo != null) {
            Cliente cliente = nodo.getCliente();
            mostrarClienteEnAtencion(cliente, tipoFilaSeleccionada);
        } else {
            JOptionPane.showMessageDialog(this, "No hay clientes en la fila normal");
        }
    }//GEN-LAST:event_btnAtenderNormalActionPerformed

    private void btnAtenderRapidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtenderRapidoActionPerformed
        tipoFilaSeleccionada = "rapido";
        NodoCliente nodo = listaRapida.getCabeza();
        if (nodo != null) {
            Cliente cliente = nodo.getCliente();
            mostrarClienteEnAtencion(cliente, tipoFilaSeleccionada);
        } else {
            JOptionPane.showMessageDialog(this, "No hay clientes en la fila rápida");
        }
    }//GEN-LAST:event_btnAtenderRapidoActionPerformed

    private void btnAtenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtenderActionPerformed
        Cliente cliente = null;

        if (tipoFilaSeleccionada.equals("preferencial")) {
            cliente = listaPreferencial.atenderCliente();
            cargarClientesEnTabla(listaPreferencial);
            actualizarFormularioPreferencial(); // Actualiza el formulario con el siguiente cliente
        } else if (tipoFilaSeleccionada.equals("normal")) {
            cliente = listaGeneral.atenderCliente();
            cargarClientesEnTabla(listaGeneral);
        } else if (tipoFilaSeleccionada.equals("rapido")) {
            cliente = listaRapida.atenderCliente();
            cargarClientesEnTabla(listaRapida);
        }

        if (cliente != null) {
            JOptionPane.showMessageDialog(this, "Cliente atendido con éxito: " + cliente.getNombre());
            limpiarCampos();

            // Remove the client from the prod.txt file
            try (BufferedReader reader = new BufferedReader(new FileReader("prod.txt")); BufferedWriter writer = new BufferedWriter(new FileWriter("prod.txt"))) {

                String line;
                boolean clienteRemoved = false;
                while ((line = reader.readLine()) != null) {
                    if (!clienteRemoved && line.contains(String.valueOf(cliente.getTiquete()))) {
                        clienteRemoved = true;
                    } else {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar el archivo prod.txt", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No hay clientes en la fila " + tipoFilaSeleccionada);
        }
    }//GEN-LAST:event_btnAtenderActionPerformed

    private void btnIngresarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarClienteActionPerformed
        // TODO add your handling code here:
        new IngresarCliente().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnIngresarClienteActionPerformed

    private void btnConfiguracionSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfiguracionSistemaActionPerformed
        // TODO add your handling code here:
        new SolicitudConfiguracion().setVisible(true);
        this.dispose();

    }//GEN-LAST:event_btnConfiguracionSistemaActionPerformed

    private void btnRoportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRoportesActionPerformed
        // TODO add your handling code here:
        new Reportes(listaPreferencial, listaRapida, listaGeneral).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRoportesActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        MenuPrincipal menuPrincipal = new MenuPrincipal();
        menuPrincipal.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(AtenderCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AtenderCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AtenderCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AtenderCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        ListaDobleClientes listaPreferencial = new ListaDobleClientes();
        ListaDobleClientes listaRapida = new ListaDobleClientes();
        ListaDobleClientes listaGeneral = new ListaDobleClientes();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AtenderCliente(listaPreferencial, listaRapida, listaGeneral).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtender;
    private javax.swing.JButton btnAtenderCliente;
    private javax.swing.JButton btnAtenderNormal;
    private javax.swing.JButton btnAtenderPreferencial;
    private javax.swing.JButton btnAtenderRapido;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfiguracionSistema;
    private javax.swing.JButton btnIngresarCliente;
    private javax.swing.JButton btnRoportes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblNombreDeBanco;
    private javax.swing.JLabel lblNombreDeBanco1;
    private javax.swing.JLabel lblSubTitulo;
    private javax.swing.JLabel lblSubTitulo1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tablaEspera;
    private javax.swing.JTextField txtCedulaCliente;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtTiqueteCliente;
    private javax.swing.JTextField txtTramiteCliente;
    // End of variables declaration//GEN-END:variables
}
