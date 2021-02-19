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
import java.util.Random;
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
public class DefaultPlayer extends javax.swing.JFrame {
    String cardImage="";
    ArrayList<String> temp = new ArrayList<>(); //temporary arraylist
    ArrayList<String> cardIds= new ArrayList<>();; //gets card values of the player to an ArrayList
    ArrayList<String> cardIds2= new ArrayList<>();; //gets card values of the PC to an ArrayList 
    Default defGame; //object of the class where single player mode happens
    String [] playids; //take the names of the all the players
    ArrayList <JButton> cardButtons = new ArrayList<JButton>(); //gets card buttons of the player to an ArrayList
    ArrayList <JButton> cardButtons2 = new ArrayList<JButton>(); //gets card buttons of the player to an ArrayList

    ArrayList<CardPropertiesUNO> playerHand; //gets the cards of a player as an ArrayList
    int choice; //value of the index which player selects a card from its hand
    String pc="Corona"; //PC's name is given as Tim before the game
    CardPropertiesUNO.cardColours declaredCol; //track the declared colour throught the game
    boolean won=false;
    int index; //number of the button
    
    public DefaultPlayer(){} //defaultplayer constructor
    public DefaultPlayer(ArrayList<String> playerids,int [] score) {
        playerids.add(pc); //add the PC's name to the playing names
        initComponents();
        temp=playerids;
        playids= temp.toArray(new String[temp.size()]);
        defGame = new Default(playids,score,this); //distribute the cards to players
        populateArrayList(); //add card buttons of the player
        populateArrayListDefault(); //add card buttons of the PC
        defGame.start(defGame); //start the game
        setPlayNames(playids[0]); //display the player's name above his/her cards in the UI
        if(defGame.use==true){ //if PC gets the chance in the begining because of a SKIP or REVERSE card
            while(defGame.givePlayer()==1){
                setButtonIcons();
                defGame.useDefaultCard();
                col();
            }
        }
        topCardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("UNOimages/large/"+defGame.topCardImage())));
        setButtonIcons();
        won=false;
    }
    
    //method to set the player name
    public void setPlayNames(String currentPlayer){
        playNameLabel.setText(currentPlayer+"'s cards");
    }
    
    public void delay(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(DefaultPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setCardColourPC(){
        //PC decides a colour after a WILD or WILD FOUR card using random function
        Random rand=new Random();
        int randVal=rand.nextInt(4); //generate random number from 0-3
        if(randVal==0) //if the value is 0 set card colour as BLUE
            defGame.setCardColour(CardPropertiesUNO.cardColours.BLUE);
                
        else if(randVal==1) //if the value is 1 set card colour as YELLOW
            defGame.setCardColour(CardPropertiesUNO.cardColours.YELLOW);
                
        else if(randVal==2) //if the value is 2 set card colour as GREEN
            defGame.setCardColour(CardPropertiesUNO.cardColours.GREEN);
                
        else if(randVal==3) //if the value is 3 set card colour as RED
            defGame.setCardColour(CardPropertiesUNO.cardColours.RED);
    }
    
    //method to set the top card and check whether the PC entered card was a WILD or WILD FOUR
    public void col(){
        setButtonIcons();
        topCardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("UNOimages/large/"+defGame.topCardImage())));
        
        //if PC has't drawn a card from the deck(used==true) or at the begining player direction changed(use==true) 
        if(defGame.used==true || defGame.use==true){
            if(defGame.giveValidCard()==CardPropertiesUNO.cardValues.WILD){
                setCardColourPC();
                JLabel msg4 = new JLabel("Corona used a WILD card and declared colour is "+defGame.giveValidCardCol());
                msg4.setFont(new Font("Arial",Font.BOLD,42));
                JOptionPane.showMessageDialog(null,msg4);
                
            }
            
            else if(defGame.giveValidCard()==CardPropertiesUNO.cardValues.WILDFOUR){
                setCardColourPC();
                ImageIcon icon=new ImageIcon(PlayGame.class.getResource("UNOimages/bluffer/bluffer.png"));
                JLabel msg4 = new JLabel("Corona used a WILD FOUR card and declared colour is "+defGame.giveValidCardCol());
                msg4.setFont(new Font("Arial",Font.BOLD,42));
                JOptionPane.showMessageDialog(null,msg4);
                JLabel msg3 = new JLabel(playids[0]+" will draw extra 4 cards");
                msg3.setFont(new Font("Arial",Font.BOLD,42));
                JOptionPane.showMessageDialog(null,msg3);
            }
        }
    }
    
    //method to play button sounds
    public void buttonSounds(){
        PlayGame pg=new PlayGame();
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
    
    public void wildCard(){
        //if the player entered card is a WILD PC gets the player Turn
        if(defGame.giveValidCard()==CardPropertiesUNO.cardValues.WILD || defGame.giveValidCard()==CardPropertiesUNO.cardValues.WILDFOUR){
            while(defGame.givePlayer()==1){
                defGame.useDefaultCard();
                buttonSounds();
                col();
            }
        }
        
    }
    
    //control all the button actions
    public void buttonActions(int i){
        buttonSounds();
        index=i;
        useCard(cardImage, defGame, index, cardButtons, this.topCardButton);
            PickColFrameDef frame = new PickColFrameDef(this.topCardButton,this); //used to check if the player,s card is a wild card
            declaredCol=frame.chooseCol(playerHand.get(choice)); //declare the colour of the player's card
            if(declaredCol != null){
            try {
                //submit the player's card
                defGame.submitPlayCard(defGame.currentPlayer(),playerHand.get(choice), declaredCol);
            } catch (InvalidCardColourSubmissionException ex) {
                Logger.getLogger(DefaultPlayer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidCardValueSubmissionException ex) {
                Logger.getLogger(DefaultPlayer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidPlayerTurnException ex) {
                Logger.getLogger(DefaultPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //if the entered card is not a WILD or WILD FOUR card
            if(declaredCol != CardPropertiesUNO.cardColours.OTHER){
                setButtonIcons();
                this.topCardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("UNOimages/large/"+defGame.topCardImage())));
                
                //controls the use of PC's(Corona's) cards(PC's index in the playids is 1) 
                while(defGame.givePlayer()==1){
                    JLabel msg2 = new JLabel("Corona's Turn!");
                    msg2.setFont(new Font("Arial",Font.BOLD,42));
                    JOptionPane.showMessageDialog(frame, msg2);
                    
                    //repeat all the steps until player index gets 0(0 is the player's index)
                    defGame.useDefaultCard(); //method to control the entering of cards of the PC 
                    buttonSounds();//play button sounds
                    col();  //method to set the top card and check if the entered pc's card was a WILD or WILD FOUR
                }
                    
            }
        }
    }
    
    //set all button icons where cards are present
    public void setButtonIcons(){
        String listString = defGame.playerHand(playids[0]).stream().map(Object::toString).collect(Collectors.joining(","));
        String [] cardNames = listString.split(",");
        cardIds = new ArrayList<>(Arrays.asList(cardNames));
        
        //gets every card images(getting all the icons that player has)
        for(int i=0;i<cardIds.size();i++){
            cardButtons.get(i).setIcon(new javax.swing.ImageIcon(getClass().getResource("UNOimages/small/"+cardIds.get(i)+ ".png")));  
            
        }
        
        //don't set icons to the buttons where cards are not present
        for(int j=cardIds.size();j<cardButtons.size();j++){
            cardButtons.get(j).setIcon(null);
        }
        
        String listString2 = defGame.playerHand(playids[1]).stream().map(Object::toString).collect(Collectors.joining(","));
        String [] cardNames2 = listString2.split(",");
        cardIds2 = new ArrayList<>(Arrays.asList(cardNames2));
         int k;
        //gets every card images(getting all the icons that player has)
        for(k=0;k<cardIds2.size();k++){
            cardButtons2.get(k).setIcon(new javax.swing.ImageIcon(getClass().getResource("UNOimages/small/"+cardIds2.get(k)+ ".png")));  
            //turning around PC's cards by replacing the back card
            cardButtons2.get(k).setIcon(new javax.swing.ImageIcon(getClass().getResource("UNOimages/small/CARD_BACK.png")));
        }
        
        //don't set icons to the buttons where cards are not present
        for(int a=cardIds2.size();a<cardButtons2.size();a++){
            cardButtons2.get(a).setIcon(null);
        }
      
    }
    
    //add the card buttons
    public void populateArrayListDefault(){
        cardButtons2.add(jButton1);
        cardButtons2.add(jButton2);
        cardButtons2.add(jButton3);
        cardButtons2.add(jButton4);
        cardButtons2.add(jButton5);
        cardButtons2.add(jButton6);
        cardButtons2.add(jButton7);
        cardButtons2.add(jButton8);
        cardButtons2.add(jButton9);
        cardButtons2.add(jButton10);
        cardButtons2.add(jButton11);
        cardButtons2.add(jButton12);
        cardButtons2.add(jButton13);
        cardButtons2.add(jButton14);
        cardButtons2.add(jButton15);  
    }
    
    //add the card buttons
    public void populateArrayList(){
        cardButtons.add(jButton16);
        cardButtons.add(jButton17);
        cardButtons.add(jButton18);
        cardButtons.add(jButton19);
        cardButtons.add(jButton20);
        cardButtons.add(jButton21);
        cardButtons.add(jButton22);
        cardButtons.add(jButton23);
        cardButtons.add(jButton24);
        cardButtons.add(jButton25);
        cardButtons.add(jButton26);
        cardButtons.add(jButton27);
        cardButtons.add(jButton28);
        cardButtons.add(jButton29);
        cardButtons.add(jButton30); 
    }
    
    //method to check the using card/cards of the player
    public void useCard(String cardName,Default game,int index,ArrayList<JButton> cardButton,JButton topCardButton)
    {
        cardImage= cardName;
        defGame=game;
        playerHand= game.playerHand(game.currentPlayer());
        choice = index;
        this.cardButtons=cardButtons;
        this.topCardButton=topCardButton;
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
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jButton30 = new javax.swing.JButton();
        DrawCardButton = new javax.swing.JButton();
        topCardButton = new javax.swing.JButton();
        playNameLabel = new javax.swing.JLabel();
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
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton31 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1320, 780));
        setResizable(false);

        jPanel1.setPreferredSize(new java.awt.Dimension(1320, 780));
        jPanel1.setLayout(null);

        jButton16.setBorderPainted(false);
        jButton16.setContentAreaFilled(false);
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton16);
        jButton16.setBounds(20, 570, 71, 111);

        jButton17.setBorderPainted(false);
        jButton17.setContentAreaFilled(false);
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton17);
        jButton17.setBounds(100, 570, 71, 111);

        jButton18.setBorderPainted(false);
        jButton18.setContentAreaFilled(false);
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton18);
        jButton18.setBounds(180, 570, 71, 111);

        jButton19.setBorderPainted(false);
        jButton19.setContentAreaFilled(false);
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton19);
        jButton19.setBounds(260, 570, 71, 111);

        jButton20.setBorderPainted(false);
        jButton20.setContentAreaFilled(false);
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton20);
        jButton20.setBounds(340, 570, 71, 111);

        jButton21.setBorderPainted(false);
        jButton21.setContentAreaFilled(false);
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton21);
        jButton21.setBounds(420, 570, 71, 111);

        jButton22.setBorderPainted(false);
        jButton22.setContentAreaFilled(false);
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton22);
        jButton22.setBounds(500, 570, 71, 111);

        jButton23.setBorderPainted(false);
        jButton23.setContentAreaFilled(false);
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton23);
        jButton23.setBounds(580, 570, 71, 111);

        jButton24.setBorderPainted(false);
        jButton24.setContentAreaFilled(false);
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton24);
        jButton24.setBounds(660, 570, 71, 111);

        jButton25.setBorderPainted(false);
        jButton25.setContentAreaFilled(false);
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton25);
        jButton25.setBounds(740, 570, 71, 111);

        jButton26.setBorderPainted(false);
        jButton26.setContentAreaFilled(false);
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton26);
        jButton26.setBounds(820, 570, 71, 111);

        jButton27.setBorderPainted(false);
        jButton27.setContentAreaFilled(false);
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton27);
        jButton27.setBounds(900, 570, 71, 111);

        jButton28.setBorderPainted(false);
        jButton28.setContentAreaFilled(false);
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton28);
        jButton28.setBounds(980, 570, 71, 111);

        jButton29.setBorderPainted(false);
        jButton29.setContentAreaFilled(false);
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton29);
        jButton29.setBounds(1060, 570, 71, 111);

        jButton30.setBorderPainted(false);
        jButton30.setContentAreaFilled(false);
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton30);
        jButton30.setBounds(1140, 570, 71, 111);

        DrawCardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gameProjectUNO/UNOimages/large/CARD_BACK.png"))); // NOI18N
        DrawCardButton.setBorderPainted(false);
        DrawCardButton.setContentAreaFilled(false);
        DrawCardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DrawCardButtonActionPerformed(evt);
            }
        });
        jPanel1.add(DrawCardButton);
        DrawCardButton.setBounds(60, 250, 107, 167);

        topCardButton.setBorderPainted(false);
        topCardButton.setContentAreaFilled(false);
        jPanel1.add(topCardButton);
        topCardButton.setBounds(230, 250, 106, 167);

        playNameLabel.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 36)); // NOI18N
        playNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(playNameLabel);
        playNameLabel.setBounds(30, 470, 310, 55);

        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(20, 20, 71, 110);

        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(100, 20, 71, 110);

        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(180, 20, 71, 110);

        jButton4.setBorderPainted(false);
        jButton4.setContentAreaFilled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4);
        jButton4.setBounds(260, 20, 71, 110);

        jButton5.setBorderPainted(false);
        jButton5.setContentAreaFilled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5);
        jButton5.setBounds(340, 20, 71, 110);

        jButton6.setBorderPainted(false);
        jButton6.setContentAreaFilled(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6);
        jButton6.setBounds(420, 20, 71, 110);

        jButton7.setBorderPainted(false);
        jButton7.setContentAreaFilled(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton7);
        jButton7.setBounds(500, 20, 71, 110);

        jButton8.setBorderPainted(false);
        jButton8.setContentAreaFilled(false);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton8);
        jButton8.setBounds(580, 20, 71, 110);

        jButton9.setBorderPainted(false);
        jButton9.setContentAreaFilled(false);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton9);
        jButton9.setBounds(660, 20, 71, 110);

        jButton10.setBorderPainted(false);
        jButton10.setContentAreaFilled(false);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton10);
        jButton10.setBounds(740, 20, 71, 110);

        jButton11.setBorderPainted(false);
        jButton11.setContentAreaFilled(false);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton11);
        jButton11.setBounds(820, 20, 71, 110);

        jButton12.setBorderPainted(false);
        jButton12.setContentAreaFilled(false);
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton12);
        jButton12.setBounds(900, 20, 71, 110);

        jButton13.setBorderPainted(false);
        jButton13.setContentAreaFilled(false);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton13);
        jButton13.setBounds(980, 20, 71, 110);

        jButton14.setBorderPainted(false);
        jButton14.setContentAreaFilled(false);
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton14);
        jButton14.setBounds(1060, 20, 71, 110);

        jButton15.setBorderPainted(false);
        jButton15.setContentAreaFilled(false);
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton15);
        jButton15.setBounds(1140, 20, 71, 110);

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Corona's Cards");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(20, 160, 270, 55);

        jButton31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gameProjectUNO/UNOimages/buttons/UNO.png"))); // NOI18N
        jButton31.setBorder(null);
        jButton31.setBorderPainted(false);
        jButton31.setContentAreaFilled(false);
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton31);
        jButton31.setBounds(450, 390, 170, 130);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gameProjectUNO/UNOimages/themes/GAME_STAGE.jpg"))); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(0, 0, 1320, 750);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 746, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        if(cardIds.get(0)!= null){
            index=0;
            String cardid = cardIds.get(0);
            buttonActions(0);
        } 
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
         if(cardIds.get(1)!= null){
            int index=1;
            String cardid = cardIds.get(1);
            buttonActions(1);
        }
    }//GEN-LAST:event_jButton17ActionPerformed

    private void DrawCardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DrawCardButtonActionPerformed
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
        JLabel msg = new JLabel(defGame.currentPlayer()+" drew a card");
        msg.setFont(new Font("Arial",Font.BOLD,42));
        JOptionPane.showMessageDialog(null,msg);
        try{
            defGame.submit(defGame.currentPlayer());
        }
        catch (InvalidPlayerTurnException e){
            java.util.logging.Logger.getLogger(DefaultPlayer.class.getName()).log(java.util.logging.Level.SEVERE,null,e);
        }
        this.setButtonIcons();
        //topCardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("UNOimages/large/"+defGame.topCardImage())));
        while(defGame.givePlayer()==1){
            defGame.useDefaultCard();
            col();
        }
    }//GEN-LAST:event_DrawCardButtonActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        if(cardIds.get(2)!= null){
            int index=2;
            String cardid = cardIds.get(2);
            buttonActions(2);
        }
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        if(cardIds.get(3)!= null){
            int index=3;
            String cardid = cardIds.get(3);
            buttonActions(3);
        }
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        if(cardIds.get(4)!= null){
            int index=4;
            String cardid = cardIds.get(4);
            buttonActions(4);
        }
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        if(cardIds.get(5)!= null){
            int index=5;
            String cardid = cardIds.get(5);
            buttonActions(5);
        }
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        if(cardIds.get(6)!= null){
            int index=6;
            String cardid = cardIds.get(6);
            buttonActions(6);
        }
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        if(cardIds.get(7)!= null){
            int index=7;
            String cardid = cardIds.get(7);
            buttonActions(7);
        }
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        if(cardIds.get(8)!= null){
            int index=8;
            String cardid = cardIds.get(8);
            buttonActions(8);
        }
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        if(cardIds.get(9)!= null){
            int index=9;
            String cardid = cardIds.get(9);
            buttonActions(9);
        }
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        if(cardIds.get(10)!= null){
            int index=10;
            String cardid = cardIds.get(10);
            buttonActions(10);
        }
    }//GEN-LAST:event_jButton26ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        if(cardIds.get(11)!= null){
            int index=11;
            String cardid = cardIds.get(11);
            buttonActions(11);
        }
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        if(cardIds.get(12)!= null){
            int index=12;
            String cardid = cardIds.get(12);
            buttonActions(12);
        }
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        if(cardIds.get(13)!= null){
            int index=13;
            String cardid = cardIds.get(13);
            buttonActions(13);
        }
    }//GEN-LAST:event_jButton29ActionPerformed

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        if(cardIds.get(14)!= null){
            int index=14;
            String cardid = cardIds.get(14);
            buttonActions(14);
        }
    }//GEN-LAST:event_jButton30ActionPerformed

                      /*******card buttons of the PC*******/
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
 
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
  
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed

    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
  
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
 
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
  
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
 
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed

    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        if( defGame.cardsInPlayer(playids[0])==2)
        {
            defGame.uno=true;
            defGame.said=true;
            PlayGame pg = new PlayGame();
            try {
                pg.playMusicStarts("sounds/uno.wav");
            } catch (IOException ex) {
                Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
            }
            ImageIcon icon=new ImageIcon(DefaultPlayer.class.getResource("UNOimages/uno!/UNO!.png"));
            JLabel msg1 = new JLabel(playids[0]);
            msg1.setFont(new Font("Arial",Font.BOLD,42));
            JOptionPane.showMessageDialog(null, msg1,"UNO!",JOptionPane.PLAIN_MESSAGE,icon);
        }
    }//GEN-LAST:event_jButton31ActionPerformed

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
            java.util.logging.Logger.getLogger(DefaultPlayer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DefaultPlayer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DefaultPlayer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DefaultPlayer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DefaultPlayer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DrawCardButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel playNameLabel;
    public javax.swing.JButton topCardButton;
    // End of variables declaration//GEN-END:variables
}
