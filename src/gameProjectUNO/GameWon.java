/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameProjectUNO;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Rashmika
 */
public class GameWon extends javax.swing.JFrame {

    /**
     * Creates new form GameWon
     */
    public GameWon() {
        try {
            PlayGame pg = new PlayGame();
            pg.playMusicStarts("sounds/game_won.wav");
        } catch (IOException ex) {
            Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
        }
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

        jPanel1 = new javax.swing.JPanel();
        Play_Again_Button = new javax.swing.JButton();
        Quit_Button = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(796, 690));
        setResizable(false);

        jPanel1.setAlignmentX(300.0F);
        jPanel1.setPreferredSize(new java.awt.Dimension(796, 690));
        jPanel1.setLayout(null);

        Play_Again_Button.setFont(new java.awt.Font("Jokerman", 1, 24)); // NOI18N
        Play_Again_Button.setForeground(new java.awt.Color(0, 0, 0));
        Play_Again_Button.setBorder(null);
        Play_Again_Button.setBorderPainted(false);
        Play_Again_Button.setContentAreaFilled(false);
        Play_Again_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Play_Again_ButtonActionPerformed(evt);
            }
        });
        jPanel1.add(Play_Again_Button);
        Play_Again_Button.setBounds(210, 40, 370, 350);

        Quit_Button.setFont(new java.awt.Font("Jokerman", 1, 24)); // NOI18N
        Quit_Button.setForeground(new java.awt.Color(255, 51, 0));
        Quit_Button.setText("TAP THE TROPHY TO PLAY AGAIN AND TAP HERE TO QUIT");
        Quit_Button.setBorder(null);
        Quit_Button.setBorderPainted(false);
        Quit_Button.setContentAreaFilled(false);
        Quit_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Quit_ButtonActionPerformed(evt);
            }
        });
        jPanel1.add(Quit_Button);
        Quit_Button.setBounds(0, 610, 800, 50);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gameProjectUNO/UNOimages/won/won.jpg"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 0, 1000, 650);

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

    private void Play_Again_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Play_Again_ButtonActionPerformed
        PlayGame pg=new PlayGame();
        try {
            pg.playMusicStarts("sounds/click.wav");
        } catch (IOException ex) {
            Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
        new GameMenu().setVisible(true);
    }//GEN-LAST:event_Play_Again_ButtonActionPerformed

    private void Quit_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Quit_ButtonActionPerformed
        PlayGame pg=new PlayGame();
        try {
            pg.playMusicStarts("sounds/click.wav");
        } catch (IOException ex) {
            Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }//GEN-LAST:event_Quit_ButtonActionPerformed

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
            java.util.logging.Logger.getLogger(GameWon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameWon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameWon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameWon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameWon().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Play_Again_Button;
    private javax.swing.JButton Quit_Button;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
