/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Model.Cliente;
import Model.ListaDobleClientes;
import Model.NodoCliente;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;

/**
 *
 * @author Franco Coward
 */
public class Reportes extends javax.swing.JFrame {

    private ListaDobleClientes listaPreferencial;
    private ListaDobleClientes listaRapida;
    private ListaDobleClientes listaGeneral;

    /**
     * Creates new form Reportes
     */
    public Reportes(ListaDobleClientes listaPreferencial, ListaDobleClientes listaRapida, ListaDobleClientes listaGeneral) {
        initComponents();
        String[] configuracion = leerConfiguracion();
        lblNombreDeBanco.setText(configuracion[0]);
        this.setLocationRelativeTo(null);
        this.listaPreferencial = listaPreferencial;
        this.listaRapida = listaRapida;
        this.listaGeneral = listaGeneral;
        generarReportes();
    }

    private void generarReportes() {
        int totalClientesPreferencial = 0;
        int totalClientesRapida = 0;
        int totalClientesGeneral = 0;

        long totalTiempoPreferencial = 0;
        long totalTiempoRapida = 0;
        long totalTiempoGeneral = 0;

        int totalClientesAtendidos = 0;
        long totalTiempoAtencion = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("reportes.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                //Parsar la información del cliente
                if (linea.startsWith("Cliente")) {
                    String[] partes = linea.split(",");
                    //tipo de cliente
                    String tipo = partes[4].split("=")[1].trim().toLowerCase();
                    //parseamos las horas
                    String horaCreacionStr = partes[5].split("=")[1].trim();
                    String horaAtencionStr = partes[6].split("=")[1].replace('}', ' ').trim();

                    LocalDateTime horaCreacion = LocalDateTime.parse(horaCreacionStr);
                    LocalDateTime horaAtencion = LocalDateTime.parse(horaAtencionStr);

                    long tiempoAtencion = java.time.Duration.between(horaCreacion, horaAtencion).getSeconds();
                    totalClientesAtendidos++;
                    totalTiempoAtencion += tiempoAtencion;

                    // Asignar según el tipo de cliente
                    switch (tipo) {
                        case "preferencial":
                            totalClientesPreferencial++;
                            totalTiempoPreferencial += tiempoAtencion;
                            break;
                        case "rápido":
                        case "rapido":
                            totalClientesRapida++;
                            totalTiempoRapida += tiempoAtencion;
                            break;
                        case "normal":
                            totalClientesGeneral++;
                            totalTiempoGeneral += tiempoAtencion;
                            break;
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo reportes.txt", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Reporte 1: Caja que atendió la mayor cantidad de clientes
       // int totalPreferencial = listaPreferencial.getSize();
        //int totalRapida = listaRapida.getSize();
        //int totalGeneral = listaGeneral.getSize();

        //Determinar la caja que atendió mayor cantidad de clientes
        String cajaMayor = "Preferencial";
        int maxClientes = totalClientesPreferencial;//totalPreferencial;

        if (totalClientesRapida > maxClientes) {//totalRapida > maxClientes) {
            cajaMayor = "Rapido";
            maxClientes = totalClientesRapida; //totalRapida;
        }

        if (totalClientesGeneral > maxClientes){//totalGeneral > maxClientes) {
            cajaMayor = "General";
            maxClientes = totalClientesGeneral;//totalGeneral;
        }

        txtReporte1.setText(cajaMayor + " (" + maxClientes + " clientes)");

        // Reporte 2: Total de clientes atendidos
       // int totalClientesAtendidos = totalPreferencial + totalRapida + totalGeneral;
        //txtReporte2.setText(String.valueOf(totalClientesAtendidos));
        txtReporte2.setText(String.valueOf(totalClientesAtendidos));

        // Reporte 3: Caja con el mejor tiempo de atención promedio
        //double promedioPreferencial = calcularPromedioAtencion(listaPreferencial);
        //double promedioRapida = calcularPromedioAtencion(listaRapida);
        //double promedioGeneral = calcularPromedioAtencion(listaGeneral);

        //Caja con el mejor tiempo de atención promedio
        String cajaMejorTiempo = "Preferencial";
        double mejorTiempo = totalClientesPreferencial > 0 ? (double) totalTiempoPreferencial / totalClientesPreferencial : Double.MAX_VALUE;//promedioPreferencial;

        if (totalClientesRapida > 0 && (double) totalTiempoRapida / totalClientesRapida < mejorTiempo) {//promedioRapida < mejorTiempo) {
            cajaMejorTiempo = "Rapido";
            mejorTiempo = (double) totalTiempoRapida / totalClientesRapida;//promedioRapida;
        }

        if (totalClientesGeneral > 0 && (double) totalTiempoGeneral / totalClientesGeneral < mejorTiempo){//promedioGeneral < mejorTiempo) {
            cajaMejorTiempo = "General";
            mejorTiempo = (double) totalTiempoGeneral / totalClientesGeneral;//promedioGeneral;
        }

        txtReporte3.setText(cajaMejorTiempo + " (" + String.format("%.2f", mejorTiempo) + " seg)");

        // Reporte 4: Tiempo promedio de atención general
        double tiempoPromedioGeneral = totalClientesAtendidos > 0 ? (double) totalTiempoAtencion / totalClientesAtendidos : 0; //(promedioPreferencial + promedioRapida + promedioGeneral) / 3;
        txtReporte4.setText(String.format("%.2f segundos", tiempoPromedioGeneral));
    }

    /*private double calcularPromedioAtencion(ListaDobleClientes lista) {
        NodoCliente actual = lista.getCabeza();
        long totalTiempo = 0;
        int contador = 0;

        while (actual != null) {
            Cliente cliente = actual.getCliente();
            if (cliente.getHoraAtencion() != null && cliente.getHoraCreacion() != null) {
                long tiempoAtencion = cliente.getHoraAtencion().getSecond() - cliente.getHoraCreacion().getSecond();
                totalTiempo += tiempoAtencion;
                contador++;
            }
            actual = actual.getSiguiente();
        }

        return contador > 0 ? (double) totalTiempo / contador : Double.MAX_VALUE;
    }*/

    public String[] leerConfiguracion() {
        String[] configuracion = new String[2];
        try (BufferedReader reader = new BufferedReader(new FileReader("prod.txt"))) {
            configuracion[0] = reader.readLine(); // Nombre del banco
            configuracion[1] = reader.readLine(); // Cantidad de cajas
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al leer la configuración del archivo", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return configuracion;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTipoCambio = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblNombreDeBanco = new javax.swing.JLabel();
        btnIngresarCliente = new javax.swing.JButton();
        btnAtenderCliente = new javax.swing.JButton();
        btnRoportes = new javax.swing.JButton();
        btnConfiguracionSistema = new javax.swing.JButton();
        lblNombreDeBanco1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblTipoCambio1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblReporte1 = new javax.swing.JLabel();
        txtReporte1 = new javax.swing.JTextField();
        lblReporte2 = new javax.swing.JLabel();
        txtReporte2 = new javax.swing.JTextField();
        lblReporte3 = new javax.swing.JLabel();
        txtReporte3 = new javax.swing.JTextField();
        lblReporte4 = new javax.swing.JLabel();
        txtReporte4 = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();

        lblTipoCambio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

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
        btnAtenderCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtenderClienteActionPerformed(evt);
            }
        });

        btnRoportes.setBackground(new java.awt.Color(46, 156, 94));
        btnRoportes.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRoportes.setForeground(new java.awt.Color(255, 255, 255));
        btnRoportes.setText("Reportes");

        btnConfiguracionSistema.setBackground(new java.awt.Color(46, 156, 94));
        btnConfiguracionSistema.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnConfiguracionSistema.setForeground(new java.awt.Color(255, 255, 255));
        btnConfiguracionSistema.setText("Configuracion");
        btnConfiguracionSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfiguracionSistemaActionPerformed(evt);
            }
        });

        lblNombreDeBanco1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Logo Cajas para bancos.png"))); // NOI18N

        lblTipoCambio1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnConfiguracionSistema, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRoportes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAtenderCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnIngresarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(lblNombreDeBanco1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblNombreDeBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(60, 60, 60)
                    .addComponent(lblTipoCambio1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(60, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(lblNombreDeBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(lblNombreDeBanco1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(56, 56, 56)
                .addComponent(btnIngresarCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAtenderCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRoportes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnConfiguracionSistema)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(253, 253, 253)
                    .addComponent(lblTipoCambio1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(254, Short.MAX_VALUE)))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Reportes del sistema");

        lblReporte1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblReporte1.setText("Caja que atendio la mayor cantidad de clientes:");

        txtReporte1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtReporte1ActionPerformed(evt);
            }
        });

        lblReporte2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblReporte2.setText("Total de clientes atendidos:");

        txtReporte2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtReporte2ActionPerformed(evt);
            }
        });

        lblReporte3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblReporte3.setText("Caja con el mejor tiempo de atencion:");

        txtReporte3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtReporte3ActionPerformed(evt);
            }
        });

        lblReporte4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblReporte4.setText("Tiempo promedio de atencion:");

        txtReporte4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtReporte4ActionPerformed(evt);
            }
        });

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
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(250, 250, 250)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblReporte1)
                            .addComponent(lblReporte2)
                            .addComponent(lblReporte3)
                            .addComponent(lblReporte4)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtReporte2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtReporte1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtReporte3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtReporte4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(253, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(74, 74, 74)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReporte1)
                    .addComponent(txtReporte1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReporte2)
                    .addComponent(txtReporte2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtReporte3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblReporte3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReporte4)
                    .addComponent(txtReporte4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(btnCancelar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIngresarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarClienteActionPerformed
        // TODO add your handling code here:
        new IngresarCliente().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnIngresarClienteActionPerformed

    private void btnAtenderClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtenderClienteActionPerformed
        // TODO add your handling code here:
        ListaDobleClientes listaPreferencial = new ListaDobleClientes();
        ListaDobleClientes listaRapida = new ListaDobleClientes();
        ListaDobleClientes listaGeneral = new ListaDobleClientes();

        new AtenderCliente(listaPreferencial, listaRapida, listaGeneral).setVisible(true);

        this.dispose();
    }//GEN-LAST:event_btnAtenderClienteActionPerformed

    private void btnConfiguracionSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfiguracionSistemaActionPerformed
        // TODO add your handling code here:
        new SolicitudConfiguracion().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnConfiguracionSistemaActionPerformed

    private void txtReporte1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtReporte1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtReporte1ActionPerformed

    private void txtReporte2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtReporte2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtReporte2ActionPerformed

    private void txtReporte3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtReporte3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtReporte3ActionPerformed

    private void txtReporte4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtReporte4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtReporte4ActionPerformed

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
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ListaDobleClientes listaPreferencial = new ListaDobleClientes();
                ListaDobleClientes listaRapida = new ListaDobleClientes();
                ListaDobleClientes listaGeneral = new ListaDobleClientes();
                new Reportes(listaPreferencial, listaRapida, listaGeneral).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtenderCliente;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfiguracionSistema;
    private javax.swing.JButton btnIngresarCliente;
    private javax.swing.JButton btnRoportes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblNombreDeBanco;
    private javax.swing.JLabel lblNombreDeBanco1;
    private javax.swing.JLabel lblReporte1;
    private javax.swing.JLabel lblReporte2;
    private javax.swing.JLabel lblReporte3;
    private javax.swing.JLabel lblReporte4;
    private javax.swing.JLabel lblTipoCambio;
    private javax.swing.JLabel lblTipoCambio1;
    private javax.swing.JTextField txtReporte1;
    private javax.swing.JTextField txtReporte2;
    private javax.swing.JTextField txtReporte3;
    private javax.swing.JTextField txtReporte4;
    // End of variables declaration//GEN-END:variables
}
