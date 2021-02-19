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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Rashmika
 */
public class PickColFrameDef extends javax.swing.JFrame {

    /**
     * Creates new form PickColFrameDef
     */
    private CardPropertiesUNO.cardColours wildColour=null;
    DefaultPlayer def;
    boolean allow=false;
    public int value;
    JButton topCardButton;
    
    public PickColFrameDef() {
        initComponents();
    }

    public PickColFrameDef(JButton topCardButton,DefaultPlayer def) {
        initComponents();
        this.def=def;
        this.topCardButton=topCardButton;
    }
    
    public CardPropertiesUNO.cardColours chooseCol(CardPropertiesUNO card){
        if(card.getCardColour() == CardPropertiesUNO.cardColours.OTHER){
            this.setVisible(true);
            this.setResizable(false);
            this.setBounds(100,150,662,520);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        blueButton = new javax.swing.JButton();
        yellowButton = new javax.swing.JButton();
        greenButton = new javax.swing.JButton();
        redButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(662, 520));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Jokerman", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Pick the colour of your WILD card");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(6, 44, 650, 76);

        blueButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        blueButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gameProjectUNO/UNOimages/buttons/BLUE_BUTTON.png"))); // NOI18N
        blueButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blueButtonActionPerformed(evt);
            }
        });
        jPanel1.add(blueButton);
        blueButton.setBounds(50, 210, 200, 80);

        yellowButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        yellowButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gameProjectUNO/UNOimages/buttons/YELLOW_BUTTON.png"))); // NOI18N
        yellowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yellowButtonActionPerformed(evt);
            }
        });
        jPanel1.add(yellowButton);
        yellowButton.setBounds(400, 210, 200, 80);

        greenButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        greenButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gameProjectUNO/UNOimages/buttons/GREEN_BUTTON.png"))); // NOI18N
        greenButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                greenButtonActionPerformed(evt);
            }
        });
        jPanel1.add(greenButton);
        greenButton.setBounds(50, 380, 200, 80);

        redButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        redButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gameProjectUNO/UNOimages/buttons/RED_BUTTON.png"))); // NOI18N
        redButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redButtonActionPerformed(evt);
            }
        });
        jPanel1.add(redButton);
        redButton.setBounds(410, 380, 200, 80);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gameProjectUNO/UNOimages/themes/RED.png"))); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(0, 0, 660, 520);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
        def.setButtonIcons();
        topCardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("UNOimages/large/"+def.defGame.topCardImage())));
        def.defGame.setCardColour(CardPropertiesUNO.cardColours.YELLOW);
        if(def.defGame.giveValidCard()==CardPropertiesUNO.cardValues.WILDFOUR){
            ImageIcon icon=new ImageIcon(PlayGame.class.getResource("UNOimages/bluffer/bluffer.png"));
            JLabel msg3 = new JLabel(def.playids[0]+" used a WILD FOUR card and Corona gets 4 cards"); 
            msg3.setFont(new Font("Arial",Font.BOLD,42));
            JOptionPane.showMessageDialog(null, msg3,"bluffer",JOptionPane.PLAIN_MESSAGE,icon);
        }
        def.wildCard();
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
        def.setButtonIcons();
        topCardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("UNOimages/large/"+def.defGame.topCardImage())));
        def.defGame.setCardColour(CardPropertiesUNO.cardColours.GREEN);
        if(def.defGame.giveValidCard()==CardPropertiesUNO.cardValues.WILDFOUR){
            ImageIcon icon=new ImageIcon(PlayGame.class.getResource("UNOimages/bluffer/bluffer.png"));
            JLabel msg3 = new JLabel(def.playids[0]+" used a WILD FOUR card and Corona gets 4 cards"); 
            msg3.setFont(new Font("Arial",Font.BOLD,42));
            JOptionPane.showMessageDialog(null, msg3,"bluffer",JOptionPane.PLAIN_MESSAGE,icon);
        }
        def.wildCard();
    }//GEN-LAST:event_greenButtonActionPerformed

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
        def.setButtonIcons();
        topCardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("UNOimages/large/"+def.defGame.topCardImage())));
        def.defGame.setCardColour(CardPropertiesUNO.cardColours.RED);
        if(def.defGame.giveValidCard()==CardPropertiesUNO.cardValues.WILDFOUR){
            ImageIcon icon=new ImageIcon(PlayGame.class.getResource("UNOimages/bluffer/bluffer.png"));
            JLabel msg3 = new JLabel(def.playids[0]+" used a WILD FOUR card and Corona gets 4 cards"); 
            msg3.setFont(new Font("Arial",Font.BOLD,42));
            JOptionPane.showMessageDialog(null, msg3,"bluffer",JOptionPane.PLAIN_MESSAGE,icon);
        }
        def.wildCard();
    }//GEN-LAST:event_redButtonActionPerformed

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
        def.setButtonIcons();
        topCardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("UNOimages/large/"+def.defGame.topCardImage())));
        def.defGame.setCardColour(CardPropertiesUNO.cardColours.BLUE);
        if(def.defGame.giveValidCard()==CardPropertiesUNO.cardValues.WILDFOUR){
            ImageIcon icon=new ImageIcon(PlayGame.class.getResource("UNOimages/bluffer/bluffer.png"));
            JLabel msg3 = new JLabel(def.playids[0]+" used a WILD FOUR card and Corona gets 4 cards"); 
            msg3.setFont(new Font("Arial",Font.BOLD,42));
            JOptionPane.showMessageDialog(null, msg3,"bluffer",JOptionPane.PLAIN_MESSAGE,icon);
        }
        def.wildCard();
    }//GEN-LAST:event_blueButtonActionPerformed

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
            java.util.logging.Logger.getLogger(PickColFrameDef.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PickColFrameDef.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PickColFrameDef.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PickColFrameDef.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PickColFrameDef().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton blueButton;
    private javax.swing.JButton greenButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton redButton;
    private javax.swing.JButton yellowButton;
    // End of variables declaration//GEN-END:variables
}
