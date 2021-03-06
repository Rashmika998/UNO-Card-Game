/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameProjectUNO;

import java.awt.Font;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Rashmika
 */
public class PickColFrame extends javax.swing.JFrame {

    private CardPropertiesUNO.cardColours wildColour=null;
    Boolean allow = false;
    JButton topCardButton;
    MultiPlayerGameStage gameStage;
    
    public PickColFrame() {
        initComponents();
    }
    
    public PickColFrame(JButton topCardButton,MultiPlayerGameStage gameStage) {
        initComponents();
        this.gameStage=gameStage;
        this.topCardButton=topCardButton;
    }
    
    public CardPropertiesUNO.cardColours chooseCol(CardPropertiesUNO card){
        if(card.getCardColour() == CardPropertiesUNO.cardColours.OTHER){
            this.setVisible(true);
            this.setResizable(false);
            this.setBounds(100,150,662,517);
        }
        return card.getCardColour();
    }    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        blueButton = new javax.swing.JButton();
        yellowButton = new javax.swing.JButton();
        greenButton = new javax.swing.JButton();
        redButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 500, 400));
        setLocation(new java.awt.Point(200, 500));
        setPreferredSize(new java.awt.Dimension(662, 517));
        setResizable(false);
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Jokerman", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Pick the colour of your WILD card");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 60, 650, 76);

        blueButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        blueButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gameProjectUNO/UNOimages/buttons/BLUE_BUTTON.png"))); // NOI18N
        blueButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blueButtonActionPerformed(evt);
            }
        });
        getContentPane().add(blueButton);
        blueButton.setBounds(50, 190, 200, 80);

        yellowButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        yellowButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gameProjectUNO/UNOimages/buttons/YELLOW_BUTTON.png"))); // NOI18N
        yellowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yellowButtonActionPerformed(evt);
            }
        });
        getContentPane().add(yellowButton);
        yellowButton.setBounds(410, 190, 200, 80);

        greenButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        greenButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gameProjectUNO/UNOimages/buttons/GREEN_BUTTON.png"))); // NOI18N
        greenButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                greenButtonActionPerformed(evt);
            }
        });
        getContentPane().add(greenButton);
        greenButton.setBounds(50, 370, 200, 80);

        redButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        redButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gameProjectUNO/UNOimages/buttons/RED_BUTTON.png"))); // NOI18N
        redButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redButtonActionPerformed(evt);
            }
        });
        getContentPane().add(redButton);
        redButton.setBounds(410, 370, 200, 80);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gameProjectUNO/UNOimages/themes/RED.png"))); // NOI18N
        jLabel2.setPreferredSize(new java.awt.Dimension(500, 400));
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, 0, 660, 520);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void blueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blueButtonActionPerformed
        PlayGame pg= new PlayGame();
        try {
            pg.playMusicStarts("sounds/click.wav");
        } catch (IOException ex) {
            Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        wildColour=CardPropertiesUNO.cardColours.BLUE;
        JLabel msg = new JLabel("WILD Card colour is BLUE");
        msg.setFont(new Font("Arial",Font.BOLD,42));
        JOptionPane.showMessageDialog(null,msg);
        allow=true;
        this.dispose();
        gameStage.setPlayNames(gameStage.game.currentPlayer());
        gameStage.setButtonIcons();
        topCardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("UNOimages/large/"+gameStage.game.topCardImage())));
        gameStage.game.setCardColour(CardPropertiesUNO.cardColours.BLUE);
         gameStage.setPlayNames(gameStage.game.currentPlayer());
    }//GEN-LAST:event_blueButtonActionPerformed

    private void redButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redButtonActionPerformed
        PlayGame pg= new PlayGame();
        try {
            pg.playMusicStarts("sounds/click.wav");
        } catch (IOException ex) {
            Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        wildColour=CardPropertiesUNO.cardColours.RED;
        JLabel msg = new JLabel("WILD Card colour is RED");
        msg.setFont(new Font("Arial",Font.BOLD,42));
        JOptionPane.showMessageDialog(null,msg);
        allow=true;
        this.dispose();
        gameStage.setPlayNames(gameStage.game.currentPlayer());
        gameStage.setButtonIcons();
        topCardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("UNOimages/large/"+gameStage.game.topCardImage())));
        gameStage.game.setCardColour(CardPropertiesUNO.cardColours.RED);
    }//GEN-LAST:event_redButtonActionPerformed

    private void yellowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yellowButtonActionPerformed
        PlayGame pg= new PlayGame();
        try {
            pg.playMusicStarts("sounds/click.wav");
        } catch (IOException ex) {
            Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        wildColour=CardPropertiesUNO.cardColours.YELLOW;
        JLabel msg = new JLabel("WILD Card colour is YELLOW");
        msg.setFont(new Font("Arial",Font.BOLD,42));
        JOptionPane.showMessageDialog(null,msg);
        allow=true;
        this.dispose();
        gameStage.setPlayNames(gameStage.game.currentPlayer());
        gameStage.setButtonIcons();
        topCardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("UNOimages/large/"+gameStage.game.topCardImage())));
        gameStage.game.setCardColour(CardPropertiesUNO.cardColours.YELLOW);
    }//GEN-LAST:event_yellowButtonActionPerformed

    private void greenButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_greenButtonActionPerformed
        PlayGame pg= new PlayGame();
        try {
            pg.playMusicStarts("sounds/click.wav");
        } catch (IOException ex) {
            Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        wildColour=CardPropertiesUNO.cardColours.GREEN;
        JLabel msg = new JLabel("WILD Card colour is GREEN");
        msg.setFont(new Font("Arial",Font.BOLD,42));
        JOptionPane.showMessageDialog(null,msg);
        allow=true;
        this.dispose();
        gameStage.setPlayNames(gameStage.game.currentPlayer());
        gameStage.setButtonIcons();
        topCardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("UNOimages/large/"+gameStage.game.topCardImage())));
        gameStage.game.setCardColour(CardPropertiesUNO.cardColours.GREEN);
    }//GEN-LAST:event_greenButtonActionPerformed

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
            java.util.logging.Logger.getLogger(PickColFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PickColFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PickColFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PickColFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PickColFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton blueButton;
    private javax.swing.JButton greenButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton redButton;
    private javax.swing.JButton yellowButton;
    // End of variables declaration//GEN-END:variables
}
