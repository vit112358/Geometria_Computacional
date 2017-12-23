/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao;

import Modelo.Ponto;
import Modelo.Reta;
import Problemas.Problemas_Classicos;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JOptionPane;

/**
 *
 * @author Ângela
 */
public class Interface_Distancia_Ponto_Reta extends javax.swing.JFrame {

    private Reta reta;
    double mouseX, mouseY;
    private Ponto p1, p2, p3;
    private int state;
    private boolean desenha = false;
    private Problemas_Classicos classicos = new Problemas_Classicos();

    /**
     * Creates new form Interface_Distancia_Ponto_Reta
     */
    public Interface_Distancia_Ponto_Reta() {
        initComponents();
        state = 0;
        desenha = true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Btn_Calcular = new javax.swing.JButton();
        jPanel1 = 		jPanel1 = new javax.swing.JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.clearRect(0, 0, this.getWidth(), this.getHeight());
                g.setColor(Color.RED);

                if(desenha){
                    if (state == 1) {
                        g.drawLine((int) p1.getX(), (int) p1.getY(), (int) mouseX, (int) mouseY);
                    }
                    if (state >= 2) {
                        g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
                    }
                    if (state == 3) {
                        g.fillOval((int) p3.getX(), (int) p3.getY(), 5, 5);
                    }

                }    	
            }
        };
        ;
        Btn_Limpar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Geometria Computacional - Distância Entre Ponto e Reta");

        Btn_Calcular.setText("Calcular");
        Btn_Calcular.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Btn_CalcularMouseClicked(evt);
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
            .addGap(0, 374, Short.MAX_VALUE)
        );

        Btn_Limpar.setText("Limpar");
        Btn_Limpar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Btn_LimparMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 521, Short.MAX_VALUE)
                        .addComponent(Btn_Limpar, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Btn_Calcular))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Btn_Calcular)
                    .addComponent(Btn_Limpar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Btn_CalcularMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_CalcularMouseClicked
        // TODO add your handling code here:
        reta = new Reta(p1, p2);
        double resultado = classicos.distanciaPontoReta(p3, reta);

        String texto = "Reta [\nPonto 1: (" + reta.p1.x + "," + reta.p1.y + ")"
                + "\nPonto 2: (" + reta.p2.x + "," + reta.p2.y + ")" + "\n]"
                + "\n\n Ponto 3: (" + p3.x + "," + p3.y + ")"
                + "\n\nResultado: " + resultado;
        JOptionPane.showMessageDialog(null, "Valor da Distancia:  \n" + texto);
    }//GEN-LAST:event_Btn_CalcularMouseClicked

    private void jPanel1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseMoved
        // TODO add your handling code here:
        mouseX = evt.getX();
        mouseY = evt.getY();

        jPanel1.repaint();
    }//GEN-LAST:event_jPanel1MouseMoved

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        // TODO add your handling code here:
        if (state == 3) {
            state = 0;
            desenha = false;
        }

        switch (state) {
            case 0:
                p1 = new Ponto(evt.getX(), evt.getY());
                Btn_Calcular.setEnabled(false);
                desenha = true;
                state++;
                break;
            case 1:
                p2 = new Ponto(evt.getX(), evt.getY());
                Btn_Calcular.setEnabled(false);
                state++;
                desenha = true;
                break;
            case 2:
                p3 = new Ponto(evt.getX(), evt.getY());
                Btn_Calcular.setEnabled(true);
                state++;
                desenha = true;
                break;
            default:
                return;
        }

        jPanel1.repaint();
    }//GEN-LAST:event_jPanel1MouseClicked

    private void Btn_LimparMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_LimparMouseClicked
        // TODO add your handling code here:
        desenha = false;
        Btn_Calcular.setEnabled(false);
        state = 0;
        jPanel1.repaint();
    }//GEN-LAST:event_Btn_LimparMouseClicked

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
            java.util.logging.Logger.getLogger(Interface_Distancia_Ponto_Reta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interface_Distancia_Ponto_Reta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interface_Distancia_Ponto_Reta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interface_Distancia_Ponto_Reta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interface_Distancia_Ponto_Reta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_Calcular;
    private javax.swing.JButton Btn_Limpar;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
