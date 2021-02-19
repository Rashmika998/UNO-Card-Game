/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameProjectUNO;

import java.awt.Font; 
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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


public class MultiPlayerGameStage extends javax.swing.JFrame {
    
    private AddPlayersNames addPlayNames = new AddPlayersNames();
    ArrayList<String> temp = new ArrayList<>();
    String [] playids;
    PlayGame game;
    ArrayList <JButton> cardButtons = new ArrayList<JButton>();
    ArrayList<String> cardIds;
    int index;
    int choice;
    String cardImage="";
    CardPropertiesUNO.cardColours declaredCol;
    ArrayList<CardPropertiesUNO> playerHand;
    
    public MultiPlayerGameStage(){}
    public MultiPlayerGameStage(ArrayList<String> playerids,int [] score) { 
        initComponents();
        temp=playerids;
        playids= temp.toArray(new String[temp.size()]);
        game = new PlayGame(playids,score,this);
        populateArrayList();
        game.start(game);
        setPlayNames();
        topCardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("UNOimages/large/"+game.topCardImage())));
        setButtonIcons();
    }
    
    
    //returns array of uno cards to a single list
    public void setButtonIcons(){
        String listString = game.playerHand(game.currentPlayer()).stream().map(Object::toString).collect(Collectors.joining(","));
        String [] cardNames = listString.split(",");
        cardIds = new ArrayList<>(Arrays.asList(cardNames));
        //gets every card images(getting all the icons that player has)
        for(int i=0;i<cardIds.size();i++){
            cardButtons.get(i).setIcon(new javax.swing.ImageIcon(getClass().getResource("UNOimages/small/"+cardIds.get(i)+ ".png")));  
        }
        //set all card icons to null if they are not in the player's hand
        for(int j=cardIds.size();j<cardButtons.size();j++){
            cardButtons.get(j).setIcon(null);
        }    
    }
    
    public void useCard(String cardName,PlayGame game,int index,ArrayList<JButton> cardButton,JButton topCardButton)
    {
        cardImage= cardName;
        this.game=game;
        playerHand= game.playerHand(game.currentPlayer());
        choice = index;
        this.cardButtons=cardButtons;
        this.topCardButton=topCardButton;
    }
    
    public void buttonActions(int i){
        //buttonSounds();
        index=i;
        useCard(cardImage, game, index, cardButtons, topCardButton);
        PickColFrame frame = new PickColFrame(topCardButton,this); //used to check if the player,s card is a wild card
        declaredCol=frame.chooseCol(playerHand.get(choice)); //declare the colour of the player's card
        if(declaredCol != null){
            try {
                //submit the player's card
                game.submitPlayCard(game.currentPlayer(),playerHand.get(choice), declaredCol);
                    
            } catch (InvalidCardColourSubmissionException ex) {
                Logger.getLogger(MultiPlayerGameStage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidCardValueSubmissionException ex) {
                Logger.getLogger(MultiPlayerGameStage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidPlayerTurnException ex) {
                Logger.getLogger(MultiPlayerGameStage.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(declaredCol != CardPropertiesUNO.cardColours.OTHER){
                setPlayNames(game.currentPlayer());
                setButtonIcons();
                topCardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("UNOimages/large/"+game.topCardImage())));
            }
            
            
        }
    }        
    
    public void cardSounds(){
        PlayGame pg= new PlayGame();
        try {
            pg.playMusicStarts("sounds/card_flip.wav");
        } catch (IOException ex) {
            Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void populateArrayList(){
        cardButtons.add(jButton1);
        cardButtons.add(jButton2);
        cardButtons.add(jButton3);
        cardButtons.add(jButton4);
        cardButtons.add(jButton5);
        cardButtons.add(jButton6);
        cardButtons.add(jButton7);
        cardButtons.add(jButton8);
        cardButtons.add(jButton9);
        cardButtons.add(jButton10);
        cardButtons.add(jButton11);
        cardButtons.add(jButton12);
        cardButtons.add(jButton13);
        cardButtons.add(jButton14);
        cardButtons.add(jButton18);
      
    }
    //sets which player's cards are visible 
    public void setPlayNames(){
        String currentPlayer = game.currentPlayer();
        playNameLabel.setText(currentPlayer+"'s cards");
    }
    
    //sets which player's cards are visible
    public void setPlayNames(String currentPlayer){
        playNameLabel.setText(currentPlayer+"'s cards");
    }            
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        playNameLabel = new javax.swing.JLabel();
        downCard = new javax.swing.JButton();
        topCardButton = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("UNO");
        setPreferredSize(new java.awt.Dimension(1321, 720));
        getContentPane().setLayout(null);

        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(20, 570, 71, 112);

        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(100, 570, 71, 111);

        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3);
        jButton3.setBounds(180, 570, 71, 111);

        jButton4.setBorderPainted(false);
        jButton4.setContentAreaFilled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4);
        jButton4.setBounds(260, 570, 71, 111);

        jButton5.setBorderPainted(false);
        jButton5.setContentAreaFilled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5);
        jButton5.setBounds(340, 570, 71, 111);

        jButton6.setBorderPainted(false);
        jButton6.setContentAreaFilled(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton6);
        jButton6.setBounds(420, 570, 71, 111);

        jButton7.setBorderPainted(false);
        jButton7.setContentAreaFilled(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton7);
        jButton7.setBounds(500, 570, 71, 111);

        jButton8.setBorderPainted(false);
        jButton8.setContentAreaFilled(false);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton8);
        jButton8.setBounds(580, 570, 71, 111);

        jButton9.setBorderPainted(false);
        jButton9.setContentAreaFilled(false);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton9);
        jButton9.setBounds(660, 570, 71, 111);

        jButton10.setBorderPainted(false);
        jButton10.setContentAreaFilled(false);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton10);
        jButton10.setBounds(740, 570, 71, 111);

        jButton11.setBorderPainted(false);
        jButton11.setContentAreaFilled(false);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton11);
        jButton11.setBounds(820, 570, 71, 111);

        jButton12.setBorderPainted(false);
        jButton12.setContentAreaFilled(false);
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton12);
        jButton12.setBounds(900, 570, 71, 111);

        jButton13.setBorderPainted(false);
        jButton13.setContentAreaFilled(false);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton13);
        jButton13.setBounds(980, 570, 71, 111);

        playNameLabel.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 36)); // NOI18N
        getContentPane().add(playNameLabel);
        playNameLabel.setBounds(20, 478, 400, 60);

        downCard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gameProjectUNO/UNOimages/large/CARD_BACK.png"))); // NOI18N
        downCard.setBorderPainted(false);
        downCard.setContentAreaFilled(false);
        downCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downCardActionPerformed(evt);
            }
        });
        getContentPane().add(downCard);
        downCard.setBounds(510, 70, 107, 167);

        topCardButton.setBorderPainted(false);
        topCardButton.setContentAreaFilled(false);
        topCardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                topCardButtonActionPerformed(evt);
            }
        });
        getContentPane().add(topCardButton);
        topCardButton.setBounds(720, 70, 106, 167);

        jButton18.setBorderPainted(false);
        jButton18.setContentAreaFilled(false);
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton18);
        jButton18.setBounds(1140, 570, 71, 111);

        jButton19.setBorderPainted(false);
        jButton19.setContentAreaFilled(false);
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton19);
        jButton19.setBounds(1220, 570, 71, 111);

        jButton14.setBorderPainted(false);
        jButton14.setContentAreaFilled(false);
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton14);
        jButton14.setBounds(1060, 570, 71, 111);

        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gameProjectUNO/UNOimages/buttons/UNO.png"))); // NOI18N
        jButton15.setBorder(null);
        jButton15.setBorderPainted(false);
        jButton15.setContentAreaFilled(false);
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton15);
        jButton15.setBounds(70, 330, 150, 130);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gameProjectUNO/UNOimages/themes/GAME_STAGE.jpg"))); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, 0, 1320, 720);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if(cardIds.get(5)!= null){
            cardSounds();
            int index=5;
            String cardid = cardIds.get(5);
            buttonActions(5);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(cardIds.get(0)!= null){
            cardSounds();
            int index=0;
            String cardid = cardIds.get(0);
            buttonActions(0);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(cardIds.get(1)!= null){
            cardSounds();
            int index=1;
            String cardid = cardIds.get(1);
            buttonActions(1);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if(cardIds.get(6)!= null){
            cardSounds();
            int index=6;
            String cardid = cardIds.get(6);
            buttonActions(6);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        if(cardIds.get(8)!= null){
            cardSounds();
            int index=8;
            String cardid = cardIds.get(8);
            buttonActions(0);
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if(cardIds.get(7)!= null){
            cardSounds();
            int index=7;
            String cardid = cardIds.get(7);
            buttonActions(7);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        if(cardIds.get(9)!= null){
            cardSounds();
            int index=9;
            String cardid = cardIds.get(9);
            buttonActions(9);
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        if(cardIds.get(10)!= null){
            cardSounds();
            int index=10;
            String cardid = cardIds.get(10);
            buttonActions(10);
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        if(cardIds.get(11)!= null){
            cardSounds();
            int index=11;
            String cardid = cardIds.get(11);
            buttonActions(11);
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        if(cardIds.get(12)!= null){
            cardSounds();
            int index=12;
            String cardid = cardIds.get(12);
            buttonActions(12);
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        if(cardIds.get(14)!= null){
            cardSounds();
            int index=14;
            String cardid = cardIds.get(14);
            buttonActions(14);
        }
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if(cardIds.get(2)!= null){
            cardSounds();
            int index=2;
            String cardid = cardIds.get(2);
            buttonActions(2);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if(cardIds.get(3)!= null){
            cardSounds();
            int index=3;
            String cardid = cardIds.get(3);
            buttonActions(3);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if(cardIds.get(4)!= null){
            cardSounds();
            int index=4;
            String cardid = cardIds.get(4);
            buttonActions(4);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        if(cardIds.get(13)!= null){
            cardSounds();
            int index=13;
            String cardid = cardIds.get(13);
            buttonActions(13);
        }
    }//GEN-LAST:event_jButton14ActionPerformed

    //this button doesn't perform any task
    private void downCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downCardActionPerformed
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
        JLabel msg = new JLabel(game.currentPlayer()+" drew a card");
        msg.setFont(new Font("Arial",Font.BOLD,42));
        JOptionPane.showMessageDialog(null,msg);
        
        try{
            game.submit(game.currentPlayer());
        }
        catch (InvalidPlayerTurnException e){
            java.util.logging.Logger.getLogger(MultiPlayerGameStage.class.getName()).log(java.util.logging.Level.SEVERE,null,e);
        }
        this.setPlayNames(game.currentPlayer());
        this.setButtonIcons();
    }//GEN-LAST:event_downCardActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        if(cardIds.get(15)!= null){
            int index=15;
            String cardid = cardIds.get(15);
            buttonActions(15);
        }
    }//GEN-LAST:event_jButton19ActionPerformed

    //this button doesn't perform any task
    private void topCardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_topCardButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_topCardButtonActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        if(game.cardsInPlayer(game.currentPlayer())==2){
            game.said=true;
            game.uno=true;
            try {
                game.playMusicStarts("sounds/uno.wav");
            } catch (IOException ex) {
                Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
            }
            ImageIcon icon=new ImageIcon(PlayGame.class.getResource("UNOimages/uno!/UNO!.png"));
            JLabel msg1 = new JLabel(game.currentPlayer());
            msg1.setFont(new Font("Arial",Font.BOLD,42));
            JOptionPane.showMessageDialog(null, msg1,"UNO!",JOptionPane.PLAIN_MESSAGE,icon);
        }
    }//GEN-LAST:event_jButton15ActionPerformed

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
            java.util.logging.Logger.getLogger(MultiPlayerGameStage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MultiPlayerGameStage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MultiPlayerGameStage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MultiPlayerGameStage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MultiPlayerGameStage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton downCard;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel playNameLabel;
    public javax.swing.JButton topCardButton;
    // End of variables declaration//GEN-END:variables
}
