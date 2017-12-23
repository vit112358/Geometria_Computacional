/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao;

import Modelo.Poligono;
import Modelo.Ponto;
import Problemas.Problemas_Classicos;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Vitor
 */
public class Interface_Area_Poligono extends javax.swing.JFrame {

    //==============Minhas Váriaveis============================================
    private static int state;
    private static ArrayList<Ponto> lista_Pontos;
    private static int estado;
    private static Problemas.Problemas_Classicos problemas = new Problemas_Classicos();
    private boolean desenha = false;
    private boolean limpar = false;
    double mouseX, mouseY;

    //==========================================================================
    /**
     * Creates new form Interface_Area_Poligono
     */
    public Interface_Area_Poligono() {
        state = 0;
        lista_Pontos = new ArrayList<>();
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.clearRect(0, 0, this.getWidth(), this.getHeight());
                g.setColor(Color.BLACK);

                if (desenha) {

                    if (state == 0) {
                        g.fillOval((int) lista_Pontos.get(state).x -3, (int) lista_Pontos.get(state).y -3, 7, 7);
                        g.drawLine((int) lista_Pontos.get(state).x , (int) lista_Pontos.get(state).y, (int) mouseX,
                            (int) mouseY);
                    } else {
                        for (int i = 0; i < lista_Pontos.size() - 1; i++) {
                            g.fillOval((int) lista_Pontos.get(i).x -3, (int) lista_Pontos.get(i).y -3, 7, 7);
                            g.drawLine((int) (int) lista_Pontos.get(i).x, (int) lista_Pontos.get(i).y,
                                (int) lista_Pontos.get(i + 1).x, (int) lista_Pontos.get(i + 1).y);
                        }
                        g.fillOval((int) lista_Pontos.get(lista_Pontos.size() - 1).x -3,
                            (int) lista_Pontos.get(lista_Pontos.size() - 1).y -3, 7, 7);
                        g.drawLine((int) lista_Pontos.get(lista_Pontos.size() - 1).x,
                            (int) lista_Pontos.get(lista_Pontos.size() - 1).y, (int) lista_Pontos.get(0).x,
                            (int) lista_Pontos.get(0).y);

                    }

                }

            }
        };
        Btn_Calcular_area = new javax.swing.JButton();
        btn_Limpar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Geometria Computacional - Area do Polígono");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel1MouseMoved(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 298, Short.MAX_VALUE)
        );

        Btn_Calcular_area.setText("Calcular Área");
        Btn_Calcular_area.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Btn_Calcular_areaMouseClicked(evt);
            }
        });

        btn_Limpar.setText("Limpar");
        btn_Limpar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_LimparMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 383, Short.MAX_VALUE)
                        .addComponent(btn_Limpar)
                        .addGap(18, 18, 18)
                        .addComponent(Btn_Calcular_area)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Btn_Calcular_area)
                    .addComponent(btn_Limpar))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        estado = 0;
    }//GEN-LAST:event_formWindowActivated

    private void jPanel1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseMoved
        // TODO add your handling code here:
        mouseX = evt.getX();
        mouseY = evt.getY();

        jPanel1.repaint();
    }//GEN-LAST:event_jPanel1MouseMoved

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        // TODO add your handling code here:
        Ponto aux = new Ponto(evt.getX(), evt.getY());

        if (lista_Pontos.isEmpty()) {
            lista_Pontos.add(aux);
            desenha = true;
        } else {
            lista_Pontos.add(aux);
            state++;
        }

        if (state >= 2) {
            Btn_Calcular_area.setEnabled(true);
        }

        jPanel1.repaint();
    }//GEN-LAST:event_jPanel1MouseClicked

    private void Btn_Calcular_areaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_Calcular_areaMouseClicked
        // TODO add your handling code here:
        Poligono p = new Poligono();
        lista_Pontos.forEach((ponto) -> {
            p.insereVertice(ponto);
        });
        double resultado = problemas.areaPoligono(p);

        String texto = "Lista de Pontos: \n";

        texto = lista_Pontos.stream().map((ponto) -> "Ponto: (" + ponto.x + "," + ponto.y + ")\n").reduce(texto, String::concat);

        texto += "Resultado: " + resultado;
        JOptionPane.showMessageDialog(null, texto);
    }//GEN-LAST:event_Btn_Calcular_areaMouseClicked

    private void btn_LimparMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_LimparMouseClicked
        // TODO add your handling code here:
        desenha = false;
        Btn_Calcular_area.setEnabled(false);
        state = 0;
        lista_Pontos.clear();
        jPanel1.repaint();
    }//GEN-LAST:event_btn_LimparMouseClicked

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interface_Area_Poligono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interface_Area_Poligono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interface_Area_Poligono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interface_Area_Poligono.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interface_Area_Poligono().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_Calcular_area;
    private javax.swing.JButton btn_Limpar;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}